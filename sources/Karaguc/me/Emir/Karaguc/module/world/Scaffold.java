package me.Emir.Karaguc.module.world;

import de.Hero.settings.Setting;
import me.Emir.Karaguc.Karaguc;
import me.Emir.Karaguc.event.EventTarget;
import me.Emir.Karaguc.event.events.EventPostMotionUpdate;
import me.Emir.Karaguc.event.events.EventPreMotionUpdate;
import me.Emir.Karaguc.module.Category;
import me.Emir.Karaguc.module.Module;
import me.Emir.Karaguc.utils.BlockUtil;
import me.Emir.Karaguc.utils.KaragucTimer;
import net.minecraft.block.BlockAir;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Scaffold extends Module {

	private BlockPos currentPos;
	private EnumFacing currentFacing;
	private boolean rotated = false;
	private KaragucTimer timer = new KaragucTimer();

	private boolean eagle;
	private double delay;

	public Scaffold() {
		super("Scaffold", Keyboard.KEY_V, Category.WORLD);
		this.setDisplayName("Scaffold");


		ArrayList<String> options = new ArrayList<>();
		//TODO: Add more Scaffold modes
		options.add("Vanilla");
		options.add("AAC");
		options.add("JumpAndWalk");

		Karaguc.instance.settingsManager.rSetting(new Setting("Scaffold Mode", this, "Vanilla", options));
		Karaguc.instance.settingsManager.rSetting(new Setting("Eagle", this, false));
		Karaguc.instance.settingsManager.rSetting(new Setting("NoSwing", this, false));
		Karaguc.instance.settingsManager.rSetting(new Setting("AutoBlockRotation", this, true));
		Karaguc.instance.settingsManager.rSetting(new Setting("BlockNumber", this, false));
		Karaguc.instance.settingsManager.rSetting(new Setting("Safewalk", this, true));
		Karaguc.instance.settingsManager.rSetting(new Setting("BlockID", this, true));
		Karaguc.instance.settingsManager.rSetting(new Setting("Delay", this, 3, 0, 100, false));

		this.delay = Karaguc.instance.settingsManager.getSettingByName("Delay").getValDouble();
		this.eagle = Karaguc.instance.settingsManager.getSettingByName("Eagle").getValBoolean();
		this.eagle = Karaguc.instance.settingsManager.getSettingByName("NoSwing").getValBoolean();
		this.eagle = Karaguc.instance.settingsManager.getSettingByName("AutoBlockRotation").getValBoolean();
		this.eagle = Karaguc.instance.settingsManager.getSettingByName("BlockNumber").getValBoolean();
		this.eagle = Karaguc.instance.settingsManager.getSettingByName("Safewalk").getValBoolean();
	}

	@EventTarget
	public void onUpdate_pre(EventPreMotionUpdate event) {

		if (eagle) {
			if (rotated) {
				this.setSneaking(true);
			}
		} else {
			this.setSneaking(false);
		}

		rotated = false;
		currentPos = null;
		currentFacing = null;

		BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ);
		if (mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir) {
			this.setBlockAndFacing(pos);

			if (currentPos != null) {
				float facing[] = BlockUtil.getDirectionToBlock(currentPos.getX(), currentPos.getY(), currentPos.getZ(),
						currentFacing);
				float yaw = facing[0];
				float pitch = Math.min(90, facing[1] + 9);
				rotated = true;
				event.setYaw(yaw);
				event.setPitch(pitch);
			}
		}
	}

	@EventTarget
	public void onUpdate_post(EventPostMotionUpdate event) {
		if (currentPos != null) {
			if (mc.thePlayer.getCurrentEquippedItem() != null
					&& mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock) {
				mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getCurrentEquippedItem(),
						currentPos, currentFacing, new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ));
				timer.setLastMS();
				mc.thePlayer.swingItem();
			}
		}
	}

	private void setSneaking(boolean bool) {
		KeyBinding sneak = mc.gameSettings.keyBindSneak;
		try {
			Field field = sneak.getClass().getDeclaredField("pressed");
			field.setAccessible(true);
			field.setBoolean(sneak, bool);
		} catch (NoSuchFieldException exception) {
			exception.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private void setBlockAndFacing(BlockPos var1) {
		if (mc.theWorld.getBlockState(var1.add(0, -1, 0)).getBlock() != Blocks.air) {
			this.currentPos = var1.add(0, -1, 0);
			currentFacing = EnumFacing.UP;
		} else if (mc.theWorld.getBlockState(var1.add(-1, 0, 0)).getBlock() != Blocks.air) {
			this.currentPos = var1.add(-1, 0, 0);
			currentFacing = EnumFacing.EAST;
		} else if (mc.theWorld.getBlockState(var1.add(1, 0, 0)).getBlock() != Blocks.air) {
			this.currentPos = var1.add(1, 0, 0);
			currentFacing = EnumFacing.WEST;
		} else if (mc.theWorld.getBlockState(var1.add(0, 0, -1)).getBlock() != Blocks.air) {
			this.currentPos = var1.add(0, 0, -1);
			currentFacing = EnumFacing.SOUTH;
		} else if (mc.theWorld.getBlockState(var1.add(0, 0, 1)).getBlock() != Blocks.air) {
			this.currentPos = var1.add(0, 0, 1);
			currentFacing = EnumFacing.NORTH;
		} else {
			currentPos = null;
			currentFacing = null;
		}
	}

	@Override
	public void onDisable() {
		this.setSneaking(false);
		super.onDisable();
	}
}