package com.heedi.batch.job.partitioning;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    int findMinId(LocalDate startDate, LocalDate endDate);

    int findMaxId(LocalDate startDate, LocalDate endDate);

}
