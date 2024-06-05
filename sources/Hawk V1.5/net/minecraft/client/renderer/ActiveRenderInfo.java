package net.minecraft.client.renderer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class ActiveRenderInfo {
   private static final FloatBuffer field_178810_d = GLAllocation.createDirectFloatBuffer(3);
   private static float rotationXY;
   private static float rotationYZ;
   private static final FloatBuffer field_178812_b = GLAllocation.createDirectFloatBuffer(16);
   private static final FloatBuffer field_178813_c = GLAllocation.createDirectFloatBuffer(16);
   private static float rotationZ;
   private static Vec3 field_178811_e = new Vec3(0.0D, 0.0D, 0.0D);
   private static final String __OBFID = "CL_00000626";
   private static final IntBuffer field_178814_a = GLAllocation.createDirectIntBuffer(16);
   private static float rotationXZ;
   private static float rotationX;

   public static float func_178808_b() {
      return rotationX;
   }

   public static Vec3 func_178806_a(Entity var0, double var1) {
      double var3 = var0.prevPosX + (var0.posX - var0.prevPosX) * var1;
      double var5 = var0.prevPosY + (var0.posY - var0.prevPosY) * var1;
      double var7 = var0.prevPosZ + (var0.posZ - var0.prevPosZ) * var1;
      double var9 = var3 + field_178811_e.xCoord;
      double var11 = var5 + field_178811_e.yCoord;
      double var13 = var7 + field_178811_e.zCoord;
      return new Vec3(var9, var11, var13);
   }

   public static float func_178805_e() {
      return rotationYZ;
   }

   public static Block func_180786_a(World var0, Entity var1, float var2) {
      Vec3 var3 = func_178806_a(var1, (double)var2);
      BlockPos var4 = new BlockPos(var3);
      IBlockState var5 = var0.getBlockState(var4);
      Block var6 = var5.getBlock();
      if (var6.getMaterial().isLiquid()) {
         float var7 = 0.0F;
         if (var5.getBlock() instanceof BlockLiquid) {
            var7 = BlockLiquid.getLiquidHeightPercent((Integer)var5.getValue(BlockLiquid.LEVEL)) - 0.11111111F;
         }

         float var8 = (float)(var4.getY() + 1) - var7;
         if (var3.yCoord >= (double)var8) {
            var6 = var0.getBlockState(var4.offsetUp()).getBlock();
         }
      }

      return var6;
   }

   public static float func_178803_d() {
      return rotationZ;
   }

   public static float func_178809_c() {
      return rotationXZ;
   }

   public static Vec3 func_178804_a() {
      return field_178811_e;
   }

   public static float func_178807_f() {
      return rotationXY;
   }

   public static void updateRenderInfo(EntityPlayer var0, boolean var1) {
      GlStateManager.getFloat(2982, field_178812_b);
      GlStateManager.getFloat(2983, field_178813_c);
      GL11.glGetInteger(2978, field_178814_a);
      float var2 = (float)((field_178814_a.get(0) + field_178814_a.get(2)) / 2);
      float var3 = (float)((field_178814_a.get(1) + field_178814_a.get(3)) / 2);
      GLU.gluUnProject(var2, var3, 0.0F, field_178812_b, field_178813_c, field_178814_a, field_178810_d);
      field_178811_e = new Vec3((double)field_178810_d.get(0), (double)field_178810_d.get(1), (double)field_178810_d.get(2));
      int var4 = var1 ? 1 : 0;
      float var5 = var0.rotationPitch;
      float var6 = var0.rotationYaw;
      rotationX = MathHelper.cos(var6 * 3.1415927F / 180.0F) * (float)(1 - var4 * 2);
      rotationZ = MathHelper.sin(var6 * 3.1415927F / 180.0F) * (float)(1 - var4 * 2);
      rotationYZ = -rotationZ * MathHelper.sin(var5 * 3.1415927F / 180.0F) * (float)(1 - var4 * 2);
      rotationXY = rotationX * MathHelper.sin(var5 * 3.1415927F / 180.0F) * (float)(1 - var4 * 2);
      rotationXZ = MathHelper.cos(var5 * 3.1415927F / 180.0F);
   }
}
