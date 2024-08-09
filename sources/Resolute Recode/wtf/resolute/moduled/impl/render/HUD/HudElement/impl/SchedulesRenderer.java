package wtf.resolute.moduled.impl.render.HUD.HudElement.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.minecraft.util.text.ITextComponent;
import wtf.resolute.evented.EventDisplay;
import wtf.resolute.evented.EventUpdate;
import wtf.resolute.manage.drag.Dragging;
import wtf.resolute.moduled.impl.render.HUD.HudElement.ElementRenderer;
import wtf.resolute.moduled.impl.render.HUD.HudElement.ElementUpdater;
import wtf.resolute.ui.schedules.rw.Schedule;
import wtf.resolute.ui.schedules.rw.SchedulesManager;
import wtf.resolute.ui.schedules.rw.TimeType;
import wtf.resolute.utiled.font.Fonted;
import wtf.resolute.utiled.render.ColorUtils;
import wtf.resolute.utiled.render.DisplayUtils;
import wtf.resolute.utiled.render.Scissor;
import wtf.resolute.utiled.render.font.Fonts;
import wtf.resolute.utiled.text.GradientUtil;

import java.awt.*;
import java.util.*;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class SchedulesRenderer implements ElementRenderer, ElementUpdater {
    final Dragging dragging;
    float width;
    float height;
    final SchedulesManager schedulesManager = new SchedulesManager();
    final TimeZone timeZone = TimeZone.getTimeZone("Europe/Moscow");
    List<Schedule> activeSchedules = new ArrayList<>();
    private static final int MINUTES_IN_DAY = 1440;
    boolean sorted = false;

    @Override
    public void update(EventUpdate e) {
        activeSchedules = schedulesManager.getSchedules();
        if (!sorted) {
            this.activeSchedules.sort(Comparator.comparingInt(schedule -> (int) -Fonts.sfMedium.getWidth(schedule.getName(), 6.5f)));
            sorted = true;
        }
    }

    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack ms = eventDisplay.getMatrixStack();

        float posX = dragging.getX();
        float posY = dragging.getY();
        float fontSize = 6.5f;
        float padding = 5;
        int firstColor = ColorUtils.getColorStyle(0);
        int secondColor = ColorUtils.getColorStyle(100);
        ITextComponent name = GradientUtil.gradient("RW Schedules");
        DisplayUtils.drawShadow(posX, posY, width, height, 10, firstColor, secondColor);
        DisplayUtils.drawRoundedRect(posX, posY, width, height, 3, DisplayUtils.reAlphaInt(new Color(33, 32, 34).getRGB(), 210));
        Scissor.push();
        Scissor.setFromComponentCoordinates(posX, posY, width, height);
        Fonts.sfMedium.drawCenteredText(ms, "RW Schedules", posX + width / 2, posY + padding + 0.5f, -1,fontSize);

        posY += fontSize + padding * 2;

        float maxWidth = Fonts.sfMedium.getWidth(name, fontSize) + padding * 2;
        float localHeight = fontSize + padding * 2;

        DisplayUtils.drawRectHorizontalW(posX + 0.5f, posY, width - 1, 2.5f, 3, ColorUtils.rgba(0, 0, 0, (int) (255 * 0.25f)));
        posY += 3f;

        for (Schedule schedule : activeSchedules) {
            String nameText = schedule.getName();
            String timeString = getTimeString(schedule);

            float nameWidth = Fonts.sfMedium.getWidth(nameText, fontSize);
            float bindWidth = Fonts.sfMedium.getWidth(timeString, fontSize);

            float localWidth = nameWidth + bindWidth + padding * 3;

            Fonts.sfMedium.drawText(ms, nameText, posX + padding, posY, ColorUtils.rgba(210, 210, 210, 255), fontSize);
            Fonts.sfMedium.drawText(ms, timeString, posX + width - padding - bindWidth, posY, ColorUtils.rgba(210, 210, 210, 255), fontSize);

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

    private String formatTime(Calendar calendar, int minutes) {
        int hours = minutes / 60;
        int secondsLeft = 59 - calendar.get(Calendar.SECOND);

        if ((minutes %= 60) > 0) {
            --minutes;
        }

        return hours + "÷ " + minutes + "ì " + secondsLeft + "ñ";
    }

    private int calculateTimeDifference(int[] times, int minutes) {
        int index = Arrays.binarySearch(times, minutes);

        if (index < 0) {
            index = -index - 1;
        }

        if (index >= times.length) {
            return times[0] + MINUTES_IN_DAY - minutes;
        }

        return times[index] - minutes;
    }

    private String getTimeString(Schedule schedule, Calendar calendar) {
        int minutes = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE);
        int[] timeArray = Arrays.stream(schedule.getTimes()).mapToInt(TimeType::getMinutesSinceMidnight).toArray();
        int timeDifference = calculateTimeDifference(timeArray, minutes);
        return formatTime(calendar, timeDifference);
    }

    public String getTimeString(Schedule schedule) {
        return getTimeString(schedule, Calendar.getInstance(timeZone));
    }

    private void drawStyledRect(float x,
                                float y,
                                float width,
                                float height,
                                float radius) {

        DisplayUtils.drawRoundedRect(x - 0.5f, y - 0.5f, width + 1, height + 1, radius + 0.5f, ColorUtils.getColor(0)); // outline
        DisplayUtils.drawRoundedRect(x, y, width, height, radius, ColorUtils.rgba(21, 21, 21, 255));
    }


}

