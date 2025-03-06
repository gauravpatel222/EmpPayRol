package com.example.employeepayrollapp.controller;

import com.example.employeepayrollapp.DTO.EmployeeDTO;
import com.example.employeepayrollapp.model.Employee;
import com.example.employeepayrollapp.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employeepayrollservice")
public class EmployeeController {
    //UC1 --> initialized project

    //UC2 --> added REST API's
    @Autowired
    private EmployeeService service;

    @GetMapping("/all")//http://localhost:8080/employeepayrollservice/all
    public List<EmployeeDTO> getAllEmployees() {
        return service.getAllEmployees().stream()
                .map(employee -> new EmployeeDTO(employee.getName(), employee.getSalary()))
                .collect(Collectors.toList());
    }

    @GetMapping("/get/{id}")//http://localhost:8080/employeepayrollservice/get/1
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        Employee employee = service.getEmployeeById(id);
        return employee != null ?
                ResponseEntity.ok(new EmployeeDTO(employee.getName(), employee.getSalary())) :
                ResponseEntity.notFound().build();
    }

    @PostMapping("/create")//http://localhost:8080/employeepayrollservice/create
    public EmployeeDTO createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        Employee employee = service.createEmployee(employeeDTO);
        return new EmployeeDTO(employee.getName(), employee.getSalary());
    }

    @PutMapping("/update/{id}")//http://localhost:8080/employeepayrollservice/update/1
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDTO employeeDTO) {
        Employee updatedEmployee = service.updateEmployee(id, employeeDTO);
        return updatedEmployee != null ?
                ResponseEntity.ok(new EmployeeDTO(updatedEmployee.getName(), updatedEmployee.getSalary())) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")//http://localhost:8080/employeepayrollservice/delete/1
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        service.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
