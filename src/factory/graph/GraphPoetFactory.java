package factory.graph;

import Exception.Edge.*;
import Exception.FormatException;
import Exception.Vertex.VertexAttributeException;
import Exception.Vertex.VertexLabelException;
import Exception.Vertex.VertexTypeException;
import LoggerFactory.*;
import factory.edge.EdgeFactory;
import graph.Graph;
import graph.GraphPoet;
import vertex.Vertex;
import factory.vertex.VertexFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GraphPoetFactory {
    public static Graph createGraph(String filePath) throws IOException, FormatException, EdgeNullVertexException, VertexAttributeException, VertexTypeException, EdgeTypeException, DirectedEdgeException, HyperEdgeException, EdgeWeightException, VertexLabelException {
        Graph poet;
        // graph name
        String graphName = GraphFactory.GraphLabel(filePath);
        poet = new GraphPoet(graphName);
        filesInput reader = new filesInput(filePath);
        // get Vertices from the file
        List<List<String>> vertexCut = GraphFactory.getVertices(reader);
        for (List<String> list : vertexCut) {
            Vertex newVertex = VertexFactory.createVertex(list.get(0), list.get(1), new String[0]);
            poet.addVertex(newVertex);
        }
        reader = new filesInput(filePath);
        List<List<String>> edgeCut = GraphFactory.getEdges(reader);
        for (List<String> list : edgeCut) {
            try {
                double weight = Double.parseDouble(list.get(2));
                poet.addEdge(EdgeFactory.createEdge(list.get(0), list.get(1), Arrays.asList(poet.getVertex(list.get(3)), poet.getVertex(list.get(4))), (int) weight));
                // 保证输入的weight必须是正整数
                if (weight <= 0 || Math.abs(weight - (int) weight) > 0.0001)
                    throw new EdgeWeightException(list.get(0), list.get(2));
            } catch (EdgeVertexTypeException | EdgeLoopException e) {
                MyLogger.warning(e.toString() + "\ngo on read the file");
            } catch (NumberFormatException e) {
                throw new EdgeWeightException(list.get(0), list.get(2));
            }
        }
        return poet;
    }
}
