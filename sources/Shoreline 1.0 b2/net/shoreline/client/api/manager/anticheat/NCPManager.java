package net.shoreline.client.api.manager.anticheat;

import net.shoreline.client.Shoreline;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.impl.event.network.PacketEvent;
import net.shoreline.client.util.Globals;
import net.shoreline.client.util.math.timer.CacheTimer;
import net.shoreline.client.util.math.timer.Timer;
import net.minecraft.block.BlockState;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.HashSet;
import java.util.Set;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class NCPManager implements Timer, Globals
{
    //
    private double x, y, z;
    private boolean lag;
    private final Timer lastRubberband = new CacheTimer();
    //
    private boolean strict;

    /**
     *
     *
     */
    public NCPManager()
    {
        Shoreline.EVENT_HANDLER.subscribe(this);
    }

    /**
     *
     *
     * @param event
     */
    @EventListener
    public void onPacketInbound(PacketEvent.Inbound event)
    {
        if (mc.player != null && mc.world != null)
        {
            if (event.getPacket() instanceof PlayerPositionLookS2CPacket packet)
            {
                final Vec3d last = new Vec3d(x, y, z);
                x = packet.getX();
                y = packet.getY();
                z = packet.getZ();
                lag = last.squaredDistanceTo(x, y, z) <= 1.0;
                lastRubberband.reset();
            }
        }
    }

    /**
     *
     *
     * @param x
     * @param y
     * @param z
     * @param dx
     * @param dy
     * @param dz
     * @return
     */
    public Set<Direction> getPlaceDirectionsNCP(final int x,
                                                final int y,
                                                final int z,
                                                final int dx,
                                                final int dy,
                                                final int dz)
    {
        // directly from NCP src
        final BlockPos pos = new BlockPos(dx, dy, dz);
        final Vec3d center = pos.toCenterPos();
        final BlockState state = mc.world.getBlockState(pos);
        final double xdiff = x - center.getX();
        final double ydiff = y - center.getY();
        final double zdiff = z - center.getZ();
        final Set<Direction> dirs = new HashSet<>(6);
        if (xdiff < -0.5)
        {
            dirs.add(Direction.WEST);
        }
        else if (xdiff > 0.5)
        {
            dirs.add(Direction.EAST);
        }
        else if (state.isFullCube(mc.world, pos))
        {
            dirs.add(Direction.WEST);
            dirs.add(Direction.EAST);
        }
        if (ydiff < -0.5)
        {
            dirs.add(Direction.DOWN);
        }
        else if (ydiff > 0.5)
        {
            dirs.add(Direction.UP);
        }
        else
        {
            dirs.add(Direction.DOWN);
            dirs.add(Direction.UP);
        }
        if (zdiff < -0.5)
        {
            dirs.add(Direction.NORTH);
        }
        else if (zdiff > 0.5)
        {
            dirs.add(Direction.SOUTH);
        }
        else if (state.isFullCube(mc.world, pos))
        {
            dirs.add(Direction.NORTH);
            dirs.add(Direction.SOUTH);
        }

        return dirs;
    }

    /**
     *
     * @return
     */
    public boolean isStrict()
    {
        return strict;
    }

    /**
     *
     *
     * @param strict
     */
    public void setStrict(boolean strict)
    {
        this.strict = strict;
    }

    /**
     *
     *
     * @return
     */
    public boolean isInRubberband()
    {
        return lag;
    }

    /**
     * Returns <tt>true</tt> if the time since the last reset has exceeded
     * the param time.
     *
     * @param time The param time
     * @return <tt>true</tt> if the time since the last reset has exceeded
     * the param time
     */
    @Override
    public boolean passed(Number time)
    {
        return lastRubberband.passed(time);
    }

    /**
     * Resets the current elapsed time state of the timer and restarts the
     * timer from 0.
     */
    @Deprecated
    @Override
    public void reset()
    {
        // DEPRECATED
    }

    /**
     * Returns the elapsed time since the last reset of the timer.
     *
     * @return The elapsed time since the last reset
     */
    @Override
    public long getElapsedTime()
    {
        return lastRubberband.getElapsedTime();
    }

    /**
     *
     *
     * @param time
     */
    @Deprecated
    @Override
    public void setElapsedTime(Number time)
    {
        // DEPRECATED
    }
}
