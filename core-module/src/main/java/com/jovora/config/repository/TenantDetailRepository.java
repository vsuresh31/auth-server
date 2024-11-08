package com.jovora.config.repository;

import com.jovora.config.entity.TenantDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantDetailRepository extends CrudRepository<TenantDetail, Long> {
    TenantDetail findByTenantName(String tenantName);
}
