package net.minecraft.command;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;

public class CommandResultStats {
   private static final int field_179676_a = CommandResultStats.Type.values().length;
   private String[] field_179675_c;
   private String[] field_179673_d;
   private static final String[] field_179674_b;
   private static final String __OBFID = "CL_00002364";

   public void func_179671_a(CommandResultStats var1) {
      CommandResultStats.Type[] var2 = CommandResultStats.Type.values();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         CommandResultStats.Type var5 = var2[var4];
         func_179667_a(this, var5, var1.field_179675_c[var5.func_179636_a()], var1.field_179673_d[var5.func_179636_a()]);
      }

   }

   private static void func_179669_a(CommandResultStats var0, CommandResultStats.Type var1) {
      if (var0.field_179675_c != field_179674_b && var0.field_179673_d != field_179674_b) {
         var0.field_179675_c[var1.func_179636_a()] = null;
         var0.field_179673_d[var1.func_179636_a()] = null;
         boolean var2 = true;
         CommandResultStats.Type[] var3 = CommandResultStats.Type.values();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            CommandResultStats.Type var6 = var3[var5];
            if (var0.field_179675_c[var6.func_179636_a()] != null && var0.field_179673_d[var6.func_179636_a()] != null) {
               var2 = false;
               break;
            }
         }

         if (var2) {
            var0.field_179675_c = field_179674_b;
            var0.field_179673_d = field_179674_b;
         }
      }

   }

   public void func_179670_b(NBTTagCompound var1) {
      NBTTagCompound var2 = new NBTTagCompound();
      CommandResultStats.Type[] var3 = CommandResultStats.Type.values();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         CommandResultStats.Type var6 = var3[var5];
         String var7 = this.field_179675_c[var6.func_179636_a()];
         String var8 = this.field_179673_d[var6.func_179636_a()];
         if (var7 != null && var8 != null) {
            var2.setString(String.valueOf((new StringBuilder(String.valueOf(var6.func_179637_b()))).append("Name")), var7);
            var2.setString(String.valueOf((new StringBuilder(String.valueOf(var6.func_179637_b()))).append("Objective")), var8);
         }
      }

      if (!var2.hasNoTags()) {
         var1.setTag("CommandStats", var2);
      }

   }

   public static void func_179667_a(CommandResultStats var0, CommandResultStats.Type var1, String var2, String var3) {
      if (var2 != null && var2.length() != 0 && var3 != null && var3.length() != 0) {
         if (var0.field_179675_c == field_179674_b || var0.field_179673_d == field_179674_b) {
            var0.field_179675_c = new String[field_179676_a];
            var0.field_179673_d = new String[field_179676_a];
         }

         var0.field_179675_c[var1.func_179636_a()] = var2;
         var0.field_179673_d[var1.func_179636_a()] = var3;
      } else {
         func_179669_a(var0, var1);
      }

   }

   static {
      field_179674_b = new String[field_179676_a];
   }

   public CommandResultStats() {
      this.field_179675_c = field_179674_b;
      this.field_179673_d = field_179674_b;
   }

   public void func_179668_a(NBTTagCompound var1) {
      if (var1.hasKey("CommandStats", 10)) {
         NBTTagCompound var2 = var1.getCompoundTag("CommandStats");
         CommandResultStats.Type[] var3 = CommandResultStats.Type.values();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            CommandResultStats.Type var6 = var3[var5];
            String var7 = String.valueOf((new StringBuilder(String.valueOf(var6.func_179637_b()))).append("Name"));
            String var8 = String.valueOf((new StringBuilder(String.valueOf(var6.func_179637_b()))).append("Objective"));
            if (var2.hasKey(var7, 8) && var2.hasKey(var8, 8)) {
               String var9 = var2.getString(var7);
               String var10 = var2.getString(var8);
               func_179667_a(this, var6, var9, var10);
            }
         }
      }

   }

   public void func_179672_a(ICommandSender var1, CommandResultStats.Type var2, int var3) {
      String var4 = this.field_179675_c[var2.func_179636_a()];
      if (var4 != null) {
         String var5;
         try {
            var5 = CommandBase.func_175758_e(var1, var4);
         } catch (EntityNotFoundException var10) {
            return;
         }

         String var6 = this.field_179673_d[var2.func_179636_a()];
         if (var6 != null) {
            Scoreboard var7 = var1.getEntityWorld().getScoreboard();
            ScoreObjective var8 = var7.getObjective(var6);
            if (var8 != null && var7.func_178819_b(var5, var8)) {
               Score var9 = var7.getValueFromObjective(var5, var8);
               var9.setScorePoints(var3);
            }
         }
      }

   }

   public static enum Type {
      AFFECTED_ENTITIES("AFFECTED_ENTITIES", 2, 2, "AffectedEntities");

      final String field_179640_g;
      private static final CommandResultStats.Type[] ENUM$VALUES = new CommandResultStats.Type[]{SUCCESS_COUNT, AFFECTED_BLOCKS, AFFECTED_ENTITIES, AFFECTED_ITEMS, QUERY_RESULT};
      final int field_179639_f;
      private static final CommandResultStats.Type[] $VALUES = new CommandResultStats.Type[]{SUCCESS_COUNT, AFFECTED_BLOCKS, AFFECTED_ENTITIES, AFFECTED_ITEMS, QUERY_RESULT};
      SUCCESS_COUNT("SUCCESS_COUNT", 0, 0, "SuccessCount");

      private static final String __OBFID = "CL_00002363";
      AFFECTED_BLOCKS("AFFECTED_BLOCKS", 1, 1, "AffectedBlocks"),
      QUERY_RESULT("QUERY_RESULT", 4, 4, "QueryResult"),
      AFFECTED_ITEMS("AFFECTED_ITEMS", 3, 3, "AffectedItems");

      public int func_179636_a() {
         return this.field_179639_f;
      }

      private Type(String var3, int var4, int var5, String var6) {
         this.field_179639_f = var5;
         this.field_179640_g = var6;
      }

      public static String[] func_179634_c() {
         String[] var0 = new String[values().length];
         int var1 = 0;
         CommandResultStats.Type[] var2 = values();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            CommandResultStats.Type var5 = var2[var4];
            var0[var1++] = var5.func_179637_b();
         }

         return var0;
      }

      public String func_179637_b() {
         return this.field_179640_g;
      }

      public static CommandResultStats.Type func_179635_a(String var0) {
         CommandResultStats.Type[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            CommandResultStats.Type var4 = var1[var3];
            if (var4.func_179637_b().equals(var0)) {
               return var4;
            }
         }

         return null;
      }
   }
}
