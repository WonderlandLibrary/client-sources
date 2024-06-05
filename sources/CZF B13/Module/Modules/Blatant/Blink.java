package gq.vapu.czfclient.Module.Modules.Blatant;

import com.mojang.authlib.GameProfile;
import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.World.EventPacketSend;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Blink extends Module {
    private EntityOtherPlayerMP blinkEntity;
    private final List<Packet> packetList = new ArrayList<Packet>();

    public Blink() {
        super("Blink", new String[]{"blonk"}, ModuleType.Blatant);
    }

    @Override
    public void onEnable() {
        this.setColor(new Color(200, 100, 200).getRGB());
        if (mc.thePlayer == null) {
            return;
        }
        this.blinkEntity = new EntityOtherPlayerMP(mc.theWorld, new GameProfile(new UUID(69L, 96L), mc.thePlayer.getName()));
        this.blinkEntity.inventory = mc.thePlayer.inventory;
        this.blinkEntity.inventoryContainer = mc.thePlayer.inventoryContainer;
        this.blinkEntity.setPositionAndRotation(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ,
                mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
        this.blinkEntity.rotationYawHead = mc.thePlayer.rotationYawHead;
        mc.theWorld.addEntityToWorld(this.blinkEntity.getEntityId(), this.blinkEntity);
    }

    @EventHandler
    private void onPacketSend(EventPacketSend event) {
        if (EventPacketSend.getPacket() instanceof C0BPacketEntityAction || EventPacketSend.getPacket() instanceof C03PacketPlayer || EventPacketSend.getPacket() instanceof C0APacketAnimation
                || EventPacketSend.getPacket() instanceof C08PacketPlayerBlockPlacement) {
            this.packetList.add(EventPacketSend.getPacket());
            event.setCancelled(true);
        }
    }

    @Override
    public void onDisable() {
        for (Packet packet : this.packetList) {
            mc.getNetHandler().addToSendQueue(packet);
        }
        this.packetList.clear();
        mc.theWorld.removeEntityFromWorld(this.blinkEntity.getEntityId());
    }
}
