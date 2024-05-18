package us.dev.direkt.module.internal.render.waypoints.handler;

import com.google.common.collect.Sets;
import net.minecraft.util.math.BlockPos;
import us.dev.direkt.Direkt;
import us.dev.direkt.file.internal.files.WaypointsFile;

import java.util.Set;

public class WaypointManager {

    private final Set<WaypointData> waypointRegistry = Sets.newHashSet();

    public Set<WaypointData> getWaypoints() {
        return waypointRegistry;
    }

    public boolean isWaypoint(String name, String world, String server) {
        return waypointRegistry.contains(new WaypointData(name, world, server, null, 0, 0, null));
    }

	public void addWaypoint(String name, String world, String server, BlockPos location, int dimension, int color, Type type) {
        waypointRegistry.add(new WaypointData(name, world, server, location, dimension, color, type));
        Direkt.getInstance().getFileManager().getFile(WaypointsFile.class).save();
	}

	public void addWaypointNoSave(String name, String world, String server, BlockPos location, int dimension, int color, Type type) {
        waypointRegistry.add(new WaypointData(name, world, server, location, dimension, color, type));
	}

	public boolean removeWaypoint(String name, String world, String server) {
        boolean flag = waypointRegistry.remove(new WaypointData(name, world, server, null, 0, 0, null));
        Direkt.getInstance().getFileManager().getFile(WaypointsFile.class).save();
        return flag;
	}

    public static class WaypointData {
        private final String name, server;
        private String world;
        private final int dimension, color;
        private final BlockPos location;
        private final Type type;

        public WaypointData(String name, String world, String server, BlockPos location, int dimension, int color, Type type) {
            this.name = name;
            this.world = world;
            this.server = server;
            this.location = location;
            this.dimension = dimension;
            this.color = color;
            this.type = type;
        }

        public String getName() {
            return this.name;
        }

        public void setWorld(String world) {
            this.world = world;
        }
        
        public String getWorld() {
            return this.world;
        }

        public String getServer() {
            return this.server;
        }

        public BlockPos getLocation() {
            return this.location;
        }

        public int getDimension() {
            return this.dimension;
        }

        public int getColor() {
            return this.color;
        }

        public Type getType() {
            return this.type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof WaypointData)) return false;

            WaypointData data = (WaypointData) o;

            return name.equalsIgnoreCase(data.name) && world.equals(data.world) && server.equals(data.server);
        }

        @Override
        public int hashCode() {
            int result = name.toLowerCase().hashCode();
            result = 31 * result + world.hashCode();
            result = 31 * result + server.hashCode();
            return result;
        }
    }

    public enum Type {
        REGULAR, DEATH
    }
}
