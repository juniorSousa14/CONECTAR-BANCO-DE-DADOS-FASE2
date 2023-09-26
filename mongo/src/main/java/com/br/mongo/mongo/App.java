package com.br.mongo.mongo;

import model.Produto;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.function.Consumer;

import static com.mongodb.client.model.Updates.set;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;	
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;;	


public class App 
{
    public static void main( String[] args )
    {
        
    	CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
    			fromProviders(PojoCodecProvider.builder().automatic(true).build()));
    	
    	MongoClient mongoClient = new  MongoClient("Localhost:27017",
    			MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());
    	
    	MongoDatabase database = mongoClient.getDatabase("TexteConectar").withCodecRegistry(pojoCodecRegistry);
    	
    	MongoCollection<Produto> collection = database.getCollection("TexteBanco", Produto.class );
    	
    	
    	// Inserindo um OBJ
    	//collection.insertOne(new Produto(1,"Arroz",5));
    	
    	// Atualizando um  OBJ
    	//collection.updateOne(new Document("_id ",1),set("descricao","Arroz Parborizado"));
    	
    	// Deletando um OBJ
    	collection.deleteOne(new Document("descricao", "Arroz Parborizado"));
    	
    }
}
