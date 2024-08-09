package dev.excellent.client.module.impl.misc;

import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.pattern.Singleton;
import dev.excellent.impl.value.impl.StringValue;

@ModuleInfo(name = "Name Protect", description = "Скрывает ваш никнейм.", category = Category.MISC)
public class NameProtect extends Module {
    public static Singleton<NameProtect> singleton = Singleton.create(() -> Module.link(NameProtect.class));
    public final StringValue name = new StringValue("Введите фейк-ник", this, "protected");

}
