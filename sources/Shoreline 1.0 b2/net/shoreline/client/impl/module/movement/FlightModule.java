package net.shoreline.client.impl.module.movement;

import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.BooleanConfig;
import net.shoreline.client.api.config.setting.EnumConfig;
import net.shoreline.client.api.config.setting.NumberConfig;
import net.shoreline.client.api.event.EventStage;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.impl.event.config.ConfigUpdateEvent;
import net.shoreline.client.impl.event.entity.player.PlayerMoveEvent;
import net.shoreline.client.util.math.timer.CacheTimer;
import net.shoreline.client.util.math.timer.Timer;
import net.shoreline.client.util.string.EnumFormatter;
import net.shoreline.client.util.Globals;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class FlightModule extends ToggleModule
{
    //
    Config<FlightMode> modeConfig = new EnumConfig<>("Mode", "The mode for " +
            "vanilla flight", FlightMode.CREATIVE, FlightMode.values());
    Config<Float> speedConfig = new NumberConfig<>("Speed", "The horizontal " +
            "flight speed", 0.1f, 2.5f, 10.0f);
    Config<Float> vspeedConfig = new NumberConfig<>("VerticalSpeed", "The " +
            "vertical flight speed", 0.1f, 1.0f, 5.0f);
    Config<Boolean> airStrictConfig = new BooleanConfig("AirStrict",
            "Accounts for air friction when flying", false);
    Config<Boolean> antiKickConfig = new BooleanConfig("AntiKick", "Prevents " +
            "vanilla flight detection", true);
    //
    private final Timer antiKickTimer = new CacheTimer();

    /**
     *
     */
    public FlightModule()
    {
        super("Flight", "Allows the player to fly in survival",
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
    public void onEnable()
    {
        antiKickTimer.reset();
        if (modeConfig.getValue() == FlightMode.VANILLA)
        {
            enableVanillaFly();
        }
    }

    /**
     *
     */
    @Override
    public void onDisable()
    {
        if (modeConfig.getValue() == FlightMode.VANILLA)
        {
            disableVanillaFly();
        }
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onPlayerMove(PlayerMoveEvent event)
    {
        event.cancel();
        // Vanilla fly kick checks every 80 ticks
        if (antiKickTimer.passed(3800) && antiKickConfig.getValue())
        {
            event.setY(-0.04);
            antiKickTimer.reset();
        }
        if (modeConfig.getValue() == FlightMode.CREATIVE)
        {
            event.setY(0.0);
            if (mc.options.jumpKey.isPressed())
            {
                event.setY(vspeedConfig.getValue());
            }
            else if (mc.options.sneakKey.isPressed())
            {
                event.setY(-vspeedConfig.getValue());
            }
            float speed = getFlySpeed();
            //
            float forward = mc.player.input.movementForward;
            float strafe = mc.player.input.movementSideways;
            float yaw = mc.player.getYaw();
            if (forward == 0.0f && strafe == 0.0f)
            {
                event.setX(0.0);
                event.setZ(0.0);
                return;
            }
            double rx = Math.cos(Math.toRadians(yaw + 90.0f));
            double rz = Math.sin(Math.toRadians(yaw + 90.0f));
            event.setX((forward * speed * rx) + (strafe * speed * rz));
            event.setZ((forward * speed * rz) - (strafe * speed * rx));
        }
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onConfigUpdate(ConfigUpdateEvent event)
    {
        if (event.getConfig() == modeConfig && event.getStage() == EventStage.POST)
        {
            if (modeConfig.getValue() == FlightMode.VANILLA)
            {
                enableVanillaFly();
            }
            else
            {
                disableVanillaFly();
            }
        }
    }

    private void enableVanillaFly()
    {
        mc.player.getAbilities().allowFlying = true;
        mc.player.getAbilities().flying = true;
        mc.player.getAbilities().setFlySpeed(speedConfig.getValue() * 0.05f);
    }

    private void disableVanillaFly()
    {
        if (!mc.player.isCreative())
        {
            mc.player.getAbilities().allowFlying = false;
        }
        mc.player.getAbilities().flying = false;
        mc.player.getAbilities().setFlySpeed(0.05f);
    }

    /**
     *
     * @return
     */
    private float getFlySpeed()
    {
        float speed = speedConfig.getValue();
        // Wierd NCP shit idk, but it works
        if (airStrictConfig.getValue())
        {
            speed = speed - speed / 159.0f;
        }
        return Math.max(speed, 0.2873f);
    }

    public enum FlightMode
    {
        CREATIVE,
        VANILLA
    }
}
