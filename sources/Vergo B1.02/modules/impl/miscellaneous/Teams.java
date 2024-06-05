package xyz.vergoclient.modules.impl.miscellaneous;

import xyz.vergoclient.modules.Module;
import net.minecraft.entity.EntityLivingBase;

public class Teams extends Module {

	public Teams() {
		super("Teams", Category.MISCELLANEOUS);
	}
	
	public static boolean isOnSameTeam(EntityLivingBase entity) {
		if (entity.getTeam() != null && mc.thePlayer.getTeam() != null) {
			char c1 = entity.getDisplayName().getFormattedText().charAt(1);
			char c2 = mc.thePlayer.getDisplayName().getFormattedText().charAt(1);
			return c1 == c2;
		}
		return false;
	}
	
}
