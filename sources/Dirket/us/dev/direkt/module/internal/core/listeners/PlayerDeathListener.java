package us.dev.direkt.module.internal.core.listeners;

import com.google.common.collect.Sets;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.EventGameTick;
import us.dev.direkt.event.internal.events.game.world.EventLoadWorld;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.Module;
import us.dev.direkt.module.internal.render.waypoints.Waypoints;
import us.dev.direkt.module.internal.render.waypoints.handler.WaypointManager;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;
import java.util.Set;

/**
 * @author Foundry
 */
@ModData(label = "Death Listener", category = ModCategory.CORE)
public class PlayerDeathListener extends Module {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private static Set<WaypointManager.WaypointData> worldDeathPoints = Sets.newHashSet();
    private World lastWorld;
    private boolean settingDeathPoint;

    @Listener
    protected Link<EventGameTick> onGameTick = new Link<>(event -> {
        if (!Wrapper.getPlayer().isEntityAlive()) {
            if (!settingDeathPoint) {
                settingDeathPoint = true;

                worldDeathPoints.add(new WaypointManager.WaypointData("Death at " + dateFormat.format(Calendar.getInstance().getTime()),
                        Waypoints.getWorld(),
                        Waypoints.getServer(),
                        new BlockPos(MathHelper.floor_double(Wrapper.getPlayer().posX), MathHelper.floor_double(Wrapper.getPlayer().posY), MathHelper.floor_double(Wrapper.getPlayer().posZ)), Wrapper.getPlayer().dimension, 0x55A70000, WaypointManager.Type.DEATH));

            }
        } else {
            settingDeathPoint = false;
        }
    });

    @Listener
    protected Link<EventLoadWorld> onWorldLoad = new Link<>(event -> {
        if (this.lastWorld == null || this.lastWorld.getSpawnPoint() != event.getWorld().getSpawnPoint()) {
            this.lastWorld = event.getWorld();
            worldDeathPoints.clear();
        }
    });

    public static Optional<Set<WaypointManager.WaypointData>> findWorldDeathPoints() {
        return Optional.of(worldDeathPoints).filter(worldDeathPoints -> !worldDeathPoints.isEmpty());
    }
}
