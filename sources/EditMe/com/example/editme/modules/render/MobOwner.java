package com.example.editme.modules.render;

import com.example.editme.EditmeMod;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.setting.SettingsManager;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityTameable;
import org.apache.commons.io.IOUtils;

@Module.Info(
   name = "MobOwner",
   description = "Displays the owner of tamed mobs",
   category = Module.Category.RENDER
)
public class MobOwner extends Module {
   private Map cachedUUIDs = new HashMap(this) {
      final MobOwner this$0;

      {
         this.this$0 = var1;
      }
   };
   private int apiRequests = 0;
   private String invalidText = "Offline or invalid UUID!";
   private Setting jump = this.register(SettingsManager.b("Jump", true));
   private static long startTime1 = 0L;
   private static long startTime = 0L;
   private Setting hp = this.register(SettingsManager.b("Health", true));
   private Setting speed = this.register(SettingsManager.b("Speed", true));

   public static String getNameFromUUID(String var0) {
      try {
         EditmeMod.log.info(String.valueOf((new StringBuilder()).append("Attempting to get name from UUID ").append(var0)));
         String var1 = IOUtils.toString(new URL(String.valueOf((new StringBuilder()).append("https://api.mojang.com/user/profiles/").append(var0.replace("-", "")).append("/names"))));
         JsonParser var2 = new JsonParser();
         return var2.parse(var1).getAsJsonArray().get(var2.parse(var1).getAsJsonArray().size() - 1).getAsJsonObject().get("name").toString();
      } catch (IOException var3) {
         EditmeMod.log.error(var3.getStackTrace());
         EditmeMod.log.error("ur internet is cringe ngl");
         return null;
      }
   }

   private void resetCache() {
      if (startTime == 0L) {
         startTime = System.currentTimeMillis();
      }

      if (startTime + 20000L <= System.currentTimeMillis()) {
         startTime = System.currentTimeMillis();
         Iterator var1 = this.cachedUUIDs.entrySet().iterator();

         while(var1.hasNext()) {
            Entry var2 = (Entry)var1.next();
            if (((String)var2.getKey()).equalsIgnoreCase(this.invalidText)) {
               this.cachedUUIDs.clear();
               return;
            }
         }
      }

   }

   public static double round(double var0, int var2) {
      if (var2 < 0) {
         throw new IllegalArgumentException();
      } else {
         BigDecimal var3 = BigDecimal.valueOf(var0);
         var3 = var3.setScale(var2, RoundingMode.HALF_UP);
         return var3.doubleValue();
      }
   }

   private String getUsername(String var1) {
      Iterator var2 = this.cachedUUIDs.entrySet().iterator();

      Entry var3;
      do {
         if (!var2.hasNext()) {
            try {
               try {
                  if (this.apiRequests > 10) {
                     return "Too many API requests";
                  }

                  this.cachedUUIDs.put(var1, ((String)Objects.requireNonNull(getNameFromUUID(var1))).replace("\"", ""));
                  ++this.apiRequests;
               } catch (IllegalStateException var4) {
                  this.cachedUUIDs.put(var1, this.invalidText);
               }
            } catch (NullPointerException var5) {
               this.cachedUUIDs.put(var1, this.invalidText);
            }

            var2 = this.cachedUUIDs.entrySet().iterator();

            do {
               if (!var2.hasNext()) {
                  return this.invalidText;
               }

               var3 = (Entry)var2.next();
            } while(!((String)var3.getKey()).equalsIgnoreCase(var1));

            return (String)var3.getValue();
         }

         var3 = (Entry)var2.next();
      } while(!((String)var3.getKey()).equalsIgnoreCase(var1));

      return (String)var3.getValue();
   }

   private void resetRequests() {
      if (startTime1 == 0L) {
         startTime1 = System.currentTimeMillis();
      }

      if (startTime1 + 10000L <= System.currentTimeMillis()) {
         startTime1 = System.currentTimeMillis();
         if (this.apiRequests >= 2) {
            this.apiRequests = 0;
         }
      }

   }

   public void onDisable() {
      this.cachedUUIDs.clear();
      Iterator var1 = mc.field_71441_e.field_72996_f.iterator();

      while(true) {
         Entity var2;
         do {
            if (!var1.hasNext()) {
               return;
            }

            var2 = (Entity)var1.next();
         } while(!(var2 instanceof EntityTameable) && !(var2 instanceof AbstractHorse));

         try {
            var2.func_174805_g(false);
         } catch (Exception var4) {
         }
      }
   }

   private String getSpeed(AbstractHorse var1) {
      return !(Boolean)this.speed.getValue() ? "" : String.valueOf((new StringBuilder()).append(" SPEED ").append(round(43.17D * (double)var1.func_70689_ay(), 2)));
   }

   private String getJump(AbstractHorse var1) {
      return !(Boolean)this.jump.getValue() ? "" : String.valueOf((new StringBuilder()).append(" JUMP ").append(round(-0.1817584952D * Math.pow(var1.func_110215_cj(), 3.0D) + 3.689713992D * Math.pow(var1.func_110215_cj(), 2.0D) + 2.128599134D * var1.func_110215_cj() - 0.343930367D, 2)));
   }

   private String getHealth(AbstractHorse var1) {
      return !(Boolean)this.hp.getValue() ? "" : String.valueOf((new StringBuilder()).append(" HP ").append(round((double)var1.func_110143_aJ(), 2)));
   }

   private String getHealth(EntityTameable var1) {
      return !(Boolean)this.hp.getValue() ? "" : String.valueOf((new StringBuilder()).append(" HP ").append(round((double)var1.func_110143_aJ(), 2)));
   }

   public void onUpdate() {
      this.resetRequests();
      this.resetCache();
      Iterator var1 = mc.field_71441_e.field_72996_f.iterator();

      while(var1.hasNext()) {
         Entity var2 = (Entity)var1.next();
         if (var2 instanceof EntityTameable) {
            EntityTameable var3 = (EntityTameable)var2;
            if (var3.func_70909_n() && var3.func_70902_q() != null) {
               var3.func_174805_g(true);
               var3.func_96094_a(String.valueOf((new StringBuilder()).append("Tamed by ").append(var3.func_70902_q().func_145748_c_().func_150254_d()).append(this.getHealth(var3))));
            }
         }

         if (var2 instanceof AbstractHorse) {
            AbstractHorse var4 = (AbstractHorse)var2;
            if (var4.func_110248_bS() && var4.func_184780_dh() != null) {
               var4.func_174805_g(true);
               var4.func_96094_a(String.valueOf((new StringBuilder()).append("Tamed by ").append(this.getUsername(var4.func_184780_dh().toString())).append(this.getHealth(var4)).append(this.getSpeed(var4)).append(this.getJump(var4))));
            }
         }
      }

   }
}
