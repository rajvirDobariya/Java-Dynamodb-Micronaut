package example.test.repo;


import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

@Requires(beans = {DynamoDbClient.class})
@Singleton
@Primary
public class DynamoRepository {
    protected final DynamoDbClient dynamoDbClient;
    public DynamoRepository(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    public boolean existsTable(String tableName) {
        try {
            dynamoDbClient.describeTable(DescribeTableRequest.builder()
                    .tableName(tableName)
                    .build());
            return true;
        } catch (ResourceNotFoundException e) {
            return false;
        }
    }



    public void createTable(CreateTableRequest createTableRequest) {
            dynamoDbClient.createTable(createTableRequest);
    }
}