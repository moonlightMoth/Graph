import com.sun.istack.internal.NotNull;

import java.util.*;

public class Graph
{
    private HashSet<Node> nodes;
    private HashMap<Edge, Integer> edges;

    private final int HASHCODE_DIVIDER = 2;

    public Graph()
    {
        nodes = new HashSet<>();
        edges = new HashMap<>();
    }

    public Graph(@NotNull HashSet<Node> nodes, @NotNull HashMap<Edge, Integer> edges)
    {
        this.nodes = nodes;
        this.edges = edges;
    }

    public Graph(@NotNull HashSet<Node> nodes, @NotNull HashSet<WeightedEdge> weightedEdges)
    {
        this.nodes = nodes;

        for (WeightedEdge we : weightedEdges)
        {
            edges.put(we.getEdge(), we.getWeight());
        }
    }

    public Graph(@NotNull HashSet<Node> nodes)
    {
        this.nodes = nodes;
        edges = new HashMap<>();
    }

    public void addNode(@NotNull Node node)
    {
        nodes.add(node);
    }

    public void addNode(@NotNull String name)
    {
        nodes.add(new Node(name));
    }

    public void addEdge(@NotNull Edge edge, @NotNull int weight)
    {
        if (weight < 0)
            throw new NegativeWeightException();

        if (!(nodes.contains(edge.getToNode()) && nodes.contains(edge.getFromNode())))
            throw new NoSuchNodeException();

        edges.putIfAbsent(edge, weight);
    }

    public void addEdge(@NotNull String fromNode, @NotNull String toNode, @NotNull int weight)
    {
        Edge edge = new Edge(fromNode, toNode);

        addEdge(edge, weight);
    }

    public void deleteEdge(@NotNull String fromNode, @NotNull String toNode)
    {
        edges.remove(new Edge(new Node(fromNode), new Node(toNode)));
    }

    public void deleteEdge(@NotNull Node fromNode, @NotNull Node toNode)
    {
        edges.remove(new Edge(fromNode, toNode));
    }

    public void deleteEdge(@NotNull Edge edge)
    {
        edges.remove(edge);
    }

    public void deleteNode(@NotNull String node)
    {
        nodes.remove(new Node(node));
        deleteAllLinkedEdges(new Node(node));
    }

    public void deleteNode(@NotNull Node node)
    {
        nodes.remove(node);
        deleteAllLinkedEdges(node);
    }

    public void changeNodeName(@NotNull String oldName, @NotNull String newName)
    {
        if (nodes.contains(new Node(newName)))
            throw new NodeAlreadyExistsException();

        if (!nodes.contains(new Node(oldName)))
            throw new NoSuchNodeException();

        nodes.remove(new Node(oldName));
        nodes.add(new Node(newName));

        changeNodeNameInEdges(oldName, newName);
    }

    public void changeEdgeWeight(@NotNull String fromNode, @NotNull String toNode, @NotNull int newWeight)
    {
        changeEdgeWeight(new Edge(new Node(fromNode), new Node(toNode)), newWeight);
    }

    public void changeEdgeWeight(@NotNull Edge edge, @NotNull int newWeight)
    {
        if (!edges.containsKey(edge))
            throw new NoSuchEdgeException();

        edges.put(edge, newWeight);
    }

    public HashSet<WeightedEdge> getIncomingEdges(@NotNull String nodeName)
    {
        return getIncomingEdges(new Node(nodeName));
    }

    public HashSet<WeightedEdge> getIncomingEdges(@NotNull Node node)
    {
        if (!nodes.contains(node))
            throw new NoSuchNodeException();

        HashSet<WeightedEdge> incomingEdges = new HashSet<>();

        for (Map.Entry<Edge, Integer> e : edges.entrySet())
        {
            if (e.getKey().getToNode().equals(node))
                incomingEdges.add(new WeightedEdge(e.getKey(), e.getValue()));
        }

        return incomingEdges;
    }

    public HashSet<WeightedEdge> getOutgoingEdges(@NotNull String nodeName)
    {
        return getOutgoingEdges(new Node(nodeName));
    }

    public HashSet<WeightedEdge> getOutgoingEdges(@NotNull Node node)
    {
        if (!nodes.contains(node))
            throw new NoSuchNodeException();

        HashSet<WeightedEdge> outgoingEdges = new HashSet<>();

        for (Map.Entry<Edge, Integer> e : edges.entrySet())
        {
            if (e.getKey().getFromNode().equals(node))
                outgoingEdges.add(new WeightedEdge(e.getKey(), e.getValue()));
        }

        return outgoingEdges;
    }

    public void clear()
    {
        edges.clear();
        nodes.clear();
    }

    public HashSet<Node> getNodes()
    {
       return nodes;
    }

    public Set<Edge> getEdges()
    {
        return edges.keySet();
    }

    public Set<WeightedEdge> getWeightedEdges()
    {
        HashSet<WeightedEdge> weightedEdges = new HashSet<>();

        for (Edge edge : edges.keySet())
        {
            weightedEdges.add(new WeightedEdge(edge, edges.get(edge)));
        }

        return weightedEdges;
    }

    private void deleteAllLinkedEdges(Node node)
    {
        ArrayList<Edge> edgesToRemove = new ArrayList<>();

        for (Edge edge : edges.keySet())
        {
            if (edge.getToNode().equals(node) || edge.getFromNode().equals(node))
                edgesToRemove.add(edge);
        }

        for (Edge edge : edgesToRemove)
        {
            edges.remove(edge);
        }
    }

    private void changeNodeNameInEdges(String oldName, String newName)
    {
        ArrayList<WeightedEdge> edgesToChange = new ArrayList<>();

        for (Edge edge : edges.keySet())
        {
            if (edge.getToNode().getName().equals(oldName) ||
                    edge.getFromNode().getName().equals(oldName))
                edgesToChange.add(new WeightedEdge(edge, edges.get(edge)));
        }

        for (WeightedEdge wEdge : edgesToChange)
        {
            edges.remove(wEdge.getEdge());

            if (wEdge.getEdge().getFromNode().getName().equals(oldName))
                wEdge.getEdge().setFromNode(new Node(newName));

            if (wEdge.getEdge().getToNode().getName().equals(oldName))
                wEdge.getEdge().setToNode(new Node(newName));

            edges.put(wEdge.getEdge(), wEdge.getWeight());
        }


    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("Nodes: ");

        for (Node node : nodes)
        {
            sb.append(node.toString());
            sb.append(", ");
        }

        sb.delete(sb.length() - 2, sb.length());
        sb.append("\n");
        sb.append("Edges: ");
        sb.append("\n");

        for (Map.Entry<Edge, Integer> e : edges.entrySet())
        {
            sb.append("\t");
            sb.append(e.getKey().toString());
            sb.append(" (");
            sb.append(e.getValue());
            sb.append(")");
            sb.append("\n");
        }

        return sb.toString();
    }

    @Override
    public int hashCode()
    {
        return nodes.hashCode() / HASHCODE_DIVIDER +
                edges.hashCode() / HASHCODE_DIVIDER;
    }

    @Override
    public boolean equals(Object o)
    {
        return o instanceof Graph &&
                ((Graph) o).edges.equals(edges) &&
                ((Graph) o).nodes.equals(nodes);
    }
}