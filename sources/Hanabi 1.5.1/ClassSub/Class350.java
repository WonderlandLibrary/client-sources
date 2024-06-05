package ClassSub;

public class Class350 extends Class59
{
    
    
    public Class350() {
        super("Hanabi");
    }
    
    @Override
    public boolean isLoginSuccessfully(final String s, final String s2) {
        return Class30.doGet("http://hanabi.alphaantileak.cn:893/hanabi/FUCKYOU/IRCLoginFUCKYOU.php?user=" + s + "&pass=" + s2).contains("true");
    }
    
    @Override
    public String getPrefix(final String s) {
        return Class30.doGet("http://hanabi.alphaantileak.cn:893/hanabi/FUCKYOU/getprefix.php?user=" + s);
    }
}
