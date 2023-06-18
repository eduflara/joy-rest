/*
 * Copyright (c) 2019.
 * Repositorio.java
 * 17/09/19 10:58
 * alejandro
 */

package com.joyero.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.io.Serializable;

public interface Repositorio<E extends Entidad, ID extends Serializable> extends JpaRepository<E, ID>, QuerydslPredicateExecutor<E> {
}
