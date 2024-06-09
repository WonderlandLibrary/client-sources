package chaos.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class EntityHelper {
		
	public static boolean isBot(Entity e) {
		if (e.isInvisible() && e.isDead && e != Minecraft.getMinecraft().thePlayer && !e.isEntityAlive() && ((EntityLivingBase)e).deathTime > 0 && ((EntityLivingBase)e).getHealth() < 0) {          
			return true;
		} else {
			return false;
		}		
	}
}
