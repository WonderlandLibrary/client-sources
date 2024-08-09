package dev.darkmoon.client.module.impl.render;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.event.render.EventOverlay;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;

@ModuleAnnotation(name = "CameraClip", category = Category.RENDER)
public class CameraClip extends Module {
    @EventTarget
    public void onOverlay(EventOverlay event) {
        if (event.getOverlayType().equals(EventOverlay.OverlayType.CAMERA_CLIP)) {
            event.setCancelled(true);
        }
    }
}
