package net.minecraft.src;

class SlotRepair extends Slot
{
    final World theWorld;
    final int blockPosX;
    final int blockPosY;
    final int blockPosZ;
    final ContainerRepair anvil;
    
    SlotRepair(final ContainerRepair par1ContainerRepair, final IInventory par2IInventory, final int par3, final int par4, final int par5, final World par6World, final int par7, final int par8, final int par9) {
        super(par2IInventory, par3, par4, par5);
        this.anvil = par1ContainerRepair;
        this.theWorld = par6World;
        this.blockPosX = par7;
        this.blockPosY = par8;
        this.blockPosZ = par9;
    }
    
    @Override
    public boolean isItemValid(final ItemStack par1ItemStack) {
        return false;
    }
    
    @Override
    public boolean canTakeStack(final EntityPlayer par1EntityPlayer) {
        return (par1EntityPlayer.capabilities.isCreativeMode || par1EntityPlayer.experienceLevel >= this.anvil.maximumCost) && this.anvil.maximumCost > 0 && this.getHasStack();
    }
    
    @Override
    public void onPickupFromSlot(final EntityPlayer par1EntityPlayer, final ItemStack par2ItemStack) {
        if (!par1EntityPlayer.capabilities.isCreativeMode) {
            par1EntityPlayer.addExperienceLevel(-this.anvil.maximumCost);
        }
        ContainerRepair.getRepairInputInventory(this.anvil).setInventorySlotContents(0, null);
        if (ContainerRepair.getStackSizeUsedInRepair(this.anvil) > 0) {
            final ItemStack var3 = ContainerRepair.getRepairInputInventory(this.anvil).getStackInSlot(1);
            if (var3 != null && var3.stackSize > ContainerRepair.getStackSizeUsedInRepair(this.anvil)) {
                final ItemStack itemStack = var3;
                itemStack.stackSize -= ContainerRepair.getStackSizeUsedInRepair(this.anvil);
                ContainerRepair.getRepairInputInventory(this.anvil).setInventorySlotContents(1, var3);
            }
            else {
                ContainerRepair.getRepairInputInventory(this.anvil).setInventorySlotContents(1, null);
            }
        }
        else {
            ContainerRepair.getRepairInputInventory(this.anvil).setInventorySlotContents(1, null);
        }
        this.anvil.maximumCost = 0;
        if (!par1EntityPlayer.capabilities.isCreativeMode && !this.theWorld.isRemote && this.theWorld.getBlockId(this.blockPosX, this.blockPosY, this.blockPosZ) == Block.anvil.blockID && par1EntityPlayer.getRNG().nextFloat() < 0.12f) {
            final int var4 = this.theWorld.getBlockMetadata(this.blockPosX, this.blockPosY, this.blockPosZ);
            final int var5 = var4 & 0x3;
            int var6 = var4 >> 2;
            if (++var6 > 2) {
                this.theWorld.setBlockToAir(this.blockPosX, this.blockPosY, this.blockPosZ);
                this.theWorld.playAuxSFX(1020, this.blockPosX, this.blockPosY, this.blockPosZ, 0);
            }
            else {
                this.theWorld.setBlockMetadataWithNotify(this.blockPosX, this.blockPosY, this.blockPosZ, var5 | var6 << 2, 2);
                this.theWorld.playAuxSFX(1021, this.blockPosX, this.blockPosY, this.blockPosZ, 0);
            }
        }
        else if (!this.theWorld.isRemote) {
            this.theWorld.playAuxSFX(1021, this.blockPosX, this.blockPosY, this.blockPosZ, 0);
        }
    }
}
