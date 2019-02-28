package moonlightMoth.graph;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/** Graph class represents directed weighted graph.
 * Graph consists of nodes and edges stored in lists.
 * Each node and edge must be unique i.e.
 * all nodes must have different names and all edges must have
 * different starting node and destination node.
 * Graph can not hold two or more edges with similar starting and ending nodes.
 *
 * Graph class prevents data duplicating i.e.
 * all edges refer only to nodes that belong nodes List
 *
 * @see Node
 * @see Edge*/

public class Graph
{
    /** List of Nodes objects */
    private List<Node> nodes;
    /** List of Edge objects */
    private List<Edge> edges;

    private static final int HASHCODE_DIVIDER = 2;

    /** Constructs empty Graph instance */

    public Graph()
    {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    /** Adds new node with specified name to nodes list
     * @param name name of new node o be added
     * @return true if node is added correctly
     * or false if graph already contains node with similar name
     * @see Node*/

    public boolean addNode(@NotNull String name)
    {
        Node node = new Node(name);

        if (!nodes.contains(node))
        {
            nodes.add(node);
            return true;
        }
        return false;
    }

    /** Adds new edge to edges list.
     * If graph already contains edge linking specified nodes, new edge will not be added.
     * @param fromNode name of node from which edge goes
     * @param toNode name of node to which edge goes
     * @param weight weight of new edge, weight must not be negative
     * @return true if edge added correctly, false if graph already contains edge
     * which links specified nodes
     * @exception NegativeWeightException is thrown when given weight is negative
     * @exception NoSuchNodeException is thrown when graph do not contain nodes with given names
     * @see Edge*/

    public boolean addEdge(@NotNull String fromNode, @NotNull String toNode, int weight)
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

    /** Deletes existing edge
     * @param fromNode name of node from which edge to be deleted goes
     * @param toNode name of node to which edge to be deleted goes
     * @return true if deletion successfully completed or
     * false if specified edge does not exist */

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

    /** Deletes existing node and all edges referring to this node
     * @param name name of node to be deleted
     * @return true if deletion successfully completed or
     * false if specified node does not exist */

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

    /** Changes existing node name.
     * This operation affects on all linked edges as they refer to this node.
     * @param oldName node which name needs to be changed
     * @param newName new name of node
     * @exception NoSuchNodeException is thrown when nodes list does not contain
     * node named oldName
     * @exception NodeAlreadyExistsException is thrown when node named newName
     * already exists before operation*/

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

    /** Changes specified edge weight.
     * Weight must be not negative
     * @param fromNode name of node from which edge goes
     * @param toNode name of node to which edge goes
     * @param newWeight new weight of edge
     * @exception NoSuchEdgeException is thrown if edges list does not contain given edge*/

    public void changeEdgeWeight(@NotNull String fromNode, @NotNull String toNode, int newWeight)
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

    /** Returns a list of edges going into node with specified name.
     * If edges list does not contain any, returns empty list
     * @param nodeName name of node to which incoming edges need to be found
     * @exception NoSuchNodeException is thrown when nodes list does not contain specified node
     * @return list of incoming edges*/

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

    /** Returns a list of edges going out of node with specified name.
     * If edges list does not contain any, returns empty list
     * @param nodeName name of node to which outgoing edges need to be found
     * @exception NoSuchNodeException is thrown when nodes list does not contain specified node
     * @return list of outgoing edges*/

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

    /** Makes graph completely empty */

    public void clear()
    {
        edges.clear();
        nodes.clear();
    }

    /** Delete all edges but keep nodes */

    public void clearEdges()
    {
        edges.clear();
    }

    /** @return a copy of nodes list */

    public List<Node> getNodes()
    {
       return new ArrayList<>(nodes);
    }

    /** @return a copy of edges list */

    public List<Edge> getEdges()
    {
        return new ArrayList<>(edges);
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

    /** Builds a string consisting of all nodes string representation and
     * all edges string representation in readable form.
     * @return graph string representation*/

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

        sb.deleteCharAt(sb.length()-1);

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
                ((Graph) o).edges.containsAll(edges) &&
                ((Graph) o).nodes.containsAll(nodes) &&
                ((Graph) o).edges.size() == edges.size() &&
                ((Graph) o).nodes.size() == nodes.size();
    }
}