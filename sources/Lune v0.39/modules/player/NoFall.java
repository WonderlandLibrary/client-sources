package me.superskidder.lune.modules.player;

import me.superskidder.lune.Lune;
import me.superskidder.lune.events.EventPacketReceive;
import me.superskidder.lune.events.EventPacketSend;
import me.superskidder.lune.events.EventPreUpdate;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.utils.player.PlayerUtil;
import me.superskidder.lune.events.EventUpdate;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.values.type.Mode;
import me.superskidder.lune.values.type.Num;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

public class NoFall extends Mod {
    public static Num<Number> fallDistance = new Num<>("FallDistance", 2.0, 0.1, 3.0);
    private Mode bypassMode = new Mode("Mode", BMode.values(), BMode.Hypixel);

    public NoFall() {
        super("NoFall", ModCategory.Player, "Cancel your fall damage");
        this.addValues(bypassMode, fallDistance);
    }

    @EventTarget
    private void onUpdate(EventPreUpdate e) {
        switch (this.bypassMode.getModeAsString()) {
            case "Hypixel":
                if (mc.thePlayer.fallDistance >= fallDistance.getValue().floatValue()) {
                    mc.thePlayer.onGround = true;
                }
                break;
            case "Always":
                if (mc.thePlayer.fallDistance > 2) {
                    mc.thePlayer.onGround = false;
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));

                }
                break;
            default:
                break;
        }
    }


    @EventTarget
    public void onPacket(EventPacketSend e) {
        if (e.getPacket() instanceof C03PacketPlayer) {
            C03PacketPlayer playerPacket = (C03PacketPlayer) e.getPacket();
            if (this.bypassMode.getModeAsString().equals("Tick")) {
                if (mc.thePlayer != null && mc.thePlayer.fallDistance > 1.5)
                    playerPacket.onGround = mc.thePlayer.ticksExisted % 2 == 0;
            }
        }
    }

    enum BMode {
        Hypixel,
        Tick,
        Always
    }
}
