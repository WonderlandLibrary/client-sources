package im.expensive.ui.display.impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.mojang.blaze3d.matrix.MatrixStack;
import im.expensive.Expensive;
import im.expensive.events.EventDisplay;
import im.expensive.events.EventUpdate;
import im.expensive.functions.impl.render.HUD;
import im.expensive.ui.display.ElementRenderer;
import im.expensive.ui.display.ElementUpdater;
import im.expensive.ui.schedules.funtime.FunTimeEventData;
import im.expensive.ui.styles.Style;
import im.expensive.utils.HTTP;
import im.expensive.utils.drag.Dragging;
import im.expensive.utils.math.StopWatch;
import im.expensive.utils.math.Vector4i;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.DisplayUtils;
import im.expensive.utils.render.Scissor;
import im.expensive.utils.render.font.Fonts;
import im.expensive.utils.text.GradientUtil;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.minecraft.util.text.ITextComponent;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;


@FieldDefaults(level = AccessLevel.PRIVATE)
public class FuntimeSchedulesRenderer implements ElementRenderer, ElementUpdater {
    static final String EVENT_LIST_URL = "http://87.251.66.149:1488/funtime/" + Expensive.userData.getUser();
    final List<FunTimeEventData> eventList = new CopyOnWriteArrayList<>();
    final Dragging dragging;
    float width, height;
    final StopWatch stopWatch = new StopWatch();

    public FuntimeSchedulesRenderer(Dragging dragging) {
        this.dragging = dragging;
        updateEventsAsync();
    }

    @Override
    public void update(EventUpdate e) {

        if (stopWatch.isReached((60 * 1000))) {
            updateEventsAsync();
            stopWatch.reset();
        }
    }

    final boolean notify = false;

    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack ms = eventDisplay.getMatrixStack();

        float posX = dragging.getX();
        float posY = dragging.getY();
        float fontSize = 6.5f;
        float padding = 5;
        ITextComponent name = GradientUtil.gradient("FT Schedules");
        Style style = Expensive.getInstance().getStyleManager().getCurrentStyle();

        drawStyledRect(posX, posY, width, height, 4);
        Scissor.push();
        Scissor.setFromComponentCoordinates(posX, posY, width, height);
        Fonts.sfui.drawCenteredText(ms, name, posX + width / 2, posY + padding + 0.5f, fontSize);

        posY += fontSize + padding * 2;

        float maxWidth = Fonts.sfMedium.getWidth(name, fontSize) + padding * 2;
        float localHeight = fontSize + padding * 2;

        DisplayUtils.drawRectHorizontalW(posX + 0.5f, posY, width - 1, 2.5f, 3, ColorUtils.rgba(0, 0, 0, (int) (255 * 0.25f)));
        posY += 3f;

        for (FunTimeEventData data : eventList) {
            int timeTo = data.timeTo - (int) (System.currentTimeMillis() / 1000);

            if (timeTo > 500 || timeTo <= 0) {
                continue;
            }

            String nameText = String.format("/an%d", data.anarchy);
            String formattedTime = formatTime(timeTo);


            float nameWidth = Fonts.sfMedium.getWidth(nameText, fontSize);
            float bindWidth = Fonts.sfMedium.getWidth(formattedTime, fontSize);
            float localWidth = nameWidth + bindWidth + padding * 3;
            Fonts.sfMedium.drawText(ms, nameText, posX + padding, posY, ColorUtils.rgba(210, 210, 210, 255), fontSize);
            Fonts.sfMedium.drawText(ms, formattedTime, posX + width - padding - bindWidth, posY, ColorUtils.rgba(210, 210, 210, 255), fontSize);

            if (localWidth > maxWidth) {
                maxWidth = localWidth;
            }

            posY += (fontSize + padding);
            localHeight += (fontSize + padding);
        }
        Scissor.unset();
        Scissor.pop();
        width = Math.max(maxWidth, 80);
        height = localHeight + 2.5f;
        dragging.setWidth(width);
        dragging.setHeight(height);
    }

    private String formatTime(int seconds) {
        if (seconds < 60) {
            return String.format("%2ds", seconds);
        } else {
            int minutes = seconds / 60;
            int remainingSeconds = seconds % 60;
            return String.format("%2dm %2ds", minutes, remainingSeconds);
        }
    }

    private void drawStyledRect(float x,
                                float y,
                                float width,
                                float height,
                                float radius) {
        Vector4i vector4i = new Vector4i(HUD.getColor(0), HUD.getColor(90), HUD.getColor(180), HUD.getColor(170));
        DisplayUtils.drawShadow(x, y, width, height, 10, vector4i.x, vector4i.y, vector4i.z, vector4i.w);
        DisplayUtils.drawGradientRound(x, y, width, height, radius + 0.5f, vector4i.x, vector4i.y, vector4i.z, vector4i.w); // outline
        DisplayUtils.drawRoundedRect(x, y, width, height, radius, ColorUtils.rgba(21, 21, 21, 230));
    }

    public static String decryptStringWithOpenSSL(String encoded, String password) throws Exception {
        byte[] decoded = Base64.getDecoder().decode(encoded);

        // Separate IV and encrypted data
        int ivSize = 16; // IV size for AES CBC
        byte[] iv = new byte[ivSize];
        byte[] encrypted = new byte[decoded.length - ivSize];
        System.arraycopy(decoded, 0, iv, 0, iv.length);
        System.arraycopy(decoded, iv.length, encrypted, 0, encrypted.length);

        // Decrypt the encrypted data using AES CBC
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(password.getBytes(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);
        byte[] decrypted = cipher.doFinal(encrypted);

        return new String(decrypted);
    }

    public void updateEventsAsync() {
        CompletableFuture.supplyAsync(() -> {
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://scriptkitty.lol/fun.php"))
                        .build();
                HttpResponse<byte[]> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
                String response = new String(httpResponse.body(), StandardCharsets.UTF_8);

                Gson gson = new Gson();
                JsonArray list = gson.fromJson(decryptStringWithOpenSSL(response, "3412b9c9da01a4e540d2f6a38cbf346f").trim(), JsonArray.class);

                ArrayList<FunTimeEventData> events = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    JsonArray obj = list.get(i).getAsJsonArray();
                    FunTimeEventData eventData = new FunTimeEventData();
                    eventData.timeTo = (int) (System.currentTimeMillis() / 1000) + obj.get(0).getAsInt();
                    eventData.anarchy = Integer.parseInt(obj.get(1).getAsString());
                    if (events.size() < 8) events.add(eventData);
                }

                return events;
            } catch (Exception e) {
                return new ArrayList<FunTimeEventData>();
            }
        }).thenAcceptAsync(updatedEvents -> {
            eventList.clear();
            eventList.addAll(updatedEvents);
        });
    }
}