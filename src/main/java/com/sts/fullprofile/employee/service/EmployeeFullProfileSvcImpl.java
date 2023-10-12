package com.sts.fullprofile.employee.service;

import com.sts.fullprofile.employee.Repositories.AddressRepository;
import com.sts.fullprofile.employee.Repositories.EmployeeFullProfileRepository;
import com.sts.fullprofile.employee.entity.AddressEntity;
import com.sts.fullprofile.employee.entity.EmployeeFullProfileEntity;
import com.sts.fullprofile.employee.exception.EmployeeFullProfileException;
import com.sts.fullprofile.employee.mapper.ClassMapper;
import com.sts.fullprofile.employee.model.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EmployeeFullProfileSvcImpl implements EmployeeFullProfileSvc {


    @Autowired
    EmployeeFullProfileRepository repository;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public ResponseEntity<EmployeeResponseHolder> registerNewEmployee(RegisterEmployeeRequest newEmployeeModel){

        EmployeeResponseHolder employeeResponseHolder = new EmployeeResponseHolder();
        try{
            String newEmpId = generateNewEmpId();

            EmployeeFullProfileEntity newEmployeeEntity = ClassMapper.mapEmployeeRegisterModelToEntity(newEmployeeModel,newEmpId);

            List<AddressEntity> addressEntityList = new ArrayList<>();
            for (Address address : newEmployeeModel.getEmpAddress()) {
                addressEntityList.add(ClassMapper.mapAddressModelToEntity(address,newEmployeeEntity));
            }
            newEmployeeEntity.setEmpAddress(addressEntityList);

            newEmployeeEntity = repository.save(newEmployeeEntity);
            Employee employee = ClassMapper.mapEntityToModel(newEmployeeEntity);
            employeeResponseHolder.setEmployeeData(employee);
            employeeResponseHolder.setHasError(false);
            employeeResponseHolder.setErrorMessage("");
            return new ResponseEntity<>(employeeResponseHolder, HttpStatus.OK);
        }
        catch (Exception e){
            employeeResponseHolder.setHasError(true);
            employeeResponseHolder.setErrorMessage(e.getMessage());
            employeeResponseHolder.setEmployeeData(null);
            return new ResponseEntity<>(employeeResponseHolder, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<EmployeeResponseHolder> updateEmployeeDetailsByEmpId(UpdateEmployeeRequest updateEmployeeRequest) {

        EmployeeResponseHolder responseHolder = new EmployeeResponseHolder();
        EmployeeFullProfileEntity updatedEmployee = null;
        String empId = updateEmployeeRequest.getEmpId();
        try{
            ResponseEntity<EmployeeResponseHolder> existingResponseHolder = this.getEmployeeByID(empId);
            if(existingResponseHolder.getStatusCode() != HttpStatus.OK){
                throw new EmployeeFullProfileException("Update failed as employee with emp id - "+ empId + " has not been found");
            }
            else {

                int deletedRowCount = addressRepository.deleteAllAddresses(empId);
                log.info("number of rows deleted: "+ deletedRowCount);

                EmployeeFullProfileEntity updatedEmployeeEntity = ClassMapper.mapEmployeeUpdateModelToEntity(updateEmployeeRequest,empId);

                List<AddressEntity> addressEntityList = new ArrayList<>();
                for (Address address : updateEmployeeRequest.getEmpAddress()) {
                    addressEntityList.add(ClassMapper.mapAddressModelToEntity(address,updatedEmployeeEntity));
                }
                updatedEmployeeEntity.setEmpAddress(addressEntityList);

                updatedEmployee = repository.save(updatedEmployeeEntity);
                Employee employee = ClassMapper.mapEntityToModel(updatedEmployee);
                responseHolder.setEmployeeData(employee);
                responseHolder.setHasError(false);
                responseHolder.setErrorMessage("");
                return new ResponseEntity<>(responseHolder, HttpStatus.OK);
            }
        }
        catch (Exception e){
            responseHolder.setHasError(true);
            responseHolder.setErrorMessage(e.getMessage());
            responseHolder.setEmployeeData(null);
            return new ResponseEntity<>(responseHolder, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<EmployeeResponseHolder> getEmployeeByID(String empId) {

        EmployeeResponseHolder responseHolder = new EmployeeResponseHolder();
        Optional<EmployeeFullProfileEntity> employeeEntity;
        try{
            employeeEntity = repository.findById(empId);
            if (employeeEntity.isPresent()){
                Employee employee = ClassMapper.mapEntityToModel(employeeEntity.get());
                responseHolder.setEmployeeData(employee);
                responseHolder.setHasError(false);
                responseHolder.setErrorMessage("");
                return ResponseEntity.ok(responseHolder);
            }
            else{
                throw new EmployeeFullProfileException("Employee not found with emp id - "+ empId);
            }
        }
        catch (Exception e){
            responseHolder.setHasError(true);
            responseHolder.setErrorMessage(e.getMessage());
            responseHolder.setEmployeeData(null);
            return new ResponseEntity<>(responseHolder,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<EmployeeListResponseHolder> listAllEmployees() {

        EmployeeListResponseHolder employeeListResponseHolder = null;
        List<EmployeeFullProfileEntity> employeeList = null;
        try{
            employeeList = repository.findAll();
            if (employeeList.size() > 0){

                List<Employee> employees = new ArrayList<>();
                for(EmployeeFullProfileEntity employeeEntity: employeeList){
                    employees.add(ClassMapper.mapEntityToModel(employeeEntity));
                }

                employeeListResponseHolder = new EmployeeListResponseHolder();
                employeeListResponseHolder.setEmployeesData(employees);
                employeeListResponseHolder.setHasError(false);
                employeeListResponseHolder.setErrorMessage("");
                return new ResponseEntity<>(employeeListResponseHolder, HttpStatus.OK);
            }
            else{
                throw new EmployeeFullProfileException("Employees not found");
            }
        }
        catch (Exception e){
            employeeListResponseHolder = new EmployeeListResponseHolder();
            employeeListResponseHolder.setHasError(true);
            employeeListResponseHolder.setErrorMessage(e.getMessage());
            employeeListResponseHolder.setEmployeesData(new ArrayList<>());
            return new ResponseEntity<>(employeeListResponseHolder, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<UpdateEmployeeStatusResponse> updateEmployeeStatus(String empId, boolean isCurrentEmployee) {
        UpdateEmployeeStatusResponse updateEmployeeStatusResponse = new UpdateEmployeeStatusResponse();
        EmployeeFullProfileEntity updatedEmployee = null;
        try{
            Optional<EmployeeFullProfileEntity> getEmployee =repository.findById(empId);
            if(!getEmployee.isPresent()){
                throw new EmployeeFullProfileException("Update employee status failed as employee with emp id - "+ empId + " has not been found");
            }
            else {
                updatedEmployee = getEmployee.get();
                updatedEmployee.setIsCurrentEmployee(isCurrentEmployee);
                updatedEmployee = repository.save(updatedEmployee);
                Employee employee = ClassMapper.mapEntityToModel(updatedEmployee);
                updateEmployeeStatusResponse.setBody("Updated employee with: "+ empId+ " has been successfully edited as current employee as "+ isCurrentEmployee);
                updateEmployeeStatusResponse.setHasError(false);
                updateEmployeeStatusResponse.setErrorMessage(null);
                return new ResponseEntity<>(updateEmployeeStatusResponse, HttpStatus.OK);
            }
        }
        catch (Exception e){
            updateEmployeeStatusResponse.setHasError(true);
            updateEmployeeStatusResponse.setErrorMessage(e.getMessage());
            updateEmployeeStatusResponse.setBody("Updation of employee with: "+ empId+ " has been failed edited as current employee as "+ isCurrentEmployee);
            return new ResponseEntity<>(updateEmployeeStatusResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<EmployeeListResponseHolder> getAllOnBenchAndCurrentEmployees() {
        EmployeeListResponseHolder employeeListResponseHolder = new EmployeeListResponseHolder();
        List<EmployeeFullProfileEntity> allOnBenchAndCurrentEmployees = new ArrayList<>();
        try{
            allOnBenchAndCurrentEmployees = repository.getAllOnBenchAndCurrentEmployees();
            if (allOnBenchAndCurrentEmployees.size() > 0){

                Employee employee = null;
                List<Employee> employees = new ArrayList<>();
                for(EmployeeFullProfileEntity employeeEntity: allOnBenchAndCurrentEmployees){
                    employee = ClassMapper.mapEntityToModel(employeeEntity);
                    employees.add(employee);
                }

                employeeListResponseHolder.setEmployeesData(employees);
                employeeListResponseHolder.setHasError(false);
                employeeListResponseHolder.setErrorMessage(null);
                return new ResponseEntity<>(employeeListResponseHolder, HttpStatus.OK);
            }
            else{
                throw new EmployeeFullProfileException("Employees not found");
            }
        }
        catch (Exception e){
            employeeListResponseHolder.setHasError(true);
            employeeListResponseHolder.setErrorMessage(e.getMessage());
            employeeListResponseHolder.setEmployeesData(new ArrayList<>());
            return new ResponseEntity<>(employeeListResponseHolder, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<EmployeeListResponseHolder> getOnBenchAndCurrentEmployee() {
        EmployeeListResponseHolder employeeListResponseHolder = new EmployeeListResponseHolder();
        List<EmployeeFullProfileEntity> onBenchAndCurrentEmployeeEntities = null;
        try{
            onBenchAndCurrentEmployeeEntities = repository.getOnBenchAndCurrentEmployee();

            List<Employee> onBenchAndCurrentEmployees = new ArrayList<>();
            for(EmployeeFullProfileEntity onBenchAndCurrentEmployeeEntity: onBenchAndCurrentEmployeeEntities){
                Employee employee = ClassMapper.mapEntityToModel(onBenchAndCurrentEmployeeEntity);
                onBenchAndCurrentEmployees.add(employee);
            }

            employeeListResponseHolder.setEmployeesData(onBenchAndCurrentEmployees);
            employeeListResponseHolder.setHasError(false);
            employeeListResponseHolder.setErrorMessage(null);
            return new ResponseEntity<>(employeeListResponseHolder, HttpStatus.OK);

        }
        catch (Exception e){
            employeeListResponseHolder.setHasError(true);
            employeeListResponseHolder.setErrorMessage(e.getMessage());
            employeeListResponseHolder.setEmployeesData(new ArrayList<>());
            return new ResponseEntity<>(employeeListResponseHolder, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String getLastGeneratedUserId(){
        return repository.findLastEmployeeId();
    }

    @Override
    public ResponseEntity<DeleteResponse> deleteEmployeeById(String empId) {
        DeleteResponse deleteResponse = new DeleteResponse();

        try{
            repository.deleteById(empId);
            deleteResponse.setBody("Employee with emp id - "+ empId + " has been deleted successfully");
            deleteResponse.setErrorMessage(null);
            deleteResponse.setHasError(false);
            return new ResponseEntity<>(deleteResponse, HttpStatus.OK);

        }
        catch (Exception e){
            deleteResponse.setHasError(true);
            deleteResponse.setErrorMessage(e.getMessage());
            deleteResponse.setBody("Some issue with deletion, emp id- "+empId);
            return new ResponseEntity<>(deleteResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    String generateNewEmpId(){
        String empId = getLastGeneratedUserId();
        if(empId == null){
            return "STS-1000";
        }
        int num =  Integer.parseInt(empId.substring(4)) + 2;

        return "STS-" + String.valueOf(num);
    }
}
