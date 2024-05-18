package net.minecraft.src;

import java.util.*;

public class TileEntityPiston extends TileEntity
{
    private int storedBlockID;
    private int storedMetadata;
    private int storedOrientation;
    private boolean extending;
    private boolean shouldHeadBeRendered;
    private float progress;
    private float lastProgress;
    private List pushedObjects;
    
    public TileEntityPiston() {
        this.pushedObjects = new ArrayList();
    }
    
    public TileEntityPiston(final int par1, final int par2, final int par3, final boolean par4, final boolean par5) {
        this.pushedObjects = new ArrayList();
        this.storedBlockID = par1;
        this.storedMetadata = par2;
        this.storedOrientation = par3;
        this.extending = par4;
        this.shouldHeadBeRendered = par5;
    }
    
    public int getStoredBlockID() {
        return this.storedBlockID;
    }
    
    @Override
    public int getBlockMetadata() {
        return this.storedMetadata;
    }
    
    public boolean isExtending() {
        return this.extending;
    }
    
    public int getPistonOrientation() {
        return this.storedOrientation;
    }
    
    public boolean shouldRenderHead() {
        return this.shouldHeadBeRendered;
    }
    
    public float getProgress(float par1) {
        if (par1 > 1.0f) {
            par1 = 1.0f;
        }
        return this.lastProgress + (this.progress - this.lastProgress) * par1;
    }
    
    public float getOffsetX(final float par1) {
        return this.extending ? ((this.getProgress(par1) - 1.0f) * Facing.offsetsXForSide[this.storedOrientation]) : ((1.0f - this.getProgress(par1)) * Facing.offsetsXForSide[this.storedOrientation]);
    }
    
    public float getOffsetY(final float par1) {
        return this.extending ? ((this.getProgress(par1) - 1.0f) * Facing.offsetsYForSide[this.storedOrientation]) : ((1.0f - this.getProgress(par1)) * Facing.offsetsYForSide[this.storedOrientation]);
    }
    
    public float getOffsetZ(final float par1) {
        return this.extending ? ((this.getProgress(par1) - 1.0f) * Facing.offsetsZForSide[this.storedOrientation]) : ((1.0f - this.getProgress(par1)) * Facing.offsetsZForSide[this.storedOrientation]);
    }
    
    private void updatePushedObjects(float par1, final float par2) {
        if (this.extending) {
            par1 = 1.0f - par1;
        }
        else {
            --par1;
        }
        final AxisAlignedBB var3 = Block.pistonMoving.getAxisAlignedBB(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.storedBlockID, par1, this.storedOrientation);
        if (var3 != null) {
            final List var4 = this.worldObj.getEntitiesWithinAABBExcludingEntity(null, var3);
            if (!var4.isEmpty()) {
                this.pushedObjects.addAll(var4);
                for (final Entity var6 : this.pushedObjects) {
                    var6.moveEntity(par2 * Facing.offsetsXForSide[this.storedOrientation], par2 * Facing.offsetsYForSide[this.storedOrientation], par2 * Facing.offsetsZForSide[this.storedOrientation]);
                }
                this.pushedObjects.clear();
            }
        }
    }
    
    public void clearPistonTileEntity() {
        if (this.lastProgress < 1.0f && this.worldObj != null) {
            final float n = 1.0f;
            this.progress = n;
            this.lastProgress = n;
            this.worldObj.removeBlockTileEntity(this.xCoord, this.yCoord, this.zCoord);
            this.invalidate();
            if (this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord) == Block.pistonMoving.blockID) {
                this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, this.storedBlockID, this.storedMetadata, 3);
                this.worldObj.notifyBlockOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.storedBlockID);
            }
        }
    }
    
    @Override
    public void updateEntity() {
        this.lastProgress = this.progress;
        if (this.lastProgress >= 1.0f) {
            this.updatePushedObjects(1.0f, 0.25f);
            this.worldObj.removeBlockTileEntity(this.xCoord, this.yCoord, this.zCoord);
            this.invalidate();
            if (this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord) == Block.pistonMoving.blockID) {
                this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, this.storedBlockID, this.storedMetadata, 3);
                this.worldObj.notifyBlockOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.storedBlockID);
            }
        }
        else {
            this.progress += 0.5f;
            if (this.progress >= 1.0f) {
                this.progress = 1.0f;
            }
            if (this.extending) {
                this.updatePushedObjects(this.progress, this.progress - this.lastProgress + 0.0625f);
            }
        }
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readFromNBT(par1NBTTagCompound);
        this.storedBlockID = par1NBTTagCompound.getInteger("blockId");
        this.storedMetadata = par1NBTTagCompound.getInteger("blockData");
        this.storedOrientation = par1NBTTagCompound.getInteger("facing");
        final float float1 = par1NBTTagCompound.getFloat("progress");
        this.progress = float1;
        this.lastProgress = float1;
        this.extending = par1NBTTagCompound.getBoolean("extending");
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("blockId", this.storedBlockID);
        par1NBTTagCompound.setInteger("blockData", this.storedMetadata);
        par1NBTTagCompound.setInteger("facing", this.storedOrientation);
        par1NBTTagCompound.setFloat("progress", this.lastProgress);
        par1NBTTagCompound.setBoolean("extending", this.extending);
    }
}
