package com.heedi.batch.job.partitioning;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductBackupRepository extends JpaRepository<ProductBackup, Long> {

}
