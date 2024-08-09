package dev.excellent.client.module.impl.render;

import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.pattern.Singleton;

@ModuleInfo(name = "Full Bright", description = "Увеличивает яркость.", category = Category.RENDER)
public class Fullbright extends Module {
    public static Singleton<Fullbright> singleton = Singleton.create(() -> Module.link(Fullbright.class));

    /*
     * @see net.minecraft.client.renderer.LightTexture
     */

    /*
      @see net.optifine.shaders.Shaders
     */

}