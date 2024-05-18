// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.render;

import me.chrest.client.option.Option;
import me.chrest.client.module.Module;

@Mod(displayName = "Name Protect", shown = false)
public class NameProtect extends Module
{
    @Option.Op(name = "Colored")
    private boolean colored;
    
    public NameProtect() {
        this.colored = true;
    }
    
    public boolean getColored() {
        return this.colored;
    }
}
