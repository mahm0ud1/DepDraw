package com.redhat.depdraw.dataservice.service;

import java.util.List;

import com.redhat.depdraw.dataservice.dao.api.ResourceCatalogDao;
import com.redhat.depdraw.model.ResourceCatalog;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ResourceCatalogService {

    @Inject
    ResourceCatalogDao resourceCatalogDao;

    public ResourceCatalog getResourceCatalogById(String resourceCatalogId) {
        return resourceCatalogDao.getResourceCatalogById(resourceCatalogId);
    }

    public List<ResourceCatalog> getResourceCatalogs() {
        return resourceCatalogDao.getResourceCatalogs();
    }
}
