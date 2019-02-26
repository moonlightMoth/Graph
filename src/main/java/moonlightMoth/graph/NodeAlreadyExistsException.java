package moonlightMoth.graph;

public class NodeAlreadyExistsException extends IllegalArgumentException
{
    @Override
    public String getMessage()
    {
        return "Node already exists";
    }
}