// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.render;

import xyz.niggfaclient.property.Property;
import xyz.niggfaclient.property.impl.DoubleProperty;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "Camera", description = "Changes ingame camera", cat = Category.RENDER)
public class Camera extends Module
{
    public static DoubleProperty x;
    public static DoubleProperty y;
    public static DoubleProperty z;
    public static DoubleProperty scale;
    public static Property<Boolean> nhc;
    
    static {
        Camera.x = new DoubleProperty("X", 1.5, -2.0, 2.0, 0.025);
        Camera.y = new DoubleProperty("Y", 0.0, -0.9, 1.55, 0.025);
        Camera.z = new DoubleProperty("Z", -1.5, -3.0, -1.5, 0.025);
        Camera.scale = new DoubleProperty("Scale", 1.5, -2.0, 1.5, 0.05);
        Camera.nhc = new Property<Boolean>("No Hurt Camera", false);
    }
}
