package dev.luvbeeq.baritone.event;

import dev.luvbeeq.baritone.Baritone;
import dev.luvbeeq.baritone.api.BaritoneUtils;
import dev.luvbeeq.baritone.api.event.events.*;
import dev.luvbeeq.baritone.api.event.events.type.EventState;
import dev.luvbeeq.baritone.api.event.listener.IEventBus;
import dev.luvbeeq.baritone.api.event.listener.IGameEventListener;
import dev.luvbeeq.baritone.api.utils.Helper;
import dev.luvbeeq.baritone.cache.WorldProvider;
import dev.luvbeeq.baritone.utils.BlockStateInterface;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public final class GameEventHandler implements IEventBus, Helper {

    private final Baritone baritone;

    private final List<IGameEventListener> listeners = new CopyOnWriteArrayList<>();

    public GameEventHandler(Baritone baritone) {
        this.baritone = baritone;
    }

    @Override
    public void onTick(TickEvent event) {
        if (!BaritoneUtils.isEnabled()) {
            return;
        }
        if (event.getType() == TickEvent.Type.IN) {
            try {
                baritone.bsi = new BlockStateInterface(baritone.getPlayerContext(), true);
            } catch(Exception ignored) {
                baritone.bsi = null;
            }
        } else {
            baritone.bsi = null;
        }
        listeners.forEach(l -> l.onTick(event));
    }

    @Override
    public void onPlayerUpdate(PlayerUpdateEvent event) {
        if (!BaritoneUtils.isEnabled()) {
            return;
        }
        listeners.forEach(l -> l.onPlayerUpdate(event));
    }

    @Override
    public void onSendChatMessage(ChatEvent event) {
        if (!BaritoneUtils.isEnabled()) {
            return;
        }
        listeners.forEach(l -> l.onSendChatMessage(event));
    }

    @Override
    public void onPreTabComplete(TabCompleteEvent event) {
        if (!BaritoneUtils.isEnabled()) {
            return;
        }
        listeners.forEach(l -> l.onPreTabComplete(event));
    }

    @Override
    public void onChunkEvent(ChunkEvent event) {
        if (!BaritoneUtils.isEnabled()) {
            return;
        }
        EventState state = event.getState();
        ChunkEvent.Type type = event.getType();

        boolean isPostPopulate = state == EventState.POST
                && (type == ChunkEvent.Type.POPULATE_FULL || type == ChunkEvent.Type.POPULATE_PARTIAL);

        World world = baritone.getPlayerContext().world();

        // Whenever the server sends us to another dimension, chunks are unloaded
        // technically after the new world has been loaded, so we perform a check
        // to make sure the chunk being unloaded is already loaded.
        boolean isPreUnload = state == EventState.PRE
                && type == ChunkEvent.Type.UNLOAD
                && world.getChunkProvider().getChunk(event.getX(), event.getZ(), null, false) != null;

        if (isPostPopulate || isPreUnload) {
            baritone.getWorldProvider().ifWorldLoaded(worldData -> {
                Chunk chunk = world.getChunk(event.getX(), event.getZ());
                worldData.getCachedWorld().queueForPacking(chunk);
            });
        }


        listeners.forEach(l -> l.onChunkEvent(event));
    }

    @Override
    public void onRenderPass(RenderEvent event) {
        if (!BaritoneUtils.isEnabled()) {
            return;
        }
        listeners.forEach(l -> l.onRenderPass(event));
    }

    @Override
    public void onWorldEvent(WorldEvent event) {
        if (!BaritoneUtils.isEnabled()) {
            return;
        }
        WorldProvider cache = baritone.getWorldProvider();

        if (event.getState() == EventState.POST) {
            cache.closeWorld();
            if (event.getWorld() != null) {
                cache.initWorld(event.getWorld());
            }
        }

        listeners.forEach(l -> l.onWorldEvent(event));
    }

    @Override
    public void onSendPacket(PacketEvent event) {
        if (!BaritoneUtils.isEnabled()) {
            return;
        }
        listeners.forEach(l -> l.onSendPacket(event));
    }

    @Override
    public void onReceivePacket(PacketEvent event) {
        if (!BaritoneUtils.isEnabled()) {
            return;
        }
        listeners.forEach(l -> l.onReceivePacket(event));
    }

    @Override
    public void onPlayerRotationMove(RotationMoveEvent event) {
        if (!BaritoneUtils.isEnabled()) {
            return;
        }
        listeners.forEach(l -> l.onPlayerRotationMove(event));
    }

    @Override
    public void onPlayerSprintState(SprintStateEvent event) {
        if (!BaritoneUtils.isEnabled()) {
            return;
        }
        listeners.forEach(l -> l.onPlayerSprintState(event));
    }

    @Override
    public void onBlockInteract(BlockInteractEvent event) {
        if (!BaritoneUtils.isEnabled()) {
            return;
        }
        listeners.forEach(l -> l.onBlockInteract(event));
    }

    @Override
    public void onPlayerDeath() {
        if (!BaritoneUtils.isEnabled()) {
            return;
        }
        listeners.forEach(IGameEventListener::onPlayerDeath);
    }

    @Override
    public void onPathEvent(PathEvent event) {
        if (!BaritoneUtils.isEnabled()) {
            return;
        }
        listeners.forEach(l -> l.onPathEvent(event));
    }

    @Override
    public void registerEventListener(IGameEventListener listener) {
        this.listeners.add(listener);
    }
}
