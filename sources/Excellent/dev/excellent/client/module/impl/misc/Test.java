package dev.excellent.client.module.impl.misc;

import dev.excellent.api.event.impl.render.Render3DPosedEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.pattern.Singleton;

@ModuleInfo(name = "Test", description = "Тестовая функция разработчиков.", category = Category.MISC)
public class Test extends Module {
    public static Singleton<Test> singleton = Singleton.create(() -> Module.link(Test.class));

    @Override
    public void toggle() {
        super.toggle();
    }

    private final Listener<Render3DPosedEvent> onRender3D = event -> {

    };


}
