package me.vaziak.sensation.client.impl.combat;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.BlockStepEvent;
import me.vaziak.sensation.client.api.event.events.PlayerJumpEvent;
import me.vaziak.sensation.client.api.event.events.PlayerUpdateEvent;
import me.vaziak.sensation.client.api.property.impl.StringsProperty;
import me.vaziak.sensation.utils.client.ChatUtils;
import me.vaziak.sensation.utils.math.BlockUtils;
import me.vaziak.sensation.utils.math.MathUtils;
import me.vaziak.sensation.utils.math.TimerUtil;
import net.minecraft.block.BlockGlass;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class Criticals extends Module { 

    public StringsProperty criticalHits = new StringsProperty("Mode", "Critical mode fucking retard", false, true, new String[]{"Watchdog", "Falcon", "Float"});
	public int groundTicks;
	public int waitDelay;
	
	public double fallDistance;
	public double y;
	
	public float yaw;
	public float pitch;
	public boolean nearGround;
	private int waitticks;
	
	private TimerUtil timer;
	
    public Criticals() {
        super("Criticals", Category.COMBAT);
        registerValue(criticalHits);
        timer = new TimerUtil();
    }
    
	@Collect
	public void onBlockStep(BlockStepEvent event) {
		if (!event.isPre() && mc.thePlayer != null && mc.thePlayer.getEntityBoundingBox().minY - mc.thePlayer.posY < .0626 && mc.thePlayer.getEntityBoundingBox().minY - mc.thePlayer.posY > .2) {
			y = mc.thePlayer.getEntityBoundingBox().minY;
			if (groundTicks != 0) {
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, y, mc.thePlayer.posZ, yaw, pitch, PlayerUpdateEvent.onGround));
				groundTicks = 0;
			}
			waitDelay = 3;
		}
	}
	
	@Collect
	public void onUpdate(PlayerUpdateEvent event) {
		setMode(criticalHits.getSelectedStrings().get(0));
		if (mc.thePlayer.fallDistance != 0) {
			waitDelay = 3;
		}
		yaw = event.getYaw();
		pitch = event.getPitch();
	}

	@Collect
	public void onPlayerJump(PlayerJumpEvent event) {
		if (mc.thePlayer == null || mc.theWorld == null) return;
		if (criticalHits.getValue().get("Watchdog") && groundTicks != 0 && mc.thePlayer.isMoving()) {
			event.setCancelled(true);  
			if (groundTicks != 0) {
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, yaw, pitch, PlayerUpdateEvent.onGround));
				groundTicks = 0;
			}
			mc.thePlayer.motionY = .42f;
	        if (mc.thePlayer.isSprinting()) {
	            float f = mc.thePlayer.rotationYaw * 0.017453292F;
	            mc.thePlayer.motionX -= (double) (MathHelper.sin(f) * 0.20F);
	            mc.thePlayer.motionZ += (double) (MathHelper.cos(f) * 0.20F);
	        }
		} else {
			event.setCancelled(false);
		}
	}
	
	public void critical(PlayerUpdateEvent event, String type) {
		boolean targetCheck1 = false;
		KillAura aura = (KillAura) Sensation.instance.cheatManager.getCheatRegistry().get("Kill Aura");
		boolean targetCheck2 = aura.getMode().equalsIgnoreCase("Multi") || aura.targetList.get(aura.targetIndex) != null && mc.thePlayer.getDistanceToEntity(aura.targetList.get(aura.targetIndex)) <= aura.reach.getValue();

 
		boolean blockcheck = mc.thePlayer.posY % .0625 == 0.0;
		boolean cancritical =  !Sensation.instance.cheatManager.isModuleEnabled("Scaffold") && (targetCheck1 || targetCheck2) && !Sensation.instance.cheatManager.isModuleEnabled("Long Jump") && !Sensation.instance.cheatManager.isModuleEnabled("Fly") && !Sensation.instance.cheatManager.isModuleEnabled("Speed")  && mc.thePlayer.fallDistance == .0 && !mc.gameSettings.keyBindJump.isKeyDown() && mc.thePlayer.isCollidedVertically && mc.thePlayer.onGround && !mc.thePlayer.isInWater();
		
		if (event.isPre()) {  
			if (blockcheck) {
				if (waitDelay <= 0) {
					waitDelay = 0;
					if (cancritical) {
						switch (criticalHits.getSelectedStrings().get(0)) {
							case "Watchdog":
 
								groundTicks++;
								event.setOnGround(false); 
								event.setPosY(mc.thePlayer.posY + (.12 + (0.003999996185303445)));
							break;
	
							case "Falcon":
								 if (groundTicks == 1) {
									 event.setOnGround(true);
									 y =  + .08;
								 } else if (groundTicks > 1) {
									 event.setOnGround(false);
									 if (y < .01) {
										 y = 0;
										 event.setOnGround(true);
										 groundTicks = 0;
									 } else if (mc.thePlayer.ticksExisted % 2 == 0){
										 y *= .28f;
									 }
								 }

								 groundTicks++;
								 event.setPosY(mc.thePlayer.posY + y); 
							break;
							case "Float": 
								 if (groundTicks == 1) {
									 event.setOnGround(true);
									 y =  + .08;
								 } else if (groundTicks > 1) {
									 event.setOnGround(false);
									 if (y < .01) {
										 y = 0;
										 event.setOnGround(true);
										 groundTicks = 0;
									 } else if (mc.thePlayer.ticksExisted % 2 == 0){
										 y *= .28f;
									 }
								 }

								 groundTicks++;
								 event.setPosY(mc.thePlayer.posY + y); 
							break;
						}
					} else {
						waitDelay = 3;
					}
				} else {
					event.setPosY(mc.thePlayer.posY);
					waitDelay --;
				}
				
			} else {
				event.setPosY(mc.thePlayer.posY);
				waitDelay = 3;
			}
		}
	}

}
