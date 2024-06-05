package net.minecraft.util;

import java.util.Iterator;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;

public class ChatComponentScore extends ChatComponentStyle {
   private String field_179998_d = "";
   private final String field_180000_c;
   private static final String __OBFID = "CL_00002309";
   private final String field_179999_b;

   public void func_179997_b(String var1) {
      this.field_179998_d = var1;
   }

   public String toString() {
      return String.valueOf((new StringBuilder("ScoreComponent{name='")).append(this.field_179999_b).append('\'').append("objective='").append(this.field_180000_c).append('\'').append(", siblings=").append(this.siblings).append(", style=").append(this.getChatStyle()).append('}'));
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof ChatComponentScore)) {
         return false;
      } else {
         ChatComponentScore var2 = (ChatComponentScore)var1;
         return this.field_179999_b.equals(var2.field_179999_b) && this.field_180000_c.equals(var2.field_180000_c) && super.equals(var1);
      }
   }

   public ChatComponentScore(String var1, String var2) {
      this.field_179999_b = var1;
      this.field_180000_c = var2;
   }

   public String func_179994_h() {
      return this.field_180000_c;
   }

   public ChatComponentScore func_179996_i() {
      ChatComponentScore var1 = new ChatComponentScore(this.field_179999_b, this.field_180000_c);
      var1.func_179997_b(this.field_179998_d);
      var1.setChatStyle(this.getChatStyle().createShallowCopy());
      Iterator var2 = this.getSiblings().iterator();

      while(var2.hasNext()) {
         IChatComponent var3 = (IChatComponent)var2.next();
         var1.appendSibling(var3.createCopy());
      }

      return var1;
   }

   public String func_179995_g() {
      return this.field_179999_b;
   }

   public String getUnformattedTextForChat() {
      MinecraftServer var1 = MinecraftServer.getServer();
      if (var1 != null && var1.func_175578_N() && StringUtils.isNullOrEmpty(this.field_179998_d)) {
         Scoreboard var2 = var1.worldServerForDimension(0).getScoreboard();
         ScoreObjective var3 = var2.getObjective(this.field_180000_c);
         if (var2.func_178819_b(this.field_179999_b, var3)) {
            Score var4 = var2.getValueFromObjective(this.field_179999_b, var3);
            this.func_179997_b(String.format("%d", var4.getScorePoints()));
         } else {
            this.field_179998_d = "";
         }
      }

      return this.field_179998_d;
   }

   public IChatComponent createCopy() {
      return this.func_179996_i();
   }
}
