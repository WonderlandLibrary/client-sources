/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.movement;

import com.mojang.authlib.GameProfile;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.PacketSendEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.world.World;

@Module.Mod
public class Blink
extends Module {
    private EntityOtherPlayerMP blinkEntity;
    private List<Packet> packetList = new ArrayList<Packet>();
    @Option.Op(name="Delay Block Place")
    private boolean blockPlace = true;
    @Option.Op(name="Delay Attack")
    private boolean attack = true;
    @Option.Op(name="Delay All")
    private boolean all;

    @Override
    public void enable() {
        if (ClientUtils.player() == null) {
            return;
        }
        this.blinkEntity = new EntityOtherPlayerMP(ClientUtils.world(), new GameProfile(new UUID(69L, 96L), "Blink"));
        this.blinkEntity.inventory = ClientUtils.player().inventory;
        this.blinkEntity.inventoryContainer = ClientUtils.player().inventoryContainer;
        this.blinkEntity.setPositionAndRotation(ClientUtils.player().posX, ClientUtils.player().posY, ClientUtils.player().posZ, ClientUtils.player().rotationYaw, ClientUtils.player().rotationPitch);
        this.blinkEntity.rotationYawHead = ClientUtils.player().rotationYawHead;
        ClientUtils.world().addEntityToWorld(this.blinkEntity.getEntityId(), this.blinkEntity);
        super.enable();
    }

    @EventTarget
    private void onPacketSend(PacketSendEvent event) {
        if (this.all || event.getPacket() instanceof C0BPacketEntityAction || event.getPacket() instanceof C03PacketPlayer || this.attack && (event.getPacket() instanceof C02PacketUseEntity || event.getPacket() instanceof C0APacketAnimation) || this.blockPlace && event.getPacket() instanceof C08PacketPlayerBlockPlacement) {
            this.packetList.add(event.getPacket());
            event.setCancelled(true);
        }
    }

    @Override
    public void disable() {
        super.disable();
        for (Packet packet : this.packetList) {
            ClientUtils.packet(packet);
        }
        this.packetList.clear();
        ClientUtils.world().removeEntityFromWorld(this.blinkEntity.getEntityId());
    }
}

