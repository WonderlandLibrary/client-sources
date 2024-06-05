package net.minecraft.event;

import com.google.common.collect.Maps;
import java.util.Map;

public class ClickEvent {
   private final String value;
   private final ClickEvent.Action action;
   private static final String __OBFID = "CL_00001260";

   public ClickEvent(ClickEvent.Action var1, String var2) {
      this.action = var1;
      this.value = var2;
   }

   public String getValue() {
      return this.value;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         ClickEvent var2 = (ClickEvent)var1;
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

   public ClickEvent.Action getAction() {
      return this.action;
   }

   public int hashCode() {
      int var1 = this.action.hashCode();
      var1 = 31 * var1 + (this.value != null ? this.value.hashCode() : 0);
      return var1;
   }

   public String toString() {
      return String.valueOf((new StringBuilder("ClickEvent{action=")).append(this.action).append(", value='").append(this.value).append('\'').append('}'));
   }

   public static enum Action {
      private static final ClickEvent.Action[] $VALUES = new ClickEvent.Action[]{OPEN_URL, OPEN_FILE, RUN_COMMAND, TWITCH_USER_INFO, SUGGEST_COMMAND, CHANGE_PAGE};
      private static final String __OBFID = "CL_00001261";
      OPEN_URL("OPEN_URL", 0, "open_url", true),
      CHANGE_PAGE("CHANGE_PAGE", 5, "change_page", true),
      SUGGEST_COMMAND("SUGGEST_COMMAND", 4, "suggest_command", true),
      RUN_COMMAND("RUN_COMMAND", 2, "run_command", true),
      TWITCH_USER_INFO("TWITCH_USER_INFO", 3, "twitch_user_info", false);

      private static final ClickEvent.Action[] ENUM$VALUES = new ClickEvent.Action[]{OPEN_URL, OPEN_FILE, RUN_COMMAND, TWITCH_USER_INFO, SUGGEST_COMMAND, CHANGE_PAGE};
      OPEN_FILE("OPEN_FILE", 1, "open_file", false);

      private final String canonicalName;
      private static final Map nameMapping = Maps.newHashMap();
      private final boolean allowedInChat;

      private Action(String var3, int var4, String var5, boolean var6) {
         this.canonicalName = var5;
         this.allowedInChat = var6;
      }

      public static ClickEvent.Action getValueByCanonicalName(String var0) {
         return (ClickEvent.Action)nameMapping.get(var0);
      }

      public String getCanonicalName() {
         return this.canonicalName;
      }

      public boolean shouldAllowInChat() {
         return this.allowedInChat;
      }

      static {
         ClickEvent.Action[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            ClickEvent.Action var3 = var0[var2];
            nameMapping.put(var3.getCanonicalName(), var3);
         }

      }
   }
}
