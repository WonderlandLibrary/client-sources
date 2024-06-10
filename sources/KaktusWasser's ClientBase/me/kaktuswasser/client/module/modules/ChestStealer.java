// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.module.modules;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.gui.inventory.GuiChest;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import me.kaktuswasser.client.event.Event;
import me.kaktuswasser.client.event.events.EatMyAssYouFuckingDecompiler;
import me.kaktuswasser.client.event.events.EveryTick;
import me.kaktuswasser.client.event.events.ReceivePacket;
import me.kaktuswasser.client.module.Module;
import net.minecraft.inventory.Container;
import net.minecraft.network.play.server.S30PacketWindowItems;

public class ChestStealer extends Module
{
    private S30PacketWindowItems packet;
    private boolean shouldEmptyChest;
    private int delay;
    private int currentSlot;
    private int[] whitelist;
    
    public ChestStealer() {
        super("ChestStealer", -2572328, Category.PLAYER);
        this.delay = 0;
        this.whitelist = new int[] { 54 };
        this.setTag("Chest Stealer");
    }
    
    private int getNextSlot(final Container container) {
        for (int i = 0, slotAmount = (container.inventorySlots.size() == 90) ? 54 : 27; i < slotAmount; ++i) {
            if (container.getInventory().get(i) != null) {
                return i;
            }
        }
        return -1;
    }
    
    public boolean isContainerEmpty(final Container container) {
        boolean temp = true;
        for (int i = 0, slotAmount = (container.inventorySlots.size() == 90) ? 54 : 27; i < slotAmount; ++i) {
            if (container.getSlot(i).getHasStack()) {
                temp = false;
            }
        }
        return temp;
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EveryTick) {
            try {
                if (!ChestStealer.mc.inGameHasFocus && this.packet != null && ChestStealer.mc.thePlayer.openContainer.windowId == this.packet.func_148911_c() && ChestStealer.mc.currentScreen instanceof GuiChest) {
                    if (!this.isContainerEmpty(ChestStealer.mc.thePlayer.openContainer)) {
                        final int slotId = this.getNextSlot(ChestStealer.mc.thePlayer.openContainer);
                        if (this.delay >= 2) {
                            ChestStealer.mc.playerController.windowClick(ChestStealer.mc.thePlayer.openContainer.windowId, slotId, 0, 1, ChestStealer.mc.thePlayer);
                            this.delay = 0;
                        }
                        ++this.delay;
                    }
                    else {
                        ChestStealer.mc.thePlayer.closeScreen();
                        this.packet = null;
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (event instanceof ReceivePacket) {
            final ReceivePacket rec = (ReceivePacket)event;
            if (rec.getPacket() instanceof S30PacketWindowItems) {
                this.packet = (S30PacketWindowItems)rec.getPacket();
            }
        }
    }
}
