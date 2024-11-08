package com.jovora.config.repository;

import com.jovora.config.entity.ConfigurationItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigurationItemRepository extends CrudRepository<ConfigurationItem, Long> {
    List<ConfigurationItem> findConfigurationItemsByCategoryId(long categoryId);
}


