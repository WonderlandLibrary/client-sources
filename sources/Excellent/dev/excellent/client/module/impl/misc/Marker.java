package dev.excellent.client.module.impl.misc;

import dev.excellent.api.event.impl.render.Render3DLastEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.pattern.Singleton;
import dev.luvbeeq.shader.DepthRenderer;

@ModuleInfo(name = "DepthTest", description = " ", category = Category.MISC)
public class Marker extends Module {
    public static Singleton<Marker> singleton = Singleton.create(() -> Module.link(Marker.class));
    private final Listener<Render3DLastEvent> onRender3D = event -> DepthRenderer.INSTANCE.doRender();
}