package com.client.glowclient.modules.movement;

import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.events.*;
import net.minecraft.network.play.server.*;
import com.client.glowclient.*;
import com.client.glowclient.modules.*;
import com.client.glowclient.utils.*;

public class BypassFly extends ModuleContainer
{
    public static BooleanValue groundDetect;
    public static BooleanValue bowBomber;
    public static BooleanValue phaseSpeed;
    public static final NumberValue glidePower;
    public static final NumberValue glidespeed;
    private boolean c;
    private Timer k;
    public static final NumberValue speed;
    public static BooleanValue fastShot;
    private Timer M;
    public static BooleanValue useTimer;
    private Timer d;
    public Timer L;
    public boolean A;
    public static BooleanValue glide;
    private boolean b;
    
    @Override
    public String M() {
        return String.format("%.2f", BypassFly.speed.k());
    }
    
    @Override
    public void D() {
        this.M.reset();
    }
    
    public BypassFly() {
        final boolean b = false;
        final boolean c = true;
        super(Category.MOVEMENT, "BypassFly", false, -1, "Fly using movement packets");
        this.M = new Timer();
        this.d = new Timer();
        this.c = c;
        this.L = new Timer();
        this.k = new Timer();
        this.b = b;
    }
    
    @SubscribeEvent
    public void A(final EventUpdate eventUpdate) {
        if (BypassFly.useTimer.M()) {
            if (!this.L.hasBeenSet()) {
                this.L.reset();
            }
            if (this.L.delay(0.0)) {
                Ta.M(1.0f);
            }
            if (this.L.delay(100.0)) {
                Ta.M(1.2f);
                this.L.reset();
            }
        }
        if (BypassFly.groundDetect.M()) {
            this.A = Wrapper.mc.player.onGround;
        }
        else {
            this.A = false;
        }
        if (ModuleManager.M("BypassFly").k() && !this.A) {
            try {
                double k = BypassFly.speed.k();
                if (BypassFly.phaseSpeed.M()) {
                    final double n = 300.0;
                    if (!this.k.hasBeenSet()) {
                        this.k.reset();
                    }
                    if (this.k.delay(0.0)) {
                        k = 0.06;
                    }
                    if (this.k.delay(n)) {
                        k = 0.25;
                        this.k.reset();
                    }
                    if (this.k.delay(n + 100.0)) {
                        this.L.reset();
                    }
                }
                u.M(k);
                final int m = BypassFly.glidespeed.M();
                if (this.M.delay(m + BypassFly.glidePower.M())) {
                    this.M.reset();
                }
                final double n2 = Wrapper.mc.player.posX + Wrapper.mc.player.motionX;
                double n3 = Wrapper.mc.player.posY + ((Wrapper.mc.gameSettings.keyBindJump.isKeyDown() && !this.M.delay(m)) ? (this.c ? 0.0625 : 0.0624) : 1.0E-9) - ((Wrapper.mc.gameSettings.keyBindSneak.isKeyDown() || this.M.delay(m)) ? (this.c ? 0.0625 : 0.0624) : 2.0E-9);
                if (!BypassFly.glide.M()) {
                    n3 = Wrapper.mc.player.posY + (Wrapper.mc.gameSettings.keyBindJump.isKeyDown() ? (this.c ? 0.0625 : 0.0624) : 1.0E-9) - (Wrapper.mc.gameSettings.keyBindSneak.isKeyDown() ? (this.c ? 0.0625 : 0.0624) : 2.0E-9);
                }
                final double n4 = Wrapper.mc.player.posZ + Wrapper.mc.player.motionZ;
                if (!this.k) {
                    Ob.M().sendPacket((Packet)new CPacketPlayer$PositionRotation(n2, n3, n4, Wrapper.mc.player.rotationYaw, Wrapper.mc.player.rotationPitch, Wrapper.mc.player.onGround));
                    Ob.M().sendPacket((Packet)new CPacketPlayer$PositionRotation(Wrapper.mc.player.posX, 1000.0 + Wrapper.mc.player.posY, Wrapper.mc.player.posZ, Wrapper.mc.player.rotationYaw, Wrapper.mc.player.rotationPitch, Wrapper.mc.player.onGround));
                }
                else {
                    Ob.M().sendPacket((Packet)new CPacketPlayer$PositionRotation(n2, n3, n4, Wrapper.mc.player.rotationYaw, 90.0f, Wrapper.mc.player.onGround));
                    Ob.M().sendPacket((Packet)new CPacketPlayer$PositionRotation(Wrapper.mc.player.posX, 1000.0 + Wrapper.mc.player.posY, Wrapper.mc.player.posZ, Wrapper.mc.player.rotationYaw, 90.0f, Wrapper.mc.player.onGround));
                }
                Ob.M().sendPacket((Packet)new CPacketEntityAction((Entity)Wrapper.mc.player, CPacketEntityAction$Action.START_FALL_FLYING));
                Wrapper.mc.player.setPosition(n2, n3, n4);
                this.c = !this.c;
                Wrapper.mc.player.motionX = 0.0;
                Wrapper.mc.player.motionY = 0.0;
                Wrapper.mc.player.motionZ = 0.0;
            }
            catch (Exception ex) {
                Ob.D(ex);
            }
        }
    }
    
    @SubscribeEvent
    public void M(final EventServerPacket eventServerPacket) {
        if (eventServerPacket.getPacket() instanceof SPacketPlayerPosLook) {
            final SPacketPlayerPosLook sPacketPlayerPosLook = (SPacketPlayerPosLook)eventServerPacket.getPacket();
            if (Wrapper.mc.player != null && Wrapper.mc.player.rotationYaw != -180.0f && Wrapper.mc.player.rotationPitch != 0.0f) {
                try {
                    final SPacketPlayerPosLook sPacketPlayerPosLook2 = sPacketPlayerPosLook;
                    sPacketPlayerPosLook2.pitch = Wrapper.mc.player.rotationPitch;
                    sPacketPlayerPosLook2.yaw = Wrapper.mc.player.rotationYaw;
                }
                catch (Exception ex) {}
            }
        }
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        int n;
        if (BypassFly.fastShot.M()) {
            n = 600;
        }
        else {
            n = 1200;
        }
        final double n2 = 20.0 / me.M().M().M();
        boolean b = false;
        if (BypassFly.groundDetect.M()) {
            final boolean onGround = Wrapper.mc.player.onGround;
        }
        else {
            b = false;
        }
        if (!b && tA.A(Wrapper.mc.player.getHeldItemMainhand()) && BypassFly.bowBomber.M()) {
            if (Wrapper.mc.gameSettings.keyBindAttack.isKeyDown()) {
                this.b = true;
                y.M(Wrapper.mc.player.rotationYaw, 90.0f, this);
                if (!this.d.hasBeenSet()) {
                    this.d.reset();
                }
                if (this.d.delay(0.0)) {
                    KeybindHelper.keyUseItem.M(true);
                }
                if (this.d.delay(n * n2)) {
                    this.d.reset();
                    KeybindHelper.keyUseItem.M(false);
                }
            }
            else {
                y.M(this);
                if (this.b) {
                    KeybindHelper.keyUseItem.M(false);
                    this.b = false;
                }
            }
        }
    }
    
    @Override
    public void E() {
        y.M(this);
        Ta.M();
        KeybindHelper.keyUseItem.M(false);
    }
    
    static {
        speed = ValueFactory.M("BypassFly", "Speed", "Motion Speed of X/Z", 0.25, 0.01, 0.0, 1.0);
        glidespeed = ValueFactory.M("BypassFly", "Glidespeed", "Speed you glide down.", 800.0, 1.0, 0.0, 2000.0);
        glidePower = ValueFactory.M("BypassFly", "GlidePower", "Power of glide", 200.0, 1.0, 0.0, 1000.0);
        BypassFly.glide = ValueFactory.M("BypassFly", "Glide", "Glides player down", true);
        BypassFly.bowBomber = ValueFactory.M("BypassFly", "BowBomber", "Spams bow downwards", true);
        BypassFly.fastShot = ValueFactory.M("BypassFly", "FastShot", "Shoots bow at a fast rate", false);
        BypassFly.groundDetect = ValueFactory.M("BypassFly", "GroundDetect", "Turns off fly if on ground.", true);
        BypassFly.useTimer = ValueFactory.M("BypassFly", "UseTimer", "Go faster with timer", false);
        BypassFly.phaseSpeed = ValueFactory.M("BypassFly", "PhaseSpeed", "Go slow sideways to be able to phase through blocks", false);
    }
}
