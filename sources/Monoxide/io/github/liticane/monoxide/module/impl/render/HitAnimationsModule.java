package io.github.liticane.monoxide.module.impl.render;

import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.value.impl.BooleanValue;
import io.github.liticane.monoxide.value.impl.NumberValue;

//Hooked in ItemRenderer class && EntityLivingBase class
@ModuleData(name = "HitAnimations", description = "Changes your item swinging animation", category = ModuleCategory.RENDER)
public class HitAnimationsModule extends Module {

    public final NumberValue<Float> swingSpeed = new NumberValue<>("Swing Speed", this, 1.2f, 0.1f, 3.5f, 1);
    public final BooleanValue smoothSwing = new BooleanValue("Smooth Swing", this, false);

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {}

}