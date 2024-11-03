package xyz.cucumber.base.module.feat.visuals;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventRender3D;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;

@ModuleInfo(
   category = Category.VISUALS,
   description = "",
   name = "Trajectories"
)
public class TrajectoriesModule extends Mod {
   @EventListener
   public void onRender3D(EventRender3D e) {
      try {
         Item item = this.mc.thePlayer.inventory.getCurrentItem().getItem();
         if (item instanceof ItemBow) {
            double power = (double)this.mc.thePlayer.getItemInUseDuration();
            System.out.println(power);
         }

         float yaw = this.mc.thePlayer.rotationYaw;
         float pitch = this.mc.thePlayer.rotationPitch;
         double motionX = -Math.sin((double)(yaw / 180.0F) * Math.PI) * Math.cos((double)(pitch / 180.0F) * Math.PI) * 0.4F;
         double motionY = -Math.sin((double)(pitch / 180.0F) * Math.PI) * 0.4;
         double var9 = Math.cos((double)(yaw / 180.0F) * Math.PI) * Math.cos((double)(pitch / 180.0F) * Math.PI) * 0.4F;
      } catch (Exception var11) {
      }
   }
}
