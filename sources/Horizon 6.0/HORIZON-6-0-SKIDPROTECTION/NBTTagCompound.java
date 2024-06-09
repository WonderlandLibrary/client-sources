package HORIZON-6-0-SKIDPROTECTION;

import java.util.concurrent.Callable;
import java.util.Set;
import java.io.DataInput;
import java.io.IOException;
import java.util.Iterator;
import java.io.DataOutput;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import java.util.Map;
import org.apache.logging.log4j.Logger;

public class NBTTagCompound extends NBTBase
{
    private static final Logger Â;
    private Map Ý;
    private static final String Ø­áŒŠá = "CL_00001215";
    
    static {
        Â = LogManager.getLogger();
    }
    
    public NBTTagCompound() {
        this.Ý = Maps.newHashMap();
    }
    
    @Override
    void HorizonCode_Horizon_È(final DataOutput output) throws IOException {
        for (final String var3 : this.Ý.keySet()) {
            final NBTBase var4 = this.Ý.get(var3);
            HorizonCode_Horizon_È(var3, var4, output);
        }
        output.writeByte(0);
    }
    
    @Override
    void HorizonCode_Horizon_È(final DataInput input, final int depth, final NBTSizeTracker sizeTracker) throws IOException {
        if (depth > 512) {
            throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
        }
        this.Ý.clear();
        byte var4;
        while ((var4 = HorizonCode_Horizon_È(input, sizeTracker)) != 0) {
            final String var5 = Â(input, sizeTracker);
            sizeTracker.HorizonCode_Horizon_È(16 * var5.length());
            final NBTBase var6 = HorizonCode_Horizon_È(var4, var5, input, depth + 1, sizeTracker);
            this.Ý.put(var5, var6);
        }
    }
    
    public Set Âµá€() {
        return this.Ý.keySet();
    }
    
    @Override
    public byte HorizonCode_Horizon_È() {
        return 10;
    }
    
    public void HorizonCode_Horizon_È(final String key, final NBTBase value) {
        this.Ý.put(key, value);
    }
    
    public void HorizonCode_Horizon_È(final String key, final byte value) {
        this.Ý.put(key, new NBTTagByte(value));
    }
    
    public void HorizonCode_Horizon_È(final String key, final short value) {
        this.Ý.put(key, new NBTTagShort(value));
    }
    
    public void HorizonCode_Horizon_È(final String key, final int value) {
        this.Ý.put(key, new NBTTagInt(value));
    }
    
    public void HorizonCode_Horizon_È(final String key, final long value) {
        this.Ý.put(key, new NBTTagLong(value));
    }
    
    public void HorizonCode_Horizon_È(final String key, final float value) {
        this.Ý.put(key, new NBTTagFloat(value));
    }
    
    public void HorizonCode_Horizon_È(final String key, final double value) {
        this.Ý.put(key, new NBTTagDouble(value));
    }
    
    public void HorizonCode_Horizon_È(final String key, final String value) {
        this.Ý.put(key, new NBTTagString(value));
    }
    
    public void HorizonCode_Horizon_È(final String key, final byte[] value) {
        this.Ý.put(key, new NBTTagByteArray(value));
    }
    
    public void HorizonCode_Horizon_È(final String key, final int[] value) {
        this.Ý.put(key, new NBTTagIntArray(value));
    }
    
    public void HorizonCode_Horizon_È(final String key, final boolean value) {
        this.HorizonCode_Horizon_È(key, (byte)(value ? 1 : 0));
    }
    
    public NBTBase HorizonCode_Horizon_È(final String key) {
        return this.Ý.get(key);
    }
    
    public byte Â(final String key) {
        final NBTBase var2 = this.Ý.get(key);
        return (byte)((var2 != null) ? var2.HorizonCode_Horizon_È() : 0);
    }
    
    public boolean Ý(final String key) {
        return this.Ý.containsKey(key);
    }
    
    public boolean Â(final String key, final int type) {
        final byte var3 = this.Â(key);
        if (var3 == type) {
            return true;
        }
        if (type != 99) {
            if (var3 > 0) {}
            return false;
        }
        return var3 == 1 || var3 == 2 || var3 == 3 || var3 == 4 || var3 == 5 || var3 == 6;
    }
    
    public byte Ø­áŒŠá(final String key) {
        try {
            return (byte)(this.Â(key, 99) ? this.Ý.get(key).Ø() : 0);
        }
        catch (ClassCastException var3) {
            return 0;
        }
    }
    
    public short Âµá€(final String key) {
        try {
            return (short)(this.Â(key, 99) ? this.Ý.get(key).à() : 0);
        }
        catch (ClassCastException var3) {
            return 0;
        }
    }
    
    public int Ó(final String key) {
        try {
            return this.Â(key, 99) ? this.Ý.get(key).Ó() : 0;
        }
        catch (ClassCastException var3) {
            return 0;
        }
    }
    
    public long à(final String key) {
        try {
            return this.Â(key, 99) ? this.Ý.get(key).Âµá€() : 0L;
        }
        catch (ClassCastException var3) {
            return 0L;
        }
    }
    
    public float Ø(final String key) {
        try {
            return this.Â(key, 99) ? this.Ý.get(key).áˆºÑ¢Õ() : 0.0f;
        }
        catch (ClassCastException var3) {
            return 0.0f;
        }
    }
    
    public double áŒŠÆ(final String key) {
        try {
            return this.Â(key, 99) ? this.Ý.get(key).áŒŠÆ() : 0.0;
        }
        catch (ClassCastException var3) {
            return 0.0;
        }
    }
    
    public String áˆºÑ¢Õ(final String key) {
        try {
            return this.Â(key, 8) ? this.Ý.get(key).Ø­áŒŠá() : "";
        }
        catch (ClassCastException var3) {
            return "";
        }
    }
    
    public byte[] ÂµÈ(final String key) {
        try {
            return this.Â(key, 7) ? this.Ý.get(key).Âµá€() : new byte[0];
        }
        catch (ClassCastException var3) {
            throw new ReportedException(this.HorizonCode_Horizon_È(key, 7, var3));
        }
    }
    
    public int[] á(final String key) {
        try {
            return this.Â(key, 11) ? this.Ý.get(key).Âµá€() : new int[0];
        }
        catch (ClassCastException var3) {
            throw new ReportedException(this.HorizonCode_Horizon_È(key, 11, var3));
        }
    }
    
    public NBTTagCompound ˆÏ­(final String key) {
        try {
            return this.Â(key, 10) ? this.Ý.get(key) : new NBTTagCompound();
        }
        catch (ClassCastException var3) {
            throw new ReportedException(this.HorizonCode_Horizon_È(key, 10, var3));
        }
    }
    
    public NBTTagList Ý(final String key, final int type) {
        try {
            if (this.Â(key) != 9) {
                return new NBTTagList();
            }
            final NBTTagList var3 = this.Ý.get(key);
            return (var3.Âµá€() > 0 && var3.Ó() != type) ? new NBTTagList() : var3;
        }
        catch (ClassCastException var4) {
            throw new ReportedException(this.HorizonCode_Horizon_È(key, 9, var4));
        }
    }
    
    public boolean £á(final String key) {
        return this.Ø­áŒŠá(key) != 0;
    }
    
    public void Å(final String key) {
        this.Ý.remove(key);
    }
    
    @Override
    public String toString() {
        String var1 = "{";
        for (final String var3 : this.Ý.keySet()) {
            var1 = String.valueOf(var1) + var3 + ':' + this.Ý.get(var3) + ',';
        }
        return String.valueOf(var1) + "}";
    }
    
    @Override
    public boolean Ý() {
        return this.Ý.isEmpty();
    }
    
    private CrashReport HorizonCode_Horizon_È(final String key, final int expectedType, final ClassCastException ex) {
        final CrashReport var4 = CrashReport.HorizonCode_Horizon_È(ex, "Reading NBT data");
        final CrashReportCategory var5 = var4.HorizonCode_Horizon_È("Corrupt NBT tag", 1);
        var5.HorizonCode_Horizon_È("Tag type found", new Callable() {
            private static final String Â = "CL_00001216";
            
            public String HorizonCode_Horizon_È() {
                return NBTBase.HorizonCode_Horizon_È[NBTTagCompound.this.Ý.get(key).HorizonCode_Horizon_È()];
            }
        });
        var5.HorizonCode_Horizon_È("Tag type expected", new Callable() {
            private static final String Â = "CL_00001217";
            
            public String HorizonCode_Horizon_È() {
                return NBTBase.HorizonCode_Horizon_È[expectedType];
            }
        });
        var5.HorizonCode_Horizon_È("Tag name", key);
        return var4;
    }
    
    @Override
    public NBTBase Â() {
        final NBTTagCompound var1 = new NBTTagCompound();
        for (final String var3 : this.Ý.keySet()) {
            var1.HorizonCode_Horizon_È(var3, this.Ý.get(var3).Â());
        }
        return var1;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (super.equals(p_equals_1_)) {
            final NBTTagCompound var2 = (NBTTagCompound)p_equals_1_;
            return this.Ý.entrySet().equals(var2.Ý.entrySet());
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ this.Ý.hashCode();
    }
    
    private static void HorizonCode_Horizon_È(final String name, final NBTBase data, final DataOutput output) throws IOException {
        output.writeByte(data.HorizonCode_Horizon_È());
        if (data.HorizonCode_Horizon_È() != 0) {
            output.writeUTF(name);
            data.HorizonCode_Horizon_È(output);
        }
    }
    
    private static byte HorizonCode_Horizon_È(final DataInput input, final NBTSizeTracker sizeTracker) throws IOException {
        return input.readByte();
    }
    
    private static String Â(final DataInput input, final NBTSizeTracker sizeTracker) throws IOException {
        return input.readUTF();
    }
    
    static NBTBase HorizonCode_Horizon_È(final byte id, final String key, final DataInput input, final int depth, final NBTSizeTracker sizeTracker) {
        final NBTBase var5 = NBTBase.HorizonCode_Horizon_È(id);
        try {
            var5.HorizonCode_Horizon_È(input, depth, sizeTracker);
            return var5;
        }
        catch (IOException var7) {
            final CrashReport var6 = CrashReport.HorizonCode_Horizon_È(var7, "Loading NBT data");
            final CrashReportCategory var8 = var6.HorizonCode_Horizon_È("NBT Tag");
            var8.HorizonCode_Horizon_È("Tag name", key);
            var8.HorizonCode_Horizon_È("Tag type", id);
            throw new ReportedException(var6);
        }
    }
    
    public void HorizonCode_Horizon_È(final NBTTagCompound other) {
        for (final String var3 : other.Ý.keySet()) {
            final NBTBase var4 = other.Ý.get(var3);
            if (var4.HorizonCode_Horizon_È() == 10) {
                if (this.Â(var3, 10)) {
                    final NBTTagCompound var5 = this.ˆÏ­(var3);
                    var5.HorizonCode_Horizon_È((NBTTagCompound)var4);
                }
                else {
                    this.HorizonCode_Horizon_È(var3, var4.Â());
                }
            }
            else {
                this.HorizonCode_Horizon_È(var3, var4.Â());
            }
        }
    }
}
