package me.valk.agway.modules.other;

import java.awt.Color;

import me.valk.event.EventListener;
import me.valk.event.EventType;
import me.valk.event.events.other.EventPacket;
import me.valk.event.events.player.EventPlayerUpdate;
import me.valk.module.ModData;
import me.valk.module.ModType;
import me.valk.module.Module;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.Container;
import net.minecraft.network.play.server.S30PacketWindowItems;

public class ChestStealer extends Module {

    private S30PacketWindowItems packet;
    private boolean shouldEmptyChest;
    private int delay;
    private int currentSlot;
    private int[] whitelist;

    public ChestStealer() {
        super(new ModData("ChestStealer", 0, new Color(220, 156, 53)), ModType.OTHER);
        this.delay = 0;
        this.whitelist = new int[]{54};
    }

    private int getNextSlot(Container container) {
        for (int i = 0, slotAmount = (container.inventorySlots.size() == 90) ? 54 : 27; i < slotAmount; ++i) {
            if (container.getInventory().get(i) != null) {
                return i;
            }
        }
        return -1;
    }

    public boolean isContainerEmpty(Container container) {
        boolean temp = true;
        for (int i = 0, slotAmount = (container.inventorySlots.size() == 90) ? 54 : 27; i < slotAmount; ++i) {
            if (container.getSlot(i).getHasStack()) {
                temp = false;
            }
        }
        return temp;
    }

    @EventListener
    public void onUpdate(EventPlayerUpdate event) {
        if (event.getType() == EventType.PRE) {
            try {
                if (!this.mc.inGameHasFocus && this.packet != null && this.mc.thePlayer.openContainer.windowId == this.packet.func_148911_c() && mc.currentScreen instanceof GuiChest) {
                    if (!this.isContainerEmpty(this.mc.thePlayer.openContainer)) {
                        int slotId = this.getNextSlot(this.mc.thePlayer.openContainer);
                        if (delay >= 1.45) {
                            mc.playerController.windowClick(p.openContainer.windowId, slotId, 0, 1, this.mc.thePlayer);
                            delay = 0;
                        }
                        ++delay;
                    } else {
                        p.closeScreen();
                        packet = null;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @EventListener
    public void onPacket(EventPacket event) {
        if (event.getType() == EventPacket.EventPacketType.RECEIVE && event.getPacket() instanceof S30PacketWindowItems) {
            this.packet = (S30PacketWindowItems) event.getPacket();
        }
    }
}