package cc.slack.features.modules.impl.ghost;

import cc.slack.events.impl.render.RenderEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.other.MathUtil;
import cc.slack.utils.player.AttackUtil;
import cc.slack.utils.player.RotationUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;

@ModuleInfo(
   name = "AimBot",
   category = Category.GHOST
)
public class AimBot extends Module {
   private final BooleanValue silent = new BooleanValue("Silent Aimbot", false);
   private final BooleanValue silentMoveFix = new BooleanValue("Silent Aimbot Move Fix", true);
   private final NumberValue<Integer> fov = new NumberValue("Max FOV", 80, 0, 180, 5);
   private final NumberValue<Integer> minFov = new NumberValue("Min FOV", 20, 0, 180, 2);
   private final NumberValue<Float> aimRange = new NumberValue("Aim Range", 4.5F, 0.0F, 8.0F, 0.1F);
   private final NumberValue<Integer> aimSpeed = new NumberValue("Aim Speed", 10, 0, 180, 1);
   public boolean isSilent;
   private EntityLivingBase target = null;

   public AimBot() {
      this.addSettings(new Value[]{this.silent, this.silentMoveFix, this.fov, this.minFov, this.aimRange, this.aimSpeed});
   }

   @Listen
   public void onRender(RenderEvent event) {
      if (event.getState() == RenderEvent.State.RENDER_3D) {
         this.target = AttackUtil.getTarget((double)(Float)this.aimRange.getValue(), "fov");
         if (this.target == null) {
            if (this.isSilent) {
               this.isSilent = false;
               RotationUtil.disable();
            }

         } else {
            float[] targetRotation = RotationUtil.getTargetRotations(this.target.getEntityBoundingBox(), RotationUtil.TargetRotation.CENTER, 0.0D);
            if (!(RotationUtil.getRotationDifference(targetRotation) > (double)(Integer)this.fov.getValue())) {
               if (!(RotationUtil.getRotationDifference(targetRotation) < (double)(Integer)this.minFov.getValue())) {
                  float[] clientRotation = RotationUtil.getLimitedRotation(RotationUtil.clientRotation, targetRotation, ((float)(Integer)this.aimSpeed.getValue() + (float)MathUtil.getRandomInRange(0.0D, (double)(Integer)this.aimSpeed.getValue() / 5.0D)) * 20.0F / (float)Minecraft.getDebugFPS());
                  if ((Boolean)this.silent.getValue()) {
                     RotationUtil.setClientRotation(clientRotation, 1);
                     RotationUtil.setStrafeFix((Boolean)this.silentMoveFix.getValue(), false);
                  } else {
                     RotationUtil.setPlayerRotation(clientRotation);
                  }

               }
            }
         }
      }
   }
}
