package io.github.liticane.monoxide.module.impl.hud;

import java.util.function.Supplier;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.value.impl.BooleanValue;
import io.github.liticane.monoxide.value.impl.ModeValue;

// Hooked in GuiPlayerTabOverlay class
@ModuleData(name = "CustomTabList", description = "Improves the minecraft default tab list.", category = ModuleCategory.HUD)
public class CustomTabListModule extends Module {

    public final ModeValue background = new ModeValue("Background", this, new String[] {"Normal", "Off"});
    public final BooleanValue customFont = new BooleanValue("Custom Font", this, false);
    public final BooleanValue blur = new BooleanValue("Blur", this, false, new Supplier[]{() -> background.is("Normal")});
    public final BooleanValue bloom = new BooleanValue("Bloom", this, false, new Supplier[]{() -> background.is("Normal")});

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {}

}
