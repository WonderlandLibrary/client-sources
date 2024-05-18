package baritone.api.event.listener;

import baritone.api.event.events.*;

public interface AbstractGameEventListener extends IGameEventListener
{
    default void onTick(final TickEvent tickEvent) {
    }
    
    default void onPlayerUpdate(final PlayerUpdateEvent playerUpdateEvent) {
    }
    
    default void onSendChatMessage(final ChatEvent chatEvent) {
    }
    
    default void onChunkEvent(final ChunkEvent chunkEvent) {
    }
    
    default void onRenderPass(final RenderEvent renderEvent) {
    }
    
    default void onWorldEvent(final WorldEvent worldEvent) {
    }
    
    default void onSendPacket(final PacketEvent packetEvent) {
    }
    
    default void onReceivePacket(final PacketEvent packetEvent) {
    }
    
    default void onPlayerRotationMove(final RotationMoveEvent rotationMoveEvent) {
    }
    
    default void onPlayerSprintState(final SprintStateEvent sprintStateEvent) {
    }
    
    default void onBlockInteract(final BlockInteractEvent blockInteractEvent) {
    }
    
    default void onPlayerDeath() {
    }
    
    default void onPathEvent(final PathEvent pathEvent) {
    }
}
