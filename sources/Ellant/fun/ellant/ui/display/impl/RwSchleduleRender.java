package fun.ellant.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import fun.ellant.Ellant;
import fun.ellant.events.EventDisplay;
import fun.ellant.events.EventUpdate;
import fun.ellant.ui.display.ElementRenderer;
import fun.ellant.ui.display.ElementUpdater;
import fun.ellant.ui.schedules.rw.Schedule;
import fun.ellant.ui.schedules.rw.SchedulesManager;
import fun.ellant.ui.schedules.rw.TimeType;
import fun.ellant.ui.styles.Style;
import fun.ellant.utils.drag.Dragging;
import fun.ellant.utils.math.MathUtil;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.font.Fonts;
import fun.ellant.utils.text.GradientUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.TimeZone;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
public class RwSchleduleRender
        implements ElementRenderer,
        ElementUpdater {
    private final Dragging dragging;
    float width;
    float height;
    private float animation;
    final SchedulesManager schedulesManager = new SchedulesManager();
    final TimeZone timeZone = TimeZone.getTimeZone("Europe/Moscow");
    List<Schedule> activeSchedules = new ArrayList<Schedule>();
    private static final int MINUTES_IN_DAY = 1440;
    boolean sorted = false;

    @Override
    public void update(EventUpdate e) {
        this.activeSchedules = this.schedulesManager.getSchedules();
        if (!this.sorted) {
            this.activeSchedules.sort(Comparator.comparingInt(schedule -> (int)(-Fonts.sfMedium.getWidth(schedule.getName(), 6.5f))));
            this.sorted = true;
        }
    }

    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack ms = eventDisplay.getMatrixStack();
        float posX = this.dragging.getX();
        float posY = this.dragging.getY();
        float fontSize = 6.5f;
        float padding = 5.0f;
        StringTextComponent name = GradientUtil.gradient("RW Schedules", ColorUtils.rgba(255, 0, 0, 255), ColorUtils.rgba(0, 255, 0, 255));
        Style style = Ellant.getInstance().getStyleManager().getCurrentStyle();
        ResourceLocation key = new ResourceLocation("expensive/images/ivent3.png");
        DisplayUtils.drawRoundedRect(posX, posY + 4.0f, this.animation, 13.0f, 1.0f, ColorUtils.rgb(27, 25, 25));
        Fonts.sfMedium.drawGradientText(ms, "RW   Schedules", posX + padding + 23.0f, posY + 6.0f, -1, ColorUtils.getColor(90), 8, 0.1f);
        posY += fontSize + padding * 2.0f;
        float maxWidth = Fonts.sfbold.getWidth(name, fontSize) + padding * 2.0f;
        float localHeight = fontSize + padding * 2.0f;
        posY += 5.0f;
        for (Schedule schedule : this.activeSchedules) {
            String nameText = schedule.getName();
            String timeString = this.getTimeString(schedule);
            float nameWidth = Fonts.sfMedium.getWidth(nameText, fontSize);
            float bindWidth = Fonts.sfMedium.getWidth(timeString, fontSize);
            float localWidth = nameWidth + bindWidth + padding * 3.0f;
            DisplayUtils.drawRoundedRect(posX, posY - 2.0f, this.animation, 11.0f, 2.0f, ColorUtils.rgb(27, 25, 25));
            Fonts.sfMedium.drawText(ms, nameText, posX + padding, posY, ColorUtils.rgba(210, 210, 210, 205), fontSize);
            Fonts.sfMedium.drawText(ms, timeString, posX + this.animation - padding - bindWidth + 2.0f, posY, ColorUtils.rgba(210, 210, 210, 205), fontSize);
            if (localWidth > maxWidth) {
                maxWidth = localWidth;
            }
            posY += 7.0f + padding;
            localHeight += fontSize + padding;
        }
        this.animation = MathUtil.lerp(this.animation, Math.max(maxWidth, 80.0f), 5.0f);
        this.height = localHeight + 2.5f;
        this.dragging.setWidth(this.animation);
        this.dragging.setHeight(this.height);
    }

    private String formatTime(Calendar calendar, int minutes) {
        int hours = minutes / 60;
        int secondsLeft = 59 - calendar.get(13);
        if ((minutes %= 60) > 0) {
            --minutes;
        }
        return hours + "\u0447 " + minutes + "\u043c " + secondsLeft + "\u0441";
    }

    private int calculateTimeDifference(int[] times, int minutes) {
        int index = Arrays.binarySearch(times, minutes);
        if (index < 0) {
            index = -index - 1;
        }
        if (index >= times.length) {
            return times[0] + 1440 - minutes;
        }
        return times[index] - minutes;
    }

    private String getTimeString(Schedule schedule, Calendar calendar) {
        int minutes = calendar.get(11) * 60 + calendar.get(12);
        int[] timeArray = Arrays.stream(schedule.getTimes()).mapToInt(TimeType::getMinutesSinceMidnight).toArray();
        int timeDifference = this.calculateTimeDifference(timeArray, minutes);
        return this.formatTime(calendar, timeDifference);
    }

    public String getTimeString(Schedule schedule) {
        return this.getTimeString(schedule, Calendar.getInstance(this.timeZone));
    }

    private void drawStyledRect(float x, float y, float width, float height, float radius) {
        DisplayUtils.drawRoundedRect(x - 0.5f, y - 0.5f, width + 1.0f, height + 1.0f, radius + 0.5f, ColorUtils.getColor(0));
        DisplayUtils.drawRoundedRect(x, y, width, height, radius, ColorUtils.rgba(21, 21, 21, 205));
    }

    public RwSchleduleRender(Dragging dragging) {
        this.dragging = dragging;
    }
}

