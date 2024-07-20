/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketResourcePackStatus;
import net.minecraft.network.play.server.SPacketResourcePackSend;
import ru.govno.client.Client;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.EventReceivePacket;
import ru.govno.client.event.events.EventSendPacket;
import ru.govno.client.module.Module;

public class SrpSpoofer
extends Module {
    public SrpSpoofer() {
        super("SrpSpoofer", 0, Module.Category.MISC);
    }

    @EventTarget
    public void onPacket(EventReceivePacket event) {
        if (!this.actived) {
            return;
        }
        if (event.getPacket() instanceof SPacketResourcePackSend) {
            SPacketResourcePackSend packetUwU = (SPacketResourcePackSend)event.getPacket();
            Minecraft.player.connection.sendPacket(new CPacketResourcePackStatus(CPacketResourcePackStatus.Action.ACCEPTED));
            Minecraft.player.connection.sendPacket(new CPacketResourcePackStatus(CPacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
            event.setCancelled(true);
            Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77[\u00a7lSrpSpoofer\u00a7r\u00a77]: \u0421\u0435\u0440\u0432\u0435\u0440 \u043f\u0440\u043e\u0441\u0438\u0442 \u0443\u0441\u0442\u0430\u043d\u043e\u0432\u043a\u0443 \u043f\u0430\u043a\u0435\u0442\u0430", false);
            Client.msg("\u00a77\u043e\u0442\u0432\u0435\u0442 \u0437\u0430\u043f\u0440\u043e\u0441 \u0431\u044b\u043b \u043f\u043e\u0434\u043c\u0435\u043d\u0435\u043d \u043d\u0430 \u043b\u043e\u0436\u043d\u044b\u0439", false);
            Client.msg("\u00a77\u043d\u0435\u043e\u0431\u0445\u043e\u0434\u0438\u043c\u043e\u0441\u0442\u044c \u0443\u0441\u0442\u0430\u043d\u043e\u0432\u043a\u0438 \u0431\u044b\u043b\u0430 \u0438\u0433\u043d\u043e\u0440\u0438\u0440\u043e\u0432\u0430\u043d\u0430 \u0438\u0433\u0440\u043e\u0439.", false);
        }
    }

    @EventTarget
    public void onPacket(EventSendPacket event) {
        if (!this.actived) {
            return;
        }
        if (event.getPacket() instanceof CPacketResourcePackStatus) {
            // empty if block
        }
    }
}

