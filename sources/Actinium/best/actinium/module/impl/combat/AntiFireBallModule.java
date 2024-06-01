package best.actinium.module.impl.combat;

import best.actinium.component.componets.RotationComponent;
import best.actinium.event.EventType;
import best.actinium.event.api.Callback;
import best.actinium.event.impl.move.MotionEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.property.impl.BooleanProperty;
import best.actinium.property.impl.NumberProperty;
import best.actinium.util.io.TimerUtil;
import best.actinium.util.player.RotationsUtils;
import net.minecraft.entity.projectile.EntityFireball;
import org.lwjglx.util.vector.Vector2f;

import java.util.Comparator;
/**
 * @author Nyghtfull
 * @since i forgor
 */
@ModuleInfo(
        name = "Anti Fire Ball",
        description = "Prevents the fireballs from hiting yo ass",
        category = ModuleCategory.COMBAT
)
public class AntiFireBallModule extends Module {
    private NumberProperty hitRange = new NumberProperty("Hit Range",this,0,3,10,0.1);
    private BooleanProperty rotations = new BooleanProperty("Rotations",this,true);
    private NumberProperty preAimRange = new NumberProperty("Pre Aim Range",this,0,6,20,0.1)
            .setHidden(() -> !rotations.isEnabled());
    private NumberProperty swingRange = new NumberProperty("Swing Range",this,0,3,10,0.1);
    private EntityFireball target;
    private TimerUtil timerUtil = new TimerUtil();

    //also make it on legit clickevent or something
    @Callback
    public void onMotion(MotionEvent event) {
        this.setSuffix(hitRange.getValue().toString());
        this.target = mc.theWorld.getLoadedEntityList().stream()
                .filter(entity -> entity instanceof EntityFireball)
                .map(entity -> (EntityFireball) entity)
                .min(Comparator.comparingDouble(entity -> mc.thePlayer.getDistanceToEntity(entity)))
                .orElse(null);

        if (this.target == null || event.getType() == EventType.POST) {
            return;
        }

        float[] rotations;
        rotations = RotationsUtils.getRotations(target);

        //maybe remove swing range kindo useless and just add it to attack range
        //attack range also through block option or something also maybe remove swing range
        if(timerUtil.hasTimeElapsed(300)) {
            if (mc.thePlayer.getDistanceToEntity(target) < hitRange.getValue()) {
                mc.thePlayer.swingItem();
                mc.playerController.attackEntity(mc.thePlayer, target);
                timerUtil.reset();
            }

            if (mc.thePlayer.getDistanceToEntity(target) < swingRange.getValue()) {
                mc.thePlayer.swingItem();
            }

            if (mc.thePlayer.getDistanceToEntity(target) < preAimRange.getValue()) {
                RotationComponent.setRotations(new Vector2f(rotations[0], rotations[1]));
            }
        }
    }
}
