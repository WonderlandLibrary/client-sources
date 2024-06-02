/**
 * 
 */
package cafe.kagu.kagu.prot;

/**
 * @author DistastefulBannock
 *
 */
public class Note {
	
	public static final int OS_TYPE_UNKNOWN = 0x3621; // OS type is unknown
	public static final int BASIC_PROCESS_CHECK_TIMEOUT = 0x3622; // Triggered when something prevents the cheat from executing cli commands
	public static final int BASIC_PROCESS_CHECK_FLAGGED = 0x3623; // Triggered when there's a blacklisted process running
	public static final int LOADED_CLASSES_INVALID_PACKAGE_CHECK = 0x3624; // Triggered when there is a package loaded into the vm that shouldn't be loaded, this could be triggered by agents, jni injection, or other causes
	public static final int WINAUTH_APP_DISABLED = 0x3625; // Triggered when the winauth auth is disabled
	public static final int WINAUTH_REQUEST_FAILED = 0x3626; // Triggered when there is an issue while sending a request to the auth server
	public static final int WINAUTH_RESPONSE_TAMPERED = 0x3627; // Triggered when the response from the auth server is tampered with
	public static final int GUISCREEN_CHANGING_WITHOUT_LOGIN = 0x3628; // Triggered when the gui screen keeps changing away from the default blank one when the user isn't logged in
	public static final int WINAUTH_LOGIN_FAILED = 0x3629; // Triggered when the login is invalid
	public static final int WINAUTH_BLACKLISTED = 0x362A; // Triggered when the user is blacklisted
	
}
