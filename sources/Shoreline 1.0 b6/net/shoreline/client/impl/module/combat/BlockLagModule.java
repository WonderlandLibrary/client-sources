package net.shoreline.client.impl.module.combat;

import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.BooleanConfig;
import net.shoreline.client.api.event.EventStage;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.BlockPlacerModule;
import net.shoreline.client.impl.event.ScreenOpenEvent;
import net.shoreline.client.impl.event.TickEvent;
import net.shoreline.client.impl.event.network.DisconnectEvent;
import net.shoreline.client.init.Managers;

/**
 * @author linus
 * @since 1.0
 */
public class BlockLagModule extends BlockPlacerModule {
    //
    Config<Boolean> selfFillConfig = new BooleanConfig("SelfFill", "Fills in the block beneath you", false);
    Config<Boolean> rotateConfig = new BooleanConfig("Rotate", "Rotates before placing the block", false);
    Config<Boolean> strictConfig = new BooleanConfig("Strict", "NCP-Updated bypass for lagging", false);
    Config<Boolean> attackConfig = new BooleanConfig("Attack", "crystals in the way of block", true);
    Config<Boolean> autoDisableConfig = new BooleanConfig("AutoDisable", "Automatically disables after placing block", false);
    //
    private BlockPos prevPos;

    /**
     *
     */
    public BlockLagModule() {
        super("BlockLag", "Rubberband clips you into a block",
                ModuleCategory.COMBAT);
    }

    @Override
    public void onEnable() {
        if (mc.player == null) {
            return;
        }
        prevPos = mc.player.getBlockPos();
    }

    @EventListener
    public void onDisconnect(DisconnectEvent event) {
        disable();
    }

    @EventListener
    public void onScreenOpen(ScreenOpenEvent event) {
        if (event.getScreen() instanceof DeathScreen) {
            disable();
        }
    }

    @EventListener
    public void onTick(TickEvent event) {
        if (event.getStage() != EventStage.PRE) {
            return;
        }
        if (prevPos != mc.player.getBlockPos() || !mc.player.isOnGround()) {
            disable();
            return;
        }
        final BlockPos pos = BlockPos.ofFloored(mc.player.getX(),
                mc.player.getY(), mc.player.getZ());
        final BlockState state = mc.world.getBlockState(pos);
        if (!isInsideBlock(state)) {
            Managers.NETWORK.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
                    mc.player.getX(), mc.player.getY() + 0.41999998688698,
                    mc.player.getZ(), true));
            Managers.NETWORK.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
                    mc.player.getX(), mc.player.getY() + 0.7531999805211997,
                    mc.player.getZ(), true));
            Managers.NETWORK.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
                    mc.player.getX(), mc.player.getY() + 1.00133597911214,
                    mc.player.getZ(), true));
            Managers.NETWORK.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
                    mc.player.getX(), mc.player.getY() + 1.16610926093821,
                    mc.player.getZ(), true));
            Managers.POSITION.setPosition(mc.player.getX(),
                    mc.player.getY() + 1.16610926093821, mc.player.getZ());
            placeObsidianBlock(pos);
            if (selfFillConfig.getValue()) {
                Managers.POSITION.setPosition(mc.player.getX(),
                        mc.player.getY() - 0.16610926093821,
                        mc.player.getZ());
            } else {
                Managers.POSITION.setPosition(mc.player.getX(),
                        mc.player.getY() - 1.16610926093821, mc.player.getZ());
                final Vec3d dist = getLagOffsetVec();
                Managers.NETWORK.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
                        dist.x, dist.y, dist.z, false));
            }
        }
        if (autoDisableConfig.getValue()) {
            disable();
        }
    }

    /**
     * @param state
     * @return
     */
    private boolean isInsideBlock(BlockState state) {
        return state.blocksMovement() && !mc.player.verticalCollision;
    }

    public Vec3d getLagOffsetVec() {
        // TODO: strict calcs
        return new Vec3d(mc.player.getX(), mc.player.getY() + 3.5, mc.player.getZ());
    }
}
