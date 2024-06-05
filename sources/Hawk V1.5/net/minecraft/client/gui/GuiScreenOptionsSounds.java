package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GuiScreenOptionsSounds extends GuiScreen {
   private static final String __OBFID = "CL_00000716";
   private final GameSettings game_settings_4;
   protected String field_146507_a = "Options";
   private String field_146508_h;
   private final GuiScreen field_146505_f;

   protected String getSoundVolume(SoundCategory var1) {
      float var2 = this.game_settings_4.getSoundLevel(var1);
      return var2 == 0.0F ? this.field_146508_h : String.valueOf((new StringBuilder(String.valueOf((int)(var2 * 100.0F)))).append("%"));
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRendererObj, this.field_146507_a, this.width / 2, 15, 16777215);
      super.drawScreen(var1, var2, var3);
   }

   public GuiScreenOptionsSounds(GuiScreen var1, GameSettings var2) {
      this.field_146505_f = var1;
      this.game_settings_4 = var2;
   }

   static GameSettings access$0(GuiScreenOptionsSounds var0) {
      return var0.game_settings_4;
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      if (var1.enabled && var1.id == 200) {
         this.mc.gameSettings.saveOptions();
         this.mc.displayGuiScreen(this.field_146505_f);
      }

   }

   public void initGui() {
      byte var1 = 0;
      this.field_146507_a = I18n.format("options.sounds.title");
      this.field_146508_h = I18n.format("options.off");
      this.buttonList.add(new GuiScreenOptionsSounds.Button(this, SoundCategory.MASTER.getCategoryId(), this.width / 2 - 155 + var1 % 2 * 160, this.height / 6 - 12 + 24 * (var1 >> 1), SoundCategory.MASTER, true));
      int var2 = var1 + 2;
      SoundCategory[] var3 = SoundCategory.values();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         SoundCategory var6 = var3[var5];
         if (var6 != SoundCategory.MASTER) {
            this.buttonList.add(new GuiScreenOptionsSounds.Button(this, var6.getCategoryId(), this.width / 2 - 155 + var2 % 2 * 160, this.height / 6 - 12 + 24 * (var2 >> 1), var6, false));
            ++var2;
         }
      }

      this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, I18n.format("gui.done")));
   }

   class Button extends GuiButton {
      private static final String __OBFID = "CL_00000717";
      private final String field_146152_s;
      public boolean field_146155_p;
      final GuiScreenOptionsSounds this$0;
      public float field_146156_o;
      private final SoundCategory field_146153_r;

      public boolean mousePressed(Minecraft var1, int var2, int var3) {
         if (super.mousePressed(var1, var2, var3)) {
            this.field_146156_o = (float)(var2 - (this.xPosition + 4)) / (float)(this.width - 8);
            this.field_146156_o = MathHelper.clamp_float(this.field_146156_o, 0.0F, 1.0F);
            var1.gameSettings.setSoundLevel(this.field_146153_r, this.field_146156_o);
            var1.gameSettings.saveOptions();
            this.displayString = String.valueOf((new StringBuilder(String.valueOf(this.field_146152_s))).append(": ").append(this.this$0.getSoundVolume(this.field_146153_r)));
            this.field_146155_p = true;
            return true;
         } else {
            return false;
         }
      }

      protected void mouseDragged(Minecraft var1, int var2, int var3) {
         if (this.visible) {
            if (this.field_146155_p) {
               this.field_146156_o = (float)(var2 - (this.xPosition + 4)) / (float)(this.width - 8);
               this.field_146156_o = MathHelper.clamp_float(this.field_146156_o, 0.0F, 1.0F);
               var1.gameSettings.setSoundLevel(this.field_146153_r, this.field_146156_o);
               var1.gameSettings.saveOptions();
               this.displayString = String.valueOf((new StringBuilder(String.valueOf(this.field_146152_s))).append(": ").append(this.this$0.getSoundVolume(this.field_146153_r)));
            }

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(this.xPosition + (int)(this.field_146156_o * (float)(this.width - 8)), this.yPosition, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.xPosition + (int)(this.field_146156_o * (float)(this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
         }

      }

      public void mouseReleased(int var1, int var2) {
         if (this.field_146155_p) {
            if (this.field_146153_r == SoundCategory.MASTER) {
               float var3 = 1.0F;
            } else {
               GuiScreenOptionsSounds.access$0(this.this$0).getSoundLevel(this.field_146153_r);
            }

            this.this$0.mc.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 1.0F));
         }

         this.field_146155_p = false;
      }

      public Button(GuiScreenOptionsSounds var1, int var2, int var3, int var4, SoundCategory var5, boolean var6) {
         super(var2, var3, var4, var6 ? 310 : 150, 20, "");
         this.this$0 = var1;
         this.field_146156_o = 1.0F;
         this.field_146153_r = var5;
         this.field_146152_s = I18n.format(String.valueOf((new StringBuilder("soundCategory.")).append(var5.getCategoryName())));
         this.displayString = String.valueOf((new StringBuilder(String.valueOf(this.field_146152_s))).append(": ").append(var1.getSoundVolume(var5)));
         this.field_146156_o = GuiScreenOptionsSounds.access$0(var1).getSoundLevel(var5);
      }

      public void playPressSound(SoundHandler var1) {
      }

      protected int getHoverState(boolean var1) {
         return 0;
      }
   }
}
