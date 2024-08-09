//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package src.Wiksi.ui.display.impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.mojang.blaze3d.matrix.MatrixStack;
import src.Wiksi.Wiksi;
import src.Wiksi.events.EventDisplay;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.ui.display.ElementRenderer;
import src.Wiksi.ui.display.ElementUpdater;
import src.Wiksi.ui.schedules.funtime.FunTimeEventData;
import src.Wiksi.ui.styles.Style;
import src.Wiksi.utils.HTTP;
import src.Wiksi.utils.drag.Dragging;
import src.Wiksi.utils.math.StopWatch;
import src.Wiksi.utils.render.ColorUtils;
import src.Wiksi.utils.render.DisplayUtils;
import src.Wiksi.utils.render.Scissor;
import src.Wiksi.utils.render.font.Fonts;
import src.Wiksi.utils.text.GradientUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.util.text.ITextComponent;

public class FuntimeSchedulesRenderer implements ElementRenderer, ElementUpdater {
    static final String EVENT_LIST_URL;
    private final List<FunTimeEventData> eventList = new CopyOnWriteArrayList();
    private final Dragging dragging;
    private float width;
    private float height;
    private final StopWatch stopWatch = new StopWatch();
    private final boolean notify = false;

    public FuntimeSchedulesRenderer(Dragging dragging) {
        this.dragging = dragging;
        this.updateEventsAsync();
    }

    public void update(EventUpdate e) {
        if (this.stopWatch.isReached(180000L)) {
            this.updateEventsAsync();
            this.stopWatch.reset();
        }

    }

    public void render(EventDisplay eventDisplay) {
        MatrixStack ms = eventDisplay.getMatrixStack();
        float posX = this.dragging.getX();
        float posY = this.dragging.getY();
        float fontSize = 6.5F;
        float padding = 5.0F;
        ITextComponent name = GradientUtil.gradient("FT Schedules", ColorUtils.rgba(255, 0, 0, 255), ColorUtils.rgba(0, 255, 0, 255));
        Style style = Wiksi.getInstance().getStyleManager().getCurrentStyle();
        DisplayUtils.drawShadow(posX, posY, this.width, this.height, 10, style.getFirstColor().getRGB(), style.getSecondColor().getRGB());
        this.drawStyledRect(posX, posY, this.width, this.height, 4.0F);
        Scissor.push();
        Scissor.setFromComponentCoordinates((double)posX, (double)posY, (double)this.width, (double)this.height);
        Fonts.sfui.drawCenteredText(ms, name, posX + this.width / 2.0F, posY + padding + 0.5F, fontSize);
        posY += fontSize + padding * 2.0F;
        float maxWidth = Fonts.sfMedium.getWidth(name, fontSize) + padding * 2.0F;
        float localHeight = fontSize + padding * 2.0F;
        DisplayUtils.drawRectHorizontalW((double)(posX + 0.5F), (double)posY, (double)(this.width - 1.0F), 2.5, 3, ColorUtils.rgba(0, 0, 0, 63));
        posY += 3.0F;
        Iterator var11 = this.eventList.iterator();

        while(var11.hasNext()) {
            FunTimeEventData data = (FunTimeEventData)var11.next();
            int timeTo = data.timeTo - (int)(System.currentTimeMillis() / 1000L);
            if (timeTo <= 500 && timeTo > 0) {
                String nameText = String.format("/an%d", data.anarchy);
                String formattedTime = this.formatTime(timeTo);
                float nameWidth = Fonts.sfMedium.getWidth(nameText, fontSize);
                float bindWidth = Fonts.sfMedium.getWidth(formattedTime, fontSize);
                float localWidth = nameWidth + bindWidth + padding * 3.0F;
                Fonts.sfMedium.drawText(ms, nameText, posX + padding, posY, ColorUtils.rgba(210, 210, 210, 255), fontSize);
                Fonts.sfMedium.drawText(ms, formattedTime, posX + this.width - padding - bindWidth, posY, ColorUtils.rgba(210, 210, 210, 255), fontSize);
                if (localWidth > maxWidth) {
                    maxWidth = localWidth;
                }

                posY += fontSize + padding;
                localHeight += fontSize + padding;
            }
        }

        Scissor.unset();
        Scissor.pop();
        this.width = Math.max(maxWidth, 80.0F);
        this.height = localHeight + 2.5F;
        this.dragging.setWidth(this.width);
        this.dragging.setHeight(this.height);
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

    private void drawStyledRect(float x, float y, float width, float height, float radius) {
        DisplayUtils.drawRoundedRect(x - 0.5F, y - 0.5F, width + 1.0F, height + 1.0F, radius + 0.5F, ColorUtils.getColor(0));
        DisplayUtils.drawRoundedRect(x, y, width, height, radius, ColorUtils.rgba(21, 21, 21, 255));
    }

    public void updateEventsAsync() {
        CompletableFuture.supplyAsync(() -> {
            String resp = HTTP.getHTTP(EVENT_LIST_URL);
            Gson gson = new Gson();
            JsonArray list = (JsonArray)gson.fromJson(resp.trim(), JsonArray.class);
            ArrayList<FunTimeEventData> events = new ArrayList();

            for(int i = 0; i < list.size(); ++i) {
                JsonArray obj = list.get(i).getAsJsonArray();
                FunTimeEventData eventData = new FunTimeEventData();
                eventData.timeTo = (int)(System.currentTimeMillis() / 1000L) + obj.get(0).getAsInt();
                eventData.anarchy = Integer.parseInt(obj.get(1).getAsString());
                if (events.size() < 8) {
                    events.add(eventData);
                }
            }

            return events;
        }).thenAcceptAsync((updatedEvents) -> {
            this.eventList.clear();
            this.eventList.addAll(updatedEvents);
        });
    }

    static {
        EVENT_LIST_URL = "http://87.251.66.149:1488/funtime/" + Wiksi.userData.getUser();
    }
}
