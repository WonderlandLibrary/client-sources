package net.shoreline.client.impl.module.movement;

import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.BooleanConfig;
import net.shoreline.client.api.config.setting.EnumConfig;
import net.shoreline.client.api.config.setting.NumberConfig;
import net.shoreline.client.api.event.EventStage;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.impl.event.TickEvent;
import net.shoreline.client.impl.event.network.PlayerUpdateEvent;
import net.shoreline.client.init.Managers;
import net.shoreline.client.util.math.timer.CacheTimer;
import net.shoreline.client.util.math.timer.Timer;
import net.shoreline.client.util.string.EnumFormatter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.entity.passive.MuleEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.shoreline.client.util.Globals;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class StepModule extends ToggleModule
{
    //
    Config<StepMode> modeConfig = new EnumConfig<>("Mode", "Step mode",
            StepMode.NORMAL, StepMode.values());
    Config<Float> heightConfig = new NumberConfig<>("Height", "The maximum " +
            "height for stepping up blocks", 1.0f, 2.5f, 10.0f);
    Config<Boolean> useTimerConfig = new BooleanConfig("UseTimer", "Slows " +
            "down packets by applying timer when stepping", true);
    Config<Boolean> strictConfig = new BooleanConfig("Strict", "Confirms the " +
            "step height for NCP servers", false, () -> heightConfig.getValue() <= 2.5f);
    Config<Boolean> entityStepConfig = new BooleanConfig("EntityStep",
            "Allows entities to step up blocks", false);
    //
    private final Timer stepTimer = new CacheTimer();
    private boolean cancelTimer;

    /**
     *
     */
    public StepModule()
    {
        super("Step", "Allows the player to step up blocks",
                ModuleCategory.MOVEMENT);
    }

    /**
     *
     * @return
     */
    @Override
    public String getModuleData()
    {
        return EnumFormatter.formatEnum(modeConfig.getValue());
    }

    /**
     *
     */
    @Override
    public void onDisable()
    {
        if (Globals.mc.player == null)
        {
            return;
        }
        setStepHeight(isAbstractHorse(Globals.mc.player.getVehicle()) ? 1.0f : 0.6f);
        Managers.TICK.setClientTick(1.0f);
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onPlayerUpdate(PlayerUpdateEvent event)
    {
        if (event.getStage() != EventStage.POST)
        {
            return;
        }
        if (modeConfig.getValue() == StepMode.NORMAL)
        {
            double stepHeight = Globals.mc.player.getY() - Globals.mc.player.prevY;
            if (stepHeight <= 0.5 || stepHeight > heightConfig.getValue())
            {
                return;
            }
            //
            final double[] offs = getStepOffsets(stepHeight);
            if (useTimerConfig.getValue())
            {
                Managers.TICK.setClientTick(stepHeight > 1.0 ? 0.15f : 0.35f);
                cancelTimer = true;
            }
            for (int i = 0; i < (stepHeight > 1.0 ? offs.length : 2); i++)
            {
                Managers.NETWORK.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(Globals.mc.player.prevX,
                        Globals.mc.player.prevY + offs[i], Globals.mc.player.prevZ, false));
            }
            if (strictConfig.getValue())
            {
                Managers.NETWORK.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(Globals.mc.player.prevX,
                        Globals.mc.player.prevY + stepHeight, Globals.mc.player.prevZ, false));
            }
            stepTimer.reset();
        }
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onTick(TickEvent event)
    {
        if (event.getStage() != EventStage.PRE)
        {
            return;
        }
        if (Globals.mc.player.isTouchingWater()
                || Globals.mc.player.isInLava()
                || Globals.mc.player.isFallFlying())
        {
            Managers.TICK.setClientTick(1.0f);
            setStepHeight(isAbstractHorse(Globals.mc.player.getVehicle()) ? 1.0f : 0.6f);
            return;
        }
        if (cancelTimer && Globals.mc.player.isOnGround())
        {
            Managers.TICK.setClientTick(1.0f);
            cancelTimer = false;
        }
        if (Globals.mc.player.isOnGround() && stepTimer.passed(200))
        {
            setStepHeight(heightConfig.getValue());
        }
        else
        {
            setStepHeight(isAbstractHorse(Globals.mc.player.getVehicle()) ? 1.0f : 0.6f);
        }
    }

    /**
     *
     * @param stepHeight
     */
    private void setStepHeight(float stepHeight)
    {
        if (entityStepConfig.getValue() && Globals.mc.player.getVehicle() != null)
        {
            Globals.mc.player.getVehicle().setStepHeight(stepHeight);
        }
        else
        {
            Globals.mc.player.setStepHeight(stepHeight);
        }
    }

    /**
     *
     * @param stepHeight The step height
     * @return
     */
    private double[] getStepOffsets(double stepHeight)
    {
        double[] packets = new double[]
                {
                        0.42, stepHeight <= 1.0 && stepHeight > 0.8 ? 0.753 :
                        0.75, 1.0, 1.16, 1.23, 1.2
                };
        if (stepHeight >= 2.5)
        {
            packets = new double[]
                    {
                            0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652,
                            1.869, 2.019, 1.907
                    };
        }
        else if (stepHeight >= 2.0)
        {
            packets = new double[]
                    {
                            0.42, 0.78, 0.63, 0.51, 0.9, 1.21, 1.45, 1.43
                    };
        }
        return packets;
    }

    /**
     *
     * @param e
     * @return
     */
    private boolean isAbstractHorse(Entity e)
    {
        return e instanceof HorseEntity || e instanceof LlamaEntity
                || e instanceof MuleEntity;
    }

    public enum StepMode
    {
        VANILLA,
        NORMAL,
        A_A_C
    }
}
