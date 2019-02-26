package moonlightMoth.graph;

import java.util.NoSuchElementException;

public class NoSuchEdgeException extends NoSuchElementException
{
    @Override
    public String getMessage()
    {
        return "Edge not exist in this graph";
    }
}
