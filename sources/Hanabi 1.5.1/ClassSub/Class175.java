package ClassSub;

public class Class175 extends Class249
{
    public static final String PACKET_ID = "CLEARUSER";
    public final Class194 user;
    public final Class59 client;
    
    
    public Class175(final Class59 client, final Class194 user) {
        super("CLEARUSER::" + client.getClientName() + "::" + user.getUsername(), Class298.RECIEVE);
        this.client = client;
        this.user = user;
    }
    
    public Class175(final String s) {
        this(Class294.getTypeByName(s.split("::")[1]), Class194.getIRCUserByNameAndType(Class294.getTypeByName(s.split("::")[1]), s.split("::")[2]));
    }
}
