/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.hud.elements;

import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.hud.Hud;
import meteordevelopment.meteorclient.systems.hud.HudElement;
import meteordevelopment.meteorclient.systems.hud.HudElementInfo;
import meteordevelopment.meteorclient.systems.hud.HudRenderer;
import meteordevelopment.meteorclient.utils.network.Http;
import meteordevelopment.meteorclient.utils.render.RenderUtils;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Identifier;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import static meteordevelopment.meteorclient.MeteorClient.mc;
import static meteordevelopment.meteorclient.utils.render.color.Color.WHITE;

public final class SpotifyHud extends HudElement {

    public static final HudElementInfo<SpotifyHud> INFO = new HudElementInfo<>(Hud.GROUP, "spotify", "Displays current spotify song and cover.", SpotifyHud::new);

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<artistMode> artistModeSetting = sgGeneral.add(new EnumSetting.Builder<artistMode>()
        .name("artist mode")
        .description("Changes the separator between the artist and the song")
        .defaultValue(artistMode.By)
        .build()
    );

    private final Setting<SettingColor> color = sgGeneral.add(new ColorSetting.Builder()
        .name("color")
        .description("Color.")
        .defaultValue(new SettingColor(0,0,0, 255))
        .build()
    );

    private final Setting<SettingColor> textcolor = sgGeneral.add(new ColorSetting.Builder()
        .name("text-color")
        .description("Text color.")
        .defaultValue(new SettingColor(0,0,0, 255))
        .build()
    );

    private final Setting<Double> scale = sgGeneral.add(new DoubleSetting.Builder()
        .name("scale")
        .description("The scale of the image.")
        .defaultValue(1)
        .sliderRange(1, 5)
        .onChanged(o -> updateSize())
        .build()
    );

    private final Setting<Double> fontscale = sgGeneral.add(new DoubleSetting.Builder()
        .name("font-scale")
        .description("Useful when scaling.")
        .defaultValue(1)
        .sliderRange(1, 5)
        .build()
    );


    private final Setting<Integer> refreshRate = sgGeneral.add(new IntSetting.Builder()
        .name("refresh-rate")
        .description("How often to change (ticks).")
        .defaultValue(1200)
        .sliderRange(10, 100)
        .build()
    );

    private final Setting<String> urlsetting = sgGeneral.add(new StringSetting.Builder()
        .name("url")
        .description("The url for simplified api (you can get this by contacting nullablepointer on discord)")
        .defaultValue("")
        .build()
    );

    private boolean locked = false;
    private boolean empty = true;
    private int ticks = 0;

    private String song = "";
    private String artists = "";

    private Integer duration = -1;

    private Integer progress = null;
    private static final Identifier TEXID = new Identifier("meteor", "tex");

    private final Hud hud = Hud.get();

    public SpotifyHud() {
        super(INFO);
        MeteorClient.EVENT_BUS.subscribe(this);
    }

    @Override
    public void remove() {
        super.remove();
        MeteorClient.EVENT_BUS.unsubscribe(this);
    }

    @EventHandler
    public void onTick(final TickEvent.Post event) {
        ticks++;
        if (ticks >= refreshRate.get()) {
            ticks = 0;
            loadText();
        }
    }


    @Override
    public void render(final HudRenderer renderer) {

        if (urlsetting.get().isEmpty() || getObject(urlsetting.get()) == null)
            return;

        if (empty) {
            loadImage();
            return;
        }

        if (Objects.equals(song, "") || Objects.equals(artists, "")) {
            loadText();
            return;
        }

        // Image
        renderer.texture(TEXID, x, y, 100 * scale.get(), 100 * scale.get(), WHITE);

        // Text
        renderer.quad(x + 100 * scale.get(), y, 400d * scale.get(), 100 * scale.get(), color.get());

        renderer.text(truncateString(song, (int) (0.4 * 100d * scale.get())), x + 100 * scale.get() + 20d * scale.get(), y + 20d * scale.get(), textcolor.get(), true, hud.getTextScale() * fontscale.get());
        renderer.text(truncateString(artistModeSetting.get().toString() + artists, (int) (0.4 * 100d * scale.get())), x + 100 * scale.get() + 20d * scale.get(), y + 50d * scale.get(), textcolor.get(), true, hud.getTextScale() * fontscale.get());

        // Time

        renderer.text(truncateString(toMinutes(progress) + "/" + toMinutes(duration), (int) (0.4 * 100d * scale.get())), x + 100 * scale.get() + 20d * scale.get(), y + 75d * scale.get(), textcolor.get(), true, hud.getTextScale() * fontscale.get() * 0.9);


        // Circle
        // renderer.quad(x, y, s, s, GuiRenderer.CIRCLE, theme.sliderHandle.get(dragging, handleMouseOver));

        // THIS WORKS BTW, idk why nullable doesnt want this
        // RenderUtils.drawCircle(renderer.drawContext.getMatrices(), x, y, scale.get(), 20, WHITE);
    }

    private void loadText() {
        new Thread(() -> {
            try {
                JSONObject obj;
                if (getObject(urlsetting.get()) == null) return;
                obj = getObject(urlsetting.get() + "song");
                if (obj != null) {
                    if ((!Objects.equals(song, obj.getString("song")) && !Objects.equals(obj.getString("song"), "")) || (!Objects.equals(artists, obj.getString("artists")) && !Objects.equals(obj.getString("artists"), ""))) {
                        loadImage();
                    }
                    song = obj.getString("song");
                    artists = obj.getString("artists");
                    duration = obj.getInt("duration");
                    progress = obj.getInt("time");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
        updateSize();
    }

    private void loadImage() {
        if (locked)
            return;
        new Thread(() -> {
            try {
                locked = true;
                // instantiate a JSON obj that gets our shit
                JSONObject obj;
                // check if the JSON is null, else return
                if (getObject(urlsetting.get()) == null) return;
                // set the JSON object to the actual object
                obj = getObject(urlsetting.get() + "cover");
                if (obj != null) {
                    String url = obj.getString("url");
                    if (!url.equals("")) {
                        NativeImage img = NativeImage.read(Http.get(url).sendInputStream());
                        mc.getTextureManager().registerTexture(TEXID, new NativeImageBackedTexture(img));
                        empty = false;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            locked = false;
        }).start();
        updateSize();
    }

    private void updateSize() {
        setSize(500 * scale.get(), 100 * scale.get());
    }

    private JSONObject getObject(final String url) {
        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);

            HttpResponse response = httpClient.execute(httpGet);

            // Check if the response code is OK (200)
            if (response.getStatusLine().getStatusCode() == 200) {
                // Extract JSON from the response entity
                String jsonString = EntityUtils.toString(response.getEntity());

                // Parse JSON using org.json
                return new JSONObject(jsonString);
            } else return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String truncateString(final String input, final int maxLength) {
        if (input == null || input.length() <= maxLength) {
            return input;
        } else {
            return input.substring(0, maxLength - 3) + "...";
        }
    }

    public static String toMinutes(final long milliseconds) {
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;

        return String.format("%d:%02d", minutes, seconds % 60);
    }

    public enum artistMode {
        By("By"),
        Dash("- "),
        Slash("/ ")

        ;

        private final String separator;

        private artistMode(String separator) {
            this.separator = separator;
        }

        public String getString() {
            return separator;
        }
    }

}
