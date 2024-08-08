package com.example.editme.modules.misc;

import com.example.editme.commands.Command;
import com.example.editme.events.GuiScreenEvent;
import com.example.editme.events.PacketEvent;
import com.example.editme.modules.Module;
import com.example.editme.util.client.EntityUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketUseEntity.Action;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

@Module.Info(
   name = "KillStreak",
   category = Module.Category.MISC,
   description = "Keeps track of ur killz"
)
public class KillStreak extends Module {
   @EventHandler
   public Listener livingDeathEventListener = new Listener(this::lambda$new$3, new Predicate[0]);
   @EventHandler
   public Listener sendListener = new Listener(KillStreak::lambda$new$2, new Predicate[0]);
   public static ConcurrentHashMap targetedPlayers = null;
   @EventHandler
   public Listener listener = new Listener(this::lambda$new$1, new Predicate[0]);
   private int streak = 0;

   private void lambda$new$1(GuiScreenEvent.Displayed var1) {
      if (var1.getScreen() instanceof GuiGameOver) {
         this.streak = 0;
      }

   }

   private static void lambda$new$2(PacketEvent.Send var0) {
      if (mc.field_71439_g != null) {
         if (targetedPlayers == null) {
            targetedPlayers = new ConcurrentHashMap();
         }

         if (var0.getPacket() instanceof CPacketUseEntity) {
            CPacketUseEntity var1 = (CPacketUseEntity)var0.getPacket();
            if (var1.func_149565_c().equals(Action.ATTACK)) {
               Entity var2 = var1.func_149564_a(mc.field_71441_e);
               if (EntityUtil.isPlayer(var2)) {
                  addTargetedPlayer(var2.func_70005_c_());
               }
            }
         }
      }
   }

   private static void lambda$onUpdate$0(String var0, Integer var1) {
      if (var1 <= 0) {
         targetedPlayers.remove(var0);
      } else {
         targetedPlayers.put(var0, var1 - 1);
      }

   }

   public void onEnable() {
      targetedPlayers = new ConcurrentHashMap();
   }

   private boolean wasTarget(String var1) {
      return targetedPlayers.containsKey(var1);
   }

   private void doKillSteak(String var1) {
      targetedPlayers.remove(var1);
      ++this.streak;
      if (this.streak > 1) {
         Command.sendRawChatMessage(String.valueOf((new StringBuilder()).append(ChatFormatting.RED.toString()).append("You're on a ").append(this.streak).append(" person kill streak")));
      }

   }

   public void onDisable() {
      targetedPlayers = null;
   }

   public static void addTargetedPlayer(String var0) {
      if (!Objects.equals(var0, mc.field_71439_g.func_70005_c_())) {
         if (targetedPlayers == null) {
            targetedPlayers = new ConcurrentHashMap();
         }

         targetedPlayers.put(var0, 20);
      }
   }

   private void lambda$new$3(LivingDeathEvent var1) {
      if (mc.field_71439_g != null) {
         if (targetedPlayers == null) {
            targetedPlayers = new ConcurrentHashMap();
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

   public void onUpdate() {
      if (!this.isDisabled() && mc.field_71439_g != null) {
         if (targetedPlayers == null) {
            targetedPlayers = new ConcurrentHashMap();
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

         targetedPlayers.forEach(KillStreak::lambda$onUpdate$0);
      }
   }
}
