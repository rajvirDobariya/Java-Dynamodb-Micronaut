package example.test.config;


import example.test.repo.DynamoRepository;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.context.event.StartupEvent;
import jakarta.inject.Singleton;
import software.amazon.awssdk.services.dynamodb.model.*;

@Requires(property = "dynamodb.host")
@Requires(property = "dynamodb.port")
@Requires(env = Environment.DEVELOPMENT)
@Singleton
public class DevBootstrap implements ApplicationEventListener<StartupEvent> {


    private final DynamoRepository dynamoRepository;
    public DevBootstrap(DynamoRepository dynamoRepository) {
        this.dynamoRepository=dynamoRepository;
    }

    @Override
    public void onApplicationEvent(StartupEvent event) {

        if (!dynamoRepository.existsTable("Employee")) {

            CreateTableRequest createTableRequest = CreateTableRequest.builder()
                    .attributeDefinitions(AttributeDefinition.builder()
                            .attributeName("emp_id")
                            .attributeType(ScalarAttributeType.S)
                            .build(),
                            AttributeDefinition.builder()
                                    .attributeName("name")
                                    .attributeType(ScalarAttributeType.S)
                                    .build(),
                            AttributeDefinition.builder()
                                    .attributeName("email")
                                    .attributeType(ScalarAttributeType.S)
                                    .build()

                    )
                    .keySchema(KeySchemaElement.builder()
                            .attributeName("emp_id")
                            .keyType(KeyType.HASH)
                            .build(),
                            KeySchemaElement.builder()
                                    .attributeName("name")
                                    .keyType(KeyType.RANGE)
                                    .build()
                    )
                    .billingMode(BillingMode.PAY_PER_REQUEST)
                    .tableName("Employee")
                    .build();
            dynamoRepository.createTable(createTableRequest);
        }
    }


}