package example.test.controller;

import example.test.model.Employee;
import example.test.repo.EmployeeRepository;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

import java.util.List;

@Controller("/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Get
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @Post
    public Employee create(@Body Employee employee) {
        return  employeeRepository.createEmployee(employee);
    }
}
