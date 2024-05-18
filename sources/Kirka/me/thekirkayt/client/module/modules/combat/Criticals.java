/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.combat;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.PacketSendEvent;
import me.thekirkayt.event.events.TickEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;

@Module.Mod
public class Criticals
extends Module {
    @Option.Op
    public boolean Packet;
    @Option.Op
    public static boolean Latest;

    public Criticals() {
        Latest = true;
    }

    @EventTarget
    public void onLatest(PacketSendEvent event) {
        C02PacketUseEntity packet;
        if (event.getPacket() instanceof C02PacketUseEntity && (packet = (C02PacketUseEntity)event.getPacket()).getAction() == C02PacketUseEntity.Action.ATTACK && this.Packet) {
            ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.player().posX, ClientUtils.player().posY + 0.05, ClientUtils.player().posZ, false));
            ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.player().posX, ClientUtils.player().posY, ClientUtils.player().posZ, false));
            ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.player().posX, ClientUtils.player().posY + 0.012511, ClientUtils.player().posZ, false));
            ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.player().posX, ClientUtils.player().posY, ClientUtils.player().posZ, false));
        }
    }

    @EventTarget
    private void onTick(TickEvent event) {
        this.updateTick();
    }

    private void updateTick() {
        if (Latest) {
            this.setSuffix("Latest");
        } else if (this.Packet) {
            this.setSuffix("Packet");
        }
    }
}

