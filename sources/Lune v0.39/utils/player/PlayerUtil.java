package me.superskidder.lune.utils.player;

import java.util.ArrayList;

import me.superskidder.lune.Lune;
import me.superskidder.lune.utils.timer.Timer;
import me.superskidder.lune.utils.client.I18n;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;

import org.lwjgl.util.vector.Vector3f;


public class PlayerUtil {
	private static Minecraft mc = Minecraft.getMinecraft();
	Timer timer = new me.superskidder.lune.utils.timer.Timer();
	me.superskidder.lune.utils.timer.Timer lastCheck = new Timer();
	public boolean shouldslow = false;
	public boolean collided = false;

	public static void sendMessage(String msg) {
		if(mc.theWorld != null) {
			String translated = I18n.format(msg);
			if(translated != null){
				msg = translated;
			}

			mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(EnumChatFormatting.LIGHT_PURPLE + "[" + Lune.CLIENT_NAME + "]" + EnumChatFormatting.GRAY + msg));
		} else {
			System.out.println(msg);
		}
	}

	public static void sendMessageClean(String msg) {
		if(mc.theWorld != null) {
			String translated = I18n.format(msg);
			if(translated != null){
				msg = translated;
			}

			mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(msg));
		} else {
			System.out.println(msg);
		}
	}

	public static boolean onServer(String server) {
		if(mc.theWorld != null) {
			if (!mc.isSingleplayer() && mc.getCurrentServerData().serverIP.toLowerCase().contains(server.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	public static void renderRotation(float[] rotations){
		mc.thePlayer.rotationYawHead = rotations[0];
		mc.thePlayer.renderYawOffset = rotations[0];
		mc.thePlayer.rotationPitchHead = rotations[1];
	}

	public static float getDirection() {
		float yaw = mc.thePlayer.rotationYaw;
		if (mc.thePlayer.moveForward < 0.0f) {
			yaw += 180.0f;
		}
		float forward = 1.0f;
		if (mc.thePlayer.moveForward < 0.0f) {
			forward = -0.5f;
		} else if (mc.thePlayer.moveForward > 0.0f) {
			forward = 0.5f;
		}
		if (mc.thePlayer.moveStrafing > 0.0f) {
			yaw -= 90.0f * forward;
		}
		if (mc.thePlayer.moveStrafing < 0.0f) {
			yaw += 90.0f * forward;
		}
		return yaw *= 0.017453292f;
	}

	public static boolean isInWater() {
		if (mc.theWorld
				.getBlockState(
						new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ))
				.getBlock().getMaterial() == Material.water) {
			return true;
		}
		return false;
	}

	public static boolean isOnWater() {
		final double y = mc.thePlayer.posY - 0.03;
		for (int x = MathHelper.floor_double(mc.thePlayer.posX); x < MathHelper
				.ceiling_double_int(mc.thePlayer.posX); ++x) {
			for (int z = MathHelper.floor_double(mc.thePlayer.posZ); z < MathHelper
					.ceiling_double_int(mc.thePlayer.posZ); ++z) {
				final BlockPos pos = new BlockPos(x, MathHelper.floor_double(y), z);
				if (mc.theWorld.getBlockState(pos).getBlock() instanceof BlockLiquid
						&& mc.theWorld.getBlockState(pos).getBlock().getMaterial() == Material.water) {
					return true;
				}
			}
		}
		return false;
	}

	public static void toFwd(double speed) {
		float yaw = mc.thePlayer.rotationYaw * 0.017453292f;
		mc.thePlayer.motionX -= (double) MathHelper.sin(yaw) * speed;
		mc.thePlayer.motionZ += (double) MathHelper.cos(yaw) * speed;
	}

	public static void setSpeed(double speed) {
		mc.thePlayer.motionX = (-Math.sin(PlayerUtil.getDirection())) * speed;
		mc.thePlayer.motionZ = Math.cos(PlayerUtil.getDirection()) * speed;
	}

	public static void tellPlayer(String string) {
		Minecraft.getMinecraft();
		mc.thePlayer.addChatMessage(new ChatComponentText(string));
	}

	public static double getSpeed() {
		Minecraft.getMinecraft();
		Minecraft.getMinecraft();
		Minecraft.getMinecraft();
		Minecraft.getMinecraft();
		return Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX
				+ mc.thePlayer.motionZ * mc.thePlayer.motionZ);
	}

	public static Block getBlockUnderPlayer(EntityPlayer inPlayer) {
		return PlayerUtil.getBlock(new BlockPos(inPlayer.posX, inPlayer.posY - 1.0, inPlayer.posZ));
	}

	public static Block getBlock(BlockPos pos) {
		Minecraft.getMinecraft();
		return mc.theWorld.getBlockState(pos).getBlock();
	}

	public static Block getBlockAtPosC(EntityPlayer inPlayer, double x2, double y2, double z2) {
		return PlayerUtil.getBlock(new BlockPos(inPlayer.posX - x2, inPlayer.posY - y2, inPlayer.posZ - z2));
	}

	public static ArrayList<Vector3f> vanillaTeleportPositions(double tpX, double tpY, double tpZ, double speed) {
		ArrayList<Vector3f> positions = new ArrayList<Vector3f>();
		Minecraft mc2 = Minecraft.getMinecraft();
		double posX = tpX - mc.thePlayer.posX;
		double posY = tpY - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight() + 1.1);
		double posZ = tpZ - mc.thePlayer.posZ;
		float yaw = (float) (Math.atan2(posZ, posX) * 180.0 / 3.141592653589793 - 90.0);
		float pitch = (float) ((-Math.atan2(posY, Math.sqrt(posX * posX + posZ * posZ))) * 180.0 / 3.141592653589793);
		double tmpX = mc.thePlayer.posX;
		double tmpY = mc.thePlayer.posY;
		double tmpZ = mc.thePlayer.posZ;
		double steps = 1.0;
		double d2 = speed;
		while (d2 < PlayerUtil.getDistance(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ,
				tpX, tpY, tpZ)) {
			steps += 1.0;
			d2 += speed;
		}
		d2 = speed;
		while (d2 < PlayerUtil.getDistance(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ,
				tpX, tpY, tpZ)) {
			tmpX = mc.thePlayer.posX - Math.sin(PlayerUtil.getDirection(yaw)) * d2;
			tmpZ = mc.thePlayer.posZ + Math.cos(PlayerUtil.getDirection(yaw)) * d2;
			positions.add(new Vector3f((float) tmpX, (float) (tmpY -= (mc.thePlayer.posY - tpY) / steps),
					(float) tmpZ));
			d2 += speed;
		}
		positions.add(new Vector3f((float) tpX, (float) tpY, (float) tpZ));
		return positions;
	}

	public static float getDirection(float yaw) {
		Minecraft.getMinecraft();
		if (mc.thePlayer.moveForward < 0.0f) {
			yaw += 180.0f;
		}
		float forward = 1.0f;
		Minecraft.getMinecraft();
		if (mc.thePlayer.moveForward < 0.0f) {
			forward = -0.5f;
		} else {
			Minecraft.getMinecraft();
			if (mc.thePlayer.moveForward > 0.0f) {
				forward = 0.5f;
			}
		}
		Minecraft.getMinecraft();
		if (mc.thePlayer.moveStrafing > 0.0f) {
			yaw -= 90.0f * forward;
		}
		Minecraft.getMinecraft();
		if (mc.thePlayer.moveStrafing < 0.0f) {
			yaw += 90.0f * forward;
		}
		return yaw *= 0.017453292f;
	}

	public static double getDistance(double x1, double y1, double z1, double x2, double y2, double z2) {
		double d0 = x1 - x2;
		double d1 = y1 - y2;
		double d2 = z1 - z2;
		return MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);
	}

	public static boolean MovementInput() {
		if (!(PlayerUtil.mc.gameSettings.keyBindForward.pressed || PlayerUtil.mc.gameSettings.keyBindLeft.pressed
				|| PlayerUtil.mc.gameSettings.keyBindRight.pressed || PlayerUtil.mc.gameSettings.keyBindBack.pressed)) {
			return false;
		}
		return true;
	}

	public static boolean isMoving() {
		if ((!mc.thePlayer.isCollidedHorizontally) && (!mc.thePlayer.isSneaking())) {
			return ((mc.thePlayer.movementInput.moveForward != 0.0F || mc.thePlayer.movementInput.moveStrafe != 0.0F));
		}
		return false;
	}

	public static double getIncremental(final double val, final double inc) {
		final double one = 1.0 / inc;
		return Math.round(val * one) / one;
	}

	public static boolean isMoving2() {
		return ((mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F));
	}

	public static boolean isInLiquid() {
		if (mc.thePlayer == null) {
			return false;
		}
		for (int x = MathHelper.floor_double(mc.thePlayer.boundingBox.minX); x < MathHelper
				.floor_double(mc.thePlayer.boundingBox.maxX) + 1; x++) {
			for (int z = MathHelper.floor_double(mc.thePlayer.boundingBox.minZ); z < MathHelper
					.floor_double(mc.thePlayer.boundingBox.maxZ) + 1; z++) {
				BlockPos pos = new BlockPos(x, (int) mc.thePlayer.boundingBox.minY, z);
				Block block = mc.theWorld.getBlockState(pos).getBlock();
				if ((block != null) && (!(block instanceof BlockAir))) {
					return block instanceof BlockLiquid;
				}
			}
		}
		return false;
	}

	public static boolean isOnGround1(double height) {
		return !PlayerUtil.mc.theWorld.getCollidingBoundingBoxes((Entity) PlayerUtil.mc.thePlayer,
				PlayerUtil.mc.thePlayer.getEntityBoundingBox().offset(0.0, -height, 0.0)).isEmpty();
	}

	public static void setMotion(double speed) {
		double forward = mc.thePlayer.movementInput.moveForward;
		double strafe = mc.thePlayer.movementInput.moveStrafe;
		float yaw = mc.thePlayer.rotationYaw;
		if (forward == 0.0 && strafe == 0.0) {
			mc.thePlayer.motionX = 0.0;
			mc.thePlayer.motionZ = 0.0;
		} else {
			if (forward != 0.0) {
				if (strafe > 0.0) {
					yaw += (float) (forward > 0.0 ? -45 : 45);
				} else if (strafe < 0.0) {
					yaw += (float) (forward > 0.0 ? 45 : -45);
				}
				strafe = 0.0;
				if (forward > 0.0) {
					forward = 1.0;
				} else if (forward < 0.0) {
					forward = -1.0;
				}
			}
			mc.thePlayer.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0f))
					+ strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f));
			mc.thePlayer.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0f))
					- strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f));
		}
	}

	public static double getBaseMoveSpeed() {
		double baseSpeed = 0.2873;
		if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
			int amplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
			baseSpeed *= 1.0 + 0.2 * (double) (amplifier + 1);
		}
		return baseSpeed;

	}

	public static BlockPos getHypixelBlockpos(String str) {
		int val = 89;
		if (str != null && str.length() > 1) {
			char[] chs = str.toCharArray();

			int lenght = chs.length;
			for (int i = 0; i < lenght; i++)
				val += (int) chs[i] * str.length() * str.length() + (int) str.charAt(0) + (int) str.charAt(1);
			val /= str.length();
		}
		return new BlockPos(val, -val % 255, val);
	}

	public static void blockHit(Entity en, boolean value) {
		Minecraft mc = Minecraft.getMinecraft();
		ItemStack stack = mc.thePlayer.getCurrentEquippedItem();
		if (mc.thePlayer.getCurrentEquippedItem() != null && en != null && value && stack.getItem() instanceof ItemSword
				&& (double) mc.thePlayer.swingProgress > 0.2D) {
			KeyBinding.onTick(mc.gameSettings.keyBindUseItem.getKeyCode());
		}

	}

	public static boolean isOnLiquid() {
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

	public static float getMaxFallDist() {
		PotionEffect potioneffect = PlayerUtil.mc.thePlayer.getActivePotionEffect(Potion.jump);
		int f = potioneffect != null ? potioneffect.getAmplifier() + 1 : 0;
		return PlayerUtil.mc.thePlayer.getMaxFallHeight() + f;
	}

	public static boolean isHoldingSword() {
		return mc.thePlayer.getCurrentEquippedItem() != null
				&& mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword;
	}

	public static boolean isBlockUnder() {
		EntityPlayerSP player = mc.thePlayer;
		WorldClient world = mc.theWorld;
		AxisAlignedBB pBb = player.getEntityBoundingBox();
		double height = player.posY + (double) player.getEyeHeight();
		int offset = 0;
		while ((double) offset < height) {
			if (!world.getCollidingBoundingBoxes(player, pBb.offset(0.0, -offset, 0.0)).isEmpty()) {
				return true;
			}
			offset += 2;
		}
		return false;
	}

	private static double getPotionDamageLevel(double damage) {
		int base = 54;
		if (PlayerUtil.mc.thePlayer.isPotionActive(Potion.jump)) {
			base += (PlayerUtil.mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 18;
		}
		if (damage != 1.0) {
			base = (int)((double)base + (damage - 1.0) * 18.0);
			if (damage > 11.0) {
				base -= 16;
			} else if (damage > 8.0) {
				base -= 8;
			}
		}
		return base;
	}

	public static void damagePlayer(boolean makeSetPosition, double damage) {
		int i = 0;
		while ((double)i <= PlayerUtil.getPotionDamageLevel(damage)) {
			PlayerUtil.mc.thePlayer.sendQueue.addToSendQueueNoEvent((Packet)new C03PacketPlayer.C04PacketPlayerPosition(PlayerUtil.mc.thePlayer.posX, PlayerUtil.mc.thePlayer.posY + 0.060100000351667404, PlayerUtil.mc.thePlayer.posZ, false));
			PlayerUtil.mc.thePlayer.sendQueue.addToSendQueueNoEvent((Packet)new C03PacketPlayer.C04PacketPlayerPosition(PlayerUtil.mc.thePlayer.posX, PlayerUtil.mc.thePlayer.posY + 5.000000237487257E-4, PlayerUtil.mc.thePlayer.posZ, false));
			PlayerUtil.mc.thePlayer.sendQueue.addToSendQueueNoEvent((Packet)new C03PacketPlayer.C04PacketPlayerPosition(PlayerUtil.mc.thePlayer.posX, PlayerUtil.mc.thePlayer.posY + 0.004999999888241291 + 6.01000003516674E-8, PlayerUtil.mc.thePlayer.posZ, false));
			++i;
		}
		PlayerUtil.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer(true));
	}

    public double getHypixelSpeed(int stage) {
		double value = MoveUtils.defaultSpeed() + 0.2 * (double) MoveUtils.getSpeedEffect()
				+ (double) MoveUtils.getSpeedEffect() / 15.0;
		double firstvalue = 0.4145 + (double) MoveUtils.getSpeedEffect() / 12.5;
		double decr = (double) stage / 500.0 * 2.0;
		if (stage == 0) {
			value = 0.64 + ((double) MoveUtils.getSpeedEffect() + 0.028 * (double) MoveUtils.getSpeedEffect()) * 0.134;
		} else if (stage == 1) {
			value = firstvalue;
		} else if (stage >= 2) {
			value = firstvalue - decr;
		}
		if (this.shouldslow || !this.lastCheck.delay(500.0f) || this.collided) {
			value = 0.2;
			if (stage == 0) {
				value = 0.0;
			}
		}
		return Math.max(value,
				this.shouldslow ? value : MoveUtils.defaultSpeed() + 0.028 * (double) MoveUtils.getSpeedEffect());
	}

	public static boolean isOnHypixel() {
		return !mc.isSingleplayer() && mc.getCurrentServerData().serverIP.contains("hypixel");
	}

	public static double defaultSpeed() {
		double baseSpeed = 0.2873;
		Minecraft.getMinecraft();
		if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
			Minecraft.getMinecraft();
			int amplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
			baseSpeed *= 1.0 + 0.2 * (double) (amplifier + 1);
		}
		return baseSpeed;
	}

	public static int getSpeedEffect() {
		if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
			return mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1;
		}
		return 0;
	}
}
