package com.jovora.config.repository;

import com.jovora.config.entity.CategoryDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryDetailRepository extends CrudRepository<CategoryDetail, Long> {

    List<CategoryDetail> findCategoryDetailsByTenantId(long tenantId);
}
