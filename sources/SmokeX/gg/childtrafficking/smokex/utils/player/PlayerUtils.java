// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.utils.player;

import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.client.entity.EntityPlayerSP;
import gg.childtrafficking.smokex.utils.system.NetworkUtils;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.block.BlockAir;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockContainer;
import net.minecraft.item.ItemCloth;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.item.ItemAppleGold;
import java.util.List;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemTool;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import java.util.Iterator;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.IInventory;
import net.minecraft.client.Minecraft;

public final class PlayerUtils
{
    private static final Minecraft mc;
    public static final int INCLUDE_ARMOR_BEGIN = 5;
    public static final int EXCLUDE_ARMOR_BEGIN = 9;
    public static final int ONLY_HOT_BAR_BEGIN = 36;
    public static final int END = 45;
    
    public static boolean isInventoryEmpty(final IInventory inventory, final boolean archery) {
        for (int i = 0; i < inventory.getSizeInventory(); ++i) {
            if (isValid(inventory.getStackInSlot(i), archery)) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isInLobby() {
        for (final Slot slot : PlayerUtils.mc.thePlayer.inventoryContainer.inventorySlots) {
            final ItemStack item = slot.getStack();
            if (item != null && item.getDisplayName() != null && item.getDisplayName().contains("(Right Click)")) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isValid(final ItemStack stack, final boolean archery) {
        if (stack == null) {
            return false;
        }
        if (stack.getItem() instanceof ItemBlock) {
            return isGoodBlockStack(stack);
        }
        if (stack.getItem() instanceof ItemSword) {
            return isBestSword(stack);
        }
        if (stack.getItem() instanceof ItemTool) {
            return isBestTool(stack);
        }
        if (stack.getItem() instanceof ItemArmor) {
            return isBestArmor(stack);
        }
        if (stack.getItem() instanceof ItemPotion) {
            return isBuffPotion(stack);
        }
        if (stack.getItem() instanceof ItemFood) {
            return isGoodFood(stack);
        }
        if (stack.getItem() instanceof ItemBow && archery) {
            return isBestBow(stack);
        }
        return (archery && stack.getItem().getUnlocalizedName().equals("item.arrow")) || stack.getItem() instanceof ItemEnderPearl || isGoodItem(stack);
    }
    
    public static boolean isBestBow(final ItemStack itemStack) {
        double bestBowDmg = -1.0;
        ItemStack bestBow = null;
        for (int i = 9; i < 45; ++i) {
            final ItemStack stack = PlayerUtils.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (stack != null && stack.getItem() instanceof ItemBow) {
                final double damage = getBowDamage(stack);
                if (damage > bestBowDmg) {
                    bestBow = stack;
                    bestBowDmg = damage;
                }
            }
        }
        return itemStack == bestBow || getBowDamage(itemStack) > bestBowDmg;
    }
    
    public static boolean isGoodItem(final ItemStack stack) {
        final Item item = stack.getItem();
        return (!(item instanceof ItemBucket) || ((ItemBucket)item).isFull == Blocks.flowing_water) && !(item instanceof ItemExpBottle) && !(item instanceof ItemFishingRod) && !(item instanceof ItemEgg) && !(item instanceof ItemSnowball) && !(item instanceof ItemSkull) && !(item instanceof ItemBucket);
    }
    
    public static boolean isBuffPotion(final ItemStack stack) {
        final ItemPotion potion = (ItemPotion)stack.getItem();
        final List<PotionEffect> effects = potion.getEffects(stack);
        for (final PotionEffect effect : effects) {
            if (Potion.potionTypes[effect.getPotionID()].isBadEffect()) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isGoodFood(final ItemStack stack) {
        final ItemFood food = (ItemFood)stack.getItem();
        return food instanceof ItemAppleGold || (food.getHealAmount(stack) >= 4 && food.getSaturationModifier(stack) >= 0.3f);
    }
    
    public static Block getBlock(final BlockPos blockPos) {
        final WorldClient theWorld = PlayerUtils.mc.theWorld;
        Block block = null;
        if (theWorld != null) {
            final IBlockState blockState = theWorld.getBlockState(blockPos);
            if (blockState != null) {
                block = blockState.getBlock();
                return block;
            }
        }
        return null;
    }
    
    public static IBlockState getState(final BlockPos blockPos) {
        return PlayerUtils.mc.theWorld.getBlockState(blockPos);
    }
    
    public static boolean isFullBlock(final BlockPos blockPos) {
        final Block block = getBlock(blockPos);
        if (block != null) {
            final AxisAlignedBB collisionBoundingBox = block.getCollisionBoundingBox(PlayerUtils.mc.theWorld, blockPos, getState(blockPos));
            if (collisionBoundingBox != null) {
                return collisionBoundingBox.maxX - collisionBoundingBox.minX == 1.0 && collisionBoundingBox.maxY - collisionBoundingBox.minY == 1.0 && collisionBoundingBox.maxZ - collisionBoundingBox.minZ == 1.0;
            }
        }
        return false;
    }
    
    public static void windowClick(final int windowId, final int slotId, final int mouseButtonClicked, final ClickType mode) {
        PlayerUtils.mc.playerController.windowClick(windowId, slotId, mouseButtonClicked, mode.ordinal(), PlayerUtils.mc.thePlayer);
    }
    
    public static void windowClick(final int slotId, final int mouseButtonClicked, final ClickType mode) {
        PlayerUtils.mc.playerController.windowClick(PlayerUtils.mc.thePlayer.openContainer.windowId, slotId, mouseButtonClicked, mode.ordinal(), PlayerUtils.mc.thePlayer);
    }
    
    public static void openInventory() {
        PlayerUtils.mc.getNetHandler().getNetworkManager().sendPacket(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
    }
    
    public static void closeInventory() {
        PlayerUtils.mc.getNetHandler().getNetworkManager().sendPacket(new C0DPacketCloseWindow(PlayerUtils.mc.thePlayer.inventoryContainer.windowId));
    }
    
    public static boolean isBestSword(final ItemStack itemStack) {
        double damage = 0.0;
        ItemStack bestStack = null;
        for (int i = 9; i < 45; ++i) {
            final ItemStack stack = PlayerUtils.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (stack != null && stack.getItem() instanceof ItemSword) {
                final double newDamage = getItemDamage(stack);
                if (newDamage > damage) {
                    damage = newDamage;
                    bestStack = stack;
                }
            }
        }
        return bestStack == itemStack || getItemDamage(itemStack) >= damage;
    }
    
    public static double getBowDamage(final ItemStack stack) {
        double damage = 0.0;
        if (stack.getItem() instanceof ItemBow && stack.isItemEnchanted()) {
            damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
        }
        return damage;
    }
    
    public static int getToolType(final ItemStack stack) {
        final ItemTool tool = (ItemTool)stack.getItem();
        if (tool instanceof ItemPickaxe) {
            return 0;
        }
        if (tool instanceof ItemAxe) {
            return 1;
        }
        if (tool instanceof ItemSpade) {
            return 2;
        }
        return -1;
    }
    
    public static boolean isBestTool(final ItemStack itemStack) {
        final int type = getToolType(itemStack);
        Tool bestTool = new Tool(-1, -1.0, null);
        for (int i = 9; i < 45; ++i) {
            final ItemStack stack = PlayerUtils.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (stack != null && stack.getItem() instanceof ItemTool && type == getToolType(stack)) {
                final double efficiency = getToolEfficiency(stack);
                if (efficiency > bestTool.getEfficiency()) {
                    bestTool = new Tool(i, efficiency, stack);
                }
            }
        }
        return bestTool.getStack() == itemStack || getToolEfficiency(itemStack) > bestTool.getEfficiency();
    }
    
    public static float getToolEfficiency(final ItemStack itemStack) {
        final ItemTool tool = (ItemTool)itemStack.getItem();
        float efficiency = tool.getToolMaterial().getEfficiencyOnProperMaterial();
        final int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack);
        if (efficiency > 1.0f && lvl > 0) {
            efficiency += lvl * lvl + 1;
        }
        return efficiency;
    }
    
    public static boolean isBestArmor(final ItemStack itemStack) {
        final ItemArmor itemArmor = (ItemArmor)itemStack.getItem();
        double reduction = 0.0;
        ItemStack bestStack = null;
        for (int i = 5; i < 45; ++i) {
            final ItemStack stack = PlayerUtils.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (stack != null && stack.getItem() instanceof ItemArmor) {
                final ItemArmor stackArmor = (ItemArmor)stack.getItem();
                if (stackArmor.armorType == itemArmor.armorType) {
                    final double newReduction = getDamageReduction(stack);
                    if (newReduction > reduction) {
                        reduction = newReduction;
                        bestStack = stack;
                    }
                }
            }
        }
        return bestStack == itemStack || getDamageReduction(itemStack) > reduction;
    }
    
    public static double getDamageReduction(final ItemStack stack) {
        double reduction = 0.0;
        if (!(stack.getItem() instanceof ItemSkull) && !(stack.getItem() instanceof ItemCloth)) {
            final ItemArmor armor = (ItemArmor)stack.getItem();
            reduction += armor.damageReduceAmount;
            if (stack.isItemEnchanted()) {
                reduction += EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack) * 0.25;
            }
        }
        return reduction;
    }
    
    public static boolean isGoodBlockStack(final ItemStack stack) {
        return stack.stackSize >= 1 && isValidBlock(Block.getBlockFromItem(stack.getItem()), true);
    }
    
    public static boolean isValidBlock(final Block block, final boolean toPlace) {
        if (block instanceof BlockContainer) {
            return false;
        }
        if (toPlace) {
            return !(block instanceof BlockFalling) && block.isFullBlock() && block.isFullCube();
        }
        final Material material = block.getMaterial();
        return !material.isReplaceable() && !material.isLiquid();
    }
    
    public static double getItemDamage(final ItemStack stack) {
        double damage = 0.0;
        final Multimap<String, AttributeModifier> attributeModifierMap = stack.getAttributeModifiers();
        for (final String attributeName : attributeModifierMap.keySet()) {
            if (attributeName.equals("generic.attackDamage")) {
                final Iterator<AttributeModifier> attributeModifiers = attributeModifierMap.get((Object)attributeName).iterator();
                if (attributeModifiers.hasNext()) {
                    damage += attributeModifiers.next().getAmount();
                    break;
                }
                break;
            }
        }
        if (stack.isItemEnchanted()) {
            damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack);
            damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25;
        }
        return damage;
    }
    
    public static boolean isOnGround(final double height) {
        return !PlayerUtils.mc.theWorld.getCollidingBoundingBoxes(PlayerUtils.mc.thePlayer, PlayerUtils.mc.thePlayer.getEntityBoundingBox().offset(0.0, -height, 0.0)).isEmpty();
    }
    
    public static float[] getPredictedRotations(final EntityLivingBase ent) {
        final double x = ent.posX + (ent.posX - ent.lastTickPosX);
        final double z = ent.posZ + (ent.posZ - ent.lastTickPosZ);
        final double y = ent.posY + ent.getEyeHeight() / 2.0f - 0.8;
        return getRotationFromPosition(x, z, y);
    }
    
    public static double getArmorStrength(final EntityPlayer entityPlayer) {
        double armorstrength = 0.0;
        for (int index = 3; index >= 0; --index) {
            final ItemStack stack = entityPlayer.inventory.armorInventory[index];
            if (stack != null) {
                armorstrength += getDamageReduction(stack);
            }
        }
        return armorstrength;
    }
    
    public static boolean isTeammate(final EntityPlayer entity) {
        final String entName = entity.getDisplayName().getFormattedText();
        final String playerName = PlayerUtils.mc.thePlayer.getDisplayName().getFormattedText();
        return entName.length() >= 2 && playerName.length() >= 2 && entName.startsWith("§") && playerName.startsWith("§") && entName.charAt(1) == playerName.charAt(1);
    }
    
    public static boolean checkPing(final EntityPlayer entityPlayer) {
        final NetworkPlayerInfo info = PlayerUtils.mc.getNetHandler().getPlayerInfo(entityPlayer.getUniqueID());
        return info != null && info.getResponseTime() >= 1;
    }
    
    public static boolean isShooterTeammate(final EntityPlayer entityPlayer) {
        final List<String> scoreboardList = ScoreboardUtils.getScoreboardData();
        boolean crims = false;
        for (final String string : scoreboardList) {
            if (string.startsWith("Team: §4")) {
                crims = true;
                break;
            }
        }
        return crims ? entityPlayer.getDisplayName().getFormattedText().contains("§4") : entityPlayer.getDisplayName().getFormattedText().contains("§3");
    }
    
    public static boolean isHoldingSword() {
        return PlayerUtils.mc.thePlayer.getCurrentEquippedItem() != null && PlayerUtils.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword;
    }
    
    public static boolean isHoldingItem() {
        return PlayerUtils.mc.thePlayer.getCurrentEquippedItem() != null;
    }
    
    public static boolean isBlockUnder() {
        for (int i = (int)(PlayerUtils.mc.thePlayer.posY - 1.0); i > 0; --i) {
            final BlockPos pos = new BlockPos(PlayerUtils.mc.thePlayer.posX, i, PlayerUtils.mc.thePlayer.posZ);
            if (!(PlayerUtils.mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir)) {
                return true;
            }
        }
        return false;
    }
    
    public static void damage(final boolean safe) {
        if (safe) {
            final double offset = 0.0625 - Math.random() * 0.003000000026077032;
            final EntityPlayerSP player = PlayerUtils.mc.thePlayer;
            final double x = player.posX;
            final double y = player.posY;
            final double z = player.posZ;
            for (int i = 0; i < getMaxFallDist() / offset + 1.0; ++i) {
                NetworkUtils.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(x, y - offset, z, false));
                NetworkUtils.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, false));
            }
            NetworkUtils.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, true));
        }
        else {
            final double packets = Math.ceil(getMinFallDist() / 0.0625);
            PlayerUtils.mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(PlayerUtils.mc.thePlayer.posX, PlayerUtils.mc.thePlayer.posY, PlayerUtils.mc.thePlayer.posZ, true));
            for (int j = 0; j < packets; ++j) {
                PlayerUtils.mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(PlayerUtils.mc.thePlayer.posX, PlayerUtils.mc.thePlayer.posY + 0.06258781076883, PlayerUtils.mc.thePlayer.posZ, false));
                PlayerUtils.mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(PlayerUtils.mc.thePlayer.posX, PlayerUtils.mc.thePlayer.posY + 8.781076883E-5, PlayerUtils.mc.thePlayer.posZ, false));
            }
            PlayerUtils.mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(PlayerUtils.mc.thePlayer.posX, PlayerUtils.mc.thePlayer.posY, PlayerUtils.mc.thePlayer.posZ, false));
            PlayerUtils.mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(PlayerUtils.mc.thePlayer.posX, PlayerUtils.mc.thePlayer.posY, PlayerUtils.mc.thePlayer.posZ, true));
        }
    }
    
    public static double getMinFallDist() {
        double baseFallDist = 3.2;
        if (PlayerUtils.mc.thePlayer.isPotionActive(Potion.jump)) {
            baseFallDist += PlayerUtils.mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1.0f;
        }
        return baseFallDist;
    }
    
    public static float getMaxFallDist() {
        final PotionEffect potioneffect = PlayerUtils.mc.thePlayer.getActivePotionEffect(Potion.jump);
        final int f = (potioneffect != null) ? (potioneffect.getAmplifier() + 1) : 0;
        return (float)(PlayerUtils.mc.thePlayer.getMaxFallHeight() + f);
    }
    
    public static void damageHigh() {
        for (int i = 0; i < PlayerUtils.mc.thePlayer.getMaxFallHeight() / 0.05510000046342611; ++i) {
            final EntityPlayerSP player = PlayerUtils.mc.thePlayer;
            NetworkUtils.sendPacket(new C03PacketPlayer.C06PacketPlayerPosLook(player.posX, player.posY + 0.060100000351667404 + StrictMath.random() * 0.003, player.posZ, PlayerUtils.mc.thePlayer.rotationYaw, PlayerUtils.mc.thePlayer.rotationPitch, false));
            NetworkUtils.sendPacket(new C03PacketPlayer.C06PacketPlayerPosLook(player.posX, player.posY + 5.000000237487257E-4 + StrictMath.random() * 0.003, player.posZ, PlayerUtils.mc.thePlayer.rotationYaw, PlayerUtils.mc.thePlayer.rotationPitch, false));
            NetworkUtils.sendPacket(new C03PacketPlayer.C06PacketPlayerPosLook(player.posX, player.posY + 0.004999999888241291 + 6.01000003516674E-8 + StrictMath.random() * 0.003, player.posZ, PlayerUtils.mc.thePlayer.rotationYaw, PlayerUtils.mc.thePlayer.rotationPitch, false));
            NetworkUtils.sendPacket(new C03PacketPlayer.C06PacketPlayerPosLook(player.posX, player.posY + 0.060100000351667404 + StrictMath.random() * 0.003, player.posZ, PlayerUtils.mc.thePlayer.rotationYaw, PlayerUtils.mc.thePlayer.rotationPitch, false));
            NetworkUtils.sendPacket(new C03PacketPlayer.C06PacketPlayerPosLook(player.posX, player.posY + 5.000000237487257E-4 + StrictMath.random() * 0.003, player.posZ, PlayerUtils.mc.thePlayer.rotationYaw, PlayerUtils.mc.thePlayer.rotationPitch, false));
            NetworkUtils.sendPacket(new C03PacketPlayer.C06PacketPlayerPosLook(player.posX, player.posY + 0.004999999888241291 + 6.01000003516674E-8 + StrictMath.random() * 0.003, player.posZ, PlayerUtils.mc.thePlayer.rotationYaw, PlayerUtils.mc.thePlayer.rotationPitch, false));
        }
        NetworkUtils.sendPacket(new C03PacketPlayer(true));
    }
    
    public static boolean isLookingAtEntity(final float yaw, final float pitch, final EntityPlayer start, final Entity entity, final double range, final boolean intercept, final boolean rayTrace) {
        final Vec3 src = PlayerUtils.mc.thePlayer.getPositionEyes(1.0f);
        final Vec3 rotationVec = Entity.getVectorForRotation(pitch, yaw);
        final Vec3 dest = src.addVector(rotationVec.xCoord * range, rotationVec.yCoord * range, rotationVec.zCoord * range);
        final MovingObjectPosition obj = PlayerUtils.mc.theWorld.rayTraceBlocks(src, dest, false, false, true);
        if (obj == null) {
            return false;
        }
        if (obj.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            if (rayTrace) {
                return false;
            }
            if (start.getDistanceToEntity(entity) > 3.0) {
                return false;
            }
        }
        return !intercept || entity.getEntityBoundingBox().expand(0.10000000149011612, 0.10000000149011612, 0.10000000149011612).calculateIntercept(src, dest) != null;
    }
    
    public static boolean isLookingAtEntity(final float yaw, final float pitch, final Entity entity, final double range, final boolean intercept, final boolean rayTrace) {
        final EntityPlayer player = PlayerUtils.mc.thePlayer;
        final Vec3 src = PlayerUtils.mc.thePlayer.getPositionEyes(1.0f);
        final Vec3 rotationVec = Entity.getVectorForRotation(pitch, yaw);
        final Vec3 dest = src.addVector(rotationVec.xCoord * range, rotationVec.yCoord * range, rotationVec.zCoord * range);
        final MovingObjectPosition obj = PlayerUtils.mc.theWorld.rayTraceBlocks(src, dest, false, false, true);
        if (obj == null) {
            return false;
        }
        if (obj.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            if (rayTrace) {
                return false;
            }
            if (player.getDistanceToEntity(entity) > 3.0) {
                return false;
            }
        }
        return !intercept || entity.getEntityBoundingBox().expand(0.10000000149011612, 0.10000000149011612, 0.10000000149011612).calculateIntercept(src, dest) != null;
    }
    
    public static boolean isOnServer(final String ip) {
        return !PlayerUtils.mc.isSingleplayer() && PlayerUtils.mc.getCurrentServerData().serverIP.toLowerCase().contains(ip.toLowerCase());
    }
    
    public static float[] getRotations(final EntityLivingBase ent) {
        final EntityPlayerSP player = PlayerUtils.mc.thePlayer;
        final double xDist = ent.posX - player.posX;
        final double zDist = ent.posZ - player.posZ;
        final AxisAlignedBB entityBB = ent.getEntityBoundingBox().expand(0.10000000149011612, 0.10000000149011612, 0.10000000149011612);
        final double playerEyePos = player.posY + player.getEyeHeight();
        final double yDist = (playerEyePos > entityBB.maxY) ? (entityBB.maxY - playerEyePos) : ((playerEyePos < entityBB.minY) ? (entityBB.minY - playerEyePos) : 0.0);
        final double fDist = MathHelper.sqrt_double(xDist * xDist + zDist * zDist);
        final float yaw = (float)(Math.atan2(zDist, xDist) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(yDist, fDist) * 180.0 / 3.141592653589793));
        return new float[] { yaw, pitch };
    }
    
    public static float[] getBuzzRotations(final EntityLivingBase ent) {
        final double x = ent.posX + Math.random() * 0.05;
        final double z = ent.posZ + Math.random() * 0.05;
        double y = 0.0;
        if (ent.posY >= PlayerUtils.mc.thePlayer.posY) {
            y = Math.max(ent.posY, PlayerUtils.mc.thePlayer.posY + 0.6) + Math.random() * 0.05;
        }
        else {
            y = ent.posY + 0.6 + Math.random() * 0.05;
        }
        return getRotationFromPosition(x, z, y);
    }
    
    public static float[] getNCPRotations(final Entity entity) {
        final EntityPlayerSP player = PlayerUtils.mc.thePlayer;
        final double xDiff = entity.posX - player.posX;
        final double zDiff = entity.posZ - player.posZ;
        final double yDiff = entity.posY - player.posY - 0.8;
        final double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        final float yaw = (float)(System.currentTimeMillis() % 360L);
        final float pitch = 90.0f;
        return new float[] { yaw, pitch };
    }
    
    public static float[] getRotationFromPosition(final double x, final double z, final double y) {
        final double xDiff = x - PlayerUtils.mc.thePlayer.posX;
        final double zDiff = z - PlayerUtils.mc.thePlayer.posZ;
        final double yDiff = y - PlayerUtils.mc.thePlayer.posY - 0.8;
        final double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        final float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(yDiff, dist) * 180.0 / 3.141592653589793));
        return new float[] { yaw, pitch };
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
    
    public enum ClickType
    {
        CLICK, 
        SHIFT_CLICK, 
        SWAP_WITH_HOT_BAR_SLOT, 
        PLACEHOLDER, 
        DROP_ITEM;
    }
    
    private static class Tool
    {
        private final int slot;
        private final double efficiency;
        private final ItemStack stack;
        
        public Tool(final int slot, final double efficiency, final ItemStack stack) {
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
