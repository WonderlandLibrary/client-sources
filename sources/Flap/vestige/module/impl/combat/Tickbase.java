package vestige.module.impl.combat;

import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.PostMotionEvent;
import vestige.event.impl.RenderEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.IntegerSetting;

public class Tickbase extends Module {
   private Killaura killauraModule;
   private int counter = -1;
   public boolean freezing;
   private final IntegerSetting ticks = new IntegerSetting("Ticks", 3, 1, 10, 1);

   public Tickbase() {
      super("Tickbase", Category.COMBAT);
      this.addSettings(new AbstractSetting[]{this.ticks});
   }

   public void onEnable() {
      this.counter = -1;
      this.freezing = false;
   }

   public boolean onDisable() {
      return false;
   }

   public void onClientStarted() {
      this.killauraModule = (Killaura)Flap.instance.getModuleManager().getModule(Killaura.class);
   }

   public int getExtraTicks() {
      if (this.counter-- > 0) {
         return -1;
      } else {
         this.freezing = false;
         return this.killauraModule.isEnabled() && (this.killauraModule.getTarget() == null || this.killauraModule.getDistanceToEntity(this.killauraModule.getTarget()) > this.killauraModule.range.getValue()) && this.killauraModule.findTarget(!this.killauraModule.mode.is("Fast Switch"), this.killauraModule.startingRange.getValue() + 0.75D) != null && mc.thePlayer.hurtTime <= 2 ? (this.counter = this.ticks.getValue()) : 0;
      }
   }

   @Listener
   public void onPostMotion(PostMotionEvent event) {
      if (this.freezing) {
         mc.thePlayer.posX = mc.thePlayer.lastTickPosX;
         mc.thePlayer.posY = mc.thePlayer.lastTickPosY;
         mc.thePlayer.posZ = mc.thePlayer.lastTickPosZ;
      }

   }

   @Listener
   public void onRender(RenderEvent event) {
      if (this.freezing) {
         mc.timer.renderPartialTicks = 0.0F;
      }

   }
}
