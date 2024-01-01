package example.test.repo;

import example.test.model.Employee;
import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.List;
import java.util.stream.Collectors;

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
       // DynamoDbTable<Employee> employeeTable = dynamoDbEnhancedClient.table("Employee", TableSchema.fromBean(Employee.class));

        SdkIterable<Employee> items = employeeTable.scan().items();
        List<Employee> collect = items.stream().collect(Collectors.toList());
        return collect;
    }

    public Employee createEmployee(Employee employee) {
      //  DynamoDbTable<Employee> employeeTable = dynamoDbEnhancedClient.table("Employee", TableSchema.fromBean(Employee.class));
        employeeTable.putItem(employee);
        return employee;
    }
}
