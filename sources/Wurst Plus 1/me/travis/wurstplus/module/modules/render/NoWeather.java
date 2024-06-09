package me.travis.wurstplus.module.modules.render;

import me.travis.wurstplus.module.Module;

@Module.Info(name = "NoWeather", description = "Removes Weather", category = Module.Category.RENDER)
public class NoWeather extends Module
{
    @Override
    public void onUpdate() {
        if (this.isDisabled()) { return; }
        if (NoWeather.mc.world.isRaining()) { NoWeather.mc.world.setRainStrength(0.0f); }
        if (NoWeather.mc.world.isThundering()) { NoWeather.mc.world.setThunderStrength(0.0f); }
    }
}