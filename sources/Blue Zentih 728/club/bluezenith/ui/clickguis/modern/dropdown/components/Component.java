package club.bluezenith.ui.clickguis.modern.dropdown.components;

import club.bluezenith.ui.clickgui.ClickGui;
import net.minecraft.client.gui.ScaledResolution;

public abstract class Component {
    public String name = "Unknown component";

    public boolean shown;

    protected float x, y, width, height;

    public Component(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public abstract float getWidth();

    public abstract float getHeight();

    public abstract float getX();

    public abstract float getY();

    public abstract void draw(int mouseX, int mouseY, ScaledResolution scaledResolution);

    public abstract void mouseClicked(int mouseX, int mouseY, int mouseButton);

    public abstract void mouseReleased(int mouseX, int mouseY, int state);

    public abstract void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick);

    public abstract boolean isMouseOver(int mouseX, int mouseY);

    public boolean isAcceptingKeypresses() {
        return false;
    }

    public boolean shouldLockDragging() {
        return false;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void moveTo(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void addToPosition(float addX, float addY) {
        this.x += addX;
        this.y += addY;
    }

    public void keyTyped(char key, int keyCode) {}

    public Component setName(String name) {
        this.name = name;
        return this;
    }

    protected boolean isMouseInBounds(int mouseX, int mouseY) {
        return ClickGui.i(mouseX, mouseY, getX(), getY(), getX() + getWidth(), getY() + getHeight());
    }

    public void onGuiClosed() {

    }

    public boolean shouldUpdateWidth() {
        return false;
    }
}
