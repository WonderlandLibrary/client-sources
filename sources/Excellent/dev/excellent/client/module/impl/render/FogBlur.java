package dev.excellent.client.module.impl.render;

import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.pattern.Singleton;
import dev.excellent.impl.value.impl.BooleanValue;
import dev.excellent.impl.value.impl.NumberValue;

@ModuleInfo(name = "Fog Blur", description = "Размывает ваше поле зрения.", category = Category.RENDER)
public class FogBlur extends Module {

    public static Singleton<FogBlur> singleton = Singleton.create(() -> Module.link(FogBlur.class));
    public final NumberValue distance = new NumberValue("Дистанция", this, 0.05F, 0.01F, 0.5F, 0.01F);
    public final NumberValue saturation = new NumberValue("Насыщенность", this, 0.5F, 0.05F, 0.95F, 0.05F);
    public final BooleanValue clientColor = new BooleanValue("Клиентский цвет", this, true);
}
