package zamorozka.modules.TRAFFIC;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;

import org.lwjgl.input.Keyboard;

import de.Hero.settings.Setting;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventJump;
import zamorozka.event.events.EventStep;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.BlockUtil;
import zamorozka.ui.ClientUtils;
import zamorozka.ui.MovementUtilis;
import zamorozka.ui.PlayerUtil;
import zamorozka.ui.PlayerUtilis;
import zamorozka.ui.TimerHelper;
import zamorozka.ui.Timerr;

public class Step extends Module {

	private final double[][] offsets = new double[][] { { 0.42D, 0.753D }, { 0.42D, 0.75D, 1.0D, 1.16D, 1.23D, 1.2D },
			{ 0.42D, 0.78D, 0.63D, 0.51D, 0.9D, 1.21D, 1.45D, 1.43D } };
	public static Timerr lastStep = new Timerr();
	public static Timerr time = new Timerr();
	boolean resetTimer;
	private boolean jumped;

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("Vanilla");
		options.add("MotionNCP");
		options.add("NCP");
		options.add("Matrix");
		options.add("CubeCraft");
		Zamorozka.settingsManager.rSetting(new Setting("Step Mode", this, "MotionNCP", options));
		Zamorozka.settingsManager.rSetting(new Setting("StepDelay", this, 0.3, 0, 1, true));
		Zamorozka.settingsManager.rSetting(new Setting("StepHeight", this, 1.5, 1, 10, true));
		Zamorozka.settingsManager.rSetting(new Setting("Reverse", this, true));
	}

	public Step() {
		super("Step", Keyboard.KEY_NONE, Category.TRAFFIC);
	}

	@EventTarget
	public void onUpdate(EventUpdate eventUpdate) {
		String currentmode = Zamorozka.instance.settingsManager.getSettingByName("Step Mode").getValString();
		String modeput = Character.toUpperCase(currentmode.charAt(0)) + currentmode.substring(1);
		this.setDisplayName("Step §f§" + " " + modeput);
		if (Zamorozka.settingsManager.getSettingByName("Reverse").getValBoolean()) {
			if (mc.player.onGround)
				jumped = false;
			if (mc.player.motionY > 0)
				jumped = true;
			if (BlockUtil.collideBlock(mc.player.getEntityBoundingBox(), block -> block instanceof BlockLiquid)
					|| BlockUtil.collideBlock(new AxisAlignedBB(mc.player.getEntityBoundingBox().maxX,
							mc.player.getEntityBoundingBox().maxY, mc.player.getEntityBoundingBox().maxZ,
							mc.player.getEntityBoundingBox().minX, mc.player.getEntityBoundingBox().minY - 0.01D,
							mc.player.getEntityBoundingBox().minZ), block -> block instanceof BlockLiquid))
				return;
			if (!mc.gameSettings.keyBindJump.isKeyDown() && !mc.player.onGround && !MovementInput.jump
					&& mc.player.motionY <= 0D && mc.player.fallDistance <= 1F && !jumped)
				mc.player.motionY--;
		}

	}

	@EventTarget
	public void onStep(EventStep step) {
		String mode = Zamorozka.instance.settingsManager.getSettingByName("Step Mode").getValString();
		final float delay = (float) Zamorozka.settingsManager.getSettingByName("StepDelay").getValDouble() * 1000;
		double stepValue = Zamorozka.settingsManager.getSettingByName("StepHeight").getValDouble();
		final float timer = 0.37F;

		if (resetTimer) {
			resetTimer = !resetTimer;
			mc.timer.timerSpeed = 1;
		}
		if (!mc.player.isInLiquid()) {
			if (step.isPre()) {
				if (mc.player.isCollidedVertically && !mc.gameSettings.keyBindJump.isPressed() && time.delay(delay)) {
					step.setStepHeight(stepValue);
					step.setActive(true);
				}
			} else {
				double rheight = mc.player.getEntityBoundingBox().minY - mc.player.posY;
				boolean canStep = rheight >= 0.625;
				if (canStep) {
					lastStep.reset();
					time.reset();
				}
				if (mode.equalsIgnoreCase("MotionNCP")) {
					if (canStep) {
						mc.timer.timerSpeed = timer
								- (rheight >= 1 ? Math.abs(1 - (float) rheight) * (timer * 0.7f) : 0);
						if (mc.timer.timerSpeed <= 0.05f) {
							mc.timer.timerSpeed = 0.05f;
						}
						resetTimer = true;
						ncpStep(rheight);
					}
				}
				if (mode.equalsIgnoreCase("Vanilla")) {
					if(canStep && (mc.player.isCollidedHorizontally)) {
						if(!getState()) return;
						mc.player.stepHeight = (float) Zamorozka.settingsManager.getSettingByName("StepHeight").getValDouble();
						resetTimer = true;
					}
				}
				if (mode.equalsIgnoreCase("Matrix")) {
					if (canStep) {
						mc.timer.timerSpeed = timer
								- (rheight >= 1 ? Math.abs(1 - (float) rheight) * (timer * 0.7f) : 0);
						if (mc.timer.timerSpeed <= 0.05f) {
							mc.timer.timerSpeed = 0.05f;
						}
						resetTimer = true;
						matrixStep(rheight);
					}
				}
				if (mode.equalsIgnoreCase("NCP")) {
					if (canStep) {
						mc.player.setSprinting(false);
						resetTimer = true;
						ncpStep(rheight);
					}
				}
				if (mode.equalsIgnoreCase("CubeCraft")) {
					if (canStep) {
						cubeStep(rheight);
						resetTimer = true;
						mc.timer.timerSpeed = rheight < 2 ? 0.6f : 0.3f;
					}
				}
			}
		}
	}

	@Override
	public void onDisable() {
		mc.player.stepHeight = 0.625f;
		mc.timer.timerSpeed = 1f;
		super.onDisable();
	}

	void cubeStep(double height) {
		double posX = mc.player.posX;
		double posZ = mc.player.posZ;
		double y = mc.player.posY;
		double first = 0.42;
		double second = 0.75;
		mc.player.connection.sendPacket(new CPacketPlayer.Position(posX, y + first, posZ, false));
	}

	void ncpStep(double height) {
		List<Double> offset = Arrays.asList(0.42, 0.333, 0.248, 0.083, -0.078);
		double posX = mc.player.posX;
		double posZ = mc.player.posZ;
		double y = mc.player.posY;
		if (height < 1.1) {
			double first = 0.42;
			double second = 0.75;
			if (height != 1) {
				first *= height;
				second *= height;
				if (first > 0.425) {
					first = 0.425;
				}
				if (second > 0.78) {
					second = 0.78;
				}
				if (second < 0.49) {
					second = 0.49;
				}
			}
			if (first == 0.42)
				first = 0.41999998688698;
			mc.player.connection.sendPacket(new CPacketPlayer.Position(posX, y + first, posZ, false));
			if (y + second < y + height)
				mc.player.connection.sendPacket(new CPacketPlayer.Position(posX, y + second, posZ, false));
			return;
		} else if (height < 1.6) {
			for (int i = 0; i < offset.size(); i++) {
				double off = offset.get(i);
				y += off;
				mc.player.connection.sendPacket(new CPacketPlayer.Position(posX, y, posZ, false));
			}
		} else if (height < 2.1) {
			double[] heights = { 0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869 };
			for (double off : heights) {
				mc.player.connection.sendPacket(new CPacketPlayer.Position(posX, y + off, posZ, false));
			}
		} else {
			double[] heights = { 0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869, 2.019, 1.907 };
			for (double off : heights) {
				mc.player.connection.sendPacket(new CPacketPlayer.Position(posX, y + off, posZ, false));
			}
		}
	}

	void matrixStep(double height) {
		List<Double> offset = Arrays.asList(0.42, 0.333, 0.248, 0.083, -0.078);
		double posX = mc.player.posX;
		double posZ = mc.player.posZ;
		double y = mc.player.posY;
		if (height < 1.1) {
			double first = 0.42;
			double second = 0.75;
			if (height != 1) {
				first *= height;
				second *= height;
				if (first > 0.425) {
					first = 0.425;
				}
				if (second > 0.78) {
					second = 0.78;
				}
				if (second < 0.49) {
					second = 0.49;
				}
			}
			if (first == 0.42)
				first = 0.41999998688698;
			mc.player.connection.sendPacket(new CPacketPlayer.Position(posX, y + first, posZ, false));
			if (y + second < y + height)
				mc.player.connection.sendPacket(new CPacketPlayer.Position(posX, y + second, posZ, true));
			return;
		} else if (height < 1.6) {
			for (int i = 0; i < offset.size(); i++) {
				double off = offset.get(i);
				y += off;
				mc.player.connection.sendPacket(new CPacketPlayer.Position(posX, y, posZ, true));
			}
		} else if (height < 2.1) {
			double[] heights = { 0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869 };
			for (double off : heights) {
				mc.player.connection.sendPacket(new CPacketPlayer.Position(posX, y + off, posZ, true));
			}
		} else {
			double[] heights = { 0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869, 2.019, 1.907 };
			for (double off : heights) {
				mc.player.connection.sendPacket(new CPacketPlayer.Position(posX, y + off, posZ, true));
			}
		}
	}
}