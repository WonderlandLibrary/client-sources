package net.minecraft.src;

public class ItemEnderEye extends Item
{
    public ItemEnderEye(final int par1) {
        super(par1);
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
    
    @Override
    public boolean onItemUse(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final World par3World, final int par4, final int par5, final int par6, final int par7, final float par8, final float par9, final float par10) {
        final int var11 = par3World.getBlockId(par4, par5, par6);
        final int var12 = par3World.getBlockMetadata(par4, par5, par6);
        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack) || var11 != Block.endPortalFrame.blockID || BlockEndPortalFrame.isEnderEyeInserted(var12)) {
            return false;
        }
        if (par3World.isRemote) {
            return true;
        }
        par3World.setBlockMetadataWithNotify(par4, par5, par6, var12 + 4, 2);
        --par1ItemStack.stackSize;
        for (int var13 = 0; var13 < 16; ++var13) {
            final double var14 = par4 + (5.0f + ItemEnderEye.itemRand.nextFloat() * 6.0f) / 16.0f;
            final double var15 = par5 + 0.8125f;
            final double var16 = par6 + (5.0f + ItemEnderEye.itemRand.nextFloat() * 6.0f) / 16.0f;
            final double var17 = 0.0;
            final double var18 = 0.0;
            final double var19 = 0.0;
            par3World.spawnParticle("smoke", var14, var15, var16, var17, var18, var19);
        }
        int var13 = var12 & 0x3;
        int var20 = 0;
        int var21 = 0;
        boolean var22 = false;
        boolean var23 = true;
        final int var24 = Direction.rotateRight[var13];
        for (int var25 = -2; var25 <= 2; ++var25) {
            final int var26 = par4 + Direction.offsetX[var24] * var25;
            final int var27 = par6 + Direction.offsetZ[var24] * var25;
            final int var28 = par3World.getBlockId(var26, par5, var27);
            if (var28 == Block.endPortalFrame.blockID) {
                final int var29 = par3World.getBlockMetadata(var26, par5, var27);
                if (!BlockEndPortalFrame.isEnderEyeInserted(var29)) {
                    var23 = false;
                    break;
                }
                var21 = var25;
                if (!var22) {
                    var20 = var25;
                    var22 = true;
                }
            }
        }
        if (var23 && var21 == var20 + 2) {
            for (int var25 = var20; var25 <= var21; ++var25) {
                int var26 = par4 + Direction.offsetX[var24] * var25;
                int var27 = par6 + Direction.offsetZ[var24] * var25;
                var26 += Direction.offsetX[var13] * 4;
                var27 += Direction.offsetZ[var13] * 4;
                final int var28 = par3World.getBlockId(var26, par5, var27);
                final int var29 = par3World.getBlockMetadata(var26, par5, var27);
                if (var28 != Block.endPortalFrame.blockID || !BlockEndPortalFrame.isEnderEyeInserted(var29)) {
                    var23 = false;
                    break;
                }
            }
            for (int var25 = var20 - 1; var25 <= var21 + 1; var25 += 4) {
                for (int var26 = 1; var26 <= 3; ++var26) {
                    int var27 = par4 + Direction.offsetX[var24] * var25;
                    int var28 = par6 + Direction.offsetZ[var24] * var25;
                    var27 += Direction.offsetX[var13] * var26;
                    var28 += Direction.offsetZ[var13] * var26;
                    final int var29 = par3World.getBlockId(var27, par5, var28);
                    final int var30 = par3World.getBlockMetadata(var27, par5, var28);
                    if (var29 != Block.endPortalFrame.blockID || !BlockEndPortalFrame.isEnderEyeInserted(var30)) {
                        var23 = false;
                        break;
                    }
                }
            }
            if (var23) {
                for (int var25 = var20; var25 <= var21; ++var25) {
                    for (int var26 = 1; var26 <= 3; ++var26) {
                        int var27 = par4 + Direction.offsetX[var24] * var25;
                        int var28 = par6 + Direction.offsetZ[var24] * var25;
                        var27 += Direction.offsetX[var13] * var26;
                        var28 += Direction.offsetZ[var13] * var26;
                        par3World.setBlock(var27, par5, var28, Block.endPortal.blockID, 0, 2);
                    }
                }
            }
        }
        return true;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        final MovingObjectPosition var4 = this.getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, false);
        if (var4 != null && var4.typeOfHit == EnumMovingObjectType.TILE) {
            final int var5 = par2World.getBlockId(var4.blockX, var4.blockY, var4.blockZ);
            if (var5 == Block.endPortalFrame.blockID) {
                return par1ItemStack;
            }
        }
        if (!par2World.isRemote) {
            final ChunkPosition var6 = par2World.findClosestStructure("Stronghold", (int)par3EntityPlayer.posX, (int)par3EntityPlayer.posY, (int)par3EntityPlayer.posZ);
            if (var6 != null) {
                final EntityEnderEye var7 = new EntityEnderEye(par2World, par3EntityPlayer.posX, par3EntityPlayer.posY + 1.62 - par3EntityPlayer.yOffset, par3EntityPlayer.posZ);
                var7.moveTowards(var6.x, var6.y, var6.z);
                par2World.spawnEntityInWorld(var7);
                par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5f, 0.4f / (ItemEnderEye.itemRand.nextFloat() * 0.4f + 0.8f));
                par2World.playAuxSFXAtEntity(null, 1002, (int)par3EntityPlayer.posX, (int)par3EntityPlayer.posY, (int)par3EntityPlayer.posZ, 0);
                if (!par3EntityPlayer.capabilities.isCreativeMode) {
                    --par1ItemStack.stackSize;
                }
            }
        }
        return par1ItemStack;
    }
}
