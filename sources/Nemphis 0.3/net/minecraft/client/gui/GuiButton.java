/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import pw.vertexcode.util.font.FontManager;
import pw.vertexcode.util.lwjgl.LWJGLUtil;

public class GuiButton
extends Gui {
    protected static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");
    protected int width = 200;
    protected int height = 20;
    public int xPosition;
    public int yPosition;
    public String displayString;
    public int id;
    public boolean enabled = true;
    public boolean visible = true;
    protected boolean hovered;
    private static final String __OBFID = "CL_00000668";

    public GuiButton(int buttonId, int x, int y, String buttonText) {
        this(buttonId, x, y, 200, 20, buttonText);
    }

    public GuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        this.id = buttonId;
        this.xPosition = x;
        this.yPosition = y;
        this.width = widthIn;
        this.height = heightIn;
        this.displayString = buttonText;
    }

    protected int getHoverState(boolean mouseOver) {
        int var2 = 1;
        if (!this.enabled) {
            var2 = 0;
        } else if (mouseOver) {
            var2 = 2;
        }
        return var2;
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            FontRenderer var4 = mc.fontRendererObj;
            boolean color = false;
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width - 40 && mouseY < this.yPosition + this.height;
            int var5 = this.getHoverState(this.hovered);
            if (!this.hovered) {
                LWJGLUtil.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, -2131956500);
                FontManager.getFont("jslight", 33).drawString(this.displayString, this.xPosition + this.width / 2 - FontManager.getFont("jslight", 33).getStringWidth(this.displayString) / 2, this.yPosition + (this.height - 12) / 2 - 2, -16777216);
            } else {
                LWJGLUtil.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, -2138636288);
                FontManager.getFont("jslight", 33).drawString(this.displayString, this.xPosition + this.width / 2 - FontManager.getFont("jslight", 33).getStringWidth(this.displayString) / 2, this.yPosition + (this.height - 12) / 2 - 2, 16777215);
            }
            mc.getTextureManager().bindTexture(buttonTextures);
            this.mouseDragged(mc, mouseX, mouseY);
        }
    }

    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
    }

    public void mouseReleased(int mouseX, int mouseY) {
    }

    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if (this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height) {
            return true;
        }
        return false;
    }

    public boolean isMouseOver() {
        return this.hovered;
    }

    public void drawButtonForegroundLayer(int mouseX, int mouseY) {
    }

    public void playPressSound(SoundHandler soundHandlerIn) {
        soundHandlerIn.playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 1.0f));
    }

    public int getButtonWidth() {
        return this.width;
    }

    public void func_175211_a(int p_175211_1_) {
        this.width = p_175211_1_;
    }
}

