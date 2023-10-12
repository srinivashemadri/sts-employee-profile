package com.sts.fullprofile.employee.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sts.fullprofile.employee.model.EmployeeDepartment;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fullprofile_employee")
public class EmployeeFullProfileEntity {

    @Id
    private String empId;

    @Column
    @NotNull
    private String empName;

    @Column
    @NotNull
    private String empEmail;

    @Column
    @NotNull
    private BigDecimal empPhoneNum;

    @Column
    @NotNull
    private Double empSalary;

    @Column
    @NotNull
    private String empDesignation;

    @Column
    @NotNull
    private int empExperience;

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private EmployeeDepartment empDept;

    @Column
    @NotNull
    private Date empDOB;

    @Column
    @NotNull
    private String empSkills;

    @Column
    @NotNull
    private Boolean isCurrentEmployee;

    @Column
    @NotNull
    private Boolean isEmpOnBench;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "employee")
    @JsonIgnoreProperties("employee")
    private List<AddressEntity> empAddress;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    @JsonManagedReference
    private List<CompanyHistoryEntity> companyHistories;
}
