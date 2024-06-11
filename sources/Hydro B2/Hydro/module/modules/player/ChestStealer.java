package Hydro.module.modules.player;

import org.lwjgl.input.Keyboard;

import Hydro.Client;
import Hydro.ClickGui.settings.Setting;
import Hydro.event.Event;
import Hydro.event.events.EventUpdate;
import Hydro.module.Category;
import Hydro.module.Module;
import Hydro.util.Timer2;
import Hydro.util.Wrapper;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

public class ChestStealer extends Module {

    public boolean priority = true;
    public boolean autoClose = true;
    public int speed = 10;
    public int randomization = 1;
    public Timer2 startDelay = new Timer2();
    public Timer2 closeDelay = new Timer2();
    public boolean wasNotChest;

    public ChestStealer() {
        super("ChestStealer", Keyboard.KEY_F, true, Category.PLAYER, "Automatically steals items out of a chest");
        Client.instance.settingsManager.rSetting(new Setting("stealerClose", "AutoClose", this, true));
        Client.instance.settingsManager.rSetting(new Setting("stealerSpeed", "Speed", this, 20, 1, 100, true));
    }

    public void onEvent(Event e) {
        if (e instanceof EventUpdate && e.isPre()) {
        	speed = (int) Client.settingsManager.getSettingByName("stealerSpeed").getValDouble();
            if (wasNotChest && mc.currentScreen instanceof GuiChest) {
                startDelay.reset();
            }
            wasNotChest = !(mc.currentScreen instanceof GuiChest);
            if (mc.currentScreen instanceof GuiChest && startDelay.getTime() > 250 && Wrapper.invCooldownElapsed((long) (1000 / Math.max(1, (speed + (int) (Math.random() * randomization - randomization / 2f)))))) {
                GuiChest chestGui = (GuiChest) mc.currentScreen;
                Container container = chestGui.inventorySlots;

                String name = chestGui.lowerChestInventory.getDisplayName().getUnformattedText();
                if (!(name.equals("Large Chest") || name.equals("Chest") || name.equals("LOW")))
                    return;

                boolean shouldClose = true;
                for (Slot slot : container.inventorySlots) {
                    int id = slot.slotNumber;
                    ItemStack item = slot.getStack();

                    if (id <= 26 && item != null && shouldSteal(item)) {
                        shouldClose = false;
                        for (Slot playerSlot : mc.thePlayer.inventoryContainer.inventorySlots) {
                            int playerSlotId = playerSlot.slotNumber;
                            boolean empty = !playerSlot.getHasStack();

                            if (empty) {
                                //mc.thePlayer.addChatMessage(new ChatComponentText(id + " " + item.toString()));
                                mc.playerController.windowClick(container.windowId, id, 0, 1, mc.thePlayer);
                                closeDelay.reset();
                                //startDelay.reset();
                                return;
                            }
                        }
                    }
                }
                if (closeDelay.getTime() > 150 && shouldClose && autoClose) {
                    mc.thePlayer.closeScreen();
                    startDelay.reset();
                }


            }
        }

    }

    public boolean isBestArmor(ItemStack compareStack) {
        for (int i = 0; i < 45; i++) {
            Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);
            ItemStack stack = slot.getStack();
            boolean hotbar = i >= 36;

            if (stack != null && compareStack != stack && stack.getItem() instanceof ItemArmor) {
                ItemArmor item = (ItemArmor) stack.getItem();
                ItemArmor compare = (ItemArmor) compareStack.getItem();
                if (item.armorType == compare.armorType) {
                    if (AutoArmor.getProtectionValue(compareStack) <= AutoArmor.getProtectionValue(stack))
                        return false;
                }
            }
        }

        return true;
    }

    public static float getSwordStrength(ItemStack stack) {
        return (!(stack.getItem() instanceof ItemSword) ? 0.0F : (float) EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25F) + (!(stack.getItem() instanceof ItemSword) ? 0.0F : (float) EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack) * 1F);
    }

    public boolean isBestSword(ItemStack compareStack) {
        for (int i = 9; i < 45; i++) {
            Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);
            ItemStack stack = slot.getStack();
            boolean hotbar = i >= 36;

            if (stack != null && compareStack != stack && stack.getItem() instanceof ItemSword) {
                ItemSword item = (ItemSword) stack.getItem();
                ItemSword compare = (ItemSword) compareStack.getItem();
                if (item.getClass() == compare.getClass()) {
                    if (compare.attackDamage + getSwordStrength(compareStack) <= item.attackDamage + getSwordStrength(stack))
                        return false;
                }
            }
        }

        return true;
    }

    public boolean isBestTool(ItemStack compareStack) {
        for (int i = 9; i < 45; i++) {
            Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);
            ItemStack stack = slot.getStack();
            boolean hotbar = i >= 36;

            if (stack != null && compareStack != stack && stack.getItem() instanceof ItemTool) {
                ItemTool item = (ItemTool) stack.getItem();
                ItemTool compare = (ItemTool) compareStack.getItem();
                if (item.getClass() == compare.getClass()) {
                    if (compare.getStrVsBlock(stack, preferredBlock(item.getClass())) <= item.getStrVsBlock(compareStack, preferredBlock(compare.getClass())))
                        return false;
                }
            }
        }

        return true;
    }

    public Block preferredBlock(Class clazz) {
        return clazz == ItemPickaxe.class ? Blocks.cobblestone : clazz == ItemAxe.class ? Blocks.log : Blocks.dirt;
    }

    public boolean shouldSteal(ItemStack itemStack) {
        Item item = itemStack.getItem();

        if (!priority)
            return true;

        if (item instanceof ItemSword)
            return isBestSword(itemStack);

        if (item instanceof ItemTool)
            return isBestTool(itemStack);

        if (item instanceof ItemFood)
            return true;//!Fan.inventoryManager.has(item, 64);

        if (item instanceof ItemBlock)
            return true;

        if (item instanceof ItemEnderPearl)
            return true;

        if (item instanceof ItemArmor)
            return isBestArmor(itemStack);

        if (item instanceof ItemSnowball)
            return true;

        if (item instanceof ItemEgg)
            return true;

        if (item instanceof ItemMonsterPlacer)
            return true;

        if (item instanceof ItemBow)
            return true; //return Fan.inventoryManager.isBestBow(itemStack);

        if (item instanceof ItemPotion)
            return true;

        return item == Items.arrow; //&& !Fan.inventoryManager.has(Items.arrow, 64);
    }
}
	
