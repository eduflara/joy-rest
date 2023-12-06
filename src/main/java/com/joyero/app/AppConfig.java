package com.joyero.app;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySources({
        @PropertySource("file:c:/config/joy.properties")
})
public class AppConfig {

    @Autowired
    private Environment env;

    public String getValue(String property) {
        return env.getProperty(property);
    }
}
