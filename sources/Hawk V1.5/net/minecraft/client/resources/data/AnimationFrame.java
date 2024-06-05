package net.minecraft.client.resources.data;

public class AnimationFrame {
   private static final String __OBFID = "CL_00001104";
   private final int frameTime;
   private final int frameIndex;

   public AnimationFrame(int var1, int var2) {
      this.frameIndex = var1;
      this.frameTime = var2;
   }

   public int getFrameIndex() {
      return this.frameIndex;
   }

   public int getFrameTime() {
      return this.frameTime;
   }

   public AnimationFrame(int var1) {
      this(var1, -1);
   }

   public boolean hasNoTime() {
      return this.frameTime == -1;
   }
}
