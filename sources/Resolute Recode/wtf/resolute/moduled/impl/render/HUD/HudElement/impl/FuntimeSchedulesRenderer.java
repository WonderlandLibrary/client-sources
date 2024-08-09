package wtf.resolute.moduled.impl.render.HUD.HudElement.impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.mojang.blaze3d.matrix.MatrixStack;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.minecraft.util.text.ITextComponent;
import wtf.resolute.ResoluteInfo;
import wtf.resolute.evented.EventDisplay;
import wtf.resolute.evented.EventUpdate;
import wtf.resolute.manage.drag.Dragging;
import wtf.resolute.moduled.impl.render.HUD.HudElement.ElementRenderer;
import wtf.resolute.moduled.impl.render.HUD.HudElement.ElementUpdater;
import wtf.resolute.ui.schedules.funtime.FunTimeEventData;
import wtf.resolute.utiled.http.HTTP;
import wtf.resolute.utiled.math.StopWatch;
import wtf.resolute.utiled.render.ColorUtils;
import wtf.resolute.utiled.render.DisplayUtils;
import wtf.resolute.utiled.render.Scissor;
import wtf.resolute.utiled.render.font.Fonts;
import wtf.resolute.utiled.text.GradientUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class FuntimeSchedulesRenderer implements ElementRenderer, ElementUpdater {
    static final String EVENT_LIST_URL = "http://87.251.66.149:1488/event/" + ResoluteInfo.userData.getUser();
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
        if (stopWatch.isReached((60 * 1000) * 3)) {
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
        int firstColor = ColorUtils.getColorStyle(0);
        int secondColor = ColorUtils.getColorStyle(100);
        DisplayUtils.drawShadow(posX, posY, width, height, 10, firstColor, secondColor);
        DisplayUtils.drawRoundedRect(posX, posY, width, height, 3, DisplayUtils.reAlphaInt(new Color(33, 32, 34).getRGB(), 210));
        Scissor.push();
        Scissor.setFromComponentCoordinates(posX, posY, width, height);
        Fonts.sfMedium.drawCenteredText(ms, "FT Schedules", posX + width / 2, posY + padding + 0.5f, -1,fontSize);

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


    public void updateEventsAsync() {
        CompletableFuture.supplyAsync(() -> {
            String resp = HTTP.getHTTP(EVENT_LIST_URL);
            Gson gson = new Gson();
            JsonArray list = gson.fromJson(resp.trim(), JsonArray.class);

            ArrayList<FunTimeEventData> events = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                JsonArray obj = list.get(i).getAsJsonArray();
                FunTimeEventData eventData = new FunTimeEventData();
                eventData.timeTo = (int) (System.currentTimeMillis() / 1000) + obj.get(0).getAsInt();
                eventData.anarchy = Integer.parseInt(obj.get(1).getAsString());
                if (events.size() < 8) events.add(eventData);
            }
            return events;
        }).thenAcceptAsync(updatedEvents -> {
            eventList.clear();
            eventList.addAll(updatedEvents);
        });
    }
}
