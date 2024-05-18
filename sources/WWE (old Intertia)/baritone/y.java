package baritone;

import java.util.*;
import baritone.api.event.listener.*;
import java.util.concurrent.*;
import baritone.api.event.events.type.*;
import baritone.api.event.events.*;

public final class y implements IEventBus, cr
{
    private final a a;
    private final List<IGameEventListener> a;
    
    public y(final a a) {
        super();
        this.a = new CopyOnWriteArrayList<IGameEventListener>();
        this.a = a;
    }
    
    @Override
    public final void onTick(final TickEvent tickEvent) {
        Label_0044: {
            if (tickEvent.getType() == TickEvent$Type.IN) {
                try {
                    this.a.a = new cn(this.a.getPlayerContext(), true);
                    break Label_0044;
                }
                catch (Exception ex) {}
            }
            this.a.a = null;
        }
        this.a.forEach(y::a);
    }
    
    @Override
    public final void onPlayerUpdate(final PlayerUpdateEvent playerUpdateEvent) {
        this.a.forEach(y::a);
    }
    
    @Override
    public final void onSendChatMessage(final ChatEvent chatEvent) {
        this.a.forEach(y::a);
    }
    
    @Override
    public final void onChunkEvent(final ChunkEvent chunkEvent) {
        final EventState state = chunkEvent.getState();
        final ChunkEvent$Type type = chunkEvent.getType();
        final boolean b = state == EventState.POST && (type == ChunkEvent$Type.POPULATE_FULL || type == ChunkEvent$Type.POPULATE_PARTIAL);
        final amu world = this.a.getPlayerContext().world();
        final boolean b2 = state == EventState.PRE && type == ChunkEvent$Type.UNLOAD && world.B().e(chunkEvent.getX(), chunkEvent.getZ());
        this.a.forEach(y::a);
    }
    
    @Override
    public final void onRenderPass(final RenderEvent renderEvent) {
        this.a.forEach(y::a);
    }
    
    @Override
    public final void onWorldEvent(final WorldEvent worldEvent) {
        final w a = this.a.a;
        if (worldEvent.getState() == EventState.POST) {
            final w w;
            final v a2 = (w = a).a;
            w.a = null;
            if (a2 != null) {
                a2.a();
            }
            if (worldEvent.getWorld() != null) {
                a.a(worldEvent.getWorld().s.q().a());
            }
        }
        this.a.forEach(y::a);
    }
    
    @Override
    public final void onSendPacket(final PacketEvent packetEvent) {
        this.a.forEach(y::b);
    }
    
    @Override
    public final void onReceivePacket(final PacketEvent packetEvent) {
        this.a.forEach(y::a);
    }
    
    @Override
    public final void onPlayerRotationMove(final RotationMoveEvent rotationMoveEvent) {
        this.a.forEach(y::a);
    }
    
    @Override
    public final void onPlayerSprintState(final SprintStateEvent sprintStateEvent) {
        this.a.forEach(y::a);
    }
    
    @Override
    public final void onBlockInteract(final BlockInteractEvent blockInteractEvent) {
        this.a.forEach(y::a);
    }
    
    @Override
    public final void onPlayerDeath() {
        this.a.forEach(IGameEventListener::onPlayerDeath);
    }
    
    @Override
    public final void onPathEvent(final PathEvent pathEvent) {
        this.a.forEach(y::a);
    }
    
    @Override
    public final void registerEventListener(final IGameEventListener gameEventListener) {
        this.a.add(gameEventListener);
    }
    
    private static void a(final PathEvent pathEvent, final IGameEventListener gameEventListener) {
        gameEventListener.onPathEvent(pathEvent);
    }
    
    private static void a(final BlockInteractEvent blockInteractEvent, final IGameEventListener gameEventListener) {
        gameEventListener.onBlockInteract(blockInteractEvent);
    }
    
    private static void a(final SprintStateEvent sprintStateEvent, final IGameEventListener gameEventListener) {
        gameEventListener.onPlayerSprintState(sprintStateEvent);
    }
    
    private static void a(final RotationMoveEvent rotationMoveEvent, final IGameEventListener gameEventListener) {
        gameEventListener.onPlayerRotationMove(rotationMoveEvent);
    }
    
    private static void a(final PacketEvent packetEvent, final IGameEventListener gameEventListener) {
        gameEventListener.onReceivePacket(packetEvent);
    }
    
    private static void b(final PacketEvent packetEvent, final IGameEventListener gameEventListener) {
        gameEventListener.onSendPacket(packetEvent);
    }
    
    private static void a(final WorldEvent worldEvent, final IGameEventListener gameEventListener) {
        gameEventListener.onWorldEvent(worldEvent);
    }
    
    private static void a(final RenderEvent renderEvent, final IGameEventListener gameEventListener) {
        gameEventListener.onRenderPass(renderEvent);
    }
    
    private static void a(final ChunkEvent chunkEvent, final IGameEventListener gameEventListener) {
        gameEventListener.onChunkEvent(chunkEvent);
    }
    
    private static void a(final amu amu, final ChunkEvent chunkEvent, final v v) {
        v.getCachedWorld().queueForPacking(amu.a(chunkEvent.getX(), chunkEvent.getZ()));
    }
    
    private static void a(final ChatEvent chatEvent, final IGameEventListener gameEventListener) {
        gameEventListener.onSendChatMessage(chatEvent);
    }
    
    private static void a(final PlayerUpdateEvent playerUpdateEvent, final IGameEventListener gameEventListener) {
        gameEventListener.onPlayerUpdate(playerUpdateEvent);
    }
    
    private static void a(final TickEvent tickEvent, final IGameEventListener gameEventListener) {
        gameEventListener.onTick(tickEvent);
    }
}
