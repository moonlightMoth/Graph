import com.sun.istack.internal.NotNull;
import java.util.*;

public class Graph
{
    private List<Node> nodes;
    private List<Edge> edges;

    private final int HASHCODE_DIVIDER = 2;

    public Graph()
    {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public boolean addNode(@NotNull Node node)
    {
        if (!nodes.contains(node))
        {
            nodes.add(node);
            return true;
        }
        return false;
    }

    public boolean addNode(@NotNull String name)
    {
        return addNode(new Node(name));
    }

    public boolean addEdge(@NotNull String fromNode, @NotNull String toNode, @NotNull int weight)
    {
        Edge edge = new Edge();

        for (Node node : nodes)
        {
            if (node.getName().equals(fromNode))
                edge.setFromNode(node);
            if (node.getName().equals(toNode))
                edge.setToNode(node);
        }

        if (edge.getFromNode() == null || edge.getToNode() == null)
            throw new NoSuchNodeException();

        for (Edge e : edges)
        {
            if (e.getToNode().equals(edge.getToNode()) &&
                    e.getFromNode().equals(edge.getFromNode()))
                return false;
        }

        edge.setWeight(weight);
        edges.add(edge);

        return true;
    }

    public boolean deleteEdge(@NotNull String fromNode, @NotNull String toNode)
    {
        for (int i = 0; i < edges.size(); i++)
        {
            if (edges.get(i).getFromNode().getName().equals(fromNode) &&
                    edges.get(i).getToNode().getName().equals(toNode))
            {
                edges.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean deleteNode(@NotNull String name)
    {
        for (int i = 0; i < nodes.size(); i++)
        {
            if (nodes.get(i).getName().equals(name))
            {
                deleteAllLinkedEdges(nodes.get(i));
                nodes.remove(i);
                return true;
            }
        }
        return false;
    }


    public void changeNodeName(@NotNull String oldName, @NotNull String newName)
    {
        for (Node node : nodes)
        {
            if (node.getName().equals(newName))
                throw new NodeAlreadyExistsException();
        }

        if (!checkNodeExist(oldName))
            throw new NoSuchNodeException();

        for (Node node : nodes)
        {
            if (node.getName().equals(oldName))
            {
                node.setName(newName);
                break;
            }
        }
    }

    public void changeEdgeWeight(@NotNull String fromNode, @NotNull String toNode, @NotNull int newWeight)
    {
        for (Edge edge : edges)
        {
            if (edge.getToNode().getName().equals(toNode) &&
                    edge.getFromNode().getName().equals(fromNode))
            {
                edge.setWeight(newWeight);
                return;
            }
        }

        throw new NoSuchEdgeException();
    }

    public List<Edge> getIncomingEdges(@NotNull String nodeName)
    {
        if (!checkNodeExist(nodeName))
            throw new NoSuchNodeException();

        ArrayList<Edge> as = new ArrayList<>();

        for (Edge edge : edges)
        {
            if (edge.getToNode().getName().equals(nodeName))
                as.add(edge);
        }

        return as;
    }

    public List<Edge> getOutgoingEdges(@NotNull String nodeName)
    {
        if (!checkNodeExist(nodeName))
            throw new NoSuchNodeException();

        ArrayList<Edge> as = new ArrayList<>();

        for (Edge edge : edges)
        {
            if (edge.getFromNode().getName().equals(nodeName))
                as.add(edge);
        }

        return as;
    }

    public void clear()
    {
        edges.clear();
        nodes.clear();
    }

    public void clearEdges()
    {
        edges.clear();
    }

    public List<Node> getNodes()
    {
       return nodes;
    }

    public List<Edge> getEdges()
    {
        return edges;
    }

    private boolean checkNodeExist(@NotNull String nodeName)
    {
        for (Node node : nodes)
        {
            if (node.getName().equals(nodeName))
                return true;
        }

        return false;
    }

    private void deleteAllLinkedEdges(Node node)
    {
        ArrayList<Edge> edgesToRemove = new ArrayList<>();

        for (Edge edge : edges)
        {
            if (edge.getToNode().equals(node) || edge.getFromNode().equals(node))
                edgesToRemove.add(edge);
        }

        edges.removeAll(edgesToRemove);
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
            sb.append(edge);
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