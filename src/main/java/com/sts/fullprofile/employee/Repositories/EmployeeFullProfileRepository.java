package com.sts.fullprofile.employee.Repositories;

import com.sts.fullprofile.employee.entity.EmployeeFullProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeFullProfileRepository extends JpaRepository<EmployeeFullProfileEntity,String> {

    @Query(value = "SELECT EXISTS(SELECT 1 FROM fullprofile_employee LIMIT 1)", nativeQuery = true)
    boolean doesTableExists();

    @Query(value = "SELECT COUNT(*) FROM fullprofile_employee", nativeQuery = true)
    long countRows();
    @Query(value = "SELECT emp_id FROM fullprofile_employee ORDER BY emp_id DESC LIMIT 1", nativeQuery = true)
    String findLastEmployeeId();

    @Query(value = "SELECT fpe FROM EmployeeFullProfileEntity fpe WHERE fpe.isCurrentEmployee = true and fpe.isEmpOnBench = true")
    List<EmployeeFullProfileEntity> getAllOnBenchAndCurrentEmployees();

    @Query(value = "SELECT fpe FROM EmployeeFullProfileEntity fpe WHERE fpe.isCurrentEmployee = true and fpe.isEmpOnBench = true")
    List<EmployeeFullProfileEntity> getOnBenchAndCurrentEmployee();

}
