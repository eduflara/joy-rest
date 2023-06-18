package com.joyero.app;


import com.joyero.base.exception.MensajeErrorUsuario;
import com.joyero.base.exception.ResultadoAvisoException;
import com.joyero.base.exception.ResultadoErrorException;
import com.joyero.base.exception.ResultadoException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by alejandro on 31/01/2017.
 */
@Service
public class EmailSrv {

    protected static final Logger logger = LogManager.getLogger(Logger.class.getName());
    private JavaMailSender mailSender;

    public static List<InternetAddress> validarDirecciones(List<String> direcciones, ResultadoException resultadoException) {
        EmailValidator emailValidator = new EmailValidator();
        List<InternetAddress> addresses = new LinkedList<>();

        for (String direccion : direcciones) {
            try {
                if (emailValidator.isValid(direccion, null)) {
                    InternetAddress address = new InternetAddress(direccion);
                    addresses.add(address);
                    address.validate();
                } else {
                    MensajeErrorUsuario mensajeErrorUsuario = new MensajeErrorUsuario("error.dirrecionEmail", "com.sumainfo.i18n.validationMessages");
                    mensajeErrorUsuario.addArg(direccion);
                    resultadoException.addError(mensajeErrorUsuario);
                    logger.error(mensajeErrorUsuario);
                }
            } catch (AddressException ex) {
                MensajeErrorUsuario mensajeErrorUsuario = new MensajeErrorUsuario("error.dirrecionEmail", "com.sumainfo.i18n.validationMessages", ex);
                mensajeErrorUsuario.addArg(ex.getRef());
                resultadoException.addError(mensajeErrorUsuario);
                logger.error(ex.getLocalizedMessage());
            }
        }
        return addresses;

    }

    @Autowired
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviar(final String asunto, final String texto, final String remitente, String destinoPara) {
        String[] temp = StringUtils.split(destinoPara, ",");
        List<String> destinosPara = Arrays.asList(temp);
        enviar(asunto, texto, remitente, destinosPara, new LinkedList<String>(), new LinkedList<String>());
    }

    public void enviar(final String asunto, final String texto, final String remitente, List<String> destinosPara) {
        enviar(asunto, texto, remitente, destinosPara, new LinkedList<String>(), new LinkedList<String>());
    }

    public void enviar(final String asunto, final String texto, final String remitente, List<String> destinosPara, List<String> destinosCC) {
        enviar(asunto, texto, remitente, destinosPara, destinosCC, new LinkedList<String>());
    }

    public void enviar(final String asunto, final String texto, final String remitente, List<String> destinosPara, List<String> destinosCC, List<String> destinosBCC) {
        ResultadoException resultadoException = new ResultadoException();
        final List<InternetAddress> addressesPara = EmailSrv.validarDirecciones(destinosPara, resultadoException);
        final List<InternetAddress> addressesCC = EmailSrv.validarDirecciones(destinosCC, resultadoException);
        final List<InternetAddress> addressesBCC = EmailSrv.validarDirecciones(destinosBCC, resultadoException);

        MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mm) throws Exception {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mm, "UTF-8");
                messageHelper.setSubject(asunto);
                messageHelper.setFrom(remitente);

                for (InternetAddress address : addressesPara) {
                    messageHelper.addTo(address);
                }

                for (InternetAddress address : addressesCC) {
                    messageHelper.addCc(address);
                }

                for (InternetAddress address : addressesBCC) {
                    messageHelper.addCc(address);
                }

                messageHelper.setText(texto, true);
            }
        };

        try {
            mailSender.send(messagePreparator);
        } catch (MailException ex) {
            MensajeErrorUsuario mensajeErrorUsuario = new MensajeErrorUsuario("error.envioEmail", "com.sumainfo.i18n.validationMessages", ex);
            resultadoException.addError(mensajeErrorUsuario);
            logger.error(ex.getLocalizedMessage());
        } finally {
            if (resultadoException.debeArrojarse(true)) {
                throw new ResultadoErrorException(resultadoException);
            } else if (resultadoException.debeArrojarse(false)) {
                throw new ResultadoAvisoException(resultadoException);
            }
        }
    }

}
