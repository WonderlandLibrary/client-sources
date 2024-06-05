package ClassSub;

public class Class347 extends Class249
{
    public static final String PACKET_ID = "HB";
    public final boolean needIGN;
    
    
    public Class347(final boolean needIGN) {
        super("HB::" + needIGN, Class298.RECIEVE);
        this.needIGN = needIGN;
    }
    
    public Class347(final String s) {
        this(s.contains("true"));
    }
}
