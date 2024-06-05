package net.minecraft.src;

import java.util.*;

public class TileEntityBeacon extends TileEntity implements IInventory
{
    public static final Potion[][] effectsList;
    private long field_82137_b;
    private float field_82138_c;
    private boolean isBeaconActive;
    private int levels;
    private int primaryEffect;
    private int secondaryEffect;
    private ItemStack payment;
    private String field_94048_i;
    
    static {
        effectsList = new Potion[][] { { Potion.moveSpeed, Potion.digSpeed }, { Potion.resistance, Potion.jump }, { Potion.damageBoost }, { Potion.regeneration } };
    }
    
    public TileEntityBeacon() {
        this.levels = -1;
    }
    
    @Override
    public void updateEntity() {
        if (this.worldObj.getTotalWorldTime() % 80L == 0L) {
            this.updateState();
            this.addEffectsToPlayers();
        }
    }
    
    private void addEffectsToPlayers() {
        if (this.isBeaconActive && this.levels > 0 && !this.worldObj.isRemote && this.primaryEffect > 0) {
            final double var1 = this.levels * 10 + 10;
            byte var2 = 0;
            if (this.levels >= 4 && this.primaryEffect == this.secondaryEffect) {
                var2 = 1;
            }
            final AxisAlignedBB var3 = AxisAlignedBB.getAABBPool().getAABB(this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1, this.yCoord + 1, this.zCoord + 1).expand(var1, var1, var1);
            var3.maxY = this.worldObj.getHeight();
            final List var4 = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, var3);
            for (final EntityPlayer var6 : var4) {
                var6.addPotionEffect(new PotionEffect(this.primaryEffect, 180, var2, true));
            }
            if (this.levels >= 4 && this.primaryEffect != this.secondaryEffect && this.secondaryEffect > 0) {
                for (final EntityPlayer var6 : var4) {
                    var6.addPotionEffect(new PotionEffect(this.secondaryEffect, 180, 0, true));
                }
            }
        }
    }
    
    private void updateState() {
        if (!this.worldObj.canBlockSeeTheSky(this.xCoord, this.yCoord + 1, this.zCoord)) {
            this.isBeaconActive = false;
            this.levels = 0;
        }
        else {
            this.isBeaconActive = true;
            this.levels = 0;
            for (int var1 = 1; var1 <= 4; this.levels = var1++) {
                final int var2 = this.yCoord - var1;
                if (var2 < 0) {
                    break;
                }
                boolean var3 = true;
                for (int var4 = this.xCoord - var1; var4 <= this.xCoord + var1 && var3; ++var4) {
                    for (int var5 = this.zCoord - var1; var5 <= this.zCoord + var1; ++var5) {
                        final int var6 = this.worldObj.getBlockId(var4, var2, var5);
                        if (var6 != Block.blockEmerald.blockID && var6 != Block.blockGold.blockID && var6 != Block.blockDiamond.blockID && var6 != Block.blockIron.blockID) {
                            var3 = false;
                            break;
                        }
                    }
                }
                if (!var3) {
                    break;
                }
            }
            if (this.levels == 0) {
                this.isBeaconActive = false;
            }
        }
    }
    
    public float func_82125_v_() {
        if (!this.isBeaconActive) {
            return 0.0f;
        }
        final int var1 = (int)(this.worldObj.getTotalWorldTime() - this.field_82137_b);
        this.field_82137_b = this.worldObj.getTotalWorldTime();
        if (var1 > 1) {
            this.field_82138_c -= var1 / 40.0f;
            if (this.field_82138_c < 0.0f) {
                this.field_82138_c = 0.0f;
            }
        }
        this.field_82138_c += 0.025f;
        if (this.field_82138_c > 1.0f) {
            this.field_82138_c = 1.0f;
        }
        return this.field_82138_c;
    }
    
    public int getPrimaryEffect() {
        return this.primaryEffect;
    }
    
    public int getSecondaryEffect() {
        return this.secondaryEffect;
    }
    
    public int getLevels() {
        return this.levels;
    }
    
    public void setLevels(final int par1) {
        this.levels = par1;
    }
    
    public void setPrimaryEffect(final int par1) {
        this.primaryEffect = 0;
        for (int var2 = 0; var2 < this.levels && var2 < 3; ++var2) {
            for (final Potion var6 : TileEntityBeacon.effectsList[var2]) {
                if (var6.id == par1) {
                    this.primaryEffect = par1;
                    return;
                }
            }
        }
    }
    
    public void setSecondaryEffect(final int par1) {
        this.secondaryEffect = 0;
        if (this.levels >= 4) {
            for (int var2 = 0; var2 < 4; ++var2) {
                for (final Potion var6 : TileEntityBeacon.effectsList[var2]) {
                    if (var6.id == par1) {
                        this.secondaryEffect = par1;
                        return;
                    }
                }
            }
        }
    }
    
    @Override
    public Packet getDescriptionPacket() {
        final NBTTagCompound var1 = new NBTTagCompound();
        this.writeToNBT(var1);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 3, var1);
    }
    
    @Override
    public double getMaxRenderDistanceSquared() {
        return 65536.0;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readFromNBT(par1NBTTagCompound);
        this.primaryEffect = par1NBTTagCompound.getInteger("Primary");
        this.secondaryEffect = par1NBTTagCompound.getInteger("Secondary");
        this.levels = par1NBTTagCompound.getInteger("Levels");
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("Primary", this.primaryEffect);
        par1NBTTagCompound.setInteger("Secondary", this.secondaryEffect);
        par1NBTTagCompound.setInteger("Levels", this.levels);
    }
    
    @Override
    public int getSizeInventory() {
        return 1;
    }
    
    @Override
    public ItemStack getStackInSlot(final int par1) {
        return (par1 == 0) ? this.payment : null;
    }
    
    @Override
    public ItemStack decrStackSize(final int par1, final int par2) {
        if (par1 != 0 || this.payment == null) {
            return null;
        }
        if (par2 >= this.payment.stackSize) {
            final ItemStack var3 = this.payment;
            this.payment = null;
            return var3;
        }
        final ItemStack payment = this.payment;
        payment.stackSize -= par2;
        return new ItemStack(this.payment.itemID, par2, this.payment.getItemDamage());
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(final int par1) {
        if (par1 == 0 && this.payment != null) {
            final ItemStack var2 = this.payment;
            this.payment = null;
            return var2;
        }
        return null;
    }
    
    @Override
    public void setInventorySlotContents(final int par1, final ItemStack par2ItemStack) {
        if (par1 == 0) {
            this.payment = par2ItemStack;
        }
    }
    
    @Override
    public String getInvName() {
        return this.isInvNameLocalized() ? this.field_94048_i : "container.beacon";
    }
    
    @Override
    public boolean isInvNameLocalized() {
        return this.field_94048_i != null && this.field_94048_i.length() > 0;
    }
    
    public void func_94047_a(final String par1Str) {
        this.field_94048_i = par1Str;
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 1;
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer par1EntityPlayer) {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && par1EntityPlayer.getDistanceSq(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5) <= 64.0;
    }
    
    @Override
    public void openChest() {
    }
    
    @Override
    public void closeChest() {
    }
    
    @Override
    public boolean isStackValidForSlot(final int par1, final ItemStack par2ItemStack) {
        return par2ItemStack.itemID == Item.emerald.itemID || par2ItemStack.itemID == Item.diamond.itemID || par2ItemStack.itemID == Item.ingotGold.itemID || par2ItemStack.itemID == Item.ingotIron.itemID;
    }
}
