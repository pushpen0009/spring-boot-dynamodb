package org.mytoshika.model;

import org.mytoshika.utils.GeoTypeConverter;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.*;

@Data
@DynamoDBTable(tableName = "Hotels")
public class Hotel {

    @DynamoDBHashKey
    @DynamoDBGeneratedUuid(DynamoDBAutoGenerateStrategy.CREATE) // Requires a mutable object
    private String id;

    @DynamoDBAttribute
    private String name;

    @DynamoDBAttribute
    @DynamoDBTypeConverted(converter = GeoTypeConverter.class)
    private Geo geo;

}
