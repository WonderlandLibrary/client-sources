package wwe;

public class ZqPV extends Exception
{
    public ZqPV() {
        super("AccountManagementException");
    }
    
    public ZqPV(final String s) {
        super(s);
    }
    
    public ZqPV(final String s, final Throwable t) {
        super(s, t);
    }
}
