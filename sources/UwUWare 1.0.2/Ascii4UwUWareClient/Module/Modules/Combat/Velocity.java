package Ascii4UwUWareClient.Module.Modules.Combat;

import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.World.EventPacketReceive;
import Ascii4UwUWareClient.API.Value.Mode;
import Ascii4UwUWareClient.API.Value.Numbers;
import Ascii4UwUWareClient.API.Value.Value;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import Ascii4UwUWareClient.Util.EventMotionUpdate;
import Ascii4UwUWareClient.Util.Helper;
import Ascii4UwUWareClient.Util.RotationUtil;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

import java.awt.*;

public class Velocity extends Module {
	private final Numbers<Double> percentage = new Numbers<Double>("Percent", "Percent", 0.0, 0.0, 100.0, 5.0);
	public static Mode <Enum> mode = new Mode("Mode", "Mode", VelocityMode.values(), VelocityMode.Normal);
	private Value <Double> xz;
	private Value<Double> y;

	public Velocity() {
		super("Velocity", new String[] { "antivelocity", "antiknockback", "antikb" }, ModuleType.Combat);
		this.addValues(this.percentage);
		addValues (mode);
		this.setColor(new Color(191, 191, 191).getRGB());
	}

	@EventHandler
	private void onPacket(EventPacketReceive e) {
		setSuffix ( percentage.getValue () );
		switch (mode.getModeAsString ()) {
			case "Normal":
				if (e.getPacket () instanceof S12PacketEntityVelocity || e.getPacket () instanceof S27PacketExplosion) {
					if (this.percentage.getValue ().equals ( 0.0 )) {
						e.setCancelled ( true );
					} else {
						S12PacketEntityVelocity packet = (S12PacketEntityVelocity) e.getPacket ();
						packet.motionX = (int) (this.percentage.getValue () / 100.0);
						packet.motionY = (int) (this.percentage.getValue () / 100.0);
						packet.motionZ = (int) (this.percentage.getValue () / 100.0);
					}
				}
				break;
		}
	}
	private void onPacket(EventMotionUpdate event) {
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
				if (this.mc.thePlayer.hurtTime > 0 && this.mc.thePlayer.fallDistance < 3.0f) {
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
		Normal,ACC,Bypass;
	}

	}

