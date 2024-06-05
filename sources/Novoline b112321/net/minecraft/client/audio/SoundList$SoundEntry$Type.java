package net.minecraft.client.audio;

public enum SoundList$SoundEntry$Type {
   FILE("file"),
   SOUND_EVENT("event");

   private final String field_148583_c;
   private static final SoundList$SoundEntry$Type[] $VALUES = new SoundList$SoundEntry$Type[]{FILE, SOUND_EVENT};

   private SoundList$SoundEntry$Type(String var3) {
      this.field_148583_c = var3;
   }

   public static SoundList$SoundEntry$Type getType(String var0) {
      for(SoundList$SoundEntry$Type var4 : values()) {
         if(var4.field_148583_c.equals(var0)) {
            return var4;
         }
      }

      return null;
   }
}
