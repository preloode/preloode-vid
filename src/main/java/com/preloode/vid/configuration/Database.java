package com.preloode.vid.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Database {


    @Value("${database.host}")
    private String host;

    @Value("${database.name}")
    private String name;

    @Value("${database.password}")
    private String password;

    @Value("${database.port}")
    private Integer port;

    @Value("${database.user}")
    private String user;


    public String getHost() {

        return this.host;

    }


    public void setHost(String host) {

        this.host = host;

    }


    public String getName() {

        return this.name;

    }


    public void setName(String name) {

        this.name = name;

    }


    public String getPassword() {

        return this.password;

    }


    public void setPassword(String password) {

        this.password = password;

    }


    public Integer getPort() {

        return this.port;

    }


    public void setPort(Integer port) {

        this.port = port;

    }


    public String getUser() {

        return this.user;

    }


    public void setUser(String user) {

        this.user = user;

    }


}
