package net.minecraft.entity.item;

import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.*;
import net.minecraft.tileentity.*;
import com.google.common.base.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.block.*;

public class EntityMinecartHopper extends EntityMinecartContainer implements IHopper
{
    private static final String[] I;
    private BlockPos field_174900_c;
    private boolean isBlocked;
    private int transferTicker;
    
    @Override
    public World getWorld() {
        return this.worldObj;
    }
    
    @Override
    public IBlockState getDefaultDisplayTile() {
        return Blocks.hopper.getDefaultState();
    }
    
    @Override
    public double getXPos() {
        return this.posX;
    }
    
    public EntityMinecartHopper(final World world) {
        super(world);
        this.isBlocked = (" ".length() != 0);
        this.transferTicker = -" ".length();
        this.field_174900_c = BlockPos.ORIGIN;
    }
    
    @Override
    public EnumMinecartType getMinecartType() {
        return EnumMinecartType.HOPPER;
    }
    
    public boolean getBlocked() {
        return this.isBlocked;
    }
    
    public void setBlocked(final boolean isBlocked) {
        this.isBlocked = isBlocked;
    }
    
    @Override
    public double getZPos() {
        return this.posZ;
    }
    
    @Override
    public int getSizeInventory() {
        return 0xBE ^ 0xBB;
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.transferTicker = nbtTagCompound.getInteger(EntityMinecartHopper.I["  ".length()]);
    }
    
    static {
        I();
    }
    
    @Override
    public double getYPos() {
        return this.posY + 0.5;
    }
    
    @Override
    public boolean interactFirst(final EntityPlayer entityPlayer) {
        if (!this.worldObj.isRemote) {
            entityPlayer.displayGUIChest(this);
        }
        return " ".length() != 0;
    }
    
    private static void I() {
        (I = new String[0x40 ^ 0x44])["".length()] = I("\u000e:7:\"\u0003!\u000b\u0010$\u0005%\u0001", "jUrTV");
        EntityMinecartHopper.I[" ".length()] = I("-\u001c1\b\u0018\u001f\u000b\"%\u0004\u0016\u00024\t\u001c\u0017", "ynPfk");
        EntityMinecartHopper.I["  ".length()] = I("\u001f#6\u0001\u0005-4%,\u0019$=3\u0000\u0001%", "KQWov");
        EntityMinecartHopper.I["   ".length()] = I("\u001d! \u0000\u0015\u0002)(\u0011L\u0018'>\u0015\u0013\u0002", "pHNev");
    }
    
    @Override
    public Container createContainer(final InventoryPlayer inventoryPlayer, final EntityPlayer entityPlayer) {
        return new ContainerHopper(inventoryPlayer, this, entityPlayer);
    }
    
    public void setTransferTicker(final int transferTicker) {
        this.transferTicker = transferTicker;
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.worldObj.isRemote && this.isEntityAlive() && this.getBlocked()) {
            if (new BlockPos(this).equals(this.field_174900_c)) {
                this.transferTicker -= " ".length();
                "".length();
                if (2 <= 0) {
                    throw null;
                }
            }
            else {
                this.setTransferTicker("".length());
            }
            if (!this.canTransfer()) {
                this.setTransferTicker("".length());
                if (this.func_96112_aD()) {
                    this.setTransferTicker(0x19 ^ 0x1D);
                    this.markDirty();
                }
            }
        }
    }
    
    @Override
    public int getDefaultDisplayTileOffset() {
        return " ".length();
    }
    
    @Override
    public void onActivatorRailPass(final int n, final int n2, final int n3, final boolean b) {
        int n4;
        if (b) {
            n4 = "".length();
            "".length();
            if (4 < 3) {
                throw null;
            }
        }
        else {
            n4 = " ".length();
        }
        final int blocked = n4;
        if (blocked != (this.getBlocked() ? 1 : 0)) {
            this.setBlocked(blocked != 0);
        }
    }
    
    @Override
    public String getGuiID() {
        return EntityMinecartHopper.I["   ".length()];
    }
    
    public boolean canTransfer() {
        if (this.transferTicker > 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public boolean func_96112_aD() {
        if (TileEntityHopper.captureDroppedItems(this)) {
            return " ".length() != 0;
        }
        final List<EntityItem> entitiesWithinAABB = (List<EntityItem>)this.worldObj.getEntitiesWithinAABB((Class<? extends Entity>)EntityItem.class, this.getEntityBoundingBox().expand(0.25, 0.0, 0.25), (com.google.common.base.Predicate<? super Entity>)EntitySelectors.selectAnything);
        if (entitiesWithinAABB.size() > 0) {
            TileEntityHopper.putDropInInventoryAllSlots(this, entitiesWithinAABB.get("".length()));
        }
        return "".length() != 0;
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setInteger(EntityMinecartHopper.I[" ".length()], this.transferTicker);
    }
    
    public EntityMinecartHopper(final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3);
        this.isBlocked = (" ".length() != 0);
        this.transferTicker = -" ".length();
        this.field_174900_c = BlockPos.ORIGIN;
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
            if (0 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void killMinecart(final DamageSource damageSource) {
        super.killMinecart(damageSource);
        if (this.worldObj.getGameRules().getBoolean(EntityMinecartHopper.I["".length()])) {
            this.dropItemWithOffset(Item.getItemFromBlock(Blocks.hopper), " ".length(), 0.0f);
        }
    }
}
