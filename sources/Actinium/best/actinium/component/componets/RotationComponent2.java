package best.actinium.component.componets;

import best.actinium.component.Component;
import best.actinium.event.api.Callback;
import best.actinium.event.impl.move.MotionEvent;
import best.actinium.util.IAccess;
import best.actinium.util.player.RotationsUtils;
import lombok.Getter;
import lombok.Setter;

/*i have never made a component before so idk*/
@Getter
@Setter
public class RotationComponent2 extends Component {
    private static float yaw, pitch;
    private static boolean shouldRotate;

    public static void disableComponent() {
        shouldRotate = false;
    }

    public static void setRotation(float yaw1,float pitch1) {
        yaw = yaw1;
        pitch = pitch1;
        shouldRotate = true;
    }

    @Callback
    public void onMotion(MotionEvent event) {
        if(!shouldRotate) {
            return;
        }

        event.setYaw(yaw);
        event.setPitch(pitch);
    }
}
