package com.redhat.depdraw.dataservice.service;

import java.util.List;
import java.util.UUID;

import com.redhat.depdraw.dataservice.dao.api.DiagramResourceDao;
import com.redhat.depdraw.model.Diagram;
import com.redhat.depdraw.model.DiagramResource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DiagramResourceService {

    @Inject
    DiagramResourceDao diagramResourceDao;

    @Inject
    DiagramService diagramService;

    public DiagramResource createDiagramResource(String diagramId, DiagramResource diagramResource) {
        UUID uuid = UUID.randomUUID();
        diagramResource.setUuid(uuid.toString());
        diagramResource.setDiagramID(diagramId);
        final DiagramResource createdDiagramResource = diagramResourceDao.create(diagramResource);
        final Diagram diagram = diagramService.getDiagramById(diagramResource.getDiagramID());
        diagram.getResourcesID().add(createdDiagramResource.getUuid());

        diagramService.updateDiagram(diagram);

        return createdDiagramResource;
    }

    public DiagramResource getDiagramResourceById(String diagramId, String diagramResourceId) {
        return diagramResourceDao.getDiagramResourceById(diagramId, diagramResourceId);
    }

    public void deleteDiagramResourceById(String diagramId, String diagramResourceId) {
        diagramResourceDao.deleteDiagramResourceById(diagramId, diagramResourceId);

        final Diagram diagram = diagramService.getDiagramById(diagramId);
        diagram.getResourcesID().remove(diagramResourceId);

        diagramService.updateDiagram(diagram);
    }

    public List<DiagramResource> getDiagramResources(String diagramId) {
        return diagramResourceDao.getDiagramResources(diagramId);
    }

    public void updateDiagramResourceDefinition(String diagramId, String resourceId, String definition) {
        diagramResourceDao.updateDefinition(diagramId, resourceId, definition);
    }

    public String getDiagramResourceDefinition(String diagramId, String resourceId) {
        return diagramResourceDao.getDefinition(diagramId, resourceId);
    }
}
