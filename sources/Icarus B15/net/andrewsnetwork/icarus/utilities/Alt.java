// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.utilities;

public class Alt
{
    private String mask;
    private final String username;
    private final String password;
    
    public Alt(final String username, final String password) {
        this(username, password, "");
    }
    
    public Alt(final String username, final String password, final String mask) {
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
}
