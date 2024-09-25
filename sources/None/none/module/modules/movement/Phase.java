package none.module.modules.movement;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventPacket;
import none.event.events.EventTick;
import none.module.Category;
import none.module.Module;
import none.utils.Utils;
import none.utils.Block.BlockUtils;
import none.valuesystem.ModeValue;

public class Phase extends Module {

	public Phase() {
		super("Phase", "Phase", Category.MOVEMENT, Keyboard.KEY_NONE);
	}
	private static String[] mode = {"AAC350"};
	public static ModeValue modes = new ModeValue("Mode", "AAC350", mode);
	private int tickTimer = 0, toggleTimer = 0;
	
	@Override
	protected void onEnable() {
		super.onEnable();
		tickTimer = 0;
		toggleTimer = 0;
		if (mc.thePlayer == null) return;
		mc.thePlayer.noClip = false;
	}
	
	@Override
	protected void onDisable() {
		super.onDisable();
		tickTimer = 0;
		toggleTimer = 0;
		if (mc.thePlayer == null) return;
		mc.thePlayer.noClip = false;
	}
	
	@Override
	@RegisterEvent(events = {EventTick.class, EventPacket.class})
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		setDisplayName(getName() + ChatFormatting.WHITE + " " + modes.getSelected());
		
		final boolean isInsideBlock = BlockUtils.isInsideBlock(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ));
		
		if (mc.thePlayer.getHealth() <= 1F || mc.thePlayer.isDead || mc.thePlayer.ticksExisted < 1) {
			setState(false);
			return;
		}
			if ((isInsideBlock || mc.thePlayer.isCollidedHorizontally)) {
				mc.thePlayer.noClip = true;
				mc.thePlayer.motionY = 0D;
	            mc.thePlayer.onGround = true;
			}
		
		if (event instanceof EventTick) {
			if (modes.getSelected().equalsIgnoreCase("AAC350")) {
//					if (toggleTimer <= 10) {
						if(tickTimer < 2 || !mc.thePlayer.isCollidedHorizontally || !(!isInsideBlock || mc.thePlayer.isSneaking())) return;
	
		                final double yaw = Math.toRadians(mc.thePlayer.rotationYaw);
		                final double oldX = mc.thePlayer.posX;
		                final double oldZ = mc.thePlayer.posZ;
		                final double x = -Math.sin(yaw);
		                final double z = Math.cos(yaw);
		
		                mc.thePlayer.setPosition(oldX + x, mc.thePlayer.posY, oldZ + z);
		                tickTimer = 0;
		                toggleTimer = 0;
//					}
			}
			tickTimer++;
			toggleTimer++;
		}
		
		if (event instanceof EventPacket) {
			EventPacket ep = (EventPacket) event;
			Packet p = ep.getPacket();
			if (modes.getSelected().equalsIgnoreCase("AAC350")) {
//				if (toggleTimer <= 40) {
					if (p instanceof C03PacketPlayer) {
						final C03PacketPlayer packetPlayer = (C03PacketPlayer) p;
		                final float yaw = (float) Utils.getDirection();
	
		                packetPlayer.x = packetPlayer.x - MathHelper.sin(yaw) * 0.00000001D;
		                packetPlayer.z = packetPlayer.z + MathHelper.cos(yaw) * 0.00000001D;
					}
//				}
			}
		}
	}

}
