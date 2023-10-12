package com.sts.fullprofile.employee.service;

import com.sts.fullprofile.employee.model.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmployeeFullProfileSvc {


    public ResponseEntity<EmployeeResponseHolder> registerNewEmployee(RegisterEmployeeRequest newEmployee);

    public ResponseEntity<EmployeeResponseHolder> getEmployeeByID(String empId);

    public ResponseEntity<EmployeeResponseHolder> updateEmployeeDetailsByEmpId(UpdateEmployeeRequest employee);

    public ResponseEntity<EmployeeListResponseHolder> listAllEmployees();

    public ResponseEntity<UpdateEmployeeStatusResponse> updateEmployeeStatus(String empId, boolean isCurrentEmployee);

    public ResponseEntity<EmployeeListResponseHolder> getAllOnBenchAndCurrentEmployees();

    ResponseEntity<EmployeeListResponseHolder> getOnBenchAndCurrentEmployee();

    public String getLastGeneratedUserId();

    public ResponseEntity<DeleteResponse> deleteEmployeeById(String empId);
}
