import java.util.HashSet;

public class Graph
{
    public HashSet<Node> nodes;
    public HashSet<Edge> edges;

    private final int HASHCODE_DIVIDER = 2;

    Graph()
    {
        nodes = new HashSet<>();
        edges = new HashSet<>();
    }

    Graph(HashSet<Node> nodes, HashSet<Edge> edges)
    {
        this.nodes = nodes;
        this.edges = edges;
    }

    Graph(HashSet<Node> nodes)
    {
        this.nodes = nodes;
        edges = new HashSet<>();
    }

    public void addNode(Node node)
    {
        nodes.add(node);
    }

    public void addEdge(Edge edge)
    {
        edges.add(edge);
    }

    public void deleteEdge(String fromNode, String toNode)
    {
        for (Edge edge: edges)
        {
            if (edge.getFromNode().equals(fromNode) && edge.getToNode().equals(toNode))
            {
                edges.remove(edge);
                break;
            }
        }
    }

    public void deleteNode(String name)
    {
        for (Node node : nodes)
        {
            if (node.getName().equals(name))
            {
                nodes.remove(node);
                break;
            }
        }
    }

    public void changeNodeName(String oldName, String newName)
    {
        for (Node node : nodes)
        {
            if (node.getName().equals(oldName))
                node.setName(newName);
        }

        changeNodeNameInEdges(oldName, newName);
    }

    public void changeEdgeWeight(String fromNode, String toNode, int newWeight)
    {
        for (Edge edge: edges)
        {
            if (edge.getFromNode().equals(fromNode) && edge.getToNode().equals(toNode))
                edge.setWeight(newWeight);
        }
    }

    public void changeEdgeWeight(Edge e, int newWeight)
    {
        for (Edge edge: edges)
        {
            if (edge.getFromNode().equals(e.getFromNode()) &&
                    edge.getToNode().equals(e.getToNode()))
                edge.setWeight(newWeight);
        }
    }

    public HashSet<Edge> getIncomingEdges(String nodeName)
    {
        if (!nodes.contains(new Node(nodeName)))
            throw new NoSuchNodeException();

        HashSet<Edge> incomingEdges = new HashSet<>();

        for (Edge edge: edges)
        {
            if (edge.getToNode().equals(nodeName))
                incomingEdges.add(edge);
        }

        return incomingEdges;
    }

    public HashSet<Edge> getIncomingEdges(Node node)
    {
        if (!nodes.contains(node))
            throw new NoSuchNodeException();

        HashSet<Edge> incomingEdges = new HashSet<>();

        for (Edge edge: edges)
        {
            if (edge.getToNode().equals(node.getName()))
                incomingEdges.add(edge);
        }

        return incomingEdges;
    }

    public HashSet<Edge> getOutgoingEdges(String nodeName)
    {
        if (!nodes.contains(new Node(nodeName)))
            throw new NoSuchNodeException();

        HashSet<Edge> outgoingEdges = new HashSet<>();

        for (Edge edge: edges)
        {
            if (edge.getFromNode().equals(nodeName))
                outgoingEdges.add(edge);
        }

        return outgoingEdges;
    }

    public HashSet<Edge> getOutgoingEdges(Node node)
    {
        if (!nodes.contains(node))
            throw new NoSuchNodeException();

        HashSet<Edge> outgoingEdges = new HashSet<>();

        for (Edge edge: edges)
        {
            if (edge.getFromNode().equals(node.getName()))
                outgoingEdges.add(edge);
        }

        return outgoingEdges;
    }

    public void clear()
    {
        edges.clear();
        nodes.clear();
    }

    private void changeNodeNameInEdges(String oldName, String newName)
    {
        for (Edge edge: edges)
        {
            if (edge.getFromNode().equals(oldName))
                edge.setFromNode(newName);

            if (edge.getToNode().equals(oldName))
                edge.setToNode(newName);
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

        for (Edge edge : edges)
        {
            sb.append("\t");
            sb.append(edge.toString());
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
