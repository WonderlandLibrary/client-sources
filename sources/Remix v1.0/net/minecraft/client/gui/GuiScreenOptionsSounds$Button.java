package net.minecraft.client.gui;

import net.minecraft.client.resources.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;

class Button extends GuiButton
{
    private final SoundCategory field_146153_r;
    private final String field_146152_s;
    public float field_146156_o;
    public boolean field_146155_p;
    
    public Button(final int p_i45024_2_, final int p_i45024_3_, final int p_i45024_4_, final SoundCategory p_i45024_5_, final boolean p_i45024_6_) {
        super(p_i45024_2_, p_i45024_3_, p_i45024_4_, p_i45024_6_ ? 310 : 150, 20, "");
        this.field_146156_o = 1.0f;
        this.field_146153_r = p_i45024_5_;
        this.field_146152_s = I18n.format("soundCategory." + p_i45024_5_.getCategoryName(), new Object[0]);
        this.displayString = this.field_146152_s + ": " + GuiScreenOptionsSounds.this.getSoundVolume(p_i45024_5_);
        this.field_146156_o = GuiScreenOptionsSounds.access$000(GuiScreenOptionsSounds.this).getSoundLevel(p_i45024_5_);
    }
    
    @Override
    protected int getHoverState(final boolean mouseOver) {
        return 0;
    }
    
    @Override
    protected void mouseDragged(final Minecraft mc, final int mouseX, final int mouseY) {
        if (this.visible) {
            if (this.field_146155_p) {
                this.field_146156_o = (mouseX - (this.xPosition + 4)) / (float)(this.width - 8);
                this.field_146156_o = MathHelper.clamp_float(this.field_146156_o, 0.0f, 1.0f);
                mc.gameSettings.setSoundLevel(this.field_146153_r, this.field_146156_o);
                mc.gameSettings.saveOptions();
                this.displayString = this.field_146152_s + ": " + GuiScreenOptionsSounds.this.getSoundVolume(this.field_146153_r);
            }
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(this.xPosition + (int)(this.field_146156_o * (this.width - 8)), this.yPosition, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.xPosition + (int)(this.field_146156_o * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
        }
    }
    
    @Override
    public boolean mousePressed(final Minecraft mc, final int mouseX, final int mouseY) {
        if (super.mousePressed(mc, mouseX, mouseY)) {
            this.field_146156_o = (mouseX - (this.xPosition + 4)) / (float)(this.width - 8);
            this.field_146156_o = MathHelper.clamp_float(this.field_146156_o, 0.0f, 1.0f);
            mc.gameSettings.setSoundLevel(this.field_146153_r, this.field_146156_o);
            mc.gameSettings.saveOptions();
            this.displayString = this.field_146152_s + ": " + GuiScreenOptionsSounds.this.getSoundVolume(this.field_146153_r);
            return this.field_146155_p = true;
        }
        return false;
    }
    
    @Override
    public void playPressSound(final SoundHandler soundHandlerIn) {
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY) {
        if (this.field_146155_p) {
            if (this.field_146153_r != SoundCategory.MASTER) {
                GuiScreenOptionsSounds.access$000(GuiScreenOptionsSounds.this).getSoundLevel(this.field_146153_r);
            }
            final GuiScreenOptionsSounds this$0 = GuiScreenOptionsSounds.this;
            GuiScreenOptionsSounds.mc.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 1.0f));
        }
        this.field_146155_p = false;
    }
}
