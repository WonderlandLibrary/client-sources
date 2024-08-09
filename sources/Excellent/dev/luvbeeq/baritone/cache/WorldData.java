package dev.luvbeeq.baritone.cache;

import dev.luvbeeq.baritone.Baritone;
import dev.luvbeeq.baritone.api.cache.ICachedWorld;
import dev.luvbeeq.baritone.api.cache.IWaypointCollection;
import dev.luvbeeq.baritone.api.cache.IWorldData;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;

import java.nio.file.Path;

/**
 * Data about a world, from dev.intave.baritone's point of view. Includes cached chunks, waypoints, and map data.
 *
 * @author leijurv
 */
public class WorldData implements IWorldData {

    public final CachedWorld cache;
    private final WaypointCollection waypoints;
    //public final MapData map;
    public final Path directory;
    public final RegistryKey<World> dimension;

    WorldData(Path directory, RegistryKey<World> dimension) {
        this.directory = directory;
        this.cache = new CachedWorld(directory.resolve("cache"), dimension);
        this.waypoints = new WaypointCollection(directory.resolve("waypoints"));
        this.dimension = dimension;
    }

    public void onClose() {
        Baritone.getExecutor().execute(() -> {
            System.out.println("Started saving the world in a new thread");
            cache.save();
        });
    }

    @Override
    public ICachedWorld getCachedWorld() {
        return this.cache;
    }

    @Override
    public IWaypointCollection getWaypoints() {
        return this.waypoints;
    }
}
