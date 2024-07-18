package com.alan.clients.ui.click.standard.components.value.impl;

import com.alan.clients.font.Fonts;
import com.alan.clients.font.Weight;
import com.alan.clients.ui.click.standard.components.value.ValueComponent;
import com.alan.clients.ui.click.standard.screen.Colors;
import com.alan.clients.util.gui.GUIUtil;
import com.alan.clients.util.gui.textbox.TextAlign;
import com.alan.clients.util.gui.textbox.TextBox;
import com.alan.clients.util.math.MathUtil;
import com.alan.clients.util.render.ColorUtil;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.util.vector.Vector2d;
import com.alan.clients.value.Value;
import com.alan.clients.value.impl.BoundsNumberValue;
import rip.vantage.commons.util.time.StopWatch;

import java.awt.*;

public class BoundsNumberValueComponent extends ValueComponent {

    private final double SLIDER_WIDTH = 100;
    private final StopWatch stopWatch = new StopWatch();
    private final double grabberWidth = 5;
    public boolean grabbed1, grabbed2;
    private double percentage1, percentage2;
    private double selector1, selector2;
    private double renderPercentage1, renderPercentage2;
    private boolean mouseOver;
    private float hoverTime;
    public final TextBox valueDisplay = new TextBox(new Vector2d(0, 0), Fonts.MAIN.get(16, Weight.REGULAR), Colors.SECONDARY_TEXT.get(), TextAlign.LEFT, (((BoundsNumberValue) value).getDefaultValue()).toString().replace(".0", "") + " " + (((BoundsNumberValue) value).getDefaultSecondValue()).toString().replace(".0", ""), 100, "1234567890. ");

    public BoundsNumberValueComponent(final Value<?> value) {
        super(value);

        this.updateSliders();
    }

    public void updateSliders() {
        final BoundsNumberValue boundsNumberValue = (BoundsNumberValue) value;

        final double percentage1 = (-boundsNumberValue.getMin().doubleValue() + boundsNumberValue.getValue().doubleValue()) / (-boundsNumberValue.getMin().doubleValue() + boundsNumberValue.getMax().doubleValue());
        final double percentage2 = (-boundsNumberValue.getMin().doubleValue() + boundsNumberValue.getSecondValue().doubleValue()) / (-boundsNumberValue.getMin().doubleValue() + boundsNumberValue.getMax().doubleValue());

        this.percentage1 = Math.min(Math.max(percentage1, 0), 1);
        this.percentage2 = Math.min(Math.max(percentage2, 0), 1);
    }

    @Override
    public void draw(final Vector2d position, final int mouseX, final int mouseY, final float partialTicks) {
        this.position = position;

        // Cast
        final BoundsNumberValue boundsNumberValue = (BoundsNumberValue) this.value;

        String firstValue = String.valueOf(boundsNumberValue.getValue().doubleValue());
        String secondValue = String.valueOf(boundsNumberValue.getSecondValue().doubleValue());

        if (firstValue.endsWith(".0")) {
            firstValue = firstValue.replace(".0", "");
        }

        if (secondValue.endsWith(".0")) {
            secondValue = secondValue.replace(".0", "");
        }

        final String value = firstValue + " " + secondValue;
        final float valueWidth = Fonts.MAIN.get(16, Weight.REGULAR).width(this.value.getName()) + 7;

        //Used to determine if the mouse is over the slider
        this.mouseOver = GUIUtil.mouseOver(this.position.x + valueWidth - 5, this.position.y - 3.5f, SLIDER_WIDTH + 10, this.height, mouseX, mouseY);
        if (this.mouseOver) {
            hoverTime = Math.min(1, hoverTime + stopWatch.getElapsedTime() / 200f);
        } else {
            hoverTime = Math.max(0, hoverTime - stopWatch.getElapsedTime() / 200f);
        }

        // Draws name
        Fonts.MAIN.get(16, Weight.REGULAR).draw(this.value.getName(), this.position.x, this.position.y, Colors.SECONDARY_TEXT.getRGBWithAlpha(opacity));

        // Draws value
        this.valueDisplay.setPosition(new Vector2d(this.position.x + valueWidth + 105, this.position.y));
        if (!this.valueDisplay.isSelected()) this.valueDisplay.setText(value);
        this.valueDisplay.setWidth(20);
        this.valueDisplay.setColor(ColorUtil.withAlpha(this.valueDisplay.getColor(), opacity));
        this.valueDisplay.draw();
//        Fonts.SF_ROUNDED.get(16, Weight.REGULAR).drawString(value.replace(".0", ""), this.position.x + valueWidth + 105, this.position.y, this.getClickGUI().fontDarkColor.hashCode());

        // Draws background
        RenderUtil.roundedRectangle(this.position.x + valueWidth, this.position.y + 1.5f, SLIDER_WIDTH, 2, 1, Colors.BACKGROUND.getWithAlpha(Math.min(opacity, Colors.BACKGROUND.get().getAlpha())));

        selector1 = this.position.x + valueWidth;
        selector2 = this.position.x + valueWidth;

        if (getClickGUI().animationTime < 0.8) grabbed1 = grabbed2 = false;

        if (grabbed1) {
            percentage1 = mouseX - selector1;
            percentage1 /= SLIDER_WIDTH;
            percentage1 = Math.max(Math.min(percentage1, 1), 0);

            boundsNumberValue.setValue((boundsNumberValue.getMin().doubleValue() + (boundsNumberValue.getMax().doubleValue() - boundsNumberValue.getMin().doubleValue()) * percentage1));
            boundsNumberValue.setValue(MathUtil.roundWithSteps(boundsNumberValue.getValue().doubleValue(), boundsNumberValue.getDecimalPlaces().doubleValue()));

            if (percentage1 > percentage2) {
                percentage2 = percentage1;
                boundsNumberValue.setSecondValue((boundsNumberValue.getMin().doubleValue() + (boundsNumberValue.getMax().doubleValue() - boundsNumberValue.getMin().doubleValue()) * percentage2));
                boundsNumberValue.setSecondValue(MathUtil.roundWithSteps(boundsNumberValue.getSecondValue().doubleValue(), boundsNumberValue.getDecimalPlaces().doubleValue()));
            }
        } else if (grabbed2) {
            percentage2 = mouseX - selector2;
            percentage2 /= SLIDER_WIDTH;
            percentage2 = Math.max(Math.min(percentage2, 1), 0);

            boundsNumberValue.setSecondValue((boundsNumberValue.getMin().doubleValue() + (boundsNumberValue.getMax().doubleValue() - boundsNumberValue.getMin().doubleValue()) * percentage2));
            boundsNumberValue.setSecondValue(MathUtil.roundWithSteps(boundsNumberValue.getSecondValue().doubleValue(), boundsNumberValue.getDecimalPlaces().doubleValue()));

            if (percentage2 < percentage1) {
                percentage1 = percentage2;
                boundsNumberValue.setValue((boundsNumberValue.getMin().doubleValue() + (boundsNumberValue.getMax().doubleValue() - boundsNumberValue.getMin().doubleValue()) * percentage1));
                boundsNumberValue.setValue(MathUtil.roundWithSteps(boundsNumberValue.getValue().doubleValue(), boundsNumberValue.getDecimalPlaces().doubleValue()));
            }
        }

        //Animations
        final int speed = 30;
        for (int i = 0; i <= stopWatch.getElapsedTime(); ++i) {
            renderPercentage1 = (renderPercentage1 * (speed - 1) + percentage1) / speed;
            renderPercentage2 = (renderPercentage2 * (speed - 1) + percentage2) / speed;
        }

        //Selectors
        //RenderUtil.triangleCentered(selector1 + renderPercentage1 * 100 - 5, this.position.y + 15, 11, this.getClickGUI().accentColor);
        //RenderUtil.triangleCentered(selector2 + renderPercentage2 * 100 + 5, this.position.y + 15, -12, this.getClickGUI().accentColor);

        final double startPositionX = selector1 + renderPercentage1 * 100;
        final double endPositionX = selector2 + renderPercentage2 * 100;
        final double boundsWidth = endPositionX - startPositionX;

        if (percentage1 != percentage2) {
            RenderUtil.roundedRectangle(startPositionX, this.position.y + 1.5f, boundsWidth, 2, 1, ColorUtil.withAlpha(getTheme().getFirstColor(), Math.min(70, opacity)));
        }

        RenderUtil.roundedRectangle(startPositionX - grabberWidth / 2f, this.position.y, grabberWidth, grabberWidth, grabberWidth / 2.0f, ColorUtil.withAlpha(getTheme().getFirstColor(), opacity));

        if (percentage1 != percentage2) {
            RenderUtil.roundedRectangle(endPositionX - grabberWidth / 2f, this.position.y, grabberWidth, grabberWidth, grabberWidth / 2.0f, ColorUtil.withAlpha(getTheme().getFirstColor(), opacity));
        }

        stopWatch.reset();
    }

    @Override
    public boolean click(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.position == null) {
            return false;
        }

        final boolean left = mouseButton == 0;

        if (left) {
            if (this.mouseOver) {
                final double distance1 = Math.abs(mouseX - (selector1 + renderPercentage1 * 100));
                final double distance2 = Math.abs(mouseX - (selector2 + renderPercentage2 * 100));

                if (distance1 < distance2) {
                    grabbed1 = true;
                } else {
                    grabbed2 = true;
                }

                return true;
            }
        }

        valueDisplay.click(mouseX, mouseY, mouseButton);
        return false;
    }

    @Override
    public void released() {
        grabbed1 = grabbed2 = false;
    }

    @Override
    public void bloom() {
        if (this.position == null) {
            return;
        }

        final double startPositionX = selector1 + renderPercentage1 * 100;
        final double endPositionX = selector2 + renderPercentage2 * 100;
        final Color color = ColorUtil.withAlpha(getTheme().getFirstColor(), opacity);
        RenderUtil.roundedRectangle(startPositionX - grabberWidth / 2.0F, this.position.y, grabberWidth, grabberWidth, grabberWidth / 2f, color);
        RenderUtil.roundedRectangle(endPositionX - grabberWidth / 2.0F, this.position.y, grabberWidth, grabberWidth, grabberWidth / 2f, color);
    }

    @Override
    public void key(final char typedChar, final int keyCode) {
        // Return
        if (keyCode == 28) {
            final BoundsNumberValue numberValue = (BoundsNumberValue) this.value;
            String[] values = valueDisplay.getText().split(" ");

            if (valueDisplay.isEmpty() || values.length != 2 || values[0].replace(" ", "").isEmpty() || values[1].replace(" ", "").isEmpty()) {
                numberValue.setValue(numberValue.getDefaultValue());
                numberValue.setSecondValue(numberValue.getDefaultSecondValue());
            } else {
                numberValue.setValue(Double.parseDouble(values[0]));
                numberValue.setSecondValue(Double.parseDouble(values[1]));
            }

            valueDisplay.setSelected(false);
            this.updateSliders();
            return;
        }

        valueDisplay.key(typedChar, keyCode);
    }
}
