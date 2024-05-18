package net.minecraft.src;

public class ItemInWorldManager
{
    public World theWorld;
    public EntityPlayerMP thisPlayerMP;
    private EnumGameType gameType;
    private boolean isDestroyingBlock;
    private int initialDamage;
    private int partiallyDestroyedBlockX;
    private int partiallyDestroyedBlockY;
    private int partiallyDestroyedBlockZ;
    private int curblockDamage;
    private boolean receivedFinishDiggingPacket;
    private int posX;
    private int posY;
    private int posZ;
    private int field_73093_n;
    private int durabilityRemainingOnBlock;
    
    public ItemInWorldManager(final World par1World) {
        this.gameType = EnumGameType.NOT_SET;
        this.durabilityRemainingOnBlock = -1;
        this.theWorld = par1World;
    }
    
    public void setGameType(final EnumGameType par1EnumGameType) {
        (this.gameType = par1EnumGameType).configurePlayerCapabilities(this.thisPlayerMP.capabilities);
        this.thisPlayerMP.sendPlayerAbilities();
    }
    
    public EnumGameType getGameType() {
        return this.gameType;
    }
    
    public boolean isCreative() {
        return this.gameType.isCreative();
    }
    
    public void initializeGameType(final EnumGameType par1EnumGameType) {
        if (this.gameType == EnumGameType.NOT_SET) {
            this.gameType = par1EnumGameType;
        }
        this.setGameType(this.gameType);
    }
    
    public void updateBlockRemoving() {
        ++this.curblockDamage;
        if (this.receivedFinishDiggingPacket) {
            final int var1 = this.curblockDamage - this.field_73093_n;
            final int var2 = this.theWorld.getBlockId(this.posX, this.posY, this.posZ);
            if (var2 == 0) {
                this.receivedFinishDiggingPacket = false;
            }
            else {
                final Block var3 = Block.blocksList[var2];
                final float var4 = var3.getPlayerRelativeBlockHardness(this.thisPlayerMP, this.thisPlayerMP.worldObj, this.posX, this.posY, this.posZ) * (var1 + 1);
                final int var5 = (int)(var4 * 10.0f);
                if (var5 != this.durabilityRemainingOnBlock) {
                    this.theWorld.destroyBlockInWorldPartially(this.thisPlayerMP.entityId, this.posX, this.posY, this.posZ, var5);
                    this.durabilityRemainingOnBlock = var5;
                }
                if (var4 >= 1.0f) {
                    this.receivedFinishDiggingPacket = false;
                    this.tryHarvestBlock(this.posX, this.posY, this.posZ);
                }
            }
        }
        else if (this.isDestroyingBlock) {
            final int var1 = this.theWorld.getBlockId(this.partiallyDestroyedBlockX, this.partiallyDestroyedBlockY, this.partiallyDestroyedBlockZ);
            final Block var6 = Block.blocksList[var1];
            if (var6 == null) {
                this.theWorld.destroyBlockInWorldPartially(this.thisPlayerMP.entityId, this.partiallyDestroyedBlockX, this.partiallyDestroyedBlockY, this.partiallyDestroyedBlockZ, -1);
                this.durabilityRemainingOnBlock = -1;
                this.isDestroyingBlock = false;
            }
            else {
                final int var7 = this.curblockDamage - this.initialDamage;
                final float var4 = var6.getPlayerRelativeBlockHardness(this.thisPlayerMP, this.thisPlayerMP.worldObj, this.partiallyDestroyedBlockX, this.partiallyDestroyedBlockY, this.partiallyDestroyedBlockZ) * (var7 + 1);
                final int var5 = (int)(var4 * 10.0f);
                if (var5 != this.durabilityRemainingOnBlock) {
                    this.theWorld.destroyBlockInWorldPartially(this.thisPlayerMP.entityId, this.partiallyDestroyedBlockX, this.partiallyDestroyedBlockY, this.partiallyDestroyedBlockZ, var5);
                    this.durabilityRemainingOnBlock = var5;
                }
            }
        }
    }
    
    public void onBlockClicked(final int par1, final int par2, final int par3, final int par4) {
        if (!this.gameType.isAdventure() || this.thisPlayerMP.canCurrentToolHarvestBlock(par1, par2, par3)) {
            if (this.isCreative()) {
                if (!this.theWorld.extinguishFire(null, par1, par2, par3, par4)) {
                    this.tryHarvestBlock(par1, par2, par3);
                }
            }
            else {
                this.theWorld.extinguishFire(null, par1, par2, par3, par4);
                this.initialDamage = this.curblockDamage;
                float var5 = 1.0f;
                final int var6 = this.theWorld.getBlockId(par1, par2, par3);
                if (var6 > 0) {
                    Block.blocksList[var6].onBlockClicked(this.theWorld, par1, par2, par3, this.thisPlayerMP);
                    var5 = Block.blocksList[var6].getPlayerRelativeBlockHardness(this.thisPlayerMP, this.thisPlayerMP.worldObj, par1, par2, par3);
                }
                if (var6 > 0 && var5 >= 1.0f) {
                    this.tryHarvestBlock(par1, par2, par3);
                }
                else {
                    this.isDestroyingBlock = true;
                    this.partiallyDestroyedBlockX = par1;
                    this.partiallyDestroyedBlockY = par2;
                    this.partiallyDestroyedBlockZ = par3;
                    final int var7 = (int)(var5 * 10.0f);
                    this.theWorld.destroyBlockInWorldPartially(this.thisPlayerMP.entityId, par1, par2, par3, var7);
                    this.durabilityRemainingOnBlock = var7;
                }
            }
        }
    }
    
    public void uncheckedTryHarvestBlock(final int par1, final int par2, final int par3) {
        if (par1 == this.partiallyDestroyedBlockX && par2 == this.partiallyDestroyedBlockY && par3 == this.partiallyDestroyedBlockZ) {
            final int var4 = this.curblockDamage - this.initialDamage;
            final int var5 = this.theWorld.getBlockId(par1, par2, par3);
            if (var5 != 0) {
                final Block var6 = Block.blocksList[var5];
                final float var7 = var6.getPlayerRelativeBlockHardness(this.thisPlayerMP, this.thisPlayerMP.worldObj, par1, par2, par3) * (var4 + 1);
                if (var7 >= 0.7f) {
                    this.isDestroyingBlock = false;
                    this.theWorld.destroyBlockInWorldPartially(this.thisPlayerMP.entityId, par1, par2, par3, -1);
                    this.tryHarvestBlock(par1, par2, par3);
                }
                else if (!this.receivedFinishDiggingPacket) {
                    this.isDestroyingBlock = false;
                    this.receivedFinishDiggingPacket = true;
                    this.posX = par1;
                    this.posY = par2;
                    this.posZ = par3;
                    this.field_73093_n = this.initialDamage;
                }
            }
        }
    }
    
    public void cancelDestroyingBlock(final int par1, final int par2, final int par3) {
        this.isDestroyingBlock = false;
        this.theWorld.destroyBlockInWorldPartially(this.thisPlayerMP.entityId, this.partiallyDestroyedBlockX, this.partiallyDestroyedBlockY, this.partiallyDestroyedBlockZ, -1);
    }
    
    private boolean removeBlock(final int par1, final int par2, final int par3) {
        final Block var4 = Block.blocksList[this.theWorld.getBlockId(par1, par2, par3)];
        final int var5 = this.theWorld.getBlockMetadata(par1, par2, par3);
        if (var4 != null) {
            var4.onBlockHarvested(this.theWorld, par1, par2, par3, var5, this.thisPlayerMP);
        }
        final boolean var6 = this.theWorld.setBlockToAir(par1, par2, par3);
        if (var4 != null && var6) {
            var4.onBlockDestroyedByPlayer(this.theWorld, par1, par2, par3, var5);
        }
        return var6;
    }
    
    public boolean tryHarvestBlock(final int par1, final int par2, final int par3) {
        if (this.gameType.isAdventure() && !this.thisPlayerMP.canCurrentToolHarvestBlock(par1, par2, par3)) {
            return false;
        }
        final int var4 = this.theWorld.getBlockId(par1, par2, par3);
        final int var5 = this.theWorld.getBlockMetadata(par1, par2, par3);
        this.theWorld.playAuxSFXAtEntity(this.thisPlayerMP, 2001, par1, par2, par3, var4 + (this.theWorld.getBlockMetadata(par1, par2, par3) << 12));
        final boolean var6 = this.removeBlock(par1, par2, par3);
        if (this.isCreative()) {
            this.thisPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet53BlockChange(par1, par2, par3, this.theWorld));
        }
        else {
            final ItemStack var7 = this.thisPlayerMP.getCurrentEquippedItem();
            final boolean var8 = this.thisPlayerMP.canHarvestBlock(Block.blocksList[var4]);
            if (var7 != null) {
                var7.onBlockDestroyed(this.theWorld, var4, par1, par2, par3, this.thisPlayerMP);
                if (var7.stackSize == 0) {
                    this.thisPlayerMP.destroyCurrentEquippedItem();
                }
            }
            if (var6 && var8) {
                Block.blocksList[var4].harvestBlock(this.theWorld, this.thisPlayerMP, par1, par2, par3, var5);
            }
        }
        return var6;
    }
    
    public boolean tryUseItem(final EntityPlayer par1EntityPlayer, final World par2World, final ItemStack par3ItemStack) {
        final int var4 = par3ItemStack.stackSize;
        final int var5 = par3ItemStack.getItemDamage();
        final ItemStack var6 = par3ItemStack.useItemRightClick(par2World, par1EntityPlayer);
        if (var6 == par3ItemStack && (var6 == null || (var6.stackSize == var4 && var6.getMaxItemUseDuration() <= 0 && var6.getItemDamage() == var5))) {
            return false;
        }
        par1EntityPlayer.inventory.mainInventory[par1EntityPlayer.inventory.currentItem] = var6;
        if (this.isCreative()) {
            var6.stackSize = var4;
            if (var6.isItemStackDamageable()) {
                var6.setItemDamage(var5);
            }
        }
        if (var6.stackSize == 0) {
            par1EntityPlayer.inventory.mainInventory[par1EntityPlayer.inventory.currentItem] = null;
        }
        if (!par1EntityPlayer.isUsingItem()) {
            ((EntityPlayerMP)par1EntityPlayer).sendContainerToPlayer(par1EntityPlayer.inventoryContainer);
        }
        return true;
    }
    
    public boolean activateBlockOrUseItem(final EntityPlayer par1EntityPlayer, final World par2World, final ItemStack par3ItemStack, final int par4, final int par5, final int par6, final int par7, final float par8, final float par9, final float par10) {
        if (!par1EntityPlayer.isSneaking() || par1EntityPlayer.getHeldItem() == null) {
            final int var11 = par2World.getBlockId(par4, par5, par6);
            if (var11 > 0 && Block.blocksList[var11].onBlockActivated(par2World, par4, par5, par6, par1EntityPlayer, par7, par8, par9, par10)) {
                return true;
            }
        }
        if (par3ItemStack == null) {
            return false;
        }
        if (this.isCreative()) {
            final int var11 = par3ItemStack.getItemDamage();
            final int var12 = par3ItemStack.stackSize;
            final boolean var13 = par3ItemStack.tryPlaceItemIntoWorld(par1EntityPlayer, par2World, par4, par5, par6, par7, par8, par9, par10);
            par3ItemStack.setItemDamage(var11);
            par3ItemStack.stackSize = var12;
            return var13;
        }
        return par3ItemStack.tryPlaceItemIntoWorld(par1EntityPlayer, par2World, par4, par5, par6, par7, par8, par9, par10);
    }
    
    public void setWorld(final WorldServer par1WorldServer) {
        this.theWorld = par1WorldServer;
    }
}
