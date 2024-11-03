package vestige.module.impl.player;

import net.minecraft.item.ItemBlock;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import vestige.event.Listener;
import vestige.event.impl.TickEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.IntegerSetting;

public class FastPlace extends Module {
   private final IntegerSetting delay = new IntegerSetting("Delay", 1, 1, 5, 1);
   private final BooleanSetting onlyblocks = new BooleanSetting("Only Blocks", false);
   private C08PacketPlayerBlockPlacement placement;
   private boolean placedBlock;

   public FastPlace() {
      super("Fastplace", Category.WORLD);
      this.addSettings(new AbstractSetting[]{this.delay, this.onlyblocks});
   }

   public boolean onDisable() {
      mc.rightClickDelayTimer = 6;
      this.placement = null;
      return false;
   }

   @Listener
   public void onTick(TickEvent event) {
      if (mc.thePlayer.ticksExisted % this.delay.getValue() == 0) {
         if (this.onlyblocks.isEnabled() && mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock) {
            mc.rightClickDelayTimer = 0;
         } else if (!this.onlyblocks.isEnabled()) {
            mc.rightClickDelayTimer = 0;
         }
      }

      this.placedBlock = false;
   }
}
