package com.preloode.vid.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Page {


    @Value("${page.size}")
    private Integer size;


    public Integer getSize() {

        return this.size;

    }


    public void setSize(Integer size) {

        this.size = size;

    }


}
