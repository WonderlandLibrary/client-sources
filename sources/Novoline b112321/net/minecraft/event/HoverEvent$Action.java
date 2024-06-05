package net.minecraft.event;

import com.google.common.collect.Maps;
import java.util.Map;

public enum HoverEvent$Action {
   SHOW_TEXT("show_text", true),
   SHOW_ACHIEVEMENT("show_achievement", true),
   SHOW_ITEM("show_item", true),
   SHOW_ENTITY("show_entity", true);

   private static final Map nameMapping = Maps.newHashMap();
   private final boolean allowedInChat;
   private final String canonicalName;
   private static final HoverEvent$Action[] $VALUES = new HoverEvent$Action[]{SHOW_TEXT, SHOW_ACHIEVEMENT, SHOW_ITEM, SHOW_ENTITY};

   private HoverEvent$Action(String var3, boolean var4) {
      this.canonicalName = var3;
      this.allowedInChat = var4;
   }

   public boolean shouldAllowInChat() {
      return this.allowedInChat;
   }

   public String getCanonicalName() {
      return this.canonicalName;
   }

   public static HoverEvent$Action getValueByCanonicalName(String var0) {
      return (HoverEvent$Action)nameMapping.get(var0);
   }

   static {
      for(HoverEvent$Action var11 : values()) {
         nameMapping.put(var11.getCanonicalName(), var11);
      }

   }
}
