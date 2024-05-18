package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.io.DataInput;
import java.io.IOException;
import java.io.DataOutput;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import java.util.List;
import org.apache.logging.log4j.Logger;

public class NBTTagList extends NBTBase
{
    private static final Logger Â;
    private List Ý;
    private byte Ø­áŒŠá;
    private static final String Âµá€ = "CL_00001224";
    
    static {
        Â = LogManager.getLogger();
    }
    
    public NBTTagList() {
        this.Ý = Lists.newArrayList();
        this.Ø­áŒŠá = 0;
    }
    
    @Override
    void HorizonCode_Horizon_È(final DataOutput output) throws IOException {
        if (!this.Ý.isEmpty()) {
            this.Ø­áŒŠá = this.Ý.get(0).HorizonCode_Horizon_È();
        }
        else {
            this.Ø­áŒŠá = 0;
        }
        output.writeByte(this.Ø­áŒŠá);
        output.writeInt(this.Ý.size());
        for (int var2 = 0; var2 < this.Ý.size(); ++var2) {
            this.Ý.get(var2).HorizonCode_Horizon_È(output);
        }
    }
    
    @Override
    void HorizonCode_Horizon_È(final DataInput input, final int depth, final NBTSizeTracker sizeTracker) throws IOException {
        if (depth > 512) {
            throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
        }
        sizeTracker.HorizonCode_Horizon_È(8L);
        this.Ø­áŒŠá = input.readByte();
        final int var4 = input.readInt();
        this.Ý = Lists.newArrayList();
        for (int var5 = 0; var5 < var4; ++var5) {
            final NBTBase var6 = NBTBase.HorizonCode_Horizon_È(this.Ø­áŒŠá);
            var6.HorizonCode_Horizon_È(input, depth + 1, sizeTracker);
            this.Ý.add(var6);
        }
    }
    
    @Override
    public byte HorizonCode_Horizon_È() {
        return 9;
    }
    
    @Override
    public String toString() {
        String var1 = "[";
        int var2 = 0;
        for (final NBTBase var4 : this.Ý) {
            var1 = String.valueOf(var1) + var2 + ':' + var4 + ',';
            ++var2;
        }
        return String.valueOf(var1) + "]";
    }
    
    public void HorizonCode_Horizon_È(final NBTBase nbt) {
        if (this.Ø­áŒŠá == 0) {
            this.Ø­áŒŠá = nbt.HorizonCode_Horizon_È();
        }
        else if (this.Ø­áŒŠá != nbt.HorizonCode_Horizon_È()) {
            NBTTagList.Â.warn("Adding mismatching tag types to tag list");
            return;
        }
        this.Ý.add(nbt);
    }
    
    public void HorizonCode_Horizon_È(final int idx, final NBTBase nbt) {
        if (idx >= 0 && idx < this.Ý.size()) {
            if (this.Ø­áŒŠá == 0) {
                this.Ø­áŒŠá = nbt.HorizonCode_Horizon_È();
            }
            else if (this.Ø­áŒŠá != nbt.HorizonCode_Horizon_È()) {
                NBTTagList.Â.warn("Adding mismatching tag types to tag list");
                return;
            }
            this.Ý.set(idx, nbt);
        }
        else {
            NBTTagList.Â.warn("index out of bounds to set tag in tag list");
        }
    }
    
    public NBTBase HorizonCode_Horizon_È(final int i) {
        return this.Ý.remove(i);
    }
    
    @Override
    public boolean Ý() {
        return this.Ý.isEmpty();
    }
    
    public NBTTagCompound Â(final int i) {
        if (i >= 0 && i < this.Ý.size()) {
            final NBTBase var2 = this.Ý.get(i);
            return (NBTTagCompound)((var2.HorizonCode_Horizon_È() == 10) ? var2 : new NBTTagCompound());
        }
        return new NBTTagCompound();
    }
    
    public int[] Ý(final int i) {
        if (i >= 0 && i < this.Ý.size()) {
            final NBTBase var2 = this.Ý.get(i);
            return (var2.HorizonCode_Horizon_È() == 11) ? ((NBTTagIntArray)var2).Âµá€() : new int[0];
        }
        return new int[0];
    }
    
    public double Ø­áŒŠá(final int i) {
        if (i >= 0 && i < this.Ý.size()) {
            final NBTBase var2 = this.Ý.get(i);
            return (var2.HorizonCode_Horizon_È() == 6) ? ((NBTTagDouble)var2).áŒŠÆ() : 0.0;
        }
        return 0.0;
    }
    
    public float Âµá€(final int i) {
        if (i >= 0 && i < this.Ý.size()) {
            final NBTBase var2 = this.Ý.get(i);
            return (var2.HorizonCode_Horizon_È() == 5) ? ((NBTTagFloat)var2).áˆºÑ¢Õ() : 0.0f;
        }
        return 0.0f;
    }
    
    public String Ó(final int i) {
        if (i >= 0 && i < this.Ý.size()) {
            final NBTBase var2 = this.Ý.get(i);
            return (var2.HorizonCode_Horizon_È() == 8) ? var2.Ø­áŒŠá() : var2.toString();
        }
        return "";
    }
    
    public NBTBase à(final int idx) {
        return (idx >= 0 && idx < this.Ý.size()) ? this.Ý.get(idx) : new NBTTagEnd();
    }
    
    public int Âµá€() {
        return this.Ý.size();
    }
    
    @Override
    public NBTBase Â() {
        final NBTTagList var1 = new NBTTagList();
        var1.Ø­áŒŠá = this.Ø­áŒŠá;
        for (final NBTBase var3 : this.Ý) {
            final NBTBase var4 = var3.Â();
            var1.Ý.add(var4);
        }
        return var1;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (super.equals(p_equals_1_)) {
            final NBTTagList var2 = (NBTTagList)p_equals_1_;
            if (this.Ø­áŒŠá == var2.Ø­áŒŠá) {
                return this.Ý.equals(var2.Ý);
            }
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ this.Ý.hashCode();
    }
    
    public int Ó() {
        return this.Ø­áŒŠá;
    }
}
