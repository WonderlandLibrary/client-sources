package client.metaware.impl.utils.util.other;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import client.metaware.impl.utils.util.PacketUtil;
import client.metaware.impl.utils.util.player.Rotation;
import client.metaware.impl.utils.util.player.RotationUtils;
import com.google.common.collect.Multimap;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.*;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class PlayerUtil {
	
	static Minecraft mc = Minecraft.getMinecraft();
    public static int MAX_HURT_RESISTANT_TIME = 20;
    public static final int INCLUDE_ARMOR_BEGIN = 5;
    public static final int EXCLUDE_ARMOR_BEGIN = 9;
    public static final int ONLY_HOT_BAR_BEGIN = 36;
    public static final int END = 45;

	public static boolean isOnSameTeam(EntityLivingBase entity) {
        if (entity.getTeam() != null && mc.thePlayer.getTeam() != null) {
            char c1 = entity.getDisplayName().getFormattedText().charAt(1);
            char c2 = mc.thePlayer.getDisplayName().getFormattedText().charAt(1);
            return c1 == c2;
        } else {
            return false;
        }
	}


    public static ArrayList<EntityLivingBase> getValidEntities() {
        final ArrayList<EntityLivingBase> validEntities = new ArrayList<EntityLivingBase>();
        for (final Object o : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if (o instanceof EntityLivingBase) {
                final EntityLivingBase en = (EntityLivingBase)o;
                if (o instanceof EntityPlayerSP || en.isDead || en.getHealth() <= 0.0f || en.getName().equals(Minecraft.getMinecraft().thePlayer.getName()) || !(o instanceof EntityPlayer)|| (validEntities.contains(o) && Minecraft.getMinecraft().thePlayer.getDistanceToEntity(en) >= Minecraft.getMinecraft().thePlayer.getDistanceToEntity(en))) {
                    continue;
                }
                validEntities.add(en);
            }
        }
        return validEntities;
    }

    public static void attackEntity(final EntityLivingBase entity) {
       final float sharpLevel = EnchantmentHelper.func_152377_a(Minecraft.getMinecraft().thePlayer.getHeldItem(), entity.getCreatureAttribute());
        final boolean vanillaCrit = Minecraft.getMinecraft().thePlayer.fallDistance > 0.0f && !Minecraft.getMinecraft().thePlayer.onGround && !Minecraft.getMinecraft().thePlayer.isOnLadder() && !Minecraft.getMinecraft().thePlayer.isInWater() && !Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.blindness) && Minecraft.getMinecraft().thePlayer.ridingEntity == null;
        PacketUtil.packet(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
        Minecraft.getMinecraft().thePlayer.swingItem();

        if (vanillaCrit) {
            Minecraft.getMinecraft().thePlayer.onCriticalHit(entity);
        }
        if (sharpLevel > 0.0f) {
            Minecraft.getMinecraft().thePlayer.onEnchantmentCritical(entity);
        }
    }

    public static EntityLivingBase getClosestEntity() {
        EntityLivingBase closestEntity = null;
        for (EntityLivingBase o : getLivingEntities(PlayerUtil::isValid)) {
            if (o != null) {
                EntityLivingBase en = (EntityLivingBase) o;
                    attackEntity(en);
                    closestEntity = en;
            }
        }
        return closestEntity;
    }

    public static boolean isValid(EntityLivingBase entLiving) {
        return PlayerUtil.isValid(entLiving, true, false, false, false, false, 40, 40);
    }

    public static void attackEntityAtPos(final EntityLivingBase entity, final double x, final double y, final double z) {
        Minecraft.getMinecraft().thePlayer.swingItem();
        final float sharpLevel = EnchantmentHelper.func_152377_a(Minecraft.getMinecraft().thePlayer.getHeldItem(), entity.getCreatureAttribute());
        final boolean vanillaCrit = Minecraft.getMinecraft().thePlayer.fallDistance > 0.0f && !Minecraft.getMinecraft().thePlayer.onGround && !Minecraft.getMinecraft().thePlayer.isOnLadder() && !Minecraft.getMinecraft().thePlayer.isInWater() && !Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.blindness) && Minecraft.getMinecraft().thePlayer.ridingEntity == null;
        PacketUtil.packet(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
        if (vanillaCrit) {
            Minecraft.getMinecraft().thePlayer.onCriticalHit(entity);
        }
        if (sharpLevel > 0.0f) {
            Minecraft.getMinecraft().thePlayer.onEnchantmentCritical(entity);
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

    public static void windowClick(int windowId, int slotId, int mouseButtonClicked, InventoryUtils.ClickType mode) {
        mc.playerController.windowClick(windowId, slotId, mouseButtonClicked, mode.ordinal(), mc.thePlayer);
    }

    public static void windowClick(int slotId, int mouseButtonClicked, InventoryUtils.ClickType mode) {
        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slotId, mouseButtonClicked, mode.ordinal(), mc.thePlayer);
    }

    public static void openInventory() {
        mc.getNetHandler().addToSendQueueNoEvent(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
    }

    public static void closeInventory() {
        mc.getNetHandler().addToSendQueueNoEvent(new C0DPacketCloseWindow(getLocalPlayer().inventoryContainer.windowId));
    }

    public static List<EntityLivingBase> getLivingEntities(final Predicate<EntityLivingBase> validator) {
        final List<EntityLivingBase> entities = new ArrayList<>();

        for (final Entity entity : mc.theWorld.loadedEntityList) {
            if (entity instanceof EntityLivingBase) {
                final EntityLivingBase e = (EntityLivingBase) entity;
                if (validator.test(e))
                    entities.add(e);
            }
        }

        return entities;
    }

    public static double getTotalArmorProtection(final EntityPlayer player) {
        double totalArmor = 0;
        for (int i = 0; i < 4; i++) {
            final ItemStack armorStack = player.getCurrentArmor(i);

            if (armorStack != null && armorStack.getItem() instanceof ItemArmor) {
                totalArmor += InventoryUtils.getDamageReduction(armorStack);
            }
        }

        return totalArmor;
    }

    public static boolean onServer(final String server) {
        final ServerData serverData = mc.getCurrentServerData();
        return serverData != null && serverData.serverIP.toLowerCase().contains(server);
    }

    public static boolean isHoldingSword(){
        return mc.thePlayer.inventory.getCurrentItem() != null && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof net.minecraft.item.ItemSword;
    }

    public static boolean onHypixel() {
        final ServerData serverData = mc.getCurrentServerData();
        if (serverData == null)
            return false;
        return serverData.serverIP.endsWith("hypixel.net") || serverData.serverIP.endsWith("hypixel.net:25565") || serverData.serverIP.equals("104.17.71.15") || serverData.serverIP.equals("104.17.71.15:25565");
    }

    public static boolean hasInvalidNetInfo(final EntityPlayer player) {
        final NetworkPlayerInfo info = mc.getNetHandler().getPlayerInfo(player.getUniqueID());
        return info == null || info.getResponseTime() != 1;
    }


    public static int getPotionModifier(final EntityLivingBase entity, final Potion potion) {
        final PotionEffect effect = entity.getActivePotionEffect(potion.id);
        if (effect != null) return effect.getAmplifier() + 1;
        return 0;
    }

    public static EntityPlayerSP getLocalPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }
	
	public static boolean isOnServer(final String serverName) {
		return !mc.isSingleplayer() && mc.getCurrentServerData().serverIP.contains(serverName);
	}
	
	public static void tpRel(double x, double y, double z) {
		final EntityPlayerSP player = mc.thePlayer;
		player.setPosition(mc.thePlayer.posX + x, mc.thePlayer.posY + y, mc.thePlayer.posZ + z);
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

    public static boolean isGoodFood(ItemStack stack) {
        ItemFood food = (ItemFood) stack.getItem();
        if (food instanceof ItemAppleGold)
            return true;
        return food.getHealAmount(stack) >= 4 && food.getSaturationModifier(stack) >= 0.3F;
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

    public static boolean isValid(final EntityLivingBase entity, boolean players, boolean monsters, boolean animals, boolean teams, boolean invisibles, float range, float blockRange) {
        if (!entity.isEntityAlive())
            return false;
        if (entity.isInvisible() && !invisibles)
            return false;
        if(entity.getName().contains("NPC") || entity.getName().contains("SHOP") || entity.getName().contains("UPGRADES"))
            return false;
        if (entity == mc.thePlayer.ridingEntity)
            return false;
        if (entity instanceof EntityOtherPlayerMP) {
            final EntityPlayer player = (EntityPlayer) entity;
            if (!players)
                return false;
            if (teams && isOnSameTeam(entity))
                return false;
        } else if (entity instanceof EntityMob) {
            if (!monsters)
                return false;
        } else if (entity instanceof EntityAnimal) {
            if (!animals)
                return false;
        } else {
            // Ignore any other types of entities
            return false;
        }

        final float dist = entity.getDistanceToEntity(mc.thePlayer);

        if (dist < Math.max(blockRange, range)) {
            final Rotation rotations = RotationUtils.getRotationsToEntity(entity);
            return fovCheck(rotations, 180);
        }

        return false;
    }

    public static boolean fovCheck(final Rotation rotations, final int fov) {
        if (fov == 180) return true;
        final float yawChange = MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw - rotations.getRotationYaw());
        final float pitchChange = MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationPitch - rotations.getRotationPitch());
        return Math.abs(yawChange) < fov && Math.abs(pitchChange) < fov;
    }

	public static boolean isValid1(EntityLivingBase entity, boolean players, boolean monsters, boolean animals, boolean teams, boolean invisibles, boolean passives, double range) {
		if (entity instanceof EntityPlayer && !players) {
			return false;
		}
		if (entity instanceof EntityMob && !monsters) {
			return false;
		}
		if (entity instanceof EntityVillager || entity instanceof EntityGolem && !passives) {
			return false;
		}
		if (entity instanceof EntityAnimal && !animals) {
			return false;
		}
		if (entity == mc.thePlayer || entity.isDead || entity.getHealth() == 0 || mc.thePlayer.getDistanceToEntity(entity) > range) {
			return false;
		}
		if (entity.isInvisible() && !invisibles) {
			return false;
		}
		if (isOnSameTeam(entity) && teams) {
			return false;
		}
		if (entity instanceof EntityBat) {
			return false;
		}
		return !(entity instanceof EntityArmorStand);
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

    public static boolean isOnLiquid() {
        if (mc.thePlayer == null) return false;
        boolean onLiquid = false;
        final int y = (int) mc.thePlayer.getEntityBoundingBox().offset(0.0D, -0.01D, 0.0D).minY;
        for (int x = MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minX); x < MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().maxX) + 1; x++) {
            for (int z = MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minZ); z < MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().maxZ) + 1; z++) {
                final Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                if (block != null && block.getMaterial() != Material.air) {
                    if (!(block instanceof BlockLiquid)) return false;
                    onLiquid = true;
                }
            }
        }
        return onLiquid;
    }
    public static boolean isOnLiquid(double profondeur)
	  {
	    boolean onLiquid = false;
	    
	    if(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - profondeur, mc.thePlayer.posZ)).getBlock().getMaterial().isLiquid()) {
	      onLiquid = true;
	    }
	    return onLiquid;
	  }
    public static boolean isTotalOnLiquid(double profondeur)
	  {	    
	    for(double x = mc.thePlayer.boundingBox.minX; x < mc.thePlayer.boundingBox.maxX; x +=0.01f){
	    	
			for(double z = mc.thePlayer.boundingBox.minZ; z < mc.thePlayer.boundingBox.maxZ; z +=0.01f){
				Block block = mc.theWorld.getBlockState(new BlockPos(x, mc.thePlayer.posY - profondeur,z)).getBlock();
    			if(!(block instanceof BlockLiquid) && !(block instanceof BlockAir)){
    				return false;
    			}
    		}
		}
	    return true;
	  }

    public static boolean isInLiquid() {
        if (mc.thePlayer.isInWater()) {
            return true;
        }
        boolean inLiquid = false;
        final int y = (int) mc.thePlayer.getEntityBoundingBox().minY;
        for (int x = MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minX); x < MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().maxX) + 1; x++) {
            for (int z = MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minZ); z < MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().maxZ) + 1; z++) {
                final Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                if (block != null && block.getMaterial() != Material.air) {
                    if (!(block instanceof BlockLiquid)) return false;
                    inLiquid = true;
                }
            }
        }
        return inLiquid;
    }

    public static boolean isBlockUnder() {
        if(Minecraft.getMinecraft().thePlayer.posY < 0)
            return false;
        for(int off = 0; off < (int)Minecraft.getMinecraft().thePlayer.posY+2; off += 2){
            AxisAlignedBB bb = Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().offset(0, -off, 0);
            if(!Minecraft.getMinecraft().theWorld.getCollidingBoundingBoxes(Minecraft.getMinecraft().thePlayer, bb).isEmpty()){
                return true;
            }
        }
        return false;
    }
}
