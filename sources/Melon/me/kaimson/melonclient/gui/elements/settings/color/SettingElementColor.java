package me.kaimson.melonclient.gui.elements.settings.color;

import me.kaimson.melonclient.gui.elements.settings.*;
import me.kaimson.melonclient.utils.*;
import java.util.function.*;
import me.kaimson.melonclient.gui.*;
import me.kaimson.melonclient.gui.elements.*;
import java.awt.*;
import me.kaimson.melonclient.gui.utils.*;

public class SettingElementColor extends SettingElement
{
    public int color;
    public int alpha;
    public final Checkbox chroma;
    public final Slider chromaSpeed;
    public final ColorPane colorPane;
    public final AlphaPane alphaPane;
    public final HuePane huePane;
    private final Consumer update;
    public boolean expanded;
    
    public SettingElementColor(final int x, final int y, final int width, final int height, final int xOffset, final int yOffset, final Setting setting, final Consumer update, final BiConsumer<Setting, SettingElement> consumer, final GuiScreen parent) {
        super(x, y, width, height, xOffset, yOffset, setting, consumer, parent);
        this.expanded = false;
        this.front = true;
        this.chroma = new Checkbox(this.getX() - 132 - 50, this.getY(), 10, 10, "Chroma", setting.getColorObject().isChroma(), null);
        this.chromaSpeed = new Slider(this.getX() - 132 - 70, this.getY() + 12, 50, 5, 0.0f, 80.0f, 1.0f, (float)setting.getColorObject().getChromaSpeed(), "Chroma Speed", null, parent);
        this.colorPane = new ColorPane(this.getX() - 28 - 4 - 100, this.getY(), 100, 48);
        this.alphaPane = new AlphaPane(this.getX() - 28, this.getY(), 10, 48);
        this.huePane = new HuePane(this.getX() - 14, this.getY(), 10, 48);
        this.update = update;
        this.color = new Color(setting.getColor(), true).getRGB();
        final int[] hsv = GuiUtils.rgbToHsv(this.color & 0xFFFFFF);
        this.huePane.hue = ((hsv[0] == -1) ? 0 : hsv[0]);
        this.colorPane.saturation = hsv[1];
        this.colorPane.value = hsv[2];
        this.alphaPane.alpha = GuiUtils.getAlpha(this.color);
    }
    
    @Override
    public void renderElement(final float partialTicks) {
        this.chroma.y = this.getY();
        this.chromaSpeed.y = this.getY() + 12;
        this.colorPane.y = this.getY();
        this.alphaPane.y = this.getY();
        this.huePane.y = this.getY();
        this.color = GuiUtils.hsvToRgb(this.huePane.hue, this.colorPane.saturation, this.colorPane.value);
        FontUtils.drawString(String.format("#%02x%02x%02x", GuiUtils.getColor(this.color).getRed(), GuiUtils.getColor(this.color).getGreen(), GuiUtils.getColor(this.color).getBlue()), (float)(this.getX() + 13), this.getY() + 1.5f, 16777215);
        this.alpha = this.alphaPane.alpha;
        if (this.expanded) {
            this.chroma.render(this.mouseX, this.mouseY, partialTicks);
            this.chromaSpeed.render(this.mouseX, this.mouseY, partialTicks);
            this.colorPane.renderPane(this.huePane.hue, this.mouseX, this.mouseY);
            this.alphaPane.renderPane(this.color, this.mouseX, this.mouseY);
            this.huePane.renderPane(this.mouseX, this.mouseY);
            this.consumer.accept(this.setting, this);
        }
    }
    
    @Override
    public boolean mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        this.chroma.mouseClicked(mouseX, mouseY, mouseButton);
        this.chromaSpeed.mouseClicked(mouseX, mouseY, mouseButton);
        this.colorPane.mouseClicked(mouseX, mouseY, mouseButton);
        this.alphaPane.mouseClicked(mouseX, mouseY, mouseButton);
        this.huePane.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.enabled && this.hovered) {
            this.expanded = !this.expanded;
            this.update.apply(this, this.expanded);
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        super.mouseReleased(mouseX, mouseY, state);
        this.chroma.mouseReleased(mouseX, mouseY, state);
        this.chromaSpeed.mouseReleased(mouseX, mouseY, state);
        this.colorPane.mouseReleased();
        this.alphaPane.mouseReleased();
        this.huePane.mouseReleased();
    }
    
    @Override
    public void renderBackground(final float partialTicks) {
        GLRectUtils.drawRoundedRect((float)this.getX(), (float)this.getY(), (float)(this.getX() + this.width), (float)(this.getY() + this.height), 2.0f, this.setting.getColor());
        GLRectUtils.drawRoundedOutline(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, 2.0f, 2.0f, new Color(255, 255, 255, 100).getRGB());
    }
    
    @FunctionalInterface
    public interface Consumer
    {
        void apply(final SettingElementColor p0, final Boolean p1);
    }
}
