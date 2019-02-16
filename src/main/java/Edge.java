import com.sun.org.apache.xpath.internal.operations.Neg;

class Edge
{
    private String fromNode;
    private String toNode;
    private int weight;

    private final int HASHCODE_DIVIDER = 3;
    private final int HASHCODE_MULTIPLIER = 13;

    Edge (String fromNode, String toNode, int weight)
    {
        if (weight < 0)
            throw new NegativeWeightException();

        this.fromNode = fromNode;
        this.toNode = toNode;
        this.weight = weight;
    }

    void setFromNode(String fromNode)
    {
        this.fromNode = fromNode;
    }

    void setToNode(String toNode)
    {
        this.toNode = toNode;
    }

    String getFromNode()
    {
        return fromNode;
    }

    String getToNode()
    {
        return toNode;
    }

    void setWeight(int weight)
    {
        if (weight < 0)
            throw new NegativeWeightException();

        this.weight = weight;
    }

    int getWeight()
    {
        return weight;
    }

    @Override
    public String toString()
    {
        return fromNode + " -> " + toNode + " = " + weight;
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
                ((Edge) o).toNode.equals(toNode);
    }
}