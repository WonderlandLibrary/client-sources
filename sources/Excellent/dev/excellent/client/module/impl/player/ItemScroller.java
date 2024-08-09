package dev.excellent.client.module.impl.player;

import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.pattern.Singleton;
import dev.excellent.impl.value.impl.NumberValue;
import lombok.Getter;

@Getter
@ModuleInfo(name = "Item Scroller", description = "Позволяет вам быстро перекладывать предметы в контейнерах.", category = Category.PLAYER)
public class ItemScroller extends Module {
    public static Singleton<ItemScroller> singleton = Singleton.create(() -> Module.link(ItemScroller.class));

    private final NumberValue delay = new NumberValue("Задержка", this, 50, 0, 1000, 1);
}