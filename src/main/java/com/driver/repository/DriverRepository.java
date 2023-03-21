package com.driver.repository;

import java.util.List;

import com.driver.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.driver.model.Driver;
@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer>{


//    @Query(value = "select * from Driver",
//            nativeQuery = true)
//    List<Driver> getListofallDriver();

}
