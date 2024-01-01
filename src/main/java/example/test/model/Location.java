package example.test.model;

import io.micronaut.context.annotation.Prototype;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotNull;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;

@DynamoDbBean
@Serdeable
@Prototype
public class Location {

    @NotNull
    private String student_id;

    @NotNull
    private String location_id;

    private String city;

    private String state;

    private String Country;

    @NotNull
    @DynamoDbSecondaryPartitionKey(indexNames = { "StudentIdIndex" })
    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    @NotNull
    @DynamoDbPartitionKey
    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }
}
