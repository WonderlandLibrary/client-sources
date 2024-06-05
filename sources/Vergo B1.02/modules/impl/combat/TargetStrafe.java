package xyz.vergoclient.modules.impl.combat;

import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import xyz.vergoclient.Vergo;
import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventMove;
import xyz.vergoclient.event.impl.EventTick;
import xyz.vergoclient.modules.Module;
import xyz.vergoclient.modules.OnEventInterface;
import xyz.vergoclient.settings.BooleanSetting;
import xyz.vergoclient.settings.ModeSetting;
import xyz.vergoclient.settings.NumberSetting;
import xyz.vergoclient.util.main.ChatUtils;
import xyz.vergoclient.util.main.MovementUtils;
import xyz.vergoclient.util.main.RotationUtils;
import xyz.vergoclient.util.main.ServerUtils;

public class TargetStrafe extends Module implements OnEventInterface {

	public TargetStrafe() {
		super("TargetStrafe", Category.COMBAT);
	}


	protected static Minecraft mc = Minecraft.getMinecraft();
	private static int strafe = 1;

	public static NumberSetting range = new NumberSetting("Range", 1.5, 1, 8, 0.1);

	public static BooleanSetting control = new BooleanSetting("Controllable", false), wallCheck = new BooleanSetting("Wall Check", true),
								 voidCheck = new BooleanSetting("Void Check", true);

	@Override
	public void onEnable() {

		/*if (Vergo.config.modDisabler.isDisabled() && ServerUtils.isOnHypixel()) {
			Vergo.config.modDisabler.toggle();
			ChatUtils.addProtMsg("Disabler has been enabled for strafe.");
		}*/

	}

	@Override
	public void onDisable() {
		// Do nothing
	}

	@Override
	public void loadSettings() {

		addSettings(range, control, voidCheck, wallCheck);
	}

	@Override
	public void onEvent(Event e) {

		if (e instanceof EventTick) {
			setInfo("Hypixel");
		}

		if (e instanceof EventMove) {
			EventMove event = (EventMove) e;
			strafe(event);
		}

	}

	public static boolean strafe(EventMove e) {
		return strafe(e, MovementUtils.getSpeed() * 0.85);
	}

	public static boolean strafe(EventMove e, double moveSpeed) {
		if (canStrafe()) {
			if(mc.gameSettings.keyBindLeft.isPressed()) strafe = 1;
			if(mc.gameSettings.keyBindRight.isPressed()) strafe = -1;

			if(mc.thePlayer.isCollidedHorizontally) strafe = -strafe;

			if(isOverVoid()) {
				strafe = -strafe;
			}

			setSpeed(e, moveSpeed, RotationUtils.getYaw(KillAura.target.getPositionVector()), strafe,
					mc.thePlayer.getDistanceToEntity(KillAura.target) <= range.getValueAsDouble() ? 0 : 1);
			return true;
		}
		return false;
	}

	public static boolean isOverVoid() {
		for (double posY = mc.thePlayer.posY; posY > 0.0D; posY--) {
			if (!(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, posY, mc.thePlayer.posZ)).getBlock() instanceof BlockAir)) {
				return false;
			}
		}
		return true;
	}

	public static boolean canStrafe() {
		if (!Vergo.config.modTargetStrafe.isEnabled() || !MovementUtils.isMoving()) {
			return false;
		}

		/*if (!(Vergo.config.modSpeed.isEnabled() || Vergo.config.modFly.isEnabled())) {
			return false;
		}*/

		if(mc.thePlayer.isOnLadder() || mc.thePlayer.isInLava() || mc.thePlayer.isInWater()) {
			return false;
		}

		if(KillAura.target == null) {
			return false;
		}

		return Vergo.config.modKillAura.isEnabled()
				&& !KillAura.target.isDead;
	}

	public static void setSpeed(EventMove moveEvent, double speed, float yaw, double strafe, double forward) {
		if (forward == 0 && strafe == 0) {
			moveEvent.setX(0);
			moveEvent.setZ(0);
		} else {
			if (forward != 0) {
				if (strafe > 0) {
					yaw += ((forward > 0) ? -45 : 45);
				} else if (strafe < 0) {
					yaw += ((forward > 0) ? 45 : -45);
				}
				strafe = 0;
				if (forward > 0) {
					forward = 1.0;
				} else if (forward < 0) {
					forward = -1.0;
				}
			}

			moveEvent.setX(forward * speed * -Math.sin(Math.toRadians(yaw)) + strafe * speed * Math.cos(Math.toRadians(yaw)));
			moveEvent.setZ(forward * speed * Math.cos(Math.toRadians(yaw)) - strafe * speed * -Math.sin(Math.toRadians(yaw)));
		}
	}
}