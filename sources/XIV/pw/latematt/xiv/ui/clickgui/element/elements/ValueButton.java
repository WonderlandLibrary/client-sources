package pw.latematt.xiv.ui.clickgui.element.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.ui.clickgui.element.Element;
import pw.latematt.xiv.value.Value;

public class ValueButton extends Element {
    private static Minecraft mc = Minecraft.getMinecraft();
    private final Value<Boolean> value;
    private final String valuePrettyName;

    public ValueButton(Value<Boolean> value, String valuePrettyName, float x, float y, float width, float height) {
        super(x, y, width, height);

        this.value = value;
        this.valuePrettyName = valuePrettyName;
    }

    @Override
    public void drawElement(int mouseX, int mouseY) {
        XIV.getInstance().getGuiClick().getTheme().renderButton(getValuePrettyName(), getValue().getValue(), getX(), getY(), getWidth(), getHeight(), isOverElement(mouseX, mouseY), this);
    }

    @Override
    public void keyPressed(int key) {

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isOverElement(mouseX, mouseY) && mouseButton == 0) {
            mc.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 1.0F));
            value.setValue(!value.getValue());
        }
    }

    public Value<Boolean> getValue() {
        return value;
    }

    public String getValuePrettyName() {
        return valuePrettyName;
    }

    @Override
    public void onGuiClosed() {
    }
}
