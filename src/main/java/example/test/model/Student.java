package example.test.model;

import io.micronaut.context.annotation.Prototype;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotNull;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
@Serdeable
@Prototype
public class Student {
    @NotNull
    private String student_id;

    private String name;

    private String jobTitle;

    @NotNull
    @DynamoDbPartitionKey
    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}
