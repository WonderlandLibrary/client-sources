/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.stb;

import org.lwjgl.stb.LibSTB;
import org.lwjgl.stb.STBRPContext;
import org.lwjgl.stb.STBRPNode;
import org.lwjgl.stb.STBRPRect;
import org.lwjgl.system.NativeType;

public class STBRectPack {
    public static final int STBRP_HEURISTIC_Skyline_default = 0;
    public static final int STBRP_HEURISTIC_Skyline_BL_sortHeight = 0;
    public static final int STBRP_HEURISTIC_Skyline_BF_sortHeight = 1;

    protected STBRectPack() {
        throw new UnsupportedOperationException();
    }

    public static native int nstbrp_pack_rects(long var0, long var2, int var4);

    public static int stbrp_pack_rects(@NativeType(value="stbrp_context *") STBRPContext sTBRPContext, @NativeType(value="stbrp_rect *") STBRPRect.Buffer buffer) {
        return STBRectPack.nstbrp_pack_rects(sTBRPContext.address(), buffer.address(), buffer.remaining());
    }

    public static native void nstbrp_init_target(long var0, int var2, int var3, long var4, int var6);

    public static void stbrp_init_target(@NativeType(value="stbrp_context *") STBRPContext sTBRPContext, int n, int n2, @NativeType(value="stbrp_node *") STBRPNode.Buffer buffer) {
        STBRectPack.nstbrp_init_target(sTBRPContext.address(), n, n2, buffer.address(), buffer.remaining());
    }

    public static native void nstbrp_setup_allow_out_of_mem(long var0, int var2);

    public static void stbrp_setup_allow_out_of_mem(@NativeType(value="stbrp_context *") STBRPContext sTBRPContext, @NativeType(value="int") boolean bl) {
        STBRectPack.nstbrp_setup_allow_out_of_mem(sTBRPContext.address(), bl ? 1 : 0);
    }

    public static native void nstbrp_setup_heuristic(long var0, int var2);

    public static void stbrp_setup_heuristic(@NativeType(value="stbrp_context *") STBRPContext sTBRPContext, int n) {
        STBRectPack.nstbrp_setup_heuristic(sTBRPContext.address(), n);
    }

    static {
        LibSTB.initialize();
    }
}

