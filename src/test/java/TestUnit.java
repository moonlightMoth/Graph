import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

class TestUnit
{
    @Test
    void addNode()
    {
        Graph actual = new Graph();
        actual.addNode(new Node("sa"));

        HashSet<Node> as = new HashSet<>();
        as.add(new Node("sa"));
        Graph expected = new Graph(as);

        System.out.println(actual);

        assertEquals(expected, actual);
    }

    @Test
    void addEdge()
    {
        Graph graph = new Graph();

        graph.addEdge(new Edge("a", "b", 34));

        HashSet<Edge> edges = new HashSet<>();
        edges.add(new Edge("a", "b", 34));
        Graph graph1 = new Graph(new HashSet<Node>(), edges);

        assertEquals(graph, graph1);
    }

    @Test
    void deleteEdge()
    {
        Graph actual = createSimpleGraph();

        actual.addEdge(new Edge("as", "sa", 21));
        actual.deleteEdge("as", "sa");

        Graph expected = createSimpleGraph();

        assertEquals(expected, actual);
    }

    @Test
    void deleteNode()
    {
        Graph actual = createSimpleGraph();

        actual.addNode(new Node("asa"));
        actual.deleteNode("asa");

        Graph expected = createSimpleGraph();

        assertEquals(expected, actual);
    }

    @Test
    void changeNodeName()  //TODO
    {
        Graph actual = createSimpleGraph();

        actual.changeNodeName("a", "r");

        Graph expected = createSimpleGraph();

        expected.changeNodeName("a", "r");

        HashSet<Node> nodes1 = new HashSet<>();
        nodes1.add(new Node("a"));

        HashSet<Node> nodes2 = new HashSet<>();
        nodes2.add(new Node("r"));

        for (Node node : nodes1)
            node.setName("r");


        System.out.println(nodes2.contains(new Node("r")));

//        Graph expected = new Graph();
//
//        expected.addNode(new Node("r"));
//        expected.addNode(new Node("b"));
//        expected.addNode(new Node("c"));
//        expected.addNode(new Node("d"));
//
//        expected.addEdge(new Edge("r", "b", 10));
//        expected.addEdge(new Edge("b", "r", 100));
//        expected.addEdge(new Edge("b", "c", 13));
//        expected.addEdge(new Edge("d", "c", 3));

        System.out.println(expected.hashCode());
        System.out.println(actual.hashCode());
        System.out.println(actual.equals(expected));
        System.out.println();
        System.out.println(actual.nodes);
        System.out.println(expected.nodes);
        System.out.println(actual.edges);
        System.out.println(actual.edges);
        System.out.println(actual.edges.equals(expected.edges));
        System.out.println(actual.nodes.equals(expected.nodes));
        System.out.println();
        System.out.println(nodes1.equals(nodes2));

        //assertEquals(expected, actual);
    }

    @Test
    void changeEdgeWeight() //TODO
    {
        HashSet<Edge> expected = new HashSet<>();

        expected.add(new Edge("b", "c", 13));
        expected.add(new Edge("d", "c", 112233));

        Graph graph = createSimpleGraph();

        graph.addEdge(new Edge("d", "c", 112233));

        assertEquals(expected, graph.getIncomingEdges("c"));
    }

    @Test
    void getIncomingEdges()
    {
        HashSet<Edge> expected = new HashSet<>();

        expected.add(new Edge("b", "c", 13));
        expected.add(new Edge("d", "c", 3));

        HashSet<Edge> actual = createSimpleGraph().getIncomingEdges("c");

        assertEquals(expected, actual);

        actual = createSimpleGraph().getIncomingEdges(new Node("c"));

        assertEquals(expected, actual);
    }

    @Test
    void getOutgoingEdges()
    {
        HashSet<Edge> expected = new HashSet<>();

        expected.add(new Edge("b", "a", 100));
        expected.add(new Edge("b", "c", 13));

        HashSet<Edge> actual = createSimpleGraph().getOutgoingEdges("b");

        assertEquals(expected, actual);

        actual = createSimpleGraph().getOutgoingEdges(new Node("b"));

        assertEquals(expected, actual);
    }

    @Test
    void clear()
    {
        Graph expected = new Graph();
        Graph actual = createSimpleGraph();

        actual.clear();

        assertEquals(expected, actual);
    }

    @Test
    void negativeWeightException()
    {
        assertThrows(NegativeWeightException.class, new CreateGraphNegativeWeight());
    }

    private Graph createSimpleGraph()
    {
        Graph graph = new Graph();

        graph.addNode(new Node("a"));
        graph.addNode(new Node("b"));
        graph.addNode(new Node("c"));
        graph.addNode(new Node("d"));

        graph.addEdge(new Edge("a", "b", 10));
        graph.addEdge(new Edge("b", "a", 100));
        graph.addEdge(new Edge("b", "c", 13));
        graph.addEdge(new Edge("d", "c", 3));

        return graph;
    }

    private class CreateGraphNegativeWeight implements Executable
    {
        @Override
        public void execute() throws Throwable
        {
            Graph graph = new Graph();

            graph.addNode(new Node("a"));
            graph.addNode(new Node("b"));

            graph.addEdge(new Edge("a", "b", -13));
        }
    }

}
