package wtf.diablo.client.module.impl.misc;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import com.google.gson.JsonObject;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.model_objects.miscellaneous.CurrentlyPlaying;
import se.michaelthelin.spotify.model_objects.specification.Track;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.event.api.EventTypeEnum;
import wtf.diablo.client.event.impl.player.motion.MotionEvent;
import wtf.diablo.client.gui.draggable.AbstractDraggableElement;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.impl.BooleanSetting;
import wtf.diablo.client.spotify.SpotifyAPI;
import wtf.diablo.client.spotify.SpotifyImageManager;
import wtf.diablo.client.spotify.SpotifyLocalServer;
import wtf.diablo.client.util.Constants;
import wtf.diablo.client.util.mc.player.chat.ChatUtil;
import wtf.diablo.client.util.render.RenderUtil;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Objects;

@ModuleMetaData(name = "Spotify Player", description = "Shows your current activity on Spotify", category = ModuleCategoryEnum.MISC)
public final class SpotifyPlayerModule extends AbstractModule {
    private static final File SPOTIFY_SETTINGS_FILE = new File(Diablo.getInstance().getMainDirectory(), "spotify_settings.json");

    private CurrentlyPlaying currentlyPlaying = new CurrentlyPlaying.Builder().setIs_playing(false).build();
    private SpotifyAPI spotifyAPI;
    private Thread updateThread;

    private final BooleanSetting filterFeatures = new BooleanSetting("Filter Features", true);
    private SpotifyImageManager imageManager;
    private Track track;

    public SpotifyPlayerModule() {
        this.registerSettings(filterFeatures);
    }

    @Override
    public void onEnable() {
        if (!SPOTIFY_SETTINGS_FILE.exists()) {
            createDefaultSpotifySettings();
            ChatUtil.addChatMessage("Created default Spotify settings file. You must edit this by running .open and changing the clientid and clientsecret in " + SPOTIFY_SETTINGS_FILE.getName() + "!");
            this.toggle(false);
            return;
        }

        new Thread(() -> {
            try {
                SpotifyLocalServer.createConnection();} catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        try {
            final FileReader reader = new FileReader(SPOTIFY_SETTINGS_FILE);
            final JsonObject json = Constants.GSON.fromJson(reader, JsonObject.class);

            SpotifyAPI.AUTH_CODE = "NOT-SET";

            spotifyAPI = new SpotifyAPI(json.get("client_id").getAsString(), json.get("client_secret").getAsString(), SpotifyHttpManager.makeUri("http://localhost:7777/diablo/spotify/"));
            Desktop.getDesktop().browse(spotifyAPI.getAuthCodeURI());
            imageManager = new SpotifyImageManager(spotifyAPI.getSpotifyApi());
            updateThread = startThread();
            updateThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onEnable();
    }

    private Thread startThread() {
        return new Thread(() -> {
            while (true) {
                try {
                    currentlyPlaying = spotifyAPI.getCurrentSong();
                    track = spotifyAPI.getSpotifyApi().getTrack(currentlyPlaying.getItem().getId()).build().executeAsync().get();
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onDisable() {
        try {
            updateThread.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDisable();
    }

    @EventHandler
    private final Listener<MotionEvent> updateEventListener = e -> {
        if (spotifyAPI == null) return;
        if (e.getEventType() != EventTypeEnum.PRE) return;

        if (!Objects.equals(SpotifyAPI.AUTH_CODE, "NOT-SET")) {
            spotifyAPI.setAuthCode();
        }
    };

    final AbstractDraggableElement spotifyPlayer = new AbstractDraggableElement("Spotify Player", 25, 25, 100, 10, this) {
        @Override
        public void draw() {
            int initialWidth = 150;

            spotifyPlayer.setHeight(39);
            spotifyPlayer.setWidth(initialWidth);

            try {
                if (currentlyPlaying.getIs_playing()) {
                    if (imageManager.getImage(currentlyPlaying.getItem()).getLocation() == null)
                        return;

                    double percent = (double) currentlyPlaying.getProgress_ms() / currentlyPlaying.getItem().getDurationMs();
                    String title = currentlyPlaying.getItem().getName();

                    float artistsScale = 0.7f;
                    float artistsMultiplier = Math.abs(1 - artistsScale) + 1;

                    float timeScale = 0.6f;
                    float timeMultiplier = Math.abs(1 - timeScale) + 1;
                    int remainingTimeMs = currentlyPlaying.getItem().getDurationMs() - currentlyPlaying.getProgress_ms();
                    String remainingTimeString = String.format("-%2d:%02d", remainingTimeMs / 1000 / 60, remainingTimeMs / 1000 % 60);

                    String[] blacklisted = new String[]{"with","feat.","ft.","featuring"};
                    for (String s : blacklisted) {
                        if (title.contains(s) && filterFeatures.getValue()) {
                            final int index = title.indexOf(s);
                            if (title.charAt(index - 1) == '(' || title.charAt(index - 1) == '[')
                                title = title.substring(0, title.indexOf(s) - 1);
                        }
                    }


                    int widthOffset = 46;
                    if (initialWidth - widthOffset < mc.fontRendererObj.getStringWidth(title))
                        spotifyPlayer.setWidth(mc.fontRendererObj.getStringWidth(title) + widthOffset);

                    Gui.drawRect(0, 0, getWidth(), getHeight(), new Color(17, 17, 17, 210).getRGB());

                    RenderUtil.drawImage(5, 5, 29, 29, imageManager.getImage(currentlyPlaying.getItem()).getLocation(), 1.0f);
                    mc.fontRendererObj.drawString(title, 40, 6, -1);
                    GlStateManager.pushMatrix();
                    GlStateManager.scale(artistsScale, artistsScale, artistsScale);
                    mc.fontRendererObj.drawString(String.format("%s%s", ChatFormatting.GRAY, track.getArtists()[0].getName()), (int) (46 * artistsMultiplier), (int) (20 * artistsMultiplier), -1);
                    GlStateManager.scale(1, 1, 1);
                    GlStateManager.popMatrix();

                    GlStateManager.pushMatrix();
                    GlStateManager.scale(timeScale, timeScale, timeScale);
                    mc.fontRendererObj.drawString(remainingTimeString, (int) (((getWidth() * 2.5f) - mc.fontRendererObj.getStringWidth(remainingTimeString)) * timeScale), (int) (26 * timeMultiplier), -1);
                    GlStateManager.scale(1, 1, 1);
                    GlStateManager.popMatrix();

                    double width = (getWidth() - 46);
                    Gui.drawRect(40, getHeight() - 8.5f, (getWidth() - 6), getHeight() - 5, new Color(170, 170, 170).getRGB());
                    Gui.drawRect(40, getHeight() - 8.5f, 40 + (percent * (width)), getHeight() - 5, new Color(255, 255, 255).getRGB());
                }
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }
    };

    public static void createDefaultSpotifySettings() {
        try {
            final JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("client_id", "");
            jsonObject.addProperty("client_secret", "");
            jsonObject.addProperty("redirect_uri", "http://localhost:7777/diablo/spotify/");

            final FileWriter fileWriter = new FileWriter(SPOTIFY_SETTINGS_FILE);
            fileWriter.write(Constants.GSON.toJson(jsonObject));
            fileWriter.close();

        } catch (Exception ignored) {}
    }
}
