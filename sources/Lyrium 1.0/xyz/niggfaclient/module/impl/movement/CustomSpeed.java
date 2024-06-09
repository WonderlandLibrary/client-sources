// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.movement;

import xyz.niggfaclient.utils.player.PlayerUtil;
import xyz.niggfaclient.utils.other.MathUtils;
import xyz.niggfaclient.utils.player.MoveUtils;
import xyz.niggfaclient.module.ModuleManager;
import xyz.niggfaclient.module.impl.combat.KillAura;
import net.minecraft.network.play.client.C03PacketPlayer;
import xyz.niggfaclient.events.impl.MoveEvent;
import xyz.niggfaclient.events.impl.MotionEvent;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.PacketEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.property.Property;
import xyz.niggfaclient.property.impl.DoubleProperty;
import xyz.niggfaclient.property.impl.EnumProperty;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "CustomSpeed", description = "Custom Speed", cat = Category.MOVEMENT)
public class CustomSpeed extends Module
{
    private final EnumProperty<GroundState> customGroundState;
    private final DoubleProperty timer;
    private final DoubleProperty motionY;
    private final DoubleProperty speed;
    private final Property<Boolean> groundStrafe;
    private final Property<Boolean> yawRot;
    private final Property<Boolean> packetRotation;
    private final Property<Boolean> fastFall;
    private final Property<Boolean> lowHop;
    @EventLink
    private final Listener<PacketEvent> packetEventListener;
    @EventLink
    private final Listener<MotionEvent> motionEventListener;
    @EventLink
    private final Listener<MoveEvent> moveEventListener;
    
    public CustomSpeed() {
        this.customGroundState = new EnumProperty<GroundState>("Ground State", GroundState.Real);
        this.timer = new DoubleProperty("Timer", 1.0, 0.1, 10.0, 0.1);
        this.motionY = new DoubleProperty("Motion Y", 0.42, 0.2, 0.42, 0.01);
        this.speed = new DoubleProperty("Speed", 1.0, 1.0, 10.0, 0.1);
        this.groundStrafe = new Property<Boolean>("Ground Strafe", false);
        this.yawRot = new Property<Boolean>("Yaw Rotation", false);
        this.packetRotation = new Property<Boolean>("Packet Rotation", false, this.yawRot::getValue);
        this.fastFall = new Property<Boolean>("Fast Fall", false);
        this.lowHop = new Property<Boolean>("Low Hop", false);
        C03PacketPlayer c03;
        this.packetEventListener = (e -> {
            if (e.getState() == PacketEvent.State.SEND && e.getPacket() instanceof C03PacketPlayer) {
                c03 = (C03PacketPlayer)e.getPacket();
                if (this.yawRot.getValue() && this.packetRotation.getValue() && !ModuleManager.getModule(KillAura.class).isEnabled()) {
                    c03.setYaw(MoveUtils.getDirection());
                }
                else {
                    c03.setYaw(c03.getYaw());
                }
            }
            return;
        });
        this.motionEventListener = (e -> {
            e.setOnGround((this.customGroundState.getValue() == GroundState.Real) ? e.isOnGround() : (this.customGroundState.getValue() == GroundState.OnGround));
            this.mc.timer.timerSpeed = this.timer.getValue().floatValue();
            if (this.yawRot.getValue()) {
                e.setYaw(MoveUtils.getDirection());
            }
            if (MoveUtils.isMoving()) {
                if (MoveUtils.isOnGround() && this.groundStrafe.getValue()) {
                    MoveUtils.setSpeed(null, MoveUtils.getBaseMoveSpeed() * this.speed.getValue());
                }
                else if (!this.groundStrafe.getValue()) {
                    MoveUtils.setSpeed(null, MoveUtils.getBaseMoveSpeed() * this.speed.getValue());
                }
                if (MoveUtils.isOnGround() && !this.lowHop.getValue()) {
                    this.mc.thePlayer.motionY = this.motionY.getValue();
                }
            }
            if (this.yawRot.getValue()) {
                this.mc.thePlayer.renderYawOffset = e.getYaw();
                this.mc.thePlayer.rotationYawHead = e.getYaw();
            }
            return;
        });
        double base;
        this.moveEventListener = (e -> {
            if (MoveUtils.isMoving()) {
                if (this.lowHop.getValue()) {
                    base = MathUtils.round(this.mc.thePlayer.posY - (int)this.mc.thePlayer.posY, 3.0);
                    if (!this.mc.thePlayer.isCollidedHorizontally) {
                        if (base == MathUtils.round(0.4, 3.0)) {
                            e.setY(0.31);
                        }
                        else if (base == MathUtils.round(0.71, 3.0)) {
                            e.setY(0.04);
                        }
                        else if (base == MathUtils.round(0.75, 3.0)) {
                            e.setY(-0.2);
                        }
                        else if (base == MathUtils.round(0.55, 3.0)) {
                            e.setY(-0.19);
                        }
                        else if (base == MathUtils.round(0.41, 3.0)) {
                            e.setY(-0.2);
                        }
                    }
                    if (this.mc.thePlayer.onGround) {
                        e.setY(0.4);
                    }
                }
                else if (this.fastFall.getValue() && !MoveUtils.isOnGround() && Math.round(e.getY() * 1000.0) / 1000.0 == 0.165) {
                    e.setY(0.0);
                }
            }
        });
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        PlayerUtil.resetCapabilities();
    }
    
    public enum GroundState
    {
        Real, 
        OnGround, 
        OffGround;
    }
}
