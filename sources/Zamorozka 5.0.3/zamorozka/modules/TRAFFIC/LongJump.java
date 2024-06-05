package zamorozka.modules.TRAFFIC;

import java.util.ArrayList;

import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerAbilities;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventMotion;
import zamorozka.event.events.EventMove;
import zamorozka.event.events.EventPacket;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.modules.PLAYER.NoFall;
import zamorozka.modules.ZAMOROZKA.LagCheck;
import zamorozka.notification.NotificationPublisher;
import zamorozka.notification.NotificationType;
import zamorozka.ui.AngleUtil;
import zamorozka.ui.ClientUtils;
import zamorozka.ui.EntityUtil;
import zamorozka.ui.EntityUtils;
import zamorozka.ui.MovementUtil;
import zamorozka.ui.MovementUtilis;
import zamorozka.ui.MovementUtils;
import zamorozka.ui.PlayerUtil;
import zamorozka.ui.SpeedUtils;
import zamorozka.ui.TimerHelper;

public class LongJump extends Module {

	private int cooldownHops;
	private TimerHelper time2 = new TimerHelper();
	private boolean jump;

	private int counter, ticks;

	private boolean canDisable;

	private float boost;
	private boolean damaged;

	TimerHelper timer;
	private double moveSpeed;
	private int stage;

	public LongJump() {
		super("LongJump", 0, Category.TRAFFIC);
	}

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("MatrixLong");
		options.add("NcpLong");
		options.add("Redesky");
		options.add("KAC");
		Zamorozka.instance.settingsManager.rSetting(new Setting("LongJump Mode", this, "MatrixLong", options));
	}

	@EventTarget
	public void onUpdate(EventMotion event) {
		String mode = Zamorozka.settingsManager.getSettingByName("LongJump Mode").getValString();
		String modeput = Character.toUpperCase(mode.charAt(0)) + mode.substring(1);
		this.setDisplayName("LongJump §f§" + " " + modeput);
		if (mode.equalsIgnoreCase("MatrixLong")) {
			if (mc.player.motionY == 0.0030162615090425808) {
				mc.timer.elapsedPartialTicks = 500000;
				mc.player.jumpMovementFactor = 0.1f;
				mc.player.motionY = 0.9;
			} else {
				mc.timer.timerSpeed = 1;
			}
			if (mc.player.motionY == -0.1376653294635854) {
				mc.timer.timerSpeed = 50;
				mc.player.motionY = 0.4;
			}
			if (mc.player.motionY == 0.09866428319336468) {
				mc.timer.timerSpeed = 500000;
				mc.player.motionY = 0.2;
			}
			if (mc.player.motionY == -0.2703294001215114) {
				mc.player.motionY = 0;
			}
			if (mc.player.motionY == -0.6517088341626173) {
				mc.player.jumpMovementFactor = 0.08f;
			}
		}
		if (mode.equalsIgnoreCase("NcpLong")) {
			if (mc.player.onGround) {
				mc.player.jump();
			}
			if (mc.player.motionY == 0.0030162615090425808) {
				mc.player.jumpMovementFactor = 1f;
			}
		}
		if (mode.equalsIgnoreCase("KAC")) {
			if (!getState())
				return;
			double x = mc.player.posX;
			double y = mc.player.posY;
			double z = mc.player.posZ;
			if (mc.player.onGround) {
				mc.player.jump();
			}
			if (mc.player.motionY >= 0.14) {
				mc.player.motionY *= 0.532f;
				mc.timer.timerSpeed = 2.3f;
			} else {
				mc.timer.timerSpeed = 2f;
			}
		}
		if (mode.equalsIgnoreCase("Redesky")) {
			if (!mc.player.onGround) {
				boost++;
			} else if (boost > 3) {
				ModuleManager.getModule(LongJump.class).setState(false);
			}
			if (mc.player.onGround && boost == 0) {
				mc.player.jump();
			}
			mc.player.jumpMovementFactor = 0.15f;
			mc.player.motionY += 0.05;
		}
	}

	@Override
	public void onEnable() {
		canDisable = false;
		this.boost = 0F;
		super.onEnable();
	}

	@Override
	public void onDisable() {
		mc.player.jumpMovementFactor = 0.0F;
		mc.timer.timerSpeed = 1f;
		mc.player.speedInAir = 0.02f;
		super.onDisable();
	}

	@EventTarget
	public void onPacket(EventPacket event) {
		Packet<?> p = event.getPacket();
		if (event.isIncoming()) {
			if (p instanceof SPacketPlayerPosLook && mc.player != null) {
				mc.player.onGround = false;
				mc.player.motionX *= 0;
				mc.player.motionZ *= 0;
				mc.player.jumpMovementFactor = 0;
				if (Zamorozka.moduleManager.getModule(LongJump.class).getState() && Zamorozka.moduleManager.getModule(LagCheck.class).getState()) {
					Zamorozka.moduleManager.getModule(LongJump.class).toggle();
					NotificationPublisher.queue("LagBack", "LongJump was lagback!", NotificationType.WARNING);
					isEnabled = false;
					ModuleManager.getModule(LongJump.class).setState(false);
					isEnabled = false;
					ModuleManager.getModule(LongJump.class).setState(false);
				}
			}
		}
	}

	public void move(float yaw, float multiplyer) {
		double moveX = -Math.sin(Math.toRadians((double) yaw)) * (double) multiplyer;
		double moveZ = Math.cos(Math.toRadians((double) yaw)) * (double) multiplyer;
		this.mc.player.motionX = moveX;
		this.mc.player.motionZ = moveZ;
	}
}