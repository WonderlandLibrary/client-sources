package cc.slack.features.modules.impl.other;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.utils.client.mc;
import io.github.nevalackin.radbus.Listen;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.potion.Potion;

@ModuleInfo(
   name = "RemoveEffect",
   category = Category.OTHER
)
public class RemoveEffect extends Module {
   private final BooleanValue shouldRemoveSlowness = new BooleanValue("Slowness", false);
   private final BooleanValue shouldRemoveMiningFatigue = new BooleanValue("MiningFatigue", false);
   private final BooleanValue shouldRemoveBlindness = new BooleanValue("Blindness", false);
   private final BooleanValue shouldRemoveWeakness = new BooleanValue("Weakness", false);
   private final BooleanValue shouldRemoveWither = new BooleanValue("Wither", false);
   private final BooleanValue shouldRemovePoison = new BooleanValue("Poison", false);
   private final BooleanValue shouldRemoveWaterBreathing = new BooleanValue("WaterBreathing", false);

   public RemoveEffect() {
      this.addSettings(new Value[]{this.shouldRemoveSlowness, this.shouldRemoveMiningFatigue, this.shouldRemoveBlindness, this.shouldRemoveWeakness, this.shouldRemoveWither, this.shouldRemovePoison, this.shouldRemoveWaterBreathing});
   }

   @Listen
   public void onUpdate(UpdateEvent event) {
      if (mc.getPlayer() != null) {
         List<Integer> effectIdsToRemove = new ArrayList();
         if ((Boolean)this.shouldRemoveSlowness.getValue()) {
            mc.getPlayer().removePotionEffectClient(Potion.moveSlowdown.id);
         }

         if ((Boolean)this.shouldRemoveMiningFatigue.getValue()) {
            mc.getPlayer().removePotionEffectClient(Potion.digSlowdown.id);
         }

         if ((Boolean)this.shouldRemoveBlindness.getValue()) {
            mc.getPlayer().removePotionEffectClient(Potion.blindness.id);
         }

         if ((Boolean)this.shouldRemoveWeakness.getValue()) {
            mc.getPlayer().removePotionEffectClient(Potion.weakness.id);
         }

         if ((Boolean)this.shouldRemoveWither.getValue()) {
            effectIdsToRemove.add(Potion.wither.id);
         }

         if ((Boolean)this.shouldRemovePoison.getValue()) {
            effectIdsToRemove.add(Potion.poison.id);
         }

         if ((Boolean)this.shouldRemoveWaterBreathing.getValue()) {
            effectIdsToRemove.add(Potion.waterBreathing.id);
         }

         Iterator var3 = effectIdsToRemove.iterator();

         while(var3.hasNext()) {
            Integer effectId = (Integer)var3.next();
            mc.getPlayer().removePotionEffectClient(effectId);
         }
      }

   }
}
