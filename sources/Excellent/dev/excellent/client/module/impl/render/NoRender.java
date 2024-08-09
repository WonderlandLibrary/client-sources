package dev.excellent.client.module.impl.render;

import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.pattern.Singleton;
import dev.excellent.impl.value.impl.BooleanValue;
import dev.excellent.impl.value.impl.MultiBooleanValue;
import lombok.Getter;

@Getter
@ModuleInfo(name = "No Render", description = "Убирает лишние элементы на вашем экране.", category = Category.RENDER)
public class NoRender extends Module {
    public static Singleton<NoRender> singleton = Singleton.create(() -> Module.link(NoRender.class));

    private final MultiBooleanValue elements = new MultiBooleanValue("Элементы", this)
            .add(
                    new BooleanValue("Огонь", true),
                    new BooleanValue("Тряска камеры", true),
                    new BooleanValue("Боссбар", true),
                    new BooleanValue("Скорборд", true),
                    new BooleanValue("Плохие эффекты", true),
                    new BooleanValue("Потеря тотема", true)
            );
}
