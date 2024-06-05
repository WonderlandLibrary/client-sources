package me.superskidder.lune.modules.world;

import com.mojang.authlib.GameProfile;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.events.EventPacketSend;
import me.superskidder.lune.manager.event.EventTarget;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Blink
        extends Mod {
    private EntityOtherPlayerMP blinkEntity;
    private List<Packet> packetsList = new ArrayList<Packet>();

    public Blink() {
        super("Blink", ModCategory.Player,"Instant movement");
    }

    @Override
    public void onEnabled() {
        if (this.mc.thePlayer == null) {
            return;
        }
        this.blinkEntity = new EntityOtherPlayerMP(this.mc.theWorld, new GameProfile(new UUID(69L, 96L), "Blink"));
        this.blinkEntity.inventory = this.mc.thePlayer.inventory;
        this.blinkEntity.inventoryContainer = this.mc.thePlayer.inventoryContainer;
        this.blinkEntity.setPositionAndRotation(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch);
        this.blinkEntity.rotationYawHead = this.mc.thePlayer.rotationYawHead;
        this.blinkEntity.setHealth(mc.thePlayer.getHealth());
        mc.theWorld.addEntityToWorld(this.blinkEntity.getEntityId(), this.blinkEntity);
    }

    @EventTarget
    private void onPacketSend(EventPacketSend event) {
        if (event.getPacket() instanceof C0BPacketEntityAction || event.getPacket() instanceof C03PacketPlayer || event.getPacket() instanceof C02PacketUseEntity || event.getPacket() instanceof C0APacketAnimation || event.getPacket() instanceof C08PacketPlayerBlockPlacement) {
            this.packetsList.add(event.getPacket());
            event.setCancelled(true);
        }
    }

    @Override
    public void onDisable() {
        if(mc.theWorld == null)
            return;
        for (Packet packet : this.packetsList) {
            mc.getNetHandler().addToSendQueue(packet);
        }
        this.packetsList.clear();
        mc.theWorld.removeEntityFromWorld(this.blinkEntity.getEntityId());
    }
}

