/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.stb;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.stb.LibSTB;
import org.lwjgl.stb.STBVorbisAlloc;
import org.lwjgl.stb.STBVorbisInfo;
import org.lwjgl.system.Checks;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class STBVorbis {
    public static final int VORBIS__no_error = 0;
    public static final int VORBIS_need_more_data = 1;
    public static final int VORBIS_invalid_api_mixing = 2;
    public static final int VORBIS_outofmem = 3;
    public static final int VORBIS_feature_not_supported = 4;
    public static final int VORBIS_too_many_channels = 5;
    public static final int VORBIS_file_open_failure = 6;
    public static final int VORBIS_seek_without_length = 7;
    public static final int VORBIS_unexpected_eof = 10;
    public static final int VORBIS_seek_invalid = 11;
    public static final int VORBIS_invalid_setup = 20;
    public static final int VORBIS_invalid_stream = 21;
    public static final int VORBIS_missing_capture_pattern = 30;
    public static final int VORBIS_invalid_stream_structure_version = 31;
    public static final int VORBIS_continued_packet_flag_invalid = 32;
    public static final int VORBIS_incorrect_stream_serial_number = 33;
    public static final int VORBIS_invalid_first_page = 34;
    public static final int VORBIS_bad_packet_type = 35;
    public static final int VORBIS_cant_find_last_page = 36;
    public static final int VORBIS_seek_failed = 37;
    public static final int VORBIS_ogg_skeleton_not_supported = 38;

    protected STBVorbis() {
        throw new UnsupportedOperationException();
    }

    public static native void nstb_vorbis_get_info(long var0, long var2);

    @NativeType(value="stb_vorbis_info")
    public static STBVorbisInfo stb_vorbis_get_info(@NativeType(value="stb_vorbis *") long l, @NativeType(value="stb_vorbis_info") STBVorbisInfo sTBVorbisInfo) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        STBVorbis.nstb_vorbis_get_info(l, sTBVorbisInfo.address());
        return sTBVorbisInfo;
    }

    public static native int nstb_vorbis_get_error(long var0);

    public static int stb_vorbis_get_error(@NativeType(value="stb_vorbis *") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return STBVorbis.nstb_vorbis_get_error(l);
    }

    public static native void nstb_vorbis_close(long var0);

    public static void stb_vorbis_close(@NativeType(value="stb_vorbis *") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        STBVorbis.nstb_vorbis_close(l);
    }

    public static native int nstb_vorbis_get_sample_offset(long var0);

    public static int stb_vorbis_get_sample_offset(@NativeType(value="stb_vorbis *") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return STBVorbis.nstb_vorbis_get_sample_offset(l);
    }

    public static native int nstb_vorbis_get_file_offset(long var0);

    @NativeType(value="unsigned int")
    public static int stb_vorbis_get_file_offset(@NativeType(value="stb_vorbis *") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return STBVorbis.nstb_vorbis_get_file_offset(l);
    }

    public static native long nstb_vorbis_open_pushdata(long var0, int var2, long var3, long var5, long var7);

    @NativeType(value="stb_vorbis *")
    public static long stb_vorbis_open_pushdata(@NativeType(value="unsigned char const *") ByteBuffer byteBuffer, @NativeType(value="int *") IntBuffer intBuffer, @NativeType(value="int *") IntBuffer intBuffer2, @Nullable @NativeType(value="stb_vorbis_alloc const *") STBVorbisAlloc sTBVorbisAlloc) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
            if (sTBVorbisAlloc != null) {
                STBVorbisAlloc.validate(sTBVorbisAlloc.address());
            }
        }
        return STBVorbis.nstb_vorbis_open_pushdata(MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining(), MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddressSafe(sTBVorbisAlloc));
    }

    public static native int nstb_vorbis_decode_frame_pushdata(long var0, long var2, int var4, long var5, long var7, long var9);

    public static int stb_vorbis_decode_frame_pushdata(@NativeType(value="stb_vorbis *") long l, @NativeType(value="unsigned char const *") ByteBuffer byteBuffer, @Nullable @NativeType(value="int *") IntBuffer intBuffer, @NativeType(value="float ***") PointerBuffer pointerBuffer, @NativeType(value="int *") IntBuffer intBuffer2) {
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkSafe((Buffer)intBuffer, 1);
            Checks.check(pointerBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
        }
        return STBVorbis.nstb_vorbis_decode_frame_pushdata(l, MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining(), MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddress(pointerBuffer), MemoryUtil.memAddress(intBuffer2));
    }

    public static native void nstb_vorbis_flush_pushdata(long var0);

    public static void stb_vorbis_flush_pushdata(@NativeType(value="stb_vorbis *") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        STBVorbis.nstb_vorbis_flush_pushdata(l);
    }

    public static native int nstb_vorbis_decode_filename(long var0, long var2, long var4, long var6);

    public static int stb_vorbis_decode_filename(@NativeType(value="char const *") ByteBuffer byteBuffer, @NativeType(value="int *") IntBuffer intBuffer, @NativeType(value="int *") IntBuffer intBuffer2, @NativeType(value="short **") PointerBuffer pointerBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
            Checks.check(pointerBuffer, 1);
        }
        return STBVorbis.nstb_vorbis_decode_filename(MemoryUtil.memAddress(byteBuffer), MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(pointerBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int stb_vorbis_decode_filename(@NativeType(value="char const *") CharSequence charSequence, @NativeType(value="int *") IntBuffer intBuffer, @NativeType(value="int *") IntBuffer intBuffer2, @NativeType(value="short **") PointerBuffer pointerBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
            Checks.check(pointerBuffer, 1);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l = memoryStack.getPointerAddress();
            int n2 = STBVorbis.nstb_vorbis_decode_filename(l, MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(pointerBuffer));
            return n2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @NativeType(value="int")
    public static ShortBuffer stb_vorbis_decode_filename(@NativeType(value="char const *") CharSequence charSequence, @NativeType(value="int *") IntBuffer intBuffer, @NativeType(value="int *") IntBuffer intBuffer2) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l = memoryStack.getPointerAddress();
            PointerBuffer pointerBuffer = memoryStack.pointers(0L);
            int n2 = STBVorbis.nstb_vorbis_decode_filename(l, MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(pointerBuffer));
            ShortBuffer shortBuffer = MemoryUtil.memShortBufferSafe(pointerBuffer.get(0), n2 * intBuffer.get(0));
            return shortBuffer;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native int nstb_vorbis_decode_memory(long var0, int var2, long var3, long var5, long var7);

    public static int stb_vorbis_decode_memory(@NativeType(value="unsigned char const *") ByteBuffer byteBuffer, @NativeType(value="int *") IntBuffer intBuffer, @NativeType(value="int *") IntBuffer intBuffer2, @NativeType(value="short **") PointerBuffer pointerBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
            Checks.check(pointerBuffer, 1);
        }
        return STBVorbis.nstb_vorbis_decode_memory(MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining(), MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(pointerBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @NativeType(value="int")
    public static ShortBuffer stb_vorbis_decode_memory(@NativeType(value="unsigned char const *") ByteBuffer byteBuffer, @NativeType(value="int *") IntBuffer intBuffer, @NativeType(value="int *") IntBuffer intBuffer2) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            PointerBuffer pointerBuffer = memoryStack.pointers(0L);
            int n2 = STBVorbis.nstb_vorbis_decode_memory(MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining(), MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(pointerBuffer));
            ShortBuffer shortBuffer = MemoryUtil.memShortBufferSafe(pointerBuffer.get(0), n2 * intBuffer.get(0));
            return shortBuffer;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native long nstb_vorbis_open_memory(long var0, int var2, long var3, long var5);

    @NativeType(value="stb_vorbis *")
    public static long stb_vorbis_open_memory(@NativeType(value="unsigned char const *") ByteBuffer byteBuffer, @NativeType(value="int *") IntBuffer intBuffer, @Nullable @NativeType(value="stb_vorbis_alloc const *") STBVorbisAlloc sTBVorbisAlloc) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            if (sTBVorbisAlloc != null) {
                STBVorbisAlloc.validate(sTBVorbisAlloc.address());
            }
        }
        return STBVorbis.nstb_vorbis_open_memory(MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining(), MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddressSafe(sTBVorbisAlloc));
    }

    public static native long nstb_vorbis_open_filename(long var0, long var2, long var4);

    @NativeType(value="stb_vorbis *")
    public static long stb_vorbis_open_filename(@NativeType(value="char const *") ByteBuffer byteBuffer, @NativeType(value="int *") IntBuffer intBuffer, @Nullable @NativeType(value="stb_vorbis_alloc const *") STBVorbisAlloc sTBVorbisAlloc) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
            Checks.check((Buffer)intBuffer, 1);
            if (sTBVorbisAlloc != null) {
                STBVorbisAlloc.validate(sTBVorbisAlloc.address());
            }
        }
        return STBVorbis.nstb_vorbis_open_filename(MemoryUtil.memAddress(byteBuffer), MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddressSafe(sTBVorbisAlloc));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="stb_vorbis *")
    public static long stb_vorbis_open_filename(@NativeType(value="char const *") CharSequence charSequence, @NativeType(value="int *") IntBuffer intBuffer, @Nullable @NativeType(value="stb_vorbis_alloc const *") STBVorbisAlloc sTBVorbisAlloc) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            if (sTBVorbisAlloc != null) {
                STBVorbisAlloc.validate(sTBVorbisAlloc.address());
            }
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l = memoryStack.getPointerAddress();
            long l2 = STBVorbis.nstb_vorbis_open_filename(l, MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddressSafe(sTBVorbisAlloc));
            return l2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native int nstb_vorbis_seek_frame(long var0, int var2);

    @NativeType(value="int")
    public static boolean stb_vorbis_seek_frame(@NativeType(value="stb_vorbis *") long l, @NativeType(value="unsigned int") int n) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return STBVorbis.nstb_vorbis_seek_frame(l, n) != 0;
    }

    public static native int nstb_vorbis_seek(long var0, int var2);

    @NativeType(value="int")
    public static boolean stb_vorbis_seek(@NativeType(value="stb_vorbis *") long l, @NativeType(value="unsigned int") int n) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return STBVorbis.nstb_vorbis_seek(l, n) != 0;
    }

    public static native int nstb_vorbis_seek_start(long var0);

    @NativeType(value="int")
    public static boolean stb_vorbis_seek_start(@NativeType(value="stb_vorbis *") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return STBVorbis.nstb_vorbis_seek_start(l) != 0;
    }

    public static native int nstb_vorbis_stream_length_in_samples(long var0);

    @NativeType(value="unsigned int")
    public static int stb_vorbis_stream_length_in_samples(@NativeType(value="stb_vorbis *") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return STBVorbis.nstb_vorbis_stream_length_in_samples(l);
    }

    public static native float nstb_vorbis_stream_length_in_seconds(long var0);

    public static float stb_vorbis_stream_length_in_seconds(@NativeType(value="stb_vorbis *") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return STBVorbis.nstb_vorbis_stream_length_in_seconds(l);
    }

    public static native int nstb_vorbis_get_frame_float(long var0, long var2, long var4);

    public static int stb_vorbis_get_frame_float(@NativeType(value="stb_vorbis *") long l, @Nullable @NativeType(value="int *") IntBuffer intBuffer, @NativeType(value="float ***") PointerBuffer pointerBuffer) {
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkSafe((Buffer)intBuffer, 1);
            Checks.check(pointerBuffer, 1);
        }
        return STBVorbis.nstb_vorbis_get_frame_float(l, MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddress(pointerBuffer));
    }

    public static native int nstb_vorbis_get_frame_short(long var0, int var2, long var3, int var5);

    public static int stb_vorbis_get_frame_short(@NativeType(value="stb_vorbis *") long l, @NativeType(value="short **") PointerBuffer pointerBuffer, int n) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return STBVorbis.nstb_vorbis_get_frame_short(l, pointerBuffer.remaining(), MemoryUtil.memAddress(pointerBuffer), n);
    }

    public static native int nstb_vorbis_get_frame_short_interleaved(long var0, int var2, long var3, int var5);

    public static int stb_vorbis_get_frame_short_interleaved(@NativeType(value="stb_vorbis *") long l, int n, @NativeType(value="short *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return STBVorbis.nstb_vorbis_get_frame_short_interleaved(l, n, MemoryUtil.memAddress(shortBuffer), shortBuffer.remaining());
    }

    public static native int nstb_vorbis_get_samples_float(long var0, int var2, long var3, int var5);

    public static int stb_vorbis_get_samples_float(@NativeType(value="stb_vorbis *") long l, @NativeType(value="float **") PointerBuffer pointerBuffer, int n) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return STBVorbis.nstb_vorbis_get_samples_float(l, pointerBuffer.remaining(), MemoryUtil.memAddress(pointerBuffer), n);
    }

    public static native int nstb_vorbis_get_samples_float_interleaved(long var0, int var2, long var3, int var5);

    public static int stb_vorbis_get_samples_float_interleaved(@NativeType(value="stb_vorbis *") long l, int n, @NativeType(value="float *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return STBVorbis.nstb_vorbis_get_samples_float_interleaved(l, n, MemoryUtil.memAddress(floatBuffer), floatBuffer.remaining());
    }

    public static native int nstb_vorbis_get_samples_short(long var0, int var2, long var3, int var5);

    public static int stb_vorbis_get_samples_short(@NativeType(value="stb_vorbis *") long l, @NativeType(value="short **") PointerBuffer pointerBuffer, int n) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return STBVorbis.nstb_vorbis_get_samples_short(l, pointerBuffer.remaining(), MemoryUtil.memAddress(pointerBuffer), n);
    }

    public static native int nstb_vorbis_get_samples_short_interleaved(long var0, int var2, long var3, int var5);

    public static int stb_vorbis_get_samples_short_interleaved(@NativeType(value="stb_vorbis *") long l, int n, @NativeType(value="short *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return STBVorbis.nstb_vorbis_get_samples_short_interleaved(l, n, MemoryUtil.memAddress(shortBuffer), shortBuffer.remaining());
    }

    public static native long nstb_vorbis_open_pushdata(long var0, int var2, int[] var3, int[] var4, long var5);

    @NativeType(value="stb_vorbis *")
    public static long stb_vorbis_open_pushdata(@NativeType(value="unsigned char const *") ByteBuffer byteBuffer, @NativeType(value="int *") int[] nArray, @NativeType(value="int *") int[] nArray2, @Nullable @NativeType(value="stb_vorbis_alloc const *") STBVorbisAlloc sTBVorbisAlloc) {
        if (Checks.CHECKS) {
            Checks.check(nArray, 1);
            Checks.check(nArray2, 1);
            if (sTBVorbisAlloc != null) {
                STBVorbisAlloc.validate(sTBVorbisAlloc.address());
            }
        }
        return STBVorbis.nstb_vorbis_open_pushdata(MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining(), nArray, nArray2, MemoryUtil.memAddressSafe(sTBVorbisAlloc));
    }

    public static native int nstb_vorbis_decode_frame_pushdata(long var0, long var2, int var4, int[] var5, long var6, int[] var8);

    public static int stb_vorbis_decode_frame_pushdata(@NativeType(value="stb_vorbis *") long l, @NativeType(value="unsigned char const *") ByteBuffer byteBuffer, @Nullable @NativeType(value="int *") int[] nArray, @NativeType(value="float ***") PointerBuffer pointerBuffer, @NativeType(value="int *") int[] nArray2) {
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkSafe(nArray, 1);
            Checks.check(pointerBuffer, 1);
            Checks.check(nArray2, 1);
        }
        return STBVorbis.nstb_vorbis_decode_frame_pushdata(l, MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining(), nArray, MemoryUtil.memAddress(pointerBuffer), nArray2);
    }

    public static native int nstb_vorbis_decode_filename(long var0, int[] var2, int[] var3, long var4);

    public static int stb_vorbis_decode_filename(@NativeType(value="char const *") ByteBuffer byteBuffer, @NativeType(value="int *") int[] nArray, @NativeType(value="int *") int[] nArray2, @NativeType(value="short **") PointerBuffer pointerBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
            Checks.check(nArray, 1);
            Checks.check(nArray2, 1);
            Checks.check(pointerBuffer, 1);
        }
        return STBVorbis.nstb_vorbis_decode_filename(MemoryUtil.memAddress(byteBuffer), nArray, nArray2, MemoryUtil.memAddress(pointerBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int stb_vorbis_decode_filename(@NativeType(value="char const *") CharSequence charSequence, @NativeType(value="int *") int[] nArray, @NativeType(value="int *") int[] nArray2, @NativeType(value="short **") PointerBuffer pointerBuffer) {
        if (Checks.CHECKS) {
            Checks.check(nArray, 1);
            Checks.check(nArray2, 1);
            Checks.check(pointerBuffer, 1);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l = memoryStack.getPointerAddress();
            int n2 = STBVorbis.nstb_vorbis_decode_filename(l, nArray, nArray2, MemoryUtil.memAddress(pointerBuffer));
            return n2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native int nstb_vorbis_decode_memory(long var0, int var2, int[] var3, int[] var4, long var5);

    public static int stb_vorbis_decode_memory(@NativeType(value="unsigned char const *") ByteBuffer byteBuffer, @NativeType(value="int *") int[] nArray, @NativeType(value="int *") int[] nArray2, @NativeType(value="short **") PointerBuffer pointerBuffer) {
        if (Checks.CHECKS) {
            Checks.check(nArray, 1);
            Checks.check(nArray2, 1);
            Checks.check(pointerBuffer, 1);
        }
        return STBVorbis.nstb_vorbis_decode_memory(MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining(), nArray, nArray2, MemoryUtil.memAddress(pointerBuffer));
    }

    public static native long nstb_vorbis_open_memory(long var0, int var2, int[] var3, long var4);

    @NativeType(value="stb_vorbis *")
    public static long stb_vorbis_open_memory(@NativeType(value="unsigned char const *") ByteBuffer byteBuffer, @NativeType(value="int *") int[] nArray, @Nullable @NativeType(value="stb_vorbis_alloc const *") STBVorbisAlloc sTBVorbisAlloc) {
        if (Checks.CHECKS) {
            Checks.check(nArray, 1);
            if (sTBVorbisAlloc != null) {
                STBVorbisAlloc.validate(sTBVorbisAlloc.address());
            }
        }
        return STBVorbis.nstb_vorbis_open_memory(MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining(), nArray, MemoryUtil.memAddressSafe(sTBVorbisAlloc));
    }

    public static native long nstb_vorbis_open_filename(long var0, int[] var2, long var3);

    @NativeType(value="stb_vorbis *")
    public static long stb_vorbis_open_filename(@NativeType(value="char const *") ByteBuffer byteBuffer, @NativeType(value="int *") int[] nArray, @Nullable @NativeType(value="stb_vorbis_alloc const *") STBVorbisAlloc sTBVorbisAlloc) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
            Checks.check(nArray, 1);
            if (sTBVorbisAlloc != null) {
                STBVorbisAlloc.validate(sTBVorbisAlloc.address());
            }
        }
        return STBVorbis.nstb_vorbis_open_filename(MemoryUtil.memAddress(byteBuffer), nArray, MemoryUtil.memAddressSafe(sTBVorbisAlloc));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="stb_vorbis *")
    public static long stb_vorbis_open_filename(@NativeType(value="char const *") CharSequence charSequence, @NativeType(value="int *") int[] nArray, @Nullable @NativeType(value="stb_vorbis_alloc const *") STBVorbisAlloc sTBVorbisAlloc) {
        if (Checks.CHECKS) {
            Checks.check(nArray, 1);
            if (sTBVorbisAlloc != null) {
                STBVorbisAlloc.validate(sTBVorbisAlloc.address());
            }
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l = memoryStack.getPointerAddress();
            long l2 = STBVorbis.nstb_vorbis_open_filename(l, nArray, MemoryUtil.memAddressSafe(sTBVorbisAlloc));
            return l2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native int nstb_vorbis_get_frame_float(long var0, int[] var2, long var3);

    public static int stb_vorbis_get_frame_float(@NativeType(value="stb_vorbis *") long l, @Nullable @NativeType(value="int *") int[] nArray, @NativeType(value="float ***") PointerBuffer pointerBuffer) {
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkSafe(nArray, 1);
            Checks.check(pointerBuffer, 1);
        }
        return STBVorbis.nstb_vorbis_get_frame_float(l, nArray, MemoryUtil.memAddress(pointerBuffer));
    }

    public static native int nstb_vorbis_get_frame_short_interleaved(long var0, int var2, short[] var3, int var4);

    public static int stb_vorbis_get_frame_short_interleaved(@NativeType(value="stb_vorbis *") long l, int n, @NativeType(value="short *") short[] sArray) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return STBVorbis.nstb_vorbis_get_frame_short_interleaved(l, n, sArray, sArray.length);
    }

    public static native int nstb_vorbis_get_samples_float_interleaved(long var0, int var2, float[] var3, int var4);

    public static int stb_vorbis_get_samples_float_interleaved(@NativeType(value="stb_vorbis *") long l, int n, @NativeType(value="float *") float[] fArray) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return STBVorbis.nstb_vorbis_get_samples_float_interleaved(l, n, fArray, fArray.length);
    }

    public static native int nstb_vorbis_get_samples_short_interleaved(long var0, int var2, short[] var3, int var4);

    public static int stb_vorbis_get_samples_short_interleaved(@NativeType(value="stb_vorbis *") long l, int n, @NativeType(value="short *") short[] sArray) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return STBVorbis.nstb_vorbis_get_samples_short_interleaved(l, n, sArray, sArray.length);
    }

    static {
        LibSTB.initialize();
    }
}

