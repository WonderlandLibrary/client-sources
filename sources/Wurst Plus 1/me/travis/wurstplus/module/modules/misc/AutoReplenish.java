// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.module.modules.misc;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.init.Items;
import me.travis.wurstplus.util.Pair;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.client.gui.inventory.GuiContainer;
import java.util.HashMap;
import net.minecraft.item.ItemStack;
import java.util.Map;
import me.travis.wurstplus.setting.Settings;
import me.travis.wurstplus.setting.Setting;
import me.travis.wurstplus.module.Module;

@Module.Info(name = "AutoReplenish", category = Module.Category.MISC)
public class AutoReplenish extends Module
{
    private Setting<Integer> threshold;
    private Setting<Integer> tickDelay;
    private int delayStep;
    
    public AutoReplenish() {
        this.threshold = this.register((Setting<Integer>)Settings.integerBuilder("Threshold").withMinimum(1).withValue(32).withMaximum(63).build());
        this.tickDelay = this.register((Setting<Integer>)Settings.integerBuilder("TickDelay").withMinimum(1).withValue(2).withMaximum(10).build());
        this.delayStep = 0;
    }
    
    private static Map<Integer, ItemStack> getInventory() {
        return getInventorySlots(9, 35);
    }
    
    private static Map<Integer, ItemStack> getHotbar() {
        return getInventorySlots(36, 44);
    }
    
    private static Map<Integer, ItemStack> getInventorySlots(int current, final int last) {
        final Map<Integer, ItemStack> fullInventorySlots = new HashMap<Integer, ItemStack>();
        while (current <= last) {
            fullInventorySlots.put(current, (ItemStack)AutoReplenish.mc.player.inventoryContainer.getInventory().get(current));
            ++current;
        }
        return fullInventorySlots;
    }
    
    @Override
    public void onUpdate() {
        if (AutoReplenish.mc.player == null || this.isDisabled()) {
            return;
        }
        if (AutoReplenish.mc.currentScreen instanceof GuiContainer) {
            return;
        }
        if (this.delayStep < this.tickDelay.getValue()) {
            ++this.delayStep;
            return;
        }
        this.delayStep = 0;
        final Pair<Integer, Integer> slots = this.findReplenishableHotbarSlot();
        if (slots == null) {
            return;
        }
        final int inventorySlot = slots.getKey();
        final int hotbarSlot = slots.getValue();
        AutoReplenish.mc.playerController.windowClick(0, inventorySlot, 0, ClickType.PICKUP, (EntityPlayer)AutoReplenish.mc.player);
        AutoReplenish.mc.playerController.windowClick(0, hotbarSlot, 0, ClickType.PICKUP, (EntityPlayer)AutoReplenish.mc.player);
        AutoReplenish.mc.playerController.windowClick(0, inventorySlot, 0, ClickType.PICKUP, (EntityPlayer)AutoReplenish.mc.player);
    }
    
    private Pair<Integer, Integer> findReplenishableHotbarSlot() {
        Pair<Integer, Integer> returnPair = null;
        for (final Map.Entry<Integer, ItemStack> hotbarSlot : getHotbar().entrySet()) {
            final ItemStack stack = hotbarSlot.getValue();
            if (!stack.isEmpty) {
                if (stack.getItem() == Items.AIR) {
                    continue;
                }
                if (!stack.isStackable()) {
                    continue;
                }
                if (stack.stackSize >= stack.getMaxStackSize()) {
                    continue;
                }
                if (stack.stackSize > this.threshold.getValue()) {
                    continue;
                }
                final int inventorySlot = this.findCompatibleInventorySlot(stack);
                if (inventorySlot == -1) {
                    continue;
                }
                returnPair = new Pair<Integer, Integer>(inventorySlot, hotbarSlot.getKey());
            }
        }
        return returnPair;
    }
    
    private int findCompatibleInventorySlot(final ItemStack hotbarStack) {
        int inventorySlot = -1;
        int smallestStackSize = 999;
        for (final Map.Entry<Integer, ItemStack> entry : getInventory().entrySet()) {
            final ItemStack inventoryStack = entry.getValue();
            if (!inventoryStack.isEmpty) {
                if (inventoryStack.getItem() == Items.AIR) {
                    continue;
                }
                if (!this.isCompatibleStacks(hotbarStack, inventoryStack)) {
                    continue;
                }
                final int currentStackSize = ((ItemStack)AutoReplenish.mc.player.inventoryContainer.getInventory().get((int)entry.getKey())).stackSize;
                if (smallestStackSize <= currentStackSize) {
                    continue;
                }
                smallestStackSize = currentStackSize;
                inventorySlot = entry.getKey();
            }
        }
        return inventorySlot;
    }
    
    private boolean isCompatibleStacks(final ItemStack stack1, final ItemStack stack2) {
        if (!stack1.getItem().equals(stack2.getItem())) {
            return false;
        }
        if (stack1.getItem() instanceof ItemBlock && stack2.getItem() instanceof ItemBlock) {
            final Block block1 = ((ItemBlock)stack1.getItem()).getBlock();
            final Block block2 = ((ItemBlock)stack2.getItem()).getBlock();
            if (!block1.material.equals(block2.material)) {
                return false;
            }
        }
        return stack1.getDisplayName().equals(stack2.getDisplayName()) && stack1.getItemDamage() == stack2.getItemDamage();
    }
}
