/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 */
package me.imfr0zen.guiapi;

import java.io.PrintStream;
import java.util.ArrayList;
import me.imfr0zen.guiapi.Frame;
import me.imfr0zen.guiapi.RenderUtil;
import me.imfr0zen.guiapi.components.Button;
import me.imfr0zen.guiapi.components.GuiComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.input.Mouse;

public class GuiFrame
implements Frame {
    public static int draggingId;
    private boolean visible;
    private boolean expanded;
    private boolean dragging;
    private int id;
    private int posX;
    private int posY;
    private int prevPosX;
    private int prevPosY;
    private String title;
    private ArrayList<Button> buttons = new ArrayList();

    public GuiFrame(String title, int posX, int posY) {
        this(title, posX, posY, true, true);
    }

    public GuiFrame(String title, int posX, int posY, boolean visible, boolean expanded) {
        this.title = title;
        this.posX = posX;
        this.posY = posY;
        this.visible = visible;
        this.expanded = expanded;
        this.id = ++me.imfr0zen.guiapi.ClickGui.currentId;
    }

    public void addButton(Button button) {
        if (!this.buttons.contains(button)) {
            this.buttons.add(button);
        }
    }

    public int getButtonId() {
        return this.id;
    }

    @Override
    public void init() {
    }

    @Override
    public void render(int mouseX, int mouseY) {
        if (this.visible) {
            int width = Math.max(100, RenderUtil.getWidth(this.title) + 15);
            if (this.expanded) {
                for (Button button : this.buttons) {
                    width = Math.max(width, button.getWidth() + 15);
                }
            }
            if (this.dragging && Mouse.isButtonDown((int)0)) {
                this.posX = mouseX - this.prevPosX;
                this.posY = mouseY - this.prevPosY;
                draggingId = this.id;
            } else {
                this.dragging = false;
                draggingId = -1;
            }
            RenderUtil.drawRect(this.posX, this.posY, this.posX + width + 1, this.posY + 15, -3527576);
            RenderUtil.drawString(this.expanded ? "\u2193" : "\u2191", this.posX + width - 7, this.posY + 3, -1);
            RenderUtil.drawString(this.title, this.posX + 50 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.title) / 2, this.posY + 4, -1);
            if (this.expanded) {
                int height = 0;
                for (Button button : this.buttons) {
                    ArrayList<GuiComponent> components;
                    button.render(this.posX + 1, this.posY + height + 15, width + 3, mouseX, mouseY);
                    if (button.getButtonId() == Button.extendedId && !(components = button.getComponents()).isEmpty()) {
                        int width0 = 10;
                        for (GuiComponent component : components) {
                            width0 = Math.max(width0, component.getWidth());
                        }
                        int i = this.posX + width + 2;
                        int height0 = 0;
                        for (GuiComponent component2 : components) {
                            height0 += component2.getHeight();
                        }
                        int i0 = this.posY + height + height0 + 13;
                        int i1 = this.posY + height + 12;
                        RenderUtil.drawRect(i + 1, i1 + 1, i + width0, i0, -870375649);
                        int height1 = 0;
                        for (GuiComponent component3 : components) {
                            component3.render(i, this.posY + height + height1 + 13, width0, mouseX, mouseY);
                            height1 += component3.getHeight();
                        }
                        RenderUtil.drawVerticalLine(i, i1, i0, -3527576);
                        RenderUtil.drawVerticalLine(i + width0, i1, i0, -3527576);
                        RenderUtil.drawHorizontalLine(i, i + width0, i1, -3527576);
                        RenderUtil.drawHorizontalLine(i, i + width0, i0, -3527576);
                    }
                    height += button.getHeight();
                }
                RenderUtil.drawVerticalLine(this.posX, this.posY + 11, this.posY + height + 15, -3527576);
                RenderUtil.drawVerticalLine(this.posX + width, this.posY + 11, this.posY + height + 15, -3527576);
                RenderUtil.drawHorizontalLine(this.posX, this.posX + width, this.posY + height + 15, -3527576);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        int width = 100;
        if (this.expanded) {
            for (Button button : this.buttons) {
                width = Math.max(width, button.getWidth());
            }
        }
        if (RenderUtil.isHovered(this.posX, this.posY, width, 13, mouseX, mouseY)) {
            if (mouseButton == 0) {
                this.prevPosX = mouseX - this.posX;
                this.prevPosY = mouseY - this.posY;
                this.dragging = true;
                draggingId = this.id;
            } else if (mouseButton == 1) {
                this.expanded = !this.expanded;
                this.dragging = false;
                draggingId = -1;
            }
        }
        if (this.expanded) {
            for (Button button : this.buttons) {
                button.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    @Override
    public void keyTyped(int keyCode, char typedChar) {
        if (this.expanded) {
            for (Button button : this.buttons) {
                button.keyTyped(keyCode, typedChar);
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.posX);
        sb.append(';');
        sb.append(this.posY);
        sb.append(';');
        sb.append(this.visible);
        sb.append(';');
        sb.append(this.expanded);
        return sb.toString();
    }

    public static GuiFrame fromString(String title, String info) {
        int posX;
        int posY;
        boolean eposXpanded;
        boolean visible;
        String[] split = info.split(";");
        if (split.length != 4) {
            System.err.println("Cannot parse FrameInfo, generating a new Frame.");
            posX = 30;
            posY = 50;
            visible = true;
            eposXpanded = false;
        } else {
            posX = Integer.parseInt(split[0]);
            posY = Integer.parseInt(split[1]);
            visible = Boolean.parseBoolean(split[2]);
            eposXpanded = Boolean.parseBoolean(split[3]);
        }
        return new GuiFrame(title, posX, posY, visible, eposXpanded);
    }
}

