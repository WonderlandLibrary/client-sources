// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module;

public enum Category
{
    COMBAT("Combat"), 
    PLAYER("Player"), 
    MOVEMENT("Movement"), 
    RENDER("Render"), 
    EXPLOIT("Exploit"), 
    MISC("Misc"), 
    GHOST("Ghost");
    
    public String name;
    public boolean expanded;
    
    private Category(final String name) {
        this.name = name;
    }
}
