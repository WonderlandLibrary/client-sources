package baritone.api.event.listener;

import baritone.api.event.events.*;

public interface IGameEventListener
{
    void onTick(final TickEvent p0);
    
    void onPlayerUpdate(final PlayerUpdateEvent p0);
    
    void onSendChatMessage(final ChatEvent p0);
    
    void onChunkEvent(final ChunkEvent p0);
    
    void onRenderPass(final RenderEvent p0);
    
    void onWorldEvent(final WorldEvent p0);
    
    void onSendPacket(final PacketEvent p0);
    
    void onReceivePacket(final PacketEvent p0);
    
    void onPlayerRotationMove(final RotationMoveEvent p0);
    
    void onPlayerSprintState(final SprintStateEvent p0);
    
    void onBlockInteract(final BlockInteractEvent p0);
    
    void onPlayerDeath();
    
    void onPathEvent(final PathEvent p0);
}
