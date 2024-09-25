package none.module.modules.movement;

import java.util.Arrays;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.stats.StatList;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventJump;
import none.event.events.EventPreMotionUpdate;
import none.event.events.EventStep;
import none.module.Category;
import none.module.Module;
import none.utils.MoveUtils;
import none.utils.PlayerUtil;
import none.utils.TimeHelper;
import none.valuesystem.BooleanValue;
import none.valuesystem.ModeValue;
import none.valuesystem.NumberValue;

public class Step extends Module{

	public Step() {
		super("Step", "Step", Category.MOVEMENT, Keyboard.KEY_NONE);
	}
	
	private static String[] mode = {"Vanilla", "NCP", "AAC", "AAC2Block"};
	public static ModeValue stepmodes = new ModeValue("Step-Mode", "Vanilla", mode);
	private NumberValue<Integer> stepheight = new NumberValue<>("Step-Height", 1, 1, 3);
	private BooleanValue point = new BooleanValue("Point", false);
	private NumberValue<Integer> delay = new NumberValue<>("Step-Delay", 5, 1, 20);
	private NumberValue<Double> ncptimer = new NumberValue<>("NCPStep-Timer", 0.37, 0.05, 1.0);
	
	boolean resetTimer;
	TimeHelper time = new TimeHelper();
	public static TimeHelper lastStep = new TimeHelper();
	public int ticks = 0;
	
	@Override
	protected void onEnable() {
		super.onEnable();
		resetTimer = false;
		ticks = 0;
	}
	
	@Override
	protected void onDisable() {
		super.onDisable();
		mc.timer.timerSpeed = 1;
	}
	
	@Override
	@RegisterEvent(events = {EventStep.class, EventJump.class, EventPreMotionUpdate.class})
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		setDisplayName(getName() + ChatFormatting.WHITE + " " + stepmodes.getSelected());
		String Mode = stepmodes.getSelected();
		double x = mc.thePlayer.posX; double y = mc.thePlayer.posY; double z = mc.thePlayer.posZ;
    	if(event instanceof EventJump){
    		EventJump ej = (EventJump)event;
    		if(ej.isPre()){
//    			if(!lastStep.hasTimeReached(60) && Mode.equalsIgnoreCase("Cubecraft")){
//    				ej.setCancelled(true);
//    			}
    		}
    	}
    	
    	if (event instanceof EventStep) {
        	EventStep es = (EventStep)event;
        	double stepValue = 1.5D;
        	float timer = ncptimer.getFloat();
        	float delay = (this.delay.getObject()/10) * 1000;
        	
        	if(Mode.equalsIgnoreCase("Vanilla"))
        		stepValue = point.getObject() ? stepheight.getInteger() + 0.5D : stepheight.getInteger();
        	if(Mode.equalsIgnoreCase("NCP"))
        		stepValue = point.getObject() ? stepheight.getInteger() + 0.5D : stepheight.getInteger();
        	if(resetTimer){
        		resetTimer = !resetTimer;
        		mc.timer.timerSpeed = 1;
        	}
        	
        	if(!PlayerUtil.isInLiquid())
            	if (es.isPre()) {
            		
            		if (Mode.equalsIgnoreCase("AAC2Block")) {
            			
            		}else if(mc.thePlayer.isCollidedVertically && !mc.gameSettings.keyBindJump.isKeyDown() && time.hasTimeReached((long)delay)){
            			es.setStepHeight(stepValue);		
            			es.setActive(true);
            			
            		}
            		
                }else{
                	double rheight = mc.thePlayer.getEntityBoundingBox().minY - mc.thePlayer.posY;
                	boolean canStep = rheight >= 0.625;
            		if(canStep){
            		 	lastStep.setLastMS();
            		 	time.setLastMS();
//                  		if (Killaura.isSetupTick() && Client.getModuleManager().isEnabled(Killaura.class)) {
//            				Module crits = Client.getModuleManager().get(Criticals.class);
//            				if(((Options) crits.getSetting(Criticals.PACKET).getValue()).getSelected().equalsIgnoreCase("minis") ||
//            						((Options) crits.getSetting(Criticals.PACKET).getValue()).getSelected().equalsIgnoreCase("hminis")){
//             	        		//if minis criticals is not on ground, send packet onground to be able to jump
//            					Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(
//            							mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, true));
//             	        	}
//            			}
            		}
            		switch(Mode){
            		case"NCP": 		
            			if(canStep){
            				mc.timer.timerSpeed = timer - (rheight >= 1 ? Math.abs(1-(float)rheight)*((float)timer*0.55f) : 0);
            				if(mc.timer.timerSpeed <= 0.05f){
            					mc.timer.timerSpeed = 0.05f;
            				}
                			resetTimer = true;
                			ncpStep(rheight);	
            			}	
            			break;
//            		case"Cubecraft":
//            			if(canStep){   	 			
//                			cubeStep(rheight);	
//                			resetTimer = true;
//            				mc.timer.timerSpeed = rheight < 2 ? 0.6f : 0.3f;
//                		}
//            			break;
            		case"AAC":
            			if(canStep){
            				if(rheight < 1.1){
            					mc.timer.timerSpeed = 0.37F;
            					resetTimer = true;
            				}else{
            					mc.timer.timerSpeed = 1 - (float)rheight*0.57f;
            					resetTimer = true;
            				}
                			aacStep(rheight);
                		}
            			break;
            		case "AAC2Block":
            			break;
            		case "Vanilla":
            			
            			return;
            		}
                }
    	}
    	
    	if (event instanceof EventPreMotionUpdate) {
    		EventPreMotionUpdate e = (EventPreMotionUpdate) event;
    		if (e.isPre()) {
    			if (Mode.equalsIgnoreCase("AAC2Block")) {
//	    			if (mc.thePlayer.isCollidedHorizontally) {
	    				switch (ticks) {
						case 0:
							if (MoveUtils.isOnGround(0.001))
								mc.thePlayer.jump();
							break;
						case 7:
							mc.thePlayer.motionY = 0;
							break;
						case 8:
							if (!MoveUtils.isOnGround(0.001)) {
								mc.thePlayer.setPosition(x, y + 1, z);
								mc.thePlayer.motionY = 0;
							}
							break;
						case 9:
							if (!MoveUtils.isOnGround(0.001)) {
								mc.thePlayer.setPosition(x, y + 1, z);
								mc.thePlayer.motionY = 0;
							}
							break;
						default:
							break;
						}
	    				ticks++;
//	    			}else {
	    				mc.timer.timerSpeed = 1F;
//	    				if (ticks > 10) {
//	    					ticks = 0;
//	    				}
//	    			}
    			}
    		}
    	}
	}
	
	void ncpStep(double height){
    	List<Double>offset = Arrays.asList(0.42,0.333,0.248,0.083,-0.078);
    	double posX = mc.thePlayer.posX; double posZ = mc.thePlayer.posZ;
    	double y = mc.thePlayer.posY;
    	if(height < 1.1){
    		double first = 0.42;
    		double second = 0.75;
    		if(height != 1){
    			first *= height;
    			second *= height;
        		if(first > 0.425){
        			first = 0.425;
        		}
        		if(second > 0.78){
        			second = 0.78;
        		}
        		if(second < 0.49){
        			second = 0.49;
        		}
    		}
    		if(first == 0.42)
    			first = 0.41999998688698;
    		mc.thePlayer.connection.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(posX, y + first, posZ, false));
    		if(y+second < y + height)
    		mc.thePlayer.connection.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(posX, y + second, posZ, false));
    		return;
    	}else if(height <1.6){
    		for(int i = 0; i < offset.size(); i++){
        		double off = offset.get(i);
        		y += off;
        		mc.thePlayer.connection.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(posX, y, posZ, false));
        	}
    	}else if(height < 2.1){
    		double[] heights = {0.425,0.821,0.699,0.599,1.022,1.372,1.652,1.869};
			for(double off : heights){
        		mc.thePlayer.connection.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(posX, y + off, posZ, false));
        	}
    	}else{
        	double[] heights = {0.425,0.821,0.699,0.599,1.022,1.372,1.652,1.869,2.019,1.907};
        	for(double off : heights){
        		mc.thePlayer.connection.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(posX, y + off, posZ, false));
        	}
    	}
    	
    }
	
	void aacStep(double height){
		if(height > 1)
			return;
		mc.thePlayer.isAirBorne = true;
        mc.thePlayer.triggerAchievement(StatList.jumpStat);
        double posX = mc.thePlayer.posX; double posY = mc.thePlayer.posY; double posZ = mc.thePlayer.posZ;
    	if(height < 1.1){
    		double first = 0.42;
    		double second = 0.75;

    		if(height > 1) {
    			return;
//    			first *= height;
//    			second *= height;
//        		if(first > 0.4349){
//        			first = 0.4349;
//        		}else if(first < 0.405){
//        			first = 0.405;
//        		} 
    		}
    		mc.thePlayer.connection.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(posX, posY + first, posZ, false));
    		if(posY+second < posY + height)
    		mc.thePlayer.connection.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(posX, posY + second, posZ, false));
    		return;
    	}
    	List<Double> offset = Arrays.asList(0.434999999999998,0.360899999999992,0.290241999999991,0.220997159999987,0.13786084000003104,0.055);
    	double y = mc.thePlayer.posY;
    	for(int i = 0; i < offset.size(); i++){
    		double off = offset.get(i);
    		y += off;
    		if(y > mc.thePlayer.posY + height){
    			double x = mc.thePlayer.posX; double z = mc.thePlayer.posZ;
    			double forward = mc.thePlayer.movementInput.moveForward;
    			double strafe = mc.thePlayer.movementInput.moveStrafe;
   	         	float YAW = mc.thePlayer.rotationYaw;
   	         	double speed = 0.3;
   	         	if(forward != 0 && strafe != 0)
   	         		speed -= 0.09;
   	         	x += (forward * speed * Math.cos(Math.toRadians(YAW + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(YAW + 90.0f))) *1;
   	         	z += (forward * speed * Math.sin(Math.toRadians(YAW + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(YAW + 90.0f))) *1;
   	         	mc.thePlayer.connection.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(
   	         			x, y,z, false));	
   	        
   	         	break;
   	         	
    		}
    		if(i== offset.size() - 1){			
    			double x = mc.thePlayer.posX; double z = mc.thePlayer.posZ;
    			double forward = mc.thePlayer.movementInput.moveForward;
    			double strafe = mc.thePlayer.movementInput.moveStrafe;
   	         	float YAW = mc.thePlayer.rotationYaw;
   	         	double speed = 0.3;
   	         	if(forward != 0 && strafe != 0)
   	         		speed -= 0.09;
   	         	x += (forward * speed * Math.cos(Math.toRadians(YAW + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(YAW + 90.0f))) *1;
   	         	z += (forward * speed * Math.sin(Math.toRadians(YAW + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(YAW + 90.0f))) *1;
   	         	mc.thePlayer.connection.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(
   	         			x, y,z, false));	
    		}else{
    			mc.thePlayer.connection.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(posX, y, posZ, false));
    		}
    	}
    }

}
