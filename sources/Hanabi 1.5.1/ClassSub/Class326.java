package ClassSub;

public class Class326 extends Class249
{
    public static final String PACKET_ID = "LOGIN";
    public final String username;
    public final String password;
    public final Class59 client;
    
    
    public Class326(final Class59 client, final String username, final String password) {
        super("LOGIN::" + username + "::" + password + "::" + client.getClientName(), Class298.SEND);
        this.username = username;
        this.password = password;
        this.client = client;
    }
    
    public Class326(final String s) {
        this(Class294.getTypeByName(s.split("::")[3]), s.split("::")[1], s.split("::")[2]);
    }
}
