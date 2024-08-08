package lol.point.returnclient.ui.cgui.dropdown.comp;

import lol.point.returnclient.theme.Theme;
import lol.point.returnclient.util.MinecraftInstance;
import lol.point.returnclient.util.render.FastFontRenderer;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class Component implements MinecraftInstance {

    private float x, y, width, height;
    private FastFontRenderer font;
    private Theme theme;

    public abstract void drawScreen(int mouseX, int mouseY);

    public abstract void mouseClicked(int mouseX, int mouseY, int mouseButton);

    public abstract void keyTyped(char typedChar, int keyCode);

    public void setSize(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

}
