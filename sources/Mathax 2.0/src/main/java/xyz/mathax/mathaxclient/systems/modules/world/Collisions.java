package xyz.mathax.mathaxclient.systems.modules.world;

import net.minecraft.block.*;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.VehicleMoveC2SPacket;
import net.minecraft.util.shape.VoxelShapes;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.entity.player.PlayerMoveEvent;
import xyz.mathax.mathaxclient.events.packets.PacketEvent;
import xyz.mathax.mathaxclient.events.world.CollisionShapeEvent;
import xyz.mathax.mathaxclient.mixininterface.IVec3d;
import xyz.mathax.mathaxclient.settings.BlockListSetting;
import xyz.mathax.mathaxclient.settings.BoolSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;

import java.util.List;

public class Collisions extends Module {
    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    public final Setting<List<Block>> blocksSetting = generalSettings.add(new BlockListSetting.Builder()
            .name("Blocks")
            .description("What blocks should be added collision box.")
            .filter(this::blockFilter)
            .build()
    );

    private final Setting<Boolean> magmaSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Magma")
            .description("Prevent you from walking over magma blocks.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> unloadedChunksSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Unloaded chunks")
            .description("Stop you from going into unloaded chunks.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> ignoreBorderSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Ignore border")
            .description("Remove world border collision.")
            .defaultValue(false)
            .build()
    );

    public Collisions(Category category) {
        super(category, "Collisions", "Adds collision boxes to certain blocks/areas.");
    }

    @EventHandler
    private void onCollisionShape(CollisionShapeEvent event) {
        if (mc.world == null || mc.player == null) {
            return;
        }

        if (event.type != CollisionShapeEvent.CollisionType.BLOCK) {
            return;
        }

        if (blocksSetting.get().contains(event.state.getBlock())) {
            event.shape = VoxelShapes.fullCube();
        } else if (magmaSetting.get() && !mc.player.isSneaking() && event.state.isAir() && mc.world.getBlockState(event.pos.down()).getBlock() == Blocks.MAGMA_BLOCK) {
            event.shape = VoxelShapes.fullCube();
        }
    }

    @EventHandler
    private void onPlayerMove(PlayerMoveEvent event) {
        int x = (int) (mc.player.getX() + event.movement.x) >> 4;
        int z = (int) (mc.player.getZ() + event.movement.z) >> 4;
        if (unloadedChunksSetting.get() && !mc.world.getChunkManager().isChunkLoaded(x, z)) {
            ((IVec3d) event.movement).set(0, event.movement.y, 0);
        }
    }

    @EventHandler
    private void onPacketSend(PacketEvent.Send event) {
        if (!unloadedChunksSetting.get()) {
            return;
        }

        if (event.packet instanceof VehicleMoveC2SPacket packet) {
            if (!mc.world.getChunkManager().isChunkLoaded((int) packet.getX() >> 4, (int) packet.getZ() >> 4)) {
                mc.player.getVehicle().updatePosition(mc.player.getVehicle().prevX, mc.player.getVehicle().prevY, mc.player.getVehicle().prevZ);
                event.cancel();
            }
        } else if (event.packet instanceof PlayerMoveC2SPacket packet) {
            if (!mc.world.getChunkManager().isChunkLoaded((int) packet.getX(mc.player.getX()) >> 4, (int) packet.getZ(mc.player.getZ()) >> 4)) {
                event.cancel();
            }
        }
    }

    private boolean blockFilter(Block block) {
        return (block instanceof AbstractFireBlock || block instanceof AbstractPressurePlateBlock || block instanceof TripwireBlock || block instanceof TripwireHookBlock || block instanceof CobwebBlock || block instanceof CampfireBlock || block instanceof SweetBerryBushBlock || block instanceof CactusBlock || block instanceof AbstractRailBlock || block instanceof TrapdoorBlock || block instanceof PowderSnowBlock || block instanceof AbstractCauldronBlock || block instanceof HoneyBlock);
    }

    public boolean ignoreBorder() {
        return  isEnabled() && ignoreBorderSetting.get();
    }
}