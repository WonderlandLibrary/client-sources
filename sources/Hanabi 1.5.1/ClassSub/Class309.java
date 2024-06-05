package ClassSub;

public final class Class309
{
    private String mask;
    private final String username;
    private String password;
    
    
    public Class309(final String s, final String s2) {
        this(s, s2, "");
    }
    
    public Class309(final String username, final String password, final String mask) {
        this.mask = "";
        this.username = username;
        this.password = password;
        this.mask = mask;
    }
    
    public String getMask() {
        return this.mask;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setMask(final String mask) {
        this.mask = mask;
    }
    
    public void setPassword(final String password) {
        this.password = password;
    }
}
