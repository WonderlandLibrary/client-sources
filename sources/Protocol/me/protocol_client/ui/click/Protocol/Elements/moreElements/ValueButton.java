package me.protocol_client.ui.click.Protocol.Elements.moreElements;

import me.protocol_client.Protocol;
import me.protocol_client.thanks_slicky.properties.Value;
import me.protocol_client.ui.click.Protocol.Elements.Element;
import net.minecraft.client.Minecraft;

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
        Protocol.getGuiClick().getTheme().renderButton(getValuePrettyName(), getValue().getValue(), getX(), getY(), getWidth(), getHeight(), isOverElement(mouseX, mouseY), this);
    }

    @Override
    public void keyPressed(int key) {

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isOverElement(mouseX, mouseY) && mouseButton == 0) {
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
