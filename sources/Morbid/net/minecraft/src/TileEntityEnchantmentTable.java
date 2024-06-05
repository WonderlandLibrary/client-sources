package net.minecraft.src;

import java.util.*;

public class TileEntityEnchantmentTable extends TileEntity
{
    public int tickCount;
    public float pageFlip;
    public float pageFlipPrev;
    public float field_70373_d;
    public float field_70374_e;
    public float bookSpread;
    public float bookSpreadPrev;
    public float bookRotation2;
    public float bookRotationPrev;
    public float bookRotation;
    private static Random rand;
    private String field_94136_s;
    
    static {
        TileEntityEnchantmentTable.rand = new Random();
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);
        if (this.func_94135_b()) {
            par1NBTTagCompound.setString("CustomName", this.field_94136_s);
        }
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readFromNBT(par1NBTTagCompound);
        if (par1NBTTagCompound.hasKey("CustomName")) {
            this.field_94136_s = par1NBTTagCompound.getString("CustomName");
        }
    }
    
    @Override
    public void updateEntity() {
        super.updateEntity();
        this.bookSpreadPrev = this.bookSpread;
        this.bookRotationPrev = this.bookRotation2;
        final EntityPlayer var1 = this.worldObj.getClosestPlayer(this.xCoord + 0.5f, this.yCoord + 0.5f, this.zCoord + 0.5f, 3.0);
        if (var1 != null) {
            final double var2 = var1.posX - (this.xCoord + 0.5f);
            final double var3 = var1.posZ - (this.zCoord + 0.5f);
            this.bookRotation = (float)Math.atan2(var3, var2);
            this.bookSpread += 0.1f;
            if (this.bookSpread < 0.5f || TileEntityEnchantmentTable.rand.nextInt(40) == 0) {
                final float var4 = this.field_70373_d;
                do {
                    this.field_70373_d += TileEntityEnchantmentTable.rand.nextInt(4) - TileEntityEnchantmentTable.rand.nextInt(4);
                } while (var4 == this.field_70373_d);
            }
        }
        else {
            this.bookRotation += 0.02f;
            this.bookSpread -= 0.1f;
        }
        while (this.bookRotation2 >= 3.1415927f) {
            this.bookRotation2 -= 6.2831855f;
        }
        while (this.bookRotation2 < -3.1415927f) {
            this.bookRotation2 += 6.2831855f;
        }
        while (this.bookRotation >= 3.1415927f) {
            this.bookRotation -= 6.2831855f;
        }
        while (this.bookRotation < -3.1415927f) {
            this.bookRotation += 6.2831855f;
        }
        float var5;
        for (var5 = this.bookRotation - this.bookRotation2; var5 >= 3.1415927f; var5 -= 6.2831855f) {}
        while (var5 < -3.1415927f) {
            var5 += 6.2831855f;
        }
        this.bookRotation2 += var5 * 0.4f;
        if (this.bookSpread < 0.0f) {
            this.bookSpread = 0.0f;
        }
        if (this.bookSpread > 1.0f) {
            this.bookSpread = 1.0f;
        }
        ++this.tickCount;
        this.pageFlipPrev = this.pageFlip;
        float var6 = (this.field_70373_d - this.pageFlip) * 0.4f;
        final float var7 = 0.2f;
        if (var6 < -var7) {
            var6 = -var7;
        }
        if (var6 > var7) {
            var6 = var7;
        }
        this.field_70374_e += (var6 - this.field_70374_e) * 0.9f;
        this.pageFlip += this.field_70374_e;
    }
    
    public String func_94133_a() {
        return this.func_94135_b() ? this.field_94136_s : "container.enchant";
    }
    
    public boolean func_94135_b() {
        return this.field_94136_s != null && this.field_94136_s.length() > 0;
    }
    
    public void func_94134_a(final String par1Str) {
        this.field_94136_s = par1Str;
    }
}
