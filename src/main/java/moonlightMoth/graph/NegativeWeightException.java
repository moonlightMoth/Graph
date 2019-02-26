package moonlightMoth.graph;

public class NegativeWeightException extends IllegalArgumentException
{
    @Override
    public String getMessage()
    {
        return "Edge weight can not be negative";
    }
}