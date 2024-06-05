package net.minecraft.event;

import com.google.common.collect.Maps;
import java.util.Map;

public enum ClickEvent$Action {
   OPEN_URL("open_url", true),
   OPEN_FILE("open_file", false),
   RUN_COMMAND("run_command", true),
   TWITCH_USER_INFO("twitch_user_info", false),
   SUGGEST_COMMAND("suggest_command", true),
   CHANGE_PAGE("change_page", true);

   private static final Map nameMapping = Maps.newHashMap();
   private final boolean allowedInChat;
   private final String canonicalName;
   private static final ClickEvent$Action[] $VALUES = new ClickEvent$Action[]{OPEN_URL, OPEN_FILE, RUN_COMMAND, TWITCH_USER_INFO, SUGGEST_COMMAND, CHANGE_PAGE};

   private ClickEvent$Action(String var3, boolean var4) {
      this.canonicalName = var3;
      this.allowedInChat = var4;
   }

   public boolean shouldAllowInChat() {
      return this.allowedInChat;
   }

   public String getCanonicalName() {
      return this.canonicalName;
   }

   public static ClickEvent$Action getValueByCanonicalName(String var0) {
      return (ClickEvent$Action)nameMapping.get(var0);
   }

   static {
      for(ClickEvent$Action var11 : values()) {
         nameMapping.put(var11.getCanonicalName(), var11);
      }

   }
}
