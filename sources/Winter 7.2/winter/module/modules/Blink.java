/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import com.mojang.authlib.GameProfile;
import io.netty.util.concurrent.GenericFutureListener;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.world.World;
import winter.event.EventListener;
import winter.event.events.PacketEvent;
import winter.module.Module;
import winter.utils.other.Timer;

public class Blink
extends Module {
    private long deltaTime;
    private long startTime;
    Timer timer;
    private EntityOtherPlayerMP player;
    private ArrayList<Packet> packets = new ArrayList();

    public Blink() {
        super("Blink", Module.Category.Movement, -1089383);
        this.setBind(0);
    }

    @Override
    public void onEnable() {
        this.deltaTime = 0;
        this.timer = new Timer();
        this.player = new EntityOtherPlayerMP(this.mc.theWorld, this.mc.thePlayer.getGameProfile());
        this.player.clonePlayer(this.mc.thePlayer, true);
        this.player.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ);
        this.player.rotationYawHead = this.mc.thePlayer.rotationYaw;
        this.player.rotationPitch = this.mc.thePlayer.rotationPitch;
        this.player.setSneaking(this.mc.thePlayer.isSneaking());
        this.mc.theWorld.addEntityToWorld(-1337, this.player);
        this.startTime = this.timer.getTime();
    }

    @Override
    public void onDisable() {
        this.mc.theWorld.removeEntity(this.player);
        for (Packet packet : this.packets) {
            this.mc.getNetHandler().getNetworkManager().dispatchPacket(packet, null);
        }
    }

    @EventListener
    public void onPacket(PacketEvent event) {
        if (PacketEvent.packet instanceof C03PacketPlayer || PacketEvent.packet instanceof C0BPacketEntityAction || PacketEvent.packet instanceof C0APacketAnimation || PacketEvent.packet instanceof C02PacketUseEntity || PacketEvent.packet instanceof C09PacketHeldItemChange || PacketEvent.packet instanceof C08PacketPlayerBlockPlacement || PacketEvent.packet instanceof C07PacketPlayerDigging) {
            event.setCancelled(true);
            this.packets.add(PacketEvent.packet);
            this.packets.trimToSize();
            this.deltaTime = this.timer.getTime() - this.startTime;
        }
    }
}

