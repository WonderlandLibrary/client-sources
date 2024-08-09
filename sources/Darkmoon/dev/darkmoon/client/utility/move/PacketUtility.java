package dev.darkmoon.client.utility.move;

import dev.darkmoon.client.event.packet.EventReceivePacket;
import dev.darkmoon.client.utility.Utility;
import net.minecraft.network.play.server.SPacketHeldItemChange;

public class PacketUtility implements Utility {
    public static boolean state;
    private boolean itemChanged;
    private int currentItem = -1;

    public void processPacket(EventReceivePacket packet) {
        if (packet.getPacket() instanceof SPacketHeldItemChange) {
            this.itemChanged = true;
        }
    }

    public void dE(boolean flag) {
        if (this.itemChanged && this.currentItem != -1) {
            state = true;
            mc.player.inventory.currentItem = this.currentItem;
            if (flag) {
                this.itemChanged = false;
                this.currentItem = -1;
                state = false;
            }
        }

    }

    public void setCurrentItem(int slot) {
        this.currentItem = slot;
    }
}
