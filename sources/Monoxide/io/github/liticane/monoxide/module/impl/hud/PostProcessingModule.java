package io.github.liticane.monoxide.module.impl.hud;

import java.util.function.Supplier;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.module.ModuleManager;
import io.github.liticane.monoxide.value.impl.BooleanValue;
import io.github.liticane.monoxide.value.impl.NumberValue;

@ModuleData(name = "PostProcessing", description = "Cool little shaders", category = ModuleCategory.HUD)
public class PostProcessingModule extends Module {

    public BooleanValue bloom = new BooleanValue("Bloom", this, true);
    public NumberValue<Integer> radius = new NumberValue<>("Radius", this, 10, 0, 20, 0, new Supplier[]{() -> bloom.getValue()});
    public BooleanValue blur = new BooleanValue("Blur", this, true);

    public PostProcessingModule() {
        this.setEnabled(true);
    }

    public static PostProcessingModule getInstance() {
        return ModuleManager.getInstance().getModule(PostProcessingModule.class);
    }

    @Override
    public void onEnable() {
        mc.gameSettings.ofFastRender = false;
    }

    @Override
    public void onDisable() {

    }

}
