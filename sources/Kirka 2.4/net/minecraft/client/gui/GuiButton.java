/*
 * Decompiled with CFR 0.143.
 */
package net.minecraft.client.gui;

import me.thekirkayt.utils.ColorUtil;
import me.thekirkayt.utils.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public class GuiButton
extends Gui {
    protected static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");
    protected int width = 200;
    private final Timer time = new Timer();
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
            mc.getTextureManager().bindTexture(buttonTextures);
            GlStateManager.color(0.0f, 0.8f, 1.0f, 1.0f);
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int var2 = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            int color = ColorUtil.rainbow(200000000L, 1.0f).getRGB();
            if (this.time.hasReached(50L)) {
                color = -2141035934;
            }
            if (this.time.hasReached(100L)) {
                color = -2139917453;
            }
            if (this.time.hasReached(150L)) {
                color = -2138864765;
            }
            if (this.time.hasReached(200L)) {
                color = -2137746284;
            }
            if (this.time.hasReached(250L)) {
                color = -2136693596;
            }
            if (this.time.hasReached(300L)) {
                color = -2136101459;
            }
            if (this.time.hasReached(350L)) {
                color = -2135509322;
            }
            if (this.time.hasReached(400L)) {
                color = -2134917185;
            }
            if (this.time.hasReached(450L)) {
                color = -2134325048;
            }
            if (this.time.hasReached(500L)) {
                color = -2133667118;
            }
            if (!this.hovered) {
                this.time.reset();
            }
            RenderHelper.drawBorderedRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, 0.8f, -587202560, -1879048192);
            if (this.hovered) {
                RenderHelper.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, ColorUtil.rainbow(200000000L, 1.0f).getRGB());
            }
            this.mouseDragged(mc, mouseX, mouseY);
            int var3 = 14737632;
            if (!this.enabled) {
                var3 = 10526880;
            } else if (this.hovered) {
                boolean bl = true;
            }
            this.drawCenteredString(mc.fontRendererObj, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, -1);
        }
    }

    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
    }

    public void mouseReleased(int mouseX, int mouseY) {
    }

    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
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

