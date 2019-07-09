package com.preloode.vid.component;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.*;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.CreateCollectionOptions;
import com.preloode.vid.configuration.Database;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;


@Component
public class Mongo {


    @Autowired
    private Database database;

    @Autowired
    private DateTime dateTime;

    private MongoDatabase mongoDatabase;

    private MongoCollection<Document> collection;

    private MongoClient mongoClient;


    @PostConstruct
    public void initialize() {

        MongoClientOptions.Builder mongoClientBuilder = new MongoClientOptions.Builder();
        //mongoClientBuilder.maxConnectionIdleTime(60000);
        MongoClientOptions mongoClientOptions = mongoClientBuilder.build();
        MongoCredential mongoCredential = MongoCredential.createCredential(this.database.getUser(), this.database.getName(), this.database.getPassword().toCharArray());
        this.mongoClient = new MongoClient(new ServerAddress(this.database.getHost(), this.database.getPort()), Arrays.asList(mongoCredential), mongoClientOptions);

        this.mongoDatabase = this.mongoClient.getDatabase(this.database.getName());

    }


    public AggregateIterable<Document> aggregateSort(String collection, Document match, Document group, Document sort) {

        this.collection = this.mongoDatabase.getCollection(collection);

        AggregateIterable<Document> result = this.collection.aggregate(Arrays.asList(
                Aggregates.match(match),
                Aggregates.group(group),
                Aggregates.sort(sort)
        ));

        return result;

    }


    public long count(String collection) {

        this.collection = this.mongoDatabase.getCollection(collection);

        return this.collection.count();

    }


    public void createCollection(String collection) {

        this.mongoDatabase.createCollection(collection, new CreateCollectionOptions().capped(false));

    }


    public void dropCollection(String collection) {

        this.mongoDatabase.getCollection(collection).drop();

    }


    public void deleteOne(String collection, Document eq) {

        this.collection = this.mongoDatabase.getCollection(collection);

        Document deleteOne = new Document();

        for (Document.Entry<String, Object> document : eq.entrySet()) {

            deleteOne.append(document.getKey(), new Document("$eq", document.getValue()));

            break;

        }

        this.collection.deleteOne(deleteOne);

    }


    public FindIterable<Document> find(String collection) {

        this.collection = this.mongoDatabase.getCollection(collection);

        FindIterable<Document> result = this.collection.find();

        return result;

    }


    public MongoCursor<Document> findAndOrEqSort(String collection, Document and, Document or, Document sort) {

        this.collection = this.mongoDatabase.getCollection(collection);

        ArrayList<Document> orArrayList = new ArrayList<Document>();

        for (Document.Entry<String, Object> document : or.entrySet()) {

            orArrayList.add(new Document(document.getKey(), document.getValue()));

        }

        Document orQuery = new Document("$or", orArrayList);

        ArrayList<Document> andQuery = new ArrayList<Document>();

        for (Document.Entry<String, Object> document : and.entrySet()) {

            andQuery.add(new Document(document.getKey(), document.getValue()));

        }

        andQuery.add(orQuery);

        Document query = new Document("$and", andQuery);

        MongoCursor<Document> result = this.collection.find(query).sort(sort).iterator();

        return result;

    }


    public MongoCursor<Document> findEqGteSort(String collection, Document eq, Document gte, Document sort) {

        this.collection = this.mongoDatabase.getCollection(collection);

        Document query = new Document();

        for (Document.Entry<String, Object> document : eq.entrySet()) {

            query.append(document.getKey(), new Document("$eq", document.getValue()));

        }

        for (Document.Entry<String, Object> document : gte.entrySet()) {

            query.append(document.getKey(), new Document("$gte", document.getValue()));

        }

        MongoCursor<Document> result = this.collection.find(query).sort(sort).iterator();

        return result;

    }


    public MongoCursor<Document> findEqInSort(String collection, Document eq, Document in, Document sort) {

        this.collection = this.mongoDatabase.getCollection(collection);

        Document query = new Document();

        for (Document.Entry<String, Object> document : eq.entrySet()) {

            query.append(document.getKey(), new Document("$eq", document.getValue()));

        }

        for (Document.Entry<String, Object> document : in.entrySet()) {

            query.append(document.getKey(), new Document("$in", document.getValue()));

        }

        MongoCursor<Document> result = this.collection.find(query).sort(sort).iterator();

        return result;

    }


    public MongoCursor<Document> findEqLteSort(String collection, Document eq, Document lte, Document sort) {

        this.collection = this.mongoDatabase.getCollection(collection);

        Document query = new Document();

        for (Document.Entry<String, Object> document : eq.entrySet()) {

            query.append(document.getKey(), new Document("$eq", document.getValue()));

        }

        for (Document.Entry<String, Object> document : lte.entrySet()) {

            query.append(document.getKey(), new Document("$lte", document.getValue()));

        }

        MongoCursor<Document> result = this.collection.find(query).sort(sort).iterator();

        return result;

    }


    public MongoCursor<Document> findEqNeInSort(String collection, Document eq, Document ne, Document in, Document sort) {

        this.collection = this.mongoDatabase.getCollection(collection);

        Document query = new Document();

        for (Document.Entry<String, Object> document : eq.entrySet()) {

            query.append(document.getKey(), new Document("$eq", document.getValue()));

        }

        for (Document.Entry<String, Object> document : ne.entrySet()) {

            query.append(document.getKey(), new Document("$ne", document.getValue()));

        }

        for (Document.Entry<String, Object> document : in.entrySet()) {

            query.append(document.getKey(), new Document("$in", document.getValue()));

        }

        MongoCursor<Document> result = this.collection.find(query).sort(sort).iterator();

        return result;

    }


    public MongoCursor<Document> findEqNeSort(String collection, Document eq, Document ne, Document sort) {

        this.collection = this.mongoDatabase.getCollection(collection);

        Document query = new Document();

        for (Document.Entry<String, Object> document : eq.entrySet()) {

            query.append(document.getKey(), new Document("$eq", document.getValue()));

        }

        for (Document.Entry<String, Object> document : ne.entrySet()) {

            query.append(document.getKey(), new Document("$ne", document.getValue()));

        }

        MongoCursor<Document> result = this.collection.find(query).sort(sort).iterator();

        return result;

    }


    public MongoCursor<Document> findEqSort(String collection, Document eq, Document sort) {

        this.collection = this.mongoDatabase.getCollection(collection);

        Document query = new Document();

        for (Document.Entry<String, Object> document : eq.entrySet()) {

            query.append(document.getKey(), new Document("$eq", document.getValue()));

        }

        MongoCursor<Document> result = this.collection.find(query).sort(sort).iterator();

        return result;

    }


    public MongoCursor<Document> findPagination(String collection, Document filter, Document sort, Integer page, Integer size) {

        this.collection = this.mongoDatabase.getCollection(collection);

        MongoCursor<Document> result = this.collection.find(filter).sort(sort).skip(page * size).limit(size).iterator();

        return result;

    }


    public MongoCursor<Document> findSort(String collection, Document sort) {

        this.collection = this.mongoDatabase.getCollection(collection);

        MongoCursor<Document> result = this.collection.find().sort(sort).iterator();

        return result;

    }


    public String insertOne(HttpServletRequest request, String collection, Document data, Document administrator) {

        this.collection = this.mongoDatabase.getCollection(collection);

        Document insertOne = data.append("_id", new ObjectId().toString())
                .append("created", new Document("administrator", administrator)
                        .append("timestamp", this.dateTime.getTimestamp(request)))
                .append("modified", new Document("administrator", administrator)
                        .append("timestamp", this.dateTime.getTimestamp(request)));
        this.collection.insertOne(insertOne);

        return insertOne.get("_id").toString();

    }


    public MongoIterable<String> listCollection() {

        MongoIterable<String> result = this.mongoDatabase.listCollectionNames();

        return result;

    }


    public void replaceOne(HttpServletRequest request, String collection, Document eq, Document data, Document administrator) {

        if (data.containsKey("id")) {

            data.remove("id");

        }

        this.collection = this.mongoDatabase.getCollection(collection);

        Document query = new Document();

        for (Document.Entry<String, Object> document : eq.entrySet()) {

            query.append(document.getKey(), new Document("$eq", document.getValue()));

            break;

        }

        Document replaceOne = data.append("modified", new Document("administrator", administrator)
                .append("timestamp", this.dateTime.getTimestamp(request)));
        this.collection.replaceOne(query, replaceOne);

    }


    public void updateOne(HttpServletRequest request, String collection, Document eq, Document data, Document administrator) {

        if (data.containsKey("id")) {

            data.remove("id");

        }

        this.collection = this.mongoDatabase.getCollection(collection);

        Document query = new Document();

        for (Document.Entry<String, Object> document : eq.entrySet()) {

            query.append(document.getKey(), new Document("$eq", document.getValue()));

            break;

        }

        data.append("modified", new Document("administrator", administrator)
                .append("timestamp", this.dateTime.getTimestamp(request)));
        Document updateOne = new Document("$set", data);
        this.collection.updateOne(query, updateOne);

    }


}
