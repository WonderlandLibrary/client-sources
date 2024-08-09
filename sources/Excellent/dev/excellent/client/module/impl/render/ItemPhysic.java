package dev.excellent.client.module.impl.render;

import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.pattern.Singleton;

@ModuleInfo(name = "Item Physic", description = "Добавляет физику выброшенным предметам", category = Category.RENDER)
public class ItemPhysic extends Module {
    public static Singleton<ItemPhysic> singleton = Singleton.create(() -> Module.link(ItemPhysic.class));
}
