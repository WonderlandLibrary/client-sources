// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.impl.modules.render;

import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.Module.Mod;
import net.minecraft.client.triton.management.option.Option;
import net.minecraft.client.triton.management.option.Option.Op;

@Mod(displayName = "Sword Transitions")
public class SwordTransitions extends Module
{
    @Op(min = -2.0, max = 2.0, increment = 0.1, name = "X-Axis")
    private double x;
    @Op(min = -2.0, max = 2.0, increment = 0.1, name = "Y-Axis")
    private double y;
    @Op(min = -2.0, max = 2.0, increment = 0.1, name = "Z-Axis")
    private double z;
    
    public SwordTransitions() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
    }
    
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public double getZ() {
        return this.z;
    }
}
