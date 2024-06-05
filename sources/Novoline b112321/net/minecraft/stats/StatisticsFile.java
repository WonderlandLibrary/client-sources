package net.minecraft.stats;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S37PacketStatistics;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.stats.StatList;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IJsonSerializable;
import net.minecraft.util.TupleIntJsonSerializable;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StatisticsFile extends StatFileWriter {
   private static final Logger LOGGER = LogManager.getLogger();
   private final MinecraftServer mcServer;
   private final File statsFile;
   private final Set field_150888_e = Sets.newHashSet();
   private int field_150885_f = -300;
   private boolean field_150886_g = false;

   public StatisticsFile(MinecraftServer var1, File var2) {
      this.mcServer = var1;
      this.statsFile = var2;
   }

   public void readStatFile() {
      if(this.statsFile.isFile()) {
         try {
            this.statsData.clear();
            this.statsData.putAll(this.parseJson(FileUtils.readFileToString(this.statsFile)));
         } catch (IOException var2) {
            LOGGER.error("Couldn\'t read statistics file " + this.statsFile, var2);
         } catch (JsonParseException var3) {
            LOGGER.error("Couldn\'t parse statistics file " + this.statsFile, var3);
         }
      }

   }

   public void saveStatFile() {
      try {
         FileUtils.writeStringToFile(this.statsFile, dumpJson(this.statsData));
      } catch (IOException var2) {
         LOGGER.error("Couldn\'t save stats", var2);
      }

   }

   public void unlockAchievement(EntityPlayer var1, StatBase var2, int var3) {
      int var4 = var2.isAchievement()?this.readStat(var2):0;
      super.unlockAchievement(var1, var2, var3);
      this.field_150888_e.add(var2);
      if(var2.isAchievement()) {
         this.field_150886_g = true;
         if(this.mcServer.isAnnouncingPlayerAchievements()) {
            this.mcServer.getConfigurationManager().sendChatMsg(new ChatComponentTranslation("chat.type.achievement", new Object[]{var1.getDisplayName(), var2.func_150955_j()}));
         }
      }

      if(var2.isAchievement()) {
         this.field_150886_g = true;
         if(this.mcServer.isAnnouncingPlayerAchievements()) {
            this.mcServer.getConfigurationManager().sendChatMsg(new ChatComponentTranslation("chat.type.achievement.taken", new Object[]{var1.getDisplayName(), var2.func_150955_j()}));
         }
      }

   }

   public Set func_150878_c() {
      HashSet var1 = Sets.newHashSet(this.field_150888_e);
      this.field_150888_e.clear();
      this.field_150886_g = false;
      return var1;
   }

   public Map parseJson(String var1) {
      JsonElement var2 = (new JsonParser()).parse(var1);
      if(!var2.isJsonObject()) {
         return Maps.newHashMap();
      } else {
         JsonObject var3 = var2.getAsJsonObject();
         HashMap var4 = Maps.newHashMap();

         for(Entry var6 : var3.entrySet()) {
            StatBase var7 = StatList.getOneShotStat((String)var6.getKey());
            TupleIntJsonSerializable var8 = new TupleIntJsonSerializable();
            if(((JsonElement)var6.getValue()).isJsonPrimitive() && ((JsonElement)var6.getValue()).getAsJsonPrimitive().isNumber()) {
               var8.setIntegerValue(((JsonElement)var6.getValue()).getAsInt());
            } else if(((JsonElement)var6.getValue()).isJsonObject()) {
               JsonObject var9 = ((JsonElement)var6.getValue()).getAsJsonObject();
               if(var9.has("value") && var9.get("value").isJsonPrimitive() && var9.get("value").getAsJsonPrimitive().isNumber()) {
                  var8.setIntegerValue(var9.getAsJsonPrimitive("value").getAsInt());
               }

               if(var9.has("progress") && var7.func_150954_l() != null) {
                  StatBase var10000 = var7;

                  try {
                     Constructor var10 = var10000.func_150954_l().getConstructor(new Class[0]);
                     IJsonSerializable var11 = (IJsonSerializable)var10.newInstance(new Object[0]);
                     var11.fromJson(var9.get("progress"));
                     var8.setJsonSerializableValue(var11);
                  } catch (Throwable var12) {
                     LOGGER.warn("Invalid statistic progress in " + this.statsFile, var12);
                  }
               }
            }

            var4.put(var7, var8);
         }

         return var4;
      }
   }

   public static String dumpJson(Map var0) {
      JsonObject var1 = new JsonObject();

      for(Entry var3 : var0.entrySet()) {
         if(((TupleIntJsonSerializable)var3.getValue()).getJsonSerializableValue() != null) {
            JsonObject var4 = new JsonObject();
            var4.addProperty("value", Integer.valueOf(((TupleIntJsonSerializable)var3.getValue()).getIntegerValue()));
            JsonObject var10000 = var4;
            String var10001 = "progress";
            Entry var10002 = var3;

            try {
               var10000.add(var10001, ((TupleIntJsonSerializable)var10002.getValue()).getJsonSerializableValue().getSerializableElement());
            } catch (Throwable var6) {
               LOGGER.warn("Couldn\'t save statistic " + ((StatBase)var3.getKey()).getStatName() + ": error serializing progress", var6);
            }

            var1.add(((StatBase)var3.getKey()).statId, var4);
         } else {
            var1.addProperty(((StatBase)var3.getKey()).statId, Integer.valueOf(((TupleIntJsonSerializable)var3.getValue()).getIntegerValue()));
         }
      }

      return var1.toString();
   }

   public void func_150877_d() {
      this.field_150888_e.addAll(this.statsData.keySet());
   }

   public void func_150876_a(EntityPlayerMP var1) {
      int var2 = this.mcServer.getTickCounter();
      HashMap var3 = Maps.newHashMap();
      if(this.field_150886_g || var2 - this.field_150885_f > 300) {
         this.field_150885_f = var2;

         for(StatBase var5 : this.func_150878_c()) {
            var3.put(var5, Integer.valueOf(this.readStat(var5)));
         }
      }

      var1.playerNetServerHandler.sendPacket(new S37PacketStatistics(var3));
   }

   public void sendAchievements(EntityPlayerMP var1) {
      HashMap var2 = Maps.newHashMap();

      for(Achievement var4 : AchievementList.achievementList) {
         if(this.hasAchievementUnlocked(var4)) {
            var2.put(var4, Integer.valueOf(this.readStat(var4)));
            this.field_150888_e.remove(var4);
         }
      }

      var1.playerNetServerHandler.sendPacket(new S37PacketStatistics(var2));
   }

   public boolean func_150879_e() {
      return this.field_150886_g;
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
