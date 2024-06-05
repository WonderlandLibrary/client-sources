package Focus.Beta.IMPL.Module.impl.combat;

import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

import java.awt.*;

import Focus.Beta.API.EventHandler;
import Focus.Beta.API.events.world.EventPacketReceive;
import Focus.Beta.API.events.world.EventPreUpdate;
import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.IMPL.Module.Type;
import Focus.Beta.IMPL.set.Mode;
import Focus.Beta.IMPL.set.Numbers;
import Focus.Beta.IMPL.set.Value;
import Focus.Beta.UTILS.Math.RotationUtil;
import Focus.Beta.UTILS.helper.Helper;

public class Velocity extends Module {
	private final Numbers<Double> v = new Numbers<Double>("Veritical", "Veritical", 0.0, 0.0, 100.0, 1.0);
	private final Numbers<Double> h = new Numbers<Double>("Horizontal", "Horizontal", 0.0, 0.0, 100.0, 1.0);
	public static Mode <Enum> mode = new Mode("Mode", "Mode", VelocityMode.values(), VelocityMode.Packet);
	private Value <Double> xz;
	private Value<Double> y;

	public Velocity() {
		super("Velocity", new String[] { "antivelocity", "antiknockback", "antikb" }, Type.COMBAT, "Don't take knockback");
		this.addValues(this.h, this.v);
		addValues (mode);
		this.setColor(new Color(191, 191, 191).getRGB());
	}

	@EventHandler
	private void onPacket(EventPacketReceive e) {
		switch (mode.getModeAsString ()) {
			case "Packet":
				if (e.getPacket () instanceof S12PacketEntityVelocity || e.getPacket () instanceof S27PacketExplosion) {
						e.setCancelled ( true );

				}

				break;
			case "Custom":
				if(e.getPacket() instanceof S12PacketEntityVelocity){
					S12PacketEntityVelocity packet = (S12PacketEntityVelocity) e.getPacket ();
					packet.motionX = (int)(h.getValue() / 100.0D);
					packet.motionY = (int)(v.getValue() / 100.0D);
					packet.motionZ = (int)(h.getValue() / 100.0D);

				}
		}
	}
	private void onPacket(EventPreUpdate event) {
		switch (mode.getModeAsString ()) {
			case "Reserve":
				double G = (RotationUtil.currentRotation != null) && (RotationUtil.moveToRotation) ? RotationUtil.currentRotation.yaw : mc.thePlayer.rotationYaw;
				if (mc.thePlayer.hurtTime == 9) {
					mc.thePlayer.motionX *= 0.9D;
					mc.thePlayer.motionZ *= 0.9D;
				}
				if (mc.thePlayer.hurtTime == 8) {
					mc.thePlayer.motionX *= 0.8D;
					mc.thePlayer.motionZ *= 0.8D;
				}
				if (mc.thePlayer.hurtTime == 5) {
					mc.thePlayer.motionX = (-Math.sin(Math.toRadians(G)) * 0.12D);
					mc.thePlayer.motionZ = (Math.cos(Math.toRadians(G)) * 0.12D);
					Helper.sendMessage ( String.valueOf ( G ) );
				}
				break;
			case"AAC":
				if (this.mc.thePlayer.hurtTime > 0 ) {
					if (this.mc.thePlayer.moveForward == 0.0f && this.mc.thePlayer.moveStrafing == 0.0f) {
						this.mc.thePlayer.motionY -=  0.3;
						this.mc.thePlayer.motionX *=  0.2;
						this.mc.thePlayer.motionZ *= 0.3;
						this.mc.thePlayer.motionY +=  0.3;
					} else {
						this.mc.thePlayer.motionY -=  0.3;
						this.mc.thePlayer.motionX *= ( 0.3) + 0.2;
						this.mc.thePlayer.motionZ *= ( 0.2) + 0.2;
						this.mc.thePlayer.motionY +=  0.3;
					}
				}
				break;
		}
	}
	public enum VelocityMode{
		Packet, Custom,AAC,Reserve;
	}

	}

