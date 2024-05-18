package com.minimap.gui;

import java.awt.*;
import net.minecraft.client.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;

public class GuiDropDown extends Gui
{
    public int x;
    public int y;
    public int xOffset;
    public int yOffset;
    public int w;
    private String[] realOptions;
    private String[] options;
    private int[] realValues;
    private int[] values;
    public static final Color background;
    public static final Color trim;
    public static final Color trimInside;
    private static final int h = 11;
    public int selected;
    public boolean closed;
    protected String screenTitle;
    
    public GuiDropDown(final String[] options, final int x, final int y, final int w, final Integer selected) {
        this(options, createDefaultValues(options.length), x, y, w, selected);
    }
    
    public GuiDropDown(final String[] options, final int[] values, final int x, final int y, final int w, final Integer selected) {
        this.xOffset = 0;
        this.yOffset = 0;
        this.realOptions = new String[0];
        this.options = new String[0];
        this.realValues = new int[0];
        this.values = new int[0];
        this.selected = 0;
        this.closed = true;
        this.x = x;
        this.y = y;
        this.w = w;
        this.realOptions = options;
        this.realValues = values;
        this.selectValue(selected);
    }
    
    public static int[] createDefaultValues(final int length) {
        final int[] values = new int[length];
        for (int i = 0; i < length; ++i) {
            values[i] = i;
        }
        return values;
    }
    
    public int size() {
        return this.options.length;
    }
    
    public int getX() {
        return this.x + this.xOffset;
    }
    
    public int getY() {
        return this.y + this.yOffset;
    }
    
    public int getValue(final int slot) {
        return this.values[slot];
    }
    
    public String getSelectedOption() {
        return this.realOptions[this.selected];
    }
    
    public void drawSlot(final int id, final int pos, final int mouseX, final int mouseY) {
        if ((this.closed && this.onDropDown(mouseX, mouseY)) || (!this.closed && this.onDropDown(mouseX, mouseY, id))) {
            Gui.drawRect(this.getX(), this.getY() + 11 * pos, this.getX() + this.w, this.getY() + 11 + 11 * pos, GuiDropDown.trimInside.hashCode());
        }
        else {
            Gui.drawRect(this.getX(), this.getY() + 11 * pos, this.getX() + this.w, this.getY() + 11 + 11 * pos, GuiDropDown.background.hashCode());
        }
        this.drawHorizontalLine(this.getX() + 1, this.getX() + this.w - 1, this.getY() + 11 * pos, GuiDropDown.trimInside.hashCode());
        this.drawCenteredString(Minecraft.getMinecraft().fontRendererObj, I18n.format(this.options[id], new Object[0]), this.getX() + this.w / 2, this.getY() + 2 + 11 * pos, 16777215);
    }
    
    public void drawSelection(final int first, final int amount, final int mouseX, final int mouseY) {
        final int totalH = 11 * amount;
        final ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());
        final int height = scaledresolution.getScaledHeight();
        if (this.y + totalH + 1 > height) {
            this.yOffset = height - this.y - totalH - 1;
        }
        else {
            this.yOffset = 0;
        }
        for (int i = first; i < first + amount; ++i) {
            this.drawSlot(i, i - first, mouseX, mouseY);
        }
        this.drawVerticalLine(this.getX(), this.getY(), this.getY() + totalH, GuiDropDown.trim.hashCode());
        this.drawVerticalLine(this.getX() + this.w, this.getY(), this.getY() + totalH, GuiDropDown.trim.hashCode());
        this.drawHorizontalLine(this.getX(), this.getX() + this.w, this.getY(), GuiDropDown.trim.hashCode());
        this.drawHorizontalLine(this.getX(), this.getX() + this.w, this.getY() + totalH, GuiDropDown.trim.hashCode());
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (!this.closed) {
            final int clickedValue = this.getClickedValue(mouseX, mouseY);
            if (clickedValue != -1) {
                this.selectValue(clickedValue);
            }
        }
        else if (this.options.length > 1) {
            this.closed = false;
        }
    }
    
    private int getClickedValue(final int mouseX, final int mouseY) {
        final int xOnMenu = mouseX - this.getX();
        final int yOnMenu = mouseY - this.getY();
        if (xOnMenu < 0 || yOnMenu < 0 || xOnMenu > this.w || yOnMenu >= this.options.length * 11) {
            return -1;
        }
        int slot = yOnMenu / 11;
        if (slot >= this.options.length) {
            slot = this.options.length - 1;
        }
        return this.values[slot];
    }
    
    public boolean onDropDown(final int mouseX, final int mouseY) {
        final int xOnMenu = mouseX - this.getX();
        final int yOnMenu = mouseY - this.getY();
        return xOnMenu >= 0 && yOnMenu >= 0 && xOnMenu <= this.w && yOnMenu < (this.closed ? 11 : (this.options.length * 11));
    }
    
    public boolean onDropDown(final int mouseX, final int mouseY, final int id) {
        final int xOnMenu = mouseX - this.getX();
        final int yOnMenu = mouseY - this.getY();
        return xOnMenu >= 0 && yOnMenu >= id * 11 && xOnMenu <= this.w && yOnMenu < id * 11 + 11;
    }
    
    public void selectValue(final int id) {
        this.selected = id;
        this.closed = true;
        this.options = this.realOptions.clone();
        this.values = this.realValues.clone();
        this.options[0] = this.realOptions[this.selected];
        this.values[0] = this.realValues[this.selected];
        for (int i = this.selected; i > 0; --i) {
            this.options[i] = this.realOptions[i - 1];
            this.values[i] = this.realValues[i - 1];
        }
    }
    
    public void drawButton(final int mouseX, final int mouseY) {
        this.drawSelection(0, this.closed ? 1 : this.options.length, mouseX, mouseY);
    }
    
    static {
        background = new Color(0, 0, 0, 200);
        trim = new Color(160, 160, 160, 255);
        trimInside = new Color(50, 50, 50, 255);
    }
}
