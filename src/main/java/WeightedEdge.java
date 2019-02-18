import com.sun.istack.internal.NotNull;

public class WeightedEdge
{
    private Edge edge;
    private int weight;

    public WeightedEdge(@NotNull Edge edge, @NotNull int weight)
    {
        if (weight < 0)
            throw new NegativeWeightException();

        this.edge = edge;
        this.weight = weight;
    }

    public Edge getEdge()
    {
        return edge;
    }

    public int getWeight()
    {
        return weight;
    }

    public void setEdge(Edge edge)
    {
        this.edge = edge;
    }

    public void setWeight(int weight)
    {
        if (weight < 0)
            throw new NegativeWeightException();

        this.weight = weight;
    }

    @Override
    public int hashCode()
    {
        return (edge.hashCode() * 34) / (weight * 31);
    }

    @Override
    public boolean equals(Object o)
    {
        return o instanceof WeightedEdge &&
                ((WeightedEdge) o).edge.equals(edge) &&
                ((WeightedEdge) o).weight == weight;
    }
}
