package vestige.module.impl.visual;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import vestige.event.Listener;
import vestige.event.impl.TickEvent;
import vestige.event.impl.UpdateEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.ModeSetting;

public class Capes extends Module {
   private final ModeSetting cape = new ModeSetting("Cape List", "Flap2", new String[]{"Flap1", "Flap2", "TerrorJob", "MushDisabler", "LadraoDisabler", "MCC 15Y", "Mojang", "Minecon 2013", "Minecon 2015", "Minecon 2016"});

   public Capes() {
      super("Cape", Category.VISUAL);
      this.setEnabledSilently(true);
      this.addSettings(new AbstractSetting[]{this.cape});
   }

   @Listener
   public void onTick(TickEvent event) {
      EntityPlayer player = mc.thePlayer;
      if (player instanceof AbstractClientPlayer) {
         AbstractClientPlayer clientPlayer = (AbstractClientPlayer)player;
         String capeTexture = "flap/capes/" + this.cape.getMode() + ".png";
         clientPlayer.setLocationOfCape(new ResourceLocation(capeTexture));
      }

   }

   @Listener
   public void onUpdate(UpdateEvent event) {
      EntityPlayer player = mc.thePlayer;
      if (player instanceof AbstractClientPlayer) {
         AbstractClientPlayer clientPlayer = (AbstractClientPlayer)player;
         String capeTexture = "flap/capes/" + this.cape.getMode() + ".png";
         clientPlayer.setLocationOfCape(new ResourceLocation(capeTexture));
      }

   }

   public void onEnable() {
      EntityPlayer player = mc.thePlayer;
      if (player instanceof AbstractClientPlayer) {
         AbstractClientPlayer clientPlayer = (AbstractClientPlayer)player;
         String capeTexture = "flap/capes/" + this.cape.getMode() + ".png";
         clientPlayer.setLocationOfCape(new ResourceLocation(capeTexture));
      }

   }

   public boolean onDisable() {
      EntityPlayer player = mc.thePlayer;
      if (player instanceof AbstractClientPlayer) {
         AbstractClientPlayer clientPlayer = (AbstractClientPlayer)player;
         String capeTexture = "";
         clientPlayer.setLocationOfCape(new ResourceLocation(capeTexture));
      }

      return false;
   }
}
