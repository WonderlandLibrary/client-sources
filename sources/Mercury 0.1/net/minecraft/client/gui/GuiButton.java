/*
 * Decompiled with CFR 0.145.
 */
package net.minecraft.client.gui;

import de.Hero.clickgui.util.ColorUtil;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import us.amerikan.gui.FontUtils;
import us.amerikan.utils.RenderHelper;

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
    FontUtils font = new FontUtils("Comfortaa", 0, 23);

    public GuiButton(int buttonId, int x2, int y2, String buttonText) {
        this(buttonId, x2, y2, 200, 20, buttonText);
    }

    public GuiButton(int buttonId, int x2, int y2, int widthIn, int heightIn, String buttonText) {
        this.id = buttonId;
        this.xPosition = x2;
        this.yPosition = y2;
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

    public void drawButton(Minecraft mc2, int mouseX, int mouseY) {
        if (this.visible) {
            FontRenderer var4 = mc2.fontRendererObj;
            mc2.getTextureManager().bindTexture(buttonTextures);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int var5 = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            RenderHelper.drawBorderedRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, 1.0f, Color.white.getRGB(), 0);
            this.mouseDragged(mc2, mouseX, mouseY);
            int var6 = 14737632;
            if (!this.enabled) {
                var6 = 10526880;
            } else if (this.hovered) {
                var6 = ColorUtil.getClickGUIColor().getRGB();
            }
            this.font.drawCenteredString(this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 12) / 2, var6);
        }
    }

    protected void mouseDragged(Minecraft mc2, int mouseX, int mouseY) {
    }

    public void mouseReleased(int mouseX, int mouseY) {
    }

    public boolean mousePressed(Minecraft mc2, int mouseX, int mouseY) {
        return this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
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

