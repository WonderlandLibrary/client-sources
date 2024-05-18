package me.protocol_client.ui.click.Protocol.Elements.moreElements;

import java.text.DecimalFormat;

import me.protocol_client.Protocol;
import me.protocol_client.thanks_slicky.properties.ClampedValue;
import me.protocol_client.ui.click.Protocol.Elements.Element;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Mouse;

public class ValueSlider extends Element {
    private final ClampedValue<Float> sliderValue;
    private double amountScrolled = 0.0;
    private boolean dragging = false;
    private final static DecimalFormat decimalFormat = new DecimalFormat("#.#");

    private static Minecraft mc = Minecraft.getMinecraft();
    private final String valuePrettyName;

    public ValueSlider(ClampedValue<Float> value, String valuePrettyName, float x, float y, float width, float height) {
        super(x, y, width, height);

        this.sliderValue = value;
        this.valuePrettyName = valuePrettyName;

        amountScrolled = toFloat(value.getDefault() / value.getMax()).doubleValue();
        sliderValue.setSliderX((float)toFloat(value.getDefault() / value.getMax()).doubleValue());
    }

    @Override
    public void drawElement(int mouseX, int mouseY) {
        Protocol.getGuiClick().getTheme().renderSlider(getValuePrettyName(), sliderValue.getValue(), getX(), getY(), getWidth(), getHeight(), getValue().getSliderX(), isOverElement(mouseX, mouseY), this);


        if ((isOverElement(mouseX, mouseY) || dragging) && Mouse.isButtonDown(0)) {
            dragging = true;

            if (mouseX > getX()) {
                double diff = mouseX - getX();
                amountScrolled = toFloat(diff / getWidth());
                amountScrolled = toFloat(amountScrolled < 0 ? 0 : amountScrolled > 1 ? 1 : amountScrolled);

                sliderValue.setSliderX(mouseX - getX());

                if (sliderValue.getSliderX() > getWidth())
                    sliderValue.setSliderX(getWidth());
                if (sliderValue.getSliderX() < 2)
                    sliderValue.setSliderX(2);

                final float calculatedValue = toFloat(amountScrolled * (sliderValue.getMax() - sliderValue.getMin()));
                sliderValue.setValue(calculatedValue + sliderValue.getMin());
            }

        } else {
            dragging = false;
        }
    }
    public static float onlyTwoDecimalPlaces(String number) {
        StringBuilder sbFloat = new StringBuilder(number);
        int start = sbFloat.indexOf(".");
        if (start < 0) {
            return new Float(sbFloat.toString());
        }
        int end = start+3;
        if((end)>(sbFloat.length()-1)) end = sbFloat.length();

        String twoPlaces = sbFloat.substring(start, end);
        sbFloat.replace(start, sbFloat.length(), twoPlaces);
        return new Float(sbFloat.toString());
    }
    @Override
    public void keyPressed(int key) {

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isOverElement(mouseX, mouseY) && mouseButton == 0) {
        }
    }

    public ClampedValue<Float> getValue() {
        return sliderValue;
    }

    public String getValuePrettyName() {
        return valuePrettyName;
    }

    @Override
    public void onGuiClosed() {
    }

    public Float toFloat(Object value) {
        if (value instanceof Integer) {
            Integer val = (Integer) value;
            return (float)val;
        } else if (value instanceof Double) {
            Double val = (Double) value;
            return (float)val.floatValue();
        } else if (value instanceof Long) {
            Long val = (Long) value;

            return (float)val;
        } else if (value instanceof Short) {
            Short val = (Short) value;

            return (float)val;
        } else if (value instanceof Float) {
            return (Float) value;
        }
        return null;
    }
}