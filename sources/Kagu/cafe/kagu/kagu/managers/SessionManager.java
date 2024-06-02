/**
 * 
 */
package cafe.kagu.kagu.managers;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;
import org.yaml.snakeyaml.Yaml;

import com.mojang.authlib.Agent;
import com.mojang.authlib.AuthenticationService;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.UserType;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.util.UUIDTypeAdapter;

import cafe.kagu.kagu.ui.gui.GuiAltManager;
import cafe.kagu.kagu.ui.gui.GuiAltManager.MicrosoftAlt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.util.Session;
import net.minecraft.util.Session.Type;
import spark.Spark;

/**
 * @author lavaflowglow
 *
 */
public class SessionManager {
	
	private static UserAuthentication userAuthentication;
	
	static {
		AuthenticationService authenticationService = new YggdrasilAuthenticationService(Minecraft.getMinecraft().getProxy(), "");
		authenticationService.createMinecraftSessionService();
		userAuthentication = authenticationService.createUserAuthentication(Agent.MINECRAFT);
	}
	
	/**
	 * Called when the client starts
	 */
	public static void start() {
		// Start the webserver to listen for requests on port 3621, the webserver will be used for microsoft auth
		Spark.port(3621);
		
		// Used for microsoft auth
		Spark.get("/", (request, response) -> {
			
			// Get all the 
			String code = request.queryMap("code").value();
			Map<String, String> authInfo = new Yaml().load(FileManager.readStringFromFile(FileManager.MICROSOFT_CONFIGURATION));
			String clientId = authInfo.get("Azure Client Id");
			String secret = authInfo.get("Azure Secret");
			
			// Send the request
			String tokenResponse = "";
			try {
				tokenResponse = getAuthTokenResponseFromAuthCode(clientId, secret, code, false);
			} catch (Exception e) {
				return "Something went wrong, please try again";
			}
			
			// Create the microsoft alt and add it to the alt manager
			JSONObject jsonResponse = new JSONObject(tokenResponse);
			MicrosoftAlt alt = new MicrosoftAlt();
			alt.setUsername("Unknown account");
			alt.setRefreshToken(jsonResponse.getString("refresh_token"));
			alt.setMinecraftAccessToken(jsonResponse.getString("access_token"));
			GuiAltManager.getAlts().add(0, alt);
			GuiAltManager.saveAlts();
			
			// Temp login line
//			loginToMicrsoftAccount(jsonResponse.getString("access_token"));
			
			return "Added microsoft account to the alt manager, you may close this tab";
		});
	}
	
	/**
	 * Logs into a permium minecraft account
	 * @param email The accounts email
	 * @param password The accounts password
	 * @return true if there were no issues with authorization, otherwise false
	 */
	public static boolean loginPremium(String email, String password) {
		try {
			userAuthentication.logOut();
			userAuthentication.setUsername(email);
			userAuthentication.setPassword(password);
			userAuthentication.logIn();
			Minecraft.getMinecraft().setSession(new Session(userAuthentication.getSelectedProfile().getName(), UUIDTypeAdapter.fromUUID(userAuthentication.getSelectedProfile().getId()), userAuthentication.getAuthenticatedToken(), userAuthentication.getUserType().getName()));
			return true;
		} catch (AuthenticationException e) {
			return false;
		}
	}
	
	/**
	 * Logs into a cracked minecraft account
	 * @param name The name to use
	 */
	public static void loginCracked(String name) {
		Minecraft.getMinecraft().setSession(new Session(name, name, "0", Type.LEGACY.toString()));
	}
	
	/**
	 * Starts microsoft authentication
	 */
	public static void startMicrosoftAuth() {
		Map<String, String> authInfo = new Yaml().load(FileManager.readStringFromFile(FileManager.MICROSOFT_CONFIGURATION));
		String clientId = authInfo.get("Azure Client Id");
		String url = "https://login.live.com/oauth20_authorize.srf?client_id=" + clientId + "&prompt=select_account&response_type=code&redirect_uri=http://localhost:3621&scope=XboxLive.signin%20offline_access";
		try {
			Minecraft.getMinecraft().currentScreen.setClickedLinkURI(new URI(url));
		}catch (Exception e) {
			return;
		}
		Minecraft.getMinecraft().displayGuiScreen(new GuiConfirmOpenLink(Minecraft.getMinecraft().currentScreen, url, 31102009, false));
	}
	
	/**
	 * Refreshes an alt's access token with the refresh token
	 * @param alt The alt to refresh
	 */
	public static void refreshAltAccessToken(MicrosoftAlt alt) {
		Map<String, String> authInfo = new Yaml().load(FileManager.readStringFromFile(FileManager.MICROSOFT_CONFIGURATION));
		String clientId = authInfo.get("Azure Client Id");
		String secret = authInfo.get("Azure Secret");
		String tokenResponse = "";
		try {
			tokenResponse = getAuthTokenResponseFromAuthCode(clientId, secret, alt.getRefreshToken(), true);
		} catch (Exception e) {
			
		}
		
		// Refresh access token
		JSONObject jsonResponse = new JSONObject(tokenResponse);
		System.out.println(jsonResponse.toString());
		alt.setMinecraftAccessToken(jsonResponse.getString("access_token"));
		GuiAltManager.saveAlts();
	}
	
	/**
	 * @param clientId The azure client id
	 * @param secret The azure secret
	 * @param code The code or refresh token
	 * @param refreshToken Whether the code is a refresh token or not
	 * @return The response
	 * @throws Exception If something went wrong
	 */
	private static String getAuthTokenResponseFromAuthCode(String clientId, String secret, String code, boolean refreshToken) throws Exception {
		HttpPost post = new HttpPost("https://login.live.com/oauth20_token.srf");
		String body = "client_id=" + clientId + "&client_secret=" + secret + (refreshToken ? "&refresh_token=" : "&code=") + code + "&grant_type=" + (refreshToken ? "refresh_token" : "authorization_code") + "&redirect_uri=http://localhost:3621";
		StringEntity entity = new StringEntity(body);
		post.setEntity(entity);
		post.setHeader("Content-Type", "application/x-www-form-urlencoded");
		return NetworkManager.getInstance().sendPost(post);
	}
	
	/**
	 * @param accessToken The access token
	 */
	public static void loginToMicrsoftAccount(String accessToken) throws Exception {
		
		JSONObject xblResponse = null;
		{ // Access token to xbl token
			HttpPost post = new HttpPost("https://user.auth.xboxlive.com/user/authenticate");
			String body = "{\r\n"
					+ "    \"Properties\": {\r\n"
					+ "        \"AuthMethod\": \"RPS\",\r\n"
					+ "        \"SiteName\": \"user.auth.xboxlive.com\",\r\n"
					+ "        \"RpsTicket\": \"d=" + accessToken + "\"\r\n"
					+ "    },\r\n"
					+ "    \"RelyingParty\": \"http://auth.xboxlive.com\",\r\n"
					+ "    \"TokenType\": \"JWT\"\r\n"
					+ " }";
			StringEntity entity = new StringEntity(body);
			post.setEntity(entity);
			post.setHeader("Content-Type", "application/json");
			post.setHeader("Accept", "application/json");
			xblResponse = new JSONObject(NetworkManager.getInstance().sendPost(post));
		}
		
		String xblToken = xblResponse.getString("Token");
		String userhash = new JSONObject(xblResponse.getJSONObject("DisplayClaims").getJSONArray("xui").get(0).toString()).getString("uhs");
		
		JSONObject xstsResponse = null;
		{ // Xbl token to xsts security token
			HttpPost post = new HttpPost("https://xsts.auth.xboxlive.com/xsts/authorize");
			String body = "{\r\n"
					+ "    \"Properties\": {\r\n"
					+ "        \"SandboxId\": \"RETAIL\",\r\n"
					+ "        \"UserTokens\": [\r\n"
					+ "            \"" + xblToken + "\"\r\n"
					+ "        ]\r\n"
					+ "    },\r\n"
					+ "    \"RelyingParty\": \"rp://api.minecraftservices.com/\",\r\n"
					+ "    \"TokenType\": \"JWT\"\r\n"
					+ " }";
			StringEntity entity = new StringEntity(body);
			post.setEntity(entity);
			post.setHeader("Content-Type", "application/json");
			post.setHeader("Accept", "application/json");
			xstsResponse = new JSONObject(NetworkManager.getInstance().sendPost(post));
		}
		
		// Handle error for xsts here later
		
		String xstsToken = xstsResponse.getString("Token");
		
		JSONObject mcResponse = null;
		{ // Get mc access token from xsts token and user hash
			HttpPost post = new HttpPost("https://api.minecraftservices.com/authentication/login_with_xbox");
			String body = " {\r\n"
					+ "    \"identityToken\": \"XBL3.0 x=" + userhash + ";" + xstsToken + "\"\r\n"
					+ " }";
			StringEntity entity = new StringEntity(body);
			post.setEntity(entity);
			post.setHeader("Content-Type", "application/json");
			post.setHeader("Accept", "application/json");
			mcResponse = new JSONObject(NetworkManager.getInstance().sendPost(post));
		}
		String mcAccessToken = mcResponse.getString("access_token");
		
		JSONObject profileResponse = null;
		{ // Get mc profile from access token
			HttpGet get = new HttpGet("https://api.minecraftservices.com/minecraft/profile");
			get.setHeader("Authorization", "Bearer " + mcAccessToken);
			profileResponse = new JSONObject(NetworkManager.getInstance().sendGet(get));
		}
		
		String ign = profileResponse.getString("name");
		String uuid = profileResponse.getString("id").replaceFirst(
				"(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5"); // Funny formatting part taken from github
		Minecraft.getMinecraft().setSession(new Session(ign, UUIDTypeAdapter.fromUUID(UUID.fromString(uuid)), mcAccessToken, UserType.MOJANG.getName()));
		
	}
	
}
