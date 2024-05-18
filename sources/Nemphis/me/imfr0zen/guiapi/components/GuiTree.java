/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 */
package me.imfr0zen.guiapi.components;

import java.util.List;
import me.imfr0zen.guiapi.Colors;
import me.imfr0zen.guiapi.RenderUtil;
import me.imfr0zen.guiapi.components.GuiComponent;
import org.lwjgl.input.Mouse;

public class GuiTree
implements GuiComponent {
    private boolean extended;
    private int posX;
    private int posY;
    private String text;
    private GuiComponent[] components;

    public /* varargs */ GuiTree(String text, GuiComponent ... components) {
        this.components = components;
        this.extended = false;
        this.text = text;
    }

    public GuiTree(String text, List<GuiComponent> components) {
        this(text, components.toArray(new GuiComponent[components.size()]));
    }

    @Override
    public void render(int posX, int posY, int width, int mouseX, int mouseY) {
        this.posX = posX;
        this.posY = posY;
        int color = 1157627904;
        if (this.extended) {
            color = Colors.buttonColorLight;
            if (RenderUtil.isHovered(posX + 2, posY, 10, posY + 11, mouseX, mouseY)) {
                color = Mouse.getEventButtonState() ? Colors.buttonColorDark : Colors.buttonColor;
            }
        }
        RenderUtil.drawRect(posX + 2, posY, posX + 13, posY + 11, color);
        RenderUtil.drawHorizontalLine(posX + 2, posX + 13, posY, -3527576);
        RenderUtil.drawHorizontalLine(posX + 2, posX + 13, posY + 11, -3527576);
        RenderUtil.drawVerticalLine(posX + 2, posY, posY + 11, -3527576);
        RenderUtil.drawVerticalLine(posX + 13, posY, posY + 11, -3527576);
        RenderUtil.drawString(this.text, posX + 16, posY + 3, 13158600);
        if (this.extended) {
            int height = 12;
            int i = 0;
            while (i < this.components.length) {
                this.components[i].render(posX + 10, posY + height, width, mouseX, mouseY);
                height += this.components[i].getHeight();
                ++i;
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && RenderUtil.isHovered(this.posX + 2, this.posY, 11, 11, mouseX, mouseY)) {
            boolean bl = this.extended = !this.extended;
        }
        if (this.extended) {
            int i = 0;
            while (i < this.components.length) {
                this.components[i].mouseClicked(mouseX, mouseY, mouseButton);
                ++i;
            }
        }
    }

    @Override
    public void keyTyped(int keyCode, char typedChar) {
        if (this.extended) {
            int i = 0;
            while (i < this.components.length) {
                this.components[i].keyTyped(keyCode, typedChar);
                ++i;
            }
        }
    }

    @Override
    public int getWidth() {
        int width = RenderUtil.getWidth(this.text) + 19;
        if (this.extended) {
            GuiComponent[] arrguiComponent = this.components;
            int n = arrguiComponent.length;
            int n2 = 0;
            while (n2 < n) {
                GuiComponent component = arrguiComponent[n2];
                width = Math.max(width, component.getWidth() + 8);
                ++n2;
            }
        }
        return width;
    }

    @Override
    public int getHeight() {
        int i = 13;
        if (this.extended) {
            int j = 0;
            while (j < this.components.length) {
                i += this.components[j].getHeight();
                ++j;
            }
        }
        return i;
    }
}

