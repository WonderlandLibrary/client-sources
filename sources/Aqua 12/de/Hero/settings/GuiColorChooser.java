// 
// Decompiled by Procyon v0.5.36
// 

package de.Hero.settings;

import net.minecraft.util.ResourceLocation;
import gui.jello.ClickguiScreen;
import intent.AquaDev.aqua.modules.visual.ShaderMultiplier;
import intent.AquaDev.aqua.utils.RenderUtil;
import intent.AquaDev.aqua.utils.StencilUtil;
import intent.AquaDev.aqua.gui.hero.ClickguiScreenHero;
import intent.AquaDev.aqua.modules.visual.Shadow;
import intent.AquaDev.aqua.modules.visual.Blur;
import intent.AquaDev.aqua.utils.ColorUtils;
import intent.AquaDev.aqua.Aqua;
import net.minecraft.client.Minecraft;
import intent.AquaDev.aqua.gui.novolineOld.ClickguiScreenNovoline;
import org.lwjgl.input.Mouse;
import java.awt.Color;
import net.minecraft.client.gui.Gui;

public class GuiColorChooser extends Gui
{
    public double x;
    public double y;
    private int width;
    private int height;
    private int finalGlowColor;
    private double hueChooserX;
    public static boolean isHovering;
    private double colorChooserX;
    private double colorChooserY;
    public int color;
    public float[] hsbValues;
    
    public GuiColorChooser(final int x, final int y) {
        this.width = 100;
        this.height = 80;
        this.color = Color.decode("#FFFFFF").getRGB();
        this.hsbValues = new float[3];
        this.x = x;
        this.y = y;
        this.color = Color.red.getRGB();
    }
    
    public GuiColorChooser(final int x, final int y, final int color) {
        this.width = 100;
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
    
    public void draw2(final int mouseX, final int mouseY) {
        final int backGroundColor = new Color(0, 0, 0, 180).getRGB();
        Gui.drawRect2(this.x, this.y - 5.0, this.x + this.width, this.y + this.height, backGroundColor);
        final int chooserWidth = this.width - 15;
        for (float i = 0.0f; i < chooserWidth; i += 0.5) {
            final float f = 1.0f / chooserWidth * i;
            final float finalI = i;
            Gui.drawRect2(this.x + 5.0 + i, this.y + this.height - 12.75, this.x + 10.0 + i + 0.5, this.y + this.height - 8.25, Color.HSBtoRGB(f, 1.0f, 1.0f));
        }
        final int hsbChooserWidth = this.width - 5;
        final int hsbChooserHeight = this.height - 25;
        for (float e = 0.0f; e < hsbChooserWidth; e += 0.5f) {
            for (float f2 = 0.0f; f2 < hsbChooserHeight; ++f2) {
                final float xPos = (float)(this.x + 2.0 + e);
                final float yPos = f2 + 2.0f;
                final float satuartion = 1.0f / hsbChooserWidth * e;
                final float n = 1.0f / hsbChooserHeight * f2;
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
    
    public void draw(final int mouseX, final int mouseY) {
        final int backGroundColor = (Minecraft.getMinecraft().currentScreen instanceof ClickguiScreenNovoline) ? new Color(40, 40, 40, 255).getRGB() : new Color(0, 0, 0, 220).getRGB();
        if (Minecraft.getMinecraft().currentScreen instanceof intent.AquaDev.aqua.gui.suicideX.ClickguiScreenNovoline) {
            final Color alphaColor = ColorUtils.getColorAlpha(Aqua.setmgr.getSetting("HUDColor").getColor(), 200);
            if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                Blur.drawBlurred(() -> Gui.drawRect2(this.x, this.y - 5.0, this.x + this.width, this.y + this.height, new Color(36, 36, 36, 255).getRGB()), false);
            }
            if (Aqua.moduleManager.getModuleByName("Shadow").isToggled() && !intent.AquaDev.aqua.gui.suicideX.ClickguiScreenNovoline.isDragging && Aqua.setmgr.getSetting("GUIDarkMode").isState()) {
                Shadow.drawGlow(() -> Gui.drawRect2(this.x, this.y - 5.0, this.x + this.width, this.y + this.height, new Color(36, 36, 36, 255).getRGB()), false);
            }
            Gui.drawRect2(this.x, this.y - 5.0, this.x + this.width, this.y + this.height, new Color(255, 255, 255, 100).getRGB());
            Gui.drawRect2(this.x, this.y - 5.0, this.x + this.width, this.y + this.height, alphaColor.getRGB());
        }
        if (Minecraft.getMinecraft().currentScreen instanceof ClickguiScreenNovoline) {
            Gui.drawRect2(this.x, this.y - 5.0, this.x + this.width, this.y + this.height, backGroundColor);
        }
        if (Minecraft.getMinecraft().currentScreen instanceof intent.AquaDev.aqua.gui.aqua.ClickguiScreenNovoline) {
            if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                Blur.drawBlurred(() -> Gui.drawRect2(this.x, this.y - 5.0, this.x + this.width, this.y + this.height, new Color(116, 116, 116, 100).getRGB()), false);
            }
            if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
                Shadow.drawGlow(() -> Gui.drawRect2(this.x, this.y - 5.0, this.x + this.width, this.y + this.height, new Color(36, 36, 36, 255).getRGB()), false);
            }
            Gui.drawRect2(this.x, this.y - 5.0, this.x + this.width, this.y + this.height, new Color(116, 116, 116, 100).getRGB());
        }
        if (Minecraft.getMinecraft().currentScreen instanceof intent.AquaDev.aqua.gui.novoline.ClickguiScreenNovoline) {
            Gui.drawRect2(this.x, this.y - 5.0, this.x + this.width, this.y + this.height, new Color(0, 0, 0, 200).getRGB());
        }
        if (Minecraft.getMinecraft().currentScreen instanceof ClickguiScreenHero) {
            Gui.drawRect2(this.x + 8.0, this.y - 6.0, this.x + this.width, this.y + this.height - 1.0, new Color(28, 29, 31).getRGB());
        }
        if (Minecraft.getMinecraft().currentScreen instanceof intent.AquaDev.aqua.gui.tenacity.ClickguiScreenNovoline) {
            if (Aqua.setmgr.getSetting("GUIGradiant").isState()) {
                StencilUtil.write(false);
                Gui.drawRect2(this.x - 1.5, this.y - 5.0, this.x + this.width + 1.5, this.y + this.height, new Color(59, 59, 69, 255).getRGB());
                StencilUtil.erase(true);
                Aqua.INSTANCE.shaderBackground.renderShader();
                StencilUtil.dispose();
            }
            else {
                Gui.drawRect2(this.x - 1.5, this.y - 5.0, this.x + this.width + 1.5, this.y + this.height, new Color(Aqua.setmgr.getSetting("GUIColor").getColor()).getRGB());
            }
            Gui.drawRect2(this.x, this.y - 5.0, this.x + this.width, this.y + this.height, new Color(59, 59, 69, 255).getRGB());
        }
        final int chooserWidth = this.width - 15;
        for (float i = 0.0f; i < chooserWidth; i += 0.5) {
            final float f = 1.0f / chooserWidth * i;
            final float finalI = i;
            if (Minecraft.getMinecraft().currentScreen instanceof ClickguiScreenHero) {
                Gui.drawRect2(this.x + 8.0 + i, this.y + this.height - 5.0, this.x + 10.0 + i + 0.5, this.y + this.height - 8.25, Color.HSBtoRGB(f, 1.0f, 1.0f));
            }
            else {
                Gui.drawRect2(this.x + 5.0 + i, this.y + this.height - 5.0, this.x + 10.0 + i + 0.5, this.y + this.height - 8.25, Color.HSBtoRGB(f, 1.0f, 1.0f));
            }
        }
        final int hsbChooserWidthRounded = this.width - 10;
        final int hsbChooserWidth = this.width - 5;
        final int hsbChooserHeight = this.height - 25;
        if (Aqua.setmgr.getSetting("GUIColorPickerGlow").isState() && Aqua.setmgr.getSetting("GUIGlowMode").getCurrentMode().equalsIgnoreCase("Complete") && Aqua.setmgr.getSetting("GUIRoundedPicker").isState()) {
            final int n;
            ShaderMultiplier.drawGlowESP(() -> RenderUtil.drawRoundedRect2Alpha((float)(this.x + 2.0) + 1.0f, this.y + 6.0 + n - 48.0 - 3.0, 89.0, this.y + 5.0 + n - this.y - 1.0, 3.0, new Color(this.finalGlowColor)), false);
        }
        if (Minecraft.getMinecraft().currentScreen instanceof ClickguiScreenHero) {
            for (float e = 0.0f; e < hsbChooserWidth; ++e) {
                for (float f2 = 0.0f; f2 < hsbChooserHeight; ++f2) {
                    final float xPos = (float)(this.x + 8.0 + e);
                    final float yPos = f2;
                    final float satuartion = 1.0f / hsbChooserWidth * e;
                    final float brightness = 1.0f / hsbChooserHeight * f2;
                    this.finalGlowColor = Color.HSBtoRGB(this.hsbValues[0], satuartion, brightness);
                    Gui.drawRect2(xPos, this.y + 14.0 + hsbChooserHeight - yPos - 1.0, xPos + 1.5f, this.y + 5.0 + hsbChooserHeight - yPos + 1.0 - 1.0, Color.HSBtoRGB(this.hsbValues[0], satuartion, brightness));
                }
            }
        }
        else if (Minecraft.getMinecraft().currentScreen instanceof ClickguiScreen) {
            for (float e = 0.0f; e < hsbChooserWidth; ++e) {
                for (float f2 = 0.0f; f2 < hsbChooserHeight; ++f2) {
                    final float xPos = (float)(this.x + 2.5 + e);
                    final float yPos = f2;
                    final float satuartion = 1.0f / hsbChooserWidth * e;
                    final float brightness = 1.0f / hsbChooserHeight * f2;
                    this.finalGlowColor = Color.HSBtoRGB(this.hsbValues[0], satuartion, brightness);
                    Gui.drawRect2(xPos - 2.5f, this.y + 14.0 + hsbChooserHeight - yPos - 1.0, xPos + 2.5f, this.y + 5.0 + hsbChooserHeight - yPos + 1.0 - 1.0, Color.HSBtoRGB(this.hsbValues[0], satuartion, brightness));
                }
            }
        }
        else if (Minecraft.getMinecraft().currentScreen instanceof ClickguiScreenNovoline) {
            for (float e = 0.0f; e < hsbChooserWidth; ++e) {
                for (float f2 = 0.0f; f2 < hsbChooserHeight; ++f2) {
                    final float xPos = (float)(this.x + 2.5 + e);
                    final float yPos = f2;
                    final float satuartion = 1.0f / hsbChooserWidth * e;
                    final float brightness = 1.0f / hsbChooserHeight * f2;
                    this.finalGlowColor = Color.HSBtoRGB(this.hsbValues[0], satuartion, brightness);
                    Gui.drawRect2(xPos, this.y + 14.0 + hsbChooserHeight - yPos - 1.0, xPos + 1.5f, this.y + 5.0 + hsbChooserHeight - yPos + 1.0 - 1.0, Color.HSBtoRGB(this.hsbValues[0], satuartion, brightness));
                }
            }
        }
        else if (Aqua.setmgr.getSetting("GUIRoundedPicker").isState()) {
            for (float e = 0.0f; e < hsbChooserWidthRounded; ++e) {
                for (float f2 = 0.0f; f2 < hsbChooserHeight; ++f2) {
                    final float xPos = (float)(this.x + 2.0 + e);
                    final float yPos = f2 - 5.0f;
                    final float satuartion = 1.0f / hsbChooserWidthRounded * e;
                    final float brightness = 1.0f / hsbChooserHeight * f2;
                    this.finalGlowColor = Color.HSBtoRGB(this.hsbValues[0], satuartion, brightness);
                    RenderUtil.drawRoundedRect2Alpha(xPos + 0.7, this.y + 5.0 + hsbChooserHeight - yPos - 1.0, 5.0, this.y + 5.0 + hsbChooserHeight - this.y - 55.0, 3.0, new Color(Color.HSBtoRGB(this.hsbValues[0], satuartion, brightness)));
                }
            }
        }
        else {
            for (float e = 0.0f; e < hsbChooserWidth; ++e) {
                for (float f2 = 0.0f; f2 < hsbChooserHeight; ++f2) {
                    final float xPos = (float)(this.x + 2.5 + e);
                    final float yPos = f2;
                    final float satuartion = 1.0f / hsbChooserWidth * e;
                    final float brightness = 1.0f / hsbChooserHeight * f2;
                    this.finalGlowColor = Color.HSBtoRGB(this.hsbValues[0], satuartion, brightness);
                    Gui.drawRect2(xPos, this.y + 5.0 + hsbChooserHeight - yPos - 1.0, xPos + 1.0f, this.y + 5.0 + hsbChooserHeight - yPos + 1.0 - 1.0, Color.HSBtoRGB(this.hsbValues[0], satuartion, brightness));
                }
            }
        }
        final int max = 255;
        final Color onlyHue = new Color(Color.HSBtoRGB(this.hsbValues[0], 1.0f, 1.0f));
        final int hueChooserColor = new Color(max - onlyHue.getRed(), max - onlyHue.getGreen(), max - onlyHue.getBlue()).getRGB();
        final Color allColor = new Color(Color.HSBtoRGB(this.hsbValues[0], this.hsbValues[1], this.hsbValues[2]));
        final int colorChooserColor = new Color(max - allColor.getRed(), max - allColor.getGreen(), max - allColor.getBlue()).getRGB();
        RenderUtil.drawImage((int)(this.x + 5.0 + this.colorChooserX - 2.5), (int)(this.y - 1.0 + this.colorChooserY + 5.0), 10, 10, new ResourceLocation("Aqua/cross.png"));
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
        if (Minecraft.getMinecraft().currentScreen instanceof ClickguiScreenHero) {
            Aqua.INSTANCE.robotoPanel.drawStringWithShadow("Color :", (float)this.x + 9.0f, (float)this.y - 6.0f, -1);
        }
        else if (Minecraft.getMinecraft().currentScreen instanceof ClickguiScreen) {
            Aqua.INSTANCE.jelloClickguiSettings.drawString("Color :", (float)this.x + 7.0f, (float)this.y - 5.0f, Color.darkGray.getRGB());
        }
        else if (Minecraft.getMinecraft().currentScreen instanceof ClickguiScreenNovoline) {
            Aqua.INSTANCE.novolineSmall.drawStringWithShadow("Color :", (float)this.x + 7.0f, (float)this.y - 5.0f, Color.white.getRGB());
        }
        else if (Minecraft.getMinecraft().currentScreen instanceof intent.AquaDev.aqua.gui.tenacity.ClickguiScreenNovoline) {
            Aqua.INSTANCE.tenacitySmall.drawString("Color :", (float)this.x + 7.0f, (float)(this.y - 3.0), Color.white.getRGB());
        }
        else {
            Aqua.INSTANCE.comfortaa4.drawString("Color :", (float)this.x + 3.0f, (float)((Minecraft.getMinecraft().currentScreen instanceof intent.AquaDev.aqua.gui.aqua.ClickguiScreenNovoline) ? (this.y - 1.0) : (this.y - 5.0)), Color.gray.getRGB());
        }
        if (Minecraft.getMinecraft().currentScreen instanceof ClickguiScreenHero) {
            RenderUtil.drawRoundedRect2Alpha(this.x + this.width - 18.0, this.y - 4.0, 13.0, 7.5, 0.0, new Color(this.finalGlowColor));
        }
        else if (Minecraft.getMinecraft().currentScreen instanceof ClickguiScreen) {
            RenderUtil.drawRoundedRect2Alpha(this.x + this.width - 15.0, this.y - 4.0, 14.0, 7.5, 0.0, new Color(this.finalGlowColor));
        }
        else if (Minecraft.getMinecraft().currentScreen instanceof ClickguiScreenNovoline) {
            RenderUtil.drawRoundedRect2Alpha(this.x + this.width - 15.0, this.y - 4.0, 13.0, 7.5, 0.0, new Color(this.finalGlowColor));
        }
        else if (Minecraft.getMinecraft().currentScreen instanceof intent.AquaDev.aqua.gui.tenacity.ClickguiScreenNovoline) {
            RenderUtil.drawRoundedRect2Alpha(this.x + this.width - 17.0, this.y - 1.0, 15.0, 7.5, 1.0, new Color(this.finalGlowColor));
        }
        else {
            RenderUtil.drawRoundedRect2Alpha(this.x + this.width - 19.0, (Minecraft.getMinecraft().currentScreen instanceof intent.AquaDev.aqua.gui.aqua.ClickguiScreenNovoline) ? (this.y - 1.0) : (this.y - 4.0), 15.0, 7.5, 2.0, new Color(this.finalGlowColor));
        }
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
    
    private boolean mouseOver(final int x, final int y, final int modX, final int modY, final int modWidth, final int modHeight) {
        return x >= modX && x <= modX + modWidth && y >= modY && y <= modY + modHeight;
    }
    
    static {
        GuiColorChooser.isHovering = false;
    }
}
