package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreenOptionsSounds;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

class GuiScreenOptionsSounds$Button extends GuiButton {
   private final SoundCategory field_146153_r;
   private final String field_146152_s;
   public float field_146156_o;
   public boolean field_146155_p;
   final GuiScreenOptionsSounds this$0;

   public GuiScreenOptionsSounds$Button(GuiScreenOptionsSounds var1, int var2, int var3, int var4, SoundCategory var5, boolean var6) {
      super(var2, var3, var4, 310, 20, "");
      this.this$0 = var1;
      this.field_146156_o = 1.0F;
      this.field_146153_r = var5;
      this.field_146152_s = I18n.format("soundCategory." + var5.getCategoryName(), new Object[0]);
      this.displayString = this.field_146152_s + ": " + var1.getSoundVolume(var5);
      this.field_146156_o = GuiScreenOptionsSounds.access$000(var1).getSoundLevel(var5);
   }

   protected int getHoverState(boolean var1) {
      return 0;
   }

   protected void mouseDragged(Minecraft var1, int var2, int var3) {
      if(this.visible) {
         if(this.field_146155_p) {
            this.field_146156_o = (float)((double)var2 - (this.xPosition + 4.0D)) / (float)(this.width - 8);
            this.field_146156_o = MathHelper.clamp_float(this.field_146156_o, 0.0F, 1.0F);
            var1.gameSettings.setSoundLevel(this.field_146153_r, this.field_146156_o);
            var1.gameSettings.saveOptions();
            this.displayString = this.field_146152_s + ": " + this.this$0.getSoundVolume(this.field_146153_r);
         }

         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         this.drawTexturedModalRect((int)this.xPosition + (int)(this.field_146156_o * (float)(this.width - 8)), (int)this.yPosition, 0, 66, 4, 20);
         this.drawTexturedModalRect((int)this.xPosition + (int)(this.field_146156_o * (float)(this.width - 8)) + 4, (int)this.yPosition, 196, 66, 4, 20);
      }

   }

   public boolean mousePressed(Minecraft var1, int var2, int var3) {
      if(super.mousePressed(var1, var2, var3)) {
         this.field_146156_o = (float)((double)var2 - (this.xPosition + 4.0D)) / (float)(this.width - 8);
         this.field_146156_o = MathHelper.clamp_float(this.field_146156_o, 0.0F, 1.0F);
         var1.gameSettings.setSoundLevel(this.field_146153_r, this.field_146156_o);
         var1.gameSettings.saveOptions();
         this.displayString = this.field_146152_s + ": " + this.this$0.getSoundVolume(this.field_146153_r);
         this.field_146155_p = true;
         return true;
      } else {
         return false;
      }
   }

   public void playPressSound(SoundHandler var1) {
   }

   public void mouseReleased(int var1, int var2) {
      if(this.field_146155_p) {
         if(this.field_146153_r == SoundCategory.MASTER) {
            float var3 = 1.0F;
         } else {
            GuiScreenOptionsSounds.access$000(this.this$0).getSoundLevel(this.field_146153_r);
         }

         this.this$0.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
      }

      this.field_146155_p = false;
   }
}
