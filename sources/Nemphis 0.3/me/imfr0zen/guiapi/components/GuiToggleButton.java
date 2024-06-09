/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 */
package me.imfr0zen.guiapi.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import me.imfr0zen.guiapi.RenderUtil;
import me.imfr0zen.guiapi.components.GuiComponent;
import org.lwjgl.input.Mouse;

public class GuiToggleButton
implements GuiComponent {
    private boolean toggled;
    private int posX;
    private int posY;
    private String text;
    private ArrayList<ActionListener> clicklisteners = new ArrayList();

    public GuiToggleButton(String text) {
        this.text = text;
    }

    public void addClickListener(ActionListener actionlistener) {
        this.clicklisteners.add(actionlistener);
    }

    public boolean isToggled() {
        return this.toggled;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }

    @Override
    public void render(int posX, int posY, int width, int mouseX, int mouseY) {
        this.posX = posX;
        this.posY = posY;
        int w = RenderUtil.getWidth(this.text);
        int color = 1157627904;
        if (this.toggled) {
            color = -2474888;
            if (RenderUtil.isHovered(posX + w + 8, posY, 10, 10, mouseX, mouseY)) {
                color = Mouse.getEventButtonState() ? -5632952 : -3527576;
            }
        }
        int i = posX + w + 8;
        int i0 = posX + w + 18;
        int i1 = posY + 11;
        RenderUtil.drawRect(i + 1, posY + 2, i0, i1, color);
        RenderUtil.drawHorizontalLine(i, i0, posY + 1, -3527576);
        RenderUtil.drawHorizontalLine(i, i0, i1, -3527576);
        RenderUtil.drawVerticalLine(i, posY, i1, -3527576);
        RenderUtil.drawVerticalLine(i0, posY, i1, -3527576);
        RenderUtil.drawString(this.text, posX + 3, posY + 3, -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        int w = RenderUtil.getWidth(this.text);
        if (RenderUtil.isHovered(this.posX + w + 8, this.posY, 10, 10, mouseX, mouseY)) {
            this.toggled = !this.toggled;
            for (ActionListener listener : this.clicklisteners) {
                listener.actionPerformed(new ActionEvent(this, this.hashCode(), "click", System.currentTimeMillis(), 0));
            }
        }
    }

    @Override
    public void keyTyped(int keyCode, char typedChar) {
    }

    @Override
    public int getWidth() {
        return RenderUtil.getWidth(this.text) + 22;
    }

    @Override
    public int getHeight() {
        return 13;
    }
}

