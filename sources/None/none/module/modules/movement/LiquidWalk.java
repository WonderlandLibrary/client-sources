package none.module.modules.movement;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.MobEffects;
import net.minecraft.util.AxisAlignedBB;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventJump;
import none.event.events.EventLiquidCollide;
import none.event.events.EventPacket;
import none.event.events.EventPreMotionUpdate;
import none.module.Category;
import none.module.Module;
import none.utils.MoveUtils;
import none.utils.TimeHelper;
import none.utils.Utils;
import none.utils.Block.BlockUtils;
import none.valuesystem.ModeValue;

public class LiquidWalk extends Module {

	public LiquidWalk() {
		super("LiquidWalk", "LiquidWalk", Category.MOVEMENT, Keyboard.KEY_L);
	}

	private static String[] modes = { "Basic", "Seksin" };
	public static ModeValue walkmodes = new ModeValue("Mode", "Basic", modes);

	public int stage, water;
	TimeHelper timer = new TimeHelper();

	@Override
	protected void onEnable() {
		super.onEnable();
		stage = 0;
		water = 0;
	}

	@Override
	protected void onDisable() {
		super.onDisable();
	}

	@Override
	@RegisterEvent(events = { EventPreMotionUpdate.class, EventPacket.class, EventLiquidCollide.class,
			EventJump.class })
	public void onEvent(Event event) {
		String currentJesus = walkmodes.getSelected();

		if (!isEnabled())
			return;

		setDisplayName(getName() + ChatFormatting.WHITE + " " + walkmodes.getSelected());

		if (event instanceof EventPacket) {
			EventPacket ep = (EventPacket) event;
			Packet p = ep.getPacket();
			if (p instanceof S08PacketPlayerPosLook) {
				stage = 0;
			}
		}

		if (event instanceof EventJump) {
			EventJump ej = (EventJump) event;
			if (BlockUtils.isOnLiquid(0.1) && currentJesus.equalsIgnoreCase("Seksin")) {
				ej.setCancelled(true);
			} else {
				ej.setCancelled(false);
			}
		}

		if (event instanceof EventPreMotionUpdate) {
			EventPreMotionUpdate em = (EventPreMotionUpdate) event;

			if (!em.isPre())
				return;

			if (currentJesus.equalsIgnoreCase("Seksin")) {
				if (BlockUtils.isOnLiquid(0.1) && !mc.thePlayer.isInWater()) {
					if (MoveUtils.isMoveKeyPressed()) {
						mc.thePlayer.setSprinting(false);
					}
					if (BlockUtils.isTotalOnLiquid(0.125)) {
						em.setY(em.getY() + (mc.thePlayer.ticksExisted % 2 == 0 ? -(0.005) : (0.005)));
						double currentSpeed = Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX
								+ mc.thePlayer.motionZ * mc.thePlayer.motionZ);

						double speed = 1.2;

						double direction = Utils.getDirection();
						if (mc.thePlayer.isPotionActive(MobEffects.SPEED)) {
							speed = 1.1;
//							mc.thePlayer.motionX = -Math.sin(direction) * speed * currentSpeed;
//							mc.thePlayer.motionZ = Math.cos(direction) * speed * currentSpeed;
						} else {
//							mc.thePlayer.motionX = -Math.sin(direction) * speed * currentSpeed;
//							mc.thePlayer.motionZ = Math.cos(direction) * speed * currentSpeed;
						}
					}
				}
			}
		}

		if (event instanceof EventLiquidCollide) {
			EventLiquidCollide ebb = (EventLiquidCollide) event;
			ebb.setBounds(new AxisAlignedBB(ebb.getPos().getX(), ebb.getPos().getY(), ebb.getPos().getZ(),
					ebb.getPos().getX() + 1, ebb.getPos().getY() + (currentJesus.equalsIgnoreCase("Seksin") ? 1 : 1),
					ebb.getPos().getZ() + 1));
			if ((mc.thePlayer.fallDistance > 2 && currentJesus.equalsIgnoreCase("Seksin"))
					|| mc.thePlayer.isInWater()) {
				ebb.setCancelled(false);
			} else {
				if (BlockUtils.isOnLiquid(0.6)) {
					ebb.setCancelled(true);
				} else {
					ebb.setCancelled(false);
				}
			}
		}
	}

	public double getMotionY(double stage) {
		stage--;
		double[] motion = new double[] { 0.500, 0.484, 0.468, 0.436, 0.404, 0.372, 0.340, 0.308, 0.276, 0.244, 0.212,
				0.180, 0.166, 0.166, 0.156, 0.123, 0.135, 0.111, 0.086, 0.098, 0.073, 0.048, 0.06, 0.036, 0.0106, 0.015,
				0.004, 0.004, 0.004, 0.004, -0.013, -0.045, -0.077, -0.109 };
		if (stage < motion.length && stage >= 0)
			return motion[(int) stage];
		else
			return -999;

	}
}
