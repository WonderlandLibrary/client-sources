package net.augustus.material.button.values;

import net.augustus.events.EventClickGui;
import net.augustus.font.UnicodeFontRenderer;
import net.augustus.material.Main;
import net.augustus.material.Tab;
import net.augustus.material.button.Button;
import net.augustus.settings.DoubleValue;
import net.augustus.settings.Setting;
import net.augustus.utils.skid.tomorrow.ColorUtils;
import net.augustus.utils.skid.tomorrow.RenderUtil;
import net.lenni0451.eventapi.manager.EventManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class BNumbers extends Button {
    private static UnicodeFontRenderer arial16B;
    private static UnicodeFontRenderer arial16;
    static {
        try {
            arial16B = new UnicodeFontRenderer(Font.createFont(0, BNumbers.class.getResourceAsStream("/ressources/ArialB.ttf")).deriveFont(18F));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            arial16 = new UnicodeFontRenderer(Font.createFont(0, BNumbers.class.getResourceAsStream("/ressources/arial.ttf")).deriveFont(16F));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public BNumbers(float x, float y, Setting v, Tab moduleTab) {
        super(x, y, v, moduleTab);
    }

    @Override
    public void drawButton(float mouseX, float mouseY) {
        arial16.drawString(v.getName() + ":" + this.round(((DoubleValue) v).getValue(), ((DoubleValue) v).getDecimalPlaces()), x, y - 4, new Color(255, 255, 255).getRGB());

        double visibleRange = ((DoubleValue) v).getMaxValue() - ((DoubleValue) v).getMinValue();
        double percentage = (((DoubleValue) v).getValue() - ((DoubleValue) v).getMinValue()) / visibleRange;

        RenderUtil.drawRect(x + 5, y + 5, x + 65, y + 6, ColorUtils.reAlpha(Main.clientColor.getRGB(), 0.6f));
        RenderUtil.drawRect(x + 5, y + 5, x + 5 + (int) (percentage * 60), y + 6, Main.clientColor.getRGB());
        RenderUtil.drawCircle((float) (x + 5 + percentage * 60), y + 5.5f, 3, Main.clientColor.getRGB());

        if (Main.isHovered(x + 5, y + 4, x + 65, y + 7, mouseX, mouseY)) {
            if (Mouse.isButtonDown(0)) {
                drag = true;
            } else {
                drag = false;
            }

            if (drag) {
                double reach = mouseX - (x + 5);
                double percent = reach / 60f;
                double newValue = ((DoubleValue) v).getMinValue() + (percent * visibleRange);
                newValue = Math.max(((DoubleValue) v).getMinValue(), Math.min(newValue, ((DoubleValue) v).getMaxValue()));
                ((DoubleValue) v).setValue(this.round(newValue, ((DoubleValue) v).getDecimalPlaces()));
                EventManager.call(new EventClickGui());
            }

            double stepSize = 1.0 / Math.pow(10, ((DoubleValue) v).getDecimalPlaces());
            if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
                ((DoubleValue) v).setValue(Math.min(((DoubleValue) v).getValue() + stepSize, ((DoubleValue) v).getMaxValue()));
            } else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
                ((DoubleValue) v).setValue(Math.max(((DoubleValue) v).getValue() - stepSize, ((DoubleValue) v).getMinValue()));
            }
        }
    }


    /*
    @Override
    public void drawButton(float mouseX, float mouseY) {
        arial16.drawString(v.getName() + ":" + this.round(((DoubleValue) v).getValue(), ((DoubleValue) v).getDecimalPlaces()), x, y - 4, new Color(255, 255, 255).getRGB());

//        arial16B.drawString("-", x, y + 3, new Color(50, 50, 50).getRGB());
//        arial16B.drawString("+", x + 65, y + 3, new Color(50, 50, 50).getRGB());

        RenderUtil.drawRect(x + 5, y + 5, x + 65, y + 6, ColorUtils.reAlpha(Main.clientColor.getRGB(), 0.6f));
        animation = (float) animationUtils.animate(60 * (((Number) ((DoubleValue)v).getValue()).floatValue() / ((DoubleValue)v).getMaxValue()), animation, 0.05f);
        RenderUtil.drawRect(x + 5, y + 5, x + 5 + animation, y + 6, Main.clientColor.getRGB());
        RenderUtil.drawCircle((float) (x + 5 + animation - ((DoubleValue)v).getMinValue()), y + 5.5f, 3, Main.clientColor.getRGB());

        if (Main.isHovered((float) (x + 5 - ((DoubleValue)v).getMinValue()), y + 4, x + 65, y + 7, mouseX, mouseY) && Mouse.isButtonDown(0)) {
            EventManager.call(new EventClickGui());
            drag = true;
        } else if (!Mouse.isButtonDown(0)) {
            drag = false;
        }

        if (drag) {
            double reach = mouseX - (x + 5);
            double percent = reach / 60f;
            double val = (((DoubleValue)v).getMaxValue() - ((DoubleValue)v).getMinValue()) * percent;
            if (val > ((DoubleValue)v).getMinValue() && val < ((DoubleValue)v).getMaxValue()) {
                ((DoubleValue)v).setValue(this.round(val, ((DoubleValue) v).getDecimalPlaces()));
            }

            if(val < ((DoubleValue)v).getMinValue()){
                ((DoubleValue)v).setValue(((DoubleValue)v).getMinValue());
            }else if(val > ((DoubleValue)v).getMaxValue()){
                ((DoubleValue)v).setValue(((DoubleValue)v).getMaxValue());
            }
        }

    }*/

    @Override
    public void mouseClicked(float mouseX, float mouseY) {
        super.mouseClicked(mouseX, mouseY);
    }
    private double round(double value, int decimalPoints) {
        if (decimalPoints == 0) {
            return (int)value;
        } else {
            double d = Math.pow(10.0, decimalPoints);
            return (double)Math.round(value * d) / d;
        }
    }
}
