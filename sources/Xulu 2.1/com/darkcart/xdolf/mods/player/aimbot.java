package com.darkcart.xdolf.mods.player;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;

public class aimbot extends Module {
	public aimbot() {
		super("aimbot", "Broken", "Locks the Player Camera at the closest Player.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.PLAYER);
	}
	@Override
	  public void onUpdate(EntityPlayerSP entity){
	  if(this.isEnabled()){
		  for(Object o: Minecraft.world.playerEntities) {
				if(o instanceof EntityPlayer) {
					EntityPlayer e = (EntityPlayer) o;
					boolean checks = !(e instanceof EntityPlayer) && Minecraft.player.getDistanceToEntity(e) <= 4D && Minecraft.player.canEntityBeSeen(e) && !e.isDead;
					if(checks) {
						faceEntity(e);
					   }
					}
				}
			}
		}
		  public void faceEntity(Entity entity)
		    {
				double x = entity.posX - Minecraft.player.posX;
				double z = entity.posZ - Minecraft.player.posZ;
				double y = entity.posY + (entity.getEyeHeight()/1.4D) - Minecraft.player.posY + (Minecraft.player.getEyeHeight()/1.4D);
				double helper = MathHelper.sqrt_double(x * x + z * z);

				float newYaw = (float)((Math.toDegrees(-Math.atan(x / z))));
				float newPitch = (float)-Math.toDegrees(Math.atan(y / helper));

				if(z < 0 && x < 0) { newYaw = (float)(90D + Math.toDegrees(Math.atan(z / x))); }
				else if(z < 0 && x > 0) { newYaw = (float)(-90D + Math.toDegrees(Math.atan(z / x))); }

				Minecraft.player.rotationYaw = newYaw; 
				Minecraft.player.rotationPitch = newPitch;
				Minecraft.player.rotationYawHead = newPitch;
		    }
		}