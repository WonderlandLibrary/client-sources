package net.minecraft.client.resources.data;

public class AnimationFrame {
   private final int frameIndex;
   private final int frameTime;

   public AnimationFrame(int var1) {
      this(var1, -1);
   }

   public AnimationFrame(int var1, int var2) {
      this.frameIndex = var1;
      this.frameTime = var2;
   }

   public boolean hasNoTime() {
      return this.frameTime == -1;
   }

   public int getFrameTime() {
      return this.frameTime;
   }

   public int getFrameIndex() {
      return this.frameIndex;
   }
}
