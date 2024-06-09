package me.finz0.osiris.module.modules.player;

import me.finz0.osiris.module.Module;
import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.event.events.PacketEvent;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

//seppuku skid, dont care enough to rewrite
public class Blink extends Module {
    public Blink() {
        super("Blink", Category.PLAYER, "Cancels most packets");
    }
    EntityOtherPlayerMP entity;
    private final Queue<Packet> packets = new ConcurrentLinkedQueue<>();
    @EventHandler
    private Listener<PacketEvent.Send> packetSendListener = new Listener<>(event -> {
        Packet packet = event.getPacket();
        if (packet instanceof CPacketChatMessage || packet instanceof CPacketConfirmTeleport || packet instanceof CPacketKeepAlive || packet instanceof CPacketTabComplete || packet instanceof CPacketClientStatus) {
            return;
        }
        packets.add(packet);
        event.cancel();
    });

    public void onEnable() {
        AuroraMod.EVENT_BUS.subscribe(this);
        entity = new EntityOtherPlayerMP(mc.world, mc.getSession().getProfile());
        entity.copyLocationAndAnglesFrom(mc.player);
        entity.rotationYaw = mc.player.rotationYaw;
        entity.rotationYawHead = mc.player.rotationYawHead;
        mc.world.addEntityToWorld(666, entity);
    }

    public void onDisable() {
        AuroraMod.EVENT_BUS.unsubscribe(this);
        if (this.entity != null) {
            mc.world.removeEntity(entity);
        }
        if (this.packets.size() > 0) {
            for (Packet packet : this.packets) {
                mc.player.connection.sendPacket(packet);
            }
            this.packets.clear();
        }
    }

    public String getHudInfo(){
        return "" + packets.size();
    }
}
