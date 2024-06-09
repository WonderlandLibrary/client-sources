package cafe.kagu.kagu.utils;
import net.minecraft.util.Util;

public class OSUtil {
	
	/**
	 * @return True if the user is using windows, otherwise false
	 */
	public static boolean isWindows() {
		return Util.getOSType() == Util.EnumOS.WINDOWS;
	}
	
	/**
	 * @return True if the user is using mac, otherwise false
	 */
	public static boolean isMac() {
		return Util.getOSType() == Util.EnumOS.OSX;
	}
	
	/**
	 * @return True if the user is using linux, otherwise false
	 */
	public static boolean isLinux() {
		return Util.getOSType() == Util.EnumOS.LINUX;
	}
	
	/**
	 * @return True if the user is using solaris, otherwise false
	 */
	public static boolean isSolaris() {
		return Util.getOSType() == Util.EnumOS.SOLARIS;
	}
	
	/**
	 * @return True if the user is using an unknown us, otherwise false
	 */
	public static boolean isUnknown() {
		return Util.getOSType() == Util.EnumOS.UNKNOWN;
	}
	
}