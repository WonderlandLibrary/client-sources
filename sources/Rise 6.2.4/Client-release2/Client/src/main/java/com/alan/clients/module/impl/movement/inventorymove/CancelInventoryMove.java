package com.alan.clients.module.impl.movement.inventorymove;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.event.impl.packet.PacketSendEvent;
import com.alan.clients.module.impl.movement.InventoryMove;
import com.alan.clients.value.Mode;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;

public class CancelInventoryMove extends Mode<InventoryMove> {

    public CancelInventoryMove(String name, InventoryMove parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<PacketSendEvent> onPacketSend = event -> {
        final Packet<?> p = event.getPacket();

        if (p instanceof C16PacketClientStatus) {
            final C16PacketClientStatus wrapper = (C16PacketClientStatus) p;

            if (wrapper.getStatus() == C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT) {
                event.setCancelled();
            }
        }

        if (p instanceof C0BPacketEntityAction) {
            final C0BPacketEntityAction wrapper = (C0BPacketEntityAction) p;

            if (wrapper.getAction() == C0BPacketEntityAction.Action.OPEN_INVENTORY) {
                event.setCancelled();
            }
        }

        if (p instanceof C0DPacketCloseWindow) {
            event.setCancelled();
        }
    };

    private final KeyBinding[] AFFECTED_BINDINGS = new KeyBinding[]{
            mc.gameSettings.keyBindForward,
            mc.gameSettings.keyBindBack,
            mc.gameSettings.keyBindRight,
            mc.gameSettings.keyBindLeft,
            mc.gameSettings.keyBindJump
    };

    @EventLink
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        if (mc.currentScreen instanceof GuiChat || mc.currentScreen == this.getClickGUI()) {
            return;
        }

        for (final KeyBinding bind : AFFECTED_BINDINGS) {
            bind.setPressed(GameSettings.isKeyDown(bind));
        }
    };
}