package club.strifeclient.ui.implementations.hud.clickgui;

import club.strifeclient.ui.GuiBase;
import club.strifeclient.util.misc.MinecraftUtil;
import club.strifeclient.util.rendering.RenderUtil;
import lombok.Getter;

@Getter
public abstract class Component<T> extends MinecraftUtil implements GuiBase {
    protected T object;
    protected Theme theme;
    protected Component<?> parent;
    protected float x, y, origWidth, origHeight, width, height;
    protected boolean visible;

    public Component(T object, Theme theme, Component<?> parent, float x, float y, float width, float height) {
        this.object = object;
        this.theme = theme;
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.origWidth = width;
        this.origHeight = height;
    }

    public boolean isVisible() {
        return parent == null || visible && parent.isVisible();
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return RenderUtil.isHovered(x + 1, y + 1, width - 1, height - 1, mouseX, mouseY);
    }
}
