package com.example.editme.modules.render;

import com.example.editme.events.GuiScreenEvent;
import com.example.editme.events.RenderEvent;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.client.LogoutSpot;
import com.example.editme.util.setting.SettingsManager;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreenServerList;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

@Module.Info(
   name = "LogoutSpots",
   category = Module.Category.RENDER
)
public class LogoutSpots extends Module {
   boolean isOnServer;
   static int cachePlayerCount;
   public static List logoutPositions = Lists.newArrayList();
   public static HashMap armorCache = new HashMap();
   static ArrayList playerMap = new ArrayList();
   private Setting renderDistance = this.register(SettingsManager.i("Render Distance", 150));
   private static ArrayList j = new ArrayList();
   @EventHandler
   public Listener displayedListener = new Listener(this::lambda$new$1, new Predicate[0]);

   private void checkPlayers() {
      ArrayList var1 = new ArrayList(mc.func_147114_u().func_175106_d());
      int var2 = var1.size();
      if (var2 != cachePlayerCount) {
         ArrayList var3 = (ArrayList)var1.clone();
         var3.removeAll(playerMap);
         if (var3.size() > 5) {
            cachePlayerCount = playerMap.size();
            this.onJoinServer();
            return;
         }

         ArrayList var4 = (ArrayList)playerMap.clone();
         var4.removeAll(var1);
         Iterator var5 = var4.iterator();

         NetworkPlayerInfo var6;
         while(var5.hasNext()) {
            var6 = (NetworkPlayerInfo)var5.next();
            this.playerLeft(var6);
         }

         var5 = var3.iterator();

         while(var5.hasNext()) {
            var6 = (NetworkPlayerInfo)var5.next();
            this.playerJoined(var6);
         }

         cachePlayerCount = playerMap.size();
         this.onJoinServer();
      }

   }

   public void onEnable() {
      try {
         this.resetArraylist();
      } catch (Exception var2) {
      }

      super.onEnable();
   }

   public static boolean isGhost(EntityPlayer var0) {
      Iterator var1 = logoutPositions.iterator();

      LogoutSpot var2;
      do {
         if (!var1.hasNext()) {
            return false;
         }

         var2 = (LogoutSpot)var1.next();
      } while(var2.entity.func_110124_au() != var0.func_110124_au());

      return true;
   }

   private void resetArraylist() {
      j.clear();
      j.addAll(mc.field_71441_e.func_72910_y());
   }

   private void lambda$new$1(GuiScreenEvent.Displayed var1) {
      if (this.isEnabled() && (var1.getScreen() instanceof GuiDisconnected || var1.getScreen() instanceof GuiScreenServerList)) {
         logoutPositions.clear();
      }

   }

   public void onWorldRender(RenderEvent var1) {
      try {
         Iterator var2 = logoutPositions.iterator();

         while(var2.hasNext()) {
            LogoutSpot var3 = (LogoutSpot)var2.next();
            if (var3.serverIp.equals(((ServerData)Objects.requireNonNull(mc.func_147104_D())).field_78845_b) && var3.getDistanceTo(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v) <= (float)(Integer)this.renderDistance.getValue()) {
               GlStateManager.func_179094_E();
               GlStateManager.func_179145_e();
               GlStateManager.func_179147_l();
               GlStateManager.func_179126_j();
               GlStateManager.func_179131_c(1.0F, 0.0F, 1.0F, 0.5F);
               mc.func_175598_ae().func_188391_a(var3.entity, var3.x - mc.func_175598_ae().field_78725_b, var3.y - mc.func_175598_ae().field_78726_c, var3.z - mc.func_175598_ae().field_78723_d, var3.entity.field_70177_z, mc.func_184121_ak(), false);
               GlStateManager.func_179140_f();
               GlStateManager.func_179084_k();
               GlStateManager.func_179121_F();
            }
         }
      } catch (NullPointerException var4) {
      }

   }

   protected void playerJoined(NetworkPlayerInfo var1) {
      for(int var2 = 0; var2 < logoutPositions.size(); ++var2) {
         if (((LogoutSpot)logoutPositions.get(var2)).name.equals(var1.func_178845_a().getName()) && !((LogoutSpot)logoutPositions.get(var2)).name.equals(mc.field_71439_g.func_70005_c_())) {
            logoutPositions.remove(var2);
            --var2;
         }
      }

   }

   public void onUpdate() {
      if (mc.field_71439_g != null) {
         if (mc.field_71439_g.field_70173_aa % 10 == 0) {
            this.checkPlayers();
            this.resetArraylist();
         } else if (mc.func_71356_B()) {
            this.disable();
         }
      }

      mc.field_71441_e.field_72996_f.forEach(LogoutSpots::lambda$onUpdate$0);
   }

   protected void playerLeft(NetworkPlayerInfo var1) {
      Iterator var2 = j.iterator();

      while(var2.hasNext()) {
         Entity var3 = (Entity)var2.next();
         if (var3.func_70005_c_().equalsIgnoreCase(var1.func_178845_a().getName()) && !var3.func_70005_c_().equals(mc.field_71439_g.func_70005_c_())) {
            this.sendNotification(String.valueOf((new StringBuilder()).append(var1.func_178845_a().getName()).append(" has logged out at, x: ").append(var3.func_180425_c().func_177958_n()).append(" y: ").append(var3.func_180425_c().func_177956_o()).append(" z: ").append(var3.func_180425_c().func_177952_p())));
            logoutPositions.add(new LogoutSpot(var3, var3.field_70165_t, var3.field_70163_u, var3.field_70161_v, var3.func_70005_c_()));
         }
      }

   }

   private void onJoinServer() {
      playerMap = new ArrayList(mc.func_147114_u().func_175106_d());
      cachePlayerCount = playerMap.size();
      this.isOnServer = true;
   }

   private static void lambda$onUpdate$0(Entity var0) {
      try {
         if (var0 instanceof EntityPlayer) {
            if (armorCache.containsKey(var0.func_110124_au()) && !isGhost((EntityPlayer)var0)) {
               Iterator var1 = ((EntityPlayer)var0).field_71071_by.field_70460_b.iterator();

               while(var1.hasNext()) {
                  ItemStack var2 = (ItemStack)var1.next();
                  if (var2 == null) {
                     return;
                  }
               }

               armorCache.replace(var0.func_110124_au(), ((EntityPlayer)var0).field_71071_by.field_70460_b);
            } else {
               armorCache.put(var0.func_110124_au(), ((EntityPlayer)var0).field_71071_by.field_70460_b);
            }
         }
      } catch (Exception var3) {
      }

   }

   public void onDisable() {
      this.resetArraylist();
      super.onDisable();
   }
}
