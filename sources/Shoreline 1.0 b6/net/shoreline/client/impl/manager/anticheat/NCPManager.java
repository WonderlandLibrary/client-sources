package net.shoreline.client.impl.manager.anticheat;

import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.shoreline.client.Shoreline;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.impl.event.network.PacketEvent;
import net.shoreline.client.util.Globals;
import net.shoreline.client.util.math.timer.CacheTimer;
import net.shoreline.client.util.math.timer.Timer;

import java.util.HashSet;
import java.util.Set;

/**
 * @author linus
 * @since 1.0
 */
public class NCPManager implements Timer, Globals {
    private final Timer lastRubberband = new CacheTimer();
    //
    private double x, y, z;
    private boolean lag;
    //
    private boolean strict;

    /**
     *
     */
    public NCPManager() {
        Shoreline.EVENT_HANDLER.subscribe(this);
    }

    @EventListener
    public void onPacketInbound(PacketEvent.Inbound event) {
        if (mc.player != null && mc.world != null) {
            if (event.getPacket() instanceof PlayerPositionLookS2CPacket packet) {
                final Vec3d last = new Vec3d(x, y, z);
                x = packet.getX();
                y = packet.getY();
                z = packet.getZ();
                lag = last.squaredDistanceTo(x, y, z) <= 1.0;
                lastRubberband.reset();
            }
        }
    }

    public Direction getPlaceDirectionNCP(BlockPos blockPos, boolean visible) {
        Vec3d eyePos = new Vec3d(mc.player.getX(), mc.player.getY() + mc.player.getStandingEyeHeight(), mc.player.getZ());
        if (blockPos.getX() == eyePos.getX() && blockPos.getY() == eyePos.getY() && blockPos.getZ() == eyePos.getZ()) {
            return Direction.DOWN;
        } else {
            Set<Direction> ncpDirections = getPlaceDirectionsNCP(eyePos, blockPos.toCenterPos());
            for (Direction dir : ncpDirections) {
                if (visible && !mc.world.isAir(blockPos.offset(dir))) {
                    continue;
                }
                return dir;
            }
        }
        return Direction.UP;
    }

    public Set<Direction> getPlaceDirectionsNCP(Vec3d eyePos, Vec3d blockPos)
    {
        return getPlaceDirectionsNCP(eyePos.x, eyePos.y, eyePos.z, blockPos.x, blockPos.y, blockPos.z);
    }

    /**
     * @param x
     * @param y
     * @param z
     * @param dx
     * @param dy
     * @param dz
     * @return
     */
    public Set<Direction> getPlaceDirectionsNCP(final double x, final double y, final double z,
                                                final double dx, final double dy, final double dz) {
        // directly from NCP src
        final double xdiff = x - dx;
        final double ydiff = y - dy;
        final double zdiff = z - dz;
        final Set<Direction> dirs = new HashSet<>(6);
        if (xdiff == 0) {
            dirs.add(Direction.EAST);
            dirs.add(Direction.WEST);
        } else {
            dirs.add(xdiff > 0 ? Direction.EAST : Direction.WEST);
        }
        if (zdiff == 0) {
            dirs.add(Direction.SOUTH);
            dirs.add(Direction.NORTH);
        } else {
            dirs.add(zdiff > 0 ? Direction.SOUTH : Direction.NORTH);
        }
        if (ydiff == 0) {
            dirs.add(Direction.UP);
            dirs.add(Direction.DOWN);
        } else {
            dirs.add(ydiff > 0 ? Direction.UP : Direction.DOWN);
        }
        return dirs;
    }

    /**
     * @return
     */
    public boolean isStrict() {
        return strict;
    }

    /**
     * @param strict
     */
    public void setStrict(boolean strict) {
        this.strict = strict;
    }

    /**
     * @return
     */
    public boolean isInRubberband() {
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
    public boolean passed(Number time) {
        return lastRubberband.passed(time);
    }

    /**
     * Resets the current elapsed time state of the timer and restarts the
     * timer from 0.
     */
    @Deprecated
    @Override
    public void reset() {
        // DEPRECATED
    }

    /**
     * Returns the elapsed time since the last reset of the timer.
     *
     * @return The elapsed time since the last reset
     */
    @Override
    public long getElapsedTime() {
        return lastRubberband.getElapsedTime();
    }

    /**
     * @param time
     */
    @Deprecated
    @Override
    public void setElapsedTime(Number time) {
        // DEPRECATED
    }
}
