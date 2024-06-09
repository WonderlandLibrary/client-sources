// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.ghost;

import xyz.niggfaclient.property.impl.DoubleProperty;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "Reach", description = "Allows you to attack in longer distance", cat = Category.GHOST)
public class Reach extends Module
{
    public final DoubleProperty range;
    
    public Reach() {
        this.range = new DoubleProperty("Range", 3.2, 3.0, 6.0, 0.05);
    }
}
