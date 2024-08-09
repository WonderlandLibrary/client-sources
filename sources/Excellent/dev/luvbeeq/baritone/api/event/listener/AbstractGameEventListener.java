package dev.luvbeeq.baritone.api.event.listener;

import dev.luvbeeq.baritone.api.event.events.*;

/**
 * An implementation of {@link IGameEventListener} that has all methods
 * overridden with empty bodies, allowing inheritors of this class to choose
 * which events they would like to listen in on.
 *
 * @author Brady
 * @see IGameEventListener
 * @since 8/1/2018
 */
public interface AbstractGameEventListener extends IGameEventListener {

    @Override
    default void onTick(TickEvent event) {
    }

    @Override
    default void onPlayerUpdate(PlayerUpdateEvent event) {
    }

    @Override
    default void onSendChatMessage(ChatEvent event) {
    }

    @Override
    default void onPreTabComplete(TabCompleteEvent event) {
    }

    @Override
    default void onChunkEvent(ChunkEvent event) {
    }

    @Override
    default void onRenderPass(RenderEvent event) {
    }

    @Override
    default void onWorldEvent(WorldEvent event) {
    }

    @Override
    default void onSendPacket(PacketEvent event) {
    }

    @Override
    default void onReceivePacket(PacketEvent event) {
    }

    @Override
    default void onPlayerRotationMove(RotationMoveEvent event) {
    }

    @Override
    default void onPlayerSprintState(SprintStateEvent event) {
    }

    @Override
    default void onBlockInteract(BlockInteractEvent event) {
    }

    @Override
    default void onPlayerDeath() {
    }

    @Override
    default void onPathEvent(PathEvent event) {
    }
}
