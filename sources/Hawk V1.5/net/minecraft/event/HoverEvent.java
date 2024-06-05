package net.minecraft.event;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.util.IChatComponent;

public class HoverEvent {
   private static final String __OBFID = "CL_00001264";
   private final IChatComponent value;
   private final HoverEvent.Action action;

   public HoverEvent(HoverEvent.Action var1, IChatComponent var2) {
      this.action = var1;
      this.value = var2;
   }

   public int hashCode() {
      int var1 = this.action.hashCode();
      var1 = 31 * var1 + (this.value != null ? this.value.hashCode() : 0);
      return var1;
   }

   public String toString() {
      return String.valueOf((new StringBuilder("HoverEvent{action=")).append(this.action).append(", value='").append(this.value).append('\'').append('}'));
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         HoverEvent var2 = (HoverEvent)var1;
         if (this.action != var2.action) {
            return false;
         } else {
            if (this.value != null) {
               if (!this.value.equals(var2.value)) {
                  return false;
               }
            } else if (var2.value != null) {
               return false;
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public HoverEvent.Action getAction() {
      return this.action;
   }

   public IChatComponent getValue() {
      return this.value;
   }

   public static enum Action {
      SHOW_ITEM("SHOW_ITEM", 2, "show_item", true);

      private final boolean allowedInChat;
      SHOW_ENTITY("SHOW_ENTITY", 3, "show_entity", true);

      private static final String __OBFID = "CL_00001265";
      private static final Map nameMapping = Maps.newHashMap();
      SHOW_TEXT("SHOW_TEXT", 0, "show_text", true);

      private final String canonicalName;
      SHOW_ACHIEVEMENT("SHOW_ACHIEVEMENT", 1, "show_achievement", true);

      private static final HoverEvent.Action[] $VALUES = new HoverEvent.Action[]{SHOW_TEXT, SHOW_ACHIEVEMENT, SHOW_ITEM, SHOW_ENTITY};
      private static final HoverEvent.Action[] ENUM$VALUES = new HoverEvent.Action[]{SHOW_TEXT, SHOW_ACHIEVEMENT, SHOW_ITEM, SHOW_ENTITY};

      public String getCanonicalName() {
         return this.canonicalName;
      }

      public boolean shouldAllowInChat() {
         return this.allowedInChat;
      }

      static {
         HoverEvent.Action[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            HoverEvent.Action var3 = var0[var2];
            nameMapping.put(var3.getCanonicalName(), var3);
         }

      }

      private Action(String var3, int var4, String var5, boolean var6) {
         this.canonicalName = var5;
         this.allowedInChat = var6;
      }

      public static HoverEvent.Action getValueByCanonicalName(String var0) {
         return (HoverEvent.Action)nameMapping.get(var0);
      }
   }
}
