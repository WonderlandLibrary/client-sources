// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.friend;

public class Friends
{
    private String name;
    private String alias;
    
    public Friends(final String name, final String alias) {
        this.name = name;
        this.alias = alias;
    }
    
    public Friends(final String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getAlias() {
        return this.alias;
    }
}
