package net.shoreline.client.impl.module.misc;

import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.BooleanConfig;
import net.shoreline.client.api.config.setting.NumberConfig;
import net.shoreline.client.api.event.EventStage;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.impl.event.TickEvent;
import net.shoreline.client.impl.event.render.TickCounterEvent;
import net.shoreline.client.init.Managers;
import net.shoreline.client.init.Modules;

import java.text.DecimalFormat;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class TimerModule extends ToggleModule
{
    //
    Config<Float> ticksConfig = new NumberConfig<>("Ticks", "Tick speed",
            0.1f, 2.0f, 50.0f);
    Config<Boolean> tpsSyncConfig = new BooleanConfig("TPSSync", "Syncs game " +
            "tick speed to server tick speed", false);
    //
    private float prevTimer = -1.0f;
    private float timer = 1.0f;

    /**
     *
     */
    public TimerModule()
    {
        super("Timer", "Changes the client tick speed",
                ModuleCategory.MISCELLANEOUS);
    }

    /**
     *
     *
     * @return
     */
    @Override
    public String getModuleData()
    {
        DecimalFormat decimal = new DecimalFormat("0.0#");
        return decimal.format(timer);
    }

    /**
     *
     */
    @Override
    public void toggle()
    {
        Modules.SPEED.setPrevTimer();
        if (Modules.SPEED.isUsingTimer())
        {
            return;
        }
        super.toggle();
    }

    /**
     *
     *
     * @param event
     */
    @EventListener
    public void onTick(TickEvent event)
    {
        if (event.getStage() == EventStage.PRE)
        {
            if (Modules.SPEED.isUsingTimer())
            {
                return;
            }
            if (tpsSyncConfig.getValue())
            {
                timer = Math.max(Managers.TICK.getTpsCurrent() / 20.0f, 0.1f);
                return;
            }
            timer = ticksConfig.getValue();
        }
    }

    /**
     *
     *
     * @param event
     */
    @EventListener
    public void onTickCounter(TickCounterEvent event)
    {
        if (timer != 1.0f)
        {
            event.cancel();
            event.setTicks(timer);
        }
    }

    /**
     *
     * @return
     */
    public float getTimer()
    {
        return timer;
    }

    /**
     *
     */
    public void resetTimer()
    {
        if (prevTimer > 0.0f)
        {
            this.timer = prevTimer;
            prevTimer = -1.0f;
        }
    }

    /**
     *
     *
     * @param timer
     */
    public void setTimer(float timer)
    {
        prevTimer = this.timer;
        this.timer = timer;
    }
}
