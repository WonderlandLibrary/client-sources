package dev.excellent.client.module.impl.player;

import dev.excellent.api.event.CancellableEvent;
import dev.excellent.api.event.impl.other.CameraClipEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.pattern.Singleton;
import dev.excellent.impl.value.impl.NumberValue;

@ModuleInfo(name = "Camera Clip", description = "Позволяет смотреть сквозь блоки от третьего лица", category = Category.PLAYER)
public class CameraClip extends Module {
    public static Singleton<CameraClip> singleton = Singleton.create(() -> Module.link(CameraClip.class));
    public final NumberValue cameraDistance = new NumberValue("Дистанция", this, 4, 1, 50, 1);
    private final Listener<CameraClipEvent> onCameraClip = CancellableEvent::cancel;
}
