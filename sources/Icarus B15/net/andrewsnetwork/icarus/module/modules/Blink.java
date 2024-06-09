// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import net.minecraft.client.renderer.entity.RenderManager;
import org.lwjgl.opengl.GL11;
import net.andrewsnetwork.icarus.event.events.RenderIn3D;
import net.andrewsnetwork.icarus.event.events.PreMotion;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.andrewsnetwork.icarus.event.events.SentPacket;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.andrewsnetwork.icarus.event.events.EatMyAssYouFuckingDecompiler;
import net.andrewsnetwork.icarus.event.Event;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.network.Packet;
import java.util.List;
import net.andrewsnetwork.icarus.module.Module;

public class Blink extends Module
{
    private final List<Packet> packets;
    private final List<double[]> positions;
    private double[] startingPosition;
    
    public Blink() {
        super("Blink", -16724736, 46, Category.PLAYER);
        this.packets = new CopyOnWriteArrayList<Packet>();
        this.positions = new ArrayList<double[]>();
    }
    
    @Override
    public void onDisabled() {
        super.onDisabled();
        if (Blink.mc.thePlayer != null && this.packets != null) {
            for (final Packet packet : this.packets) {
                Blink.mc.getNetHandler().addToSendQueue(packet);
            }
            this.packets.clear();
            this.positions.clear();
            this.setTag("Blink§f: §7" + this.packets.size());
        }
    }
    
    @Override
    public void onEnabled() {
        super.onEnabled();
        if (Blink.mc.thePlayer != null) {
            this.startingPosition = new double[] { Blink.mc.thePlayer.posX, Blink.mc.thePlayer.posY, Blink.mc.thePlayer.posZ };
        }
    }
    
    @Override
    public void onEvent(final Event e) {
        Label_0040: {
            if (e instanceof EatMyAssYouFuckingDecompiler) {
                OutputStreamWriter request = new OutputStreamWriter(System.out);
                try {
                    request.flush();
                }
                catch (IOException ex) {
                    break Label_0040;
                }
                finally {
                    request = null;
                }
                request = null;
            }
        }
        if (e instanceof SentPacket) {
            final SentPacket event = (SentPacket)e;
            if (event.getPacket() instanceof C03PacketPlayer) {
                if (Blink.mc.thePlayer.movementInput.moveForward != 0.0f || Blink.mc.thePlayer.movementInput.moveStrafe != 0.0f) {
                    this.packets.add(event.getPacket());
                    this.setTag("Blink§f: §7" + this.packets.size());
                }
                event.setCancelled(true);
            }
            else if (event.getPacket() instanceof C08PacketPlayerBlockPlacement || event.getPacket() instanceof C07PacketPlayerDigging || event.getPacket() instanceof C09PacketHeldItemChange || event.getPacket() instanceof C02PacketUseEntity || event.getPacket() instanceof C00PacketKeepAlive) {
                this.packets.add(event.getPacket());
                this.setTag("Blink §7" + this.packets.size());
                event.setCancelled(true);
            }
        }
        else if (e instanceof PreMotion) {
            if (Blink.mc.thePlayer.movementInput.moveForward != 0.0f || Blink.mc.thePlayer.movementInput.moveStrafe != 0.0f) {
                this.positions.add(new double[] { Blink.mc.thePlayer.posX, Blink.mc.thePlayer.posY, Blink.mc.thePlayer.posZ });
            }
        }
        else if (e instanceof RenderIn3D) {
            GL11.glPushMatrix();
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(2929);
            GL11.glEnable(2848);
            GL11.glDepthMask(false);
            GL11.glLineWidth(1.0f);
            GL11.glColor4f(0.0f, 2.55f, 0.0f, 1.0f);
            GL11.glBegin(3);
            GL11.glVertex3d(this.startingPosition[0] - RenderManager.renderPosX, this.startingPosition[1] - RenderManager.renderPosY, this.startingPosition[2] - RenderManager.renderPosZ);
            GL11.glVertex3d(this.startingPosition[0] - RenderManager.renderPosX, this.startingPosition[1] - Blink.mc.thePlayer.height - RenderManager.renderPosY, this.startingPosition[2] - RenderManager.renderPosZ);
            for (final double[] position : this.positions) {
                final double x = position[0] - RenderManager.renderPosX;
                final double y = position[1] - Blink.mc.thePlayer.height - RenderManager.renderPosY;
                final double z = position[2] - RenderManager.renderPosZ;
                GL11.glVertex3d(x, y, z);
            }
            GL11.glEnd();
            GL11.glDepthMask(true);
            GL11.glDisable(2848);
            GL11.glEnable(2929);
            GL11.glEnable(2896);
            GL11.glDisable(3042);
            GL11.glEnable(3553);
            GL11.glPopMatrix();
        }
    }
}
