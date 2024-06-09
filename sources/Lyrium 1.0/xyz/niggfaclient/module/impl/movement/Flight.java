// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.movement;

import xyz.niggfaclient.utils.player.PlayerUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import xyz.niggfaclient.notifications.Notification;
import xyz.niggfaclient.notifications.NotificationType;
import xyz.niggfaclient.Client;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.client.C03PacketPlayer;
import xyz.niggfaclient.utils.player.MoveUtils;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.block.BlockAir;
import xyz.niggfaclient.events.impl.PacketEvent;
import xyz.niggfaclient.events.impl.MoveEvent;
import xyz.niggfaclient.events.impl.MotionEvent;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.CollideEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.utils.other.TimerUtil;
import xyz.niggfaclient.property.Property;
import xyz.niggfaclient.property.impl.DoubleProperty;
import xyz.niggfaclient.property.impl.EnumProperty;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "Flight", description = "Allows you to Flight", key = 33, cat = Category.MOVEMENT)
public class Flight extends Module
{
    private final EnumProperty<Mode> mode;
    private final DoubleProperty fSpeed;
    private final Property<Boolean> lCheck;
    private final Property<Boolean> viewbobbing;
    private final Property<Boolean> fakeDmg;
    private final TimerUtil timer;
    private int ticksSinceFlag;
    private int offGroundTicks;
    private int onGroundTicks;
    private int ticks;
    private double startX;
    private double startY;
    private double startZ;
    private double lagbackX;
    private double lagbackY;
    private double lagbackZ;
    private boolean lagback;
    @EventLink
    private final Listener<CollideEvent> collideEventListener;
    @EventLink
    private final Listener<MotionEvent> motionEventListener;
    @EventLink
    private final Listener<MoveEvent> moveEventListener;
    @EventLink
    private final Listener<PacketEvent> packetEventListener;
    
    public Flight() {
        this.mode = new EnumProperty<Mode>("Mode", Mode.Vanilla);
        this.fSpeed = new DoubleProperty("Speed", 3.0, 0.1, 10.0, 0.1);
        this.lCheck = new Property<Boolean>("Lagback Check", true);
        this.viewbobbing = new Property<Boolean>("View Bobbing", true);
        this.fakeDmg = new Property<Boolean>("Fake Damage", false);
        this.timer = new TimerUtil();
        this.collideEventListener = (e -> {
            if (this.mc.thePlayer == null || this.mc.theWorld == null) {
                return;
            }
            else {
                switch (this.mode.getValue()) {
                    case Collide: {
                        if (e.getBlock() instanceof BlockAir && e.getY() < this.mc.thePlayer.posY) {
                            e.setBoundingBox(AxisAlignedBB.fromBounds(e.getX(), e.getY(), e.getZ(), e.getX() + 1.0, this.mc.thePlayer.posY, e.getZ() + 1.0));
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
        EntityPlayerSP thePlayer;
        EntityPlayerSP thePlayer2;
        double mathGround2;
        this.motionEventListener = (e -> {
            this.setDisplayName(this.getName() + " §7" + this.mode.getValue());
            if (this.mc.thePlayer == null || this.mc.theWorld == null) {
                return;
            }
            else {
                if (this.viewbobbing.getValue() && MoveUtils.isMoving()) {
                    this.mc.thePlayer.cameraYaw = 0.105f;
                }
                if (this.mc.thePlayer.onGround) {
                    this.offGroundTicks = 0;
                    ++this.onGroundTicks;
                }
                else {
                    this.onGroundTicks = 0;
                    ++this.offGroundTicks;
                }
                switch (this.mode.getValue()) {
                    case Vanilla: {
                        this.mc.thePlayer.motionY = 0.0;
                        if (this.mc.gameSettings.keyBindJump.pressed) {
                            thePlayer = this.mc.thePlayer;
                            thePlayer.motionY += this.fSpeed.getValue() * 0.3;
                        }
                        if (this.mc.gameSettings.keyBindSneak.pressed) {
                            thePlayer2 = this.mc.thePlayer;
                            thePlayer2.motionY -= this.fSpeed.getValue() * 0.3;
                        }
                        MoveUtils.setSpeed(null, MoveUtils.getBaseMoveSpeed() * this.fSpeed.getValue());
                        break;
                    }
                    case Creative: {
                        this.mc.thePlayer.capabilities.isFlying = true;
                        break;
                    }
                    case AirWalk: {
                        e.setOnGround(true);
                        this.mc.thePlayer.motionY = 0.0;
                        MoveUtils.setSpeed(null, MoveUtils.getBaseMoveSpeed());
                        break;
                    }
                    case WatchdogSlow: {
                        if (this.ticks == 2) {
                            e.setY(e.getY() - 22.0 - Math.random() * 5.0);
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                    case Vulcan: {
                        ++this.ticksSinceFlag;
                        if (this.ticksSinceFlag >= 4) {
                            this.mc.thePlayer.motionY = 0.0;
                            this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, Math.round(e.getY() / 0.5) * 0.5, this.mc.thePlayer.posZ);
                        }
                        if ((this.ticksSinceFlag <= 20 && this.ticksSinceFlag >= 0 && this.ticksSinceFlag >= 4) || this.mc.thePlayer.posY % 0.5 == 0.0) {
                            mathGround2 = Math.round(e.getY() / 0.015625) * 0.015625;
                            this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, mathGround2, this.mc.thePlayer.posZ);
                            e.setY(mathGround2);
                            e.setOnGround(true);
                        }
                        MoveUtils.setSpeed(null, 0.2694);
                        break;
                    }
                    case Collide: {
                        this.mc.thePlayer.motionY = 0.0;
                        MoveUtils.setSpeed(null, MoveUtils.getSpeed());
                        break;
                    }
                }
                ++this.ticks;
                return;
            }
        });
        this.moveEventListener = (e -> {
            switch (this.mode.getValue()) {
                case WatchdogSlow: {
                    e.setY(0.0);
                    if (this.ticks == 4 || this.ticks == 5) {
                        MoveUtils.setSpeed(e, MoveUtils.getBaseMoveSpeed());
                    }
                    else {
                        MoveUtils.setSpeed(e, 0.0);
                    }
                    if (this.ticks == 6) {
                        this.ticks = 0;
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
        C03PacketPlayer c03;
        S08PacketPlayerPosLook s08;
        this.packetEventListener = (e -> {
            if (e.getState() == PacketEvent.State.SEND) {
                switch (this.mode.getValue()) {
                    case Collide: {
                        if (e.getPacket() instanceof C03PacketPlayer) {
                            c03 = (C03PacketPlayer)e.getPacket();
                            c03.setOnGround(true);
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                }
            }
            if (e.getState() == PacketEvent.State.RECEIVE) {
                switch (this.mode.getValue()) {
                    case Vulcan: {
                        if (e.getPacket() instanceof S08PacketPlayerPosLook) {
                            s08 = (S08PacketPlayerPosLook)e.getPacket();
                            if (this.mc.thePlayer.ticksExisted > 20 && !this.mc.isSingleplayer()) {
                                if (Math.abs(s08.getX() - this.startX) + Math.abs(s08.getY() - this.startY) + Math.abs(s08.getZ() - this.startZ) < 4.0) {
                                    e.setCancelled(true);
                                    break;
                                }
                                else {
                                    this.toggle();
                                    break;
                                }
                            }
                            else {
                                break;
                            }
                        }
                        else {
                            break;
                        }
                        break;
                    }
                }
                if (e.getPacket() instanceof S08PacketPlayerPosLook && this.lCheck.getValue()) {
                    Client.getInstance().getNotificationManager().add(new Notification("Lag back", "Disabling flight due to a teleport.", 5000L, NotificationType.INFO));
                    this.toggle();
                }
            }
        });
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.lagback = false;
        this.timer.reset();
        this.ticksSinceFlag = 999;
        this.ticks = 0;
        this.startX = this.mc.thePlayer.posX;
        this.startY = this.mc.thePlayer.posY;
        this.startZ = this.mc.thePlayer.posZ;
        final double lagbackX = 0.0;
        this.lagbackZ = lagbackX;
        this.lagbackY = lagbackX;
        this.lagbackX = lagbackX;
        if (this.fakeDmg.getValue()) {
            this.mc.thePlayer.handleStatusUpdate((byte)2);
        }
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        if (this.mode.getValue() == Mode.Vulcan) {
            this.mc.thePlayer.motionY = -0.09800000190735147;
            MoveUtils.setSpeed(null, 0.0);
            MoveUtils.setSpeed(null, 0.1);
        }
        PlayerUtil.resetCapabilities();
        MoveUtils.setSpeed(null, 0.0);
    }
    
    public enum Mode
    {
        Vanilla, 
        Creative, 
        AirWalk, 
        Collide, 
        Vulcan, 
        WatchdogSlow;
    }
}
