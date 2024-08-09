package dev.excellent.client.module.impl.render;

import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.pattern.Singleton;
import lombok.Getter;

@ModuleInfo(name = "China Hat", description = "Отображает шляпу самурая.", category = Category.RENDER)
public class ChinaHat extends Module {
    @Getter
    private static final Singleton<ChinaHat> singleton = Singleton.create(() -> Module.link(ChinaHat.class));
}
