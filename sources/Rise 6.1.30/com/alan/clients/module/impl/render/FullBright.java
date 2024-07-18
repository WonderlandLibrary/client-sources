package com.alan.clients.module.impl.render;

import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.module.impl.render.fullbright.EffectFullBright;
import com.alan.clients.module.impl.render.fullbright.GammaFullBright;
import com.alan.clients.value.impl.ModeValue;

@ModuleInfo(aliases = {"module.render.fullbright.name"}, description = "module.render.fullbright.description", category = Category.RENDER)
public final class FullBright extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new GammaFullBright("Gamma", this))
            .add(new EffectFullBright("Effect", this))
            .setDefault("Gamma");
}