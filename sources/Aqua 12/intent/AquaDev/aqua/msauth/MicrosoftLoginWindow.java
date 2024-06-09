// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.msauth;

import java.net.URLStreamHandler;
import net.minecraft.util.Session;
import net.minecraft.client.Minecraft;
import java.util.HashMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Map;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.net.Proxy;
import java.net.URL;
import sun.net.www.protocol.https.Handler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import javafx.scene.web.WebView;
import javafx.application.Platform;
import java.awt.Container;
import javafx.embed.swing.JFXPanel;
import java.awt.Component;
import javax.swing.JFrame;

public class MicrosoftLoginWindow extends JFrame
{
    private static final String CLIENT_ID = "00000000402b5328";
    private static final String MC_STORE_ITEM_1 = "product_minecraft";
    private static final String MC_STORE_ITEM_2 = "game_minecraft";
    public static EAuthenticationSteps authenticationStep;
    private static MicrosoftLoginWindow instance;
    
    public MicrosoftLoginWindow() {
        (MicrosoftLoginWindow.instance = this).setTitle("Connect with Microsoft");
        this.setSize(600, 700);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setContentPane((Container)new JFXPanel());
        Platform.runLater(this::loadScene);
        this.overrideWindow();
    }
    
    public static MicrosoftLoginWindow getInstance() {
        return MicrosoftLoginWindow.instance;
    }
    
    public void start() {
        Platform.runLater(this::loadScene);
    }
    
    private void loadScene() {
        MicrosoftLoginWindow.authenticationStep = EAuthenticationSteps.AUTHORIZE;
        final WebView webView = new WebView();
        final JFXPanel content = (JFXPanel)this.getContentPane();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                MicrosoftLoginWindow.this.reset();
                System.out.println("User closed window");
            }
        });
        webView.getEngine().load("https://login.live.com/oauth20_authorize.srf?client_id=00000000402b5328&response_type=code&redirect_uri=https://login.live.com/oauth20_desktop.srf&scope=XboxLive.signin%20offline_access&prompt=login");
        content.setScene(new Scene((Parent)webView, (double)this.getWidth(), (double)this.getHeight()));
        this.setVisible(true);
    }
    
    public void overrideWindow() {
        try {
            URL.setURLStreamHandlerFactory(protocol -> {
                if (!protocol.equals("https")) {
                    return null;
                }
                else {
                    return new Handler() {
                        @Override
                        protected URLConnection openConnection(final URL url, final Proxy proxy) throws IOException {
                            final HttpURLConnection connection = (HttpURLConnection)super.openConnection(url, proxy);
                            if (url.toString().contains("denied")) {
                                System.out.println("Denied Connection");
                                MicrosoftLoginWindow.this.reset();
                            }
                            else if (url.toString().contains("https://login.live.com/oauth20_desktop.srf?code") && MicrosoftLoginWindow.authenticationStep == EAuthenticationSteps.AUTHORIZE) {
                                MicrosoftLoginWindow.this.getMicrosoftToken(url);
                                MicrosoftLoginWindow.this.setVisible(false);
                            }
                            return connection;
                        }
                    };
                }
            });
        }
        catch (Error ignored) {
            System.out.println("Override already applied");
        }
    }
    
    private void getMicrosoftToken(final URL tokenURL) {
        System.out.println("Token: " + tokenURL.toString().split("=")[1]);
        MicrosoftLoginWindow.authenticationStep = EAuthenticationSteps.MS_TOKEN;
        final JsonObject response = URLUtils.readJSONFromURL("https://login.live.com/oauth20_token.srf?client_id=00000000402b5328&grant_type=authorization_code&redirect_uri=https://login.live.com/oauth20_desktop.srf&code=" + tokenURL.toString().split("=")[1], null);
        System.out.println("Access Token: " + response.get("access_token").getAsString());
        this.getXboxLiveToken(response.get("access_token").getAsString());
    }
    
    private void getXboxLiveToken(final String token) {
        MicrosoftLoginWindow.authenticationStep = EAuthenticationSteps.XBOXLIVE;
        final JsonObject properties = new JsonObject();
        properties.addProperty("AuthMethod", "RPS");
        properties.addProperty("SiteName", "user.auth.xboxlive.com");
        properties.addProperty("RpsTicket", "d=" + token);
        final JsonObject request = new JsonObject();
        request.add("Properties", properties);
        request.addProperty("RelyingParty", "http://auth.xboxlive.com");
        request.addProperty("TokenType", "JWT");
        final JsonObject response = URLUtils.postJson("https://user.auth.xboxlive.com/user/authenticate", request);
        System.out.println("Xbox Live Access Token: " + response.get("Token"));
        this.getXSTS(response.get("Token").getAsString());
    }
    
    private void getXSTS(final String token) {
        MicrosoftLoginWindow.authenticationStep = EAuthenticationSteps.XSTS;
        final JsonPrimitive jsonToken = new JsonPrimitive(token);
        final JsonArray userTokens = new JsonArray();
        userTokens.add(jsonToken);
        final JsonObject properties = new JsonObject();
        properties.addProperty("SandboxId", "RETAIL");
        properties.add("UserTokens", userTokens);
        final JsonObject request = new JsonObject();
        request.add("Properties", properties);
        request.addProperty("RelyingParty", "rp://api.minecraftservices.com/");
        request.addProperty("TokenType", "JWT");
        final JsonObject response = URLUtils.postJson("https://xsts.auth.xboxlive.com/xsts/authorize", request);
        if (response.has("XErr")) {
            final String asString = response.get("XErr").getAsString();
            switch (asString) {
                case "2148916233": {
                    System.out.println("This account doesn't have an Xbox account.");
                    break;
                }
                case "2148916235": {
                    System.out.println("Xbox isn't available in your country.");
                    break;
                }
                case "2148916238": {
                    System.out.println("The account is under 18 and must be added to a Family (https://start.ui.xboxlive.com/AddChildToFamily)");
                    break;
                }
            }
            this.reset();
        }
        else {
            System.out.println("XSTS Token: " + response.get("Token"));
            this.getMinecraftToken(response.getAsJsonObject("DisplayClaims").get("xui").getAsJsonArray().get(0).getAsJsonObject().get("uhs").getAsString(), response.get("Token").getAsString());
        }
    }
    
    private void getMinecraftToken(final String uhs, final String token) {
        MicrosoftLoginWindow.authenticationStep = EAuthenticationSteps.MCAUTH;
        final JsonObject request = new JsonObject();
        request.addProperty("identityToken", String.format("XBL3.0 x=%s;%s", uhs, token));
        final JsonObject response = URLUtils.postJson("https://api.minecraftservices.com/authentication/login_with_xbox", request);
        System.out.println("Minecraft Token: " + response.get("access_token"));
        this.checkMinecraftOwnership(response.get("access_token").getAsString());
    }
    
    private void checkMinecraftOwnership(final String token) {
        MicrosoftLoginWindow.authenticationStep = EAuthenticationSteps.CHECK_OWNERSHIP;
        final Map<String, String> headers = new HashMap<String, String>();
        boolean ownsMinecraft = true;
        headers.put("Authorization", "Bearer " + token);
        final JsonObject request = URLUtils.readJSONFromURL("https://api.minecraftservices.com/entitlements/mcstore", headers);
        for (int i = 0; i < request.get("items").getAsJsonArray().size(); ++i) {
            final String itemName = request.get("items").getAsJsonArray().get(i).getAsJsonObject().get("name").getAsString();
            if (itemName.equals("product_minecraft") || itemName.equals("game_minecraft")) {
                ownsMinecraft = true;
                break;
            }
        }
        if (!ownsMinecraft) {
            System.out.println("User doesn't own Minecraft");
        }
        else {
            this.getMinecraftProfile(token);
        }
    }
    
    private void getMinecraftProfile(final String token) {
        MicrosoftLoginWindow.authenticationStep = EAuthenticationSteps.MC_PROFILE;
        final Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "Bearer " + token);
        final JsonObject request = URLUtils.readJSONFromURL("https://api.minecraftservices.com/minecraft/profile", headers);
        System.out.println("Account Owner: " + request.get("name"));
        Minecraft.getMinecraft().session = new Session(request.get("name").getAsString(), request.get("id").getAsString(), token, "mojang");
        MicrosoftLoginWindow.authenticationStep = EAuthenticationSteps.DONE;
        this.reset();
    }
    
    private void reset() {
        this.setVisible(false);
        MicrosoftLoginWindow.authenticationStep = EAuthenticationSteps.IDLE;
    }
    
    static {
        MicrosoftLoginWindow.authenticationStep = EAuthenticationSteps.IDLE;
    }
    
    private enum EAuthenticationSteps
    {
        IDLE, 
        AUTHORIZE, 
        MS_TOKEN, 
        XBOXLIVE, 
        XSTS, 
        MCAUTH, 
        CHECK_OWNERSHIP, 
        MC_PROFILE, 
        DONE;
    }
}
