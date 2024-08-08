package net.futureclient.client.modules.miscellaneous.cheststealer;

import net.futureclient.client.Ac;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.futureclient.client.tB;
import net.futureclient.loader.mixin.common.gui.wrapper.IGuiChest;
import net.minecraft.client.gui.inventory.GuiChest;
import net.futureclient.client.events.EventUpdate;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.miscellaneous.ChestStealer;
import net.futureclient.client.lF;
import net.futureclient.client.n;

public class Listener1 extends n<lF>
{
    public final ChestStealer k;
    
    public Listener1(final ChestStealer k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventUpdate)event);
    }
    
    public void M(final EventUpdate eventUpdate) {
        if (!(ChestStealer.getMinecraft4().currentScreen instanceof GuiChest)) {
            return;
        }
        final GuiChest guiChest = (GuiChest)ChestStealer.getMinecraft9().currentScreen;
        if (ChestStealer.b().contains(((IGuiChest)guiChest).getLowerChestInventory().getDisplayName().getUnformattedText())) {
            return;
        }
        if (((tB.Sd)ChestStealer.M(this.k).M()).equals((Object)tB.Sd.k)) {
            int sizeInventory;
            int i = sizeInventory = ((IGuiChest)guiChest).getLowerChestInventory().getSizeInventory();
            while (i <= ((IGuiChest)guiChest).getLowerChestInventory().getSizeInventory() + 35) {
                if (!ChestStealer.getMinecraft5().player.inventoryContainer.getSlot(sizeInventory - ((IGuiChest)guiChest).getLowerChestInventory().getSizeInventory() + 9).getStack().isEmpty() && ChestStealer.M(this.k).e(ChestStealer.M(this.k).B().floatValue() * 1000.0f)) {
                    ChestStealer.getMinecraft3().playerController.windowClick(guiChest.inventorySlots.windowId, sizeInventory, 0, ClickType.QUICK_MOVE, (EntityPlayer)ChestStealer.getMinecraft1().player);
                    ChestStealer.M(this.k).e();
                    return;
                }
                i = ++sizeInventory;
            }
        }
        else {
            int j = 0;
            int n = 0;
            while (j < ((IGuiChest)guiChest).getLowerChestInventory().getSizeInventory()) {
                if (!((IGuiChest)guiChest).getLowerChestInventory().getStackInSlot(n).isEmpty() && ChestStealer.M(this.k).e(ChestStealer.M(this.k).B().floatValue() * 1000.0f)) {
                    Listener1 listener1 = null;
                    Label_0447: {
                        switch (Ac.k[((tB.Sd)ChestStealer.M(this.k).M()).ordinal()]) {
                            case 1:
                                ChestStealer.getMinecraft7().playerController.windowClick(guiChest.inventorySlots.windowId, n, 0, ClickType.QUICK_MOVE, (EntityPlayer)ChestStealer.getMinecraft8().player);
                                listener1 = this;
                                break Label_0447;
                            case 2:
                                ChestStealer.getMinecraft6().playerController.windowClick(guiChest.inventorySlots.windowId, n, 0, ClickType.PICKUP, (EntityPlayer)ChestStealer.getMinecraft2().player);
                                ChestStealer.getMinecraft().playerController.windowClick(guiChest.inventorySlots.windowId, -999, 0, ClickType.PICKUP, (EntityPlayer)ChestStealer.getMinecraft10().player);
                                break;
                        }
                        listener1 = this;
                    }
                    ChestStealer.M(listener1.k).e();
                }
                j = ++n;
            }
        }
    }
}
