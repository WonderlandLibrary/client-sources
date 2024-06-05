package net.shoreline.client.impl.module.render;

import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.EnumConfig;
import net.shoreline.client.api.config.setting.NumberConfig;
import net.shoreline.client.api.event.EventStage;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.impl.event.TickEvent;
import net.shoreline.client.impl.event.network.PacketEvent;
import net.shoreline.client.util.string.EnumFormatter;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class NoWeatherModule extends ToggleModule
{
    // WHY THE FUCK DOES THIS NEED A HACK
    //
    Config<Weather> weatherConfig = new EnumConfig<>("Weather", "The world " +
            "weather", Weather.CLEAR, Weather.values());
    Config<Integer> dayTimeConfig = new NumberConfig<>("Time", "The world " +
            "time of day", 0, 6000, 24000);
    //
    private Weather weather;

    /**
     *
     */
    public NoWeatherModule()
    {
        super("NoWeather", "Prevents weather rendering", ModuleCategory.RENDER);
    }

    /**
     *
     * @return
     */
    @Override
    public String getModuleData()
    {
        return EnumFormatter.formatEnum(weatherConfig.getValue());
    }

    /**
     *
     */
    @Override
    public void onEnable()
    {
        if (mc.world != null)
        {
            if (mc.world.isThundering())
            {
                weather = Weather.THUNDER;
            }
            else if (mc.world.isRaining())
            {
                weather = Weather.RAIN;
            }
            else
            {
                weather = Weather.CLEAR;
            }
            setWeather(weatherConfig.getValue());
            mc.world.setTimeOfDay(dayTimeConfig.getValue());
        }
    }

    /**
     *
     */
    @Override
    public void onDisable()
    {
        if (mc.world != null && weather != null)
        {
            setWeather(weather);
        }
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onTick(TickEvent event)
    {
        if (event.getStage() == EventStage.POST)
        {
            setWeather(weatherConfig.getValue());
            mc.world.setTimeOfDay(dayTimeConfig.getValue());
        }
    }

    /**
     *
     * @param weather
     */
    private void setWeather(Weather weather)
    {
        switch (weather)
        {
            case CLEAR ->
            {
                mc.world.getLevelProperties().setRaining(false);
                mc.world.setRainGradient(0.0f);
                mc.world.setThunderGradient(0.0f);
            }
            case RAIN ->
            {
                mc.world.getLevelProperties().setRaining(true);
                mc.world.setRainGradient(1.0f);
                mc.world.setThunderGradient(0.0f);
            }
            case THUNDER ->
            {
                mc.world.getLevelProperties().setRaining(true);
                mc.world.setRainGradient(2.0f);
                mc.world.setThunderGradient(1.0f);
            }
        }
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onPacketInbound(PacketEvent.Inbound event)
    {
        if (event.getPacket() instanceof GameStateChangeS2CPacket packet)
        {
            if (packet.getReason() == GameStateChangeS2CPacket.RAIN_STARTED
                    || packet.getReason() == GameStateChangeS2CPacket.RAIN_STOPPED
                    || packet.getReason() == GameStateChangeS2CPacket.RAIN_GRADIENT_CHANGED
                    || packet.getReason() == GameStateChangeS2CPacket.THUNDER_GRADIENT_CHANGED)
            {
                event.cancel();
            }
        }
        else if (event.getPacket() instanceof WorldTimeUpdateS2CPacket)
        {
            event.cancel();
        }
    }

    public enum Weather
    {
        CLEAR,
        RAIN,
        THUNDER,
        ASH
    }
}
