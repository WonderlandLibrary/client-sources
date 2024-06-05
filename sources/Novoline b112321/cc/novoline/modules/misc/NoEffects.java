package cc.novoline.modules.misc;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.PacketDirection;
import cc.novoline.events.events.PacketEvent;
import cc.novoline.events.events.PlayerUpdateEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.ListProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.misc.WindowedFullscreen;
import cc.novoline.modules.visual.Brightness;
import net.minecraft.network.play.server.S1DPacketEntityEffect;

public final class NoEffects extends AbstractModule {
   @Property("effects")
   private final ListProperty effects = PropertyFactory.createList("Night Vision", "Blindness", "Nausea").acceptableValues((Object[])(new String[]{"Night Vision", "Blindness", "Nausea"}));

   public NoEffects(ModuleManager var1) {
      super(var1, "NoEffects", "No Effects", EnumModuleType.MISC, "remove bad potion effects");
      Manager.put(new Setting("NF_EFFECTS", "Effects", SettingType.SELECTBOX, this, this.effects));
   }

   @EventTarget
   public void onPlayerUpdate(PlayerUpdateEvent var1) {
      String[] var2 = WindowedFullscreen.a();
      if(this.effects.contains("Nausea") && this.mc.player.isPotionActive(9)) {
         this.mc.player.removePotionEffectClient(9);
      }

      if((!this.isEnabled(Brightness.class) || !((Brightness)this.getModule(Brightness.class)).getMode().equals("Effect")) && this.effects.contains("Night Vision") && this.mc.player.isPotionActive(16)) {
         this.mc.player.removePotionEffectClient(16);
      }

      if(this.effects.contains("Blindness") && this.mc.player.isPotionActive(15)) {
         this.mc.player.removePotionEffectClient(15);
      }

   }

   @EventTarget
   public void onPacket(PacketEvent var1) {
      String[] var2 = WindowedFullscreen.a();
      if(var1.getDirection().equals(PacketDirection.INCOMING) && var1.getPacket() instanceof S1DPacketEntityEffect) {
         S1DPacketEntityEffect var3 = (S1DPacketEntityEffect)var1.getPacket();
         if(this.effects.contains("Nausea") && var3.getEffect().getPotionID() == 9 || this.effects.contains("Night Vision") && var3.getEffect().getPotionID() == 16 || this.effects.contains("Blindness") && var3.getEffect().getPotionID() == 15) {
            var3.getEffect().setShowParticles(false);
            var1.setCancelled(true);
         }
      }

   }
}
