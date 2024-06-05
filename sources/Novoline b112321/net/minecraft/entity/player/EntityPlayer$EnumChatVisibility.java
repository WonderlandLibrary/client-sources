package net.minecraft.entity.player;

public enum EntityPlayer$EnumChatVisibility {
   FULL(0, "options.chat.visibility.full"),
   SYSTEM(1, "options.chat.visibility.system"),
   HIDDEN(2, "options.chat.visibility.hidden");

   private static final EntityPlayer$EnumChatVisibility[] ID_LOOKUP = new EntityPlayer$EnumChatVisibility[values().length];
   private final int chatVisibility;
   private final String resourceKey;
   private static final EntityPlayer$EnumChatVisibility[] $VALUES = new EntityPlayer$EnumChatVisibility[]{FULL, SYSTEM, HIDDEN};

   private EntityPlayer$EnumChatVisibility(int var3, String var4) {
      this.chatVisibility = var3;
      this.resourceKey = var4;
   }

   public int getChatVisibility() {
      return this.chatVisibility;
   }

   public static EntityPlayer$EnumChatVisibility getEnumChatVisibility(int var0) {
      return ID_LOOKUP[var0 % ID_LOOKUP.length];
   }

   public String getResourceKey() {
      return this.resourceKey;
   }

   static {
      for(EntityPlayer$EnumChatVisibility var11 : values()) {
         ID_LOOKUP[var11.chatVisibility] = var11;
      }

   }
}
