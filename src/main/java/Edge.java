import com.sun.istack.internal.NotNull;

class Edge
{
    private Node fromNode;
    private Node toNode;

    private final int HASHCODE_DIVIDER = 3;
    private final int HASHCODE_MULTIPLIER = 13;

    public Edge (@NotNull Node fromNode, @NotNull Node toNode)
    {
        this.fromNode = fromNode;
        this.toNode = toNode;
    }

    public Edge (@NotNull String fromNode, @NotNull String toNode)
    {
        this.fromNode = new Node(fromNode);
        this.toNode = new Node(toNode);
    }

    void setFromNode(@NotNull Node fromNode)
    {
        this.fromNode = fromNode;
    }

    void setToNode(@NotNull Node toNode)
    {
        this.toNode = toNode;
    }

    Node getFromNode()
    {
        return fromNode;
    }

    Node getToNode()
    {
        return toNode;
    }

    @Override
    public String toString()
    {
        return fromNode + " -> " + toNode;
    }

    @Override
    public int hashCode()
    {
        return toNode.hashCode() / HASHCODE_DIVIDER +
                fromNode.hashCode() / HASHCODE_DIVIDER;
    }

    @Override
    public boolean equals(Object o)
    {
        return o instanceof Edge &&
                ((Edge) o).fromNode.equals(fromNode) &&
                ((Edge) o).toNode.equals(toNode);
    }
}