package vestige.module.impl.combat;

import net.minecraft.entity.EntityLivingBase;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.JumpEvent;
import vestige.event.impl.StrafeEvent;
import vestige.event.impl.UpdateEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.module.impl.world.ScaffoldV2;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.DoubleSetting;
import vestige.util.player.MovementUtil;
import vestige.util.player.RotationsUtil;
import vestige.util.world.BlockUtils;

public class TargetStrafeV2 extends Module {
   private final DoubleSetting range = new DoubleSetting("Range", 1.0D, 0.2D, 6.0D, 0.1D);
   private final BooleanSetting speed = new BooleanSetting("Allow speed", true);
   private final BooleanSetting fly = new BooleanSetting("Allow fly", false);
   private final BooleanSetting manual = new BooleanSetting("Allow manual", false);
   private final BooleanSetting strafe = new BooleanSetting("Strafe around", true);
   public static float yaw;
   public static EntityLivingBase target = null;
   private boolean left;
   private boolean colliding;
   private static boolean active = false;

   public TargetStrafeV2() {
      super("Target Strafe", Category.COMBAT);
      this.addSettings(new AbstractSetting[]{this.range, this.speed, this.fly, this.manual, this.strafe});
   }

   public static float getMovementYaw() {
      return active && target != null ? yaw : mc.thePlayer.rotationYaw;
   }

   @Listener
   public void onJump(JumpEvent event) {
      if (active) {
         target = ((Killaura)Flap.instance.getModuleManager().getModule(Killaura.class)).target;
         if (target != null) {
            float[] rots = RotationsUtil.getRotationsToEntity(target, false);
            yaw = rots[0];
            double distance = ((Killaura)Flap.instance.getModuleManager().getModule(Killaura.class)).getDistanceToEntity(target);
            double offset = 90.0D - distance * 20.0D;
            event.setYaw((float)((double)yaw + offset));
            event.setCancelled(true);
         }
      }

   }

   @Listener
   public void onStrafe(StrafeEvent event) {
      if (active) {
         target = ((Killaura)Flap.instance.getModuleManager().getModule(Killaura.class)).target;
         if (target != null) {
            float[] rots = RotationsUtil.getRotationsToEntity(target, false);
            yaw = rots[0];
            double distance = ((Killaura)Flap.instance.getModuleManager().getModule(Killaura.class)).getDistanceToEntity(target);
            double offset = 90.0D - distance * 20.0D;
            event.setYaw((float)((double)yaw + offset));
         }
      }

   }

   @Listener
   public void onPreUpdate(UpdateEvent event) {
      if (Flap.instance.getModuleManager().getModule(ScaffoldV2.class) != null && !((ScaffoldV2)Flap.instance.getModuleManager().getModule(ScaffoldV2.class)).isEnabled() && Flap.instance.getModuleManager().getModule(Killaura.class) != null && ((Killaura)Flap.instance.getModuleManager().getModule(Killaura.class)).isEnabled()) {
         active = true;
         if (!mc.thePlayer.isCollidedHorizontally && BlockUtils.isBlockUnder(5)) {
            this.colliding = false;
         } else {
            if (!this.colliding) {
               if (this.strafe.isEnabled()) {
                  MovementUtil.strafes();
               }

               this.left = !this.left;
            }

            this.colliding = true;
         }

      } else {
         active = false;
      }
   }

   public boolean onDisable() {
      active = false;
      return false;
   }
}
