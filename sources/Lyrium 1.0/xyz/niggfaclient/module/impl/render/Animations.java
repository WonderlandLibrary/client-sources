// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.render;

import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.UpdateEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.property.impl.DoubleProperty;
import xyz.niggfaclient.property.impl.EnumProperty;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "Animations", description = "Changes sword animation", cat = Category.RENDER)
public class Animations extends Module
{
    public static EnumProperty<AnimationMode> mode;
    public static DoubleProperty sSpeed;
    public static DoubleProperty spinSpeed;
    @EventLink
    private final Listener<UpdateEvent> updateEventListener;
    
    public Animations() {
        this.updateEventListener = (e -> this.setDisplayName(this.getName() + " §7" + Animations.mode.getValue()));
    }
    
    static {
        Animations.mode = new EnumProperty<AnimationMode>("Animation Mode", AnimationMode.OnePointSeven);
        Animations.sSpeed = new DoubleProperty("Swing Speed", 6.0, 0.0, 16.0, 1.0);
        Animations.spinSpeed = new DoubleProperty("Spin Speed", 6.0, 0.0, 16.0, 1.0);
    }
    
    public enum AnimationMode
    {
        OnePointSeven, 
        Old, 
        Slide, 
        Plain, 
        Sigma, 
        Remix, 
        Swank, 
        Swang, 
        Stella, 
        Astolfo, 
        Ethereal, 
        Exhibition, 
        Exhibition2;
    }
}
