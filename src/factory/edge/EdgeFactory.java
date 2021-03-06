package factory.edge;

import Exception.Edge.*;
import Exception.*;
import edge.Edge;
import vertex.Vertex;

import java.util.List;

public class EdgeFactory {
    public static Edge createEdge(String label, String type, List<Vertex> vertices, double weight) throws FormatException, EdgeLoopException, EdgeVertexTypeException, HyperEdgeException, EdgeWeightException {
        switch (type) {
            case "WordNeighborhood":
                return poetEdgeFactory.createEdge(label, vertices, weight);
            case "NetworkConnection":
                return networkEdgeFactory.createEdge(label, vertices, weight);
            case "ForwardTie":
                return forwardEdgeFactory.createEdge(label, vertices, weight);
            case "FriendTie":
                return friendEdgeFactory.createEdge(label, vertices, weight);
            case "CommentTie":
                return commentEdgeFactory.createEdge(label, vertices, weight);
            case "MovieDirectorRelation":
                return MovieDirectorEdgeFactory.createEdge(label, vertices, weight);
            case "MovieActorRelation":
                return MovieActorEdgeFactory.createEdge(label, vertices, weight);
            case "SameMovieHyperEdge":
                return SameMovieHyperEdgeFactory.createEdge(label, vertices, weight);
            default:
                throw new FormatException("The Edge Type is not Supported");
        }
    }

    public static boolean edgeType(String label, String type, boolean directed) throws DirectedEdgeException, FormatException {
        switch (type) {
            case "WordNeighborhood":
            case "ForwardTie":
            case "FriendTie":
            case "CommentTie":
                return directed;
            case "NetworkConnection":
            case "MovieDirectorRelation":
            case "MovieActorRelation":
            case "SameMovieHyperEdge":
                if (directed)
                    throw new DirectedEdgeException(label);
                return true;
            default:
                throw new FormatException("The Edge Type is not Supported");
        }
    }
}
