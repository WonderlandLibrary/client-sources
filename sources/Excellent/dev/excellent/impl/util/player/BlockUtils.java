package dev.excellent.impl.util.player;

import dev.excellent.api.interfaces.game.IMinecraft;
import lombok.experimental.UtilityClass;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.joml.Vector3d;

@UtilityClass
public class BlockUtils implements IMinecraft {

    public Block getBlock(BlockPos pos) {
        return getState(pos).getBlock();
    }

    public Block getBlock(double x, double y, double z) {
        return mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
    }

    public Block getBlockAbovePlayer(PlayerEntity inPlayer, double blocks) {
        blocks += inPlayer.getHeight();
        return getBlockAtPos(new BlockPos(inPlayer.getPosX(), inPlayer.getPosY() + blocks, inPlayer.getPosZ()));
    }

    public Block getBlockAtPos(BlockPos inBlockPos) {
        AbstractBlock.AbstractBlockState s = mc.world.getBlockState(inBlockPos);
        return s.getBlock();
    }

    public Block getBlockAtPosC(PlayerEntity inPlayer, double x, double y, double z) {
        return getBlockAtPos(new BlockPos(inPlayer.getPosX() - x, inPlayer.getPosY() - y, inPlayer.getPosZ() - z));
    }

    public float getBlockDistance(float xDiff, float yDiff, float zDiff) {
        return MathHelper.sqrt(((xDiff - 0.5F) * (xDiff - 0.5F)) + ((yDiff - 0.5F) * (yDiff - 0.5F))
                + ((zDiff - 0.5F) * (zDiff - 0.5F)));
    }

    public float[] getDirectionToBlock(final double x, final double y, final double z, final Direction direction) {
        Vector3d var = new Vector3d();
        var.x = x + 0.5D;
        var.y = y + 0.5D;
        var.z = z + 0.5D;
        var.x += (double) direction.getDirectionVec().getX() * 0.5D;
        var.y += (double) direction.getDirectionVec().getY() * 0.5D;
        var.z += (double) direction.getDirectionVec().getZ() * 0.5D;
        return getRotations(var.x, var.y, var.z);
    }

    public float[] getRotations(final double posX, final double posY, final double posZ) {
        final ClientPlayerEntity player = mc.player;
        final double x = posX - player.getPosX();
        final double y = posY - (player.getPosY() + (double) player.getEyeHeight());
        final double z = posZ - player.getPosZ();
        final double dist = MathHelper.sqrt(x * x + z * z);
        final float yaw = (float) (Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
        final float pitch = (float) (-(Math.atan2(y, dist) * 180.0D / Math.PI));
        return new float[]{yaw, pitch};
    }

    public BlockPos getBlockPos(BlockPos inBlockPos) {
        return inBlockPos;
    }

    public BlockPos getBlockPos(double x, double y, double z) {
        return getBlockPos(new BlockPos(x, y, z));
    }

    public BlockPos getBlockPosUnderPlayer(PlayerEntity inPlayer) {
        return new BlockPos(inPlayer.getPosX(), (inPlayer.getPosY() + (mc.player.motion.y + 0.1D)) - 1D, inPlayer.getPosZ());
    }

    public Block getBlockUnderPlayer(PlayerEntity inPlayer) {
        return getBlockAtPos(
                new BlockPos(inPlayer.getPosX(), (inPlayer.getPosY() + (mc.player.motion.y + 0.1D)) - 1D, inPlayer.getPosZ()));
    }

    public float getHorizontalPlayerBlockDistance(BlockPos blockPos) {
        float xDiff = (float) (mc.player.getPosX() - blockPos.getX());
        float zDiff = (float) (mc.player.getPosZ() - blockPos.getZ());
        return MathHelper.sqrt(((xDiff - 0.5F) * (xDiff - 0.5F)) + ((zDiff - 0.5F) * (zDiff - 0.5F)));
    }


    public float getPlayerBlockDistance(BlockPos blockPos) {
        return getPlayerBlockDistance(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public float getPlayerBlockDistance(double posX, double posY, double posZ) {
        float xDiff = (float) (mc.player.getPosX() - posX);
        float yDiff = (float) (mc.player.getPosY() - posY);
        float zDiff = (float) (mc.player.getPosZ() - posZ);
        return getBlockDistance(xDiff, yDiff, zDiff);
    }

    public AbstractBlock.AbstractBlockState getState(BlockPos pos) {
        return mc.world.getBlockState(pos);
    }
}
