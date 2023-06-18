/*
 * Copyright (c) 2019.
 * SpcRestWebApplication.java
 * 13/06/19 9:39
 * alejandro
 */

package com.joyero.app;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources({
        @PropertySource("file:c:/config/joy.properties")
})
public class RestWebApplication extends SpringBootServletInitializer {
}
