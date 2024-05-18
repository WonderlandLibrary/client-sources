// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.gui.screens.altmanager.slot;

public class Alt
{
    private String user;
    private String pass;
    private boolean premium;
    
    public Alt(final String username, final String password) {
        this.premium = true;
        this.user = username;
        this.pass = password;
    }
    
    public Alt(final String username) {
        this.premium = false;
        this.user = username;
        this.pass = "N/A";
    }
    
    public String getFileLine() {
        if (this.premium) {
            return this.user.concat(":").concat(this.pass);
        }
        return this.user;
    }
    
    public String getUsername() {
        return this.user;
    }
    
    public String getPassword() {
        if (this.premium) {
            return this.pass;
        }
        return "";
    }
    
    public boolean isPremium() {
        return this.premium;
    }
}
