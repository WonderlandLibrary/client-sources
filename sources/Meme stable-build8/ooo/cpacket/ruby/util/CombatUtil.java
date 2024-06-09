package ooo.cpacket.ruby.util;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import ooo.cpacket.ruby.Ruby;
import ooo.cpacket.ruby.module.attack.AntiBot;

public class CombatUtil {
	static private final Minecraft mc = Minecraft.getMinecraft();
	// TODO improve stuff for aura
	public static boolean canBlock() {
		for (Entity o : (List<Entity>) mc.theWorld.loadedEntityList) {
			if (o instanceof EntityPlayer && !o.isDead && o != mc.thePlayer
					&& mc.thePlayer.getDistanceToEntity(
							o) <= 7) {
				if (Ruby.getRuby.getModuleManager().getModule(AntiBot.class).isEnabled() && !AntiBot.isEntityValidTarget(o)) {
					continue;
				}
				return true;
			}
		}
		return false;
	}
	
}
