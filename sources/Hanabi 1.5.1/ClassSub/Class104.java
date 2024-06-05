package ClassSub;

public class Class104 extends Class249
{
    public static final String PACKET_ID = "ONLINEUSER";
    public final Class59 client;
    public final Class194 user;
    public final String inGamename;
    public final boolean isClientFriend;
    
    
    public Class104(final Class59 client, final Class194 user, final String inGamename, final boolean isClientFriend) {
        super("ONLINEUSER::" + client.getClientName() + "::" + user.getUsername() + "::" + inGamename + "::" + isClientFriend, Class298.RECIEVE);
        this.client = client;
        this.user = user;
        this.inGamename = inGamename;
        this.isClientFriend = isClientFriend;
    }
    
    public Class104(final String s) {
        this(Class294.getTypeByName(s.split("::")[1]), Class194.getIRCUserByNameAndType(Class294.getTypeByName(s.split("::")[1]), s.split("::")[2]), s.split("::")[3], s.split("::")[4].equals("true"));
        this.user.setInGamename(this.inGamename);
        this.user.setIsClientFriend(this.isClientFriend);
    }
}
