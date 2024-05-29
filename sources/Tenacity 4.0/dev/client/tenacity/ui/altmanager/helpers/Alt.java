package dev.client.tenacity.ui.altmanager.helpers;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import fr.litarvan.openauth.microsoft.model.response.MinecraftProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

import java.io.IOException;
import java.net.Proxy;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;

public class Alt {

    public static Minecraft mc = Minecraft.getMinecraft();
    public static int stage = -1, skinChecks;
    public static AltType currentLoginMethod = AltType.MOJANG;
    @Expose
    @SerializedName("uuid")
    public String uuid;
    @Expose
    @SerializedName("username")
    public String username;
    @Expose
    @SerializedName("email")
    public String email;
    @Expose
    @SerializedName("password")
    public String password;
    @Expose
    @SerializedName("altState")
    public AltState altState;
    @Expose
    @SerializedName("altType")
    public AltType altType;
    public boolean checkedSkin;

    public Alt(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Alt() {
    }

    private void login(boolean microsoft) {
        stage = 0;
        if (this.password.isEmpty()) {
            String uuid = UUID.randomUUID().toString();
            mc.session = new Session(this.email, uuid, "", "mojang");
            this.username = this.email;
            this.uuid = uuid;
            this.altState = AltState.LOGIN_SUCCESS;
            stage = 2;
            return;
        }
        Session auth = this.createSession(this.email, this.password, microsoft);
        if (auth == null) {
            stage = 1;
            altState = AltState.LOGIN_FAIL;
        } else {
            mc.session = auth;
            uuid = auth.getPlayerID();
            username = auth.getUsername();
            stage = 2;
            altState = AltState.LOGIN_SUCCESS;
            altType = currentLoginMethod;
        }
    }


    public void loginAsync(boolean microsoft) {
        new Thread(() -> {
            login(microsoft);
            new Thread(() -> {
                try {
                    Files.write(AltManagerUtils.altsFile.toPath(), new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create().toJson(AltManagerUtils.alts.toArray(new Alt[0])).getBytes(StandardCharsets.UTF_8));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }).start();
    }

    private Session createSession(String username, String password, boolean microsoft) {
        if (microsoft) {
            MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
            try {
                MicrosoftAuthResult result = authenticator.loginWithCredentials(username, password);
                MinecraftProfile profile = result.getProfile();
                return new Session(profile.getName(), profile.getId(), result.getAccessToken(), "microsoft");
            } catch (MicrosoftAuthenticationException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
            YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) service.createUserAuthentication(Agent.MINECRAFT);
            auth.setUsername(username);
            auth.setPassword(password);
            try {
                if (username.endsWith("@alt.com")) {
                    auth.setPassword("sample pass");
                }
                auth.logIn();
                return new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(),
                        auth.getAuthenticatedToken(), "mojang");
            } catch (AuthenticationException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public enum AltState {
        @Expose
        @SerializedName("1")
        LOGIN_FAIL,

        @Expose
        @SerializedName("2")
        LOGIN_SUCCESS
    }

    public enum AltType {
        @Expose
        @SerializedName("1")
        MICROSOFT,

        @Expose
        @SerializedName("2")
        MOJANG
    }
}
