package zamorozka.modules.WORLD;

import java.util.Random;

import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.opengl.GL11;

import de.Hero.settings.Setting;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.AngleUtil;
import zamorozka.ui.BlockUtils;
import zamorozka.ui.MiscUtils;
import zamorozka.ui.RotUtils;
import zamorozka.ui.ZBlock;
import zamorozka.ui.ZItem;

public class Tower extends Module {

	public Tower() {
		super("Scaffold", 0, Category.WORLD);
	}

	/*@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("SprintJump", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("BlockFase1", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("BlockFase2", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("BlockFase3", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("ScaffoldSpeed", this, 0, 0, 2, true));
		Zamorozka.settingsManager.rSetting(new Setting("PlaceChance", this, 100, 1, 100, true));
		Zamorozka.settingsManager.rSetting(new Setting("BlockDown", this, 1, 0, 5, true));
		Zamorozka.settingsManager.rSetting(new Setting("Tower", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("Pitch", this, 180, 0, 360, true));
		Zamorozka.settingsManager.rSetting(new Setting("Yaw", this, 180, 0, 360, true));
	}

	int delay;

	@EventTarget
	public void onUpdate(EventPreMotionUpdates eventUpdate) {
		BlockPos belowPlayer = new BlockPos(mc.player)
				.down((int) Zamorozka.settingsManager.getSettingByName("BlockDown").getValDouble());

		if (!ZBlock.getMaterial(belowPlayer).isReplaceable()) {
			return;
		}

		int newSlot = -1;
		delay += 1;
		for (int i = 0; i < 9; i++) {
			ItemStack stack = mc.player.inventory.getStackInSlot(i);
			if ((!ZItem.isNullOrEmpty(stack)) && ((stack.getItem() instanceof ItemBlock))) {
				if (Block.getBlockFromItem(stack.getItem()).getDefaultState().isFullBlock()) {
					newSlot = i;

					break;
				}
			}
		}
		if (newSlot == -1) {
			return;
		}
		int oldSlot = mc.player.inventory.currentItem;
		mc.player.inventory.currentItem = newSlot;
		if (Zamorozka.settingsManager.getSettingByName("Tower").getValBoolean()) {
			if (mc.gameSettings.keyBindJump.isKeyDown()) {
				BlockUtils.placeBlockScaffold(belowPlayer);
				mc.timer.timerSpeed = 0.5f;
			}
		}

		mc.player.motionX *= Zamorozka.settingsManager.getSettingByName("ScaffoldSpeed").getValDouble();
		mc.player.motionZ *= Zamorozka.settingsManager.getSettingByName("ScaffoldSpeed").getValDouble();

		if (Zamorozka.settingsManager.getSettingByName("BlockFase1").getValBoolean()) {
			jfaceEntity(belowPlayer);
		}
		double place = Zamorozka.settingsManager.getSettingByName("PlaceChance").getValDouble();
		if (AngleUtil.randomFloat(0.0f, 100.0f) <= place) {
			BlockUtils.placeBlockScaffold(belowPlayer);
		}
		if (Zamorozka.settingsManager.getSettingByName("SprintJump").getValBoolean()) {
			if (mc.gameSettings.keyBindSprint.isKeyDown()) {
				if (mc.player.onGround) {
					mc.player.motionY = 0.4;
					mc.player.motionX *= 0;
					mc.player.motionZ *= 0;
				}
			}
		}
		if (newSlot != oldSlot) {

			mc.player.inventory.currentItem = oldSlot;
		}

	}

	public static synchronized void jfaceEntity(BlockPos e) {
		final float[] rotations = getRotationsNeeded(e);

		if (rotations != null) {
			// mc.player.rotationYaw = rotations[0];
			// mc.player.rotationPitch = (float) (rotations[1] + Math.random()*5);// 14
			mc.player.connection
					.sendPacket(new CPacketPlayer.Rotation(rotations[0], rotations[1] + 1, mc.player.onGround));

			mc.player.renderYawOffset = rotations[0];
			mc.player.rotationYawHead = rotations[1];
		}
	}

	public static synchronized void faceEntity(BlockPos e) {
		final float[] rotations = getRotationsNeeded(e);

		if (rotations != null) {
			// mc.player.rotationYaw = rotations[0] +1;
			// mc.player.rotationPitch = rotations[1] + 1.0F;// 14

			mc.player.connection.sendPacket(new CPacketPlayer.Rotation(rotations[0] + 1, rotations[1] + 1.0F, false));

			mc.player.renderYawOffset = rotations[0];
			mc.player.rotationYawHead = rotations[0];
		}
	}

	public static float[] getRotationsNeeded(BlockPos entity) {

		Random random = new Random();
		if (entity == null) {
			return null;
		}

		AxisAlignedBB check = new AxisAlignedBB(entity.getX(), entity.getY(), entity.getZ(), entity.getX() + 1,
				entity.getY() + 1, entity.getZ() + 1);
		final double diffX = entity.getX() - mc.player.posX;
		final double diffZ = entity.getZ() - mc.player.posZ;
		double diffY;

		diffY = entity.down().getY() - (mc.player.posY + mc.player.getEyeHeight() + 1);

		final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
		float yaw = (float) ((float) (Math.atan2(diffZ, diffX)
				* Zamorozka.settingsManager.getSettingByName("Yaw").getValDouble() / 3.141592653589793D) - 180
				+ random.nextFloat());
		float pitch = (float) -(Math.atan2(diffY, dist)
				* Zamorozka.settingsManager.getSettingByName("Pitch").getValDouble() / 3.141592653589793D
				+ random.nextFloat());
		return new float[] { mc.player.rotationYaw + MathHelper.wrapAngleTo180_float(yaw),
				MathHelper.wrapAngleTo180_float(pitch) };

	}

	@Override
	public void onDisable() {
		mc.timer.timerSpeed = 1f;
		super.onDisable();
	}*/

}