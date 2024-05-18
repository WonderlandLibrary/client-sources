package net.minecraft.entity;

import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public abstract class EntityAgeable extends EntityCreature
{
    protected int field_175503_c;
    protected int field_175502_b;
    private float ageHeight;
    protected int growingAge;
    private float ageWidth;
    private static final String[] I;
    
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
            if (4 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.worldObj.isRemote) {
            if (this.field_175503_c > 0) {
                if (this.field_175503_c % (0x8F ^ 0x8B) == 0) {
                    this.worldObj.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width, this.posY + 0.5 + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width, 0.0, 0.0, 0.0, new int["".length()]);
                }
                this.field_175503_c -= " ".length();
            }
            this.setScaleForAge(this.isChild());
            "".length();
            if (0 < -1) {
                throw null;
            }
        }
        else {
            int growingAge = this.getGrowingAge();
            if (growingAge < 0) {
                ++growingAge;
                this.setGrowingAge(growingAge);
                if (growingAge == 0) {
                    this.onGrowingAdult();
                    "".length();
                    if (0 >= 3) {
                        throw null;
                    }
                }
            }
            else if (growingAge > 0) {
                --growingAge;
                this.setGrowingAge(growingAge);
            }
        }
    }
    
    public int getGrowingAge() {
        int n;
        if (this.worldObj.isRemote) {
            n = this.dataWatcher.getWatchableObjectByte(0x48 ^ 0x44);
            "".length();
            if (3 == -1) {
                throw null;
            }
        }
        else {
            n = this.growingAge;
        }
        return n;
    }
    
    static {
        I();
    }
    
    protected final void setScale(final float n) {
        super.setSize(this.ageWidth * n, this.ageHeight * n);
    }
    
    public void addGrowth(final int n) {
        this.func_175501_a(n, "".length() != 0);
    }
    
    public boolean interact(final EntityPlayer entityPlayer) {
        final ItemStack currentItem = entityPlayer.inventory.getCurrentItem();
        if (currentItem != null && currentItem.getItem() == Items.spawn_egg) {
            if (!this.worldObj.isRemote) {
                final Class<? extends Entity> classFromID = EntityList.getClassFromID(currentItem.getMetadata());
                if (classFromID != null && this.getClass() == classFromID) {
                    final EntityAgeable child = this.createChild(this);
                    if (child != null) {
                        child.setGrowingAge(-(4556 + 23821 - 28253 + 23876));
                        child.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0f, 0.0f);
                        this.worldObj.spawnEntityInWorld(child);
                        if (currentItem.hasDisplayName()) {
                            child.setCustomNameTag(currentItem.getDisplayName());
                        }
                        if (!entityPlayer.capabilities.isCreativeMode) {
                            final ItemStack itemStack = currentItem;
                            itemStack.stackSize -= " ".length();
                            if (currentItem.stackSize <= 0) {
                                entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
                            }
                        }
                    }
                }
            }
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.setGrowingAge(nbtTagCompound.getInteger(EntityAgeable.I["  ".length()]));
        this.field_175502_b = nbtTagCompound.getInteger(EntityAgeable.I["   ".length()]);
    }
    
    @Override
    protected final void setSize(final float ageWidth, final float ageHeight) {
        int n;
        if (this.ageWidth > 0.0f) {
            n = " ".length();
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        final int n2 = n;
        this.ageWidth = ageWidth;
        this.ageHeight = ageHeight;
        if (n2 == 0) {
            this.setScale(1.0f);
        }
    }
    
    @Override
    public boolean isChild() {
        if (this.getGrowingAge() < 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private static void I() {
        (I = new String[0xA5 ^ 0xA1])["".length()] = I(".\t)", "onLMk");
        EntityAgeable.I[" ".length()] = I("\u0012:\u00164<0\u0014\u00032", "TUdWY");
        EntityAgeable.I["  ".length()] = I("2\u0003\u0004", "sdaKK");
        EntityAgeable.I["   ".length()] = I("/8\u00043\u0016\r\u0016\u00115", "iWvPs");
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setInteger(EntityAgeable.I["".length()], this.getGrowingAge());
        nbtTagCompound.setInteger(EntityAgeable.I[" ".length()], this.field_175502_b);
    }
    
    protected void onGrowingAdult() {
    }
    
    public void setGrowingAge(final int growingAge) {
        this.dataWatcher.updateObject(0xAB ^ 0xA7, (byte)MathHelper.clamp_int(growingAge, -" ".length(), " ".length()));
        this.growingAge = growingAge;
        this.setScaleForAge(this.isChild());
    }
    
    public EntityAgeable(final World world) {
        super(world);
        this.ageWidth = -1.0f;
    }
    
    public void setScaleForAge(final boolean b) {
        float scale;
        if (b) {
            scale = 0.5f;
            "".length();
            if (3 == 2) {
                throw null;
            }
        }
        else {
            scale = 1.0f;
        }
        this.setScale(scale);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(0x21 ^ 0x2D, (byte)"".length());
    }
    
    public void func_175501_a(final int n, final boolean b) {
        final int growingAge;
        int length = (growingAge = this.getGrowingAge()) + n * (0xA3 ^ 0xB7);
        if (length > 0) {
            length = "".length();
            if (growingAge < 0) {
                this.onGrowingAdult();
            }
        }
        final int n2 = length - growingAge;
        this.setGrowingAge(length);
        if (b) {
            this.field_175502_b += n2;
            if (this.field_175503_c == 0) {
                this.field_175503_c = (0x0 ^ 0x28);
            }
        }
        if (this.getGrowingAge() == 0) {
            this.setGrowingAge(this.field_175502_b);
        }
    }
    
    public abstract EntityAgeable createChild(final EntityAgeable p0);
}
