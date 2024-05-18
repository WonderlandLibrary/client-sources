package me.protocol_client.modules;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import me.protocol_client.thanks_slicky.properties.Value;
import net.minecraft.block.BlockAir;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MovementInput;
import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventMove;
import events.EventPacketSent;
import events.EventPostMotionUpdates;
import events.EventPreMotionUpdates;

public class Speed extends Module {

	public final Value<Boolean>	vanilla	= new Value<>("speed_vanilla", false);
	public final Value<Boolean>	yport	= new Value<>("speed_new", true);
	public final Value<Boolean>	old		= new Value<>("speed_old", false);
	public final Value<Boolean>	aac		= new Value<>("speed_aac", false);

	public Speed() {
		super("Speed", "speed", 0, Category.MOVEMENT, new String[] { "speed" });
	}

	public void onEnable() {
		EventManager.register(this);
	}

	public void onDisable() {
		this.delay1 = (this.delay2 = this.delay3 = 0.0D);
		net.minecraft.util.Timer.timerSpeed = 1.0F;
		Blocks.packed_ice.slipperiness = 0.98F;
		Blocks.ice.slipperiness = 0.98F;
		Wrapper.getPlayer().motionY = -1000f;
		EventManager.unregister(this);
	}

	private int				ticks;
	private int				movement;
	public boolean			shouldJump	= true;

	private double			delay1		= 0.0D;
	private double			delay2		= 0.0D;
	private double			delay3;
	public static boolean	collideCheck;
	private boolean			b1;
	private boolean			b2;
	private boolean			b3;
	public static double	speed;
	public static float		moveSpeed	= 2.4f;

	@EventTarget
	public void onMove(EventMove event) {
		if (Wrapper.getPlayer().moveForward == 0 && Wrapper.getPlayer().moveStrafing == 0) {
			event.setCancelled(true);
		}
		if (vanilla.getValue()) {
			this.moveSpeed = 2f;
			MovementInput movementInput = Wrapper.getPlayer().movementInput;
			float forward = movementInput.moveForward;
			float strafe = movementInput.moveStrafe;
			double mx = Math.cos(Math.toRadians(Wrapper.getPlayer().rotationYaw + 90.0F));
			double mz = Math.sin(Math.toRadians(Wrapper.getPlayer().rotationYaw + 90.0F));
			double motionX = forward * this.moveSpeed * mx + strafe * this.moveSpeed * mz;
			double motionZ = forward * this.moveSpeed * mz - strafe * this.moveSpeed * mx;
			event.x = (forward * this.moveSpeed * mx + strafe * this.moveSpeed * mz);
			event.z = (forward * this.moveSpeed * mz - strafe * this.moveSpeed * mx);
		}
	}

	@EventTarget
	public void onPreEvent(EventPreMotionUpdates event) {
		setDisplayName("Speed");
		if (old.getValue()) {
			this.setDisplayName(getDisplayName() + " [Old]");
			if (Wrapper.getPlayer().onGround && (Wrapper.getPlayer().moveForward != 0 || Wrapper.getPlayer().moveStrafing != 0)) {
				Wrapper.getPlayer().motionY = 0.05f;
				Wrapper.getPlayer().motionX *= Wrapper.getPlayer().isSprinting() ? 1.4f : 1.6f;
				Wrapper.getPlayer().motionZ *= Wrapper.getPlayer().isSprinting() ? 1.4f : 1.6f;
			}
		}
		if (aac.getValue()) {
			this.setDisplayName(this.getDisplayName() + " [AAC]");
			if (Wrapper.getPlayer().onGround) {
				Wrapper.getPlayer().motionX *= 1.80f;
				Wrapper.getPlayer().motionZ *= 1.80f;
				Wrapper.mc().timer.timerSpeed = 1f;
			}
		}
		if (vanilla.getValue()) {
			this.setDisplayName(getDisplayName() + " [Vanilla]");
		}
		if (yport.getValue()) {
			setDisplayName(getDisplayName() + " [New]");
			if ((Wrapper.getPlayer().moveForward != 0 || Wrapper.getPlayer().moveStrafing != 0) && !Wrapper.getPlayer().isCollidedHorizontally) {
				if (Wrapper.getPlayer().fallDistance > 3.994)
					return;
				Wrapper.getPlayer().jumpMovementFactor *= 1.0485f;
				if (Wrapper.getPlayer().isInWater() || Wrapper.getPlayer().isOnLadder())
					return;
				Wrapper.getPlayer().posY -= 0.3993f;
				Wrapper.getPlayer().motionY = -1000f;
				Wrapper.mc().timer.timerSpeed = 1.0F;
			}
		}
	}

	@EventTarget
	public void onPostEvent(EventPostMotionUpdates event) {
		if (yport.getValue()) {
			if (Wrapper.getPlayer().isInWater() || Wrapper.getPlayer().isOnLadder())
				return;
			boolean check = Wrapper.getBlockAbovePlayer(Wrapper.getPlayer(), 1) instanceof BlockAir ? Wrapper.getPlayer().isCollidedVertically : Wrapper.getPlayer().onGround;
			if (check && (Wrapper.getPlayer().moveForward != 0 || Wrapper.getPlayer().moveStrafing != 0) && !Wrapper.getPlayer().isCollidedHorizontally) {
				Wrapper.getPlayer().posY += 0.3993f;
				Wrapper.getPlayer().motionY = 0.3993f;
				Wrapper.getPlayer().distanceWalkedOnStepModified = 44f;
				Wrapper.getPlayer().motionX *= 1.59f;
				Wrapper.getPlayer().motionZ *= 1.59f;
				Wrapper.mc().timer.timerSpeed = 1.2F;
			}
		}
		if (aac.getValue()) {
			if (Wrapper.getPlayer().onGround) {
				Wrapper.getPlayer().motionX *= 0.7f;
				Wrapper.getPlayer().motionZ *= 0.7f;
				Wrapper.mc().timer.timerSpeed = 1.1f;
			}
		}
	}

	@EventTarget
	public void onPacketSent(EventPacketSent packet) {
		updateMS();
		if (packet.getPacket() instanceof C03PacketPlayer) {
			if (aac.getValue()) {

			} else {

			}
		}
	}

	public void runCmd(String s) {
		try {
			String[] args = s.split(" ");
			if (args[0].startsWith("mode")) {
				if (args[1].equalsIgnoreCase("old")) {
					this.old.setValue(!this.old.getValue());
					Wrapper.tellPlayer("§7Speed mode " + Protocol.primColor + "old §7is " + (this.old.getValue() ? "now" : "no longer") + " active");
					return;
				}
				if (args[1].equalsIgnoreCase("new")) {
					this.yport.setValue(!this.yport.getValue());
					Wrapper.tellPlayer("§7Speed mode " + Protocol.primColor + "new §7is " + (this.yport.getValue() ? "now" : "no longer") + " active");
					return;
				}
				if (args[1].equalsIgnoreCase("AAC")) {
					this.aac.setValue(!this.aac.getValue());
					Wrapper.tellPlayer("§7Speed mode " + Protocol.primColor + "AAC §7is " + (this.aac.getValue() ? "now" : "no longer") + " active");
					return;
				}
				if (args[1].equalsIgnoreCase("vanilla")) {
					this.vanilla.setValue(!this.vanilla.getValue());
					Wrapper.tellPlayer("§7Speed mode " + Protocol.primColor + "vanilla §7is " + (this.vanilla.getValue() ? "now" : "no longer") + " active");
					return;
				}
				Wrapper.invalidCommand("Speed");
				Wrapper.tellPlayer("\2477-" + Protocol.primColor + "Speed\2477 <mode> <vanilla/old/yport/aac>");
				return;
			}
			Wrapper.invalidCommand("Speed");
			Wrapper.tellPlayer("\2477-" + Protocol.primColor + "Speed\2477 <mode> <vanilla/old/yport/aac>");
			return;
		} catch (Exception e) {
			Wrapper.invalidCommand("Speed");
			Wrapper.tellPlayer("\2477-" + Protocol.primColor + "Speed\2477 <mode> <vanilla/old/yport/aac>");
		}
	}
}
