package io.github.nevalackin.radium.gui.click.component;

import io.github.nevalackin.radium.gui.click.ClickGui;
import net.minecraft.client.gui.ScaledResolution;

import java.util.ArrayList;
import java.util.List;

public class Component {

    protected final List<Component> children = new ArrayList<>();
    private final Component parent;
    private final String name;
    private int x;
    private int y;
    private int width;
    private int height;

    public Component(Component parent,
                     String name,
                     int x, int y,
                     int width,
                     int height) {
        this.parent = parent;
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Component getParent() {
        return parent;
    }

    public void drawComponent(ScaledResolution scaledResolution,
                              int mouseX,
                              int mouseY) {
        for (Component child : children) {
            child.drawComponent(scaledResolution, mouseX, mouseY);
        }
    }

    public void onMouseClick(int mouseX,
                             int mouseY,
                             int button) {
        for (Component child : children) {
            child.onMouseClick(mouseX, mouseY, button);
        }
    }

    public void onMouseRelease(int button) {
        for (Component child : children) {
            child.onMouseRelease(button);
        }
    }

    public void onKeyPress(int keyCode) {
        for (Component child : children) {
            child.onKeyPress(keyCode);
        }
    }

    public String getName() {
        return name;
    }

    public int getX() {
        Component familyMember = parent;
        int familyTreeX = x;

        while (familyMember != null) {
            familyTreeX += familyMember.x;
            familyMember = familyMember.parent;
        }

        return familyTreeX;
    }

    public void setX(int x) {
        this.x = x;
    }

    protected boolean isHovered(int mouseX,
                                int mouseY) {
        int x;
        int y;
        return mouseX >= (x = getX()) && mouseY >= (y = getY()) && mouseX < x + getWidth() && mouseY < y + getHeight();
    }

    public int getY() {
        Component familyMember = parent;
        int familyTreeY = y;

        while (familyMember != null) {
            familyTreeY += familyMember.y;
            familyMember = familyMember.parent;
        }

        return familyTreeY;
    }

    protected int getBackgroundColor(boolean hovered) {
        return hovered ?
                ClickGui.getInstance().getPalette().getHoveredBackgroundColor().getRGB() :
                ClickGui.getInstance().getPalette().getPanelBackgroundColor().getRGB();
    }

    protected int getSecondaryBackgroundColor(boolean hovered) {
        return hovered ?
                ClickGui.getInstance().getPalette().getHoveredSecondaryBackgroundColor().getRGB() :
                ClickGui.getInstance().getPalette().getSecondaryBackgroundColor().getRGB();
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<Component> getChildren() {
        return children;
    }

}
