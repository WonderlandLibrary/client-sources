// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.render;

import me.chrest.client.option.Option;
import me.chrest.client.module.Module;

@Mod(displayName = "Sword Transitions")
public class SwordTransitions extends Module
{
    @Option.Op(min = -2.0, max = 2.0, increment = 0.1, name = "X-Axis")
    private double x;
    @Option.Op(min = -2.0, max = 2.0, increment = 0.1, name = "Y-Axis")
    private double y;
    @Option.Op(min = -2.0, max = 2.0, increment = 0.1, name = "Z-Axis")
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
