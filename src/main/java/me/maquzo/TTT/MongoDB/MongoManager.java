package me.maquzo.TTT.MongoDB;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import me.maquzo.TTT.Files.MongoDBConfig;
import me.maquzo.TTT.TTT;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

public class MongoManager {

    private final String host;
    private final int port;

    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Document> serverCollection;

    private TTT plugin;


    public MongoManager(String host, int port) {
        this.host = host;
        this.port = port;

    }

    public void connect() {
        try {
<<<<<<< HEAD
            MongoCredential credential = MongoCredential.createCredential(MongoDBConfig.get().getString("username"), MongoDBConfig.get().getString("database"), MongoDBConfig.get().getString("password").toCharArray());
            client = new MongoClient(new ServerAddress(MongoDBConfig.get().getString("host"), 27017), Collections.singletonList(credential));
            database = client.getDatabase(MongoDBConfig.get().getString("database"));
=======
            MongoCredential credential = MongoCredential.createCredential("FIck deine Eltern", "Nein amn", "No, sir".toCharArray());
            client = new MongoClient(new ServerAddress("ICh hasse dich", 27017), Collections.singletonList(credential));
            database = client.getDatabase("NEIN!");
>>>>>>> 46888185c224ca5389c203a78e9a7bd8b1274085
            serverCollection = database.getCollection("Server");
            Bukkit.getLogger().info(TTT.prefix + "§aSuccessfully connected to the database!");
        } catch (Exception e) {
            Bukkit.getLogger().info(TTT.error + "§cThere was an Error trying to connect to the database!");
            e.printStackTrace();
        }

    }


    public MongoCollection<Document> getServerCollection() {
        return serverCollection;
    }

    public int getPort() {
        return port;
    }

    public MongoClient getClient() {
        return client;
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public String getHost() {
        return host;
    }

}
