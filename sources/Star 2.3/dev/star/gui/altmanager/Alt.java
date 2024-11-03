package dev.star.gui.altmanager;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import dev.star.Client;
import dev.star.gui.altmanager.microsoft.MicrosoftLogin;
import dev.star.gui.notifications.NotificationManager;
import dev.star.gui.notifications.NotificationType;
import dev.star.utils.font.FontUtil;
import dev.star.utils.server.ban.HypixelBan;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class Alt {

    public static Minecraft mc = Minecraft.getMinecraft();
    public static int stage = -1;
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
    @Expose
    @SerializedName("hypixelBan")
    public HypixelBan hypixelBan;
    @Expose
    @SerializedName("favorite")
    public boolean favorite;

    public ResourceLocation head;
    public boolean headTexture;
    public int headTries;

    public Alt(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Alt() {
    }

    private void login(boolean microsoft) {
        stage = 0;
        if ((!microsoft && this.password.isEmpty())) {
            String uuid = UUID.randomUUID().toString();
            mc.session = new Session(this.email, uuid, "", "mojang");
            this.username = this.email;
            this.uuid = uuid;
            this.altState = AltState.LOGIN_SUCCESS;
            altType = AltType.CRACKED;
            stage = 2;
            Client.INSTANCE.currentSessionAlt = this;
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
            Client.INSTANCE.currentSessionAlt = this;
        }
    }

    private Session createSession(String username, String password, boolean microsoft) {
        NotificationManager.post(NotificationType.INFO, "Alt Manager", "Opening browser to complete Microsoft authentication...", 12);
        CompletableFuture<Session> future = new CompletableFuture<>();
        MicrosoftLogin.getRefreshToken(refreshToken -> {
            if (refreshToken != null) {
                System.out.println("Refresh token: " + refreshToken);
                MicrosoftLogin.LoginData login = MicrosoftLogin.login(refreshToken);

                if (login != null) {
                    if (login.username != null) {
                        currentLoginMethod = AltType.MICROSOFT;
                        future.complete(new Session(login.username, login.uuid, login.mcToken, "microsoft"));
                    } else {
                        NotificationManager.post(NotificationType.DISABLE, "Alt Manager", "Failed to log in ", 12);
                    }
                }
                else {
                    NotificationManager.post(NotificationType.DISABLE, "Alt Manager", "Failed to log in ", 12);
                }
            }
            else {
                NotificationManager.post(NotificationType.DISABLE, "Alt Manager", "Failed to log in ", 12);
            }
        });
        return future.join();
    }

    public String getType() {
        return altType == null ? "Not logged in" : altType.getName();
    }

    @Override
    public String toString() {
        return "Alt{" +
                "uuid='" + uuid + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", altState=" + altState +
                ", altType=" + altType +
                ", headTries=" + headTries +
                '}';
    }

    @Getter
    @RequiredArgsConstructor
    public enum AltState {
        @Expose
        @SerializedName("1")
        LOGIN_FAIL(FontUtil.XMARK),

        @Expose
        @SerializedName("2")
        LOGIN_SUCCESS(FontUtil.CHECKMARK);
        private final String icon;
    }

    @Getter
    @RequiredArgsConstructor
    public enum AltType {
        @Expose
        @SerializedName("1")
        MICROSOFT("Microsoft"),

        @Expose
        @SerializedName("2")
        MOJANG("Mojang"),

        @Expose
        @SerializedName("3")
        CRACKED("Cracked");
        private final String name;
    }
}
