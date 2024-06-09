// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.alt.design;

import java.io.InputStream;
import javax.net.ssl.HttpsURLConnection;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ProtocolException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import com.thealtening.api.retriever.AsynchronousDataRetriever;
import com.thealtening.api.retriever.BasicDataRetriever;
import net.minecraft.client.gui.GuiMainMenu;
import io.netty.util.internal.ThreadLocalRandom;
import intent.AquaDev.aqua.altloader.Api;
import intent.AquaDev.aqua.altloader.RedeemResponse;
import intent.AquaDev.aqua.altloader.Callback;
import java.net.URI;
import com.thealtening.api.TheAltening;
import intent.AquaDev.aqua.msauth.MicrosoftAuthentication;
import com.thealtening.auth.service.AlteningServiceType;
import com.mojang.authlib.exceptions.AuthenticationException;
import net.minecraft.util.Session;
import net.minecraft.client.Minecraft;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import java.net.Proxy;
import intent.AquaDev.aqua.Aqua;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.gui.ScaledResolution;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.input.Keyboard;
import com.google.gson.GsonBuilder;
import net.minecraft.client.gui.GuiTextField;
import com.google.gson.Gson;
import com.thealtening.auth.TheAlteningAuthentication;
import intent.AquaDev.aqua.altloader.AltLoader;
import net.minecraft.client.gui.GuiScreen;

public class AltManager extends GuiScreen
{
    private static final AltLoader altLoader;
    private static final String ALTENING_AUTH_SERVER = "http://authserver.thealtening.com/";
    private static final String ALTENING_SESSION_SERVER = "http://sessionserver.thealtening.com/";
    private static TheAlteningAuthentication alteningAuthentication;
    public GuiScreen parentScreen;
    private final Gson parser;
    private final String status = "";
    public GuiTextField email;
    public GuiTextField password;
    public static String emailName;
    public static String passwordName;
    public static int i;
    String apiKey;
    
    public AltManager(final GuiScreen event) {
        this.parser = new GsonBuilder().create();
        this.apiKey = "";
        this.parentScreen = event;
    }
    
    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.add(new GuiButton(13, 10, AltManager.height / 4 + 120 + 205, 68, 20, "TheAltening"));
        this.buttonList.add(new GuiButton(12, 10, AltManager.height / 4 + 120 + 180, 68, 20, "EasyMC"));
        this.buttonList.add(new GuiButton(11, 10, AltManager.height / 4 + 144 + 130, 68, 20, "Microsoft"));
        this.buttonList.add(new GuiButton(9, 10, AltManager.height / 4 + 96 + 130, 68, 20, "Add"));
        this.buttonList.add(new GuiButton(3, 10, AltManager.height / 4 + 96 + 96, 68, 20, "Remove"));
        this.buttonList.add(new GuiButton(1, 10, AltManager.height / 4 + 96 + 50, 68, 20, "Login"));
        this.buttonList.add(new GuiButton(5, 10, AltManager.height / 4 + 96 + 73, 68, 20, "Back"));
        this.buttonList.add(new GuiButton(2, 10, AltManager.height / 4 + 96 + 15, 68, 20, "Clipboard"));
        this.email = new GuiTextField(3, this.fontRendererObj, 10, 50, 200, 20);
        this.password = new GuiTextField(4, this.fontRendererObj, 10, 100, 200, 20);
    }
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        this.email.textboxKeyTyped(typedChar, keyCode);
        this.password.textboxKeyTyped(typedChar, keyCode);
        if (keyCode == 1) {
            this.mc.displayGuiScreen(this.parentScreen);
        }
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    public void updateScreen() {
        this.email.updateCursorCounter();
        this.password.updateCursorCounter();
    }
    
    public void mouseClicked(final int x, final int y, final int m) {
        this.email.mouseClicked(x, y, m);
        this.password.mouseClicked(x, y, m);
        try {
            super.mouseClicked(x, y, m);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final ScaledResolution s = new ScaledResolution(this.mc);
        if (!GL11.glGetString(7937).contains("AMD") && !GL11.glGetString(7937).contains("RX ")) {
            Aqua.INSTANCE.shaderBackgroundMM.renderShader();
        }
        else {
            this.drawDefaultBackground();
        }
        final int i1 = 0;
        Aqua.INSTANCE.comfortaa3.drawStringWithShadow("Email:", 10.0f, 36.0f, -1);
        Aqua.INSTANCE.comfortaa3.drawStringWithShadow("Password:", 10.0f, 86.0f, -1);
        Aqua.INSTANCE.comfortaa3.drawStringWithShadow("§3Logged in with §a" + this.mc.getSession().getUsername(), (float)(AltManager.width / 2), 20.0f, -1);
        this.email.drawTextBox();
        this.password.drawTextBox();
        final int boxY = 26;
        final int boxX = 50;
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    public boolean login1(final String email, final String password) {
        try {
            final YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
            final YggdrasilUserAuthentication auth = new YggdrasilUserAuthentication(service, Agent.MINECRAFT);
            auth.setUsername(email);
            auth.setPassword(password);
            if (auth.canLogIn()) {
                try {
                    auth.logIn();
                    Minecraft.getMinecraft().session = new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
                    return true;
                }
                catch (AuthenticationException authenticationException) {
                    return false;
                }
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }
    
    public void login(final String Email, final String password) {
        Aqua.INSTANCE.ircClient.setIngameName(Minecraft.getMinecraft().session.getUsername());
        AltManager.alteningAuthentication.updateService(AlteningServiceType.MOJANG);
        try {
            Minecraft.getMinecraft().session = Login.logIn(Email, password);
        }
        catch (Exception e) {
            final YggdrasilAuthenticationService authenticationService = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
            final YggdrasilUserAuthentication authentication = (YggdrasilUserAuthentication)authenticationService.createUserAuthentication(Agent.MINECRAFT);
            authentication.setUsername(Email);
            authentication.setPassword(password);
            try {
                authentication.logIn();
                Minecraft.getMinecraft().session = new Session(authentication.getSelectedProfile().getName(), authentication.getSelectedProfile().getId().toString(), authentication.getAuthenticatedToken(), "mojang");
            }
            catch (Exception e2) {
                Minecraft.getMinecraft().session = new Session(Email, "", "", "mojang");
            }
        }
    }
    
    public void loginAltening(final String Email, final String password) {
        Aqua.INSTANCE.ircClient.setIngameName(Minecraft.getMinecraft().session.getUsername());
        AltManager.alteningAuthentication.updateService(AlteningServiceType.THEALTENING);
        try {
            Minecraft.getMinecraft().session = Login.logIn(Email, password);
        }
        catch (Exception e) {
            final YggdrasilAuthenticationService authenticationService = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
            final YggdrasilUserAuthentication authentication = (YggdrasilUserAuthentication)authenticationService.createUserAuthentication(Agent.MINECRAFT);
            authentication.setUsername(Email);
            authentication.setPassword(password);
            try {
                authentication.logIn();
                Minecraft.getMinecraft().session = new Session(authentication.getSelectedProfile().getName(), authentication.getSelectedProfile().getId().toString(), authentication.getAuthenticatedToken(), "mojang");
            }
            catch (Exception e2) {
                Minecraft.getMinecraft().session = new Session(Email, "", "", "mojang");
            }
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) throws IOException {
        if (button.id == 11) {
            MicrosoftAuthentication.getInstance().loginWithPopUpWindow();
        }
        if (button.id == 13) {
            final String clipboard = GuiScreen.getClipboardString();
            if (!clipboard.contains("@alt")) {
                if (clipboard.contains("api-")) {
                    this.apiKey = clipboard;
                }
                if (this.apiKey != null) {
                    final BasicDataRetriever basicDataRetriever = TheAltening.newBasicRetriever(this.apiKey);
                    final AsynchronousDataRetriever asynchronousDataRetriever = basicDataRetriever.toAsync();
                    this.loginAltening(basicDataRetriever.getAccount().getToken(), "test");
                    return;
                }
            }
            this.loginAltening(GuiScreen.getClipboardString(), "test");
        }
        if (button.id == 10) {}
        if (button.id == 9 && !this.email.getText().isEmpty() && !this.password.getText().isEmpty()) {
            AltManager.emailName = this.email.getText();
            AltManager.passwordName = this.password.getText();
            AltsSaver.AltTypeList.add(new AltTypes(AltManager.emailName, AltManager.passwordName));
        }
        if (button.id == 12) {
            final String token = GuiScreen.getClipboardString();
            if (token.equals("")) {
                try {
                    final Class<?> oclass = Class.forName("java.awt.Desktop");
                    final Object object = oclass.getMethod("getDesktop", (Class<?>[])new Class[0]).invoke(null, new Object[0]);
                    oclass.getMethod("browse", URI.class).invoke(object, new URI("https://easymc.io/"));
                }
                catch (Throwable t) {}
            }
            Api.redeem(token, new Callback<Object>() {
                @Override
                public void done(final Object o) {
                    if (o instanceof String) {
                        return;
                    }
                    if (AltManager.altLoader.savedSession == null) {
                        AltManager.altLoader.savedSession = AltManager.this.mc.getSession();
                    }
                    final RedeemResponse response = AltManager.altLoader.easyMCSession = (RedeemResponse)o;
                    AltManager.altLoader.setEasyMCSession(response);
                }
            });
        }
        if (button.id == 1) {
            if (!this.email.getText().isEmpty() && !this.password.getText().isEmpty()) {
                this.login(this.email.getText().trim(), this.password.getText().trim());
                this.email.setText("");
                this.password.setText("");
            }
            else if (this.email.getText().isEmpty() && this.password.getText().isEmpty()) {
                this.password.setText("a");
                final StringBuilder randomName = new StringBuilder();
                final String alphabet = "1234567891012121314151638926704982";
                for (int random = ThreadLocalRandom.current().nextInt(2, 5), i = 0; i < random; ++i) {
                    randomName.append(alphabet.charAt(ThreadLocalRandom.current().nextInt(1, alphabet.length())));
                }
                this.email.setText("AquaClient" + (Object)randomName);
            }
        }
        if (button.id == 2) {
            final String Copy = GuiScreen.getClipboardString();
            final String[] WomboCombo = Copy.split(":");
            final String Email1 = WomboCombo[0];
            final String Passwort = WomboCombo[1];
            this.email.writeText(Email1);
            this.password.writeText(Passwort);
        }
        if (button.id == 26) {
            this.mc.displayGuiScreen(this.parentScreen);
        }
        if (button.id == 5) {
            this.mc.displayGuiScreen(new GuiMainMenu());
        }
    }
    
    public static boolean loginToken(final String token) {
        Aqua.INSTANCE.ircClient.setIngameName(Minecraft.getMinecraft().session.getUsername());
        URL url = null;
        try {
            url = new URL("https://api.minecraftservices.com/minecraft/profile/");
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection)url.openConnection();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
        conn.setRequestProperty("Authorization", "Bearer " + token);
        conn.setRequestProperty("Content-Type", "application/json");
        try {
            conn.setRequestMethod("GET");
        }
        catch (ProtocolException e3) {
            e3.printStackTrace();
        }
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        }
        catch (IOException e4) {
            e4.printStackTrace();
        }
        final StringBuilder response = new StringBuilder();
        while (true) {
            String output;
            try {
                if ((output = in.readLine()) == null) {
                    break;
                }
            }
            catch (IOException e5) {
                e5.printStackTrace();
                return false;
            }
            response.append(output);
        }
        final String jsonString = response.toString();
        final JsonObject obj = new JsonParser().parse(jsonString).getAsJsonObject();
        final String username = obj.get("name").getAsString();
        final String uuid = obj.get("id").getAsString();
        Minecraft.getMinecraft().session = new Session(username, uuid, token, "null");
        try {
            in.close();
        }
        catch (IOException e6) {
            e6.printStackTrace();
        }
        return true;
    }
    
    public Session generateAltening(final String apiKey) throws IOException {
        AltManager.alteningAuthentication.updateService(AlteningServiceType.THEALTENING);
        final URL url = new URL("https://api.thealtening.com/v2/generate?key=" + apiKey);
        final HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        final InputStream inputStream = connection.getInputStream();
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        final StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line).append("\n");
        }
        final JsonObject json = this.parser.fromJson(builder.toString(), JsonObject.class);
        final String token = json.get("token").getAsString();
        connection.disconnect();
        return this.getSession(token, Aqua.name);
    }
    
    public Session getSession(final String name, final String password) {
        Aqua.INSTANCE.ircClient.setIngameName(Minecraft.getMinecraft().session.getUsername());
        final YggdrasilAuthenticationService yggdrasilAuthenticationService = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        final YggdrasilUserAuthentication yggdrasilUserAuthentication = new YggdrasilUserAuthentication(yggdrasilAuthenticationService, Agent.MINECRAFT);
        yggdrasilUserAuthentication.setUsername(name);
        yggdrasilUserAuthentication.setPassword(password);
        try {
            yggdrasilUserAuthentication.logIn();
            System.out.println("Login successful!");
            return new Session(yggdrasilUserAuthentication.getSelectedProfile().getName(), yggdrasilUserAuthentication.getSelectedProfile().getId().toString(), yggdrasilUserAuthentication.getAuthenticatedToken(), "mojang");
        }
        catch (Exception e) {
            return null;
        }
    }
    
    static {
        altLoader = new AltLoader();
        AltManager.alteningAuthentication = TheAlteningAuthentication.mojang();
        AltManager.emailName = "";
        AltManager.passwordName = "";
        AltManager.i = 0;
    }
}
