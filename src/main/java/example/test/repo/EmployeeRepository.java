package example.test.repo;

import example.test.model.Employee;
import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.*;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.*;
import java.util.stream.Collectors;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import java.util.List;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.UpdateItemEnhancedRequest;
import java.util.Optional;

@Requires(beans = DynamoDbClient.class)
@Singleton
public class EmployeeRepository {

    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
    private final DynamoDbTable<Employee> employeeTable;


    public EmployeeRepository(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        this.dynamoDbEnhancedClient = dynamoDbEnhancedClient;
        this.employeeTable = dynamoDbEnhancedClient.table("Employee", TableSchema.fromBean(Employee.class));
    }


    public List<Employee> findAll() {
        SdkIterable<Employee> items = employeeTable.scan().items();
        List<Employee> collect = items.stream().collect(Collectors.toList());
        return collect;
    }

    public Employee createEmployee(Employee employee) {
        employeeTable.putItem(employee);
        return employee;
    }

    public Optional<Employee> findByEmpId(String empId) {
        QueryEnhancedRequest queryRequest = QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(k -> k.partitionValue(empId)))
                .build();

        return employeeTable.query(queryRequest).items().stream().findFirst();
    }

    public List<Employee> findByEmail(String email) {
        Expression expression = Expression.builder()
                .expression("#email = :email")
                .expressionNames(Collections.singletonMap("#email", "email"))
                .expressionValues(Collections.singletonMap(":email", AttributeValue.builder().s(email).build()))
                .build();

        ScanEnhancedRequest scanRequest = ScanEnhancedRequest.builder()
                .filterExpression(expression)
                .build();

        List<Employee> employees = employeeTable.scan(scanRequest).items().stream()
                .collect(Collectors.toList());

        return employees;
    }

    public List<Employee> findByName(String name) {
        Expression expression = Expression.builder()
                .expression("#name = :name")
                .expressionNames(Collections.singletonMap("#name", "name"))
                .expressionValues(Collections.singletonMap(":name", AttributeValue.builder().s(name).build()))
                .build();

        ScanEnhancedRequest scanRequest = ScanEnhancedRequest.builder()
                .filterExpression(expression)
                .build();

        List<Employee> employees = employeeTable.scan(scanRequest).items().stream()
                .collect(Collectors.toList());

        return employees;
    }

    public Optional<Employee> updateEmployeeById(Employee updatedEmployee) {

        UpdateItemEnhancedRequest<Employee> updateRequest = UpdateItemEnhancedRequest.builder(Employee.class)
                .item(updatedEmployee)
                .build();
        employeeTable.updateItem(updateRequest);

        return Optional.of(updatedEmployee);
    }

    public Optional<Employee> deleteEmployeeById(String empId, String sortKeyValue) {
        Key key = Key.builder()
                .partitionValue(empId)
                .sortValue(sortKeyValue)
                .build();
        DeleteItemEnhancedRequest deleteRequest = DeleteItemEnhancedRequest.builder()
                .key(key)
                .build();
        Employee deletedEmployee = employeeTable.deleteItem(deleteRequest);

        return Optional.ofNullable(deletedEmployee);
    }
    //            dynamoDbClient.createTable(CreateTableRequest.builder()
//                    .attributeDefinitions(AttributeDefinition.builder()
//                                .attributeName("emp_id")
//                                .attributeType(ScalarAttributeType.S)
//                                .build(),
//                        AttributeDefinition.builder()
//                                .attributeName("name")
//                                .attributeType(ScalarAttributeType.S)
//                                .build()
//                                )
//                                        .keySchema(Arrays.asList(KeySchemaElement.builder()
//                                .attributeName("emp_id")
//                                .keyType(KeyType.HASH)
//                                .build(),
//                        KeySchemaElement.builder()
//                                .attributeName("name")
//                                .keyType(KeyType.RANGE)
//                                .build()))
//            .billingMode(BillingMode.PAY_PER_REQUEST)
//                .tableName("Employee")
//                .build());

}
