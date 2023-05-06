package com.redhat.depdraw.dataservice.service;

import java.util.List;

import com.redhat.depdraw.dataservice.dao.api.LineCatalogDao;
import com.redhat.depdraw.model.LineCatalog;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class LineCatalogService {

    @Inject
    LineCatalogDao lineCatalogDao;

    public LineCatalog getLineCatalogById(String lineCatalogId) {
        return lineCatalogDao.getLineCatalogById(lineCatalogId);
    }

    public List<LineCatalog> getLineCatalogs() {
        return lineCatalogDao.getLineCatalogs();
    }
}
