package me.superskidder.lune.modules.player;

import me.superskidder.lune.events.*;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Debug extends Mod {
    public Debug() {
        super("Debug", ModCategory.Player, "Dev");
    }

    @Override
    public void onEnabled() {
        mc.getSoundHandler().update();
    }

    @Override
    public void onDisable() {

    }


    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {

    }

    @EventTarget
    public void onUpdate(EventPreUpdate eventUpdate) {

    }

    @EventTarget
    public void onUpdate(EventPostUpdate eventUpdate) {

    }

    @EventTarget
    public void onUpdate(EventPacketSend eventUpdate) {
//        if(!(eventUpdate.getPacket() instanceof C03PacketPlayer || eventUpdate.getPacket() instanceof C00PacketKeepAlive)) {
//            System.out.println((eventUpdate.getPacket()));
//        }
        if(eventUpdate.getPacket() instanceof C03PacketPlayer.C04PacketPlayerPosition) {
            eventUpdate.setCancelled(true);
        }
    }

    @EventTarget
    public void onUpdate(EventPacketReceive eventUpdate) {

    }

    @EventTarget
    public void onUpdate(EventAttack eventUpdate) {

    }
}
