// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.gui;

public class Changelog
{
    public String[] description;
    public ChangelogType type;
    
    public Changelog(final String[] description, final ChangelogType type) {
        this.description = description;
        this.type = type;
    }
    
    public String[] getDescription() {
        return this.description;
    }
    
    public ChangelogType getType() {
        return this.type;
    }
}
