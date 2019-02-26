package moonlightMoth.graph;

import java.util.NoSuchElementException;

public class NoSuchNodeException extends NoSuchElementException
{
    @Override
    public String getMessage()
    {
        return "Node not exist in this graph";
    }
}
