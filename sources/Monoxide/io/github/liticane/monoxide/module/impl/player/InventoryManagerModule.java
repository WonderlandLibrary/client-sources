package io.github.liticane.monoxide.module.impl.player;

import io.github.liticane.monoxide.module.ModuleManager;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.value.impl.BooleanValue;
import io.github.liticane.monoxide.value.impl.NumberValue;
import io.github.liticane.monoxide.listener.event.minecraft.input.GuiHandleEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;
import io.github.liticane.monoxide.util.interfaces.Methods;
import io.github.liticane.monoxide.util.math.random.RandomUtil;
import io.github.liticane.monoxide.util.math.time.TimeHelper;

import java.util.Arrays;
import java.util.List;

@ModuleData(name = "InventoryManager", description = "Cleans and sorts your inventory", category = ModuleCategory.PLAYER)
public class InventoryManagerModule extends Module {
    private final BooleanValue openInventory = new BooleanValue("Open Inventory", this, true);
    private final NumberValue<Long> minStartDelay = new NumberValue<Long>("Minimum Start Delay", this, 250L, 0L, 1000L, 0),
            maxStartDelay = new NumberValue<Long>("Maximum Start Delay", this, 300L, 0L, 1000L, 0),
            minThrowDelay = new NumberValue<Long>("Minimum Throw Delay", this, 250L, 0L, 1000L, 0),
            maxThrowDelay = new NumberValue<Long>("Maximum Throw Delay", this, 300L, 0L, 1000L, 0);
    private final BooleanValue preferSwords = new BooleanValue("Prefer Swords", this, true),
            keepTools = new BooleanValue("Keep Tools", this, true);
    private final NumberValue<Integer> weaponSlot = new NumberValue<Integer>("Weapon Slot", this, 1, 0, 9, 0);
    private final NumberValue<Integer> bowSlot = new NumberValue<Integer>("Bow Slot", this, 2, 0, 9, 0);
    private final NumberValue<Integer> pickaxeSlot = new NumberValue<Integer>("Pickaxe Slot", this, 0, 0, 9, 0);
    private final NumberValue<Integer> axeSlot = new NumberValue<Integer>("Axe Slot", this, 0, 0, 9, 0);
    private final NumberValue<Integer> shovelSlot = new NumberValue<Integer>("Shovel Slot", this, 0, 0, 9, 0);

    private final List<Item> trashItems = Arrays.asList(Items.dye, Items.paper, Items.saddle, Items.string, Items.banner, Items.fishing_rod);
    private AutoArmorModule autoArmor;
    private final TimeHelper timeHelper = new TimeHelper();
    private final TimeHelper throwTimer = new TimeHelper();

    @Listen
    public void onGui(GuiHandleEvent guiHandleEvent) {
        if(autoArmor == null)
            autoArmor = ModuleManager.getInstance().getModule(AutoArmorModule.class);
        if (Methods.mc.currentScreen instanceof GuiInventory) {
            if (!timeHelper.hasReached((long) (RandomUtil.randomBetween(this.minStartDelay.getValue(), this.maxStartDelay.getValue())))) {
                throwTimer.reset();
                return;
            }
        } else {
            timeHelper.reset();
            if (openInventory.getValue())
                return;
        }

        if (autoArmor.isEnabled() && !autoArmor.isFinished()) {
            timeHelper.reset();
            throwTimer.reset();
            if (openInventory.getValue())
                return;
        }

        for (int i = 9; i < 45; i++) {
            if (getPlayer().inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = getPlayer().inventoryContainer.getSlot(i).getStack();
                long throwDelay = (long) RandomUtil.randomBetween(this.minThrowDelay.getValue(), this.maxThrowDelay.getValue());
                if (throwTimer.hasReached(throwDelay)) {
                    if (weaponSlot.getValue() != 0 && (is.getItem() instanceof ItemSword || is.getItem() instanceof ItemAxe || is.getItem() instanceof ItemPickaxe) && is == bestWeapon() && Methods.mc.thePlayer.inventoryContainer.getInventory().contains(bestWeapon()) && Methods.mc.thePlayer.inventoryContainer.getSlot(35 + weaponSlot.getValue()).getStack() != is && !preferSwords.getValue()) {
                        getPlayerController().windowClick(Methods.mc.thePlayer.inventoryContainer.windowId, i, weaponSlot.getValue() - 1, 2, Methods.mc.thePlayer);
                        throwTimer.reset();
                        if (throwDelay != 0) {
                            break;
                        }
                    } else if (weaponSlot.getValue() != 0 && is.getItem() instanceof ItemSword && is == bestSword() && Methods.mc.thePlayer.inventoryContainer.getInventory().contains(bestSword()) && Methods.mc.thePlayer.inventoryContainer.getSlot(35 + weaponSlot.getValue()).getStack() != is && preferSwords.getValue()) {
                        getPlayerController().windowClick(Methods.mc.thePlayer.inventoryContainer.windowId, i, weaponSlot.getValue() - 1, 2, Methods.mc.thePlayer);
                        throwTimer.reset();
                        if (throwDelay != 0) {
                            break;
                        }
                    } else if (bowSlot.getValue() != 0 && is.getItem() instanceof ItemBow && is == bestBow() && Methods.mc.thePlayer.inventoryContainer.getInventory().contains(bestBow()) && Methods.mc.thePlayer.inventoryContainer.getSlot(35 + bowSlot.getValue()).getStack() != is) {
                        getPlayerController().windowClick(Methods.mc.thePlayer.inventoryContainer.windowId, i, bowSlot.getValue() - 1, 2, Methods.mc.thePlayer);
                        throwTimer.reset();
                        if (throwDelay != 0) {
                            break;
                        }
                    } else if (pickaxeSlot.getValue() != 0 && is.getItem() instanceof ItemPickaxe && is == bestPick() && is != bestWeapon() && keepTools.getValue() && Methods.mc.thePlayer.inventoryContainer.getInventory().contains(bestPick()) && Methods.mc.thePlayer.inventoryContainer.getSlot(35 + pickaxeSlot.getValue()).getStack() != is) {
                        getPlayerController().windowClick(Methods.mc.thePlayer.inventoryContainer.windowId, i, pickaxeSlot.getValue() - 1, 2, Methods.mc.thePlayer);
                        throwTimer.reset();
                        if (throwDelay != 0) {
                            break;
                        }
                    } else if (axeSlot.getValue() != 0 && is.getItem() instanceof ItemAxe && is == bestAxe() && is != bestWeapon() && keepTools.getValue() && Methods.mc.thePlayer.inventoryContainer.getInventory().contains(bestAxe()) && Methods.mc.thePlayer.inventoryContainer.getSlot(35 + axeSlot.getValue()).getStack() != is) {
                        getPlayerController().windowClick(Methods.mc.thePlayer.inventoryContainer.windowId, i, axeSlot.getValue() - 1, 2, Methods.mc.thePlayer);
                        throwTimer.reset();
                        if (throwDelay != 0) {
                            break;
                        }
                    } else if (shovelSlot.getValue() != 0 && is.getItem() instanceof ItemSpade && is == bestShovel() && is != bestWeapon() && keepTools.getValue() && Methods.mc.thePlayer.inventoryContainer.getInventory().contains(bestShovel()) && Methods.mc.thePlayer.inventoryContainer.getSlot(35 + shovelSlot.getValue()).getStack() != is) {
                        getPlayerController().windowClick(Methods.mc.thePlayer.inventoryContainer.windowId, i, shovelSlot.getValue() - 1, 2, Methods.mc.thePlayer);
                        throwTimer.reset();
                        if (throwDelay != 0) {
                            break;
                        }
                    } else if (trashItems.contains(is.getItem()) || isBadStack(is)) {
                        getPlayerController().windowClick(Methods.mc.thePlayer.inventoryContainer.windowId, i, 1, 4, Methods.mc.thePlayer);
                        throwTimer.reset();
                        if (throwDelay != 0) {
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {}

    public boolean isBadStack(ItemStack is) {
        if ((is.getItem() instanceof ItemSword) && is != bestWeapon() && !preferSwords.getValue())
            return true;
        if (is.getItem() instanceof ItemSword && is != bestSword() && preferSwords.getValue())
            return true;
        if (is.getItem() instanceof ItemBow && is != bestBow())
            return true;
        if (keepTools.getValue()) {
            if (is.getItem() instanceof ItemAxe && is != bestAxe() && (preferSwords.getValue() || is != bestWeapon()))
                return true;
            if (is.getItem() instanceof ItemPickaxe && is != bestPick() && (preferSwords.getValue() || is != bestWeapon()))
                return true;
            return is.getItem() instanceof ItemSpade && is != bestShovel();
        } else {
            if (is.getItem() instanceof ItemAxe && (preferSwords.getValue() || is != bestWeapon()))
                return true;
            if (is.getItem() instanceof ItemPickaxe && (preferSwords.getValue() || is != bestWeapon()))
                return true;
            return is.getItem() instanceof ItemSpade;
        }
    }

    public ItemStack bestWeapon() {
        ItemStack bestWeapon = null;
        float itemDamage = -1;

        for (int i = 9; i < 45; i++) {
            if (getPlayer().inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = getPlayer().inventoryContainer.getSlot(i).getStack();
                if (is.getItem() instanceof ItemSword || is.getItem() instanceof ItemAxe || is.getItem() instanceof ItemPickaxe) {
                    float toolDamage = getItemDamage(is);
                    if (toolDamage >= itemDamage) {
                        itemDamage = getItemDamage(is);
                        bestWeapon = is;
                    }
                }
            }
        }

        return bestWeapon;
    }

    public ItemStack bestSword() {
        ItemStack bestSword = null;
        float itemDamage = -1;

        for (int i = 9; i < 45; i++) {
            if (getPlayer().inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = getPlayer().inventoryContainer.getSlot(i).getStack();
                if (is.getItem() instanceof ItemSword) {
                    float swordDamage = getItemDamage(is);
                    if (swordDamage >= itemDamage) {
                        itemDamage = getItemDamage(is);
                        bestSword = is;
                    }
                }
            }
        }

        return bestSword;
    }

    public ItemStack bestBow() {
        ItemStack bestBow = null;
        float itemDamage = -1;

        for (int i = 9; i < 45; i++) {
            if (getPlayer().inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = getPlayer().inventoryContainer.getSlot(i).getStack();
                if (is.getItem() instanceof ItemBow) {
                    float bowDamage = getBowDamage(is);
                    if (bowDamage >= itemDamage) {
                        itemDamage = getBowDamage(is);
                        bestBow = is;
                    }
                }
            }
        }

        return bestBow;
    }

    public ItemStack bestAxe() {
        ItemStack bestTool = null;
        float itemSkill = -1;

        for (int i = 9; i < 45; i++) {
            if (getPlayer().inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = getPlayer().inventoryContainer.getSlot(i).getStack();
                if (is.getItem() instanceof ItemAxe) {
                    float toolSkill = getToolRating(is);
                    if (toolSkill >= itemSkill) {
                        itemSkill = getToolRating(is);
                        bestTool = is;
                    }
                }
            }
        }

        return bestTool;
    }

    public ItemStack bestPick() {
        ItemStack bestTool = null;
        float itemSkill = -1;

        for (int i = 9; i < 45; i++) {
            if (getPlayer().inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = getPlayer().inventoryContainer.getSlot(i).getStack();
                if (is.getItem() instanceof ItemPickaxe) {
                    float toolSkill = getToolRating(is);
                    if (toolSkill >= itemSkill) {
                        itemSkill = getToolRating(is);
                        bestTool = is;
                    }
                }
            }
        }

        return bestTool;
    }

    public ItemStack bestShovel() {
        ItemStack bestTool = null;
        float itemSkill = -1;

        for (int i = 9; i < 45; i++) {
            if (getPlayer().inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = getPlayer().inventoryContainer.getSlot(i).getStack();
                if (is.getItem() instanceof ItemSpade) {
                    float toolSkill = getToolRating(is);
                    if (toolSkill >= itemSkill) {
                        itemSkill = getToolRating(is);
                        bestTool = is;
                    }
                }
            }
        }

        return bestTool;
    }

    public float getToolRating(ItemStack itemStack) {
        float damage = getToolMaterialRating(itemStack, false);
        damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack) * 2.00F;
        damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch.effectId, itemStack) * 0.50F;
        damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, itemStack) * 0.50F;
        damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemStack) * 0.10F;
        damage += (itemStack.getMaxDamage() - itemStack.getItemDamage()) * 0.000000000001F;
        return damage;
    }

    public float getItemDamage(ItemStack itemStack) {
        float damage = getToolMaterialRating(itemStack, true);
        damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack) * 1.25F;
        damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, itemStack) * 0.50F;
        damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemStack) * 0.01F;
        damage += (itemStack.getMaxDamage() - itemStack.getItemDamage()) * 0.000000000001F;

        if (itemStack.getItem() instanceof ItemSword)
            damage += 0.2;
        return damage;
    }

    public float getBowDamage(ItemStack itemStack) {
        float damage = 5;
        damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemStack) * 1.25F;
        damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemStack) * 0.75F;
        damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemStack) * 0.50F;
        damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemStack) * 0.10F;
        damage += itemStack.getMaxDamage() - itemStack.getItemDamage() * 0.001F;
        return damage;
    }

    public float getToolMaterialRating(ItemStack itemStack, boolean checkForDamage) {
        final Item is = itemStack.getItem();
        float rating = 0;

        if (is instanceof ItemSword) {
            switch (((ItemSword) is).getToolMaterialName()) {
                case "GOLD":
                case "WOOD":
                    rating = 4;
                    break;
                case "STONE":
                    rating = 5;
                    break;
                case "IRON":
                    rating = 6;
                    break;
                case "EMERALD":
                    rating = 7;
                    break;
            }
        } else if (is instanceof ItemPickaxe) {
            switch (((ItemPickaxe) is).getToolMaterialName()) {
                case "GOLD":
                case "WOOD":
                    rating = 2;
                    break;
                case "STONE":
                    rating = 3;
                    break;
                case "IRON":
                    rating = checkForDamage ? 4 : 40;
                    break;
                case "EMERALD":
                    rating = checkForDamage ? 5 : 50;
                    break;
                default:
                    break;
            }
        } else if (is instanceof ItemAxe) {
            switch (((ItemAxe) is).getToolMaterialName()) {
                case "GOLD":
                case "WOOD":
                    rating = 3;
                    break;
                case "STONE":
                    rating = 4;
                    break;
                case "IRON":
                    rating = 5;
                    break;
                case "EMERALD":
                    rating = 6;
                    break;
                default:
                    break;
            }
        } else if (is instanceof ItemSpade) {
            switch (((ItemSpade) is).getToolMaterialName()) {
                case "GOLD":
                case "WOOD":
                    rating = 1;
                    break;
                case "STONE":
                    rating = 2;
                    break;
                case "IRON":
                    rating = 3;
                    break;
                case "EMERALD":
                    rating = 4;
                    break;
                default:
                    break;
            }
        }

        return rating;
    }
}
