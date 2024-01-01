package example.test.config;

import io.micronaut.context.annotation.Value;
import io.micronaut.context.event.BeanCreatedEvent;
import io.micronaut.context.event.BeanCreatedEventListener;
import io.micronaut.context.exceptions.ConfigurationException;
import jakarta.inject.Singleton;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.services.dynamodb.DynamoDbClientBuilder;

import java.net.URI;
import java.net.URISyntaxException;

@Singleton
//@Requires(env = {Environment.TEST, "local"})
public class DynamoDbClientBuilderListener implements BeanCreatedEventListener<DynamoDbClientBuilder> {
    private final URI endpoint;
    private final String accessKeyId;
    private final String secretAccessKey;

    DynamoDbClientBuilderListener(@Value("${dynamodb.host}") String host,
                                  @Value("${dynamodb.port}") String port) {
        try {
            this.endpoint = new URI("http://" + host + ":" + port);
        } catch (URISyntaxException e) {
            throw new ConfigurationException("dynamodb.endpoint not a valid URI", e);
        }
        this.accessKeyId = "fakeMyKeyId";
        this.secretAccessKey = "fakeSecretAccessKey";
    }

    @Override
    public DynamoDbClientBuilder onCreated(BeanCreatedEvent<DynamoDbClientBuilder> event) {
        return event.getBean().endpointOverride(endpoint)
                .credentialsProvider(() -> new AwsCredentials() {
                    @Override
                    public String accessKeyId() {
                        return accessKeyId;
                    }

                    @Override
                    public String secretAccessKey() {
                        return secretAccessKey;
                    }
                });
    }
}