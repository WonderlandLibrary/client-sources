package client.metaware.impl.module.player;

import client.metaware.Metaware;
import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.api.properties.property.impl.DoubleProperty;
import client.metaware.impl.event.impl.player.UpdatePlayerEvent;
import client.metaware.impl.module.movmeent.Flight;
import client.metaware.impl.module.movmeent.Speed;
import client.metaware.impl.utils.util.player.MovementUtils;

@ModuleInfo(renderName = "Derp", name = "Derp", category = Category.PLAYER)
public class Derp extends Module {

    private float rotationYaw, rotationPitch;
    public boolean flip;

    public DoubleProperty speed = new DoubleProperty("Speed", 1, 1, 100, 1);

    @EventHandler
    private Listener<UpdatePlayerEvent> updatePlayerEventListener = event -> {
        if(Metaware.INSTANCE.getModuleManager().getModuleByClass(Speed.class).isToggled()) {


//            rotationYaw = (float) RenderUtil.animate(MovementUtils.getMovementDirection(), event.getYaw(), 90f);
//            flip = rotationPitch == 80 ? true : rotationPitch == -80 ? false : flip;
//            if (flip) rotationPitch -= 5;
//            else rotationPitch += 5;
//            if (rotationPitch >= 80) rotationPitch = 80;
//            else if (rotationPitch <= -80) rotationPitch = -80;
            event.setYaw(MovementUtils.getMovementDirection());
        }
    };
}
