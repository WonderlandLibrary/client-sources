package dev.luvbeeq.baritone.api;

import dev.excellent.api.interfaces.client.IAccess;
import dev.excellent.client.module.impl.misc.Baritone;
import dev.luvbeeq.baritone.api.event.events.RotationMoveEvent;
import dev.luvbeeq.baritone.api.event.events.SprintStateEvent;
import dev.luvbeeq.baritone.api.utils.BlockOptionalMeta;
import lombok.experimental.UtilityClass;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.loot.LootPredicateManager;
import net.minecraft.loot.LootTableManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.server.ServerWorld;

@UtilityClass
public class BaritoneUtils implements IAccess {

    public boolean isAllowFlying(PlayerAbilities capabilities, ClientPlayerEntity clientPlayer) {
        IBaritone baritone = BaritoneAPI.getProvider().getBaritoneForPlayer(clientPlayer);
        if (baritone == null) {
            return capabilities.allowFlying;
        }
        return !baritone.getPathingBehavior().isPathing() && capabilities.allowFlying;
    }

    public boolean isKeyDown(KeyBinding keyBinding, ClientPlayerEntity clientPlayer) {
        IBaritone baritone = BaritoneAPI.getProvider().getBaritoneForPlayer(clientPlayer);
        if (baritone == null) {
            return keyBinding.isKeyDown();
        }
        if (BaritoneUtils.isActive()) {
            SprintStateEvent event = new SprintStateEvent();
            baritone.getGameEventHandler().onPlayerSprintState(event);
            if (event.getState() != null) {
                return event.getState();
            }
        }
        if (baritone != BaritoneAPI.getProvider().getPrimaryBaritone()) {
            return false;
        }
        return keyBinding.isKeyDown();
    }

    public float overrideYaw(LivingEntity self, RotationMoveEvent jumpRotationEvent) {
        if (self instanceof ClientPlayerEntity clientPlayer && BaritoneAPI.getProvider().getBaritoneForPlayer(clientPlayer) != null && jumpRotationEvent != null) {
            return jumpRotationEvent.getYaw();
        }
        return self.rotationYaw;
    }

    public MinecraftServer getServer(ServerWorld world) {
        if (world == null) {
            return null;
        }
        return world.getServer();
    }

    public LootTableManager getLootTableManager(MinecraftServer server) {
        if (server == null) {
            return BlockOptionalMeta.getManager();
        }
        return server.getLootTableManager();
    }

    public LootPredicateManager getLootPredicateManager(MinecraftServer server) {
        if (server == null) {
            return BlockOptionalMeta.getPredicateManager();
        }
        return server.func_229736_aP_();
    }

    public boolean passEvents(Screen screen, ClientPlayerEntity player) {
        return (BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing() && player != null) || screen.passEvents;
    }

    public boolean isEnabled() {
        return Baritone.singleton.get() != null && Baritone.singleton.get().isEnabled();
    }

    public boolean isActive() {
        IBaritone baritone = BaritoneAPI.getProvider().getPrimaryBaritone();
        return (baritone.getPathingBehavior().isPathing()
                || baritone.getBuilderProcess().isActive()
                || baritone.getCustomGoalProcess().isActive()
                || baritone.getExploreProcess().isActive()
                || baritone.getFarmProcess().isActive()
                || baritone.getMineProcess().isActive()
                || baritone.getGetToBlockProcess().isActive()
                || baritone.getFollowProcess().isActive()
        ) && isEnabled();
    }

}
