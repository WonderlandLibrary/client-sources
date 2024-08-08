package com.example.editme.modules.misc;

import com.example.editme.events.PacketEvent;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.client.EntityUtil;
import com.example.editme.util.setting.SettingsManager;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketUseEntity.Action;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

@Module.Info(
   name = "AutoEZ",
   category = Module.Category.MISC
)
public class AutoEZ extends Module {
   @EventHandler
   public Listener sendListener = new Listener(this::lambda$new$1, new Predicate[0]);
   private ConcurrentHashMap targetedPlayers = null;
   @EventHandler
   public Listener livingDeathEventListener = new Listener(this::lambda$new$2, new Predicate[0]);
   private Setting timeoutTicks = this.register(SettingsManager.i("TimeoutTicks", 20));

   private void lambda$new$2(LivingDeathEvent var1) {
      if (mc.field_71439_g != null) {
         if (this.targetedPlayers == null) {
            this.targetedPlayers = new ConcurrentHashMap();
         }

         EntityLivingBase var2 = var1.getEntityLiving();
         if (var2 != null) {
            if (EntityUtil.isPlayer(var2)) {
               EntityPlayer var3 = (EntityPlayer)var2;
               if (var3.func_110143_aJ() <= 0.0F) {
                  String var4 = var3.func_70005_c_();
                  if (this.wasTarget(var4)) {
                     this.doKillSteak(var4);
                  }

               }
            }
         }
      }
   }

   private void doKillSteak(String var1) {
      this.targetedPlayers.remove(var1);
      mc.field_71439_g.func_71165_d(String.valueOf((new StringBuilder()).append("Haha get ez'd ").append(var1)));
   }

   public void addTargetedPlayer(String var1) {
      if (!Objects.equals(var1, mc.field_71439_g.func_70005_c_())) {
         if (this.targetedPlayers == null) {
            this.targetedPlayers = new ConcurrentHashMap();
         }

         this.targetedPlayers.put(var1, this.timeoutTicks.getValue());
      }
   }

   private void lambda$onUpdate$0(String var1, Integer var2) {
      if (var2 <= 0) {
         this.targetedPlayers.remove(var1);
      } else {
         this.targetedPlayers.put(var1, var2 - 1);
      }

   }

   private boolean wasTarget(String var1) {
      return this.targetedPlayers.containsKey(var1);
   }

   public void onEnable() {
      this.targetedPlayers = new ConcurrentHashMap();
   }

   public void onDisable() {
      this.targetedPlayers = null;
   }

   public void onUpdate() {
      if (!this.isDisabled() && mc.field_71439_g != null) {
         if (this.targetedPlayers == null) {
            this.targetedPlayers = new ConcurrentHashMap();
         }

         Iterator var1 = mc.field_71441_e.func_72910_y().iterator();

         while(var1.hasNext()) {
            Entity var2 = (Entity)var1.next();
            if (EntityUtil.isPlayer(var2)) {
               EntityPlayer var3 = (EntityPlayer)var2;
               if (var3.func_110143_aJ() <= 0.0F) {
                  String var4 = var3.func_70005_c_();
                  if (this.wasTarget(var4)) {
                     this.doKillSteak(var4);
                     break;
                  }
               }
            }
         }

         this.targetedPlayers.forEach(this::lambda$onUpdate$0);
      }
   }

   private void lambda$new$1(PacketEvent.Send var1) {
      if (mc.field_71439_g != null) {
         if (this.targetedPlayers == null) {
            this.targetedPlayers = new ConcurrentHashMap();
         }

         if (var1.getPacket() instanceof CPacketUseEntity) {
            CPacketUseEntity var2 = (CPacketUseEntity)var1.getPacket();
            if (var2.func_149565_c().equals(Action.ATTACK)) {
               Entity var3 = var2.func_149564_a(mc.field_71441_e);
               if (EntityUtil.isPlayer(var3)) {
                  this.addTargetedPlayer(var3.func_70005_c_());
               }
            }
         }
      }
   }
}
