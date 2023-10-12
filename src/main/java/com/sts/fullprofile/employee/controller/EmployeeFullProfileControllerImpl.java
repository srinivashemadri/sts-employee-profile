package com.sts.fullprofile.employee.controller;

import com.sts.fullprofile.employee.api.EmployeeApi;
import com.sts.fullprofile.employee.model.*;
import com.sts.fullprofile.employee.service.EmployeeFullProfileSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/full-profile")
@Validated
public class EmployeeFullProfileControllerImpl implements EmployeeApi {

    @Autowired
    EmployeeFullProfileSvc employeeFullProfileSvc;

    @Override
    public ResponseEntity<EmployeeResponseHolder> registerNewEmployee(@Valid @RequestBody RegisterEmployeeRequest newEmployee) {
        return employeeFullProfileSvc.registerNewEmployee(newEmployee);
    }

    @Override
    public ResponseEntity<EmployeeResponseHolder> getEmployeeByID(String empId) {
        return employeeFullProfileSvc.getEmployeeByID(empId);
    }

    @Override
    public ResponseEntity<EmployeeResponseHolder> updateEmployeeDetails(UpdateEmployeeRequest employee) {
        return employeeFullProfileSvc.updateEmployeeDetailsByEmpId(employee);
    }

    @Override
    public ResponseEntity<UpdateEmployeeStatusResponse> updateEmployeeStatus(String empId, Boolean isCurrentEmployee) {
        return employeeFullProfileSvc.updateEmployeeStatus(empId,isCurrentEmployee);
    }

    @Override
    public ResponseEntity<EmployeeListResponseHolder> listAllEmployees() {
        return employeeFullProfileSvc.listAllEmployees();
    }

    @Override
    public ResponseEntity<DeleteResponse> deleteEmployeeById(String employeeId) {
        return employeeFullProfileSvc.deleteEmployeeById(employeeId);
    }

    @Override
    public ResponseEntity<EmployeeListResponseHolder> getAllOnBenchAndCurrentEmployees() {
        return employeeFullProfileSvc.getAllOnBenchAndCurrentEmployees();
    }
}
