package net.minecraft.client.gui;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundEventAccessorComposite;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;

class GuiOptions$1 extends GuiButton {
   final GuiOptions this$0;

   GuiOptions$1(GuiOptions var1, int var2, int var3, int var4, int var5, int var6, String var7) {
      super(var2, var3, var4, var5, var6, var7);
      this.this$0 = var1;
   }

   public void playPressSound(SoundHandler var1) {
      SoundEventAccessorComposite var2 = var1.getRandomSoundFromCategories(new SoundCategory[]{SoundCategory.ANIMALS, SoundCategory.BLOCKS, SoundCategory.MOBS, SoundCategory.PLAYERS, SoundCategory.WEATHER});
      var1.playSound(PositionedSoundRecord.create(var2.getSoundEventLocation(), 0.5F));
   }
}
