package wwe.modules.world;

import wwe.*;
import wwe.modules.*;
import wwe.utils.*;

public class AntiWeather extends qMIe
{
    public AntiWeather() {
        super("Anti Weather", "Disables rain", 0, Category.WORLD);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        Wrapper.mc.world.setRainStrength(0.0f);
        Wrapper.mc.world.setThunderStrength(0.0f);
    }
}
