package ClassSub;

public class Class199 extends Class249
{
    public static final String PACKET_ID = "CHAT";
    public final Class194 user;
    public final String chatMessage;
    public final Class59 client;
    
    
    public Class199(final Class59 client, final Class194 user, final String chatMessage) {
        super("CHAT::" + client.getClientName() + "::" + user.getUsername() + "::" + chatMessage, Class298.SEND);
        this.user = user;
        this.chatMessage = chatMessage;
        this.client = client;
    }
    
    public Class199(final String s) {
        this(Class294.getTypeByName(s.split("::")[1]), Class194.getIRCUserByNameAndType(Class294.getTypeByName(s.split("::")[1]), s.split("::")[2]), s.split("::")[3]);
    }
}
