// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.player;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemTool;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import events.listeners.EventUpdate;
import events.Event;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.utils.TimeUtil;
import intent.AquaDev.aqua.modules.Module;

public class ChestStealer extends Module
{
    TimeUtil time;
    
    public ChestStealer() {
        super("ChestStealer", Type.Player, "ChestStealer", 0, Category.Player);
        this.time = new TimeUtil();
        Aqua.setmgr.register(new Setting("Delay", this, 50.0, 0.0, 1000.0, false));
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventUpdate) {
            final float delay = (float)Aqua.setmgr.getSetting("ChestStealerDelay").getCurrentNumber();
            if (ChestStealer.mc.thePlayer.openContainer instanceof ContainerChest) {
                final ContainerChest container = (ContainerChest)ChestStealer.mc.thePlayer.openContainer;
                for (int i = 0; i < container.getLowerChestInventory().getSizeInventory(); ++i) {
                    if (container.getLowerChestInventory().getStackInSlot(i) != null && this.time.hasReached((long)delay)) {
                        ChestStealer.mc.playerController.windowClick(container.windowId, i, 0, 1, ChestStealer.mc.thePlayer);
                        this.time.reset();
                    }
                }
                final GuiChest chest = (GuiChest)ChestStealer.mc.currentScreen;
                if (this.isChestEmpty(chest) || this.isInventoryFull()) {
                    ChestStealer.mc.thePlayer.closeScreen();
                }
            }
        }
    }
    
    private boolean isChestEmpty(final GuiChest chest) {
        for (int index = 0; index < chest.getLowerChestInventory().getSizeInventory(); ++index) {
            final ItemStack stack = chest.getLowerChestInventory().getStackInSlot(index);
            if (stack != null && this.isValidItem(stack)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean isInventoryFull() {
        for (int index = 9; index <= 44; ++index) {
            final ItemStack stack = ChestStealer.mc.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack == null) {
                return false;
            }
        }
        return true;
    }
    
    private boolean isValidItem(final ItemStack itemStack) {
        return itemStack.getItem() instanceof ItemArmor || itemStack.getItem() instanceof ItemSword || itemStack.getItem() instanceof ItemTool || itemStack.getItem() instanceof ItemFood || itemStack.getItem() instanceof ItemPotion || itemStack.getItem() instanceof ItemBlock;
    }
}
