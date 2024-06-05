package ClassSub;

public class Class59
{
    String name;
    
    
    public Class59(final String name) {
        this.name = name;
    }
    
    public String getClientName() {
        return this.name;
    }
    
    public boolean isLoginSuccessfully(final String s, final String s2) {
        return false;
    }
    
    public String getPrefix(final String s) {
        return null;
    }
    
    public boolean isStaff(final String s) {
        return Class30.doGet("http://hanabi.alphaantileak.cn:893/Staff.txt").contains("|" + s + "|");
    }
}
