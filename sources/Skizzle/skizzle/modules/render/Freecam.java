/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.render;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.world.WorldSettings;
import skizzle.events.Event;
import skizzle.events.listeners.EventMotion;
import skizzle.events.listeners.EventPacket;
import skizzle.modules.Module;
import skizzle.modules.player.Blink;
import skizzle.util.MoveUtil;

public class Freecam
extends Module {
    public EntityOtherPlayerMP fakePlayer = null;
    public double enableY;
    public int enableMode;
    public double enableX;
    public float enablePitch;
    public double enableZ;
    public float enableYaw;

    public Freecam() {
        super(Qprot0.0("\u6960\u71d9\u523f\ua7e1\u4096\uecfd\u8c22"), 0, Module.Category.RENDER);
        Freecam Nigga;
    }

    @Override
    public void onEvent(Event Nigga) {
        Event Nigga2;
        if (Nigga instanceof EventPacket && (Nigga2 = (EventPacket)Nigga).isOutgoing() && Nigga2.getPacket() instanceof C03PacketPlayer) {
            Nigga2.setCancelled(true);
        }
        Nigga.mc.thePlayer.isCollidedHorizontally = false;
        Nigga.mc.thePlayer.isCollidedVertically = false;
        if (Nigga instanceof EventMotion) {
            Nigga.mc.thePlayer.capabilities.isFlying = true;
            Nigga2 = (EventMotion)Nigga;
            if (MoveUtil.isMoving()) {
                MoveUtil.setSpeed(Float.intBitsToFloat(1.05816051E9f ^ 0x7F123F92));
            } else {
                Nigga.mc.thePlayer.motionX = 0.0;
                Nigga.mc.thePlayer.motionZ = 0.0;
            }
        }
    }

    @Override
    public void onEnable() {
        Freecam Nigga;
        Nigga.enableX = Nigga.mc.thePlayer.posX;
        Nigga.enableY = Nigga.mc.thePlayer.posY;
        Nigga.enableZ = Nigga.mc.thePlayer.posZ;
        Nigga.enableMode = Nigga.mc.playerController.currentGameType.getID();
        Nigga.enablePitch = Nigga.mc.thePlayer.rotationPitch;
        Nigga.enableYaw = Nigga.mc.thePlayer.rotationYaw;
        Nigga.fakePlayer = Blink.createFakePlayer(-69);
        Nigga.mc.playerController.setGameType(WorldSettings.GameType.SPECTATOR);
    }

    public static {
        throw throwable;
    }

    @Override
    public void onDisable() {
        Freecam Nigga;
        Nigga.mc.thePlayer.setPositionAndRotation(Nigga.enableX, Nigga.enableY, Nigga.enableZ, Nigga.enableYaw, Nigga.enablePitch);
        Nigga.mc.playerController.setGameType(WorldSettings.GameType.getByID(Nigga.enableMode));
        Blink.removeFakePlayer(-69);
        Nigga.fakePlayer = null;
        Nigga.mc.thePlayer.capabilities.isFlying = false;
    }
}

