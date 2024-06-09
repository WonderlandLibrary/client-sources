package exhibition.module.impl.player;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventTick;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class SpeedMine extends Module {
   public SpeedMine(ModuleData data) {
      super(data);
   }

   @RegisterEvent(
      events = {EventTick.class}
   )
   public void onEvent(Event event) {
      if (mc.thePlayer.getHeldItem() != null) {
         if (!(mc.thePlayer.getHeldItem().getItem() instanceof ItemPickaxe)) {
            return;
         }

         mc.playerController.blockHitDelay = 0;
         mc.thePlayer.addPotionEffect(new PotionEffect(Potion.digSpeed.getId(), 100, 1));
      } else {
         mc.playerController.blockHitDelay = 0;
         mc.thePlayer.addPotionEffect(new PotionEffect(Potion.digSpeed.getId(), 100, 1));
      }

   }

   public void onDisable() {
      super.onDisable();
      mc.thePlayer.removePotionEffect(Potion.digSpeed.getId());
   }
}
