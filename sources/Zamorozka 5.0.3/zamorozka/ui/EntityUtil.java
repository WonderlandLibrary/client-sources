package zamorozka.ui;

import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import zamorozka.modules.COMBAT.KillAura;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EntityUtil implements MCUtil {

	private static final Map<String, Long> serverIpPingCache = new HashMap<>();

	static EntityLivingBase target = KillAura.target;

	public static boolean isPassive(Entity e) {
		if (e instanceof EntityWolf && ((EntityWolf) e).isAngry())
			return false;
		return e instanceof EntityAnimal || e instanceof EntityAgeable || e instanceof EntityTameable || e instanceof EntityAmbientCreature || e instanceof EntitySquid;
	}

	public static long getPingToServer(String ip) {
		return serverIpPingCache.get(ip);
	}

	public static EntityLivingBase searchEntityByName(final String name) {
		EntityLivingBase newEntity = null;
		Minecraft.getMinecraft();
		for (final Object o : mc.world.loadedEntityList) {
			final EntityLivingBase en2 = (EntityLivingBase) o;
			if (!(o instanceof EntityPlayerSP) && !en2.isDead) {
				Minecraft.getMinecraft();
				if (mc.player.canEntityBeSeen(en2) || newEntity != null || !en2.getName().equals(name)) {
					continue;
				}
				newEntity = en2;
			}
		}
		return newEntity;
	}

	public boolean isNPC(EntityPlayer player) {
		try {
			NetworkPlayerInfo p = mc.player.connection.getPlayerInfo(player.getUniqueID());
			if (p.getGameType().isSurvivalOrAdventure() || p.getGameType().isCreative()) {
				return false;
			}
		} catch (Exception e) {
			return true;
		}
		return true;
	}

	public static TileEntityChest getClosestChest() {
		TileEntityChest entity = null;
		double maxDist = 4;
		for (TileEntity tileEntity : mc.world.loadedTileEntityList) {
			if (tileEntity instanceof TileEntityChest && mc.player.getDistanceSq(tileEntity.getPos()) < maxDist) {
				entity = (TileEntityChest) tileEntity;
				maxDist = mc.player.getDistanceSq(entity.getPos());
			}
		}
		return entity;
	}

	public static boolean isOnTab(Entity entity) {
		for (NetworkPlayerInfo info : mc.getConnection().getPlayerInfoMap()) {
			if (info.getGameProfile().getName().equals(entity.getName()))
				return true;
		}
		return false;
	}

	public static float getPing(EntityLivingBase target) {
		if (Minecraft.getConnection() == null || target == null)
			return 0;

		NetworkPlayerInfo info = Minecraft.getConnection().getPlayerInfo(target.getUniqueID());
		if (info == null)
			return 0;

		return EntityUtil.clamp(info.getResponseTime(), 1, 100000);
	}

	public static void block() {
		if (!mc.player.isBlocking() && mc.player.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof ItemShield) {
			mc.playerController.processRightClick(mc.player, mc.world, EnumHand.OFF_HAND);
		}
	}

	public static void unblock() {
		if (mc.player.isBlocking() && mc.player.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof ItemShield) {
			sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(-.8, -.8, -.8), EnumFacing.DOWN));
		}
	}

	public static boolean hasNoArmorPlayer() {
		if (((EntityPlayer) target).getEquipmentInSlot(3) != null && ((EntityPlayer) target).getEquipmentInSlot(2) != null && ((EntityPlayer) target).getEquipmentInSlot(1) != null && ((EntityPlayer) target).getEquipmentInSlot(0) != null) {
			return true;
		}
		return false;
	}

	public static boolean hasNoMixArmor(Entity e) {
		ItemStack head = ((EntityPlayer) e).getItemStackFromSlot(EntityEquipmentSlot.HEAD);
		ItemStack chest = ((EntityPlayer) e).getItemStackFromSlot(EntityEquipmentSlot.CHEST);
		ItemStack legs = ((EntityPlayer) e).getItemStackFromSlot(EntityEquipmentSlot.LEGS);
		ItemStack feet = ((EntityPlayer) e).getItemStackFromSlot(EntityEquipmentSlot.FEET);
		if (head.getItem() == Items.DIAMOND_HELMET && chest.getItem() == Items.DIAMOND_CHESTPLATE && legs.getItem() == Items.DIAMOND_LEGGINGS && feet.getItem() == Items.DIAMOND_BOOTS) {
			return false;
		}
		return true;
	}

	public static String getEnchantShortName(Enchantment enchantment) {
		if (enchantment == Enchantments.FIRE_PROTECTION)
			return "F Prot";
		if (enchantment == Enchantments.FEATHER_FALLING)
			return "Fea Fa";
		if (enchantment == Enchantments.BLAST_PROTECTION)
			return "B Prot";
		if (enchantment == Enchantments.PROJECTILE_PROTECTION)
			return "P Prot";
		if (enchantment == Enchantments.AQUA_AFFINITY)
			return "Aqua A";
		if (enchantment == Enchantments.THORNS)
			return "Thorns";
		if (enchantment == Enchantments.DEPTH_STRIDER)
			return "Depth S";
		if (enchantment == Enchantments.FROST_WALKER)
			return "Frost W";
		if (enchantment == Enchantments.field_190941_k)
			return "Curse B";
		if (enchantment == Enchantments.SMITE)
			return "Smite";
		if (enchantment == Enchantments.BANE_OF_ARTHROPODS)
			return "Bane A";
		if (enchantment == Enchantments.FIRE_ASPECT)
			return "Fire A";
		if (enchantment == Enchantments.SILK_TOUCH)
			return "Silk T";
		if (enchantment == Enchantments.POWER)
			return "Power";
		if (enchantment == Enchantments.PUNCH)
			return "Punch";
		if (enchantment == Enchantments.FLAME)
			return "Flame";
		if (enchantment == Enchantments.LUCK_OF_THE_SEA)
			return "Luck S";
		if (enchantment == Enchantments.field_190940_C)
			return "Curse V";

		return enchantment.getName().toString().substring(0, 4);
	}

	public static int getArmorItemsEquipSlot(ItemStack stack, boolean equipmentSlot) {
		if (stack.getUnlocalizedName().contains("helmet"))
			return equipmentSlot ? 4 : 5;
		if (stack.getUnlocalizedName().contains("chestplate"))
			return equipmentSlot ? 3 : 6;
		if (stack.getUnlocalizedName().contains("leggings"))
			return equipmentSlot ? 2 : 7;
		if (stack.getUnlocalizedName().contains("boots"))
			return equipmentSlot ? 1 : 8;
		return -1;
	}

	public static void attack(final Entity target) {
		sendPacket(new CPacketUseEntity(target));
		Minecraft.player.swingArm(EnumHand.MAIN_HAND);
		Minecraft.player.resetCooldown();
	}

	public static void attackEntity(Entity entity, boolean packet, boolean swingArm) {
		if (packet) {
			EntityUtil.mc.player.connection.sendPacket((Packet) new CPacketUseEntity(entity));
		} else {
			EntityUtil.mc.playerController.attackEntity((EntityPlayer) EntityUtil.mc.player, entity);
		}
		if (swingArm) {
			EntityUtil.mc.player.swingArm(EnumHand.MAIN_HAND);
		}
	}

	public static void doCritical() {

		Minecraft.getMinecraft();
		if (Minecraft.player.isInWater()) {

			Minecraft.getMinecraft();
			if (Minecraft.player.isInsideOfMaterial(Material.LAVA)) {

				Minecraft.getMinecraft();
				if (Minecraft.player.onGround) {

					Minecraft.getMinecraft();
					Minecraft.player.motionY = 0.10000000149011612;

					Minecraft.getMinecraft();
					Minecraft.player.fallDistance = 0.1f;

					Minecraft.getMinecraft();
					Minecraft.player.onGround = false;
				}
			}
		}
	}

	public static boolean isTeam2(final EntityPlayer e, final EntityLivingBase e2) {
		final String localPlayerTeamCode = String.valueOf(e.getDisplayName().getFormattedText().charAt(1)).toLowerCase();
		final String otherPlayerTeamCode = String.valueOf(e2.getDisplayName().getFormattedText().charAt(1)).toUpperCase();
		return localPlayerTeamCode == otherPlayerTeamCode;

	}

	public static Vec3d getEntityWorldPosition(Entity entity) {
		double partial = mc.timer.elapsedPartialTicks;

		double x = entity.lastTickPosX + ((entity.posX - entity.lastTickPosX) * partial);
		double y = entity.lastTickPosY + ((entity.posY - entity.lastTickPosY) * partial);
		double z = entity.lastTickPosZ + ((entity.posZ - entity.lastTickPosZ) * partial);

		return new Vec3d(x, y, z);
	}

	public static Vec3d getEntityRenderPosition(Entity entity) {
		double partial = mc.timer.renderPartialTicks;

		double x = entity.lastTickPosX + ((entity.posX - entity.lastTickPosX) * partial) - mc.getRenderManager().viewerPosX;
		double y = entity.lastTickPosY + ((entity.posY - entity.lastTickPosY) * partial) - mc.getRenderManager().viewerPosY;
		double z = entity.lastTickPosZ + ((entity.posZ - entity.lastTickPosZ) * partial) - mc.getRenderManager().viewerPosZ;

		return new Vec3d(x, y, z);
	}

	public static double getRotationDifference(double[] rotation) {
		return Math.sqrt(Math.pow(Math.abs(angleDifference(Minecraft.player.rotationYaw % 360, rotation[0])), 2) + Math.pow(Math.abs(angleDifference(Minecraft.player.rotationPitch, rotation[1])), 2));
	}

	private static double angleDifference(final double a, final double b) {
		return ((a - b) % 360.0 + 540.0) % 360.0 - 180.0;
	}

	public static boolean isAnimal(Entity entity) {
		return entity instanceof EntityAnimal;
	}

	public static boolean isMonster(Entity entity) {
		return entity instanceof IMob || entity instanceof EntityDragon || entity instanceof EntityGolem;
	}

	public static boolean isNeutral(Entity entity) {
		return entity instanceof EntityBat || entity instanceof EntitySquid || entity instanceof EntityVillager;
	}

	public static boolean isProjectile(Entity entity) {
		return entity instanceof IProjectile || entity instanceof EntityFishHook;
	}

	public static Vec3d getTileEntityRenderPosition(TileEntity entity) {
		double x = entity.getPos().getX() - mc.getRenderManager().viewerPosX;
		double y = entity.getPos().getY() - mc.getRenderManager().viewerPosY;
		double z = entity.getPos().getZ() - mc.getRenderManager().viewerPosZ;

		return new Vec3d(x, y, z);
	}

	private static String isTeam(EntityPlayer player) {
		final Matcher m = Pattern.compile("Â§(.).*Â§r").matcher(player.getDisplayName().getFormattedText());
		if (m.find()) {
			return m.group(1);
		}
		return "f";
	}

	public static boolean isTeam(final EntityPlayer e, final EntityPlayer e2) {
		if (e2.getTeam() != null && e.getTeam() != null) {
			Character target = e2.getDisplayName().getFormattedText().charAt(1);
			Character player = e.getDisplayName().getFormattedText().charAt(1);
			return target.equals(player);
		} else {
			return true;
		}
	}

	public static void getCurrentPots() {
		EnumHand hand = EnumHand.MAIN_HAND;
		if (Minecraft.player.getHeldItemOffhand().getItem() == Items.SPLASH_POTION) {
			hand = EnumHand.OFF_HAND;
		} else if (Minecraft.player.getHeldItemMainhand().getItem() != Items.SPLASH_POTION) {
			for (int i = 0; i < 9; i++) {
				if (Minecraft.player.inventory.getStackInSlot(i).getItem() == Items.SPLASH_POTION) {
					Minecraft.player.inventory.currentItem = i;
				}
			}
		}
	}

	public static boolean isPlayOnServer(String s) {
		if (mc.isSingleplayer()) {
			return false;
		} else {
			return mc.getCurrentServerData().serverIP.contains(s);
		}
	}

	public static boolean isBlockUnder() {
		for (int i = (int) (Minecraft.player.posY - 1.0D); i > 0; --i) {
			BlockPos pos = new BlockPos(Minecraft.player.posX, i, Minecraft.player.posZ);
			if (!(mc.world.getBlockState(pos).getBlock() instanceof BlockAir)) {
				return true;
			}
		}

		return false;
	}

	public static void getCurrentSoups() {
		EnumHand hand = EnumHand.MAIN_HAND;
		if (Minecraft.player.getHeldItemOffhand().getItem() == Items.MUSHROOM_STEW) {
			hand = EnumHand.OFF_HAND;
		} else if (Minecraft.player.getHeldItemMainhand().getItem() != Items.MUSHROOM_STEW) {
			for (int i = 0; i < 9; i++) {
				if (Minecraft.player.inventory.getStackInSlot(i).getItem() == Items.MUSHROOM_STEW) {
					Minecraft.player.inventory.currentItem = i;
				}
			}
		}
	}

	protected static void sendPacket(final Packet packet) {
		Minecraft.getConnection().sendPacket(packet);
	}

	public static boolean isLowHealth(EntityLivingBase entity, EntityLivingBase entityPriority) {
		return entityPriority == null || entity.getHealth() < entityPriority.getHealth();
	}

	public static boolean isClosest(EntityLivingBase entity, EntityLivingBase entityPriority) {
		return entityPriority == null || Minecraft.player.getDistanceToEntity(entity) < Minecraft.player.getDistanceToEntity(entityPriority);
	}

	public static boolean isLiving(Entity e) {
		return e instanceof EntityLivingBase;
	}

	public static boolean isFakeLocalPlayer(Entity entity) {
		return entity != null && entity.getEntityId() == -100 && Wrapper.getPlayer() != entity;
	}

	public static int getAxeAtHotbar() {
		for (int i = 0; i < 9; ++i) {
			final ItemStack itemStack = Minecraft.player.inventory.getStackInSlot(i);
			if (itemStack.getItem() instanceof ItemAxe) {
				return i;
			}
		}
		return 1;
	}

	public static int getSwordAtHotbar() {
		for (int i = 0; i < 9; ++i) {
			final ItemStack itemStack = Minecraft.player.inventory.getStackInSlot(i);
			if (itemStack.getItem() instanceof ItemSword) {
				return i;
			}
		}
		return 0;
	}

	public static int getPotAtHotbar() {
		for (int i = 0; i < 9; ++i) {
			final ItemStack itemStack = Minecraft.player.inventory.getStackInSlot(i);
			if (itemStack.getItem() == Items.MUSHROOM_STEW) {
				return i;
			}
		}
		return 1;
	}

	/**
	 * Find the entities interpolated amount
	 */
	public static Vec3d getInterpolatedAmount(Entity entity, double x, double y, double z) {
		return new Vec3d((entity.posX - entity.lastTickPosX) * x, (entity.posY - entity.lastTickPosY) * y, (entity.posZ - entity.lastTickPosZ) * z);
	}

	public static Vec3d getInterpolatedAmount(Entity entity, Vec3d vec) {
		return getInterpolatedAmount(entity, vec.xCoord, vec.yCoord, vec.zCoord);
	}

	public static Vec3d getInterpolatedAmount(Entity entity, double ticks) {
		return getInterpolatedAmount(entity, ticks, ticks, ticks);
	}

	/**
	 * If the mob by default wont attack the player, but will if the player attacks
	 * it
	 */
	public static boolean isNeutralMob(Entity entity) {
		return entity instanceof EntityPigZombie || entity instanceof EntityWolf || entity instanceof EntityEnderman;
	}

	/**
	 * Find the entities interpolated position
	 */
	public static Vec3d getInterpolatedPos(Entity entity, float ticks) {
		return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(getInterpolatedAmount(entity, ticks));
	}

	public static boolean isAlive(Entity entity) {
		return isLiving(entity) && !entity.isDead && ((EntityLivingBase) (entity)).getHealth() > 0;
	}

	public static boolean isDead(Entity entity) {
		return !isAlive(entity);
	}

	public static Vec3d getInterpolatedRenderPos(Entity entity, float ticks) {
		return getInterpolatedPos(entity, ticks).subtract(RenderManager.renderPosX, RenderManager.renderPosY, RenderManager.renderPosZ);
	}

	public static float getHealth(Entity entity) {
		if (isLiving(entity)) {
			EntityLivingBase livingBase = (EntityLivingBase) entity;
			return livingBase.getHealth() + livingBase.getAbsorptionAmount();
		}
		return 0.0f;
	}

	public static boolean isInWater(Entity entity) {
		if (entity == null)
			return false;

		double y = entity.posY + 0.01;

		for (int x = MathHelper.floor(entity.posX); x < MathHelper.ceil(entity.posX); x++)
			for (int z = MathHelper.floor(entity.posZ); z < MathHelper.ceil(entity.posZ); z++) {
				BlockPos pos = new BlockPos(x, (int) y, z);

				if (Wrapper.getWorld().getBlockState(pos).getBlock() instanceof BlockLiquid)
					return true;
			}

		return false;
	}

	public static boolean isDrivenByPlayer(Entity entityIn) {
		return Wrapper.getPlayer() != null && entityIn != null && entityIn.equals(Wrapper.getPlayer().getRidingEntity());
	}

	public static boolean isAboveWater(Entity entity) {
		return isAboveWater(entity, false);
	}

	public static boolean isAboveWater(Entity entity, boolean packet) {
		if (entity == null)
			return false;

		double y = entity.posY - (packet ? 0.03 : (EntityUtil.isPlayer(entity) ? 0.2 : 0.5)); // increasing this seems
																								// to flag more in NCP
																								// but needs to be
																								// increased so the
																								// player lands on solid
																								// water

		for (int x = MathHelper.floor(entity.posX); x < MathHelper.ceil(entity.posX); x++)
			for (int z = MathHelper.floor(entity.posZ); z < MathHelper.ceil(entity.posZ); z++) {
				BlockPos pos = new BlockPos(x, MathHelper.floor(y), z);

				if (Wrapper.getWorld().getBlockState(pos).getBlock() instanceof BlockLiquid)
					return true;
			}

		return false;
	}

	public static boolean canEntityFeetBeSeen(Entity entityIn) {
		return mc.world.rayTraceBlocks(new Vec3d(Minecraft.player.posX, Minecraft.player.posX + Minecraft.player.getEyeHeight(), Minecraft.player.posZ), new Vec3d(entityIn.posX, entityIn.posY, entityIn.posZ), false, true, false) == null;
	}

	public static float[] calculateLookAt(double px, double py, double pz, EntityPlayer me) {
		double dirx = me.posX - px;
		double diry = me.posY - py;
		double dirz = me.posZ - pz;

		double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);

		dirx /= len;
		diry /= len;
		dirz /= len;

		double pitch = Math.asin(diry);
		double yaw = Math.atan2(dirz, dirx);

		// to degree
		pitch = pitch * 180.0d / Math.PI;
		yaw = yaw * 180.0d / Math.PI;

		yaw += 90f;

		return new float[] { (float) yaw, (float) pitch };
	}

	public static boolean isPlayer(Entity entity) {
		return entity instanceof EntityPlayer;
	}

	public static double getRelativeX(float yaw) {
		return MathHelper.sin(-yaw * 0.017453292F);
	}

	public static double getRelativeZ(float yaw) {
		return MathHelper.cos(yaw * 0.017453292F);
	}

	public static double GetDistance(double p_X, double p_Y, double p_Z, double x, double y, double z) {
		double d0 = p_X - x;
		double d1 = p_Y - y;
		double d2 = p_Z - z;
		return MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
	}

	public static double GetDistanceOfEntityToBlock(Entity p_Entity, BlockPos p_Pos) {
		return GetDistance(p_Entity.posX, p_Entity.posY, p_Entity.posZ, p_Pos.getX(), p_Pos.getY(), p_Pos.getZ());
	}

	public static float clamp(double val, double min, double max) {
		if (val <= min) {
			val = min;
		}
		if (val >= max) {
			val = max;
		}
		return (float) val;
	}

	public static boolean isPassiveMob(final Entity e) {
		return e instanceof EntityAnimal || e instanceof EntityAgeable || e instanceof EntityTameable || e instanceof EntityAmbientCreature || e instanceof EntitySquid;
	}

	public static int GetRecursiveItemSlot(final Item input) {
		if (Minecraft.player == null) {
			return 0;
		}
		for (int i = Minecraft.player.inventoryContainer.getInventory().size() - 1; i > 0; --i) {
			if (i != 0 && i != 5 && i != 6 && i != 7) {
				if (i != 8) {
					final ItemStack s = Minecraft.player.inventoryContainer.getInventory().get(i);
					if (!s.func_190926_b()) {
						if (s.getItem() == input) {
							return i;
						}
					}
				}
			}
		}
		return -1;
	}

	public static int GetItemSlotNotHotbar(final Item input) {
		if (Minecraft.player == null) {
			return 0;
		}
		for (int i = 9; i < 36; ++i) {
			final Item item = Minecraft.player.inventory.getStackInSlot(i).getItem();
			if (item == input) {
				return i;
			}
		}
		return -1;
	}

	public static float GetHealthWithAbsorption() {
		return Minecraft.player.getHealth() + Minecraft.player.getAbsorptionAmount();
	}

	public static int GetItemSlot(final Item input) {
		if (Minecraft.player == null) {
			return 0;
		}
		for (int i = 0; i < Minecraft.player.inventoryContainer.getInventory().size(); ++i) {
			if (i != 0 && i != 5 && i != 6 && i != 7) {
				if (i != 8) {
					final ItemStack s = Minecraft.player.inventoryContainer.getInventory().get(i);
					if (!s.func_190926_b()) {
						if (s.getItem() == input) {
							return i;
						}
					}
				}
			}
		}
		return -1;
	}

	public static boolean isPlayerMovingKeybind() {
		return EntityUtil.mc.gameSettings.keyBindForward.isKeyDown() || EntityUtil.mc.gameSettings.keyBindBack.isKeyDown() || EntityUtil.mc.gameSettings.keyBindLeft.isKeyDown() || EntityUtil.mc.gameSettings.keyBindRight.isKeyDown();
	}

	public static boolean isPlayerMovingMomentum() {
		return Minecraft.player.moveForward > 0.0f || Minecraft.player.moveStrafing > 0.0f || Minecraft.player.moveForward < 0.0f || Minecraft.player.moveStrafing < 0.0f;
	}

	public static boolean isPlayerMovingLegit() {
		return EntityUtil.mc.gameSettings.keyBindForward.isKeyDown();
	}

	public static void getPlayerSpeed() {
		final DecimalFormat df = new DecimalFormat("#.#");
		final double deltaX = Minecraft.player.posX - Minecraft.player.prevPosX;
		final double deltaY = Minecraft.player.posY - Minecraft.player.prevPosY;
	}

	public static int getPing() {
		if (EntityUtil.mc.world == null || Minecraft.player == null || Minecraft.player.getUniqueID() == null) {
			return 0;
		}
		final NetworkPlayerInfo debugInfo = Minecraft.getConnection().getPlayerInfo(Minecraft.player.getUniqueID());
		if (debugInfo == null) {
			return 0;
		}
		return debugInfo.getResponseTime();
	}

	/**
	 * 
	 * 
	 * @return index 0 = yaw | index 1 = pitch
	 */
	public static float[] getFacePosRemote(Vec3d src, Vec3d dest) {
		double diffX = dest.xCoord - src.xCoord;
		double diffY = dest.yCoord - (src.yCoord);
		double diffZ = dest.zCoord - src.zCoord;
		double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
		float yaw = (MathHelper.wrapAngleTo180_float((float) (Math.atan2(diffZ, diffX) * 180.0F / Math.PI)) - 90.0F);
		float pitch = (MathHelper.wrapAngleTo180_float((float) -(Math.atan2(diffY, dist) * 180.0F / Math.PI)));
		return new float[] { RotationUtils.fixRotation(mc.player.rotationYaw, yaw), RotationUtils.fixRotation(mc.player.rotationPitch, pitch) };
	}

	/**
	 * 
	 * @param vec
	 * @return index 0 = yaw | index 1 = pitch
	 */
	public static float[] getFacePosEntityRemote(EntityLivingBase facing, Entity en) {
		if (en == null) {
			return new float[] { facing.rotationYaw, facing.rotationPitch };
		}
		Vec3d vec = new Vec3d(facing.posX, facing.posY + en.getEyeHeight(), facing.posZ);
		Vec3d vec1 = new Vec3d(en.posX, en.posY + en.getEyeHeight(), en.posZ);
		return getFacePosRemote(new Vec3d(facing.posX, facing.posY + en.getEyeHeight(), facing.posZ), new Vec3d(en.posX, en.posY + en.getEyeHeight(), en.posZ));
	}

	public static EntityLivingBase getClosestEntityToEntity(float range, Entity ent) {
		EntityLivingBase closestEntity = null;
		float mindistance = range;
		for (Object o : Minecraft.getMinecraft().world.loadedEntityList) {
			if (isNotItem(o) && !ent.isEntityEqual((EntityLivingBase) o)) {
				EntityLivingBase en = (EntityLivingBase) o;
				if (ent.getDistanceToEntity(en) < mindistance) {
					mindistance = ent.getDistanceToEntity(en);
					closestEntity = en;
				}
			}
		}
		return closestEntity;
	}

	public static boolean isNotItem(Object o) {
		if (!(o instanceof EntityLivingBase)) {
			return false;
		}
		return true;
	}

}