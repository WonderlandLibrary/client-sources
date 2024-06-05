package net.minecraft.client.resources.data;

import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class AnimationMetadataSection implements IMetadataSection {
   private final List animationFrames;
   private final int frameHeight;
   private final boolean field_177220_e;
   private final int frameWidth;
   private static final String __OBFID = "CL_00001106";
   private final int frameTime;

   private AnimationFrame getAnimationFrame(int var1) {
      return (AnimationFrame)this.animationFrames.get(var1);
   }

   public int getFrameHeight() {
      return this.frameHeight;
   }

   public boolean frameHasTime(int var1) {
      return !((AnimationFrame)this.animationFrames.get(var1)).hasNoTime();
   }

   public boolean func_177219_e() {
      return this.field_177220_e;
   }

   public Set getFrameIndexSet() {
      HashSet var1 = Sets.newHashSet();
      Iterator var2 = this.animationFrames.iterator();

      while(var2.hasNext()) {
         AnimationFrame var3 = (AnimationFrame)var2.next();
         var1.add(var3.getFrameIndex());
      }

      return var1;
   }

   public AnimationMetadataSection(List var1, int var2, int var3, int var4, boolean var5) {
      this.animationFrames = var1;
      this.frameWidth = var2;
      this.frameHeight = var3;
      this.frameTime = var4;
      this.field_177220_e = var5;
   }

   public int getFrameWidth() {
      return this.frameWidth;
   }

   public int getFrameTimeSingle(int var1) {
      AnimationFrame var2 = this.getAnimationFrame(var1);
      return var2.hasNoTime() ? this.frameTime : var2.getFrameTime();
   }

   public int getFrameCount() {
      return this.animationFrames.size();
   }

   public int getFrameIndex(int var1) {
      return ((AnimationFrame)this.animationFrames.get(var1)).getFrameIndex();
   }

   public int getFrameTime() {
      return this.frameTime;
   }
}
