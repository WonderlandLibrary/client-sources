// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.gui.account.component;

import org.lwjgl.opengl.GL11;
import me.chrest.utils.ClientUtils;
import me.chrest.utils.RenderingUtils;

public class Button
{
    private static int nullColor;
    public String text;
    private int x1;
    private int x2;
    private int y1;
    private int y2;
    private int fillColor;
    private int borderColor;
    private int fadeState;
    private static final int MAX_FADE = 15;
    private int minHex;
    private int maxHex;
    private int mouseX;
    private int mouseY;
    
    public Button(final String text, final int x1, final int x2, final int y1, final int y2, final int minHex, final int maxHex) {
        this.borderColor = 1280595028;
        this.fadeState = 0;
        this.text = text;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.minHex = minHex;
        this.maxHex = maxHex;
    }
    
    public void draw(final int mouseX, final int mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.updateFade();
        RenderingUtils.drawGradientBorderedRectReliant(this.x1, this.y1, this.x2, this.y2, 1.5f, this.borderColor, this.borderColor, this.fillColor);
        this.drawCenteredString(this.text, 1.2f);
    }
    
    public void drawCenteredString(String text, final float scale) {
        boolean tooLong = false;
        while (ClientUtils.clientFont().getStringWidth(text) * scale > this.getWidth()) {
            text = text.substring(0, text.length() - 1);
            tooLong = true;
        }
        if (tooLong) {
            text = text.substring(0, text.length() - 4);
            text = String.valueOf(String.valueOf(text)) + "...";
        }
        GL11.glScalef(scale, scale, scale);
        int strWidth = ClientUtils.clientFont().getStringWidth(text);
        strWidth *= (int)scale;
        int x = this.x1 + this.getWidth() / 2 - strWidth / 2;
        int y = this.y1 + this.getHeight() / 2 - 4;
        x /= (int)scale;
        y /= (int)scale;
        ClientUtils.clientFont().drawStringWithShadow(text, x, y, -1);
        GL11.glScalef(1.0f / scale, 1.0f / scale, 1.0f / scale);
    }
    
    public void updateFade() {
        if (this.isOver() && this.fadeState < 15) {
            ++this.fadeState;
        }
        else if (!this.isOver() && this.fadeState > 0) {
            --this.fadeState;
        }
        final double ratio = this.fadeState / 15.0;
        this.fillColor = this.getFadeHex(this.minHex, this.maxHex, ratio);
    }
    
    public void onClick(final int button) {
    }
    
    public boolean isOver() {
        return this.mouseX >= this.x1 && this.mouseX <= this.x2 && this.mouseY >= this.y1 && this.mouseY <= this.y2;
    }
    
    public int getFadeHex(final int hex1, final int hex2, final double ratio) {
        int r = hex1 >> 16;
        int g = hex1 >> 8 & 0xFF;
        int b = hex1 & 0xFF;
        r += (int)(((hex2 >> 16) - r) * ratio);
        g += (int)(((hex2 >> 8 & 0xFF) - g) * ratio);
        b += (int)(((hex2 & 0xFF) - b) * ratio);
        return r << 16 | g << 8 | b;
    }
    
    public int getWidth() {
        return this.x2 - this.x1;
    }
    
    public int getHeight() {
        return this.y2 - this.y1;
    }
    
    public void setText(final String text) {
        this.text = text;
    }
    
    public int getBorderColor() {
        return this.borderColor;
    }
    
    public void setBorderColor(final int borderColor) {
        this.borderColor = borderColor;
    }
    
    public int getX1() {
        return this.x1;
    }
    
    public void setX1(final int x1) {
        this.x1 = x1;
    }
    
    public int getX2() {
        return this.x2;
    }
    
    public void setX2(final int x2) {
        this.x2 = x2;
    }
    
    public int getY1() {
        return this.y1;
    }
    
    public void setY1(final int y1) {
        this.y1 = y1;
    }
    
    public int getY2() {
        return this.y2;
    }
    
    public void setY2(final int y2) {
        this.y2 = y2;
    }
    
    public int getFillColor() {
        return this.fillColor;
    }
}
