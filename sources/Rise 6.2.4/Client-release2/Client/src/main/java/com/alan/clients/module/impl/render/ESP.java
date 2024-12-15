package com.alan.clients.module.impl.render;

import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.module.impl.render.esp.ChamsESP;
import com.alan.clients.module.impl.render.esp.GlowESP;
import com.alan.clients.module.impl.render.esp.SkeletalESP;
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.NumberValue;

@ModuleInfo(aliases = {"module.render.esp.name"}, description = "module.render.esp.description", category = Category.RENDER)
public final class ESP extends Module {

    private final BooleanValue glowESP = new BooleanValue("Glow", this, false, new GlowESP("", this));
    private final BooleanValue chamsESP = new BooleanValue("Chams", this, false, new ChamsESP("", this));
    public final BooleanValue staticColorChamsESP = new BooleanValue("Static Color", this, false, () -> !chamsESP.getValue());
    private final BooleanValue skeletalESPValue = new BooleanValue("Skeletal", this, false, new SkeletalESP("",this));
    public final BooleanValue whiteColor = new BooleanValue("White Color", this, false, () -> !skeletalESPValue.getValue());
    public final NumberValue skeletalWidth = new NumberValue("Width", this, 0.5, 0.1, 1.5, 0.1);
}
