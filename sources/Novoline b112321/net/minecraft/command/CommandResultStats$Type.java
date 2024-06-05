package net.minecraft.command;

public enum CommandResultStats$Type {
   SUCCESS_COUNT(0, "SuccessCount"),
   AFFECTED_BLOCKS(1, "AffectedBlocks"),
   AFFECTED_ENTITIES(2, "AffectedEntities"),
   AFFECTED_ITEMS(3, "AffectedItems"),
   QUERY_RESULT(4, "QueryResult");

   final int typeID;
   final String typeName;
   private static final CommandResultStats$Type[] $VALUES = new CommandResultStats$Type[]{SUCCESS_COUNT, AFFECTED_BLOCKS, AFFECTED_ENTITIES, AFFECTED_ITEMS, QUERY_RESULT};

   private CommandResultStats$Type(int var3, String var4) {
      this.typeID = var3;
      this.typeName = var4;
   }

   public int getTypeID() {
      return this.typeID;
   }

   public String getTypeName() {
      return this.typeName;
   }

   public static String[] getTypeNames() {
      String[] var0 = new String[values().length];
      int var1 = 0;

      for(CommandResultStats$Type var5 : values()) {
         var0[var1++] = var5.getTypeName();
      }

      return var0;
   }

   public static CommandResultStats$Type getTypeByName(String var0) {
      for(CommandResultStats$Type var4 : values()) {
         if(var4.getTypeName().equals(var0)) {
            return var4;
         }
      }

      return null;
   }
}
