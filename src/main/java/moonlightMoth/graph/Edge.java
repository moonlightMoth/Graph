package moonlightMoth.graph;

import com.sun.istack.internal.NotNull;

/** Edge class represents connection between two Nodes
 * as weighted directed edge */

public class Edge
{
    /** Node from which edge goes */
    private Node fromNode = null;
    /** Node to which edge goes */
    private Node toNode = null;
    /** Edge weight */
    private int weight;

    private static final int HASHCODE_DIVIDER = 3;
    private static final int HASHCODE_MULTIPLIER = 13;

    /** Creates new Edge implementation. Initialises fromNode, toNode and weight
     * @param fromNode Node from which edge goes
     * @param toNode Node to which edge goes
     * @param weight Edge weight, must be not negative
     * @exception NegativeWeightException Is thrown when param weight is negative*/

    public Edge (@NotNull String fromNode, @NotNull String toNode, @NotNull int weight)
    {
        if (weight < 0)
            throw new NegativeWeightException();

        this.weight = weight;
        this.fromNode = new Node(fromNode);
        this.toNode = new Node(toNode);
    }

    Edge()
    {

    }

    void setFromNode(@NotNull Node fromNode)
    {
        this.fromNode = fromNode;
    }

    void setToNode(@NotNull Node toNode)
    {
        this.toNode = toNode;
    }

    void setWeight(int weight)
    {
        if (weight < 0)
            throw new NegativeWeightException();

        this.weight = weight;
    }

    /** Returns Node from which edge goes */

    public Node getFromNode()
    {
        return fromNode;
    }

    /** Returns Node to which edge goes */

    public Node getToNode()
    {
        return toNode;
    }

    /** Returns Edge weight */

    public int getWeight()
    {
        return weight;
    }

    /** Returns a string representation of edge.
     * The string consists of fromNode name, toNode name and edge weight
     * in following format: "fromNode -> toNode (weight)"
     * @return string representation of Edge*/

    @Override
    public String toString()
    {
        return fromNode + " -> " + toNode + " (" + weight + ")";
    }

    @Override
    public int hashCode()
    {
        return toNode.hashCode() / HASHCODE_DIVIDER +
                fromNode.hashCode() / HASHCODE_DIVIDER +
                weight * HASHCODE_MULTIPLIER;
    }

    @Override
    public boolean equals(Object o)
    {
        return o instanceof Edge &&
                ((Edge) o).fromNode.equals(fromNode) &&
                ((Edge) o).toNode.equals(toNode) &&
                ((Edge) o).weight == weight;
    }
}