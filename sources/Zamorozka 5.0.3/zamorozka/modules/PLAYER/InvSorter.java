package zamorozka.modules.PLAYER;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.Hero.settings.Setting;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventUpdate;
import zamorozka.event.events.UpdateEvent;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.modules.COMBAT.AutoArmor;
import zamorozka.ui.InventoryUtil;
import zamorozka.ui.TimerHelper;
import zamorozka.ui.Timerr;

public class InvSorter extends Module {

	@Override
	public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Basic");
        options.add("Other");
        Zamorozka.instance.settingsManager.rSetting(new Setting("Sort Mode", this, "Basic", options));
		Zamorozka.settingsManager.rSetting(new Setting("BlockCap", this, 128, 8, 256, true));
		Zamorozka.settingsManager.rSetting(new Setting("SortDelay", this, 1, 0, 10, true));
		Zamorozka.settingsManager.rSetting(new Setting("Archer", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("Food", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("Sword", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("InvCleaner", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("OpenInv", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("NoMovingSwap", this, false));
	}
	
    public static int weaponSlot = 36, pickaxeSlot = 37, axeSlot = 38, shovelSlot = 39;
    Timerr timer = new Timerr();
    ArrayList<Integer> whitelistedItems = new ArrayList<>();
    public static final List<Block> invalidBlocks = Arrays.asList(Blocks.ENCHANTING_TABLE, Blocks.FURNACE, Blocks.CARPET, Blocks.CRAFTING_TABLE, Blocks.TRAPPED_CHEST, Blocks.CHEST, Blocks.DISPENSER, Blocks.AIR, Blocks.WATER, Blocks.LAVA, Blocks.FLOWING_WATER, Blocks.FLOWING_LAVA, Blocks.SAND, Blocks.SNOW_LAYER, Blocks.TORCH, Blocks.ANVIL, Blocks.JUKEBOX, Blocks.STONE_BUTTON, Blocks.WOODEN_BUTTON, Blocks.LEVER, Blocks.NOTEBLOCK, Blocks.STONE_PRESSURE_PLATE, Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, Blocks.WOODEN_PRESSURE_PLATE, Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, Blocks.STONE_SLAB, Blocks.WOODEN_SLAB, Blocks.STONE_SLAB2, Blocks.RED_MUSHROOM, Blocks.BROWN_MUSHROOM, Blocks.YELLOW_FLOWER, Blocks.RED_FLOWER, Blocks.ANVIL, Blocks.GLASS_PANE, Blocks.STAINED_GLASS_PANE, Blocks.IRON_BARS, Blocks.CACTUS, Blocks.LADDER, Blocks.WEB);
	
	public InvSorter() {
		super("InvSorter", 0, Category.PLAYER);
	}
	@EventTarget
    public void onUpdate(UpdateEvent eventUpdate) {
        long delay = (long) Zamorozka.settingsManager.getSettingByName("SortDelay").getValDouble() * 50;
        String mode = Zamorozka.settingsManager.getSettingByName("Sort Mode").getValString();
        this.setSuffix(mode);
        if (!eventUpdate.isPre())
            return;
        if(!(mc.currentScreen instanceof GuiInventory) && (Zamorozka.settingsManager.getSettingByName("OpenInv").getValBoolean()))
        	return;
        if(mc.player.isMoving() && (Zamorozka.settingsManager.getSettingByName("NoMovingSwap").getValBoolean())) {
        	return;
        }
        if (mc.currentScreen == null || mc.currentScreen instanceof GuiInventory || mc.currentScreen instanceof GuiChat) {
            if (timer.delay(delay) && weaponSlot >= 36) {
                if (!mc.player.inventoryContainer.getSlot(weaponSlot).getHasStack()) {
                    getBestWeapon(weaponSlot);
                } else {
                    if (!isBestWeapon(mc.player.inventoryContainer.getSlot(weaponSlot).getStack())) {
                        getBestWeapon(weaponSlot);
                    }
                }
            }
            if (timer.delay(delay) && pickaxeSlot >= 36) {
                getBestPickaxe(pickaxeSlot);
            }
            if (timer.delay(delay) && shovelSlot >= 36) {
                getBestShovel(shovelSlot);
            }
            if (timer.delay(delay) && axeSlot >= 36) {
                getBestAxe(axeSlot);
            }
            if (timer.delay(delay) && Zamorozka.settingsManager.getSettingByName("InvCleaner").getValBoolean()) {
                for (int i = 9; i < 45; i++) {
                    if (mc.player.inventoryContainer.getSlot(i).getHasStack()) {
                        ItemStack is = mc.player.inventoryContainer.getSlot(i).getStack();
                        if (shouldDrop(is, i)) {
                            drop(i);
                            if(delay == 0) {
                            	mc.player.closeScreen();
                            }
                            timer.reset();
                            if (delay > 0)
                                break;
                        }
                    }
                }
            }
        }
    }

    public void shiftClick(int slot) {
        mc.playerController.windowClick(mc.player.inventoryContainer.windowId, slot, 0, ClickType.QUICK_MOVE, mc.player);
    }
    public void swap(int slot1, int hotbarSlot) {
        mc.playerController.windowClick(mc.player.inventoryContainer.windowId, slot1, hotbarSlot, ClickType.SWAP, mc.player);
    }
    public void drop(int slot) {
        mc.playerController.windowClick(mc.player.inventoryContainer.windowId, slot, 1, ClickType.THROW, mc.player);
    }
    public boolean isBestWeapon(ItemStack stack) {
        float damage = getDamage(stack);
        for (int i = 9; i < 45; i++) {
            if (mc.player.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.player.inventoryContainer.getSlot(i).getStack();
                if (getDamage(is) > damage && (is.getItem() instanceof ItemSword || !Zamorozka.settingsManager.getSettingByName("Sword").getValBoolean()))
                    return false;
            }
        }
        if ((stack.getItem() instanceof ItemSword || !Zamorozka.settingsManager.getSettingByName("Sword").getValBoolean())) {
            return true;
        } else {
            return false;
        }
    }
    public void getBestWeapon(int slot) {
        for (int i = 9; i < 45; i++) {
            if (mc.player.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.player.inventoryContainer.getSlot(i).getStack();
                if (isBestWeapon(is) && getDamage(is) > 0 && (is.getItem() instanceof ItemSword || !Zamorozka.settingsManager.getSettingByName("Sword").getValBoolean())) {
                    swap(i, slot - 36);
                    timer.reset();
                    break;
                }
            }
        }
    }
    private float getDamage(ItemStack stack) {
        float damage = 0;
        Item item = stack.getItem();
        if (item instanceof ItemTool) {
            ItemTool tool = (ItemTool) item;
            damage += tool.getDamageVsEntity();
        }
        if (item instanceof ItemSword) {
            ItemSword sword = (ItemSword) item;
            damage += sword.getDamageVsEntity();
        }
        damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(16), stack) * 1.25f + EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(20), stack) * 0.01f;
        return damage;
    }
    public boolean shouldDrop(ItemStack stack, int slot) {
        if (stack.getDisplayName().toLowerCase().contains("/")) {
            return false;
        }
        if (stack.getDisplayName().toLowerCase().contains("ןנוהלועû")) {
            return false;
        }
        if (stack.getDisplayName().toLowerCase().contains("§k||")) {
            return false;
        }
        if (stack.getDisplayName().toLowerCase().contains("wool")) {
            return false;
        }
        if (stack.getDisplayName().toLowerCase().contains("כמבבט")) {
            return false;
        }
        if ((slot == weaponSlot && isBestWeapon(mc.player.inventoryContainer.getSlot(weaponSlot).getStack())) ||
                (slot == pickaxeSlot && isBestPickaxe(mc.player.inventoryContainer.getSlot(pickaxeSlot).getStack()) && pickaxeSlot >= 0) ||
                (slot == axeSlot && isBestAxe(mc.player.inventoryContainer.getSlot(axeSlot).getStack()) && axeSlot >= 0) ||
                (slot == shovelSlot && isBestShovel(mc.player.inventoryContainer.getSlot(shovelSlot).getStack()) && shovelSlot >= 0)) {
            return false;
        }
        if (stack.getItem() instanceof ItemArmor) {
            for (int type = 1; type < 5; type++) {
                if (mc.player.inventoryContainer.getSlot(4 + type).getHasStack()) {
                    ItemStack is = mc.player.inventoryContainer.getSlot(4 + type).getStack();
                    if (InventoryUtil.isBestArmor(is, type)) {
                        continue;
                    }
                }
                if (InventoryUtil.isBestArmor(stack, type)) {
                    return false;
                }
            }
        }
        if (stack.getItem() instanceof ItemBlock && (getBlockCount() > (float)Zamorozka.settingsManager.getSettingByName("BlockCap").getValDouble() || invalidBlocks.contains(((ItemBlock) stack.getItem()).getBlock()))) {
            return true;
        }
        if (stack.getItem() instanceof ItemPotion) {
            if (isBadPotion(stack)) {
                return true;
            }
        }

        if (stack.getItem() instanceof ItemFood && Zamorozka.settingsManager.getSettingByName("Food").getValBoolean() && !(stack.getItem() instanceof ItemAppleGold)) {
            return true;
        }
        if (stack.getItem() instanceof ItemHoe || stack.getItem() instanceof ItemTool || stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemArmor) {
            return true;
        }
        if ((stack.getItem() instanceof ItemBow || stack.getItem().getUnlocalizedName().contains("arrow")) && Zamorozka.settingsManager.getSettingByName("Archer").getValBoolean()) {
            return true;
        }

        return (stack.getItem().getUnlocalizedName().contains("tnt")) ||
                (stack.getItem().getUnlocalizedName().contains("stick")) ||
                (stack.getItem().getUnlocalizedName().contains("egg")) ||
                (stack.getItem().getUnlocalizedName().contains("string")) ||
                (stack.getItem().getUnlocalizedName().contains("cake")) ||
                (stack.getItem().getUnlocalizedName().contains("mushroom")) ||
                (stack.getItem().getUnlocalizedName().contains("flint")) ||
                (stack.getItem().getUnlocalizedName().contains("compass")) ||
                (stack.getItem().getUnlocalizedName().contains("dyePowder")) ||
                (stack.getItem().getUnlocalizedName().contains("feather")) ||
                (stack.getItem().getUnlocalizedName().contains("bucket")) ||
                (stack.getItem().getUnlocalizedName().contains("chest") && !stack.getDisplayName().toLowerCase().contains("collect")) ||
                (stack.getItem().getUnlocalizedName().contains("snow")) ||
                (stack.getItem().getUnlocalizedName().contains("fish")) ||
                (stack.getItem().getUnlocalizedName().contains("enchant")) ||
                (stack.getItem().getUnlocalizedName().contains("exp")) ||
                (stack.getItem().getUnlocalizedName().contains("shears")) ||
                (stack.getItem().getUnlocalizedName().contains("anvil")) ||
                (stack.getItem().getUnlocalizedName().contains("torch")) ||
                (stack.getItem().getUnlocalizedName().contains("seeds")) ||
                (stack.getItem().getUnlocalizedName().contains("leather")) ||
                (stack.getItem().getUnlocalizedName().contains("reeds")) ||
                (stack.getItem().getUnlocalizedName().contains("skull")) ||
                (stack.getItem().getUnlocalizedName().contains("wool")) ||
                (stack.getItem().getUnlocalizedName().contains("record")) ||
                (stack.getItem().getUnlocalizedName().contains("snowball")) ||
                (stack.getItem() instanceof ItemGlassBottle) ||
                (stack.getItem().getUnlocalizedName().contains("piston"));
    }
    public ArrayList<Integer> getWhitelistedItem() {
        return whitelistedItems;
    }
    private int getBlockCount() {
        int blockCount = 0;
        for (int i = 0; i < 45; i++) {
            if (mc.player.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.player.inventoryContainer.getSlot(i).getStack();
                Item item = is.getItem();
                if (is.getItem() instanceof ItemBlock && !invalidBlocks.contains(((ItemBlock) item).getBlock())) {
                    blockCount += is.stackSize;
                }
            }
        }
        return blockCount;
    }
    private void getBestPickaxe(int slot) {
        for (int i = 9; i < 45; i++) {
            if (mc.player.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.player.inventoryContainer.getSlot(i).getStack();

                if (isBestPickaxe(is) && pickaxeSlot != i) {
                    if (!isBestWeapon(is))
                        if (!mc.player.inventoryContainer.getSlot(pickaxeSlot).getHasStack()) {
                            swap(i, pickaxeSlot - 36);
                            timer.reset();
                            if ((float)Zamorozka.settingsManager.getSettingByName("SortDelay").getValDouble() > 0)
                                return;
                        } else if (!isBestPickaxe(mc.player.inventoryContainer.getSlot(pickaxeSlot).getStack())) {
                            swap(i, pickaxeSlot - 36);
                            timer.reset();
                            if ((float)Zamorozka.settingsManager.getSettingByName("SortDelay").getValDouble() > 0)
                                return;
                        }
                }
            }
        }
    }
    private void getBestShovel(int slot) {
        for (int i = 9; i < 45; i++) {
            if (mc.player.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.player.inventoryContainer.getSlot(i).getStack();

                if (isBestShovel(is) && shovelSlot != i) {
                    if (!isBestWeapon(is))
                        if (!mc.player.inventoryContainer.getSlot(shovelSlot).getHasStack()) {
                            swap(i, shovelSlot - 36);
                            timer.reset();
                            if ((float)Zamorozka.settingsManager.getSettingByName("SortDelay").getValDouble() > 0)
                                return;
                        } else if (!isBestShovel(mc.player.inventoryContainer.getSlot(shovelSlot).getStack())) {
                            swap(i, shovelSlot - 36);
                            timer.reset();
                            if ((float)Zamorozka.settingsManager.getSettingByName("SortDelay").getValDouble() > 0)
                                return;
                        }
                }
            }
        }
    }
    private void getBestAxe(int slot) {
        for (int i = 9; i < 45; i++) {
            if (mc.player.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.player.inventoryContainer.getSlot(i).getStack();

                if (isBestAxe(is) && axeSlot != i) {
                    if (!isBestWeapon(is))
                        if (!mc.player.inventoryContainer.getSlot(axeSlot).getHasStack()) {
                            swap(i, axeSlot - 36);
                            timer.reset();
                            if ((float)Zamorozka.settingsManager.getSettingByName("SortDelay").getValDouble() > 0)
                                return;
                        } else if (!isBestAxe(mc.player.inventoryContainer.getSlot(axeSlot).getStack())) {
                            swap(i, axeSlot - 36);
                            timer.reset();
                            if ((float)Zamorozka.settingsManager.getSettingByName("SortDelay").getValDouble() > 0)
                                return;
                        }
                }
            }
        }
    }
    private boolean isBestPickaxe(ItemStack stack) {
        Item item = stack.getItem();
        if (!(item instanceof ItemPickaxe))
            return false;
        float value = getToolEffect(stack);
        for (int i = 9; i < 45; i++) {
            if (mc.player.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.player.inventoryContainer.getSlot(i).getStack();
                if (getToolEffect(is) > value && is.getItem() instanceof ItemPickaxe) {
                    return false;
                }
            }
        }
        return true;
    }
    private boolean isBestShovel(ItemStack stack) {
        Item item = stack.getItem();
        if (!(item instanceof ItemSpade))
            return false;
        float value = getToolEffect(stack);
        for (int i = 9; i < 45; i++) {
            if (mc.player.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.player.inventoryContainer.getSlot(i).getStack();
                if (getToolEffect(is) > value && is.getItem() instanceof ItemSpade) {
                    return false;
                }
            }
        }
        return true;
    }
    private boolean isBestAxe(ItemStack stack) {
        Item item = stack.getItem();
        if (!(item instanceof ItemAxe))
            return false;
        float value = getToolEffect(stack);
        for (int i = 9; i < 45; i++) {
            if (mc.player.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.player.inventoryContainer.getSlot(i).getStack();
                if (getToolEffect(is) > value && is.getItem() instanceof ItemAxe && !isBestWeapon(stack)) {
                    return false;
                }
            }
        }
        return true;
    }
    private float getToolEffect(ItemStack stack) {
        Item item = stack.getItem();
        if (!(item instanceof ItemTool))
            return 0;
        String name = item.getUnlocalizedName();
        ItemTool tool = (ItemTool) item;
        float value = 1;
        if (item instanceof ItemPickaxe) {
            value = tool.getStrVsBlock(stack, Blocks.STONE.getDefaultState());
            if (name.toLowerCase().contains("gold")) {
                value -= 5;
            }
        } else if (item instanceof ItemSpade) {
            value = tool.getStrVsBlock(stack, Blocks.DIRT.getDefaultState());
            if (name.toLowerCase().contains("gold")) {
                value -= 5;
            }
        } else if (item instanceof ItemAxe) {
            value = tool.getStrVsBlock(stack, Blocks.LOG.getDefaultState());
            if (name.toLowerCase().contains("gold")) {
                value -= 5;
            }
        } else
            return 1f;
        value += EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(32), stack) * 0.0075D;
        value += EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(34), stack) / 100d;
        return value;
    }
    private boolean isBadPotion(ItemStack stack) {
        if (stack != null && stack.getItem() instanceof ItemPotion) {
            for (final Object o : PotionUtils.getEffectsFromStack(stack)) {
                final PotionEffect effect = (PotionEffect) o;
                if (effect.getPotion() == Potion.getPotionById(19) || effect.getPotion() == Potion.getPotionById(7) || effect.getPotion() == Potion.getPotionById(2) || effect.getPotion() == Potion.getPotionById(18)) {
                    return true;
                }
            }
        }
        return false;
    }
    boolean invContainsType(int type) {
        for (int i = 9; i < 45; i++) {
            if (mc.player.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.player.inventoryContainer.getSlot(i).getStack();
                Item item = is.getItem();
                if (item instanceof ItemArmor) {
                    ItemArmor armor = (ItemArmor) item;
                    if (type == armor.armorType.getIndex()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public void getBestArmor() {
        for (int type = 1; type < 5; type++) {
            if (mc.player.inventoryContainer.getSlot(4 + type).getHasStack()) {
                ItemStack is = mc.player.inventoryContainer.getSlot(4 + type).getStack();
                if (InventoryUtil.isBestArmor(is, type)) {
                    continue;
                } else {
                    drop(4 + type);
                }
            }
            for (int i = 9; i < 45; i++) {
                if (mc.player.inventoryContainer.getSlot(i).getHasStack()) {
                    ItemStack is = mc.player.inventoryContainer.getSlot(i).getStack();
                    if (InventoryUtil.isBestArmor(is, type) && InventoryUtil.getProtection(is) > 0) {
                        shiftClick(i);
                        timer.reset();
                        if ((float)Zamorozka.settingsManager.getSettingByName("SortDelay").getValDouble() > 0)
                            return;
                    }
                }
            }
        }
    }
}