package koks.gui.clickgui.elements;

import koks.Koks;
import koks.utilities.value.values.NumberValue;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.text.DecimalFormat;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 10:09
 */
public class ElementSlider extends Element {

    public boolean dragging;
    public final NumberValue numberValue;
    private String type;
    private NumberType numberType;

    public ElementSlider(NumberValue value) {
        super(value);
        this.numberValue = value;

        if (numberValue.getDefaultValue() instanceof Long) {
            if (numberValue.getMaxValue().longValue() > 999)
                type = "seconds";
            else type = "ms";
            this.numberType = NumberType.LONG;
        } else if (numberValue.getDefaultValue() instanceof Integer) {
            type = "";
            this.numberType = NumberType.INTEGER;
        } else if (numberValue.getDefaultValue() instanceof Float) {
            type = "";
            this.numberType = NumberType.FLOAT;
        } else if (numberValue.getDefaultValue() instanceof Double) {
            type = "";
            this.numberType = NumberType.DOUBLE;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        double sliderCurrent = (this.numberValue.getDefaultValue().doubleValue() - this.numberValue.getMinValue().doubleValue()) / (this.numberValue.getMaxValue().doubleValue() - this.numberValue.getMinValue().doubleValue());

        getRenderUtils().drawRect(7, getX(), getY() + 2, getX() + getWidth(), getY() + getHeight() - 2, new Color(40, 39, 42, 255));
        getRenderUtils().drawRect(7, getX(), getY() + 2, (float) (getX() + (sliderCurrent * getWidth())), getY() + getHeight() - 2, Koks.getKoks().client_color);

        getRenderUtils().drawOutlineRect(getX(), getY() + 2, (float) (getX() + getWidth()), getY() + getHeight() - 2, 1, Color.black);

        GL11.glPushMatrix();
        GL11.glTranslated(getX() + 9, getY() + getHeight() / 2 - getFontRenderer().FONT_HEIGHT / 2 + 1.5F, 0);
        GL11.glScaled(0.75, 0.75, 0.75);
        getFontRenderer().drawStringWithShadow(numberValue.getName() + " " + (type.equalsIgnoreCase("seconds") ? this.numberValue.getDefaultValue().doubleValue() / 1000 + " " + type : this.numberValue.getDefaultValue() + " " + type), 0, 0, -1);
        GL11.glPopMatrix();
        updateValueSlider(mouseX);
    }

    public void updateValueSlider(double x) {
        if (this.dragging) {

            switch (numberType) {
                case INTEGER:
                    int intValue = (int) Math.round(Math.max(Math.min((x - this.getX()) / this.getWidth() * (this.numberValue.getMaxValue().intValue() - this.numberValue.getMinValue().intValue()) + this.numberValue.getMinValue().intValue(), this.numberValue.getMaxValue().intValue()), this.numberValue.getMinValue().intValue()));
                    this.numberValue.setDefaultValue(intValue);
                    break;
                case LONG:
                    long longValue = (Math.round(Math.max(Math.min((x - this.getX()) / this.getWidth() * (this.numberValue.getMaxValue().longValue() - this.numberValue.getMinValue().longValue()) + this.numberValue.getMinValue().longValue(), this.numberValue.getMaxValue().longValue()), this.numberValue.getMinValue().longValue()) * 100) / 100);
                    this.numberValue.setDefaultValue(longValue);
                    break;
                case DOUBLE:
                    double doubleValue = (Math.round(Math.max(Math.min((x - this.getX()) / this.getWidth() * (this.numberValue.getMaxValue().doubleValue() - this.numberValue.getMinValue().doubleValue()) + this.numberValue.getMinValue().doubleValue(), this.numberValue.getMaxValue().doubleValue()), this.numberValue.getMinValue().doubleValue()) * 100.0D) / 100.0D);
                    this.numberValue.setDefaultValue(doubleValue);
                    break;
                case FLOAT:
                    float floatValue = (Math.round(Math.max(Math.min((x - this.getX()) / this.getWidth() * (this.numberValue.getMaxValue().floatValue() - this.numberValue.getMinValue().floatValue()) + this.numberValue.getMinValue().floatValue(), this.numberValue.getMaxValue().floatValue()), this.numberValue.getMinValue().floatValue()) * 100.0F) / 100.0F);
                    this.numberValue.setDefaultValue(floatValue);
                    break;
            }

        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovering(mouseX, mouseY) && mouseButton == 0) {
            this.dragging = true;
        }
    }

    @Override
    public void mouseReleased() {
        this.dragging = false;
    }

    public boolean isHovering(int mouseX, int mouseY) {
        return mouseX > getX() && mouseX < getX() + getWidth() && mouseY > getY() && mouseY < getY() + getHeight() - 2;
    }

    @Override
    public void keyTyped(int keyCode) {
    }

    public enum NumberType {
        INTEGER, FLOAT, DOUBLE, LONG;
    }

}
