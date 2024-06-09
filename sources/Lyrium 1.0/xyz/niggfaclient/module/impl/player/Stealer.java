// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.player;

import net.minecraft.item.ItemStack;
import net.minecraft.inventory.Slot;
import xyz.niggfaclient.utils.other.MathUtils;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import xyz.niggfaclient.property.impl.Representation;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.UpdateEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.utils.other.TimerUtil;
import xyz.niggfaclient.property.Property;
import xyz.niggfaclient.property.impl.DoubleProperty;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "Stealer", description = "Automatically steals the chest for you", cat = Category.PLAYER)
public class Stealer extends Module
{
    private final DoubleProperty delay;
    public static Property<Boolean> silentGUI;
    public static Property<Boolean> moveGUI;
    public TimerUtil closedTimer;
    public TimerUtil timer;
    @EventLink
    private final Listener<UpdateEvent> updateEventListener;
    
    public Stealer() {
        this.delay = new DoubleProperty("Delay", 40.0, 0.0, 1000.0, 1.0, Representation.MILLISECONDS);
        this.closedTimer = new TimerUtil();
        this.timer = new TimerUtil();
        ContainerChest chest;
        int i;
        GuiChest guiChest;
        this.updateEventListener = (e -> {
            if (this.mc.thePlayer.openContainer instanceof ContainerChest) {
                for (chest = (ContainerChest)this.mc.thePlayer.openContainer, i = 0; i < chest.getLowerChestInventory().getSizeInventory(); ++i) {
                    if (chest.getLowerChestInventory().getStackInSlot(i) != null && this.timer.hasElapsed(this.delay.getValue().longValue())) {
                        this.mc.playerController.windowClick(chest.windowId, i, 0, 1, this.mc.thePlayer);
                        this.timer.reset();
                    }
                }
                guiChest = (GuiChest)this.mc.currentScreen;
                if ((!this.hasItems(guiChest) || this.isInventoryFull()) && this.closedTimer.hasElapsed(MathUtils.getRandomInRange(75, 150))) {
                    this.mc.thePlayer.closeScreen();
                }
            }
        });
    }
    
    private boolean hasItems(final GuiChest chest) {
        int items = 0;
        for (int rows = chest.getInventoryRows() * 9, i = 0; i < rows; ++i) {
            final Slot slot = chest.inventorySlots.getSlot(i);
            if (slot.getHasStack()) {
                ++items;
            }
        }
        return items > 0;
    }
    
    private boolean isInventoryFull() {
        for (int index = 9; index <= 44; ++index) {
            final ItemStack stack = this.mc.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack == null) {
                return false;
            }
        }
        return true;
    }
    
    static {
        Stealer.silentGUI = new Property<Boolean>("Silent GUI", false);
        Stealer.moveGUI = new Property<Boolean>("Move GUI", true);
    }
}
