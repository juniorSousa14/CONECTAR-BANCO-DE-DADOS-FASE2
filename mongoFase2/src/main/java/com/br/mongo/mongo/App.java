package com.br.mongo.mongo;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;



public class App 
{
    public static void main( String[] args ) throws IOException, InterruptedException
    {

    	
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
        		fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        
        MongoClient mongoClient = new MongoClient("localhost:27017", 
        		MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());
        
        MongoDatabase database = mongoClient.getDatabase("exemplo")
        		.withCodecRegistry(pojoCodecRegistry);
        
        MongoCollection<Document> collection = database.getCollection("api");
        

        HttpClient httpClient = HttpClient.newHttpClient();
        URI apiUri = URI.create("https://eonet.gsfc.nasa.gov/api/v2.1/events?limit=5");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(apiUri)
                .build();


        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());


        if (response.statusCode() == 200) {

            String responseBody = response.body();


            Document document = Document.parse(responseBody);
            collection.insertOne(document);

            System.out.println("Dados da API inseridos no MongoDB com sucesso.");
        } else {
            System.err.println("Falha ao acessar a API. Código de status: " + response.statusCode());
        }


        mongoClient.close();
    }
}

