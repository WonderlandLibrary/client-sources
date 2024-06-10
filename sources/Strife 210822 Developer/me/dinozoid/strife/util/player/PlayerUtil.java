package me.dinozoid.strife.util.player;

import com.google.common.collect.Multimap;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.dinozoid.strife.StrifeClient;
import me.dinozoid.strife.module.implementations.player.ScaffoldModule;
import me.dinozoid.strife.util.MinecraftUtil;
import me.dinozoid.strife.util.network.HttpUtil;
import me.dinozoid.strife.util.system.MathUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.*;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomUtils;

import javax.tools.Tool;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PlayerUtil extends MinecraftUtil {

    public static int MAX_HURT_RESISTANT_TIME = 20;
    public static final int INCLUDE_ARMOR_BEGIN = 5;
    public static final int EXCLUDE_ARMOR_BEGIN = 9;
    public static final int ONLY_HOT_BAR_BEGIN = 36;
    public static final int END = 45;

    public enum ClickType {
        // if mouseButtonClicked is 0 `DROP_ITEM` will drop 1
        // item from the stack else if it is 1 it will drop the entire stack
        CLICK, SHIFT_CLICK, SWAP_WITH_HOT_BAR_SLOT, PLACEHOLDER, DROP_ITEM
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
    
    public static boolean isInventoryEmpty(IInventory inventory, boolean archery, boolean smart) {
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            if (isValid(inventory.getStackInSlot(i), archery, smart))
                return false;
        }
        return true;
    }

    public static boolean isInventoryFull() {
        for (int i = 9; i < 45; i++) {
            if (!mc.thePlayer.inventoryContainer.getSlot(i).getHasStack())
                return false;
        }
        return true;
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

    public static boolean isGoodItem(ItemStack stack) {
        final Item item = stack.getItem();
        if (item instanceof ItemBucket)
            if (((ItemBucket) item).isFull != Blocks.flowing_water)
                return false;

        return !(item instanceof ItemExpBottle) && !(item instanceof ItemFishingRod) &&
                !(item instanceof ItemEgg) && !(item instanceof ItemSnowball) &&
                !(item instanceof ItemSkull) && !(item instanceof ItemBucket);
    }

    public static boolean isHoldingSword() {
        return mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword;
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

    public static int getToolType(ItemStack stack) {
        ItemTool tool = (ItemTool) stack.getItem();
        if (tool instanceof ItemPickaxe) return 0;
        else if (tool instanceof ItemAxe) return 1;
        else if (tool instanceof ItemSpade) return 2;
        else return -1;
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
    
    public static boolean isGoodBlockStack(ItemStack stack) {
        if (stack.stackSize < 1)
            return false;
        return isValidBlock(Block.getBlockFromItem(stack.getItem()), true);
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

    public static float[] getPredictedRotations(EntityLivingBase ent) {
        double x = ent.posX + (ent.posX - ent.lastTickPosX) * Math.random();
        double z = ent.posZ + (ent.posZ - ent.lastTickPosZ) * Math.random();
        double y = ent.posY + ent.getEyeHeight() / 2.0F * Math.random();
        return getRotationFromPosition(x, z, y);
    }

    public static float[] getRotationFromPosition(double x, double z, double y) {
        double xDiff = x - mc.thePlayer.posX;
        double zDiff = z - mc.thePlayer.posZ;
        double yDiff = y - mc.thePlayer.posY - 0.8;
        double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float) Math.toDegrees(Math.atan2(zDiff, xDiff)) - 90.0F;
        float pitch = (float) -Math.toDegrees(Math.atan2(yDiff, dist));
        return new float[]{yaw, pitch};
    }

    public static float[] getScaffoldRotations(ScaffoldModule.BlockData data) {
        final Vec3 eyes = mc.thePlayer.getPositionEyes(RandomUtils.nextFloat(2.997f, 3.997f));
        final Vec3 position = new Vec3(data.pos.getX() + 0.49, data.pos.getY() + 0.49, data.pos.getZ() + 0.49).add(new Vec3(data.face.getDirectionVec()).scale(0.489997f));
        final Vec3 resultPosition = position.subtract(eyes);
        float yaw = (float) Math.toDegrees(Math.atan2(resultPosition.zCoord, resultPosition.xCoord)) - 90.0F;
        float pitch = (float) -Math.toDegrees(Math.atan2(resultPosition.yCoord, Math.hypot(resultPosition.xCoord, resultPosition.zCoord)));
        return new float[] {yaw, pitch};
    }

    public static Vec3 getVectorForRotation(float yaw, float pitch) {
        float f = MathHelper.cos((float) (-yaw * 0.017163291F - Math.PI));
        float f2 = MathHelper.sin((float) (-yaw * 0.017163291F - Math.PI));
        float f3 = -MathHelper.cos(-pitch * 0.017163291F);
        float f4 = MathHelper.sin(-pitch * 0.017163291F);
        return new Vec3(f2 * f3, f4, f * f3);
    }

    public static double getArmorStrength(EntityPlayer entityPlayer) {
        double armorstrength = 0;
        for (int index = 3; index >= 0; index--) {
            final ItemStack stack = entityPlayer.inventory.armorInventory[index];
            if (stack != null)
                armorstrength += getDamageReduction(stack);
        }
        return armorstrength;
    }
    
    public static boolean isGoodFood(ItemStack stack) {
        ItemFood food = (ItemFood) stack.getItem();
        if (food instanceof ItemAppleGold)
            return true;
        return food.getHealAmount(stack) >= 4 && food.getSaturationModifier(stack) >= 0.3F;
    }

    public static void windowClick(int windowId, int slotId, int mouseButtonClicked, ClickType mode) {
        mc.playerController.windowClick(windowId, slotId, mouseButtonClicked, mode.ordinal(), mc.thePlayer);
    }

    public static void windowClick(int slotId, int mouseButtonClicked, ClickType mode) {
        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slotId, mouseButtonClicked, mode.ordinal(), mc.thePlayer);
    }

    public static void openInventory() {
        mc.getNetHandler().getNetworkManager().sendPacket(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
    }

    public static void closeInventory() {
        mc.getNetHandler().getNetworkManager().sendPacket(new C0DPacketCloseWindow(mc.thePlayer.inventoryContainer.windowId));
    }
    
    public static void sendMessage(String message) {
        mc.thePlayer.addChatMessage(new ChatComponentText(ChatFormatting.translateAlternateColorCodes('&', message)));
    }

    public static void sendMessageWithPrefix(String message) {
        mc.thePlayer.addChatMessage(new ChatComponentText(StrifeClient.COMMAND_PREFIX + ChatFormatting.translateAlternateColorCodes('&', message)));
    }

    public static boolean isOnServer(String ip) {
        return !mc.isSingleplayer() && mc.getCurrentServerData().serverIP.endsWith(ip);
    }

    public static double getEffectiveHealth(EntityLivingBase entity) {
        return entity.getHealth() * (entity.getMaxHealth() / entity.getTotalArmorValue());
    }

    public static boolean isBlockUnder(double x, double y, double z) {
        for (int i = (int) y - 1; i > 0; --i) {
            if (new BlockPos(x, i, z).getBlock() instanceof BlockAir) continue;
            return true;
        }
        return false;
    }

    public static boolean isBlockUnder() {
        return isBlockUnder(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
    }

    public static boolean isTeammate(EntityPlayer entityPlayer) {
        if(entityPlayer != null) {
            String text = entityPlayer.getDisplayName().getFormattedText();
            String playerText = mc.thePlayer.getDisplayName().getFormattedText();
            if(text.length() < 2 || playerText.length() < 2) return false;
            if(!text.startsWith("\u00A7") || !playerText.startsWith("\u00A7")) return false;
            return text.charAt(1) == playerText.charAt(1);
        }
        return false;
    }

    public static CompletableFuture<String> getUUIDString(String name) {
        final CompletableFuture<HttpUtil.HttpResponse> httpsResponse = HttpUtil.asyncHttpsConnection("https://api.mojang.com/users/profiles/minecraft/" + name);
        final CompletableFuture<String> toComplete = new CompletableFuture<>();
        httpsResponse.whenCompleteAsync((response, throwable) -> {
            if(response.content().equals("Invalid UUID")) toComplete.complete(null);
            JsonParser parser = new JsonParser();
            JsonObject uuidObject = parser.parse(response.content()).getAsJsonObject();
            toComplete.complete(uuidObject.get("id").getAsString());
        });
        return toComplete;
    }

}
