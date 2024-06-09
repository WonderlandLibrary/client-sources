package me.valk.agway.modules.combat;

import java.awt.*;

import me.valk.event.EventListener;
import me.valk.event.events.other.EventPacket;
import me.valk.module.ModData;
import me.valk.module.ModType;
import me.valk.module.Module;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class Criticals extends Module
{
    public Criticals() {
        super(new ModData("Criticals", 0, new Color(255, 99, 84)), ModType.COMBAT);
    }
    
    @EventListener
    public void onPacketSend(final EventPacket event) {
        if (event.getPacket() instanceof C02PacketUseEntity) {
            final C02PacketUseEntity packet = (C02PacketUseEntity)event.getPacket();
            if (packet.getAction() == C02PacketUseEntity.Action.ATTACK) {        
                this.p.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.p.posX, this.p.posY + 0.05, this.p.posZ, true));
                this.p.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.p.posX, this.p.posY, this.p.posZ, false));
                this.p.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.p.posX, this.p.posY + 0.012511, this.p.posZ, false));
                this.p.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.p.posX, this.p.posY, this.p.posZ, false));
            }
        }
    }
}
