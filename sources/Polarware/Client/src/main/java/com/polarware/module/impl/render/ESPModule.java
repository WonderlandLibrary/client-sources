package com.polarware.module.impl.render;

import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.module.impl.render.esp.ChamsESP;
import com.polarware.module.impl.render.esp.GlowESP;
import com.polarware.value.impl.BooleanValue;

@ModuleInfo(name = "module.render.esp.name", description = "module.render.esp.description", category = Category.RENDER)
public final class ESPModule extends Module {

    private BooleanValue glowESP = new BooleanValue("Glow", this, false, new GlowESP("", this));
    private BooleanValue chamsESP = new BooleanValue("Chams", this, false, new ChamsESP("", this));

}
