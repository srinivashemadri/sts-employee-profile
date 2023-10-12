package com.sts.fullprofile.employee.mapper;

import com.sts.fullprofile.employee.entity.AddressEntity;
import com.sts.fullprofile.employee.entity.EmployeeFullProfileEntity;
import com.sts.fullprofile.employee.model.Address;
import com.sts.fullprofile.employee.model.Employee;
import com.sts.fullprofile.employee.model.RegisterEmployeeRequest;
import com.sts.fullprofile.employee.model.UpdateEmployeeRequest;
import java.util.ArrayList;
import java.util.List;

public class ClassMapper {

    public static AddressEntity mapAddressModelToEntity(Address address,EmployeeFullProfileEntity newEmployee){
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddressLine1(address.getAddressLine1());
        addressEntity.setAddressLine2(address.getAddressLine2());
        addressEntity.setAddressType(address.getAddressType());
        addressEntity.setAddressZipCode(address.getAddressZipCode());
        addressEntity.setLandmark(address.getLandmark());
        addressEntity.setEmployee(newEmployee);
        return addressEntity;
    }

    public static Address mapAddressEntityToModel(AddressEntity addressEntity){
        Address address = new Address();
        address.setAddressId(addressEntity.getAddressId());
        address.setAddressLine1(addressEntity.getAddressLine1());
        address.setAddressLine2(addressEntity.getAddressLine2());
        address.setAddressType(addressEntity.getAddressType());
        address.setAddressZipCode(addressEntity.getAddressZipCode());
        address.setLandmark(addressEntity.getLandmark());
        return address;
    }

    public static Employee mapEntityToModel(EmployeeFullProfileEntity employeeFullProfileEntity){

        Employee employeeModel = new Employee();
        employeeModel.setEmpId(employeeFullProfileEntity.getEmpId());
        employeeModel.setEmpName(employeeFullProfileEntity.getEmpName());
        employeeModel.setEmpEmail(employeeFullProfileEntity.getEmpEmail());
        employeeModel.setEmpPhoneNum(employeeFullProfileEntity.getEmpPhoneNum());
        employeeModel.setEmpSalary(employeeFullProfileEntity.getEmpSalary());
        employeeModel.setEmpDesignation(employeeFullProfileEntity.getEmpDesignation());
        employeeModel.setEmpExperience(employeeFullProfileEntity.getEmpExperience());
        employeeModel.setEmpDept(employeeFullProfileEntity.getEmpDept());
        employeeModel.setEmpDOB(employeeFullProfileEntity.getEmpDOB());
        employeeModel.setEmpSkills(employeeFullProfileEntity.getEmpSkills());
        employeeModel.setIsCurrentEmployee(employeeFullProfileEntity.getIsCurrentEmployee());
        employeeModel.setIsEmployeeOnBench(employeeFullProfileEntity.getIsEmpOnBench());

        List<Address> addressList = new ArrayList<>();
        for(AddressEntity addressEntity:employeeFullProfileEntity.getEmpAddress()){
            addressList.add(mapAddressEntityToModel(addressEntity));
        }
        employeeModel.setEmpAddress(addressList);

        return employeeModel;
    }

    public static EmployeeFullProfileEntity mapEmployeeRegisterModelToEntity(RegisterEmployeeRequest employee, String newEmpId){

        EmployeeFullProfileEntity newEmployee = new EmployeeFullProfileEntity();
        newEmployee.setEmpId(newEmpId);
        newEmployee.setEmpName(employee.getEmpName());
        newEmployee.setEmpEmail(employee.getEmpEmail());
        newEmployee.setEmpPhoneNum(employee.getEmpPhoneNum());
        newEmployee.setEmpSalary(employee.getEmpSalary());
        newEmployee.setEmpDesignation(getDesignation(employee.getEmpExperience()));
        newEmployee.setEmpExperience(employee.getEmpExperience());
        newEmployee.setEmpDept(employee.getEmpDept());
        newEmployee.setEmpDOB(employee.getEmpDOB());
        newEmployee.setEmpSkills(employee.getEmpSkills());
        newEmployee.setIsCurrentEmployee(false);
        newEmployee.setIsEmpOnBench(true);

        return newEmployee;
    }

    public static EmployeeFullProfileEntity mapEmployeeUpdateModelToEntity(UpdateEmployeeRequest employee, String newEmpId){

        EmployeeFullProfileEntity newEmployee = new EmployeeFullProfileEntity();
        newEmployee.setEmpId(newEmpId);
        newEmployee.setEmpName(employee.getEmpName());
        newEmployee.setEmpEmail(employee.getEmpEmail());
        newEmployee.setEmpPhoneNum(employee.getEmpPhoneNum());
        newEmployee.setEmpSalary(employee.getEmpSalary());
        newEmployee.setEmpDesignation(getDesignation(employee.getEmpExperience()));
        newEmployee.setEmpExperience(employee.getEmpExperience());
        newEmployee.setEmpDept(employee.getEmpDept());
        newEmployee.setEmpDOB(employee.getEmpDOB());
        newEmployee.setEmpSkills(employee.getEmpSkills());
        newEmployee.setIsCurrentEmployee(false);
        newEmployee.setIsEmpOnBench(true);

        return newEmployee;
    }

    private static String getDesignation(int experience){

        if(experience <= 2)
            return "Programmer Analyst Trainee";
        else if (experience <4) {
            return "Programmer Analyst";
        } else if (experience < 6) {
            return "Associate";
        }
        else if(experience < 10){
            return "Senior Associate";
        }
        else
            return "Manager";
    }
}


