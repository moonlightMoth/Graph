package moonlightMoth.graph;

import com.sun.istack.internal.NotNull;

/** Node class represents node of graph */

public class Node
{
    /** Name of node*/
    private String name;

    private static final int HASHCODE_MULTIPLIER = 3;

    /** Creates new node
     * @param name name of node*/

    public Node(@NotNull String name)
    {
        this.name = name;
    }

    /** Returns string which consists of node name*/

    public String getName()
    {
        return name;
    }

    void setName(@NotNull String name)
    {
        this.name = name;
    }

    /** Similar to getName()*/

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
