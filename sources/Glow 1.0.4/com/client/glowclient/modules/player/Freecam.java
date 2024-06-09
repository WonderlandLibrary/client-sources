package com.client.glowclient.modules.player;

import net.minecraft.entity.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.event.world.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.lwjgl.opengl.*;
import com.client.glowclient.modules.other.*;
import net.minecraft.client.renderer.entity.*;
import com.client.glowclient.events.*;
import net.minecraft.util.math.*;
import com.client.glowclient.utils.*;
import net.minecraft.client.renderer.vertex.*;
import com.client.glowclient.modules.*;
import com.client.glowclient.*;
import com.client.glowclient.modules.movement.*;

public class Freecam extends ModuleContainer
{
    private double a;
    public static BooleanValue nametag;
    private double g;
    private boolean l;
    private double K;
    public static final NumberValue speed;
    private float k;
    private float H;
    private double f;
    public static BooleanValue tracer;
    public static BooleanValue eSPbox;
    private double d;
    private double L;
    private Entity A;
    private float B;
    private float b;
    
    @SubscribeEvent
    public void M(final EventServerPacket eventServerPacket) {
        if (eventServerPacket.getPacket() instanceof SPacketPlayerPosLook) {
            final SPacketPlayerPosLook sPacketPlayerPosLook = (SPacketPlayerPosLook)eventServerPacket.getPacket();
            final SPacketPlayerPosLook sPacketPlayerPosLook3;
            final SPacketPlayerPosLook sPacketPlayerPosLook2;
            this.d = (sPacketPlayerPosLook2 = (sPacketPlayerPosLook3 = sPacketPlayerPosLook)).getX();
            this.f = sPacketPlayerPosLook2.getY();
            this.g = sPacketPlayerPosLook3.getZ();
            this.B = sPacketPlayerPosLook3.getPitch();
            this.b = sPacketPlayerPosLook.getYaw();
        }
    }
    
    static {
        speed = ValueFactory.M("FreeCam", "Speed", "Flight Speed", 0.15, 0.05, 0.0, 0.5);
        Freecam.tracer = ValueFactory.M("FreeCam", "Tracer", "Shows tracer line", true);
        Freecam.eSPbox = ValueFactory.M("FreeCam", "ESPbox", "Shows ESP outline", true);
        Freecam.nametag = ValueFactory.M("FreeCam", "Nametag", "Shows player nametag", true);
    }
    
    @SubscribeEvent
    public void M(final WorldEvent$Load worldEvent$Load) {
        this.a = this.d;
        this.L = this.f;
        this.K = this.g;
        this.k = this.B;
        this.H = this.b;
    }
    
    @Override
    public void D() {
        if (Wrapper.mc.player != null) {
            this.l = (Wrapper.mc.player.getRidingEntity() != null);
            Freecam freecam;
            if (Wrapper.mc.player.getRidingEntity() == null) {
                freecam = this;
                this.a = Wrapper.mc.player.posX;
                this.L = Wrapper.mc.player.posY;
                this.K = Wrapper.mc.player.posZ;
            }
            else {
                freecam = this;
                this.a = Wrapper.mc.player.posX;
                this.L = Wrapper.mc.player.posY;
                this.K = Wrapper.mc.player.posZ;
                this.A = Wrapper.mc.player.getRidingEntity();
                Wrapper.mc.player.dismountRidingEntity();
            }
            freecam.k = Wrapper.mc.player.rotationPitch;
            this.H = Wrapper.mc.player.rotationYaw;
            Wrapper.mc.player.capabilities.isFlying = true;
            Wrapper.mc.player.capabilities.setFlySpeed((float)Freecam.speed.k());
            Wrapper.mc.player.noClip = true;
        }
    }
    
    @SubscribeEvent
    public void M(final EventClientPacket eventClientPacket) {
        if (eventClientPacket.getPacket() instanceof CPacketPlayer || eventClientPacket.getPacket() instanceof CPacketInput || eventClientPacket.getPacket() instanceof CPacketConfirmTeleport) {
            eventClientPacket.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void M(final RenderGameOverlayEvent$Text renderGameOverlayEvent$Text) {
        final ca ca = new ca();
        final ScaledResolution scaledResolution = new ScaledResolution(FMLClientHandler.instance().getClient());
        if (ModuleManager.M("Blink").k()) {}
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void D(final EventUpdate eventUpdate) {
        final MovementInput movementInput = Wrapper.mc.player.movementInput;
        final double n = movementInput.moveForward;
        final double n2 = movementInput.moveStrafe;
        final float rotationYaw = Wrapper.mc.player.rotationYaw;
        final double n3 = 0.5 + Freecam.speed.k();
        if (!Wrapper.mc.gameSettings.keyBindJump.isKeyDown() && !Wrapper.mc.gameSettings.keyBindSneak.isKeyDown()) {
            Wrapper.mc.player.motionY = 0.0;
        }
        if (Wrapper.mc.gameSettings.keyBindJump.isKeyDown()) {
            Wrapper.mc.player.motionY = 0.5 + Freecam.speed.k();
        }
        if (Wrapper.mc.gameSettings.keyBindSneak.isKeyDown()) {
            Wrapper.mc.player.motionY = -(0.5 + Freecam.speed.k());
        }
        if (Wrapper.mc.gameSettings.keyBindJump.isKeyDown() && Wrapper.mc.gameSettings.keyBindSneak.isKeyDown()) {
            Wrapper.mc.player.motionY = 0.0;
        }
        if (n == 0.0 && n2 == 0.0) {
            Wrapper.mc.player.motionX = 0.0;
            Wrapper.mc.player.motionZ = 0.0;
        }
        else if (!Wrapper.mc.player.onGround) {
            Wrapper.mc.player.motionX = n * n3 * Math.cos(Math.toRadians(rotationYaw + 90.0f)) + n2 * n3 * Math.sin(Math.toRadians(rotationYaw + 90.0f));
            Wrapper.mc.player.motionZ = n * n3 * Math.sin(Math.toRadians(rotationYaw + 90.0f)) - n2 * n3 * Math.cos(Math.toRadians(rotationYaw + 90.0f));
        }
        Wrapper.mc.player.capabilities.isFlying = true;
        Wrapper.mc.player.noClip = true;
        Wrapper.mc.player.onGround = false;
        Wrapper.mc.player.fallDistance = 0.0f;
    }
    
    public void M(String s, double n, double n2, final double n3, final double n4, final ca ca) {
        float n5;
        if ((n5 = (float)n4 / 5.0f) < 1.625f) {
            n5 = 2.0f;
        }
        final RenderManager renderManager = Wrapper.mc.getRenderManager();
        final float n6 = n5 * 0.01f;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)n, (float)n2 + 1.4f, (float)n3);
        final float n7 = 1.0f;
        final float n8 = 0.0f;
        GL11.glNormal3f(n8, n7, n8);
        final float n9 = -renderManager.playerViewY;
        final float n10 = 1.0f;
        final float n11 = 0.0f;
        GL11.glRotatef(n9, n11, n10, n11);
        float n12;
        if (Wrapper.mc.gameSettings.thirdPersonView == 2) {
            n12 = n6;
            final float n13 = -renderManager.playerViewX;
            final float n14 = 1.0f;
            final float n15 = 0.0f;
            GL11.glRotatef(n13, n14, n15, n15);
        }
        else {
            final float playerViewX = renderManager.playerViewX;
            final float n16 = 1.0f;
            final float n17 = 0.0f;
            GL11.glRotatef(playerViewX, n16, n17, n17);
            n12 = n6;
        }
        GL11.glScalef(-n12, -n6, n6 / 100.0f);
        fd.M(2896, false);
        fd.M(2929, false);
        final int n18 = (int)Ia.M(HUD.F, s, 1.0);
        final int n19 = -1555;
        final int n20 = 1;
        final int n21 = 0;
        ba.M(n19, n19, n20, n20, ga.M(n21, n21, n21, n21));
        fd.M(3042, true);
        GL11.glBlendFunc(770, 771);
        n = -n18 / 2;
        n2 = -Ia.M(HUD.F) - 1.0;
        s = s;
        if (HUD.F != null) {
            final ca m = ca.K().M(HUD.F).M(ca::k).M();
            final int n22 = 0;
            final ca a = m.D(ga.M(n22, n22, n22, 100)).k(n - 2.0, n2 - 2.0, Ia.M(HUD.F, s, 1.0) + 4.0, Ia.M(HUD.F) + 4.0).A();
            final int n23 = 255;
            final ca i = a.D(ga.M(n23, n23, n23, n23)).M(s, n + 1.0, n2 + 1.0, true);
            final int n24 = 255;
            i.D(ga.M(n24, n24, n24, n24)).M(s, n, n2);
        }
        else {
            final ca j = ca.K().M(HUD.F).M(ca::k).M();
            final int n25 = 0;
            final ca k = j.D(ga.M(n25, n25, n25, 100)).k(n - 2.0, n2 - 2.0, Ia.M(HUD.F, s, 1.0) + 4.0, Ia.M(HUD.F) + 4.0).A().M(ca::E);
            final int n26 = 255;
            k.D(ga.M(n26, n26, n26, n26)).M(s, n, n2 - 1.0, true);
        }
        GL11.glPushMatrix();
        GL11.glPopMatrix();
        fd.M(2929, true);
        final float n27 = 1.0f;
        GL11.glColor4f(n27, n27, n27, n27);
        GL11.glPopMatrix();
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void M(final EventRenderWorld eventRenderWorld) {
        try {
            final double renderPosX = Wrapper.mc.getRenderManager().renderPosX;
            final double renderPosY = Wrapper.mc.getRenderManager().renderPosY;
            final double renderPosZ = Wrapper.mc.getRenderManager().renderPosZ;
            if (Freecam.eSPbox.M()) {
                final double n = this.a - 0.3;
                final double l = this.L;
                final double n2 = this.K - 0.3;
                final int n3 = 255;
                Ma.M(n, l, n2, n3, n3, n3);
            }
            if (Freecam.tracer.M()) {
                try {
                    GL11.glPushMatrix();
                    GL11.glEnable(2848);
                    GL11.glDisable(2929);
                    GL11.glDisable(3553);
                    GL11.glDepthMask(false);
                    GL11.glBlendFunc(770, 771);
                    GL11.glEnable(3042);
                    GL11.glLineWidth(2.0f);
                    final float n4 = 1.0f;
                    GL11.glColor4f(n4, n4, n4, n4);
                    final double n5 = 0.0;
                    final Vec3d rotateYaw = new Vec3d(n5, n5, 1.0).rotatePitch(-(float)Math.toRadians(MinecraftHelper.getPlayer().rotationPitch)).rotateYaw(-(float)Math.toRadians(MinecraftHelper.getPlayer().rotationYaw));
                    GL11.glBegin(2);
                    final int n6 = 3042;
                    GL11.glVertex3d(rotateYaw.x, MinecraftHelper.getPlayer().getEyeHeight() + rotateYaw.y, rotateYaw.z);
                    GL11.glVertex3d(this.a - renderPosX, this.L - renderPosY + 1.0, this.K - renderPosZ);
                    GL11.glEnd();
                    GL11.glDisable(n6);
                    GL11.glDepthMask(true);
                    GL11.glEnable(3553);
                    GL11.glEnable(2929);
                    GL11.glDisable(2848);
                    GL11.glPopMatrix();
                }
                catch (Exception ex) {}
            }
            if (Freecam.nametag.M()) {
                eventRenderWorld.getBuffer().begin(1, DefaultVertexFormats.POSITION_COLOR);
                this.M(Wrapper.mc.player.getName() + " [Freecam]", this.a - 0.6 - renderPosX + 0.6, this.L - 1.8 - renderPosY + 2.3, this.K - 0.6 - renderPosZ + 0.6, Wrapper.mc.player.getDistance(this.a, this.L, this.K), ca.m());
                eventRenderWorld.getTessellator().draw();
            }
        }
        catch (Exception ex2) {}
    }
    
    public Freecam() {
        super(Category.PLAYER, "Freecam", false, -1, "View different parts of the world without moving character");
    }
    
    @Override
    public void E() {
        ma.M();
        if (Wrapper.mc.player != null) {
            Wrapper.mc.player.setPositionAndRotation(this.a, this.L, this.K, this.H, this.k);
            final Freecam freecam = this;
            final double a = 0.0;
            freecam.K = a;
            freecam.L = a;
            freecam.a = a;
            final float n = 0.0f;
            this.H = n;
            this.k = n;
            try {
                Wrapper.mc.player.capabilities.isFlying = (ModuleManager.M("ElytraControl").k() && ElytraControl.mode.equals("Creative"));
            }
            catch (Throwable t) {
                Wrapper.mc.player.capabilities.isFlying = false;
            }
            Wrapper.mc.player.capabilities.setFlySpeed(0.05f);
            if (this.l) {
                Wrapper.mc.player.startRiding(this.A, true);
            }
        }
    }
}
