package com.joyero.app.seguridad.sistema;


import com.google.common.collect.Lists;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/app/seg/sistema")
public class SistemaRest {


    @Autowired
    private SistemaRepository repository;

    /**
     * Recupera un único autor con el identificador que se pase como parámetro
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}", produces = {"application/json"})
    public Optional<Sistema> get(@PathVariable Long id) {
        Optional<Sistema> autor = repository.findById(id);
        return autor;
    }


    /**
     * Recupera todos los autores (Pueden ser muchos objetos y que se ralentize el proceso)
     *
     * @return
     */
    @GetMapping(value = "/all", produces = {"application/json"})
    public List<Sistema> findAll() {
        List<Sistema> autores = repository.findAll();
        return autores;
    }

    /**
     * Recupera autores paginados, filtrados y ordenados
     * <p>
     * http://localhost:8080/app/autores?codSistema=230&idLegislatura=1
     * http://localhost:8080/app/autores?codSistema=230&page=0&size=2&sort=id,desc
     * http://localhost:8080/app/autores?codSistema=230&page=0&size=2&sort=id,idLegislatura,asc
     *
     * @param predicate
     * @param pageable
     * @return
     */
    @GetMapping(produces = {"application/json"})
    @ResponseBody
    public List<Sistema> findAll(@QuerydslPredicate(root = Sistema.class) Predicate predicate, Pageable pageable) {

        Iterable<Sistema> result = repository.findAll(predicate, pageable);
        List<Sistema> autores = Lists.newArrayList(result);

        return autores;
    }

    /**
     * Recupeta el todal de autores que cumplen con una serie de filtros
     *
     * @param predicate
     * @return
     */
    @RequestMapping(value = "/count", produces = {"application/json"})
    @ResponseBody
    public long getCount(@QuerydslPredicate(root = Sistema.class) Predicate predicate) {
        return repository.count(predicate);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Sistema entidad) {
        try {
            HttpHeaders header = new HttpHeaders();
            ResponseEntity<?> response = new ResponseEntity<>(repository.save(entidad), header, HttpStatus.CREATED);
            return response;
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_ACCEPTABLE, ex.getLocalizedMessage());
        }
    }


    @PutMapping()
    public ResponseEntity<?> update(@RequestBody Sistema entidad) {
        HttpHeaders header = new HttpHeaders();
        try {
            ResponseEntity<?> response = new ResponseEntity<>(repository.save(entidad), header, HttpStatus.OK);
            return response;
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_ACCEPTABLE, ex.getLocalizedMessage());
        }

    }

    /**
     * Recupera un único autor con el identificador que se pase como parámetro
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
