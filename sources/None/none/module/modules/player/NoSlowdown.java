package none.module.modules.player;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventPreMotionUpdate;
import none.module.Category;
import none.module.Module;
import none.module.modules.combat.Killaura;
import none.utils.MoveUtils;
import none.utils.PlayerUtil;
import none.utils.TimeHelper;
import none.valuesystem.ModeValue;

public class NoSlowdown extends Module{
	
	private static String[] modes = {"Vanilla", "AAC", "Hypixel", "NCP"};
	public static ModeValue noslowmode = new ModeValue("NoSlow-Mode", "Vanilla", modes);
	
	TimeHelper timer = new TimeHelper();
	
	public NoSlowdown() {
		super("NoSlowdown", "NoSlowdown", Category.PLAYER, Keyboard.KEY_NONE);
	}
	
	@Override
	protected void onEnable() {
		super.onEnable();
	}
	
	@Override
	@RegisterEvent(events = EventPreMotionUpdate.class)
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		setDisplayName(getName() + ChatFormatting.WHITE + " " + noslowmode.getSelected());
		
		if (event instanceof EventPreMotionUpdate) {
			EventPreMotionUpdate em = (EventPreMotionUpdate) event;
			switch (noslowmode.getSelected()) {
			case "NCP": {
				if(mc.thePlayer.isUsingItem() && PlayerUtil.isMoving() && MoveUtils.isOnGround(0.42)){
					double x = mc.thePlayer.posX; double y = mc.thePlayer.posY; double z = mc.thePlayer.posZ;
					if (em.isPre()) {					
						mc.thePlayer.connection.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
					} else if (em.isPost()) {
						mc.thePlayer.connection.sendPacket(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
						
					}
				}
	           
			}
			break;
			case "Hypixel": {
				if(mc.thePlayer.isUsingItem() && PlayerUtil.isMoving() && MoveUtils.isOnGround(0.42)){
					double x = mc.thePlayer.posX; double y = mc.thePlayer.posY; double z = mc.thePlayer.posZ;
					if (em.isPre()) {
						mc.thePlayer.connection.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
					} else if (em.isPost()) {
						mc.thePlayer.connection.sendPacket(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
					}
				}
			}
			break;
			
			case "AAC":{
//				if((mc.thePlayer.isUsingItem() || Killaura.isblocking) 
//						&& PlayerUtil.isMoving()){
//					if (em.isPre()) {
//						if(mc.thePlayer.onGround || MoveUtils.isOnGround(0.5))
//						mc.thePlayer.connection.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
//					} else if (em.isPost()) {
//						if(timer.hasTimeReached(65)){
//							mc.thePlayer.connection.sendPacket(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
//							timer.setLastMS();
//						}
//					}
//				}
			}
			break;
			
			case "Vanilla":{
				
			}
			break;
			}
		}
	}

}
