import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.NoSuchElementException;

class TestUnit //TODO check kraynie cases
{
    @Test
    void addNodeTest()
    {
        Graph actual = new Graph();
        actual.addNode(new Node("sa"));

        HashSet<Node> as = new HashSet<>();
        as.add(new Node("sa"));
        Graph expected = new Graph(as);

        assertEquals(expected, actual);
    }

    @Test
    void addEdgeTest()
    {
        Graph graph = new Graph();
        graph.addNode("a");
        graph.addNode("b");
        graph.addNode("c");
        graph.addEdge("a", "b", 13);

        HashSet<WeightedEdge> expected = new HashSet<>();

        expected.add(new WeightedEdge(new Edge("a", "b"), 13));

        assertEquals(expected, graph.getWeightedEdges());

        Executable exec = () -> {
            Graph g = new Graph();

            g.addEdge(new Edge("a", "b"), 13);
        };

        Executable exec1 = () -> {
            Graph g = new Graph();

            g.addEdge("a", "b", 13);
        };

        Executable exec2 = () -> {
            Graph g = new Graph();

            g.addNode("a");
            g.addEdge("a", "b", 13);
        };

        Executable exec3 = () -> {
            Graph g = new Graph();

            g.addNode("a");
            g.addEdge("a", "b", -13);
        };

        assertThrows(NoSuchNodeException.class, exec);
        assertThrows(NoSuchNodeException.class, exec1);
        assertThrows(NoSuchNodeException.class, exec2);
        assertThrows(NegativeWeightException.class, exec3);
    }

    @Test
    void deleteEdgeTest()
    {
        Graph actual = createSimpleGraph();

        actual.addEdge(new Edge("b", "d"), 21);
        actual.deleteEdge("b", "d");

        Graph expected = createSimpleGraph();

        assertEquals(expected, actual);
    }

    @Test
    void deleteNodeTest()
    {
        Graph actual = createSimpleGraph();

        actual.addNode("z");
        actual.addNode("x");
        actual.addEdge("z", "x", 34);
        actual.addEdge("x", "b", 12);
        actual.deleteNode("x");
        actual.deleteNode("z");

        Graph expected = createSimpleGraph();

        assertEquals(expected, actual);
    }

    @Test
    void changeNodeNameTest()
    {
        Graph actual = new Graph();

        actual.addNode("a");
        actual.addNode("b");
        actual.addNode("c");
        actual.addEdge("a", "b", 13);
        actual.addEdge("b", "c", 34);
        actual.changeNodeName("b", "r");

        Graph expected = new Graph();

        expected.addNode("a");
        expected.addNode("r");
        expected.addNode("c");
        expected.addEdge("a", "r", 13);
        expected.addEdge("r", "c", 34);

        assertEquals(expected, actual);

        Executable exec = () -> {
            Graph g = createSimpleGraph();

            g.changeNodeName("j", "f");
        };

        Executable exec1 = () -> {
            Graph g = createSimpleGraph();

            g.changeNodeName("a", "b");
        };

        assertThrows(NoSuchNodeException.class, exec);
        assertThrows(NodeAlreadyExistsException.class, exec1);
    }

    @Test
    void changeEdgeWeightTest()
    {
        HashSet<WeightedEdge> expected = new HashSet<>();

        expected.add(new WeightedEdge(new Edge("a", "b"), 12));

        Graph graph = new Graph();

        graph.addNode("a");
        graph.addNode("b");
        graph.addEdge("a", "b", 344);
        graph.changeEdgeWeight("a", "b", 12);

        assertEquals(expected, graph.getWeightedEdges());

        Executable exec = () -> {
            Graph g = createSimpleGraph();

            g.changeEdgeWeight("t", "d", 56);
        };

        assertThrows(NoSuchEdgeException.class, exec);
    }

    @Test
    void getIncomingEdgesTest() //TODO
    {
        HashSet<WeightedEdge> expected = new HashSet<>();

        expected.add(new WeightedEdge(new Edge("b", "c"), 13));
        expected.add(new WeightedEdge(new Edge("d", "c"), 3));

        HashSet<WeightedEdge> actual = createSimpleGraph().getIncomingEdges("c");

        assertEquals(expected, actual);

        actual = createSimpleGraph().getIncomingEdges(new Node("c"));

        assertEquals(expected, actual);
    }

    @Test
    void getOutgoingEdgesTest()
    {
        HashSet<WeightedEdge> expected = new HashSet<>();

        expected.add(new WeightedEdge(new Edge("b", "a"), 100));
        expected.add(new WeightedEdge(new Edge("b", "c"), 13));

        HashSet<WeightedEdge> actual = createSimpleGraph().getOutgoingEdges("b");

        assertEquals(expected, actual);

        actual = createSimpleGraph().getOutgoingEdges(new Node("b"));

        assertEquals(expected, actual);
    }

    @Test
    void clearTest()
    {
        Graph expected = new Graph();
        Graph actual = createSimpleGraph();

        actual.clear();

        assertEquals(expected, actual);
    }

    @Test
    void negativeWeightExceptionTest()
    {
        Executable exec = () -> {
            Graph graph = new Graph();

            graph.addNode(new Node("a"));
            graph.addNode(new Node("b"));

            graph.addEdge(new Edge("a", "b"), -13);
        };

        assertThrows(NegativeWeightException.class, exec);
    }

    private Graph createSimpleGraph()
    {
        Graph graph = new Graph();

        graph.addNode(new Node("a"));
        graph.addNode(new Node("b"));
        graph.addNode(new Node("c"));
        graph.addNode(new Node("d"));

        graph.addEdge(new Edge("a", "b"), 10);
        graph.addEdge(new Edge("b", "a"), 100);
        graph.addEdge(new Edge("b", "c"), 13);
        graph.addEdge(new Edge("d", "c"), 3);

        return graph;
    }
}
