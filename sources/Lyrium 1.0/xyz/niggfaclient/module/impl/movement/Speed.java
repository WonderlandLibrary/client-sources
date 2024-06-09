// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.movement;

import xyz.niggfaclient.events.CancellableEvent;
import net.minecraft.potion.Potion;
import xyz.niggfaclient.notifications.Notification;
import xyz.niggfaclient.notifications.NotificationType;
import xyz.niggfaclient.Client;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import xyz.niggfaclient.utils.player.MoveUtils;
import xyz.niggfaclient.events.impl.JumpEvent;
import xyz.niggfaclient.events.impl.MoveEvent;
import xyz.niggfaclient.events.impl.PacketEvent;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.MotionEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.property.Property;
import xyz.niggfaclient.property.impl.DoubleProperty;
import xyz.niggfaclient.property.impl.EnumProperty;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "Speed", description = "Allows you to auto BHop", cat = Category.MOVEMENT)
public class Speed extends Module
{
    public static EnumProperty<Mode> mode;
    public static DoubleProperty timer;
    public static DoubleProperty speed;
    public static Property<Boolean> lCheck;
    private double moveSpeed;
    private boolean prevOnGround;
    @EventLink
    private final Listener<MotionEvent> motionEventListener;
    @EventLink
    private final Listener<PacketEvent> packetEventListener;
    @EventLink
    private final Listener<MoveEvent> moveEventListener;
    @EventLink
    private final Listener<JumpEvent> jumpEventListener;
    
    public Speed() {
        this.motionEventListener = (e -> {
            this.setDisplayName(this.getName() + " §7" + Speed.mode.getValue());
            if (this.mc.thePlayer == null || this.mc.theWorld == null) {
                return;
            }
            else {
                this.mc.timer.setTimerSpeed(Speed.timer.getValue().floatValue());
                switch (Speed.mode.getValue()) {
                    case VanillaHop: {
                        if (MoveUtils.isMoving()) {
                            if (MoveUtils.isOnGround()) {
                                this.mc.thePlayer.jump();
                            }
                        }
                        else {
                            MoveUtils.setSpeed(null, 0.0);
                        }
                        MoveUtils.setSpeed(null, (float)(MoveUtils.getBaseMoveSpeed() * Speed.speed.getValue()));
                        break;
                    }
                    case VanillaGround: {
                        if (MoveUtils.isMoving()) {
                            MoveUtils.setSpeed(null, 0.0);
                        }
                        MoveUtils.setSpeed(null, (float)(MoveUtils.getBaseMoveSpeed() * Speed.speed.getValue()));
                        break;
                    }
                    case Legit: {
                        if (MoveUtils.isMoving() && MoveUtils.isOnGround() && this.mc.thePlayer.motionY < 0.003) {
                            this.mc.thePlayer.jump();
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                }
                return;
            }
        });
        this.packetEventListener = (e -> {
            if (e.getState() == PacketEvent.State.RECEIVE && e.getPacket() instanceof S08PacketPlayerPosLook && Speed.lCheck.getValue()) {
                Client.getInstance().getNotificationManager().add(new Notification("Lag back", "Disabling speed due to a teleport.", 5000L, NotificationType.INFO));
                this.toggle();
            }
            return;
        });
        this.moveEventListener = (e -> {
            switch (Speed.mode.getValue()) {
                case LatestNCP: {
                    if (MoveUtils.isOnGround()) {
                        this.prevOnGround = true;
                        if (MoveUtils.isMoving()) {
                            e.setY(MoveUtils.getJumpHeight());
                            this.moveSpeed += 0.2;
                        }
                    }
                    else if (this.prevOnGround) {
                        this.moveSpeed = (this.mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 0.33 : 0.3);
                        this.prevOnGround = false;
                    }
                    else {
                        this.moveSpeed *= (this.mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 0.95 : 0.9);
                        this.moveSpeed += 0.03;
                    }
                    MoveUtils.setSpeed(e, this.moveSpeed);
                    break;
                }
            }
            return;
        });
        this.jumpEventListener = CancellableEvent::setCancelled;
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.prevOnGround = this.mc.thePlayer.onGround;
        this.moveSpeed = MoveUtils.getBaseMoveSpeed();
        MoveUtils.resetLastTickDistance();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        this.mc.timer.timerSpeed = 1.0f;
    }
    
    static {
        Speed.mode = new EnumProperty<Mode>("Mode", Mode.VanillaHop);
        Speed.timer = new DoubleProperty("Timer Speed", 1.0, 0.1, 20.0, 0.1);
        Speed.speed = new DoubleProperty("Speed", 2.0, 1.0, 10.0, 0.1);
        Speed.lCheck = new Property<Boolean>("Lagback Check", true);
    }
    
    public enum Mode
    {
        VanillaHop, 
        VanillaGround, 
        Legit, 
        LatestNCP;
    }
}
