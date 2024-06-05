package ClassSub;

public class Class201 extends Class59
{
    
    
    public Class201() {
        super("AzureWare");
    }
    
    @Override
    public boolean isLoginSuccessfully(final String s, final String s2) {
        return Class30.doGet("http://hanabi.alphaantileak.cn:893/azureware/FUCKYOU/IRCLoginFUCKYOU.php?user=" + s + "&pass=" + s2).contains("true");
    }
    
    @Override
    public String getPrefix(final String s) {
        return Class30.doGet("http://hanabi.alphaantileak.cn:893/azureware/FUCKYOU/getprefix.php?user=" + s);
    }
}
