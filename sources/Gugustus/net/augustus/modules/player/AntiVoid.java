package net.augustus.modules.player;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;

import net.augustus.Augustus;
import net.augustus.events.EventEarlyTick;
import net.augustus.events.EventSendPacket;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.DoubleValue;
import net.augustus.settings.StringValue;
import net.augustus.utils.ChatUtil;
import net.augustus.utils.MoveUtil;
import net.augustus.utils.TimeHelper;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.util.AxisAlignedBB;

public class AntiVoid extends Module{
	
	public TimeHelper blinkTimeHelper = new TimeHelper();
	public ArrayList<Packet> blinkPackets = new ArrayList<Packet>();
	
	public DoubleValue fallDist = new DoubleValue(56354, "FallDist", this, 7, 1, 20, 1);
	public StringValue mode = new StringValue(6457634, "Mode", this, "MotionFlag", new String[]{"MotionFlag", "Blink"});
	public DoubleValue motion = new DoubleValue(34859034, "Motion", this, 3, 1, 6, 1);
	public DoubleValue blinkDur = new DoubleValue(234654354, "BlinkDuration", this, 1500, 1, 5000, 1);
	
	public boolean done = false;
	
	public AntiVoid() {
		super("AntiVoid", Color.RED, Categorys.PLAYER);
	}
	
   @EventTarget
   public void onEventEarlyTick(EventEarlyTick eventEarlyTick) {
	   if(this.isToggled()) {
		   if(mc.thePlayer.onGround)
			   done = false;
		   if(!done) {
			   switch(mode.getSelected()) {
				   case "MotionFlag": {
					   if(mc.thePlayer.fallDistance > fallDist.getValue() && !isBlockUnder()) {
						   mc.thePlayer.motionY += motion.getValue();
						   done = true;
					   }
					   break;
				   }
			   }
		   }
	   }
   }
	
   @EventTarget
	public void onPacketSend(EventSendPacket eventSendPacket) {
	   if(this.isToggled()) {
		   if(mode.getSelected().equals("Blink")) {
			   if(!isBlockUnder2() && !(mc.thePlayer.hurtTime > 0)) {
				   if(blinkTimeHelper.reached((long)this.blinkDur.getValue())) {
					   blinkTimeHelper.reset();
				   }else {
					   if(MoveUtil.isMoving()) {
//						   ChatUtil.sendChat("blink" + (1000 / blinkTimeHelper.getTime()));
						   blinkPackets.add(eventSendPacket.getPacket());
						   eventSendPacket.setCanceled(true);
					   }
				   }
			   }
		   }
	   }
   }
   
   private boolean isBlockUnder() {
		if (mc.thePlayer.posY < 0)
			return false;
		for (int offset = 0; offset < (int) mc.thePlayer.posY + 2; offset += 2) {
			AxisAlignedBB bb = mc.thePlayer.getEntityBoundingBox().offset(0, -offset, 0);
			if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb).isEmpty()) {
				return true;
			}
		}
		return false;
	}
   
   private boolean isBlockUnder2() {
		if (mc.thePlayer.posY < 0)
			return false;
		for (int offset = 0; offset < (int) mc.thePlayer.posY + 2; offset += 2) {
        	final double yaw = Math.toRadians(Augustus.getInstance().getYawPitchHelper().realYaw);
            final double x = -Math.sin(yaw) * 1;
            final double z = Math.cos(yaw) * 1;
			AxisAlignedBB bb = mc.thePlayer.getEntityBoundingBox().offset(x, -offset, z);
			if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb).isEmpty()) {
				return true;
			}
		}
		return false;
	}

}
