// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraft.client.gui;

import org.lwjgl.opengl.GL11;
import intent.AquaDev.aqua.Aqua;
import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class IMGButton extends GuiButton
{
    private ResourceLocation loc;
    private int notHoveredWidth;
    private int notHoveredHeight;
    private int scale;
    public String buttonText;
    
    public IMGButton(final int buttonId, final int x, final int y, final int widthIn, final int heightIn, final String buttonText, final ResourceLocation loc) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.scale = 0;
        this.loc = loc;
        this.notHoveredWidth = widthIn;
        this.notHoveredHeight = heightIn;
        this.buttonText = buttonText;
    }
    
    @Override
    public void drawButton(final Minecraft mc, final int mouseX, final int mouseY) {
        if (this.visible) {
            mc.getTextureManager().bindTexture(this.loc);
            this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
            final int i = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            this.mouseDragged(mc, mouseX, mouseY);
            if (this.hovered) {
                final Color color = Color.white;
                Aqua.INSTANCE.comfortaa4.drawString(this.buttonText, (float)(this.xPosition + 1), (float)(this.yPosition + 55), color.getRGB());
                if (this.scale < 10 && this.scale + 1 < 10) {
                    this.scale += 2;
                }
                this.notHoveredWidth = this.width + this.scale;
                this.notHoveredHeight = this.height + this.scale;
            }
            else {
                if (this.scale + 20 > 9.5f) {
                    this.scale -= 2;
                }
                this.notHoveredWidth = this.width + this.scale;
                this.notHoveredHeight = this.height + this.scale;
            }
            if (this.hovered) {
                GL11.glColor4f(0.78431374f, 0.78431374f, 0.78431374f, 1.0f);
            }
            else {
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            }
            drawImage(this.xPosition - this.scale / 2, this.yPosition - this.scale / 2, this.notHoveredWidth, this.notHoveredHeight, this.loc);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
    
    public static void drawImage(final int x, final int y, final int width, final int height, final ResourceLocation resourceLocation) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, (float)width, (float)height);
    }
}
