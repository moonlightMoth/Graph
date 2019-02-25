import com.sun.istack.internal.NotNull;

class Edge
{
    private Node fromNode = null;
    private Node toNode = null;
    private int weight;

    private final int HASHCODE_DIVIDER = 3;
    private final int HASHCODE_MULTIPLIER = 13;

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

    void setWeight(@NotNull int weight)
    {
        if (weight < 0)
            throw new NegativeWeightException();

        this.weight = weight;
    }

    public Node getFromNode()
    {
        return fromNode;
    }

    public Node getToNode()
    {
        return toNode;
    }

    public int getWeight()
    {
        return weight;
    }

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