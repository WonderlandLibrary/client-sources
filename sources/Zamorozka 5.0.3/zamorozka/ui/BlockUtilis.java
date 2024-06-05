package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class BlockUtilis {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static float[] getDirectionToBlock(int var0, int var1, int var2, EnumFacing var3) {
        EntityEgg var4 = new EntityEgg(mc.world);
        var4.posX = var0 + 0.5D;
        var4.posY = var1 + 0.5D;
        var4.posZ = var2 + 0.5D;
        var4.posX += var3.getDirectionVec().getX() * 0.25D;
        var4.posY += var3.getDirectionVec().getY() * 0.25D;
        var4.posZ += var3.getDirectionVec().getZ() * 0.25D;
        return getDirectionToEntity(var4);
    }

    private static float[] getDirectionToEntity(Entity var0) {
        return new float[]{getYaw(var0) + Minecraft.player.rotationYaw, getPitch(var0) + Minecraft.player.rotationPitch};
    }

    public static float[] getRotationNeededForBlock(EntityPlayer paramEntityPlayer, BlockPos pos) {
        double d1 = pos.getX() - paramEntityPlayer.posX;
        double d2 = pos.getY() + 0.5D - paramEntityPlayer.posY + paramEntityPlayer.getEyeHeight();
        double d3 = pos.getZ() - paramEntityPlayer.posZ;
        double d4 = Math.sqrt(d1 * d1 + d3 * d3);
        float f1 = (float) (Math.atan2(d3, d1) * 180.0D / Math.PI) - 90.0F;
        float f2 = (float) -(Math.atan2(d2, d4) * 180.0D / Math.PI);
        return new float[]{f1, f2};
    }

    public static float getYaw(Entity var0) {
        double var5, var1 = var0.posX - Minecraft.player.posX;
        double var3 = var0.posZ - Minecraft.player.posZ;
        if (var3 < 0.0D && var1 < 0.0D) {
            var5 = 90.0D + Math.toDegrees(Math.atan(var3 / var1));
        } else if (var3 < 0.0D && var1 > 0.0D) {
            var5 = -90.0D + Math.toDegrees(Math.atan(var3 / var1));
        } else {
            var5 = Math.toDegrees(-Math.atan(var1 / var3));
        }
        return MathHelper.wrapDegrees(-(Minecraft.player.rotationYaw - (float) var5));
    }

    public static float getPitch(Entity var0) {
        double var1 = var0.posX - Minecraft.player.posX;
        double var3 = var0.posZ - Minecraft.player.posZ;
        double var5 = var0.posY - 1.6D + var0.getEyeHeight() - Minecraft.player.posY;
        double var7 = MathHelper.sqrt(var1 * var1 + var3 * var3);
        double var9 = -Math.toDegrees(Math.atan(var5 / var7));
        return -MathHelper.wrapDegrees(Minecraft.player.rotationPitch - (float) var9);
    }

    public static void blockESPBox(BlockPos blockPos) {
        double x =
                blockPos.getX() -
                        RenderManager.renderPosX;
        double y =
                blockPos.getY() -
                        RenderManager.renderPosY;
        double z =
                blockPos.getZ() -
                        RenderManager.renderPosZ;
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(1.0F);
        GL11.glColor4d(0.0D, 1.0D, 0.0D, 0.15000000596046448D);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4d(0.0D, 0.0D, 1.0D, 0.5D);
        RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), new Color(255, 255, 255).getRGB(), 55, 55, 55);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }
}