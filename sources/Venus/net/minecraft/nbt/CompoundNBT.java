/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.nbt;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.nbt.ByteArrayNBT;
import net.minecraft.nbt.ByteNBT;
import net.minecraft.nbt.DoubleNBT;
import net.minecraft.nbt.FloatNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.INBTType;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.LongArrayNBT;
import net.minecraft.nbt.LongNBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTypes;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.nbt.NumberNBT;
import net.minecraft.nbt.ShortNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class CompoundNBT
implements INBT {
    public static final Codec<CompoundNBT> CODEC = Codec.PASSTHROUGH.comapFlatMap(CompoundNBT::lambda$static$0, CompoundNBT::lambda$static$1);
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Pattern SIMPLE_VALUE = Pattern.compile("[A-Za-z0-9._+-]+");
    public static final INBTType<CompoundNBT> TYPE = new INBTType<CompoundNBT>(){

        @Override
        public CompoundNBT readNBT(DataInput dataInput, int n, NBTSizeTracker nBTSizeTracker) throws IOException {
            byte by;
            nBTSizeTracker.read(384L);
            if (n > 512) {
                throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
            }
            HashMap<String, INBT> hashMap = Maps.newHashMap();
            while ((by = CompoundNBT.readType(dataInput, nBTSizeTracker)) != 0) {
                String string = CompoundNBT.readKey(dataInput, nBTSizeTracker);
                nBTSizeTracker.read(224 + 16 * string.length());
                INBT iNBT = CompoundNBT.loadNBT(NBTTypes.getGetTypeByID(by), string, dataInput, n + 1, nBTSizeTracker);
                if (hashMap.put(string, iNBT) == null) continue;
                nBTSizeTracker.read(288L);
            }
            return new CompoundNBT(hashMap);
        }

        @Override
        public String getName() {
            return "COMPOUND";
        }

        @Override
        public String getTagName() {
            return "TAG_Compound";
        }

        @Override
        public INBT readNBT(DataInput dataInput, int n, NBTSizeTracker nBTSizeTracker) throws IOException {
            return this.readNBT(dataInput, n, nBTSizeTracker);
        }
    };
    private final Map<String, INBT> tagMap;

    protected CompoundNBT(Map<String, INBT> map) {
        this.tagMap = map;
    }

    public CompoundNBT() {
        this(Maps.newHashMap());
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        for (String string : this.tagMap.keySet()) {
            INBT iNBT = this.tagMap.get(string);
            CompoundNBT.writeEntry(string, iNBT, dataOutput);
        }
        dataOutput.writeByte(0);
    }

    public Set<String> keySet() {
        return this.tagMap.keySet();
    }

    @Override
    public byte getId() {
        return 1;
    }

    public INBTType<CompoundNBT> getType() {
        return TYPE;
    }

    public int size() {
        return this.tagMap.size();
    }

    @Nullable
    public INBT put(String string, INBT iNBT) {
        return this.tagMap.put(string, iNBT);
    }

    public void putByte(String string, byte by) {
        this.tagMap.put(string, ByteNBT.valueOf(by));
    }

    public void putShort(String string, short s) {
        this.tagMap.put(string, ShortNBT.valueOf(s));
    }

    public void putInt(String string, int n) {
        this.tagMap.put(string, IntNBT.valueOf(n));
    }

    public void putLong(String string, long l) {
        this.tagMap.put(string, LongNBT.valueOf(l));
    }

    public void putUniqueId(String string, UUID uUID) {
        this.tagMap.put(string, NBTUtil.func_240626_a_(uUID));
    }

    public UUID getUniqueId(String string) {
        return NBTUtil.readUniqueId(this.get(string));
    }

    public boolean hasUniqueId(String string) {
        INBT iNBT = this.get(string);
        return iNBT != null && iNBT.getType() == IntArrayNBT.TYPE && ((IntArrayNBT)iNBT).getIntArray().length == 4;
    }

    public void putFloat(String string, float f) {
        this.tagMap.put(string, FloatNBT.valueOf(f));
    }

    public void putDouble(String string, double d) {
        this.tagMap.put(string, DoubleNBT.valueOf(d));
    }

    public void putString(String string, String string2) {
        this.tagMap.put(string, StringNBT.valueOf(string2));
    }

    public void putByteArray(String string, byte[] byArray) {
        this.tagMap.put(string, new ByteArrayNBT(byArray));
    }

    public void putIntArray(String string, int[] nArray) {
        this.tagMap.put(string, new IntArrayNBT(nArray));
    }

    public void putIntArray(String string, List<Integer> list) {
        this.tagMap.put(string, new IntArrayNBT(list));
    }

    public void putLongArray(String string, long[] lArray) {
        this.tagMap.put(string, new LongArrayNBT(lArray));
    }

    public void putLongArray(String string, List<Long> list) {
        this.tagMap.put(string, new LongArrayNBT(list));
    }

    public void putBoolean(String string, boolean bl) {
        this.tagMap.put(string, ByteNBT.valueOf(bl));
    }

    @Nullable
    public INBT get(String string) {
        return this.tagMap.get(string);
    }

    public byte getTagId(String string) {
        INBT iNBT = this.tagMap.get(string);
        return iNBT == null ? (byte)0 : iNBT.getId();
    }

    public boolean contains(String string) {
        return this.tagMap.containsKey(string);
    }

    public boolean contains(String string, int n) {
        byte by = this.getTagId(string);
        if (by == n) {
            return false;
        }
        if (n != 99) {
            return true;
        }
        return by == 1 || by == 2 || by == 3 || by == 4 || by == 5 || by == 6;
    }

    public byte getByte(String string) {
        try {
            if (this.contains(string, 0)) {
                return ((NumberNBT)this.tagMap.get(string)).getByte();
            }
        } catch (ClassCastException classCastException) {
            // empty catch block
        }
        return 1;
    }

    public short getShort(String string) {
        try {
            if (this.contains(string, 0)) {
                return ((NumberNBT)this.tagMap.get(string)).getShort();
            }
        } catch (ClassCastException classCastException) {
            // empty catch block
        }
        return 1;
    }

    public int getInt(String string) {
        try {
            if (this.contains(string, 0)) {
                return ((NumberNBT)this.tagMap.get(string)).getInt();
            }
        } catch (ClassCastException classCastException) {
            // empty catch block
        }
        return 1;
    }

    public long getLong(String string) {
        try {
            if (this.contains(string, 0)) {
                return ((NumberNBT)this.tagMap.get(string)).getLong();
            }
        } catch (ClassCastException classCastException) {
            // empty catch block
        }
        return 0L;
    }

    public float getFloat(String string) {
        try {
            if (this.contains(string, 0)) {
                return ((NumberNBT)this.tagMap.get(string)).getFloat();
            }
        } catch (ClassCastException classCastException) {
            // empty catch block
        }
        return 0.0f;
    }

    public double getDouble(String string) {
        try {
            if (this.contains(string, 0)) {
                return ((NumberNBT)this.tagMap.get(string)).getDouble();
            }
        } catch (ClassCastException classCastException) {
            // empty catch block
        }
        return 0.0;
    }

    public String getString(String string) {
        try {
            if (this.contains(string, 1)) {
                return this.tagMap.get(string).getString();
            }
        } catch (ClassCastException classCastException) {
            // empty catch block
        }
        return "";
    }

    public byte[] getByteArray(String string) {
        try {
            if (this.contains(string, 0)) {
                return ((ByteArrayNBT)this.tagMap.get(string)).getByteArray();
            }
        } catch (ClassCastException classCastException) {
            throw new ReportedException(this.generateCrashReport(string, ByteArrayNBT.TYPE, classCastException));
        }
        return new byte[0];
    }

    public int[] getIntArray(String string) {
        try {
            if (this.contains(string, 0)) {
                return ((IntArrayNBT)this.tagMap.get(string)).getIntArray();
            }
        } catch (ClassCastException classCastException) {
            throw new ReportedException(this.generateCrashReport(string, IntArrayNBT.TYPE, classCastException));
        }
        return new int[0];
    }

    public long[] getLongArray(String string) {
        try {
            if (this.contains(string, 1)) {
                return ((LongArrayNBT)this.tagMap.get(string)).getAsLongArray();
            }
        } catch (ClassCastException classCastException) {
            throw new ReportedException(this.generateCrashReport(string, LongArrayNBT.TYPE, classCastException));
        }
        return new long[0];
    }

    public CompoundNBT getCompound(String string) {
        try {
            if (this.contains(string, 1)) {
                return (CompoundNBT)this.tagMap.get(string);
            }
        } catch (ClassCastException classCastException) {
            throw new ReportedException(this.generateCrashReport(string, TYPE, classCastException));
        }
        return new CompoundNBT();
    }

    public ListNBT getList(String string, int n) {
        try {
            if (this.getTagId(string) == 9) {
                ListNBT listNBT = (ListNBT)this.tagMap.get(string);
                if (!listNBT.isEmpty() && listNBT.getTagType() != n) {
                    return new ListNBT();
                }
                return listNBT;
            }
        } catch (ClassCastException classCastException) {
            throw new ReportedException(this.generateCrashReport(string, ListNBT.TYPE, classCastException));
        }
        return new ListNBT();
    }

    public boolean getBoolean(String string) {
        return this.getByte(string) != 0;
    }

    public void remove(String string) {
        this.tagMap.remove(string);
    }

    @Override
    public String toString() {
        Object object;
        StringBuilder stringBuilder = new StringBuilder("{");
        Object object2 = this.tagMap.keySet();
        if (LOGGER.isDebugEnabled()) {
            object = Lists.newArrayList(this.tagMap.keySet());
            Collections.sort(object);
            object2 = object;
        }
        object = object2.iterator();
        while (object.hasNext()) {
            String string = (String)object.next();
            if (stringBuilder.length() != 1) {
                stringBuilder.append(',');
            }
            stringBuilder.append(CompoundNBT.handleEscape(string)).append(':').append(this.tagMap.get(string));
        }
        return stringBuilder.append('}').toString();
    }

    public boolean isEmpty() {
        return this.tagMap.isEmpty();
    }

    private CrashReport generateCrashReport(String string, INBTType<?> iNBTType, ClassCastException classCastException) {
        CrashReport crashReport = CrashReport.makeCrashReport(classCastException, "Reading NBT data");
        CrashReportCategory crashReportCategory = crashReport.makeCategoryDepth("Corrupt NBT tag", 1);
        crashReportCategory.addDetail("Tag type found", () -> this.lambda$generateCrashReport$2(string));
        crashReportCategory.addDetail("Tag type expected", iNBTType::getName);
        crashReportCategory.addDetail("Tag name", string);
        return crashReport;
    }

    @Override
    public CompoundNBT copy() {
        HashMap<String, INBT> hashMap = Maps.newHashMap(Maps.transformValues(this.tagMap, INBT::copy));
        return new CompoundNBT(hashMap);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        return object instanceof CompoundNBT && Objects.equals(this.tagMap, ((CompoundNBT)object).tagMap);
    }

    public int hashCode() {
        return this.tagMap.hashCode();
    }

    private static void writeEntry(String string, INBT iNBT, DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(iNBT.getId());
        if (iNBT.getId() != 0) {
            dataOutput.writeUTF(string);
            iNBT.write(dataOutput);
        }
    }

    private static byte readType(DataInput dataInput, NBTSizeTracker nBTSizeTracker) throws IOException {
        return dataInput.readByte();
    }

    private static String readKey(DataInput dataInput, NBTSizeTracker nBTSizeTracker) throws IOException {
        return dataInput.readUTF();
    }

    private static INBT loadNBT(INBTType<?> iNBTType, String string, DataInput dataInput, int n, NBTSizeTracker nBTSizeTracker) {
        try {
            return iNBTType.readNBT(dataInput, n, nBTSizeTracker);
        } catch (IOException iOException) {
            CrashReport crashReport = CrashReport.makeCrashReport(iOException, "Loading NBT data");
            CrashReportCategory crashReportCategory = crashReport.makeCategory("NBT Tag");
            crashReportCategory.addDetail("Tag name", string);
            crashReportCategory.addDetail("Tag type", iNBTType.getName());
            throw new ReportedException(crashReport);
        }
    }

    public CompoundNBT merge(CompoundNBT compoundNBT) {
        for (String string : compoundNBT.tagMap.keySet()) {
            INBT iNBT = compoundNBT.tagMap.get(string);
            if (iNBT.getId() == 10) {
                if (this.contains(string, 1)) {
                    CompoundNBT compoundNBT2 = this.getCompound(string);
                    compoundNBT2.merge((CompoundNBT)iNBT);
                    continue;
                }
                this.put(string, iNBT.copy());
                continue;
            }
            this.put(string, iNBT.copy());
        }
        return this;
    }

    protected static String handleEscape(String string) {
        return SIMPLE_VALUE.matcher(string).matches() ? string : StringNBT.quoteAndEscape(string);
    }

    protected static ITextComponent getNameComponent(String string) {
        if (SIMPLE_VALUE.matcher(string).matches()) {
            return new StringTextComponent(string).mergeStyle(SYNTAX_HIGHLIGHTING_KEY);
        }
        String string2 = StringNBT.quoteAndEscape(string);
        String string3 = string2.substring(0, 1);
        IFormattableTextComponent iFormattableTextComponent = new StringTextComponent(string2.substring(1, string2.length() - 1)).mergeStyle(SYNTAX_HIGHLIGHTING_KEY);
        return new StringTextComponent(string3).append(iFormattableTextComponent).appendString(string3);
    }

    @Override
    public ITextComponent toFormattedComponent(String string, int n) {
        Object object;
        if (this.tagMap.isEmpty()) {
            return new StringTextComponent("{}");
        }
        StringTextComponent stringTextComponent = new StringTextComponent("{");
        Collection<String> collection = this.tagMap.keySet();
        if (LOGGER.isDebugEnabled()) {
            object = Lists.newArrayList(this.tagMap.keySet());
            Collections.sort(object);
            collection = object;
        }
        if (!string.isEmpty()) {
            stringTextComponent.appendString("\n");
        }
        Iterator iterator2 = collection.iterator();
        while (iterator2.hasNext()) {
            String string2 = (String)iterator2.next();
            object = new StringTextComponent(Strings.repeat(string, n + 1)).append(CompoundNBT.getNameComponent(string2)).appendString(String.valueOf(':')).appendString(" ").append(this.tagMap.get(string2).toFormattedComponent(string, n + 1));
            if (iterator2.hasNext()) {
                object.appendString(String.valueOf(',')).appendString(string.isEmpty() ? " " : "\n");
            }
            stringTextComponent.append((ITextComponent)object);
        }
        if (!string.isEmpty()) {
            stringTextComponent.appendString("\n").appendString(Strings.repeat(string, n));
        }
        stringTextComponent.appendString("}");
        return stringTextComponent;
    }

    protected Map<String, INBT> getTagMap() {
        return Collections.unmodifiableMap(this.tagMap);
    }

    @Override
    public INBT copy() {
        return this.copy();
    }

    private String lambda$generateCrashReport$2(String string) throws Exception {
        return this.tagMap.get(string).getType().getName();
    }

    private static Dynamic lambda$static$1(CompoundNBT compoundNBT) {
        return new Dynamic<CompoundNBT>(NBTDynamicOps.INSTANCE, compoundNBT);
    }

    private static DataResult lambda$static$0(Dynamic dynamic) {
        INBT iNBT = dynamic.convert(NBTDynamicOps.INSTANCE).getValue();
        return iNBT instanceof CompoundNBT ? DataResult.success((CompoundNBT)iNBT) : DataResult.error("Not a compound tag: " + iNBT);
    }
}

