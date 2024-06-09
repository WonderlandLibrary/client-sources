// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.movement;

import xyz.niggfaclient.utils.player.PlayerUtil;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.network.Packet;
import xyz.niggfaclient.utils.network.PacketUtil;
import net.minecraft.network.play.client.C03PacketPlayer;
import xyz.niggfaclient.utils.player.MoveUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import xyz.niggfaclient.events.impl.MoveEvent;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.MotionEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.utils.other.TimerUtil;
import xyz.niggfaclient.property.Property;
import xyz.niggfaclient.property.impl.EnumProperty;
import xyz.niggfaclient.property.impl.DoubleProperty;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "CustomFlight", description = "Custom Flight", cat = Category.MOVEMENT)
public class CustomFlight extends Module
{
    private final DoubleProperty customSpeedH;
    private final DoubleProperty customSpeedV;
    private final DoubleProperty customTimer;
    private final EnumProperty<CKBypass> customKickBypass;
    private final EnumProperty<CGState> customGroundState;
    private final EnumProperty<CDMGMode> customDamage;
    private final DoubleProperty vclipPosition;
    private final Property<Boolean> delayKickBypass;
    private final Property<Boolean> stopOnDisable;
    private final Property<Boolean> smoothCamera;
    private final Property<Boolean> startVClipDist;
    private final DoubleProperty upDistance;
    private final Property<Boolean> glide;
    private final DoubleProperty cGlideSpeed;
    private final Property<Boolean> dashFly;
    private final DoubleProperty timerDelay;
    private final DoubleProperty posX;
    private final DoubleProperty posY;
    private final DoubleProperty posZ;
    private final Property<Boolean> up;
    private final TimerUtil timer;
    private long groundTimer;
    private float startY;
    @EventLink
    private final Listener<MotionEvent> motionEventListener;
    @EventLink
    private final Listener<MoveEvent> moveEventListener;
    
    public CustomFlight() {
        this.customSpeedH = new DoubleProperty("Speed Horizontal", 1.5, 1.0, 10.0, 0.1);
        this.customSpeedV = new DoubleProperty("Speed Vertical", 1.0, 1.0, 10.0, 0.1);
        this.customTimer = new DoubleProperty("Timer", 1.0, 0.1, 10.0, 0.1);
        this.customKickBypass = new EnumProperty<CKBypass>("Kick Bypass", CKBypass.None);
        this.customGroundState = new EnumProperty<CGState>("Ground State", CGState.Real);
        this.customDamage = new EnumProperty<CDMGMode>("Damage", CDMGMode.None);
        this.vclipPosition = new DoubleProperty("VClip Position", 0.5, 0.1, 5.0, 0.1, () -> this.customDamage.getValue() == CDMGMode.VClip);
        this.delayKickBypass = new Property<Boolean>("Delay Kick Bypass", false, () -> this.customKickBypass.getValue() == CKBypass.Packet);
        this.stopOnDisable = new Property<Boolean>("Stop on Disable", false);
        this.smoothCamera = new Property<Boolean>("Smooth Camera", false);
        this.startVClipDist = new Property<Boolean>("Start VClip Distance", false);
        this.upDistance = new DoubleProperty("VClip Dist", 1.0, 1.0, 10.0, 0.1, this.startVClipDist::getValue);
        this.glide = new Property<Boolean>("Glide", false);
        this.cGlideSpeed = new DoubleProperty("Glide Speed", 0.32, 1.0E-4, 1.0, 0.001, this.glide::getValue);
        this.dashFly = new Property<Boolean>("Dash Fly", false);
        this.timerDelay = new DoubleProperty("Delay", 500.0, 500.0, 400.0, 1.0, this.dashFly::getValue);
        this.posX = new DoubleProperty("Position X", 3.5, 0.1, 20.0, 0.1, this.dashFly::getValue);
        this.posY = new DoubleProperty("Position Y", 1.5, 0.0, 40.0, 0.1, this.dashFly::getValue);
        this.posZ = new DoubleProperty("Position Z", 3.5, 0.1, 20.0, 0.1, this.dashFly::getValue);
        this.up = new Property<Boolean>("Up", false, this.dashFly::getValue);
        this.timer = new TimerUtil();
        EntityPlayerSP thePlayer;
        EntityPlayerSP thePlayer2;
        EntityPlayerSP thePlayer3;
        EntityPlayerSP thePlayer4;
        this.motionEventListener = (e -> {
            e.setOnGround((this.customGroundState.getValue() == CGState.Real) ? e.isOnGround() : (this.customGroundState.getValue() == CGState.OnGround));
            this.mc.timer.timerSpeed = this.customTimer.getValue().floatValue();
            EntityPlayerSP.enableCameraYOffset = false;
            if (this.smoothCamera.getValue() && this.mc.thePlayer.posY > this.startY) {
                EntityPlayerSP.enableCameraYOffset = true;
                EntityPlayerSP.cameraYPosition = this.startY;
            }
            if (this.dashFly.getValue()) {
                this.mc.thePlayer.motionY = 0.0;
                if (this.timer.hasElapsed(this.timerDelay.getValue().longValue())) {
                    this.mc.thePlayer.setPosition(this.mc.thePlayer.posX + -Math.sin(Math.toRadians(this.mc.thePlayer.rotationYaw)) * this.posX.getValue(), ((boolean)this.up.getValue()) ? (this.mc.thePlayer.posY + this.posY.getValue()) : (this.mc.thePlayer.posY - this.posY.getValue()), this.mc.thePlayer.posZ + Math.cos(Math.toRadians(this.mc.thePlayer.rotationYaw)) * this.posZ.getValue());
                    this.timer.reset();
                }
            }
            else {
                if (this.glide.getValue()) {
                    thePlayer = this.mc.thePlayer;
                    thePlayer.motionY -= this.cGlideSpeed.getValue();
                }
                else {
                    this.mc.thePlayer.motionY = 0.0;
                }
                if (this.customKickBypass.getValue() == CKBypass.Motion) {
                    thePlayer2 = this.mc.thePlayer;
                    thePlayer2.motionY -= 0.128;
                }
                else if (this.customKickBypass.getValue() == CKBypass.Packet && (!this.delayKickBypass.getValue() || this.mc.thePlayer.ticksExisted % 10 == 0)) {
                    this.handleVanillaKickBypass();
                }
                if (this.mc.gameSettings.keyBindJump.pressed) {
                    thePlayer3 = this.mc.thePlayer;
                    thePlayer3.motionY += this.customSpeedV.getValue();
                }
                if (this.mc.gameSettings.keyBindSneak.pressed) {
                    thePlayer4 = this.mc.thePlayer;
                    thePlayer4.motionY -= this.customSpeedV.getValue();
                }
                MoveUtils.setSpeed(null, this.customSpeedH.getValue());
            }
            return;
        });
        this.moveEventListener = (e -> {
            if (this.dashFly.getValue()) {
                MoveUtils.setSpeed(e, 0.0);
            }
        });
    }
    
    private void handleVanillaKickBypass() {
        if (System.currentTimeMillis() - this.groundTimer < 1000L) {
            return;
        }
        final double x = this.mc.thePlayer.posX;
        final double y = this.mc.thePlayer.posY;
        final double z = this.mc.thePlayer.posZ;
        final double ground = this.calculateGround();
        for (double posY = y; posY > ground; posY -= 8.0) {
            PacketUtil.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(x, posY, z, true));
            if (posY - 8.0 < ground) {
                break;
            }
        }
        PacketUtil.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(x, ground, z, true));
        for (double posY = ground; posY < y; posY += 8.0) {
            PacketUtil.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(x, posY, z, true));
            if (posY + 8.0 > y) {
                break;
            }
        }
        PacketUtil.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, true));
        this.groundTimer = System.currentTimeMillis();
    }
    
    public double calculateGround() {
        final double y = this.mc.thePlayer.posY;
        final AxisAlignedBB playerBoundingBox = this.mc.thePlayer.getEntityBoundingBox();
        for (double blockHeight = 1.0, ground = y; ground > 0.0; ground -= blockHeight) {
            final AxisAlignedBB customBox = new AxisAlignedBB(playerBoundingBox.maxX, ground + blockHeight, playerBoundingBox.maxZ, playerBoundingBox.minX, ground, playerBoundingBox.minZ);
            if (this.mc.theWorld.checkBlockCollision(customBox)) {
                if (blockHeight <= 0.05) {
                    return ground + blockHeight;
                }
                ground += blockHeight;
                blockHeight = 0.05;
            }
        }
        return 0.0;
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        switch (this.customDamage.getValue()) {
            case Fake: {
                this.mc.thePlayer.handleStatusUpdate((byte)2);
                break;
            }
            case Normal: {
                PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 3.115, this.mc.thePlayer.posZ, false));
                PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, false));
                PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, true));
                break;
            }
            case VClip: {
                this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY - this.vclipPosition.getValue(), this.mc.thePlayer.posZ);
                PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 3.115, this.mc.thePlayer.posZ, false));
                PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, false));
                PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, true));
                break;
            }
        }
        if (this.startVClipDist.getValue()) {
            this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + this.upDistance.getValue(), this.mc.thePlayer.posZ);
        }
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        PlayerUtil.resetCapabilities();
        this.startY = (float)this.mc.thePlayer.posY;
        EntityPlayerSP.enableCameraYOffset = false;
        if (this.stopOnDisable.getValue()) {
            MoveUtils.setSpeed(null, 0.0);
        }
    }
    
    public enum CKBypass
    {
        None, 
        Packet, 
        Motion;
    }
    
    public enum CDMGMode
    {
        None, 
        Fake, 
        VClip, 
        Normal;
    }
    
    public enum CGState
    {
        Real, 
        OnGround, 
        OffGround;
    }
}
