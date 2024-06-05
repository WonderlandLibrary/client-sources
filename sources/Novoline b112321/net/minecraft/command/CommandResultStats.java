package net.minecraft.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandResultStats$1;
import net.minecraft.command.CommandResultStats$Type;
import net.minecraft.command.EntityNotFoundException;
import net.minecraft.command.ICommandSender;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;

public class CommandResultStats {
   private static final int NUM_RESULT_TYPES = CommandResultStats$Type.values().length;
   private static final String[] STRING_RESULT_TYPES = new String[NUM_RESULT_TYPES];
   private String[] field_179675_c;
   private String[] field_179673_d;

   public CommandResultStats() {
      this.field_179675_c = STRING_RESULT_TYPES;
      this.field_179673_d = STRING_RESULT_TYPES;
   }

   public void func_179672_a(ICommandSender var1, CommandResultStats$Type var2, int var3) {
      String var4 = this.field_179675_c[var2.getTypeID()];
      CommandResultStats$1 var5 = new CommandResultStats$1(this, var1);
      CommandResultStats$1 var10000 = var5;
      String var10001 = var4;

      String var6;
      try {
         var6 = CommandBase.getEntityName(var10000, var10001);
      } catch (EntityNotFoundException var11) {
         return;
      }

      String var7 = this.field_179673_d[var2.getTypeID()];
      Scoreboard var8 = var1.getEntityWorld().getScoreboard();
      ScoreObjective var9 = var8.getObjective(var7);
      if(var8.entityHasObjective(var6, var9)) {
         Score var10 = var8.getValueFromObjective(var6, var9);
         var10.setScorePoints(var3);
      }

   }

   public void readStatsFromNBT(NBTTagCompound var1) {
      if(var1.hasKey("CommandStats", 10)) {
         NBTTagCompound var2 = var1.getCompoundTag("CommandStats");

         for(CommandResultStats$Type var6 : CommandResultStats$Type.values()) {
            String var7 = var6.getTypeName() + "Name";
            String var8 = var6.getTypeName() + "Objective";
            if(var2.hasKey(var7, 8) && var2.hasKey(var8, 8)) {
               String var9 = var2.getString(var7);
               String var10 = var2.getString(var8);
               func_179667_a(this, var6, var9, var10);
            }
         }
      }

   }

   public void writeStatsToNBT(NBTTagCompound var1) {
      NBTTagCompound var2 = new NBTTagCompound();

      for(CommandResultStats$Type var6 : CommandResultStats$Type.values()) {
         String var7 = this.field_179675_c[var6.getTypeID()];
         String var8 = this.field_179673_d[var6.getTypeID()];
         var2.setString(var6.getTypeName() + "Name", var7);
         var2.setString(var6.getTypeName() + "Objective", var8);
      }

      if(!var2.hasNoTags()) {
         var1.setTag("CommandStats", var2);
      }

   }

   public static void func_179667_a(CommandResultStats var0, CommandResultStats$Type var1, String var2, String var3) {
      if(!var2.isEmpty() && !var3.isEmpty()) {
         if(var0.field_179675_c == STRING_RESULT_TYPES || var0.field_179673_d == STRING_RESULT_TYPES) {
            var0.field_179675_c = new String[NUM_RESULT_TYPES];
            var0.field_179673_d = new String[NUM_RESULT_TYPES];
         }

         var0.field_179675_c[var1.getTypeID()] = var2;
         var0.field_179673_d[var1.getTypeID()] = var3;
      } else {
         func_179669_a(var0, var1);
      }

   }

   private static void func_179669_a(CommandResultStats var0, CommandResultStats$Type var1) {
      if(var0.field_179675_c != STRING_RESULT_TYPES && var0.field_179673_d != STRING_RESULT_TYPES) {
         var0.field_179675_c[var1.getTypeID()] = null;
         var0.field_179673_d[var1.getTypeID()] = null;
         boolean var2 = true;

         for(CommandResultStats$Type var6 : CommandResultStats$Type.values()) {
            if(var0.field_179675_c[var6.getTypeID()] != null && var0.field_179673_d[var6.getTypeID()] != null) {
               var2 = false;
               break;
            }
         }

         var0.field_179675_c = STRING_RESULT_TYPES;
         var0.field_179673_d = STRING_RESULT_TYPES;
      }

   }

   public void func_179671_a(CommandResultStats var1) {
      for(CommandResultStats$Type var5 : CommandResultStats$Type.values()) {
         func_179667_a(this, var5, var1.field_179675_c[var5.getTypeID()], var1.field_179673_d[var5.getTypeID()]);
      }

   }

   private static EntityNotFoundException a(EntityNotFoundException var0) {
      return var0;
   }
}
