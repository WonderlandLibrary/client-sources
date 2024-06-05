package net.minecraft.src;

public class ItemBucket extends Item
{
    private int isFull;
    
    public ItemBucket(final int par1, final int par2) {
        super(par1);
        this.maxStackSize = 1;
        this.isFull = par2;
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        final float var4 = 1.0f;
        final double var5 = par3EntityPlayer.prevPosX + (par3EntityPlayer.posX - par3EntityPlayer.prevPosX) * var4;
        final double var6 = par3EntityPlayer.prevPosY + (par3EntityPlayer.posY - par3EntityPlayer.prevPosY) * var4 + 1.62 - par3EntityPlayer.yOffset;
        final double var7 = par3EntityPlayer.prevPosZ + (par3EntityPlayer.posZ - par3EntityPlayer.prevPosZ) * var4;
        final boolean var8 = this.isFull == 0;
        final MovingObjectPosition var9 = this.getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, var8);
        if (var9 == null) {
            return par1ItemStack;
        }
        if (var9.typeOfHit == EnumMovingObjectType.TILE) {
            int var10 = var9.blockX;
            int var11 = var9.blockY;
            int var12 = var9.blockZ;
            if (!par2World.canMineBlock(par3EntityPlayer, var10, var11, var12)) {
                return par1ItemStack;
            }
            if (this.isFull == 0) {
                if (!par3EntityPlayer.canPlayerEdit(var10, var11, var12, var9.sideHit, par1ItemStack)) {
                    return par1ItemStack;
                }
                if (par2World.getBlockMaterial(var10, var11, var12) == Material.water && par2World.getBlockMetadata(var10, var11, var12) == 0) {
                    par2World.setBlockToAir(var10, var11, var12);
                    if (par3EntityPlayer.capabilities.isCreativeMode) {
                        return par1ItemStack;
                    }
                    if (--par1ItemStack.stackSize <= 0) {
                        return new ItemStack(Item.bucketWater);
                    }
                    if (!par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Item.bucketWater))) {
                        par3EntityPlayer.dropPlayerItem(new ItemStack(Item.bucketWater.itemID, 1, 0));
                    }
                    return par1ItemStack;
                }
                else if (par2World.getBlockMaterial(var10, var11, var12) == Material.lava && par2World.getBlockMetadata(var10, var11, var12) == 0) {
                    par2World.setBlockToAir(var10, var11, var12);
                    if (par3EntityPlayer.capabilities.isCreativeMode) {
                        return par1ItemStack;
                    }
                    if (--par1ItemStack.stackSize <= 0) {
                        return new ItemStack(Item.bucketLava);
                    }
                    if (!par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Item.bucketLava))) {
                        par3EntityPlayer.dropPlayerItem(new ItemStack(Item.bucketLava.itemID, 1, 0));
                    }
                    return par1ItemStack;
                }
            }
            else {
                if (this.isFull < 0) {
                    return new ItemStack(Item.bucketEmpty);
                }
                if (var9.sideHit == 0) {
                    --var11;
                }
                if (var9.sideHit == 1) {
                    ++var11;
                }
                if (var9.sideHit == 2) {
                    --var12;
                }
                if (var9.sideHit == 3) {
                    ++var12;
                }
                if (var9.sideHit == 4) {
                    --var10;
                }
                if (var9.sideHit == 5) {
                    ++var10;
                }
                if (!par3EntityPlayer.canPlayerEdit(var10, var11, var12, var9.sideHit, par1ItemStack)) {
                    return par1ItemStack;
                }
                if (this.tryPlaceContainedLiquid(par2World, var5, var6, var7, var10, var11, var12) && !par3EntityPlayer.capabilities.isCreativeMode) {
                    return new ItemStack(Item.bucketEmpty);
                }
            }
        }
        else if (this.isFull == 0 && var9.entityHit instanceof EntityCow) {
            return new ItemStack(Item.bucketMilk);
        }
        return par1ItemStack;
    }
    
    public boolean tryPlaceContainedLiquid(final World par1World, final double par2, final double par4, final double par6, final int par8, final int par9, final int par10) {
        if (this.isFull <= 0) {
            return false;
        }
        if (!par1World.isAirBlock(par8, par9, par10) && par1World.getBlockMaterial(par8, par9, par10).isSolid()) {
            return false;
        }
        if (par1World.provider.isHellWorld && this.isFull == Block.waterMoving.blockID) {
            par1World.playSoundEffect(par2 + 0.5, par4 + 0.5, par6 + 0.5, "random.fizz", 0.5f, 2.6f + (par1World.rand.nextFloat() - par1World.rand.nextFloat()) * 0.8f);
            for (int var11 = 0; var11 < 8; ++var11) {
                par1World.spawnParticle("largesmoke", par8 + Math.random(), par9 + Math.random(), par10 + Math.random(), 0.0, 0.0, 0.0);
            }
        }
        else {
            par1World.setBlock(par8, par9, par10, this.isFull, 0, 3);
        }
        return true;
    }
}
