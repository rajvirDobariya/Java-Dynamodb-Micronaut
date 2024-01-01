package example.test.repo;

import example.test.model.Employee;
import example.test.model.Location;
import example.test.model.Student;
import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Requires(beans = DynamoDbEnhancedClient.class)
@Singleton
public class LocationRepository {
    private final DynamoDbTable<Location> locationTable;

    public LocationRepository(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        this.locationTable = dynamoDbEnhancedClient.table("Location", TableSchema.fromBean(Location.class));
    }

    public List<Location> findAll() {
        SdkIterable<Location> items = locationTable.scan().items();
        List<Location> collect = items.stream().collect(Collectors.toList());
        return collect;
    }

    public Location createLocation(Location location) {
        locationTable.putItem(location);
        return location;
    }

    public Optional<Location> findByLocationId(String locationId) {
        QueryEnhancedRequest queryRequest = QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(k -> k.partitionValue(locationId)))
                .build();
        return locationTable.query(queryRequest).items().stream().findFirst();
    }

    public Optional<Location> updateLocation(Location updatedLocation) {
        locationTable.updateItem(updatedLocation);
        return Optional.of(updatedLocation);
    }

//    public Optional<Location> deleteLocation(String locationId) {
//        Map<String, AttributeValue> keyValues = new HashMap<>();
//        keyValues.put("location_id", AttributeValue.builder().s(locationId).build());
//        locationTable.deleteItem(keyValues);
//        return findByLocationId(locationId);
//    }
}
    //    // Location
//        dynamoDbClient.createTable(CreateTableRequest.builder()
//                .attributeDefinitions(
//            AttributeDefinition.builder()
//                                .attributeName("location_id")
//                                .attributeType(ScalarAttributeType.S)
//                                .build(),
//                        AttributeDefinition.builder() // Add attribute definition for student_id
//                                .attributeName("student_id")
//                                .attributeType(ScalarAttributeType.S)
//                                .build()
//                )
//                        .keySchema(Arrays.asList(
//            KeySchemaElement.builder()
//                                .attributeName("location_id")
//                                .keyType(KeyType.HASH)
//                                .build()
//                ))
//                        .globalSecondaryIndexes(
//            GlobalSecondaryIndex.builder()
//                                .indexName("StudentIdIndex") // Specify GSI name
//                                .keySchema(
//            KeySchemaElement.builder()
//                                                .attributeName("student_id")
//                                                .keyType(KeyType.HASH)
//                                                .build()
//                                )
//                                        .projection(Projection.builder().projectionType(ProjectionType.ALL).build()) // Define projection as needed
//            .build()
//                )
//                        .billingMode(BillingMode.PAY_PER_REQUEST)
//                .tableName("Location")
//                .build()
//        );


