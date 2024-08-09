/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.nbt;

import java.io.DataInput;
import java.io.IOException;
import net.minecraft.nbt.EndNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTSizeTracker;

public interface INBTType<T extends INBT> {
    public T readNBT(DataInput var1, int var2, NBTSizeTracker var3) throws IOException;

    default public boolean isPrimitive() {
        return true;
    }

    public String getName();

    public String getTagName();

    public static INBTType<EndNBT> getEndNBT(int n) {
        return new INBTType<EndNBT>(n){
            final int val$id;
            {
                this.val$id = n;
            }

            @Override
            public EndNBT readNBT(DataInput dataInput, int n, NBTSizeTracker nBTSizeTracker) throws IOException {
                throw new IllegalArgumentException("Invalid tag id: " + this.val$id);
            }

            @Override
            public String getName() {
                return "INVALID[" + this.val$id + "]";
            }

            @Override
            public String getTagName() {
                return "UNKNOWN_" + this.val$id;
            }

            @Override
            public INBT readNBT(DataInput dataInput, int n, NBTSizeTracker nBTSizeTracker) throws IOException {
                return this.readNBT(dataInput, n, nBTSizeTracker);
            }
        };
    }
}

