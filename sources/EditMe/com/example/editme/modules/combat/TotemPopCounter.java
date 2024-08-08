package com.example.editme.modules.combat;

import com.example.editme.EditmeMod;
import com.example.editme.events.PacketEvent;
import com.example.editme.events.TotemPopEvent;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.setting.SettingsManager;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityStatus;

@Module.Info(
   name = "TotemPopCounter",
   description = "Counts totem pops for you and enemies",
   category = Module.Category.COMBAT
)
public class TotemPopCounter extends Module {
   @EventHandler
   public Listener totemPopEvent;
   private HashMap popList = new HashMap();
   @EventHandler
   public Listener totemPopListener;
   private Setting mode;

   private void lambda$new$0(TotemPopEvent var1) {
      if (this.popList == null) {
         this.popList = new HashMap();
      }

      if (this.popList.get(var1.getEntity().func_70005_c_()) == null) {
         this.popList.put(var1.getEntity().func_70005_c_(), 1);
         this.sendNotification(String.valueOf((new StringBuilder()).append(this.colourchoice()).append(var1.getEntity().func_70005_c_()).append(" popped ").append(1).append(" totem!")));
      } else if (this.popList.get(var1.getEntity().func_70005_c_()) != null) {
         int var2 = (Integer)this.popList.get(var1.getEntity().func_70005_c_());
         ++var2;
         this.popList.put(var1.getEntity().func_70005_c_(), var2);
         this.sendNotification(String.valueOf((new StringBuilder()).append(this.colourchoice()).append(var1.getEntity().func_70005_c_()).append(" popped ").append(var2).append(" totems!")));
      }

   }

   private static void lambda$new$1(PacketEvent.Receive var0) {
      if (mc.field_71441_e != null && mc.field_71439_g != null) {
         if (var0.getPacket() instanceof SPacketEntityStatus) {
            SPacketEntityStatus var1 = (SPacketEntityStatus)var0.getPacket();
            if (var1.func_149160_c() == 35) {
               Entity var2 = var1.func_149161_a(mc.field_71441_e);
               EditmeMod.EVENT_BUS.post(new TotemPopEvent(var2));
            }
         }

      }
   }

   public void onUpdate() {
      Iterator var1 = mc.field_71441_e.field_73010_i.iterator();

      while(var1.hasNext()) {
         EntityPlayer var2 = (EntityPlayer)var1.next();
         if (var2.func_110143_aJ() <= 0.0F && this.popList.containsKey(var2.func_70005_c_())) {
            this.sendNotification(String.valueOf((new StringBuilder()).append(this.colourchoice()).append(var2.func_70005_c_()).append(" died after popping ").append(this.popList.get(var2.func_70005_c_())).append(" totems!")));
            this.popList.remove(var2.func_70005_c_(), this.popList.get(var2.func_70005_c_()));
         }
      }

   }

   private String colourchoice() {
      switch((TotemPopCounter.colour)this.mode.getValue()) {
      case BLACK:
         return "&0";
      case RED:
         return "&c";
      case AQUA:
         return "&b";
      case BLUE:
         return "&9";
      case GOLD:
         return "&6";
      case GRAY:
         return "&7";
      case WHITE:
         return "&f";
      case GREEN:
         return "&a";
      case YELLOW:
         return "&e";
      case DARK_RED:
         return "&4";
      case DARK_AQUA:
         return "&3";
      case DARK_BLUE:
         return "&1";
      case DARK_GRAY:
         return "&8";
      case DARK_GREEN:
         return "&2";
      case DARK_PURPLE:
         return "&5";
      case LIGHT_PURPLE:
         return "&d";
      default:
         return "";
      }
   }

   public TotemPopCounter() {
      this.mode = this.register(SettingsManager.e("Colour", TotemPopCounter.colour.RED));
      this.totemPopEvent = new Listener(this::lambda$new$0, new Predicate[0]);
      this.totemPopListener = new Listener(TotemPopCounter::lambda$new$1, new Predicate[0]);
   }

   private static enum colour {
      GRAY,
      DARK_GRAY,
      DARK_AQUA,
      DARK_PURPLE,
      WHITE,
      GOLD;

      private static final TotemPopCounter.colour[] $VALUES = new TotemPopCounter.colour[]{BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY, DARK_GRAY, BLUE, GREEN, AQUA, RED, LIGHT_PURPLE, YELLOW, WHITE};
      AQUA,
      YELLOW,
      GREEN,
      LIGHT_PURPLE,
      DARK_RED,
      RED,
      DARK_GREEN,
      BLACK,
      BLUE,
      DARK_BLUE;
   }
}
