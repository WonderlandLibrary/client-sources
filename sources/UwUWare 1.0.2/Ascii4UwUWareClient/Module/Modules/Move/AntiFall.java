package Ascii4UwUWareClient.Module.Modules.Move;

import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.World.EventMove;
import Ascii4UwUWareClient.API.Events.World.EventPreUpdate;
import Ascii4UwUWareClient.API.Value.Mode;
import Ascii4UwUWareClient.API.Value.Numbers;
import Ascii4UwUWareClient.API.Value.Option;
import Ascii4UwUWareClient.Client;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import Ascii4UwUWareClient.Util.MoveUtils;
import Ascii4UwUWareClient.Util.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;

import java.awt.*;

public class AntiFall extends Module {

	private boolean saveMe;
	private final TimerUtil timer = new TimerUtil();
	private final Mode<Enum> mode = new Mode("Mode", "Mode", AntiMode.values(), AntiMode.Motion );
	private final Option<Boolean> ov = new Option<Boolean>("OnlyVoid", "OnlyVoid", true);
	private static final Numbers<Double> distance = new Numbers<Double>("Distance", "Distance", 5.0, 1.0, 10.0, 1.0);

	public AntiFall() {
		super("AntiFall", new String[] { "novoid", "antifall" }, ModuleType.Move);
		this.setColor(new Color(223, 233, 233).getRGB());
		this.addValues(this.ov, distance, this.mode);
	}

	private boolean isBlockUnder() {
		if (Minecraft.thePlayer.posY < 0)
			return false;
		for (int off = 0; off < (int) Minecraft.thePlayer.posY + 2; off += 2) {
			AxisAlignedBB bb = Minecraft.thePlayer.boundingBox.offset(0, -off, 0);
			if (!Minecraft.theWorld.getCollidingBoundingBoxes( Minecraft.thePlayer, bb).isEmpty()) {
				return true;
			}
		}
		return false;
	}

	@EventHandler
	private void onMove(EventMove e) {
		if (Minecraft.thePlayer.fallDistance > (mode.getModeAsString().equalsIgnoreCase("Hypixel") ? 4 : distance.getValue())
				&& !Client.instance.getModuleManager().getModuleByClass(Fly.class).isEnabled()) {
			if (!(this.ov.getValue()) || !isBlockUnder()) {
				if (!saveMe) {
					saveMe = true;
					timer.reset();
				}
				Minecraft.thePlayer.fallDistance = 0;
				if (this.mode.getValue() == AntiMode.Hypixel) {
					Minecraft.thePlayer.motionY = 0;
					Minecraft.thePlayer.motionX = 0;
					Minecraft.thePlayer.motionZ = 0;
					Minecraft.thePlayer.capabilities.setFlySpeed(0);
					Minecraft.thePlayer.setMoveSpeed(e, 0);
					Minecraft.thePlayer.capabilities.isFlying = true;
				} else if (this.mode.getValue() == AntiMode.Motion) {
					EventMove.setY ( Minecraft.thePlayer.motionY = 0);
				}
			}
		}
	}

	@EventHandler
	private void onUpdate(EventPreUpdate e) {
		this.setSuffix(this.mode.getValue());
		if ((saveMe && timer.delay(mode.getModeAsString().equalsIgnoreCase("Hypixel") ? 4000 : 150F)) || Minecraft.thePlayer.isCollidedVertically) {
			saveMe = false;
			if(mode.getModeAsString().equalsIgnoreCase("Hypixel")){
				Minecraft.thePlayer.capabilities.isFlying = false;
			}
			timer.reset();
		}
	}
	
	@Override
	public void onEnable() {
	}

	enum AntiMode {
		Motion, Hypixel
	}
}
