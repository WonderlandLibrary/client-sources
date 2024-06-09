// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.player;

import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import events.listeners.EventClickMouse;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.MovingObjectPosition;
import intent.AquaDev.aqua.modules.combat.BedAura;
import events.listeners.EventUpdate;
import events.Event;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.modules.Module;

public class AutoTool extends Module
{
    private int slot;
    
    public AutoTool() {
        super("AutoTool", Type.Player, "AutoTool", 0, Category.Player);
        this.slot = 0;
        System.out.println("Sprint::init");
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
    public void onEvent(final Event e) {
        if (e instanceof EventUpdate && !BedAura.rotating) {
            double lastSpeed = 0.0;
            this.slot = AutoTool.mc.thePlayer.inventory.currentItem;
            if (AutoTool.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                for (int i = 36; i < AutoTool.mc.thePlayer.inventoryContainer.inventorySlots.size(); ++i) {
                    final ItemStack itemStack = AutoTool.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                    if (itemStack != null) {
                        final Item item = itemStack.getItem();
                        if (AutoTool.mc.gameSettings.keyBindAttack.isKeyDown() && (item instanceof ItemTool || item instanceof ItemSword)) {
                            final double toolSpeed = this.getToolSpeed(itemStack);
                            final double currentSpeed = this.getToolSpeed(AutoTool.mc.thePlayer.getHeldItem());
                            if (toolSpeed > 1.0 && toolSpeed > currentSpeed && toolSpeed > lastSpeed) {
                                this.slot = i - 36;
                                lastSpeed = toolSpeed;
                            }
                        }
                    }
                }
            }
        }
        if (e instanceof EventClickMouse && !BedAura.rotating) {
            ((EventClickMouse)e).setSlot(this.slot);
        }
    }
    
    private double getToolSpeed(final ItemStack itemStack) {
        double damage = 0.0;
        if (itemStack != null && (itemStack.getItem() instanceof ItemTool || itemStack.getItem() instanceof ItemSword)) {
            if (itemStack.getItem() instanceof ItemAxe) {
                damage += itemStack.getItem().getStrVsBlock(itemStack, AutoTool.mc.theWorld.getBlockState(AutoTool.mc.objectMouseOver.getBlockPos()).getBlock()) + EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack);
            }
            else if (itemStack.getItem() instanceof ItemPickaxe) {
                damage += itemStack.getItem().getStrVsBlock(itemStack, AutoTool.mc.theWorld.getBlockState(AutoTool.mc.objectMouseOver.getBlockPos()).getBlock()) + EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack);
            }
            else if (itemStack.getItem() instanceof ItemSpade) {
                damage += itemStack.getItem().getStrVsBlock(itemStack, AutoTool.mc.theWorld.getBlockState(AutoTool.mc.objectMouseOver.getBlockPos()).getBlock()) + EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack);
            }
            else if (itemStack.getItem() instanceof ItemSword) {
                damage += itemStack.getItem().getStrVsBlock(itemStack, AutoTool.mc.theWorld.getBlockState(AutoTool.mc.objectMouseOver.getBlockPos()).getBlock());
            }
            damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemStack) / 11.0;
            damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch.effectId, itemStack) / 11.0;
            damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack) / 33.0;
            damage -= itemStack.getItemDamage() / 10000.0;
            return damage;
        }
        return 0.0;
    }
}
