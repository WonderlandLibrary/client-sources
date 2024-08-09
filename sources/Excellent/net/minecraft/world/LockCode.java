package net.minecraft.world;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import javax.annotation.concurrent.Immutable;

@Immutable
public class LockCode
{
    public static final LockCode EMPTY_CODE = new LockCode("");
    private final String lock;

    public LockCode(String code)
    {
        this.lock = code;
    }

    public boolean func_219964_a(ItemStack p_219964_1_)
    {
        return this.lock.isEmpty() || !p_219964_1_.isEmpty() && p_219964_1_.hasDisplayName() && this.lock.equals(p_219964_1_.getDisplayName().getString());
    }

    public void write(CompoundNBT nbt)
    {
        if (!this.lock.isEmpty())
        {
            nbt.putString("Lock", this.lock);
        }
    }

    public static LockCode read(CompoundNBT nbt)
    {
        return nbt.contains("Lock", 8) ? new LockCode(nbt.getString("Lock")) : EMPTY_CODE;
    }
}
