/*
 * Copyright (c) 2019.
 * ControladorRest.java
 * 24/01/19 11:29
 * alejandro
 */

package com.joyero.base;

import com.google.common.collect.ImmutableMap;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.joyero.app.AppConfig;
import com.joyero.base.exception.MensajeErrorUsuario;
import com.joyero.base.exception.ResultadoException;
import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolation;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public abstract class ControladorRest<E extends Entidad, ID extends Serializable> {

    @Autowired
    protected AppConfig appConfig;

    @SuppressWarnings("unchecked")
    protected final Class<E> type =
            (Class<E>)
                    ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    private final ImmutableMap<String, String> fieldTypes =
            Arrays.stream(type.getDeclaredFields())
                    .collect(
                            ImmutableMap.toImmutableMap(
                                    Field::getName, field -> field.getType().getSimpleName().toLowerCase()));

    @Autowired(required = false)
    protected JpaRepository<E, ID> repository;

    /**
     * Recupera un objeto con el identificador especificado como parámetro
     *
     * @param id
     * @return
     */
    @GetMapping(
            value = "/{id}",
            produces = {"application/json"})
    public Optional<E> get(@PathVariable ID id) {
        Optional<E> entidad = repository.findById(id);
        return entidad;
    }

    @GetMapping(
            value = "/all",
            produces = {"application/json"})
    public List<E> findAll(@RequestParam MultiValueMap<String, String> queryPath, Sort sort) {
        Predicate predicate = buildPredicate(queryPath);
        QuerydslPredicateExecutor querydslPredicateExecutor = (QuerydslPredicateExecutor) repository;
        List<E> listado = null;
        if (sort != null) {
            listado = (List<E>) querydslPredicateExecutor.findAll(predicate, sort);
        } else {
            listado = (List<E>) querydslPredicateExecutor.findAll(predicate);
        }
        return listado;
    }

    @GetMapping(
            value = "/find",
            produces = {"application/json"})
    @ResponseBody
    public List<E> find(@RequestParam MultiValueMap<String, String> queryPath, Pageable pageable) {
        Predicate predicate = buildPredicate(queryPath);
        if(queryPath.containsKey("all")) {
            pageable = Pageable.unpaged();
        }
        QuerydslPredicateExecutor querydslPredicateExecutor = (QuerydslPredicateExecutor) repository;
        List<E> result = querydslPredicateExecutor.findAll(predicate, pageable).getContent();
        return result;
    }

    @RequestMapping(
            value = "/count",
            produces = {"application/json"},
            method = RequestMethod.GET)
    @ResponseBody
    public long getCount(@RequestParam MultiValueMap<String, String> queryPath) {
        Predicate predicate = buildPredicate(queryPath);
        QuerydslPredicateExecutor<E> querydslPredicateExecutor =
                (QuerydslPredicateExecutor<E>) repository;
        return querydslPredicateExecutor.count(predicate);
    }

    protected void preGuardar(E entidad) {
    }

    protected void postGuardar(E entidad) {
    }

    protected void preActualizar(E entidad) {
    }

    protected void postActualizar(E entidad) {
    }

    protected void preEliminar(ID id) {
    }

    protected void postEliminar(ID id) {
    }

    protected ResultadoException validaGuardar(E entidad) {
        return new ResultadoException();
    }

    protected ResultadoException validaActualizar(E entidad) {
        return new ResultadoException();
    }

    protected ResultadoException validaEliminar(E object) {
        return new ResultadoException();
    }

    protected ResultadoException validaEliminar(ID id) {
        return new ResultadoException();
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> save(@RequestBody E entidad) {
        ResultadoException resultadoException = validaGuardar(entidad);
        try {
            preGuardar(entidad);

            if (!resultadoException.debeArrojarse()) {
                HttpHeaders header = new HttpHeaders();
                ResponseEntity<?> response =
                        new ResponseEntity<>(repository.save(entidad), header, HttpStatus.CREATED);
                postGuardar(entidad);
                return response;
            } else {
                StringBuilder stb = new StringBuilder();
                for (MensajeErrorUsuario error : resultadoException.getErrores()) {
                    stb.append(error.getMsgId());
                }
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, stb.toString());
            }

        } catch (DataIntegrityViolationException ex) {
            if (ex.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException constraintViolationEx =
                        (ConstraintViolationException) ex.getCause();
                throw new ResponseStatusException(
                        HttpStatus.NOT_ACCEPTABLE,
                        constraintViolationEx.getSQLException().getLocalizedMessage());
            }
            throw new ResponseStatusException(
                    HttpStatus.NOT_ACCEPTABLE, ex.getCause().getLocalizedMessage());
        } catch (javax.validation.ConstraintViolationException ex) {
            StringBuilder stb = new StringBuilder();
            for (ConstraintViolation<?> constraintViolation : ex.getConstraintViolations()) {
                stb.append(constraintViolation.getPropertyPath()).append(":");
                stb.append(constraintViolation.getMessage()).append("\n");
            }

            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, stb.toString());
        }
        //        } catch (Exception e) {
        //            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        //        }
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> update(@RequestBody E entidad) {
        ResultadoException resultadoException = validaActualizar(entidad);
        try {
            preActualizar(entidad);

            if (!resultadoException.debeArrojarse()) {
                HttpHeaders header = new HttpHeaders();
                ResponseEntity<?> response =
                        new ResponseEntity<>(repository.save(entidad), header, HttpStatus.CREATED);
                postActualizar(entidad);
                return response;
            } else {
                StringBuilder stb = new StringBuilder();
                for (MensajeErrorUsuario error : resultadoException.getErrores()) {
                    stb.append(error.getMsgId());
                }
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, stb.toString());
            }

        } catch (DataIntegrityViolationException ex) {
            if (ex.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException constraintViolationEx =
                        (ConstraintViolationException) ex.getCause();
                throw new ResponseStatusException(
                        HttpStatus.NOT_ACCEPTABLE,
                        constraintViolationEx.getSQLException().getLocalizedMessage());
            }
            throw new ResponseStatusException(
                    HttpStatus.NOT_ACCEPTABLE, ex.getCause().getLocalizedMessage());
        }
        //        } catch (Exception e) {
        //            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
        // e.getCause().toString());
        //        }
    }

    /**
     * Elimina el objeto que tenga el identificador especificado como parámetro
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable ID id) {
        ResultadoException resultadoException = validaEliminar(id);
        preEliminar(id);
        try {
            repository.deleteById(id);
            postEliminar(id);
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException ex) {
            if (ex.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException constraintViolationEx =
                        (ConstraintViolationException) ex.getCause();
                String constraintName = constraintViolationEx.getConstraintName();
                if (constraintName == null) {
                    int errorCode = constraintViolationEx.getSQLException().getErrorCode();

                    String detailMessage = constraintViolationEx.getSQLException().getLocalizedMessage();
                    int fkStart = detailMessage.indexOf("fk_");
                    constraintName = detailMessage.substring(fkStart);
                    constraintName = constraintName.substring(0, constraintName.indexOf("'"));
                }
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, constraintName);
            }
            throw new ResponseStatusException(
                    HttpStatus.NOT_ACCEPTABLE, ex.getCause().getLocalizedMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_ACCEPTABLE, e.getCause().getLocalizedMessage());
        }
    }

    public Predicate buildPredicate(MultiValueMap<String, String> map) {
        String className = StringUtils.uncapitalize(type.getSimpleName());

        PathBuilder<E> entityPath = new PathBuilder<>(type, className);
        Predicate predicate = null;

        NumberFormat numberFormat = NumberFormat.getInstance();

        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            Predicate predicateTmp = null;

            Logger.getLogger(className).log(Level.INFO, entry.toString());

            String filedType = fieldTypes.get(entry.getKey());

            // TODO
            if (entry.getKey().contains(".")) {
                String stringValue = entry.getValue().get(0);
                if (stringValue.equals("isNull")) {
                    predicateTmp = entityPath.get(entry.getKey()).isNull();
                } else if (stringValue.equals("true") || stringValue.equals("false")) {
                    predicateTmp = entityPath.getBoolean(entry.getKey()).eq(BooleanUtils.toBoolean(stringValue));
                } else {
                    predicateTmp = entityPath.getString(entry.getKey()).containsIgnoreCase(stringValue);
                }
            } else {

                switch (entry.getKey()) {
                    case "page":
                    case "size":
                    case "sort":
                    case "all":
                        continue;
                }

                switch (filedType) {
                    case "long":
                    case "integer":
                    case "double":
                    case "float":
                    case "bigdecimal":
                    case "biginteger":
                        if (entry.getValue().size() == 1) {
                            // Único valor de consulta
                            String stringValue = entry.getValue().get(0);

                            if (stringValue.equals("isNull")) {
                                predicateTmp = entityPath.get(entry.getKey()).isNull();
                                break;
                            } else if (stringValue.equals("isNotNull")) {
                                predicateTmp = entityPath.get(entry.getKey()).isNotNull();
                                break;
                            }

                            Number number = null;

                            try {
                                if (stringValue.length() > 2 && stringValue.charAt(1) == '=') {
                                    number = numberFormat.parse(stringValue.substring(2));
                                } else if (stringValue.length() > 1) {
                                    number = numberFormat.parse(stringValue.substring(1));
                                }
                            } catch (ParseException ignored) {
                            }

                            Class numberClass = number != null ? number.getClass() : null;

                            switch (stringValue.charAt(0)) {
                                case '<':
                                    if (stringValue.charAt(1) == '=') {
                                        predicateTmp = entityPath.getNumber(entry.getKey(), numberClass).loe(number);
                                    } else {
                                        predicateTmp = entityPath.getNumber(entry.getKey(), numberClass).lt(number);
                                    }
                                    break;
                                case '>':
                                    if (stringValue.charAt(1) == '=') {
                                        predicateTmp = entityPath.getNumber(entry.getKey(), numberClass).goe(number);
                                    } else {
                                        predicateTmp = entityPath.getNumber(entry.getKey(), numberClass).gt(number);
                                    }
                                    break;
                                case '!':
                                    predicateTmp = entityPath.getNumber(entry.getKey(), numberClass).ne(number);
                                    break;
                                default:
                                    Number number2 = null;
                                    try {
                                        number2 = numberFormat.parse(stringValue);
                                    } catch (ParseException ignored) {
                                    }
                                    Class number2Class = number2.getClass();
                                    predicateTmp = entityPath.getNumber(entry.getKey(), number2Class).eq(number2);
                                    break;
                            }
                        } else {
                            // Múltiples valores de consulta
                            List<Number> valuesNumberIn = new ArrayList<>();
                            List<Number> valuesNumberNotIn = new ArrayList<>();
                            boolean nulles = false;
                            boolean noNulles = false;

                            for (String value : entry.getValue()) {
                                if (value.equals("isNull")) {
                                    nulles = true;
                                } else if (value.equals("isNotNull")) {
                                    noNulles = true;
                                } else if (value.charAt(0) == '!') {
                                    Number number = null;
                                    try {
                                        number = numberFormat.parse(value.substring(1));
                                    } catch (ParseException ignored) {
                                    }
                                    valuesNumberNotIn.add(number);
                                } else {
                                    Number number = null;
                                    try {
                                        number = numberFormat.parse(value);
                                    } catch (ParseException ignored) {
                                    }
                                    valuesNumberIn.add(number);
                                }
                            }

                            if (filedType.equals(Integer.class.getSimpleName().toLowerCase())) {
                                List<Integer> intList =
                                        valuesNumberIn.stream()
                                                .mapToInt(number -> number.intValue())
                                                .boxed()
                                                .collect(Collectors.toList());
                                predicateTmp = entityPath.get(entry.getKey(), Integer.class).in(intList);
                            } else if (filedType.equals(Long.class.getSimpleName().toLowerCase())) {
                                List<Long> longList;
                                if (valuesNumberIn.size() > 0) {
                                    longList =
                                            valuesNumberIn.stream()
                                                    .mapToLong(number -> number.longValue())
                                                    .boxed()
                                                    .collect(Collectors.toList());
                                    predicateTmp = entityPath.get(entry.getKey(), Long.class).in(longList);
                                } else {
                                    if (valuesNumberNotIn.size() > 0) {
                                        longList =
                                                valuesNumberNotIn.stream()
                                                        .mapToLong(number -> number.longValue())
                                                        .boxed()
                                                        .collect(Collectors.toList());
                                        predicateTmp = entityPath.get(entry.getKey(), Long.class).notIn(longList);
                                    }
                                }

                            } else if (filedType.equals(BigInteger.class.getSimpleName().toLowerCase())) {
                                List<BigInteger> bigIntegerList =
                                        valuesNumberIn.stream()
                                                .map(number -> BigInteger.valueOf(number.longValue()))
                                                .collect(Collectors.toList());
                                predicateTmp = entityPath.get(entry.getKey(), BigInteger.class).in(bigIntegerList);
                            } else if (filedType.equals(Float.class.getSimpleName().toLowerCase())) {
                                List<Float> floatList =
                                        valuesNumberIn.stream()
                                                .map(number -> number.floatValue())
                                                .collect(Collectors.toList());
                                predicateTmp = entityPath.get(entry.getKey(), Float.class).in(floatList);
                            } else if (filedType.equals(Double.class.getSimpleName().toLowerCase())) {
                                List<Double> doubleList =
                                        valuesNumberIn.stream()
                                                .mapToDouble(number -> number.doubleValue())
                                                .boxed()
                                                .collect(Collectors.toList());
                                predicateTmp = entityPath.get(entry.getKey(), Double.class).in(doubleList);
                            } else if (filedType.equals(BigDecimal.class.getSimpleName().toLowerCase())) {
                                List<BigDecimal> bigDecimalList =
                                        valuesNumberIn.stream()
                                                .map(number -> BigDecimal.valueOf(number.doubleValue()))
                                                .collect(Collectors.toList());
                                predicateTmp = entityPath.get(entry.getKey(), BigDecimal.class).in(bigDecimalList);
                            }

                            if (nulles) {
                                predicateTmp =
                                        ExpressionUtils.or(predicateTmp, entityPath.get(entry.getKey()).isNull());
                            } else if (noNulles) {
                                predicateTmp =
                                        ExpressionUtils.or(predicateTmp, entityPath.get(entry.getKey()).isNotNull());
                            }
                        }
                        break;
                    case "string":
                        if (entry.getValue().size() == 1) {
                            // Único valor de consulta
                            String stringValue = entry.getValue().get(0);

                            if (stringValue.equals("isNull")) {
                                predicateTmp = entityPath.get(entry.getKey()).isNull();
                                break;
                            } else if (stringValue.equals("isNotNull")) {
                                predicateTmp = entityPath.get(entry.getKey()).isNotNull();
                                break;
                            }

                            switch (stringValue.charAt(0)) {
                                case '<':
                                    predicateTmp =
                                            stringValue.charAt(1) == '='
                                                    ? entityPath.getString(entry.getKey()).loe(stringValue.substring(2))
                                                    : entityPath.getString(entry.getKey()).lt(stringValue.substring(1));
                                    break;
                                case '>':
                                    predicateTmp =
                                            stringValue.charAt(1) == '='
                                                    ? entityPath.getString(entry.getKey()).goe(stringValue.substring(2))
                                                    : entityPath.getString(entry.getKey()).gt(stringValue.substring(1));
                                    break;
                                case ':':
                                    predicateTmp =
                                            stringValue.charAt(1) == '!'
                                                    ? entityPath.getString(entry.getKey()).ne(stringValue.substring(2))
                                                    : entityPath.getString(entry.getKey()).eq(stringValue.substring(1));
                                    break;
                                case '!':
                                    predicateTmp =
                                            entityPath
                                                    .getString(entry.getKey())
                                                    .notLike("%" + stringValue.substring(1) + "%");
                                    break;
                                default:
                                    predicateTmp =
                                            entityPath.getString(entry.getKey()).containsIgnoreCase(stringValue);
                                    break;
                            }
                        } else {

                            Map<Boolean, List<String>> asdf =
                                    entry.getValue().stream()
                                            .collect(Collectors.partitioningBy(o -> o.charAt(0) != '!'));

                            if (!asdf.get(true).isEmpty()) {
                                predicateTmp = entityPath.get(entry.getKey()).in(asdf.get(true));
                            }
                            if (!asdf.get(false).isEmpty()) {
                                predicateTmp = entityPath.get(entry.getKey()).notIn(asdf.get(true));
                            }
                        }
                        break;

                    case "localdate":

                        // String[] parsePatterns = {"dd-MM-yyyy hh:mm", "dd-MM-yyyy"};

                        if (entry.getValue().size() == 1) {
                            String stringValue = entry.getValue().get(0);

                            if (stringValue.equals("isNull")) {
                                predicateTmp = entityPath.get(entry.getKey()).isNull();
                                break;
                            } else if (stringValue.equals("isNotNull")) {
                                predicateTmp = entityPath.get(entry.getKey()).isNotNull();
                                break;
                            }

                            Date dateValue1 = null;
                            Date dateValue2 = null;

                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

                            LocalDate localDateValue1 = LocalDate.now();
                            LocalDate localDateValue2 = LocalDate.now();

                            try {
                                localDateValue1 = LocalDate.parse(stringValue.substring(1), formatter);
                                localDateValue2 = LocalDate.parse(stringValue.substring(2), formatter);
                            } catch (DateTimeParseException ex) {

                            }

                            switch (stringValue.charAt(0)) {
                                case '<':
                                    predicateTmp =
                                            stringValue.charAt(1) == '='
                                                    ? entityPath.getDate(entry.getKey(), LocalDate.class).loe(localDateValue2)
                                                    : entityPath.getDate(entry.getKey(), LocalDate.class).lt(localDateValue1);
                                    break;
                                case '>':
                                    predicateTmp =
                                            stringValue.charAt(1) == '='
                                                    ? entityPath.getDate(entry.getKey(), LocalDate.class).goe(localDateValue2)
                                                    : entityPath.getDate(entry.getKey(), LocalDate.class).gt(localDateValue1);
                                    break;
                                case '!':
                                    predicateTmp =
                                            entityPath.getDate(entry.getKey(), LocalDate.class).ne(localDateValue1);
                                    break;
                                default:
                                    LocalDate localDateValue = LocalDate.parse(stringValue, formatter);
                                    predicateTmp =
                                            entityPath.getDate(entry.getKey(), LocalDate.class).eq(localDateValue);
                                    break;
                            }
                        } else if (entry.getValue().size() == 2) {
                            String stringDate1 = entry.getValue().get(0);
                            String stringDate2 = entry.getValue().get(1);

                            List<LocalDateTime> dates = new ArrayList<>();

                            LocalDateTime date1 = LocalDateTime.now();
                            LocalDateTime date2 = LocalDateTime.now();

                            try {
                                date1 = LocalDateTime.parse(stringDate1);
                                date2 = LocalDateTime.parse(stringDate2);
                                break;
                            } catch (DateTimeParseException ignored) {
                            }

                            dates.add(date1);
                            dates.add(date2);
                            Collections.sort(dates);

                            predicateTmp =
                                    entityPath
                                            .getDateTime(entry.getKey(), LocalDateTime.class)
                                            .between(dates.get(0), dates.get(1));
                            break;
                        } else {

                        }
                        break;

                    case "datetime":
                    case "localdatetime":
                    case "date":
                        String[] parsePatterns = {"dd-MM-yyyy hh:mm", "dd-MM-yyyy"};

                        if (entry.getValue().size() == 1) {
                            String stringValue = entry.getValue().get(0);

                            if (stringValue.equals("isNull")) {
                                predicateTmp = entityPath.get(entry.getKey()).isNull();
                                break;
                            } else if (stringValue.equals("isNotNull")) {
                                predicateTmp = entityPath.get(entry.getKey()).isNotNull();
                                break;
                            }

                            Date dateValue1 = null;
                            Date dateValue2 = null;

                            for (String pattern : parsePatterns) {
                                SimpleDateFormat parser = new SimpleDateFormat(pattern);
                                try {
                                    dateValue1 = parser.parse(stringValue.substring(1));
                                    dateValue2 = parser.parse(stringValue.substring(2));
                                    break;
                                } catch (ParseException ignored) {
                                }
                            }

                            switch (stringValue.charAt(0)) {
                                case '<':
                                    predicateTmp =
                                            stringValue.charAt(1) == '='
                                                    ? entityPath.getDate(entry.getKey(), Date.class).loe(dateValue2)
                                                    : entityPath.getDate(entry.getKey(), Date.class).lt(dateValue1);
                                    break;
                                case '>':
                                    predicateTmp =
                                            stringValue.charAt(1) == '='
                                                    ? entityPath.getDate(entry.getKey(), Date.class).goe(dateValue2)
                                                    : entityPath.getDate(entry.getKey(), Date.class).gt(dateValue1);
                                    break;
                                case '!':
                                    predicateTmp = entityPath.getDate(entry.getKey(), Date.class).ne(dateValue1);
                                    break;
                                default:
                                    for (String pattern : parsePatterns) {
                                        SimpleDateFormat parser = new SimpleDateFormat(pattern);
                                        try {
                                            predicateTmp =
                                                    entityPath
                                                            .getDate(entry.getKey(), Date.class)
                                                            .eq(parser.parse(stringValue));
                                            break;
                                        } catch (ParseException ignored) {
                                        }
                                    }
                                    break;
                            }
                        } else if (entry.getValue().size() == 2) {
                            String stringDate1 = entry.getValue().get(0);
                            String stringDate2 = entry.getValue().get(1);

                            List<LocalDateTime> dates = new ArrayList<>();

                            LocalDateTime date1 = LocalDateTime.parse(stringDate1);
                            LocalDateTime date2 = LocalDateTime.parse(stringDate2);

                            dates.add(date1);
                            dates.add(date2);
                            Collections.sort(dates);

                            predicateTmp =
                                    entityPath
                                            .getDateTime(entry.getKey(), LocalDateTime.class)
                                            .between(dates.get(0), dates.get(1));
                            break;
                        } else {
                            // Múltiples valores de consulta
                            List<Date> valuesDateIn = new ArrayList<>();
                            List<Date> valuesDateNotIn = new ArrayList<>();
                            for (String value : entry.getValue()) {

                                value = value.replaceAll(">", "");
                                value = value.replaceAll(">=", "");
                                value = value.replaceAll("<", "");
                                value = value.replaceAll("<=", "");

                                Date dateValue = null;
                                Date dateValue1 = null;

                                for (String pattern : parsePatterns) {
                                    SimpleDateFormat parser = new SimpleDateFormat(pattern);
                                    try {
                                        dateValue = parser.parse(value.substring(1));
                                        dateValue1 = parser.parse(value.substring(1).substring(2));
                                        break;
                                    } catch (ParseException ignored) {
                                    }
                                }

                                if (value.charAt(0) == '!') {
                                    valuesDateNotIn.add(dateValue1);
                                } else {
                                    valuesDateIn.add(dateValue);
                                }
                            }
                            if (!valuesDateIn.isEmpty()) {
                                predicateTmp = entityPath.get(entry.getKey()).in(valuesDateIn);
                            }
                            if (!valuesDateNotIn.isEmpty()) {
                                predicateTmp = entityPath.get(entry.getKey()).notIn(valuesDateNotIn);
                            }
                        }
                        break;
                    case "boolean":
                        String stringValue = entry.getValue().get(0);
                        Boolean booleanValue = Boolean.parseBoolean(stringValue);

                        if (stringValue.equals("isNull")) {
                            predicateTmp = entityPath.get(entry.getKey()).isNull();
                        } else if (stringValue.equals("isNotNull")) {
                            predicateTmp = entityPath.get(entry.getKey()).isNotNull();
                        } else if (booleanValue) {
                            predicateTmp = entityPath.getBoolean(entry.getKey()).isTrue();
                        } else {
                            predicateTmp = entityPath.getBoolean(entry.getKey()).isFalse();
                        }
                        break;
                    default:
                        String stringObjectValue = entry.getValue().get(0);
                        if (stringObjectValue.equals("isNull")) {
                            predicateTmp = entityPath.get(entry.getKey()).isNull();
                        } else if (stringObjectValue.equals("isNotNull")) {
                            predicateTmp = entityPath.get(entry.getKey()).isNotNull();
                        } else {
                            predicateTmp = customBuildPrecicate(entry.getKey(), stringObjectValue);
                        }
                }
            }

            if (predicate == null) {
                predicate = predicateTmp;
            } else {
                predicate = ExpressionUtils.and(predicate, predicateTmp);
            }
        }
        // Spring Boot 2.2.3, requiere que el predicado sea no nulo
        if (predicate == null) {
            predicate = Expressions.asBoolean(true).isTrue();
        }

        return predicate;
    }

    protected Predicate customBuildPrecicate(String property, String value) {
        return null;
    }
}
