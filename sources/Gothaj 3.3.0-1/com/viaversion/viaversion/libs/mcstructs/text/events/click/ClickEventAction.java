package com.viaversion.viaversion.libs.mcstructs.text.events.click;

public enum ClickEventAction {
   OPEN_URL("open_url", true),
   OPEN_FILE("open_file", false),
   RUN_COMMAND("run_command", true),
   TWITCH_USER_INFO("twitch_user_info", false),
   SUGGEST_COMMAND("suggest_command", true),
   CHANGE_PAGE("change_page", true),
   COPY_TO_CLIPBOARD("copy_to_clipboard", true);

   private final String name;
   private final boolean userDefinable;

   public static ClickEventAction getByName(String name) {
      return getByName(name, true);
   }

   public static ClickEventAction getByName(String name, boolean ignoreCase) {
      for (ClickEventAction clickEventAction : values()) {
         if (ignoreCase) {
            if (clickEventAction.getName().equalsIgnoreCase(name)) {
               return clickEventAction;
            }
         } else if (clickEventAction.getName().equals(name)) {
            return clickEventAction;
         }
      }

      return null;
   }

   private ClickEventAction(String name, boolean userDefinable) {
      this.name = name;
      this.userDefinable = userDefinable;
   }

   public String getName() {
      return this.name;
   }

   public boolean isUserDefinable() {
      return this.userDefinable;
   }
}
