// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.movement;

import net.minecraft.network.Packet;
import xyz.niggfaclient.utils.network.PacketUtil;
import net.minecraft.network.play.client.C03PacketPlayer;
import xyz.niggfaclient.utils.player.MoveUtils;
import xyz.niggfaclient.module.impl.player.Scaffold;
import xyz.niggfaclient.module.ModuleManager;
import xyz.niggfaclient.events.impl.StepConfirmEvent;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.UpdateEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.property.impl.DoubleProperty;
import xyz.niggfaclient.property.impl.EnumProperty;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "Step", description = "Allows you step higher", cat = Category.MOVEMENT)
public class Step extends Module
{
    private final EnumProperty<Mode> mode;
    private final DoubleProperty height;
    private final DoubleProperty timer;
    private long lastStep;
    private boolean stepping;
    @EventLink
    private final Listener<UpdateEvent> updateEventListener;
    @EventLink
    private final Listener<StepConfirmEvent> stepConfirmEventListener;
    
    public Step() {
        this.mode = new EnumProperty<Mode>("Mode", Mode.Motion);
        this.height = new DoubleProperty("Height", 1.0, 1.0, 5.0, 0.1);
        this.timer = new DoubleProperty("Timer", 0.3, 0.1, 1.0, 0.1);
        this.updateEventListener = (e -> {
            this.setDisplayName(this.getName() + " §7" + this.mode.getValue());
            switch (this.mode.getValue()) {
                case NCP:
                case Verus:
                case Motion: {
                    if (!ModuleManager.getModule(Speed.class).isEnabled() && !ModuleManager.getModule(Scaffold.class).isEnabled() && !ModuleManager.getModule(Flight.class).isEnabled() && MoveUtils.isMovingOnGround()) {
                        this.mc.thePlayer.stepHeight = this.height.getValue().floatValue();
                    }
                    else {
                        this.mc.thePlayer.stepHeight = 0.625f;
                    }
                    if (this.stepping && System.currentTimeMillis() - this.lastStep > 50L) {
                        this.stepping = false;
                        this.mc.timer.timerSpeed = 1.0f;
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
            }
            return;
        });
        final double realHeight;
        double[] array;
        int length;
        int i = 0;
        double val;
        double[] array2;
        int length2;
        int j = 0;
        double val2;
        this.stepConfirmEventListener = (e -> {
            realHeight = this.mc.thePlayer.getEntityBoundingBox().minY - this.mc.thePlayer.posY;
            if (MoveUtils.isMovingOnGround() && realHeight > 0.625 && realHeight <= 1.5) {
                this.mc.timer.timerSpeed = this.timer.getValue().floatValue();
                switch (this.mode.getValue()) {
                    case NCP: {
                        array = new double[] { 0.42, 0.75 };
                        for (length = array.length; i < length; ++i) {
                            val = array[i];
                            PacketUtil.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + val, this.mc.thePlayer.posZ, false));
                        }
                        break;
                    }
                    case Verus: {
                        array2 = new double[] { 0.5, 1.0 };
                        for (length2 = array2.length; j < length2; ++j) {
                            val2 = array2[j];
                            PacketUtil.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + val2, this.mc.thePlayer.posZ, true));
                        }
                        break;
                    }
                }
                this.stepping = true;
                this.lastStep = System.currentTimeMillis();
            }
        });
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.stepping = false;
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        this.mc.thePlayer.stepHeight = 0.625f;
        this.mc.timer.timerSpeed = 1.0f;
    }
    
    public enum Mode
    {
        Motion, 
        Verus, 
        NCP;
    }
}
