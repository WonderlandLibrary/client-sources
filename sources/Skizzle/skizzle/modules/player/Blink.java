/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.player;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.play.client.C03PacketPlayer;
import skizzle.events.Event;
import skizzle.events.listeners.EventPacket;
import skizzle.events.listeners.EventRender3D;
import skizzle.modules.Module;
import skizzle.util.RenderUtil;

public class Blink
extends Module {
    public ArrayList<C03PacketPlayer> packets = new ArrayList();
    public double enableY;
    public double enableZ;
    public double enableX;

    @Override
    public void onEnable() {
        Blink Nigga;
        Blink.createFakePlayer(-50);
        Nigga.enableX = Nigga.mc.getRenderManager().renderPosX;
        Nigga.enableY = Nigga.mc.getRenderManager().renderPosY;
        Nigga.enableZ = Nigga.mc.getRenderManager().renderPosZ;
    }

    public static {
        throw throwable;
    }

    public static EntityOtherPlayerMP createFakePlayer(int Nigga) {
        Minecraft.getMinecraft();
        EntityOtherPlayerMP Nigga2 = new EntityOtherPlayerMP(Minecraft.theWorld, Minecraft.getMinecraft().thePlayer.getGameProfile());
        Nigga2.clonePlayer(Minecraft.getMinecraft().thePlayer, true);
        Nigga2.copyLocationAndAnglesFrom(Minecraft.getMinecraft().thePlayer);
        Nigga2.rotationYawHead = Minecraft.getMinecraft().thePlayer.rotationYawHead;
        Minecraft.getMinecraft();
        Minecraft.theWorld.addEntityToWorld(Nigga, Nigga2);
        Nigga2.isFake = true;
        return Nigga2;
    }

    @Override
    public void onDisable() {
        Blink Nigga;
        for (C03PacketPlayer Nigga2 : Nigga.packets) {
            Nigga.mc.getNetHandler().addToSendQueueWithoutEvent(Nigga2);
        }
        Nigga.packets.clear();
        Blink.removeFakePlayer(-50);
    }

    public static void removeFakePlayer(int Nigga) {
        Minecraft.getMinecraft();
        if (Minecraft.theWorld != null) {
            Minecraft.getMinecraft();
            Minecraft.theWorld.removeEntityFromWorld(Nigga);
        }
    }

    public Blink() {
        super(Qprot0.0("\u2da0\u71c7\u16ff\ua7ea\u0f5a"), 0, Module.Category.PLAYER);
        Blink Nigga;
        Nigga.onDisable();
    }

    @Override
    public void onEvent(Event Nigga) {
        EventPacket Nigga2;
        Blink Nigga3;
        Nigga3.setSuffix("" + Nigga3.packets.size());
        if (Nigga instanceof EventRender3D) {
            GlStateManager.color(Float.intBitsToFloat(2.13164416E9f ^ 0x7F0E4EF4), Float.intBitsToFloat(1.08028032E9f ^ 0x7F10F7DA), Float.intBitsToFloat(2.12901939E9f ^ 0x7EE641F5));
            RenderUtil.drawTracer(Nigga3.enableX - Nigga3.mc.getRenderManager().renderPosX, Nigga3.enableY - Nigga3.mc.getRenderManager().renderPosY, Nigga3.enableZ - Nigga3.mc.getRenderManager().renderPosZ, Nigga3.mc.thePlayer.getEyeHeight());
        }
        if (Nigga instanceof EventPacket && (Nigga2 = (EventPacket)Nigga).isOutgoing() && Nigga2.getPacket() instanceof C03PacketPlayer) {
            Nigga3.packets.add((C03PacketPlayer)Nigga2.getPacket());
            Nigga2.setCancelled(true);
        }
    }
}

