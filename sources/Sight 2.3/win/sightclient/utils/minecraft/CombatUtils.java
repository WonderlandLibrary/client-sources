package win.sightclient.utils.minecraft;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import win.sightclient.Sight;
import win.sightclient.module.Module;
import win.sightclient.module.combat.AntiBot;

public class CombatUtils {

	protected static Minecraft mc = Minecraft.getMinecraft();
	
	private static Module teams;
	private static Module players;
	private static Module invisibles;
	private static Module mobs;
	private static Module animals;
	private static Module nofriends;
	private static Module others;
	private static Module ignoredead;
	
	private static void init() {
		teams = Sight.instance.mm.getModuleByName("Teams");
		players = Sight.instance.mm.getModuleByName("players");
		invisibles = Sight.instance.mm.getModuleByName("invisibles");
		mobs = Sight.instance.mm.getModuleByName("mobs");
		animals = Sight.instance.mm.getModuleByName("animals");
		nofriends = Sight.instance.mm.getModuleByName("nofriends");
		others = Sight.instance.mm.getModuleByName("others");
		ignoredead = Sight.instance.mm.getModuleByName("IgnoreDead");
	}
	
	public static boolean isValid(Entity entity, boolean combat) {
		if (teams == null || players == null || invisibles == null || mobs == null || animals == null || nofriends == null || others == null) {
			init();
		}
		if (entity.getName().equals("AntiCheat")) {
			return false;
		}
		if (!(entity instanceof EntityLivingBase) || (entity == mc.thePlayer && combat) || entity instanceof EntityArmorStand) {
			return false;
		}
		
		if (entity instanceof EntityPlayer && AntiBot.isBot((EntityPlayer) entity)) {
			return false;
		}
		
		if (!entity.isEntityAlive() && !ignoredead.isToggled()) {
			return false;
		}
		
		if (entity instanceof EntityPlayer && !players.isToggled()) {
			return false;
		} else if (entity instanceof EntityPlayer && players.isToggled() && entity.isInvisible() && !invisibles.isToggled()) {
			return false;
		} else if (entity instanceof EntityMob && !mobs.isToggled()) {
			return false;
		} else if (entity instanceof EntityAnimal && !animals.isToggled()) {
			return false;
		} else if (!(entity instanceof EntityAnimal) && !(entity instanceof EntityMob) && !(entity instanceof EntityPlayer) && !others.isToggled()) {
			return false;
		} else if (combat && entity instanceof EntityPlayer && players.isToggled() && teams.isToggled()
				&& (mc.thePlayer.getDisplayName().getFormattedText().startsWith("�") && entity.getDisplayName().getFormattedText().startsWith("�")
						&& entity.getDisplayName().getFormattedText().charAt(1) == mc.thePlayer.getDisplayName().getFormattedText().charAt(1))) {
			return false;
		} /*else if (entity instanceof EntityPlayer && Sight.instance.friendManager.isFriend(entity.getName()) && !nofriends.isToggled()) {
			return false;
		}*/
		
		return true;
	}
}
