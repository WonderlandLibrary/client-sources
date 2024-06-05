package jaco.mp3.a;

public final class u extends q
{
    private u(final String s, final Throwable t) {
        super(s, t);
    }
    
    public u(int n, final Throwable t) {
        n = n;
        this("Decoder errorcode " + Integer.toHexString(n), t);
    }
}
