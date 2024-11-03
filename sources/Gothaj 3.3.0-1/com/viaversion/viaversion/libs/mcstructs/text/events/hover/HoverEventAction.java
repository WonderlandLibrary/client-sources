package com.viaversion.viaversion.libs.mcstructs.text.events.hover;

public enum HoverEventAction {
   SHOW_TEXT("show_text", true),
   SHOW_ACHIEVEMENT("show_achievement", true),
   SHOW_ITEM("show_item", true),
   SHOW_ENTITY("show_entity", true);

   private final String name;
   private final boolean userDefinable;

   public static HoverEventAction getByName(String name) {
      for (HoverEventAction hoverEventAction : values()) {
         if (hoverEventAction.getName().equalsIgnoreCase(name)) {
            return hoverEventAction;
         }
      }

      return null;
   }

   public static HoverEventAction getByName(String name, boolean ignoreCase) {
      for (HoverEventAction hoverEventAction : values()) {
         if (ignoreCase) {
            if (hoverEventAction.getName().equalsIgnoreCase(name)) {
               return hoverEventAction;
            }
         } else if (hoverEventAction.getName().equals(name)) {
            return hoverEventAction;
         }
      }

      return null;
   }

   private HoverEventAction(String name, boolean userDefinable) {
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
