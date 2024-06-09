// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.render;

import xyz.niggfaclient.utils.render.ColorUtil;
import xyz.niggfaclient.property.Property;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "BlockOutline", description = "BlockOutline", cat = Category.RENDER)
public class BlockOutline extends Module
{
    public static Property<Boolean> filled;
    public static Property<Integer> blockOutlineColor;
    
    static {
        BlockOutline.filled = new Property<Boolean>("Filled", true);
        BlockOutline.blockOutlineColor = new Property<Integer>("Color", ColorUtil.RED);
    }
}
