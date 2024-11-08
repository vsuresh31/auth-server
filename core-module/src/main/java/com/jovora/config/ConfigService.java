package com.jovora.config;

import com.jovora.config.entity.CategoryDetail;
import com.jovora.config.entity.ConfigurationItem;
import com.jovora.config.entity.TenantDetail;
import com.jovora.config.repository.CategoryDetailRepository;
import com.jovora.config.repository.ConfigurationItemRepository;
import com.jovora.config.repository.TenantDetailRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigService {
    private final CategoryDetailRepository categoryDetailRepository;

    private final ConfigurationItemRepository configurationItemRepository;

    private final TenantDetailRepository tenantDetailRepository;

    public ConfigService(CategoryDetailRepository categoryDetailRepository, ConfigurationItemRepository configurationItemRepository, TenantDetailRepository tenantDetailRepository) {
        this.categoryDetailRepository = categoryDetailRepository;
        this.configurationItemRepository = configurationItemRepository;
        this.tenantDetailRepository = tenantDetailRepository;
    }

    public TenantDetail getTenantDetail(String tenantName) {
        TenantDetail byTenantName = tenantDetailRepository.findByTenantName(tenantName);
        List<CategoryDetail> categoryDetailsByTenantId = categoryDetailRepository.findCategoryDetailsByTenantId(byTenantName.getTenantId());
        List<ConfigurationItem> configurationItems = categoryDetailsByTenantId.stream().map(m -> m.categoryId).map(configurationItemRepository::findConfigurationItemsByCategoryId).flatMap(List::stream).toList();
        byTenantName.setConfigurationItems(configurationItems);

        return byTenantName;
    }
}
