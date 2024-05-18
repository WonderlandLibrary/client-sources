package net.minecraft.entity.item;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class EntityItemFrame extends EntityHanging
{
    private float itemDropChance;
    private static final String[] I;
    
    private static void I() {
        (I = new String[0xB4 ^ 0xBE])["".length()] = I("5\u00043\u001f\u001c8\u001f\u000f5\u001a>\u001b\u0005", "Qkvqh");
        EntityItemFrame.I[" ".length()] = I("'?\u0012=\u0014l", "AMsPq");
        EntityItemFrame.I["  ".length()] = I("0\u0000\u0016;", "ytsVl");
        EntityItemFrame.I["   ".length()] = I("*:))$\f:-0\u001f\f ", "cNLDv");
        EntityItemFrame.I[0x22 ^ 0x26] = I("\b\u001b.\"(3\u0000;\f\u0004 \u0001(*", "AoKOl");
        EntityItemFrame.I[0x9E ^ 0x9B] = I(",\u0004\u0015;", "eppVe");
        EntityItemFrame.I[0x34 ^ 0x32] = I("?\u001e-!#\u0019\u001e)8\u0018\u0019\u0004", "vjHLq");
        EntityItemFrame.I[0xE ^ 0x9] = I("\u001d--,\u0014&68\u0002857+$", "TYHAP");
        EntityItemFrame.I[0x43 ^ 0x4B] = I("\u0011\u001c<\u0005!*\u0007)+\r9\u0006:\r", "XhYhe");
        EntityItemFrame.I[0xB3 ^ 0xBA] = I("\u0007<5 \u000b7<(+", "CUGEh");
    }
    
    public ItemStack getDisplayedItem() {
        return this.getDataWatcher().getWatchableObjectItemStack(0x6E ^ 0x66);
    }
    
    @Override
    protected void entityInit() {
        this.getDataWatcher().addObjectByDataType(0x10 ^ 0x18, 0x7B ^ 0x7E);
        this.getDataWatcher().addObject(0x45 ^ 0x4C, (byte)"".length());
    }
    
    @Override
    public int getHeightPixels() {
        return 0x74 ^ 0x78;
    }
    
    public int getRotation() {
        return this.getDataWatcher().getWatchableObjectByte(0x17 ^ 0x1E);
    }
    
    @Override
    public float getCollisionBorderSize() {
        return 0.0f;
    }
    
    public EntityItemFrame(final World world) {
        super(world);
        this.itemDropChance = 1.0f;
    }
    
    private void setDisplayedItemWithUpdate(ItemStack copy, final boolean b) {
        if (copy != null) {
            copy = copy.copy();
            copy.stackSize = " ".length();
            copy.setItemFrame(this);
        }
        this.getDataWatcher().updateObject(0x30 ^ 0x38, copy);
        this.getDataWatcher().setObjectWatched(0x94 ^ 0x9C);
        if (b && this.hangingPosition != null) {
            this.worldObj.updateComparatorOutputLevel(this.hangingPosition, Blocks.air);
        }
    }
    
    public void dropItemOrSelf(final Entity entity, final boolean b) {
        if (this.worldObj.getGameRules().getBoolean(EntityItemFrame.I["".length()])) {
            final ItemStack displayedItem = this.getDisplayedItem();
            if (entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isCreativeMode) {
                this.removeFrameFromMap(displayedItem);
                return;
            }
            if (b) {
                this.entityDropItem(new ItemStack(Items.item_frame), 0.0f);
            }
            if (displayedItem != null && this.rand.nextFloat() < this.itemDropChance) {
                final ItemStack copy = displayedItem.copy();
                this.removeFrameFromMap(copy);
                this.entityDropItem(copy, 0.0f);
            }
        }
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        final NBTTagCompound compoundTag = nbtTagCompound.getCompoundTag(EntityItemFrame.I[0xC1 ^ 0xC4]);
        if (compoundTag != null && !compoundTag.hasNoTags()) {
            this.setDisplayedItemWithUpdate(ItemStack.loadItemStackFromNBT(compoundTag), "".length() != 0);
            this.func_174865_a(nbtTagCompound.getByte(EntityItemFrame.I[0x9E ^ 0x98]), "".length() != 0);
            if (nbtTagCompound.hasKey(EntityItemFrame.I[0x7E ^ 0x79], 0x1C ^ 0x7F)) {
                this.itemDropChance = nbtTagCompound.getFloat(EntityItemFrame.I[0x2 ^ 0xA]);
            }
            if (nbtTagCompound.hasKey(EntityItemFrame.I[0x9D ^ 0x94])) {
                this.func_174865_a(this.getRotation() * "  ".length(), "".length() != 0);
            }
        }
        super.readEntityFromNBT(nbtTagCompound);
    }
    
    public EntityItemFrame(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        super(world, blockPos);
        this.itemDropChance = 1.0f;
        this.updateFacingWithBoundingBox(enumFacing);
    }
    
    static {
        I();
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (false) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public int func_174866_q() {
        int length;
        if (this.getDisplayedItem() == null) {
            length = "".length();
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        else {
            length = this.getRotation() % (0xCF ^ 0xC7) + " ".length();
        }
        return length;
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        if (this.getDisplayedItem() != null) {
            nbtTagCompound.setTag(EntityItemFrame.I["  ".length()], this.getDisplayedItem().writeToNBT(new NBTTagCompound()));
            nbtTagCompound.setByte(EntityItemFrame.I["   ".length()], (byte)this.getRotation());
            nbtTagCompound.setFloat(EntityItemFrame.I[0x8F ^ 0x8B], this.itemDropChance);
        }
        super.writeEntityToNBT(nbtTagCompound);
    }
    
    public void setItemRotation(final int n) {
        this.func_174865_a(n, " ".length() != 0);
    }
    
    private void removeFrameFromMap(final ItemStack itemStack) {
        if (itemStack != null) {
            if (itemStack.getItem() == Items.filled_map) {
                ((ItemMap)itemStack.getItem()).getMapData(itemStack, this.worldObj).mapDecorations.remove(EntityItemFrame.I[" ".length()] + this.getEntityId());
            }
            itemStack.setItemFrame(null);
        }
    }
    
    @Override
    public void onBroken(final Entity entity) {
        this.dropItemOrSelf(entity, " ".length() != 0);
    }
    
    @Override
    public int getWidthPixels() {
        return 0xB6 ^ 0xBA;
    }
    
    @Override
    public boolean isInRangeToRenderDist(final double n) {
        final double n2 = 16.0 * 64.0 * this.renderDistanceWeight;
        if (n < n2 * n2) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.isEntityInvulnerable(damageSource)) {
            return "".length() != 0;
        }
        if (!damageSource.isExplosion() && this.getDisplayedItem() != null) {
            if (!this.worldObj.isRemote) {
                this.dropItemOrSelf(damageSource.getEntity(), "".length() != 0);
                this.setDisplayedItem(null);
            }
            return " ".length() != 0;
        }
        return super.attackEntityFrom(damageSource, n);
    }
    
    public void setDisplayedItem(final ItemStack itemStack) {
        this.setDisplayedItemWithUpdate(itemStack, " ".length() != 0);
    }
    
    @Override
    public boolean interactFirst(final EntityPlayer entityPlayer) {
        if (this.getDisplayedItem() == null) {
            final ItemStack heldItem = entityPlayer.getHeldItem();
            if (heldItem != null && !this.worldObj.isRemote) {
                this.setDisplayedItem(heldItem);
                if (!entityPlayer.capabilities.isCreativeMode) {
                    final ItemStack itemStack = heldItem;
                    if ((itemStack.stackSize -= " ".length()) <= 0) {
                        entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
                        "".length();
                        if (-1 >= 2) {
                            throw null;
                        }
                    }
                }
            }
        }
        else if (!this.worldObj.isRemote) {
            this.setItemRotation(this.getRotation() + " ".length());
        }
        return " ".length() != 0;
    }
    
    private void func_174865_a(final int n, final boolean b) {
        this.getDataWatcher().updateObject(0x3C ^ 0x35, (byte)(n % (0x7B ^ 0x73)));
        if (b && this.hangingPosition != null) {
            this.worldObj.updateComparatorOutputLevel(this.hangingPosition, Blocks.air);
        }
    }
}
