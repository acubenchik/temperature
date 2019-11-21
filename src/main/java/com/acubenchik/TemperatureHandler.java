package com.acubenchik;

import com.acubenchik.model.TemperatureRequest;
import com.acubenchik.model.TemperatureResponse;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.UUID;

public class TemperatureHandler implements RequestHandler<TemperatureRequest, TemperatureResponse> {

    private static Table table;

    static {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
        DynamoDB dynamoDB = new DynamoDB(client);
        table = dynamoDB.getTable("Temperature");
    }

    @Override
    public TemperatureResponse handleRequest(TemperatureRequest temperatureRequest, Context context) {
        PutItemOutcome result =
                table.putItem(new Item().withPrimaryKey("id", UUID.randomUUID().toString())
                        .with("temperature", 12));
        TemperatureResponse temperatureResponse = new TemperatureResponse();
        temperatureResponse.setMessage(result.getPutItemResult().toString());
        return temperatureResponse;
    }
}
