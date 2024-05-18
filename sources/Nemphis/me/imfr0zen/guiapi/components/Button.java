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
import java.util.Collection;
import me.imfr0zen.guiapi.ClickGui;
import me.imfr0zen.guiapi.GuiFrame;
import me.imfr0zen.guiapi.RenderUtil;
import me.imfr0zen.guiapi.components.GuiComponent;
import me.imfr0zen.guiapi.listeners.ExtendListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.input.Mouse;

public class Button
implements GuiComponent {
    public static int extendedId = -1;
    private int id;
    private int textWidth;
    private int width;
    private int posX;
    private int posY;
    public static int color = -12566464;
    private String text;
    private ArrayList<ActionListener> clicklisteners = new ArrayList();
    private ArrayList<GuiComponent> guicomponents = new ArrayList();

    public Button(String text) {
        this.text = text;
        this.textWidth = ClickGui.FONTRENDERER.getStringWidth(this.getText());
        this.id = ++ClickGui.currentId;
    }

    public void addClickListener(ActionListener actionlistener) {
        this.clicklisteners.add(actionlistener);
    }

    public void addExtendListener(ExtendListener listener) {
        listener.addComponents();
        this.guicomponents.addAll(listener.getComponents());
    }

    public String getText() {
        return this.text;
    }

    public int getButtonId() {
        return this.id;
    }

    @Override
    public void render(int posX, int posY, int width, int mouseX, int mouseY) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        int height = this.getHeight();
        if (RenderUtil.isHovered(posX, posY, width, height, mouseX, mouseY)) {
            color = -2135286696;
            if (Mouse.getEventButtonState()) {
                color = -2136339384;
            }
        }
        RenderUtil.drawRect(posX, posY, posX + width - 3, posY + height, -2134234008);
        RenderUtil.drawString(this.text, posX + 50 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.text) / 2, posY + 2, -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (GuiFrame.draggingId == -1 && RenderUtil.isHovered(this.posX, this.posY, this.width, this.getHeight(), mouseX, mouseY)) {
            if (mouseButton == 1) {
                extendedId = extendedId != this.id ? this.id : -1;
            } else if (mouseButton == 0) {
                for (ActionListener listener : this.clicklisteners) {
                    listener.actionPerformed(new ActionEvent(this, this.id, "click", System.currentTimeMillis(), 0));
                }
            }
        }
        if (extendedId == this.id) {
            for (GuiComponent component : this.guicomponents) {
                component.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    @Override
    public void keyTyped(int keyCode, char typedChar) {
        if (extendedId == this.id) {
            for (GuiComponent component : this.guicomponents) {
                component.keyTyped(keyCode, typedChar);
            }
        }
    }

    @Override
    public int getWidth() {
        return this.textWidth + 5;
    }

    @Override
    public int getHeight() {
        return ClickGui.FONTRENDERER.FONT_HEIGHT + 3;
    }

    public ArrayList<GuiComponent> getComponents() {
        return this.guicomponents;
    }
}

