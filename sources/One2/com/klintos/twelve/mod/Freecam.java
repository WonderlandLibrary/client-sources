package com.klintos.twelve.mod;

import com.darkmagician6.eventapi.EventTarget;
import com.klintos.twelve.Twelve;
import com.klintos.twelve.handlers.ModHandler;
import com.klintos.twelve.mod.Mod;
import com.klintos.twelve.mod.ModCategory;
import com.klintos.twelve.mod.events.EventPacketSend;
import com.klintos.twelve.mod.events.EventPreUpdate;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class Freecam
extends Mod {
    private double startPosX;
    private double startPosY;
    private double startPosZ;
    private float startPitch;
    private float startYaw;

    public Freecam() {
        super("Freecam", 0, ModCategory.MISC);
    }

    @Override
    public void onEnable() {
        EntityOtherPlayerMP fakePlayer = new EntityOtherPlayerMP(Freecam.mc.theWorld, Freecam.mc.thePlayer.sendQueue.func_175105_e());
        fakePlayer.func_180432_n(Freecam.mc.thePlayer);
        fakePlayer.setPositionAndRotation(Freecam.mc.thePlayer.posX, Freecam.mc.thePlayer.boundingBox.minY, Freecam.mc.thePlayer.posZ, Freecam.mc.thePlayer.rotationYawHead, Freecam.mc.thePlayer.rotationPitch);
        fakePlayer.rotationYawHead = Freecam.mc.thePlayer.rotationYawHead;
        Freecam.mc.theWorld.addEntityToWorld(-1, fakePlayer);
        this.startPosX = Freecam.mc.thePlayer.posX;
        this.startPosY = Freecam.mc.thePlayer.posY;
        this.startPosZ = Freecam.mc.thePlayer.posZ;
        this.startPitch = Freecam.mc.thePlayer.rotationPitch;
        this.startYaw = Freecam.mc.thePlayer.rotationYaw;
    }

    @EventTarget
    public void onPreUpdate(EventPreUpdate event) {
        Freecam.mc.thePlayer.noClip = Twelve.getInstance().getModHandler().getMod("Flight").getEnabled();
    }

    @EventTarget
    public void onPacketSend(EventPacketSend event) {
        if (event.getPacket() instanceof C03PacketPlayer) {
            event.setCancelled(true);
        }
    }

    @Override
    public void onDisable() {
        Freecam.mc.theWorld.removeEntityFromWorld(-1);
        Freecam.mc.thePlayer.rotationPitch = this.startPitch;
        Freecam.mc.thePlayer.rotationYaw = this.startYaw;
        Freecam.mc.thePlayer.setPosition(this.startPosX, this.startPosY, this.startPosZ);
        Freecam.mc.thePlayer.noClip = false;
        Freecam.mc.thePlayer.onGround = true;
    }
}

