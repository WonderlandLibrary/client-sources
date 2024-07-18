package com.alan.clients.module.impl.render;

import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.module.impl.render.esp.ChamsESP;
import com.alan.clients.module.impl.render.esp.GlowESP;
import com.alan.clients.value.impl.BooleanValue;

@ModuleInfo(aliases = {"module.render.esp.name"}, description = "module.render.esp.description", category = Category.RENDER)
public final class ESP extends Module {

    private final BooleanValue glowESP = new BooleanValue("Glow", this, false, new GlowESP("", this));
    private final BooleanValue chamsESP = new BooleanValue("Chams", this, false, new ChamsESP("", this));
    public final BooleanValue staticColorESP = new BooleanValue("Static Color", this, false, () -> !chamsESP.getValue());

}
