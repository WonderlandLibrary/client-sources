package none.module.modules.movement;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.HWID;
import none.Client;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventMove;
import none.module.Category;
import none.module.Module;
import none.utils.TimeHelper;
import none.valuesystem.BooleanValue;
import none.valuesystem.ModeValue;
import none.valuesystem.NumberValue;

public class AntiVoid extends Module{

	public AntiVoid() {
		super("AntiVoid", "AntiVoid", Category.MOVEMENT, Keyboard.KEY_NONE);
	}
	
	private static String[] modes = {"Hypixel", "Motion"};
	public static ModeValue catchmodes = new ModeValue("Catch-Modes", "Hypixel", modes);
	
	public static BooleanValue onlyVoid = new BooleanValue("Only-Void", true);
	public static NumberValue<Double> fallDis = new NumberValue<>("Fall-Distance", 5.0, 3.0, 24.0);
	public static NumberValue<Integer> delay = new NumberValue<>("ResetDelay", 150, 40, 300);
	private boolean canSave = true;
	private TimeHelper timer = new TimeHelper();
	
	@Override
	protected void onEnable() {
		if (mc.thePlayer == null) return;
		
		if (catchmodes.getSelected().equalsIgnoreCase("Hypixel") && !HWID.isHWID()) {
			evc("Premium Only");
			Client.instance.notification.show(Client.notification("Premium", "You are not Premium", 3));
			setState(false);
			return;
		}
		super.onEnable();
	}
	
	@Override
	@RegisterEvent(events = {EventMove.class})
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		setDisplayName(getName() + " " + ChatFormatting.WHITE + catchmodes.getSelected());
		if ((timer.hasTimeReached(delay.getInteger()) || mc.thePlayer.isCollidedVertically)&& !canSave) {
			canSave = true;
			timer.setLastMS();
		}
		double dist = fallDis.getDouble();
		String modes = catchmodes.getSelected();
		
		if (event instanceof EventMove) {
			EventMove em = (EventMove) event;
			if (mc.thePlayer.fallDistance > dist && !Client.instance.moduleManager.fly.isEnabled()) {
				if (!onlyVoid.getObject() || !isBlockUnder()) {
					if (canSave) {
						if (modes.equalsIgnoreCase("Hypixel")) {
							mc.thePlayer.connection.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,mc.thePlayer.posY + 11,mc.thePlayer.posZ, false));
						}else if (modes.equalsIgnoreCase("Motion")) {	
							em.setY(mc.thePlayer.motionY = 0);
						}
						canSave = false;
						timer.setLastMS();
					}
				}
			}
		}
	}
	
	private boolean isBlockUnder() {
    	if(mc.thePlayer.posY < 0)
    		return false;
    	for(int off = 0; off < (int)mc.thePlayer.posY+2; off += 2){
    		AxisAlignedBB bb = mc.thePlayer.boundingBox.offset(0, -off, 0);
    		if(!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb).isEmpty()){
    			return true;
    		}
    	}
    	return false;
    }

}
