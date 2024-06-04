package com.polarware.module.impl.render;

import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.module.impl.render.fullbright.EffectFullBright;
import com.polarware.module.impl.render.fullbright.GammaFullBright;
import com.polarware.value.impl.ModeValue;

/**
 * @author Patrick
 * @since 10/19/2021
 */
@ModuleInfo(name = "module.render.fullbright.name", description = "module.render.fullbright.description", category = Category.RENDER)
public final class FullBrightModule extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new EffectFullBright("Normal", this))
            .setDefault("Normal");
}