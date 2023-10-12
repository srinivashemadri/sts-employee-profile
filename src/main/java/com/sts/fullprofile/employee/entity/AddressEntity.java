package com.sts.fullprofile.employee.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sts.fullprofile.employee.model.AddressType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    @Column(nullable = false)
    private BigDecimal addressZipCode;

    @Column(nullable = false)
    private String addressLine1;

    @Column(nullable = false)
    private String addressLine2;

    @Column(nullable = true)
    private String landmark;

    @ManyToOne
    @JoinColumn(name = "employeeIdFk")
    @JsonBackReference
    private EmployeeFullProfileEntity employee;

}
