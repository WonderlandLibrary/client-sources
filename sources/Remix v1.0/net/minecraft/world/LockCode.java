package net.minecraft.world;

import net.minecraft.nbt.*;

public class LockCode
{
    public static final LockCode EMPTY_CODE;
    private final String lock;
    
    public LockCode(final String p_i45903_1_) {
        this.lock = p_i45903_1_;
    }
    
    public static LockCode fromNBT(final NBTTagCompound nbt) {
        if (nbt.hasKey("Lock", 8)) {
            final String var1 = nbt.getString("Lock");
            return new LockCode(var1);
        }
        return LockCode.EMPTY_CODE;
    }
    
    public boolean isEmpty() {
        return this.lock == null || this.lock.isEmpty();
    }
    
    public String getLock() {
        return this.lock;
    }
    
    public void toNBT(final NBTTagCompound nbt) {
        nbt.setString("Lock", this.lock);
    }
    
    static {
        EMPTY_CODE = new LockCode("");
    }
}
