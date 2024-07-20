/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;
import ru.govno.client.module.Module;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.TimerHelper;

public class TopkaSort
extends Module {
    private final TimerHelper second = new TimerHelper();

    public TopkaSort() {
        super("TopkaSort", 0, Module.Category.PLAYER);
        this.settings.add(new Settings("OnPressAltKey", false, (Module)this));
        this.settings.add(new Settings("DroperDelay", 120.0f, 1000.0f, 50.0f, this));
        this.settings.add(new Settings("ForBowUser", true, (Module)this));
        this.settings.add(new Settings("ShlakoDrop", true, (Module)this));
        this.settings.add(new Settings("BadFood", true, (Module)this));
        this.settings.add(new Settings("BadArmor", true, (Module)this));
        this.settings.add(new Settings("LeatherArm", true, (Module)this));
        this.settings.add(new Settings("ChainmailArm", true, (Module)this));
        this.settings.add(new Settings("IronArm", true, (Module)this));
        this.settings.add(new Settings("GoldenArm", true, (Module)this));
        this.settings.add(new Settings("BadTools", true, (Module)this));
        this.settings.add(new Settings("WoodenTool", true, (Module)this));
        this.settings.add(new Settings("StoneTool", true, (Module)this));
        this.settings.add(new Settings("IronTool", true, (Module)this));
        this.settings.add(new Settings("GoldenTool", true, (Module)this));
        this.settings.add(new Settings("DropBlocks", true, (Module)this));
        this.settings.add(new Settings("OnlyInInv", false, (Module)this));
    }

    @Override
    public void onUpdate() {
        if (!(TopkaSort.mc.currentScreen instanceof GuiInventory) && this.currentBooleanValue("OnlyInInv")) {
            return;
        }
        if (!Keyboard.isKeyDown(56) && this.currentBooleanValue("OnPressAltKey")) {
            return;
        }
        if ((TopkaSort.mc.currentScreen == null || TopkaSort.mc.currentScreen instanceof GuiInventory || TopkaSort.mc.currentScreen instanceof GuiChat) && this.second.hasReached(0.0f)) {
            for (int i = 0; i < 45; ++i) {
                if (!Minecraft.player.inventoryContainer.getSlot(i).getHasStack()) continue;
                ItemStack is = Minecraft.player.inventoryContainer.getSlot(i).getStack();
                if (is == Minecraft.player.inventory.armorInventory.get(0)) continue;
                if (is == Minecraft.player.inventory.armorInventory.get(1)) continue;
                if (is == Minecraft.player.inventory.armorInventory.get(2)) continue;
                if (is == Minecraft.player.inventory.armorInventory.get(3) || !this.shouldDrop(is, i) || !this.second.hasReached(this.currentFloatValue("DroperDelay"))) continue;
                TopkaSort.mc.playerController.windowClick(Minecraft.player.inventoryContainer.windowId, i, 1, ClickType.THROW, Minecraft.player);
                this.second.reset();
            }
        }
    }

    public boolean shouldDrop(ItemStack stack, int slot) {
        if (stack.getItem() instanceof ItemBlock && this.currentBooleanValue("DropBlocks") && stack.getItem() != ItemBlock.getItemById(7) && stack.getItem() != ItemBlock.getItemById(33) && stack.getItem() != ItemBlock.getItemById(29) && stack.getItem() != ItemBlock.getItemById(46) && stack.getItem() != ItemBlock.getItemById(120) && stack.getItem() != ItemBlock.getItemById(41) && stack.getItem() != ItemBlock.getItemById(89) && stack.getItem() != ItemBlock.getItemById(14) && stack.getItem() != ItemBlock.getItemById(133) && stack.getItem() != ItemBlock.getItemById(129) && stack.getItem() != ItemBlock.getItemById(56) && stack.getItem() != ItemBlock.getItemById(57) && stack.getItem() != ItemBlock.getItemById(165) && stack.getItem() != ItemBlock.getItemById(52) && stack.getItem() != ItemBlock.getItemById(30) && stack.getItem() != ItemBlock.getItemById(49) && stack.getItem() != ItemBlock.getItemById(130) && stack.getItem() != ItemBlock.getItemById(49) && stack.getItem() != ItemBlock.getItemById(219) && stack.getItem() != ItemBlock.getItemById(220) && stack.getItem() != ItemBlock.getItemById(221) && stack.getItem() != ItemBlock.getItemById(222) && stack.getItem() != ItemBlock.getItemById(223) && stack.getItem() != ItemBlock.getItemById(224) && stack.getItem() != ItemBlock.getItemById(225) && stack.getItem() != ItemBlock.getItemById(226) && stack.getItem() != ItemBlock.getItemById(227) && stack.getItem() != ItemBlock.getItemById(228) && stack.getItem() != ItemBlock.getItemById(229) && stack.getItem() != ItemBlock.getItemById(230) && stack.getItem() != ItemBlock.getItemById(231) && stack.getItem() != ItemBlock.getItemById(232) && stack.getItem() != ItemBlock.getItemById(233) && stack.getItem() != ItemBlock.getItemById(234)) {
            return true;
        }
        if (!(this.currentBooleanValue("ForBowUser") || stack.getItem() != Items.BOW && stack.getItem() != Items.ARROW && stack.getItem() != Items.TIPPED_ARROW)) {
            return true;
        }
        if (this.currentBooleanValue("ShlakoDrop") && (stack.getItem() == Items.STICK || stack.getItem() == ItemBlock.getItemById(324) || stack.getItem() == ItemBlock.getItemById(330) || stack.getItem() == ItemBlock.getItemById(427) || stack.getItem() == ItemBlock.getItemById(428) || stack.getItem() == ItemBlock.getItemById(429) || stack.getItem() == ItemBlock.getItemById(430) || stack.getItem() == ItemBlock.getItemById(431) || stack.getItem() == ItemBlock.getItemById(328) || stack.getItem() == ItemBlock.getItemById(407) || stack.getItem() == ItemBlock.getItemById(342) || stack.getItem() == ItemBlock.getItemById(333) || stack.getItem() == ItemBlock.getItemById(444) || stack.getItem() == ItemBlock.getItemById(445) || stack.getItem() == ItemBlock.getItemById(446) || stack.getItem() == ItemBlock.getItemById(447) || stack.getItem() == ItemBlock.getItemById(448) || stack.getItem() == Items.BEETROOT_SEEDS || stack.getItem() == Items.WHEAT || stack.getItem() == Items.MELON_SEEDS || stack.getItem() == Items.PUMPKIN_SEEDS || stack.getItem() == Items.WHEAT_SEEDS || stack.getItem() == Items.DYE || stack.getItem() == Items.CLOCK || stack.getItem() == Items.COMPASS || stack.getItem() == Items.PAPER || stack.getItem() == Items.FISHING_ROD || stack.getItem() == Items.SLIME_BALL || stack.getItem() == Items.CLAY_BALL || stack.getItem() == Items.BONE || stack.getItem() == Items.BOWL || stack.getItem() == Items.CARROT_ON_A_STICK || stack.getItem() == Items.FEATHER || stack.getItem() == Items.GLASS_BOTTLE || stack.getItem() == Items.ENDER_EYE || stack.getItem() == Items.SADDLE || stack.getItem() == Items.SIGN || stack.getItem() == Items.MAP || stack.getItem() == Items.EGG || stack.getItem() == Items.ENDER_EYE || stack.getItem() == Items.HOPPER_MINECART || stack.getItem() == Items.FLOWER_POT || stack.getItem() == Items.LEATHER || stack.getItem() == Items.REEDS || stack.getItem() == Items.NAME_TAG || stack.getItem() == Items.SHEARS || stack.getItem() == Items.ENCHANTED_BOOK || stack.getItem() == Items.LEAD)) {
            return true;
        }
        if (this.currentBooleanValue("BadFood") && (stack.getItem() == Items.ROTTEN_FLESH || stack.getItem() == Items.POTATO || stack.getItem() == Items.CHICKEN || stack.getItem() == Items.BEEF || stack.getItem() == Items.FISH || stack.getItem() == Items.MUTTON || stack.getItem() == Items.PORKCHOP || stack.getItem() == Items.RABBIT || stack.getItem() == Items.COOKIE)) {
            return true;
        }
        if (this.currentBooleanValue("BadArmor") && ((stack.getItem() == Items.CHAINMAIL_BOOTS || stack.getItem() == Items.CHAINMAIL_LEGGINGS || stack.getItem() == Items.CHAINMAIL_CHESTPLATE || stack.getItem() == Items.CHAINMAIL_HELMET) && this.currentBooleanValue("ChainmailArm") || (stack.getItem() == Items.GOLDEN_BOOTS || stack.getItem() == Items.GOLDEN_LEGGINGS || stack.getItem() == Items.GOLDEN_CHESTPLATE || stack.getItem() == Items.GOLDEN_HELMET) && this.currentBooleanValue("GoldenArm") || (stack.getItem() == Items.LEATHER_BOOTS || stack.getItem() == Items.LEATHER_LEGGINGS || stack.getItem() == Items.LEATHER_CHESTPLATE || stack.getItem() == Items.LEATHER_HELMET) && this.currentBooleanValue("LeatherArm") || (stack.getItem() == Items.IRON_BOOTS || stack.getItem() == Items.IRON_LEGGINGS || stack.getItem() == Items.IRON_CHESTPLATE || stack.getItem() == Items.IRON_HELMET) && this.currentBooleanValue("IronArm"))) {
            return true;
        }
        return this.currentBooleanValue("BadTools") && ((stack.getItem() == Items.WOODEN_SWORD || stack.getItem() == Items.WOODEN_PICKAXE || stack.getItem() == Items.WOODEN_AXE || stack.getItem() == Items.WOODEN_SHOVEL || stack.getItem() == Items.WOODEN_HOE) && this.currentBooleanValue("WoodenTool") || (stack.getItem() == Items.STONE_SWORD || stack.getItem() == Items.STONE_PICKAXE || stack.getItem() == Items.STONE_AXE || stack.getItem() == Items.STONE_SHOVEL || stack.getItem() == Items.STONE_HOE) && this.currentBooleanValue("StoneTool") || (stack.getItem() == Items.IRON_SWORD || stack.getItem() == Items.IRON_PICKAXE || stack.getItem() == Items.IRON_AXE || stack.getItem() == Items.IRON_SHOVEL || stack.getItem() == Items.IRON_HOE) && this.currentBooleanValue("IronTool") || (stack.getItem() == Items.GOLDEN_SWORD || stack.getItem() == Items.GOLDEN_PICKAXE || stack.getItem() == Items.GOLDEN_AXE || stack.getItem() == Items.GOLDEN_SHOVEL || stack.getItem() == Items.GOLDEN_HOE) && this.currentBooleanValue("GoldenTool"));
    }
}

