package club.bluezenith.ui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;

import java.util.function.Consumer;

import static club.bluezenith.util.font.FontUtil.rubikR30;
import static java.lang.String.format;

public class BorderlessCombobox extends GuiButton {
    private final String[] values;
    private String currentValue;
    private int currentIndex;
    private Consumer<String> valueChangeListener;
    FontRenderer fontRenderer = rubikR30;

    public BorderlessCombobox(int buttonId, int x, int y, String buttonText, String... values) {
        super(buttonId, x, y, buttonText);
        this.values = values;
        this.currentValue = values[0];
    }

    public BorderlessCombobox setFont(FontRenderer fontRenderer) {
        this.fontRenderer = fontRenderer;
        return this;
    }

    public BorderlessCombobox setValueChangeListener(Consumer<String> valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
        return this;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        this.drawModifiedButton(mc, mouseX, mouseY);
    }

    @Override
    protected void drawModifiedButton(Minecraft mc, int mouseX, int mouseY) {
        float textWidth = fontRenderer.getStringWidthF(this.displayString);
        String drawnString;

        fontRenderer.drawString(
                drawnString = format("§7%s: §f%s", this.displayString, this.values[currentIndex]),
                this.xPosition, this.yPosition,
                -1,true);
        textWidth += fontRenderer.getStringWidthF(drawnString);

        this.hovered = mouseX >= this.xPosition - 1 && mouseY >= this.yPosition && mouseX < this.xPosition + textWidth - 22 && mouseY < this.yPosition + 9;
        this.mouseDragged(mc, mouseX, mouseY);
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        final boolean wasPressedOn = this.visible && this.enabled && this.hovered;
        if(wasPressedOn)
        switchMode();
        return wasPressedOn;
    }

    private void switchMode() {
        this.currentIndex++;
        if(this.currentIndex >= this.values.length) this.currentIndex = 0;
        if(this.currentIndex < 0) this.currentIndex = 0;
        currentValue = this.values[currentIndex];
        if(this.valueChangeListener != null) this.valueChangeListener.accept(currentValue);
    }

    public String getCurrentValue() {
        return this.currentValue;
    }

    public int getCurrentIndex() {
        return this.currentIndex;
    }

    public BorderlessCombobox setCurrentIndex(int index) {
        if(index < 0 || index >= values.length) return this;
        this.currentIndex = index;
        return this;
    }
}
