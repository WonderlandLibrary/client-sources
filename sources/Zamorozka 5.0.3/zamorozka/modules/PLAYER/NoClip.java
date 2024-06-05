package zamorozka.modules.PLAYER;

import java.util.ArrayList;
import java.util.UUID;

import org.lwjgl.opengl.GL11;

import de.Hero.settings.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockHopper;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketConfirmTransaction;
import net.minecraft.network.play.client.CPacketCreativeInventoryAction;
import net.minecraft.network.play.client.CPacketKeepAlive;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketSpectate;
import net.minecraft.network.play.client.CPacketPlayer.Position;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Timer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameType;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.event.events.EventUpdate;
import zamorozka.event.events.RenderEvent3D;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.modules.VISUALLY.Freecam;
import zamorozka.ui.AngleUtil;
import zamorozka.ui.ClientUtils;
import zamorozka.ui.ItemStackUtil;
import zamorozka.ui.MoveUtils;
import zamorozka.ui.MovementUtil;
import zamorozka.ui.MovementUtilis;
import zamorozka.ui.MovementUtils;
import zamorozka.ui.PlayerUtil;

public class NoClip extends Module {

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("Vanilla");
		options.add("SunriseTest");
		options.add("SunriseTest2");
		Zamorozka.instance.settingsManager.rSetting(new Setting("NoClip Mode", this, "SunriseTest", options));
		Zamorozka.settingsManager.rSetting(new Setting("SunriseClipTimerSpeed", this, 0.3, 0.1, 1, true));
		Zamorozka.settingsManager.rSetting(new Setting("SunriseTicksDisable", this, 4, 2, 20, true));
		Zamorozka.settingsManager.rSetting(new Setting("AntiRelogBypass", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("ForwardBoost", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("ForwardBoostSpeed", this, 0.3, 0.1, 2, false));
		Zamorozka.settingsManager.rSetting(new Setting("SpeedUp", this, 0.5, 0.1, 2, true));
		Zamorozka.settingsManager.rSetting(new Setting("SpeedDown", this, 0.5, 0.1, 2, true));
	}

	public NoClip() {
		super("NoClip", 0, Category.PLAYER);
	}

	private int moveUnder;

	@EventTarget
	public void onPre(EventPreMotionUpdates event) {
		if (!getState())
			return;
		double spd = Zamorozka.settingsManager.getSettingByName("AntiRelogBypass").getValDouble();
		double up = Zamorozka.settingsManager.getSettingByName("SpeedUp").getValDouble();
		double down = Zamorozka.settingsManager.getSettingByName("SpeedDown").getValDouble();
		String mode = Zamorozka.settingsManager.getSettingByName("NoClip Mode").getValString();
		if (mc.player != null) {
			if (mode.equalsIgnoreCase("Vanilla")) {
				mc.player.noClip = false;
				mc.player.onGround = mc.player.motionY >= 0.092 ? true : false;
				mc.player.motionY = 0.0000000000000000000000000000000000000000000001;
				if (Zamorozka.settingsManager.getSettingByName("ForwardBoostSpeed").getValBoolean()) {
					if (mc.gameSettings.keyBindForward.isKeyDown()) {
						MovementUtilis.forward(Zamorozka.settingsManager.getSettingByName("ForwardBoostSpeed").getValDouble());
					}
				}
			}
			if (mode.equalsIgnoreCase("SunriseTest")) {
				mc.player.noClip = false;
				mc.player.onGround = mc.player.motionY >= 0.092 ? true : false;
				mc.player.motionY = 0.0000000000000000000000000000000000000000000001;
				sendPacket(new CPacketSpectate(UUID.randomUUID()));
				double phx = mc.player.posX;
				double phz = mc.player.posZ;
				if (phx == mc.player.posX && phz == mc.player.posZ) {
					if (mc.gameSettings.keyBindForward.isKeyDown()) {
						MovementUtilis.forward(0.3);
					}
					mc.timer.timerSpeed = (float) Zamorozka.settingsManager.getSettingByName("SunriseClipTimerSpeed").getValDouble();
				}
			}
			if (mode.equalsIgnoreCase("SunriseTest2")) {
				mc.player.noClip = false;
				mc.player.onGround = mc.player.motionY >= 0.092 ? true : false;
				mc.player.motionY = 0.0000000000000000000000000000000000000000000001;
				sendPacket(new CPacketSpectate(UUID.randomUUID()));
				double a = mc.player.rotationYaw * 0.017453292;
				double l = mc.player.rotationYaw * 0.017453292 - Math.PI * 1.5;
				double r = mc.player.rotationYaw * 0.017453292 + Math.PI * 1.5;
				double rf = mc.player.rotationYaw * 0.017453292 + Math.PI * 0.19;
				double lf = mc.player.rotationYaw * 0.017453292 + Math.PI * -0.19;
				double lb = mc.player.rotationYaw * 0.017453292 - Math.PI * 0.76;
				double rb = mc.player.rotationYaw * 0.017453292 - Math.PI * -0.76;
				if (mc.gameSettings.keyBindForward.pressed) {
					if (mc.gameSettings.keyBindLeft.pressed && !mc.gameSettings.keyBindRight.pressed) {
						mc.player.motionX -= (Math.sin(lf) * 0.07);
						mc.player.motionZ += (Math.cos(lf) * 0.07);
					} else if (mc.gameSettings.keyBindRight.pressed && !mc.gameSettings.keyBindLeft.pressed) {
						mc.player.motionX -= (Math.sin(rf) * 0.07);
						mc.player.motionZ += (Math.cos(rf) * 0.07);
					} else {
						mc.player.motionX -= (Math.sin(a) * 0.07);
						mc.player.motionZ += (Math.cos(a) * 0.07);
					}
				} else if (mc.gameSettings.keyBindBack.pressed) {
					if (mc.gameSettings.keyBindLeft.pressed && !mc.gameSettings.keyBindRight.pressed) {
						mc.player.motionX -= (Math.sin(lb) * 0.07);
						mc.player.motionZ += (Math.cos(lb) * 0.07);
					} else if (mc.gameSettings.keyBindRight.pressed && !mc.gameSettings.keyBindLeft.pressed) {
						mc.player.motionX -= (Math.sin(rb) * 0.07);
						mc.player.motionZ += (Math.cos(rb) * 0.07);
					} else {
						mc.player.motionX += (Math.sin(a) * 0.07);
						mc.player.motionZ -= (Math.cos(a) * 0.07);
					}
				} else if (mc.gameSettings.keyBindLeft.pressed && !mc.gameSettings.keyBindRight.pressed && !mc.gameSettings.keyBindForward.pressed && !mc.gameSettings.keyBindBack.pressed) {
					mc.player.motionX += (Math.sin(l) * 0.07);
					mc.player.motionZ -= (Math.cos(l) * 0.07);
				} else if (mc.gameSettings.keyBindRight.pressed && !mc.gameSettings.keyBindLeft.pressed && !mc.gameSettings.keyBindForward.pressed && !mc.gameSettings.keyBindBack.pressed) {
					mc.player.motionX += (Math.sin(r) * 0.07);
					mc.player.motionZ -= (Math.cos(r) * 0.07);
				}
				if (mc.player.ticksExisted % (int) Zamorozka.settingsManager.getSettingByName("SunriseTicksDisable").getValDouble() == 0) {
					isEnabled = false;
					mc.timer.timerSpeed = 1f;

				}
			}
		}
		float f = (float) MovementUtilis.getDirection() + 13 - 40;
		if (Zamorozka.settingsManager.getSettingByName("AntiRelogBypass").getValBoolean()) {
			MovementUtilis.forward(f);
			sendPacket(new CPacketSpectate(UUID.randomUUID()));
		}
		if (mc.gameSettings.keyBindJump.pressed)
			mc.player.motionY = up;
		if (mc.gameSettings.keyBindSneak.pressed)
			mc.player.motionY = -down;
	}

	public boolean isInsideBlock() {
		for (int x = MathHelper.floor(mc.player.boundingBox.minX); x < MathHelper.floor(mc.player.boundingBox.maxX) + 1; ++x) {
			for (int y = MathHelper.floor(mc.player.boundingBox.minY + 1.0D); y < MathHelper.floor(mc.player.boundingBox.maxY) + 2; ++y) {
				for (int z = MathHelper.floor(mc.player.boundingBox.minZ); z < MathHelper.floor(mc.player.boundingBox.maxZ) + 1; ++z) {
					Block block = mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
					if (block != null && !(block instanceof BlockAir)) {
						AxisAlignedBB boundingBox = block.getSelectedBoundingBox(mc.world.getBlockState(new BlockPos(x, y, z)), mc.world, new BlockPos(x, y, z));
						if (block instanceof BlockHopper) {
							boundingBox = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);
						}

						if (boundingBox != null && mc.player.boundingBox.intersectsWith(boundingBox))
							return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean isNoClip(Entity entity) {
		if (ModuleManager.getModule(NoClip.class).getState() && mc.player != null && (mc.player.ridingEntity == null || entity == mc.player.ridingEntity))
			return true;
		return entity.noClip;

	}

	public void onDisable() {
		mc.timer.timerSpeed = 1f;
		mc.renderGlobal.loadRenderers();
		mc.player.onGround = false;

	}
}
