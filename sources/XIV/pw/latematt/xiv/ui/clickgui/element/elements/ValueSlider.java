package pw.latematt.xiv.ui.clickgui.element.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.ui.clickgui.element.Element;
import pw.latematt.xiv.value.ClampedValue;

/**
 * @author Rederpz
 * @author TehNawnanon
 */
public class ValueSlider extends Element {
    private final ClampedValue<Float> sliderValue;
    private double amountScrolled = 0.0;
    private boolean dragging = false;

    private static Minecraft mc = Minecraft.getMinecraft();
    private final String valuePrettyName;

    public ValueSlider(ClampedValue<Float> value, String valuePrettyName, float x, float y, float width, float height) {
        super(x, y, width, height);

        this.sliderValue = value;
        this.valuePrettyName = valuePrettyName;

        amountScrolled = toFloat(value.getDefault() / value.getMax()).doubleValue();
        sliderValue.setSliderX(sliderValue.getValue() / width);
    }

    @Override
    public void drawElement(int mouseX, int mouseY) {
        XIV.getInstance().getGuiClick().getTheme().renderSlider(getValuePrettyName(), sliderValue.getValue(), getX(), getY(), getWidth(), getHeight(), getValue().getSliderX(), isOverElement(mouseX, mouseY), this);


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

    @Override
    public void keyPressed(int key) {

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isOverElement(mouseX, mouseY) && mouseButton == 0) {
            mc.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 1.0F));
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

            return val.floatValue();
        } else if (value instanceof Double) {
            Double val = (Double) value;

            return val.floatValue();
        } else if (value instanceof Long) {
            Long val = (Long) value;

            return val.floatValue();
        } else if (value instanceof Short) {
            Short val = (Short) value;

            return val.floatValue();
        } else if (value instanceof Float) {
            return (Float) value;
        }
        return null;
    }
}