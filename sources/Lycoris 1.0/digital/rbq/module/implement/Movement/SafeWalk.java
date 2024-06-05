package digital.rbq.module.implement.Movement;

import com.darkmagician6.eventapi.EventTarget;
import digital.rbq.event.MoveEvent;
import digital.rbq.module.Category;
import digital.rbq.module.Module;

/**
 * Created by John on 2017/04/26.
 */
public class SafeWalk extends Module {
    public SafeWalk(){
        super("SafeWalk", Category.Movement, false);
    }

    @EventTarget
    public void onMove(MoveEvent e) {
        e.setSafeWalk(true);
    }
}
