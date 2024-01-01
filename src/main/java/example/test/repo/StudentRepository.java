package example.test.repo;


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
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.*;
import java.util.stream.Collectors;

@Requires(beans = DynamoDbEnhancedClient.class)
@Singleton
public class StudentRepository {
    private final DynamoDbTable<Student> studentTable;
private final LocationRepository locationRepository;
    public StudentRepository(DynamoDbEnhancedClient dynamoDbEnhancedClient, LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
        this.studentTable = dynamoDbEnhancedClient.table("Student", TableSchema.fromBean(Student.class));
    }

    public List<Student> findAll() {
        SdkIterable<Student> items = studentTable.scan().items();
        List<Student> collect = items.stream().collect(Collectors.toList());
        return collect;
    }

    public Student createStudent(Student student) {
        studentTable.putItem(student);
        return student;
    }

    public Optional<Student> findByStudentId(String studentId) {
        QueryEnhancedRequest queryRequest = QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(k -> k.partitionValue(studentId)))
                .build();
        return studentTable.query(queryRequest).items().stream().findFirst();
    }

    public Optional<Student> updateStudent(Student updatedStudent) {
        studentTable.updateItem(updatedStudent);
        return Optional.of(updatedStudent);
    }

}
    // Student
//        if (!existsTable("Student")) {
//        dynamoDbClient.createTable(CreateTableRequest.builder()
//                .attributeDefinitions(AttributeDefinition.builder()
//                        .attributeName("student_id")
//                        .attributeType(ScalarAttributeType.S)
//                        .build()
//                )
//                .keySchema(Arrays.asList(KeySchemaElement.builder()
//                                .attributeName("student_id")
//                                .keyType(KeyType.HASH)
//                                .build()
//                        )
//                )
//                .billingMode(BillingMode.PAY_PER_REQUEST)
//                .tableName("Student")
//                .build());
//    }
//

