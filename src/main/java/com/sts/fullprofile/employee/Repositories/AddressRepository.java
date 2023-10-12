package com.sts.fullprofile.employee.Repositories;

import com.sts.fullprofile.employee.entity.AddressEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Integer> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM address_entity where employee_id_fk = :foreignKeyId", nativeQuery = true)
    int deleteAllAddresses(@Param("foreignKeyId") String foreignKeyId);
}
