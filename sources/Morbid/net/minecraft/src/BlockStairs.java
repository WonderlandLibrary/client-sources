package net.minecraft.src;

import java.util.*;

public class BlockStairs extends Block
{
    private static final int[][] field_72159_a;
    private final Block modelBlock;
    private final int modelBlockMetadata;
    private boolean field_72156_cr;
    private int field_72160_cs;
    
    static {
        field_72159_a = new int[][] { { 2, 6 }, { 3, 7 }, { 2, 3 }, { 6, 7 }, { 0, 4 }, { 1, 5 }, { 0, 1 }, { 4, 5 } };
    }
    
    protected BlockStairs(final int par1, final Block par2Block, final int par3) {
        super(par1, par2Block.blockMaterial);
        this.field_72156_cr = false;
        this.field_72160_cs = 0;
        this.modelBlock = par2Block;
        this.modelBlockMetadata = par3;
        this.setHardness(par2Block.blockHardness);
        this.setResistance(par2Block.blockResistance / 3.0f);
        this.setStepSound(par2Block.stepSound);
        this.setLightOpacity(255);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        if (this.field_72156_cr) {
            this.setBlockBounds(0.5f * (this.field_72160_cs % 2), 0.5f * (this.field_72160_cs / 2 % 2), 0.5f * (this.field_72160_cs / 4 % 2), 0.5f + 0.5f * (this.field_72160_cs % 2), 0.5f + 0.5f * (this.field_72160_cs / 2 % 2), 0.5f + 0.5f * (this.field_72160_cs / 4 % 2));
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public int getRenderType() {
        return 10;
    }
    
    public void func_82541_d(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        final int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        if ((var5 & 0x4) != 0x0) {
            this.setBlockBounds(0.0f, 0.5f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        }
    }
    
    public static boolean isBlockStairsID(final int par0) {
        return par0 > 0 && Block.blocksList[par0] instanceof BlockStairs;
    }
    
    private boolean func_82540_f(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        final int var6 = par1IBlockAccess.getBlockId(par2, par3, par4);
        return isBlockStairsID(var6) && par1IBlockAccess.getBlockMetadata(par2, par3, par4) == par5;
    }
    
    public boolean func_82542_g(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        final int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        final int var6 = var5 & 0x3;
        float var7 = 0.5f;
        float var8 = 1.0f;
        if ((var5 & 0x4) != 0x0) {
            var7 = 0.0f;
            var8 = 0.5f;
        }
        float var9 = 0.0f;
        float var10 = 1.0f;
        float var11 = 0.0f;
        float var12 = 0.5f;
        boolean var13 = true;
        if (var6 == 0) {
            var9 = 0.5f;
            var12 = 1.0f;
            final int var14 = par1IBlockAccess.getBlockId(par2 + 1, par3, par4);
            final int var15 = par1IBlockAccess.getBlockMetadata(par2 + 1, par3, par4);
            if (isBlockStairsID(var14) && (var5 & 0x4) == (var15 & 0x4)) {
                final int var16 = var15 & 0x3;
                if (var16 == 3 && !this.func_82540_f(par1IBlockAccess, par2, par3, par4 + 1, var5)) {
                    var12 = 0.5f;
                    var13 = false;
                }
                else if (var16 == 2 && !this.func_82540_f(par1IBlockAccess, par2, par3, par4 - 1, var5)) {
                    var11 = 0.5f;
                    var13 = false;
                }
            }
        }
        else if (var6 == 1) {
            var10 = 0.5f;
            var12 = 1.0f;
            final int var14 = par1IBlockAccess.getBlockId(par2 - 1, par3, par4);
            final int var15 = par1IBlockAccess.getBlockMetadata(par2 - 1, par3, par4);
            if (isBlockStairsID(var14) && (var5 & 0x4) == (var15 & 0x4)) {
                final int var16 = var15 & 0x3;
                if (var16 == 3 && !this.func_82540_f(par1IBlockAccess, par2, par3, par4 + 1, var5)) {
                    var12 = 0.5f;
                    var13 = false;
                }
                else if (var16 == 2 && !this.func_82540_f(par1IBlockAccess, par2, par3, par4 - 1, var5)) {
                    var11 = 0.5f;
                    var13 = false;
                }
            }
        }
        else if (var6 == 2) {
            var11 = 0.5f;
            var12 = 1.0f;
            final int var14 = par1IBlockAccess.getBlockId(par2, par3, par4 + 1);
            final int var15 = par1IBlockAccess.getBlockMetadata(par2, par3, par4 + 1);
            if (isBlockStairsID(var14) && (var5 & 0x4) == (var15 & 0x4)) {
                final int var16 = var15 & 0x3;
                if (var16 == 1 && !this.func_82540_f(par1IBlockAccess, par2 + 1, par3, par4, var5)) {
                    var10 = 0.5f;
                    var13 = false;
                }
                else if (var16 == 0 && !this.func_82540_f(par1IBlockAccess, par2 - 1, par3, par4, var5)) {
                    var9 = 0.5f;
                    var13 = false;
                }
            }
        }
        else if (var6 == 3) {
            final int var14 = par1IBlockAccess.getBlockId(par2, par3, par4 - 1);
            final int var15 = par1IBlockAccess.getBlockMetadata(par2, par3, par4 - 1);
            if (isBlockStairsID(var14) && (var5 & 0x4) == (var15 & 0x4)) {
                final int var16 = var15 & 0x3;
                if (var16 == 1 && !this.func_82540_f(par1IBlockAccess, par2 + 1, par3, par4, var5)) {
                    var10 = 0.5f;
                    var13 = false;
                }
                else if (var16 == 0 && !this.func_82540_f(par1IBlockAccess, par2 - 1, par3, par4, var5)) {
                    var9 = 0.5f;
                    var13 = false;
                }
            }
        }
        this.setBlockBounds(var9, var7, var11, var10, var8, var12);
        return var13;
    }
    
    public boolean func_82544_h(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        final int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        final int var6 = var5 & 0x3;
        float var7 = 0.5f;
        float var8 = 1.0f;
        if ((var5 & 0x4) != 0x0) {
            var7 = 0.0f;
            var8 = 0.5f;
        }
        float var9 = 0.0f;
        float var10 = 0.5f;
        float var11 = 0.5f;
        float var12 = 1.0f;
        boolean var13 = false;
        if (var6 == 0) {
            final int var14 = par1IBlockAccess.getBlockId(par2 - 1, par3, par4);
            final int var15 = par1IBlockAccess.getBlockMetadata(par2 - 1, par3, par4);
            if (isBlockStairsID(var14) && (var5 & 0x4) == (var15 & 0x4)) {
                final int var16 = var15 & 0x3;
                if (var16 == 3 && !this.func_82540_f(par1IBlockAccess, par2, par3, par4 - 1, var5)) {
                    var11 = 0.0f;
                    var12 = 0.5f;
                    var13 = true;
                }
                else if (var16 == 2 && !this.func_82540_f(par1IBlockAccess, par2, par3, par4 + 1, var5)) {
                    var11 = 0.5f;
                    var12 = 1.0f;
                    var13 = true;
                }
            }
        }
        else if (var6 == 1) {
            final int var14 = par1IBlockAccess.getBlockId(par2 + 1, par3, par4);
            final int var15 = par1IBlockAccess.getBlockMetadata(par2 + 1, par3, par4);
            if (isBlockStairsID(var14) && (var5 & 0x4) == (var15 & 0x4)) {
                var9 = 0.5f;
                var10 = 1.0f;
                final int var16 = var15 & 0x3;
                if (var16 == 3 && !this.func_82540_f(par1IBlockAccess, par2, par3, par4 - 1, var5)) {
                    var11 = 0.0f;
                    var12 = 0.5f;
                    var13 = true;
                }
                else if (var16 == 2 && !this.func_82540_f(par1IBlockAccess, par2, par3, par4 + 1, var5)) {
                    var11 = 0.5f;
                    var12 = 1.0f;
                    var13 = true;
                }
            }
        }
        else if (var6 == 2) {
            final int var14 = par1IBlockAccess.getBlockId(par2, par3, par4 - 1);
            final int var15 = par1IBlockAccess.getBlockMetadata(par2, par3, par4 - 1);
            if (isBlockStairsID(var14) && (var5 & 0x4) == (var15 & 0x4)) {
                var11 = 0.0f;
                var12 = 0.5f;
                final int var16 = var15 & 0x3;
                if (var16 == 1 && !this.func_82540_f(par1IBlockAccess, par2 - 1, par3, par4, var5)) {
                    var13 = true;
                }
                else if (var16 == 0 && !this.func_82540_f(par1IBlockAccess, par2 + 1, par3, par4, var5)) {
                    var9 = 0.5f;
                    var10 = 1.0f;
                    var13 = true;
                }
            }
        }
        else if (var6 == 3) {
            final int var14 = par1IBlockAccess.getBlockId(par2, par3, par4 + 1);
            final int var15 = par1IBlockAccess.getBlockMetadata(par2, par3, par4 + 1);
            if (isBlockStairsID(var14) && (var5 & 0x4) == (var15 & 0x4)) {
                final int var16 = var15 & 0x3;
                if (var16 == 1 && !this.func_82540_f(par1IBlockAccess, par2 - 1, par3, par4, var5)) {
                    var13 = true;
                }
                else if (var16 == 0 && !this.func_82540_f(par1IBlockAccess, par2 + 1, par3, par4, var5)) {
                    var9 = 0.5f;
                    var10 = 1.0f;
                    var13 = true;
                }
            }
        }
        if (var13) {
            this.setBlockBounds(var9, var7, var11, var10, var8, var12);
        }
        return var13;
    }
    
    @Override
    public void addCollisionBoxesToList(final World par1World, final int par2, final int par3, final int par4, final AxisAlignedBB par5AxisAlignedBB, final List par6List, final Entity par7Entity) {
        this.func_82541_d(par1World, par2, par3, par4);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        final boolean var8 = this.func_82542_g(par1World, par2, par3, par4);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        if (var8 && this.func_82544_h(par1World, par2, par3, par4)) {
            super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void randomDisplayTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        this.modelBlock.randomDisplayTick(par1World, par2, par3, par4, par5Random);
    }
    
    @Override
    public void onBlockClicked(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer) {
        this.modelBlock.onBlockClicked(par1World, par2, par3, par4, par5EntityPlayer);
    }
    
    @Override
    public void onBlockDestroyedByPlayer(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        this.modelBlock.onBlockDestroyedByPlayer(par1World, par2, par3, par4, par5);
    }
    
    @Override
    public int getMixedBrightnessForBlock(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        return this.modelBlock.getMixedBrightnessForBlock(par1IBlockAccess, par2, par3, par4);
    }
    
    @Override
    public float getBlockBrightness(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        return this.modelBlock.getBlockBrightness(par1IBlockAccess, par2, par3, par4);
    }
    
    @Override
    public float getExplosionResistance(final Entity par1Entity) {
        return this.modelBlock.getExplosionResistance(par1Entity);
    }
    
    @Override
    public int getRenderBlockPass() {
        return this.modelBlock.getRenderBlockPass();
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        return this.modelBlock.getIcon(par1, this.modelBlockMetadata);
    }
    
    @Override
    public int tickRate(final World par1World) {
        return this.modelBlock.tickRate(par1World);
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(final World par1World, final int par2, final int par3, final int par4) {
        return this.modelBlock.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
    }
    
    @Override
    public void velocityToAddToEntity(final World par1World, final int par2, final int par3, final int par4, final Entity par5Entity, final Vec3 par6Vec3) {
        this.modelBlock.velocityToAddToEntity(par1World, par2, par3, par4, par5Entity, par6Vec3);
    }
    
    @Override
    public boolean isCollidable() {
        return this.modelBlock.isCollidable();
    }
    
    @Override
    public boolean canCollideCheck(final int par1, final boolean par2) {
        return this.modelBlock.canCollideCheck(par1, par2);
    }
    
    @Override
    public boolean canPlaceBlockAt(final World par1World, final int par2, final int par3, final int par4) {
        return this.modelBlock.canPlaceBlockAt(par1World, par2, par3, par4);
    }
    
    @Override
    public void onBlockAdded(final World par1World, final int par2, final int par3, final int par4) {
        this.onNeighborBlockChange(par1World, par2, par3, par4, 0);
        this.modelBlock.onBlockAdded(par1World, par2, par3, par4);
    }
    
    @Override
    public void breakBlock(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6) {
        this.modelBlock.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
    
    @Override
    public void onEntityWalking(final World par1World, final int par2, final int par3, final int par4, final Entity par5Entity) {
        this.modelBlock.onEntityWalking(par1World, par2, par3, par4, par5Entity);
    }
    
    @Override
    public void updateTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        this.modelBlock.updateTick(par1World, par2, par3, par4, par5Random);
    }
    
    @Override
    public boolean onBlockActivated(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer, final int par6, final float par7, final float par8, final float par9) {
        return this.modelBlock.onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer, 0, 0.0f, 0.0f, 0.0f);
    }
    
    @Override
    public void onBlockDestroyedByExplosion(final World par1World, final int par2, final int par3, final int par4, final Explosion par5Explosion) {
        this.modelBlock.onBlockDestroyedByExplosion(par1World, par2, par3, par4, par5Explosion);
    }
    
    @Override
    public void onBlockPlacedBy(final World par1World, final int par2, final int par3, final int par4, final EntityLiving par5EntityLiving, final ItemStack par6ItemStack) {
        final int var7 = MathHelper.floor_double(par5EntityLiving.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
        final int var8 = par1World.getBlockMetadata(par2, par3, par4) & 0x4;
        if (var7 == 0) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 0x2 | var8, 2);
        }
        if (var7 == 1) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 0x1 | var8, 2);
        }
        if (var7 == 2) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 0x3 | var8, 2);
        }
        if (var7 == 3) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, var8, 2);
        }
    }
    
    @Override
    public int onBlockPlaced(final World par1World, final int par2, final int par3, final int par4, final int par5, final float par6, final float par7, final float par8, final int par9) {
        return (par5 != 0 && (par5 == 1 || par7 <= 0.5)) ? par9 : (par9 | 0x4);
    }
    
    @Override
    public MovingObjectPosition collisionRayTrace(final World par1World, final int par2, final int par3, final int par4, final Vec3 par5Vec3, final Vec3 par6Vec3) {
        final MovingObjectPosition[] var7 = new MovingObjectPosition[8];
        final int var8 = par1World.getBlockMetadata(par2, par3, par4);
        final int var9 = var8 & 0x3;
        final boolean var10 = (var8 & 0x4) == 0x4;
        final int[] var11 = BlockStairs.field_72159_a[var9 + (var10 ? 4 : 0)];
        this.field_72156_cr = true;
        for (int var12 = 0; var12 < 8; ++var12) {
            this.field_72160_cs = var12;
            final int[] var13 = var11;
            for (int var14 = var11.length, var15 = 0; var15 < var14; ++var15) {
                final int var16 = var13[var15];
                if (var16 == var12) {}
            }
            var7[var12] = super.collisionRayTrace(par1World, par2, par3, par4, par5Vec3, par6Vec3);
        }
        final int[] var17 = var11;
        for (int var18 = var11.length, var14 = 0; var14 < var18; ++var14) {
            final int var15 = var17[var14];
            var7[var15] = null;
        }
        MovingObjectPosition var19 = null;
        double var20 = 0.0;
        final MovingObjectPosition[] var21 = var7;
        final int var16 = var7.length;
        for (final MovingObjectPosition var23 : var21) {
            if (var23 != null) {
                final double var24 = var23.hitVec.squareDistanceTo(par6Vec3);
                if (var24 > var20) {
                    var19 = var23;
                    var20 = var24;
                }
            }
        }
        return var19;
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
    }
}
