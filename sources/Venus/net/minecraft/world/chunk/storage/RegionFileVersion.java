/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.chunk.storage;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;
import javax.annotation.Nullable;

public class RegionFileVersion {
    private static final Int2ObjectMap<RegionFileVersion> field_227161_d_ = new Int2ObjectOpenHashMap<RegionFileVersion>();
    public static final RegionFileVersion field_227158_a_ = RegionFileVersion.func_227167_a_(new RegionFileVersion(1, GZIPInputStream::new, GZIPOutputStream::new));
    public static final RegionFileVersion field_227159_b_ = RegionFileVersion.func_227167_a_(new RegionFileVersion(2, InflaterInputStream::new, DeflaterOutputStream::new));
    public static final RegionFileVersion field_227160_c_ = RegionFileVersion.func_227167_a_(new RegionFileVersion(3, RegionFileVersion::lambda$static$0, RegionFileVersion::lambda$static$1));
    private final int field_227162_e_;
    private final IWrapper<InputStream> field_227163_f_;
    private final IWrapper<OutputStream> field_227164_g_;

    private RegionFileVersion(int n, IWrapper<InputStream> iWrapper, IWrapper<OutputStream> iWrapper2) {
        this.field_227162_e_ = n;
        this.field_227163_f_ = iWrapper;
        this.field_227164_g_ = iWrapper2;
    }

    private static RegionFileVersion func_227167_a_(RegionFileVersion regionFileVersion) {
        field_227161_d_.put(regionFileVersion.field_227162_e_, regionFileVersion);
        return regionFileVersion;
    }

    @Nullable
    public static RegionFileVersion func_227166_a_(int n) {
        return (RegionFileVersion)field_227161_d_.get(n);
    }

    public static boolean func_227170_b_(int n) {
        return field_227161_d_.containsKey(n);
    }

    public int func_227165_a_() {
        return this.field_227162_e_;
    }

    public OutputStream func_227169_a_(OutputStream outputStream) throws IOException {
        return this.field_227164_g_.wrap(outputStream);
    }

    public InputStream func_227168_a_(InputStream inputStream) throws IOException {
        return this.field_227163_f_.wrap(inputStream);
    }

    private static OutputStream lambda$static$1(OutputStream outputStream) throws IOException {
        return outputStream;
    }

    private static InputStream lambda$static$0(InputStream inputStream) throws IOException {
        return inputStream;
    }

    @FunctionalInterface
    static interface IWrapper<O> {
        public O wrap(O var1) throws IOException;
    }
}

