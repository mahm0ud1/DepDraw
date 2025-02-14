package com.redhat.depdraw.dataservice.service;

import java.util.List;
import java.util.UUID;

import com.redhat.depdraw.dataservice.dao.api.LineDao;
import com.redhat.depdraw.model.Diagram;
import com.redhat.depdraw.model.Line;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class LineService {

    @Inject
    LineDao lineDao;

    @Inject
    DiagramService diagramService;

    public Line createLine(String diagramId, Line line) {
        UUID uuid = UUID.randomUUID();
        line.setUuid(uuid.toString());
        line.setDiagramID(diagramId);

        final Line createdLine = lineDao.create(line);

        final Diagram diagram = diagramService.getDiagramById(line.getDiagramID());
        diagram.getLinesID().add(line.getUuid());

        diagramService.updateDiagram(diagram);

        return createdLine;
    }

    public Line getLineById(String diagramId, String lineId) {
        return lineDao.getLineById(diagramId, lineId);
    }

    public void deleteLineById(String diagramId, String lineId) {
        final Line line = getLineById(diagramId, lineId);

        if(line != null) {
            lineDao.deleteLineById(diagramId, lineId);
            final Diagram diagram = diagramService.getDiagramById(line.getDiagramID());
            if(diagram != null){
                diagram.getLinesID().remove(lineId);
            }

            diagramService.updateDiagram(diagram);
        }
    }

    public List<Line> getLines(String diagramId) {
        return lineDao.getLines(diagramId);
    }
}
