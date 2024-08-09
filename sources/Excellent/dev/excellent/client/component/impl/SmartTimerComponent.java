package dev.excellent.client.component.impl;

import dev.excellent.api.event.impl.player.MotionEvent;
import dev.excellent.api.event.impl.render.Render2DEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.component.Component;
import dev.excellent.client.module.impl.movement.Timer;
import dev.excellent.impl.font.Font;
import dev.excellent.impl.font.Fonts;
import dev.excellent.impl.util.animation.Animation;
import dev.excellent.impl.util.animation.Easing;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.color.ColorUtil;

public class SmartTimerComponent extends Component {
    private final Animation barAnimation = new Animation(Easing.EASE_OUT_QUAD, 50);
    private final Animation percentageAnimation = new Animation(Easing.LINEAR, 100);
    private final Listener<Render2DEvent> onRender = event -> {
        if (getTimer().mode.is("Кружок")) {
            renderTimerCircle(event);
        } else if (getTimer().mode.is("Обычный")) {
            renderTimerDefault(event);
        }
    };
    private final Listener<MotionEvent> onMotion = event -> getTimer().updateTimer(event.getYaw(), event.getPitch(), event.getX(), event.getY(), event.getZ());

    private void renderTimerCircle(Render2DEvent event) {

        if (!getTimer().smart.getValue()) return;
        float posX = (float) getTimer().drag.position.x;
        float posY = (float) getTimer().drag.position.y;
        float size = 14;
        getTimer().drag.size.set(size, size);
        float quotient = getTimer().maxViolation / getTimer().timerAmount.getValue().floatValue();
        float minimumValue = Math.min(getTimer().getViolation(), quotient);
        float perc = ((quotient - minimumValue) / quotient);
        barAnimation.run(perc * 359);
        percentageAnimation.run(perc * 100.0D);
        float targetPercentage = (float) percentageAnimation.getValue();
        Font medium = Fonts.INTER_BOLD.get(10);
        String text = String.valueOf((int) Mathf.round(targetPercentage, 1));
        int color = ColorUtil.getColor(0, 0, 0, 255);
        RectUtil.drawShadowSegments(event.getMatrix(), posX + (size / 2F), posY + (size / 2F), posX + (size / 2F), posY + (size / 2F), (size / 1.5F), color, color, color, color, false, false);
        RectUtil.drawDuadsCircle(event.getMatrix(), posX + (size / 2F), posY + (size / 2F), (size / 2F), 359, 4, ColorUtil.getColor(40, 40, 40, 0.4F * 255), false);
        RectUtil.drawDuadsCircleClientColored(event.getMatrix(), posX + (size / 2F), posY + (size / 2F), (size / 2F), barAnimation.getValue(), 4, false, 0.4F);
        medium.drawCenter(event.getMatrix(), text, posX + (size / 2F) + 0.25F, posY + (size / 2F) - (medium.getHeight() / 2F) + 0.5F, -1);
    }

    private void renderTimerDefault(Render2DEvent event) {

        float pc = 0.8F;
        int white = ColorUtil.getColor(255, 255, 255, 255);
        int black = ColorUtil.getColor(0, 0, 0, 255);

        int color1 = ColorUtil.overCol(white / 3, getTheme().getClientColor(0), pc);
        int color2 = ColorUtil.overCol(white / 3, getTheme().getClientColor(90), pc);
        int color3 = ColorUtil.overCol(white / 3, getTheme().getClientColor(90), pc);
        int color4 = ColorUtil.overCol(white / 3, getTheme().getClientColor(0), pc);

        if (!getTimer().smart.getValue()) return;
        float size = 75;
        double margin = 7;
        float quotient = getTimer().maxViolation / getTimer().timerAmount.getValue().floatValue() + 10;
        float minimumValue = Math.min(getTimer().getViolation(), quotient);
        float perc = ((quotient - minimumValue) / quotient);
        barAnimation.run(size * perc);
        percentageAnimation.run(perc * 100.0D);
        float targetPercentage = (float) percentageAnimation.getValue();
        Font medium = Fonts.INTER_BOLD.get(12);
        String text = String.valueOf((int) targetPercentage);
        float posX = (float) getTimer().drag.position.x;
        float posY = (float) getTimer().drag.position.y;
        float rectWidth = (float) barAnimation.getValue();
        float rectHeight = (float) (size / margin);

        getTimer().drag.size.set(rectWidth, rectHeight);

        posX += (size - rectWidth) / 2F;

        RectUtil.drawRoundedRectShadowed(event.getMatrix(), posX, posY, posX + rectWidth, posY + rectHeight, getTheme().round(), 1, black, black, black, black, false, false, true, true);
        RectUtil.drawRoundedRectShadowed(event.getMatrix(), posX, posY, posX + rectWidth, posY + rectHeight, getTheme().round(), 1, color1, color2, color3, color4, true, true, true, true);
        medium.drawCenter(event.getMatrix(), text, posX + rectWidth / 2F, posY + rectHeight / 2F - (medium.getHeight() / 2F) + 0.5F, -1);
    }


    private Timer getTimer() {
        return Timer.singleton.get();
    }
}
