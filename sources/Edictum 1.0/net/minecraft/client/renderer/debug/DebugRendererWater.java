package net.minecraft.client.renderer.debug;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DebugRendererWater implements DebugRenderer.IDebugRenderer
{
    private final Minecraft minecraft;
    private EntityPlayer field_190062_b;
    private double field_190063_c;
    private double field_190064_d;
    private double field_190065_e;

    public DebugRendererWater(Minecraft minecraftIn)
    {
        this.minecraft = minecraftIn;
    }

    public void func_190060_a(float p_190060_1_, long p_190060_2_)
    {
        this.field_190062_b = this.minecraft.thePlayer;
        this.field_190063_c = this.field_190062_b.lastTickPosX + (this.field_190062_b.posX - this.field_190062_b.lastTickPosX) * (double)p_190060_1_;
        this.field_190064_d = this.field_190062_b.lastTickPosY + (this.field_190062_b.posY - this.field_190062_b.lastTickPosY) * (double)p_190060_1_;
        this.field_190065_e = this.field_190062_b.lastTickPosZ + (this.field_190062_b.posZ - this.field_190062_b.lastTickPosZ) * (double)p_190060_1_;
        BlockPos blockpos = this.minecraft.thePlayer.getPosition();
        World world = this.minecraft.thePlayer.worldObj;
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(0.0F, 1.0F, 0.0F, 0.75F);
        GlStateManager.disableTexture2D();
        GlStateManager.glLineWidth(6.0F);

        for (BlockPos blockpos1 : BlockPos.PooledMutableBlockPos.getAllInBox(blockpos.add(-10, -10, -10), blockpos.add(10, 10, 10)))
        {
            IBlockState iblockstate = world.getBlockState(blockpos1);

            if (iblockstate.getBlock() == Blocks.WATER || iblockstate.getBlock() == Blocks.FLOWING_WATER)
            {
                double d0 = (double)EntityBoat.getLiquidHeight(iblockstate, world, blockpos1);
                RenderGlobal.func_189696_b((new AxisAlignedBB((double)((float)blockpos1.getX() + 0.01F), (double)((float)blockpos1.getY() + 0.01F), (double)((float)blockpos1.getZ() + 0.01F), (double)((float)blockpos1.getX() + 0.99F), d0, (double)((float)blockpos1.getZ() + 0.99F))).offset(-this.field_190063_c, -this.field_190064_d, -this.field_190065_e), 1.0F, 1.0F, 1.0F, 0.2F);
            }
        }

        for (BlockPos blockpos2 : BlockPos.PooledMutableBlockPos.getAllInBox(blockpos.add(-10, -10, -10), blockpos.add(10, 10, 10)))
        {
            IBlockState iblockstate1 = world.getBlockState(blockpos2);

            if (iblockstate1.getBlock() == Blocks.WATER || iblockstate1.getBlock() == Blocks.FLOWING_WATER)
            {
                Integer integer = (Integer)iblockstate1.getValue(BlockLiquid.LEVEL);
                double d1 = integer.intValue() > 7 ? 0.9D : 1.0D - 0.11D * (double)integer.intValue();
                String s = iblockstate1.getBlock() == Blocks.FLOWING_WATER ? "f" : "s";
                DebugRenderer.func_190076_a(s + " " + integer, (double)blockpos2.getX() + 0.5D, (double)blockpos2.getY() + d1, (double)blockpos2.getZ() + 0.5D, p_190060_1_, -16777216);
            }
        }

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}
