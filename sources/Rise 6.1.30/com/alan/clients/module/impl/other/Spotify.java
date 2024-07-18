package com.alan.clients.module.impl.other;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.other.TickEvent;
import com.alan.clients.event.impl.render.Render2DEvent;
import com.alan.clients.font.Fonts;
import com.alan.clients.font.Weight;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.DevelopmentFeature;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.util.vector.Vector2d;
import com.alan.clients.value.impl.DragValue;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.Normalizer;
import java.util.Scanner;

import static com.alan.clients.layer.Layers.*;
@DevelopmentFeature
@ModuleInfo(aliases = {"module.other.spotify.name"}, description = "module.other.spotify.description", category = Category.RENDER)
public class Spotify extends Module {
    public final DragValue positionValue = new DragValue("Position", this, new Vector2d(200, 200));
    public String song = "Loading...";
    public String artist = "Loading...";
    public String auth_token;
    public String client_id;
    public String client_secret;
    public String progress = "1:43";
    public String length = "2:56";
    public int duration_ms = 1;
    public int progress_ms = 1;
    public int ticks = 0;

    public static boolean updatedInfo = false;

    @EventLink
    public final Listener<Render2DEvent> onRender2D = event -> {
        progress = (progress_ms / 1000 / 60) + ":";
        if ((progress_ms / 1000 % 60) < 10) {
            progress += "0" + (int)(progress_ms / 1000 % 60);
        } else {
            progress += (int)(progress_ms / 1000 % 60);
        }
        length = (duration_ms / 1000 / 60) + ":";
        if ((duration_ms / 1000 % 60) < 10) {
            length += "0" + (duration_ms / 1000 % 60);
        } else {
            length += (int)(duration_ms / 1000 % 60);
        }
        positionValue.setScale(new Vector2d(180, 65));

        final double x = positionValue.position.getX() + 65;

        final Color backgroundColor = getTheme().getDropShadow(), backgroundColor2 = getTheme().getDropShadow();

        // Blur and bloom
        getLayer(BLUR).add(() -> RenderUtil.roundedRectangle(positionValue.position.getX(), positionValue.position.getY(),
                positionValue.scale.getX(), positionValue.scale.getY(), 10, Color.BLACK));
        getLayer(BLOOM).add(() -> RenderUtil.drawRoundedGradientRect(positionValue.position.getX(), positionValue.position.getY(),
                positionValue.scale.getX(), positionValue.scale.getY(), 11, backgroundColor, backgroundColor2, false));

        getLayer(REGULAR).add(() -> {
            // Background
            RenderUtil.drawRoundedGradientRect(positionValue.position.getX(), positionValue.position.getY(), positionValue.scale.getX(),
                    positionValue.scale.getY(), 10, getTheme().getBackgroundShade(),
                    getTheme().getBackgroundShade(), false);

            // Song and artist name
            Fonts.MAIN.get(20, Weight.BOLD).draw(song, x, positionValue.position.getY() + 15, Color.WHITE.getRGB());
            Fonts.MAIN.get(16, Weight.BOLD).draw(artist, x, positionValue.position.getY() + 28, new Color(255, 255, 255, 128).getRGB());

            // Progress background
            RenderUtil.roundedRectangle(x, positionValue.position.getY() + 45, 100, 6, 3, getTheme().getBackgroundShade());

            // Progress bar
            RenderUtil.drawRoundedGradientRect(x, positionValue.position.getY() + 45, progress_ms * 100 / duration_ms, 6, 3,
                    getTheme().getFirstColor(), getTheme().getSecondColor(), true);

            // Time and duration
            Fonts.MAIN.get(13, Weight.BOLD).drawRight(progress + " / ",
                    x + 100 - Fonts.MAIN.get(13, Weight.BOLD).width(length), positionValue.position.getY() + 35,
                    new Color(255, 255, 255, 128).getRGB());
            Fonts.MAIN.get(13, Weight.BOLD).drawRight(length, x + 100, positionValue.position.getY() + 35,
                    new Color(255, 255, 255, 48).getRGB());
        });
    };
    public void auth() {
        new Thread(() -> {
            try {
                URL url = new URL("https://accounts.spotify.com/api/token");
                HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
                httpConn.setRequestMethod("POST");

                httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                httpConn.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
                writer.write("grant_type=client_credentials&client_id=" + client_id + "&client_secret=" + client_secret);
                writer.flush();
                writer.close();
                httpConn.getOutputStream().close();

                InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                        ? httpConn.getInputStream()
                        : httpConn.getErrorStream();
                Scanner s = new Scanner(responseStream).useDelimiter("\\A");
                String response = s.hasNext() ? s.next() : "";
                JSONObject obj = new JSONObject(response);
                auth_token = obj.getString("access_token");
                ChatUtil.display("Logged in");
            } catch (Exception e) {
                ChatUtil.display("Spotify auth error");
            }
        }).start();
    }
    public void getSpotifyInfo() {
        try {
            System.out.println("Bearer " + auth_token);
            if (!updatedInfo) {
                updatedInfo = true;
                URL url = new URL("https://api.spotify.com/v1/me/player?market=US");
                HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
                httpConn.setRequestMethod("GET");

                httpConn.setRequestProperty("authority", "api.spotify.com");
                httpConn.setRequestProperty("accept", "*/*");
                httpConn.setRequestProperty("accept-language", "en-US,en;q=0.9");
                httpConn.setRequestProperty("authorization", "Bearer " + auth_token);
                httpConn.setRequestProperty("origin", "https://developer.spotify.com");
                httpConn.setRequestProperty("referer", "https://developer.spotify.com/");

                InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                        ? httpConn.getInputStream()
                        : httpConn.getErrorStream();
                Scanner s = new Scanner(responseStream).useDelimiter("\\A");
                String response = s.hasNext() ? s.next() : "";
                JSONObject obj = new JSONObject(response);
                ChatUtil.display(response);
                progress_ms = obj.getInt("progress_ms");
                JSONObject item = obj.getJSONObject("item");
                JSONObject album = item.getJSONObject("album");
                duration_ms = item.getInt("duration_ms");
                JSONArray arists = album.getJSONArray("artists");
                song = Normalizer.normalize(item.getString("name"),Normalizer.Form.NFKD).replaceAll("[^\\p{ASCII}]", "");
                if (song.length() > 20) {
                    song = song.substring(0, 20);
                }
                JSONObject author = new JSONObject(arists.get(0).toString());
                artist = Normalizer.normalize(author.getString("name"), Normalizer.Form.NFKD).replaceAll("[^\\p{ASCII}]", "");
                updatedInfo = false;
            }
        } catch (Exception e) {
            ChatUtil.display(e);
            if (!client_id.isEmpty() && !client_secret.isEmpty()) {
                ChatUtil.display("Spotify auth token expired. Login... Please wait");
//                auth();
            }
            updatedInfo = false;
        }
    }
    @EventLink
    public final Listener<TickEvent> onTick = event -> {
        new Thread(() -> {
//            System.out.println(ticks % (20 * 60));
            if (ticks % (20 * 60) == 0 && this.isEnabled()) {
                updatedInfo = false;
                getSpotifyInfo();
            }
            if (ticks % 20 == 0 && this.isEnabled()) {
                if ((progress_ms + 1000) > duration_ms) {
                    getSpotifyInfo();
                } else {
                    progress_ms += 1000;
                }
            }
            ticks++;
        }).start();
    };
}
