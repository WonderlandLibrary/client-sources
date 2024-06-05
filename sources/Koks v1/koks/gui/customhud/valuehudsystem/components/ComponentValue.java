package koks.gui.customhud.valuehudsystem.components;

import koks.gui.customhud.Component;
import koks.gui.customhud.valuehudsystem.ValueHUD;
import koks.utilities.RenderUtils;
import koks.utilities.value.Value;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

/**
 * @author avox | lmao | kroko
 * @created on 06.09.2020 : 03:38
 */
public abstract class ComponentValue {

    private int x, y, width, height;

    private ValueHUD value;
    private koks.gui.customhud.Component component;

    private final RenderUtils renderUtils = new RenderUtils();
    private final Minecraft mc = Minecraft.getMinecraft();
    private final FontRenderer mcFontRenderer = Minecraft.getMinecraft().fontRendererObj;

    public ComponentValue(ValueHUD value) {
        this.value = value;
    }

    public abstract void drawScreen(int mouseX, int mouseY);

    public abstract void mouseClicked(int mouseX, int mouseY, int mouseButton);

    public boolean isHovering(int mouseX, int mouseY) {
        return mouseX > x - 2 && mouseX < x + width + 2 && mouseY > y - 2 && mouseY < y + height + 1;
    }

    public abstract void mouseReleased();

    public void setInformation(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public koks.gui.customhud.Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public ValueHUD getValue() {
        return value;
    }

    public void setValue(ValueHUD value) {
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public RenderUtils getRenderUtils() {
        return renderUtils;
    }

    public Minecraft getMc() {
        return mc;
    }

    public FontRenderer getMcFontRenderer() {
        return mcFontRenderer;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
