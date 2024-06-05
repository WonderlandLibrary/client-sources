package net.minecraft.src;

import java.util.concurrent.*;

class CallableLevelWeather implements Callable
{
    final WorldInfo worldInfoInstance;
    
    CallableLevelWeather(final WorldInfo par1WorldInfo) {
        this.worldInfoInstance = par1WorldInfo;
    }
    
    public String callLevelWeatherInfo() {
        return String.format("Rain time: %d (now: %b), thunder time: %d (now: %b)", WorldInfo.getRainTime(this.worldInfoInstance), WorldInfo.getRaining(this.worldInfoInstance), WorldInfo.getThunderTime(this.worldInfoInstance), WorldInfo.getThundering(this.worldInfoInstance));
    }
    
    @Override
    public Object call() {
        return this.callLevelWeatherInfo();
    }
}
