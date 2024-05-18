package com.kilo.alt.handler;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.GL11;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.kilo.util.Timer;
import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;

/**
 * @author Meckimp
 */
public class AccountManager {

	public static AccountManager instance = new AccountManager();
	protected static Minecraft mc = Minecraft.getMinecraft();
    private static final String AUTH_SERVER_ADDRESS = "authserver.mojang.com";

    private static final String SESSION_SERVER_ADDRESS = "session.minecraft.net";

    private static final URL MOJANG_STATUS_CHECKER_ADDRESS;

    private List<AccountInfo> accounts = new ArrayList();
    //private String lastAccount = mc.getSession().getUsername();
    
    private static Timer checkDelayTimer = new Timer();
    
    public List<AccountInfo> getAccounts(){
        return this.accounts;
    }

    public void addAccount(Session session, String accountName, String username, String password){
    	this.addAccount(session, accountName, username, password, true);
    }
    
    public void addAccount(Session session, String accountName, String username, String password, boolean save){
    	this.accounts.add(new AccountInfo(session, accountName, username, password));
    	if(save)
    		AccountsFile.instance.save();
    }

    public void removeAccount(int slot){
        this.accounts.remove(slot);
        AccountsFile.instance.save();
    }

    public void setAccount(int slot, Session session, String accountName, String username, String password){
        this.accounts.set(slot, new AccountInfo(session, accountName, username, password));
        AccountsFile.instance.save();
    }

    public void setLastAccount(String account){
     //   this.lastAccount = account;
    }

    public void reloadAccounts(){
        this.accounts.clear();
        AccountsFile.instance.load();
    }
    
    public static AccountManager getInstance() {
    	return instance;
    }
    
	// Render Stuff ---------------------------------------------------------------------------------
	
    public void drawFace(String username, int x, int y){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        ResourceLocation resourceLocation = AbstractClientPlayer.getLocationSkin(username);
        AbstractClientPlayer.getDownloadImageSkin(resourceLocation, username);
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        GlStateManager.enableBlend();
        Gui.drawModalRectWithCustomSizedTexture(x, y, 32.0F, 32.0F, 32, 32, 256.0F, 256.0F);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 160.0F, 32.0F, 32, 32, 256.0F, 256.0F);
        GlStateManager.disableBlend();
    }
    
    private static final Type STRING_MAP_TYPE = new TypeToken<Map<String, String>>() { }.getType();
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(STRING_MAP_TYPE, new MojangStatusMapTypeAdapter()).create();
    private static Map<String, String> status;

    private static class MojangStatusMapTypeAdapter implements JsonDeserializer<Map<String, String>> {
        public Map<String, String> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext ctx) {
            Map<String, String> result = new HashMap<String, String>();
            for (JsonElement element : json.getAsJsonArray()) {
                for (Map.Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()) {
                    result.put(entry.getKey(), entry.getValue().getAsString());
                    // break because there should only be one entry
                    break;
                }
            }
            return result;
        }
    }
    
    public static void checkServers() {
        if (checkDelayTimer.hasReach(30, TimeUnit.SECONDS)) {
            try {
                System.out.println("Checking Minecraft Servers: Check ID = " + (int) (Math.random() * 10000));
                String json = IOUtils.toString(MOJANG_STATUS_CHECKER_ADDRESS);
                status = GSON.fromJson(json, STRING_MAP_TYPE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            checkDelayTimer.reset();
        }
    }

    public static void drawServers() {
    	if(status != null){
    		
    		String authserver = status.get(AUTH_SERVER_ADDRESS).replace("green", "\247aOnline").replace("yellow", "\2476Slow").replace("red", "\2474Offline");
    		String sessonserver = status.get(SESSION_SERVER_ADDRESS).replace("green", "\247aOnline").replace("yellow", "\2476Slow").replace("red", "\2474Offline");
    		
	        GL11.glScaled(0.5, 0.5, 0.5);
	        
	        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("\2477> Authentication Service", 10, 28, 0xFFFFFF);
	        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("\2477| " + authserver, 165, 28, 0xFFFFFF);
	
	        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("\2477> Multiplayer Session Service", 10, 38, 0xFFFFFF);
	        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("\2477| " + sessonserver, 165, 38, 0xFFFFFF);
	        GL11.glScaled(2.0, 2.0, 2.0);
    	}
    }
    
    // Login Stuff ----------------------------------------------------------------------------------
    
	public void YggdrasilAuthenticator(int slot){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        String textDateFormat = "[" + dateFormat.format(date) + "] [Client thread/INFO]: ";
		if(this.getAccounts().get(slot).getSession().getToken().length() != 0 && isTokenValidated(this.getAccounts().get(slot).getSession())){
			this.getAccounts().get(slot).setError("");
			Minecraft.getMinecraft().setSession(new Session(this.getAccounts().get(slot).getAccountName(), this.getAccounts().get(slot).getSession().getPlayerID(), this.getAccounts().get(slot).getSession().getToken(), Session.Type.MOJANG.toString()));
			System.out.println(textDateFormat + "Logging in with session");
		}
		else if(!this.getAccounts().get(slot).getPassword().equals("") && this.getAccounts().get(slot).getPassword() != null) {
			String username = this.getAccounts().get(slot).getUsername();
			String password = this.getAccounts().get(slot).getPassword();
			Session authResponse = null;
			
			this.getAccounts().get(slot).setError("");
			try {
				authResponse = loginPassword(username, password);
	        } catch (AuthenticationException e){
	        	this.getAccounts().get(slot).setError(e.getMessage());
	        	System.out.println(textDateFormat + e.getMessage());
	        }
			
        	if (authResponse != null){
        		Minecraft.getMinecraft().setSession(authResponse);
        		this.accounts.get(slot).setSession(authResponse);
        	}
        }
	}
    
	public void YggdrasilAuthenticator(String username, String password){
		if(password != null && !password.equals("")) {
        	Session authResponse = null;
			try {
				authResponse = loginPassword(username, password);
			} catch (AuthenticationException e) {
		        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		        Date date = new Date();
		        String textDateFormat = "[" + dateFormat.format(date) + "] [Client thread/INFO]: ";
	        	System.out.println(textDateFormat + e.getMessage());
			}
        	if (authResponse != null)
        		Minecraft.getMinecraft().setSession(authResponse);
        }
	}
 
	private Session loginPassword(String username, String password) throws AuthenticationException {
        if ((username == null || username.length() == 0) || (password == null || password.length() == 0))
			return null;
        YggdrasilAuthenticationService authserver = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication post = (YggdrasilUserAuthentication)authserver.createUserAuthentication(Agent.MINECRAFT);
    	post.logOut();
        post.setUsername(username);
        post.setPassword(password);
        post.logIn();
        if(post.getSelectedProfile() == null)
        	return null;
        return new Session(post.getSelectedProfile().getName(), post.getSelectedProfile().getId().toString(), post.getAuthenticatedToken(), Session.Type.MOJANG.toString());
	}
	
	private static boolean isTokenValidated(Session session){
        boolean response = true;
        try {
    		String fakeServerID = UUID.randomUUID().toString().replace("-", "") + UUID.randomUUID().toString().replace("-", "").substring(0, 7);
    		//System.out.println(fakeServerID);
    		mc.getSessionService().joinServer(session.getProfile(), session.getToken(), fakeServerID);
		} catch (AuthenticationException e) {
			response = false;
		}
        return response;
	}

    static {
        URL workingUrl = null;
        try {
            workingUrl = new URL("http://status.mojang.com/check");
        } catch (MalformedURLException e) {
            System.err.println("Malformed URL found trying while to format Mojang server status checker URL string");
            e.printStackTrace();
        }
        MOJANG_STATUS_CHECKER_ADDRESS = workingUrl;
    }
    
    public class AccountInfo {
    	private Session session;
    	private String accountName;
    	private String username;
    	private String password;
    	private String error;
    	
    	public AccountInfo(Session session, String accountName, String username, String password){
    		this.session = session;
    		this.accountName = accountName;
    		this.username = username;
    		this.password = password;
    		this.error = "";
    	}

		public Session getSession() {
			return session;
		}

		public void setSession(Session session) {
			this.session = session;
		}

		public String getAccountName() {
			return accountName;
		}

		public void setAccountName(String accountName) {
			this.accountName = accountName;
		}

		public String getUsername() {
			return username;
		}

		public String getPassword() {
			return password;
		}

		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
		}
    }
    
}
