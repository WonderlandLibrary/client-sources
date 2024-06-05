/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.UTILS.world;

import com.google.common.collect.Multimap;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemCloth;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;
import net.minecraft.util.Vec3;

public class PlayerUtil {
    private static final Minecraft mc = Minecraft.getMinecraft();
    public static int MAX_HURT_RESISTANT_TIME = 20;
    public static final int INCLUDE_ARMOR_BEGIN = 5;
    public static final int EXCLUDE_ARMOR_BEGIN = 9;
    public static final int ONLY_HOT_BAR_BEGIN = 36;
    public static final int END = 45;

    public static EntityPlayerSP getLocalPlayer() {
        Minecraft.getMinecraft();
        return Minecraft.thePlayer;
    }

    public static boolean isInsideBlock() {
        int x = MathHelper.floor_double(Minecraft.thePlayer.boundingBox.minX);
        block0: while (x < MathHelper.floor_double(Minecraft.thePlayer.boundingBox.maxX) + 1) {
            int y = MathHelper.floor_double(Minecraft.thePlayer.boundingBox.minY + 1.0);
            while (true) {
                if (y < MathHelper.floor_double(Minecraft.thePlayer.boundingBox.maxY) + 2) {
                } else {
                    ++x;
                    continue block0;
                }
                for (int z = MathHelper.floor_double(Minecraft.thePlayer.boundingBox.minZ); z < MathHelper.floor_double(Minecraft.thePlayer.boundingBox.maxZ) + 1; ++z) {
                    Block block = PlayerUtil.mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (block == null || block instanceof BlockAir) continue;
                    AxisAlignedBB boundingBox = block.getCollisionBoundingBox(PlayerUtil.mc.theWorld, new BlockPos(x, y, z), PlayerUtil.mc.theWorld.getBlockState(new BlockPos(x, y, z)));
                    if (block instanceof BlockHopper) {
                        boundingBox = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);
                    }
                    if (boundingBox == null) continue;
                    if (!Minecraft.thePlayer.boundingBox.intersectsWith(boundingBox)) continue;
                    return true;
                }
                ++y;
            }
            break;
        }
        return false;
    }

    public static boolean isMoving() {
        if (Minecraft.thePlayer.isCollidedHorizontally) return false;
        if (Minecraft.thePlayer.isSneaking()) return false;
        MovementInput cfr_ignored_0 = Minecraft.thePlayer.movementInput;
        if (MovementInput.moveForward != 0.0f) return true;
        MovementInput cfr_ignored_1 = Minecraft.thePlayer.movementInput;
        if (MovementInput.moveStrafe != 0.0f) return true;
        return false;
    }

    public static boolean isMoving2() {
        if (Minecraft.thePlayer.moveForward != 0.0f) return true;
        if (Minecraft.thePlayer.moveStrafing != 0.0f) return true;
        return false;
    }

    public static boolean isGoodFood(ItemStack stack) {
        ItemFood food = (ItemFood)stack.getItem();
        if (food instanceof ItemAppleGold) {
            return true;
        }
        if (food.getHealAmount(stack) < 4) return false;
        if (!(food.getSaturationModifier(stack) >= 0.3f)) return false;
        return true;
    }

    public static Vec3 getVectorForRotation(float yaw, float pitch) {
        float f = MathHelper.cos((float)((double)(-yaw * 0.017163292f) - Math.PI));
        float f2 = MathHelper.sin((float)((double)(-yaw * 0.017163292f) - Math.PI));
        float f3 = -MathHelper.cos(-pitch * 0.017163292f);
        float f4 = MathHelper.sin(-pitch * 0.017163292f);
        return new Vec3(f2 * f3, f4, f * f3);
    }

    public static boolean isValid(ItemStack stack, boolean archery, boolean check) {
        if (stack == null) {
            return false;
        }
        if (!check) return true;
        if (stack.getItem() instanceof ItemBlock) {
            return PlayerUtil.isGoodBlockStack(stack);
        }
        if (stack.getItem() instanceof ItemSword) {
            return PlayerUtil.isBestSword(stack);
        }
        if (stack.getItem() instanceof ItemTool) {
            return PlayerUtil.isBestTool(stack);
        }
        if (stack.getItem() instanceof ItemArmor) {
            return PlayerUtil.isBestArmor(stack);
        }
        if (stack.getItem() instanceof ItemPotion) {
            return PlayerUtil.isBuffPotion(stack);
        }
        if (stack.getItem() instanceof ItemFood) {
            return PlayerUtil.isGoodFood(stack);
        }
        if (stack.getItem() instanceof ItemBow && archery) {
            return PlayerUtil.isBestBow(stack);
        }
        if (archery && stack.getItem().getUnlocalizedName().equals("item.arrow")) {
            return true;
        }
        if (!(stack.getItem() instanceof ItemEnderPearl)) return PlayerUtil.isGoodItem(stack);
        return true;
    }

    public static boolean isBestTool(ItemStack itemStack) {
        int type = PlayerUtil.getToolType(itemStack);
        Tool bestTool = new Tool(-1, -1.0, null);
        for (int i = 9; i < 45; ++i) {
            double efficiency;
            ItemStack stack = Minecraft.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (stack == null || !(stack.getItem() instanceof ItemTool) || type != PlayerUtil.getToolType(stack) || !((efficiency = (double)PlayerUtil.getToolEfficiency(stack)) > bestTool.getEfficiency())) continue;
            bestTool = new Tool(i, efficiency, stack);
        }
        if (bestTool.getStack() == itemStack) return true;
        if ((double)PlayerUtil.getToolEfficiency(itemStack) > bestTool.getEfficiency()) return true;
        return false;
    }

    public static float getToolEfficiency(ItemStack itemStack) {
        ItemTool tool = (ItemTool)itemStack.getItem();
        float efficiency = tool.getToolMaterial().getEfficiencyOnProperMaterial();
        int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack);
        if (!(efficiency > 1.0f)) return efficiency;
        if (lvl <= 0) return efficiency;
        efficiency += (float)(lvl * lvl + 1);
        return efficiency;
    }

    public static int getToolType(ItemStack stack) {
        ItemTool tool = (ItemTool)stack.getItem();
        if (tool instanceof ItemPickaxe) {
            return 0;
        }
        if (tool instanceof ItemAxe) {
            return 1;
        }
        if (!(tool instanceof ItemSpade)) return -1;
        return 2;
    }

    public static boolean isGoodBlockStack(ItemStack stack) {
        if (stack.stackSize >= 1) return PlayerUtil.isValidBlock(Block.getBlockFromItem(stack.getItem()), true);
        return false;
    }

    public static boolean isBuffPotion(ItemStack stack) {
        PotionEffect effect;
        ItemPotion potion = (ItemPotion)stack.getItem();
        List<PotionEffect> effects = potion.getEffects(stack);
        Iterator<PotionEffect> iterator = effects.iterator();
        do {
            if (!iterator.hasNext()) return true;
        } while (!Potion.potionTypes[(effect = iterator.next()).getPotionID()].isBadEffect());
        return false;
    }

    public static boolean potionHasEffect(ItemStack itemStack, int id) {
        PotionEffect effect;
        if (!(itemStack.getItem() instanceof ItemPotion)) return false;
        ItemPotion potion = (ItemPotion)itemStack.getItem();
        Iterator<PotionEffect> iterator = potion.getEffects(itemStack).iterator();
        do {
            if (!iterator.hasNext()) return false;
        } while ((effect = iterator.next()).getPotionID() != id);
        return true;
    }

    public static boolean isGoodItem(ItemStack stack) {
        Item item = stack.getItem();
        if (item instanceof ItemBucket && ((ItemBucket)item).isFull != Blocks.flowing_water) {
            return false;
        }
        if (item instanceof ItemExpBottle) return false;
        if (item instanceof ItemFishingRod) return false;
        if (item instanceof ItemEgg) return false;
        if (item instanceof ItemSnowball) return false;
        if (item instanceof ItemSkull) return false;
        if (item instanceof ItemBucket) return false;
        return true;
    }

    public static boolean isBestSword(ItemStack itemStack) {
        double damage = 0.0;
        ItemStack bestStack = null;
        for (int i = 9; i < 45; ++i) {
            double newDamage;
            ItemStack stack = Minecraft.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (stack == null || !(stack.getItem() instanceof ItemSword) || !((newDamage = PlayerUtil.getItemDamage(stack)) > damage)) continue;
            damage = newDamage;
            bestStack = stack;
        }
        if (bestStack == itemStack) return true;
        if (PlayerUtil.getItemDamage(itemStack) >= damage) return true;
        return false;
    }

    public static boolean isBestBow(ItemStack itemStack) {
        double bestBowDmg = -1.0;
        ItemStack bestBow = null;
        for (int i = 9; i < 45; ++i) {
            double damage;
            ItemStack stack = Minecraft.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (stack == null || !(stack.getItem() instanceof ItemBow) || !((damage = PlayerUtil.getBowDamage(stack)) > bestBowDmg)) continue;
            bestBow = stack;
            bestBowDmg = damage;
        }
        if (itemStack == bestBow) return true;
        if (PlayerUtil.getBowDamage(itemStack) > bestBowDmg) return true;
        return false;
    }

    public static double getBowDamage(ItemStack stack) {
        double damage = 0.0;
        if (!(stack.getItem() instanceof ItemBow)) return damage;
        if (!stack.isItemEnchanted()) return damage;
        damage += (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
        return damage;
    }

    public static boolean isBestArmor(ItemStack itemStack) {
        ItemArmor itemArmor = (ItemArmor)itemStack.getItem();
        double reduction = 0.0;
        ItemStack bestStack = null;
        for (int i = 5; i < 45; ++i) {
            double newReduction;
            ItemStack stack = Minecraft.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (stack == null || !(stack.getItem() instanceof ItemArmor)) continue;
            ItemArmor stackArmor = (ItemArmor)stack.getItem();
            if (stackArmor.armorType != itemArmor.armorType || !((newReduction = PlayerUtil.getDamageReduction(stack)) > reduction)) continue;
            reduction = newReduction;
            bestStack = stack;
        }
        if (bestStack == itemStack) return true;
        if (PlayerUtil.getDamageReduction(itemStack) > reduction) return true;
        return false;
    }

    public static double getItemDamage(ItemStack stack) {
        double damage = 0.0;
        Multimap<String, AttributeModifier> attributeModifierMap = stack.getAttributeModifiers();
        for (String attributeName : attributeModifierMap.keySet()) {
            if (!attributeName.equals("generic.attackDamage")) continue;
            Iterator<AttributeModifier> attributeModifiers = attributeModifierMap.get(attributeName).iterator();
            if (!attributeModifiers.hasNext()) break;
            damage += attributeModifiers.next().getAmount();
            break;
        }
        if (!stack.isItemEnchanted()) return damage;
        damage += (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack);
        damage += (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25;
        return damage;
    }

    public static double getDamageReduction(ItemStack stack) {
        ItemArmor armor;
        double reduction = 0.0;
        if (stack.getItem() instanceof ItemSkull) return reduction += (double)armor.damageReduceAmount;
        if (stack.getItem() instanceof ItemCloth) return reduction += (double)armor.damageReduceAmount;
        if (stack.getItem() instanceof ItemBanner) return reduction += (double)armor.damageReduceAmount;
        armor = (ItemArmor)stack.getItem();
        if (!stack.isItemEnchanted()) return reduction += (double)armor.damageReduceAmount;
        reduction += (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack) * 0.25;
        return reduction += (double)armor.damageReduceAmount;
    }

    public static boolean isInLiquid() {
        Minecraft mc = Minecraft.getMinecraft();
        if (Minecraft.thePlayer == null) {
            return false;
        }
        int x = MathHelper.floor_double(Minecraft.thePlayer.getEntityBoundingBox().minX);
        while (x < MathHelper.floor_double(Minecraft.thePlayer.getEntityBoundingBox().maxX) + 1) {
            for (int z = MathHelper.floor_double(Minecraft.thePlayer.getEntityBoundingBox().minZ); z < MathHelper.floor_double(Minecraft.thePlayer.getEntityBoundingBox().maxZ) + 1; ++z) {
                BlockPos pos = new BlockPos(x, (int)Minecraft.thePlayer.getEntityBoundingBox().minY, z);
                Block block = mc.theWorld.getBlockState(pos).getBlock();
                if (block == null || block instanceof BlockAir) continue;
                return block instanceof BlockLiquid;
            }
            ++x;
        }
        return false;
    }

    public static boolean isValidBlock(Block block, boolean toPlace) {
        if (block instanceof BlockContainer) {
            return false;
        }
        if (toPlace) {
            if (block instanceof BlockFalling) return false;
            if (!block.isFullBlock()) return false;
            if (!block.isFullCube()) return false;
            return true;
        }
        Material material = block.getMaterial();
        if (material.isReplaceable()) return false;
        if (material.isLiquid()) return false;
        return true;
    }

    public static boolean isBad(ItemStack item) {
        if (item.getItem() instanceof ItemArmor) return false;
        if (item.getItem() instanceof ItemTool) return false;
        if (item.getItem() instanceof ItemBlock) return false;
        if (item.getItem() instanceof ItemSword) return false;
        if (item.getItem() instanceof ItemEnderPearl) return false;
        if (item.getItem() instanceof ItemFood) return false;
        if (item.getItem() instanceof ItemPotion) {
            if (!PlayerUtil.isBadPotion(item)) return false;
        }
        if (item.getDisplayName().toLowerCase().contains((Object)((Object)EnumChatFormatting.GRAY) + "(right click)")) return false;
        return true;
    }

    public static boolean isInLiquid(Entity e) {
        int x = MathHelper.floor_double(e.boundingBox.minY);
        while (x < MathHelper.floor_double(e.boundingBox.maxX) + 1) {
            for (int z = MathHelper.floor_double(e.boundingBox.minZ); z < MathHelper.floor_double(e.boundingBox.maxZ) + 1; ++z) {
                BlockPos pos = new BlockPos(x, (int)e.boundingBox.minY, z);
                Block block = Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
                if (block == null || block instanceof BlockAir) continue;
                return block instanceof BlockLiquid;
            }
            ++x;
        }
        return false;
    }

    public static boolean isBadPotion(ItemStack stack) {
        PotionEffect effect;
        if (stack == null) return false;
        if (!(stack.getItem() instanceof ItemPotion)) return false;
        ItemPotion potion = (ItemPotion)stack.getItem();
        if (!ItemPotion.isSplash(stack.getItemDamage())) return false;
        Iterator<PotionEffect> iterator = potion.getEffects(stack).iterator();
        do {
            if (!iterator.hasNext()) return false;
            PotionEffect o = iterator.next();
            effect = o;
            if (effect.getPotionID() == Potion.poison.getId()) return true;
            if (effect.getPotionID() == Potion.harm.getId()) return true;
            if (effect.getPotionID() == Potion.moveSlowdown.getId()) return true;
        } while (effect.getPotionID() != Potion.weakness.getId());
        return true;
    }

    public static boolean isOnLiquid() {
        Minecraft mc = Minecraft.getMinecraft();
        AxisAlignedBB boundingBox = Minecraft.thePlayer.getEntityBoundingBox();
        if (boundingBox == null) {
            return false;
        }
        boundingBox = boundingBox.contract(0.01, 0.0, 0.01).offset(0.0, -0.01, 0.0);
        boolean onLiquid = false;
        int y = (int)boundingBox.minY;
        int x = MathHelper.floor_double(boundingBox.minX);
        while (x < MathHelper.floor_double(boundingBox.maxX + 1.0)) {
            for (int z = MathHelper.floor_double(boundingBox.minZ); z < MathHelper.floor_double(boundingBox.maxZ + 1.0); ++z) {
                Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                if (block == Blocks.air) continue;
                if (!(block instanceof BlockLiquid)) {
                    return false;
                }
                onLiquid = true;
            }
            ++x;
        }
        return onLiquid;
    }

    public static boolean isHoldingSword() {
        Minecraft.getMinecraft();
        if (Minecraft.thePlayer == null) return false;
        if (Minecraft.getMinecraft().theWorld == null) return false;
        Minecraft.getMinecraft();
        if (Minecraft.thePlayer.getCurrentEquippedItem() == null) return false;
        Minecraft.getMinecraft();
        if (!(Minecraft.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword)) return false;
        return true;
    }

    private static class Tool {
        private final int slot;
        private final double efficiency;
        private final ItemStack stack;

        public Tool(int slot, double efficiency, ItemStack stack) {
            this.slot = slot;
            this.efficiency = efficiency;
            this.stack = stack;
        }

        public int getSlot() {
            return this.slot;
        }

        public double getEfficiency() {
            return this.efficiency;
        }

        public ItemStack getStack() {
            return this.stack;
        }
    }
}

