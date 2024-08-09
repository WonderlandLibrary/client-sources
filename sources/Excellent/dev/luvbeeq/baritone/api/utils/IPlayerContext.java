package dev.luvbeeq.baritone.api.utils;

import dev.luvbeeq.baritone.api.cache.IWorldData;
import net.minecraft.block.SlabBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author Brady
 * @since 11/12/2018
 */
public interface IPlayerContext {

    Minecraft minecraft();

    ClientPlayerEntity player();

    IPlayerController playerController();

    World world();

    default Iterable<Entity> entities() {
        return ((ClientWorld) world()).getAllEntities();
    }

    default Stream<Entity> entitiesStream() {
        return StreamSupport.stream(entities().spliterator(), false);
    }


    IWorldData worldData();

    RayTraceResult objectMouseOver();

    default BetterBlockPos playerFeet() {
        // TODO find a better way to deal with soul sand!!!!!
        BetterBlockPos feet = new BetterBlockPos(player().getPositionVec().x, player().getPositionVec().y + 0.1251, player().getPositionVec().z);

        // sometimes when calling this from another thread or while world is null, it'll throw a NullPointerException
        // that causes the game to immediately crash
        //
        // so of course crashing on 2b is horribly bad due to queue times and logout spot
        // catch the NPE and ignore it if it does happen
        //
        // this does not impact performance at all since we're not null checking constantly
        // if there is an exception, the only overhead is Java generating the exception object... so we can ignore it
        try {
            if (world().getBlockState(feet).getBlock() instanceof SlabBlock) {
                return feet.up();
            }
        } catch(NullPointerException ignored) {
        }

        return feet;
    }

    default Vector3d playerFeetAsVec() {
        return new Vector3d(player().getPositionVec().x, player().getPositionVec().y, player().getPositionVec().z);
    }

    default Vector3d playerHead() {
        return new Vector3d(player().getPositionVec().x, player().getPositionVec().y + player().getEyeHeight(), player().getPositionVec().z);
    }

    BetterBlockPos viewerPos();

    default Rotation playerRotations() {
        return new Rotation(player().rotationYaw, player().rotationPitch);
    }

    static double eyeHeight(boolean ifSneaking) {
        return ifSneaking ? 1.27 : 1.62;
    }

    /**
     * Returns the block that the crosshair is currently placed over. Updated once per tick.
     *
     * @return The position of the highlighted block
     */
    default Optional<BlockPos> getSelectedBlock() {
        RayTraceResult result = objectMouseOver();
        if (result != null && result.getType() == RayTraceResult.Type.BLOCK) {
            return Optional.of(((BlockRayTraceResult) result).getPos());
        }
        return Optional.empty();
    }

    default boolean isLookingAt(BlockPos pos) {
        return getSelectedBlock().equals(Optional.of(pos));
    }
}
