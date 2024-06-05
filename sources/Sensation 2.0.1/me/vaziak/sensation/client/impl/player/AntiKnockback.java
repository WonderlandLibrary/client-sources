package me.vaziak.sensation.client.impl.player;

import java.util.ArrayList;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.PlayerUpdateEvent;
import me.vaziak.sensation.client.api.event.events.ProcessPacketEvent;
import me.vaziak.sensation.client.api.property.impl.DoubleProperty;
import me.vaziak.sensation.client.api.property.impl.StringsProperty;
import me.vaziak.sensation.client.impl.combat.Criticals;
import me.vaziak.sensation.client.impl.combat.KillAura;
import me.vaziak.sensation.client.impl.movement.Speed;
import me.vaziak.sensation.utils.client.ChatUtils;
import me.vaziak.sensation.utils.math.MathUtils;
import me.vaziak.sensation.utils.math.TimerUtil;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;

public class AntiKnockback extends Module {

    private StringsProperty mode = new StringsProperty("Mode", "How this cheat will function.", null,
            false, true, new String[]{"Packet", "Reverse", "OldAGC", "Reverse2", "AntiVirus", "AGC"});
    DoubleProperty prop_motion = new DoubleProperty("Horizontal Multiplier", "The amount motion is modified for original knockback ( motionxz *=)", () -> mode.getValue().get("Reverse"), 1.10, 0.5, 3.5, 0.1, null);
    DoubleProperty prop_motiony = new DoubleProperty("Vertical Adder", "The amount motion is modified from original knockback (motiony *=)", () -> mode.getValue().get("Reverse"), 0.10, 0.21, 1.0, 0.1, null);


    TimerUtil timer;
    private double[] positions = new double[]{0.0, 0.0, 0.0};
    private double[] motions = new double[]{0.0, 0.0, 0.0};
    private double cosmicVel;
    private KillAura cheat_killAura;
    private Speed cheat_Speed;
    private Regen cheat_Regen;
    private double posy;
    private boolean grounded, revert;
    private ArrayList<Double> optifine;
	private boolean gotVelocity;

    public AntiKnockback() {
        super("Velocity", Category.PLAYER);
        registerValue(mode, prop_motion, prop_motiony);
        timer = new TimerUtil();
        optifine = new ArrayList();
    }

    public void onEnable() {
    	cosmicVel = 0;
        if (mode.getValue().get("AntiVirus")) {
            timer.reset();
        }
    }

    @Collect
    public void onProcessPacket(ProcessPacketEvent processPacketEvent) {
        if (mc.thePlayer == null || mc.getCurrentServerData() == null)
            return;
        if (processPacketEvent.getPacket() instanceof S02PacketChat) {
        	S02PacketChat packet = (S02PacketChat)processPacketEvent.getPacket();
        	if (packet.getChatComponent().getFormattedText().toString().length() < 10) {
        		processPacketEvent.setCancelled(true);
        	}
        }
        if (mode.getSelectedStrings().get(0).equalsIgnoreCase("Reverse2")) {

            if (mc.thePlayer.onGround && (processPacketEvent.getPacket() instanceof S12PacketEntityVelocity) && (((S12PacketEntityVelocity) processPacketEvent.getPacket()).getEntityID() == mc.thePlayer.getEntityId())) {
                S12PacketEntityVelocity velocity = (S12PacketEntityVelocity) processPacketEvent.getPacket();
                if (!timer.hasPassed(1000)) {
	                double motionX = (velocity.getMotionX() / 8000.0);
	                double motionY = (velocity.getMotionY() / 8000.0);
	                double motionZ = (velocity.getMotionZ() / 8000.0);
	
	            	mc.thePlayer.setPosition(mc.thePlayer.posX + motionX, mc.thePlayer.posY + motionY, mc.thePlayer.posZ + motionZ);
	                posy = getGroundLevel() - .5;
                }
                revert = true;
            }
        }
        
        if (mode.getValue().get("AGC")) {
        	if ((processPacketEvent.getPacket() instanceof S12PacketEntityVelocity)) {
	        	S12PacketEntityVelocity velocity = ((S12PacketEntityVelocity) processPacketEvent.getPacket());
	        	cosmicVel = System.currentTimeMillis();

        	}
        }
        if (mode.getValue().get("Packet") || mode.getValue().get("AntiVirus")) {
        	if (timer.hasPassed(20000)) timer.reset();
            if ((processPacketEvent.getPacket() instanceof S12PacketEntityVelocity) && (((S12PacketEntityVelocity) processPacketEvent.getPacket()).getEntityID() == mc.thePlayer.getEntityId())) {
                S12PacketEntityVelocity packet = (S12PacketEntityVelocity) processPacketEvent.getPacket();
                if (onServer("cosmic")) {

                    packet.motionX = packet.motionZ = 0;
                    packet.motionY *= .3;
                    processPacketEvent.setCancelled(false);
                } else {

                    packet.motionX = packet.motionY = packet.motionZ = 0;
                    processPacketEvent.setCancelled(true);
                }
            }
        }
        if (processPacketEvent.getPacket() instanceof S27PacketExplosion) {
            S27PacketExplosion packetExplosion = (S27PacketExplosion) processPacketEvent.getPacket();
            packetExplosion.field_149152_f = packetExplosion.field_149153_g = packetExplosion.field_149159_h = 0;
            processPacketEvent.setCancelled(true);
        }
    }

    @Collect
    public void onPlayerUpdate(PlayerUpdateEvent playerUpdateEvent) {
        setMode(mode.getSelectedStrings().get(0));
        if (cheat_killAura == null) {
            cheat_killAura = ((KillAura) Sensation.instance.cheatManager.getCheatRegistry().get("Kill Aura"));
        }

        if (cheat_Speed == null) {
            cheat_Speed = (Speed) Sensation.instance.cheatManager.getCheatRegistry().get("Speed");
        }

        if (cheat_Regen == null) {
            cheat_Regen = (Regen) Sensation.instance.cheatManager.getCheatRegistry().get("Regen");
        }

        if (mode.getValue().get("AGC")) {
            if(cosmicVel != 0L && System.currentTimeMillis() - cosmicVel > MathUtils.getRandomInRange(120, 140)) {
            	double random = MathUtils.getRandomInRange(.3, .47);
                mc.thePlayer.motionX *= random;
                mc.thePlayer.motionZ *= random;
                this.
                cosmicVel = 0L;
            }
            if (System.currentTimeMillis() - cosmicVel > 2000L) {
                cosmicVel = 0L;
            }
        }
        if (mode.getValue().get("AntiVirus")) {
            if (mc.thePlayer.ticksExisted < 2) {
                timer.reset();
            }
            if (!timer.hasPassed(3000)) { 
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(false));
            } else { 
            }
        }
     /*   if (playerUpdateEvent.isPre() && playerUpdateEvent.getYaw() != playerUpdateEvent.getLastYaw() && mc.gameSettings.ofKeyBindZoom.isKeyDown()) {
        		double first = playerUpdateEvent.getYaw() - ((int)playerUpdateEvent.getYaw());
        		double second = playerUpdateEvent.getLastYaw() - ((int)playerUpdateEvent.getLastYaw());
        		double value = first - second;
        		optifine.add(value);
        		ChatUtils.debug(String.valueOf(optifine.size() + " " + value));
 
        	 if (!optifine.isEmpty() && optifine.size() > 300){
        		 ChatUtils.debug("DONE");
        		 ChatUtils.debug("DONE");
        		 ChatUtils.debug("DONE");
        		 ChatUtils.debug("DONE");
        		 for (int i = 0; i < optifine.size(); i++) {
        			 System.out.println(String.valueOf(optifine.get(i)) + "f, ");
        		 }
        		 optifine.clear();
			}
        }*/
    /*    if (playerUpdateEvent.isPre() && playerUpdateEvent.getPitch() != playerUpdateEvent.getLastPitch() && mc.gameSettings.ofKeyBindZoom.isKeyDown()) {
 
    		double value = playerUpdateEvent.getPitch() - ((int) playerUpdateEvent.getPitch());
    		optifine.add(value);
    		ChatUtils.debug(String.valueOf(optifine.size() + " " + value));

    	 if (!optifine.isEmpty() && optifine.size() > 300){
    		 ChatUtils.debug("DONE");
    		 ChatUtils.debug("DONE");
    		 ChatUtils.debug("DONE");
    		 ChatUtils.debug("DONE");
    		 for (int i = 0; i < optifine.size(); i++) {
    			 System.out.println(String.valueOf(optifine.get(i)) + "f, ");
    		 }
    		 optifine.clear();
		}
    }*/
        if (mc.getCurrentServerData() != null && (mc.getCurrentServerData().serverIP.toLowerCase().contains("ghostly"))) {
            if (mc.thePlayer.isPotionActive(Potion.moveSlowdown) || mc.thePlayer.isPotionActive(Potion.blindness)) {
                mc.thePlayer.removePotionEffectClient(Potion.moveSlowdown.id);
                mc.thePlayer.removePotionEffectClient(Potion.blindness.id);
                mc.thePlayer.removePotionEffect(Potion.moveSlowdown.id);
                mc.thePlayer.removePotionEffect(Potion.blindness.id);
                mc.thePlayer.addChatComponentMessage(new ChatComponentText("Bypassing this shit freeze plugin lul - take that L ghostly"));
            }
        }

        if (mode.getSelectedStrings().get(0).equalsIgnoreCase("Reverse2")) {

            if (mc.thePlayer.hurtTime != 0) {
                Criticals crits = (Criticals) Sensation.instance.cheatManager.getCheatRegistry().get("Criticals");
                crits.waitDelay = 5; 
                cheat_Speed.waitticks = 8;
            }
            if (!timer.hasPassed(1000)) {
	            if (MathUtils.isInputBetween(mc.thePlayer.hurtTime, 8, 10)) {
	                playerUpdateEvent.setPosY(posy); 
	            }
            } else {
            	if (cosmicVel++ > 10) {
            		cosmicVel = 0;
            		timer.reset();
            	}
            	
            }
        } 

        if (mode.getSelectedStrings().get(0).equalsIgnoreCase("OldAGC")) {
            if (mc.thePlayer.hurtTime != 0) {
                mc.thePlayer.motionY = -8;
                mc.thePlayer.motionX *= .65;
                mc.thePlayer.motionZ *= .65;
            }
        }

        if (mode.getSelectedStrings().get(0).equalsIgnoreCase("AGC")) {
            if(mc.thePlayer.hurtTime > 0) {
                if(!gotVelocity || mc.thePlayer.onGround || mc.thePlayer.fallDistance > 2F) return;

                mc.thePlayer.onGround = true;
            }else
                gotVelocity = false;

        }
        if (mode.getSelectedStrings().get(0).equalsIgnoreCase("Reverse")) {
            if (mc.thePlayer.hurtTime != 0) {
                Criticals crits = (Criticals) Sensation.instance.cheatManager.getCheatRegistry().get("Criticals");
                crits.waitDelay = 5; 
                cheat_Speed.waitticks = 8;
            }
            boolean onMinemenClub = mc.getCurrentServerData() != null && (mc.getCurrentServerData().serverIP.toLowerCase().contains("minemen") || mc.getCurrentServerData().serverIP.toLowerCase().contains("invadedlands") || mc.getCurrentServerData().serverIP.toLowerCase().contains("velt"));
            if (mc.thePlayer.hurtTime == 8) {
                if (onMinemenClub) {
                    if (grounded && timer.hasPassed(200)) {
                    	timer.reset();

                        mc.thePlayer.motionX *= .5;
                        mc.thePlayer.motionZ *= .5;
                    	playerUpdateEvent.setPosY(posy + .25); 
                        grounded = false;
                    }
                }
                motions[0] = mc.thePlayer.motionX * prop_motion.getValue();
                motions[1] = mc.thePlayer.motionY * prop_motiony.getValue();
                motions[2] = mc.thePlayer.motionZ * prop_motion.getValue();
            } else if (mc.thePlayer.hurtTime == 9) {
                if (onMinemenClub) {
                    playerUpdateEvent.setPosY(posy); 
                    grounded = true;
                    revert = true;
                }
                mc.thePlayer.motionX = motions[0];
                mc.thePlayer.motionY = motions[1];
                mc.thePlayer.motionZ = motions[2];
                mc.thePlayer.hurtTime = 0;
            }
        }
    }

    public double getGroundLevel() {
        for (int i = (int) Math.round(mc.thePlayer.posY); i > 0; --i) {
            AxisAlignedBB box = mc.thePlayer.getEntityBoundingBox().addCoord(0, 0, 0);
            box.minY = i - 1;
            box.maxY = i;
            if (isColliding(box) && box.minY <= mc.thePlayer.posY)
                return i;
        }
        return 0;
    }

    private boolean isColliding(AxisAlignedBB box) {
        return mc.theWorld.checkBlockCollision(box);
    }
}