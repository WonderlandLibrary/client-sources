package net.futureclient.client.modules.render;

import net.minecraft.client.Minecraft;
import net.futureclient.client.modules.render.noweather.Listener2;
import net.futureclient.client.modules.render.noweather.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.futureclient.client.Ea;

public class NoWeather extends Ea
{
    public NoWeather() {
        super("NoWeather", new String[] { "NoWeather", "NoWather", "NoRain", "AntiWeather", "AntiRain", "RainRemove", "NoThunder" }, true, 10092543, Category.RENDER);
        this.M(new n[] { new Listener1(this), new Listener2(this) });
    }
    
    public static Minecraft getMinecraft() {
        return NoWeather.D;
    }
    
    public static Minecraft getMinecraft1() {
        return NoWeather.D;
    }
}
