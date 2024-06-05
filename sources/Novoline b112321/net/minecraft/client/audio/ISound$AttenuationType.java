package net.minecraft.client.audio;

public enum ISound$AttenuationType {
   NONE(0),
   LINEAR(2);

   private final int type;
   private static final ISound$AttenuationType[] $VALUES = new ISound$AttenuationType[]{NONE, LINEAR};

   private ISound$AttenuationType(int var3) {
      this.type = var3;
   }

   public int getTypeInt() {
      return this.type;
   }
}
