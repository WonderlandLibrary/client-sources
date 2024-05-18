// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.handlers.friend;

public class Friend
{
    private String username;
    private String alias;
    
    public Friend(final String username, final String alias) {
        this.username = username;
        this.alias = alias;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
    
    public void setAlias(final String alias) {
        this.alias = alias;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public String getAlias() {
        return this.alias;
    }
}
