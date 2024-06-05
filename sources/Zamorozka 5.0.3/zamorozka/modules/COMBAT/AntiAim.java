package zamorozka.modules.COMBAT;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketEntityAction;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPostMotionUpdates;
import zamorozka.event.events.EventPreMotion;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.MathUtils;
import zamorozka.ui.TimeHelper;

public class AntiAim extends Module {

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("Reverse");
		options.add("Jittery");
		options.add("XD");
		options.add("ClientSpin");
		options.add("SpinSlow");
		options.add("SpinFast");
		options.add("Aside");
		options.add("Glitchy");
		Zamorozka.instance.settingsManager.rSetting(new Setting("Spin Mode", this, "SpinSlow", options));
		Zamorozka.settingsManager.rSetting(new Setting("CustomPitch", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("CustomPitchValue", this, 90, -90, 90, true));
		Zamorozka.settingsManager.rSetting(new Setting("SpinSpeed", this, 5, 0, 50, true));
	}

	public float yaw;
	public float pitch;
	public Boolean sneak;
	float[] lastAngles;
	public static float rotationPitch;
	private boolean fake;
	private boolean fake1;
	private boolean shouldsneak;
	TimeHelper fakeJitter;

	public AntiAim() {
		super("AntiAim", Keyboard.KEY_NONE, Category.COMBAT);
	}

	@EventTarget
	public void onPreMotion(EventPreMotionUpdates eventMotion) {
		String mode = Zamorozka.instance.settingsManager.getSettingByName("Spin Mode").getValString();
		String modeput = Character.toUpperCase(mode.charAt(0)) + mode.substring(1);
		this.setDisplayName("AntiAim §f§" + " " + modeput);
		if (!this.mc.gameSettings.keyBindUseItem.pressed && KillAura.target == null) {
			if (this.lastAngles == null) {
				this.lastAngles = new float[] { this.mc.player.rotationYaw, this.mc.player.rotationPitch };
			}
			boolean fake;
			if (!this.fake) {
				fake = true;
			} else {
				fake = false;
			}
			this.fake = fake;
			if (mode.equalsIgnoreCase("Jitter")) {
				final float n;
				yaw = (n = this.lastAngles[0] + 90.0f);
				this.lastAngles = new float[] { n, this.lastAngles[1] };
				this.updateAngles(n, this.lastAngles[1]);
				mc.player.renderYawOffset = n;
				mc.player.prevRenderYawOffset = n;
				mc.player.rotationYawHead = n;
			}
		}
		if (Zamorozka.settingsManager.getSettingByName("CustomPitch").getValBoolean()) {
			float pp = (float) Zamorozka.settingsManager.getSettingByName("CustomPitchValue").getValDouble();
			eventMotion.setPitch(pp);
			mc.player.rotationPitchHead = pp;
		}
		float sp = (float) Zamorozka.settingsManager.getSettingByName("SpinSpeed").getValDouble();
		if (mode.equalsIgnoreCase("SpinSlow")) {
			final float yaw8 = this.lastAngles[0] + sp;
			this.lastAngles = new float[] { yaw8, this.lastAngles[1] };
			float sens = getSensitivityMultiplier();
			float yaw = (float) (yaw8 + MathUtils.getRandomInRange(1, -5));
			float pitch = (float) (MathUtils.getRandomInRange(1, -5));
			float yawGCD = (Math.round(yaw / sens) * sens);
			float pitchGCD = (Math.round(pitch / sens) * sens);
			eventMotion.setYaw(yaw);
			eventMotion.setPitch(pitch);
			this.mc.player.rotationYawHead = lastAngles[0] + sp;
			this.mc.player.renderYawOffset = lastAngles[0] + sp;
			
		}
		if (mode.equalsIgnoreCase("Jittery")) {
			final float n;
			yaw = (n = this.lastAngles[0] + 90.0f);
			this.lastAngles = new float[] { n, this.lastAngles[1] };
			float sens = getSensitivityMultiplier();
			eventMotion.setYaw(Math.round(n / sens) * sens);
			this.updateAngles(n, this.lastAngles[1]);
		}
		if (mode.equalsIgnoreCase("ClientSpin")) {
			final float yaw8 = this.lastAngles[0] + 10.0f;
			this.lastAngles = new float[] { yaw8, this.lastAngles[1] };
			float sens = getSensitivityMultiplier();
			mc.player.rotationYaw = (Math.round(yaw8 / sens) * sens);
			this.updateAngles(yaw8, this.lastAngles[1]);
		}
		if (mode.equalsIgnoreCase("SpinFast")) {
			final float yaw7 = this.lastAngles[0] + 45.0f;
			this.lastAngles = new float[] { yaw7, this.lastAngles[1] };
			float sens = getSensitivityMultiplier();
			eventMotion.setYaw(Math.round(yaw7 / sens) * sens);
			this.updateAngles(yaw7, this.lastAngles[1]);
		}
		if (mode.equalsIgnoreCase("XD")) {
			final float yaw = this.lastAngles[0] + 150000.0f;
			this.lastAngles = new float[] { yaw, this.lastAngles[1] };
			float sens = getSensitivityMultiplier();
			eventMotion.setYaw(Math.round(yaw / sens) * sens);
			this.updateAngles(yaw, this.lastAngles[1]);
		}
		if (mode.equalsIgnoreCase("Reverse")) {
			final float yaw2 = this.mc.player.rotationYaw + 180.0f;
			this.lastAngles = new float[] { yaw2, this.lastAngles[1] };
			float sens = getSensitivityMultiplier();
			eventMotion.setYaw(Math.round(yaw2 / sens) * sens);
			this.updateAngles(yaw2, this.lastAngles[1]);
		}
		if (mode.equalsIgnoreCase("Aside")) {
			final float yaw3 = this.mc.player.rotationYaw - 90.0f;
			this.lastAngles = new float[] { yaw3, this.lastAngles[1] };
			float sens = getSensitivityMultiplier();
			eventMotion.setYaw(Math.round(yaw3 / sens) * sens);
			this.updateAngles(yaw3, this.lastAngles[1]);
		}
		if (mode.equalsIgnoreCase("Glitchy")) {
			final float yaw6 = (float) (this.mc.player.rotationYaw + 5.0f + Math.random() * 175.0);
			this.lastAngles = new float[] { yaw6, this.lastAngles[1] };
			float sens = getSensitivityMultiplier();
			eventMotion.setYaw(Math.round(yaw6 / sens) * sens);
			this.updateAngles(yaw6, this.lastAngles[1]);
		}
	}

	private float getSensitivityMultiplier() {
		float f = mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
		return (f * f * f * 8.0F) * 0.15F;
	}

	public void updateAngles(final float n, final float rotationPitch) {
		if (this.mc.gameSettings.thirdPersonView != 0) {
			AntiAim.rotationPitch = rotationPitch;
			this.mc.player.rotationYawHead = n;
			this.mc.player.renderYawOffset = n;
		}
	}
}