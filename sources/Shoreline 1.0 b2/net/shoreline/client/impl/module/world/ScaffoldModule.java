package net.shoreline.client.impl.module.world;

import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.BooleanConfig;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.RotationModule;
import net.shoreline.client.impl.event.network.PlayerUpdateEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class ScaffoldModule extends RotationModule
{
    //
    Config<Boolean> rotateConfig = new BooleanConfig("Rotate", "Rotates " +
            "before placing blocks", false);
    Config<Boolean> towerConfig = new BooleanConfig("Tower", "Automatically" +
            " towers when jumping", true);
    Config<Boolean> stopSprintConfig = new BooleanConfig("StopSprint", "Stops" +
            " sprinting when placing blocks", false);
    Config<Boolean> keepYConfig = new BooleanConfig("KeepY", "Maintains " +
            "the y-level of the player", false);
    //
    private double y;

    /**
     *
     */
    public ScaffoldModule()
    {
        super("Scaffold", "Automatically places blocks below the player",
                ModuleCategory.WORLD);
    }

    /**
     *
     */
    @Override
    public void onDisable()
    {
        if (mc.player == null)
        {
            return;
        }

    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onPlayerUpdate(PlayerUpdateEvent event)
    {
        if (mc.player == null)
        {
            return;
        }
        if (keepYConfig.getValue() && (y == -1.0
                || mc.player.fallDistance > 2.0f))
        {
            y = mc.player.getY() - 1.0;
        }

    }

    /**
     *
     * @return
     */
    private BlockPos getFloorPlacement()
    {
        double y2 = keepYConfig.getValue() ? y : mc.player.getY() - 1.0;
        BlockPos pos = BlockPos.ofFloored(mc.player.getX(), y2,
                mc.player.getZ());
        for (Direction dir : Direction.values())
        {
            BlockPos n = pos.offset(dir);
            if (!mc.world.isAir(n))
            {
                return n;
            }
        }
        for (Direction dir : Direction.values())
        {
            BlockPos n = pos.offset(dir);
            if (mc.world.isAir(n))
            {
                for (Direction dir2 : Direction.values())
                {
                    BlockPos n2 = n.offset(dir2);
                    if (!mc.world.isAir(n2))
                    {
                        return n2;
                    }
                }
            }
        }
        return null;
    }
}
