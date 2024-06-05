package win.sightclient.utils.management;

import java.util.ArrayList;

import net.minecraft.util.EnumChatFormatting;

public class Changelog {

	private static ArrayList<String> changeLog = new ArrayList<String>();
	
	static {
		changeLog.add(EnumChatFormatting.BOLD + "- V2.00");
		changeLog.add("Recoded Sight...");
	}
	
	public static ArrayList<String> getChangelog() {
		return changeLog;
	}
}
