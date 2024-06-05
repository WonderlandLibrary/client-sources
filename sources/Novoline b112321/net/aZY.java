package net;

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

class aZY extends GuiButton {
   private final SoundCategory r;
   private final String s;
   public float p;
   public boolean t;
   final GuiScreenOptionsSounds q;

   public aZY(GuiScreenOptionsSounds var1, int var2, int var3, int var4, SoundCategory var5, boolean var6) {
      super(var2, var3, var4, 310, 20, "");
      this.q = var1;
      this.p = 1.0F;
      this.r = var5;
      this.s = I18n.format("soundCategory." + var5.getCategoryName(), new Object[0]);
      this.displayString = this.s + ": " + var1.getSoundVolume(var5);
      this.p = GuiScreenOptionsSounds.access$000(var1).getSoundLevel(var5);
   }

   protected int getHoverState(boolean var1) {
      return 0;
   }

   protected void mouseDragged(Minecraft var1, int var2, int var3) {
      if(this.visible) {
         if(this.t) {
            this.p = (float)((double)var2 - (this.m + 4.0D)) / (float)(this.width - 8);
            this.p = MathHelper.a(this.p, 0.0F, 1.0F);
            var1.gameSettings.setSoundLevel(this.r, this.p);
            var1.gameSettings.saveOptions();
            this.displayString = this.s + ": " + this.q.getSoundVolume(this.r);
         }

         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         this.drawTexturedModalRect((int)this.m + (int)(this.p * (float)(this.width - 8)), (int)this.h, 0, 66, 4, 20);
         this.drawTexturedModalRect((int)this.m + (int)(this.p * (float)(this.width - 8)) + 4, (int)this.h, 196, 66, 4, 20);
      }

   }

   public boolean mousePressed(Minecraft var1, int var2, int var3) {
      if(super.mousePressed(var1, var2, var3)) {
         this.p = (float)((double)var2 - (this.m + 4.0D)) / (float)(this.width - 8);
         this.p = MathHelper.a(this.p, 0.0F, 1.0F);
         var1.gameSettings.setSoundLevel(this.r, this.p);
         var1.gameSettings.saveOptions();
         this.displayString = this.s + ": " + this.q.getSoundVolume(this.r);
         this.t = true;
         return true;
      } else {
         return false;
      }
   }

   public void playPressSound(SoundHandler var1) {
   }

   public void mouseReleased(int var1, int var2) {
      if(this.t) {
         if(this.r == SoundCategory.MASTER) {
            float var3 = 1.0F;
         } else {
            GuiScreenOptionsSounds.access$000(this.q).getSoundLevel(this.r);
         }

         this.q.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
      }

      this.t = false;
   }
}
