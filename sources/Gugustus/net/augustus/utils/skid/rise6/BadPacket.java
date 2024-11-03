package net.augustus.utils.skid.rise6;

import net.augustus.events.EventSendPacket;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C16PacketClientStatus;

public final class BadPacket {
    private static boolean slot;

    private static boolean attack;

    private static boolean swing;

    private static boolean block;

    private static boolean inventory;

    @EventTarget
    public void sendPacket(EventSendPacket event) {
        Packet<?> packet = event.getPacket();
        if (packet instanceof net.minecraft.network.play.client.C09PacketHeldItemChange) {
            slot = true;
        } else if (packet instanceof net.minecraft.network.play.client.C0APacketAnimation) {
            swing = true;
        } else if (packet instanceof net.minecraft.network.play.client.C02PacketUseEntity) {
            attack = true;
        } else if (packet instanceof net.minecraft.network.play.client.C08PacketPlayerBlockPlacement || packet instanceof net.minecraft.network.play.client.C07PacketPlayerDigging) {
            block = true;
        } else if (packet instanceof net.minecraft.network.play.client.C0EPacketClickWindow || (packet instanceof C16PacketClientStatus && ((C16PacketClientStatus)packet).getStatus() == C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT) || packet instanceof net.minecraft.network.play.client.C0DPacketCloseWindow) {
            inventory = true;
        } else if (packet instanceof net.minecraft.network.play.client.C03PacketPlayer) {
            reset();
        }
    }

    public static boolean bad() {
        return bad(true, true, true, true, true);
    }

    public static boolean bad(boolean slot, boolean attack, boolean swing, boolean block, boolean inventory) {
        return ((BadPacket.slot && slot) || (BadPacket.attack && attack) || (BadPacket.swing && swing) || (BadPacket.block && block) || (BadPacket.inventory && inventory));
    }

    public static void reset() {
        slot = false;
        swing = false;
        attack = false;
        block = false;
        inventory = false;
    }
}
