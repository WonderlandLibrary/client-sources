package dev.excellent.client.module.impl.misc;

import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.pattern.Singleton;
import dev.excellent.impl.value.impl.BooleanValue;
import dev.excellent.impl.value.impl.NumberValue;
import lombok.Getter;

@Getter
@ModuleInfo(name = "Notifications", description = "Отправляет оповещение при вкл/выкл модулей.", category = Category.MISC)
public class Notifications extends Module {
    public static Singleton<Notifications> singleton = Singleton.create(() -> Module.link(Notifications.class));
    private final BooleanValue sound = new BooleanValue("Звук", this, true);
    private final NumberValue volume = new NumberValue("Громкость", this, 0.25F, 0.05F, 1F, 0.05F, () -> !sound.getValue());
}
