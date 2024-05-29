package dev.client.tenacity.ui.oldaltmanager.backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.client.tenacity.Tenacity;
import dev.client.tenacity.ui.notifications.NotificationManager;
import dev.client.tenacity.ui.notifications.NotificationType;
import dev.client.tenacity.utils.objects.PasswordField;
import dev.utils.time.TimerUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AltManagerUtils {
    public static List<Alt> alts = new ArrayList<>();
    public static File altsFile = new File(Tenacity.DIRECTORY, "Alts.json");

    public AltManagerUtils() {
        if(!altsFile.exists()) {
            altsFile.mkdirs();
        }
        try {
            byte[] content = Files.readAllBytes(altsFile.toPath());
            alts = new ArrayList<>(Arrays.asList(new Gson().fromJson(new String(content), Alt[].class)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeAltsToFile(TimerUtil timerUtil) {
        if (timerUtil.hasTimeElapsed(15000, true)) {
            new Thread(() -> {
                try {
                    Files.write(altsFile.toPath(), new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create().toJson(alts.toArray(new Alt[0])).getBytes(StandardCharsets.UTF_8));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public int getAmountOfAlts() {
        return alts.size();
    }

    public void deleteAlt(Alt alt) {
        if (alt != null) {
            alts.remove(alt);
            NotificationManager.post(NotificationType.SUCCESS, "Alt Manager", "Deleted " + alt.username + "!");
            try {
                Files.write(altsFile.toPath(), new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create().toJson(alts.toArray(new Alt[0])).getBytes(StandardCharsets.UTF_8));
                //Show success message
            } catch (IOException e) {
                e.printStackTrace();
                //    Notification.post(NotificationType.WARNING, "Failed to save", "Failed to save alt list due to an IOException");
            }
        }
    }

    public void login(PasswordField username, PasswordField password) {
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

        loginWithString(usernameS, passwordS, Alt.currentLoginMethod == Alt.AltType.MICROSOFT);
    }

    public void loginWithString(String username, String password, boolean microsoft) {
        for (Alt alt : alts) {
            if (alt.email.equals(username) && alt.password.equals(password)) {
                Alt.stage = 0;
                alt.loginAsync(microsoft);
                return;
            }
        }
        Alt alt = new Alt(username, password);
        alts.add(alt);
        Alt.stage = 0;
        alt.loginAsync(microsoft);
    }
}
