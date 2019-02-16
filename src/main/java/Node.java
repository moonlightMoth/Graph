class Node
{
    private String name;

    private final int HASHCODE_MULTIPLIER = 3;

    Node(String name)
    {
        this.name = name;
    }

    String getName()
    {
        return name;
    }

    void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return name;
    }

    @Override
    public int hashCode()
    {
        return name.hashCode() * HASHCODE_MULTIPLIER;
    }

    @Override
    public boolean equals(Object o)
    {
        return o instanceof Node && ((Node) o).getName().equals(name);
    }
}
