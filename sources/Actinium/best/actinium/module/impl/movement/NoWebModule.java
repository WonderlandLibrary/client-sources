package best.actinium.module.impl.movement;

import best.actinium.event.api.Callback;
import best.actinium.event.impl.move.MotionEvent;
import best.actinium.event.impl.move.WebSlowDownEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.property.impl.BooleanProperty;
import best.actinium.property.impl.NumberProperty;

@ModuleInfo(
        name = "No Web",
        description = "Removes Web SlowDown",
        category = ModuleCategory.MOVEMENT
)
public class NoWebModule extends Module {
    private NumberProperty speed = new NumberProperty("Slow Down",this,0,0.22,10,0.05);
    private NumberProperty y = new NumberProperty("Y",this,0,0,10,1);
    private BooleanProperty motion = new BooleanProperty("Motion",this,false);
    private NumberProperty motionSpeed = new NumberProperty("Motion Speed",this,0,0.25,5,0.05)
            .setHidden(() -> !motion.isEnabled());

    @Callback
    public void onWeb(WebSlowDownEvent event) {
        event.setCancelled(true);
        event.setSlowDown(speed.getValue().floatValue());
        event.setNoMotion(motion.isEnabled());
        event.setMotionSpeed(motion.isEnabled() ? motionSpeed.getValue().floatValue() : 0);
        event.setMotionY(y.getValue().floatValue());
    }

}
