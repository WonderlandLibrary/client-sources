package dev.star.gui.altmanager;

import dev.star.Client;
import dev.star.gui.altmanager.microsoft.MicrosoftLogin;
import dev.star.gui.notifications.NotificationManager;
import dev.star.gui.notifications.NotificationType;
import dev.star.utils.Utils;
import dev.star.utils.misc.Multithreading;
import dev.star.utils.objects.TextField;
import lombok.Getter;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.Session;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AltManagerUtils implements Utils {

    @Getter
    private static List<Alt> alts = new ArrayList<>();

    public AltManagerUtils() {
    }

    public void login(TextField username, TextField password) {
        String usernameS;
        String passwordS;
        if (username.getText().contains(":")) {
            String[] combo = username.getText().split(":");
            usernameS = combo[0];
            passwordS = combo[1];
        } else {
            usernameS = username.getText();
            passwordS = password.getText();
        }

        if (usernameS.isEmpty() && passwordS.isEmpty()) return;

        loginWithString(usernameS, passwordS, false);
    }


    public void microsoftLoginAsync(String email, String password) {
        microsoftLoginAsync(null, email, password);
    }


    public void microsoftLoginAsync(Alt alt, String email, String password) {
        NotificationManager.post(NotificationType.INFO, "Alt Manager", "Opening browser to complete Microsoft authentication...", 12);
        if (alt == null) {
            alt = new Alt(email, password);
        }
        Alt finalAlt = alt;
        Multithreading.runAsync(() -> {
            CompletableFuture<Session> future = new CompletableFuture<>();
            MicrosoftLogin.getRefreshToken(refreshToken -> {
                if (refreshToken != null) {
                    MicrosoftLogin.LoginData login = MicrosoftLogin.login(refreshToken);
                    future.complete(new Session(login.username, login.uuid, login.mcToken, "microsoft"));
                }
            });
            Session auth = future.join();
            if (auth != null) {
                mc.session = auth;
                finalAlt.uuid = auth.getPlayerID();
                finalAlt.altType = Alt.AltType.MICROSOFT;
                finalAlt.username = auth.getUsername();
                if (auth.getUsername() == null) {
                    NotificationManager.post(NotificationType.WARNING, "Alt Manager", "Please set an username on your Minecraft account!", 12);
                }
                Alt.stage = 2;
                finalAlt.altState = Alt.AltState.LOGIN_SUCCESS;
                AltManagerUtils.getAlts().add(finalAlt);
                Client.INSTANCE.currentSessionAlt = finalAlt;
               // Star.INSTANCE.getAltManager().getAltPanel().refreshAlts();
            } else {
                Alt.stage = 1;
                finalAlt.altState = Alt.AltState.LOGIN_FAIL;
            }
        });

    }

    public void loginWithString(String username, String password, boolean microsoft) {
        for (Alt alt : alts) {
            if (alt.email.equals(username) && alt.password.equals(password)) {
                Alt.stage = 0;
                return;
            }
        }
        Alt alt = new Alt(username, password);
        alts.add(alt);
        Alt.stage = 0;
    }

    public void getHead(Alt alt) {
        if (alt.uuid == null || alt.head != null || alt.headTexture || alt.headTries > 5) return;
        Multithreading.runAsync(() -> {
            alt.headTries++;
            try {
                BufferedImage image = ImageIO.read(new URL("https://visage.surgeplay.com/bust/160/" + alt.uuid));
                alt.headTexture = true;
                // run on main thread for OpenGL context
                mc.addScheduledTask(() -> {
                    DynamicTexture texture = new DynamicTexture(image);
                    alt.head = mc.getTextureManager().getDynamicTextureLocation("HEAD-" + alt.uuid, texture);
                });
            } catch (IOException e) {
                alt.headTexture = false;
            }
        });
    }

}
