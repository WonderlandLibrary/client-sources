package xyz.vergoclient.util.main;

import net.minecraft.util.Util;

public class OSUtil {
	
	public static boolean isWindows() {
		return Util.getOSType() == Util.EnumOS.WINDOWS;
	}
	
	public static boolean isMac() {
		return Util.getOSType() == Util.EnumOS.OSX;
	}
	
	public static boolean isLinux() {
		return Util.getOSType() == Util.EnumOS.LINUX;
	}
	
	public static boolean isSolaris() {
		return Util.getOSType() == Util.EnumOS.SOLARIS;
	}
	
	public static boolean isUnknown() {
		return Util.getOSType() == Util.EnumOS.UNKNOWN;
	}
	
}
