package net.shoreline.client.impl.manager.network.latency;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.shoreline.client.util.world.FakePlayerEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author linus
 * @since 1.0
 */
public class PlayerLatencyTracker {
    // The tracking entity
    private final PlayerEntity track;
    private final FakePlayerEntity backtrack;
    private final Map<Vec3d, Long> latencyPositions = new HashMap<>();

    /**
     * @param track
     */
    public PlayerLatencyTracker(PlayerEntity track) {
        this.track = track;
        this.backtrack = new FakePlayerEntity(track);
    }

    /**
     * @param track
     * @param spawn
     */
    public PlayerLatencyTracker(PlayerEntity track, Vec3d spawn) {
        this(track);
        onPositionUpdate(spawn);
    }

    /**
     * @param pos
     */
    public void onPositionUpdate(final Vec3d pos) {
        latencyPositions.entrySet().removeIf(p ->
                System.currentTimeMillis() - p.getValue() > 1000);
        latencyPositions.put(pos, System.currentTimeMillis());
    }


    /**
     * @param floor
     * @param range
     * @return
     */
    public FakePlayerEntity getTrackedPlayer(final Vec3d floor,
                                             final long range) {
        final Vec3d pos = getTrackedData(floor, range);
        backtrack.setPosition(pos);
        return backtrack;
    }

    /**
     * @param floor
     * @param range
     * @return
     */
    public Vec3d getTrackedData(final Vec3d floor,
                                final long range) {
        Vec3d trackpos = null;
        if (!latencyPositions.isEmpty()) {
            double min = Double.MAX_VALUE;
            for (Map.Entry<Vec3d, Long> pos : latencyPositions.entrySet()) {
                long elapsed = System.currentTimeMillis() - pos.getValue();
                if (elapsed > range) {
                    continue;
                }
                final Vec3d p = pos.getKey();
                double dist = floor.squaredDistanceTo(p.getX(), p.getY(),
                        p.getZ());
                if (dist < min) {
                    min = dist;
                    trackpos = p;
                }
            }
        }
        return trackpos;
    }
}
