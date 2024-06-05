package zamorozka.modules.TRAFFIC;

import java.util.ArrayList;

import de.Hero.settings.Setting;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.math.BlockPos;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventJump;
import zamorozka.event.events.EventMove;
import zamorozka.event.events.EventPacket;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.event.events.EventSendPacket;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.modules.WORLD.Terrain;
import zamorozka.modules.ZAMOROZKA.LagCheck;
import zamorozka.notification.NotificationPublisher;
import zamorozka.notification.NotificationType;
import zamorozka.ui.BlockUtil;
import zamorozka.ui.ClientUtils;
import zamorozka.ui.MovementUtilis;
import zamorozka.ui.MovementUtils;
import zamorozka.ui.Timer2;
import zamorozka.ui.TimerHelper;

public class Jesus extends Module {

	private int counter = 0;
	private int counter2 = 0;

	private long lastSwim = 0L;
	Timer2 ticks2 = new Timer2();
	private int motionDelay;
	Timer2 timer;

	private int stage, ticks;

	public Jesus() {
		super("Jesus", 0, Category.TRAFFIC);
	}

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("Motion");
		options.add("Matrix");
		options.add("Sunrise");
		options.add("NCP");
		options.add("Jump");
		options.add("Dolphin");
		options.add("MiniJump");
		Zamorozka.instance.settingsManager.rSetting(new Setting("Jesus Mode", this, "Motion", options));
		Zamorozka.settingsManager.rSetting(new Setting("MotionSpeed", this, 0.5, 0, 10, true));
		Zamorozka.settingsManager.rSetting(new Setting("JumpBoost", this, 0.5, 0, 10, true));
		Zamorozka.settingsManager.rSetting(new Setting("KeepUp", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("WaterJump", this, true));
	}

	@EventTarget
	public void onUpdate(EventUpdate e) {
		String mode = Zamorozka.settingsManager.getSettingByName("Jesus Mode").getValString();
		if (mode.equalsIgnoreCase("Matrix")) {
			if ((mc.player.isInWater() || mc.player.isInLava()) && mc.player.motionY < 0) {
				mc.gameSettings.keyBindSneak.pressed = false;
				mc.gameSettings.keyBindJump.pressed = false;
			}
			if (mode.equalsIgnoreCase("NCP")) {
				if ((mc.player.isInWater() || mc.player.isInLava()) && mc.player.motionY < 0) {
					mc.gameSettings.keyBindSneak.pressed = false;
					mc.gameSettings.keyBindJump.pressed = false;
				}
			}
		}
	}

	@EventTarget
	public void onUpdate(EventPreMotionUpdates event) {
		String mode = Zamorozka.settingsManager.getSettingByName("Jesus Mode").getValString();
		String modeput = Character.toUpperCase(mode.charAt(0)) + mode.substring(1);
		this.setDisplayName("Jesus §f§" + " " + modeput);
		if (mode.equalsIgnoreCase("Motion")) {
			double ff = Zamorozka.settingsManager.getSettingByName("MotionSpeed").getValDouble();
			if ((mc.player.isInWater() || mc.player.isInLava()) && mc.gameSettings.keyBindForward.pressed) {
				ClientUtils.setSpeed(ff);
			}
		}
		if (mode.equalsIgnoreCase("Jump")) {
			if ((mc.player.isInWater() || mc.player.isInLava()) && mc.player.motionY < 0) {
				mc.player.jump();
			}
		}
		if (mode.equalsIgnoreCase("NCP")) {
			mc.player.onGround = true;
		}
		if (mode.equalsIgnoreCase("MiniJump")) {
			if (mc.player.isInLiquid2() && mc.player.motionY < 0) {
				mc.player.motionY = 0.35;
				MovementUtilis.setMotion(0.14);
			}
			if (mc.player.isMoving() && mc.player.isInLiquid2()) {
				MovementUtilis.setMotion(MovementUtilis.getBaseMoveSpeed());
			}
		}
		if (mode.equalsIgnoreCase("Matrix")) {
			if (!(mc.player.isInLiquid2()))
				return;
			if (!mc.player.isCollidedHorizontally) {
				mc.gameSettings.keyBindJump.pressed = false;
			}
			MovementUtilis.setMotion(0.12);
			if (mc.player.ticksExisted % 1 == 0) {
				mc.player.motionY = 0.001;
			}
			if (mc.player.ticksExisted % 2 == 0) {
				mc.player.motionY = -0.001;
			}
			if (mc.player.isCollidedHorizontally && mc.gameSettings.keyBindJump.isKeyDown()) {
				mc.player.motionY = 0.2;
			}
		}
		if (mode.equalsIgnoreCase("Sunrise")) {
			if (this.mc.player.isInWater()) {
				ClientUtils.setSpeed(
						0.25D + Zamorozka.settingsManager.getSettingByName("MotionSpeed").getValDouble() / 10.0D);
				if (Zamorozka.settingsManager.getSettingByName("KeepUp").getValBoolean()
						|| Zamorozka.settingsManager.getSettingByName("WaterJump").getValBoolean()) {
					BlockPos pos = new BlockPos(this.mc.player.posX, this.mc.player.posY + 1.0D, this.mc.player.posZ);
					if (this.mc.world.getBlockState(pos) != null
							&& this.mc.world.getBlockState(pos).getBlock() instanceof BlockLiquid) {
						double motionY = 0.15D;
						if (Zamorozka.settingsManager.getSettingByName("WaterJump").getValBoolean()) {
							motionY = Zamorozka.settingsManager.getSettingByName("JumpBoost").getValDouble() / 10.0D;
						}

						this.mc.player.motionY = motionY;
					}
				}
			}

		}

		if (mode.equalsIgnoreCase("Dolphin")) {
			if (mc.player.isInWater() || mc.player.isInLava()) {
				mc.player.motionY = 0.04f;
			} else {
				if (mc.player.isCollidedHorizontally) {
					mc.player.motionY = 0.05f;
				}
			}
		}
	}

	@EventTarget
	public void onPacket(EventSendPacket e) {
		String mode = Zamorozka.settingsManager.getSettingByName("Jesus Mode").getValString();
		if (mode.equalsIgnoreCase("NCP")) {
			if (e.getPacket() instanceof CPacketPlayer && BlockUtil.isOnLiquid(0.001)
					&& BlockUtil.isTotalOnLiquid(0.001) && mc.player.isOnLiquid()) {
				CPacketPlayer packet = (CPacketPlayer) e.getPacket();
				packet.y = this.mc.player.ticksExisted % 2 == 0 ? packet.y + 0.015D : packet.y - 0.015D;
				mc.player.motionY = 0.0;
			}
		}
	}
	
	@EventTarget
	public void onMove(EventMove event) {
		String mode = Zamorozka.settingsManager.getSettingByName("Jesus Mode").getValString();
		if (mode.equalsIgnoreCase("Matrix")) {
			if (!(mc.player.isInLiquid2()))
				return;
			MovementUtilis.setMotion(MovementUtilis.getBaseMoveSpeed());
		}
		if (mode.equalsIgnoreCase("NCP")) {
			if (!(mc.player.isInLiquid2()))
				return;
			MovementUtilis.setMotion(MovementUtilis.getBaseMoveSpeed());
		}
	}

	private void AAC2() {
		if (Jesus.mc.gameSettings.keyBindJump.pressed) {
			return;
		}
		if (mc.world.handleMaterialAcceleration(
				Minecraft.player.getEntityBoundingBox().expand(0.0, -1.0, 0.0).contract(0.001, 0.0, 0.001),
				Material.WATER, (Entity) Minecraft.player)) {
			Jesus.mc.gameSettings.keyBindJump.pressed = true;
			this.lastSwim = 1L;
		} else if (this.lastSwim == 1L) {
			Jesus.mc.gameSettings.keyBindJump.pressed = false;
			this.lastSwim = 0L;
		}
	}

	@EventTarget
	public void onPacket(EventPacket event) {
		Packet<?> p = event.getPacket();
		if (event.isIncoming()) {
			if (p instanceof SPacketPlayerPosLook && mc.player != null && mc.player.isInLiquid2()) {
				mc.player.onGround = false;
				mc.player.motionX *= 0;
				mc.player.motionZ *= 0;
				mc.player.jumpMovementFactor = 0;
				if (Zamorozka.moduleManager.getModule(Jesus.class).getState()
						&& Zamorozka.moduleManager.getModule(LagCheck.class).getState()) {
					Zamorozka.moduleManager.getModule(Jesus.class).toggle();
					NotificationPublisher.queue("LagBack", "Jesus was lagback!", NotificationType.WARNING);
					isEnabled = false;
					ModuleManager.getModule(Jesus.class).setState(false);
					isEnabled = false;
					ModuleManager.getModule(Jesus.class).setState(false);
				}
			}
		}
	}

	@Override
	public void onDisable() {
		mc.timer.timerSpeed = 1f;
		super.onDisable();
	}
}
