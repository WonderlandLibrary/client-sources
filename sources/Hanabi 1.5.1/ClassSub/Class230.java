package ClassSub;

public class Class230 extends Class59
{
    
    
    public Class230() {
        super("Debug");
    }
    
    @Override
    public boolean isLoginSuccessfully(final String s, final String s2) {
        return Class30.doGet("http://hanabi.alphaantileak.cn:893/debug/StaffMenu/IRCLoginFUCKYOU.php?user=" + s + "&pass=" + s2).contains("true");
    }
    
    @Override
    public String getPrefix(final String s) {
        return Class30.doGet("http://hanabi.alphaantileak.cn:893/debug/StaffMenu/getprefix.php?user=" + s);
    }
}
