package arsenic.utils.render;

import java.awt.Color;

import arsenic.utils.font.FontRendererExtension;
import net.minecraft.client.gui.GuiScreen;

public class RenderInfo {
    private int index = 0;
    private final int mouseX, mouseY;
    private final FontRendererExtension<?> fr;
    private final GuiScreen guiScreen;
    public final float[] STACK = new float[16];

    public RenderInfo(int mouseX, int mouseY, FontRendererExtension<?> fr, GuiScreen guiScreen) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.fr = fr;
        this.guiScreen = guiScreen;
    }

    public final int getIndex() { return index; }

    public final void setIndex(int index) { this.index = index; }

    public final int getMouseX() { return mouseX; }

    public final int getMouseY() { return mouseY; }

    public final Color getLighterColorByLevel(Color color) {
        return getColorByLevel(color, -index);
    }

    public final Color getDarkerColorByLevel(Color color) {
        return getColorByLevel(color, index);
    }

    public GuiScreen getGuiScreen() { return guiScreen; }

    private Color getColorByLevel(Color color, int level) {
        for (int i = 0; i < level; i++) { color = color.darker(); }
        return color;
    }

    public final FontRendererExtension<?> getFr() { return fr; }

}
