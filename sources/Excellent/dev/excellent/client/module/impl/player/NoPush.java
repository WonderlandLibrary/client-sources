package dev.excellent.client.module.impl.player;

import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.pattern.Singleton;
import dev.excellent.impl.value.impl.BooleanValue;
import dev.excellent.impl.value.impl.MultiBooleanValue;

@ModuleInfo(name = "No Push", description = "Убирает колиззию.", category = Category.PLAYER)

public class NoPush extends Module {
    public static Singleton<NoPush> singleton = Singleton.create(() -> Module.link(NoPush.class));
    public final MultiBooleanValue elements = new MultiBooleanValue("Не отталкиваться от", this)
            .add(
                    new BooleanValue("Игроков", true),
                    new BooleanValue("Блоков", true),
                    new BooleanValue("Воды", true)
            );

}
