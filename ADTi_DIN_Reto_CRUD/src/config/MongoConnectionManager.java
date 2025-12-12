package config;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoConnectionManager {
    private static final MongoClient client;
    private static final MongoDatabase db;

    static {
        client = MongoClients.create("mongodb://localhost:27017");
        db = client.getDatabase("retoMongo");
    }

    public static MongoDatabase getDatabase() {
        return db;
    }
}