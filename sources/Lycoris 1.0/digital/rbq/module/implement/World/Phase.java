package digital.rbq.module.implement.World;

import com.darkmagician6.eventapi.EventTarget;
import digital.rbq.event.TickEvent;
import digital.rbq.module.Category;
import digital.rbq.module.Module;
import digital.rbq.module.implement.World.phase.*;

/**
 * Created by John on 2017/04/27.
 */
//Todo: Skid Phases!
public class Phase extends Module {
    public Phase() {
        super("Phase", Category.World, true, new Skip(), new Old(), new Full(), new Clip()/*, new Hypixel()*/);
    }
}
