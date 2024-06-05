package Ascii4UwUWareClient.Module.Modules.Misc;

import Ascii4UwUWareClient.Client;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public class Teams extends Module {

	public Teams() {
		super("Teams", new String[] {}, ModuleType.Misc);
	}

	public static boolean isOnSameTeam(Entity entity) {
		if (!Client.instance.getModuleManager().getModuleByClass(Teams.class).isEnabled())
			return false;
		if (Minecraft.getMinecraft().thePlayer.getDisplayName().getUnformattedText().startsWith("\247")) {
			if (Minecraft.getMinecraft().thePlayer.getDisplayName().getUnformattedText().length() <= 2
					|| entity.getDisplayName().getUnformattedText().length() <= 2) {
				return false;
			}
			if (Minecraft.getMinecraft().thePlayer.getDisplayName().getUnformattedText().substring(0, 2)
					.equals(entity.getDisplayName().getUnformattedText().substring(0, 2))) {
				return true;
			}
		}
		return false;
	}

}
