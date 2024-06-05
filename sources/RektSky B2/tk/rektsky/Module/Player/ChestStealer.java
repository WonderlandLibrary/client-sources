package tk.rektsky.Module.Player;

import tk.rektsky.Module.*;
import tk.rektsky.Module.Settings.*;
import tk.rektsky.Event.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.server.*;
import tk.rektsky.Event.Events.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.network.*;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;

public class ChestStealer extends Module
{
    private Container inventory;
    private IInventory iinventory;
    private Integer windowId;
    private Integer ticks;
    private BooleanSetting slientSetting;
    
    public ChestStealer() {
        super("ChestStealer", "Grab all items from chest very fast", 0, Category.PLAYER);
        this.ticks = 0;
        this.registerSetting(this.slientSetting = new BooleanSetting("Slient", true));
    }
    
    @Override
    public String getSuffix() {
        if (this.slientSetting.getValue()) {
            return "Slient";
        }
        return "OpenInv";
    }
    
    @Override
    public void onEvent(final Event e) {
        try {
            if (e instanceof PacketReceiveEvent && this.slientSetting.getValue()) {
                final Packet p = ((PacketReceiveEvent)e).getPacket();
                if (((PacketReceiveEvent)e).getPacket() instanceof S2DPacketOpenWindow) {
                    final S2DPacketOpenWindow packet = (S2DPacketOpenWindow)((PacketReceiveEvent)e).getPacket();
                    if (packet.getWindowTitle().getUnformattedTextForChat().equalsIgnoreCase("chest")) {
                        ((PacketReceiveEvent)e).setCanceled(true);
                        this.iinventory = new InventoryBasic(packet.getWindowTitle(), packet.getSlotCount());
                        this.inventory = new ContainerChest(this.mc.thePlayer.inventory, this.iinventory, this.mc.thePlayer);
                        this.windowId = packet.getWindowId();
                    }
                }
                if (p instanceof S30PacketWindowItems) {
                    final S30PacketWindowItems packet2 = (S30PacketWindowItems)((PacketReceiveEvent)e).getPacket();
                    if (this.windowId == null) {
                        return;
                    }
                    if (packet2.getWindowID() != this.windowId) {
                        return;
                    }
                    this.inventory.putStacksInSlots(packet2.getItemStacks());
                    ((PacketReceiveEvent)e).setCanceled(true);
                }
            }
            if (e instanceof WorldTickEvent) {
                final Integer ticks = this.ticks;
                ++this.ticks;
                if (this.ticks % 1 == 0) {
                    for (int i = 0; i <= 10; ++i) {
                        boolean isChest = false;
                        if (!this.slientSetting.getValue()) {
                            this.inventory = this.mc.thePlayer.openContainer;
                            isChest = (this.mc.currentScreen instanceof GuiChest && ((GuiChest)this.mc.currentScreen).lowerChestInventory.getDisplayName().getUnformattedText().equalsIgnoreCase("chest"));
                        }
                        else {
                            isChest = (this.windowId != null);
                        }
                        if (isChest) {
                            if (!this.empty(this.inventory)) {
                                final int index = this.next(this.inventory);
                                if (this.slientSetting.getValue()) {
                                    final short short1 = this.inventory.getNextTransactionID(this.mc.thePlayer.inventory);
                                    this.mc.getNetHandler().addToSendQueue(new C0EPacketClickWindow(this.windowId, index, 0, 1, this.inventory.getSlot(index).getStack(), short1));
                                    this.inventory.slotClick(index, 0, 1, this.mc.thePlayer);
                                }
                                else {
                                    this.mc.playerController.windowClick(this.mc.thePlayer.openContainer.windowId, index, 0, 1, this.mc.thePlayer);
                                }
                                if (this.slientSetting.getValue()) {
                                    this.inventory.putStackInSlot(index, null);
                                }
                            }
                            else if (this.slientSetting.getValue()) {
                                this.mc.getNetHandler().addToSendQueue(new C0DPacketCloseWindow(this.inventory.windowId));
                                this.windowId = null;
                            }
                            else {
                                this.mc.thePlayer.closeScreen();
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }
    
    private boolean empty(final Container c) {
        for (int slot = (c.inventorySlots.size() == 90) ? 54 : 27, i = 0; i < slot; ++i) {
            if (c.getSlot(i).getHasStack()) {
                return false;
            }
        }
        return true;
    }
    
    private int next(final Container c) {
        for (int slot = (c.inventorySlots.size() == 90) ? 54 : 27, i = 0; i < slot; ++i) {
            if (c.getInventory().get(i) != null) {
                return i;
            }
        }
        return -1;
    }
}
