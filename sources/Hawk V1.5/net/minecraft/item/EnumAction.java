package net.minecraft.item;

public enum EnumAction {
   DRINK("DRINK", 2);

   private static final EnumAction[] ENUM$VALUES = new EnumAction[]{NONE, EAT, DRINK, BLOCK, BOW};
   private static final EnumAction[] $VALUES = new EnumAction[]{NONE, EAT, DRINK, BLOCK, BOW};
   NONE("NONE", 0);

   private static final String __OBFID = "CL_00000073";
   BOW("BOW", 4),
   EAT("EAT", 1),
   BLOCK("BLOCK", 3);

   private EnumAction(String var3, int var4) {
   }
}
