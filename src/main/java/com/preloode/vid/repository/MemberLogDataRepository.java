package com.preloode.vid.repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.preloode.vid.component.Mongo;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;


@Repository
public class MemberLogDataRepository {


    /* _id
    city
    contact {
        bbm_pin
        email_address
        line_id
        phone_number
        wechat_id
        whatsapp_number
    }
    country
    created {
        administrator {
            id
            username
        }
        timestamp
    }
    credit {
        affiliate_commission
        main
        promotion
        referral_commission
    }
    file
    game {
        account {
            password
            username
        }
        id
        name
        type {
            id
            name
        }
    }
    gender
    group {
        id
        name
    }
    identity
    language
    log {
        last_deposit {
            amount
            timestamp
        }
        last_login {
            client {
                browser_id
                browser_manufacturer
                browser_name
                browser_rendering_engine
                browser_type
                browser_version
                device_id
                device_manufacturer
                device_operating_system
                device_type
                id
                ip
                mac
            }
            timestamp
        }
        last_post {
            content
            timestamp
        }
        last_withdrawal {
            amount
            timestamp
        }
    }
    member_id
    modified {
        administrator {
            id
            username
        }
        timestamp
    }
    name {
        first
        last
        middle
    }
    password
    payment {
        bank {
            account {
                id
                name
                number
            }
            id
            name
        }
        method
    }
    province
    recovery_password
    referral {
        id
        type
        username
    }
    status
    street_address
    type
    username
    zip_code */
    @Autowired
    private Mongo mongo;

    private String collection;


    public String getCollection() {

        return collection;

    }


    public void setCollection(String collection) {

        this.collection = collection;

    }


    @PostConstruct
    public void initialize() {

        String collection = "member_log_data";
        this.setCollection(collection);

    }


    public long count() {

        return this.mongo.count(this.collection);

    }


    public void deleteOne(Document eq) {

        this.mongo.deleteOne(this.collection, eq);

    }


    public FindIterable<Document> find() {

        return this.mongo.find(this.collection);

    }


    public MongoCursor<Document> findEqInSort(Document eq, Document in, Document sort) {

        return this.mongo.findEqInSort(this.collection, eq, in, sort);

    }


    public MongoCursor<Document> findEqLteSort(Document eq, Document lte, Document sort) {

        return this.mongo.findEqLteSort(this.collection, eq, lte, sort);

    }


    public MongoCursor<Document> findEqNeInSort(Document eq, Document ne, Document in, Document sort) {

        return this.mongo.findEqNeInSort(this.collection, eq, ne, in, sort);

    }


    public MongoCursor<Document> findEqNeSort(Document eq, Document ne, Document sort) {

        return this.mongo.findEqNeSort(this.collection, eq, ne, sort);

    }


    public MongoCursor<Document> findEqSort(Document eq, Document sort) {

        return this.mongo.findEqSort(this.collection, eq, sort);

    }


    public MongoCursor<Document> findPagination(Document filter, Document sort, Integer page, Integer size) {

        MongoCursor<Document> result = this.mongo.findPagination(this.collection, filter, sort, page, size);

        return result;

    }


    public MongoCursor<Document> findSort(Document sort) {

        return this.mongo.findSort(this.collection, sort);

    }


    public String insertOne(HttpServletRequest request, Document data, Document administrator) {

        return this.mongo.insertOne(request, this.collection, data, administrator);

    }


    public void updateOne(HttpServletRequest request, Document eq, Document data, Document administrator) {

        this.mongo.updateOne(request, this.collection, eq, data, administrator);

    }


}
