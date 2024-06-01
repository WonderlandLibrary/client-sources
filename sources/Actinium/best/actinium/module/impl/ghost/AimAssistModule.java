package best.actinium.module.impl.ghost;

import best.actinium.event.api.Callback;
import best.actinium.event.impl.move.MotionEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.property.impl.BooleanProperty;
import best.actinium.property.impl.NumberProperty;
import best.actinium.util.io.TimerUtil;
import best.actinium.util.player.PlayerUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

import java.util.Comparator;
import java.util.Random;
@ModuleInfo(
        name = "Aim Assist",
        description = "Helps u Aim(i think).",
        category = ModuleCategory.GHOST
)
public class AimAssistModule extends Module {
    public NumberProperty horizontalSpeed = new NumberProperty("Speed Horizontal",this,0.0f, 0.4f, 10.0f, 0.1f);
    public NumberProperty verticalSpeed = new NumberProperty("Speed Vertical",this, 0.0f, 0.4f, 10.0f, 0.1f);
    public NumberProperty range = new NumberProperty("Range", this,0.2f, 3.0f, 6.0f, 0.1f);
    public NumberProperty fov = new NumberProperty( "Fov",this, 60.0f, 1.0f, 360.0f, 10f);
    public BooleanProperty holdingSword = new BooleanProperty("Is Holding Sword",this, true);
    private EntityLivingBase target;
    private TimerUtil timer = new TimerUtil();

    @Callback
    public void onUpdate(MotionEvent event) {
        this.target = mc.theWorld.getLoadedEntityList().stream()
                .filter(entity -> entity instanceof EntityPlayer && entity != mc.thePlayer).map(entity -> (EntityPlayer) entity)
                .min(Comparator.comparingDouble(entity -> mc.thePlayer.getDistanceToEntity(entity))).orElse(null);

        if (holdingSword.isEnabled() && !PlayerUtil.isHoldingSword())
            return;

        if (this.target == null || !timer.finished(1) || !mc.gameSettings.keyBindAttack.isKeyDown() || this.target.getDistanceToEntity(this.mc.thePlayer) > range.getValue())
            return;

        if (this.mc.objectMouseOver.entityHit != null)
            return;


        this.mc.thePlayer.rotationYaw = this.faceTarget(target, horizontalSpeed.getValue().floatValue(), verticalSpeed.getValue().floatValue(), false)[0];
        timer.reset();
    }

    private float[] faceTarget(final Entity target, final float horizontal, final float vertical, final boolean miss) {
        final double var4 = target.posX - this.mc.thePlayer.posX;
        final double var5 = target.posZ - this.mc.thePlayer.posZ;
        double var7;

        if (target instanceof EntityLivingBase) {
            final EntityLivingBase var6 = (EntityLivingBase) target;
            var7 = var6.posY + var6.getEyeHeight() - (this.mc.thePlayer.posY + this.mc.thePlayer.getEyeHeight());
        } else {
            var7 = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2.0 - (this.mc.thePlayer.posY + this.mc.thePlayer.getEyeHeight());
        }

        final Random rnd = new Random();
        final float offset = miss ? (rnd.nextInt(15) * 0.25f + 5.0f) : 0.0f;
        final double var8 = MathHelper.sqrt_double(var4 * var4 + var5 * var5);
        final float var9 = (float) (Math.atan2(var5 + offset, var4) * 180.0 / 3.141592653589793) - 90.0f;
        final float var10 = (float) (-(Math.atan2(var7 - ((target instanceof EntityPlayer) ? 0.5f : 0.0f) + offset, var8) * 180.0 / 3.141592653589793));
        final float pitch = changeRotation(this.mc.thePlayer.rotationPitch, var10, vertical);
        final float yaw = changeRotation(this.mc.thePlayer.rotationYaw, var9, horizontal);
        return new float[]{yaw, pitch};
    }

    private float changeRotation(final float p_70663_1_, final float p_70663_2_, final float p_70663_3_) {
        float var4 = MathHelper.wrapAngleTo180_float(p_70663_2_ - p_70663_1_);
        if (var4 > p_70663_3_) {
            var4 = p_70663_3_;
        }

        if (var4 < -p_70663_3_) {
            var4 = -p_70663_3_;
        }

        return p_70663_1_ + var4;
    }
}
