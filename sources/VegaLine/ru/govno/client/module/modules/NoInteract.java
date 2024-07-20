/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketUseEntity;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.EventSendPacket;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.HitAura;
import ru.govno.client.module.settings.Settings;

public class NoInteract
extends Module {
    public static Module get;

    public NoInteract() {
        super("NoInteract", 0, Module.Category.PLAYER);
        get = this;
        this.settings.add(new Settings("NoEntityInteract", true, (Module)this));
        this.settings.add(new Settings("OnlyWithAura", true, (Module)this));
    }

    @EventTarget
    public void onSendPacket(EventSendPacket event) {
        Packet packet = event.getPacket();
        if (!(packet instanceof CPacketUseEntity)) {
            return;
        }
        CPacketUseEntity cPacketUseEntity = (CPacketUseEntity)packet;
        if (this.currentBooleanValue("NoEntityInteract") && (!this.currentBooleanValue("OnlyWithAura") || HitAura.TARGET_ROTS != null) && NoInteract.mc.world != null && cPacketUseEntity.getEntityFromWorld(NoInteract.mc.world) instanceof EntityPlayer) {
            event.setCancelled(cPacketUseEntity.getAction() == CPacketUseEntity.Action.INTERACT_AT || cPacketUseEntity.getAction() == CPacketUseEntity.Action.INTERACT);
        }
    }
}

