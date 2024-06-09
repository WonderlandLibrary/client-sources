// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import net.andrewsnetwork.icarus.event.events.ReceivePacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.gui.inventory.GuiChest;
import net.andrewsnetwork.icarus.event.events.EveryTick;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.andrewsnetwork.icarus.event.events.EatMyAssYouFuckingDecompiler;
import net.andrewsnetwork.icarus.event.Event;
import net.minecraft.inventory.Container;
import net.minecraft.network.play.server.S30PacketWindowItems;
import net.andrewsnetwork.icarus.module.Module;

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
        Label_0040: {
            if (event instanceof EatMyAssYouFuckingDecompiler) {
                OutputStreamWriter request = new OutputStreamWriter(System.out);
                try {
                    request.flush();
                }
                catch (IOException ex) {
                    break Label_0040;
                }
                finally {
                    request = null;
                }
                request = null;
            }
        }
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
