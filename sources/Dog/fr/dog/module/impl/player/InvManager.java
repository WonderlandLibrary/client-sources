package fr.dog.module.impl.player;

import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.player.PlayerTickEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.property.impl.BooleanProperty;
import fr.dog.property.impl.NumberProperty;
import fr.dog.util.BlockUtil;
import fr.dog.util.math.TimeUtil;
import fr.dog.util.packet.PacketUtil;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.*;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class InvManager extends Module {
    public InvManager() {
        super("InvManager", ModuleCategory.PLAYER);
        this.registerProperties(delay,spoof,slot,drop,arrow,axe,picaxe,spade,bucket,book,block,gapple,speedPotion);
    }

    private final NumberProperty delay = NumberProperty.newInstance("Delay", 0F, 100F, 1000F, 50F);
    private final BooleanProperty spoof = BooleanProperty.newInstance("Spoof", false);
    private final BooleanProperty slot = BooleanProperty.newInstance("Slot Change", false);
    private final BooleanProperty drop = BooleanProperty.newInstance("Drop Option", false);
    private final BooleanProperty arrow = BooleanProperty.newInstance("Drop Arrow And bow", true,drop::getValue);
    private final NumberProperty block = NumberProperty.newInstance("Block Slot", 1F, 9F, 9F, 1F,slot::getValue);
    private final NumberProperty gapple = NumberProperty.newInstance("Gapple Slot", 1F, 8F, 9F, 1F,slot::getValue);
    private final NumberProperty speedPotion = NumberProperty.newInstance("Speed Potion Slot", 1F, 7F, 9F, 1F,slot::getValue);
    private final BooleanProperty bucket = BooleanProperty.newInstance("Drop Bucket", false,drop::getValue);
    private final BooleanProperty book = BooleanProperty.newInstance("Drop Book", false,drop::getValue);
    private final BooleanProperty axe = BooleanProperty.newInstance("Drop Axe", false,drop::getValue);
    private final BooleanProperty picaxe = BooleanProperty.newInstance("Drop PickAxe", false,drop::getValue);
    private final BooleanProperty spade = BooleanProperty.newInstance("Drop Shovel", false,drop::getValue);

    private final TimeUtil timeUtil = new TimeUtil();
    private final int weaponSlot = 36;
    private final int pickaxeSlot = 37;
    private final int axeSlot = 38;
    private final int shovelSlot = 39;

    @SubscribeEvent
    private void onPlayerTick(PlayerTickEvent event) {
        if (mc.thePlayer.openContainer instanceof ContainerChest) {
            return;
        }

        if (!(mc.currentScreen instanceof GuiInventory || (spoof.getValue() && mc.currentScreen == null))) {
            return;
        }
        getBestArmor();
        getBestSpeedPotion();
        for (int type = 1; type < 5; type++) {
            if (mc.thePlayer.inventoryContainer.getSlot(4 + type).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(4 + type).getStack();
                if (!isBestArmor(is, type)) {
                    return;
                }
            } else if (invContainsType(type - 1)) {
                return;
            }
        }

        if (!mc.thePlayer.inventoryContainer.getSlot(weaponSlot).getHasStack()) {
            getBestWeapon(weaponSlot);
        } else {
            if (!isBestWeapon(mc.thePlayer.inventoryContainer.getSlot(weaponSlot).getStack())) {
                getBestWeapon(weaponSlot);
            }
        }
        getBestPickaxe();
        getBestShovel();
        getBestAxe();
        getBestGap();
        getBestBlock();

        for (int i = 9; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (shouldDrop(is, i)) {
                    drop(i);
                }
            }
            if (i == 44 && timeUtil.finished(delay.getValue().intValue())) {
                PacketUtil.sendPacket(new C0DPacketCloseWindow());
            }
        }
    }



    public static double getProtection(ItemStack stack) {
        double prot = 0;
        if ((stack.getItem() instanceof ItemArmor armor)) {
            prot += armor.damageReduceAmount + (100 - armor.damageReduceAmount) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack) * 0.0075D;
            prot += EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, stack) / 100d;
            prot += EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, stack) / 100d;
            prot += EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack) / 100d;
            prot += EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack) / 50d;
        }
        return prot;
    }

    public void shiftClick(int slot) {
        if (timeUtil.finished(delay.getValue().intValue())) {
            mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, 0, 1, mc.thePlayer);
            timeUtil.reset();
        }
    }

    public void swap(int slot1, int hotbarSlot) {
        if (timeUtil.finished(delay.getValue().intValue())) {
            mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot1, hotbarSlot, 2, mc.thePlayer);
            timeUtil.reset();
        }
    }

    public void drop(int slot) {
        if (timeUtil.finished(delay.getValue().intValue())) {
            mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, 1, 4, mc.thePlayer);
            timeUtil.reset();
        }
    }

    public boolean isBestWeapon(ItemStack stack) {
        float damage = getDamage(stack);

        for (int i = 9; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (getDamage(is) > damage && (is.getItem() instanceof ItemSword))
                    return false;
            }
        }
        return stack.getItem() instanceof ItemSword;

    }

    public void getBestWeapon(int slot) {
        for (int i = 9; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (isBestWeapon(is) && getDamage(is) > 0 && (is.getItem() instanceof ItemSword)) {
                    swap(i, slot - 36);
                    break;
                }
            }
        }
    }


    private float getDamage(ItemStack stack) {
        float damage = 0;
        Item item = stack.getItem();
        if (item instanceof ItemSword sword) {
            damage += sword.attackDamage;
        }
        damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25f +
                EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack) * 2f;
        if (stack.getItemDamage() * 3 > stack.getMaxItemUseDuration()) {
            damage -= 3;
        }
        return damage;
    }

    public boolean shouldDrop(ItemStack stack, int slot) {
        if (stack.getDisplayName().toLowerCase().contains("(right click)")) {
            return false;
        }
        if (stack.getDisplayName().toLowerCase().contains(" k||")) {
            return false;
        }
        //return false;

        if (stack.getItem() instanceof ItemFishingRod) {
            return true;
        }

        if ((slot == weaponSlot && isBestWeapon(mc.thePlayer.inventoryContainer.getSlot(weaponSlot).getStack())) ||
                (slot == pickaxeSlot && (!picaxe.getValue() && isBestPickaxe(mc.thePlayer.inventoryContainer.getSlot(pickaxeSlot).getStack()))) ||
                (slot == axeSlot && (!axe.getValue() && isBestAxe(mc.thePlayer.inventoryContainer.getSlot(axeSlot).getStack()))) ||
                (slot == shovelSlot && (!spade.getValue() && isBestShovel(mc.thePlayer.inventoryContainer.getSlot(shovelSlot).getStack())))) {
            return false;
        }
        if (stack.getItem() instanceof ItemArmor) {
            for (int type = 1; type < 5; type++) {
                if (mc.thePlayer.inventoryContainer.getSlot(4 + type).getHasStack()) {
                    ItemStack is = mc.thePlayer.inventoryContainer.getSlot(4 + type).getStack();
                    if (isBestArmor(is, type)) {
                        continue;
                    }
                }
                if (isBestArmor(stack, type)) {
                    return false;
                }
            }
        }
        if (stack.getItem() instanceof ItemBlock && (getBlockCount() > 256 || BlockUtil.blockBlacklist.contains(((ItemBlock) stack.getItem()).getBlock()))) {
            return true;
        }
        if (stack.getItem() instanceof ItemPotion) {
            if (isBadPotion(stack)) {
                return true;
            }
        }

        //return true;
        if (stack.getItem() instanceof ItemHoe || stack.getItem() instanceof ItemTool || stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemArmor) {
            return true;
        }
        if ((stack.getItem() instanceof ItemBow || stack.getItem().getUnlocalizedName().contains("arrow"))) {
            if (arrow.getValue()) {
                return true;
            }
        }
        if ((stack.getItem() instanceof ItemBucket || stack.getItem() instanceof ItemBucketMilk || stack.getItem().getUnlocalizedName().contains("Bucket"))) {
            if (bucket.getValue()) {
                return true;
            }
        }
        if ((stack.getItem() instanceof ItemEnchantedBook || stack.getItem() instanceof ItemBook || stack.getItem().getUnlocalizedName().contains("Book"))) {
            if (book.getValue()) {
                return true;
            }
        }



        return (stack.getItem().getUnlocalizedName().contains("tnt")) ||
                (stack.getItem().getUnlocalizedName().contains("stick")) ||
                (stack.getItem().getUnlocalizedName().contains("egg")) ||
                (stack.getItem().getUnlocalizedName().contains("string")) ||
                (stack.getItem().getUnlocalizedName().contains("cake")) ||
                (stack.getItem().getUnlocalizedName().contains("mushroom")) ||
                (stack.getItem().getUnlocalizedName().contains("flint")) ||
                (stack.getItem().getUnlocalizedName().contains("feather")) ||
                (stack.getItem().getUnlocalizedName().contains("shears")) ||
                (stack.getItem().getUnlocalizedName().contains("torch")) ||
                (stack.getItem().getUnlocalizedName().contains("seeds")) ||
                (stack.getItem().getUnlocalizedName().contains("reeds")) ||
                (stack.getItem().getUnlocalizedName().contains("record")) ||
                (stack.getItem().getUnlocalizedName().contains("snowball")) ||
                (stack.getItem().getUnlocalizedName().contains("beacon")) ||
                (stack.getItem().getUnlocalizedName().contains("piston")) ||
                (stack.getItem() instanceof ItemGlassBottle) ||
                (stack.getItem() instanceof ItemExpBottle);
    }


    private int getBlockCount() {
        int blockCount = 0;
        for (int i = 0; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                Item item = is.getItem();
                if (is.getItem() instanceof ItemBlock && !BlockUtil.blockBlacklist.contains(((ItemBlock) item).getBlock())) {
                    blockCount += is.stackSize;
                }
            }
        }
        return blockCount;
    }

    private void getBestPickaxe() {
        for (int i = 9; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();

                if (isBestPickaxe(is) && pickaxeSlot != i) {
                    if (!isBestWeapon(is))
                        if (!mc.thePlayer.inventoryContainer.getSlot(pickaxeSlot).getHasStack()) {
                            swap(i, pickaxeSlot - 36);
                        } else if (!isBestPickaxe(mc.thePlayer.inventoryContainer.getSlot(pickaxeSlot).getStack())) {
                            swap(i, pickaxeSlot - 36);
                        }

                }
            }
        }
    }

    private void getBestShovel() {
        for (int i = 9; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();

                if (isBestShovel(is) && shovelSlot != i) {
                    if (!isBestWeapon(is))
                        if (!mc.thePlayer.inventoryContainer.getSlot(shovelSlot).getHasStack()) {
                            swap(i, shovelSlot - 36);
                        } else if (!isBestShovel(mc.thePlayer.inventoryContainer.getSlot(shovelSlot).getStack())) {
                            swap(i, shovelSlot - 36);
                        }

                }
            }
        }
    }


    private void getBestAxe() {
        for (int i = 9; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();

                if (isBestAxe(is) && axeSlot != i) {
                    if (!isBestWeapon(is))
                        if (!mc.thePlayer.inventoryContainer.getSlot(axeSlot).getHasStack()) {
                            swap(i, axeSlot - 36);
                        } else if (!isBestAxe(mc.thePlayer.inventoryContainer.getSlot(axeSlot).getStack())) {
                            swap(i, axeSlot - 36);
                        }

                }
            }
        }
    }

    private void getBestBlock() {
        int selectedSlot = block.getValue().intValue() - 1;
        if (selectedSlot < 0 || selectedSlot > 8) {
            selectedSlot = 0;
        }
        for (int i = 9; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (is.getItem() instanceof ItemBlock) {
                    if (!mc.thePlayer.inventoryContainer.getSlot(selectedSlot + 36).getHasStack()) {
                        swap(i, selectedSlot);
                        break;
                    }
                }
            }
        }
    }

    private void getBestSpeedPotion() {
        int selectedSlot = speedPotion.getValue().intValue() - 1;
        if (selectedSlot < 0 || selectedSlot > 8) {
            selectedSlot = 0;
        }
        for (int i = 9; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (is.getItem() instanceof ItemPotion) {
                    ItemPotion potion = (ItemPotion) is.getItem();
                    if (isSpeedPotion(potion, is)) {
                        if (!mc.thePlayer.inventoryContainer.getSlot(selectedSlot + 36).getHasStack()) {
                            swap(i, selectedSlot);
                            break;
                        }
                    }
                }
            }
        }
    }
    private boolean isSpeedPotion(ItemPotion potion, ItemStack stack) {
        if (potion.getEffects(stack) != null) {
            for (PotionEffect effect : potion.getEffects(stack)) {
                if (effect.getPotionID() == Potion.moveSpeed.getId()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void getBestGap() {
        int selectedSlot = gapple.getValue().intValue();
        if (selectedSlot < 0 || selectedSlot > 8) {
            selectedSlot = 0;
        }

        for (int i = 9; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (is.getItem() instanceof ItemAppleGold) {
                    if (!mc.thePlayer.inventoryContainer.getSlot(selectedSlot - 1).getHasStack()) {
                        swap(i, selectedSlot - 1);
                        break;
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
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
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
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
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
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (getToolEffect(is) > value && is.getItem() instanceof ItemAxe && !isBestWeapon(stack)) {
                    return false;
                }

            }
        }
        return true;
    }

    private float getToolEffect(ItemStack stack) {
        Item item = stack.getItem();
        if (!(item instanceof ItemTool tool))
            return 0;
        String name = item.getUnlocalizedName();
        float value;
        if (item instanceof ItemPickaxe) {
            value = tool.getStrVsBlock(stack, Blocks.stone);
            if (name.toLowerCase().contains("gold")) {
                value -= 5;
            }
        } else if (item instanceof ItemSpade) {
            value = tool.getStrVsBlock(stack, Blocks.dirt);
            if (name.toLowerCase().contains("gold")) {
                value -= 5;
            }
        } else if (item instanceof ItemAxe) {
            value = tool.getStrVsBlock(stack, Blocks.log);
            if (name.toLowerCase().contains("gold")) {
                value -= 5;
            }
        } else
            return 1f;
        value += (float) (EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, stack) * 0.0075D);
        value += (float) (EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack) / 100d);
        return value;
    }

    private boolean isBadPotion(ItemStack stack) {
        if (stack != null && stack.getItem() instanceof ItemPotion potion) {
            if (potion.getEffects(stack) == null)
                return true;
            for (final PotionEffect o : potion.getEffects(stack)) {
                if (o.getPotionID() == Potion.poison.getId() || o.getPotionID() == Potion.harm.getId() || o.getPotionID() == Potion.moveSlowdown.getId() || o.getPotionID() == Potion.weakness.getId()) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean invContainsType(int type) {

        for (int i = 9; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                Item item = is.getItem();
                if (item instanceof ItemArmor armor) {
                    if (type == armor.armorType) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void getBestArmor() {
        for (int type = 1; type < 5; type++) {
            if (mc.thePlayer.inventoryContainer.getSlot(4 + type).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(4 + type).getStack();
                if (isBestArmor(is, type)) {
                    continue;
                } else {
                    drop(4 + type);
                }
            }
            for (int i = 9; i < 45; i++) {
                if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                    ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                    if (isBestArmor(is, type) && getProtection(is) > 0) {
                        shiftClick(i);
                    }
                }
            }
        }
    }

    public boolean isBestArmor(ItemStack stack, int type) {
        double prot = getProtection(stack);

        String strType = "";
        if (type == 1) {
            strType = "helmet";
        } else if (type == 2) {
            strType = "chestplate";
        } else if (type == 3) {
            strType = "leggings";
        } else if (type == 4) {
            strType = "boots";
        }

        if (!stack.getUnlocalizedName().contains(strType)) {
            return false;
        }
        for (int i = 5; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (getProtection(is) > prot && is.getUnlocalizedName().contains(strType))
                    return false;
            }
        }
        return true;
    }
}
