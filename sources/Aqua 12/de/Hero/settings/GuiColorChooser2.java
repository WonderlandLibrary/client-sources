// 
// Decompiled by Procyon v0.5.36
// 

package de.Hero.settings;

import org.lwjgl.input.Mouse;
import java.awt.Color;
import net.minecraft.client.gui.Gui;

public class GuiColorChooser2 extends Gui
{
    public double x;
    public double y;
    private int width;
    private int height;
    private double hueChooserX;
    private double colorChooserX;
    private double colorChooserY;
    public int color;
    public float[] hsbValues;
    
    public GuiColorChooser2(final int x, final int y) {
        this.width = 145;
        this.height = 80;
        this.color = Color.decode("#FFFFFF").getRGB();
        this.hsbValues = new float[3];
        this.x = x;
        this.y = y;
        this.color = Color.red.getRGB();
    }
    
    public GuiColorChooser2(final int x, final int y, final int color) {
        this.width = 145;
        this.height = 80;
        this.color = Color.decode("#FFFFFF").getRGB();
        this.hsbValues = new float[3];
        this.x = x;
        this.y = y;
        this.setHueChooserByHue(this.hsbValues[0]);
        this.setHueChooserBySB(this.hsbValues[1], this.hsbValues[2]);
        this.color = color;
        final Color c = new Color(color);
        this.hsbValues = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), this.hsbValues);
    }
    
    public void draw(final int mouseX, final int mouseY) {
        final int backGroundColor = new Color(0, 0, 0, 180).getRGB();
        final int chooserWidth = this.width - 15;
        for (float i = 0.0f; i < chooserWidth; i += 0.5) {
            final float f = 1.0f / chooserWidth * i;
            final float finalI = i;
            Gui.drawRect2(this.x + 5.0 + i, this.y + this.height - 12.75, this.x + 10.0 + i + 0.5, this.y + this.height - 8.25, Color.HSBtoRGB(f, 1.0f, 1.0f));
        }
        final int hsbChooserWidth = this.width - 5;
        final int hsbChooserHeight = this.height - 25;
        for (float e = 0.0f; e < hsbChooserWidth; ++e) {
            for (float f2 = 0.0f; f2 < hsbChooserHeight; ++f2) {
                final float xPos = (float)(this.x + 2.5 + e);
                final float yPos = f2;
                final float satuartion = 1.0f / hsbChooserWidth * e;
                final float brightness = 1.0f / hsbChooserHeight * f2;
                Gui.drawRect2(xPos, this.y + 5.0 + hsbChooserHeight - yPos - 1.0, xPos + 1.0f, this.y + 5.0 + hsbChooserHeight - yPos + 1.0 - 1.0, Color.HSBtoRGB(this.hsbValues[0], satuartion, brightness));
            }
        }
        final int max = 255;
        final Color onlyHue = new Color(Color.HSBtoRGB(this.hsbValues[0], 1.0f, 1.0f));
        final int hueChooserColor = new Color(max - onlyHue.getRed(), max - onlyHue.getGreen(), max - onlyHue.getBlue()).getRGB();
        Gui.drawRect2(this.x + 7.0 + this.hueChooserX, this.y + this.height - 12.75, this.x + 7.0 + this.hueChooserX + 0.5, this.y + this.height - 8.25, hueChooserColor);
        final Color allColor = new Color(Color.HSBtoRGB(this.hsbValues[0], this.hsbValues[1], this.hsbValues[2]));
        final int colorChooserColor = new Color(max - allColor.getRed(), max - allColor.getGreen(), max - allColor.getBlue()).getRGB();
        if (Mouse.isButtonDown(0)) {
            if (mouseY >= this.y + 5.0 + this.height - 20.0 && mouseY <= this.y + 5.0 + this.height - 10.0) {
                double diff = mouseX - this.x - 5.0;
                if (diff > this.width - 10.5) {
                    diff = this.width - 10.5;
                }
                if (diff < 0.0) {
                    diff = 0.0;
                }
                this.hueChooserX = diff;
                this.setHueChooserByHue((float)(1.0f / (this.width - 10) * this.hueChooserX));
            }
            if (mouseX >= this.x - 3.0 && mouseX <= this.x + this.width + 5.0 && mouseY >= this.y + 5.0 && mouseY <= this.y + this.height - 20.0) {
                double diffX = mouseX - this.x - 3.0;
                if (diffX > this.width - 10) {
                    diffX = this.width - 10;
                }
                if (diffX < 0.0) {
                    diffX = 0.0;
                }
                double diffY = mouseY - this.y - 5.0;
                if (diffY > 55.0) {
                    diffY = 55.0;
                }
                if (diffY < 0.25) {
                    diffY = 0.25;
                }
                this.colorChooserX = diffX - 3.0;
                this.colorChooserY = diffY;
                this.hsbValues[1] = (float)(1.0f / (this.width - 10) * this.colorChooserX);
                this.hsbValues[2] = 1.0f - (float)(0.0181818176060915 * this.colorChooserY);
            }
        }
        this.color = Color.HSBtoRGB(this.hsbValues[0], this.hsbValues[1], this.hsbValues[2]);
    }
    
    public void setHue(float hue) {
        if (hue > 1.0f) {
            hue = 1.0f;
        }
        this.hsbValues[0] = hue;
    }
    
    public void setHueChooserByHue(final float hue) {
        this.hueChooserX = (this.width - 10) * hue;
        this.setHue(hue);
    }
    
    public void setHueChooserBySB(final float s, final float b) {
        this.colorChooserX = (this.width - 10) * s;
        this.colorChooserY = 55.0f - 55.0f * b;
    }
    
    public void setSaturation(float sat) {
        if (sat > 1.0f) {
            sat = 1.0f;
        }
        this.hsbValues[1] = sat;
    }
    
    public void setBrightness(float bright) {
        if (bright > 1.0f) {
            bright = 1.0f;
        }
        this.hsbValues[2] = bright;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public void setWidth(final int width) {
        this.width = width;
    }
    
    public void setHeight(final int height) {
        this.height = height;
    }
}
