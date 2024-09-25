package none.module.modules.combat;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S2APacketParticles;
import none.Client;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventAttack;
import none.event.events.EventJump;
import none.event.events.EventPacket;
import none.event.events.EventPreMotionUpdate;
import none.event.events.EventStep;
import none.module.Category;
import none.module.Module;
import none.module.modules.movement.Fly;
import none.module.modules.movement.Speed;
import none.utils.MoveUtils;
import none.utils.TimeHelper;
import none.valuesystem.ModeValue;
import none.valuesystem.NumberValue;

public class Criticals extends Module{

	public Criticals() {
		super("Criticals", "Criticals", Category.COMBAT, Keyboard.KEY_NONE);
	}
	
	private static final String[] mode = {"Packet", "Minis", "Hover", "Jump", "NoGround", "AAC4"};
	public static ModeValue modes = new ModeValue("Mode", "Packet", mode);
	public static NumberValue<Integer> HURTTIME = new NumberValue<>("HURTTIME", 12, 1, 20);
	
	TimeHelper lastStep = new TimeHelper();
    TimeHelper timer = new TimeHelper();
    int groundTicks, stage, count;
    double y;
    //0.0625101D
    
    @Override
    protected void onEnable() {
    	super.onEnable();
    	stage = 0;
    	count = 0;
    	if (modes.getSelected().equalsIgnoreCase("NoGround")) {
        	if (mc.thePlayer.onGround) 
        		mc.thePlayer.jump();
    	}
    }
	
	@Override
	@RegisterEvent(events = {EventPacket.class, EventPreMotionUpdate.class, EventJump.class, EventAttack.class, EventStep.class})
	public void onEvent(Event event) {
		if (!isEnabled())  return;
		
		setDisplayName(getName() + ChatFormatting.WHITE + " " + modes.getSelected());
		
		String MODE = modes.getSelected();
		
		if(event instanceof EventStep){
        	EventStep es = (EventStep)event;
        
        	if(!es.isPre()){
        		lastStep.setLastMS();
        		if(!mc.thePlayer.isCollidedHorizontally){
        			y = mc.thePlayer.boundingBox.minY;
        			stage = 0;
        		}
        	}
        }
		
		if (event instanceof EventPreMotionUpdate) {
			EventPreMotionUpdate em = (EventPreMotionUpdate)event;
            if(MoveUtils.isOnGround(0.001)){
            	groundTicks ++;
            }else if(!mc.thePlayer.onGround){
            	groundTicks = 0;
            }
            
            if(em.isPre() && MODE.equalsIgnoreCase("Hover")){
        		mc.thePlayer.lastReportedPosY = 0;
    			double ypos = mc.thePlayer.posY;
        		if(MoveUtils.isOnGround(0.001)){
            		em.setOnGround(false);
        			if(stage == 0){
        				y = ypos + 1E-8;
        				em.setOnGround(true);
        			}else if(stage == 1)
        				y-= 5E-15;
        			else
        				y-= 4E-15;
        			
        			if(y <= mc.thePlayer.posY){
        				stage = 0;
        				y = mc.thePlayer.posY;
        				em.setOnGround(true);
        			}
        			em.setY(y);
        			stage ++;
        		}else {
        			stage = 0;
        		}
            }
        }
		
		if (event instanceof EventPacket) {
            EventPacket ep = (EventPacket) event;
            Packet p = ep.getPacket();
         
            if (p instanceof S2APacketParticles ) {
            
                return;
            }
            if(p instanceof S08PacketPlayerPosLook){
		    	stage = 0;
            }
            if(p instanceof C0FPacketConfirmTransaction){
            	C0FPacketConfirmTransaction packet = (C0FPacketConfirmTransaction)p;
            	boolean accepted = packet.isAccepted();
            	int uid = packet.getUid();
            	if(accepted && uid == 0){
        			count ++;
            	}		
            }
            
            if (p instanceof C03PacketPlayer) {
            	C03PacketPlayer packet = (C03PacketPlayer) p;
            	if (modes.getSelected().equalsIgnoreCase("NoGround")) {
	            	if (mc.thePlayer.onGround && Killaura.targeter != null) 
	            		packet.onGround = false;
            	}
            }
        }
		
		if(event instanceof EventJump){
        	EventJump ej = (EventJump)event;
        	if(ej.isPre()){
        		 if (Killaura.isSetupTick() && Client.instance.moduleManager.killaura.isEnabled()) {
        	        	if(MODE.equalsIgnoreCase("Minis")){
        	        		//if minis criticals is not on ground, send packet onground to be able to jump
        	        		Minecraft.getMinecraft().thePlayer.connection.sendPacket(new C03PacketPlayer.C06PacketPlayerPosLook(
        	        				mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, true));
        	        	}
        		 }
        	}
        }
		
		if(event instanceof EventAttack){
        	EventAttack ea = (EventAttack)event;
        	if(ea.isPreAttack()){
        	      if( ea.getEntity() != null){    	        
        	        	if(mc.thePlayer.onGround && MoveUtils.isOnGround(0.001) && !(Client.instance.moduleManager.speed.isEnabled() || Client.instance.moduleManager.fly.isEnabled())){
        	        		if (MODE.equalsIgnoreCase("AAC4")) {
        	        			if (ea.getEntity().hurtResistantTime <= HURTTIME.getInteger() && lastStep.hasTimeReached(20)
       	                    		 && (timer.hasTimeReached(150) && ea.getEntity().hurtResistantTime > 0) && mc.thePlayer.isCollidedVertically) {
//        	        				if (!MoveUtils.isMoveKeyPressed()) {
//        	        					mc.getConnection().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 5.349e-9, mc.thePlayer.posZ, false));
//        	        					mc.getConnection().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 4.953e-9, mc.thePlayer.posZ, false));
//        	        				}
        	        				evc("Criticals.");
//        	        				mc.getConnection().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.949e-13, mc.thePlayer.posZ, false));
//        	        				mc.getConnection().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.153e-13, mc.thePlayer.posZ, false));
        	        				mc.getConnection().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.153e-13, mc.thePlayer.posZ, false));
        	        			}
        	        			timer.setLastMS();
        	        		}else if(MODE.equalsIgnoreCase("Packet")){
        	                     if(ea.getEntity().hurtResistantTime <= HURTTIME.getInteger() && lastStep.hasTimeReached(20)
        	                    		 && (timer.hasTimeReached(200) || ea.getEntity().hurtResistantTime > 0) && mc.thePlayer.isCollidedVertically){
        	                    	 if(groundTicks > 1){
        	                    		 doCrits(); 
        	                    	 }
        	                    	
        	                     }
        	                     timer.setLastMS();
        	        		 }else if (!mc.thePlayer.isJumping && MODE.equalsIgnoreCase("Jump")){
        	        			 mc.thePlayer.jump();
        	        		 }
        	        	}else if (!mc.thePlayer.onGround && !MoveUtils.isOnGround(0.001) && !(Client.instance.moduleManager.speed.isEnabled() || Client.instance.moduleManager.fly.isEnabled())) {
            	        	if (MODE.equalsIgnoreCase("AAC4")) {
        	        			if (ea.getEntity().hurtResistantTime <= HURTTIME.getInteger() && lastStep.hasTimeReached(20)
       	                    		 && (timer.hasTimeReached(250) && ea.getEntity().hurtResistantTime > 0) && !mc.thePlayer.isCollidedVertically) {
        	        				evc("Air Criticals");
        	        				mc.getConnection().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.949e-13, mc.thePlayer.posZ, false));
        	        				mc.getConnection().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.153e-13, mc.thePlayer.posZ, false));
        	        			}
        	        			timer.setLastMS();
        	        		}
            	        }
        	        }
        	}
        }
	}
	
	private boolean hurtTimeCheck(Entity entity) {
        return entity != null && entity.hurtResistantTime <= HURTTIME.getInteger();
    }

    public static void doCrits() {
    	Minecraft mc = Minecraft.getMinecraft();
    	//0.0625 , 17.64e-8 
    	double off = 0.0626;
		double x = mc.thePlayer.posX; double y = mc.thePlayer.posY; double z = mc.thePlayer.posZ;
		mc.thePlayer.connection.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(x, y+off, z, false));
//		if(modes.getSelected().equalsIgnoreCase("HPacket")){
//			mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y+off+0.00000000001, z, false));
//		}
		mc.thePlayer.connection.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, false));
    	
    	
    }

}
