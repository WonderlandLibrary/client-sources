// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.impl.modules.render;

import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.Module.Mod;
import net.minecraft.client.triton.management.option.Option.Op;

@Mod(displayName = "Name Protect", shown = false)
public class NameProtect extends Module
{
    @Op(name = "Colored")
    private boolean colored;
    
    public NameProtect() {
        this.colored = true;
    }
    
    public boolean getColored() {
        return this.colored;
    }
}
