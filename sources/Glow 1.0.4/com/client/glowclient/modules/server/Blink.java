package com.client.glowclient.modules.server;

import net.minecraft.client.entity.*;
import net.minecraft.network.*;
import org.lwjgl.opengl.*;
import com.client.glowclient.modules.other.*;
import net.minecraft.client.renderer.entity.*;
import com.client.glowclient.events.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.utils.*;
import java.util.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.client.*;
import net.minecraft.client.gui.*;
import com.client.glowclient.modules.*;
import com.client.glowclient.*;
import net.minecraft.network.play.client.*;

public class Blink extends ModuleContainer
{
    private EntityOtherPlayerMP f;
    public static BooleanValue tracer;
    private double G;
    private double d;
    private final ArrayList<Packet> L;
    public static BooleanValue eSPbox;
    private double B;
    public static BooleanValue nametag;
    
    static {
        Blink.tracer = ValueFactory.M("Blink", "Tracer", "Shows tracer line", true);
        Blink.eSPbox = ValueFactory.M("Blink", "ESPbox", "Shows ESPbox", true);
        Blink.nametag = ValueFactory.M("Blink", "Nametag", "Shows player nametag", true);
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
        final int n19 = -1;
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
            if (Blink.eSPbox.M()) {
                final double n = this.B - 0.3;
                final double d = this.d;
                final double n2 = this.G - 0.3;
                final int n3 = 255;
                Ma.M(n, d, n2, n3, n3, n3);
            }
            if (Blink.tracer.M()) {
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
                GL11.glVertex3d(this.B - renderPosX, this.d - renderPosY + 1.0, this.G - renderPosZ);
                GL11.glEnd();
                GL11.glDisable(n6);
                GL11.glDepthMask(true);
                GL11.glEnable(3553);
                GL11.glEnable(2929);
                GL11.glDisable(2848);
            }
            if (Blink.nametag.M()) {
                Wrapper.mc.getRenderManager();
                final double n7 = this.B - 0.6 - renderPosX;
                Wrapper.mc.getRenderManager();
                final double n8 = this.d - 1.8 - renderPosY;
                Wrapper.mc.getRenderManager();
                this.M(Wrapper.mc.player.getName() + " [Blink]", n7 + 0.6, n8 + 2.3, this.G - 0.6 - renderPosZ + 0.6, Wrapper.mc.player.getDistance(this.B, this.d, this.G), ca.m());
            }
        }
        catch (Exception ex) {}
    }
    
    @Override
    public void E() {
        if (!ModuleManager.M("Freecam").k()) {
            final Iterator<Packet> iterator2;
            Iterator<Packet> iterator = iterator2 = this.L.iterator();
            while (iterator.hasNext()) {
                PacketUtils.sendPacket2(iterator2.next());
                iterator = iterator2;
            }
            this.L.clear();
        }
    }
    
    @SubscribeEvent
    public void M(final RenderGameOverlayEvent$Text renderGameOverlayEvent$Text) {
        final ca ca = new ca();
        final ScaledResolution scaledResolution = new ScaledResolution(FMLClientHandler.instance().getClient());
        if (!ModuleManager.M("Freecam").k()) {}
    }
    
    public void d() {
        final boolean b = false;
        this.L.clear();
        dB.M(b);
    }
    
    public Blink() {
        super(Category.SERVER, "Blink", false, -1, "Cancels CPacketPlayer for period of time");
        this.L = new ArrayList<Packet>();
    }
    
    @SubscribeEvent
    public void M(final Cd cd) {
        if (!ModuleManager.M("Freecam").k()) {
            final Packet<?> packet;
            if (!((packet = cd.getPacket()) instanceof CPacketPlayer)) {
                return;
            }
            cd.setCanceled(true);
            if (!(packet instanceof CPacketPlayer$Position) && !(packet instanceof CPacketPlayer$PositionRotation)) {
                return;
            }
            this.L.add(packet);
        }
    }
    
    @Override
    public void D() {
        if (Wrapper.mc.player != null) {
            this.B = Wrapper.mc.player.posX;
            this.d = Wrapper.mc.player.posY;
            this.G = Wrapper.mc.player.posZ;
        }
    }
}
