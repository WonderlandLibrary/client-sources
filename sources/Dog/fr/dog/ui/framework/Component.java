package fr.dog.ui.framework;

import fr.dog.util.InstanceAccess;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Component implements InstanceAccess {
    protected float x, y, width, height;
    protected boolean expanded, hovered;

    public void init() { /* */ };
    public void render(final int mouseX, final int mouseY) { /* */ };

    public boolean click(final int mouseX, final int mouseY, final int button) {
        hovered = isHovered(mouseX, mouseY);
        return false;
    };

    public void release(final int mouseX, final int mouseY, final int state) { /* */ };
    public void type(final char typedChar, final int keyCode) { /* */ };

    public boolean isHovered(float mouseX, float mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height+1;
    }
}
