package im.expensive.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import im.expensive.Expensive;
import im.expensive.events.EventDisplay;
import im.expensive.events.EventUpdate;
import im.expensive.functions.impl.render.HUD;
import im.expensive.ui.display.ElementRenderer;
import im.expensive.ui.display.ElementUpdater;
import im.expensive.ui.schedules.rw.Schedule;
import im.expensive.ui.schedules.rw.SchedulesManager;
import im.expensive.ui.schedules.rw.TimeType;
import im.expensive.ui.styles.Style;
import im.expensive.utils.drag.Dragging;
import im.expensive.utils.math.Vector4i;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.DisplayUtils;
import im.expensive.utils.render.Scissor;
import im.expensive.utils.render.font.Fonts;
import im.expensive.utils.text.GradientUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.minecraft.util.text.ITextComponent;

import java.util.*;

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
        ITextComponent name = GradientUtil.gradient("RW Schedules");
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

        return hours + "ч " + minutes + "м " + secondsLeft + "с";
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
        Vector4i vector4i = new Vector4i(HUD.getColor(0), HUD.getColor(90), HUD.getColor(180), HUD.getColor(170));
        DisplayUtils.drawShadow(x, y, width, height, 10, vector4i.x, vector4i.y, vector4i.z, vector4i.w);
        DisplayUtils.drawGradientRound(x, y, width, height, radius + 0.5f, vector4i.x, vector4i.y, vector4i.z, vector4i.w); // outline
        DisplayUtils.drawRoundedRect(x, y, width, height, radius, ColorUtils.rgba(21, 21, 21, 230));
    }

}
