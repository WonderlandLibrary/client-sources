package koks.modules.impl.player;

import koks.Koks;
import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.modules.Module;
import koks.utilities.RandomUtil;
import koks.utilities.TimeUtil;
import koks.utilities.value.values.BooleanValue;
import koks.utilities.value.values.NumberValue;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.ChatComponentText;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author avox | lmao | kroko
 * @created on 05.09.2020 : 00:18
 */
public class InventoryManager extends Module {

    public List<Item> trashItems;
    public BooleanValue<Boolean> openedInventory = new BooleanValue<>("Opened Inventory", true, this);
    public NumberValue<Long> startDelay = new NumberValue<>("Start Delay", 100L, 500L, 0L, this);
    public NumberValue<Long> throwDelay = new NumberValue<>("Throw Delay", 90L, 125L, 150L, 0L, this);
    public BooleanValue<Boolean> preferSword = new BooleanValue<>("Prefer Sword", true, this);
    public BooleanValue<Boolean> keepTools = new BooleanValue<>("KeepTools", true, this);
    public NumberValue<Integer> swordSlot = new NumberValue<>("Weapon Slot", 1, 9, 0, this);
    public NumberValue<Integer> bowSlot = new NumberValue<>("Bow Slot", 2, 9, 0, this);
    public NumberValue<Integer> pickSlot = new NumberValue<>("Pickaxe Slot", 3, 9, 0, this);
    public NumberValue<Integer> axeSlot = new NumberValue<>("Axe Slot", 4, 9, 0, this);
    public NumberValue<Integer> shovelSlot = new NumberValue<>("Shovel Slot", 5, 9, 0, this);
    private AutoArmor autoArmor;
    private final RandomUtil randomUtil = new RandomUtil();
    private final TimeUtil startTimer = new TimeUtil();
    private final TimeUtil throwTimer = new TimeUtil();

    public InventoryManager() {
        super("InventoryManager", "Your inventory is clean", Category.PLAYER);

        trashItems = Arrays.asList(Items.feather, Items.dye, Items.paper, Items.saddle, Items.string, Items.banner, Items.fishing_rod, Items.boat);
        addValue(openedInventory);
        addValue(startDelay);
        addValue(throwDelay);
        addValue(preferSword);
        addValue(keepTools);
        addValue(swordSlot);
        addValue(bowSlot);
        addValue(pickSlot);
        addValue(axeSlot);
        addValue(shovelSlot);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            setModuleInfo(throwDelay.getMinDefaultValue() + ", " + throwDelay.getDefaultValue());
            if (mc.currentScreen instanceof GuiInventory) {
                if (!startTimer.hasReached(startDelay.getDefaultValue())) {
                    throwTimer.reset();
                    return;
                }
            } else {
                startTimer.reset();
                if (openedInventory.isToggled())
                    return;
            }

            if (autoArmor.isToggled() && !autoArmor.isFinished()) {
                startTimer.reset();
                throwTimer.reset();
                if (openedInventory.isToggled())
                    return;
            }

            for (int i = 9; i < 45; i++) {
                if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                    ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();

                    if (throwTimer.hasReached(randomUtil.randomLong(throwDelay.getMinDefaultValue(), throwDelay.getDefaultValue()))) {
                        if (swordSlot.getDefaultValue() != 0 && (is.getItem() instanceof ItemSword || is.getItem() instanceof ItemAxe || is.getItem() instanceof ItemPickaxe) && is == bestWeapon() && mc.thePlayer.inventoryContainer.getInventory().contains(bestWeapon()) && mc.thePlayer.inventoryContainer.getSlot(35 + swordSlot.getDefaultValue()).getStack() != is && !preferSword.isToggled()) {
                            mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, i, swordSlot.getDefaultValue() - 1, 2, mc.thePlayer);
                            throwTimer.reset();
                            break;
                        } else if (swordSlot.getDefaultValue() != 0 && is.getItem() instanceof ItemSword && is == bestSword() && mc.thePlayer.inventoryContainer.getInventory().contains(bestSword()) && mc.thePlayer.inventoryContainer.getSlot(35 + swordSlot.getDefaultValue()).getStack() != is && preferSword.isToggled()) {
                            mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, i, swordSlot.getDefaultValue() - 1, 2, mc.thePlayer);
                            throwTimer.reset();
                            break;
                        } else if (bowSlot.getDefaultValue() != 0 && is.getItem() instanceof ItemBow && is == bestBow() && mc.thePlayer.inventoryContainer.getInventory().contains(bestBow()) && mc.thePlayer.inventoryContainer.getSlot(35 + bowSlot.getDefaultValue()).getStack() != is) {
                            mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, i, bowSlot.getDefaultValue() - 1, 2, mc.thePlayer);
                            throwTimer.reset();
                            break;
                        } else if (pickSlot.getDefaultValue() != 0 && is.getItem() instanceof ItemPickaxe && is == bestPick() && is != bestWeapon() && keepTools.isToggled() && mc.thePlayer.inventoryContainer.getInventory().contains(bestPick()) && mc.thePlayer.inventoryContainer.getSlot(35 + pickSlot.getDefaultValue()).getStack() != is) {
                            mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, i, pickSlot.getDefaultValue() - 1, 2, mc.thePlayer);
                            throwTimer.reset();
                            break;
                        } else if (axeSlot.getDefaultValue() != 0 && is.getItem() instanceof ItemAxe && is == bestAxe() && is != bestWeapon() && keepTools.isToggled() && mc.thePlayer.inventoryContainer.getInventory().contains(bestAxe()) && mc.thePlayer.inventoryContainer.getSlot(35 + axeSlot.getDefaultValue()).getStack() != is) {
                            mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, i, axeSlot.getDefaultValue() - 1, 2, mc.thePlayer);
                            throwTimer.reset();
                            break;
                        } else if (shovelSlot.getDefaultValue() != 0 && is.getItem() instanceof ItemSpade && is == bestShovel() && is != bestWeapon() && keepTools.isToggled() && mc.thePlayer.inventoryContainer.getInventory().contains(bestShovel()) && mc.thePlayer.inventoryContainer.getSlot(35 + shovelSlot.getDefaultValue()).getStack() != is) {
                            mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, i, shovelSlot.getDefaultValue() - 1, 2, mc.thePlayer);
                            throwTimer.reset();
                            break;
                        } else if (trashItems.contains(is.getItem()) || isBadStack(is)) {
                            mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, i, 1, 4, mc.thePlayer);
                            throwTimer.reset();
                            break;
                        }
                    }
                }
            }
        }
    }

    public boolean isBadStack(ItemStack is) {
        if ((is.getItem() instanceof ItemSword) && is != bestWeapon() && !preferSword.isToggled())
            return true;
        if (is.getItem() instanceof ItemSword && is != bestSword() && preferSword.isToggled())
            return true;
        if (is.getItem() instanceof ItemBow && is != bestBow())
            return true;
        if (keepTools.isToggled()) {
            if (is.getItem() instanceof ItemAxe && is != bestAxe() && (preferSword.isToggled() || is != bestWeapon()))
                return true;
            if (is.getItem() instanceof ItemPickaxe && is != bestPick() && (preferSword.isToggled() || is != bestWeapon()))
                return true;
            if (is.getItem() instanceof ItemSpade && is != bestShovel())
                return true;
        } else {
            if (is.getItem() instanceof ItemAxe && (preferSword.isToggled() || is != bestWeapon()))
                return true;
            if (is.getItem() instanceof ItemPickaxe && (preferSword.isToggled() || is != bestWeapon()))
                return true;
            if (is.getItem() instanceof ItemSpade)
                return true;
        }
        return false;
    }

    public ItemStack bestWeapon() {
        ItemStack bestWeapon = null;
        float itemDamage = -1;

        for (int i = 9; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
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
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
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
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
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
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
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
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
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
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
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
        Item is = itemStack.getItem();
        float rating = 0;

        if (is instanceof ItemSword) {
            switch (((ItemSword) is).getToolMaterialName()) {
                case "WOOD":
                    rating = 4;
                    break;
                case "GOLD":
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
                case "WOOD":
                    rating = 2;
                    break;
                case "GOLD":
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
            }
        } else if (is instanceof ItemAxe) {
            switch (((ItemAxe) is).getToolMaterialName()) {
                case "WOOD":
                    rating = 3;
                    break;
                case "GOLD":
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
            }
        } else if (is instanceof ItemSpade) {
            switch (((ItemSpade) is).getToolMaterialName()) {
                case "WOOD":
                    rating = 1;
                    break;
                case "GOLD":
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
            }
        }

        return rating;
    }

    @Override
    public void onEnable() {
        autoArmor = Koks.getKoks().moduleManager.getModule(AutoArmor.class);
    }

    @Override
    public void onDisable() {

    }

}