package com.example.editme.modules.render;

import com.example.editme.events.EventRenderMap;
import com.example.editme.events.PacketEvent;
import com.example.editme.events.RenderArmorEvent;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.setting.SettingsManager;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.network.play.server.SPacketSpawnExperienceOrb;
import net.minecraft.network.play.server.SPacketSpawnGlobalEntity;
import net.minecraft.network.play.server.SPacketSpawnMob;
import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraft.network.play.server.SPacketSpawnPainting;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent.OverlayType;

@Module.Info(
   name = "NoRender",
   category = Module.Category.RENDER,
   description = "Ignore entity spawn packets"
)
public class NoRender extends Module {
   private Setting mob = this.register(SettingsManager.b("Mob", false));
   @EventHandler
   public Listener blockOverlayEventListener = new Listener(this::lambda$new$2, new Predicate[0]);
   private Setting gentity = this.register(SettingsManager.b("GEntity", false));
   @EventHandler
   public Listener renderArmorEvent = new Listener(this::lambda$new$1, new Predicate[0]);
   @EventHandler
   public Listener receiveListener = new Listener(this::lambda$new$0, new Predicate[0]);
   private Setting object = this.register(SettingsManager.b("Object", false));
   private Setting armor = this.register(SettingsManager.b("Armor"));
   private Setting maps = this.register(SettingsManager.b("Maps", false));
   @EventHandler
   private Listener OnRenderMap = new Listener(this::lambda$new$3, new Predicate[0]);
   private Setting fire = this.register(SettingsManager.b("Fire"));
   private Setting xp = this.register(SettingsManager.b("XP", false));
   private Setting paint = this.register(SettingsManager.b("Paintings", false));
   private Setting explosion = this.register(SettingsManager.b("Explosions", false));

   private void lambda$new$1(RenderArmorEvent var1) {
      if ((Boolean)this.armor.getValue()) {
         var1.cancel();
      }

   }

   private void lambda$new$0(PacketEvent.Receive var1) {
      Packet var2 = var1.getPacket();
      if (var2 instanceof SPacketSpawnMob && (Boolean)this.mob.getValue() || var2 instanceof SPacketSpawnGlobalEntity && (Boolean)this.gentity.getValue() || var2 instanceof SPacketSpawnObject && (Boolean)this.object.getValue() || var2 instanceof SPacketSpawnExperienceOrb && (Boolean)this.xp.getValue() || var2 instanceof SPacketExplosion && (Boolean)this.explosion.getValue() || var2 instanceof SPacketSpawnPainting && (Boolean)this.paint.getValue()) {
         var1.cancel();
      }

   }

   private void lambda$new$3(EventRenderMap var1) {
      if ((Boolean)this.maps.getValue()) {
         var1.cancel();
      }

   }

   private void lambda$new$2(RenderBlockOverlayEvent var1) {
      if ((Boolean)this.fire.getValue() && var1.getOverlayType() == OverlayType.FIRE) {
         var1.setCanceled(true);
      }

   }
}
