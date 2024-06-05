package net.minecraft.src;

import java.util.*;

public class TileEntityChest extends TileEntity implements IInventory
{
    private ItemStack[] chestContents;
    public boolean adjacentChestChecked;
    public TileEntityChest adjacentChestZNeg;
    public TileEntityChest adjacentChestXPos;
    public TileEntityChest adjacentChestXNeg;
    public TileEntityChest adjacentChestZPosition;
    public float lidAngle;
    public float prevLidAngle;
    public int numUsingPlayers;
    private int ticksSinceSync;
    private int field_94046_i;
    private String field_94045_s;
    
    public TileEntityChest() {
        this.chestContents = new ItemStack[36];
        this.adjacentChestChecked = false;
        this.field_94046_i = -1;
    }
    
    @Override
    public int getSizeInventory() {
        return 27;
    }
    
    @Override
    public ItemStack getStackInSlot(final int par1) {
        return this.chestContents[par1];
    }
    
    @Override
    public ItemStack decrStackSize(final int par1, final int par2) {
        if (this.chestContents[par1] == null) {
            return null;
        }
        if (this.chestContents[par1].stackSize <= par2) {
            final ItemStack var3 = this.chestContents[par1];
            this.chestContents[par1] = null;
            this.onInventoryChanged();
            return var3;
        }
        final ItemStack var3 = this.chestContents[par1].splitStack(par2);
        if (this.chestContents[par1].stackSize == 0) {
            this.chestContents[par1] = null;
        }
        this.onInventoryChanged();
        return var3;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(final int par1) {
        if (this.chestContents[par1] != null) {
            final ItemStack var2 = this.chestContents[par1];
            this.chestContents[par1] = null;
            return var2;
        }
        return null;
    }
    
    @Override
    public void setInventorySlotContents(final int par1, final ItemStack par2ItemStack) {
        this.chestContents[par1] = par2ItemStack;
        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
        this.onInventoryChanged();
    }
    
    @Override
    public String getInvName() {
        return this.isInvNameLocalized() ? this.field_94045_s : "container.chest";
    }
    
    @Override
    public boolean isInvNameLocalized() {
        return this.field_94045_s != null && this.field_94045_s.length() > 0;
    }
    
    public void func_94043_a(final String par1Str) {
        this.field_94045_s = par1Str;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readFromNBT(par1NBTTagCompound);
        final NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
        this.chestContents = new ItemStack[this.getSizeInventory()];
        if (par1NBTTagCompound.hasKey("CustomName")) {
            this.field_94045_s = par1NBTTagCompound.getString("CustomName");
        }
        for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
            final NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            final int var5 = var4.getByte("Slot") & 0xFF;
            if (var5 >= 0 && var5 < this.chestContents.length) {
                this.chestContents[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);
        final NBTTagList var2 = new NBTTagList();
        for (int var3 = 0; var3 < this.chestContents.length; ++var3) {
            if (this.chestContents[var3] != null) {
                final NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.chestContents[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }
        par1NBTTagCompound.setTag("Items", var2);
        if (this.isInvNameLocalized()) {
            par1NBTTagCompound.setString("CustomName", this.field_94045_s);
        }
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer par1EntityPlayer) {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && par1EntityPlayer.getDistanceSq(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5) <= 64.0;
    }
    
    @Override
    public void updateContainingBlockInfo() {
        super.updateContainingBlockInfo();
        this.adjacentChestChecked = false;
    }
    
    private void func_90009_a(final TileEntityChest par1TileEntityChest, final int par2) {
        if (par1TileEntityChest.isInvalid()) {
            this.adjacentChestChecked = false;
        }
        else if (this.adjacentChestChecked) {
            switch (par2) {
                case 0: {
                    if (this.adjacentChestZPosition != par1TileEntityChest) {
                        this.adjacentChestChecked = false;
                        break;
                    }
                    break;
                }
                case 1: {
                    if (this.adjacentChestXNeg != par1TileEntityChest) {
                        this.adjacentChestChecked = false;
                        break;
                    }
                    break;
                }
                case 2: {
                    if (this.adjacentChestZNeg != par1TileEntityChest) {
                        this.adjacentChestChecked = false;
                        break;
                    }
                    break;
                }
                case 3: {
                    if (this.adjacentChestXPos != par1TileEntityChest) {
                        this.adjacentChestChecked = false;
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    public void checkForAdjacentChests() {
        if (!this.adjacentChestChecked) {
            this.adjacentChestChecked = true;
            this.adjacentChestZNeg = null;
            this.adjacentChestXPos = null;
            this.adjacentChestXNeg = null;
            this.adjacentChestZPosition = null;
            if (this.func_94044_a(this.xCoord - 1, this.yCoord, this.zCoord)) {
                this.adjacentChestXNeg = (TileEntityChest)this.worldObj.getBlockTileEntity(this.xCoord - 1, this.yCoord, this.zCoord);
            }
            if (this.func_94044_a(this.xCoord + 1, this.yCoord, this.zCoord)) {
                this.adjacentChestXPos = (TileEntityChest)this.worldObj.getBlockTileEntity(this.xCoord + 1, this.yCoord, this.zCoord);
            }
            if (this.func_94044_a(this.xCoord, this.yCoord, this.zCoord - 1)) {
                this.adjacentChestZNeg = (TileEntityChest)this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord - 1);
            }
            if (this.func_94044_a(this.xCoord, this.yCoord, this.zCoord + 1)) {
                this.adjacentChestZPosition = (TileEntityChest)this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord + 1);
            }
            if (this.adjacentChestZNeg != null) {
                this.adjacentChestZNeg.func_90009_a(this, 0);
            }
            if (this.adjacentChestZPosition != null) {
                this.adjacentChestZPosition.func_90009_a(this, 2);
            }
            if (this.adjacentChestXPos != null) {
                this.adjacentChestXPos.func_90009_a(this, 1);
            }
            if (this.adjacentChestXNeg != null) {
                this.adjacentChestXNeg.func_90009_a(this, 3);
            }
        }
    }
    
    private boolean func_94044_a(final int par1, final int par2, final int par3) {
        final Block var4 = Block.blocksList[this.worldObj.getBlockId(par1, par2, par3)];
        return var4 != null && var4 instanceof BlockChest && ((BlockChest)var4).isTrapped == this.func_98041_l();
    }
    
    @Override
    public void updateEntity() {
        super.updateEntity();
        this.checkForAdjacentChests();
        ++this.ticksSinceSync;
        if (!this.worldObj.isRemote && this.numUsingPlayers != 0 && (this.ticksSinceSync + this.xCoord + this.yCoord + this.zCoord) % 200 == 0) {
            this.numUsingPlayers = 0;
            final float var1 = 5.0f;
            final List var2 = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getAABBPool().getAABB(this.xCoord - var1, this.yCoord - var1, this.zCoord - var1, this.xCoord + 1 + var1, this.yCoord + 1 + var1, this.zCoord + 1 + var1));
            for (final EntityPlayer var4 : var2) {
                if (var4.openContainer instanceof ContainerChest) {
                    final IInventory var5 = ((ContainerChest)var4.openContainer).getLowerChestInventory();
                    if (var5 != this && (!(var5 instanceof InventoryLargeChest) || !((InventoryLargeChest)var5).isPartOfLargeChest(this))) {
                        continue;
                    }
                    ++this.numUsingPlayers;
                }
            }
        }
        this.prevLidAngle = this.lidAngle;
        final float var1 = 0.1f;
        if (this.numUsingPlayers > 0 && this.lidAngle == 0.0f && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null) {
            double var6 = this.xCoord + 0.5;
            double var7 = this.zCoord + 0.5;
            if (this.adjacentChestZPosition != null) {
                var7 += 0.5;
            }
            if (this.adjacentChestXPos != null) {
                var6 += 0.5;
            }
            this.worldObj.playSoundEffect(var6, this.yCoord + 0.5, var7, "random.chestopen", 0.5f, this.worldObj.rand.nextFloat() * 0.1f + 0.9f);
        }
        if ((this.numUsingPlayers == 0 && this.lidAngle > 0.0f) || (this.numUsingPlayers > 0 && this.lidAngle < 1.0f)) {
            final float var8 = this.lidAngle;
            if (this.numUsingPlayers > 0) {
                this.lidAngle += var1;
            }
            else {
                this.lidAngle -= var1;
            }
            if (this.lidAngle > 1.0f) {
                this.lidAngle = 1.0f;
            }
            final float var9 = 0.5f;
            if (this.lidAngle < var9 && var8 >= var9 && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null) {
                double var7 = this.xCoord + 0.5;
                double var10 = this.zCoord + 0.5;
                if (this.adjacentChestZPosition != null) {
                    var10 += 0.5;
                }
                if (this.adjacentChestXPos != null) {
                    var7 += 0.5;
                }
                this.worldObj.playSoundEffect(var7, this.yCoord + 0.5, var10, "random.chestclosed", 0.5f, this.worldObj.rand.nextFloat() * 0.1f + 0.9f);
            }
            if (this.lidAngle < 0.0f) {
                this.lidAngle = 0.0f;
            }
        }
    }
    
    @Override
    public boolean receiveClientEvent(final int par1, final int par2) {
        if (par1 == 1) {
            this.numUsingPlayers = par2;
            return true;
        }
        return super.receiveClientEvent(par1, par2);
    }
    
    @Override
    public void openChest() {
        if (this.numUsingPlayers < 0) {
            this.numUsingPlayers = 0;
        }
        ++this.numUsingPlayers;
        this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID, 1, this.numUsingPlayers);
        this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID);
        this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType().blockID);
    }
    
    @Override
    public void closeChest() {
        if (this.getBlockType() != null && this.getBlockType() instanceof BlockChest) {
            --this.numUsingPlayers;
            this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID, 1, this.numUsingPlayers);
            this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID);
            this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType().blockID);
        }
    }
    
    @Override
    public boolean isStackValidForSlot(final int par1, final ItemStack par2ItemStack) {
        return true;
    }
    
    @Override
    public void invalidate() {
        super.invalidate();
        this.updateContainingBlockInfo();
        this.checkForAdjacentChests();
    }
    
    public int func_98041_l() {
        if (this.field_94046_i == -1) {
            if (this.worldObj == null || !(this.getBlockType() instanceof BlockChest)) {
                return 0;
            }
            this.field_94046_i = ((BlockChest)this.getBlockType()).isTrapped;
        }
        return this.field_94046_i;
    }
}
