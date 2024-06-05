package net;

import com.google.common.base.Predicate;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;

final class a24 implements Predicate {
   final Map a;

   a24(Map var1) {
      this.a = var1;
   }

   public boolean a(Entity var1) {
      Scoreboard var2 = MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
      Iterator var3 = this.a.entrySet().iterator();
      if(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         String var5 = (String)var4.getKey();
         boolean var6 = false;
         if(var5.endsWith("_min") && var5.length() > 4) {
            var6 = true;
            var5 = var5.substring(0, var5.length() - 4);
         }

         ScoreObjective var7 = var2.getObjective(var5);
         return false;
      } else {
         return true;
      }
   }
}
