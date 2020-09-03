package net.valux.legacy.proxy.db;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Mongo {

    private MongoClient client;
    private MongoDatabase legacy;
    private MongoDatabase shared;

    public enum Database {
        LEGACY, SHARED;
    }

    public Mongo() {

        Logger logger = Logger.getLogger("org.mongodb.driver");
        logger.setLevel(Level.SEVERE);

        this.client = new MongoClient(new MongoClientURI("###"));
        System.out.print("[Proxy] Successfully connected to the MongoDB server on ###");

        this.shared = this.client.getDatabase("###");
        this.legacy = this.client.getDatabase("###");

    }

    public FindIterable<Document> find(Database db, String collection, Document doc) {
        if (db == Database.LEGACY) {
            return this.legacy.getCollection(collection).find(doc);
        }
        else if (db == Database.SHARED) {
            return this.shared.getCollection(collection).find(doc);
        }
        return null;
    }

    public Document findOne(Database db, String collection, Document doc) {
        if (db == Database.LEGACY) {
            FindIterable<Document> docs = this.legacy.getCollection(collection).find(doc);
            return docs.first();
        }
        else if (db == Database.SHARED) {
            FindIterable<Document> docs = this.shared.getCollection(collection).find(doc);
            return docs.first();
        }
        return null;
    }

    public void updateDocument(Database db, String collection, Document search, Document update) {
        if (db == Database.LEGACY) {
            this.legacy.getCollection(collection).findOneAndUpdate(search, update);
        }
        else if (db == Database.SHARED) {
            this.shared.getCollection(collection).findOneAndUpdate(search, update);
        }
    }

    public void insertDocument(Database db, String collection, Document doc) {
        if (db == Database.LEGACY) {
            this.legacy.getCollection(collection).insertOne(doc);
        }
        else if (db == Database.SHARED) {
            this.shared.getCollection(collection).insertOne(doc);
        }
    }

}
