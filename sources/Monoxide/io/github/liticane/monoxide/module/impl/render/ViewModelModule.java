package io.github.liticane.monoxide.module.impl.render;

import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.value.impl.NumberValue;

//Hooked in ItemRenderer class
@ModuleData(name = "ViewModel", description = "Changes your hand item size & position", category = ModuleCategory.RENDER)
public class ViewModelModule extends Module {

    public final NumberValue<Float> xPos = new NumberValue<Float>("X Position", this, 0.56f, 0.10f, 1f, 2);
    public final NumberValue<Float> yPos = new NumberValue<Float>("Y Position", this, 0.52f, 0.10f, 1f, 2);
    public final NumberValue<Float> scale = new NumberValue<Float>("Item Scale", this, 0.4f, 0.01f, 1f, 2);

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {}

}