package org.mytoshika.repository;

import java.util.Collections;

import org.mytoshika.exception.DuplicateTableException;
import org.mytoshika.exception.GenericDynamoDBException;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomHotelRepositoryImpl implements CustomHotelRepository {

    private static final String TABLE_NAME = "Hotels";

    private final DynamoDB dynamoDB;

    public CustomHotelRepositoryImpl(DynamoDB dynamoDB) {
        this.dynamoDB = dynamoDB;
    }

    @Override
    public void createTable() {

        try {
            Table table = dynamoDB.createTable(TABLE_NAME,
                    Collections.singletonList(new KeySchemaElement("id", KeyType.HASH)),
                    Collections.singletonList(new AttributeDefinition("id", ScalarAttributeType.S)),
                    new ProvisionedThroughput(10L, 10L));
            table.waitForActive();

            log.info("Success. Table status: " + table.getDescription().getTableStatus());
        } catch (ResourceInUseException e) {
            log.error("Table already Exists: {}", e.getMessage());
            throw new DuplicateTableException(e);

        } catch (Exception e) {
            log.error("Unable to create table: {}", e.getMessage());
            throw new GenericDynamoDBException(e);
        }
    }


}
