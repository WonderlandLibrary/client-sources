package client.metaware.impl.module.render;

import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.impl.event.impl.player.UpdatePlayerEvent;
import net.minecraft.util.ResourceLocation;

@ModuleInfo(name = "Cape", renderName = "Cape", category = Category.VISUALS)
public class Cape extends Module {

    private ResourceLocation oldCape;

    @Override
    public void onEnable() {
        super.onEnable();
        if(mc.thePlayer == null || mc.theWorld == null) return;
        oldCape = mc.thePlayer.getLocationOfCape();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if(mc.thePlayer == null || mc.theWorld == null) return;
        mc.thePlayer.setLocationOfCape(oldCape);
    }

    @EventHandler
    private final Listener<UpdatePlayerEvent> eventListener = event -> {
        if(mc.thePlayer == null || mc.theWorld == null) return;
      mc.thePlayer.setLocationOfCape(new ResourceLocation("whiz/WhizCapeNewV3.png"));
    };
}