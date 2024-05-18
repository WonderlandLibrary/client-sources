package net.minecraft.src;

import java.util.*;

public abstract class BlockFluid extends Block
{
    private Icon[] theIcon;
    
    protected BlockFluid(final int par1, final Material par2Material) {
        super(par1, par2Material);
        final float var3 = 0.0f;
        final float var4 = 0.0f;
        this.setBlockBounds(0.0f + var4, 0.0f + var3, 0.0f + var4, 1.0f + var4, 1.0f + var3, 1.0f + var4);
        this.setTickRandomly(true);
    }
    
    @Override
    public boolean getBlocksMovement(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        return this.blockMaterial != Material.lava;
    }
    
    @Override
    public int getBlockColor() {
        return 16777215;
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        if (this.blockMaterial != Material.water) {
            return 16777215;
        }
        int var5 = 0;
        int var6 = 0;
        int var7 = 0;
        for (int var8 = -1; var8 <= 1; ++var8) {
            for (int var9 = -1; var9 <= 1; ++var9) {
                final int var10 = par1IBlockAccess.getBiomeGenForCoords(par2 + var9, par4 + var8).waterColorMultiplier;
                var5 += (var10 & 0xFF0000) >> 16;
                var6 += (var10 & 0xFF00) >> 8;
                var7 += (var10 & 0xFF);
            }
        }
        return (var5 / 9 & 0xFF) << 16 | (var6 / 9 & 0xFF) << 8 | (var7 / 9 & 0xFF);
    }
    
    public static float getFluidHeightPercent(int par0) {
        if (par0 >= 8) {
            par0 = 0;
        }
        return (par0 + 1) / 9.0f;
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        return (par1 != 0 && par1 != 1) ? this.theIcon[1] : this.theIcon[0];
    }
    
    protected int getFlowDecay(final World par1World, final int par2, final int par3, final int par4) {
        return (par1World.getBlockMaterial(par2, par3, par4) == this.blockMaterial) ? par1World.getBlockMetadata(par2, par3, par4) : -1;
    }
    
    protected int getEffectiveFlowDecay(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        if (par1IBlockAccess.getBlockMaterial(par2, par3, par4) != this.blockMaterial) {
            return -1;
        }
        int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        if (var5 >= 8) {
            var5 = 0;
        }
        return var5;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean canCollideCheck(final int par1, final boolean par2) {
        return par2 && par1 == 0;
    }
    
    @Override
    public boolean isBlockSolid(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        final Material var6 = par1IBlockAccess.getBlockMaterial(par2, par3, par4);
        return var6 != this.blockMaterial && (par5 == 1 || (var6 != Material.ice && super.isBlockSolid(par1IBlockAccess, par2, par3, par4, par5)));
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        final Material var6 = par1IBlockAccess.getBlockMaterial(par2, par3, par4);
        return var6 != this.blockMaterial && (par5 == 1 || (var6 != Material.ice && super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5)));
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World par1World, final int par2, final int par3, final int par4) {
        return null;
    }
    
    @Override
    public int getRenderType() {
        return 4;
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return 0;
    }
    
    @Override
    public int quantityDropped(final Random par1Random) {
        return 0;
    }
    
    private Vec3 getFlowVector(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        Vec3 var5 = par1IBlockAccess.getWorldVec3Pool().getVecFromPool(0.0, 0.0, 0.0);
        final int var6 = this.getEffectiveFlowDecay(par1IBlockAccess, par2, par3, par4);
        for (int var7 = 0; var7 < 4; ++var7) {
            int var8 = par2;
            int var9 = par4;
            if (var7 == 0) {
                var8 = par2 - 1;
            }
            if (var7 == 1) {
                var9 = par4 - 1;
            }
            if (var7 == 2) {
                ++var8;
            }
            if (var7 == 3) {
                ++var9;
            }
            int var10 = this.getEffectiveFlowDecay(par1IBlockAccess, var8, par3, var9);
            if (var10 < 0) {
                if (!par1IBlockAccess.getBlockMaterial(var8, par3, var9).blocksMovement()) {
                    var10 = this.getEffectiveFlowDecay(par1IBlockAccess, var8, par3 - 1, var9);
                    if (var10 >= 0) {
                        final int var11 = var10 - (var6 - 8);
                        var5 = var5.addVector((var8 - par2) * var11, (par3 - par3) * var11, (var9 - par4) * var11);
                    }
                }
            }
            else if (var10 >= 0) {
                final int var11 = var10 - var6;
                var5 = var5.addVector((var8 - par2) * var11, (par3 - par3) * var11, (var9 - par4) * var11);
            }
        }
        if (par1IBlockAccess.getBlockMetadata(par2, par3, par4) >= 8) {
            boolean var12 = false;
            if (var12 || this.isBlockSolid(par1IBlockAccess, par2, par3, par4 - 1, 2)) {
                var12 = true;
            }
            if (var12 || this.isBlockSolid(par1IBlockAccess, par2, par3, par4 + 1, 3)) {
                var12 = true;
            }
            if (var12 || this.isBlockSolid(par1IBlockAccess, par2 - 1, par3, par4, 4)) {
                var12 = true;
            }
            if (var12 || this.isBlockSolid(par1IBlockAccess, par2 + 1, par3, par4, 5)) {
                var12 = true;
            }
            if (var12 || this.isBlockSolid(par1IBlockAccess, par2, par3 + 1, par4 - 1, 2)) {
                var12 = true;
            }
            if (var12 || this.isBlockSolid(par1IBlockAccess, par2, par3 + 1, par4 + 1, 3)) {
                var12 = true;
            }
            if (var12 || this.isBlockSolid(par1IBlockAccess, par2 - 1, par3 + 1, par4, 4)) {
                var12 = true;
            }
            if (var12 || this.isBlockSolid(par1IBlockAccess, par2 + 1, par3 + 1, par4, 5)) {
                var12 = true;
            }
            if (var12) {
                var5 = var5.normalize().addVector(0.0, -6.0, 0.0);
            }
        }
        var5 = var5.normalize();
        return var5;
    }
    
    @Override
    public void velocityToAddToEntity(final World par1World, final int par2, final int par3, final int par4, final Entity par5Entity, final Vec3 par6Vec3) {
        final Vec3 var7 = this.getFlowVector(par1World, par2, par3, par4);
        par6Vec3.xCoord += var7.xCoord;
        par6Vec3.yCoord += var7.yCoord;
        par6Vec3.zCoord += var7.zCoord;
    }
    
    @Override
    public int tickRate(final World par1World) {
        return (this.blockMaterial == Material.water) ? 5 : ((this.blockMaterial == Material.lava) ? (par1World.provider.hasNoSky ? 10 : 30) : 0);
    }
    
    @Override
    public int getMixedBrightnessForBlock(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        final int var5 = par1IBlockAccess.getLightBrightnessForSkyBlocks(par2, par3, par4, 0);
        final int var6 = par1IBlockAccess.getLightBrightnessForSkyBlocks(par2, par3 + 1, par4, 0);
        final int var7 = var5 & 0xFF;
        final int var8 = var6 & 0xFF;
        final int var9 = var5 >> 16 & 0xFF;
        final int var10 = var6 >> 16 & 0xFF;
        return ((var7 > var8) ? var7 : var8) | ((var9 > var10) ? var9 : var10) << 16;
    }
    
    @Override
    public float getBlockBrightness(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        final float var5 = par1IBlockAccess.getLightBrightness(par2, par3, par4);
        final float var6 = par1IBlockAccess.getLightBrightness(par2, par3 + 1, par4);
        return (var5 > var6) ? var5 : var6;
    }
    
    @Override
    public int getRenderBlockPass() {
        return (this.blockMaterial == Material.water) ? 1 : 0;
    }
    
    @Override
    public void randomDisplayTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        if (this.blockMaterial == Material.water) {
            if (par5Random.nextInt(10) == 0) {
                final int var6 = par1World.getBlockMetadata(par2, par3, par4);
                if (var6 <= 0 || var6 >= 8) {
                    par1World.spawnParticle("suspended", par2 + par5Random.nextFloat(), par3 + par5Random.nextFloat(), par4 + par5Random.nextFloat(), 0.0, 0.0, 0.0);
                }
            }
            for (int var6 = 0; var6 < 0; ++var6) {
                final int var7 = par5Random.nextInt(4);
                int var8 = par2;
                int var9 = par4;
                if (var7 == 0) {
                    var8 = par2 - 1;
                }
                if (var7 == 1) {
                    ++var8;
                }
                if (var7 == 2) {
                    var9 = par4 - 1;
                }
                if (var7 == 3) {
                    ++var9;
                }
                if (par1World.getBlockMaterial(var8, par3, var9) == Material.air && (par1World.getBlockMaterial(var8, par3 - 1, var9).blocksMovement() || par1World.getBlockMaterial(var8, par3 - 1, var9).isLiquid())) {
                    final float var10 = 0.0625f;
                    double var11 = par2 + par5Random.nextFloat();
                    final double var12 = par3 + par5Random.nextFloat();
                    double var13 = par4 + par5Random.nextFloat();
                    if (var7 == 0) {
                        var11 = par2 - var10;
                    }
                    if (var7 == 1) {
                        var11 = par2 + 1 + var10;
                    }
                    if (var7 == 2) {
                        var13 = par4 - var10;
                    }
                    if (var7 == 3) {
                        var13 = par4 + 1 + var10;
                    }
                    double var14 = 0.0;
                    double var15 = 0.0;
                    if (var7 == 0) {
                        var14 = -var10;
                    }
                    if (var7 == 1) {
                        var14 = var10;
                    }
                    if (var7 == 2) {
                        var15 = -var10;
                    }
                    if (var7 == 3) {
                        var15 = var10;
                    }
                    par1World.spawnParticle("splash", var11, var12, var13, var14, 0.0, var15);
                }
            }
        }
        if (this.blockMaterial == Material.water && par5Random.nextInt(64) == 0) {
            final int var6 = par1World.getBlockMetadata(par2, par3, par4);
            if (var6 > 0 && var6 < 8) {
                par1World.playSound(par2 + 0.5f, par3 + 0.5f, par4 + 0.5f, "liquid.water", par5Random.nextFloat() * 0.25f + 0.75f, par5Random.nextFloat() * 1.0f + 0.5f, false);
            }
        }
        if (this.blockMaterial == Material.lava && par1World.getBlockMaterial(par2, par3 + 1, par4) == Material.air && !par1World.isBlockOpaqueCube(par2, par3 + 1, par4)) {
            if (par5Random.nextInt(100) == 0) {
                final double var16 = par2 + par5Random.nextFloat();
                final double var17 = par3 + this.maxY;
                final double var18 = par4 + par5Random.nextFloat();
                par1World.spawnParticle("lava", var16, var17, var18, 0.0, 0.0, 0.0);
                par1World.playSound(var16, var17, var18, "liquid.lavapop", 0.2f + par5Random.nextFloat() * 0.2f, 0.9f + par5Random.nextFloat() * 0.15f, false);
            }
            if (par5Random.nextInt(200) == 0) {
                par1World.playSound(par2, par3, par4, "liquid.lava", 0.2f + par5Random.nextFloat() * 0.2f, 0.9f + par5Random.nextFloat() * 0.15f, false);
            }
        }
        if (par5Random.nextInt(10) == 0 && par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) && !par1World.getBlockMaterial(par2, par3 - 2, par4).blocksMovement()) {
            final double var16 = par2 + par5Random.nextFloat();
            final double var17 = par3 - 1.05;
            final double var18 = par4 + par5Random.nextFloat();
            if (this.blockMaterial == Material.water) {
                par1World.spawnParticle("dripWater", var16, var17, var18, 0.0, 0.0, 0.0);
            }
            else {
                par1World.spawnParticle("dripLava", var16, var17, var18, 0.0, 0.0, 0.0);
            }
        }
    }
    
    public static double getFlowDirection(final IBlockAccess par0IBlockAccess, final int par1, final int par2, final int par3, final Material par4Material) {
        Vec3 var5 = null;
        if (par4Material == Material.water) {
            var5 = Block.waterMoving.getFlowVector(par0IBlockAccess, par1, par2, par3);
        }
        if (par4Material == Material.lava) {
            var5 = Block.lavaMoving.getFlowVector(par0IBlockAccess, par1, par2, par3);
        }
        return (var5.xCoord == 0.0 && var5.zCoord == 0.0) ? -1000.0 : (Math.atan2(var5.zCoord, var5.xCoord) - 1.5707963267948966);
    }
    
    @Override
    public void onBlockAdded(final World par1World, final int par2, final int par3, final int par4) {
        this.checkForHarden(par1World, par2, par3, par4);
    }
    
    @Override
    public void onNeighborBlockChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        this.checkForHarden(par1World, par2, par3, par4);
    }
    
    private void checkForHarden(final World par1World, final int par2, final int par3, final int par4) {
        if (par1World.getBlockId(par2, par3, par4) == this.blockID && this.blockMaterial == Material.lava) {
            boolean var5 = false;
            if (var5 || par1World.getBlockMaterial(par2, par3, par4 - 1) == Material.water) {
                var5 = true;
            }
            if (var5 || par1World.getBlockMaterial(par2, par3, par4 + 1) == Material.water) {
                var5 = true;
            }
            if (var5 || par1World.getBlockMaterial(par2 - 1, par3, par4) == Material.water) {
                var5 = true;
            }
            if (var5 || par1World.getBlockMaterial(par2 + 1, par3, par4) == Material.water) {
                var5 = true;
            }
            if (var5 || par1World.getBlockMaterial(par2, par3 + 1, par4) == Material.water) {
                var5 = true;
            }
            if (var5) {
                final int var6 = par1World.getBlockMetadata(par2, par3, par4);
                if (var6 == 0) {
                    par1World.setBlock(par2, par3, par4, Block.obsidian.blockID);
                }
                else if (var6 <= 4) {
                    par1World.setBlock(par2, par3, par4, Block.cobblestone.blockID);
                }
                this.triggerLavaMixEffects(par1World, par2, par3, par4);
            }
        }
    }
    
    protected void triggerLavaMixEffects(final World par1World, final int par2, final int par3, final int par4) {
        par1World.playSoundEffect(par2 + 0.5f, par3 + 0.5f, par4 + 0.5f, "random.fizz", 0.5f, 2.6f + (par1World.rand.nextFloat() - par1World.rand.nextFloat()) * 0.8f);
        for (int var5 = 0; var5 < 8; ++var5) {
            par1World.spawnParticle("largesmoke", par2 + Math.random(), par3 + 1.2, par4 + Math.random(), 0.0, 0.0, 0.0);
        }
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        if (this.blockMaterial == Material.lava) {
            this.theIcon = new Icon[] { par1IconRegister.registerIcon("lava"), par1IconRegister.registerIcon("lava_flow") };
        }
        else {
            this.theIcon = new Icon[] { par1IconRegister.registerIcon("water"), par1IconRegister.registerIcon("water_flow") };
        }
    }
    
    public static Icon func_94424_b(final String par0Str) {
        return (par0Str == "water") ? Block.waterMoving.theIcon[0] : ((par0Str == "water_flow") ? Block.waterMoving.theIcon[1] : ((par0Str == "lava") ? Block.lavaMoving.theIcon[0] : ((par0Str == "lava_flow") ? Block.lavaMoving.theIcon[1] : null)));
    }
}
