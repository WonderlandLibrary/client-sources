package dev.excellent.client.module.impl.misc;

import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.pattern.Singleton;
import dev.excellent.impl.value.impl.NumberValue;

@ModuleInfo(name = "See Invisibles", category = Category.MISC, description = "Задает невидимым игрокам свою прозрачность.")
public class SeeInvisibles extends Module {
    public static Singleton<SeeInvisibles> singleton = Singleton.create(() -> Module.link(SeeInvisibles.class));
    public final NumberValue alpha = new NumberValue("Прозрачность", this, 0.5F, 0.3F, 1.0F, 0.1F);
}
