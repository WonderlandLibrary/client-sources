package Focus.Beta.UTILS.world;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.google.common.collect.Multimap;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minecraft.block.*;
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

public class PlayerUtil {
    private final static Minecraft mc = Minecraft.getMinecraft();
    public static int MAX_HURT_RESISTANT_TIME = 20;
    public static final int INCLUDE_ARMOR_BEGIN = 5;
    public static final int EXCLUDE_ARMOR_BEGIN = 9;
    public static final int ONLY_HOT_BAR_BEGIN = 36;
    public static final int END = 45;

    public static EntityPlayerSP getLocalPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }
    public static boolean isInsideBlock() {
        for (int x = MathHelper.floor_double(
                mc.thePlayer.boundingBox.minX); x < MathHelper.floor_double(mc.thePlayer.boundingBox.maxX) + 1; ++x) {
            for (int y = MathHelper.floor_double(
                    mc.thePlayer.boundingBox.minY + 1.0D); y < MathHelper.floor_double(mc.thePlayer.boundingBox.maxY)
                    + 2; ++y) {
                for (int z = MathHelper.floor_double(
                        mc.thePlayer.boundingBox.minZ); z < MathHelper.floor_double(mc.thePlayer.boundingBox.maxZ)
                        + 1; ++z) {
                    Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (block != null && !(block instanceof BlockAir)) {
                        AxisAlignedBB boundingBox = block.getCollisionBoundingBox(mc.theWorld, new BlockPos(x, y, z),
                                mc.theWorld.getBlockState(new BlockPos(x, y, z)));
                        if (block instanceof BlockHopper) {
                            boundingBox = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);
                        }

                        if (boundingBox != null && mc.thePlayer.boundingBox.intersectsWith(boundingBox))
                            return true;
                    }
                }
            }
        }
        return false;
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
            return slot;
        }

        public double getEfficiency() {
            return efficiency;
        }

        public ItemStack getStack() {
            return stack;
        }
    }
    
    public static boolean isMoving() {
        if ((!mc.thePlayer.isCollidedHorizontally) && (!mc.thePlayer.isSneaking())) {
            return ((mc.thePlayer.movementInput.moveForward != 0.0F || mc.thePlayer.movementInput.moveStrafe != 0.0F));
        }
        return false;
    }
    public static boolean isMoving2() {
    	 return ((mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F));
    }
    public static boolean isGoodFood(ItemStack stack) {
        ItemFood food = (ItemFood) stack.getItem();
        if (food instanceof ItemAppleGold)
            return true;
        return food.getHealAmount(stack) >= 4 && food.getSaturationModifier(stack) >= 0.3F;
    }
    
    public static net.minecraft.util.Vec3 getVectorForRotation(float yaw, float pitch) {
        float f = MathHelper.cos((float) (-yaw * 0.017163291F - Math.PI));
        float f2 = MathHelper.sin((float) (-yaw * 0.017163291F - Math.PI));
        float f3 = -MathHelper.cos(-pitch * 0.017163291F);
        float f4 = MathHelper.sin(-pitch * 0.017163291F);
        return new net.minecraft.util.Vec3(f2 * f3, f4, f * f3);
    }

    public static boolean isValid(ItemStack stack, boolean archery, boolean check) {
        if (stack == null) return false;
        if(check) {
            if (stack.getItem() instanceof ItemBlock) {
                return isGoodBlockStack(stack);
            } else if (stack.getItem() instanceof ItemSword) {
                return isBestSword(stack);
            } else if (stack.getItem() instanceof ItemTool) {
                return isBestTool(stack);
            } else if (stack.getItem() instanceof ItemArmor) {
                return isBestArmor(stack);
            } else if (stack.getItem() instanceof ItemPotion) {
                return isBuffPotion(stack);
            } else if (stack.getItem() instanceof ItemFood) {
                return isGoodFood(stack);
            } else if (stack.getItem() instanceof ItemBow && archery) {
                return isBestBow(stack);
            } else if (archery && stack.getItem().getUnlocalizedName().equals("item.arrow")) {
                return true;
            } else if (stack.getItem() instanceof ItemEnderPearl) {
                return true;
            } else return isGoodItem(stack);
        }
        return true;
    }
    public static boolean isBestTool(ItemStack itemStack) {
        final int type = getToolType(itemStack);
        Tool bestTool = new Tool(-1, -1, null);
        for (int i = EXCLUDE_ARMOR_BEGIN; i < END; i++) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (stack != null && stack.getItem() instanceof ItemTool && type == getToolType(stack)) {
                double efficiency = getToolEfficiency(stack);
                if (efficiency > bestTool.getEfficiency())
                    bestTool = new Tool(i, efficiency, stack);
            }
        }
        return bestTool.getStack() == itemStack || getToolEfficiency(itemStack) > bestTool.getEfficiency();
    }
    
    public static float getToolEfficiency(ItemStack itemStack) {
        ItemTool tool = (ItemTool) itemStack.getItem();
        float efficiency = tool.getToolMaterial().getEfficiencyOnProperMaterial();
        int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack);
        if (efficiency > 1.0F && lvl > 0)
            efficiency += lvl * lvl + 1;
        return efficiency;
    }
    public static int getToolType(ItemStack stack) {
        ItemTool tool = (ItemTool) stack.getItem();
        if (tool instanceof ItemPickaxe) return 0;
        else if (tool instanceof ItemAxe) return 1;
        else if (tool instanceof ItemSpade) return 2;
        else return -1;
    }
    public static boolean isGoodBlockStack(ItemStack stack) {
        if (stack.stackSize < 1)
            return false;
        return isValidBlock(Block.getBlockFromItem(stack.getItem()), true);
    }
    

    public static boolean isBuffPotion(ItemStack stack) {
        ItemPotion potion = (ItemPotion) stack.getItem();
        List<PotionEffect> effects = potion.getEffects(stack);
        for (PotionEffect effect : effects)
            if (Potion.potionTypes[effect.getPotionID()].isBadEffect())
                return false;

        return true;
    }

    public static boolean potionHasEffect(ItemStack itemStack, int id) {
        if(itemStack.getItem() instanceof ItemPotion) {
            ItemPotion potion = (ItemPotion) itemStack.getItem();
            for(PotionEffect effect : potion.getEffects(itemStack)) {
                if(effect.getPotionID() == id) return true;
            }
        }
        return false;
    }
    public static boolean isGoodItem(ItemStack stack) {
        final Item item = stack.getItem();
        if (item instanceof ItemBucket)
            if (((ItemBucket) item).isFull != Blocks.flowing_water)
                return false;

        return !(item instanceof ItemExpBottle) && !(item instanceof ItemFishingRod) &&
                !(item instanceof ItemEgg) && !(item instanceof ItemSnowball) &&
                !(item instanceof ItemSkull) && !(item instanceof ItemBucket);
    }

    public static boolean isBestSword(ItemStack itemStack) {
        double damage = 0;
        ItemStack bestStack = null;
        for (int i = EXCLUDE_ARMOR_BEGIN; i < END; i++) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();

            if (stack != null && stack.getItem() instanceof ItemSword) {
                double newDamage = getItemDamage(stack);
                if (newDamage > damage) {
                    damage = newDamage;
                    bestStack = stack;
                }
            }
        }
        return bestStack == itemStack || getItemDamage(itemStack) >= damage;
    }

    public static boolean isBestBow(ItemStack itemStack) {
        double bestBowDmg = -1;
        ItemStack bestBow = null;
        for (int i = EXCLUDE_ARMOR_BEGIN; i < END; i++) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (stack != null && stack.getItem() instanceof ItemBow) {
                double damage = getBowDamage(stack);
                if (damage > bestBowDmg) {
                    bestBow = stack;
                    bestBowDmg = damage;
                }
            }
        }

        return itemStack == bestBow || getBowDamage(itemStack) > bestBowDmg;
    }

    public static double getBowDamage(ItemStack stack) {
        double damage = 0.0D;
        if (stack.getItem() instanceof ItemBow && stack.isItemEnchanted())
            damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
        return damage;
    }
    
    public static boolean isBestArmor(ItemStack itemStack) {
        ItemArmor itemArmor = (ItemArmor) itemStack.getItem();
        double reduction = 0.0;
        ItemStack bestStack = null;
        for (int i = INCLUDE_ARMOR_BEGIN; i < END; i++) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (stack != null && stack.getItem() instanceof ItemArmor) {
                ItemArmor stackArmor = (ItemArmor) stack.getItem();
                if (stackArmor.armorType == itemArmor.armorType) {
                    double newReduction = getDamageReduction(stack);
                    if (newReduction > reduction) {
                        reduction = newReduction;
                        bestStack = stack;
                    }
                }
            }
        }
        return bestStack == itemStack || getDamageReduction(itemStack) > reduction;
    }

    public static double getItemDamage(ItemStack stack) {
        double damage = 0.0;
        final Multimap<String, AttributeModifier> attributeModifierMap = stack.getAttributeModifiers();
        for (String attributeName : attributeModifierMap.keySet()) {
            if (attributeName.equals("generic.attackDamage")) {
                Iterator<AttributeModifier> attributeModifiers = attributeModifierMap.get(attributeName).iterator();
                if (attributeModifiers.hasNext())
                    damage += attributeModifiers.next().getAmount();
                break;
            }
        }
        if (stack.isItemEnchanted()) {
            damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack);
            damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25;
        }
        return damage;
    }

    public static double getDamageReduction(ItemStack stack) {
        double reduction = 0.0;
        if (!(stack.getItem() instanceof ItemSkull) && !(stack.getItem() instanceof ItemCloth) && !(stack.getItem() instanceof ItemBanner)) {
            ItemArmor armor = (ItemArmor) stack.getItem();
            reduction += armor.damageReduceAmount;
            if (stack.isItemEnchanted())
                reduction += EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack) * 0.25D;
        }
        return reduction;
    }
   
    public static boolean isInLiquid() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null) {
            return false;
        }
        for (int x = MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minX); x < MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().maxX) + 1; x++) {
            for (int z = MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minZ); z < MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().maxZ) + 1; z++) {
                BlockPos pos = new BlockPos(x, (int) mc.thePlayer.getEntityBoundingBox().minY, z);
                Block block = mc.theWorld.getBlockState(pos).getBlock();
                if ((block != null) && (!(block instanceof BlockAir))) {
                    return block instanceof BlockLiquid;
                }
            }
        }
        return false;
    }
    public static boolean isValidBlock(Block block, boolean toPlace) {
        if (block instanceof BlockContainer)
            return false;
        if (toPlace) {
            return !(block instanceof BlockFalling) && block.isFullBlock() && block.isFullCube();
        } else {
            final Material material = block.getMaterial();
            return !material.isReplaceable() && !material.isLiquid();
        }
    }

    public static boolean isBad(ItemStack item) {
        return (!(item.getItem() instanceof net.minecraft.item.ItemArmor) &&
                !(item.getItem() instanceof ItemTool) && !(item.getItem() instanceof net.minecraft.item.ItemBlock) &&
                !(item.getItem() instanceof ItemSword) && !(item.getItem() instanceof net.minecraft.item.ItemEnderPearl) &&
                !(item.getItem() instanceof net.minecraft.item.ItemFood) && (!(item.getItem() instanceof ItemPotion) || isBadPotion(item)) &&
                !item.getDisplayName().toLowerCase().contains(EnumChatFormatting.GRAY + "(right click)"));
    }
    public static boolean isInLiquid(Entity e) {
        for(int x = MathHelper.floor_double(e.boundingBox.minY); x < MathHelper.floor_double(e.boundingBox.maxX) + 1; ++x) {
            for(int z = MathHelper.floor_double(e.boundingBox.minZ); z < MathHelper.floor_double(e.boundingBox.maxZ) + 1; ++z) {
                BlockPos pos = new BlockPos(x, (int)e.boundingBox.minY, z);
                Block block = Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
                if(block != null && !(block instanceof BlockAir))
                    return block instanceof BlockLiquid;
            }
        }
        return false;
    }

    public static boolean isBadPotion(ItemStack stack) {
        if (stack != null && stack.getItem() instanceof ItemPotion) {
            ItemPotion potion = (ItemPotion)stack.getItem();
            if (ItemPotion.isSplash(stack.getItemDamage()))
                for (Object o : potion.getEffects(stack)) {
                    PotionEffect effect = (PotionEffect)o;
                    if (effect.getPotionID() == Potion.poison.getId() || effect.getPotionID() == Potion.harm.getId() || effect.getPotionID() == Potion.moveSlowdown.getId() || effect.getPotionID() == Potion.weakness.getId())
                        return true;
                }
        }
        return false;
    }


    public static boolean isOnLiquid() {
        Minecraft mc = Minecraft.getMinecraft();
        AxisAlignedBB boundingBox = mc.thePlayer.getEntityBoundingBox();
        if (boundingBox == null) {
            return false;
        }
        boundingBox = boundingBox.contract(0.01D, 0.0D, 0.01D).offset(0.0D, -0.01D, 0.0D);
        boolean onLiquid = false;
        int y = (int) boundingBox.minY;
        for (int x = MathHelper.floor_double(boundingBox.minX); x < MathHelper
                .floor_double(boundingBox.maxX + 1.0D); x++) {
            for (int z = MathHelper.floor_double(boundingBox.minZ); z < MathHelper
                    .floor_double(boundingBox.maxZ + 1.0D); z++) {
                Block block = mc.theWorld.getBlockState((new BlockPos(x, y, z))).getBlock();
                if (block != Blocks.air) {
                    if (!(block instanceof BlockLiquid)) {
                        return false;
                    }
                    onLiquid = true;
                }
            }
        }
        return onLiquid;
    }


    public static boolean isHoldingSword() {
        if (Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().theWorld != null && Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem() != null && Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword) {
            return true;
        }
        return false;
    }




}
