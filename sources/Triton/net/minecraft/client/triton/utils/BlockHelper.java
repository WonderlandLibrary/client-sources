package net.minecraft.client.triton.utils;

import java.util.Iterator;
import java.util.Map;

import javax.xml.stream.Location;

import com.google.common.collect.Multimap;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

public final class BlockHelper {

	private static float smoothYaw;
	private static float smoothPitch;

	public static void bestTool(int x, int y, int z) {
		int blockId = Block
				.getIdFromBlock(Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)).getBlock());
		int bestSlot = 0;
		float f = -1.0F;
		for (int i1 = 36; i1 < 45; i1++) {
			try {
				ItemStack curSlot = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i1).getStack();
				if ((((curSlot.getItem() instanceof ItemTool)) || ((curSlot.getItem() instanceof ItemSword))
						|| ((curSlot.getItem() instanceof ItemShears)))
						&& (curSlot.getStrVsBlock(Block.getBlockById(blockId)) > f)) {
					bestSlot = i1 - 36;
					f = curSlot.getStrVsBlock(Block.getBlockById(blockId));
				}
			} catch (Exception localException) {
			}
		}
		if (f != -1.0F) {
			ClientUtils.player().inventory.currentItem = bestSlot;
			ClientUtils.mc().playerController.updateController();
		}
	}

	public static Block getBlockAtPos(BlockPos inBlockPos) {
		BlockPos currentPos = inBlockPos;
		IBlockState s = ClientUtils.world().getBlockState(currentPos);
		return s.getBlock();
	}

	public static float[] getBlockRotations(double x, double y, double z) {
		double var4 = x - ClientUtils.player().posX + 0.5D;
		double var6 = z - ClientUtils.player().posZ + 0.5D;

		double var8 = y - (ClientUtils.player().posY + ClientUtils.player().getEyeHeight() - 1.0D);
		double var14 = MathHelper.sqrt_double(var4 * var4 + var6 * var6);
		float var12 = (float) (Math.atan2(var6, var4) * 180.0D / 3.141592653589793D) - 90.0F;

		return new float[] { var12, (float) -(Math.atan2(var8, var14) * 180.0D / 3.141592653589793D) };
	}

	public static Block getBlock(Entity entity, double offset) {
		if (entity == null) {
			return null;
		}
		int y = (int) entity.getEntityBoundingBox().offset(0.0D, offset, 0.0D).minY;
		for (int x = MathHelper.floor_double(entity.getEntityBoundingBox().minX); x < MathHelper
				.floor_double(entity.getEntityBoundingBox().maxX) + 1; x++) {
			int z = MathHelper.floor_double(entity.getEntityBoundingBox().minZ);
			if (z < MathHelper.floor_double(entity.getEntityBoundingBox().maxZ) + 1) {
				return ClientUtils.world().getBlockState(new BlockPos(x, y, z)).getBlock();
			}
		}
		return null;
	}

	public static boolean isInsideBlock(EntityPlayer player) {
		for (int x = MathHelper.floor_double(player.boundingBox.minX); x < MathHelper
				.floor_double(player.boundingBox.maxX) + 1; x++) {
			for (int y = MathHelper.floor_double(player.boundingBox.minY); y < MathHelper
					.floor_double(player.boundingBox.maxY) + 1; y++) {
				for (int z = MathHelper.floor_double(player.boundingBox.minZ); z < MathHelper
						.floor_double(player.boundingBox.maxZ) + 1; z++) {
					Block block = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
					if ((block != null) && (!(block instanceof BlockAir))) {
						AxisAlignedBB boundingBox = block.getCollisionBoundingBox(ClientUtils.world(),
								new BlockPos(x, y, z), ClientUtils.world().getBlockState(new BlockPos(x, y, z)));
						if ((boundingBox != null) && (player.boundingBox.intersectsWith(boundingBox))) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public static boolean isInLiquid() {
		boolean inLiquid = false;
		if ((ClientUtils.player() == null) || (ClientUtils.player().boundingBox == null)) {
			return false;
		}
		int y = (int) ClientUtils.player().boundingBox.minY;
		for (int x = MathHelper.floor_double(ClientUtils.player().boundingBox.minX); x < MathHelper
				.floor_double(ClientUtils.player().boundingBox.maxX) + 1; x++) {
			for (int z = MathHelper.floor_double(ClientUtils.player().boundingBox.minZ); z < MathHelper
					.floor_double(ClientUtils.player().boundingBox.maxZ) + 1; z++) {
				Block block = ClientUtils.world().getBlockState(new BlockPos(x, y, z)).getBlock();
				if ((block != null) && (!(block instanceof BlockAir))) {
					if (!(block instanceof BlockLiquid)) {
						return false;
					}
					inLiquid = true;
				}
			}
		}
		return inLiquid;
	}

	public static boolean isInLiquidNew() {
		boolean inLiquid = false;
		int y = (int) ClientUtils.player().boundingBox.minY;
		for (int x = MathHelper.floor_double(ClientUtils.player().boundingBox.minX); x < MathHelper
				.floor_double(ClientUtils.player().boundingBox.maxX) + 1; x++) {
			for (int z = MathHelper.floor_double(ClientUtils.player().boundingBox.minZ); z < MathHelper
					.floor_double(ClientUtils.player().boundingBox.maxZ) + 1; z++) {
				Block block = ClientUtils.world().getBlockState(new BlockPos(x, y, z)).getBlock();
				if ((block != null) && (!(block instanceof BlockAir))) {
					if (!(block instanceof BlockLiquid)) {
						return false;
					}
					inLiquid = true;
				}
			}
		}
		return inLiquid;
	}

	public static boolean isOnIce() {
		boolean onIce = false;
		int y = (int) (ClientUtils.player().boundingBox.minY - 1.0D);
		for (int x = MathHelper.floor_double(ClientUtils.player().boundingBox.minX); x < MathHelper
				.floor_double(ClientUtils.player().boundingBox.maxX) + 1; x++) {
			for (int z = MathHelper.floor_double(ClientUtils.player().boundingBox.minZ); z < MathHelper
					.floor_double(ClientUtils.player().boundingBox.maxZ) + 1; z++) {
				Block block = ClientUtils.world().getBlockState(new BlockPos(x, y, z)).getBlock();
				if ((block != null) && (!(block instanceof BlockAir))
						&& (((block instanceof BlockPackedIce)) || ((block instanceof BlockIce)))) {
					onIce = true;
				}
			}
		}
		return onIce;
	}

	public static boolean isOnLadder() {
		boolean onLadder = false;
		int y = (int) (ClientUtils.player().boundingBox.minY - 1.0D);
		for (int x = MathHelper.floor_double(ClientUtils.player().boundingBox.minX); x < MathHelper
				.floor_double(ClientUtils.player().boundingBox.maxX) + 1; x++) {
			for (int z = MathHelper.floor_double(ClientUtils.player().boundingBox.minZ); z < MathHelper
					.floor_double(ClientUtils.player().boundingBox.maxZ) + 1; z++) {
				Block block = ClientUtils.world().getBlockState(new BlockPos(x, y, z)).getBlock();
				if ((block != null) && (!(block instanceof BlockAir))) {
					if ((!(block instanceof BlockLadder)) && (!(block instanceof BlockLadder))) {
						return false;
					}
					onLadder = true;
				}
			}
		}
		return (onLadder) || (ClientUtils.player().isOnLadder());
	}

	public static boolean isOnLiquid() {
		boolean onLiquid = false;
		int y = (int) (ClientUtils.player().boundingBox.minY - 0.01D);
		for (int x = MathHelper.floor_double(ClientUtils.player().boundingBox.minX); x < MathHelper
				.floor_double(ClientUtils.player().boundingBox.maxX) + 1; x++) {
			for (int z = MathHelper.floor_double(ClientUtils.player().boundingBox.minZ); z < MathHelper
					.floor_double(ClientUtils.player().boundingBox.maxZ) + 1; z++) {
				Block block = ClientUtils.world().getBlockState(new BlockPos(x, y, z)).getBlock();
				if ((block != null) && (!(block instanceof BlockAir))) {
					if (!(block instanceof BlockLiquid)) {
						return false;
					}
					onLiquid = true;
				}
			}
		}
		return onLiquid;
	}

	public static EnumFacing getFacing(BlockPos pos) {
		EnumFacing[] orderedValues = { EnumFacing.UP, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH,
				EnumFacing.WEST, EnumFacing.DOWN };
		EnumFacing[] arrayOfEnumFacing1;
		int j = (arrayOfEnumFacing1 = orderedValues).length;
		for (int i = 0; i < j; i++) {
			EnumFacing facing = arrayOfEnumFacing1[i];
			Entity temp = new EntitySnowball(ClientUtils.world());
			temp.posX = (pos.getX() + 0.5D);
			temp.posY = (pos.getY() + 0.5D);
			temp.posZ = (pos.getZ() + 0.5D);
			temp.posX += facing.getDirectionVec().getX() * 0.5D;
			temp.posY += facing.getDirectionVec().getY() * 0.5D;
			temp.posZ += facing.getDirectionVec().getZ() * 0.5D;
			if (ClientUtils.player().canEntityBeSeen(temp)) {
				return facing;
			}
		}
		return null;
	}

	public static float[] getRotationFromPosition(double x, double z, double y) {
		double xDiff = x - Minecraft.getMinecraft().thePlayer.posX;
		double zDiff = z - Minecraft.getMinecraft().thePlayer.posZ;
		double yDiff = y - Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight();
		double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
		float yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D) - 90.0F;
		float pitch = (float) -(Math.atan2(yDiff, dist) * 180.0D / 3.141592653589793D);
		return new float[] { yaw, pitch };
	}

	public static int getBestWeapon(Entity target) {
		int originalSlot = ClientUtils.player().inventory.currentItem;
		int weaponSlot = -1;
		float weaponDamage = 1.0F;
		for (byte slot = 0; slot < 9; slot = (byte) (slot + 1)) {
			ClientUtils.player().inventory.currentItem = slot;
			ItemStack itemStack = ClientUtils.player().getHeldItem();
			if (itemStack != null) {
				float damage = getItemDamage(itemStack);

				damage += EnchantmentHelper.func_152377_a(itemStack, EnumCreatureAttribute.UNDEFINED);
				if (damage > weaponDamage) {
					weaponDamage = damage;
					weaponSlot = slot;
				}
			}
		}
		if (weaponSlot != -1) {
			return weaponSlot;
		}
		return originalSlot;
	}

	public static void updateInventory() {
		for (int index = 0; index < 44; index++) {
			try {
				int offset = index < 9 ? 36 : 0;

				Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(
						index + offset, Minecraft.getMinecraft().thePlayer.inventory.mainInventory[index]));
			} catch (Exception localException) {
			}
		}
	}

	public static float getDistanceToGround(Entity e) {
		if (ClientUtils.player().isCollidedVertically) {
			return 0.0F;
		}
		for (float a = (float) e.posY; a > 0.0F; a -= 1.0F) {
			int[] stairs = { 53, 67, 108, 109, 114, 128, 134, 135, 136, 156, 163, 164, 180 };
			int[] exemptIds = { 6, 27, 28, 30, 31, 32, 37, 38, 39, 40, 50, 51, 55, 59, 63, 65, 66, 68, 69, 70, 72, 75,
					76, 77, 83, 92, 93, 94, 104, 105, 106, 115, 119, 131, 132, 143, 147, 148, 149, 150, 157, 171, 175,
					176, 177 };

			Block block = getBlockAtPos(new BlockPos(e.posX, a - 1.0F, e.posZ));
			if (!(block instanceof BlockAir)) {
				if ((Block.getIdFromBlock(block) == 44) || (Block.getIdFromBlock(block) == 126)) {
					return (float) (e.posY - a - 0.5D) < 0.0F ? 0.0F : (float) (e.posY - a - 0.5D);
				}
				int[] arrayOfInt1;
				int j = (arrayOfInt1 = stairs).length;
				for (int i = 0; i < j; i++) {
					int id = arrayOfInt1[i];
					if (Block.getIdFromBlock(block) == id) {
						return (float) (e.posY - a - 1.0D) < 0.0F ? 0.0F : (float) (e.posY - a - 1.0D);
					}
				}
				j = (arrayOfInt1 = exemptIds).length;
				for (int i = 0; i < j; i++) {
					int id = arrayOfInt1[i];
					if (Block.getIdFromBlock(block) == id) {
						return (float) (e.posY - a) < 0.0F ? 0.0F : (float) (e.posY - a);
					}
				}
				return (float) (e.posY - a + block.getBlockBoundsMaxY() - 1.0D);
			}
		}
		return 0.0F;
	}

	public static float[] getFacingRotations(int x, int y, int z, EnumFacing facing) {
		Entity temp = new EntitySnowball(ClientUtils.world());
		temp.posX = (x + 0.5D);
		temp.posY = (y + 0.5D);
		temp.posZ = (z + 0.5D);
		try {
			temp.posX += facing.getDirectionVec().getX() * 0.25D;
			temp.posY += facing.getDirectionVec().getY() * 0.25D;
			temp.posZ += facing.getDirectionVec().getZ() * 0.25D;
		} catch (NullPointerException localNullPointerException) {
		}
		return getAngles(temp);
	}

	public static float getYawChangeToEntity(Entity entity) {
		double deltaX = entity.posX - ClientUtils.player().posX;
		double deltaZ = entity.posZ - ClientUtils.player().posZ;
		double yawToEntity;
		if ((deltaZ < 0.0D) && (deltaX < 0.0D)) {
			yawToEntity = 90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
		} else {
			if ((deltaZ < 0.0D) && (deltaX > 0.0D)) {
				yawToEntity = -90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
			} else {
				yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
			}
		}
		return MathHelper.wrapAngleTo180_float(-(ClientUtils.player().rotationYaw - (float) yawToEntity));
	}

	public static float getPitchChangeToEntity(Entity entity) {
		double deltaX = entity.posX - ClientUtils.player().posX;
		double deltaZ = entity.posZ - ClientUtils.player().posZ;
		double deltaY = entity.posY - 1.6D + entity.getEyeHeight() - ClientUtils.player().posY;
		double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);

		double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));

		return -MathHelper.wrapAngleTo180_float(ClientUtils.player().rotationPitch - (float) pitchToEntity);
	}

	public static float[] getAngles(Entity e) {
		return new float[] { getYawChangeToEntity(e) + ClientUtils.player().rotationYaw,
				getPitchChangeToEntity(e) + ClientUtils.player().rotationPitch };
	}

	public static float[] getEntityRotations(EntityPlayer player, Entity target) {
		double posX = target.posX - player.posX;
		double posY = target.posY + target.getEyeHeight() - (player.posY + player.getEyeHeight() + 0.5D);
		double posZ = target.posZ - player.posZ;
		double var14 = MathHelper.sqrt_double(posX * posX + posZ * posZ);
		float yaw = (float) (Math.atan2(posZ, posX) * 180.0D / 3.141592653589793D) - 90.0F;
		float pitch = (float) -(Math.atan2(posY, var14) * 180.0D / 3.141592653589793D);
		return new float[] { yaw, pitch };
	}

	private float[] getBlockRotations(int x, int y, int z) {
		double var4 = x - ClientUtils.player().posX + 0.5D;
		double var6 = z - ClientUtils.player().posZ + 0.5D;
		double var8 = y - (ClientUtils.player().posY + ClientUtils.player().getEyeHeight() - 1.0D);
		double var14 = MathHelper.sqrt_double(var4 * var4 + var6 * var6);
		float var12 = (float) (Math.atan2(var6, var4) * 180.0D / 3.141592653589793D) - 90.0F;
		return new float[] { var12, (float) -(Math.atan2(var8, var14) * 180.0D / 3.141592653589793D) };
	}

	private static float getItemDamage(ItemStack itemStack) {
		Multimap multimap = itemStack.getAttributeModifiers();
		if (!multimap.isEmpty()) {
			Iterator iterator = multimap.entries().iterator();
			if (iterator.hasNext()) {
				Map.Entry entry = (Map.Entry) iterator.next();
				AttributeModifier attributeModifier = (AttributeModifier) entry.getValue();
				double damage;
				if ((attributeModifier.getOperation() != 1) && (attributeModifier.getOperation() != 2)) {
					damage = attributeModifier.getAmount();
				} else {
					damage = attributeModifier.getAmount() * 100.0D;
				}
				if (attributeModifier.getAmount() > 1.0D) {
					return 1.0F + (float) damage;
				}
				return 1.0F;
			}
		}
		return 1.0F;
	}
}
