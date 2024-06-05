package net.minecraft.src;

import net.minecraft.client.*;
import org.lwjgl.opengl.*;

public class RenderBlocks
{
    public IBlockAccess blockAccess;
    public Icon overrideBlockTexture;
    public boolean flipTexture;
    public boolean renderAllFaces;
    public static boolean fancyGrass;
    public static boolean cfgGrassFix;
    public boolean useInventoryTint;
    public double renderMinX;
    public double renderMaxX;
    public double renderMinY;
    public double renderMaxY;
    public double renderMinZ;
    public double renderMaxZ;
    public boolean lockBlockBounds;
    public boolean partialRenderBounds;
    public final Minecraft minecraftRB;
    public int uvRotateEast;
    public int uvRotateWest;
    public int uvRotateSouth;
    public int uvRotateNorth;
    public int uvRotateTop;
    public int uvRotateBottom;
    public boolean enableAO;
    public float aoLightValueScratchXYZNNN;
    public float aoLightValueScratchXYNN;
    public float aoLightValueScratchXYZNNP;
    public float aoLightValueScratchYZNN;
    public float aoLightValueScratchYZNP;
    public float aoLightValueScratchXYZPNN;
    public float aoLightValueScratchXYPN;
    public float aoLightValueScratchXYZPNP;
    public float aoLightValueScratchXYZNPN;
    public float aoLightValueScratchXYNP;
    public float aoLightValueScratchXYZNPP;
    public float aoLightValueScratchYZPN;
    public float aoLightValueScratchXYZPPN;
    public float aoLightValueScratchXYPP;
    public float aoLightValueScratchYZPP;
    public float aoLightValueScratchXYZPPP;
    public float aoLightValueScratchXZNN;
    public float aoLightValueScratchXZPN;
    public float aoLightValueScratchXZNP;
    public float aoLightValueScratchXZPP;
    public int aoBrightnessXYZNNN;
    public int aoBrightnessXYNN;
    public int aoBrightnessXYZNNP;
    public int aoBrightnessYZNN;
    public int aoBrightnessYZNP;
    public int aoBrightnessXYZPNN;
    public int aoBrightnessXYPN;
    public int aoBrightnessXYZPNP;
    public int aoBrightnessXYZNPN;
    public int aoBrightnessXYNP;
    public int aoBrightnessXYZNPP;
    public int aoBrightnessYZPN;
    public int aoBrightnessXYZPPN;
    public int aoBrightnessXYPP;
    public int aoBrightnessYZPP;
    public int aoBrightnessXYZPPP;
    public int aoBrightnessXZNN;
    public int aoBrightnessXZPN;
    public int aoBrightnessXZNP;
    public int aoBrightnessXZPP;
    public int brightnessTopLeft;
    public int brightnessBottomLeft;
    public int brightnessBottomRight;
    public int brightnessTopRight;
    public float colorRedTopLeft;
    public float colorRedBottomLeft;
    public float colorRedBottomRight;
    public float colorRedTopRight;
    public float colorGreenTopLeft;
    public float colorGreenBottomLeft;
    public float colorGreenBottomRight;
    public float colorGreenTopRight;
    public float colorBlueTopLeft;
    public float colorBlueBottomLeft;
    public float colorBlueBottomRight;
    public float colorBlueTopRight;
    public boolean aoLightValuesCalculated;
    public float aoLightValueOpaque;
    
    static {
        RenderBlocks.fancyGrass = true;
        RenderBlocks.cfgGrassFix = true;
    }
    
    public RenderBlocks(final IBlockAccess par1IBlockAccess) {
        this.overrideBlockTexture = null;
        this.flipTexture = false;
        this.renderAllFaces = false;
        this.useInventoryTint = true;
        this.lockBlockBounds = false;
        this.partialRenderBounds = false;
        this.uvRotateEast = 0;
        this.uvRotateWest = 0;
        this.uvRotateSouth = 0;
        this.uvRotateNorth = 0;
        this.uvRotateTop = 0;
        this.uvRotateBottom = 0;
        this.aoLightValueOpaque = 0.2f;
        this.blockAccess = par1IBlockAccess;
        this.minecraftRB = Minecraft.getMinecraft();
        this.aoLightValueOpaque = 1.0f - Config.getAmbientOcclusionLevel() * 0.8f;
    }
    
    public RenderBlocks() {
        this.overrideBlockTexture = null;
        this.flipTexture = false;
        this.renderAllFaces = false;
        this.useInventoryTint = true;
        this.lockBlockBounds = false;
        this.partialRenderBounds = false;
        this.uvRotateEast = 0;
        this.uvRotateWest = 0;
        this.uvRotateSouth = 0;
        this.uvRotateNorth = 0;
        this.uvRotateTop = 0;
        this.uvRotateBottom = 0;
        this.aoLightValueOpaque = 0.2f;
        this.minecraftRB = Minecraft.getMinecraft();
    }
    
    public void setOverrideBlockTexture(final Icon par1Icon) {
        this.overrideBlockTexture = par1Icon;
    }
    
    public void clearOverrideBlockTexture() {
        this.overrideBlockTexture = null;
    }
    
    public boolean hasOverrideBlockTexture() {
        return this.overrideBlockTexture != null;
    }
    
    public void setRenderBounds(final double par1, final double par3, final double par5, final double par7, final double par9, final double par11) {
        if (!this.lockBlockBounds) {
            this.renderMinX = par1;
            this.renderMaxX = par7;
            this.renderMinY = par3;
            this.renderMaxY = par9;
            this.renderMinZ = par5;
            this.renderMaxZ = par11;
            this.partialRenderBounds = (this.minecraftRB.gameSettings.ambientOcclusion >= 2 && (this.renderMinX > 0.0 || this.renderMaxX < 1.0 || this.renderMinY > 0.0 || this.renderMaxY < 1.0 || this.renderMinZ > 0.0 || this.renderMaxZ < 1.0));
        }
    }
    
    public void setRenderBoundsFromBlock(final Block par1Block) {
        if (!this.lockBlockBounds) {
            this.renderMinX = par1Block.getBlockBoundsMinX();
            this.renderMaxX = par1Block.getBlockBoundsMaxX();
            this.renderMinY = par1Block.getBlockBoundsMinY();
            this.renderMaxY = par1Block.getBlockBoundsMaxY();
            this.renderMinZ = par1Block.getBlockBoundsMinZ();
            this.renderMaxZ = par1Block.getBlockBoundsMaxZ();
            this.partialRenderBounds = (this.minecraftRB.gameSettings.ambientOcclusion >= 2 && (this.renderMinX > 0.0 || this.renderMaxX < 1.0 || this.renderMinY > 0.0 || this.renderMaxY < 1.0 || this.renderMinZ > 0.0 || this.renderMaxZ < 1.0));
        }
    }
    
    public void overrideBlockBounds(final double par1, final double par3, final double par5, final double par7, final double par9, final double par11) {
        this.renderMinX = par1;
        this.renderMaxX = par7;
        this.renderMinY = par3;
        this.renderMaxY = par9;
        this.renderMinZ = par5;
        this.renderMaxZ = par11;
        this.lockBlockBounds = true;
        this.partialRenderBounds = (this.minecraftRB.gameSettings.ambientOcclusion >= 2 && (this.renderMinX > 0.0 || this.renderMaxX < 1.0 || this.renderMinY > 0.0 || this.renderMaxY < 1.0 || this.renderMinZ > 0.0 || this.renderMaxZ < 1.0));
    }
    
    public void unlockBlockBounds() {
        this.lockBlockBounds = false;
    }
    
    public void renderBlockUsingTexture(final Block par1Block, final int par2, final int par3, final int par4, final Icon par5Icon) {
        this.setOverrideBlockTexture(par5Icon);
        this.renderBlockByRenderType(par1Block, par2, par3, par4);
        this.clearOverrideBlockTexture();
    }
    
    public void renderBlockAllFaces(final Block par1Block, final int par2, final int par3, final int par4) {
        this.renderAllFaces = true;
        this.renderBlockByRenderType(par1Block, par2, par3, par4);
        this.renderAllFaces = false;
    }
    
    public boolean renderBlockByRenderType(final Block par1Block, final int par2, final int par3, final int par4) {
        final int var5 = par1Block.getRenderType();
        if (var5 == -1) {
            return false;
        }
        par1Block.setBlockBoundsBasedOnState(this.blockAccess, par2, par3, par4);
        if (Config.isBetterSnow() && par1Block == Block.signPost && this.hasSnowNeighbours(par2, par3, par4)) {
            this.renderSnow(par2, par3, par4, Block.snow.maxY);
        }
        this.setRenderBoundsFromBlock(par1Block);
        switch (var5) {
            case 0: {
                return this.renderStandardBlock(par1Block, par2, par3, par4);
            }
            case 1: {
                return this.renderCrossedSquares(par1Block, par2, par3, par4);
            }
            case 2: {
                return this.renderBlockTorch(par1Block, par2, par3, par4);
            }
            case 3: {
                return this.renderBlockFire((BlockFire)par1Block, par2, par3, par4);
            }
            case 4: {
                return this.renderBlockFluids(par1Block, par2, par3, par4);
            }
            case 5: {
                return this.renderBlockRedstoneWire(par1Block, par2, par3, par4);
            }
            case 6: {
                return this.renderBlockCrops(par1Block, par2, par3, par4);
            }
            case 7: {
                return this.renderBlockDoor(par1Block, par2, par3, par4);
            }
            case 8: {
                return this.renderBlockLadder(par1Block, par2, par3, par4);
            }
            case 9: {
                return this.renderBlockMinecartTrack((BlockRailBase)par1Block, par2, par3, par4);
            }
            case 10: {
                return this.renderBlockStairs((BlockStairs)par1Block, par2, par3, par4);
            }
            case 11: {
                return this.renderBlockFence((BlockFence)par1Block, par2, par3, par4);
            }
            case 12: {
                return this.renderBlockLever(par1Block, par2, par3, par4);
            }
            case 13: {
                return this.renderBlockCactus(par1Block, par2, par3, par4);
            }
            case 14: {
                return this.renderBlockBed(par1Block, par2, par3, par4);
            }
            case 15: {
                return this.renderBlockRepeater((BlockRedstoneRepeater)par1Block, par2, par3, par4);
            }
            case 16: {
                return this.renderPistonBase(par1Block, par2, par3, par4, false);
            }
            case 17: {
                return this.renderPistonExtension(par1Block, par2, par3, par4, true);
            }
            case 18: {
                return this.renderBlockPane((BlockPane)par1Block, par2, par3, par4);
            }
            case 19: {
                return this.renderBlockStem(par1Block, par2, par3, par4);
            }
            case 20: {
                return this.renderBlockVine(par1Block, par2, par3, par4);
            }
            case 21: {
                return this.renderBlockFenceGate((BlockFenceGate)par1Block, par2, par3, par4);
            }
            default: {
                if (Reflector.ModLoader.exists()) {
                    return Reflector.callBoolean(Reflector.ModLoader_renderWorldBlock, this, this.blockAccess, par2, par3, par4, par1Block, var5);
                }
                return Reflector.FMLRenderAccessLibrary.exists() && Reflector.callBoolean(Reflector.FMLRenderAccessLibrary_renderWorldBlock, this, this.blockAccess, par2, par3, par4, par1Block, var5);
            }
            case 23: {
                return this.renderBlockLilyPad(par1Block, par2, par3, par4);
            }
            case 24: {
                return this.renderBlockCauldron((BlockCauldron)par1Block, par2, par3, par4);
            }
            case 25: {
                return this.renderBlockBrewingStand((BlockBrewingStand)par1Block, par2, par3, par4);
            }
            case 26: {
                return this.renderBlockEndPortalFrame((BlockEndPortalFrame)par1Block, par2, par3, par4);
            }
            case 27: {
                return this.renderBlockDragonEgg((BlockDragonEgg)par1Block, par2, par3, par4);
            }
            case 28: {
                return this.renderBlockCocoa((BlockCocoa)par1Block, par2, par3, par4);
            }
            case 29: {
                return this.renderBlockTripWireSource(par1Block, par2, par3, par4);
            }
            case 30: {
                return this.renderBlockTripWire(par1Block, par2, par3, par4);
            }
            case 31: {
                return this.renderBlockLog(par1Block, par2, par3, par4);
            }
            case 32: {
                return this.renderBlockWall((BlockWall)par1Block, par2, par3, par4);
            }
            case 33: {
                return this.renderBlockFlowerpot((BlockFlowerPot)par1Block, par2, par3, par4);
            }
            case 34: {
                return this.renderBlockBeacon((BlockBeacon)par1Block, par2, par3, par4);
            }
            case 35: {
                return this.renderBlockAnvil((BlockAnvil)par1Block, par2, par3, par4);
            }
            case 36: {
                return this.renderBlockRedstoneLogic((BlockRedstoneLogic)par1Block, par2, par3, par4);
            }
            case 37: {
                return this.renderBlockComparator((BlockComparator)par1Block, par2, par3, par4);
            }
            case 38: {
                return this.renderBlockHopper((BlockHopper)par1Block, par2, par3, par4);
            }
            case 39: {
                return this.renderBlockQuartz(par1Block, par2, par3, par4);
            }
        }
    }
    
    public boolean renderBlockEndPortalFrame(final BlockEndPortalFrame par1BlockEndPortalFrame, final int par2, final int par3, final int par4) {
        final int var5 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        final int var6 = var5 & 0x3;
        if (var6 == 0) {
            this.uvRotateTop = 3;
        }
        else if (var6 == 3) {
            this.uvRotateTop = 1;
        }
        else if (var6 == 1) {
            this.uvRotateTop = 2;
        }
        if (!BlockEndPortalFrame.isEnderEyeInserted(var5)) {
            this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 0.8125, 1.0);
            this.renderStandardBlock(par1BlockEndPortalFrame, par2, par3, par4);
            this.uvRotateTop = 0;
            return true;
        }
        this.renderAllFaces = true;
        this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 0.8125, 1.0);
        this.renderStandardBlock(par1BlockEndPortalFrame, par2, par3, par4);
        this.setOverrideBlockTexture(par1BlockEndPortalFrame.func_94398_p());
        this.setRenderBounds(0.25, 0.8125, 0.25, 0.75, 1.0, 0.75);
        this.renderStandardBlock(par1BlockEndPortalFrame, par2, par3, par4);
        this.renderAllFaces = false;
        this.clearOverrideBlockTexture();
        this.uvRotateTop = 0;
        return true;
    }
    
    public boolean renderBlockBed(final Block par1Block, final int par2, final int par3, final int par4) {
        final Tessellator var5 = Tessellator.instance;
        final int var6 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        int var7 = BlockDirectional.getDirection(var6);
        boolean var8 = BlockBed.isBlockHeadOfBed(var6);
        if (Reflector.ForgeBlock_getBedDirection.exists()) {
            var7 = Reflector.callInt(par1Block, Reflector.ForgeBlock_getBedDirection, this.blockAccess, par2, par3, par4);
        }
        if (Reflector.ForgeBlock_isBedFoot.exists()) {
            var8 = Reflector.callBoolean(par1Block, Reflector.ForgeBlock_isBedFoot, this.blockAccess, par2, par3, par4);
        }
        final float var9 = 0.5f;
        final float var10 = 1.0f;
        final float var11 = 0.8f;
        final float var12 = 0.6f;
        final int var13 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
        var5.setBrightness(var13);
        var5.setColorOpaque_F(var9, var9, var9);
        Icon var14 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 0);
        if (this.overrideBlockTexture != null) {
            var14 = this.overrideBlockTexture;
        }
        double var15 = var14.getMinU();
        double var16 = var14.getMaxU();
        double var17 = var14.getMinV();
        double var18 = var14.getMaxV();
        double var19 = par2 + this.renderMinX;
        double var20 = par2 + this.renderMaxX;
        double var21 = par3 + this.renderMinY + 0.1875;
        double var22 = par4 + this.renderMinZ;
        double var23 = par4 + this.renderMaxZ;
        var5.addVertexWithUV(var19, var21, var23, var15, var18);
        var5.addVertexWithUV(var19, var21, var22, var15, var17);
        var5.addVertexWithUV(var20, var21, var22, var16, var17);
        var5.addVertexWithUV(var20, var21, var23, var16, var18);
        var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4));
        var5.setColorOpaque_F(var10, var10, var10);
        var14 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 1);
        if (this.overrideBlockTexture != null) {
            var14 = this.overrideBlockTexture;
        }
        var15 = var14.getMinU();
        var16 = var14.getMaxU();
        var17 = var14.getMinV();
        var18 = var14.getMaxV();
        var19 = var15;
        var20 = var16;
        var21 = var17;
        var22 = var17;
        var23 = var15;
        double var24 = var16;
        double var25 = var18;
        double var26 = var18;
        if (var7 == 0) {
            var20 = var15;
            var21 = var18;
            var23 = var16;
            var26 = var17;
        }
        else if (var7 == 2) {
            var19 = var16;
            var22 = var18;
            var24 = var15;
            var25 = var17;
        }
        else if (var7 == 3) {
            var19 = var16;
            var22 = var18;
            var24 = var15;
            var25 = var17;
            var20 = var15;
            var21 = var18;
            var23 = var16;
            var26 = var17;
        }
        final double var27 = par2 + this.renderMinX;
        final double var28 = par2 + this.renderMaxX;
        final double var29 = par3 + this.renderMaxY;
        final double var30 = par4 + this.renderMinZ;
        final double var31 = par4 + this.renderMaxZ;
        var5.addVertexWithUV(var28, var29, var31, var23, var25);
        var5.addVertexWithUV(var28, var29, var30, var19, var21);
        var5.addVertexWithUV(var27, var29, var30, var20, var22);
        var5.addVertexWithUV(var27, var29, var31, var24, var26);
        int var32 = Direction.directionToFacing[var7];
        if (var8) {
            var32 = Direction.directionToFacing[Direction.rotateOpposite[var7]];
        }
        byte var33 = 4;
        switch (var7) {
            case 0: {
                var33 = 5;
                break;
            }
            case 1: {
                var33 = 3;
                break;
            }
            case 3: {
                var33 = 2;
                break;
            }
        }
        if (var32 != 2 && (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3, par4 - 1, 2))) {
            var5.setBrightness((this.renderMinZ > 0.0) ? var13 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1));
            var5.setColorOpaque_F(var11, var11, var11);
            this.flipTexture = (var33 == 2);
            this.renderFaceZNeg(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 2));
        }
        if (var32 != 3 && (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3, par4 + 1, 3))) {
            var5.setBrightness((this.renderMaxZ < 1.0) ? var13 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1));
            var5.setColorOpaque_F(var11, var11, var11);
            this.flipTexture = (var33 == 3);
            this.renderFaceZPos(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 3));
        }
        if (var32 != 4 && (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2 - 1, par3, par4, 4))) {
            var5.setBrightness((this.renderMinZ > 0.0) ? var13 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4));
            var5.setColorOpaque_F(var12, var12, var12);
            this.flipTexture = (var33 == 4);
            this.renderFaceXNeg(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 4));
        }
        if (var32 != 5 && (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2 + 1, par3, par4, 5))) {
            var5.setBrightness((this.renderMaxZ < 1.0) ? var13 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4));
            var5.setColorOpaque_F(var12, var12, var12);
            this.flipTexture = (var33 == 5);
            this.renderFaceXPos(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 5));
        }
        this.flipTexture = false;
        return true;
    }
    
    public boolean renderBlockBrewingStand(final BlockBrewingStand par1BlockBrewingStand, final int par2, final int par3, final int par4) {
        this.setRenderBounds(0.4375, 0.0, 0.4375, 0.5625, 0.875, 0.5625);
        this.renderStandardBlock(par1BlockBrewingStand, par2, par3, par4);
        this.setOverrideBlockTexture(par1BlockBrewingStand.getBrewingStandIcon());
        this.setRenderBounds(0.5625, 0.0, 0.3125, 0.9375, 0.125, 0.6875);
        this.renderStandardBlock(par1BlockBrewingStand, par2, par3, par4);
        this.setRenderBounds(0.125, 0.0, 0.0625, 0.5, 0.125, 0.4375);
        this.renderStandardBlock(par1BlockBrewingStand, par2, par3, par4);
        this.setRenderBounds(0.125, 0.0, 0.5625, 0.5, 0.125, 0.9375);
        this.renderStandardBlock(par1BlockBrewingStand, par2, par3, par4);
        this.clearOverrideBlockTexture();
        final Tessellator var5 = Tessellator.instance;
        var5.setBrightness(par1BlockBrewingStand.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        final float var6 = 1.0f;
        final int var7 = par1BlockBrewingStand.colorMultiplier(this.blockAccess, par2, par3, par4);
        float var8 = (var7 >> 16 & 0xFF) / 255.0f;
        float var9 = (var7 >> 8 & 0xFF) / 255.0f;
        float var10 = (var7 & 0xFF) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            final float var11 = (var8 * 30.0f + var9 * 59.0f + var10 * 11.0f) / 100.0f;
            final float var12 = (var8 * 30.0f + var9 * 70.0f) / 100.0f;
            final float var13 = (var8 * 30.0f + var10 * 70.0f) / 100.0f;
            var8 = var11;
            var9 = var12;
            var10 = var13;
        }
        var5.setColorOpaque_F(var6 * var8, var6 * var9, var6 * var10);
        Icon var14 = this.getBlockIconFromSideAndMetadata(par1BlockBrewingStand, 0, 0);
        if (this.hasOverrideBlockTexture()) {
            var14 = this.overrideBlockTexture;
        }
        final double var15 = var14.getMinV();
        final double var16 = var14.getMaxV();
        final int var17 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        for (int var18 = 0; var18 < 3; ++var18) {
            final double var19 = var18 * 3.141592653589793 * 2.0 / 3.0 + 1.5707963267948966;
            final double var20 = var14.getInterpolatedU(8.0);
            double var21 = var14.getMaxU();
            if ((var17 & 1 << var18) != 0x0) {
                var21 = var14.getMinU();
            }
            final double var22 = par2 + 0.5;
            final double var23 = par2 + 0.5 + Math.sin(var19) * 8.0 / 16.0;
            final double var24 = par4 + 0.5;
            final double var25 = par4 + 0.5 + Math.cos(var19) * 8.0 / 16.0;
            var5.addVertexWithUV(var22, par3 + 1, var24, var20, var15);
            var5.addVertexWithUV(var22, par3 + 0, var24, var20, var16);
            var5.addVertexWithUV(var23, par3 + 0, var25, var21, var16);
            var5.addVertexWithUV(var23, par3 + 1, var25, var21, var15);
            var5.addVertexWithUV(var23, par3 + 1, var25, var21, var15);
            var5.addVertexWithUV(var23, par3 + 0, var25, var21, var16);
            var5.addVertexWithUV(var22, par3 + 0, var24, var20, var16);
            var5.addVertexWithUV(var22, par3 + 1, var24, var20, var15);
        }
        par1BlockBrewingStand.setBlockBoundsForItemRender();
        return true;
    }
    
    public boolean renderBlockCauldron(final BlockCauldron par1BlockCauldron, final int par2, final int par3, final int par4) {
        this.renderStandardBlock(par1BlockCauldron, par2, par3, par4);
        final Tessellator var5 = Tessellator.instance;
        var5.setBrightness(par1BlockCauldron.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        final float var6 = 1.0f;
        final int var7 = par1BlockCauldron.colorMultiplier(this.blockAccess, par2, par3, par4);
        float var8 = (var7 >> 16 & 0xFF) / 255.0f;
        float var9 = (var7 >> 8 & 0xFF) / 255.0f;
        float var10 = (var7 & 0xFF) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            final float var11 = (var8 * 30.0f + var9 * 59.0f + var10 * 11.0f) / 100.0f;
            final float var12 = (var8 * 30.0f + var9 * 70.0f) / 100.0f;
            final float var13 = (var8 * 30.0f + var10 * 70.0f) / 100.0f;
            var8 = var11;
            var9 = var12;
            var10 = var13;
        }
        var5.setColorOpaque_F(var6 * var8, var6 * var9, var6 * var10);
        final Icon var14 = par1BlockCauldron.getBlockTextureFromSide(2);
        final float var12 = 0.125f;
        this.renderFaceXPos(par1BlockCauldron, par2 - 1.0f + var12, par3, par4, var14);
        this.renderFaceXNeg(par1BlockCauldron, par2 + 1.0f - var12, par3, par4, var14);
        this.renderFaceZPos(par1BlockCauldron, par2, par3, par4 - 1.0f + var12, var14);
        this.renderFaceZNeg(par1BlockCauldron, par2, par3, par4 + 1.0f - var12, var14);
        final Icon var15 = BlockCauldron.func_94375_b("cauldron_inner");
        this.renderFaceYPos(par1BlockCauldron, par2, par3 - 1.0f + 0.25f, par4, var15);
        this.renderFaceYNeg(par1BlockCauldron, par2, par3 + 1.0f - 0.75f, par4, var15);
        int var16 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        if (var16 > 0) {
            final Icon var17 = BlockFluid.func_94424_b("water");
            if (var16 > 3) {
                var16 = 3;
            }
            final int var18 = CustomColorizer.getFluidColor(Block.waterStill, this.blockAccess, par2, par3, par4);
            final float var19 = (var18 >> 16 & 0xFF) / 255.0f;
            final float var20 = (var18 >> 8 & 0xFF) / 255.0f;
            final float var21 = (var18 & 0xFF) / 255.0f;
            var5.setColorOpaque_F(var19, var20, var21);
            this.renderFaceYPos(par1BlockCauldron, par2, par3 - 1.0f + (6.0f + var16 * 3.0f) / 16.0f, par4, var17);
        }
        return true;
    }
    
    public boolean renderBlockFlowerpot(final BlockFlowerPot par1BlockFlowerPot, final int par2, final int par3, final int par4) {
        this.renderStandardBlock(par1BlockFlowerPot, par2, par3, par4);
        final Tessellator var5 = Tessellator.instance;
        var5.setBrightness(par1BlockFlowerPot.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        final float var6 = 1.0f;
        int var7 = par1BlockFlowerPot.colorMultiplier(this.blockAccess, par2, par3, par4);
        final Icon var8 = this.getBlockIconFromSide(par1BlockFlowerPot, 0);
        float var9 = (var7 >> 16 & 0xFF) / 255.0f;
        float var10 = (var7 >> 8 & 0xFF) / 255.0f;
        float var11 = (var7 & 0xFF) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            final float var12 = (var9 * 30.0f + var10 * 59.0f + var11 * 11.0f) / 100.0f;
            final float var13 = (var9 * 30.0f + var10 * 70.0f) / 100.0f;
            final float var14 = (var9 * 30.0f + var11 * 70.0f) / 100.0f;
            var9 = var12;
            var10 = var13;
            var11 = var14;
        }
        var5.setColorOpaque_F(var6 * var9, var6 * var10, var6 * var11);
        final float var12 = 0.1865f;
        this.renderFaceXPos(par1BlockFlowerPot, par2 - 0.5f + var12, par3, par4, var8);
        this.renderFaceXNeg(par1BlockFlowerPot, par2 + 0.5f - var12, par3, par4, var8);
        this.renderFaceZPos(par1BlockFlowerPot, par2, par3, par4 - 0.5f + var12, var8);
        this.renderFaceZNeg(par1BlockFlowerPot, par2, par3, par4 + 0.5f - var12, var8);
        this.renderFaceYPos(par1BlockFlowerPot, par2, par3 - 0.5f + var12 + 0.1875f, par4, this.getBlockIcon(Block.dirt));
        final int var15 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        if (var15 != 0) {
            final float var14 = 0.0f;
            final float var16 = 4.0f;
            final float var17 = 0.0f;
            BlockFlower var18 = null;
            switch (var15) {
                case 1: {
                    var18 = Block.plantRed;
                    break;
                }
                case 2: {
                    var18 = Block.plantYellow;
                    break;
                }
                case 7: {
                    var18 = Block.mushroomRed;
                    break;
                }
                case 8: {
                    var18 = Block.mushroomBrown;
                    break;
                }
            }
            var5.addTranslation(var14 / 16.0f, var16 / 16.0f, var17 / 16.0f);
            if (var18 != null) {
                this.renderBlockByRenderType(var18, par2, par3, par4);
            }
            else if (var15 == 9) {
                this.renderAllFaces = true;
                final float var19 = 0.125f;
                this.setRenderBounds(0.5f - var19, 0.0, 0.5f - var19, 0.5f + var19, 0.25, 0.5f + var19);
                this.renderStandardBlock(Block.cactus, par2, par3, par4);
                this.setRenderBounds(0.5f - var19, 0.25, 0.5f - var19, 0.5f + var19, 0.5, 0.5f + var19);
                this.renderStandardBlock(Block.cactus, par2, par3, par4);
                this.setRenderBounds(0.5f - var19, 0.5, 0.5f - var19, 0.5f + var19, 0.75, 0.5f + var19);
                this.renderStandardBlock(Block.cactus, par2, par3, par4);
                this.renderAllFaces = false;
                this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
            }
            else if (var15 == 3) {
                this.drawCrossedSquares(Block.sapling, 0, par2, par3, par4, 0.75f);
            }
            else if (var15 == 5) {
                this.drawCrossedSquares(Block.sapling, 2, par2, par3, par4, 0.75f);
            }
            else if (var15 == 4) {
                this.drawCrossedSquares(Block.sapling, 1, par2, par3, par4, 0.75f);
            }
            else if (var15 == 6) {
                this.drawCrossedSquares(Block.sapling, 3, par2, par3, par4, 0.75f);
            }
            else if (var15 == 11) {
                var7 = Block.tallGrass.colorMultiplier(this.blockAccess, par2, par3, par4);
                var9 = (var7 >> 16 & 0xFF) / 255.0f;
                var10 = (var7 >> 8 & 0xFF) / 255.0f;
                var11 = (var7 & 0xFF) / 255.0f;
                var5.setColorOpaque_F(var6 * var9, var6 * var10, var6 * var11);
                this.drawCrossedSquares(Block.tallGrass, 2, par2, par3, par4, 0.75f);
            }
            else if (var15 == 10) {
                this.drawCrossedSquares(Block.deadBush, 2, par2, par3, par4, 0.75f);
            }
            var5.addTranslation(-var14 / 16.0f, -var16 / 16.0f, -var17 / 16.0f);
        }
        return true;
    }
    
    public boolean renderBlockAnvil(final BlockAnvil par1BlockAnvil, final int par2, final int par3, final int par4) {
        return this.renderBlockAnvilMetadata(par1BlockAnvil, par2, par3, par4, this.blockAccess.getBlockMetadata(par2, par3, par4));
    }
    
    public boolean renderBlockAnvilMetadata(final BlockAnvil par1BlockAnvil, final int par2, final int par3, final int par4, final int par5) {
        final Tessellator var6 = Tessellator.instance;
        var6.setBrightness(par1BlockAnvil.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        final float var7 = 1.0f;
        final int var8 = par1BlockAnvil.colorMultiplier(this.blockAccess, par2, par3, par4);
        float var9 = (var8 >> 16 & 0xFF) / 255.0f;
        float var10 = (var8 >> 8 & 0xFF) / 255.0f;
        float var11 = (var8 & 0xFF) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            final float var12 = (var9 * 30.0f + var10 * 59.0f + var11 * 11.0f) / 100.0f;
            final float var13 = (var9 * 30.0f + var10 * 70.0f) / 100.0f;
            final float var14 = (var9 * 30.0f + var11 * 70.0f) / 100.0f;
            var9 = var12;
            var10 = var13;
            var11 = var14;
        }
        var6.setColorOpaque_F(var7 * var9, var7 * var10, var7 * var11);
        return this.renderBlockAnvilOrient(par1BlockAnvil, par2, par3, par4, par5, false);
    }
    
    public boolean renderBlockAnvilOrient(final BlockAnvil par1BlockAnvil, final int par2, final int par3, final int par4, final int par5, final boolean par6) {
        final int var7 = par6 ? 0 : (par5 & 0x3);
        boolean var8 = false;
        float var9 = 0.0f;
        switch (var7) {
            case 0: {
                this.uvRotateSouth = 2;
                this.uvRotateNorth = 1;
                this.uvRotateTop = 3;
                this.uvRotateBottom = 3;
                break;
            }
            case 1: {
                this.uvRotateEast = 1;
                this.uvRotateWest = 2;
                this.uvRotateTop = 2;
                this.uvRotateBottom = 1;
                var8 = true;
                break;
            }
            case 2: {
                this.uvRotateSouth = 1;
                this.uvRotateNorth = 2;
                break;
            }
            case 3: {
                this.uvRotateEast = 2;
                this.uvRotateWest = 1;
                this.uvRotateTop = 1;
                this.uvRotateBottom = 2;
                var8 = true;
                break;
            }
        }
        var9 = this.renderBlockAnvilRotate(par1BlockAnvil, par2, par3, par4, 0, var9, 0.75f, 0.25f, 0.75f, var8, par6, par5);
        var9 = this.renderBlockAnvilRotate(par1BlockAnvil, par2, par3, par4, 1, var9, 0.5f, 0.0625f, 0.625f, var8, par6, par5);
        var9 = this.renderBlockAnvilRotate(par1BlockAnvil, par2, par3, par4, 2, var9, 0.25f, 0.3125f, 0.5f, var8, par6, par5);
        this.renderBlockAnvilRotate(par1BlockAnvil, par2, par3, par4, 3, var9, 0.625f, 0.375f, 1.0f, var8, par6, par5);
        this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
        this.uvRotateEast = 0;
        this.uvRotateWest = 0;
        this.uvRotateSouth = 0;
        this.uvRotateNorth = 0;
        this.uvRotateTop = 0;
        this.uvRotateBottom = 0;
        return true;
    }
    
    public float renderBlockAnvilRotate(final BlockAnvil par1BlockAnvil, final int par2, final int par3, final int par4, final int par5, final float par6, float par7, final float par8, float par9, final boolean par10, final boolean par11, final int par12) {
        if (par10) {
            final float var13 = par7;
            par7 = par9;
            par9 = var13;
        }
        par7 /= 2.0f;
        par9 /= 2.0f;
        par1BlockAnvil.field_82521_b = par5;
        this.setRenderBounds(0.5f - par7, par6, 0.5f - par9, 0.5f + par7, par6 + par8, 0.5f + par9);
        if (par11) {
            final Tessellator var14 = Tessellator.instance;
            var14.startDrawingQuads();
            var14.setNormal(0.0f, -1.0f, 0.0f);
            this.renderFaceYNeg(par1BlockAnvil, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(par1BlockAnvil, 0, par12));
            var14.draw();
            var14.startDrawingQuads();
            var14.setNormal(0.0f, 1.0f, 0.0f);
            this.renderFaceYPos(par1BlockAnvil, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(par1BlockAnvil, 1, par12));
            var14.draw();
            var14.startDrawingQuads();
            var14.setNormal(0.0f, 0.0f, -1.0f);
            this.renderFaceZNeg(par1BlockAnvil, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(par1BlockAnvil, 2, par12));
            var14.draw();
            var14.startDrawingQuads();
            var14.setNormal(0.0f, 0.0f, 1.0f);
            this.renderFaceZPos(par1BlockAnvil, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(par1BlockAnvil, 3, par12));
            var14.draw();
            var14.startDrawingQuads();
            var14.setNormal(-1.0f, 0.0f, 0.0f);
            this.renderFaceXNeg(par1BlockAnvil, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(par1BlockAnvil, 4, par12));
            var14.draw();
            var14.startDrawingQuads();
            var14.setNormal(1.0f, 0.0f, 0.0f);
            this.renderFaceXPos(par1BlockAnvil, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(par1BlockAnvil, 5, par12));
            var14.draw();
        }
        else {
            this.renderStandardBlock(par1BlockAnvil, par2, par3, par4);
        }
        return par6 + par8;
    }
    
    public boolean renderBlockTorch(final Block par1Block, final int par2, final int par3, final int par4) {
        final int var5 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        final Tessellator var6 = Tessellator.instance;
        var6.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        var6.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        final double var7 = 0.4000000059604645;
        final double var8 = 0.5 - var7;
        final double var9 = 0.20000000298023224;
        if (var5 == 1) {
            this.renderTorchAtAngle(par1Block, par2 - var8, par3 + var9, par4, -var7, 0.0, 0);
        }
        else if (var5 == 2) {
            this.renderTorchAtAngle(par1Block, par2 + var8, par3 + var9, par4, var7, 0.0, 0);
        }
        else if (var5 == 3) {
            this.renderTorchAtAngle(par1Block, par2, par3 + var9, par4 - var8, 0.0, -var7, 0);
        }
        else if (var5 == 4) {
            this.renderTorchAtAngle(par1Block, par2, par3 + var9, par4 + var8, 0.0, var7, 0);
        }
        else {
            this.renderTorchAtAngle(par1Block, par2, par3, par4, 0.0, 0.0, 0);
            if (par1Block != Block.torchWood && Config.isBetterSnow() && this.hasSnowNeighbours(par2, par3, par4)) {
                this.renderSnow(par2, par3, par4, Block.snow.maxY);
            }
        }
        return true;
    }
    
    public boolean renderBlockRepeater(final BlockRedstoneRepeater par1BlockRedstoneRepeater, final int par2, final int par3, final int par4) {
        final int var5 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        final int var6 = var5 & 0x3;
        final int var7 = (var5 & 0xC) >> 2;
        final Tessellator var8 = Tessellator.instance;
        var8.setBrightness(par1BlockRedstoneRepeater.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        var8.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        final double var9 = -0.1875;
        final boolean var10 = par1BlockRedstoneRepeater.func_94476_e(this.blockAccess, par2, par3, par4, var5);
        double var11 = 0.0;
        double var12 = 0.0;
        double var13 = 0.0;
        double var14 = 0.0;
        switch (var6) {
            case 0: {
                var14 = -0.3125;
                var12 = BlockRedstoneRepeater.repeaterTorchOffset[var7];
                break;
            }
            case 1: {
                var13 = 0.3125;
                var11 = -BlockRedstoneRepeater.repeaterTorchOffset[var7];
                break;
            }
            case 2: {
                var14 = 0.3125;
                var12 = -BlockRedstoneRepeater.repeaterTorchOffset[var7];
                break;
            }
            case 3: {
                var13 = -0.3125;
                var11 = BlockRedstoneRepeater.repeaterTorchOffset[var7];
                break;
            }
        }
        if (!var10) {
            this.renderTorchAtAngle(par1BlockRedstoneRepeater, par2 + var11, par3 + var9, par4 + var12, 0.0, 0.0, 0);
        }
        else {
            final Icon var15 = this.getBlockIcon(Block.bedrock);
            this.setOverrideBlockTexture(var15);
            float var16 = 2.0f;
            float var17 = 14.0f;
            float var18 = 7.0f;
            float var19 = 9.0f;
            switch (var6) {
                case 1:
                case 3: {
                    var16 = 7.0f;
                    var17 = 9.0f;
                    var18 = 2.0f;
                    var19 = 14.0f;
                    break;
                }
            }
            this.setRenderBounds(var16 / 16.0f + (float)var11, 0.125, var18 / 16.0f + (float)var12, var17 / 16.0f + (float)var11, 0.25, var19 / 16.0f + (float)var12);
            final double var20 = var15.getInterpolatedU(var16);
            final double var21 = var15.getInterpolatedV(var18);
            final double var22 = var15.getInterpolatedU(var17);
            final double var23 = var15.getInterpolatedV(var19);
            var8.addVertexWithUV(par2 + var16 / 16.0f + var11, par3 + 0.25f, par4 + var18 / 16.0f + var12, var20, var21);
            var8.addVertexWithUV(par2 + var16 / 16.0f + var11, par3 + 0.25f, par4 + var19 / 16.0f + var12, var20, var23);
            var8.addVertexWithUV(par2 + var17 / 16.0f + var11, par3 + 0.25f, par4 + var19 / 16.0f + var12, var22, var23);
            var8.addVertexWithUV(par2 + var17 / 16.0f + var11, par3 + 0.25f, par4 + var18 / 16.0f + var12, var22, var21);
            this.renderStandardBlock(par1BlockRedstoneRepeater, par2, par3, par4);
            this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 0.125, 1.0);
            this.clearOverrideBlockTexture();
        }
        var8.setBrightness(par1BlockRedstoneRepeater.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        var8.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        this.renderTorchAtAngle(par1BlockRedstoneRepeater, par2 + var13, par3 + var9, par4 + var14, 0.0, 0.0, 0);
        this.renderBlockRedstoneLogic(par1BlockRedstoneRepeater, par2, par3, par4);
        return true;
    }
    
    public boolean renderBlockComparator(final BlockComparator par1BlockComparator, final int par2, final int par3, final int par4) {
        final Tessellator var5 = Tessellator.instance;
        var5.setBrightness(par1BlockComparator.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        var5.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        final int var6 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        final int var7 = var6 & 0x3;
        double var8 = 0.0;
        double var9 = -0.1875;
        double var10 = 0.0;
        double var11 = 0.0;
        double var12 = 0.0;
        Icon var13;
        if (par1BlockComparator.func_94490_c(var6)) {
            var13 = Block.torchRedstoneActive.getBlockTextureFromSide(0);
        }
        else {
            var9 -= 0.1875;
            var13 = Block.torchRedstoneIdle.getBlockTextureFromSide(0);
        }
        switch (var7) {
            case 0: {
                var10 = -0.3125;
                var12 = 1.0;
                break;
            }
            case 1: {
                var8 = 0.3125;
                var11 = -1.0;
                break;
            }
            case 2: {
                var10 = 0.3125;
                var12 = -1.0;
                break;
            }
            case 3: {
                var8 = -0.3125;
                var11 = 1.0;
                break;
            }
        }
        this.renderTorchAtAngle(par1BlockComparator, par2 + 0.25 * var11 + 0.1875 * var12, par3 - 0.1875f, par4 + 0.25 * var12 + 0.1875 * var11, 0.0, 0.0, var6);
        this.renderTorchAtAngle(par1BlockComparator, par2 + 0.25 * var11 + -0.1875 * var12, par3 - 0.1875f, par4 + 0.25 * var12 + -0.1875 * var11, 0.0, 0.0, var6);
        this.setOverrideBlockTexture(var13);
        this.renderTorchAtAngle(par1BlockComparator, par2 + var8, par3 + var9, par4 + var10, 0.0, 0.0, var6);
        this.clearOverrideBlockTexture();
        this.renderBlockRedstoneLogicMetadata(par1BlockComparator, par2, par3, par4, var7);
        return true;
    }
    
    public boolean renderBlockRedstoneLogic(final BlockRedstoneLogic par1BlockRedstoneLogic, final int par2, final int par3, final int par4) {
        final Tessellator var5 = Tessellator.instance;
        this.renderBlockRedstoneLogicMetadata(par1BlockRedstoneLogic, par2, par3, par4, this.blockAccess.getBlockMetadata(par2, par3, par4) & 0x3);
        return true;
    }
    
    public void renderBlockRedstoneLogicMetadata(final BlockRedstoneLogic par1BlockRedstoneLogic, final int par2, final int par3, final int par4, final int par5) {
        this.renderStandardBlock(par1BlockRedstoneLogic, par2, par3, par4);
        final Tessellator var6 = Tessellator.instance;
        var6.setBrightness(par1BlockRedstoneLogic.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        var6.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        final int var7 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        final Icon var8 = this.getBlockIconFromSideAndMetadata(par1BlockRedstoneLogic, 1, var7);
        final double var9 = var8.getMinU();
        final double var10 = var8.getMaxU();
        final double var11 = var8.getMinV();
        final double var12 = var8.getMaxV();
        final double var13 = 0.125;
        double var14 = par2 + 1;
        double var15 = par2 + 1;
        double var16 = par2 + 0;
        double var17 = par2 + 0;
        double var18 = par4 + 0;
        double var19 = par4 + 1;
        double var20 = par4 + 1;
        double var21 = par4 + 0;
        final double var22 = par3 + var13;
        if (par5 == 2) {
            var15 = (var14 = par2 + 0);
            var17 = (var16 = par2 + 1);
            var21 = (var18 = par4 + 1);
            var20 = (var19 = par4 + 0);
        }
        else if (par5 == 3) {
            var17 = (var14 = par2 + 0);
            var16 = (var15 = par2 + 1);
            var19 = (var18 = par4 + 0);
            var21 = (var20 = par4 + 1);
        }
        else if (par5 == 1) {
            var17 = (var14 = par2 + 1);
            var16 = (var15 = par2 + 0);
            var19 = (var18 = par4 + 1);
            var21 = (var20 = par4 + 0);
        }
        var6.addVertexWithUV(var17, var22, var21, var9, var11);
        var6.addVertexWithUV(var16, var22, var20, var9, var12);
        var6.addVertexWithUV(var15, var22, var19, var10, var12);
        var6.addVertexWithUV(var14, var22, var18, var10, var11);
    }
    
    public void renderPistonBaseAllFaces(final Block par1Block, final int par2, final int par3, final int par4) {
        this.renderPistonBase(par1Block, par2, par3, par4, this.renderAllFaces = true);
        this.renderAllFaces = false;
    }
    
    public boolean renderPistonBase(final Block par1Block, final int par2, final int par3, final int par4, final boolean par5) {
        final int var6 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        final boolean var7 = par5 || (var6 & 0x8) != 0x0;
        final int var8 = BlockPistonBase.getOrientation(var6);
        if (var7) {
            switch (var8) {
                case 0: {
                    this.uvRotateEast = 3;
                    this.uvRotateWest = 3;
                    this.uvRotateSouth = 3;
                    this.uvRotateNorth = 3;
                    this.setRenderBounds(0.0, 0.25, 0.0, 1.0, 1.0, 1.0);
                    break;
                }
                case 1: {
                    this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 0.75, 1.0);
                    break;
                }
                case 2: {
                    this.uvRotateSouth = 1;
                    this.uvRotateNorth = 2;
                    this.setRenderBounds(0.0, 0.0, 0.25, 1.0, 1.0, 1.0);
                    break;
                }
                case 3: {
                    this.uvRotateSouth = 2;
                    this.uvRotateNorth = 1;
                    this.uvRotateTop = 3;
                    this.uvRotateBottom = 3;
                    this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 0.75);
                    break;
                }
                case 4: {
                    this.uvRotateEast = 1;
                    this.uvRotateWest = 2;
                    this.uvRotateTop = 2;
                    this.uvRotateBottom = 1;
                    this.setRenderBounds(0.25, 0.0, 0.0, 1.0, 1.0, 1.0);
                    break;
                }
                case 5: {
                    this.uvRotateEast = 2;
                    this.uvRotateWest = 1;
                    this.uvRotateTop = 1;
                    this.uvRotateBottom = 2;
                    this.setRenderBounds(0.0, 0.0, 0.0, 0.75, 1.0, 1.0);
                    break;
                }
            }
            ((BlockPistonBase)par1Block).func_96479_b((float)this.renderMinX, (float)this.renderMinY, (float)this.renderMinZ, (float)this.renderMaxX, (float)this.renderMaxY, (float)this.renderMaxZ);
            this.renderStandardBlock(par1Block, par2, par3, par4);
            this.uvRotateEast = 0;
            this.uvRotateWest = 0;
            this.uvRotateSouth = 0;
            this.uvRotateNorth = 0;
            this.uvRotateTop = 0;
            this.uvRotateBottom = 0;
            this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
            ((BlockPistonBase)par1Block).func_96479_b((float)this.renderMinX, (float)this.renderMinY, (float)this.renderMinZ, (float)this.renderMaxX, (float)this.renderMaxY, (float)this.renderMaxZ);
        }
        else {
            switch (var8) {
                case 0: {
                    this.uvRotateEast = 3;
                    this.uvRotateWest = 3;
                    this.uvRotateSouth = 3;
                    this.uvRotateNorth = 3;
                    break;
                }
                case 2: {
                    this.uvRotateSouth = 1;
                    this.uvRotateNorth = 2;
                    break;
                }
                case 3: {
                    this.uvRotateSouth = 2;
                    this.uvRotateNorth = 1;
                    this.uvRotateTop = 3;
                    this.uvRotateBottom = 3;
                    break;
                }
                case 4: {
                    this.uvRotateEast = 1;
                    this.uvRotateWest = 2;
                    this.uvRotateTop = 2;
                    this.uvRotateBottom = 1;
                    break;
                }
                case 5: {
                    this.uvRotateEast = 2;
                    this.uvRotateWest = 1;
                    this.uvRotateTop = 1;
                    this.uvRotateBottom = 2;
                    break;
                }
            }
            this.renderStandardBlock(par1Block, par2, par3, par4);
            this.uvRotateEast = 0;
            this.uvRotateWest = 0;
            this.uvRotateSouth = 0;
            this.uvRotateNorth = 0;
            this.uvRotateTop = 0;
            this.uvRotateBottom = 0;
        }
        return true;
    }
    
    public void renderPistonRodUD(final double par1, final double par3, final double par5, final double par7, final double par9, final double par11, final float par13, final double par14) {
        Icon var16 = BlockPistonBase.func_94496_b("piston_side");
        if (this.hasOverrideBlockTexture()) {
            var16 = this.overrideBlockTexture;
        }
        final Tessellator var17 = Tessellator.instance;
        final double var18 = var16.getMinU();
        final double var19 = var16.getMinV();
        final double var20 = var16.getInterpolatedU(par14);
        final double var21 = var16.getInterpolatedV(4.0);
        var17.setColorOpaque_F(par13, par13, par13);
        var17.addVertexWithUV(par1, par7, par9, var20, var19);
        var17.addVertexWithUV(par1, par5, par9, var18, var19);
        var17.addVertexWithUV(par3, par5, par11, var18, var21);
        var17.addVertexWithUV(par3, par7, par11, var20, var21);
    }
    
    public void renderPistonRodSN(final double par1, final double par3, final double par5, final double par7, final double par9, final double par11, final float par13, final double par14) {
        Icon var16 = BlockPistonBase.func_94496_b("piston_side");
        if (this.hasOverrideBlockTexture()) {
            var16 = this.overrideBlockTexture;
        }
        final Tessellator var17 = Tessellator.instance;
        final double var18 = var16.getMinU();
        final double var19 = var16.getMinV();
        final double var20 = var16.getInterpolatedU(par14);
        final double var21 = var16.getInterpolatedV(4.0);
        var17.setColorOpaque_F(par13, par13, par13);
        var17.addVertexWithUV(par1, par5, par11, var20, var19);
        var17.addVertexWithUV(par1, par5, par9, var18, var19);
        var17.addVertexWithUV(par3, par7, par9, var18, var21);
        var17.addVertexWithUV(par3, par7, par11, var20, var21);
    }
    
    public void renderPistonRodEW(final double par1, final double par3, final double par5, final double par7, final double par9, final double par11, final float par13, final double par14) {
        Icon var16 = BlockPistonBase.func_94496_b("piston_side");
        if (this.hasOverrideBlockTexture()) {
            var16 = this.overrideBlockTexture;
        }
        final Tessellator var17 = Tessellator.instance;
        final double var18 = var16.getMinU();
        final double var19 = var16.getMinV();
        final double var20 = var16.getInterpolatedU(par14);
        final double var21 = var16.getInterpolatedV(4.0);
        var17.setColorOpaque_F(par13, par13, par13);
        var17.addVertexWithUV(par3, par5, par9, var20, var19);
        var17.addVertexWithUV(par1, par5, par9, var18, var19);
        var17.addVertexWithUV(par1, par7, par11, var18, var21);
        var17.addVertexWithUV(par3, par7, par11, var20, var21);
    }
    
    public void renderPistonExtensionAllFaces(final Block par1Block, final int par2, final int par3, final int par4, final boolean par5) {
        this.renderAllFaces = true;
        this.renderPistonExtension(par1Block, par2, par3, par4, par5);
        this.renderAllFaces = false;
    }
    
    public boolean renderPistonExtension(final Block par1Block, final int par2, final int par3, final int par4, final boolean par5) {
        final int var6 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        final int var7 = BlockPistonExtension.getDirectionMeta(var6);
        final float var8 = par1Block.getBlockBrightness(this.blockAccess, par2, par3, par4);
        final float var9 = par5 ? 1.0f : 0.5f;
        final double var10 = par5 ? 16.0 : 8.0;
        switch (var7) {
            case 0: {
                this.uvRotateEast = 3;
                this.uvRotateWest = 3;
                this.uvRotateSouth = 3;
                this.uvRotateNorth = 3;
                this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 0.25, 1.0);
                this.renderStandardBlock(par1Block, par2, par3, par4);
                this.renderPistonRodUD(par2 + 0.375f, par2 + 0.625f, par3 + 0.25f, par3 + 0.25f + var9, par4 + 0.625f, par4 + 0.625f, var8 * 0.8f, var10);
                this.renderPistonRodUD(par2 + 0.625f, par2 + 0.375f, par3 + 0.25f, par3 + 0.25f + var9, par4 + 0.375f, par4 + 0.375f, var8 * 0.8f, var10);
                this.renderPistonRodUD(par2 + 0.375f, par2 + 0.375f, par3 + 0.25f, par3 + 0.25f + var9, par4 + 0.375f, par4 + 0.625f, var8 * 0.6f, var10);
                this.renderPistonRodUD(par2 + 0.625f, par2 + 0.625f, par3 + 0.25f, par3 + 0.25f + var9, par4 + 0.625f, par4 + 0.375f, var8 * 0.6f, var10);
                break;
            }
            case 1: {
                this.setRenderBounds(0.0, 0.75, 0.0, 1.0, 1.0, 1.0);
                this.renderStandardBlock(par1Block, par2, par3, par4);
                this.renderPistonRodUD(par2 + 0.375f, par2 + 0.625f, par3 - 0.25f + 1.0f - var9, par3 - 0.25f + 1.0f, par4 + 0.625f, par4 + 0.625f, var8 * 0.8f, var10);
                this.renderPistonRodUD(par2 + 0.625f, par2 + 0.375f, par3 - 0.25f + 1.0f - var9, par3 - 0.25f + 1.0f, par4 + 0.375f, par4 + 0.375f, var8 * 0.8f, var10);
                this.renderPistonRodUD(par2 + 0.375f, par2 + 0.375f, par3 - 0.25f + 1.0f - var9, par3 - 0.25f + 1.0f, par4 + 0.375f, par4 + 0.625f, var8 * 0.6f, var10);
                this.renderPistonRodUD(par2 + 0.625f, par2 + 0.625f, par3 - 0.25f + 1.0f - var9, par3 - 0.25f + 1.0f, par4 + 0.625f, par4 + 0.375f, var8 * 0.6f, var10);
                break;
            }
            case 2: {
                this.uvRotateSouth = 1;
                this.uvRotateNorth = 2;
                this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 0.25);
                this.renderStandardBlock(par1Block, par2, par3, par4);
                this.renderPistonRodSN(par2 + 0.375f, par2 + 0.375f, par3 + 0.625f, par3 + 0.375f, par4 + 0.25f, par4 + 0.25f + var9, var8 * 0.6f, var10);
                this.renderPistonRodSN(par2 + 0.625f, par2 + 0.625f, par3 + 0.375f, par3 + 0.625f, par4 + 0.25f, par4 + 0.25f + var9, var8 * 0.6f, var10);
                this.renderPistonRodSN(par2 + 0.375f, par2 + 0.625f, par3 + 0.375f, par3 + 0.375f, par4 + 0.25f, par4 + 0.25f + var9, var8 * 0.5f, var10);
                this.renderPistonRodSN(par2 + 0.625f, par2 + 0.375f, par3 + 0.625f, par3 + 0.625f, par4 + 0.25f, par4 + 0.25f + var9, var8, var10);
                break;
            }
            case 3: {
                this.uvRotateSouth = 2;
                this.uvRotateNorth = 1;
                this.uvRotateTop = 3;
                this.uvRotateBottom = 3;
                this.setRenderBounds(0.0, 0.0, 0.75, 1.0, 1.0, 1.0);
                this.renderStandardBlock(par1Block, par2, par3, par4);
                this.renderPistonRodSN(par2 + 0.375f, par2 + 0.375f, par3 + 0.625f, par3 + 0.375f, par4 - 0.25f + 1.0f - var9, par4 - 0.25f + 1.0f, var8 * 0.6f, var10);
                this.renderPistonRodSN(par2 + 0.625f, par2 + 0.625f, par3 + 0.375f, par3 + 0.625f, par4 - 0.25f + 1.0f - var9, par4 - 0.25f + 1.0f, var8 * 0.6f, var10);
                this.renderPistonRodSN(par2 + 0.375f, par2 + 0.625f, par3 + 0.375f, par3 + 0.375f, par4 - 0.25f + 1.0f - var9, par4 - 0.25f + 1.0f, var8 * 0.5f, var10);
                this.renderPistonRodSN(par2 + 0.625f, par2 + 0.375f, par3 + 0.625f, par3 + 0.625f, par4 - 0.25f + 1.0f - var9, par4 - 0.25f + 1.0f, var8, var10);
                break;
            }
            case 4: {
                this.uvRotateEast = 1;
                this.uvRotateWest = 2;
                this.uvRotateTop = 2;
                this.uvRotateBottom = 1;
                this.setRenderBounds(0.0, 0.0, 0.0, 0.25, 1.0, 1.0);
                this.renderStandardBlock(par1Block, par2, par3, par4);
                this.renderPistonRodEW(par2 + 0.25f, par2 + 0.25f + var9, par3 + 0.375f, par3 + 0.375f, par4 + 0.625f, par4 + 0.375f, var8 * 0.5f, var10);
                this.renderPistonRodEW(par2 + 0.25f, par2 + 0.25f + var9, par3 + 0.625f, par3 + 0.625f, par4 + 0.375f, par4 + 0.625f, var8, var10);
                this.renderPistonRodEW(par2 + 0.25f, par2 + 0.25f + var9, par3 + 0.375f, par3 + 0.625f, par4 + 0.375f, par4 + 0.375f, var8 * 0.6f, var10);
                this.renderPistonRodEW(par2 + 0.25f, par2 + 0.25f + var9, par3 + 0.625f, par3 + 0.375f, par4 + 0.625f, par4 + 0.625f, var8 * 0.6f, var10);
                break;
            }
            case 5: {
                this.uvRotateEast = 2;
                this.uvRotateWest = 1;
                this.uvRotateTop = 1;
                this.uvRotateBottom = 2;
                this.setRenderBounds(0.75, 0.0, 0.0, 1.0, 1.0, 1.0);
                this.renderStandardBlock(par1Block, par2, par3, par4);
                this.renderPistonRodEW(par2 - 0.25f + 1.0f - var9, par2 - 0.25f + 1.0f, par3 + 0.375f, par3 + 0.375f, par4 + 0.625f, par4 + 0.375f, var8 * 0.5f, var10);
                this.renderPistonRodEW(par2 - 0.25f + 1.0f - var9, par2 - 0.25f + 1.0f, par3 + 0.625f, par3 + 0.625f, par4 + 0.375f, par4 + 0.625f, var8, var10);
                this.renderPistonRodEW(par2 - 0.25f + 1.0f - var9, par2 - 0.25f + 1.0f, par3 + 0.375f, par3 + 0.625f, par4 + 0.375f, par4 + 0.375f, var8 * 0.6f, var10);
                this.renderPistonRodEW(par2 - 0.25f + 1.0f - var9, par2 - 0.25f + 1.0f, par3 + 0.625f, par3 + 0.375f, par4 + 0.625f, par4 + 0.625f, var8 * 0.6f, var10);
                break;
            }
        }
        this.uvRotateEast = 0;
        this.uvRotateWest = 0;
        this.uvRotateSouth = 0;
        this.uvRotateNorth = 0;
        this.uvRotateTop = 0;
        this.uvRotateBottom = 0;
        this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
        return true;
    }
    
    public boolean renderBlockLever(final Block par1Block, final int par2, final int par3, final int par4) {
        final int var5 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        final int var6 = var5 & 0x7;
        final boolean var7 = (var5 & 0x8) > 0;
        final Tessellator var8 = Tessellator.instance;
        final boolean var9 = this.hasOverrideBlockTexture();
        if (!var9) {
            this.setOverrideBlockTexture(this.getBlockIcon(Block.cobblestone));
        }
        final float var10 = 0.25f;
        final float var11 = 0.1875f;
        final float var12 = 0.1875f;
        if (var6 == 5) {
            this.setRenderBounds(0.5f - var11, 0.0, 0.5f - var10, 0.5f + var11, var12, 0.5f + var10);
        }
        else if (var6 == 6) {
            this.setRenderBounds(0.5f - var10, 0.0, 0.5f - var11, 0.5f + var10, var12, 0.5f + var11);
        }
        else if (var6 == 4) {
            this.setRenderBounds(0.5f - var11, 0.5f - var10, 1.0f - var12, 0.5f + var11, 0.5f + var10, 1.0);
        }
        else if (var6 == 3) {
            this.setRenderBounds(0.5f - var11, 0.5f - var10, 0.0, 0.5f + var11, 0.5f + var10, var12);
        }
        else if (var6 == 2) {
            this.setRenderBounds(1.0f - var12, 0.5f - var10, 0.5f - var11, 1.0, 0.5f + var10, 0.5f + var11);
        }
        else if (var6 == 1) {
            this.setRenderBounds(0.0, 0.5f - var10, 0.5f - var11, var12, 0.5f + var10, 0.5f + var11);
        }
        else if (var6 == 0) {
            this.setRenderBounds(0.5f - var10, 1.0f - var12, 0.5f - var11, 0.5f + var10, 1.0, 0.5f + var11);
        }
        else if (var6 == 7) {
            this.setRenderBounds(0.5f - var11, 1.0f - var12, 0.5f - var10, 0.5f + var11, 1.0, 0.5f + var10);
        }
        this.renderStandardBlock(par1Block, par2, par3, par4);
        if (!var9) {
            this.clearOverrideBlockTexture();
        }
        var8.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        float var13 = 1.0f;
        if (Block.lightValue[par1Block.blockID] > 0) {
            var13 = 1.0f;
        }
        var8.setColorOpaque_F(var13, var13, var13);
        Icon var14 = this.getBlockIconFromSide(par1Block, 0);
        if (this.hasOverrideBlockTexture()) {
            var14 = this.overrideBlockTexture;
        }
        double var15 = var14.getMinU();
        double var16 = var14.getMinV();
        double var17 = var14.getMaxU();
        double var18 = var14.getMaxV();
        final Vec3[] var19 = new Vec3[8];
        final float var20 = 0.0625f;
        final float var21 = 0.0625f;
        final float var22 = 0.625f;
        var19[0] = this.blockAccess.getWorldVec3Pool().getVecFromPool(-var20, 0.0, -var21);
        var19[1] = this.blockAccess.getWorldVec3Pool().getVecFromPool(var20, 0.0, -var21);
        var19[2] = this.blockAccess.getWorldVec3Pool().getVecFromPool(var20, 0.0, var21);
        var19[3] = this.blockAccess.getWorldVec3Pool().getVecFromPool(-var20, 0.0, var21);
        var19[4] = this.blockAccess.getWorldVec3Pool().getVecFromPool(-var20, var22, -var21);
        var19[5] = this.blockAccess.getWorldVec3Pool().getVecFromPool(var20, var22, -var21);
        var19[6] = this.blockAccess.getWorldVec3Pool().getVecFromPool(var20, var22, var21);
        var19[7] = this.blockAccess.getWorldVec3Pool().getVecFromPool(-var20, var22, var21);
        for (int var23 = 0; var23 < 8; ++var23) {
            if (var7) {
                final Vec3 vec3 = var19[var23];
                vec3.zCoord -= 0.0625;
                var19[var23].rotateAroundX(0.69813174f);
            }
            else {
                final Vec3 vec4 = var19[var23];
                vec4.zCoord += 0.0625;
                var19[var23].rotateAroundX(-0.69813174f);
            }
            if (var6 == 0 || var6 == 7) {
                var19[var23].rotateAroundZ(3.1415927f);
            }
            if (var6 == 6 || var6 == 0) {
                var19[var23].rotateAroundY(1.5707964f);
            }
            if (var6 > 0 && var6 < 5) {
                final Vec3 vec5 = var19[var23];
                vec5.yCoord -= 0.375;
                var19[var23].rotateAroundX(1.5707964f);
                if (var6 == 4) {
                    var19[var23].rotateAroundY(0.0f);
                }
                if (var6 == 3) {
                    var19[var23].rotateAroundY(3.1415927f);
                }
                if (var6 == 2) {
                    var19[var23].rotateAroundY(1.5707964f);
                }
                if (var6 == 1) {
                    var19[var23].rotateAroundY(-1.5707964f);
                }
                final Vec3 vec6 = var19[var23];
                vec6.xCoord += par2 + 0.5;
                final Vec3 vec7 = var19[var23];
                vec7.yCoord += par3 + 0.5f;
                final Vec3 vec8 = var19[var23];
                vec8.zCoord += par4 + 0.5;
            }
            else if (var6 != 0 && var6 != 7) {
                final Vec3 vec9 = var19[var23];
                vec9.xCoord += par2 + 0.5;
                final Vec3 vec10 = var19[var23];
                vec10.yCoord += par3 + 0.125f;
                final Vec3 vec11 = var19[var23];
                vec11.zCoord += par4 + 0.5;
            }
            else {
                final Vec3 vec12 = var19[var23];
                vec12.xCoord += par2 + 0.5;
                final Vec3 vec13 = var19[var23];
                vec13.yCoord += par3 + 0.875f;
                final Vec3 vec14 = var19[var23];
                vec14.zCoord += par4 + 0.5;
            }
        }
        Vec3 var24 = null;
        Vec3 var25 = null;
        Vec3 var26 = null;
        Vec3 var27 = null;
        for (int var28 = 0; var28 < 6; ++var28) {
            if (var28 == 0) {
                var15 = var14.getInterpolatedU(7.0);
                var16 = var14.getInterpolatedV(6.0);
                var17 = var14.getInterpolatedU(9.0);
                var18 = var14.getInterpolatedV(8.0);
            }
            else if (var28 == 2) {
                var15 = var14.getInterpolatedU(7.0);
                var16 = var14.getInterpolatedV(6.0);
                var17 = var14.getInterpolatedU(9.0);
                var18 = var14.getMaxV();
            }
            if (var28 == 0) {
                var24 = var19[0];
                var25 = var19[1];
                var26 = var19[2];
                var27 = var19[3];
            }
            else if (var28 == 1) {
                var24 = var19[7];
                var25 = var19[6];
                var26 = var19[5];
                var27 = var19[4];
            }
            else if (var28 == 2) {
                var24 = var19[1];
                var25 = var19[0];
                var26 = var19[4];
                var27 = var19[5];
            }
            else if (var28 == 3) {
                var24 = var19[2];
                var25 = var19[1];
                var26 = var19[5];
                var27 = var19[6];
            }
            else if (var28 == 4) {
                var24 = var19[3];
                var25 = var19[2];
                var26 = var19[6];
                var27 = var19[7];
            }
            else if (var28 == 5) {
                var24 = var19[0];
                var25 = var19[3];
                var26 = var19[7];
                var27 = var19[4];
            }
            var8.addVertexWithUV(var24.xCoord, var24.yCoord, var24.zCoord, var15, var18);
            var8.addVertexWithUV(var25.xCoord, var25.yCoord, var25.zCoord, var17, var18);
            var8.addVertexWithUV(var26.xCoord, var26.yCoord, var26.zCoord, var17, var16);
            var8.addVertexWithUV(var27.xCoord, var27.yCoord, var27.zCoord, var15, var16);
        }
        if (Config.isBetterSnow() && this.hasSnowNeighbours(par2, par3, par4)) {
            this.renderSnow(par2, par3, par4, Block.snow.maxY);
        }
        return true;
    }
    
    public boolean renderBlockTripWireSource(final Block par1Block, final int par2, final int par3, final int par4) {
        final Tessellator var5 = Tessellator.instance;
        final int var6 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        final int var7 = var6 & 0x3;
        final boolean var8 = (var6 & 0x4) == 0x4;
        final boolean var9 = (var6 & 0x8) == 0x8;
        final boolean var10 = !this.blockAccess.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4);
        final boolean var11 = this.hasOverrideBlockTexture();
        if (!var11) {
            this.setOverrideBlockTexture(this.getBlockIcon(Block.planks));
        }
        final float var12 = 0.25f;
        final float var13 = 0.125f;
        final float var14 = 0.125f;
        final float var15 = 0.3f - var12;
        final float var16 = 0.3f + var12;
        if (var7 == 2) {
            this.setRenderBounds(0.5f - var13, var15, 1.0f - var14, 0.5f + var13, var16, 1.0);
        }
        else if (var7 == 0) {
            this.setRenderBounds(0.5f - var13, var15, 0.0, 0.5f + var13, var16, var14);
        }
        else if (var7 == 1) {
            this.setRenderBounds(1.0f - var14, var15, 0.5f - var13, 1.0, var16, 0.5f + var13);
        }
        else if (var7 == 3) {
            this.setRenderBounds(0.0, var15, 0.5f - var13, var14, var16, 0.5f + var13);
        }
        this.renderStandardBlock(par1Block, par2, par3, par4);
        if (!var11) {
            this.clearOverrideBlockTexture();
        }
        var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        float var17 = 1.0f;
        if (Block.lightValue[par1Block.blockID] > 0) {
            var17 = 1.0f;
        }
        var5.setColorOpaque_F(var17, var17, var17);
        Icon var18 = this.getBlockIconFromSide(par1Block, 0);
        if (this.hasOverrideBlockTexture()) {
            var18 = this.overrideBlockTexture;
        }
        double var19 = var18.getMinU();
        double var20 = var18.getMinV();
        double var21 = var18.getMaxU();
        double var22 = var18.getMaxV();
        final Vec3[] var23 = new Vec3[8];
        final float var24 = 0.046875f;
        final float var25 = 0.046875f;
        final float var26 = 0.3125f;
        var23[0] = this.blockAccess.getWorldVec3Pool().getVecFromPool(-var24, 0.0, -var25);
        var23[1] = this.blockAccess.getWorldVec3Pool().getVecFromPool(var24, 0.0, -var25);
        var23[2] = this.blockAccess.getWorldVec3Pool().getVecFromPool(var24, 0.0, var25);
        var23[3] = this.blockAccess.getWorldVec3Pool().getVecFromPool(-var24, 0.0, var25);
        var23[4] = this.blockAccess.getWorldVec3Pool().getVecFromPool(-var24, var26, -var25);
        var23[5] = this.blockAccess.getWorldVec3Pool().getVecFromPool(var24, var26, -var25);
        var23[6] = this.blockAccess.getWorldVec3Pool().getVecFromPool(var24, var26, var25);
        var23[7] = this.blockAccess.getWorldVec3Pool().getVecFromPool(-var24, var26, var25);
        for (int var27 = 0; var27 < 8; ++var27) {
            final Vec3 vec3 = var23[var27];
            vec3.zCoord += 0.0625;
            if (var9) {
                var23[var27].rotateAroundX(0.5235988f);
                final Vec3 vec4 = var23[var27];
                vec4.yCoord -= 0.4375;
            }
            else if (var8) {
                var23[var27].rotateAroundX(0.08726647f);
                final Vec3 vec5 = var23[var27];
                vec5.yCoord -= 0.4375;
            }
            else {
                var23[var27].rotateAroundX(-0.69813174f);
                final Vec3 vec6 = var23[var27];
                vec6.yCoord -= 0.375;
            }
            var23[var27].rotateAroundX(1.5707964f);
            if (var7 == 2) {
                var23[var27].rotateAroundY(0.0f);
            }
            if (var7 == 0) {
                var23[var27].rotateAroundY(3.1415927f);
            }
            if (var7 == 1) {
                var23[var27].rotateAroundY(1.5707964f);
            }
            if (var7 == 3) {
                var23[var27].rotateAroundY(-1.5707964f);
            }
            final Vec3 vec7 = var23[var27];
            vec7.xCoord += par2 + 0.5;
            final Vec3 vec8 = var23[var27];
            vec8.yCoord += par3 + 0.3125f;
            final Vec3 vec9 = var23[var27];
            vec9.zCoord += par4 + 0.5;
        }
        Vec3 var28 = null;
        Vec3 var29 = null;
        Vec3 var30 = null;
        Vec3 var31 = null;
        final byte var32 = 7;
        final byte var33 = 9;
        final byte var34 = 9;
        final byte var35 = 16;
        for (int var36 = 0; var36 < 6; ++var36) {
            if (var36 == 0) {
                var28 = var23[0];
                var29 = var23[1];
                var30 = var23[2];
                var31 = var23[3];
                var19 = var18.getInterpolatedU(var32);
                var20 = var18.getInterpolatedV(var34);
                var21 = var18.getInterpolatedU(var33);
                var22 = var18.getInterpolatedV(var34 + 2);
            }
            else if (var36 == 1) {
                var28 = var23[7];
                var29 = var23[6];
                var30 = var23[5];
                var31 = var23[4];
            }
            else if (var36 == 2) {
                var28 = var23[1];
                var29 = var23[0];
                var30 = var23[4];
                var31 = var23[5];
                var19 = var18.getInterpolatedU(var32);
                var20 = var18.getInterpolatedV(var34);
                var21 = var18.getInterpolatedU(var33);
                var22 = var18.getInterpolatedV(var35);
            }
            else if (var36 == 3) {
                var28 = var23[2];
                var29 = var23[1];
                var30 = var23[5];
                var31 = var23[6];
            }
            else if (var36 == 4) {
                var28 = var23[3];
                var29 = var23[2];
                var30 = var23[6];
                var31 = var23[7];
            }
            else if (var36 == 5) {
                var28 = var23[0];
                var29 = var23[3];
                var30 = var23[7];
                var31 = var23[4];
            }
            var5.addVertexWithUV(var28.xCoord, var28.yCoord, var28.zCoord, var19, var22);
            var5.addVertexWithUV(var29.xCoord, var29.yCoord, var29.zCoord, var21, var22);
            var5.addVertexWithUV(var30.xCoord, var30.yCoord, var30.zCoord, var21, var20);
            var5.addVertexWithUV(var31.xCoord, var31.yCoord, var31.zCoord, var19, var20);
        }
        final float var37 = 0.09375f;
        final float var38 = 0.09375f;
        final float var39 = 0.03125f;
        var23[0] = this.blockAccess.getWorldVec3Pool().getVecFromPool(-var37, 0.0, -var38);
        var23[1] = this.blockAccess.getWorldVec3Pool().getVecFromPool(var37, 0.0, -var38);
        var23[2] = this.blockAccess.getWorldVec3Pool().getVecFromPool(var37, 0.0, var38);
        var23[3] = this.blockAccess.getWorldVec3Pool().getVecFromPool(-var37, 0.0, var38);
        var23[4] = this.blockAccess.getWorldVec3Pool().getVecFromPool(-var37, var39, -var38);
        var23[5] = this.blockAccess.getWorldVec3Pool().getVecFromPool(var37, var39, -var38);
        var23[6] = this.blockAccess.getWorldVec3Pool().getVecFromPool(var37, var39, var38);
        var23[7] = this.blockAccess.getWorldVec3Pool().getVecFromPool(-var37, var39, var38);
        for (int var40 = 0; var40 < 8; ++var40) {
            final Vec3 vec10 = var23[var40];
            vec10.zCoord += 0.21875;
            if (var9) {
                final Vec3 vec11 = var23[var40];
                vec11.yCoord -= 0.09375;
                final Vec3 vec12 = var23[var40];
                vec12.zCoord -= 0.1625;
                var23[var40].rotateAroundX(0.0f);
            }
            else if (var8) {
                final Vec3 vec13 = var23[var40];
                vec13.yCoord += 0.015625;
                final Vec3 vec14 = var23[var40];
                vec14.zCoord -= 0.171875;
                var23[var40].rotateAroundX(0.17453294f);
            }
            else {
                var23[var40].rotateAroundX(0.87266463f);
            }
            if (var7 == 2) {
                var23[var40].rotateAroundY(0.0f);
            }
            if (var7 == 0) {
                var23[var40].rotateAroundY(3.1415927f);
            }
            if (var7 == 1) {
                var23[var40].rotateAroundY(1.5707964f);
            }
            if (var7 == 3) {
                var23[var40].rotateAroundY(-1.5707964f);
            }
            final Vec3 vec15 = var23[var40];
            vec15.xCoord += par2 + 0.5;
            final Vec3 vec16 = var23[var40];
            vec16.yCoord += par3 + 0.3125f;
            final Vec3 vec17 = var23[var40];
            vec17.zCoord += par4 + 0.5;
        }
        final byte var41 = 5;
        final byte var42 = 11;
        final byte var43 = 3;
        final byte var44 = 9;
        for (int var45 = 0; var45 < 6; ++var45) {
            if (var45 == 0) {
                var28 = var23[0];
                var29 = var23[1];
                var30 = var23[2];
                var31 = var23[3];
                var19 = var18.getInterpolatedU(var41);
                var20 = var18.getInterpolatedV(var43);
                var21 = var18.getInterpolatedU(var42);
                var22 = var18.getInterpolatedV(var44);
            }
            else if (var45 == 1) {
                var28 = var23[7];
                var29 = var23[6];
                var30 = var23[5];
                var31 = var23[4];
            }
            else if (var45 == 2) {
                var28 = var23[1];
                var29 = var23[0];
                var30 = var23[4];
                var31 = var23[5];
                var19 = var18.getInterpolatedU(var41);
                var20 = var18.getInterpolatedV(var43);
                var21 = var18.getInterpolatedU(var42);
                var22 = var18.getInterpolatedV(var43 + 2);
            }
            else if (var45 == 3) {
                var28 = var23[2];
                var29 = var23[1];
                var30 = var23[5];
                var31 = var23[6];
            }
            else if (var45 == 4) {
                var28 = var23[3];
                var29 = var23[2];
                var30 = var23[6];
                var31 = var23[7];
            }
            else if (var45 == 5) {
                var28 = var23[0];
                var29 = var23[3];
                var30 = var23[7];
                var31 = var23[4];
            }
            var5.addVertexWithUV(var28.xCoord, var28.yCoord, var28.zCoord, var19, var22);
            var5.addVertexWithUV(var29.xCoord, var29.yCoord, var29.zCoord, var21, var22);
            var5.addVertexWithUV(var30.xCoord, var30.yCoord, var30.zCoord, var21, var20);
            var5.addVertexWithUV(var31.xCoord, var31.yCoord, var31.zCoord, var19, var20);
        }
        if (var8) {
            final double var46 = var23[0].yCoord;
            final float var47 = 0.03125f;
            final float var48 = 0.5f - var47 / 2.0f;
            final float var49 = var48 + var47;
            final Icon var50 = this.getBlockIcon(Block.tripWire);
            final double var51 = var18.getMinU();
            final double var52 = var18.getInterpolatedV(var8 ? 2.0 : 0.0);
            final double var53 = var18.getMaxU();
            final double var54 = var18.getInterpolatedV(var8 ? 4.0 : 2.0);
            final double var55 = (var10 ? 3.5f : 1.5f) / 16.0;
            var17 = par1Block.getBlockBrightness(this.blockAccess, par2, par3, par4) * 0.75f;
            var5.setColorOpaque_F(var17, var17, var17);
            if (var7 == 2) {
                var5.addVertexWithUV(par2 + var48, par3 + var55, par4 + 0.25, var51, var52);
                var5.addVertexWithUV(par2 + var49, par3 + var55, par4 + 0.25, var51, var54);
                var5.addVertexWithUV(par2 + var49, par3 + var55, par4, var53, var54);
                var5.addVertexWithUV(par2 + var48, par3 + var55, par4, var53, var52);
                var5.addVertexWithUV(par2 + var48, var46, par4 + 0.5, var51, var52);
                var5.addVertexWithUV(par2 + var49, var46, par4 + 0.5, var51, var54);
                var5.addVertexWithUV(par2 + var49, par3 + var55, par4 + 0.25, var53, var54);
                var5.addVertexWithUV(par2 + var48, par3 + var55, par4 + 0.25, var53, var52);
            }
            else if (var7 == 0) {
                var5.addVertexWithUV(par2 + var48, par3 + var55, par4 + 0.75, var51, var52);
                var5.addVertexWithUV(par2 + var49, par3 + var55, par4 + 0.75, var51, var54);
                var5.addVertexWithUV(par2 + var49, var46, par4 + 0.5, var53, var54);
                var5.addVertexWithUV(par2 + var48, var46, par4 + 0.5, var53, var52);
                var5.addVertexWithUV(par2 + var48, par3 + var55, par4 + 1, var51, var52);
                var5.addVertexWithUV(par2 + var49, par3 + var55, par4 + 1, var51, var54);
                var5.addVertexWithUV(par2 + var49, par3 + var55, par4 + 0.75, var53, var54);
                var5.addVertexWithUV(par2 + var48, par3 + var55, par4 + 0.75, var53, var52);
            }
            else if (var7 == 1) {
                var5.addVertexWithUV(par2, par3 + var55, par4 + var49, var51, var54);
                var5.addVertexWithUV(par2 + 0.25, par3 + var55, par4 + var49, var53, var54);
                var5.addVertexWithUV(par2 + 0.25, par3 + var55, par4 + var48, var53, var52);
                var5.addVertexWithUV(par2, par3 + var55, par4 + var48, var51, var52);
                var5.addVertexWithUV(par2 + 0.25, par3 + var55, par4 + var49, var51, var54);
                var5.addVertexWithUV(par2 + 0.5, var46, par4 + var49, var53, var54);
                var5.addVertexWithUV(par2 + 0.5, var46, par4 + var48, var53, var52);
                var5.addVertexWithUV(par2 + 0.25, par3 + var55, par4 + var48, var51, var52);
            }
            else {
                var5.addVertexWithUV(par2 + 0.5, var46, par4 + var49, var51, var54);
                var5.addVertexWithUV(par2 + 0.75, par3 + var55, par4 + var49, var53, var54);
                var5.addVertexWithUV(par2 + 0.75, par3 + var55, par4 + var48, var53, var52);
                var5.addVertexWithUV(par2 + 0.5, var46, par4 + var48, var51, var52);
                var5.addVertexWithUV(par2 + 0.75, par3 + var55, par4 + var49, var51, var54);
                var5.addVertexWithUV(par2 + 1, par3 + var55, par4 + var49, var53, var54);
                var5.addVertexWithUV(par2 + 1, par3 + var55, par4 + var48, var53, var52);
                var5.addVertexWithUV(par2 + 0.75, par3 + var55, par4 + var48, var51, var52);
            }
        }
        return true;
    }
    
    public boolean renderBlockTripWire(final Block par1Block, final int par2, final int par3, final int par4) {
        final Tessellator var5 = Tessellator.instance;
        Icon var6 = this.getBlockIconFromSide(par1Block, 0);
        final int var7 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        final boolean var8 = (var7 & 0x4) == 0x4;
        final boolean var9 = (var7 & 0x2) == 0x2;
        if (this.hasOverrideBlockTexture()) {
            var6 = this.overrideBlockTexture;
        }
        var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        final float var10 = par1Block.getBlockBrightness(this.blockAccess, par2, par3, par4) * 0.75f;
        var5.setColorOpaque_F(var10, var10, var10);
        final double var11 = var6.getMinU();
        final double var12 = var6.getInterpolatedV(var8 ? 2.0 : 0.0);
        final double var13 = var6.getMaxU();
        final double var14 = var6.getInterpolatedV(var8 ? 4.0 : 2.0);
        final double var15 = (var9 ? 3.5f : 1.5f) / 16.0;
        final boolean var16 = BlockTripWire.func_72148_a(this.blockAccess, par2, par3, par4, var7, 1);
        final boolean var17 = BlockTripWire.func_72148_a(this.blockAccess, par2, par3, par4, var7, 3);
        boolean var18 = BlockTripWire.func_72148_a(this.blockAccess, par2, par3, par4, var7, 2);
        boolean var19 = BlockTripWire.func_72148_a(this.blockAccess, par2, par3, par4, var7, 0);
        final float var20 = 0.03125f;
        final float var21 = 0.5f - var20 / 2.0f;
        final float var22 = var21 + var20;
        if (!var18 && !var17 && !var19 && !var16) {
            var18 = true;
            var19 = true;
        }
        if (var18) {
            var5.addVertexWithUV(par2 + var21, par3 + var15, par4 + 0.25, var11, var12);
            var5.addVertexWithUV(par2 + var22, par3 + var15, par4 + 0.25, var11, var14);
            var5.addVertexWithUV(par2 + var22, par3 + var15, par4, var13, var14);
            var5.addVertexWithUV(par2 + var21, par3 + var15, par4, var13, var12);
            var5.addVertexWithUV(par2 + var21, par3 + var15, par4, var13, var12);
            var5.addVertexWithUV(par2 + var22, par3 + var15, par4, var13, var14);
            var5.addVertexWithUV(par2 + var22, par3 + var15, par4 + 0.25, var11, var14);
            var5.addVertexWithUV(par2 + var21, par3 + var15, par4 + 0.25, var11, var12);
        }
        if (var18 || (var19 && !var17 && !var16)) {
            var5.addVertexWithUV(par2 + var21, par3 + var15, par4 + 0.5, var11, var12);
            var5.addVertexWithUV(par2 + var22, par3 + var15, par4 + 0.5, var11, var14);
            var5.addVertexWithUV(par2 + var22, par3 + var15, par4 + 0.25, var13, var14);
            var5.addVertexWithUV(par2 + var21, par3 + var15, par4 + 0.25, var13, var12);
            var5.addVertexWithUV(par2 + var21, par3 + var15, par4 + 0.25, var13, var12);
            var5.addVertexWithUV(par2 + var22, par3 + var15, par4 + 0.25, var13, var14);
            var5.addVertexWithUV(par2 + var22, par3 + var15, par4 + 0.5, var11, var14);
            var5.addVertexWithUV(par2 + var21, par3 + var15, par4 + 0.5, var11, var12);
        }
        if (var19 || (var18 && !var17 && !var16)) {
            var5.addVertexWithUV(par2 + var21, par3 + var15, par4 + 0.75, var11, var12);
            var5.addVertexWithUV(par2 + var22, par3 + var15, par4 + 0.75, var11, var14);
            var5.addVertexWithUV(par2 + var22, par3 + var15, par4 + 0.5, var13, var14);
            var5.addVertexWithUV(par2 + var21, par3 + var15, par4 + 0.5, var13, var12);
            var5.addVertexWithUV(par2 + var21, par3 + var15, par4 + 0.5, var13, var12);
            var5.addVertexWithUV(par2 + var22, par3 + var15, par4 + 0.5, var13, var14);
            var5.addVertexWithUV(par2 + var22, par3 + var15, par4 + 0.75, var11, var14);
            var5.addVertexWithUV(par2 + var21, par3 + var15, par4 + 0.75, var11, var12);
        }
        if (var19) {
            var5.addVertexWithUV(par2 + var21, par3 + var15, par4 + 1, var11, var12);
            var5.addVertexWithUV(par2 + var22, par3 + var15, par4 + 1, var11, var14);
            var5.addVertexWithUV(par2 + var22, par3 + var15, par4 + 0.75, var13, var14);
            var5.addVertexWithUV(par2 + var21, par3 + var15, par4 + 0.75, var13, var12);
            var5.addVertexWithUV(par2 + var21, par3 + var15, par4 + 0.75, var13, var12);
            var5.addVertexWithUV(par2 + var22, par3 + var15, par4 + 0.75, var13, var14);
            var5.addVertexWithUV(par2 + var22, par3 + var15, par4 + 1, var11, var14);
            var5.addVertexWithUV(par2 + var21, par3 + var15, par4 + 1, var11, var12);
        }
        if (var16) {
            var5.addVertexWithUV(par2, par3 + var15, par4 + var22, var11, var14);
            var5.addVertexWithUV(par2 + 0.25, par3 + var15, par4 + var22, var13, var14);
            var5.addVertexWithUV(par2 + 0.25, par3 + var15, par4 + var21, var13, var12);
            var5.addVertexWithUV(par2, par3 + var15, par4 + var21, var11, var12);
            var5.addVertexWithUV(par2, par3 + var15, par4 + var21, var11, var12);
            var5.addVertexWithUV(par2 + 0.25, par3 + var15, par4 + var21, var13, var12);
            var5.addVertexWithUV(par2 + 0.25, par3 + var15, par4 + var22, var13, var14);
            var5.addVertexWithUV(par2, par3 + var15, par4 + var22, var11, var14);
        }
        if (var16 || (var17 && !var18 && !var19)) {
            var5.addVertexWithUV(par2 + 0.25, par3 + var15, par4 + var22, var11, var14);
            var5.addVertexWithUV(par2 + 0.5, par3 + var15, par4 + var22, var13, var14);
            var5.addVertexWithUV(par2 + 0.5, par3 + var15, par4 + var21, var13, var12);
            var5.addVertexWithUV(par2 + 0.25, par3 + var15, par4 + var21, var11, var12);
            var5.addVertexWithUV(par2 + 0.25, par3 + var15, par4 + var21, var11, var12);
            var5.addVertexWithUV(par2 + 0.5, par3 + var15, par4 + var21, var13, var12);
            var5.addVertexWithUV(par2 + 0.5, par3 + var15, par4 + var22, var13, var14);
            var5.addVertexWithUV(par2 + 0.25, par3 + var15, par4 + var22, var11, var14);
        }
        if (var17 || (var16 && !var18 && !var19)) {
            var5.addVertexWithUV(par2 + 0.5, par3 + var15, par4 + var22, var11, var14);
            var5.addVertexWithUV(par2 + 0.75, par3 + var15, par4 + var22, var13, var14);
            var5.addVertexWithUV(par2 + 0.75, par3 + var15, par4 + var21, var13, var12);
            var5.addVertexWithUV(par2 + 0.5, par3 + var15, par4 + var21, var11, var12);
            var5.addVertexWithUV(par2 + 0.5, par3 + var15, par4 + var21, var11, var12);
            var5.addVertexWithUV(par2 + 0.75, par3 + var15, par4 + var21, var13, var12);
            var5.addVertexWithUV(par2 + 0.75, par3 + var15, par4 + var22, var13, var14);
            var5.addVertexWithUV(par2 + 0.5, par3 + var15, par4 + var22, var11, var14);
        }
        if (var17) {
            var5.addVertexWithUV(par2 + 0.75, par3 + var15, par4 + var22, var11, var14);
            var5.addVertexWithUV(par2 + 1, par3 + var15, par4 + var22, var13, var14);
            var5.addVertexWithUV(par2 + 1, par3 + var15, par4 + var21, var13, var12);
            var5.addVertexWithUV(par2 + 0.75, par3 + var15, par4 + var21, var11, var12);
            var5.addVertexWithUV(par2 + 0.75, par3 + var15, par4 + var21, var11, var12);
            var5.addVertexWithUV(par2 + 1, par3 + var15, par4 + var21, var13, var12);
            var5.addVertexWithUV(par2 + 1, par3 + var15, par4 + var22, var13, var14);
            var5.addVertexWithUV(par2 + 0.75, par3 + var15, par4 + var22, var11, var14);
        }
        return true;
    }
    
    public boolean renderBlockFire(final BlockFire par1BlockFire, final int par2, int par3, final int par4) {
        final Tessellator var5 = Tessellator.instance;
        final Icon var6 = par1BlockFire.func_94438_c(0);
        final Icon var7 = par1BlockFire.func_94438_c(1);
        Icon var8 = var6;
        if (this.hasOverrideBlockTexture()) {
            var8 = this.overrideBlockTexture;
        }
        var5.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        var5.setBrightness(par1BlockFire.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        double var9 = var8.getMinU();
        double var10 = var8.getMinV();
        double var11 = var8.getMaxU();
        double var12 = var8.getMaxV();
        float var13 = 1.4f;
        if (!this.blockAccess.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) && !Block.fire.canBlockCatchFire(this.blockAccess, par2, par3 - 1, par4)) {
            final float var14 = 0.2f;
            final float var15 = 0.0625f;
            if ((par2 + par3 + par4 & 0x1) == 0x1) {
                var9 = var7.getMinU();
                var10 = var7.getMinV();
                var11 = var7.getMaxU();
                var12 = var7.getMaxV();
            }
            if ((par2 / 2 + par3 / 2 + par4 / 2 & 0x1) == 0x1) {
                final double var16 = var11;
                var11 = var9;
                var9 = var16;
            }
            if (Block.fire.canBlockCatchFire(this.blockAccess, par2 - 1, par3, par4)) {
                var5.addVertexWithUV(par2 + var14, par3 + var13 + var15, par4 + 1, var11, var10);
                var5.addVertexWithUV(par2 + 0, par3 + 0 + var15, par4 + 1, var11, var12);
                var5.addVertexWithUV(par2 + 0, par3 + 0 + var15, par4 + 0, var9, var12);
                var5.addVertexWithUV(par2 + var14, par3 + var13 + var15, par4 + 0, var9, var10);
                var5.addVertexWithUV(par2 + var14, par3 + var13 + var15, par4 + 0, var9, var10);
                var5.addVertexWithUV(par2 + 0, par3 + 0 + var15, par4 + 0, var9, var12);
                var5.addVertexWithUV(par2 + 0, par3 + 0 + var15, par4 + 1, var11, var12);
                var5.addVertexWithUV(par2 + var14, par3 + var13 + var15, par4 + 1, var11, var10);
            }
            if (Block.fire.canBlockCatchFire(this.blockAccess, par2 + 1, par3, par4)) {
                var5.addVertexWithUV(par2 + 1 - var14, par3 + var13 + var15, par4 + 0, var9, var10);
                var5.addVertexWithUV(par2 + 1 - 0, par3 + 0 + var15, par4 + 0, var9, var12);
                var5.addVertexWithUV(par2 + 1 - 0, par3 + 0 + var15, par4 + 1, var11, var12);
                var5.addVertexWithUV(par2 + 1 - var14, par3 + var13 + var15, par4 + 1, var11, var10);
                var5.addVertexWithUV(par2 + 1 - var14, par3 + var13 + var15, par4 + 1, var11, var10);
                var5.addVertexWithUV(par2 + 1 - 0, par3 + 0 + var15, par4 + 1, var11, var12);
                var5.addVertexWithUV(par2 + 1 - 0, par3 + 0 + var15, par4 + 0, var9, var12);
                var5.addVertexWithUV(par2 + 1 - var14, par3 + var13 + var15, par4 + 0, var9, var10);
            }
            if (Block.fire.canBlockCatchFire(this.blockAccess, par2, par3, par4 - 1)) {
                var5.addVertexWithUV(par2 + 0, par3 + var13 + var15, par4 + var14, var11, var10);
                var5.addVertexWithUV(par2 + 0, par3 + 0 + var15, par4 + 0, var11, var12);
                var5.addVertexWithUV(par2 + 1, par3 + 0 + var15, par4 + 0, var9, var12);
                var5.addVertexWithUV(par2 + 1, par3 + var13 + var15, par4 + var14, var9, var10);
                var5.addVertexWithUV(par2 + 1, par3 + var13 + var15, par4 + var14, var9, var10);
                var5.addVertexWithUV(par2 + 1, par3 + 0 + var15, par4 + 0, var9, var12);
                var5.addVertexWithUV(par2 + 0, par3 + 0 + var15, par4 + 0, var11, var12);
                var5.addVertexWithUV(par2 + 0, par3 + var13 + var15, par4 + var14, var11, var10);
            }
            if (Block.fire.canBlockCatchFire(this.blockAccess, par2, par3, par4 + 1)) {
                var5.addVertexWithUV(par2 + 1, par3 + var13 + var15, par4 + 1 - var14, var9, var10);
                var5.addVertexWithUV(par2 + 1, par3 + 0 + var15, par4 + 1 - 0, var9, var12);
                var5.addVertexWithUV(par2 + 0, par3 + 0 + var15, par4 + 1 - 0, var11, var12);
                var5.addVertexWithUV(par2 + 0, par3 + var13 + var15, par4 + 1 - var14, var11, var10);
                var5.addVertexWithUV(par2 + 0, par3 + var13 + var15, par4 + 1 - var14, var11, var10);
                var5.addVertexWithUV(par2 + 0, par3 + 0 + var15, par4 + 1 - 0, var11, var12);
                var5.addVertexWithUV(par2 + 1, par3 + 0 + var15, par4 + 1 - 0, var9, var12);
                var5.addVertexWithUV(par2 + 1, par3 + var13 + var15, par4 + 1 - var14, var9, var10);
            }
            if (Block.fire.canBlockCatchFire(this.blockAccess, par2, par3 + 1, par4)) {
                final double var16 = par2 + 0.5 + 0.5;
                final double var17 = par2 + 0.5 - 0.5;
                final double var18 = par4 + 0.5 + 0.5;
                final double var19 = par4 + 0.5 - 0.5;
                final double var20 = par2 + 0.5 - 0.5;
                final double var21 = par2 + 0.5 + 0.5;
                final double var22 = par4 + 0.5 - 0.5;
                final double var23 = par4 + 0.5 + 0.5;
                var9 = var6.getMinU();
                var10 = var6.getMinV();
                var11 = var6.getMaxU();
                var12 = var6.getMaxV();
                ++par3;
                var13 = -0.2f;
                if ((par2 + par3 + par4 & 0x1) == 0x0) {
                    var5.addVertexWithUV(var20, par3 + var13, par4 + 0, var11, var10);
                    var5.addVertexWithUV(var16, par3 + 0, par4 + 0, var11, var12);
                    var5.addVertexWithUV(var16, par3 + 0, par4 + 1, var9, var12);
                    var5.addVertexWithUV(var20, par3 + var13, par4 + 1, var9, var10);
                    var9 = var7.getMinU();
                    var10 = var7.getMinV();
                    var11 = var7.getMaxU();
                    var12 = var7.getMaxV();
                    var5.addVertexWithUV(var21, par3 + var13, par4 + 1, var11, var10);
                    var5.addVertexWithUV(var17, par3 + 0, par4 + 1, var11, var12);
                    var5.addVertexWithUV(var17, par3 + 0, par4 + 0, var9, var12);
                    var5.addVertexWithUV(var21, par3 + var13, par4 + 0, var9, var10);
                }
                else {
                    var5.addVertexWithUV(par2 + 0, par3 + var13, var23, var11, var10);
                    var5.addVertexWithUV(par2 + 0, par3 + 0, var19, var11, var12);
                    var5.addVertexWithUV(par2 + 1, par3 + 0, var19, var9, var12);
                    var5.addVertexWithUV(par2 + 1, par3 + var13, var23, var9, var10);
                    var9 = var7.getMinU();
                    var10 = var7.getMinV();
                    var11 = var7.getMaxU();
                    var12 = var7.getMaxV();
                    var5.addVertexWithUV(par2 + 1, par3 + var13, var22, var11, var10);
                    var5.addVertexWithUV(par2 + 1, par3 + 0, var18, var11, var12);
                    var5.addVertexWithUV(par2 + 0, par3 + 0, var18, var9, var12);
                    var5.addVertexWithUV(par2 + 0, par3 + var13, var22, var9, var10);
                }
            }
        }
        else {
            double var24 = par2 + 0.5 + 0.2;
            double var16 = par2 + 0.5 - 0.2;
            double var17 = par4 + 0.5 + 0.2;
            double var18 = par4 + 0.5 - 0.2;
            double var19 = par2 + 0.5 - 0.3;
            double var20 = par2 + 0.5 + 0.3;
            double var21 = par4 + 0.5 - 0.3;
            double var22 = par4 + 0.5 + 0.3;
            var5.addVertexWithUV(var19, par3 + var13, par4 + 1, var11, var10);
            var5.addVertexWithUV(var24, par3 + 0, par4 + 1, var11, var12);
            var5.addVertexWithUV(var24, par3 + 0, par4 + 0, var9, var12);
            var5.addVertexWithUV(var19, par3 + var13, par4 + 0, var9, var10);
            var5.addVertexWithUV(var20, par3 + var13, par4 + 0, var11, var10);
            var5.addVertexWithUV(var16, par3 + 0, par4 + 0, var11, var12);
            var5.addVertexWithUV(var16, par3 + 0, par4 + 1, var9, var12);
            var5.addVertexWithUV(var20, par3 + var13, par4 + 1, var9, var10);
            var9 = var7.getMinU();
            var10 = var7.getMinV();
            var11 = var7.getMaxU();
            var12 = var7.getMaxV();
            var5.addVertexWithUV(par2 + 1, par3 + var13, var22, var11, var10);
            var5.addVertexWithUV(par2 + 1, par3 + 0, var18, var11, var12);
            var5.addVertexWithUV(par2 + 0, par3 + 0, var18, var9, var12);
            var5.addVertexWithUV(par2 + 0, par3 + var13, var22, var9, var10);
            var5.addVertexWithUV(par2 + 0, par3 + var13, var21, var11, var10);
            var5.addVertexWithUV(par2 + 0, par3 + 0, var17, var11, var12);
            var5.addVertexWithUV(par2 + 1, par3 + 0, var17, var9, var12);
            var5.addVertexWithUV(par2 + 1, par3 + var13, var21, var9, var10);
            var24 = par2 + 0.5 - 0.5;
            var16 = par2 + 0.5 + 0.5;
            var17 = par4 + 0.5 - 0.5;
            var18 = par4 + 0.5 + 0.5;
            var19 = par2 + 0.5 - 0.4;
            var20 = par2 + 0.5 + 0.4;
            var21 = par4 + 0.5 - 0.4;
            var22 = par4 + 0.5 + 0.4;
            var5.addVertexWithUV(var19, par3 + var13, par4 + 0, var9, var10);
            var5.addVertexWithUV(var24, par3 + 0, par4 + 0, var9, var12);
            var5.addVertexWithUV(var24, par3 + 0, par4 + 1, var11, var12);
            var5.addVertexWithUV(var19, par3 + var13, par4 + 1, var11, var10);
            var5.addVertexWithUV(var20, par3 + var13, par4 + 1, var9, var10);
            var5.addVertexWithUV(var16, par3 + 0, par4 + 1, var9, var12);
            var5.addVertexWithUV(var16, par3 + 0, par4 + 0, var11, var12);
            var5.addVertexWithUV(var20, par3 + var13, par4 + 0, var11, var10);
            var9 = var6.getMinU();
            var10 = var6.getMinV();
            var11 = var6.getMaxU();
            var12 = var6.getMaxV();
            var5.addVertexWithUV(par2 + 0, par3 + var13, var22, var9, var10);
            var5.addVertexWithUV(par2 + 0, par3 + 0, var18, var9, var12);
            var5.addVertexWithUV(par2 + 1, par3 + 0, var18, var11, var12);
            var5.addVertexWithUV(par2 + 1, par3 + var13, var22, var11, var10);
            var5.addVertexWithUV(par2 + 1, par3 + var13, var21, var9, var10);
            var5.addVertexWithUV(par2 + 1, par3 + 0, var17, var9, var12);
            var5.addVertexWithUV(par2 + 0, par3 + 0, var17, var11, var12);
            var5.addVertexWithUV(par2 + 0, par3 + var13, var21, var11, var10);
        }
        return true;
    }
    
    public boolean renderBlockRedstoneWire(final Block par1Block, final int par2, final int par3, final int par4) {
        final Tessellator var5 = Tessellator.instance;
        final int var6 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        final Icon var7 = BlockRedstoneWire.func_94409_b("redstoneDust_cross");
        final Icon var8 = BlockRedstoneWire.func_94409_b("redstoneDust_line");
        final Icon var9 = BlockRedstoneWire.func_94409_b("redstoneDust_cross_overlay");
        final Icon var10 = BlockRedstoneWire.func_94409_b("redstoneDust_line_overlay");
        var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        final float var11 = 1.0f;
        final float var12 = var6 / 15.0f;
        float var13 = var12 * 0.6f + 0.4f;
        if (var6 == 0) {
            var13 = 0.3f;
        }
        float var14 = var12 * var12 * 0.7f - 0.5f;
        float var15 = var12 * var12 * 0.6f - 0.7f;
        if (var14 < 0.0f) {
            var14 = 0.0f;
        }
        if (var15 < 0.0f) {
            var15 = 0.0f;
        }
        final int var16 = CustomColorizer.getRedstoneColor(var6);
        if (var16 != -1) {
            final int var17 = var16 >> 16 & 0xFF;
            final int var18 = var16 >> 8 & 0xFF;
            final int var19 = var16 & 0xFF;
            var13 = var17 / 255.0f;
            var14 = var18 / 255.0f;
            var15 = var19 / 255.0f;
        }
        var5.setColorOpaque_F(var13, var14, var15);
        boolean var20 = BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2 - 1, par3, par4, 1) || (!this.blockAccess.isBlockNormalCube(par2 - 1, par3, par4) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2 - 1, par3 - 1, par4, -1));
        boolean var21 = BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2 + 1, par3, par4, 3) || (!this.blockAccess.isBlockNormalCube(par2 + 1, par3, par4) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2 + 1, par3 - 1, par4, -1));
        boolean var22 = BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2, par3, par4 - 1, 2) || (!this.blockAccess.isBlockNormalCube(par2, par3, par4 - 1) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2, par3 - 1, par4 - 1, -1));
        boolean var23 = BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2, par3, par4 + 1, 0) || (!this.blockAccess.isBlockNormalCube(par2, par3, par4 + 1) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2, par3 - 1, par4 + 1, -1));
        if (!this.blockAccess.isBlockNormalCube(par2, par3 + 1, par4)) {
            if (this.blockAccess.isBlockNormalCube(par2 - 1, par3, par4) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2 - 1, par3 + 1, par4, -1)) {
                var20 = true;
            }
            if (this.blockAccess.isBlockNormalCube(par2 + 1, par3, par4) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2 + 1, par3 + 1, par4, -1)) {
                var21 = true;
            }
            if (this.blockAccess.isBlockNormalCube(par2, par3, par4 - 1) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2, par3 + 1, par4 - 1, -1)) {
                var22 = true;
            }
            if (this.blockAccess.isBlockNormalCube(par2, par3, par4 + 1) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2, par3 + 1, par4 + 1, -1)) {
                var23 = true;
            }
        }
        float var24 = par2 + 0;
        float var25 = par2 + 1;
        float var26 = par4 + 0;
        float var27 = par4 + 1;
        boolean var28 = false;
        if ((var20 || var21) && !var22 && !var23) {
            var28 = true;
        }
        if ((var22 || var23) && !var21 && !var20) {
            var28 = true;
        }
        if (!var28) {
            int var29 = 0;
            int var30 = 0;
            int var31 = 16;
            int var32 = 16;
            if (!var20) {
                var24 += 0.3125f;
            }
            if (!var20) {
                var29 += 5;
            }
            if (!var21) {
                var25 -= 0.3125f;
            }
            if (!var21) {
                var31 -= 5;
            }
            if (!var22) {
                var26 += 0.3125f;
            }
            if (!var22) {
                var30 += 5;
            }
            if (!var23) {
                var27 -= 0.3125f;
            }
            if (!var23) {
                var32 -= 5;
            }
            var5.addVertexWithUV(var25, par3 + 0.015625, var27, var7.getInterpolatedU(var31), var7.getInterpolatedV(var32));
            var5.addVertexWithUV(var25, par3 + 0.015625, var26, var7.getInterpolatedU(var31), var7.getInterpolatedV(var30));
            var5.addVertexWithUV(var24, par3 + 0.015625, var26, var7.getInterpolatedU(var29), var7.getInterpolatedV(var30));
            var5.addVertexWithUV(var24, par3 + 0.015625, var27, var7.getInterpolatedU(var29), var7.getInterpolatedV(var32));
            var5.setColorOpaque_F(var11, var11, var11);
            var5.addVertexWithUV(var25, par3 + 0.015625, var27, var9.getInterpolatedU(var31), var9.getInterpolatedV(var32));
            var5.addVertexWithUV(var25, par3 + 0.015625, var26, var9.getInterpolatedU(var31), var9.getInterpolatedV(var30));
            var5.addVertexWithUV(var24, par3 + 0.015625, var26, var9.getInterpolatedU(var29), var9.getInterpolatedV(var30));
            var5.addVertexWithUV(var24, par3 + 0.015625, var27, var9.getInterpolatedU(var29), var9.getInterpolatedV(var32));
        }
        else if (var28) {
            var5.addVertexWithUV(var25, par3 + 0.015625, var27, var8.getMaxU(), var8.getMaxV());
            var5.addVertexWithUV(var25, par3 + 0.015625, var26, var8.getMaxU(), var8.getMinV());
            var5.addVertexWithUV(var24, par3 + 0.015625, var26, var8.getMinU(), var8.getMinV());
            var5.addVertexWithUV(var24, par3 + 0.015625, var27, var8.getMinU(), var8.getMaxV());
            var5.setColorOpaque_F(var11, var11, var11);
            var5.addVertexWithUV(var25, par3 + 0.015625, var27, var10.getMaxU(), var10.getMaxV());
            var5.addVertexWithUV(var25, par3 + 0.015625, var26, var10.getMaxU(), var10.getMinV());
            var5.addVertexWithUV(var24, par3 + 0.015625, var26, var10.getMinU(), var10.getMinV());
            var5.addVertexWithUV(var24, par3 + 0.015625, var27, var10.getMinU(), var10.getMaxV());
        }
        else {
            var5.addVertexWithUV(var25, par3 + 0.015625, var27, var8.getMaxU(), var8.getMaxV());
            var5.addVertexWithUV(var25, par3 + 0.015625, var26, var8.getMinU(), var8.getMaxV());
            var5.addVertexWithUV(var24, par3 + 0.015625, var26, var8.getMinU(), var8.getMinV());
            var5.addVertexWithUV(var24, par3 + 0.015625, var27, var8.getMaxU(), var8.getMinV());
            var5.setColorOpaque_F(var11, var11, var11);
            var5.addVertexWithUV(var25, par3 + 0.015625, var27, var10.getMaxU(), var10.getMaxV());
            var5.addVertexWithUV(var25, par3 + 0.015625, var26, var10.getMinU(), var10.getMaxV());
            var5.addVertexWithUV(var24, par3 + 0.015625, var26, var10.getMinU(), var10.getMinV());
            var5.addVertexWithUV(var24, par3 + 0.015625, var27, var10.getMaxU(), var10.getMinV());
        }
        if (!this.blockAccess.isBlockNormalCube(par2, par3 + 1, par4)) {
            if (this.blockAccess.isBlockNormalCube(par2 - 1, par3, par4) && this.blockAccess.getBlockId(par2 - 1, par3 + 1, par4) == Block.redstoneWire.blockID) {
                var5.setColorOpaque_F(var11 * var13, var11 * var14, var11 * var15);
                var5.addVertexWithUV(par2 + 0.015625, par3 + 1 + 0.021875f, par4 + 1, var8.getMaxU(), var8.getMinV());
                var5.addVertexWithUV(par2 + 0.015625, par3 + 0, par4 + 1, var8.getMinU(), var8.getMinV());
                var5.addVertexWithUV(par2 + 0.015625, par3 + 0, par4 + 0, var8.getMinU(), var8.getMaxV());
                var5.addVertexWithUV(par2 + 0.015625, par3 + 1 + 0.021875f, par4 + 0, var8.getMaxU(), var8.getMaxV());
                var5.setColorOpaque_F(var11, var11, var11);
                var5.addVertexWithUV(par2 + 0.015625, par3 + 1 + 0.021875f, par4 + 1, var10.getMaxU(), var10.getMinV());
                var5.addVertexWithUV(par2 + 0.015625, par3 + 0, par4 + 1, var10.getMinU(), var10.getMinV());
                var5.addVertexWithUV(par2 + 0.015625, par3 + 0, par4 + 0, var10.getMinU(), var10.getMaxV());
                var5.addVertexWithUV(par2 + 0.015625, par3 + 1 + 0.021875f, par4 + 0, var10.getMaxU(), var10.getMaxV());
            }
            if (this.blockAccess.isBlockNormalCube(par2 + 1, par3, par4) && this.blockAccess.getBlockId(par2 + 1, par3 + 1, par4) == Block.redstoneWire.blockID) {
                var5.setColorOpaque_F(var11 * var13, var11 * var14, var11 * var15);
                var5.addVertexWithUV(par2 + 1 - 0.015625, par3 + 0, par4 + 1, var8.getMinU(), var8.getMaxV());
                var5.addVertexWithUV(par2 + 1 - 0.015625, par3 + 1 + 0.021875f, par4 + 1, var8.getMaxU(), var8.getMaxV());
                var5.addVertexWithUV(par2 + 1 - 0.015625, par3 + 1 + 0.021875f, par4 + 0, var8.getMaxU(), var8.getMinV());
                var5.addVertexWithUV(par2 + 1 - 0.015625, par3 + 0, par4 + 0, var8.getMinU(), var8.getMinV());
                var5.setColorOpaque_F(var11, var11, var11);
                var5.addVertexWithUV(par2 + 1 - 0.015625, par3 + 0, par4 + 1, var10.getMinU(), var10.getMaxV());
                var5.addVertexWithUV(par2 + 1 - 0.015625, par3 + 1 + 0.021875f, par4 + 1, var10.getMaxU(), var10.getMaxV());
                var5.addVertexWithUV(par2 + 1 - 0.015625, par3 + 1 + 0.021875f, par4 + 0, var10.getMaxU(), var10.getMinV());
                var5.addVertexWithUV(par2 + 1 - 0.015625, par3 + 0, par4 + 0, var10.getMinU(), var10.getMinV());
            }
            if (this.blockAccess.isBlockNormalCube(par2, par3, par4 - 1) && this.blockAccess.getBlockId(par2, par3 + 1, par4 - 1) == Block.redstoneWire.blockID) {
                var5.setColorOpaque_F(var11 * var13, var11 * var14, var11 * var15);
                var5.addVertexWithUV(par2 + 1, par3 + 0, par4 + 0.015625, var8.getMinU(), var8.getMaxV());
                var5.addVertexWithUV(par2 + 1, par3 + 1 + 0.021875f, par4 + 0.015625, var8.getMaxU(), var8.getMaxV());
                var5.addVertexWithUV(par2 + 0, par3 + 1 + 0.021875f, par4 + 0.015625, var8.getMaxU(), var8.getMinV());
                var5.addVertexWithUV(par2 + 0, par3 + 0, par4 + 0.015625, var8.getMinU(), var8.getMinV());
                var5.setColorOpaque_F(var11, var11, var11);
                var5.addVertexWithUV(par2 + 1, par3 + 0, par4 + 0.015625, var10.getMinU(), var10.getMaxV());
                var5.addVertexWithUV(par2 + 1, par3 + 1 + 0.021875f, par4 + 0.015625, var10.getMaxU(), var10.getMaxV());
                var5.addVertexWithUV(par2 + 0, par3 + 1 + 0.021875f, par4 + 0.015625, var10.getMaxU(), var10.getMinV());
                var5.addVertexWithUV(par2 + 0, par3 + 0, par4 + 0.015625, var10.getMinU(), var10.getMinV());
            }
            if (this.blockAccess.isBlockNormalCube(par2, par3, par4 + 1) && this.blockAccess.getBlockId(par2, par3 + 1, par4 + 1) == Block.redstoneWire.blockID) {
                var5.setColorOpaque_F(var11 * var13, var11 * var14, var11 * var15);
                var5.addVertexWithUV(par2 + 1, par3 + 1 + 0.021875f, par4 + 1 - 0.015625, var8.getMaxU(), var8.getMinV());
                var5.addVertexWithUV(par2 + 1, par3 + 0, par4 + 1 - 0.015625, var8.getMinU(), var8.getMinV());
                var5.addVertexWithUV(par2 + 0, par3 + 0, par4 + 1 - 0.015625, var8.getMinU(), var8.getMaxV());
                var5.addVertexWithUV(par2 + 0, par3 + 1 + 0.021875f, par4 + 1 - 0.015625, var8.getMaxU(), var8.getMaxV());
                var5.setColorOpaque_F(var11, var11, var11);
                var5.addVertexWithUV(par2 + 1, par3 + 1 + 0.021875f, par4 + 1 - 0.015625, var10.getMaxU(), var10.getMinV());
                var5.addVertexWithUV(par2 + 1, par3 + 0, par4 + 1 - 0.015625, var10.getMinU(), var10.getMinV());
                var5.addVertexWithUV(par2 + 0, par3 + 0, par4 + 1 - 0.015625, var10.getMinU(), var10.getMaxV());
                var5.addVertexWithUV(par2 + 0, par3 + 1 + 0.021875f, par4 + 1 - 0.015625, var10.getMaxU(), var10.getMaxV());
            }
        }
        if (Config.isBetterSnow() && this.hasSnowNeighbours(par2, par3, par4)) {
            this.renderSnow(par2, par3, par4, 0.01);
        }
        return true;
    }
    
    public boolean renderBlockMinecartTrack(final BlockRailBase par1BlockRailBase, final int par2, final int par3, final int par4) {
        final Tessellator var5 = Tessellator.instance;
        int var6 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        Icon var7 = this.getBlockIconFromSideAndMetadata(par1BlockRailBase, 0, var6);
        if (this.hasOverrideBlockTexture()) {
            var7 = this.overrideBlockTexture;
        }
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null) {
            var7 = ConnectedTextures.getConnectedTexture(this.blockAccess, par1BlockRailBase, par2, par3, par4, 1, var7);
        }
        if (par1BlockRailBase.isPowered()) {
            var6 &= 0x7;
        }
        var5.setBrightness(par1BlockRailBase.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        var5.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        final double var8 = var7.getMinU();
        final double var9 = var7.getMinV();
        final double var10 = var7.getMaxU();
        final double var11 = var7.getMaxV();
        final double var12 = 0.0625;
        double var13 = par2 + 1;
        double var14 = par2 + 1;
        double var15 = par2 + 0;
        double var16 = par2 + 0;
        double var17 = par4 + 0;
        double var18 = par4 + 1;
        double var19 = par4 + 1;
        double var20 = par4 + 0;
        double var21 = par3 + var12;
        double var22 = par3 + var12;
        double var23 = par3 + var12;
        double var24 = par3 + var12;
        if (var6 != 1 && var6 != 2 && var6 != 3 && var6 != 7) {
            if (var6 == 8) {
                var14 = (var13 = par2 + 0);
                var16 = (var15 = par2 + 1);
                var20 = (var17 = par4 + 1);
                var19 = (var18 = par4 + 0);
            }
            else if (var6 == 9) {
                var16 = (var13 = par2 + 0);
                var15 = (var14 = par2 + 1);
                var18 = (var17 = par4 + 0);
                var20 = (var19 = par4 + 1);
            }
        }
        else {
            var16 = (var13 = par2 + 1);
            var15 = (var14 = par2 + 0);
            var18 = (var17 = par4 + 1);
            var20 = (var19 = par4 + 0);
        }
        if (var6 != 2 && var6 != 4) {
            if (var6 == 3 || var6 == 5) {
                ++var22;
                ++var23;
            }
        }
        else {
            ++var21;
            ++var24;
        }
        var5.addVertexWithUV(var13, var21, var17, var10, var9);
        var5.addVertexWithUV(var14, var22, var18, var10, var11);
        var5.addVertexWithUV(var15, var23, var19, var8, var11);
        var5.addVertexWithUV(var16, var24, var20, var8, var9);
        var5.addVertexWithUV(var16, var24, var20, var8, var9);
        var5.addVertexWithUV(var15, var23, var19, var8, var11);
        var5.addVertexWithUV(var14, var22, var18, var10, var11);
        var5.addVertexWithUV(var13, var21, var17, var10, var9);
        if (Config.isBetterSnow() && this.hasSnowNeighbours(par2, par3, par4)) {
            this.renderSnow(par2, par3, par4, 0.05);
        }
        return true;
    }
    
    public boolean renderBlockLadder(final Block par1Block, final int par2, final int par3, final int par4) {
        final Tessellator var5 = Tessellator.instance;
        Icon var6 = this.getBlockIconFromSide(par1Block, 0);
        if (this.hasOverrideBlockTexture()) {
            var6 = this.overrideBlockTexture;
        }
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null) {
            var6 = ConnectedTextures.getConnectedTexture(this.blockAccess, par1Block, par2, par3, par4, -1, var6);
        }
        var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        final float var7 = 1.0f;
        var5.setColorOpaque_F(var7, var7, var7);
        final double var8 = var6.getMinU();
        final double var9 = var6.getMinV();
        final double var10 = var6.getMaxU();
        final double var11 = var6.getMaxV();
        final int var12 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        final double var13 = 0.0;
        final double var14 = 0.05000000074505806;
        if (var12 == 5) {
            var5.addVertexWithUV(par2 + var14, par3 + 1 + var13, par4 + 1 + var13, var8, var9);
            var5.addVertexWithUV(par2 + var14, par3 + 0 - var13, par4 + 1 + var13, var8, var11);
            var5.addVertexWithUV(par2 + var14, par3 + 0 - var13, par4 + 0 - var13, var10, var11);
            var5.addVertexWithUV(par2 + var14, par3 + 1 + var13, par4 + 0 - var13, var10, var9);
        }
        if (var12 == 4) {
            var5.addVertexWithUV(par2 + 1 - var14, par3 + 0 - var13, par4 + 1 + var13, var10, var11);
            var5.addVertexWithUV(par2 + 1 - var14, par3 + 1 + var13, par4 + 1 + var13, var10, var9);
            var5.addVertexWithUV(par2 + 1 - var14, par3 + 1 + var13, par4 + 0 - var13, var8, var9);
            var5.addVertexWithUV(par2 + 1 - var14, par3 + 0 - var13, par4 + 0 - var13, var8, var11);
        }
        if (var12 == 3) {
            var5.addVertexWithUV(par2 + 1 + var13, par3 + 0 - var13, par4 + var14, var10, var11);
            var5.addVertexWithUV(par2 + 1 + var13, par3 + 1 + var13, par4 + var14, var10, var9);
            var5.addVertexWithUV(par2 + 0 - var13, par3 + 1 + var13, par4 + var14, var8, var9);
            var5.addVertexWithUV(par2 + 0 - var13, par3 + 0 - var13, par4 + var14, var8, var11);
        }
        if (var12 == 2) {
            var5.addVertexWithUV(par2 + 1 + var13, par3 + 1 + var13, par4 + 1 - var14, var8, var9);
            var5.addVertexWithUV(par2 + 1 + var13, par3 + 0 - var13, par4 + 1 - var14, var8, var11);
            var5.addVertexWithUV(par2 + 0 - var13, par3 + 0 - var13, par4 + 1 - var14, var10, var11);
            var5.addVertexWithUV(par2 + 0 - var13, par3 + 1 + var13, par4 + 1 - var14, var10, var9);
        }
        return true;
    }
    
    public boolean renderBlockVine(final Block par1Block, final int par2, final int par3, final int par4) {
        final Tessellator var5 = Tessellator.instance;
        Icon var6 = this.getBlockIconFromSide(par1Block, 0);
        if (this.hasOverrideBlockTexture()) {
            var6 = this.overrideBlockTexture;
        }
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null) {
            var6 = ConnectedTextures.getConnectedTexture(this.blockAccess, par1Block, par2, par3, par4, -1, var6);
        }
        final float var7 = 1.0f;
        var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        final int var8 = CustomColorizer.getColorMultiplier(par1Block, this.blockAccess, par2, par3, par4);
        final float var9 = (var8 >> 16 & 0xFF) / 255.0f;
        final float var10 = (var8 >> 8 & 0xFF) / 255.0f;
        final float var11 = (var8 & 0xFF) / 255.0f;
        var5.setColorOpaque_F(var7 * var9, var7 * var10, var7 * var11);
        final double var12 = var6.getMinU();
        final double var13 = var6.getMinV();
        final double var14 = var6.getMaxU();
        final double var15 = var6.getMaxV();
        final double var16 = 0.05000000074505806;
        final int var17 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        if ((var17 & 0x2) != 0x0) {
            var5.addVertexWithUV(par2 + var16, par3 + 1, par4 + 1, var12, var13);
            var5.addVertexWithUV(par2 + var16, par3 + 0, par4 + 1, var12, var15);
            var5.addVertexWithUV(par2 + var16, par3 + 0, par4 + 0, var14, var15);
            var5.addVertexWithUV(par2 + var16, par3 + 1, par4 + 0, var14, var13);
            var5.addVertexWithUV(par2 + var16, par3 + 1, par4 + 0, var14, var13);
            var5.addVertexWithUV(par2 + var16, par3 + 0, par4 + 0, var14, var15);
            var5.addVertexWithUV(par2 + var16, par3 + 0, par4 + 1, var12, var15);
            var5.addVertexWithUV(par2 + var16, par3 + 1, par4 + 1, var12, var13);
        }
        if ((var17 & 0x8) != 0x0) {
            var5.addVertexWithUV(par2 + 1 - var16, par3 + 0, par4 + 1, var14, var15);
            var5.addVertexWithUV(par2 + 1 - var16, par3 + 1, par4 + 1, var14, var13);
            var5.addVertexWithUV(par2 + 1 - var16, par3 + 1, par4 + 0, var12, var13);
            var5.addVertexWithUV(par2 + 1 - var16, par3 + 0, par4 + 0, var12, var15);
            var5.addVertexWithUV(par2 + 1 - var16, par3 + 0, par4 + 0, var12, var15);
            var5.addVertexWithUV(par2 + 1 - var16, par3 + 1, par4 + 0, var12, var13);
            var5.addVertexWithUV(par2 + 1 - var16, par3 + 1, par4 + 1, var14, var13);
            var5.addVertexWithUV(par2 + 1 - var16, par3 + 0, par4 + 1, var14, var15);
        }
        if ((var17 & 0x4) != 0x0) {
            var5.addVertexWithUV(par2 + 1, par3 + 0, par4 + var16, var14, var15);
            var5.addVertexWithUV(par2 + 1, par3 + 1, par4 + var16, var14, var13);
            var5.addVertexWithUV(par2 + 0, par3 + 1, par4 + var16, var12, var13);
            var5.addVertexWithUV(par2 + 0, par3 + 0, par4 + var16, var12, var15);
            var5.addVertexWithUV(par2 + 0, par3 + 0, par4 + var16, var12, var15);
            var5.addVertexWithUV(par2 + 0, par3 + 1, par4 + var16, var12, var13);
            var5.addVertexWithUV(par2 + 1, par3 + 1, par4 + var16, var14, var13);
            var5.addVertexWithUV(par2 + 1, par3 + 0, par4 + var16, var14, var15);
        }
        if ((var17 & 0x1) != 0x0) {
            var5.addVertexWithUV(par2 + 1, par3 + 1, par4 + 1 - var16, var12, var13);
            var5.addVertexWithUV(par2 + 1, par3 + 0, par4 + 1 - var16, var12, var15);
            var5.addVertexWithUV(par2 + 0, par3 + 0, par4 + 1 - var16, var14, var15);
            var5.addVertexWithUV(par2 + 0, par3 + 1, par4 + 1 - var16, var14, var13);
            var5.addVertexWithUV(par2 + 0, par3 + 1, par4 + 1 - var16, var14, var13);
            var5.addVertexWithUV(par2 + 0, par3 + 0, par4 + 1 - var16, var14, var15);
            var5.addVertexWithUV(par2 + 1, par3 + 0, par4 + 1 - var16, var12, var15);
            var5.addVertexWithUV(par2 + 1, par3 + 1, par4 + 1 - var16, var12, var13);
        }
        if (this.blockAccess.isBlockNormalCube(par2, par3 + 1, par4)) {
            var5.addVertexWithUV(par2 + 1, par3 + 1 - var16, par4 + 0, var12, var13);
            var5.addVertexWithUV(par2 + 1, par3 + 1 - var16, par4 + 1, var12, var15);
            var5.addVertexWithUV(par2 + 0, par3 + 1 - var16, par4 + 1, var14, var15);
            var5.addVertexWithUV(par2 + 0, par3 + 1 - var16, par4 + 0, var14, var13);
        }
        return true;
    }
    
    public boolean renderBlockPane(final BlockPane par1BlockPane, final int par2, final int par3, final int par4) {
        final int var5 = this.blockAccess.getHeight();
        final Tessellator var6 = Tessellator.instance;
        var6.setBrightness(par1BlockPane.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        final float var7 = 1.0f;
        final int var8 = par1BlockPane.colorMultiplier(this.blockAccess, par2, par3, par4);
        float var9 = (var8 >> 16 & 0xFF) / 255.0f;
        float var10 = (var8 >> 8 & 0xFF) / 255.0f;
        float var11 = (var8 & 0xFF) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            final float var12 = (var9 * 30.0f + var10 * 59.0f + var11 * 11.0f) / 100.0f;
            final float var13 = (var9 * 30.0f + var10 * 70.0f) / 100.0f;
            final float var14 = (var9 * 30.0f + var11 * 70.0f) / 100.0f;
            var9 = var12;
            var10 = var13;
            var11 = var14;
        }
        var6.setColorOpaque_F(var7 * var9, var7 * var10, var7 * var11);
        ConnectedProperties var15 = null;
        Icon var16;
        Icon var17;
        if (this.hasOverrideBlockTexture()) {
            var16 = this.overrideBlockTexture;
            var17 = this.overrideBlockTexture;
        }
        else {
            final int var18 = this.blockAccess.getBlockMetadata(par2, par3, par4);
            var16 = this.getBlockIconFromSideAndMetadata(par1BlockPane, 0, var18);
            var17 = par1BlockPane.getSideTextureIndex();
            if (Config.isConnectedTextures()) {
                var15 = ConnectedTextures.getConnectedProperties(this.blockAccess, par1BlockPane, par2, par3, par4, -1, var16);
            }
        }
        Icon var19 = var16;
        Icon var20 = var16;
        Icon var21 = var16;
        if (var15 != null) {
            final int var22 = par1BlockPane.blockID;
            final int var23 = this.blockAccess.getBlockId(par2 + 1, par3, par4);
            final int var24 = this.blockAccess.getBlockId(par2 - 1, par3, par4);
            final int var25 = this.blockAccess.getBlockId(par2, par3 + 1, par4);
            final int var26 = this.blockAccess.getBlockId(par2, par3 - 1, par4);
            final int var27 = this.blockAccess.getBlockId(par2, par3, par4 + 1);
            final int var28 = this.blockAccess.getBlockId(par2, par3, par4 - 1);
            final boolean var29 = var23 == var22;
            final boolean var30 = var24 == var22;
            final boolean var31 = var25 == var22;
            final boolean var32 = var26 == var22;
            final boolean var33 = var27 == var22;
            final boolean var34 = var28 == var22;
            final int var35 = ConnectedTextures.getPaneTextureIndex(var29, var30, var31, var32);
            final int var36 = ConnectedTextures.getReversePaneTextureIndex(var35);
            final int var37 = ConnectedTextures.getPaneTextureIndex(var33, var34, var31, var32);
            final int var38 = ConnectedTextures.getReversePaneTextureIndex(var37);
            var16 = ConnectedTextures.getCtmTexture(var15, var35, var16);
            var19 = ConnectedTextures.getCtmTexture(var15, var36, var19);
            var20 = ConnectedTextures.getCtmTexture(var15, var37, var20);
            var21 = ConnectedTextures.getCtmTexture(var15, var38, var21);
        }
        final int var18 = var16.getOriginX();
        final int var22 = var16.getOriginY();
        final double var39 = var16.getMinU();
        final double var40 = var16.getInterpolatedU(8.0);
        final double var41 = var16.getMaxU();
        final double var42 = var16.getMinV();
        final double var43 = var16.getMaxV();
        final int var44 = var19.getOriginX();
        final int var45 = var19.getOriginY();
        final double var46 = var19.getMinU();
        final double var47 = var19.getInterpolatedU(8.0);
        final double var48 = var19.getMaxU();
        final double var49 = var19.getMinV();
        final double var50 = var19.getMaxV();
        final int var51 = var20.getOriginX();
        final int var52 = var20.getOriginY();
        final double var53 = var20.getMinU();
        final double var54 = var20.getInterpolatedU(8.0);
        final double var55 = var20.getMaxU();
        final double var56 = var20.getMinV();
        final double var57 = var20.getMaxV();
        final int var58 = var21.getOriginX();
        final int var59 = var21.getOriginY();
        final double var60 = var21.getMinU();
        final double var61 = var21.getInterpolatedU(8.0);
        final double var62 = var21.getMaxU();
        final double var63 = var21.getMinV();
        final double var64 = var21.getMaxV();
        final int var65 = var17.getOriginX();
        final int var66 = var17.getOriginY();
        final double var67 = var17.getInterpolatedU(7.0);
        final double var68 = var17.getInterpolatedU(9.0);
        final double var69 = var17.getMinV();
        final double var70 = var17.getInterpolatedV(8.0);
        final double var71 = var17.getMaxV();
        final double var72 = par2;
        final double var73 = par2 + 0.5;
        final double var74 = par2 + 1;
        final double var75 = par4;
        final double var76 = par4 + 0.5;
        final double var77 = par4 + 1;
        final double var78 = par2 + 0.5 - 0.0625;
        final double var79 = par2 + 0.5 + 0.0625;
        final double var80 = par4 + 0.5 - 0.0625;
        final double var81 = par4 + 0.5 + 0.0625;
        final boolean var82 = par1BlockPane.canThisPaneConnectToThisBlockID(this.blockAccess.getBlockId(par2, par3, par4 - 1));
        final boolean var83 = par1BlockPane.canThisPaneConnectToThisBlockID(this.blockAccess.getBlockId(par2, par3, par4 + 1));
        final boolean var84 = par1BlockPane.canThisPaneConnectToThisBlockID(this.blockAccess.getBlockId(par2 - 1, par3, par4));
        final boolean var85 = par1BlockPane.canThisPaneConnectToThisBlockID(this.blockAccess.getBlockId(par2 + 1, par3, par4));
        final boolean var86 = par1BlockPane.shouldSideBeRendered(this.blockAccess, par2, par3 + 1, par4, 1);
        final boolean var87 = par1BlockPane.shouldSideBeRendered(this.blockAccess, par2, par3 - 1, par4, 0);
        if ((!var84 || !var85) && (var84 || var85 || var82 || var83)) {
            if (var84 && !var85) {
                var6.addVertexWithUV(var72, par3 + 1, var76, var39, var42);
                var6.addVertexWithUV(var72, par3 + 0, var76, var39, var43);
                var6.addVertexWithUV(var73, par3 + 0, var76, var40, var43);
                var6.addVertexWithUV(var73, par3 + 1, var76, var40, var42);
                var6.addVertexWithUV(var73, par3 + 1, var76, var47, var49);
                var6.addVertexWithUV(var73, par3 + 0, var76, var47, var50);
                var6.addVertexWithUV(var72, par3 + 0, var76, var48, var50);
                var6.addVertexWithUV(var72, par3 + 1, var76, var48, var49);
                if (!var83 && !var82) {
                    var6.addVertexWithUV(var73, par3 + 1, var81, var67, var69);
                    var6.addVertexWithUV(var73, par3 + 0, var81, var67, var71);
                    var6.addVertexWithUV(var73, par3 + 0, var80, var68, var71);
                    var6.addVertexWithUV(var73, par3 + 1, var80, var68, var69);
                    var6.addVertexWithUV(var73, par3 + 1, var80, var67, var69);
                    var6.addVertexWithUV(var73, par3 + 0, var80, var67, var71);
                    var6.addVertexWithUV(var73, par3 + 0, var81, var68, var71);
                    var6.addVertexWithUV(var73, par3 + 1, var81, var68, var69);
                }
                if (var86 || (par3 < var5 - 1 && this.blockAccess.isAirBlock(par2 - 1, par3 + 1, par4))) {
                    var6.addVertexWithUV(var72, par3 + 1 + 0.01, var81, var68, var70);
                    var6.addVertexWithUV(var73, par3 + 1 + 0.01, var81, var68, var71);
                    var6.addVertexWithUV(var73, par3 + 1 + 0.01, var80, var67, var71);
                    var6.addVertexWithUV(var72, par3 + 1 + 0.01, var80, var67, var70);
                    var6.addVertexWithUV(var73, par3 + 1 + 0.01, var81, var68, var70);
                    var6.addVertexWithUV(var72, par3 + 1 + 0.01, var81, var68, var71);
                    var6.addVertexWithUV(var72, par3 + 1 + 0.01, var80, var67, var71);
                    var6.addVertexWithUV(var73, par3 + 1 + 0.01, var80, var67, var70);
                }
                if (var87 || (par3 > 1 && this.blockAccess.isAirBlock(par2 - 1, par3 - 1, par4))) {
                    var6.addVertexWithUV(var72, par3 - 0.01, var81, var68, var70);
                    var6.addVertexWithUV(var73, par3 - 0.01, var81, var68, var71);
                    var6.addVertexWithUV(var73, par3 - 0.01, var80, var67, var71);
                    var6.addVertexWithUV(var72, par3 - 0.01, var80, var67, var70);
                    var6.addVertexWithUV(var73, par3 - 0.01, var81, var68, var70);
                    var6.addVertexWithUV(var72, par3 - 0.01, var81, var68, var71);
                    var6.addVertexWithUV(var72, par3 - 0.01, var80, var67, var71);
                    var6.addVertexWithUV(var73, par3 - 0.01, var80, var67, var70);
                }
            }
            else if (!var84 && var85) {
                var6.addVertexWithUV(var73, par3 + 1, var76, var40, var42);
                var6.addVertexWithUV(var73, par3 + 0, var76, var40, var43);
                var6.addVertexWithUV(var74, par3 + 0, var76, var41, var43);
                var6.addVertexWithUV(var74, par3 + 1, var76, var41, var42);
                var6.addVertexWithUV(var74, par3 + 1, var76, var46, var49);
                var6.addVertexWithUV(var74, par3 + 0, var76, var46, var50);
                var6.addVertexWithUV(var73, par3 + 0, var76, var47, var50);
                var6.addVertexWithUV(var73, par3 + 1, var76, var47, var49);
                if (!var83 && !var82) {
                    var6.addVertexWithUV(var73, par3 + 1, var80, var67, var69);
                    var6.addVertexWithUV(var73, par3 + 0, var80, var67, var71);
                    var6.addVertexWithUV(var73, par3 + 0, var81, var68, var71);
                    var6.addVertexWithUV(var73, par3 + 1, var81, var68, var69);
                    var6.addVertexWithUV(var73, par3 + 1, var81, var67, var69);
                    var6.addVertexWithUV(var73, par3 + 0, var81, var67, var71);
                    var6.addVertexWithUV(var73, par3 + 0, var80, var68, var71);
                    var6.addVertexWithUV(var73, par3 + 1, var80, var68, var69);
                }
                if (var86 || (par3 < var5 - 1 && this.blockAccess.isAirBlock(par2 + 1, par3 + 1, par4))) {
                    var6.addVertexWithUV(var73, par3 + 1 + 0.01, var81, var68, var69);
                    var6.addVertexWithUV(var74, par3 + 1 + 0.01, var81, var68, var70);
                    var6.addVertexWithUV(var74, par3 + 1 + 0.01, var80, var67, var70);
                    var6.addVertexWithUV(var73, par3 + 1 + 0.01, var80, var67, var69);
                    var6.addVertexWithUV(var74, par3 + 1 + 0.01, var81, var68, var69);
                    var6.addVertexWithUV(var73, par3 + 1 + 0.01, var81, var68, var70);
                    var6.addVertexWithUV(var73, par3 + 1 + 0.01, var80, var67, var70);
                    var6.addVertexWithUV(var74, par3 + 1 + 0.01, var80, var67, var69);
                }
                if (var87 || (par3 > 1 && this.blockAccess.isAirBlock(par2 + 1, par3 - 1, par4))) {
                    var6.addVertexWithUV(var73, par3 - 0.01, var81, var68, var69);
                    var6.addVertexWithUV(var74, par3 - 0.01, var81, var68, var70);
                    var6.addVertexWithUV(var74, par3 - 0.01, var80, var67, var70);
                    var6.addVertexWithUV(var73, par3 - 0.01, var80, var67, var69);
                    var6.addVertexWithUV(var74, par3 - 0.01, var81, var68, var69);
                    var6.addVertexWithUV(var73, par3 - 0.01, var81, var68, var70);
                    var6.addVertexWithUV(var73, par3 - 0.01, var80, var67, var70);
                    var6.addVertexWithUV(var74, par3 - 0.01, var80, var67, var69);
                }
            }
        }
        else {
            var6.addVertexWithUV(var72, par3 + 1, var76, var39, var42);
            var6.addVertexWithUV(var72, par3 + 0, var76, var39, var43);
            var6.addVertexWithUV(var74, par3 + 0, var76, var41, var43);
            var6.addVertexWithUV(var74, par3 + 1, var76, var41, var42);
            var6.addVertexWithUV(var74, par3 + 1, var76, var46, var49);
            var6.addVertexWithUV(var74, par3 + 0, var76, var46, var50);
            var6.addVertexWithUV(var72, par3 + 0, var76, var48, var50);
            var6.addVertexWithUV(var72, par3 + 1, var76, var48, var49);
            if (var86) {
                var6.addVertexWithUV(var72, par3 + 1 + 0.01, var81, var68, var71);
                var6.addVertexWithUV(var74, par3 + 1 + 0.01, var81, var68, var69);
                var6.addVertexWithUV(var74, par3 + 1 + 0.01, var80, var67, var69);
                var6.addVertexWithUV(var72, par3 + 1 + 0.01, var80, var67, var71);
                var6.addVertexWithUV(var74, par3 + 1 + 0.01, var81, var68, var71);
                var6.addVertexWithUV(var72, par3 + 1 + 0.01, var81, var68, var69);
                var6.addVertexWithUV(var72, par3 + 1 + 0.01, var80, var67, var69);
                var6.addVertexWithUV(var74, par3 + 1 + 0.01, var80, var67, var71);
            }
            else {
                if (par3 < var5 - 1 && this.blockAccess.isAirBlock(par2 - 1, par3 + 1, par4)) {
                    var6.addVertexWithUV(var72, par3 + 1 + 0.01, var81, var68, var70);
                    var6.addVertexWithUV(var73, par3 + 1 + 0.01, var81, var68, var71);
                    var6.addVertexWithUV(var73, par3 + 1 + 0.01, var80, var67, var71);
                    var6.addVertexWithUV(var72, par3 + 1 + 0.01, var80, var67, var70);
                    var6.addVertexWithUV(var73, par3 + 1 + 0.01, var81, var68, var70);
                    var6.addVertexWithUV(var72, par3 + 1 + 0.01, var81, var68, var71);
                    var6.addVertexWithUV(var72, par3 + 1 + 0.01, var80, var67, var71);
                    var6.addVertexWithUV(var73, par3 + 1 + 0.01, var80, var67, var70);
                }
                if (par3 < var5 - 1 && this.blockAccess.isAirBlock(par2 + 1, par3 + 1, par4)) {
                    var6.addVertexWithUV(var73, par3 + 1 + 0.01, var81, var68, var69);
                    var6.addVertexWithUV(var74, par3 + 1 + 0.01, var81, var68, var70);
                    var6.addVertexWithUV(var74, par3 + 1 + 0.01, var80, var67, var70);
                    var6.addVertexWithUV(var73, par3 + 1 + 0.01, var80, var67, var69);
                    var6.addVertexWithUV(var74, par3 + 1 + 0.01, var81, var68, var69);
                    var6.addVertexWithUV(var73, par3 + 1 + 0.01, var81, var68, var70);
                    var6.addVertexWithUV(var73, par3 + 1 + 0.01, var80, var67, var70);
                    var6.addVertexWithUV(var74, par3 + 1 + 0.01, var80, var67, var69);
                }
            }
            if (var87) {
                var6.addVertexWithUV(var72, par3 - 0.01, var81, var68, var71);
                var6.addVertexWithUV(var74, par3 - 0.01, var81, var68, var69);
                var6.addVertexWithUV(var74, par3 - 0.01, var80, var67, var69);
                var6.addVertexWithUV(var72, par3 - 0.01, var80, var67, var71);
                var6.addVertexWithUV(var74, par3 - 0.01, var81, var68, var71);
                var6.addVertexWithUV(var72, par3 - 0.01, var81, var68, var69);
                var6.addVertexWithUV(var72, par3 - 0.01, var80, var67, var69);
                var6.addVertexWithUV(var74, par3 - 0.01, var80, var67, var71);
            }
            else {
                if (par3 > 1 && this.blockAccess.isAirBlock(par2 - 1, par3 - 1, par4)) {
                    var6.addVertexWithUV(var72, par3 - 0.01, var81, var68, var70);
                    var6.addVertexWithUV(var73, par3 - 0.01, var81, var68, var71);
                    var6.addVertexWithUV(var73, par3 - 0.01, var80, var67, var71);
                    var6.addVertexWithUV(var72, par3 - 0.01, var80, var67, var70);
                    var6.addVertexWithUV(var73, par3 - 0.01, var81, var68, var70);
                    var6.addVertexWithUV(var72, par3 - 0.01, var81, var68, var71);
                    var6.addVertexWithUV(var72, par3 - 0.01, var80, var67, var71);
                    var6.addVertexWithUV(var73, par3 - 0.01, var80, var67, var70);
                }
                if (par3 > 1 && this.blockAccess.isAirBlock(par2 + 1, par3 - 1, par4)) {
                    var6.addVertexWithUV(var73, par3 - 0.01, var81, var68, var69);
                    var6.addVertexWithUV(var74, par3 - 0.01, var81, var68, var70);
                    var6.addVertexWithUV(var74, par3 - 0.01, var80, var67, var70);
                    var6.addVertexWithUV(var73, par3 - 0.01, var80, var67, var69);
                    var6.addVertexWithUV(var74, par3 - 0.01, var81, var68, var69);
                    var6.addVertexWithUV(var73, par3 - 0.01, var81, var68, var70);
                    var6.addVertexWithUV(var73, par3 - 0.01, var80, var67, var70);
                    var6.addVertexWithUV(var74, par3 - 0.01, var80, var67, var69);
                }
            }
        }
        if ((!var82 || !var83) && (var84 || var85 || var82 || var83)) {
            if (var82 && !var83) {
                var6.addVertexWithUV(var73, par3 + 1, var75, var53, var56);
                var6.addVertexWithUV(var73, par3 + 0, var75, var53, var57);
                var6.addVertexWithUV(var73, par3 + 0, var76, var54, var57);
                var6.addVertexWithUV(var73, par3 + 1, var76, var54, var56);
                var6.addVertexWithUV(var73, par3 + 1, var76, var61, var63);
                var6.addVertexWithUV(var73, par3 + 0, var76, var61, var64);
                var6.addVertexWithUV(var73, par3 + 0, var75, var62, var64);
                var6.addVertexWithUV(var73, par3 + 1, var75, var62, var63);
                if (!var85 && !var84) {
                    var6.addVertexWithUV(var78, par3 + 1, var76, var67, var69);
                    var6.addVertexWithUV(var78, par3 + 0, var76, var67, var71);
                    var6.addVertexWithUV(var79, par3 + 0, var76, var68, var71);
                    var6.addVertexWithUV(var79, par3 + 1, var76, var68, var69);
                    var6.addVertexWithUV(var79, par3 + 1, var76, var67, var69);
                    var6.addVertexWithUV(var79, par3 + 0, var76, var67, var71);
                    var6.addVertexWithUV(var78, par3 + 0, var76, var68, var71);
                    var6.addVertexWithUV(var78, par3 + 1, var76, var68, var69);
                }
                if (var86 || (par3 < var5 - 1 && this.blockAccess.isAirBlock(par2, par3 + 1, par4 - 1))) {
                    var6.addVertexWithUV(var78, par3 + 1 + 0.005, var75, var68, var69);
                    var6.addVertexWithUV(var78, par3 + 1 + 0.005, var76, var68, var70);
                    var6.addVertexWithUV(var79, par3 + 1 + 0.005, var76, var67, var70);
                    var6.addVertexWithUV(var79, par3 + 1 + 0.005, var75, var67, var69);
                    var6.addVertexWithUV(var78, par3 + 1 + 0.005, var76, var68, var69);
                    var6.addVertexWithUV(var78, par3 + 1 + 0.005, var75, var68, var70);
                    var6.addVertexWithUV(var79, par3 + 1 + 0.005, var75, var67, var70);
                    var6.addVertexWithUV(var79, par3 + 1 + 0.005, var76, var67, var69);
                }
                if (var87 || (par3 > 1 && this.blockAccess.isAirBlock(par2, par3 - 1, par4 - 1))) {
                    var6.addVertexWithUV(var78, par3 - 0.005, var75, var68, var69);
                    var6.addVertexWithUV(var78, par3 - 0.005, var76, var68, var70);
                    var6.addVertexWithUV(var79, par3 - 0.005, var76, var67, var70);
                    var6.addVertexWithUV(var79, par3 - 0.005, var75, var67, var69);
                    var6.addVertexWithUV(var78, par3 - 0.005, var76, var68, var69);
                    var6.addVertexWithUV(var78, par3 - 0.005, var75, var68, var70);
                    var6.addVertexWithUV(var79, par3 - 0.005, var75, var67, var70);
                    var6.addVertexWithUV(var79, par3 - 0.005, var76, var67, var69);
                }
            }
            else if (!var82 && var83) {
                var6.addVertexWithUV(var73, par3 + 1, var76, var54, var56);
                var6.addVertexWithUV(var73, par3 + 0, var76, var54, var57);
                var6.addVertexWithUV(var73, par3 + 0, var77, var55, var57);
                var6.addVertexWithUV(var73, par3 + 1, var77, var55, var56);
                var6.addVertexWithUV(var73, par3 + 1, var77, var60, var63);
                var6.addVertexWithUV(var73, par3 + 0, var77, var60, var64);
                var6.addVertexWithUV(var73, par3 + 0, var76, var61, var64);
                var6.addVertexWithUV(var73, par3 + 1, var76, var61, var63);
                if (!var85 && !var84) {
                    var6.addVertexWithUV(var79, par3 + 1, var76, var67, var69);
                    var6.addVertexWithUV(var79, par3 + 0, var76, var67, var71);
                    var6.addVertexWithUV(var78, par3 + 0, var76, var68, var71);
                    var6.addVertexWithUV(var78, par3 + 1, var76, var68, var69);
                    var6.addVertexWithUV(var78, par3 + 1, var76, var67, var69);
                    var6.addVertexWithUV(var78, par3 + 0, var76, var67, var71);
                    var6.addVertexWithUV(var79, par3 + 0, var76, var68, var71);
                    var6.addVertexWithUV(var79, par3 + 1, var76, var68, var69);
                }
                if (var86 || (par3 < var5 - 1 && this.blockAccess.isAirBlock(par2, par3 + 1, par4 + 1))) {
                    var6.addVertexWithUV(var78, par3 + 1 + 0.005, var76, var67, var70);
                    var6.addVertexWithUV(var78, par3 + 1 + 0.005, var77, var67, var71);
                    var6.addVertexWithUV(var79, par3 + 1 + 0.005, var77, var68, var71);
                    var6.addVertexWithUV(var79, par3 + 1 + 0.005, var76, var68, var70);
                    var6.addVertexWithUV(var78, par3 + 1 + 0.005, var77, var67, var70);
                    var6.addVertexWithUV(var78, par3 + 1 + 0.005, var76, var67, var71);
                    var6.addVertexWithUV(var79, par3 + 1 + 0.005, var76, var68, var71);
                    var6.addVertexWithUV(var79, par3 + 1 + 0.005, var77, var68, var70);
                }
                if (var87 || (par3 > 1 && this.blockAccess.isAirBlock(par2, par3 - 1, par4 + 1))) {
                    var6.addVertexWithUV(var78, par3 - 0.005, var76, var67, var70);
                    var6.addVertexWithUV(var78, par3 - 0.005, var77, var67, var71);
                    var6.addVertexWithUV(var79, par3 - 0.005, var77, var68, var71);
                    var6.addVertexWithUV(var79, par3 - 0.005, var76, var68, var70);
                    var6.addVertexWithUV(var78, par3 - 0.005, var77, var67, var70);
                    var6.addVertexWithUV(var78, par3 - 0.005, var76, var67, var71);
                    var6.addVertexWithUV(var79, par3 - 0.005, var76, var68, var71);
                    var6.addVertexWithUV(var79, par3 - 0.005, var77, var68, var70);
                }
            }
        }
        else {
            var6.addVertexWithUV(var73, par3 + 1, var77, var60, var63);
            var6.addVertexWithUV(var73, par3 + 0, var77, var60, var64);
            var6.addVertexWithUV(var73, par3 + 0, var75, var62, var64);
            var6.addVertexWithUV(var73, par3 + 1, var75, var62, var63);
            var6.addVertexWithUV(var73, par3 + 1, var75, var53, var56);
            var6.addVertexWithUV(var73, par3 + 0, var75, var53, var57);
            var6.addVertexWithUV(var73, par3 + 0, var77, var55, var57);
            var6.addVertexWithUV(var73, par3 + 1, var77, var55, var56);
            if (var86) {
                var6.addVertexWithUV(var79, par3 + 1 + 0.005, var77, var68, var71);
                var6.addVertexWithUV(var79, par3 + 1 + 0.005, var75, var68, var69);
                var6.addVertexWithUV(var78, par3 + 1 + 0.005, var75, var67, var69);
                var6.addVertexWithUV(var78, par3 + 1 + 0.005, var77, var67, var71);
                var6.addVertexWithUV(var79, par3 + 1 + 0.005, var75, var68, var71);
                var6.addVertexWithUV(var79, par3 + 1 + 0.005, var77, var68, var69);
                var6.addVertexWithUV(var78, par3 + 1 + 0.005, var77, var67, var69);
                var6.addVertexWithUV(var78, par3 + 1 + 0.005, var75, var67, var71);
            }
            else {
                if (par3 < var5 - 1 && this.blockAccess.isAirBlock(par2, par3 + 1, par4 - 1)) {
                    var6.addVertexWithUV(var78, par3 + 1 + 0.005, var75, var68, var69);
                    var6.addVertexWithUV(var78, par3 + 1 + 0.005, var76, var68, var70);
                    var6.addVertexWithUV(var79, par3 + 1 + 0.005, var76, var67, var70);
                    var6.addVertexWithUV(var79, par3 + 1 + 0.005, var75, var67, var69);
                    var6.addVertexWithUV(var78, par3 + 1 + 0.005, var76, var68, var69);
                    var6.addVertexWithUV(var78, par3 + 1 + 0.005, var75, var68, var70);
                    var6.addVertexWithUV(var79, par3 + 1 + 0.005, var75, var67, var70);
                    var6.addVertexWithUV(var79, par3 + 1 + 0.005, var76, var67, var69);
                }
                if (par3 < var5 - 1 && this.blockAccess.isAirBlock(par2, par3 + 1, par4 + 1)) {
                    var6.addVertexWithUV(var78, par3 + 1 + 0.005, var76, var67, var70);
                    var6.addVertexWithUV(var78, par3 + 1 + 0.005, var77, var67, var71);
                    var6.addVertexWithUV(var79, par3 + 1 + 0.005, var77, var68, var71);
                    var6.addVertexWithUV(var79, par3 + 1 + 0.005, var76, var68, var70);
                    var6.addVertexWithUV(var78, par3 + 1 + 0.005, var77, var67, var70);
                    var6.addVertexWithUV(var78, par3 + 1 + 0.005, var76, var67, var71);
                    var6.addVertexWithUV(var79, par3 + 1 + 0.005, var76, var68, var71);
                    var6.addVertexWithUV(var79, par3 + 1 + 0.005, var77, var68, var70);
                }
            }
            if (var87) {
                var6.addVertexWithUV(var79, par3 - 0.005, var77, var68, var71);
                var6.addVertexWithUV(var79, par3 - 0.005, var75, var68, var69);
                var6.addVertexWithUV(var78, par3 - 0.005, var75, var67, var69);
                var6.addVertexWithUV(var78, par3 - 0.005, var77, var67, var71);
                var6.addVertexWithUV(var79, par3 - 0.005, var75, var68, var71);
                var6.addVertexWithUV(var79, par3 - 0.005, var77, var68, var69);
                var6.addVertexWithUV(var78, par3 - 0.005, var77, var67, var69);
                var6.addVertexWithUV(var78, par3 - 0.005, var75, var67, var71);
            }
            else {
                if (par3 > 1 && this.blockAccess.isAirBlock(par2, par3 - 1, par4 - 1)) {
                    var6.addVertexWithUV(var78, par3 - 0.005, var75, var68, var69);
                    var6.addVertexWithUV(var78, par3 - 0.005, var76, var68, var70);
                    var6.addVertexWithUV(var79, par3 - 0.005, var76, var67, var70);
                    var6.addVertexWithUV(var79, par3 - 0.005, var75, var67, var69);
                    var6.addVertexWithUV(var78, par3 - 0.005, var76, var68, var69);
                    var6.addVertexWithUV(var78, par3 - 0.005, var75, var68, var70);
                    var6.addVertexWithUV(var79, par3 - 0.005, var75, var67, var70);
                    var6.addVertexWithUV(var79, par3 - 0.005, var76, var67, var69);
                }
                if (par3 > 1 && this.blockAccess.isAirBlock(par2, par3 - 1, par4 + 1)) {
                    var6.addVertexWithUV(var78, par3 - 0.005, var76, var67, var70);
                    var6.addVertexWithUV(var78, par3 - 0.005, var77, var67, var71);
                    var6.addVertexWithUV(var79, par3 - 0.005, var77, var68, var71);
                    var6.addVertexWithUV(var79, par3 - 0.005, var76, var68, var70);
                    var6.addVertexWithUV(var78, par3 - 0.005, var77, var67, var70);
                    var6.addVertexWithUV(var78, par3 - 0.005, var76, var67, var71);
                    var6.addVertexWithUV(var79, par3 - 0.005, var76, var68, var71);
                    var6.addVertexWithUV(var79, par3 - 0.005, var77, var68, var70);
                }
            }
        }
        if (Config.isBetterSnow() && this.hasSnowNeighbours(par2, par3, par4)) {
            this.renderSnow(par2, par3, par4, Block.snow.maxY);
        }
        return true;
    }
    
    public boolean renderCrossedSquares(final Block par1Block, final int par2, final int par3, final int par4) {
        final Tessellator var5 = Tessellator.instance;
        var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        final float var6 = 1.0f;
        final int var7 = CustomColorizer.getColorMultiplier(par1Block, this.blockAccess, par2, par3, par4);
        float var8 = (var7 >> 16 & 0xFF) / 255.0f;
        float var9 = (var7 >> 8 & 0xFF) / 255.0f;
        float var10 = (var7 & 0xFF) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            final float var11 = (var8 * 30.0f + var9 * 59.0f + var10 * 11.0f) / 100.0f;
            final float var12 = (var8 * 30.0f + var9 * 70.0f) / 100.0f;
            final float var13 = (var8 * 30.0f + var10 * 70.0f) / 100.0f;
            var8 = var11;
            var9 = var12;
            var10 = var13;
        }
        var5.setColorOpaque_F(var6 * var8, var6 * var9, var6 * var10);
        double var14 = par2;
        double var15 = par3;
        double var16 = par4;
        if (par1Block == Block.tallGrass) {
            long var17 = par2 * 3129871 ^ par4 * 116129781L ^ par3;
            var17 = var17 * var17 * 42317861L + var17 * 11L;
            var14 += ((var17 >> 16 & 0xFL) / 15.0f - 0.5) * 0.5;
            var15 += ((var17 >> 20 & 0xFL) / 15.0f - 1.0) * 0.2;
            var16 += ((var17 >> 24 & 0xFL) / 15.0f - 0.5) * 0.5;
        }
        this.drawCrossedSquares(par1Block, this.blockAccess.getBlockMetadata(par2, par3, par4), var14, var15, var16, 1.0f);
        if (Config.isBetterSnow() && this.hasSnowNeighbours(par2, par3, par4)) {
            this.renderSnow(par2, par3, par4, Block.snow.maxY);
        }
        return true;
    }
    
    public boolean renderBlockStem(final Block par1Block, final int par2, final int par3, final int par4) {
        final BlockStem var5 = (BlockStem)par1Block;
        final Tessellator var6 = Tessellator.instance;
        var6.setBrightness(var5.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        final float var7 = 1.0f;
        final int var8 = CustomColorizer.getStemColorMultiplier(var5, this.blockAccess, par2, par3, par4);
        float var9 = (var8 >> 16 & 0xFF) / 255.0f;
        float var10 = (var8 >> 8 & 0xFF) / 255.0f;
        float var11 = (var8 & 0xFF) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            final float var12 = (var9 * 30.0f + var10 * 59.0f + var11 * 11.0f) / 100.0f;
            final float var13 = (var9 * 30.0f + var10 * 70.0f) / 100.0f;
            final float var14 = (var9 * 30.0f + var11 * 70.0f) / 100.0f;
            var9 = var12;
            var10 = var13;
            var11 = var14;
        }
        var6.setColorOpaque_F(var7 * var9, var7 * var10, var7 * var11);
        var5.setBlockBoundsBasedOnState(this.blockAccess, par2, par3, par4);
        final int var15 = var5.getState(this.blockAccess, par2, par3, par4);
        if (var15 < 0) {
            this.renderBlockStemSmall(var5, this.blockAccess.getBlockMetadata(par2, par3, par4), this.renderMaxY, par2, par3 - 0.0625f, par4);
        }
        else {
            this.renderBlockStemSmall(var5, this.blockAccess.getBlockMetadata(par2, par3, par4), 0.5, par2, par3 - 0.0625f, par4);
            this.renderBlockStemBig(var5, this.blockAccess.getBlockMetadata(par2, par3, par4), var15, this.renderMaxY, par2, par3 - 0.0625f, par4);
        }
        return true;
    }
    
    public boolean renderBlockCrops(final Block par1Block, final int par2, final int par3, final int par4) {
        final Tessellator var5 = Tessellator.instance;
        var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        var5.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        this.renderBlockCropsImpl(par1Block, this.blockAccess.getBlockMetadata(par2, par3, par4), par2, par3 - 0.0625f, par4);
        return true;
    }
    
    public void renderTorchAtAngle(final Block par1Block, double par2, final double par4, double par6, final double par8, final double par10, final int par12) {
        final Tessellator var13 = Tessellator.instance;
        Icon var14 = this.getBlockIconFromSideAndMetadata(par1Block, 0, par12);
        if (this.hasOverrideBlockTexture()) {
            var14 = this.overrideBlockTexture;
        }
        final double var15 = var14.getMinU();
        final double var16 = var14.getMinV();
        final double var17 = var14.getMaxU();
        final double var18 = var14.getMaxV();
        final double var19 = var14.getInterpolatedU(7.0);
        final double var20 = var14.getInterpolatedV(6.0);
        final double var21 = var14.getInterpolatedU(9.0);
        final double var22 = var14.getInterpolatedV(8.0);
        final double var23 = var14.getInterpolatedU(7.0);
        final double var24 = var14.getInterpolatedV(13.0);
        final double var25 = var14.getInterpolatedU(9.0);
        final double var26 = var14.getInterpolatedV(15.0);
        par2 += 0.5;
        par6 += 0.5;
        final double var27 = par2 - 0.5;
        final double var28 = par2 + 0.5;
        final double var29 = par6 - 0.5;
        final double var30 = par6 + 0.5;
        final double var31 = 0.0625;
        final double var32 = 0.625;
        var13.addVertexWithUV(par2 + par8 * (1.0 - var32) - var31, par4 + var32, par6 + par10 * (1.0 - var32) - var31, var19, var20);
        var13.addVertexWithUV(par2 + par8 * (1.0 - var32) - var31, par4 + var32, par6 + par10 * (1.0 - var32) + var31, var19, var22);
        var13.addVertexWithUV(par2 + par8 * (1.0 - var32) + var31, par4 + var32, par6 + par10 * (1.0 - var32) + var31, var21, var22);
        var13.addVertexWithUV(par2 + par8 * (1.0 - var32) + var31, par4 + var32, par6 + par10 * (1.0 - var32) - var31, var21, var20);
        var13.addVertexWithUV(par2 + var31 + par8, par4, par6 - var31 + par10, var25, var24);
        var13.addVertexWithUV(par2 + var31 + par8, par4, par6 + var31 + par10, var25, var26);
        var13.addVertexWithUV(par2 - var31 + par8, par4, par6 + var31 + par10, var23, var26);
        var13.addVertexWithUV(par2 - var31 + par8, par4, par6 - var31 + par10, var23, var24);
        var13.addVertexWithUV(par2 - var31, par4 + 1.0, var29, var15, var16);
        var13.addVertexWithUV(par2 - var31 + par8, par4 + 0.0, var29 + par10, var15, var18);
        var13.addVertexWithUV(par2 - var31 + par8, par4 + 0.0, var30 + par10, var17, var18);
        var13.addVertexWithUV(par2 - var31, par4 + 1.0, var30, var17, var16);
        var13.addVertexWithUV(par2 + var31, par4 + 1.0, var30, var15, var16);
        var13.addVertexWithUV(par2 + par8 + var31, par4 + 0.0, var30 + par10, var15, var18);
        var13.addVertexWithUV(par2 + par8 + var31, par4 + 0.0, var29 + par10, var17, var18);
        var13.addVertexWithUV(par2 + var31, par4 + 1.0, var29, var17, var16);
        var13.addVertexWithUV(var27, par4 + 1.0, par6 + var31, var15, var16);
        var13.addVertexWithUV(var27 + par8, par4 + 0.0, par6 + var31 + par10, var15, var18);
        var13.addVertexWithUV(var28 + par8, par4 + 0.0, par6 + var31 + par10, var17, var18);
        var13.addVertexWithUV(var28, par4 + 1.0, par6 + var31, var17, var16);
        var13.addVertexWithUV(var28, par4 + 1.0, par6 - var31, var15, var16);
        var13.addVertexWithUV(var28 + par8, par4 + 0.0, par6 - var31 + par10, var15, var18);
        var13.addVertexWithUV(var27 + par8, par4 + 0.0, par6 - var31 + par10, var17, var18);
        var13.addVertexWithUV(var27, par4 + 1.0, par6 - var31, var17, var16);
    }
    
    public void drawCrossedSquares(final Block par1Block, final int par2, final double par3, final double par5, final double par7, final float par9) {
        final Tessellator var10 = Tessellator.instance;
        Icon var11 = this.getBlockIconFromSideAndMetadata(par1Block, 0, par2);
        if (this.hasOverrideBlockTexture()) {
            var11 = this.overrideBlockTexture;
        }
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null) {
            var11 = ConnectedTextures.getConnectedTexture(this.blockAccess, par1Block, (int)par3, (int)par5, (int)par7, -1, var11);
        }
        final double var12 = var11.getMinU();
        final double var13 = var11.getMinV();
        final double var14 = var11.getMaxU();
        final double var15 = var11.getMaxV();
        final double var16 = 0.45 * par9;
        final double var17 = par3 + 0.5 - var16;
        final double var18 = par3 + 0.5 + var16;
        final double var19 = par7 + 0.5 - var16;
        final double var20 = par7 + 0.5 + var16;
        var10.addVertexWithUV(var17, par5 + par9, var19, var12, var13);
        var10.addVertexWithUV(var17, par5 + 0.0, var19, var12, var15);
        var10.addVertexWithUV(var18, par5 + 0.0, var20, var14, var15);
        var10.addVertexWithUV(var18, par5 + par9, var20, var14, var13);
        var10.addVertexWithUV(var18, par5 + par9, var20, var12, var13);
        var10.addVertexWithUV(var18, par5 + 0.0, var20, var12, var15);
        var10.addVertexWithUV(var17, par5 + 0.0, var19, var14, var15);
        var10.addVertexWithUV(var17, par5 + par9, var19, var14, var13);
        var10.addVertexWithUV(var17, par5 + par9, var20, var12, var13);
        var10.addVertexWithUV(var17, par5 + 0.0, var20, var12, var15);
        var10.addVertexWithUV(var18, par5 + 0.0, var19, var14, var15);
        var10.addVertexWithUV(var18, par5 + par9, var19, var14, var13);
        var10.addVertexWithUV(var18, par5 + par9, var19, var12, var13);
        var10.addVertexWithUV(var18, par5 + 0.0, var19, var12, var15);
        var10.addVertexWithUV(var17, par5 + 0.0, var20, var14, var15);
        var10.addVertexWithUV(var17, par5 + par9, var20, var14, var13);
    }
    
    public void renderBlockStemSmall(final Block par1Block, final int par2, final double par3, final double par5, final double par7, final double par9) {
        final Tessellator var11 = Tessellator.instance;
        Icon var12 = this.getBlockIconFromSideAndMetadata(par1Block, 0, par2);
        if (this.hasOverrideBlockTexture()) {
            var12 = this.overrideBlockTexture;
        }
        final double var13 = var12.getMinU();
        final double var14 = var12.getMinV();
        final double var15 = var12.getMaxU();
        final double var16 = var12.getInterpolatedV(par3 * 16.0);
        final double var17 = par5 + 0.5 - 0.44999998807907104;
        final double var18 = par5 + 0.5 + 0.44999998807907104;
        final double var19 = par9 + 0.5 - 0.44999998807907104;
        final double var20 = par9 + 0.5 + 0.44999998807907104;
        var11.addVertexWithUV(var17, par7 + par3, var19, var13, var14);
        var11.addVertexWithUV(var17, par7 + 0.0, var19, var13, var16);
        var11.addVertexWithUV(var18, par7 + 0.0, var20, var15, var16);
        var11.addVertexWithUV(var18, par7 + par3, var20, var15, var14);
        var11.addVertexWithUV(var18, par7 + par3, var20, var13, var14);
        var11.addVertexWithUV(var18, par7 + 0.0, var20, var13, var16);
        var11.addVertexWithUV(var17, par7 + 0.0, var19, var15, var16);
        var11.addVertexWithUV(var17, par7 + par3, var19, var15, var14);
        var11.addVertexWithUV(var17, par7 + par3, var20, var13, var14);
        var11.addVertexWithUV(var17, par7 + 0.0, var20, var13, var16);
        var11.addVertexWithUV(var18, par7 + 0.0, var19, var15, var16);
        var11.addVertexWithUV(var18, par7 + par3, var19, var15, var14);
        var11.addVertexWithUV(var18, par7 + par3, var19, var13, var14);
        var11.addVertexWithUV(var18, par7 + 0.0, var19, var13, var16);
        var11.addVertexWithUV(var17, par7 + 0.0, var20, var15, var16);
        var11.addVertexWithUV(var17, par7 + par3, var20, var15, var14);
    }
    
    public boolean renderBlockLilyPad(final Block par1Block, final int par2, final int par3, final int par4) {
        final Tessellator var5 = Tessellator.instance;
        Icon var6 = this.getBlockIconFromSide(par1Block, 1);
        if (this.hasOverrideBlockTexture()) {
            var6 = this.overrideBlockTexture;
        }
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null) {
            var6 = ConnectedTextures.getConnectedTexture(this.blockAccess, par1Block, par2, par3, par4, -1, var6);
        }
        final float var7 = 0.015625f;
        final double var8 = var6.getMinU();
        final double var9 = var6.getMinV();
        final double var10 = var6.getMaxU();
        final double var11 = var6.getMaxV();
        long var12 = par2 * 3129871 ^ par4 * 116129781L ^ par3;
        var12 = var12 * var12 * 42317861L + var12 * 11L;
        final int var13 = (int)(var12 >> 16 & 0x3L);
        var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        final float var14 = par2 + 0.5f;
        final float var15 = par4 + 0.5f;
        final float var16 = (var13 & 0x1) * 0.5f * (1 - var13 / 2 % 2 * 2);
        final float var17 = (var13 + 1 & 0x1) * 0.5f * (1 - (var13 + 1) / 2 % 2 * 2);
        final int var18 = CustomColorizer.getLilypadColor();
        var5.setColorOpaque_I(var18);
        var5.addVertexWithUV(var14 + var16 - var17, par3 + var7, var15 + var16 + var17, var8, var9);
        var5.addVertexWithUV(var14 + var16 + var17, par3 + var7, var15 - var16 + var17, var10, var9);
        var5.addVertexWithUV(var14 - var16 + var17, par3 + var7, var15 - var16 - var17, var10, var11);
        var5.addVertexWithUV(var14 - var16 - var17, par3 + var7, var15 + var16 - var17, var8, var11);
        var5.setColorOpaque_I((var18 & 0xFEFEFE) >> 1);
        var5.addVertexWithUV(var14 - var16 - var17, par3 + var7, var15 + var16 - var17, var8, var11);
        var5.addVertexWithUV(var14 - var16 + var17, par3 + var7, var15 - var16 - var17, var10, var11);
        var5.addVertexWithUV(var14 + var16 + var17, par3 + var7, var15 - var16 + var17, var10, var9);
        var5.addVertexWithUV(var14 + var16 - var17, par3 + var7, var15 + var16 + var17, var8, var9);
        return true;
    }
    
    public void renderBlockStemBig(final BlockStem par1BlockStem, final int par2, final int par3, final double par4, final double par6, final double par8, final double par10) {
        final Tessellator var12 = Tessellator.instance;
        Icon var13 = par1BlockStem.func_94368_p();
        if (this.hasOverrideBlockTexture()) {
            var13 = this.overrideBlockTexture;
        }
        double var14 = var13.getMinU();
        final double var15 = var13.getMinV();
        double var16 = var13.getMaxU();
        final double var17 = var13.getMaxV();
        final double var18 = par6 + 0.5 - 0.5;
        final double var19 = par6 + 0.5 + 0.5;
        final double var20 = par10 + 0.5 - 0.5;
        final double var21 = par10 + 0.5 + 0.5;
        final double var22 = par6 + 0.5;
        final double var23 = par10 + 0.5;
        if ((par3 + 1) / 2 % 2 == 1) {
            final double var24 = var16;
            var16 = var14;
            var14 = var24;
        }
        if (par3 < 2) {
            var12.addVertexWithUV(var18, par8 + par4, var23, var14, var15);
            var12.addVertexWithUV(var18, par8 + 0.0, var23, var14, var17);
            var12.addVertexWithUV(var19, par8 + 0.0, var23, var16, var17);
            var12.addVertexWithUV(var19, par8 + par4, var23, var16, var15);
            var12.addVertexWithUV(var19, par8 + par4, var23, var16, var15);
            var12.addVertexWithUV(var19, par8 + 0.0, var23, var16, var17);
            var12.addVertexWithUV(var18, par8 + 0.0, var23, var14, var17);
            var12.addVertexWithUV(var18, par8 + par4, var23, var14, var15);
        }
        else {
            var12.addVertexWithUV(var22, par8 + par4, var21, var14, var15);
            var12.addVertexWithUV(var22, par8 + 0.0, var21, var14, var17);
            var12.addVertexWithUV(var22, par8 + 0.0, var20, var16, var17);
            var12.addVertexWithUV(var22, par8 + par4, var20, var16, var15);
            var12.addVertexWithUV(var22, par8 + par4, var20, var16, var15);
            var12.addVertexWithUV(var22, par8 + 0.0, var20, var16, var17);
            var12.addVertexWithUV(var22, par8 + 0.0, var21, var14, var17);
            var12.addVertexWithUV(var22, par8 + par4, var21, var14, var15);
        }
    }
    
    public void renderBlockCropsImpl(final Block par1Block, final int par2, final double par3, final double par5, final double par7) {
        final Tessellator var9 = Tessellator.instance;
        Icon var10 = this.getBlockIconFromSideAndMetadata(par1Block, 0, par2);
        if (this.hasOverrideBlockTexture()) {
            var10 = this.overrideBlockTexture;
        }
        final double var11 = var10.getMinU();
        final double var12 = var10.getMinV();
        final double var13 = var10.getMaxU();
        final double var14 = var10.getMaxV();
        double var15 = par3 + 0.5 - 0.25;
        double var16 = par3 + 0.5 + 0.25;
        double var17 = par7 + 0.5 - 0.5;
        double var18 = par7 + 0.5 + 0.5;
        var9.addVertexWithUV(var15, par5 + 1.0, var17, var11, var12);
        var9.addVertexWithUV(var15, par5 + 0.0, var17, var11, var14);
        var9.addVertexWithUV(var15, par5 + 0.0, var18, var13, var14);
        var9.addVertexWithUV(var15, par5 + 1.0, var18, var13, var12);
        var9.addVertexWithUV(var15, par5 + 1.0, var18, var11, var12);
        var9.addVertexWithUV(var15, par5 + 0.0, var18, var11, var14);
        var9.addVertexWithUV(var15, par5 + 0.0, var17, var13, var14);
        var9.addVertexWithUV(var15, par5 + 1.0, var17, var13, var12);
        var9.addVertexWithUV(var16, par5 + 1.0, var18, var11, var12);
        var9.addVertexWithUV(var16, par5 + 0.0, var18, var11, var14);
        var9.addVertexWithUV(var16, par5 + 0.0, var17, var13, var14);
        var9.addVertexWithUV(var16, par5 + 1.0, var17, var13, var12);
        var9.addVertexWithUV(var16, par5 + 1.0, var17, var11, var12);
        var9.addVertexWithUV(var16, par5 + 0.0, var17, var11, var14);
        var9.addVertexWithUV(var16, par5 + 0.0, var18, var13, var14);
        var9.addVertexWithUV(var16, par5 + 1.0, var18, var13, var12);
        var15 = par3 + 0.5 - 0.5;
        var16 = par3 + 0.5 + 0.5;
        var17 = par7 + 0.5 - 0.25;
        var18 = par7 + 0.5 + 0.25;
        var9.addVertexWithUV(var15, par5 + 1.0, var17, var11, var12);
        var9.addVertexWithUV(var15, par5 + 0.0, var17, var11, var14);
        var9.addVertexWithUV(var16, par5 + 0.0, var17, var13, var14);
        var9.addVertexWithUV(var16, par5 + 1.0, var17, var13, var12);
        var9.addVertexWithUV(var16, par5 + 1.0, var17, var11, var12);
        var9.addVertexWithUV(var16, par5 + 0.0, var17, var11, var14);
        var9.addVertexWithUV(var15, par5 + 0.0, var17, var13, var14);
        var9.addVertexWithUV(var15, par5 + 1.0, var17, var13, var12);
        var9.addVertexWithUV(var16, par5 + 1.0, var18, var11, var12);
        var9.addVertexWithUV(var16, par5 + 0.0, var18, var11, var14);
        var9.addVertexWithUV(var15, par5 + 0.0, var18, var13, var14);
        var9.addVertexWithUV(var15, par5 + 1.0, var18, var13, var12);
        var9.addVertexWithUV(var15, par5 + 1.0, var18, var11, var12);
        var9.addVertexWithUV(var15, par5 + 0.0, var18, var11, var14);
        var9.addVertexWithUV(var16, par5 + 0.0, var18, var13, var14);
        var9.addVertexWithUV(var16, par5 + 1.0, var18, var13, var12);
    }
    
    public boolean renderBlockFluids(final Block par1Block, final int par2, final int par3, final int par4) {
        final Tessellator var5 = Tessellator.instance;
        final int var6 = CustomColorizer.getFluidColor(par1Block, this.blockAccess, par2, par3, par4);
        final float var7 = (var6 >> 16 & 0xFF) / 255.0f;
        final float var8 = (var6 >> 8 & 0xFF) / 255.0f;
        final float var9 = (var6 & 0xFF) / 255.0f;
        final boolean var10 = par1Block.shouldSideBeRendered(this.blockAccess, par2, par3 + 1, par4, 1);
        final boolean var11 = par1Block.shouldSideBeRendered(this.blockAccess, par2, par3 - 1, par4, 0);
        final boolean[] var12 = { par1Block.shouldSideBeRendered(this.blockAccess, par2, par3, par4 - 1, 2), par1Block.shouldSideBeRendered(this.blockAccess, par2, par3, par4 + 1, 3), par1Block.shouldSideBeRendered(this.blockAccess, par2 - 1, par3, par4, 4), par1Block.shouldSideBeRendered(this.blockAccess, par2 + 1, par3, par4, 5) };
        if (!var10 && !var11 && !var12[0] && !var12[1] && !var12[2] && !var12[3]) {
            return false;
        }
        boolean var13 = false;
        final float var14 = 0.5f;
        final float var15 = 1.0f;
        final float var16 = 0.8f;
        final float var17 = 0.6f;
        final double var18 = 0.0;
        final double var19 = 1.0;
        final Material var20 = par1Block.blockMaterial;
        final int var21 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        double var22 = this.getFluidHeight(par2, par3, par4, var20);
        double var23 = this.getFluidHeight(par2, par3, par4 + 1, var20);
        double var24 = this.getFluidHeight(par2 + 1, par3, par4 + 1, var20);
        double var25 = this.getFluidHeight(par2 + 1, par3, par4, var20);
        final double var26 = 0.0010000000474974513;
        if (this.renderAllFaces || var10) {
            var13 = true;
            Icon var27 = this.getBlockIconFromSideAndMetadata(par1Block, 1, var21);
            final float var28 = (float)BlockFluid.getFlowDirection(this.blockAccess, par2, par3, par4, var20);
            if (var28 > -999.0f) {
                var27 = this.getBlockIconFromSideAndMetadata(par1Block, 2, var21);
            }
            var22 -= var26;
            var23 -= var26;
            var24 -= var26;
            var25 -= var26;
            double var29;
            double var30;
            double var31;
            double var32;
            double var33;
            double var34;
            double var35;
            double var36;
            if (var28 < -999.0f) {
                var29 = var27.getInterpolatedU(0.0);
                var30 = var27.getInterpolatedV(0.0);
                var31 = var29;
                var32 = var27.getInterpolatedV(16.0);
                var33 = var27.getInterpolatedU(16.0);
                var34 = var32;
                var35 = var33;
                var36 = var30;
            }
            else {
                final float var37 = MathHelper.sin(var28) * 0.25f;
                final float var38 = MathHelper.cos(var28) * 0.25f;
                var29 = var27.getInterpolatedU(8.0f + (-var38 - var37) * 16.0f);
                var30 = var27.getInterpolatedV(8.0f + (-var38 + var37) * 16.0f);
                var31 = var27.getInterpolatedU(8.0f + (-var38 + var37) * 16.0f);
                var32 = var27.getInterpolatedV(8.0f + (var38 + var37) * 16.0f);
                var33 = var27.getInterpolatedU(8.0f + (var38 + var37) * 16.0f);
                var34 = var27.getInterpolatedV(8.0f + (var38 - var37) * 16.0f);
                var35 = var27.getInterpolatedU(8.0f + (var38 - var37) * 16.0f);
                var36 = var27.getInterpolatedV(8.0f + (-var38 - var37) * 16.0f);
            }
            var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
            final float var37 = 1.0f;
            var5.setColorOpaque_F(var15 * var37 * var7, var15 * var37 * var8, var15 * var37 * var9);
            final double var39 = 3.90625E-5;
            var5.addVertexWithUV(par2 + 0, par3 + var22, par4 + 0, var29 + var39, var30 + var39);
            var5.addVertexWithUV(par2 + 0, par3 + var23, par4 + 1, var31 + var39, var32 - var39);
            var5.addVertexWithUV(par2 + 1, par3 + var24, par4 + 1, var33 - var39, var34 - var39);
            var5.addVertexWithUV(par2 + 1, par3 + var25, par4 + 0, var35 - var39, var36 + var39);
        }
        if (this.renderAllFaces || var11) {
            var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4));
            final float var40 = 1.0f;
            var5.setColorOpaque_F(var14 * var40 * var7, var14 * var40 * var8, var14 * var40 * var9);
            this.renderFaceYNeg(par1Block, par2, par3 + var26, par4, this.getBlockIconFromSide(par1Block, 0));
            var13 = true;
        }
        for (int var41 = 0; var41 < 4; ++var41) {
            int var42 = par2;
            int var43 = par4;
            if (var41 == 0) {
                var43 = par4 - 1;
            }
            if (var41 == 1) {
                ++var43;
            }
            if (var41 == 2) {
                var42 = par2 - 1;
            }
            if (var41 == 3) {
                ++var42;
            }
            final Icon var44 = this.getBlockIconFromSideAndMetadata(par1Block, var41 + 2, var21);
            if (this.renderAllFaces || var12[var41]) {
                double var29;
                double var30;
                double var32;
                double var33;
                double var35;
                double var36;
                if (var41 == 0) {
                    var29 = var22;
                    var33 = var25;
                    var35 = par2;
                    var32 = par2 + 1;
                    var30 = par4 + var26;
                    var36 = par4 + var26;
                }
                else if (var41 == 1) {
                    var29 = var24;
                    var33 = var23;
                    var35 = par2 + 1;
                    var32 = par2;
                    var30 = par4 + 1 - var26;
                    var36 = par4 + 1 - var26;
                }
                else if (var41 == 2) {
                    var29 = var23;
                    var33 = var22;
                    var35 = par2 + var26;
                    var32 = par2 + var26;
                    var30 = par4 + 1;
                    var36 = par4;
                }
                else {
                    var29 = var25;
                    var33 = var24;
                    var35 = par2 + 1 - var26;
                    var32 = par2 + 1 - var26;
                    var30 = par4;
                    var36 = par4 + 1;
                }
                var13 = true;
                final float var45 = var44.getInterpolatedU(0.0);
                final float var37 = var44.getInterpolatedU(8.0);
                final float var38 = var44.getInterpolatedV((1.0 - var29) * 16.0 * 0.5);
                final float var46 = var44.getInterpolatedV((1.0 - var33) * 16.0 * 0.5);
                final float var47 = var44.getInterpolatedV(8.0);
                var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, var42, par3, var43));
                float var48 = 1.0f;
                if (var41 < 2) {
                    var48 *= var16;
                }
                else {
                    var48 *= var17;
                }
                var5.setColorOpaque_F(var15 * var48 * var7, var15 * var48 * var8, var15 * var48 * var9);
                var5.addVertexWithUV(var35, par3 + var29, var30, var45, var38);
                var5.addVertexWithUV(var32, par3 + var33, var36, var37, var46);
                var5.addVertexWithUV(var32, par3 + 0, var36, var37, var47);
                var5.addVertexWithUV(var35, par3 + 0, var30, var45, var47);
            }
        }
        this.renderMinY = var18;
        this.renderMaxY = var19;
        return var13;
    }
    
    public float getFluidHeight(final int par1, final int par2, final int par3, final Material par4Material) {
        int var5 = 0;
        float var6 = 0.0f;
        for (int var7 = 0; var7 < 4; ++var7) {
            final int var8 = par1 - (var7 & 0x1);
            final int var9 = par3 - (var7 >> 1 & 0x1);
            if (this.blockAccess.getBlockMaterial(var8, par2 + 1, var9) == par4Material) {
                return 1.0f;
            }
            final Material var10 = this.blockAccess.getBlockMaterial(var8, par2, var9);
            if (var10 == par4Material) {
                final int var11 = this.blockAccess.getBlockMetadata(var8, par2, var9);
                if (var11 >= 8 || var11 == 0) {
                    var6 += BlockFluid.getFluidHeightPercent(var11) * 10.0f;
                    var5 += 10;
                }
                var6 += BlockFluid.getFluidHeightPercent(var11);
                ++var5;
            }
            else if (!var10.isSolid()) {
                ++var6;
                ++var5;
            }
        }
        return 1.0f - var6 / var5;
    }
    
    public void renderBlockSandFalling(final Block par1Block, final World par2World, final int par3, final int par4, final int par5, final int par6) {
        final float var7 = 0.5f;
        final float var8 = 1.0f;
        final float var9 = 0.8f;
        final float var10 = 0.6f;
        final Tessellator var11 = Tessellator.instance;
        var11.startDrawingQuads();
        var11.setBrightness(par1Block.getMixedBrightnessForBlock(par2World, par3, par4, par5));
        final float var12 = 1.0f;
        float var13 = 1.0f;
        if (var13 < var12) {
            var13 = var12;
        }
        var11.setColorOpaque_F(var7 * var13, var7 * var13, var7 * var13);
        this.renderFaceYNeg(par1Block, -0.5, -0.5, -0.5, this.getBlockIconFromSideAndMetadata(par1Block, 0, par6));
        var13 = 1.0f;
        if (var13 < var12) {
            var13 = var12;
        }
        var11.setColorOpaque_F(var8 * var13, var8 * var13, var8 * var13);
        this.renderFaceYPos(par1Block, -0.5, -0.5, -0.5, this.getBlockIconFromSideAndMetadata(par1Block, 1, par6));
        var13 = 1.0f;
        if (var13 < var12) {
            var13 = var12;
        }
        var11.setColorOpaque_F(var9 * var13, var9 * var13, var9 * var13);
        this.renderFaceZNeg(par1Block, -0.5, -0.5, -0.5, this.getBlockIconFromSideAndMetadata(par1Block, 2, par6));
        var13 = 1.0f;
        if (var13 < var12) {
            var13 = var12;
        }
        var11.setColorOpaque_F(var9 * var13, var9 * var13, var9 * var13);
        this.renderFaceZPos(par1Block, -0.5, -0.5, -0.5, this.getBlockIconFromSideAndMetadata(par1Block, 3, par6));
        var13 = 1.0f;
        if (var13 < var12) {
            var13 = var12;
        }
        var11.setColorOpaque_F(var10 * var13, var10 * var13, var10 * var13);
        this.renderFaceXNeg(par1Block, -0.5, -0.5, -0.5, this.getBlockIconFromSideAndMetadata(par1Block, 4, par6));
        var13 = 1.0f;
        if (var13 < var12) {
            var13 = var12;
        }
        var11.setColorOpaque_F(var10 * var13, var10 * var13, var10 * var13);
        this.renderFaceXPos(par1Block, -0.5, -0.5, -0.5, this.getBlockIconFromSideAndMetadata(par1Block, 5, par6));
        var11.draw();
    }
    
    public boolean renderStandardBlock(final Block par1Block, final int par2, final int par3, final int par4) {
        final int var5 = CustomColorizer.getColorMultiplier(par1Block, this.blockAccess, par2, par3, par4);
        float var6 = (var5 >> 16 & 0xFF) / 255.0f;
        float var7 = (var5 >> 8 & 0xFF) / 255.0f;
        float var8 = (var5 & 0xFF) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            final float var9 = (var6 * 30.0f + var7 * 59.0f + var8 * 11.0f) / 100.0f;
            final float var10 = (var6 * 30.0f + var7 * 70.0f) / 100.0f;
            final float var11 = (var6 * 30.0f + var8 * 70.0f) / 100.0f;
            var6 = var9;
            var7 = var10;
            var8 = var11;
        }
        return (Minecraft.isAmbientOcclusionEnabled() && Block.lightValue[par1Block.blockID] == 0) ? (this.partialRenderBounds ? this.func_102027_b(par1Block, par2, par3, par4, var6, var7, var8) : this.renderStandardBlockWithAmbientOcclusion(par1Block, par2, par3, par4, var6, var7, var8)) : this.renderStandardBlockWithColorMultiplier(par1Block, par2, par3, par4, var6, var7, var8);
    }
    
    public boolean renderBlockLog(final Block par1Block, final int par2, final int par3, final int par4) {
        final int var5 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        final int var6 = var5 & 0xC;
        if (var6 == 4) {
            this.uvRotateEast = 1;
            this.uvRotateWest = 1;
            this.uvRotateTop = 1;
            this.uvRotateBottom = 1;
        }
        else if (var6 == 8) {
            this.uvRotateSouth = 1;
            this.uvRotateNorth = 1;
        }
        final boolean var7 = this.renderStandardBlock(par1Block, par2, par3, par4);
        this.uvRotateSouth = 0;
        this.uvRotateEast = 0;
        this.uvRotateWest = 0;
        this.uvRotateNorth = 0;
        this.uvRotateTop = 0;
        this.uvRotateBottom = 0;
        return var7;
    }
    
    public boolean renderBlockQuartz(final Block par1Block, final int par2, final int par3, final int par4) {
        final int var5 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        if (var5 == 3) {
            this.uvRotateEast = 1;
            this.uvRotateWest = 1;
            this.uvRotateTop = 1;
            this.uvRotateBottom = 1;
        }
        else if (var5 == 4) {
            this.uvRotateSouth = 1;
            this.uvRotateNorth = 1;
        }
        final boolean var6 = this.renderStandardBlock(par1Block, par2, par3, par4);
        this.uvRotateSouth = 0;
        this.uvRotateEast = 0;
        this.uvRotateWest = 0;
        this.uvRotateNorth = 0;
        this.uvRotateTop = 0;
        this.uvRotateBottom = 0;
        return var6;
    }
    
    public boolean renderStandardBlockWithAmbientOcclusion(final Block par1Block, int par2, int par3, int par4, final float par5, final float par6, final float par7) {
        this.enableAO = true;
        final boolean var8 = Tessellator.instance.defaultTexture;
        final boolean var9 = Config.isBetterGrass() && var8;
        final boolean var10 = par1Block == Block.glass;
        boolean var11 = false;
        float var12 = 0.0f;
        float var13 = 0.0f;
        float var14 = 0.0f;
        float var15 = 0.0f;
        boolean var16 = true;
        int var17 = -1;
        final Tessellator var18 = Tessellator.instance;
        var18.setBrightness(983055);
        if (par1Block == Block.grass) {
            var16 = false;
        }
        else if (this.hasOverrideBlockTexture()) {
            var16 = false;
        }
        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3 - 1, par4, 0)) {
            if (this.renderMinY <= 0.0) {
                --par3;
            }
            this.aoBrightnessXYNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
            this.aoBrightnessYZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
            this.aoBrightnessYZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
            this.aoBrightnessXYPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);
            this.aoLightValueScratchXYNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4);
            this.aoLightValueScratchYZNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 - 1);
            this.aoLightValueScratchYZNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 + 1);
            this.aoLightValueScratchXYPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4);
            final boolean var19 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3 - 1, par4)];
            final boolean var20 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3 - 1, par4)];
            final boolean var21 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 - 1, par4 + 1)];
            final boolean var22 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 - 1, par4 - 1)];
            if (!var22 && !var20) {
                this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXYNN;
                this.aoBrightnessXYZNNN = this.aoBrightnessXYNN;
            }
            else {
                this.aoLightValueScratchXYZNNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4 - 1);
                this.aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4 - 1);
            }
            if (!var21 && !var20) {
                this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXYNN;
                this.aoBrightnessXYZNNP = this.aoBrightnessXYNN;
            }
            else {
                this.aoLightValueScratchXYZNNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4 + 1);
                this.aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4 + 1);
            }
            if (!var22 && !var19) {
                this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXYPN;
                this.aoBrightnessXYZPNN = this.aoBrightnessXYPN;
            }
            else {
                this.aoLightValueScratchXYZPNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4 - 1);
                this.aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4 - 1);
            }
            if (!var21 && !var19) {
                this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXYPN;
                this.aoBrightnessXYZPNP = this.aoBrightnessXYPN;
            }
            else {
                this.aoLightValueScratchXYZPNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4 + 1);
                this.aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4 + 1);
            }
            if (this.renderMinY <= 0.0) {
                ++par3;
            }
            if (var17 < 0) {
                var17 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
            }
            int var23 = var17;
            if (this.renderMinY <= 0.0 || !this.blockAccess.isBlockOpaqueCube(par2, par3 - 1, par4)) {
                var23 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
            }
            final float var24 = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4);
            var12 = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXYNN + this.aoLightValueScratchYZNP + var24) / 4.0f;
            var15 = (this.aoLightValueScratchYZNP + var24 + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXYPN) / 4.0f;
            var14 = (var24 + this.aoLightValueScratchYZNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNN) / 4.0f;
            var13 = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNN + var24 + this.aoLightValueScratchYZNN) / 4.0f;
            this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXYNN, this.aoBrightnessYZNP, var23);
            this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXYPN, var23);
            this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYPN, this.aoBrightnessXYZPNN, var23);
            this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNN, this.aoBrightnessYZNN, var23);
            if (var10) {
                var13 = var24;
                var14 = var24;
                var15 = var24;
                var12 = var24;
                final int n = var23;
                this.brightnessBottomLeft = n;
                this.brightnessBottomRight = n;
                this.brightnessTopRight = n;
                this.brightnessTopLeft = n;
            }
            if (var16) {
                final float n2 = par5 * 0.5f;
                this.colorRedTopRight = n2;
                this.colorRedBottomRight = n2;
                this.colorRedBottomLeft = n2;
                this.colorRedTopLeft = n2;
                final float n3 = par6 * 0.5f;
                this.colorGreenTopRight = n3;
                this.colorGreenBottomRight = n3;
                this.colorGreenBottomLeft = n3;
                this.colorGreenTopLeft = n3;
                final float n4 = par7 * 0.5f;
                this.colorBlueTopRight = n4;
                this.colorBlueBottomRight = n4;
                this.colorBlueBottomLeft = n4;
                this.colorBlueTopLeft = n4;
            }
            else {
                final float n5 = 0.5f;
                this.colorRedTopRight = n5;
                this.colorRedBottomRight = n5;
                this.colorRedBottomLeft = n5;
                this.colorRedTopLeft = n5;
                final float n6 = 0.5f;
                this.colorGreenTopRight = n6;
                this.colorGreenBottomRight = n6;
                this.colorGreenBottomLeft = n6;
                this.colorGreenTopLeft = n6;
                final float n7 = 0.5f;
                this.colorBlueTopRight = n7;
                this.colorBlueBottomRight = n7;
                this.colorBlueBottomLeft = n7;
                this.colorBlueTopLeft = n7;
            }
            this.colorRedTopLeft *= var12;
            this.colorGreenTopLeft *= var12;
            this.colorBlueTopLeft *= var12;
            this.colorRedBottomLeft *= var13;
            this.colorGreenBottomLeft *= var13;
            this.colorBlueBottomLeft *= var13;
            this.colorRedBottomRight *= var14;
            this.colorGreenBottomRight *= var14;
            this.colorBlueBottomRight *= var14;
            this.colorRedTopRight *= var15;
            this.colorGreenTopRight *= var15;
            this.colorBlueTopRight *= var15;
            this.renderFaceYNeg(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 0));
            var11 = true;
        }
        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3 + 1, par4, 1)) {
            if (this.renderMaxY >= 1.0) {
                ++par3;
            }
            this.aoBrightnessXYNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
            this.aoBrightnessXYPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);
            this.aoBrightnessYZPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
            this.aoBrightnessYZPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
            this.aoLightValueScratchXYNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4);
            this.aoLightValueScratchXYPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4);
            this.aoLightValueScratchYZPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 - 1);
            this.aoLightValueScratchYZPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 + 1);
            final boolean var19 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3 + 1, par4)];
            final boolean var20 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3 + 1, par4)];
            final boolean var21 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 + 1, par4 + 1)];
            final boolean var22 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 + 1, par4 - 1)];
            if (!var22 && !var20) {
                this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXYNP;
                this.aoBrightnessXYZNPN = this.aoBrightnessXYNP;
            }
            else {
                this.aoLightValueScratchXYZNPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4 - 1);
                this.aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4 - 1);
            }
            if (!var22 && !var19) {
                this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXYPP;
                this.aoBrightnessXYZPPN = this.aoBrightnessXYPP;
            }
            else {
                this.aoLightValueScratchXYZPPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4 - 1);
                this.aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4 - 1);
            }
            if (!var21 && !var20) {
                this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXYNP;
                this.aoBrightnessXYZNPP = this.aoBrightnessXYNP;
            }
            else {
                this.aoLightValueScratchXYZNPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4 + 1);
                this.aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4 + 1);
            }
            if (!var21 && !var19) {
                this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXYPP;
                this.aoBrightnessXYZPPP = this.aoBrightnessXYPP;
            }
            else {
                this.aoLightValueScratchXYZPPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4 + 1);
                this.aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4 + 1);
            }
            if (this.renderMaxY >= 1.0) {
                --par3;
            }
            if (var17 < 0) {
                var17 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
            }
            int var23 = var17;
            if (this.renderMaxY >= 1.0 || !this.blockAccess.isBlockOpaqueCube(par2, par3 + 1, par4)) {
                var23 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);
            }
            final float var24 = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4);
            var15 = (this.aoLightValueScratchXYZNPP + this.aoLightValueScratchXYNP + this.aoLightValueScratchYZPP + var24) / 4.0f;
            var12 = (this.aoLightValueScratchYZPP + var24 + this.aoLightValueScratchXYZPPP + this.aoLightValueScratchXYPP) / 4.0f;
            var13 = (var24 + this.aoLightValueScratchYZPN + this.aoLightValueScratchXYPP + this.aoLightValueScratchXYZPPN) / 4.0f;
            var14 = (this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPN + var24 + this.aoLightValueScratchYZPN) / 4.0f;
            this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXYZNPP, this.aoBrightnessXYNP, this.aoBrightnessYZPP, var23);
            this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXYZPPP, this.aoBrightnessXYPP, var23);
            this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXYPP, this.aoBrightnessXYZPPN, var23);
            this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessXYNP, this.aoBrightnessXYZNPN, this.aoBrightnessYZPN, var23);
            if (var10) {
                var13 = var24;
                var14 = var24;
                var15 = var24;
                var12 = var24;
                final int n8 = var23;
                this.brightnessBottomLeft = n8;
                this.brightnessBottomRight = n8;
                this.brightnessTopRight = n8;
                this.brightnessTopLeft = n8;
            }
            this.colorRedTopRight = par5;
            this.colorRedBottomRight = par5;
            this.colorRedBottomLeft = par5;
            this.colorRedTopLeft = par5;
            this.colorGreenTopRight = par6;
            this.colorGreenBottomRight = par6;
            this.colorGreenBottomLeft = par6;
            this.colorGreenTopLeft = par6;
            this.colorBlueTopRight = par7;
            this.colorBlueBottomRight = par7;
            this.colorBlueBottomLeft = par7;
            this.colorBlueTopLeft = par7;
            this.colorRedTopLeft *= var12;
            this.colorGreenTopLeft *= var12;
            this.colorBlueTopLeft *= var12;
            this.colorRedBottomLeft *= var13;
            this.colorGreenBottomLeft *= var13;
            this.colorBlueBottomLeft *= var13;
            this.colorRedBottomRight *= var14;
            this.colorGreenBottomRight *= var14;
            this.colorBlueBottomRight *= var14;
            this.colorRedTopRight *= var15;
            this.colorGreenTopRight *= var15;
            this.colorBlueTopRight *= var15;
            this.renderFaceYPos(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 1));
            var11 = true;
        }
        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3, par4 - 1, 2)) {
            if (this.renderMinZ <= 0.0) {
                --par4;
            }
            this.aoLightValueScratchXZNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4);
            this.aoLightValueScratchYZNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4);
            this.aoLightValueScratchYZPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4);
            this.aoLightValueScratchXZPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4);
            this.aoBrightnessXZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
            this.aoBrightnessYZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
            this.aoBrightnessYZPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);
            this.aoBrightnessXZPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);
            final boolean var19 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3, par4 - 1)];
            final boolean var20 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3, par4 - 1)];
            final boolean var21 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 + 1, par4 - 1)];
            final boolean var22 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 - 1, par4 - 1)];
            if (!var20 && !var22) {
                this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
                this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
            }
            else {
                this.aoLightValueScratchXYZNNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3 - 1, par4);
                this.aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3 - 1, par4);
            }
            if (!var20 && !var21) {
                this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
                this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
            }
            else {
                this.aoLightValueScratchXYZNPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3 + 1, par4);
                this.aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3 + 1, par4);
            }
            if (!var19 && !var22) {
                this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
                this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
            }
            else {
                this.aoLightValueScratchXYZPNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3 - 1, par4);
                this.aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3 - 1, par4);
            }
            if (!var19 && !var21) {
                this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
                this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
            }
            else {
                this.aoLightValueScratchXYZPPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3 + 1, par4);
                this.aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3 + 1, par4);
            }
            if (this.renderMinZ <= 0.0) {
                ++par4;
            }
            if (var17 < 0) {
                var17 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
            }
            int var23 = var17;
            if (this.renderMinZ <= 0.0 || !this.blockAccess.isBlockOpaqueCube(par2, par3, par4 - 1)) {
                var23 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
            }
            final float var24 = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 - 1);
            var12 = (this.aoLightValueScratchXZNN + this.aoLightValueScratchXYZNPN + var24 + this.aoLightValueScratchYZPN) / 4.0f;
            var13 = (var24 + this.aoLightValueScratchYZPN + this.aoLightValueScratchXZPN + this.aoLightValueScratchXYZPPN) / 4.0f;
            var14 = (this.aoLightValueScratchYZNN + var24 + this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXZPN) / 4.0f;
            var15 = (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXZNN + this.aoLightValueScratchYZNN + var24) / 4.0f;
            this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessYZPN, var23);
            this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, var23);
            this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYZPNN, this.aoBrightnessXZPN, var23);
            this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXZNN, this.aoBrightnessYZNN, var23);
            if (var10) {
                var13 = var24;
                var14 = var24;
                var15 = var24;
                var12 = var24;
                final int n9 = var23;
                this.brightnessBottomLeft = n9;
                this.brightnessBottomRight = n9;
                this.brightnessTopRight = n9;
                this.brightnessTopLeft = n9;
            }
            if (var16) {
                final float n10 = par5 * 0.8f;
                this.colorRedTopRight = n10;
                this.colorRedBottomRight = n10;
                this.colorRedBottomLeft = n10;
                this.colorRedTopLeft = n10;
                final float n11 = par6 * 0.8f;
                this.colorGreenTopRight = n11;
                this.colorGreenBottomRight = n11;
                this.colorGreenBottomLeft = n11;
                this.colorGreenTopLeft = n11;
                final float n12 = par7 * 0.8f;
                this.colorBlueTopRight = n12;
                this.colorBlueBottomRight = n12;
                this.colorBlueBottomLeft = n12;
                this.colorBlueTopLeft = n12;
            }
            else {
                final float n13 = 0.8f;
                this.colorRedTopRight = n13;
                this.colorRedBottomRight = n13;
                this.colorRedBottomLeft = n13;
                this.colorRedTopLeft = n13;
                final float n14 = 0.8f;
                this.colorGreenTopRight = n14;
                this.colorGreenBottomRight = n14;
                this.colorGreenBottomLeft = n14;
                this.colorGreenTopLeft = n14;
                final float n15 = 0.8f;
                this.colorBlueTopRight = n15;
                this.colorBlueBottomRight = n15;
                this.colorBlueBottomLeft = n15;
                this.colorBlueTopLeft = n15;
            }
            this.colorRedTopLeft *= var12;
            this.colorGreenTopLeft *= var12;
            this.colorBlueTopLeft *= var12;
            this.colorRedBottomLeft *= var13;
            this.colorGreenBottomLeft *= var13;
            this.colorBlueBottomLeft *= var13;
            this.colorRedBottomRight *= var14;
            this.colorGreenBottomRight *= var14;
            this.colorBlueBottomRight *= var14;
            this.colorRedTopRight *= var15;
            this.colorGreenTopRight *= var15;
            this.colorBlueTopRight *= var15;
            Icon var25 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 2);
            if (var9) {
                var25 = this.fixAoSideGrassTexture(var25, par2, par3, par4, 2, par5, par6, par7);
            }
            this.renderFaceZNeg(par1Block, par2, par3, par4, var25);
            if (var8 && RenderBlocks.fancyGrass && var25 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture()) {
                this.colorRedTopLeft *= par5;
                this.colorRedBottomLeft *= par5;
                this.colorRedBottomRight *= par5;
                this.colorRedTopRight *= par5;
                this.colorGreenTopLeft *= par6;
                this.colorGreenBottomLeft *= par6;
                this.colorGreenBottomRight *= par6;
                this.colorGreenTopRight *= par6;
                this.colorBlueTopLeft *= par7;
                this.colorBlueBottomLeft *= par7;
                this.colorBlueBottomRight *= par7;
                this.colorBlueTopRight *= par7;
                this.renderFaceZNeg(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
            }
            var11 = true;
        }
        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3, par4 + 1, 3)) {
            if (this.renderMaxZ >= 1.0) {
                ++par4;
            }
            this.aoLightValueScratchXZNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4);
            this.aoLightValueScratchXZPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4);
            this.aoLightValueScratchYZNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4);
            this.aoLightValueScratchYZPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4);
            this.aoBrightnessXZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
            this.aoBrightnessXZPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);
            this.aoBrightnessYZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
            this.aoBrightnessYZPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);
            final boolean var19 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3, par4 + 1)];
            final boolean var20 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3, par4 + 1)];
            final boolean var21 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 + 1, par4 + 1)];
            final boolean var22 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 - 1, par4 + 1)];
            if (!var20 && !var22) {
                this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
                this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
            }
            else {
                this.aoLightValueScratchXYZNNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3 - 1, par4);
                this.aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3 - 1, par4);
            }
            if (!var20 && !var21) {
                this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
                this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
            }
            else {
                this.aoLightValueScratchXYZNPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3 + 1, par4);
                this.aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3 + 1, par4);
            }
            if (!var19 && !var22) {
                this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
                this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
            }
            else {
                this.aoLightValueScratchXYZPNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3 - 1, par4);
                this.aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3 - 1, par4);
            }
            if (!var19 && !var21) {
                this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
                this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
            }
            else {
                this.aoLightValueScratchXYZPPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3 + 1, par4);
                this.aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3 + 1, par4);
            }
            if (this.renderMaxZ >= 1.0) {
                --par4;
            }
            if (var17 < 0) {
                var17 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
            }
            int var23 = var17;
            if (this.renderMaxZ >= 1.0 || !this.blockAccess.isBlockOpaqueCube(par2, par3, par4 + 1)) {
                var23 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
            }
            final float var24 = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 + 1);
            var12 = (this.aoLightValueScratchXZNP + this.aoLightValueScratchXYZNPP + var24 + this.aoLightValueScratchYZPP) / 4.0f;
            var15 = (var24 + this.aoLightValueScratchYZPP + this.aoLightValueScratchXZPP + this.aoLightValueScratchXYZPPP) / 4.0f;
            var14 = (this.aoLightValueScratchYZNP + var24 + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXZPP) / 4.0f;
            var13 = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXZNP + this.aoLightValueScratchYZNP + var24) / 4.0f;
            this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYZNPP, this.aoBrightnessYZPP, var23);
            this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXZPP, this.aoBrightnessXYZPPP, var23);
            this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, var23);
            this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, this.aoBrightnessYZNP, var23);
            if (var10) {
                var13 = var24;
                var14 = var24;
                var15 = var24;
                var12 = var24;
                final int n16 = var23;
                this.brightnessBottomLeft = n16;
                this.brightnessBottomRight = n16;
                this.brightnessTopRight = n16;
                this.brightnessTopLeft = n16;
            }
            if (var16) {
                final float n17 = par5 * 0.8f;
                this.colorRedTopRight = n17;
                this.colorRedBottomRight = n17;
                this.colorRedBottomLeft = n17;
                this.colorRedTopLeft = n17;
                final float n18 = par6 * 0.8f;
                this.colorGreenTopRight = n18;
                this.colorGreenBottomRight = n18;
                this.colorGreenBottomLeft = n18;
                this.colorGreenTopLeft = n18;
                final float n19 = par7 * 0.8f;
                this.colorBlueTopRight = n19;
                this.colorBlueBottomRight = n19;
                this.colorBlueBottomLeft = n19;
                this.colorBlueTopLeft = n19;
            }
            else {
                final float n20 = 0.8f;
                this.colorRedTopRight = n20;
                this.colorRedBottomRight = n20;
                this.colorRedBottomLeft = n20;
                this.colorRedTopLeft = n20;
                final float n21 = 0.8f;
                this.colorGreenTopRight = n21;
                this.colorGreenBottomRight = n21;
                this.colorGreenBottomLeft = n21;
                this.colorGreenTopLeft = n21;
                final float n22 = 0.8f;
                this.colorBlueTopRight = n22;
                this.colorBlueBottomRight = n22;
                this.colorBlueBottomLeft = n22;
                this.colorBlueTopLeft = n22;
            }
            this.colorRedTopLeft *= var12;
            this.colorGreenTopLeft *= var12;
            this.colorBlueTopLeft *= var12;
            this.colorRedBottomLeft *= var13;
            this.colorGreenBottomLeft *= var13;
            this.colorBlueBottomLeft *= var13;
            this.colorRedBottomRight *= var14;
            this.colorGreenBottomRight *= var14;
            this.colorBlueBottomRight *= var14;
            this.colorRedTopRight *= var15;
            this.colorGreenTopRight *= var15;
            this.colorBlueTopRight *= var15;
            Icon var25 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 3);
            if (var9) {
                var25 = this.fixAoSideGrassTexture(var25, par2, par3, par4, 3, par5, par6, par7);
            }
            this.renderFaceZPos(par1Block, par2, par3, par4, var25);
            if (var8 && RenderBlocks.fancyGrass && var25 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture()) {
                this.colorRedTopLeft *= par5;
                this.colorRedBottomLeft *= par5;
                this.colorRedBottomRight *= par5;
                this.colorRedTopRight *= par5;
                this.colorGreenTopLeft *= par6;
                this.colorGreenBottomLeft *= par6;
                this.colorGreenBottomRight *= par6;
                this.colorGreenTopRight *= par6;
                this.colorBlueTopLeft *= par7;
                this.colorBlueBottomLeft *= par7;
                this.colorBlueBottomRight *= par7;
                this.colorBlueTopRight *= par7;
                this.renderFaceZPos(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
            }
            var11 = true;
        }
        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2 - 1, par3, par4, 4)) {
            if (this.renderMinX <= 0.0) {
                --par2;
            }
            this.aoLightValueScratchXYNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4);
            this.aoLightValueScratchXZNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 - 1);
            this.aoLightValueScratchXZNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 + 1);
            this.aoLightValueScratchXYNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4);
            this.aoBrightnessXYNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
            this.aoBrightnessXZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
            this.aoBrightnessXZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
            this.aoBrightnessXYNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);
            final boolean var19 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3 + 1, par4)];
            final boolean var20 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3 - 1, par4)];
            final boolean var21 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3, par4 - 1)];
            final boolean var22 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3, par4 + 1)];
            if (!var21 && !var20) {
                this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
                this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
            }
            else {
                this.aoLightValueScratchXYZNNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4 - 1);
                this.aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4 - 1);
            }
            if (!var22 && !var20) {
                this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
                this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
            }
            else {
                this.aoLightValueScratchXYZNNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4 + 1);
                this.aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4 + 1);
            }
            if (!var21 && !var19) {
                this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
                this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
            }
            else {
                this.aoLightValueScratchXYZNPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4 - 1);
                this.aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4 - 1);
            }
            if (!var22 && !var19) {
                this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
                this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
            }
            else {
                this.aoLightValueScratchXYZNPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4 + 1);
                this.aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4 + 1);
            }
            if (this.renderMinX <= 0.0) {
                ++par2;
            }
            if (var17 < 0) {
                var17 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
            }
            int var23 = var17;
            if (this.renderMinX <= 0.0 || !this.blockAccess.isBlockOpaqueCube(par2 - 1, par3, par4)) {
                var23 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
            }
            final float var24 = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4);
            var15 = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNP + var24 + this.aoLightValueScratchXZNP) / 4.0f;
            var12 = (var24 + this.aoLightValueScratchXZNP + this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPP) / 4.0f;
            var13 = (this.aoLightValueScratchXZNN + var24 + this.aoLightValueScratchXYZNPN + this.aoLightValueScratchXYNP) / 4.0f;
            var14 = (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXYNN + this.aoLightValueScratchXZNN + var24) / 4.0f;
            this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, var23);
            this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYNP, this.aoBrightnessXYZNPP, var23);
            this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessXYNP, var23);
            this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXYNN, this.aoBrightnessXZNN, var23);
            if (var10) {
                var13 = var24;
                var14 = var24;
                var15 = var24;
                var12 = var24;
                final int n23 = var23;
                this.brightnessBottomLeft = n23;
                this.brightnessBottomRight = n23;
                this.brightnessTopRight = n23;
                this.brightnessTopLeft = n23;
            }
            if (var16) {
                final float n24 = par5 * 0.6f;
                this.colorRedTopRight = n24;
                this.colorRedBottomRight = n24;
                this.colorRedBottomLeft = n24;
                this.colorRedTopLeft = n24;
                final float n25 = par6 * 0.6f;
                this.colorGreenTopRight = n25;
                this.colorGreenBottomRight = n25;
                this.colorGreenBottomLeft = n25;
                this.colorGreenTopLeft = n25;
                final float n26 = par7 * 0.6f;
                this.colorBlueTopRight = n26;
                this.colorBlueBottomRight = n26;
                this.colorBlueBottomLeft = n26;
                this.colorBlueTopLeft = n26;
            }
            else {
                final float n27 = 0.6f;
                this.colorRedTopRight = n27;
                this.colorRedBottomRight = n27;
                this.colorRedBottomLeft = n27;
                this.colorRedTopLeft = n27;
                final float n28 = 0.6f;
                this.colorGreenTopRight = n28;
                this.colorGreenBottomRight = n28;
                this.colorGreenBottomLeft = n28;
                this.colorGreenTopLeft = n28;
                final float n29 = 0.6f;
                this.colorBlueTopRight = n29;
                this.colorBlueBottomRight = n29;
                this.colorBlueBottomLeft = n29;
                this.colorBlueTopLeft = n29;
            }
            this.colorRedTopLeft *= var12;
            this.colorGreenTopLeft *= var12;
            this.colorBlueTopLeft *= var12;
            this.colorRedBottomLeft *= var13;
            this.colorGreenBottomLeft *= var13;
            this.colorBlueBottomLeft *= var13;
            this.colorRedBottomRight *= var14;
            this.colorGreenBottomRight *= var14;
            this.colorBlueBottomRight *= var14;
            this.colorRedTopRight *= var15;
            this.colorGreenTopRight *= var15;
            this.colorBlueTopRight *= var15;
            Icon var25 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 4);
            if (var9) {
                var25 = this.fixAoSideGrassTexture(var25, par2, par3, par4, 4, par5, par6, par7);
            }
            this.renderFaceXNeg(par1Block, par2, par3, par4, var25);
            if (var8 && RenderBlocks.fancyGrass && var25 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture()) {
                this.colorRedTopLeft *= par5;
                this.colorRedBottomLeft *= par5;
                this.colorRedBottomRight *= par5;
                this.colorRedTopRight *= par5;
                this.colorGreenTopLeft *= par6;
                this.colorGreenBottomLeft *= par6;
                this.colorGreenBottomRight *= par6;
                this.colorGreenTopRight *= par6;
                this.colorBlueTopLeft *= par7;
                this.colorBlueBottomLeft *= par7;
                this.colorBlueBottomRight *= par7;
                this.colorBlueTopRight *= par7;
                this.renderFaceXNeg(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
            }
            var11 = true;
        }
        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2 + 1, par3, par4, 5)) {
            if (this.renderMaxX >= 1.0) {
                ++par2;
            }
            this.aoLightValueScratchXYPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4);
            this.aoLightValueScratchXZPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 - 1);
            this.aoLightValueScratchXZPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 + 1);
            this.aoLightValueScratchXYPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4);
            this.aoBrightnessXYPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
            this.aoBrightnessXZPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
            this.aoBrightnessXZPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
            this.aoBrightnessXYPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);
            final boolean var19 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3 + 1, par4)];
            final boolean var20 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3 - 1, par4)];
            final boolean var21 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3, par4 + 1)];
            final boolean var22 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3, par4 - 1)];
            if (!var20 && !var22) {
                this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
                this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
            }
            else {
                this.aoLightValueScratchXYZPNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4 - 1);
                this.aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4 - 1);
            }
            if (!var20 && !var21) {
                this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
                this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
            }
            else {
                this.aoLightValueScratchXYZPNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4 + 1);
                this.aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4 + 1);
            }
            if (!var19 && !var22) {
                this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
                this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
            }
            else {
                this.aoLightValueScratchXYZPPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4 - 1);
                this.aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4 - 1);
            }
            if (!var19 && !var21) {
                this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
                this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
            }
            else {
                this.aoLightValueScratchXYZPPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4 + 1);
                this.aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4 + 1);
            }
            if (this.renderMaxX >= 1.0) {
                --par2;
            }
            if (var17 < 0) {
                var17 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
            }
            int var23 = var17;
            if (this.renderMaxX >= 1.0 || !this.blockAccess.isBlockOpaqueCube(par2 + 1, par3, par4)) {
                var23 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);
            }
            final float var24 = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4);
            var12 = (this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNP + var24 + this.aoLightValueScratchXZPP) / 4.0f;
            var13 = (this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXZPN + var24) / 4.0f;
            var14 = (this.aoLightValueScratchXZPN + var24 + this.aoLightValueScratchXYZPPN + this.aoLightValueScratchXYPP) / 4.0f;
            var15 = (var24 + this.aoLightValueScratchXZPP + this.aoLightValueScratchXYPP + this.aoLightValueScratchXYZPPP) / 4.0f;
            this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXYPN, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, var23);
            this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXZPP, this.aoBrightnessXYPP, this.aoBrightnessXYZPPP, var23);
            this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, this.aoBrightnessXYPP, var23);
            this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXYZPNN, this.aoBrightnessXYPN, this.aoBrightnessXZPN, var23);
            if (var10) {
                var13 = var24;
                var14 = var24;
                var15 = var24;
                var12 = var24;
                final int n30 = var23;
                this.brightnessBottomLeft = n30;
                this.brightnessBottomRight = n30;
                this.brightnessTopRight = n30;
                this.brightnessTopLeft = n30;
            }
            if (var16) {
                final float n31 = par5 * 0.6f;
                this.colorRedTopRight = n31;
                this.colorRedBottomRight = n31;
                this.colorRedBottomLeft = n31;
                this.colorRedTopLeft = n31;
                final float n32 = par6 * 0.6f;
                this.colorGreenTopRight = n32;
                this.colorGreenBottomRight = n32;
                this.colorGreenBottomLeft = n32;
                this.colorGreenTopLeft = n32;
                final float n33 = par7 * 0.6f;
                this.colorBlueTopRight = n33;
                this.colorBlueBottomRight = n33;
                this.colorBlueBottomLeft = n33;
                this.colorBlueTopLeft = n33;
            }
            else {
                final float n34 = 0.6f;
                this.colorRedTopRight = n34;
                this.colorRedBottomRight = n34;
                this.colorRedBottomLeft = n34;
                this.colorRedTopLeft = n34;
                final float n35 = 0.6f;
                this.colorGreenTopRight = n35;
                this.colorGreenBottomRight = n35;
                this.colorGreenBottomLeft = n35;
                this.colorGreenTopLeft = n35;
                final float n36 = 0.6f;
                this.colorBlueTopRight = n36;
                this.colorBlueBottomRight = n36;
                this.colorBlueBottomLeft = n36;
                this.colorBlueTopLeft = n36;
            }
            this.colorRedTopLeft *= var12;
            this.colorGreenTopLeft *= var12;
            this.colorBlueTopLeft *= var12;
            this.colorRedBottomLeft *= var13;
            this.colorGreenBottomLeft *= var13;
            this.colorBlueBottomLeft *= var13;
            this.colorRedBottomRight *= var14;
            this.colorGreenBottomRight *= var14;
            this.colorBlueBottomRight *= var14;
            this.colorRedTopRight *= var15;
            this.colorGreenTopRight *= var15;
            this.colorBlueTopRight *= var15;
            Icon var25 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 5);
            if (var9) {
                var25 = this.fixAoSideGrassTexture(var25, par2, par3, par4, 5, par5, par6, par7);
            }
            this.renderFaceXPos(par1Block, par2, par3, par4, var25);
            if (var8 && RenderBlocks.fancyGrass && var25 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture()) {
                this.colorRedTopLeft *= par5;
                this.colorRedBottomLeft *= par5;
                this.colorRedBottomRight *= par5;
                this.colorRedTopRight *= par5;
                this.colorGreenTopLeft *= par6;
                this.colorGreenBottomLeft *= par6;
                this.colorGreenBottomRight *= par6;
                this.colorGreenTopRight *= par6;
                this.colorBlueTopLeft *= par7;
                this.colorBlueBottomLeft *= par7;
                this.colorBlueBottomRight *= par7;
                this.colorBlueTopRight *= par7;
                this.renderFaceXPos(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
            }
            var11 = true;
        }
        this.enableAO = false;
        return var11;
    }
    
    public boolean func_102027_b(final Block par1Block, int par2, int par3, int par4, final float par5, final float par6, final float par7) {
        this.enableAO = true;
        boolean var8 = false;
        float var9 = 0.0f;
        float var10 = 0.0f;
        float var11 = 0.0f;
        float var12 = 0.0f;
        boolean var13 = true;
        final int var14 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
        final Tessellator var15 = Tessellator.instance;
        var15.setBrightness(983055);
        if (par1Block == Block.grass) {
            var13 = false;
        }
        else if (this.hasOverrideBlockTexture()) {
            var13 = false;
        }
        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3 - 1, par4, 0)) {
            if (this.renderMinY <= 0.0) {
                --par3;
            }
            this.aoBrightnessXYNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
            this.aoBrightnessYZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
            this.aoBrightnessYZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
            this.aoBrightnessXYPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);
            this.aoLightValueScratchXYNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4);
            this.aoLightValueScratchYZNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 - 1);
            this.aoLightValueScratchYZNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 + 1);
            this.aoLightValueScratchXYPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4);
            final boolean var16 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3 - 1, par4)];
            final boolean var17 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3 - 1, par4)];
            final boolean var18 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 - 1, par4 + 1)];
            final boolean var19 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 - 1, par4 - 1)];
            if (!var19 && !var17) {
                this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXYNN;
                this.aoBrightnessXYZNNN = this.aoBrightnessXYNN;
            }
            else {
                this.aoLightValueScratchXYZNNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4 - 1);
                this.aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4 - 1);
            }
            if (!var18 && !var17) {
                this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXYNN;
                this.aoBrightnessXYZNNP = this.aoBrightnessXYNN;
            }
            else {
                this.aoLightValueScratchXYZNNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4 + 1);
                this.aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4 + 1);
            }
            if (!var19 && !var16) {
                this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXYPN;
                this.aoBrightnessXYZPNN = this.aoBrightnessXYPN;
            }
            else {
                this.aoLightValueScratchXYZPNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4 - 1);
                this.aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4 - 1);
            }
            if (!var18 && !var16) {
                this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXYPN;
                this.aoBrightnessXYZPNP = this.aoBrightnessXYPN;
            }
            else {
                this.aoLightValueScratchXYZPNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4 + 1);
                this.aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4 + 1);
            }
            if (this.renderMinY <= 0.0) {
                ++par3;
            }
            int var20 = var14;
            if (this.renderMinY <= 0.0 || !this.blockAccess.isBlockOpaqueCube(par2, par3 - 1, par4)) {
                var20 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
            }
            final float var21 = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4);
            var9 = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXYNN + this.aoLightValueScratchYZNP + var21) / 4.0f;
            var12 = (this.aoLightValueScratchYZNP + var21 + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXYPN) / 4.0f;
            var11 = (var21 + this.aoLightValueScratchYZNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNN) / 4.0f;
            var10 = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNN + var21 + this.aoLightValueScratchYZNN) / 4.0f;
            this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXYNN, this.aoBrightnessYZNP, var20);
            this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXYPN, var20);
            this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYPN, this.aoBrightnessXYZPNN, var20);
            this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNN, this.aoBrightnessYZNN, var20);
            if (var13) {
                final float n = par5 * 0.5f;
                this.colorRedTopRight = n;
                this.colorRedBottomRight = n;
                this.colorRedBottomLeft = n;
                this.colorRedTopLeft = n;
                final float n2 = par6 * 0.5f;
                this.colorGreenTopRight = n2;
                this.colorGreenBottomRight = n2;
                this.colorGreenBottomLeft = n2;
                this.colorGreenTopLeft = n2;
                final float n3 = par7 * 0.5f;
                this.colorBlueTopRight = n3;
                this.colorBlueBottomRight = n3;
                this.colorBlueBottomLeft = n3;
                this.colorBlueTopLeft = n3;
            }
            else {
                final float n4 = 0.5f;
                this.colorRedTopRight = n4;
                this.colorRedBottomRight = n4;
                this.colorRedBottomLeft = n4;
                this.colorRedTopLeft = n4;
                final float n5 = 0.5f;
                this.colorGreenTopRight = n5;
                this.colorGreenBottomRight = n5;
                this.colorGreenBottomLeft = n5;
                this.colorGreenTopLeft = n5;
                final float n6 = 0.5f;
                this.colorBlueTopRight = n6;
                this.colorBlueBottomRight = n6;
                this.colorBlueBottomLeft = n6;
                this.colorBlueTopLeft = n6;
            }
            this.colorRedTopLeft *= var9;
            this.colorGreenTopLeft *= var9;
            this.colorBlueTopLeft *= var9;
            this.colorRedBottomLeft *= var10;
            this.colorGreenBottomLeft *= var10;
            this.colorBlueBottomLeft *= var10;
            this.colorRedBottomRight *= var11;
            this.colorGreenBottomRight *= var11;
            this.colorBlueBottomRight *= var11;
            this.colorRedTopRight *= var12;
            this.colorGreenTopRight *= var12;
            this.colorBlueTopRight *= var12;
            this.renderFaceYNeg(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 0));
            var8 = true;
        }
        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3 + 1, par4, 1)) {
            if (this.renderMaxY >= 1.0) {
                ++par3;
            }
            this.aoBrightnessXYNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
            this.aoBrightnessXYPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);
            this.aoBrightnessYZPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
            this.aoBrightnessYZPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
            this.aoLightValueScratchXYNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4);
            this.aoLightValueScratchXYPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4);
            this.aoLightValueScratchYZPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 - 1);
            this.aoLightValueScratchYZPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 + 1);
            final boolean var16 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3 + 1, par4)];
            final boolean var17 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3 + 1, par4)];
            final boolean var18 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 + 1, par4 + 1)];
            final boolean var19 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 + 1, par4 - 1)];
            if (!var19 && !var17) {
                this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXYNP;
                this.aoBrightnessXYZNPN = this.aoBrightnessXYNP;
            }
            else {
                this.aoLightValueScratchXYZNPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4 - 1);
                this.aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4 - 1);
            }
            if (!var19 && !var16) {
                this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXYPP;
                this.aoBrightnessXYZPPN = this.aoBrightnessXYPP;
            }
            else {
                this.aoLightValueScratchXYZPPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4 - 1);
                this.aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4 - 1);
            }
            if (!var18 && !var17) {
                this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXYNP;
                this.aoBrightnessXYZNPP = this.aoBrightnessXYNP;
            }
            else {
                this.aoLightValueScratchXYZNPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4 + 1);
                this.aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4 + 1);
            }
            if (!var18 && !var16) {
                this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXYPP;
                this.aoBrightnessXYZPPP = this.aoBrightnessXYPP;
            }
            else {
                this.aoLightValueScratchXYZPPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4 + 1);
                this.aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4 + 1);
            }
            if (this.renderMaxY >= 1.0) {
                --par3;
            }
            int var20 = var14;
            if (this.renderMaxY >= 1.0 || !this.blockAccess.isBlockOpaqueCube(par2, par3 + 1, par4)) {
                var20 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);
            }
            final float var21 = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4);
            var12 = (this.aoLightValueScratchXYZNPP + this.aoLightValueScratchXYNP + this.aoLightValueScratchYZPP + var21) / 4.0f;
            var9 = (this.aoLightValueScratchYZPP + var21 + this.aoLightValueScratchXYZPPP + this.aoLightValueScratchXYPP) / 4.0f;
            var10 = (var21 + this.aoLightValueScratchYZPN + this.aoLightValueScratchXYPP + this.aoLightValueScratchXYZPPN) / 4.0f;
            var11 = (this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPN + var21 + this.aoLightValueScratchYZPN) / 4.0f;
            this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXYZNPP, this.aoBrightnessXYNP, this.aoBrightnessYZPP, var20);
            this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXYZPPP, this.aoBrightnessXYPP, var20);
            this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXYPP, this.aoBrightnessXYZPPN, var20);
            this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessXYNP, this.aoBrightnessXYZNPN, this.aoBrightnessYZPN, var20);
            this.colorRedTopRight = par5;
            this.colorRedBottomRight = par5;
            this.colorRedBottomLeft = par5;
            this.colorRedTopLeft = par5;
            this.colorGreenTopRight = par6;
            this.colorGreenBottomRight = par6;
            this.colorGreenBottomLeft = par6;
            this.colorGreenTopLeft = par6;
            this.colorBlueTopRight = par7;
            this.colorBlueBottomRight = par7;
            this.colorBlueBottomLeft = par7;
            this.colorBlueTopLeft = par7;
            this.colorRedTopLeft *= var9;
            this.colorGreenTopLeft *= var9;
            this.colorBlueTopLeft *= var9;
            this.colorRedBottomLeft *= var10;
            this.colorGreenBottomLeft *= var10;
            this.colorBlueBottomLeft *= var10;
            this.colorRedBottomRight *= var11;
            this.colorGreenBottomRight *= var11;
            this.colorBlueBottomRight *= var11;
            this.colorRedTopRight *= var12;
            this.colorGreenTopRight *= var12;
            this.colorBlueTopRight *= var12;
            this.renderFaceYPos(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 1));
            var8 = true;
        }
        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3, par4 - 1, 2)) {
            if (this.renderMinZ <= 0.0) {
                --par4;
            }
            this.aoLightValueScratchXZNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4);
            this.aoLightValueScratchYZNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4);
            this.aoLightValueScratchYZPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4);
            this.aoLightValueScratchXZPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4);
            this.aoBrightnessXZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
            this.aoBrightnessYZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
            this.aoBrightnessYZPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);
            this.aoBrightnessXZPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);
            final boolean var16 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3, par4 - 1)];
            final boolean var17 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3, par4 - 1)];
            final boolean var18 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 + 1, par4 - 1)];
            final boolean var19 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 - 1, par4 - 1)];
            if (!var17 && !var19) {
                this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
                this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
            }
            else {
                this.aoLightValueScratchXYZNNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3 - 1, par4);
                this.aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3 - 1, par4);
            }
            if (!var17 && !var18) {
                this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
                this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
            }
            else {
                this.aoLightValueScratchXYZNPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3 + 1, par4);
                this.aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3 + 1, par4);
            }
            if (!var16 && !var19) {
                this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
                this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
            }
            else {
                this.aoLightValueScratchXYZPNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3 - 1, par4);
                this.aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3 - 1, par4);
            }
            if (!var16 && !var18) {
                this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
                this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
            }
            else {
                this.aoLightValueScratchXYZPPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3 + 1, par4);
                this.aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3 + 1, par4);
            }
            if (this.renderMinZ <= 0.0) {
                ++par4;
            }
            int var20 = var14;
            if (this.renderMinZ <= 0.0 || !this.blockAccess.isBlockOpaqueCube(par2, par3, par4 - 1)) {
                var20 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
            }
            final float var21 = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 - 1);
            final float var22 = (this.aoLightValueScratchXZNN + this.aoLightValueScratchXYZNPN + var21 + this.aoLightValueScratchYZPN) / 4.0f;
            final float var23 = (var21 + this.aoLightValueScratchYZPN + this.aoLightValueScratchXZPN + this.aoLightValueScratchXYZPPN) / 4.0f;
            final float var24 = (this.aoLightValueScratchYZNN + var21 + this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXZPN) / 4.0f;
            final float var25 = (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXZNN + this.aoLightValueScratchYZNN + var21) / 4.0f;
            var9 = (float)(var22 * this.renderMaxY * (1.0 - this.renderMinX) + var23 * this.renderMinY * this.renderMinX + var24 * (1.0 - this.renderMaxY) * this.renderMinX + var25 * (1.0 - this.renderMaxY) * (1.0 - this.renderMinX));
            var10 = (float)(var22 * this.renderMaxY * (1.0 - this.renderMaxX) + var23 * this.renderMaxY * this.renderMaxX + var24 * (1.0 - this.renderMaxY) * this.renderMaxX + var25 * (1.0 - this.renderMaxY) * (1.0 - this.renderMaxX));
            var11 = (float)(var22 * this.renderMinY * (1.0 - this.renderMaxX) + var23 * this.renderMinY * this.renderMaxX + var24 * (1.0 - this.renderMinY) * this.renderMaxX + var25 * (1.0 - this.renderMinY) * (1.0 - this.renderMaxX));
            var12 = (float)(var22 * this.renderMinY * (1.0 - this.renderMinX) + var23 * this.renderMinY * this.renderMinX + var24 * (1.0 - this.renderMinY) * this.renderMinX + var25 * (1.0 - this.renderMinY) * (1.0 - this.renderMinX));
            final int var26 = this.getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessYZPN, var20);
            final int var27 = this.getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, var20);
            final int var28 = this.getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYZPNN, this.aoBrightnessXZPN, var20);
            final int var29 = this.getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXZNN, this.aoBrightnessYZNN, var20);
            this.brightnessTopLeft = this.mixAoBrightness(var26, var27, var28, var29, this.renderMaxY * (1.0 - this.renderMinX), this.renderMaxY * this.renderMinX, (1.0 - this.renderMaxY) * this.renderMinX, (1.0 - this.renderMaxY) * (1.0 - this.renderMinX));
            this.brightnessBottomLeft = this.mixAoBrightness(var26, var27, var28, var29, this.renderMaxY * (1.0 - this.renderMaxX), this.renderMaxY * this.renderMaxX, (1.0 - this.renderMaxY) * this.renderMaxX, (1.0 - this.renderMaxY) * (1.0 - this.renderMaxX));
            this.brightnessBottomRight = this.mixAoBrightness(var26, var27, var28, var29, this.renderMinY * (1.0 - this.renderMaxX), this.renderMinY * this.renderMaxX, (1.0 - this.renderMinY) * this.renderMaxX, (1.0 - this.renderMinY) * (1.0 - this.renderMaxX));
            this.brightnessTopRight = this.mixAoBrightness(var26, var27, var28, var29, this.renderMinY * (1.0 - this.renderMinX), this.renderMinY * this.renderMinX, (1.0 - this.renderMinY) * this.renderMinX, (1.0 - this.renderMinY) * (1.0 - this.renderMinX));
            if (var13) {
                final float n7 = par5 * 0.8f;
                this.colorRedTopRight = n7;
                this.colorRedBottomRight = n7;
                this.colorRedBottomLeft = n7;
                this.colorRedTopLeft = n7;
                final float n8 = par6 * 0.8f;
                this.colorGreenTopRight = n8;
                this.colorGreenBottomRight = n8;
                this.colorGreenBottomLeft = n8;
                this.colorGreenTopLeft = n8;
                final float n9 = par7 * 0.8f;
                this.colorBlueTopRight = n9;
                this.colorBlueBottomRight = n9;
                this.colorBlueBottomLeft = n9;
                this.colorBlueTopLeft = n9;
            }
            else {
                final float n10 = 0.8f;
                this.colorRedTopRight = n10;
                this.colorRedBottomRight = n10;
                this.colorRedBottomLeft = n10;
                this.colorRedTopLeft = n10;
                final float n11 = 0.8f;
                this.colorGreenTopRight = n11;
                this.colorGreenBottomRight = n11;
                this.colorGreenBottomLeft = n11;
                this.colorGreenTopLeft = n11;
                final float n12 = 0.8f;
                this.colorBlueTopRight = n12;
                this.colorBlueBottomRight = n12;
                this.colorBlueBottomLeft = n12;
                this.colorBlueTopLeft = n12;
            }
            this.colorRedTopLeft *= var9;
            this.colorGreenTopLeft *= var9;
            this.colorBlueTopLeft *= var9;
            this.colorRedBottomLeft *= var10;
            this.colorGreenBottomLeft *= var10;
            this.colorBlueBottomLeft *= var10;
            this.colorRedBottomRight *= var11;
            this.colorGreenBottomRight *= var11;
            this.colorBlueBottomRight *= var11;
            this.colorRedTopRight *= var12;
            this.colorGreenTopRight *= var12;
            this.colorBlueTopRight *= var12;
            final Icon var30 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 2);
            this.renderFaceZNeg(par1Block, par2, par3, par4, var30);
            if (RenderBlocks.fancyGrass && var30.getIconName().equals("grass_side") && !this.hasOverrideBlockTexture()) {
                this.colorRedTopLeft *= par5;
                this.colorRedBottomLeft *= par5;
                this.colorRedBottomRight *= par5;
                this.colorRedTopRight *= par5;
                this.colorGreenTopLeft *= par6;
                this.colorGreenBottomLeft *= par6;
                this.colorGreenBottomRight *= par6;
                this.colorGreenTopRight *= par6;
                this.colorBlueTopLeft *= par7;
                this.colorBlueBottomLeft *= par7;
                this.colorBlueBottomRight *= par7;
                this.colorBlueTopRight *= par7;
                this.renderFaceZNeg(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
            }
            var8 = true;
        }
        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3, par4 + 1, 3)) {
            if (this.renderMaxZ >= 1.0) {
                ++par4;
            }
            this.aoLightValueScratchXZNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4);
            this.aoLightValueScratchXZPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4);
            this.aoLightValueScratchYZNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4);
            this.aoLightValueScratchYZPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4);
            this.aoBrightnessXZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
            this.aoBrightnessXZPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);
            this.aoBrightnessYZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
            this.aoBrightnessYZPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);
            final boolean var16 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3, par4 + 1)];
            final boolean var17 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3, par4 + 1)];
            final boolean var18 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 + 1, par4 + 1)];
            final boolean var19 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 - 1, par4 + 1)];
            if (!var17 && !var19) {
                this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
                this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
            }
            else {
                this.aoLightValueScratchXYZNNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3 - 1, par4);
                this.aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3 - 1, par4);
            }
            if (!var17 && !var18) {
                this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
                this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
            }
            else {
                this.aoLightValueScratchXYZNPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3 + 1, par4);
                this.aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3 + 1, par4);
            }
            if (!var16 && !var19) {
                this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
                this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
            }
            else {
                this.aoLightValueScratchXYZPNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3 - 1, par4);
                this.aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3 - 1, par4);
            }
            if (!var16 && !var18) {
                this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
                this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
            }
            else {
                this.aoLightValueScratchXYZPPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3 + 1, par4);
                this.aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3 + 1, par4);
            }
            if (this.renderMaxZ >= 1.0) {
                --par4;
            }
            int var20 = var14;
            if (this.renderMaxZ >= 1.0 || !this.blockAccess.isBlockOpaqueCube(par2, par3, par4 + 1)) {
                var20 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
            }
            final float var21 = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 + 1);
            final float var22 = (this.aoLightValueScratchXZNP + this.aoLightValueScratchXYZNPP + var21 + this.aoLightValueScratchYZPP) / 4.0f;
            final float var23 = (var21 + this.aoLightValueScratchYZPP + this.aoLightValueScratchXZPP + this.aoLightValueScratchXYZPPP) / 4.0f;
            final float var24 = (this.aoLightValueScratchYZNP + var21 + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXZPP) / 4.0f;
            final float var25 = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXZNP + this.aoLightValueScratchYZNP + var21) / 4.0f;
            var9 = (float)(var22 * this.renderMaxY * (1.0 - this.renderMinX) + var23 * this.renderMaxY * this.renderMinX + var24 * (1.0 - this.renderMaxY) * this.renderMinX + var25 * (1.0 - this.renderMaxY) * (1.0 - this.renderMinX));
            var10 = (float)(var22 * this.renderMinY * (1.0 - this.renderMinX) + var23 * this.renderMinY * this.renderMinX + var24 * (1.0 - this.renderMinY) * this.renderMinX + var25 * (1.0 - this.renderMinY) * (1.0 - this.renderMinX));
            var11 = (float)(var22 * this.renderMinY * (1.0 - this.renderMaxX) + var23 * this.renderMinY * this.renderMaxX + var24 * (1.0 - this.renderMinY) * this.renderMaxX + var25 * (1.0 - this.renderMinY) * (1.0 - this.renderMaxX));
            var12 = (float)(var22 * this.renderMaxY * (1.0 - this.renderMaxX) + var23 * this.renderMaxY * this.renderMaxX + var24 * (1.0 - this.renderMaxY) * this.renderMaxX + var25 * (1.0 - this.renderMaxY) * (1.0 - this.renderMaxX));
            final int var26 = this.getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYZNPP, this.aoBrightnessYZPP, var20);
            final int var27 = this.getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXZPP, this.aoBrightnessXYZPPP, var20);
            final int var28 = this.getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, var20);
            final int var29 = this.getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, this.aoBrightnessYZNP, var20);
            this.brightnessTopLeft = this.mixAoBrightness(var26, var29, var28, var27, this.renderMaxY * (1.0 - this.renderMinX), (1.0 - this.renderMaxY) * (1.0 - this.renderMinX), (1.0 - this.renderMaxY) * this.renderMinX, this.renderMaxY * this.renderMinX);
            this.brightnessBottomLeft = this.mixAoBrightness(var26, var29, var28, var27, this.renderMinY * (1.0 - this.renderMinX), (1.0 - this.renderMinY) * (1.0 - this.renderMinX), (1.0 - this.renderMinY) * this.renderMinX, this.renderMinY * this.renderMinX);
            this.brightnessBottomRight = this.mixAoBrightness(var26, var29, var28, var27, this.renderMinY * (1.0 - this.renderMaxX), (1.0 - this.renderMinY) * (1.0 - this.renderMaxX), (1.0 - this.renderMinY) * this.renderMaxX, this.renderMinY * this.renderMaxX);
            this.brightnessTopRight = this.mixAoBrightness(var26, var29, var28, var27, this.renderMaxY * (1.0 - this.renderMaxX), (1.0 - this.renderMaxY) * (1.0 - this.renderMaxX), (1.0 - this.renderMaxY) * this.renderMaxX, this.renderMaxY * this.renderMaxX);
            if (var13) {
                final float n13 = par5 * 0.8f;
                this.colorRedTopRight = n13;
                this.colorRedBottomRight = n13;
                this.colorRedBottomLeft = n13;
                this.colorRedTopLeft = n13;
                final float n14 = par6 * 0.8f;
                this.colorGreenTopRight = n14;
                this.colorGreenBottomRight = n14;
                this.colorGreenBottomLeft = n14;
                this.colorGreenTopLeft = n14;
                final float n15 = par7 * 0.8f;
                this.colorBlueTopRight = n15;
                this.colorBlueBottomRight = n15;
                this.colorBlueBottomLeft = n15;
                this.colorBlueTopLeft = n15;
            }
            else {
                final float n16 = 0.8f;
                this.colorRedTopRight = n16;
                this.colorRedBottomRight = n16;
                this.colorRedBottomLeft = n16;
                this.colorRedTopLeft = n16;
                final float n17 = 0.8f;
                this.colorGreenTopRight = n17;
                this.colorGreenBottomRight = n17;
                this.colorGreenBottomLeft = n17;
                this.colorGreenTopLeft = n17;
                final float n18 = 0.8f;
                this.colorBlueTopRight = n18;
                this.colorBlueBottomRight = n18;
                this.colorBlueBottomLeft = n18;
                this.colorBlueTopLeft = n18;
            }
            this.colorRedTopLeft *= var9;
            this.colorGreenTopLeft *= var9;
            this.colorBlueTopLeft *= var9;
            this.colorRedBottomLeft *= var10;
            this.colorGreenBottomLeft *= var10;
            this.colorBlueBottomLeft *= var10;
            this.colorRedBottomRight *= var11;
            this.colorGreenBottomRight *= var11;
            this.colorBlueBottomRight *= var11;
            this.colorRedTopRight *= var12;
            this.colorGreenTopRight *= var12;
            this.colorBlueTopRight *= var12;
            final Icon var30 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 3);
            this.renderFaceZPos(par1Block, par2, par3, par4, var30);
            if (RenderBlocks.fancyGrass && var30.getIconName().equals("grass_side") && !this.hasOverrideBlockTexture()) {
                this.colorRedTopLeft *= par5;
                this.colorRedBottomLeft *= par5;
                this.colorRedBottomRight *= par5;
                this.colorRedTopRight *= par5;
                this.colorGreenTopLeft *= par6;
                this.colorGreenBottomLeft *= par6;
                this.colorGreenBottomRight *= par6;
                this.colorGreenTopRight *= par6;
                this.colorBlueTopLeft *= par7;
                this.colorBlueBottomLeft *= par7;
                this.colorBlueBottomRight *= par7;
                this.colorBlueTopRight *= par7;
                this.renderFaceZPos(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
            }
            var8 = true;
        }
        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2 - 1, par3, par4, 4)) {
            if (this.renderMinX <= 0.0) {
                --par2;
            }
            this.aoLightValueScratchXYNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4);
            this.aoLightValueScratchXZNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 - 1);
            this.aoLightValueScratchXZNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 + 1);
            this.aoLightValueScratchXYNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4);
            this.aoBrightnessXYNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
            this.aoBrightnessXZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
            this.aoBrightnessXZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
            this.aoBrightnessXYNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);
            final boolean var16 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3 + 1, par4)];
            final boolean var17 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3 - 1, par4)];
            final boolean var18 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3, par4 - 1)];
            final boolean var19 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3, par4 + 1)];
            if (!var18 && !var17) {
                this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
                this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
            }
            else {
                this.aoLightValueScratchXYZNNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4 - 1);
                this.aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4 - 1);
            }
            if (!var19 && !var17) {
                this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
                this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
            }
            else {
                this.aoLightValueScratchXYZNNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4 + 1);
                this.aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4 + 1);
            }
            if (!var18 && !var16) {
                this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
                this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
            }
            else {
                this.aoLightValueScratchXYZNPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4 - 1);
                this.aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4 - 1);
            }
            if (!var19 && !var16) {
                this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
                this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
            }
            else {
                this.aoLightValueScratchXYZNPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4 + 1);
                this.aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4 + 1);
            }
            if (this.renderMinX <= 0.0) {
                ++par2;
            }
            int var20 = var14;
            if (this.renderMinX <= 0.0 || !this.blockAccess.isBlockOpaqueCube(par2 - 1, par3, par4)) {
                var20 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
            }
            final float var21 = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4);
            final float var22 = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNP + var21 + this.aoLightValueScratchXZNP) / 4.0f;
            final float var23 = (var21 + this.aoLightValueScratchXZNP + this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPP) / 4.0f;
            final float var24 = (this.aoLightValueScratchXZNN + var21 + this.aoLightValueScratchXYZNPN + this.aoLightValueScratchXYNP) / 4.0f;
            final float var25 = (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXYNN + this.aoLightValueScratchXZNN + var21) / 4.0f;
            var9 = (float)(var23 * this.renderMaxY * this.renderMaxZ + var24 * this.renderMaxY * (1.0 - this.renderMaxZ) + var25 * (1.0 - this.renderMaxY) * (1.0 - this.renderMaxZ) + var22 * (1.0 - this.renderMaxY) * this.renderMaxZ);
            var10 = (float)(var23 * this.renderMaxY * this.renderMinZ + var24 * this.renderMaxY * (1.0 - this.renderMinZ) + var25 * (1.0 - this.renderMaxY) * (1.0 - this.renderMinZ) + var22 * (1.0 - this.renderMaxY) * this.renderMinZ);
            var11 = (float)(var23 * this.renderMinY * this.renderMinZ + var24 * this.renderMinY * (1.0 - this.renderMinZ) + var25 * (1.0 - this.renderMinY) * (1.0 - this.renderMinZ) + var22 * (1.0 - this.renderMinY) * this.renderMinZ);
            var12 = (float)(var23 * this.renderMinY * this.renderMaxZ + var24 * this.renderMinY * (1.0 - this.renderMaxZ) + var25 * (1.0 - this.renderMinY) * (1.0 - this.renderMaxZ) + var22 * (1.0 - this.renderMinY) * this.renderMaxZ);
            final int var26 = this.getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, var20);
            final int var27 = this.getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYNP, this.aoBrightnessXYZNPP, var20);
            final int var28 = this.getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessXYNP, var20);
            final int var29 = this.getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXYNN, this.aoBrightnessXZNN, var20);
            this.brightnessTopLeft = this.mixAoBrightness(var27, var28, var29, var26, this.renderMaxY * this.renderMaxZ, this.renderMaxY * (1.0 - this.renderMaxZ), (1.0 - this.renderMaxY) * (1.0 - this.renderMaxZ), (1.0 - this.renderMaxY) * this.renderMaxZ);
            this.brightnessBottomLeft = this.mixAoBrightness(var27, var28, var29, var26, this.renderMaxY * this.renderMinZ, this.renderMaxY * (1.0 - this.renderMinZ), (1.0 - this.renderMaxY) * (1.0 - this.renderMinZ), (1.0 - this.renderMaxY) * this.renderMinZ);
            this.brightnessBottomRight = this.mixAoBrightness(var27, var28, var29, var26, this.renderMinY * this.renderMinZ, this.renderMinY * (1.0 - this.renderMinZ), (1.0 - this.renderMinY) * (1.0 - this.renderMinZ), (1.0 - this.renderMinY) * this.renderMinZ);
            this.brightnessTopRight = this.mixAoBrightness(var27, var28, var29, var26, this.renderMinY * this.renderMaxZ, this.renderMinY * (1.0 - this.renderMaxZ), (1.0 - this.renderMinY) * (1.0 - this.renderMaxZ), (1.0 - this.renderMinY) * this.renderMaxZ);
            if (var13) {
                final float n19 = par5 * 0.6f;
                this.colorRedTopRight = n19;
                this.colorRedBottomRight = n19;
                this.colorRedBottomLeft = n19;
                this.colorRedTopLeft = n19;
                final float n20 = par6 * 0.6f;
                this.colorGreenTopRight = n20;
                this.colorGreenBottomRight = n20;
                this.colorGreenBottomLeft = n20;
                this.colorGreenTopLeft = n20;
                final float n21 = par7 * 0.6f;
                this.colorBlueTopRight = n21;
                this.colorBlueBottomRight = n21;
                this.colorBlueBottomLeft = n21;
                this.colorBlueTopLeft = n21;
            }
            else {
                final float n22 = 0.6f;
                this.colorRedTopRight = n22;
                this.colorRedBottomRight = n22;
                this.colorRedBottomLeft = n22;
                this.colorRedTopLeft = n22;
                final float n23 = 0.6f;
                this.colorGreenTopRight = n23;
                this.colorGreenBottomRight = n23;
                this.colorGreenBottomLeft = n23;
                this.colorGreenTopLeft = n23;
                final float n24 = 0.6f;
                this.colorBlueTopRight = n24;
                this.colorBlueBottomRight = n24;
                this.colorBlueBottomLeft = n24;
                this.colorBlueTopLeft = n24;
            }
            this.colorRedTopLeft *= var9;
            this.colorGreenTopLeft *= var9;
            this.colorBlueTopLeft *= var9;
            this.colorRedBottomLeft *= var10;
            this.colorGreenBottomLeft *= var10;
            this.colorBlueBottomLeft *= var10;
            this.colorRedBottomRight *= var11;
            this.colorGreenBottomRight *= var11;
            this.colorBlueBottomRight *= var11;
            this.colorRedTopRight *= var12;
            this.colorGreenTopRight *= var12;
            this.colorBlueTopRight *= var12;
            final Icon var30 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 4);
            this.renderFaceXNeg(par1Block, par2, par3, par4, var30);
            if (RenderBlocks.fancyGrass && var30.getIconName().equals("grass_side") && !this.hasOverrideBlockTexture()) {
                this.colorRedTopLeft *= par5;
                this.colorRedBottomLeft *= par5;
                this.colorRedBottomRight *= par5;
                this.colorRedTopRight *= par5;
                this.colorGreenTopLeft *= par6;
                this.colorGreenBottomLeft *= par6;
                this.colorGreenBottomRight *= par6;
                this.colorGreenTopRight *= par6;
                this.colorBlueTopLeft *= par7;
                this.colorBlueBottomLeft *= par7;
                this.colorBlueBottomRight *= par7;
                this.colorBlueTopRight *= par7;
                this.renderFaceXNeg(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
            }
            var8 = true;
        }
        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2 + 1, par3, par4, 5)) {
            if (this.renderMaxX >= 1.0) {
                ++par2;
            }
            this.aoLightValueScratchXYPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4);
            this.aoLightValueScratchXZPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 - 1);
            this.aoLightValueScratchXZPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 + 1);
            this.aoLightValueScratchXYPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4);
            this.aoBrightnessXYPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
            this.aoBrightnessXZPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
            this.aoBrightnessXZPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
            this.aoBrightnessXYPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);
            final boolean var16 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3 + 1, par4)];
            final boolean var17 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3 - 1, par4)];
            final boolean var18 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3, par4 + 1)];
            final boolean var19 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3, par4 - 1)];
            if (!var17 && !var19) {
                this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
                this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
            }
            else {
                this.aoLightValueScratchXYZPNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4 - 1);
                this.aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4 - 1);
            }
            if (!var17 && !var18) {
                this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
                this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
            }
            else {
                this.aoLightValueScratchXYZPNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4 + 1);
                this.aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4 + 1);
            }
            if (!var16 && !var19) {
                this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
                this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
            }
            else {
                this.aoLightValueScratchXYZPPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4 - 1);
                this.aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4 - 1);
            }
            if (!var16 && !var18) {
                this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
                this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
            }
            else {
                this.aoLightValueScratchXYZPPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4 + 1);
                this.aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4 + 1);
            }
            if (this.renderMaxX >= 1.0) {
                --par2;
            }
            int var20 = var14;
            if (this.renderMaxX >= 1.0 || !this.blockAccess.isBlockOpaqueCube(par2 + 1, par3, par4)) {
                var20 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);
            }
            final float var21 = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4);
            final float var22 = (this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNP + var21 + this.aoLightValueScratchXZPP) / 4.0f;
            final float var23 = (this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXZPN + var21) / 4.0f;
            final float var24 = (this.aoLightValueScratchXZPN + var21 + this.aoLightValueScratchXYZPPN + this.aoLightValueScratchXYPP) / 4.0f;
            final float var25 = (var21 + this.aoLightValueScratchXZPP + this.aoLightValueScratchXYPP + this.aoLightValueScratchXYZPPP) / 4.0f;
            var9 = (float)(var22 * (1.0 - this.renderMinY) * this.renderMaxZ + var23 * (1.0 - this.renderMinY) * (1.0 - this.renderMaxZ) + var24 * this.renderMinY * (1.0 - this.renderMaxZ) + var25 * this.renderMinY * this.renderMaxZ);
            var10 = (float)(var22 * (1.0 - this.renderMinY) * this.renderMinZ + var23 * (1.0 - this.renderMinY) * (1.0 - this.renderMinZ) + var24 * this.renderMinY * (1.0 - this.renderMinZ) + var25 * this.renderMinY * this.renderMinZ);
            var11 = (float)(var22 * (1.0 - this.renderMaxY) * this.renderMinZ + var23 * (1.0 - this.renderMaxY) * (1.0 - this.renderMinZ) + var24 * this.renderMaxY * (1.0 - this.renderMinZ) + var25 * this.renderMaxY * this.renderMinZ);
            var12 = (float)(var22 * (1.0 - this.renderMaxY) * this.renderMaxZ + var23 * (1.0 - this.renderMaxY) * (1.0 - this.renderMaxZ) + var24 * this.renderMaxY * (1.0 - this.renderMaxZ) + var25 * this.renderMaxY * this.renderMaxZ);
            final int var26 = this.getAoBrightness(this.aoBrightnessXYPN, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, var20);
            final int var27 = this.getAoBrightness(this.aoBrightnessXZPP, this.aoBrightnessXYPP, this.aoBrightnessXYZPPP, var20);
            final int var28 = this.getAoBrightness(this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, this.aoBrightnessXYPP, var20);
            final int var29 = this.getAoBrightness(this.aoBrightnessXYZPNN, this.aoBrightnessXYPN, this.aoBrightnessXZPN, var20);
            this.brightnessTopLeft = this.mixAoBrightness(var26, var29, var28, var27, (1.0 - this.renderMinY) * this.renderMaxZ, (1.0 - this.renderMinY) * (1.0 - this.renderMaxZ), this.renderMinY * (1.0 - this.renderMaxZ), this.renderMinY * this.renderMaxZ);
            this.brightnessBottomLeft = this.mixAoBrightness(var26, var29, var28, var27, (1.0 - this.renderMinY) * this.renderMinZ, (1.0 - this.renderMinY) * (1.0 - this.renderMinZ), this.renderMinY * (1.0 - this.renderMinZ), this.renderMinY * this.renderMinZ);
            this.brightnessBottomRight = this.mixAoBrightness(var26, var29, var28, var27, (1.0 - this.renderMaxY) * this.renderMinZ, (1.0 - this.renderMaxY) * (1.0 - this.renderMinZ), this.renderMaxY * (1.0 - this.renderMinZ), this.renderMaxY * this.renderMinZ);
            this.brightnessTopRight = this.mixAoBrightness(var26, var29, var28, var27, (1.0 - this.renderMaxY) * this.renderMaxZ, (1.0 - this.renderMaxY) * (1.0 - this.renderMaxZ), this.renderMaxY * (1.0 - this.renderMaxZ), this.renderMaxY * this.renderMaxZ);
            if (var13) {
                final float n25 = par5 * 0.6f;
                this.colorRedTopRight = n25;
                this.colorRedBottomRight = n25;
                this.colorRedBottomLeft = n25;
                this.colorRedTopLeft = n25;
                final float n26 = par6 * 0.6f;
                this.colorGreenTopRight = n26;
                this.colorGreenBottomRight = n26;
                this.colorGreenBottomLeft = n26;
                this.colorGreenTopLeft = n26;
                final float n27 = par7 * 0.6f;
                this.colorBlueTopRight = n27;
                this.colorBlueBottomRight = n27;
                this.colorBlueBottomLeft = n27;
                this.colorBlueTopLeft = n27;
            }
            else {
                final float n28 = 0.6f;
                this.colorRedTopRight = n28;
                this.colorRedBottomRight = n28;
                this.colorRedBottomLeft = n28;
                this.colorRedTopLeft = n28;
                final float n29 = 0.6f;
                this.colorGreenTopRight = n29;
                this.colorGreenBottomRight = n29;
                this.colorGreenBottomLeft = n29;
                this.colorGreenTopLeft = n29;
                final float n30 = 0.6f;
                this.colorBlueTopRight = n30;
                this.colorBlueBottomRight = n30;
                this.colorBlueBottomLeft = n30;
                this.colorBlueTopLeft = n30;
            }
            this.colorRedTopLeft *= var9;
            this.colorGreenTopLeft *= var9;
            this.colorBlueTopLeft *= var9;
            this.colorRedBottomLeft *= var10;
            this.colorGreenBottomLeft *= var10;
            this.colorBlueBottomLeft *= var10;
            this.colorRedBottomRight *= var11;
            this.colorGreenBottomRight *= var11;
            this.colorBlueBottomRight *= var11;
            this.colorRedTopRight *= var12;
            this.colorGreenTopRight *= var12;
            this.colorBlueTopRight *= var12;
            final Icon var30 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 5);
            this.renderFaceXPos(par1Block, par2, par3, par4, var30);
            if (RenderBlocks.fancyGrass && var30.getIconName().equals("grass_side") && !this.hasOverrideBlockTexture()) {
                this.colorRedTopLeft *= par5;
                this.colorRedBottomLeft *= par5;
                this.colorRedBottomRight *= par5;
                this.colorRedTopRight *= par5;
                this.colorGreenTopLeft *= par6;
                this.colorGreenBottomLeft *= par6;
                this.colorGreenBottomRight *= par6;
                this.colorGreenTopRight *= par6;
                this.colorBlueTopLeft *= par7;
                this.colorBlueBottomLeft *= par7;
                this.colorBlueBottomRight *= par7;
                this.colorBlueTopRight *= par7;
                this.renderFaceXPos(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
            }
            var8 = true;
        }
        this.enableAO = false;
        return var8;
    }
    
    public int getAoBrightness(int par1, int par2, int par3, final int par4) {
        if (par1 == 0) {
            par1 = par4;
        }
        if (par2 == 0) {
            par2 = par4;
        }
        if (par3 == 0) {
            par3 = par4;
        }
        return par1 + par2 + par3 + par4 >> 2 & 0xFF00FF;
    }
    
    public int mixAoBrightness(final int par1, final int par2, final int par3, final int par4, final double par5, final double par7, final double par9, final double par11) {
        final int var13 = (int)((par1 >> 16 & 0xFF) * par5 + (par2 >> 16 & 0xFF) * par7 + (par3 >> 16 & 0xFF) * par9 + (par4 >> 16 & 0xFF) * par11) & 0xFF;
        final int var14 = (int)((par1 & 0xFF) * par5 + (par2 & 0xFF) * par7 + (par3 & 0xFF) * par9 + (par4 & 0xFF) * par11) & 0xFF;
        return var13 << 16 | var14;
    }
    
    public boolean renderStandardBlockWithColorMultiplier(final Block par1Block, final int par2, final int par3, final int par4, final float par5, final float par6, final float par7) {
        this.enableAO = false;
        final boolean var8 = Tessellator.instance.defaultTexture;
        final boolean var9 = Config.isBetterGrass() && var8;
        final Tessellator var10 = Tessellator.instance;
        boolean var11 = false;
        int var12 = -1;
        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3 - 1, par4, 0)) {
            if (var12 < 0) {
                var12 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
            }
            float var16;
            float var15;
            float var14;
            final float var13 = var14 = (var15 = (var16 = 0.5f));
            if (par1Block != Block.grass) {
                var16 = var13 * par5;
                var15 = var13 * par6;
                var14 = var13 * par7;
            }
            var10.setBrightness((this.renderMinY > 0.0) ? var12 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4));
            var10.setColorOpaque_F(var16, var15, var14);
            this.renderFaceYNeg(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 0));
            var11 = true;
        }
        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3 + 1, par4, 1)) {
            if (var12 < 0) {
                var12 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
            }
            final float var13 = 1.0f;
            final float var16 = var13 * par5;
            final float var15 = var13 * par6;
            final float var14 = var13 * par7;
            var10.setBrightness((this.renderMaxY < 1.0) ? var12 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4));
            var10.setColorOpaque_F(var16, var15, var14);
            this.renderFaceYPos(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 1));
            var11 = true;
        }
        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3, par4 - 1, 2)) {
            if (var12 < 0) {
                var12 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
            }
            float var15;
            float var14;
            float var17;
            final float var16 = var17 = (var14 = (var15 = 0.8f));
            if (par1Block != Block.grass) {
                var15 = var16 * par5;
                var14 = var16 * par6;
                var17 = var16 * par7;
            }
            var10.setBrightness((this.renderMinZ > 0.0) ? var12 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1));
            var10.setColorOpaque_F(var15, var14, var17);
            Icon var18 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 2);
            if (var9) {
                if (var18 == TextureUtils.iconGrassSide || var18 == TextureUtils.iconMycelSide) {
                    var18 = Config.getSideGrassTexture(this.blockAccess, par2, par3, par4, 2, var18);
                    if (var18 == TextureUtils.iconGrassTop) {
                        var10.setColorOpaque_F(var15 * par5, var14 * par6, var17 * par7);
                    }
                }
                if (var18 == TextureUtils.iconSnowSide) {
                    var18 = Config.getSideSnowGrassTexture(this.blockAccess, par2, par3, par4, 2);
                }
            }
            this.renderFaceZNeg(par1Block, par2, par3, par4, var18);
            if (var8 && RenderBlocks.fancyGrass && var18 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture()) {
                var10.setColorOpaque_F(var15 * par5, var14 * par6, var17 * par7);
                this.renderFaceZNeg(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
            }
            var11 = true;
        }
        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3, par4 + 1, 3)) {
            if (var12 < 0) {
                var12 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
            }
            float var15;
            float var14;
            float var17;
            final float var16 = var17 = (var14 = (var15 = 0.8f));
            if (par1Block != Block.grass) {
                var15 = var16 * par5;
                var14 = var16 * par6;
                var17 = var16 * par7;
            }
            var10.setBrightness((this.renderMaxZ < 1.0) ? var12 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1));
            var10.setColorOpaque_F(var15, var14, var17);
            Icon var18 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 3);
            if (var9) {
                if (var18 == TextureUtils.iconGrassSide || var18 == TextureUtils.iconMycelSide) {
                    var18 = Config.getSideGrassTexture(this.blockAccess, par2, par3, par4, 3, var18);
                    if (var18 == TextureUtils.iconGrassTop) {
                        var10.setColorOpaque_F(var15 * par5, var14 * par6, var17 * par7);
                    }
                }
                if (var18 == TextureUtils.iconSnowSide) {
                    var18 = Config.getSideSnowGrassTexture(this.blockAccess, par2, par3, par4, 3);
                }
            }
            this.renderFaceZPos(par1Block, par2, par3, par4, var18);
            if (var8 && RenderBlocks.fancyGrass && var18 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture()) {
                var10.setColorOpaque_F(var15 * par5, var14 * par6, var17 * par7);
                this.renderFaceZPos(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
            }
            var11 = true;
        }
        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2 - 1, par3, par4, 4)) {
            if (var12 < 0) {
                var12 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
            }
            float var15;
            float var14;
            float var17;
            final float var16 = var17 = (var14 = (var15 = 0.6f));
            if (par1Block != Block.grass) {
                var15 = var16 * par5;
                var14 = var16 * par6;
                var17 = var16 * par7;
            }
            var10.setBrightness((this.renderMinX > 0.0) ? var12 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4));
            var10.setColorOpaque_F(var15, var14, var17);
            Icon var18 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 4);
            if (var9) {
                if (var18 == TextureUtils.iconGrassSide || var18 == TextureUtils.iconMycelSide) {
                    var18 = Config.getSideGrassTexture(this.blockAccess, par2, par3, par4, 4, var18);
                    if (var18 == TextureUtils.iconGrassTop) {
                        var10.setColorOpaque_F(var15 * par5, var14 * par6, var17 * par7);
                    }
                }
                if (var18 == TextureUtils.iconSnowSide) {
                    var18 = Config.getSideSnowGrassTexture(this.blockAccess, par2, par3, par4, 4);
                }
            }
            this.renderFaceXNeg(par1Block, par2, par3, par4, var18);
            if (var8 && RenderBlocks.fancyGrass && var18 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture()) {
                var10.setColorOpaque_F(var15 * par5, var14 * par6, var17 * par7);
                this.renderFaceXNeg(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
            }
            var11 = true;
        }
        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2 + 1, par3, par4, 5)) {
            if (var12 < 0) {
                var12 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
            }
            float var15;
            float var14;
            float var17;
            final float var16 = var17 = (var14 = (var15 = 0.6f));
            if (par1Block != Block.grass) {
                var15 = var16 * par5;
                var14 = var16 * par6;
                var17 = var16 * par7;
            }
            var10.setBrightness((this.renderMaxX < 1.0) ? var12 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4));
            var10.setColorOpaque_F(var15, var14, var17);
            Icon var18 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 5);
            if (var9) {
                if (var18 == TextureUtils.iconGrassSide || var18 == TextureUtils.iconMycelSide) {
                    var18 = Config.getSideGrassTexture(this.blockAccess, par2, par3, par4, 5, var18);
                    if (var18 == TextureUtils.iconGrassTop) {
                        var10.setColorOpaque_F(var15 * par5, var14 * par6, var17 * par7);
                    }
                }
                if (var18 == TextureUtils.iconSnowSide) {
                    var18 = Config.getSideSnowGrassTexture(this.blockAccess, par2, par3, par4, 5);
                }
            }
            this.renderFaceXPos(par1Block, par2, par3, par4, var18);
            if (var8 && RenderBlocks.fancyGrass && var18 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture()) {
                var10.setColorOpaque_F(var15 * par5, var14 * par6, var17 * par7);
                this.renderFaceXPos(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
            }
            var11 = true;
        }
        return var11;
    }
    
    public boolean renderBlockCocoa(final BlockCocoa par1BlockCocoa, final int par2, final int par3, final int par4) {
        final Tessellator var5 = Tessellator.instance;
        var5.setBrightness(par1BlockCocoa.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        var5.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        final int var6 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        final int var7 = BlockDirectional.getDirection(var6);
        final int var8 = BlockCocoa.func_72219_c(var6);
        final Icon var9 = par1BlockCocoa.func_94468_i_(var8);
        final int var10 = 4 + var8 * 2;
        final int var11 = 5 + var8 * 2;
        final double var12 = 15.0 - var10;
        final double var13 = 15.0;
        final double var14 = 4.0;
        final double var15 = 4.0 + var11;
        double var16 = var9.getInterpolatedU(var12);
        double var17 = var9.getInterpolatedU(var13);
        double var18 = var9.getInterpolatedV(var14);
        double var19 = var9.getInterpolatedV(var15);
        double var20 = 0.0;
        double var21 = 0.0;
        switch (var7) {
            case 0: {
                var20 = 8.0 - var10 / 2;
                var21 = 15.0 - var10;
                break;
            }
            case 1: {
                var20 = 1.0;
                var21 = 8.0 - var10 / 2;
                break;
            }
            case 2: {
                var20 = 8.0 - var10 / 2;
                var21 = 1.0;
                break;
            }
            case 3: {
                var20 = 15.0 - var10;
                var21 = 8.0 - var10 / 2;
                break;
            }
        }
        double var22 = par2 + var20 / 16.0;
        double var23 = par2 + (var20 + var10) / 16.0;
        double var24 = par3 + (12.0 - var11) / 16.0;
        double var25 = par3 + 0.75;
        double var26 = par4 + var21 / 16.0;
        double var27 = par4 + (var21 + var10) / 16.0;
        var5.addVertexWithUV(var22, var24, var26, var16, var19);
        var5.addVertexWithUV(var22, var24, var27, var17, var19);
        var5.addVertexWithUV(var22, var25, var27, var17, var18);
        var5.addVertexWithUV(var22, var25, var26, var16, var18);
        var5.addVertexWithUV(var23, var24, var27, var16, var19);
        var5.addVertexWithUV(var23, var24, var26, var17, var19);
        var5.addVertexWithUV(var23, var25, var26, var17, var18);
        var5.addVertexWithUV(var23, var25, var27, var16, var18);
        var5.addVertexWithUV(var23, var24, var26, var16, var19);
        var5.addVertexWithUV(var22, var24, var26, var17, var19);
        var5.addVertexWithUV(var22, var25, var26, var17, var18);
        var5.addVertexWithUV(var23, var25, var26, var16, var18);
        var5.addVertexWithUV(var22, var24, var27, var16, var19);
        var5.addVertexWithUV(var23, var24, var27, var17, var19);
        var5.addVertexWithUV(var23, var25, var27, var17, var18);
        var5.addVertexWithUV(var22, var25, var27, var16, var18);
        int var28 = var10;
        if (var8 >= 2) {
            var28 = var10 - 1;
        }
        var16 = var9.getMinU();
        var17 = var9.getInterpolatedU(var28);
        var18 = var9.getMinV();
        var19 = var9.getInterpolatedV(var28);
        var5.addVertexWithUV(var22, var25, var27, var16, var19);
        var5.addVertexWithUV(var23, var25, var27, var17, var19);
        var5.addVertexWithUV(var23, var25, var26, var17, var18);
        var5.addVertexWithUV(var22, var25, var26, var16, var18);
        var5.addVertexWithUV(var22, var24, var26, var16, var18);
        var5.addVertexWithUV(var23, var24, var26, var17, var18);
        var5.addVertexWithUV(var23, var24, var27, var17, var19);
        var5.addVertexWithUV(var22, var24, var27, var16, var19);
        var16 = var9.getInterpolatedU(12.0);
        var17 = var9.getMaxU();
        var18 = var9.getMinV();
        var19 = var9.getInterpolatedV(4.0);
        var20 = 8.0;
        var21 = 0.0;
        switch (var7) {
            case 0: {
                var20 = 8.0;
                var21 = 12.0;
                final double var29 = var16;
                var16 = var17;
                var17 = var29;
                break;
            }
            case 1: {
                var20 = 0.0;
                var21 = 8.0;
                break;
            }
            case 2: {
                var20 = 8.0;
                var21 = 0.0;
                break;
            }
            case 3: {
                var20 = 12.0;
                var21 = 8.0;
                final double var29 = var16;
                var16 = var17;
                var17 = var29;
                break;
            }
        }
        var22 = par2 + var20 / 16.0;
        var23 = par2 + (var20 + 4.0) / 16.0;
        var24 = par3 + 0.75;
        var25 = par3 + 1.0;
        var26 = par4 + var21 / 16.0;
        var27 = par4 + (var21 + 4.0) / 16.0;
        if (var7 != 2 && var7 != 0) {
            if (var7 == 1 || var7 == 3) {
                var5.addVertexWithUV(var23, var24, var26, var16, var19);
                var5.addVertexWithUV(var22, var24, var26, var17, var19);
                var5.addVertexWithUV(var22, var25, var26, var17, var18);
                var5.addVertexWithUV(var23, var25, var26, var16, var18);
                var5.addVertexWithUV(var22, var24, var26, var17, var19);
                var5.addVertexWithUV(var23, var24, var26, var16, var19);
                var5.addVertexWithUV(var23, var25, var26, var16, var18);
                var5.addVertexWithUV(var22, var25, var26, var17, var18);
            }
        }
        else {
            var5.addVertexWithUV(var22, var24, var26, var17, var19);
            var5.addVertexWithUV(var22, var24, var27, var16, var19);
            var5.addVertexWithUV(var22, var25, var27, var16, var18);
            var5.addVertexWithUV(var22, var25, var26, var17, var18);
            var5.addVertexWithUV(var22, var24, var27, var16, var19);
            var5.addVertexWithUV(var22, var24, var26, var17, var19);
            var5.addVertexWithUV(var22, var25, var26, var17, var18);
            var5.addVertexWithUV(var22, var25, var27, var16, var18);
        }
        return true;
    }
    
    public boolean renderBlockBeacon(final BlockBeacon par1BlockBeacon, final int par2, final int par3, final int par4) {
        final float var5 = 0.1875f;
        this.setOverrideBlockTexture(this.getBlockIcon(Block.obsidian));
        this.setRenderBounds(0.125, 0.0062500000931322575, 0.125, 0.875, var5, 0.875);
        this.renderStandardBlock(par1BlockBeacon, par2, par3, par4);
        this.setOverrideBlockTexture(this.getBlockIcon(Block.glass));
        this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
        this.renderStandardBlock(par1BlockBeacon, par2, par3, par4);
        this.setOverrideBlockTexture(par1BlockBeacon.getBeaconIcon());
        this.setRenderBounds(0.1875, var5, 0.1875, 0.8125, 0.875, 0.8125);
        this.renderStandardBlock(par1BlockBeacon, par2, par3, par4);
        this.clearOverrideBlockTexture();
        return true;
    }
    
    public boolean renderBlockCactus(final Block par1Block, final int par2, final int par3, final int par4) {
        final int var5 = par1Block.colorMultiplier(this.blockAccess, par2, par3, par4);
        float var6 = (var5 >> 16 & 0xFF) / 255.0f;
        float var7 = (var5 >> 8 & 0xFF) / 255.0f;
        float var8 = (var5 & 0xFF) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            final float var9 = (var6 * 30.0f + var7 * 59.0f + var8 * 11.0f) / 100.0f;
            final float var10 = (var6 * 30.0f + var7 * 70.0f) / 100.0f;
            final float var11 = (var6 * 30.0f + var8 * 70.0f) / 100.0f;
            var6 = var9;
            var7 = var10;
            var8 = var11;
        }
        return this.renderBlockCactusImpl(par1Block, par2, par3, par4, var6, var7, var8);
    }
    
    public boolean renderBlockCactusImpl(final Block par1Block, final int par2, final int par3, final int par4, final float par5, final float par6, final float par7) {
        final Tessellator var8 = Tessellator.instance;
        boolean var9 = false;
        final float var10 = 0.5f;
        final float var11 = 1.0f;
        final float var12 = 0.8f;
        final float var13 = 0.6f;
        final float var14 = var10 * par5;
        final float var15 = var11 * par5;
        final float var16 = var12 * par5;
        final float var17 = var13 * par5;
        final float var18 = var10 * par6;
        final float var19 = var11 * par6;
        final float var20 = var12 * par6;
        final float var21 = var13 * par6;
        final float var22 = var10 * par7;
        final float var23 = var11 * par7;
        final float var24 = var12 * par7;
        final float var25 = var13 * par7;
        final float var26 = 0.0625f;
        final int var27 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3 - 1, par4, 0)) {
            var8.setBrightness((this.renderMinY > 0.0) ? var27 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4));
            var8.setColorOpaque_F(var14, var18, var22);
            this.renderFaceYNeg(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 0));
            var9 = true;
        }
        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3 + 1, par4, 1)) {
            var8.setBrightness((this.renderMaxY < 1.0) ? var27 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4));
            var8.setColorOpaque_F(var15, var19, var23);
            this.renderFaceYPos(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 1));
            var9 = true;
        }
        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3, par4 - 1, 2)) {
            var8.setBrightness((this.renderMinZ > 0.0) ? var27 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1));
            var8.setColorOpaque_F(var16, var20, var24);
            var8.addTranslation(0.0f, 0.0f, var26);
            this.renderFaceZNeg(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 2));
            var8.addTranslation(0.0f, 0.0f, -var26);
            var9 = true;
        }
        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3, par4 + 1, 3)) {
            var8.setBrightness((this.renderMaxZ < 1.0) ? var27 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1));
            var8.setColorOpaque_F(var16, var20, var24);
            var8.addTranslation(0.0f, 0.0f, -var26);
            this.renderFaceZPos(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 3));
            var8.addTranslation(0.0f, 0.0f, var26);
            var9 = true;
        }
        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2 - 1, par3, par4, 4)) {
            var8.setBrightness((this.renderMinX > 0.0) ? var27 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4));
            var8.setColorOpaque_F(var17, var21, var25);
            var8.addTranslation(var26, 0.0f, 0.0f);
            this.renderFaceXNeg(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 4));
            var8.addTranslation(-var26, 0.0f, 0.0f);
            var9 = true;
        }
        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2 + 1, par3, par4, 5)) {
            var8.setBrightness((this.renderMaxX < 1.0) ? var27 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4));
            var8.setColorOpaque_F(var17, var21, var25);
            var8.addTranslation(-var26, 0.0f, 0.0f);
            this.renderFaceXPos(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 5));
            var8.addTranslation(var26, 0.0f, 0.0f);
            var9 = true;
        }
        return var9;
    }
    
    public boolean renderBlockFence(final BlockFence par1BlockFence, final int par2, final int par3, final int par4) {
        boolean var5 = false;
        float var6 = 0.375f;
        float var7 = 0.625f;
        this.setRenderBounds(var6, 0.0, var6, var7, 1.0, var7);
        this.renderStandardBlock(par1BlockFence, par2, par3, par4);
        var5 = true;
        boolean var8 = false;
        boolean var9 = false;
        if (par1BlockFence.canConnectFenceTo(this.blockAccess, par2 - 1, par3, par4) || par1BlockFence.canConnectFenceTo(this.blockAccess, par2 + 1, par3, par4)) {
            var8 = true;
        }
        if (par1BlockFence.canConnectFenceTo(this.blockAccess, par2, par3, par4 - 1) || par1BlockFence.canConnectFenceTo(this.blockAccess, par2, par3, par4 + 1)) {
            var9 = true;
        }
        final boolean var10 = par1BlockFence.canConnectFenceTo(this.blockAccess, par2 - 1, par3, par4);
        final boolean var11 = par1BlockFence.canConnectFenceTo(this.blockAccess, par2 + 1, par3, par4);
        final boolean var12 = par1BlockFence.canConnectFenceTo(this.blockAccess, par2, par3, par4 - 1);
        final boolean var13 = par1BlockFence.canConnectFenceTo(this.blockAccess, par2, par3, par4 + 1);
        if (!var8 && !var9) {
            var8 = true;
        }
        var6 = 0.4375f;
        var7 = 0.5625f;
        float var14 = 0.75f;
        float var15 = 0.9375f;
        final float var16 = var10 ? 0.0f : var6;
        final float var17 = var11 ? 1.0f : var7;
        final float var18 = var12 ? 0.0f : var6;
        final float var19 = var13 ? 1.0f : var7;
        if (var8) {
            this.setRenderBounds(var16, var14, var6, var17, var15, var7);
            this.renderStandardBlock(par1BlockFence, par2, par3, par4);
            var5 = true;
        }
        if (var9) {
            this.setRenderBounds(var6, var14, var18, var7, var15, var19);
            this.renderStandardBlock(par1BlockFence, par2, par3, par4);
            var5 = true;
        }
        var14 = 0.375f;
        var15 = 0.5625f;
        if (var8) {
            this.setRenderBounds(var16, var14, var6, var17, var15, var7);
            this.renderStandardBlock(par1BlockFence, par2, par3, par4);
            var5 = true;
        }
        if (var9) {
            this.setRenderBounds(var6, var14, var18, var7, var15, var19);
            this.renderStandardBlock(par1BlockFence, par2, par3, par4);
            var5 = true;
        }
        par1BlockFence.setBlockBoundsBasedOnState(this.blockAccess, par2, par3, par4);
        if (Config.isBetterSnow() && this.hasSnowNeighbours(par2, par3, par4)) {
            this.renderSnow(par2, par3, par4, Block.snow.maxY);
        }
        return var5;
    }
    
    public boolean renderBlockWall(final BlockWall par1BlockWall, final int par2, final int par3, final int par4) {
        final boolean var5 = par1BlockWall.canConnectWallTo(this.blockAccess, par2 - 1, par3, par4);
        final boolean var6 = par1BlockWall.canConnectWallTo(this.blockAccess, par2 + 1, par3, par4);
        final boolean var7 = par1BlockWall.canConnectWallTo(this.blockAccess, par2, par3, par4 - 1);
        final boolean var8 = par1BlockWall.canConnectWallTo(this.blockAccess, par2, par3, par4 + 1);
        final boolean var9 = var7 && var8 && !var5 && !var6;
        final boolean var10 = !var7 && !var8 && var5 && var6;
        final boolean var11 = this.blockAccess.isAirBlock(par2, par3 + 1, par4);
        if ((var9 || var10) && var11) {
            if (var9) {
                this.setRenderBounds(0.3125, 0.0, 0.0, 0.6875, 0.8125, 1.0);
                this.renderStandardBlock(par1BlockWall, par2, par3, par4);
            }
            else {
                this.setRenderBounds(0.0, 0.0, 0.3125, 1.0, 0.8125, 0.6875);
                this.renderStandardBlock(par1BlockWall, par2, par3, par4);
            }
        }
        else {
            this.setRenderBounds(0.25, 0.0, 0.25, 0.75, 1.0, 0.75);
            this.renderStandardBlock(par1BlockWall, par2, par3, par4);
            if (var5) {
                this.setRenderBounds(0.0, 0.0, 0.3125, 0.25, 0.8125, 0.6875);
                this.renderStandardBlock(par1BlockWall, par2, par3, par4);
            }
            if (var6) {
                this.setRenderBounds(0.75, 0.0, 0.3125, 1.0, 0.8125, 0.6875);
                this.renderStandardBlock(par1BlockWall, par2, par3, par4);
            }
            if (var7) {
                this.setRenderBounds(0.3125, 0.0, 0.0, 0.6875, 0.8125, 0.25);
                this.renderStandardBlock(par1BlockWall, par2, par3, par4);
            }
            if (var8) {
                this.setRenderBounds(0.3125, 0.0, 0.75, 0.6875, 0.8125, 1.0);
                this.renderStandardBlock(par1BlockWall, par2, par3, par4);
            }
        }
        par1BlockWall.setBlockBoundsBasedOnState(this.blockAccess, par2, par3, par4);
        if (Config.isBetterSnow() && this.hasSnowNeighbours(par2, par3, par4)) {
            this.renderSnow(par2, par3, par4, Block.snow.maxY);
        }
        return true;
    }
    
    public boolean renderBlockDragonEgg(final BlockDragonEgg par1BlockDragonEgg, final int par2, final int par3, final int par4) {
        boolean var5 = false;
        int var6 = 0;
        for (int var7 = 0; var7 < 8; ++var7) {
            byte var8 = 0;
            byte var9 = 1;
            if (var7 == 0) {
                var8 = 2;
            }
            if (var7 == 1) {
                var8 = 3;
            }
            if (var7 == 2) {
                var8 = 4;
            }
            if (var7 == 3) {
                var8 = 5;
                var9 = 2;
            }
            if (var7 == 4) {
                var8 = 6;
                var9 = 3;
            }
            if (var7 == 5) {
                var8 = 7;
                var9 = 5;
            }
            if (var7 == 6) {
                var8 = 6;
                var9 = 2;
            }
            if (var7 == 7) {
                var8 = 3;
            }
            final float var10 = var8 / 16.0f;
            final float var11 = 1.0f - var6 / 16.0f;
            final float var12 = 1.0f - (var6 + var9) / 16.0f;
            var6 += var9;
            this.setRenderBounds(0.5f - var10, var12, 0.5f - var10, 0.5f + var10, var11, 0.5f + var10);
            this.renderStandardBlock(par1BlockDragonEgg, par2, par3, par4);
        }
        var5 = true;
        this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
        return var5;
    }
    
    public boolean renderBlockFenceGate(final BlockFenceGate par1BlockFenceGate, final int par2, final int par3, final int par4) {
        final boolean var5 = true;
        final int var6 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        final boolean var7 = BlockFenceGate.isFenceGateOpen(var6);
        final int var8 = BlockDirectional.getDirection(var6);
        float var9 = 0.375f;
        float var10 = 0.5625f;
        float var11 = 0.75f;
        float var12 = 0.9375f;
        float var13 = 0.3125f;
        float var14 = 1.0f;
        if (((var8 == 2 || var8 == 0) && this.blockAccess.getBlockId(par2 - 1, par3, par4) == Block.cobblestoneWall.blockID && this.blockAccess.getBlockId(par2 + 1, par3, par4) == Block.cobblestoneWall.blockID) || ((var8 == 3 || var8 == 1) && this.blockAccess.getBlockId(par2, par3, par4 - 1) == Block.cobblestoneWall.blockID && this.blockAccess.getBlockId(par2, par3, par4 + 1) == Block.cobblestoneWall.blockID)) {
            var9 -= 0.1875f;
            var10 -= 0.1875f;
            var11 -= 0.1875f;
            var12 -= 0.1875f;
            var13 -= 0.1875f;
            var14 -= 0.1875f;
        }
        this.renderAllFaces = true;
        if (var8 != 3 && var8 != 1) {
            float var15 = 0.0f;
            float var16 = 0.125f;
            final float var17 = 0.4375f;
            final float var18 = 0.5625f;
            this.setRenderBounds(var15, var13, var17, var16, var14, var18);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            var15 = 0.875f;
            var16 = 1.0f;
            this.setRenderBounds(var15, var13, var17, var16, var14, var18);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
        }
        else {
            this.uvRotateTop = 1;
            final float var15 = 0.4375f;
            final float var16 = 0.5625f;
            float var17 = 0.0f;
            float var18 = 0.125f;
            this.setRenderBounds(var15, var13, var17, var16, var14, var18);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            var17 = 0.875f;
            var18 = 1.0f;
            this.setRenderBounds(var15, var13, var17, var16, var14, var18);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            this.uvRotateTop = 0;
        }
        if (var7) {
            if (var8 == 2 || var8 == 0) {
                this.uvRotateTop = 1;
            }
            if (var8 == 3) {
                this.setRenderBounds(0.8125, var9, 0.0, 0.9375, var12, 0.125);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.8125, var9, 0.875, 0.9375, var12, 1.0);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.5625, var9, 0.0, 0.8125, var10, 0.125);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.5625, var9, 0.875, 0.8125, var10, 1.0);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.5625, var11, 0.0, 0.8125, var12, 0.125);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.5625, var11, 0.875, 0.8125, var12, 1.0);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            }
            else if (var8 == 1) {
                this.setRenderBounds(0.0625, var9, 0.0, 0.1875, var12, 0.125);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.0625, var9, 0.875, 0.1875, var12, 1.0);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.1875, var9, 0.0, 0.4375, var10, 0.125);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.1875, var9, 0.875, 0.4375, var10, 1.0);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.1875, var11, 0.0, 0.4375, var12, 0.125);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.1875, var11, 0.875, 0.4375, var12, 1.0);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            }
            else if (var8 == 0) {
                this.setRenderBounds(0.0, var9, 0.8125, 0.125, var12, 0.9375);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.875, var9, 0.8125, 1.0, var12, 0.9375);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.0, var9, 0.5625, 0.125, var10, 0.8125);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.875, var9, 0.5625, 1.0, var10, 0.8125);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.0, var11, 0.5625, 0.125, var12, 0.8125);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.875, var11, 0.5625, 1.0, var12, 0.8125);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            }
            else if (var8 == 2) {
                this.setRenderBounds(0.0, var9, 0.0625, 0.125, var12, 0.1875);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.875, var9, 0.0625, 1.0, var12, 0.1875);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.0, var9, 0.1875, 0.125, var10, 0.4375);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.875, var9, 0.1875, 1.0, var10, 0.4375);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.0, var11, 0.1875, 0.125, var12, 0.4375);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.875, var11, 0.1875, 1.0, var12, 0.4375);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            }
        }
        else if (var8 != 3 && var8 != 1) {
            float var15 = 0.375f;
            float var16 = 0.5f;
            final float var17 = 0.4375f;
            final float var18 = 0.5625f;
            this.setRenderBounds(var15, var9, var17, var16, var12, var18);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            var15 = 0.5f;
            var16 = 0.625f;
            this.setRenderBounds(var15, var9, var17, var16, var12, var18);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            var15 = 0.625f;
            var16 = 0.875f;
            this.setRenderBounds(var15, var9, var17, var16, var10, var18);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            this.setRenderBounds(var15, var11, var17, var16, var12, var18);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            var15 = 0.125f;
            var16 = 0.375f;
            this.setRenderBounds(var15, var9, var17, var16, var10, var18);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            this.setRenderBounds(var15, var11, var17, var16, var12, var18);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
        }
        else {
            this.uvRotateTop = 1;
            final float var15 = 0.4375f;
            final float var16 = 0.5625f;
            float var17 = 0.375f;
            float var18 = 0.5f;
            this.setRenderBounds(var15, var9, var17, var16, var12, var18);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            var17 = 0.5f;
            var18 = 0.625f;
            this.setRenderBounds(var15, var9, var17, var16, var12, var18);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            var17 = 0.625f;
            var18 = 0.875f;
            this.setRenderBounds(var15, var9, var17, var16, var10, var18);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            this.setRenderBounds(var15, var11, var17, var16, var12, var18);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            var17 = 0.125f;
            var18 = 0.375f;
            this.setRenderBounds(var15, var9, var17, var16, var10, var18);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            this.setRenderBounds(var15, var11, var17, var16, var12, var18);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
        }
        if (Config.isBetterSnow() && this.hasSnowNeighbours(par2, par3, par4)) {
            this.renderSnow(par2, par3, par4, Block.snow.maxY);
        }
        this.renderAllFaces = false;
        this.uvRotateTop = 0;
        this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
        return var5;
    }
    
    public boolean renderBlockHopper(final BlockHopper par1BlockHopper, final int par2, final int par3, final int par4) {
        final Tessellator var5 = Tessellator.instance;
        var5.setBrightness(par1BlockHopper.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        final float var6 = 1.0f;
        final int var7 = par1BlockHopper.colorMultiplier(this.blockAccess, par2, par3, par4);
        float var8 = (var7 >> 16 & 0xFF) / 255.0f;
        float var9 = (var7 >> 8 & 0xFF) / 255.0f;
        float var10 = (var7 & 0xFF) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            final float var11 = (var8 * 30.0f + var9 * 59.0f + var10 * 11.0f) / 100.0f;
            final float var12 = (var8 * 30.0f + var9 * 70.0f) / 100.0f;
            final float var13 = (var8 * 30.0f + var10 * 70.0f) / 100.0f;
            var8 = var11;
            var9 = var12;
            var10 = var13;
        }
        var5.setColorOpaque_F(var6 * var8, var6 * var9, var6 * var10);
        return this.renderBlockHopperMetadata(par1BlockHopper, par2, par3, par4, this.blockAccess.getBlockMetadata(par2, par3, par4), false);
    }
    
    public boolean renderBlockHopperMetadata(final BlockHopper par1BlockHopper, final int par2, final int par3, final int par4, final int par5, final boolean par6) {
        final Tessellator var7 = Tessellator.instance;
        final int var8 = BlockHopper.getDirectionFromMetadata(par5);
        final double var9 = 0.625;
        this.setRenderBounds(0.0, var9, 0.0, 1.0, 1.0, 1.0);
        if (par6) {
            var7.startDrawingQuads();
            var7.setNormal(0.0f, -1.0f, 0.0f);
            this.renderFaceYNeg(par1BlockHopper, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(par1BlockHopper, 0, par5));
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(0.0f, 1.0f, 0.0f);
            this.renderFaceYPos(par1BlockHopper, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(par1BlockHopper, 1, par5));
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(0.0f, 0.0f, -1.0f);
            this.renderFaceZNeg(par1BlockHopper, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(par1BlockHopper, 2, par5));
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(0.0f, 0.0f, 1.0f);
            this.renderFaceZPos(par1BlockHopper, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(par1BlockHopper, 3, par5));
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(-1.0f, 0.0f, 0.0f);
            this.renderFaceXNeg(par1BlockHopper, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(par1BlockHopper, 4, par5));
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(1.0f, 0.0f, 0.0f);
            this.renderFaceXPos(par1BlockHopper, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(par1BlockHopper, 5, par5));
            var7.draw();
        }
        else {
            this.renderStandardBlock(par1BlockHopper, par2, par3, par4);
        }
        if (!par6) {
            var7.setBrightness(par1BlockHopper.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
            final float var10 = 1.0f;
            final int var11 = par1BlockHopper.colorMultiplier(this.blockAccess, par2, par3, par4);
            float var12 = (var11 >> 16 & 0xFF) / 255.0f;
            float var13 = (var11 >> 8 & 0xFF) / 255.0f;
            float var14 = (var11 & 0xFF) / 255.0f;
            if (EntityRenderer.anaglyphEnable) {
                final float var15 = (var12 * 30.0f + var13 * 59.0f + var14 * 11.0f) / 100.0f;
                final float var16 = (var12 * 30.0f + var13 * 70.0f) / 100.0f;
                final float var17 = (var12 * 30.0f + var14 * 70.0f) / 100.0f;
                var12 = var15;
                var13 = var16;
                var14 = var17;
            }
            var7.setColorOpaque_F(var10 * var12, var10 * var13, var10 * var14);
        }
        final Icon var18 = BlockHopper.getHopperIcon("hopper");
        final Icon var19 = BlockHopper.getHopperIcon("hopper_inside");
        float var12 = 0.125f;
        if (par6) {
            var7.startDrawingQuads();
            var7.setNormal(1.0f, 0.0f, 0.0f);
            this.renderFaceXPos(par1BlockHopper, -1.0f + var12, 0.0, 0.0, var18);
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(-1.0f, 0.0f, 0.0f);
            this.renderFaceXNeg(par1BlockHopper, 1.0f - var12, 0.0, 0.0, var18);
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(0.0f, 0.0f, 1.0f);
            this.renderFaceZPos(par1BlockHopper, 0.0, 0.0, -1.0f + var12, var18);
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(0.0f, 0.0f, -1.0f);
            this.renderFaceZNeg(par1BlockHopper, 0.0, 0.0, 1.0f - var12, var18);
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(0.0f, 1.0f, 0.0f);
            this.renderFaceYPos(par1BlockHopper, 0.0, -1.0 + var9, 0.0, var19);
            var7.draw();
        }
        else {
            this.renderFaceXPos(par1BlockHopper, par2 - 1.0f + var12, par3, par4, var18);
            this.renderFaceXNeg(par1BlockHopper, par2 + 1.0f - var12, par3, par4, var18);
            this.renderFaceZPos(par1BlockHopper, par2, par3, par4 - 1.0f + var12, var18);
            this.renderFaceZNeg(par1BlockHopper, par2, par3, par4 + 1.0f - var12, var18);
            this.renderFaceYPos(par1BlockHopper, par2, par3 - 1.0f + var9, par4, var19);
        }
        this.setOverrideBlockTexture(var18);
        final double var20 = 0.25;
        final double var21 = 0.25;
        this.setRenderBounds(var20, var21, var20, 1.0 - var20, var9 - 0.002, 1.0 - var20);
        if (par6) {
            var7.startDrawingQuads();
            var7.setNormal(1.0f, 0.0f, 0.0f);
            this.renderFaceXPos(par1BlockHopper, 0.0, 0.0, 0.0, var18);
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(-1.0f, 0.0f, 0.0f);
            this.renderFaceXNeg(par1BlockHopper, 0.0, 0.0, 0.0, var18);
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(0.0f, 0.0f, 1.0f);
            this.renderFaceZPos(par1BlockHopper, 0.0, 0.0, 0.0, var18);
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(0.0f, 0.0f, -1.0f);
            this.renderFaceZNeg(par1BlockHopper, 0.0, 0.0, 0.0, var18);
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(0.0f, 1.0f, 0.0f);
            this.renderFaceYPos(par1BlockHopper, 0.0, 0.0, 0.0, var18);
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(0.0f, -1.0f, 0.0f);
            this.renderFaceYNeg(par1BlockHopper, 0.0, 0.0, 0.0, var18);
            var7.draw();
        }
        else {
            this.renderStandardBlock(par1BlockHopper, par2, par3, par4);
        }
        if (!par6) {
            final double var22 = 0.375;
            final double var23 = 0.25;
            this.setOverrideBlockTexture(var18);
            if (var8 == 0) {
                this.setRenderBounds(var22, 0.0, var22, 1.0 - var22, 0.25, 1.0 - var22);
                this.renderStandardBlock(par1BlockHopper, par2, par3, par4);
            }
            if (var8 == 2) {
                this.setRenderBounds(var22, var21, 0.0, 1.0 - var22, var21 + var23, var20);
                this.renderStandardBlock(par1BlockHopper, par2, par3, par4);
            }
            if (var8 == 3) {
                this.setRenderBounds(var22, var21, 1.0 - var20, 1.0 - var22, var21 + var23, 1.0);
                this.renderStandardBlock(par1BlockHopper, par2, par3, par4);
            }
            if (var8 == 4) {
                this.setRenderBounds(0.0, var21, var22, var20, var21 + var23, 1.0 - var22);
                this.renderStandardBlock(par1BlockHopper, par2, par3, par4);
            }
            if (var8 == 5) {
                this.setRenderBounds(1.0 - var20, var21, var22, 1.0, var21 + var23, 1.0 - var22);
                this.renderStandardBlock(par1BlockHopper, par2, par3, par4);
            }
        }
        this.clearOverrideBlockTexture();
        return true;
    }
    
    public boolean renderBlockStairs(final BlockStairs par1BlockStairs, final int par2, final int par3, final int par4) {
        par1BlockStairs.func_82541_d(this.blockAccess, par2, par3, par4);
        this.setRenderBoundsFromBlock(par1BlockStairs);
        this.renderStandardBlock(par1BlockStairs, par2, par3, par4);
        final boolean var5 = par1BlockStairs.func_82542_g(this.blockAccess, par2, par3, par4);
        this.setRenderBoundsFromBlock(par1BlockStairs);
        this.renderStandardBlock(par1BlockStairs, par2, par3, par4);
        if (var5 && par1BlockStairs.func_82544_h(this.blockAccess, par2, par3, par4)) {
            this.setRenderBoundsFromBlock(par1BlockStairs);
            this.renderStandardBlock(par1BlockStairs, par2, par3, par4);
        }
        return true;
    }
    
    public boolean renderBlockDoor(final Block par1Block, final int par2, final int par3, final int par4) {
        final Tessellator var5 = Tessellator.instance;
        final int var6 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        if ((var6 & 0x8) != 0x0) {
            if (this.blockAccess.getBlockId(par2, par3 - 1, par4) != par1Block.blockID) {
                return false;
            }
        }
        else if (this.blockAccess.getBlockId(par2, par3 + 1, par4) != par1Block.blockID) {
            return false;
        }
        boolean var7 = false;
        final float var8 = 0.5f;
        final float var9 = 1.0f;
        final float var10 = 0.8f;
        final float var11 = 0.6f;
        final int var12 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
        var5.setBrightness((this.renderMinY > 0.0) ? var12 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4));
        var5.setColorOpaque_F(var8, var8, var8);
        this.renderFaceYNeg(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 0));
        var7 = true;
        var5.setBrightness((this.renderMaxY < 1.0) ? var12 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4));
        var5.setColorOpaque_F(var9, var9, var9);
        this.renderFaceYPos(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 1));
        var7 = true;
        var5.setBrightness((this.renderMinZ > 0.0) ? var12 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1));
        var5.setColorOpaque_F(var10, var10, var10);
        Icon var13 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 2);
        this.renderFaceZNeg(par1Block, par2, par3, par4, var13);
        var7 = true;
        this.flipTexture = false;
        var5.setBrightness((this.renderMaxZ < 1.0) ? var12 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1));
        var5.setColorOpaque_F(var10, var10, var10);
        var13 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 3);
        this.renderFaceZPos(par1Block, par2, par3, par4, var13);
        var7 = true;
        this.flipTexture = false;
        var5.setBrightness((this.renderMinX > 0.0) ? var12 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4));
        var5.setColorOpaque_F(var11, var11, var11);
        var13 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 4);
        this.renderFaceXNeg(par1Block, par2, par3, par4, var13);
        var7 = true;
        this.flipTexture = false;
        var5.setBrightness((this.renderMaxX < 1.0) ? var12 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4));
        var5.setColorOpaque_F(var11, var11, var11);
        var13 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 5);
        this.renderFaceXPos(par1Block, par2, par3, par4, var13);
        var7 = true;
        this.flipTexture = false;
        return var7;
    }
    
    public void renderFaceYNeg(final Block par1Block, final double par2, final double par4, final double par6, Icon par8Icon) {
        final Tessellator var9 = Tessellator.instance;
        if (this.hasOverrideBlockTexture()) {
            par8Icon = this.overrideBlockTexture;
        }
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null && this.uvRotateBottom == 0) {
            par8Icon = ConnectedTextures.getConnectedTexture(this.blockAccess, par1Block, (int)par2, (int)par4, (int)par6, 0, par8Icon);
        }
        boolean var10 = false;
        if (Config.isNaturalTextures() && this.overrideBlockTexture == null && this.uvRotateBottom == 0) {
            final NaturalProperties var11 = NaturalTextures.getNaturalProperties(par8Icon);
            if (var11 != null) {
                final int var12 = Config.getRandom((int)par2, (int)par4, (int)par6, 0);
                if (var11.rotation > 1) {
                    this.uvRotateBottom = (var12 & 0x3);
                }
                if (var11.rotation == 2) {
                    this.uvRotateBottom = this.uvRotateBottom / 2 * 3;
                }
                if (var11.flip) {
                    this.flipTexture = ((var12 & 0x4) != 0x0);
                }
                var10 = true;
            }
        }
        double var13 = par8Icon.getInterpolatedU(this.renderMinX * 16.0);
        double var14 = par8Icon.getInterpolatedU(this.renderMaxX * 16.0);
        double var15 = par8Icon.getInterpolatedV(this.renderMinZ * 16.0);
        double var16 = par8Icon.getInterpolatedV(this.renderMaxZ * 16.0);
        if (this.renderMinX < 0.0 || this.renderMaxX > 1.0) {
            var13 = par8Icon.getMinU();
            var14 = par8Icon.getMaxU();
        }
        if (this.renderMinZ < 0.0 || this.renderMaxZ > 1.0) {
            var15 = par8Icon.getMinV();
            var16 = par8Icon.getMaxV();
        }
        if (this.flipTexture) {
            final double var17 = var13;
            var13 = var14;
            var14 = var17;
        }
        double var17 = var14;
        double var18 = var13;
        double var19 = var15;
        double var20 = var16;
        if (this.uvRotateBottom == 2) {
            var13 = par8Icon.getInterpolatedU(this.renderMinZ * 16.0);
            var15 = par8Icon.getInterpolatedV(16.0 - this.renderMaxX * 16.0);
            var14 = par8Icon.getInterpolatedU(this.renderMaxZ * 16.0);
            var16 = par8Icon.getInterpolatedV(16.0 - this.renderMinX * 16.0);
            if (this.flipTexture) {
                final double var21 = var13;
                var13 = var14;
                var14 = var21;
            }
            var19 = var15;
            var20 = var16;
            var17 = var13;
            var18 = var14;
            var15 = var16;
            var16 = var19;
        }
        else if (this.uvRotateBottom == 1) {
            var13 = par8Icon.getInterpolatedU(16.0 - this.renderMaxZ * 16.0);
            var15 = par8Icon.getInterpolatedV(this.renderMinX * 16.0);
            var14 = par8Icon.getInterpolatedU(16.0 - this.renderMinZ * 16.0);
            var16 = par8Icon.getInterpolatedV(this.renderMaxX * 16.0);
            if (this.flipTexture) {
                final double var21 = var13;
                var13 = var14;
                var14 = var21;
            }
            var17 = var14;
            var18 = var13;
            var13 = var14;
            var14 = var18;
            var19 = var16;
            var20 = var15;
        }
        else if (this.uvRotateBottom == 3) {
            var13 = par8Icon.getInterpolatedU(16.0 - this.renderMinX * 16.0);
            var14 = par8Icon.getInterpolatedU(16.0 - this.renderMaxX * 16.0);
            var15 = par8Icon.getInterpolatedV(16.0 - this.renderMinZ * 16.0);
            var16 = par8Icon.getInterpolatedV(16.0 - this.renderMaxZ * 16.0);
            if (this.flipTexture) {
                final double var21 = var13;
                var13 = var14;
                var14 = var21;
            }
            var17 = var14;
            var18 = var13;
            var19 = var15;
            var20 = var16;
        }
        if (var10) {
            this.uvRotateBottom = 0;
            this.flipTexture = false;
        }
        final double var21 = par2 + this.renderMinX;
        final double var22 = par2 + this.renderMaxX;
        final double var23 = par4 + this.renderMinY;
        final double var24 = par6 + this.renderMinZ;
        final double var25 = par6 + this.renderMaxZ;
        if (this.enableAO) {
            var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            var9.setBrightness(this.brightnessTopLeft);
            var9.addVertexWithUV(var21, var23, var25, var18, var20);
            var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            var9.setBrightness(this.brightnessBottomLeft);
            var9.addVertexWithUV(var21, var23, var24, var13, var15);
            var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            var9.setBrightness(this.brightnessBottomRight);
            var9.addVertexWithUV(var22, var23, var24, var17, var19);
            var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            var9.setBrightness(this.brightnessTopRight);
            var9.addVertexWithUV(var22, var23, var25, var14, var16);
        }
        else {
            var9.addVertexWithUV(var21, var23, var25, var18, var20);
            var9.addVertexWithUV(var21, var23, var24, var13, var15);
            var9.addVertexWithUV(var22, var23, var24, var17, var19);
            var9.addVertexWithUV(var22, var23, var25, var14, var16);
        }
    }
    
    public void renderFaceYPos(final Block par1Block, final double par2, final double par4, final double par6, Icon par8Icon) {
        final Tessellator var9 = Tessellator.instance;
        if (this.hasOverrideBlockTexture()) {
            par8Icon = this.overrideBlockTexture;
        }
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null && this.uvRotateTop == 0) {
            par8Icon = ConnectedTextures.getConnectedTexture(this.blockAccess, par1Block, (int)par2, (int)par4, (int)par6, 1, par8Icon);
        }
        boolean var10 = false;
        if (Config.isNaturalTextures() && this.overrideBlockTexture == null && this.uvRotateTop == 0) {
            final NaturalProperties var11 = NaturalTextures.getNaturalProperties(par8Icon);
            if (var11 != null) {
                final int var12 = Config.getRandom((int)par2, (int)par4, (int)par6, 1);
                if (var11.rotation > 1) {
                    this.uvRotateTop = (var12 & 0x3);
                }
                if (var11.rotation == 2) {
                    this.uvRotateTop = this.uvRotateTop / 2 * 3;
                }
                if (var11.flip) {
                    this.flipTexture = ((var12 & 0x4) != 0x0);
                }
                var10 = true;
            }
        }
        double var13 = par8Icon.getInterpolatedU(this.renderMinX * 16.0);
        double var14 = par8Icon.getInterpolatedU(this.renderMaxX * 16.0);
        double var15 = par8Icon.getInterpolatedV(this.renderMinZ * 16.0);
        double var16 = par8Icon.getInterpolatedV(this.renderMaxZ * 16.0);
        if (this.flipTexture) {
            final double var17 = var13;
            var13 = var14;
            var14 = var17;
        }
        if (this.renderMinX < 0.0 || this.renderMaxX > 1.0) {
            var13 = par8Icon.getMinU();
            var14 = par8Icon.getMaxU();
        }
        if (this.renderMinZ < 0.0 || this.renderMaxZ > 1.0) {
            var15 = par8Icon.getMinV();
            var16 = par8Icon.getMaxV();
        }
        double var17 = var14;
        double var18 = var13;
        double var19 = var15;
        double var20 = var16;
        if (this.uvRotateTop == 1) {
            var13 = par8Icon.getInterpolatedU(this.renderMinZ * 16.0);
            var15 = par8Icon.getInterpolatedV(16.0 - this.renderMaxX * 16.0);
            var14 = par8Icon.getInterpolatedU(this.renderMaxZ * 16.0);
            var16 = par8Icon.getInterpolatedV(16.0 - this.renderMinX * 16.0);
            if (this.flipTexture) {
                final double var21 = var13;
                var13 = var14;
                var14 = var21;
            }
            var19 = var15;
            var20 = var16;
            var17 = var13;
            var18 = var14;
            var15 = var16;
            var16 = var19;
        }
        else if (this.uvRotateTop == 2) {
            var13 = par8Icon.getInterpolatedU(16.0 - this.renderMaxZ * 16.0);
            var15 = par8Icon.getInterpolatedV(this.renderMinX * 16.0);
            var14 = par8Icon.getInterpolatedU(16.0 - this.renderMinZ * 16.0);
            var16 = par8Icon.getInterpolatedV(this.renderMaxX * 16.0);
            if (this.flipTexture) {
                final double var21 = var13;
                var13 = var14;
                var14 = var21;
            }
            var17 = var14;
            var18 = var13;
            var13 = var14;
            var14 = var18;
            var19 = var16;
            var20 = var15;
        }
        else if (this.uvRotateTop == 3) {
            var13 = par8Icon.getInterpolatedU(16.0 - this.renderMinX * 16.0);
            var14 = par8Icon.getInterpolatedU(16.0 - this.renderMaxX * 16.0);
            var15 = par8Icon.getInterpolatedV(16.0 - this.renderMinZ * 16.0);
            var16 = par8Icon.getInterpolatedV(16.0 - this.renderMaxZ * 16.0);
            if (this.flipTexture) {
                final double var21 = var13;
                var13 = var14;
                var14 = var21;
            }
            var17 = var14;
            var18 = var13;
            var19 = var15;
            var20 = var16;
        }
        if (var10) {
            this.uvRotateTop = 0;
            this.flipTexture = false;
        }
        final double var21 = par2 + this.renderMinX;
        final double var22 = par2 + this.renderMaxX;
        final double var23 = par4 + this.renderMaxY;
        final double var24 = par6 + this.renderMinZ;
        final double var25 = par6 + this.renderMaxZ;
        if (this.enableAO) {
            var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            var9.setBrightness(this.brightnessTopLeft);
            var9.addVertexWithUV(var22, var23, var25, var14, var16);
            var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            var9.setBrightness(this.brightnessBottomLeft);
            var9.addVertexWithUV(var22, var23, var24, var17, var19);
            var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            var9.setBrightness(this.brightnessBottomRight);
            var9.addVertexWithUV(var21, var23, var24, var13, var15);
            var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            var9.setBrightness(this.brightnessTopRight);
            var9.addVertexWithUV(var21, var23, var25, var18, var20);
        }
        else {
            var9.addVertexWithUV(var22, var23, var25, var14, var16);
            var9.addVertexWithUV(var22, var23, var24, var17, var19);
            var9.addVertexWithUV(var21, var23, var24, var13, var15);
            var9.addVertexWithUV(var21, var23, var25, var18, var20);
        }
    }
    
    public void renderFaceZNeg(final Block par1Block, final double par2, final double par4, final double par6, Icon par8Icon) {
        final Tessellator var9 = Tessellator.instance;
        if (this.hasOverrideBlockTexture()) {
            par8Icon = this.overrideBlockTexture;
        }
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null && this.uvRotateEast == 0) {
            par8Icon = ConnectedTextures.getConnectedTexture(this.blockAccess, par1Block, (int)par2, (int)par4, (int)par6, 2, par8Icon);
        }
        boolean var10 = false;
        if (Config.isNaturalTextures() && this.overrideBlockTexture == null && this.uvRotateEast == 0) {
            final NaturalProperties var11 = NaturalTextures.getNaturalProperties(par8Icon);
            if (var11 != null) {
                final int var12 = Config.getRandom((int)par2, (int)par4, (int)par6, 2);
                if (var11.rotation > 1) {
                    this.uvRotateEast = (var12 & 0x3);
                }
                if (var11.rotation == 2) {
                    this.uvRotateEast = this.uvRotateEast / 2 * 3;
                }
                if (var11.flip) {
                    this.flipTexture = ((var12 & 0x4) != 0x0);
                }
                var10 = true;
            }
        }
        double var13 = par8Icon.getInterpolatedU(this.renderMinX * 16.0);
        double var14 = par8Icon.getInterpolatedU(this.renderMaxX * 16.0);
        double var15 = par8Icon.getInterpolatedV(16.0 - this.renderMaxY * 16.0);
        double var16 = par8Icon.getInterpolatedV(16.0 - this.renderMinY * 16.0);
        if (this.flipTexture) {
            final double var17 = var13;
            var13 = var14;
            var14 = var17;
        }
        if (this.renderMinX < 0.0 || this.renderMaxX > 1.0) {
            var13 = par8Icon.getMinU();
            var14 = par8Icon.getMaxU();
        }
        if (this.renderMinY < 0.0 || this.renderMaxY > 1.0) {
            var15 = par8Icon.getMinV();
            var16 = par8Icon.getMaxV();
        }
        double var17 = var14;
        double var18 = var13;
        double var19 = var15;
        double var20 = var16;
        if (this.uvRotateEast == 2) {
            var13 = par8Icon.getInterpolatedU(this.renderMinY * 16.0);
            var15 = par8Icon.getInterpolatedV(16.0 - this.renderMinX * 16.0);
            var14 = par8Icon.getInterpolatedU(this.renderMaxY * 16.0);
            var16 = par8Icon.getInterpolatedV(16.0 - this.renderMaxX * 16.0);
            if (this.flipTexture) {
                final double var21 = var13;
                var13 = var14;
                var14 = var21;
            }
            var19 = var15;
            var20 = var16;
            var17 = var13;
            var18 = var14;
            var15 = var16;
            var16 = var19;
        }
        else if (this.uvRotateEast == 1) {
            var13 = par8Icon.getInterpolatedU(16.0 - this.renderMaxY * 16.0);
            var15 = par8Icon.getInterpolatedV(this.renderMaxX * 16.0);
            var14 = par8Icon.getInterpolatedU(16.0 - this.renderMinY * 16.0);
            var16 = par8Icon.getInterpolatedV(this.renderMinX * 16.0);
            if (this.flipTexture) {
                final double var21 = var13;
                var13 = var14;
                var14 = var21;
            }
            var17 = var14;
            var18 = var13;
            var13 = var14;
            var14 = var18;
            var19 = var16;
            var20 = var15;
        }
        else if (this.uvRotateEast == 3) {
            var13 = par8Icon.getInterpolatedU(16.0 - this.renderMinX * 16.0);
            var14 = par8Icon.getInterpolatedU(16.0 - this.renderMaxX * 16.0);
            var15 = par8Icon.getInterpolatedV(this.renderMaxY * 16.0);
            var16 = par8Icon.getInterpolatedV(this.renderMinY * 16.0);
            if (this.flipTexture) {
                final double var21 = var13;
                var13 = var14;
                var14 = var21;
            }
            var17 = var14;
            var18 = var13;
            var19 = var15;
            var20 = var16;
        }
        if (var10) {
            this.uvRotateEast = 0;
            this.flipTexture = false;
        }
        final double var21 = par2 + this.renderMinX;
        final double var22 = par2 + this.renderMaxX;
        final double var23 = par4 + this.renderMinY;
        final double var24 = par4 + this.renderMaxY;
        final double var25 = par6 + this.renderMinZ;
        if (this.enableAO) {
            var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            var9.setBrightness(this.brightnessTopLeft);
            var9.addVertexWithUV(var21, var24, var25, var17, var19);
            var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            var9.setBrightness(this.brightnessBottomLeft);
            var9.addVertexWithUV(var22, var24, var25, var13, var15);
            var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            var9.setBrightness(this.brightnessBottomRight);
            var9.addVertexWithUV(var22, var23, var25, var18, var20);
            var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            var9.setBrightness(this.brightnessTopRight);
            var9.addVertexWithUV(var21, var23, var25, var14, var16);
        }
        else {
            var9.addVertexWithUV(var21, var24, var25, var17, var19);
            var9.addVertexWithUV(var22, var24, var25, var13, var15);
            var9.addVertexWithUV(var22, var23, var25, var18, var20);
            var9.addVertexWithUV(var21, var23, var25, var14, var16);
        }
    }
    
    public void renderFaceZPos(final Block par1Block, final double par2, final double par4, final double par6, Icon par8Icon) {
        final Tessellator var9 = Tessellator.instance;
        if (this.hasOverrideBlockTexture()) {
            par8Icon = this.overrideBlockTexture;
        }
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null && this.uvRotateWest == 0) {
            par8Icon = ConnectedTextures.getConnectedTexture(this.blockAccess, par1Block, (int)par2, (int)par4, (int)par6, 3, par8Icon);
        }
        boolean var10 = false;
        if (Config.isNaturalTextures() && this.overrideBlockTexture == null && this.uvRotateWest == 0) {
            final NaturalProperties var11 = NaturalTextures.getNaturalProperties(par8Icon);
            if (var11 != null) {
                final int var12 = Config.getRandom((int)par2, (int)par4, (int)par6, 3);
                if (var11.rotation > 1) {
                    this.uvRotateWest = (var12 & 0x3);
                }
                if (var11.rotation == 2) {
                    this.uvRotateWest = this.uvRotateWest / 2 * 3;
                }
                if (var11.flip) {
                    this.flipTexture = ((var12 & 0x4) != 0x0);
                }
                var10 = true;
            }
        }
        double var13 = par8Icon.getInterpolatedU(this.renderMinX * 16.0);
        double var14 = par8Icon.getInterpolatedU(this.renderMaxX * 16.0);
        double var15 = par8Icon.getInterpolatedV(16.0 - this.renderMaxY * 16.0);
        double var16 = par8Icon.getInterpolatedV(16.0 - this.renderMinY * 16.0);
        if (this.flipTexture) {
            final double var17 = var13;
            var13 = var14;
            var14 = var17;
        }
        if (this.renderMinX < 0.0 || this.renderMaxX > 1.0) {
            var13 = par8Icon.getMinU();
            var14 = par8Icon.getMaxU();
        }
        if (this.renderMinY < 0.0 || this.renderMaxY > 1.0) {
            var15 = par8Icon.getMinV();
            var16 = par8Icon.getMaxV();
        }
        double var17 = var14;
        double var18 = var13;
        double var19 = var15;
        double var20 = var16;
        if (this.uvRotateWest == 1) {
            var13 = par8Icon.getInterpolatedU(this.renderMinY * 16.0);
            var16 = par8Icon.getInterpolatedV(16.0 - this.renderMinX * 16.0);
            var14 = par8Icon.getInterpolatedU(this.renderMaxY * 16.0);
            var15 = par8Icon.getInterpolatedV(16.0 - this.renderMaxX * 16.0);
            if (this.flipTexture) {
                final double var21 = var13;
                var13 = var14;
                var14 = var21;
            }
            var19 = var15;
            var20 = var16;
            var17 = var13;
            var18 = var14;
            var15 = var16;
            var16 = var19;
        }
        else if (this.uvRotateWest == 2) {
            var13 = par8Icon.getInterpolatedU(16.0 - this.renderMaxY * 16.0);
            var15 = par8Icon.getInterpolatedV(this.renderMinX * 16.0);
            var14 = par8Icon.getInterpolatedU(16.0 - this.renderMinY * 16.0);
            var16 = par8Icon.getInterpolatedV(this.renderMaxX * 16.0);
            if (this.flipTexture) {
                final double var21 = var13;
                var13 = var14;
                var14 = var21;
            }
            var17 = var14;
            var18 = var13;
            var13 = var14;
            var14 = var18;
            var19 = var16;
            var20 = var15;
        }
        else if (this.uvRotateWest == 3) {
            var13 = par8Icon.getInterpolatedU(16.0 - this.renderMinX * 16.0);
            var14 = par8Icon.getInterpolatedU(16.0 - this.renderMaxX * 16.0);
            var15 = par8Icon.getInterpolatedV(this.renderMaxY * 16.0);
            var16 = par8Icon.getInterpolatedV(this.renderMinY * 16.0);
            if (this.flipTexture) {
                final double var21 = var13;
                var13 = var14;
                var14 = var21;
            }
            var17 = var14;
            var18 = var13;
            var19 = var15;
            var20 = var16;
        }
        if (var10) {
            this.uvRotateWest = 0;
            this.flipTexture = false;
        }
        final double var21 = par2 + this.renderMinX;
        final double var22 = par2 + this.renderMaxX;
        final double var23 = par4 + this.renderMinY;
        final double var24 = par4 + this.renderMaxY;
        final double var25 = par6 + this.renderMaxZ;
        if (this.enableAO) {
            var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            var9.setBrightness(this.brightnessTopLeft);
            var9.addVertexWithUV(var21, var24, var25, var13, var15);
            var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            var9.setBrightness(this.brightnessBottomLeft);
            var9.addVertexWithUV(var21, var23, var25, var18, var20);
            var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            var9.setBrightness(this.brightnessBottomRight);
            var9.addVertexWithUV(var22, var23, var25, var14, var16);
            var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            var9.setBrightness(this.brightnessTopRight);
            var9.addVertexWithUV(var22, var24, var25, var17, var19);
        }
        else {
            var9.addVertexWithUV(var21, var24, var25, var13, var15);
            var9.addVertexWithUV(var21, var23, var25, var18, var20);
            var9.addVertexWithUV(var22, var23, var25, var14, var16);
            var9.addVertexWithUV(var22, var24, var25, var17, var19);
        }
    }
    
    public void renderFaceXNeg(final Block par1Block, final double par2, final double par4, final double par6, Icon par8Icon) {
        final Tessellator var9 = Tessellator.instance;
        if (this.hasOverrideBlockTexture()) {
            par8Icon = this.overrideBlockTexture;
        }
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null && this.uvRotateNorth == 0) {
            par8Icon = ConnectedTextures.getConnectedTexture(this.blockAccess, par1Block, (int)par2, (int)par4, (int)par6, 4, par8Icon);
        }
        boolean var10 = false;
        if (Config.isNaturalTextures() && this.overrideBlockTexture == null && this.uvRotateNorth == 0) {
            final NaturalProperties var11 = NaturalTextures.getNaturalProperties(par8Icon);
            if (var11 != null) {
                final int var12 = Config.getRandom((int)par2, (int)par4, (int)par6, 4);
                if (var11.rotation > 1) {
                    this.uvRotateNorth = (var12 & 0x3);
                }
                if (var11.rotation == 2) {
                    this.uvRotateNorth = this.uvRotateNorth / 2 * 3;
                }
                if (var11.flip) {
                    this.flipTexture = ((var12 & 0x4) != 0x0);
                }
                var10 = true;
            }
        }
        double var13 = par8Icon.getInterpolatedU(this.renderMinZ * 16.0);
        double var14 = par8Icon.getInterpolatedU(this.renderMaxZ * 16.0);
        double var15 = par8Icon.getInterpolatedV(16.0 - this.renderMaxY * 16.0);
        double var16 = par8Icon.getInterpolatedV(16.0 - this.renderMinY * 16.0);
        if (this.flipTexture) {
            final double var17 = var13;
            var13 = var14;
            var14 = var17;
        }
        if (this.renderMinZ < 0.0 || this.renderMaxZ > 1.0) {
            var13 = par8Icon.getMinU();
            var14 = par8Icon.getMaxU();
        }
        if (this.renderMinY < 0.0 || this.renderMaxY > 1.0) {
            var15 = par8Icon.getMinV();
            var16 = par8Icon.getMaxV();
        }
        double var17 = var14;
        double var18 = var13;
        double var19 = var15;
        double var20 = var16;
        if (this.uvRotateNorth == 1) {
            var13 = par8Icon.getInterpolatedU(this.renderMinY * 16.0);
            var15 = par8Icon.getInterpolatedV(16.0 - this.renderMaxZ * 16.0);
            var14 = par8Icon.getInterpolatedU(this.renderMaxY * 16.0);
            var16 = par8Icon.getInterpolatedV(16.0 - this.renderMinZ * 16.0);
            if (this.flipTexture) {
                final double var21 = var13;
                var13 = var14;
                var14 = var21;
            }
            var19 = var15;
            var20 = var16;
            var17 = var13;
            var18 = var14;
            var15 = var16;
            var16 = var19;
        }
        else if (this.uvRotateNorth == 2) {
            var13 = par8Icon.getInterpolatedU(16.0 - this.renderMaxY * 16.0);
            var15 = par8Icon.getInterpolatedV(this.renderMinZ * 16.0);
            var14 = par8Icon.getInterpolatedU(16.0 - this.renderMinY * 16.0);
            var16 = par8Icon.getInterpolatedV(this.renderMaxZ * 16.0);
            if (this.flipTexture) {
                final double var21 = var13;
                var13 = var14;
                var14 = var21;
            }
            var17 = var14;
            var18 = var13;
            var13 = var14;
            var14 = var18;
            var19 = var16;
            var20 = var15;
        }
        else if (this.uvRotateNorth == 3) {
            var13 = par8Icon.getInterpolatedU(16.0 - this.renderMinZ * 16.0);
            var14 = par8Icon.getInterpolatedU(16.0 - this.renderMaxZ * 16.0);
            var15 = par8Icon.getInterpolatedV(this.renderMaxY * 16.0);
            var16 = par8Icon.getInterpolatedV(this.renderMinY * 16.0);
            if (this.flipTexture) {
                final double var21 = var13;
                var13 = var14;
                var14 = var21;
            }
            var17 = var14;
            var18 = var13;
            var19 = var15;
            var20 = var16;
        }
        if (var10) {
            this.uvRotateNorth = 0;
            this.flipTexture = false;
        }
        final double var21 = par2 + this.renderMinX;
        final double var22 = par4 + this.renderMinY;
        final double var23 = par4 + this.renderMaxY;
        final double var24 = par6 + this.renderMinZ;
        final double var25 = par6 + this.renderMaxZ;
        if (this.enableAO) {
            var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            var9.setBrightness(this.brightnessTopLeft);
            var9.addVertexWithUV(var21, var23, var25, var17, var19);
            var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            var9.setBrightness(this.brightnessBottomLeft);
            var9.addVertexWithUV(var21, var23, var24, var13, var15);
            var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            var9.setBrightness(this.brightnessBottomRight);
            var9.addVertexWithUV(var21, var22, var24, var18, var20);
            var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            var9.setBrightness(this.brightnessTopRight);
            var9.addVertexWithUV(var21, var22, var25, var14, var16);
        }
        else {
            var9.addVertexWithUV(var21, var23, var25, var17, var19);
            var9.addVertexWithUV(var21, var23, var24, var13, var15);
            var9.addVertexWithUV(var21, var22, var24, var18, var20);
            var9.addVertexWithUV(var21, var22, var25, var14, var16);
        }
    }
    
    public void renderFaceXPos(final Block par1Block, final double par2, final double par4, final double par6, Icon par8Icon) {
        final Tessellator var9 = Tessellator.instance;
        if (this.hasOverrideBlockTexture()) {
            par8Icon = this.overrideBlockTexture;
        }
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null && this.uvRotateSouth == 0) {
            par8Icon = ConnectedTextures.getConnectedTexture(this.blockAccess, par1Block, (int)par2, (int)par4, (int)par6, 5, par8Icon);
        }
        boolean var10 = false;
        if (Config.isNaturalTextures() && this.overrideBlockTexture == null && this.uvRotateSouth == 0) {
            final NaturalProperties var11 = NaturalTextures.getNaturalProperties(par8Icon);
            if (var11 != null) {
                final int var12 = Config.getRandom((int)par2, (int)par4, (int)par6, 5);
                if (var11.rotation > 1) {
                    this.uvRotateSouth = (var12 & 0x3);
                }
                if (var11.rotation == 2) {
                    this.uvRotateSouth = this.uvRotateSouth / 2 * 3;
                }
                if (var11.flip) {
                    this.flipTexture = ((var12 & 0x4) != 0x0);
                }
                var10 = true;
            }
        }
        double var13 = par8Icon.getInterpolatedU(this.renderMinZ * 16.0);
        double var14 = par8Icon.getInterpolatedU(this.renderMaxZ * 16.0);
        double var15 = par8Icon.getInterpolatedV(16.0 - this.renderMaxY * 16.0);
        double var16 = par8Icon.getInterpolatedV(16.0 - this.renderMinY * 16.0);
        if (this.flipTexture) {
            final double var17 = var13;
            var13 = var14;
            var14 = var17;
        }
        if (this.renderMinZ < 0.0 || this.renderMaxZ > 1.0) {
            var13 = par8Icon.getMinU();
            var14 = par8Icon.getMaxU();
        }
        if (this.renderMinY < 0.0 || this.renderMaxY > 1.0) {
            var15 = par8Icon.getMinV();
            var16 = par8Icon.getMaxV();
        }
        double var17 = var14;
        double var18 = var13;
        double var19 = var15;
        double var20 = var16;
        if (this.uvRotateSouth == 2) {
            var13 = par8Icon.getInterpolatedU(this.renderMinY * 16.0);
            var15 = par8Icon.getInterpolatedV(16.0 - this.renderMinZ * 16.0);
            var14 = par8Icon.getInterpolatedU(this.renderMaxY * 16.0);
            var16 = par8Icon.getInterpolatedV(16.0 - this.renderMaxZ * 16.0);
            if (this.flipTexture) {
                final double var21 = var13;
                var13 = var14;
                var14 = var21;
            }
            var19 = var15;
            var20 = var16;
            var17 = var13;
            var18 = var14;
            var15 = var16;
            var16 = var19;
        }
        else if (this.uvRotateSouth == 1) {
            var13 = par8Icon.getInterpolatedU(16.0 - this.renderMaxY * 16.0);
            var15 = par8Icon.getInterpolatedV(this.renderMaxZ * 16.0);
            var14 = par8Icon.getInterpolatedU(16.0 - this.renderMinY * 16.0);
            var16 = par8Icon.getInterpolatedV(this.renderMinZ * 16.0);
            if (this.flipTexture) {
                final double var21 = var13;
                var13 = var14;
                var14 = var21;
            }
            var17 = var14;
            var18 = var13;
            var13 = var14;
            var14 = var18;
            var19 = var16;
            var20 = var15;
        }
        else if (this.uvRotateSouth == 3) {
            var13 = par8Icon.getInterpolatedU(16.0 - this.renderMinZ * 16.0);
            var14 = par8Icon.getInterpolatedU(16.0 - this.renderMaxZ * 16.0);
            var15 = par8Icon.getInterpolatedV(this.renderMaxY * 16.0);
            var16 = par8Icon.getInterpolatedV(this.renderMinY * 16.0);
            if (this.flipTexture) {
                final double var21 = var13;
                var13 = var14;
                var14 = var21;
            }
            var17 = var14;
            var18 = var13;
            var19 = var15;
            var20 = var16;
        }
        if (var10) {
            this.uvRotateSouth = 0;
            this.flipTexture = false;
        }
        final double var21 = par2 + this.renderMaxX;
        final double var22 = par4 + this.renderMinY;
        final double var23 = par4 + this.renderMaxY;
        final double var24 = par6 + this.renderMinZ;
        final double var25 = par6 + this.renderMaxZ;
        if (this.enableAO) {
            var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            var9.setBrightness(this.brightnessTopLeft);
            var9.addVertexWithUV(var21, var22, var25, var18, var20);
            var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            var9.setBrightness(this.brightnessBottomLeft);
            var9.addVertexWithUV(var21, var22, var24, var14, var16);
            var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            var9.setBrightness(this.brightnessBottomRight);
            var9.addVertexWithUV(var21, var23, var24, var17, var19);
            var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            var9.setBrightness(this.brightnessTopRight);
            var9.addVertexWithUV(var21, var23, var25, var13, var15);
        }
        else {
            var9.addVertexWithUV(var21, var22, var25, var18, var20);
            var9.addVertexWithUV(var21, var22, var24, var14, var16);
            var9.addVertexWithUV(var21, var23, var24, var17, var19);
            var9.addVertexWithUV(var21, var23, var25, var13, var15);
        }
    }
    
    public void renderBlockAsItem(final Block par1Block, int par2, final float par3) {
        final Tessellator var4 = Tessellator.instance;
        final boolean var5 = par1Block.blockID == Block.grass.blockID;
        if (par1Block == Block.dispenser || par1Block == Block.dropper || par1Block == Block.furnaceIdle) {
            par2 = 3;
        }
        if (this.useInventoryTint) {
            int var6 = par1Block.getRenderColor(par2);
            if (var5) {
                var6 = 16777215;
            }
            final float var7 = (var6 >> 16 & 0xFF) / 255.0f;
            final float var8 = (var6 >> 8 & 0xFF) / 255.0f;
            final float var9 = (var6 & 0xFF) / 255.0f;
            GL11.glColor4f(var7 * par3, var8 * par3, var9 * par3, 1.0f);
        }
        int var6 = par1Block.getRenderType();
        this.setRenderBoundsFromBlock(par1Block);
        if (var6 != 0 && var6 != 31 && var6 != 39 && var6 != 16 && var6 != 26) {
            if (var6 == 1) {
                var4.startDrawingQuads();
                var4.setNormal(0.0f, -1.0f, 0.0f);
                this.drawCrossedSquares(par1Block, par2, -0.5, -0.5, -0.5, 1.0f);
                var4.draw();
            }
            else if (var6 == 19) {
                var4.startDrawingQuads();
                var4.setNormal(0.0f, -1.0f, 0.0f);
                par1Block.setBlockBoundsForItemRender();
                this.renderBlockStemSmall(par1Block, par2, this.renderMaxY, -0.5, -0.5, -0.5);
                var4.draw();
            }
            else if (var6 == 23) {
                var4.startDrawingQuads();
                var4.setNormal(0.0f, -1.0f, 0.0f);
                par1Block.setBlockBoundsForItemRender();
                var4.draw();
            }
            else if (var6 == 13) {
                par1Block.setBlockBoundsForItemRender();
                GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
                final float var7 = 0.0625f;
                var4.startDrawingQuads();
                var4.setNormal(0.0f, -1.0f, 0.0f);
                this.renderFaceYNeg(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSide(par1Block, 0));
                var4.draw();
                var4.startDrawingQuads();
                var4.setNormal(0.0f, 1.0f, 0.0f);
                this.renderFaceYPos(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSide(par1Block, 1));
                var4.draw();
                var4.startDrawingQuads();
                var4.setNormal(0.0f, 0.0f, -1.0f);
                var4.addTranslation(0.0f, 0.0f, var7);
                this.renderFaceZNeg(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSide(par1Block, 2));
                var4.addTranslation(0.0f, 0.0f, -var7);
                var4.draw();
                var4.startDrawingQuads();
                var4.setNormal(0.0f, 0.0f, 1.0f);
                var4.addTranslation(0.0f, 0.0f, -var7);
                this.renderFaceZPos(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSide(par1Block, 3));
                var4.addTranslation(0.0f, 0.0f, var7);
                var4.draw();
                var4.startDrawingQuads();
                var4.setNormal(-1.0f, 0.0f, 0.0f);
                var4.addTranslation(var7, 0.0f, 0.0f);
                this.renderFaceXNeg(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSide(par1Block, 4));
                var4.addTranslation(-var7, 0.0f, 0.0f);
                var4.draw();
                var4.startDrawingQuads();
                var4.setNormal(1.0f, 0.0f, 0.0f);
                var4.addTranslation(-var7, 0.0f, 0.0f);
                this.renderFaceXPos(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSide(par1Block, 5));
                var4.addTranslation(var7, 0.0f, 0.0f);
                var4.draw();
                GL11.glTranslatef(0.5f, 0.5f, 0.5f);
            }
            else if (var6 == 22) {
                GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
                GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
                ChestItemRenderHelper.instance.renderChest(par1Block, par2, par3);
                GL11.glEnable(32826);
            }
            else if (var6 == 6) {
                var4.startDrawingQuads();
                var4.setNormal(0.0f, -1.0f, 0.0f);
                this.renderBlockCropsImpl(par1Block, par2, -0.5, -0.5, -0.5);
                var4.draw();
            }
            else if (var6 == 2) {
                var4.startDrawingQuads();
                var4.setNormal(0.0f, -1.0f, 0.0f);
                this.renderTorchAtAngle(par1Block, -0.5, -0.5, -0.5, 0.0, 0.0, 0);
                var4.draw();
            }
            else if (var6 == 10) {
                for (int var10 = 0; var10 < 2; ++var10) {
                    if (var10 == 0) {
                        this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 0.5);
                    }
                    if (var10 == 1) {
                        this.setRenderBounds(0.0, 0.0, 0.5, 1.0, 0.5, 1.0);
                    }
                    GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, -1.0f, 0.0f);
                    this.renderFaceYNeg(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSide(par1Block, 0));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 1.0f, 0.0f);
                    this.renderFaceYPos(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSide(par1Block, 1));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 0.0f, -1.0f);
                    this.renderFaceZNeg(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSide(par1Block, 2));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 0.0f, 1.0f);
                    this.renderFaceZPos(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSide(par1Block, 3));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(-1.0f, 0.0f, 0.0f);
                    this.renderFaceXNeg(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSide(par1Block, 4));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(1.0f, 0.0f, 0.0f);
                    this.renderFaceXPos(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSide(par1Block, 5));
                    var4.draw();
                    GL11.glTranslatef(0.5f, 0.5f, 0.5f);
                }
            }
            else if (var6 == 27) {
                int var10 = 0;
                GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
                var4.startDrawingQuads();
                for (int var11 = 0; var11 < 8; ++var11) {
                    byte var12 = 0;
                    byte var13 = 1;
                    if (var11 == 0) {
                        var12 = 2;
                    }
                    if (var11 == 1) {
                        var12 = 3;
                    }
                    if (var11 == 2) {
                        var12 = 4;
                    }
                    if (var11 == 3) {
                        var12 = 5;
                        var13 = 2;
                    }
                    if (var11 == 4) {
                        var12 = 6;
                        var13 = 3;
                    }
                    if (var11 == 5) {
                        var12 = 7;
                        var13 = 5;
                    }
                    if (var11 == 6) {
                        var12 = 6;
                        var13 = 2;
                    }
                    if (var11 == 7) {
                        var12 = 3;
                    }
                    final float var14 = var12 / 16.0f;
                    final float var15 = 1.0f - var10 / 16.0f;
                    final float var16 = 1.0f - (var10 + var13) / 16.0f;
                    var10 += var13;
                    this.setRenderBounds(0.5f - var14, var16, 0.5f - var14, 0.5f + var14, var15, 0.5f + var14);
                    var4.setNormal(0.0f, -1.0f, 0.0f);
                    this.renderFaceYNeg(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSide(par1Block, 0));
                    var4.setNormal(0.0f, 1.0f, 0.0f);
                    this.renderFaceYPos(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSide(par1Block, 1));
                    var4.setNormal(0.0f, 0.0f, -1.0f);
                    this.renderFaceZNeg(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSide(par1Block, 2));
                    var4.setNormal(0.0f, 0.0f, 1.0f);
                    this.renderFaceZPos(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSide(par1Block, 3));
                    var4.setNormal(-1.0f, 0.0f, 0.0f);
                    this.renderFaceXNeg(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSide(par1Block, 4));
                    var4.setNormal(1.0f, 0.0f, 0.0f);
                    this.renderFaceXPos(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSide(par1Block, 5));
                }
                var4.draw();
                GL11.glTranslatef(0.5f, 0.5f, 0.5f);
                this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
            }
            else if (var6 == 11) {
                for (int var10 = 0; var10 < 4; ++var10) {
                    float var8 = 0.125f;
                    if (var10 == 0) {
                        this.setRenderBounds(0.5f - var8, 0.0, 0.0, 0.5f + var8, 1.0, var8 * 2.0f);
                    }
                    if (var10 == 1) {
                        this.setRenderBounds(0.5f - var8, 0.0, 1.0f - var8 * 2.0f, 0.5f + var8, 1.0, 1.0);
                    }
                    var8 = 0.0625f;
                    if (var10 == 2) {
                        this.setRenderBounds(0.5f - var8, 1.0f - var8 * 3.0f, -var8 * 2.0f, 0.5f + var8, 1.0f - var8, 1.0f + var8 * 2.0f);
                    }
                    if (var10 == 3) {
                        this.setRenderBounds(0.5f - var8, 0.5f - var8 * 3.0f, -var8 * 2.0f, 0.5f + var8, 0.5f - var8, 1.0f + var8 * 2.0f);
                    }
                    GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, -1.0f, 0.0f);
                    this.renderFaceYNeg(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSide(par1Block, 0));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 1.0f, 0.0f);
                    this.renderFaceYPos(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSide(par1Block, 1));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 0.0f, -1.0f);
                    this.renderFaceZNeg(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSide(par1Block, 2));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 0.0f, 1.0f);
                    this.renderFaceZPos(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSide(par1Block, 3));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(-1.0f, 0.0f, 0.0f);
                    this.renderFaceXNeg(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSide(par1Block, 4));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(1.0f, 0.0f, 0.0f);
                    this.renderFaceXPos(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSide(par1Block, 5));
                    var4.draw();
                    GL11.glTranslatef(0.5f, 0.5f, 0.5f);
                }
                this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
            }
            else if (var6 == 21) {
                for (int var10 = 0; var10 < 3; ++var10) {
                    float var8 = 0.0625f;
                    if (var10 == 0) {
                        this.setRenderBounds(0.5f - var8, 0.30000001192092896, 0.0, 0.5f + var8, 1.0, var8 * 2.0f);
                    }
                    if (var10 == 1) {
                        this.setRenderBounds(0.5f - var8, 0.30000001192092896, 1.0f - var8 * 2.0f, 0.5f + var8, 1.0, 1.0);
                    }
                    var8 = 0.0625f;
                    if (var10 == 2) {
                        this.setRenderBounds(0.5f - var8, 0.5, 0.0, 0.5f + var8, 1.0f - var8, 1.0);
                    }
                    GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, -1.0f, 0.0f);
                    this.renderFaceYNeg(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSide(par1Block, 0));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 1.0f, 0.0f);
                    this.renderFaceYPos(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSide(par1Block, 1));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 0.0f, -1.0f);
                    this.renderFaceZNeg(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSide(par1Block, 2));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 0.0f, 1.0f);
                    this.renderFaceZPos(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSide(par1Block, 3));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(-1.0f, 0.0f, 0.0f);
                    this.renderFaceXNeg(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSide(par1Block, 4));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(1.0f, 0.0f, 0.0f);
                    this.renderFaceXPos(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSide(par1Block, 5));
                    var4.draw();
                    GL11.glTranslatef(0.5f, 0.5f, 0.5f);
                }
            }
            else if (var6 == 32) {
                for (int var10 = 0; var10 < 2; ++var10) {
                    if (var10 == 0) {
                        this.setRenderBounds(0.0, 0.0, 0.3125, 1.0, 0.8125, 0.6875);
                    }
                    if (var10 == 1) {
                        this.setRenderBounds(0.25, 0.0, 0.25, 0.75, 1.0, 0.75);
                    }
                    GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, -1.0f, 0.0f);
                    this.renderFaceYNeg(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(par1Block, 0, par2));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 1.0f, 0.0f);
                    this.renderFaceYPos(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(par1Block, 1, par2));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 0.0f, -1.0f);
                    this.renderFaceZNeg(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(par1Block, 2, par2));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 0.0f, 1.0f);
                    this.renderFaceZPos(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(par1Block, 3, par2));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(-1.0f, 0.0f, 0.0f);
                    this.renderFaceXNeg(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(par1Block, 4, par2));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(1.0f, 0.0f, 0.0f);
                    this.renderFaceXPos(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(par1Block, 5, par2));
                    var4.draw();
                    GL11.glTranslatef(0.5f, 0.5f, 0.5f);
                }
                this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
            }
            else if (var6 == 35) {
                GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
                this.renderBlockAnvilOrient((BlockAnvil)par1Block, 0, 0, 0, par2, true);
                GL11.glTranslatef(0.5f, 0.5f, 0.5f);
            }
            else if (var6 == 34) {
                for (int var10 = 0; var10 < 3; ++var10) {
                    if (var10 == 0) {
                        this.setRenderBounds(0.125, 0.0, 0.125, 0.875, 0.1875, 0.875);
                        this.setOverrideBlockTexture(this.getBlockIcon(Block.obsidian));
                    }
                    else if (var10 == 1) {
                        this.setRenderBounds(0.1875, 0.1875, 0.1875, 0.8125, 0.875, 0.8125);
                        this.setOverrideBlockTexture(Block.beacon.getBeaconIcon());
                    }
                    else if (var10 == 2) {
                        this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
                        this.setOverrideBlockTexture(this.getBlockIcon(Block.glass));
                    }
                    GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, -1.0f, 0.0f);
                    this.renderFaceYNeg(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(par1Block, 0, par2));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 1.0f, 0.0f);
                    this.renderFaceYPos(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(par1Block, 1, par2));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 0.0f, -1.0f);
                    this.renderFaceZNeg(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(par1Block, 2, par2));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 0.0f, 1.0f);
                    this.renderFaceZPos(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(par1Block, 3, par2));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(-1.0f, 0.0f, 0.0f);
                    this.renderFaceXNeg(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(par1Block, 4, par2));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(1.0f, 0.0f, 0.0f);
                    this.renderFaceXPos(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(par1Block, 5, par2));
                    var4.draw();
                    GL11.glTranslatef(0.5f, 0.5f, 0.5f);
                }
                this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
                this.clearOverrideBlockTexture();
            }
            else if (var6 == 38) {
                GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
                this.renderBlockHopperMetadata((BlockHopper)par1Block, 0, 0, 0, 0, true);
                GL11.glTranslatef(0.5f, 0.5f, 0.5f);
            }
            else if (Reflector.ModLoader.exists()) {
                Reflector.callVoid(Reflector.ModLoader_renderInvBlock, this, par1Block, par2, var6);
            }
            else if (Reflector.FMLRenderAccessLibrary.exists()) {
                Reflector.callVoid(Reflector.FMLRenderAccessLibrary_renderInventoryBlock, this, par1Block, par2, var6);
            }
        }
        else {
            if (var6 == 16) {
                par2 = 1;
            }
            par1Block.setBlockBoundsForItemRender();
            this.setRenderBoundsFromBlock(par1Block);
            GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
            GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
            var4.startDrawingQuads();
            var4.setNormal(0.0f, -1.0f, 0.0f);
            this.renderFaceYNeg(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(par1Block, 0, par2));
            var4.draw();
            if (var5 && this.useInventoryTint) {
                final int var10 = par1Block.getRenderColor(par2);
                final float var8 = (var10 >> 16 & 0xFF) / 255.0f;
                final float var9 = (var10 >> 8 & 0xFF) / 255.0f;
                final float var17 = (var10 & 0xFF) / 255.0f;
                GL11.glColor4f(var8 * par3, var9 * par3, var17 * par3, 1.0f);
            }
            var4.startDrawingQuads();
            var4.setNormal(0.0f, 1.0f, 0.0f);
            this.renderFaceYPos(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(par1Block, 1, par2));
            var4.draw();
            if (var5 && this.useInventoryTint) {
                GL11.glColor4f(par3, par3, par3, 1.0f);
            }
            var4.startDrawingQuads();
            var4.setNormal(0.0f, 0.0f, -1.0f);
            this.renderFaceZNeg(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(par1Block, 2, par2));
            var4.draw();
            var4.startDrawingQuads();
            var4.setNormal(0.0f, 0.0f, 1.0f);
            this.renderFaceZPos(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(par1Block, 3, par2));
            var4.draw();
            var4.startDrawingQuads();
            var4.setNormal(-1.0f, 0.0f, 0.0f);
            this.renderFaceXNeg(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(par1Block, 4, par2));
            var4.draw();
            var4.startDrawingQuads();
            var4.setNormal(1.0f, 0.0f, 0.0f);
            this.renderFaceXPos(par1Block, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(par1Block, 5, par2));
            var4.draw();
            GL11.glTranslatef(0.5f, 0.5f, 0.5f);
        }
    }
    
    public static boolean renderItemIn3d(final int par0) {
        switch (par0) {
            case 0:
            case 10:
            case 11:
            case 13:
            case 16:
            case 21:
            case 22:
            case 26:
            case 27:
            case 31:
            case 32:
            case 34:
            case 35:
            case 39: {
                return true;
            }
            default: {
                return Reflector.ModLoader.exists() ? Reflector.callBoolean(Reflector.ModLoader_renderBlockIsItemFull3D, par0) : (Reflector.FMLRenderAccessLibrary.exists() && Reflector.callBoolean(Reflector.FMLRenderAccessLibrary_renderItemAsFull3DBlock, par0));
            }
        }
    }
    
    public Icon getBlockIcon(final Block par1Block, final IBlockAccess par2IBlockAccess, final int par3, final int par4, final int par5, final int par6) {
        return this.getIconSafe(par1Block.getBlockTexture(par2IBlockAccess, par3, par4, par5, par6));
    }
    
    public Icon getBlockIconFromSideAndMetadata(final Block par1Block, final int par2, final int par3) {
        return this.getIconSafe(par1Block.getIcon(par2, par3));
    }
    
    public Icon getBlockIconFromSide(final Block par1Block, final int par2) {
        return this.getIconSafe(par1Block.getBlockTextureFromSide(par2));
    }
    
    public Icon getBlockIcon(final Block par1Block) {
        return this.getIconSafe(par1Block.getBlockTextureFromSide(1));
    }
    
    public Icon getIconSafe(final Icon par1Icon) {
        return (par1Icon == null) ? this.minecraftRB.renderEngine.getMissingIcon(0) : par1Icon;
    }
    
    private float getAmbientOcclusionLightValue(final IBlockAccess var1, final int var2, final int var3, final int var4) {
        final Block var5 = Block.blocksList[var1.getBlockId(var2, var3, var4)];
        if (var5 == null) {
            return 1.0f;
        }
        final boolean var6 = var5.blockMaterial.blocksMovement() && var5.renderAsNormalBlock();
        return var6 ? this.aoLightValueOpaque : 1.0f;
    }
    
    private Icon fixAoSideGrassTexture(Icon var1, final int var2, final int var3, final int var4, final int var5, final float var6, final float var7, final float var8) {
        if (var1 == TextureUtils.iconGrassSide || var1 == TextureUtils.iconMycelSide) {
            var1 = Config.getSideGrassTexture(this.blockAccess, var2, var3, var4, var5, var1);
            if (var1 == TextureUtils.iconGrassTop) {
                this.colorRedTopLeft *= var6;
                this.colorRedBottomLeft *= var6;
                this.colorRedBottomRight *= var6;
                this.colorRedTopRight *= var6;
                this.colorGreenTopLeft *= var7;
                this.colorGreenBottomLeft *= var7;
                this.colorGreenBottomRight *= var7;
                this.colorGreenTopRight *= var7;
                this.colorBlueTopLeft *= var8;
                this.colorBlueBottomLeft *= var8;
                this.colorBlueBottomRight *= var8;
                this.colorBlueTopRight *= var8;
            }
        }
        if (var1 == TextureUtils.iconSnowSide) {
            var1 = Config.getSideSnowGrassTexture(this.blockAccess, var2, var3, var4, var5);
        }
        return var1;
    }
    
    private boolean hasSnowNeighbours(final int var1, final int var2, final int var3) {
        final int var4 = Block.snow.blockID;
        return (this.blockAccess.getBlockId(var1 - 1, var2, var3) == var4 || this.blockAccess.getBlockId(var1 + 1, var2, var3) == var4 || this.blockAccess.getBlockId(var1, var2, var3 - 1) == var4 || this.blockAccess.getBlockId(var1, var2, var3 + 1) == var4) && this.blockAccess.isBlockOpaqueCube(var1, var2 - 1, var3);
    }
    
    private void renderSnow(final int var1, final int var2, final int var3, final double var4) {
        this.setRenderBoundsFromBlock(Block.snow);
        this.renderMaxY = var4;
        this.renderStandardBlock(Block.snow, var1, var2, var3);
    }
}
