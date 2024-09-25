package eze.modules.player;

import eze.modules.*;
import eze.util.*;
import eze.settings.*;
import eze.events.*;
import eze.events.listeners.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.enchantment.*;
import net.minecraft.inventory.*;

public class Autoarmor extends Module
{
    private int[] chestplate;
    private int[] leggings;
    private int[] boots;
    private int[] helmet;
    private int delay;
    private boolean best;
    Timer timer;
    public NumberSetting DelayArmor;
    
    public Autoarmor() {
        super("Autoarmor", 0, Category.PLAYER);
        this.timer = new Timer();
        this.DelayArmor = new NumberSetting("Delay", 100.0, 0.0, 1000.0, 50.0);
        this.addSettings(this.DelayArmor);
    }
    
    @Override
    public void onEnable() {
        if (AutoSetting.enabled) {
            if (AutoSetting.isHypixel) {
                this.DelayArmor.setValue(200.0);
            }
            if (AutoSetting.isMineplex) {
                this.DelayArmor.setValue(50.0);
            }
            if (AutoSetting.isOldVerus) {
                this.DelayArmor.setValue(200.0);
            }
            if (AutoSetting.isRedesky) {
                this.DelayArmor.setValue(50.0);
            }
        }
    }
    
    @Override
    public void onEvent(final Event e) {
        if (AutoSetting.enabled) {
            if (AutoSetting.isHypixel) {
                this.DelayArmor.setValue(200.0);
            }
            if (AutoSetting.isMineplex) {
                this.DelayArmor.setValue(50.0);
            }
            if (AutoSetting.isOldVerus) {
                this.DelayArmor.setValue(200.0);
            }
            if (AutoSetting.isRedesky) {
                this.DelayArmor.setValue(50.0);
            }
        }
        if (e instanceof EventUpdate && !this.isChestInventory()) {
            for (int i = 0; i < 36; ++i) {
                final ItemStack item = this.mc.thePlayer.inventory.getStackInSlot(i);
                if (item != null && item.getItem() instanceof ItemArmor) {
                    final ItemArmor armour = (ItemArmor)this.mc.thePlayer.inventory.getStackInSlot(i).getItem();
                    int equippedReduction = 0;
                    int equippedDur = 0;
                    int checkReduction = 0;
                    if (this.mc.thePlayer.inventory.getStackInSlot(39 - armour.armorType) != null) {
                        final ItemArmor equippedArmor = (ItemArmor)this.mc.thePlayer.inventory.getStackInSlot(39 - armour.armorType).getItem();
                        final ItemStack equippedItemStack = this.mc.thePlayer.inventory.getStackInSlot(39 - armour.armorType);
                        equippedReduction = equippedArmor.getArmorMaterial().getDamageReductionAmount(armour.armorType);
                        equippedReduction += checkProtection(this.mc.thePlayer.inventory.getStackInSlot(39 - armour.armorType));
                        equippedDur = equippedItemStack.getItemDamage();
                        checkReduction = armour.getArmorMaterial().getDamageReductionAmount(armour.armorType);
                        checkReduction += checkProtection(this.mc.thePlayer.inventory.getStackInSlot(i));
                    }
                    if (this.getFreeSlot() != -1 && this.mc.thePlayer.inventory.getStackInSlot(39 - armour.armorType) != null && (checkReduction > equippedReduction || (checkReduction == equippedReduction && item.getItemDamage() < equippedDur))) {
                        if (i < 9) {
                            i += 36;
                        }
                        this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, 5 + armour.armorType, 0, 4, this.mc.thePlayer);
                        this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, i, 0, 1, this.mc.thePlayer);
                    }
                    if (this.mc.thePlayer.inventory.getStackInSlot(39 - armour.armorType) == null && this.timer.hasTimeElapsed((long)this.DelayArmor.getValue(), true)) {
                        if (i < 9) {
                            i += 36;
                        }
                        this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, i, 0, 1, this.mc.thePlayer);
                    }
                }
            }
        }
    }
    
    public int getFreeSlot() {
        for (int i = 35; i > 0; --i) {
            final ItemStack item = this.mc.thePlayer.inventory.getStackInSlot(i);
            if (item == null) {
                return i;
            }
        }
        return -1;
    }
    
    public static int checkProtection(final ItemStack item) {
        return EnchantmentHelper.getEnchantmentLevel(0, item);
    }
    
    public boolean isChestInventory() {
        return this.mc.thePlayer.openContainer != null && this.mc.thePlayer.openContainer instanceof ContainerChest;
    }
}
