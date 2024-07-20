/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.ContainerShulkerBox;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import ru.govno.client.module.Module;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.InventoryUtil;
import ru.govno.client.utils.Movement.MoveMeHelp;
import ru.govno.client.utils.TimerHelper;

public class InvMngr
extends Module {
    public static int weaponSlot = 36;
    public static int pickaxeSlot = 37;
    public static int axeSlot = 38;
    public static int shovelSlot = 39;
    public static List<Block> invalidBlocks = Arrays.asList(Blocks.ENCHANTING_TABLE, Blocks.FURNACE, Blocks.CARPET, Blocks.CRAFTING_TABLE, Blocks.TRAPPED_CHEST, Blocks.CHEST, Blocks.DISPENSER, Blocks.AIR, Blocks.WATER, Blocks.LAVA, Blocks.FLOWING_WATER, Blocks.FLOWING_LAVA, Blocks.SAND, Blocks.SNOW_LAYER, Blocks.TORCH, Blocks.ANVIL, Blocks.JUKEBOX, Blocks.STONE_BUTTON, Blocks.WOODEN_BUTTON, Blocks.LEVER, Blocks.NOTEBLOCK, Blocks.STONE_PRESSURE_PLATE, Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, Blocks.WOODEN_PRESSURE_PLATE, Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, Blocks.STONE_SLAB, Blocks.WOODEN_SLAB, Blocks.STONE_SLAB2, Blocks.RED_MUSHROOM, Blocks.BROWN_MUSHROOM, Blocks.YELLOW_FLOWER, Blocks.RED_FLOWER, Blocks.ANVIL, Blocks.GLASS_PANE, Blocks.STAINED_GLASS_PANE, Blocks.IRON_BARS, Blocks.CACTUS, Blocks.LADDER, Blocks.WEB);
    private final TimerHelper timer = new TimerHelper();

    public InvMngr() {
        super("InvMngr", 0, Module.Category.PLAYER);
        this.settings.add(new Settings("BlockCap", 128.0f, 256.0f, 8.0f, this));
        this.settings.add(new Settings("SortDelay", 50.0f, 250.0f, 0.0f, this));
        this.settings.add(new Settings("ForTheArcher", false, (Module)this));
        this.settings.add(new Settings("Food", false, (Module)this));
        this.settings.add(new Settings("Sword", true, (Module)this));
        this.settings.add(new Settings("InvCleaner", true, (Module)this));
        this.settings.add(new Settings("OnlyInInv", true, (Module)this));
        this.settings.add(new Settings("NoMovingSwap", false, (Module)this));
    }

    @Override
    public void onUpdate() {
        long delay = (long)this.currentFloatValue("SortDelay");
        if (Minecraft.player.openContainer instanceof ContainerChest || Minecraft.player.openContainer instanceof ContainerShulkerBox) {
            return;
        }
        if (!(InvMngr.mc.currentScreen instanceof GuiInventory) && this.currentBooleanValue("OnlyInInv")) {
            return;
        }
        if ((MoveMeHelp.getSpeed() != 0.0 || Minecraft.player.serverSprintState || MoveMeHelp.moveKeysPressed()) && this.currentBooleanValue("NoMovingSwap")) {
            return;
        }
        if (InvMngr.mc.currentScreen == null || InvMngr.mc.currentScreen instanceof GuiInventory || InvMngr.mc.currentScreen instanceof GuiChat) {
            if (this.timer.hasReached(delay) && weaponSlot >= 36) {
                if (!Minecraft.player.inventoryContainer.getSlot(weaponSlot).getHasStack()) {
                    this.getBestWeapon(weaponSlot);
                } else if (!this.isBestWeapon(Minecraft.player.inventoryContainer.getSlot(weaponSlot).getStack())) {
                    this.getBestWeapon(weaponSlot);
                }
            }
            if (this.timer.hasReached(delay) && pickaxeSlot >= 36) {
                this.getBestPickaxe();
            }
            if (this.timer.hasReached(delay) && shovelSlot >= 36) {
                this.getBestShovel();
            }
            if (this.timer.hasReached(delay) && axeSlot >= 36) {
                this.getBestAxe();
            }
            if (this.timer.hasReached(delay) && this.currentBooleanValue("InvCleaner")) {
                for (int i = 9; i < 45; ++i) {
                    ItemStack is;
                    if (!Minecraft.player.inventoryContainer.getSlot(i).getHasStack() || !this.shouldDrop(is = Minecraft.player.inventoryContainer.getSlot(i).getStack(), i)) continue;
                    this.drop(i);
                    if (delay == 0L) {
                        Minecraft.player.closeScreen();
                    }
                    this.timer.reset();
                    if (delay > 0L) break;
                }
            }
        }
    }

    public void swap(int slot, int hotbarSlot) {
        InvMngr.mc.playerController.windowClick(Minecraft.player.inventoryContainer.windowId, slot, hotbarSlot, ClickType.SWAP, Minecraft.player);
    }

    public void drop(int slot) {
        InvMngr.mc.playerController.windowClick(Minecraft.player.inventoryContainer.windowId, slot, 1, ClickType.THROW, Minecraft.player);
    }

    public boolean isBestWeapon(ItemStack stack) {
        float damage = this.getDamage(stack);
        for (int i = 9; i < 45; ++i) {
            ItemStack is;
            if (!Minecraft.player.inventoryContainer.getSlot(i).getHasStack() || !(this.getDamage(is = Minecraft.player.inventoryContainer.getSlot(i).getStack()) > damage) || !(is.getItem() instanceof ItemSword) && this.currentBooleanValue("Sword")) continue;
            return false;
        }
        return stack.getItem() instanceof ItemSword || !this.currentBooleanValue("Sword");
    }

    public void getBestWeapon(int slot) {
        for (int i = 9; i < 45; ++i) {
            ItemStack is;
            if (!Minecraft.player.inventoryContainer.getSlot(i).getHasStack() || !this.isBestWeapon(is = Minecraft.player.inventoryContainer.getSlot(i).getStack()) || !(this.getDamage(is) > 0.0f) || !(is.getItem() instanceof ItemSword) && this.currentBooleanValue("Sword")) continue;
            this.swap(i, slot - 36);
            this.timer.reset();
            break;
        }
    }

    private float getDamage(ItemStack stack) {
        float damage = 0.0f;
        Item item = stack.getItem();
        if (item instanceof ItemTool) {
            ItemTool tool = (ItemTool)item;
            damage += tool.getDamageVsEntity();
        }
        if (item instanceof ItemSword) {
            ItemSword sword = (ItemSword)item;
            damage += sword.getDamageVsEntity();
        }
        return damage += (float)EnchantmentHelper.getEnchantmentLevel(Objects.requireNonNull(Enchantment.getEnchantmentByID(16)), stack) * 1.25f + (float)EnchantmentHelper.getEnchantmentLevel(Objects.requireNonNull(Enchantment.getEnchantmentByID(20)), stack) * 0.01f;
    }

    public boolean shouldDrop(ItemStack stack, int slot) {
        if (stack.getDisplayName().toLowerCase().contains("/")) {
            return false;
        }
        if (stack.getDisplayName().toLowerCase().contains("\u00a7k||")) {
            return false;
        }
        if (stack.getDisplayName().toLowerCase().contains("kit")) {
            return false;
        }
        if (stack.getDisplayName().toLowerCase().contains("wool")) {
            return false;
        }
        if (slot == weaponSlot && this.isBestWeapon(Minecraft.player.inventoryContainer.getSlot(weaponSlot).getStack()) || slot == pickaxeSlot && this.isBestPickaxe(Minecraft.player.inventoryContainer.getSlot(pickaxeSlot).getStack()) && pickaxeSlot >= 0 || slot == axeSlot && this.isBestAxe(Minecraft.player.inventoryContainer.getSlot(axeSlot).getStack()) && axeSlot >= 0 || slot == shovelSlot && this.isBestShovel(Minecraft.player.inventoryContainer.getSlot(shovelSlot).getStack()) && shovelSlot >= 0) {
            return false;
        }
        if (stack.getItem() instanceof ItemBucket) {
            return false;
        }
        if (stack.getItem() instanceof ItemArmor) {
            for (int type2 = 1; type2 < 5; ++type2) {
                ItemStack is;
                if (Minecraft.player.inventoryContainer.getSlot(4 + type2).getHasStack() && InventoryUtil.isBestArmor(is = Minecraft.player.inventoryContainer.getSlot(4 + type2).getStack(), type2) || !InventoryUtil.isBestArmor(stack, type2)) continue;
                return false;
            }
        }
        if (stack.getItem() instanceof ItemBlock && ((float)this.getBlockCount() > this.currentFloatValue("BlockCap") || invalidBlocks.contains(((ItemBlock)stack.getItem()).getBlock()))) {
            return true;
        }
        if (stack.getItem() instanceof ItemPotion && this.isBadPotion(stack)) {
            return true;
        }
        if (stack.getItem() instanceof ItemFood && this.currentBooleanValue("Food") && !(stack.getItem() instanceof ItemAppleGold)) {
            return true;
        }
        if (stack.getItem() instanceof ItemHoe || stack.getItem() instanceof ItemTool || stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemArmor) {
            return true;
        }
        if ((stack.getItem() instanceof ItemBow || stack.getItem().getUnlocalizedName().contains("arrow")) && this.currentBooleanValue("ForTheArcher")) {
            return true;
        }
        return stack.getItem().getUnlocalizedName().contains("tnt") || stack.getItem().getUnlocalizedName().contains("stick") || stack.getItem().getUnlocalizedName().contains("egg") || stack.getItem().getUnlocalizedName().contains("string") || stack.getItem().getUnlocalizedName().contains("cake") || stack.getItem().getUnlocalizedName().contains("mushroom") || stack.getItem().getUnlocalizedName().contains("flint") || stack.getItem().getUnlocalizedName().contains("dyePowder") || stack.getItem().getUnlocalizedName().contains("feather") || stack.getItem().getUnlocalizedName().contains("bucket") || stack.getItem().getUnlocalizedName().contains("chest") && !stack.getDisplayName().toLowerCase().contains("collect") || stack.getItem().getUnlocalizedName().contains("snow") || stack.getItem().getUnlocalizedName().contains("fish") || stack.getItem().getUnlocalizedName().contains("enchant") || stack.getItem().getUnlocalizedName().contains("exp") || stack.getItem().getUnlocalizedName().contains("shears") || stack.getItem().getUnlocalizedName().contains("anvil") || stack.getItem().getUnlocalizedName().contains("torch") || stack.getItem().getUnlocalizedName().contains("seeds") || stack.getItem().getUnlocalizedName().contains("leather") || stack.getItem().getUnlocalizedName().contains("reeds") || stack.getItem().getUnlocalizedName().contains("skull") || stack.getItem().getUnlocalizedName().contains("wool") || stack.getItem().getUnlocalizedName().contains("record") || stack.getItem().getUnlocalizedName().contains("snowball") || stack.getItem() instanceof ItemGlassBottle || stack.getItem().getUnlocalizedName().contains("piston");
    }

    private int getBlockCount() {
        int blockCount = 0;
        for (int i = 0; i < 45; ++i) {
            if (!Minecraft.player.inventoryContainer.getSlot(i).getHasStack()) continue;
            ItemStack is = Minecraft.player.inventoryContainer.getSlot(i).getStack();
            Item item = is.getItem();
            if (!(is.getItem() instanceof ItemBlock) || invalidBlocks.contains(((ItemBlock)item).getBlock())) continue;
            blockCount += is.stackSize;
        }
        return blockCount;
    }

    private void getBestPickaxe() {
        for (int i = 9; i < 45; ++i) {
            ItemStack is;
            if (!Minecraft.player.inventoryContainer.getSlot(i).getHasStack() || !this.isBestPickaxe(is = Minecraft.player.inventoryContainer.getSlot(i).getStack()) || pickaxeSlot == i || this.isBestWeapon(is)) continue;
            if (!Minecraft.player.inventoryContainer.getSlot(pickaxeSlot).getHasStack()) {
                this.swap(i, pickaxeSlot - 36);
                this.timer.reset();
                if (!(this.currentFloatValue("SortDelay") > 0.0f)) continue;
                return;
            }
            if (this.isBestPickaxe(Minecraft.player.inventoryContainer.getSlot(pickaxeSlot).getStack())) continue;
            this.swap(i, pickaxeSlot - 36);
            this.timer.reset();
            if (!(this.currentFloatValue("SortDelay") > 0.0f)) continue;
            return;
        }
    }

    private void getBestShovel() {
        for (int i = 9; i < 45; ++i) {
            ItemStack is;
            if (!Minecraft.player.inventoryContainer.getSlot(i).getHasStack() || !this.isBestShovel(is = Minecraft.player.inventoryContainer.getSlot(i).getStack()) || shovelSlot == i || this.isBestWeapon(is)) continue;
            if (!Minecraft.player.inventoryContainer.getSlot(shovelSlot).getHasStack()) {
                this.swap(i, shovelSlot - 36);
                this.timer.reset();
                if (!(this.currentFloatValue("SortDelay") > 0.0f)) continue;
                return;
            }
            if (this.isBestShovel(Minecraft.player.inventoryContainer.getSlot(shovelSlot).getStack())) continue;
            this.swap(i, shovelSlot - 36);
            this.timer.reset();
            if (!(this.currentFloatValue("SortDelay") > 0.0f)) continue;
            return;
        }
    }

    private void getBestAxe() {
        for (int i = 9; i < 45; ++i) {
            ItemStack is;
            if (!Minecraft.player.inventoryContainer.getSlot(i).getHasStack() || !this.isBestAxe(is = Minecraft.player.inventoryContainer.getSlot(i).getStack()) || axeSlot == i || this.isBestWeapon(is)) continue;
            if (!Minecraft.player.inventoryContainer.getSlot(axeSlot).getHasStack()) {
                this.swap(i, axeSlot - 36);
                this.timer.reset();
                if (!(this.currentFloatValue("SortDelay") > 0.0f)) continue;
                return;
            }
            if (this.isBestAxe(Minecraft.player.inventoryContainer.getSlot(axeSlot).getStack())) continue;
            this.swap(i, axeSlot - 36);
            this.timer.reset();
            if (!(this.currentFloatValue("SortDelay") > 0.0f)) continue;
            return;
        }
    }

    private boolean isBestPickaxe(ItemStack stack) {
        Item item = stack.getItem();
        if (!(item instanceof ItemPickaxe)) {
            return false;
        }
        float value = this.getToolEffect(stack);
        for (int i = 9; i < 45; ++i) {
            ItemStack is;
            if (!Minecraft.player.inventoryContainer.getSlot(i).getHasStack() || !(this.getToolEffect(is = Minecraft.player.inventoryContainer.getSlot(i).getStack()) > value) || !(is.getItem() instanceof ItemPickaxe)) continue;
            return false;
        }
        return true;
    }

    private boolean isBestShovel(ItemStack stack) {
        Item item = stack.getItem();
        if (!(item instanceof ItemSpade)) {
            return false;
        }
        float value = this.getToolEffect(stack);
        for (int i = 9; i < 45; ++i) {
            ItemStack is;
            if (!Minecraft.player.inventoryContainer.getSlot(i).getHasStack() || !(this.getToolEffect(is = Minecraft.player.inventoryContainer.getSlot(i).getStack()) > value) || !(is.getItem() instanceof ItemSpade)) continue;
            return false;
        }
        return true;
    }

    private boolean isBestAxe(ItemStack stack) {
        Item item = stack.getItem();
        if (!(item instanceof ItemAxe)) {
            return false;
        }
        float value = this.getToolEffect(stack);
        for (int i = 9; i < 45; ++i) {
            ItemStack is;
            if (!Minecraft.player.inventoryContainer.getSlot(i).getHasStack() || !(this.getToolEffect(is = Minecraft.player.inventoryContainer.getSlot(i).getStack()) > value) || !(is.getItem() instanceof ItemAxe) || this.isBestWeapon(stack)) continue;
            return false;
        }
        return true;
    }

    private float getToolEffect(ItemStack stack) {
        float value;
        Item item = stack.getItem();
        if (!(item instanceof ItemTool)) {
            return 0.0f;
        }
        ItemTool tool = (ItemTool)item;
        String name = item.getUnlocalizedName();
        if (item instanceof ItemPickaxe) {
            value = tool.getStrVsBlock(stack, Blocks.STONE.getDefaultState());
            if (name.toLowerCase().contains("gold")) {
                value -= 5.0f;
            }
        } else if (item instanceof ItemSpade) {
            value = tool.getStrVsBlock(stack, Blocks.DIRT.getDefaultState());
            if (name.toLowerCase().contains("gold")) {
                value -= 5.0f;
            }
        } else if (item instanceof ItemAxe) {
            value = tool.getStrVsBlock(stack, Blocks.LOG.getDefaultState());
            if (name.toLowerCase().contains("gold")) {
                value -= 5.0f;
            }
        } else {
            return 1.0f;
        }
        value = (float)((double)value + (double)EnchantmentHelper.getEnchantmentLevel(Objects.requireNonNull(Enchantment.getEnchantmentByID(32)), stack) * 0.0075);
        value = (float)((double)value + (double)EnchantmentHelper.getEnchantmentLevel(Objects.requireNonNull(Enchantment.getEnchantmentByID(34)), stack) / 100.0);
        return value;
    }

    private boolean isBadPotion(ItemStack stack) {
        if (stack != null && stack.getItem() instanceof ItemPotion) {
            for (PotionEffect o : PotionUtils.getEffectsFromStack(stack)) {
                if (o.getPotion() != Potion.getPotionById(19) && o.getPotion() != Potion.getPotionById(7) && o.getPotion() != Potion.getPotionById(2) && o.getPotion() != Potion.getPotionById(18)) continue;
                return true;
            }
        }
        return false;
    }
}

