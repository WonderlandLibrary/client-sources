package xyz.vergoclient.security.account;

public class AccountUtils {
	
	public static Account account = null;
	
	private static boolean isBanned = false;
	
	public static boolean isLoggedIn() {
		return account != null;
	}
	
	public static void setBanned(boolean isBanned) {
		AccountUtils.isBanned = isBanned;
	}
	
	public static boolean isBanned() {
		return AccountUtils.isBanned;
	}
	
}
