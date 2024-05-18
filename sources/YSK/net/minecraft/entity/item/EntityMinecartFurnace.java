package net.minecraft.entity.item;

import net.minecraft.item.*;
import net.minecraft.block.state.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class EntityMinecartFurnace extends EntityMinecart
{
    private static final String[] I;
    public double pushZ;
    private int fuel;
    public double pushX;
    
    @Override
    public void killMinecart(final DamageSource damageSource) {
        super.killMinecart(damageSource);
        if (!damageSource.isExplosion() && this.worldObj.getGameRules().getBoolean(EntityMinecartFurnace.I["".length()])) {
            this.entityDropItem(new ItemStack(Blocks.furnace, " ".length()), 0.0f);
        }
    }
    
    protected void setMinecartPowered(final boolean b) {
        if (b) {
            this.dataWatcher.updateObject(0xB ^ 0x1B, (byte)(this.dataWatcher.getWatchableObjectByte(0xB6 ^ 0xA6) | " ".length()));
            "".length();
            if (0 == 3) {
                throw null;
            }
        }
        else {
            this.dataWatcher.updateObject(0x3F ^ 0x2F, (byte)(this.dataWatcher.getWatchableObjectByte(0xB3 ^ 0xA3) & -"  ".length()));
        }
    }
    
    private static void I() {
        (I = new String[0x97 ^ 0x90])["".length()] = I("<&+#91=\u0017\t?79\u001d", "XInMM");
        EntityMinecartFurnace.I[" ".length()] = I("\u0011\"\u0019\u0011\u001b", "AWjyC");
        EntityMinecartFurnace.I["  ".length()] = I("\u0011\u0005\u0017\"1", "ApdJk");
        EntityMinecartFurnace.I["   ".length()] = I("\u0015-\u0013\u0016", "SXvzj");
        EntityMinecartFurnace.I[0x3F ^ 0x3B] = I("=>196", "mKBQn");
        EntityMinecartFurnace.I[0x10 ^ 0x15] = I("\u001b\u001e\u0019/\u0013", "KkjGI");
        EntityMinecartFurnace.I[0x4B ^ 0x4D] = I("\u0013\u001b&\u001a", "UnCve");
    }
    
    @Override
    public IBlockState getDefaultDisplayTile() {
        Block block;
        if (this.isMinecartPowered()) {
            block = Blocks.lit_furnace;
            "".length();
            if (-1 < -1) {
                throw null;
            }
        }
        else {
            block = Blocks.furnace;
        }
        return block.getDefaultState().withProperty((IProperty<Comparable>)BlockFurnace.FACING, EnumFacing.NORTH);
    }
    
    @Override
    public boolean interactFirst(final EntityPlayer entityPlayer) {
        final ItemStack currentItem = entityPlayer.inventory.getCurrentItem();
        if (currentItem != null && currentItem.getItem() == Items.coal) {
            if (!entityPlayer.capabilities.isCreativeMode) {
                final ItemStack itemStack = currentItem;
                if ((itemStack.stackSize -= " ".length()) == 0) {
                    entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
                }
            }
            this.fuel += 1024 + 1342 - 1028 + 2262;
        }
        this.pushX = this.posX - entityPlayer.posX;
        this.pushZ = this.posZ - entityPlayer.posZ;
        return " ".length() != 0;
    }
    
    @Override
    public EnumMinecartType getMinecartType() {
        return EnumMinecartType.FURNACE;
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.pushX = nbtTagCompound.getDouble(EntityMinecartFurnace.I[0x3F ^ 0x3B]);
        this.pushZ = nbtTagCompound.getDouble(EntityMinecartFurnace.I[0x89 ^ 0x8C]);
        this.fuel = nbtTagCompound.getShort(EntityMinecartFurnace.I[0x5E ^ 0x58]);
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.fuel > 0) {
            this.fuel -= " ".length();
        }
        if (this.fuel <= 0) {
            final double n = 0.0;
            this.pushZ = n;
            this.pushX = n;
        }
        int minecartPowered;
        if (this.fuel > 0) {
            minecartPowered = " ".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            minecartPowered = "".length();
        }
        this.setMinecartPowered(minecartPowered != 0);
        if (this.isMinecartPowered() && this.rand.nextInt(0x5F ^ 0x5B) == 0) {
            this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY + 0.8, this.posZ, 0.0, 0.0, 0.0, new int["".length()]);
        }
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(0xD1 ^ 0xC1, new Byte((byte)"".length()));
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setDouble(EntityMinecartFurnace.I[" ".length()], this.pushX);
        nbtTagCompound.setDouble(EntityMinecartFurnace.I["  ".length()], this.pushZ);
        nbtTagCompound.setShort(EntityMinecartFurnace.I["   ".length()], (short)this.fuel);
    }
    
    @Override
    protected double getMaximumSpeed() {
        return 0.2;
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
            if (3 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected void func_180460_a(final BlockPos blockPos, final IBlockState blockState) {
        super.func_180460_a(blockPos, blockState);
        final double n = this.pushX * this.pushX + this.pushZ * this.pushZ;
        if (n > 1.0E-4 && this.motionX * this.motionX + this.motionZ * this.motionZ > 0.001) {
            final double n2 = MathHelper.sqrt_double(n);
            this.pushX /= n2;
            this.pushZ /= n2;
            if (this.pushX * this.motionX + this.pushZ * this.motionZ < 0.0) {
                this.pushX = 0.0;
                this.pushZ = 0.0;
                "".length();
                if (4 < 1) {
                    throw null;
                }
            }
            else {
                final double n3 = n2 / this.getMaximumSpeed();
                this.pushX *= n3;
                this.pushZ *= n3;
            }
        }
    }
    
    protected boolean isMinecartPowered() {
        if ((this.dataWatcher.getWatchableObjectByte(0xA6 ^ 0xB6) & " ".length()) != 0x0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public EntityMinecartFurnace(final World world) {
        super(world);
    }
    
    public EntityMinecartFurnace(final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3);
    }
    
    @Override
    protected void applyDrag() {
        final double n = this.pushX * this.pushX + this.pushZ * this.pushZ;
        if (n > 1.0E-4) {
            final double n2 = MathHelper.sqrt_double(n);
            this.pushX /= n2;
            this.pushZ /= n2;
            final double n3 = 1.0;
            this.motionX *= 0.800000011920929;
            this.motionY *= 0.0;
            this.motionZ *= 0.800000011920929;
            this.motionX += this.pushX * n3;
            this.motionZ += this.pushZ * n3;
            "".length();
            if (4 < 0) {
                throw null;
            }
        }
        else {
            this.motionX *= 0.9800000190734863;
            this.motionY *= 0.0;
            this.motionZ *= 0.9800000190734863;
        }
        super.applyDrag();
    }
}
