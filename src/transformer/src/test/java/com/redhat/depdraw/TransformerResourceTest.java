package com.redhat.depdraw;

import com.redhat.depdraw.model.Line;
import com.redhat.depdraw.model.LineCatalog;
import com.redhat.depdraw.transformer.DataServiceClient;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

@QuarkusTest
public class TransformerResourceTest {

    @InjectMock
    @RestClient
    DataServiceClient dataServiceClient;

    @Test
    public void testTransformerHealthEndpoint() {
        given()
          .when().get("/health")
          .then()
             .statusCode(200);
    }

    @Test
    public void testTransformDiagramLinesWithLabels() {
        List<Line> lines = List.of(new Line("uuid", "diagramID", "lineCatalogID", "1", "2"));
        Mockito.when(dataServiceClient.getLinesByDigramId(anyString())).thenReturn(lines);
        Mockito.when(dataServiceClient.getLineCatalogById(anyString())).thenReturn(new LineCatalog("uuid","lineCatalog1", Set.of(LineCatalog.INHERIT_LABELS)));
        Mockito.when(dataServiceClient.getDefinition(anyString(), eq("1"))).thenReturn("{\n" +
                "  \"apiVersion\": \"v1\",\n" +
                        "  \"kind\": \"Pod\",\n" +
                        "  \"metadata\": {\n" +
                        "    \"name\": \"xmlxml\",\n" +
                        "    \"labels\": {\n" +
                        "       \"environment\" : \"production\",\n" +
                        "       \"app\": \"nginx\"\n" +
                        "    }\n" +
                        "  },\n" +
                        "  \"spec\": {\n" +
                        "    \"containers\": [\n" +
                        "      {\n" +
                        "        \"name\": \"nginx\",\n" +
                        "        \"image\": \"nginx:1.14.2\",\n" +
                        "        \"ports\": [\n" +
                        "          {\n" +
                        "            \"containerPort\": 11\n" +
                        "          }\n" +
                        "        ]\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  }\n" +
                        "}");
        Mockito.when(dataServiceClient.getDefinition(anyString(), eq("2"))).thenReturn("{\n" +
                "  \"apiVersion\": \"v1\",\n" +
                "  \"kind\": \"Pod\",\n" +
                "  \"metadata\": {\n" +
                "    \"name\": \"destName\"\n" +
                "  },\n" +
                "  \"spec\": {\n" +
                "    \"containers\": [\n" +
                "      {\n" +
                "        \"name\": \"nginx\",\n" +
                "        \"image\": \"nginx:1.14.2\",\n" +
                "        \"ports\": [\n" +
                "          {\n" +
                "            \"containerPort\": 11\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}");

        given()
                .when().get("/transform/1111-1111/lines")
                .then()
                .statusCode(200);
    }

    @Test
    public void testTransformDiagramLinesWithDestExistingLabels() {
        List<Line> lines = List.of(new Line("uuid", "diagramID", "lineCatalogID", "1", "2"));
        Mockito.when(dataServiceClient.getLinesByDigramId(anyString())).thenReturn(lines);
        Mockito.when(dataServiceClient.getLineCatalogById(anyString())).thenReturn(new LineCatalog("uuid","lineCatalog1", Set.of(LineCatalog.INHERIT_LABELS)));
        Mockito.when(dataServiceClient.getDefinition(anyString(), eq("1"))).thenReturn("{\n" +
                "  \"apiVersion\": \"v1\",\n" +
                "  \"kind\": \"Pod\",\n" +
                "  \"metadata\": {\n" +
                "    \"name\": \"xmlxml\",\n" +
                "    \"labels\": {\n" +
                "       \"environment\" : \"production\",\n" +
                "       \"app\": \"nginx\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"spec\": {\n" +
                "    \"containers\": [\n" +
                "      {\n" +
                "        \"name\": \"nginx\",\n" +
                "        \"image\": \"nginx:1.14.2\",\n" +
                "        \"ports\": [\n" +
                "          {\n" +
                "            \"containerPort\": 11\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}");
        Mockito.when(dataServiceClient.getDefinition(anyString(), eq("2"))).thenReturn("{\n" +
                "  \"apiVersion\": \"v1\",\n" +
                "  \"kind\": \"Pod\",\n" +
                "  \"metadata\": {\n" +
                "    \"name\": \"xmlxml\",\n" +
                "    \"labels\": {\n" +
                "       \"environment\" : \"production\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"spec\": {\n" +
                "    \"containers\": [\n" +
                "      {\n" +
                "        \"name\": \"nginx\",\n" +
                "        \"image\": \"nginx:1.14.2\",\n" +
                "        \"ports\": [\n" +
                "          {\n" +
                "            \"containerPort\": 11\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}");

        given()
                .when().get("/transform/1111-1111/lines")
                .then()
                .statusCode(200);
    }

    @Test
    public void testTransformDiagramLinesWithAnnotations() {
        List<Line> lines = List.of(new Line("uuid", "diagramID", "lineCatalogID", "1", "2"));
        Mockito.when(dataServiceClient.getLinesByDigramId(anyString())).thenReturn(lines);
        Mockito.when(dataServiceClient.getLineCatalogById(anyString())).thenReturn(new LineCatalog("uuid","lineCatalog1", Set.of(LineCatalog.INHERIT_ANNOTATIONS)));
        Mockito.when(dataServiceClient.getDefinition(anyString(), eq("1"))).thenReturn("{\n" +
                "  \"apiVersion\": \"v1\",\n" +
                "  \"kind\": \"Pod\",\n" +
                "  \"metadata\": {\n" +
                "    \"name\": \"xmlxml\",\n" +
                "    \"annotations\": {\n" +
                "       \"environment\" : \"production\",\n" +
                "       \"app\": \"nginx\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"spec\": {\n" +
                "    \"containers\": [\n" +
                "      {\n" +
                "        \"name\": \"nginx\",\n" +
                "        \"image\": \"nginx:1.14.2\",\n" +
                "        \"ports\": [\n" +
                "          {\n" +
                "            \"containerPort\": 11\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}");
        Mockito.when(dataServiceClient.getDefinition(anyString(), eq("2"))).thenReturn("{\n" +
                "  \"apiVersion\": \"v1\",\n" +
                "  \"kind\": \"Pod\",\n" +
                "  \"metadata\": {\n" +
                "    \"name\": \"xmlxml\"\n" +
                "  },\n" +
                "  \"spec\": {\n" +
                "    \"containers\": [\n" +
                "      {\n" +
                "        \"name\": \"nginx\",\n" +
                "        \"image\": \"nginx:1.14.2\",\n" +
                "        \"ports\": [\n" +
                "          {\n" +
                "            \"containerPort\": 11\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}");

        given()
                .when().get("/transform/1111-1111/lines")
                .then()
                .statusCode(200);
    }

    @Test
    public void testTransformDiagramLinesWithDestExistingAnnotations() {
        List<Line> lines = List.of(new Line("uuid", "diagramID", "lineCatalogID", "1", "2"));
        Mockito.when(dataServiceClient.getLinesByDigramId(anyString())).thenReturn(lines);
        Mockito.when(dataServiceClient.getLineCatalogById(anyString())).thenReturn(new LineCatalog("uuid","lineCatalog1", Set.of(LineCatalog.INHERIT_ANNOTATIONS)));
        Mockito.when(dataServiceClient.getDefinition(anyString(), eq("1"))).thenReturn("{\n" +
                "  \"apiVersion\": \"v1\",\n" +
                "  \"kind\": \"Pod\",\n" +
                "  \"metadata\": {\n" +
                "    \"name\": \"xmlxml\",\n" +
                "    \"annotations\": {\n" +
                "       \"environment\" : \"production\",\n" +
                "       \"app\": \"nginx\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"spec\": {\n" +
                "    \"containers\": [\n" +
                "      {\n" +
                "        \"name\": \"nginx\",\n" +
                "        \"image\": \"nginx:1.14.2\",\n" +
                "        \"ports\": [\n" +
                "          {\n" +
                "            \"containerPort\": 11\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}");
        Mockito.when(dataServiceClient.getDefinition(anyString(), eq("2"))).thenReturn("{\n" +
                "  \"apiVersion\": \"v1\",\n" +
                "  \"kind\": \"Pod\",\n" +
                "  \"metadata\": {\n" +
                "    \"name\": \"xmlxml\",\n" +
                "    \"annotations\": {\n" +
                "       \"environment\" : \"production\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"spec\": {\n" +
                "    \"containers\": [\n" +
                "      {\n" +
                "        \"name\": \"nginx\",\n" +
                "        \"image\": \"nginx:1.14.2\",\n" +
                "        \"ports\": [\n" +
                "          {\n" +
                "            \"containerPort\": 11\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}");

        given()
                .when().get("/transform/1111-1111/lines")
                .then()
                .statusCode(200);
    }

    @Test
    public void testTransformDiagramLinesWithMetadata() {
        List<Line> lines = List.of(new Line("uuid", "diagramID", "lineCatalogID", "1", "2"));
        Mockito.when(dataServiceClient.getLinesByDigramId(anyString())).thenReturn(lines);
        Mockito.when(dataServiceClient.getLineCatalogById(anyString())).thenReturn(new LineCatalog("uuid","lineCatalog1", Set.of(LineCatalog.INHERIT_METADATA)));
        Mockito.when(dataServiceClient.getDefinition(anyString(), eq("1"))).thenReturn("{\n" +
                "  \"apiVersion\": \"v1\",\n" +
                "  \"kind\": \"Pod\",\n" +
                "  \"metadata\": {\n" +
                "    \"name\": \"xmlxml\",\n" +
                "    \"labels\": {\n" +
                "       \"environment\" : \"production\",\n" +
                "       \"app\": \"nginx\"\n" +
                "    },\n" +
                "    \"annotations\": {\n" +
                "       \"environment\" : \"annotation-test\",\n" +
                "       \"app\": \"annotation-test\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"spec\": {\n" +
                "    \"containers\": [\n" +
                "      {\n" +
                "        \"name\": \"nginx\",\n" +
                "        \"image\": \"nginx:1.14.2\",\n" +
                "        \"ports\": [\n" +
                "          {\n" +
                "            \"containerPort\": 11\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}");
        Mockito.when(dataServiceClient.getDefinition(anyString(), eq("2"))).thenReturn("{\n" +
                "  \"apiVersion\": \"v1\",\n" +
                "  \"kind\": \"Pod\",\n" +
                "  \"metadata\": {\n" +
                "    \"name\": \"destName\"\n" +
                "  },\n" +
                "  \"spec\": {\n" +
                "    \"containers\": [\n" +
                "      {\n" +
                "        \"name\": \"nginx\",\n" +
                "        \"image\": \"nginx:1.14.2\",\n" +
                "        \"ports\": [\n" +
                "          {\n" +
                "            \"containerPort\": 11\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}");

        given()
                .when().get("/transform/1111-1111/lines")
                .then()
                .statusCode(200);
    }

    @Test
    public void testTransformDiagramLinesWithDestExistingMetadata() {
        List<Line> lines = List.of(new Line("uuid", "diagramID", "lineCatalogID", "1", "2"));
        Mockito.when(dataServiceClient.getLinesByDigramId(anyString())).thenReturn(lines);
        Mockito.when(dataServiceClient.getLineCatalogById(anyString())).thenReturn(new LineCatalog("uuid","lineCatalog1", Set.of(LineCatalog.INHERIT_METADATA)));
        Mockito.when(dataServiceClient.getDefinition(anyString(), eq("1"))).thenReturn("{\n" +
                "  \"apiVersion\": \"v1\",\n" +
                "  \"kind\": \"Pod\",\n" +
                "  \"metadata\": {\n" +
                "    \"name\": \"xmlxml\",\n" +
                "    \"labels\": {\n" +
                "       \"environment\" : \"production\",\n" +
                "       \"app\": \"nginx\"\n" +
                "    },\n" +
                "    \"annotations\": {\n" +
                "       \"environment\" : \"annotation-test\",\n" +
                "       \"app\": \"annotation-test\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"spec\": {\n" +
                "    \"containers\": [\n" +
                "      {\n" +
                "        \"name\": \"nginx\",\n" +
                "        \"image\": \"nginx:1.14.2\",\n" +
                "        \"ports\": [\n" +
                "          {\n" +
                "            \"containerPort\": 11\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}");
        Mockito.when(dataServiceClient.getDefinition(anyString(), eq("2"))).thenReturn("{\n" +
                "  \"apiVersion\": \"v1\",\n" +
                "  \"kind\": \"Pod\",\n" +
                "  \"metadata\": {\n" +
                "    \"name\": \"xmlxml\",\n" +
                "    \"labels\": {\n" +
                "       \"app\": \"nginx\"\n" +
                "    },\n" +
                "    \"annotations\": {\n" +
                "       \"app\": \"annotation-test\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"spec\": {\n" +
                "    \"containers\": [\n" +
                "      {\n" +
                "        \"name\": \"nginx\",\n" +
                "        \"image\": \"nginx:1.14.2\",\n" +
                "        \"ports\": [\n" +
                "          {\n" +
                "            \"containerPort\": 11\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}");

        given()
                .when().get("/transform/1111-1111/lines")
                .then()
                .statusCode(200);
    }

    @Test
    public void testTransformDiagramLinesWithSelector() {
        List<Line> lines = List.of(new Line("uuid", "diagramID", "lineCatalogID", "1", "2"));
        Mockito.when(dataServiceClient.getLinesByDigramId(anyString())).thenReturn(lines);
        Mockito.when(dataServiceClient.getLineCatalogById(anyString())).thenReturn(new LineCatalog("uuid","lineCatalog1", Set.of(LineCatalog.SELECT_RESOURCE)));
        Mockito.when(dataServiceClient.getDefinition(anyString(), eq("1"))).thenReturn("{\n" +
                "  \"apiVersion\": \"v1\",\n" +
                "  \"kind\": \"Pod\",\n" +
                "  \"metadata\": {\n" +
                "    \"name\": \"destName\"\n" +
                "  },\n" +
                "  \"spec\": {\n" +
                "    \"selector\": {\n" +
                "      \"matchLabels\": {\n" +
                "        \"app\": \"metadata-pod\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"containers\": [\n" +
                "      {\n" +
                "        \"name\": \"nginx\",\n" +
                "        \"image\": \"nginx:1.14.2\",\n" +
                "        \"ports\": [\n" +
                "          {\n" +
                "            \"containerPort\": 11\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}");
        Mockito.when(dataServiceClient.getDefinition(anyString(), eq("2"))).thenReturn("{\n" +
                "  \"apiVersion\": \"v1\",\n" +
                "  \"kind\": \"Pod\",\n" +
                "  \"metadata\": {\n" +
                "    \"name\": \"xmlxml\"\n" +
                "  },\n" +
                "  \"spec\": {\n" +
                "    \"containers\": [\n" +
                "      {\n" +
                "        \"name\": \"nginx\",\n" +
                "        \"image\": \"nginx:1.14.2\",\n" +
                "        \"ports\": [\n" +
                "          {\n" +
                "            \"containerPort\": 11\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}");

        given()
                .when().get("/transform/1111-1111/lines")
                .then()
                .statusCode(200);
    }

}