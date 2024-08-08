package lol.point.returnclient.module.impl.visual;

import lol.point.returnclient.events.impl.render.EventRenderHurtCameraEffect;
import lol.point.returnclient.module.Category;
import lol.point.returnclient.module.Module;
import lol.point.returnclient.module.ModuleInfo;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;

@ModuleInfo(
        name = "NoHurtCam",
        description = "doesn't render the camera's hurt effect",
        category = Category.RENDER)
public class NoHurtCam extends Module {

    @Subscribe
    private final Listener<EventRenderHurtCameraEffect> onHurtEffect = new Listener<>(eventRenderHurtCameraEffect -> eventRenderHurtCameraEffect.setCancelled(true));

}
