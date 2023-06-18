package com.joyero.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/app/jasperreport")
public class JasperReportRest {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private JasperReportSrv jasperReportSrv;

    @GetMapping(value = "/report", produces = {"application/pdf"})
    public byte[] executeReport(@RequestParam String reportName, @RequestParam Map<String, Object> parameters, @RequestParam(defaultValue = "pdf") String exportFormat) {
        parameters.put("REPORT_DIR",appConfig.getValue("jasperReport.dir"));
        jasperReportSrv.fillReport(reportName, parameters);

        File file = new File(appConfig.getValue("jasperReport.dir") + reportName + ".pdf");
        try {
            byte[] data = FileCopyUtils.copyToByteArray(file);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
