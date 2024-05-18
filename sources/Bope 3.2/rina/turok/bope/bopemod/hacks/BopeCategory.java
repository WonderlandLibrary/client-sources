package rina.turok.bope.bopemod.hacks;

public enum BopeCategory {
   BOPE_CHAT("B.O.P.E Chat", "BopeChat"),
   BOPE_COMBAT("B.O.P.E Combat", "BopeCombat"),
   BOPE_MOVEMENT("B.O.P.E Movement", "BopeMovement"),
   BOPE_RENDER("B.O.P.E Render", "BopeRender"),
   BOPE_EXPLOIT("B.O.P.E Exploit", "BopeExploit"),
   BOPE_MISC("B.O.P.E Misc", "BopeMisc"),
   BOPE_PLAYER("B.O.P.E Player", "BopePlayer"),
   BOPE_GUI("B.O.P.E GUI", "BopeGUI"),
   BOPE_SYS("B.O.P.E SYS", "BopeSys");

   String name;
   String tag;

   private BopeCategory(String name, String tag) {
      this.name = name;
      this.tag = tag;
   }

   public String get_name() {
      return this.name;
   }

   public String get_tag() {
      return this.tag;
   }
}
