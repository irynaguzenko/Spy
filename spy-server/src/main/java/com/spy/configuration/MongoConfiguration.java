package com.spy.configuration;

import com.fasterxml.jackson.databind.Module;
import com.spy.converter.DateToLocalDateTimeConverter;
import com.spy.converter.LocalDateTimeToDateConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.geo.GeoJsonModule;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongoConfiguration {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new DateToLocalDateTimeConverter());
        converters.add(new LocalDateTimeToDateConverter());
        return new MongoCustomConversions(converters);
    }

    @Bean
    public Module registerGeoJsonModule(){
        return new GeoJsonModule();
    }
}
