package config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoConnectionManager {

    private static MongoClient cliente;

    private MongoConnectionManager() {
    }

    public static MongoDatabase getDatabase() {
        if (cliente == null) {
            // Crea un MongoClient, que internamente ya maneja un pool
            cliente = MongoClients.create("mongodb://localhost:27017");
        }
        return cliente.getDatabase("retoMongo");
    }
}


