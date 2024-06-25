package cc.slack.features.modules.impl.ghost;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.client.mc;
import cc.slack.utils.player.AttackUtil;
import cc.slack.utils.player.RotationUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

@ModuleInfo(
   name = "AimAssist",
   category = Category.GHOST
)
public class AimAssist extends Module {
   private final BooleanValue lowerSens = new BooleanValue("Lower Sensitivity On Target", true);
   private final NumberValue<Float> lowerSensAmount = new NumberValue("Lowered Sensitity Percentage", 0.6F, 0.0F, 1.0F, 0.1F);
   private final BooleanValue accelSens = new BooleanValue("Dynamic Acceleration", true);
   private float prevDist;
   private float currDist;
   private float[] prevRot;
   private boolean wasAccel = false;
   private float sens;
   private float gameSens;

   public AimAssist() {
      this.addSettings(new Value[]{this.lowerSens, this.lowerSensAmount, this.accelSens});
   }

   @Listen
   public void onUpdate(UpdateEvent event) {
      this.gameSens = mc.getGameSettings().mouseSensitivity;
      if ((Boolean)this.lowerSens.getValue() && mc.getMinecraft().objectMouseOver.entityHit != null) {
         this.sens = this.gameSens * (Float)this.lowerSensAmount.getValue();
      }

      if ((Boolean)this.accelSens.getValue() && mc.getMinecraft().objectMouseOver.entityHit == null) {
         EntityLivingBase target = AttackUtil.getTarget(4.6D, "FOV");
         if (target != null) {
            if (this.wasAccel) {
               this.prevDist = this.currDist;
               this.currDist = (float)RotationUtil.getRotationDifference((Entity)target);
               if (RotationUtil.getRotationDifference(this.prevRot) * 0.6D < (double)(this.prevDist - this.currDist)) {
                  this.sens = this.gameSens * 1.2F;
               } else {
                  this.sens = this.gameSens;
               }

               this.prevRot = new float[]{mc.getPlayer().rotationYaw, mc.getPlayer().rotationPitch};
            } else {
               this.prevRot = new float[]{mc.getPlayer().rotationYaw, mc.getPlayer().rotationPitch};
               this.prevDist = (float)RotationUtil.getRotationDifference((Entity)target);
               this.currDist = this.prevDist;
               this.wasAccel = true;
            }
         }
      }

   }

   public float getSens() {
      return this.isToggle() ? this.sens : mc.getGameSettings().mouseSensitivity;
   }
}
