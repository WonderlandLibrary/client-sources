package net.minecraft.client.audio;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.audio.SoundCategory;

public class SoundList {
   private final List soundList = Lists.newArrayList();
   private boolean replaceExisting;
   private SoundCategory category;

   public List getSoundList() {
      return this.soundList;
   }

   public boolean canReplaceExisting() {
      return this.replaceExisting;
   }

   public void setReplaceExisting(boolean var1) {
      this.replaceExisting = var1;
   }

   public SoundCategory getSoundCategory() {
      return this.category;
   }

   public void setSoundCategory(SoundCategory var1) {
      this.category = var1;
   }
}
