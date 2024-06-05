package io.github.nevalackin.radium.module.impl.movement;

import io.github.nevalackin.radium.event.impl.player.UpdatePositionEvent;
import io.github.nevalackin.radium.event.impl.player.UseItemEvent;
import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import io.github.nevalackin.radium.module.ModuleManager;
import io.github.nevalackin.radium.module.impl.combat.KillAura;
import io.github.nevalackin.radium.utils.MovementUtils;
import io.github.nevalackin.radium.utils.Wrapper;
import io.github.nevalackin.radium.property.Property;
import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

@ModuleInfo(label = "No Slowdown", category = ModuleCategory.MOVEMENT)
public final class NoSlowdown extends Module {

    private static final C07PacketPlayerDigging PLAYER_DIGGING = new C07PacketPlayerDigging(
            C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN);
    private static final C08PacketPlayerBlockPlacement BLOCK_PLACEMENT = new C08PacketPlayerBlockPlacement(
            new BlockPos(-1, -1, -1), 255, null, 0.0f, 0.0f, 0.0f);

    private final Property<Boolean> ncpProperty = new Property<>("NCP", true);

    public static boolean isNoSlowdownEnabled() {
        return ModuleManager.getInstance(NoSlowdown.class).isEnabled();
    }

    @Listener
    private void onUseItemEvent(UseItemEvent e) {
        e.setCancelled();
    }

    @Listener
    private void onUpdatePositionEvent(UpdatePositionEvent e) {
        if (ncpProperty.getValue() && MovementUtils.isMoving() &&
                !KillAura.isBlocking() && Wrapper.getPlayer().isBlocking()) {
            if (e.isPre()) {
                Wrapper.sendPacketDirect(PLAYER_DIGGING);
            } else {
                Wrapper.sendPacketDirect(BLOCK_PLACEMENT);
            }
        }
    }
}
