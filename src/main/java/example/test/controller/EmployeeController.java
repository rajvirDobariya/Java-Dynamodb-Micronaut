package example.test.controller;

import example.test.model.Employee;
import example.test.repo.EmployeeRepository;
import io.micronaut.http.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller("/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;


    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Get
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Post
    public Employee createEmployee(@Body Employee employee) {
        return  employeeRepository.createEmployee(employee);
    }

    @Get("/by-email")
    public List<Employee> getEmployeesByEmail(@QueryValue String email) {
        return employeeRepository.findByEmail(email);
    }

    @Get("/by-name")
    public List<Employee> getEmployeesByName(@QueryValue String name) {
        return employeeRepository.findByName(name);
    }

    @Get("/{empId}")
    public Optional<Employee> getEmployeeById(@PathVariable String empId) {
        return employeeRepository.findByEmpId(empId);
    }

    @Put
    public Optional<Employee> updateEmployeeById(@Body Employee updatedEmployee) {
        return employeeRepository.updateEmployeeById(updatedEmployee);
    }

    @Delete
    public Optional<Employee> deleteEmployeeById(@QueryValue String empId, @QueryValue String sortKeyValue) {
        return employeeRepository.deleteEmployeeById(empId,sortKeyValue);
    }
}
