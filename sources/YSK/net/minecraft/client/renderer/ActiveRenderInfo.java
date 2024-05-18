package net.minecraft.client.renderer;

import java.nio.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;

public class ActiveRenderInfo
{
    private static float rotationZ;
    private static final FloatBuffer OBJECTCOORDS;
    private static final FloatBuffer PROJECTION;
    private static float rotationYZ;
    private static float rotationXY;
    private static float rotationX;
    private static Vec3 position;
    private static final FloatBuffer MODELVIEW;
    private static final IntBuffer VIEWPORT;
    private static float rotationXZ;
    
    public static float getRotationYZ() {
        return ActiveRenderInfo.rotationYZ;
    }
    
    public static Vec3 getPosition() {
        return ActiveRenderInfo.position;
    }
    
    public static Vec3 projectViewFromEntity(final Entity entity, final double n) {
        return new Vec3(entity.prevPosX + (entity.posX - entity.prevPosX) * n + ActiveRenderInfo.position.xCoord, entity.prevPosY + (entity.posY - entity.prevPosY) * n + ActiveRenderInfo.position.yCoord, entity.prevPosZ + (entity.posZ - entity.prevPosZ) * n + ActiveRenderInfo.position.zCoord);
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static float getRotationXZ() {
        return ActiveRenderInfo.rotationXZ;
    }
    
    static {
        VIEWPORT = GLAllocation.createDirectIntBuffer(0x99 ^ 0x89);
        MODELVIEW = GLAllocation.createDirectFloatBuffer(0xD1 ^ 0xC1);
        PROJECTION = GLAllocation.createDirectFloatBuffer(0x20 ^ 0x30);
        OBJECTCOORDS = GLAllocation.createDirectFloatBuffer("   ".length());
        ActiveRenderInfo.position = new Vec3(0.0, 0.0, 0.0);
    }
    
    public static float getRotationZ() {
        return ActiveRenderInfo.rotationZ;
    }
    
    public static void updateRenderInfo(final EntityPlayer entityPlayer, final boolean b) {
        GlStateManager.getFloat(2265 + 2044 - 2464 + 1137, ActiveRenderInfo.MODELVIEW);
        GlStateManager.getFloat(88 + 1846 - 1378 + 2427, ActiveRenderInfo.PROJECTION);
        GL11.glGetInteger(1092 + 2899 - 2570 + 1557, ActiveRenderInfo.VIEWPORT);
        GLU.gluUnProject((float)((ActiveRenderInfo.VIEWPORT.get("".length()) + ActiveRenderInfo.VIEWPORT.get("  ".length())) / "  ".length()), (float)((ActiveRenderInfo.VIEWPORT.get(" ".length()) + ActiveRenderInfo.VIEWPORT.get("   ".length())) / "  ".length()), 0.0f, ActiveRenderInfo.MODELVIEW, ActiveRenderInfo.PROJECTION, ActiveRenderInfo.VIEWPORT, ActiveRenderInfo.OBJECTCOORDS);
        ActiveRenderInfo.position = new Vec3(ActiveRenderInfo.OBJECTCOORDS.get("".length()), ActiveRenderInfo.OBJECTCOORDS.get(" ".length()), ActiveRenderInfo.OBJECTCOORDS.get("  ".length()));
        int n;
        if (b) {
            n = " ".length();
            "".length();
            if (0 < -1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        final int n2 = n;
        final float rotationPitch = entityPlayer.rotationPitch;
        final float rotationYaw = entityPlayer.rotationYaw;
        ActiveRenderInfo.rotationX = MathHelper.cos(rotationYaw * 3.1415927f / 180.0f) * (" ".length() - n2 * "  ".length());
        ActiveRenderInfo.rotationZ = MathHelper.sin(rotationYaw * 3.1415927f / 180.0f) * (" ".length() - n2 * "  ".length());
        ActiveRenderInfo.rotationYZ = -ActiveRenderInfo.rotationZ * MathHelper.sin(rotationPitch * 3.1415927f / 180.0f) * (" ".length() - n2 * "  ".length());
        ActiveRenderInfo.rotationXY = ActiveRenderInfo.rotationX * MathHelper.sin(rotationPitch * 3.1415927f / 180.0f) * (" ".length() - n2 * "  ".length());
        ActiveRenderInfo.rotationXZ = MathHelper.cos(rotationPitch * 3.1415927f / 180.0f);
    }
    
    public static float getRotationXY() {
        return ActiveRenderInfo.rotationXY;
    }
    
    public static float getRotationX() {
        return ActiveRenderInfo.rotationX;
    }
    
    public static Block getBlockAtEntityViewpoint(final World world, final Entity entity, final float n) {
        final Vec3 projectViewFromEntity = projectViewFromEntity(entity, n);
        final BlockPos blockPos = new BlockPos(projectViewFromEntity);
        final IBlockState blockState = world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        if (block.getMaterial().isLiquid()) {
            float n2 = 0.0f;
            if (blockState.getBlock() instanceof BlockLiquid) {
                n2 = BlockLiquid.getLiquidHeightPercent(blockState.getValue((IProperty<Integer>)BlockLiquid.LEVEL)) - 0.11111111f;
            }
            if (projectViewFromEntity.yCoord >= blockPos.getY() + " ".length() - n2) {
                block = world.getBlockState(blockPos.up()).getBlock();
            }
        }
        return block;
    }
}
