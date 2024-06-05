package me.superskidder.lune.modules.combat;

import me.superskidder.lune.Lune;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.manager.event.EventTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class Teams extends Mod {

	public Teams() {
		super("Teams", ModCategory.Combat,"Make killAura doesn't to hit your team's players");

	}


	@EventTarget
	private void OnFriend() {
	}

	public static boolean isOnSameTeam(Entity entity) {
		if (!Lune.moduleManager.getModByClass(Teams.class).getState()) {
			return false;
		}
		if (mc.thePlayer.getDisplayName().getUnformattedText().startsWith("\u00a7")) {
			if (mc.thePlayer.getDisplayName().getUnformattedText().length() <= 2
					|| entity.getDisplayName().getUnformattedText().length() <= 2) {
				return false;
			}
			if (mc.thePlayer.getDisplayName().getUnformattedText().substring(0, 2)
					.equals(entity.getDisplayName().getUnformattedText().substring(0, 2))) {
				return true;
			}

		}
		return false;
	}

	public static boolean isTeam(EntityPlayer e, EntityPlayer e2) {
		return e.getDisplayName().getFormattedText().contains("§" + isOnSameTeam(e))
				&& e2.getDisplayName().getFormattedText().contains("§" + isOnSameTeam(e));
	}

}
