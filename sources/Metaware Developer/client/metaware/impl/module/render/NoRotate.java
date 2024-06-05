package client.metaware.impl.module.render;

import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.impl.event.impl.player.UpdatePlayerEvent;
import client.metaware.impl.event.impl.server.ServerPosLookEvent;
import client.metaware.impl.utils.util.player.RotationUtils;

@ModuleInfo(name = "NoRotate", description = "No rotation duh duhduh", renderName = "No Rotate", category = Category.VISUALS)
public class NoRotate extends Module {

    public int ticksSinceFlag;
    public float yaw, pitch;


    @EventHandler
    private final Listener<UpdatePlayerEvent> eventListener = event -> {
        		if(event.isPre()){
			if(ticksSinceFlag <= 4){
				this.ticksSinceFlag++;

				final float[] rotations = RotationUtils.getRotations(new float[]{this.yaw, this.pitch}, 45.0F, new float[]{event.getYaw(), event.getPitch()});

				this.yaw = rotations[0];
				this.pitch = rotations[1];

				event.setYaw(rotations[0]);
				event.setPitch(rotations[1]);
			}
		}
    };


    @EventHandler
    private final Listener<ServerPosLookEvent> eventListener1 = event -> {
        this.yaw = event.getYaw();
        this.pitch = event.getPitch();

        this.ticksSinceFlag = 0;

		event.setYaw(mc.thePlayer.rotationYaw);
		event.setPitch(mc.thePlayer.rotationPitch);
    };


}
