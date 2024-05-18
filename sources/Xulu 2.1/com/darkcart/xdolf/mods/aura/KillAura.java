package com.darkcart.xdolf.mods.aura;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.mods.Hacks;
import com.darkcart.xdolf.util.Category;
import com.darkcart.xdolf.util.Value;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;

public class KillAura extends Module {
	 public static EntityLivingBase target;
	public static Value auraRange = new Value("Aura Range");
	
	public KillAura() {
		super("killAura", "Delay", "Automatically attacks players or mobs.", Keyboard.KEY_R, 0xFFFFFF, Category.COMBAT);
	}
	
	private float yaw, pitch, yawHead;
	
	@Override
	public void beforeUpdate(EntityPlayerSP player) {
		try {
			this.yaw = player.rotationYaw;
			this.pitch = player.rotationPitch;
			this.yawHead = player.rotationYawHead;
		}catch(Exception ex){ /* stop crash when leaving server with aura on */ }
	}
	
	/**
	 * Wrapper.faceEntity(this.target); = Sends packets to face the player/mob/whatever.
	 * @param player
	 */
	
	@Override
	public void onUpdate(EntityPlayerSP player) {
		delay(player);
	}

		
	
	public void delay(EntityPlayerSP player) {
		if(auraRange.getValue() < 3)
		{
			auraRange.setValue(4F);
		}
		if(isEnabled())
		{
			try {
				if(Wrapper.getCooldown() >= 1)
				{
					for(Object o: Wrapper.getWorld().loadedEntityList)
					{
						if(!Hacks.findMod(AuraPlayer.class).isEnabled() || !Hacks.findMod(AuraMob.class).isEnabled())
						{
							if(Hacks.findMod(AuraPlayer.class).isEnabled())
							{
								Entity e = (Entity) o;
								boolean checks = !Wrapper.getFriends().isFriend((e).getName()) && !(e instanceof EntityPlayerSP) && (e instanceof EntityPlayer) && player.getDistanceToEntity(e) <= auraRange.getValue() && ((EntityLivingBase)e).getHealth() > 0;
								if(checks) 
								{
									player.setSprinting(false);
									faceEntity(e);
									Wrapper.getMinecraft().playerController.attackEntity(player, e);
									player.swingArm(EnumHand.MAIN_HAND);
									Wrapper.faceEntity(target);
									player.setSprinting(false);
									break;
								}
							}else
							if(Hacks.findMod(AuraMob.class).isEnabled())
							{
								Entity e = (Entity) o;
								boolean checks = !(e instanceof EntityPlayerSP) && !(e instanceof EntityPlayer) && (e instanceof EntityLivingBase) && player.getDistanceToEntity(e) <= auraRange.getValue() && ((EntityLivingBase)e).getHealth() > 0;
								if(checks) 
								{
									player.setSprinting(false);
									faceEntity(e);
									Wrapper.getMinecraft().playerController.attackEntity(player, e);
									player.swingArm(EnumHand.MAIN_HAND);
									Wrapper.faceEntity(target);
									break;
								}
							}
						}else
						if(Hacks.findMod(AuraPlayer.class).isEnabled() && Hacks.findMod(AuraMob.class).isEnabled())
						{
							Entity e = (Entity) o;
							boolean checks = !(e instanceof EntityPlayerSP) && (e instanceof EntityLivingBase) && player.getDistanceToEntity(e) <= auraRange.getValue() && ((EntityLivingBase)e).getHealth() > 0;
				
							if(e instanceof EntityPlayer) 
							{
								EntityPlayer ep = (EntityPlayer) o;
								checks = checks && !Wrapper.getFriends().isFriend(ep.getName());
								checks = checks && !ep.isPotionActive(Potion.getPotionById(14));
							}
							if(checks) 
							{
								player.setSprinting(false);
								faceEntity(e);
								Wrapper.getMinecraft().playerController.attackEntity(player, e);
								player.swingArm(EnumHand.MAIN_HAND);
								Wrapper.faceEntity(target); 
								break;
							}
						}
					}
				}
			}catch(Exception ex){}
		}
	}

	@Override
	public void afterUpdate(EntityPlayerSP player) {
		try {
			player.rotationYaw = this.yaw;
			player.rotationPitch = this.pitch;
			player.rotationYawHead = this.yawHead;
		}catch(Exception ex){}
	}
	
	public void faceEntity(Entity entity)
    {
		double x = entity.posX - Wrapper.getPlayer().posX;
		double z = entity.posZ - Wrapper.getPlayer().posZ;
		double y = entity.posY + (entity.getEyeHeight()/1.4D) - Wrapper.getPlayer().posY + (Wrapper.getPlayer().getEyeHeight()/1.4D);
		double helper = MathHelper.sqrt(x * x + z * z);

		float newYaw = (float)((Math.toDegrees(-Math.atan(x / z))));
		float newPitch = (float)-Math.toDegrees(Math.atan(y / helper));

		if(z < 0 && x < 0) { newYaw = (float)(90D + Math.toDegrees(Math.atan(z / x))); }
		else if(z < 0 && x > 0) { newYaw = (float)(-90D + Math.toDegrees(Math.atan(z / x))); }

		Wrapper.getPlayer().rotationYaw = newYaw; 
		Wrapper.getPlayer().rotationPitch = newPitch;
		Wrapper.getPlayer().rotationYawHead = newPitch;
    }
}