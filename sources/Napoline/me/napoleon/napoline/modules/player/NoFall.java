package me.napoleon.napoline.modules.player;

import me.napoleon.napoline.Napoline;
import me.napoleon.napoline.events.EventPacketReceive;
import me.napoleon.napoline.events.EventPacketSend;
import me.napoleon.napoline.events.EventPreUpdate;
import me.napoleon.napoline.events.EventUpdate;
import me.napoleon.napoline.junk.values.type.Mode;
import me.napoleon.napoline.junk.values.type.Num;
import me.napoleon.napoline.manager.event.EventTarget;
import me.napoleon.napoline.modules.Mod;
import me.napoleon.napoline.modules.ModCategory;
import me.napoleon.napoline.utils.player.PlayerUtil;
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
