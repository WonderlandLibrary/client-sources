// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.movement;

import java.util.Iterator;
import xyz.niggfaclient.utils.render.RenderUtils;
import java.awt.Color;
import xyz.niggfaclient.module.impl.render.HUD;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import xyz.niggfaclient.module.ModuleManager;
import xyz.niggfaclient.module.impl.combat.KillAura;
import xyz.niggfaclient.events.impl.Render3DEvent;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.MotionEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.property.impl.DoubleProperty;
import xyz.niggfaclient.property.Property;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "TargetStrafe", description = "Strafes around your target", cat = Category.MOVEMENT)
public class TargetStrafe extends Module
{
    public static Property<Boolean> holdSpace;
    private final DoubleProperty points;
    private final Property<Boolean> onTarget;
    public static DoubleProperty range;
    private final Property<Boolean> controllable;
    private final Property<Boolean> render;
    public byte strafeDirection;
    @EventLink
    private final Listener<MotionEvent> motionEventListener;
    @EventLink
    private final Listener<Render3DEvent> render3DEventListener;
    
    public TargetStrafe() {
        this.points = new DoubleProperty("Points", 90.0, 3.0, 100.0, 1.0);
        this.onTarget = new Property<Boolean>("On Target", false);
        this.controllable = new Property<Boolean>("Controllable", true);
        this.render = new Property<Boolean>("Render", true);
        this.motionEventListener = (e -> {
            this.setDisplayName("Target Strafe");
            if (this.mc.thePlayer.isCollidedHorizontally) {
                this.strafeDirection = (byte)(-this.strafeDirection);
            }
            else if (this.controllable.getValue()) {
                if (this.mc.gameSettings.keyBindLeft.isKeyDown()) {
                    this.strafeDirection = 1;
                }
                if (this.mc.gameSettings.keyBindRight.isKeyDown()) {
                    this.strafeDirection = -1;
                }
            }
            return;
        });
        final KillAura killAura;
        final Iterator<Entity> iterator;
        Entity entity;
        this.render3DEventListener = (e -> {
            killAura = ModuleManager.getModule(KillAura.class);
            this.mc.theWorld.getLoadedEntityList().iterator();
            while (iterator.hasNext()) {
                entity = iterator.next();
                if (this.render.getValue() && this.onTarget.getValue() && entity instanceof EntityPlayer && entity != this.mc.thePlayer && killAura.target == entity && killAura.isEnabled() && !killAura.targets.isEmpty()) {
                    RenderUtils.drawLinesAroundPlayer(entity, TargetStrafe.range.getValue(), this.points.getValue().intValue(), new Color(HUD.hudColor.getValue()));
                }
                else if (!this.onTarget.getValue() && this.render.getValue()) {
                    RenderUtils.drawLinesAroundPlayer(this.mc.thePlayer, TargetStrafe.range.getValue(), this.points.getValue().intValue(), new Color(HUD.hudColor.getValue()));
                }
                else {
                    continue;
                }
            }
        });
    }
    
    static {
        TargetStrafe.holdSpace = new Property<Boolean>("On Space", true);
        TargetStrafe.range = new DoubleProperty("Range", 2.0, 0.1, 6.0, 0.1);
    }
}
