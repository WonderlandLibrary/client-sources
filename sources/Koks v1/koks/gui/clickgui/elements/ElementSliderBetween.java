package koks.gui.clickgui.elements;

import koks.Koks;
import koks.utilities.value.Value;
import koks.utilities.value.values.NumberValue;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @author avox | lmao | kroko
 * @created on 04.09.2020 : 08:50
 */
public class ElementSliderBetween extends Element {

    private final NumberValue numberValue;

    public boolean dragging;
    private String type;
    private ElementSlider.NumberType numberType;

    public ElementSliderBetween(NumberValue value) {
        super(value);
        this.numberValue = value;
        if (numberValue.getDefaultValue() instanceof Long) {
            type = "ms";
            this.numberType = ElementSlider.NumberType.LONG;
        } else if (numberValue.getDefaultValue() instanceof Integer) {
            type = "";
            this.numberType = ElementSlider.NumberType.INTEGER;
        } else if (numberValue.getDefaultValue() instanceof Float) {
            type = "";
            this.numberType = ElementSlider.NumberType.FLOAT;
        } else if (numberValue.getDefaultValue() instanceof Double) {
            type = "";
            this.numberType = ElementSlider.NumberType.DOUBLE;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {

        double current = ((numberValue.getDefaultValue().doubleValue() - numberValue.getMinValue().doubleValue()) / (numberValue.getMaxValue().doubleValue() - numberValue.getMinValue().doubleValue()));
        double currentMin = (numberValue.getMinDefaultValue().doubleValue() - numberValue.getMinValue().doubleValue()) / (numberValue.getMaxValue().doubleValue() - numberValue.getMinValue().doubleValue());

        getRenderUtils().drawRect(7, getX(), getY() + 2, getX() + getWidth(), getY() + getHeight() - 2, new Color(40, 39, 42, 255));
        getRenderUtils().drawRect(7, (int) (getX() + currentMin * getWidth()), getY() + 2, (int) (getX() + current * getWidth()), getY() + getHeight() - 2, Koks.getKoks().client_color);

        GL11.glPushMatrix();
        GL11.glTranslated(getX() + 9, getY() + getHeight() / 2 - getFontRenderer().FONT_HEIGHT / 2 + 1.5F, 0);
        GL11.glScaled(0.75, 0.75, 0.75);
        getFontRenderer().drawStringWithShadow(numberValue.getName() + " " + numberValue.getMinDefaultValue() + ":" + numberValue.getDefaultValue() + " " + type, 0, 0, -1);
        GL11.glPopMatrix();

        getRenderUtils().drawOutlineRect(getX(), getY() + 2, (float) (getX() + getWidth()), getY() + getHeight() - 2, 1, Color.black);

        updateValueSlider(mouseX);
    }

    public void updateValueSlider(double x) {
        if (this.dragging) {

            double percentBar = (numberValue.getDefaultValue().floatValue() - numberValue.getMinValue().floatValue()) / (numberValue.getMaxValue().floatValue() - numberValue.getMinValue().floatValue());
            double percentBarMin = (numberValue.getMinDefaultValue().floatValue() - numberValue.getMinValue().floatValue()) / (numberValue.getMaxValue().floatValue() - numberValue.getMinValue().floatValue());
            double x1 = this.getX() + (percentBar * getWidth());
            double x2 = this.getX() + (percentBarMin * getWidth());
            double dist = (x1 - x2);
            double middle = dist / 2;

            if (this.dragging && !numberValue.getDefaultValue().equals(numberValue.getMinDefaultValue())) {
                if (x <= x1 - middle) {
                    switch (numberType) {
                        case INTEGER:
                            int intValue = (int) Math.round(Math.max(Math.min((x - this.getX()) / this.getWidth() * (this.numberValue.getMaxValue().intValue() - this.numberValue.getMinValue().intValue()) + this.numberValue.getMinValue().intValue(), this.numberValue.getMaxValue().intValue()), this.numberValue.getMinValue().intValue()));
                            this.numberValue.setMinDefaultValue(intValue);
                            break;
                        case LONG:
                            long longValue = (Math.round(Math.max(Math.min((x - this.getX()) / this.getWidth() * (this.numberValue.getMaxValue().longValue() - this.numberValue.getMinValue().longValue()) + this.numberValue.getMinValue().longValue(), this.numberValue.getMaxValue().longValue()), this.numberValue.getMinValue().longValue()) * 100) / 100);
                            this.numberValue.setMinDefaultValue(longValue);
                            break;
                        case DOUBLE:
                            double doubleValue = (Math.round(Math.max(Math.min((x - this.getX()) / this.getWidth() * (this.numberValue.getMaxValue().doubleValue() - this.numberValue.getMinValue().doubleValue()) + this.numberValue.getMinValue().doubleValue(), this.numberValue.getMaxValue().doubleValue()), this.numberValue.getMinValue().doubleValue()) * 100.0D) / 100.0D);
                            this.numberValue.setMinDefaultValue(doubleValue);
                            break;
                        case FLOAT:
                            float floatValue = (Math.round(Math.max(Math.min((x - this.getX()) / this.getWidth() * (this.numberValue.getMaxValue().floatValue() - this.numberValue.getMinValue().floatValue()) + this.numberValue.getMinValue().floatValue(), this.numberValue.getMaxValue().floatValue()), this.numberValue.getMinValue().floatValue()) * 100.0F) / 100.0F);
                            this.numberValue.setMinDefaultValue(floatValue);
                            break;
                    }
                } else {
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
            } else if (!this.dragging && numberValue.getDefaultValue().floatValue() < numberValue.getMinDefaultValue().floatValue()) {
                float min = numberValue.getMinDefaultValue().floatValue();
                float max = numberValue.getDefaultValue().floatValue();
                numberValue.setMinDefaultValue(max);
                numberValue.setDefaultValue(min);
            } else if (this.dragging && numberValue.getDefaultValue().equals(numberValue.getMinDefaultValue())) {
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
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovering(mouseX, mouseY) && mouseButton == 0) {
            this.dragging = true;
        }
    }

    @Override
    public void mouseReleased() {
        dragging = false;
    }

    public boolean isHovering(int mouseX, int mouseY) {
        return mouseX > getX() && mouseX < getX() + getWidth() && mouseY > getY() && mouseY < getY() + getHeight() - 2;
    }

    @Override
    public void keyTyped(int keyCode) {

    }


}
