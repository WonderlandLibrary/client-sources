/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.INBTType;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class EndNBT
implements INBT {
    public static final INBTType<EndNBT> TYPE = new INBTType<EndNBT>(){

        @Override
        public EndNBT readNBT(DataInput dataInput, int n, NBTSizeTracker nBTSizeTracker) {
            nBTSizeTracker.read(64L);
            return INSTANCE;
        }

        @Override
        public String getName() {
            return "END";
        }

        @Override
        public String getTagName() {
            return "TAG_End";
        }

        @Override
        public boolean isPrimitive() {
            return false;
        }

        @Override
        public INBT readNBT(DataInput dataInput, int n, NBTSizeTracker nBTSizeTracker) throws IOException {
            return this.readNBT(dataInput, n, nBTSizeTracker);
        }
    };
    public static final EndNBT INSTANCE = new EndNBT();

    private EndNBT() {
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
    }

    @Override
    public byte getId() {
        return 1;
    }

    public INBTType<EndNBT> getType() {
        return TYPE;
    }

    @Override
    public String toString() {
        return "END";
    }

    @Override
    public EndNBT copy() {
        return this;
    }

    @Override
    public ITextComponent toFormattedComponent(String string, int n) {
        return StringTextComponent.EMPTY;
    }

    @Override
    public INBT copy() {
        return this.copy();
    }
}

