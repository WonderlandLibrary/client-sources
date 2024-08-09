/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system;

import javax.annotation.Nullable;
import org.lwjgl.system.Library;

public final class JNI {
    private JNI() {
    }

    public static native byte invokePB(byte var0, long var1, long var3);

    public static native double invokeD(long var0);

    public static native double invokeD(int var0, long var1);

    public static native double invokePD(long var0, long var2);

    public static native double invokePPD(long var0, long var2, long var4);

    public static native float invokeF(int var0, long var1);

    public static native float invokePF(long var0, long var2);

    public static native float invokePF(long var0, int var2, long var3);

    public static native float invokePF(long var0, float var2, float var3, long var4);

    public static native float invokePPF(long var0, long var2, long var4);

    public static native float invokePPF(long var0, int var2, long var3, long var5);

    public static native float invokePPF(long var0, float var2, long var3, int var5, long var6);

    public static native int invokeI(long var0);

    public static native int invokeI(int var0, long var1);

    public static native int invokeI(boolean var0, long var1);

    public static native int invokeI(int var0, int var1, long var2);

    public static native int invokeI(int var0, short var1, long var2);

    public static native int invokeI(int var0, int var1, int var2, long var3);

    public static native int invokePI(long var0, long var2);

    public static native int invokePI(int var0, long var1, long var3);

    public static native int invokePI(long var0, int var2, long var3);

    public static native int invokePI(long var0, short var2, long var3);

    public static native int invokePI(short var0, long var1, long var3);

    public static native int invokePI(long var0, int var2, int var3, long var4);

    public static native int invokePI(short var0, long var1, byte var3, long var4);

    public static native int invokePI(long var0, int var2, int var3, int var4, long var5);

    public static native int invokePJI(long var0, long var2, long var4);

    public static native int invokePPI(long var0, long var2, long var4);

    public static native int invokePPI(long var0, int var2, long var3, long var5);

    public static native int invokePPI(long var0, long var2, int var4, long var5);

    public static native int invokePPI(long var0, long var2, short var4, long var5);

    public static native int invokePPI(long var0, int var2, long var3, int var5, long var6);

    public static native int invokePPI(long var0, int var2, int var3, long var4, int var6, long var7);

    public static native int invokePPI(int var0, long var1, int var3, long var4, int var6, boolean var7, long var8);

    public static native int invokePPI(long var0, int var2, int var3, int var4, int var5, long var6, long var8);

    public static native int invokePPI(long var0, int var2, int var3, int var4, int var5, long var6, int var8, long var9);

    public static native int invokePPJI(long var0, long var2, long var4, long var6);

    public static native int invokePPPI(long var0, long var2, long var4, long var6);

    public static native int invokePPPI(long var0, int var2, long var3, long var5, long var7);

    public static native int invokePPPI(long var0, long var2, int var4, long var5, long var7);

    public static native int invokePPPI(long var0, long var2, long var4, int var6, long var7);

    public static native int invokePPPI(long var0, long var2, int var4, int var5, long var6, long var8);

    public static native int invokePPPI(long var0, long var2, int var4, long var5, int var7, long var8);

    public static native int invokePPPI(long var0, long var2, int var4, long var5, int var7, int var8, long var9);

    public static native int invokePPPI(long var0, int var2, int var3, int var4, long var5, long var7, int var9, long var10);

    public static native int invokePPPPI(long var0, long var2, long var4, long var6, long var8);

    public static native int invokePPPPI(long var0, long var2, int var4, long var5, long var7, long var9);

    public static native int invokePPPPI(long var0, long var2, long var4, int var6, long var7, long var9);

    public static native int invokePPPPI(long var0, long var2, long var4, long var6, int var8, long var9);

    public static native int invokePPPPI(long var0, long var2, int var4, int var5, long var6, long var8, long var10);

    public static native int invokePPPPI(long var0, long var2, long var4, int var6, int var7, long var8, long var10);

    public static native int invokePPPPI(long var0, int var2, int var3, long var4, long var6, long var8, int var10, long var11);

    public static native int invokePPPPPI(long var0, long var2, long var4, long var6, long var8, long var10);

    public static native int invokePJJJPI(long var0, long var2, long var4, long var6, int var8, long var9, long var11);

    public static native int invokePPPPPI(long var0, int var2, long var3, long var5, long var7, long var9, long var11);

    public static native int invokePPPPPI(long var0, long var2, long var4, int var6, long var7, int var9, int var10, long var11, long var13);

    public static native int invokePPPPPPI(long var0, long var2, long var4, long var6, long var8, long var10, long var12);

    public static native int invokePPPPPPI(long var0, long var2, long var4, long var6, long var8, long var10, int var12, long var13);

    public static native int invokePPPPPPPI(long var0, long var2, long var4, int var6, int var7, long var8, long var10, int var12, long var13, int var15, long var16, int var18, long var19);

    public static native int invokePPPPPPPPI(long var0, int var2, int var3, long var4, long var6, long var8, long var10, long var12, long var14, long var16, long var18);

    public static native long invokeJ(long var0);

    public static native long invokePJ(long var0, long var2);

    public static native long invokePJ(long var0, int var2, long var3);

    public static native long invokePJ(long var0, float var2, int var3, float var4, int var5, long var6);

    public static native long invokePPJ(long var0, long var2, long var4);

    public static native long invokePPJ(long var0, long var2, int var4, long var5);

    public static native long invokeP(long var0);

    public static native long invokeP(int var0, long var1);

    public static native long invokeP(short var0, long var1);

    public static native long invokeP(boolean var0, long var1);

    public static native long invokeP(int var0, int var1, long var2);

    public static native long invokePP(long var0, long var2);

    public static native long invokePP(int var0, long var1, long var3);

    public static native long invokePP(long var0, byte var2, long var3);

    public static native long invokePP(long var0, double var2, long var4);

    public static native long invokePP(long var0, int var2, long var3);

    public static native long invokePP(short var0, long var1, long var3);

    public static native long invokePP(int var0, int var1, long var2, long var4);

    public static native long invokePP(int var0, short var1, long var2, long var4);

    public static native long invokePP(long var0, int var2, int var3, long var4);

    public static native long invokePP(int var0, int var1, int var2, long var3, long var5);

    public static native long invokePP(long var0, int var2, int var3, int var4, long var5);

    public static native long invokeJP(short var0, short var1, short var2, byte var3, int var4, long var5, long var7);

    public static native long invokePP(long var0, int var2, byte var3, int var4, boolean var5, boolean var6, long var7);

    public static native long invokePJP(long var0, long var2, long var4);

    public static native long invokePPP(long var0, long var2, long var4);

    public static native long invokePJP(long var0, int var2, long var3, long var5);

    public static native long invokePJP(long var0, long var2, int var4, long var5);

    public static native long invokePPP(int var0, long var1, long var3, long var5);

    public static native long invokePPP(long var0, int var2, long var3, long var5);

    public static native long invokePPP(long var0, long var2, byte var4, long var5);

    public static native long invokePPP(long var0, long var2, int var4, long var5);

    public static native long invokePPP(long var0, int var2, int var3, long var4, long var6);

    public static native long invokePPP(long var0, int var2, long var3, int var5, long var6);

    public static native long invokePPP(long var0, long var2, int var4, byte var5, long var6);

    public static native long invokePPP(long var0, long var2, int var4, int var5, long var6);

    public static native long invokePPP(long var0, long var2, boolean var4, boolean var5, long var6);

    public static native long invokePPP(long var0, boolean var2, boolean var3, long var4, long var6);

    public static native long invokePPP(int var0, int var1, int var2, int var3, long var4, long var6, long var8);

    public static native long invokePPP(long var0, short var2, short var3, short var4, short var5, long var6, long var8);

    public static native long invokePPP(int var0, int var1, int var2, int var3, long var4, int var6, long var7, long var9);

    public static native long invokePJJP(long var0, long var2, long var4, long var6);

    public static native long invokePPJP(long var0, long var2, long var4, long var6);

    public static native long invokePPPP(long var0, long var2, long var4, long var6);

    public static native long invokePPPP(int var0, long var1, long var3, long var5, long var7);

    public static native long invokePPPP(long var0, int var2, long var3, long var5, long var7);

    public static native long invokePPPP(long var0, long var2, int var4, long var5, long var7);

    public static native long invokePPPP(long var0, long var2, long var4, int var6, long var7);

    public static native long invokePPPP(int var0, int var1, long var2, long var4, long var6, long var8);

    public static native long invokePPPP(long var0, int var2, int var3, long var4, long var6, long var8);

    public static native long invokePPPP(long var0, long var2, int var4, int var5, long var6, long var8);

    public static native long invokePPPP(long var0, long var2, int var4, long var5, int var7, long var8);

    public static native long invokePPPP(long var0, long var2, long var4, int var6, int var7, long var8);

    public static native long invokePPPP(long var0, int var2, long var3, long var5, int var7, int var8, long var9);

    public static native long invokePPPP(long var0, long var2, int var4, int var5, long var6, int var8, long var9);

    public static native long invokePPPP(int var0, int var1, int var2, long var3, long var5, int var7, long var8, long var10);

    public static native long invokePPPP(long var0, long var2, int var4, int var5, long var6, int var8, int var9, long var10);

    public static native long invokePPJPP(long var0, long var2, long var4, long var6, long var8);

    public static native long invokePPPPP(long var0, long var2, long var4, long var6, long var8);

    public static native long invokePPPJP(int var0, long var1, long var3, long var5, long var7, long var9);

    public static native long invokePPPPP(long var0, int var2, long var3, long var5, long var7, long var9);

    public static native long invokePPPPP(long var0, long var2, int var4, long var5, long var7, long var9);

    public static native long invokePPPPP(long var0, long var2, long var4, int var6, long var7, long var9);

    public static native long invokePPPPP(long var0, long var2, long var4, long var6, int var8, long var9);

    public static native long invokePJPPP(long var0, long var2, int var4, long var5, long var7, int var9, long var10);

    public static native long invokePPPJP(long var0, long var2, long var4, long var6, int var8, int var9, long var10);

    public static native long invokePPPPP(long var0, long var2, int var4, long var5, int var7, long var8, long var10);

    public static native long invokePPPPP(long var0, long var2, long var4, int var6, long var7, int var9, int var10, long var11);

    public static native long invokePPPPP(long var0, long var2, long var4, long var6, int var8, int var9, int var10, long var11);

    public static native long invokePPPPP(int var0, int var1, int var2, long var3, long var5, long var7, int var9, long var10, long var12);

    public static native long invokePJPJPP(long var0, long var2, long var4, long var6, long var8, long var10);

    public static native long invokePPPPPP(long var0, long var2, long var4, long var6, long var8, long var10);

    public static native long invokePPPPPP(long var0, long var2, long var4, int var6, long var7, long var9, long var11);

    public static native long invokePPPPPP(long var0, long var2, long var4, long var6, int var8, long var9, long var11);

    public static native long invokePPPPPP(long var0, long var2, long var4, long var6, long var8, int var10, long var11);

    public static native long invokePPJPPP(long var0, long var2, long var4, int var6, int var7, long var8, long var10, long var12);

    public static native long invokePPPPPP(long var0, long var2, long var4, long var6, long var8, int var10, int var11, int var12, long var13);

    public static native long invokePPPPPP(long var0, long var2, int var4, int var5, int var6, int var7, int var8, int var9, int var10, long var11, long var13, long var15, long var17);

    public static native long invokePPJJPPP(long var0, long var2, long var4, long var6, long var8, long var10, long var12);

    public static native long invokePPPPPPP(long var0, long var2, long var4, long var6, long var8, long var10, long var12);

    public static native long invokePPPPPPP(long var0, long var2, long var4, int var6, long var7, long var9, long var11, long var13);

    public static native long invokePPPPPPP(long var0, long var2, long var4, long var6, int var8, long var9, long var11, long var13);

    public static native long invokePPPPPPP(long var0, long var2, long var4, long var6, long var8, int var10, long var11, long var13);

    public static native long invokePPPPPPP(long var0, long var2, long var4, long var6, long var8, long var10, int var12, long var13);

    public static native long invokePPPPPPP(long var0, long var2, long var4, long var6, int var8, long var9, int var11, long var12, int var14, int var15, long var16);

    public static native long invokePPPPPPP(long var0, long var2, long var4, long var6, long var8, int var10, long var11, int var13, int var14, int var15, long var16);

    public static native long invokePPPPPPPP(long var0, long var2, long var4, long var6, int var8, long var9, long var11, long var13, long var15);

    public static native long invokePPPPPPPP(long var0, long var2, long var4, long var6, long var8, int var10, long var11, int var13, long var14, int var16, long var17);

    public static native long invokePPPPJJPPP(long var0, long var2, long var4, long var6, int var8, long var9, int var11, long var12, int var14, long var15, long var17, long var19);

    public static native long invokePPPPPJJPP(long var0, long var2, long var4, long var6, long var8, int var10, long var11, int var13, long var14, int var16, long var17, long var19);

    public static native long invokePPPPPJPPP(long var0, long var2, long var4, long var6, long var8, int var10, long var11, int var13, long var14, int var16, long var17, long var19);

    public static native long invokePPPPPJPPP(long var0, int var2, long var3, long var5, long var7, long var9, int var11, int var12, long var13, int var15, long var16, long var18, long var20);

    public static native long invokePPPPPJPPP(long var0, int var2, long var3, long var5, long var7, long var9, int var11, int var12, long var13, int var15, int var16, long var17, long var19, long var21);

    public static native long invokePPPPPPPPP(long var0, long var2, long var4, long var6, long var8, long var10, long var12, int var14, long var15, int var17, int var18, int var19, int var20, int var21, long var22);

    public static native long invokePPPPPPPPP(long var0, int var2, long var3, long var5, long var7, int var9, long var10, long var12, int var14, long var15, long var17, int var19, int var20, int var21, int var22, long var23);

    public static native long invokePPPPPJJJPP(long var0, long var2, long var4, long var6, long var8, int var10, long var11, long var13, long var15, int var17, long var18, long var20);

    public static native long invokePPPPPPPPPP(long var0, long var2, long var4, long var6, int var8, long var9, long var11, long var13, long var15, int var17, long var18, long var20);

    public static native long invokePPPPPPPPPP(long var0, long var2, long var4, long var6, long var8, long var10, long var12, int var14, long var15, int var17, long var18, int var20, long var21);

    public static native long invokePPPPPJPPPP(long var0, long var2, long var4, long var6, long var8, int var10, long var11, int var13, int var14, long var15, int var17, int var18, long var19, long var21, long var23);

    public static native long invokePPPPPPPPPPP(long var0, long var2, long var4, long var6, long var8, long var10, long var12, long var14, long var16, long var18, long var20);

    public static native long invokePPPPPPPPPPP(long var0, long var2, long var4, long var6, long var8, long var10, long var12, int var14, long var15, int var17, long var18, long var20, int var22, long var23);

    public static native long invokePPPPPJPPPPPP(long var0, long var2, long var4, long var6, long var8, int var10, long var11, int var13, int var14, long var15, long var17, int var19, int var20, long var21, long var23, long var25, long var27);

    public static native long invokePPPPPJJPPPPPPP(long var0, long var2, long var4, long var6, long var8, int var10, long var11, int var13, long var14, int var16, long var17, long var19, int var21, long var22, long var24, long var26, long var28, long var30);

    public static native short invokeS(long var0);

    public static native short invokeS(int var0, long var1);

    public static native short invokeS(int var0, short var1, long var2);

    public static native short invokeS(short var0, byte var1, long var2);

    public static native short invokeS(short var0, boolean var1, long var2);

    public static native short invokeS(short var0, short var1, boolean var2, long var3);

    public static native short invokeS(short var0, short var1, short var2, short var3, long var4);

    public static native short invokePS(long var0, long var2);

    public static native short invokePS(long var0, short var2, long var3);

    public static native short invokeJS(int var0, int var1, long var2, long var4);

    public static native short invokePS(byte var0, long var1, boolean var3, long var4);

    public static native short invokePS(int var0, long var1, short var3, long var4);

    public static native short invokePS(long var0, int var2, short var3, long var4);

    public static native short invokePS(short var0, long var1, short var3, long var4);

    public static native short invokeJS(short var0, short var1, int var2, long var3, long var5);

    public static native short invokeJS(int var0, boolean var1, short var2, int var3, long var4, long var6);

    public static native short invokePS(long var0, short var2, short var3, int var4, int var5, long var6);

    public static native short invokePS(long var0, short var2, short var3, short var4, short var5, long var6);

    public static native short invokePPS(long var0, long var2, short var4, long var5);

    public static native short invokeJPS(short var0, boolean var1, short var2, int var3, long var4, long var6, long var8);

    public static native short invokeJPS(short var0, short var1, short var2, boolean var3, int var4, long var5, long var7, long var9);

    public static native short invokeJPS(short var0, short var1, boolean var2, short var3, int var4, long var5, long var7, long var9);

    public static native short invokePJPS(long var0, long var2, byte var4, long var5, long var7);

    public static native short invokePPPS(long var0, long var2, long var4, short var6, float var7, long var8);

    public static native void invokeV(long var0);

    public static native void invokeV(double var0, long var2);

    public static native void invokeV(float var0, long var1);

    public static native void invokeV(int var0, long var1);

    public static native void invokeV(short var0, long var1);

    public static native void invokeV(byte var0, int var1, long var2);

    public static native void invokeV(byte var0, boolean var1, long var2);

    public static native void invokeV(int var0, float var1, long var2);

    public static native void invokeV(int var0, int var1, long var2);

    public static native void invokeV(int var0, short var1, long var2);

    public static native void invokeV(short var0, int var1, long var2);

    public static native void invokeV(short var0, short var1, long var2);

    public static native void invokeV(short var0, boolean var1, long var2);

    public static native void invokeV(byte var0, short var1, int var2, long var3);

    public static native void invokeV(int var0, int var1, double var2, long var4);

    public static native void invokeV(int var0, int var1, float var2, long var3);

    public static native void invokeV(int var0, int var1, int var2, long var3);

    public static native void invokeV(short var0, int var1, int var2, long var3);

    public static native void invokeV(byte var0, short var1, int var2, int var3, long var4);

    public static native void invokeV(byte var0, short var1, short var2, int var3, long var4);

    public static native void invokeV(int var0, float var1, float var2, float var3, long var4);

    public static native void invokeV(int var0, int var1, int var2, int var3, long var4);

    public static native void invokeV(short var0, short var1, int var2, boolean var3, long var4);

    public static native void invokeV(short var0, short var1, short var2, int var3, long var4);

    public static native void invokeV(byte var0, short var1, byte var2, int var3, int var4, long var5);

    public static native void invokeV(int var0, int var1, double var2, double var4, double var6, long var8);

    public static native void invokeV(int var0, int var1, float var2, float var3, float var4, long var5);

    public static native void invokeV(int var0, int var1, int var2, int var3, int var4, long var5);

    public static native void invokeV(short var0, short var1, int var2, float var3, byte var4, long var5);

    public static native void invokeV(short var0, short var1, int var2, int var3, int var4, long var5);

    public static native void invokeV(short var0, short var1, short var2, int var3, boolean var4, long var5);

    public static native void invokeV(short var0, short var1, short var2, short var3, short var4, long var5);

    public static native void invokeV(short var0, short var1, short var2, short var3, short var4, int var5, boolean var6, long var7);

    public static native void invokeV(short var0, short var1, float var2, byte var3, byte var4, byte var5, byte var6, byte var7, byte var8, byte var9, byte var10, byte var11, long var12);

    public static native void invokeV(short var0, short var1, byte var2, short var3, short var4, short var5, short var6, byte var7, short var8, short var9, short var10, short var11, short var12, short var13, long var14);

    public static native void invokePV(long var0, long var2);

    public static native void invokeJV(long var0, int var2, long var3);

    public static native void invokePV(byte var0, long var1, long var3);

    public static native void invokePV(int var0, long var1, long var3);

    public static native void invokePV(long var0, float var2, long var3);

    public static native void invokePV(long var0, int var2, long var3);

    public static native void invokePV(long var0, short var2, long var3);

    public static native void invokePV(long var0, boolean var2, long var3);

    public static native void invokePV(short var0, long var1, long var3);

    public static native void invokeJV(int var0, int var1, long var2, long var4);

    public static native void invokePV(int var0, int var1, long var2, long var4);

    public static native void invokePV(long var0, double var2, double var4, long var6);

    public static native void invokePV(long var0, float var2, float var3, long var4);

    public static native void invokePV(long var0, int var2, int var3, long var4);

    public static native void invokePV(long var0, int var2, short var3, long var4);

    public static native void invokePV(long var0, short var2, boolean var3, long var4);

    public static native void invokePV(short var0, int var1, long var2, long var4);

    public static native void invokePV(short var0, long var1, int var3, long var4);

    public static native void invokePV(short var0, long var1, short var3, long var4);

    public static native void invokePV(short var0, short var1, long var2, long var4);

    public static native void invokePV(byte var0, long var1, int var3, int var4, long var5);

    public static native void invokePV(long var0, byte var2, short var3, int var4, long var5);

    public static native void invokePV(long var0, int var2, int var3, int var4, long var5);

    public static native void invokePV(long var0, short var2, int var3, int var4, long var5);

    public static native void invokePV(short var0, short var1, byte var2, long var3, long var5);

    public static native void invokePV(int var0, int var1, long var2, int var4, int var5, long var6);

    public static native void invokePV(long var0, byte var2, short var3, short var4, int var5, long var6);

    public static native void invokePV(long var0, int var2, int var3, int var4, int var5, long var6);

    public static native void invokePV(long var0, short var2, short var3, int var4, boolean var5, long var6);

    public static native void invokePV(long var0, byte var2, short var3, byte var4, int var5, int var6, long var7);

    public static native void invokePV(long var0, byte var2, short var3, int var4, int var5, short var6, long var7);

    public static native void invokePV(long var0, int var2, int var3, int var4, int var5, boolean var6, long var7);

    public static native void invokePV(long var0, short var2, int var3, short var4, short var5, byte var6, long var7);

    public static native void invokePV(long var0, short var2, short var3, int var4, int var5, int var6, long var7);

    public static native void invokePV(long var0, short var2, short var3, short var4, int var5, boolean var6, long var7);

    public static native void invokePV(long var0, short var2, short var3, short var4, short var5, short var6, long var7);

    public static native void invokePV(short var0, short var1, short var2, short var3, long var4, short var6, long var7);

    public static native void invokePV(long var0, short var2, short var3, short var4, short var5, short var6, int var7, boolean var8, long var9);

    public static native void invokePV(long var0, short var2, short var3, short var4, boolean var5, boolean var6, short var7, int var8, long var9);

    public static native void invokePV(short var0, byte var1, short var2, short var3, short var4, short var5, short var6, short var7, long var8, long var10);

    public static native void invokePV(short var0, short var1, byte var2, short var3, short var4, short var5, short var6, long var7, short var9, long var10);

    public static native void invokePV(short var0, short var1, byte var2, byte var3, short var4, short var5, short var6, short var7, long var8, short var10, long var11);

    public static native void invokePV(long var0, short var2, short var3, byte var4, short var5, short var6, short var7, short var8, byte var9, short var10, short var11, short var12, short var13, short var14, short var15, long var16);

    public static native void invokePPV(long var0, long var2, long var4);

    public static native void invokePJV(long var0, long var2, int var4, long var5);

    public static native void invokePPV(int var0, long var1, long var3, long var5);

    public static native void invokePPV(long var0, int var2, long var3, long var5);

    public static native void invokePPV(long var0, long var2, float var4, long var5);

    public static native void invokePPV(long var0, long var2, int var4, long var5);

    public static native void invokePPV(long var0, long var2, boolean var4, long var5);

    public static native void invokePPV(short var0, long var1, long var3, long var5);

    public static native void invokePPV(long var0, int var2, int var3, long var4, long var6);

    public static native void invokePPV(long var0, int var2, long var3, int var5, long var6);

    public static native void invokePPV(long var0, long var2, int var4, int var5, long var6);

    public static native void invokePPV(long var0, short var2, long var3, short var5, long var6);

    public static native void invokePPV(long var0, float var2, long var3, int var5, int var6, long var7);

    public static native void invokePPV(short var0, short var1, byte var2, long var3, long var5, long var7);

    public static native void invokePPV(long var0, byte var2, long var3, int var5, int var6, short var7, long var8);

    public static native void invokePPV(long var0, long var2, int var4, int var5, int var6, int var7, int var8, long var9);

    public static native void invokePPPV(long var0, long var2, long var4, long var6);

    public static native void invokePJJV(long var0, int var2, long var3, long var5, long var7);

    public static native void invokePJPV(long var0, long var2, long var4, int var6, long var7);

    public static native void invokePPPV(int var0, long var1, long var3, long var5, long var7);

    public static native void invokePPPV(long var0, int var2, long var3, long var5, long var7);

    public static native void invokePPPV(long var0, long var2, int var4, long var5, long var7);

    public static native void invokePPPV(long var0, long var2, long var4, int var6, long var7);

    public static native void invokeJJJV(int var0, int var1, long var2, long var4, long var6, long var8);

    public static native void invokePPPV(int var0, int var1, long var2, long var4, long var6, long var8);

    public static native void invokePPPV(long var0, int var2, long var3, long var5, int var7, long var8);

    public static native void invokePPPV(long var0, long var2, int var4, long var5, short var7, long var8);

    public static native void invokePPPV(long var0, long var2, long var4, boolean var6, boolean var7, long var8);

    public static native void invokePPPV(long var0, long var2, short var4, int var5, long var6, long var8);

    public static native void invokePPPV(long var0, long var2, long var4, int var6, boolean var7, boolean var8, long var9);

    public static native void invokePPPV(long var0, boolean var2, int var3, long var4, long var6, int var8, long var9);

    public static native void invokePPPV(long var0, long var2, int var4, int var5, int var6, long var7, int var9, boolean var10, long var11);

    public static native void invokePPPPV(long var0, long var2, long var4, long var6, long var8);

    public static native void invokePPPPV(long var0, int var2, long var3, long var5, long var7, long var9);

    public static native void invokePPPPV(long var0, long var2, long var4, long var6, int var8, long var9);

    public static native void invokePPPPV(long var0, long var2, short var4, long var5, long var7, long var9);

    public static native void invokePPPPPV(long var0, long var2, long var4, long var6, long var8, long var10);

    public static native void invokePPPPPV(long var0, int var2, long var3, long var5, long var7, long var9, long var11);

    public static native void invokePPPPPV(int var0, long var1, int var3, long var4, long var6, long var8, int var10, long var11, int var13, boolean var14, long var15);

    public static native void invokePPPPPPV(long var0, long var2, long var4, long var6, long var8, long var10, long var12);

    public static native void invokePPPPPPPV(long var0, long var2, long var4, long var6, long var8, long var10, long var12, long var14);

    public static native boolean invokeZ(long var0);

    public static native boolean invokeZ(int var0, long var1);

    public static native boolean invokePZ(long var0, long var2);

    public static native boolean invokePZ(long var0, int var2, long var3);

    public static native boolean invokePZ(long var0, int var2, int var3, int var4, long var5);

    public static native boolean invokeJZ(short var0, boolean var1, short var2, int var3, long var4, long var6);

    public static native boolean invokePPZ(long var0, long var2, long var4);

    public static native boolean invokePPPZ(long var0, long var2, long var4, long var6);

    public static native boolean invokePJPZ(long var0, long var2, long var4, int var6, long var7);

    public static native boolean invokePPPZ(long var0, long var2, long var4, int var6, long var7);

    public static native boolean invokePPPZ(long var0, long var2, int var4, long var5, int var7, long var8);

    public static native boolean invokePPPZ(long var0, long var2, long var4, boolean var6, int var7, long var8);

    public static native boolean invokePPPPZ(long var0, long var2, long var4, long var6, long var8);

    public static native boolean invokePPPPZ(long var0, long var2, long var4, byte var6, long var7, long var9);

    public static native boolean invokePPPPPZ(long var0, long var2, long var4, long var6, long var8, int var10, long var11);

    public static native boolean invokePPPPPZ(long var0, long var2, long var4, long var6, long var8, boolean var10, int var11, long var12);

    public static native float callF(long var0);

    public static native float callF(int var0, int var1, int var2, long var3);

    public static native float callPF(int var0, int var1, long var2, long var4);

    public static native float callPPPF(long var0, long var2, long var4, long var6);

    public static native int callI(long var0);

    public static native int callI(int var0, long var1);

    public static native int callI(int var0, float var1, long var2);

    public static native int callI(int var0, int var1, long var2);

    public static native int callI(int var0, int var1, int var2, long var3);

    public static native int callJI(long var0, long var2);

    public static native int callPI(long var0, long var2);

    public static native int callJI(int var0, long var1, long var3);

    public static native int callJI(long var0, float var2, long var3);

    public static native int callJI(long var0, int var2, long var3);

    public static native int callPI(int var0, long var1, long var3);

    public static native int callPI(long var0, float var2, long var3);

    public static native int callPI(long var0, int var2, long var3);

    public static native int callPI(long var0, boolean var2, long var3);

    public static native int callJI(long var0, float var2, float var3, long var4);

    public static native int callJI(long var0, int var2, boolean var3, long var4);

    public static native int callPI(int var0, int var1, long var2, long var4);

    public static native int callPI(int var0, long var1, int var3, long var4);

    public static native int callPI(long var0, float var2, float var3, long var4);

    public static native int callPI(long var0, int var2, float var3, long var4);

    public static native int callPI(long var0, int var2, int var3, long var4);

    public static native int callJI(long var0, float var2, float var3, float var4, long var5);

    public static native int callPI(int var0, int var1, int var2, long var3, long var5);

    public static native int callPI(int var0, long var1, int var3, int var4, long var5);

    public static native int callPI(long var0, int var2, int var3, int var4, long var5);

    public static native int callPI(int var0, int var1, int var2, int var3, long var4, long var6);

    public static native int callPI(long var0, int var2, int var3, int var4, int var5, long var6);

    public static native int callPI(int var0, int var1, int var2, float var3, float var4, long var5, long var7);

    public static native int callPI(int var0, int var1, int var2, int var3, int var4, long var5, long var7);

    public static native int callPI(int var0, long var1, int var3, int var4, float var5, int var6, long var7);

    public static native int callPI(long var0, int var2, int var3, int var4, int var5, int var6, int var7, long var8);

    public static native int callPI(int var0, int var1, long var2, int var4, int var5, int var6, int var7, float var8, long var9);

    public static native int callJJI(long var0, long var2, long var4);

    public static native int callJPI(long var0, long var2, long var4);

    public static native int callPJI(long var0, long var2, long var4);

    public static native int callPPI(long var0, long var2, long var4);

    public static native int callJJI(int var0, long var1, long var3, long var5);

    public static native int callJPI(long var0, int var2, long var3, long var5);

    public static native int callJPI(long var0, long var2, int var4, long var5);

    public static native int callPJI(long var0, int var2, long var3, long var5);

    public static native int callPJI(long var0, long var2, int var4, long var5);

    public static native int callPPI(int var0, long var1, long var3, long var5);

    public static native int callPPI(long var0, byte var2, long var3, long var5);

    public static native int callPPI(long var0, int var2, long var3, long var5);

    public static native int callPPI(long var0, long var2, float var4, long var5);

    public static native int callPPI(long var0, long var2, int var4, long var5);

    public static native int callPPI(long var0, short var2, long var3, long var5);

    public static native int callJPI(long var0, int var2, long var3, float var5, long var6);

    public static native int callJPI(long var0, int var2, long var3, int var5, long var6);

    public static native int callJPI(long var0, long var2, int var4, int var5, long var6);

    public static native int callPJI(long var0, int var2, int var3, long var4, long var6);

    public static native int callPPI(int var0, int var1, long var2, long var4, long var6);

    public static native int callPPI(int var0, long var1, long var3, int var5, long var6);

    public static native int callPPI(long var0, int var2, int var3, long var4, long var6);

    public static native int callPPI(long var0, int var2, long var3, int var5, long var6);

    public static native int callPPI(long var0, long var2, int var4, int var5, long var6);

    public static native int callJPI(long var0, int var2, int var3, long var4, int var6, long var7);

    public static native int callJPI(long var0, long var2, int var4, int var5, int var6, long var7);

    public static native int callPPI(int var0, int var1, int var2, long var3, long var5, long var7);

    public static native int callPPI(int var0, int var1, long var2, int var4, long var5, long var7);

    public static native int callPPI(long var0, int var2, int var3, int var4, long var5, long var7);

    public static native int callPPI(long var0, int var2, int var3, long var4, int var6, long var7);

    public static native int callPPI(long var0, long var2, int var4, int var5, int var6, long var7);

    public static native int callJJI(long var0, float var2, float var3, float var4, float var5, long var6, long var8);

    public static native int callPPI(int var0, int var1, int var2, long var3, int var5, long var6, long var8);

    public static native int callPPI(long var0, long var2, int var4, int var5, int var6, int var7, long var8);

    public static native int callPPI(long var0, int var2, int var3, int var4, int var5, int var6, long var7, long var9);

    public static native int callPPI(long var0, int var2, int var3, int var4, int var5, int var6, int var7, long var8, long var10);

    public static native int callPPI(int var0, int var1, long var2, long var4, int var6, int var7, int var8, int var9, float var10, long var11);

    public static native int callPPI(long var0, int var2, int var3, int var4, int var5, int var6, int var7, long var8, int var10, int var11, int var12, int var13, int var14, int var15, int var16, int var17, int var18, long var19);

    public static native int callJJPI(long var0, long var2, long var4, long var6);

    public static native int callJPPI(long var0, long var2, long var4, long var6);

    public static native int callPJPI(long var0, long var2, long var4, long var6);

    public static native int callPPJI(long var0, long var2, long var4, long var6);

    public static native int callPPPI(long var0, long var2, long var4, long var6);

    public static native int callJJPI(long var0, long var2, long var4, int var6, long var7);

    public static native int callJPJI(long var0, long var2, int var4, long var5, long var7);

    public static native int callJPPI(long var0, int var2, long var3, long var5, long var7);

    public static native int callJPPI(long var0, long var2, int var4, long var5, long var7);

    public static native int callJPPI(long var0, long var2, long var4, int var6, long var7);

    public static native int callPJPI(long var0, int var2, long var3, long var5, long var7);

    public static native int callPJPI(long var0, long var2, int var4, long var5, long var7);

    public static native int callPPJI(long var0, int var2, long var3, long var5, long var7);

    public static native int callPPJI(long var0, long var2, int var4, long var5, long var7);

    public static native int callPPJI(long var0, long var2, long var4, int var6, long var7);

    public static native int callPPPI(int var0, long var1, long var3, long var5, long var7);

    public static native int callPPPI(long var0, byte var2, long var3, long var5, long var7);

    public static native int callPPPI(long var0, int var2, long var3, long var5, long var7);

    public static native int callPPPI(long var0, long var2, int var4, long var5, long var7);

    public static native int callPPPI(long var0, long var2, long var4, int var6, long var7);

    public static native int callPPPI(long var0, short var2, long var3, long var5, long var7);

    public static native int callJPPI(long var0, int var2, long var3, int var5, long var6, long var8);

    public static native int callJPPI(long var0, int var2, long var3, long var5, int var7, long var8);

    public static native int callPPJI(long var0, int var2, long var3, int var5, long var6, long var8);

    public static native int callPPPI(int var0, int var1, long var2, long var4, long var6, long var8);

    public static native int callPPPI(long var0, int var2, long var3, int var5, long var6, long var8);

    public static native int callPPPI(long var0, long var2, int var4, long var5, int var7, long var8);

    public static native int callJPJI(long var0, int var2, float var3, long var4, int var6, long var7, long var9);

    public static native int callJPPI(long var0, int var2, long var3, int var5, long var6, int var8, long var9);

    public static native int callPPPI(long var0, int var2, int var3, int var4, long var5, long var7, long var9);

    public static native int callPPPI(long var0, long var2, int var4, int var5, int var6, long var7, long var9);

    public static native int callPPJI(int var0, int var1, long var2, int var4, long var5, boolean var7, long var8, long var10);

    public static native int callPPPI(long var0, long var2, int var4, int var5, int var6, int var7, long var8, long var10);

    public static native int callPPPI(long var0, int var2, int var3, int var4, int var5, int var6, int var7, int var8, long var9, long var11, long var13);

    public static native int callJPPPI(long var0, long var2, long var4, long var6, long var8);

    public static native int callPJJJI(long var0, long var2, long var4, long var6, long var8);

    public static native int callPJPPI(long var0, long var2, long var4, long var6, long var8);

    public static native int callPPPPI(long var0, long var2, long var4, long var6, long var8);

    public static native int callJPPPI(long var0, long var2, int var4, long var5, long var7, long var9);

    public static native int callPJPPI(long var0, long var2, int var4, long var5, long var7, long var9);

    public static native int callPPPPI(long var0, int var2, long var3, long var5, long var7, long var9);

    public static native int callPPPPI(long var0, long var2, byte var4, long var5, long var7, long var9);

    public static native int callPPPPI(long var0, long var2, int var4, long var5, long var7, long var9);

    public static native int callPPPPI(long var0, long var2, long var4, int var6, long var7, long var9);

    public static native int callPPPPI(long var0, long var2, long var4, long var6, int var8, long var9);

    public static native int callPPPPI(long var0, long var2, short var4, long var5, long var7, long var9);

    public static native int callJPPPI(long var0, int var2, long var3, long var5, long var7, int var9, long var10);

    public static native int callPJPPI(long var0, long var2, int var4, int var5, long var6, long var8, long var10);

    public static native int callPPPPI(long var0, int var2, int var3, long var4, long var6, long var8, long var10);

    public static native int callPPPPI(long var0, int var2, long var3, int var5, long var6, long var8, long var10);

    public static native int callPPPPI(long var0, long var2, int var4, int var5, long var6, long var8, long var10);

    public static native int callPPPPI(long var0, int var2, long var3, int var5, int var6, long var7, long var9, long var11);

    public static native int callJPPJI(long var0, int var2, int var3, long var4, int var6, long var7, boolean var9, long var10, long var12);

    public static native int callPPPPI(long var0, int var2, int var3, int var4, int var5, int var6, int var7, int var8, long var9, long var11, long var13, long var15);

    public static native int callPJPPPI(long var0, long var2, long var4, long var6, long var8, long var10);

    public static native int callPPPPPI(long var0, long var2, long var4, long var6, long var8, long var10);

    public static native int callPJJJPI(long var0, long var2, long var4, long var6, int var8, long var9, long var11);

    public static native int callPJPPPI(long var0, long var2, int var4, long var5, long var7, long var9, long var11);

    public static native int callPPPPPI(long var0, int var2, long var3, long var5, long var7, long var9, long var11);

    public static native int callPPPPPI(long var0, long var2, byte var4, long var5, long var7, long var9, long var11);

    public static native int callPPPPPI(long var0, long var2, int var4, long var5, long var7, long var9, long var11);

    public static native int callPPPPPI(long var0, long var2, long var4, int var6, long var7, long var9, long var11);

    public static native int callPPPPPI(long var0, long var2, long var4, long var6, long var8, int var10, long var11);

    public static native int callPPPPPI(long var0, long var2, short var4, long var5, long var7, long var9, long var11);

    public static native int callJJPPPI(long var0, long var2, int var4, long var5, int var7, long var8, long var10, long var12);

    public static native int callPPJPPI(long var0, int var2, long var3, long var5, int var7, long var8, long var10, long var12);

    public static native int callPPJPPI(long var0, long var2, int var4, long var5, int var7, long var8, long var10, long var12);

    public static native int callPPPPPI(int var0, int var1, long var2, long var4, long var6, long var8, long var10, long var12);

    public static native int callPPPPPI(long var0, long var2, long var4, long var6, long var8, int var10, int var11, long var12);

    public static native int callJPPPPI(int var0, int var1, long var2, long var4, int var6, long var7, long var9, long var11, long var13);

    public static native int callPJPPJI(long var0, long var2, int var4, int var5, long var6, long var8, long var10, int var12, long var13);

    public static native int callPPPPPI(long var0, int var2, long var3, int var5, long var6, int var8, long var9, long var11, long var13);

    public static native int callPJJJJPI(long var0, long var2, long var4, long var6, long var8, long var10, long var12);

    public static native int callPPPPPPI(long var0, long var2, long var4, long var6, long var8, long var10, long var12);

    public static native int callPJPPPPI(long var0, int var2, long var3, long var5, long var7, int var9, long var10, long var12, long var14);

    public static native int callPPPJPPI(long var0, int var2, long var3, long var5, long var7, int var9, long var10, long var12, long var14);

    public static native int callPPPPPPI(int var0, int var1, long var2, long var4, long var6, long var8, long var10, long var12, long var14);

    public static native int callPPPPPPI(long var0, int var2, long var3, long var5, long var7, int var9, long var10, long var12, long var14);

    public static native int callPPPPPPPI(long var0, long var2, int var4, long var5, long var7, long var9, long var11, long var13, long var15);

    public static native int callPPPPPPPI(long var0, long var2, long var4, long var6, int var8, long var9, long var11, long var13, long var15);

    public static native int callPPPPPPPI(long var0, long var2, long var4, long var6, long var8, int var10, long var11, long var13, long var15);

    public static native int callPPPPPPPI(long var0, int var2, long var3, long var5, int var7, long var8, long var10, long var12, long var14, long var16);

    public static native int callPPPPPPPI(long var0, long var2, int var4, long var5, long var7, long var9, int var11, long var12, long var14, long var16);

    public static native int callPPPPPPPPI(long var0, long var2, long var4, long var6, long var8, long var10, int var12, long var13, long var15, long var17);

    public static native int callPPPPPPPPI(long var0, long var2, long var4, long var6, int var8, long var9, long var11, int var13, long var14, long var16, long var18);

    public static native int callJPPPPPPPPI(long var0, long var2, long var4, long var6, long var8, long var10, long var12, long var14, long var16, long var18);

    public static native int callPPPPPPPPPI(long var0, long var2, int var4, long var5, long var7, long var9, long var11, long var13, int var15, long var16, long var18, long var20);

    public static native int callPPPPPPPPPPPPI(long var0, long var2, long var4, long var6, long var8, long var10, long var12, long var14, long var16, long var18, int var20, long var21, long var23, long var25);

    public static native int callPPPPPPPPPPPPI(long var0, long var2, int var4, long var5, long var7, long var9, long var11, long var13, long var15, long var17, long var19, int var21, long var22, long var24, long var26);

    public static native long callJ(long var0);

    public static native long callJ(int var0, long var1);

    public static native long callJ(int var0, int var1, long var2);

    public static native long callJ(int var0, int var1, boolean var2, int var3, int var4, long var5);

    public static native long callJJ(long var0, long var2);

    public static native long callPJ(long var0, long var2);

    public static native long callPJ(int var0, int var1, long var2, long var4);

    public static native long callPPJ(long var0, long var2, long var4);

    public static native long callPPJ(long var0, int var2, long var3, long var5);

    public static native long callP(long var0);

    public static native long callP(int var0, long var1);

    public static native long callP(int var0, int var1, long var2);

    public static native long callP(int var0, float var1, float var2, float var3, long var4);

    public static native long callPP(long var0, long var2);

    public static native long callPP(int var0, long var1, long var3);

    public static native long callPP(long var0, int var2, long var3);

    public static native long callPP(int var0, long var1, int var3, long var4);

    public static native long callPP(long var0, int var2, int var3, long var4);

    public static native long callPPP(long var0, long var2, long var4);

    public static native long callPPP(int var0, long var1, long var3, long var5);

    public static native long callPPP(long var0, int var2, long var3, long var5);

    public static native long callPPP(long var0, long var2, int var4, long var5);

    public static native long callPPP(int var0, long var1, long var3, int var5, long var6);

    public static native long callPPP(int var0, int var1, int var2, long var3, long var5, long var7);

    public static native long callPPP(long var0, int var2, int var3, int var4, long var5, long var7);

    public static native long callPPP(long var0, long var2, int var4, int var5, int var6, long var7);

    public static native long callPPPP(long var0, long var2, long var4, long var6);

    public static native long callPJPP(long var0, long var2, int var4, long var5, long var7);

    public static native long callPJPP(long var0, long var2, long var4, int var6, long var7);

    public static native long callPPPP(long var0, int var2, long var3, long var5, long var7);

    public static native long callPPPP(long var0, long var2, int var4, long var5, long var7);

    public static native long callPPPP(long var0, long var2, long var4, int var6, long var7);

    public static native long callPPPP(long var0, long var2, int var4, int var5, long var6, long var8);

    public static native long callPPPP(long var0, long var2, int var4, long var5, int var7, long var8);

    public static native long callPJPP(long var0, long var2, int var4, int var5, int var6, long var7, long var9);

    public static native long callPPJPP(long var0, long var2, long var4, long var6, long var8);

    public static native long callPPPPP(long var0, long var2, long var4, long var6, long var8);

    public static native long callPJPPP(long var0, long var2, int var4, long var5, long var7, long var9);

    public static native long callPJPPP(long var0, long var2, long var4, int var6, long var7, long var9);

    public static native long callPPPPP(long var0, int var2, long var3, long var5, long var7, long var9);

    public static native long callPPPPP(long var0, long var2, int var4, long var5, long var7, long var9);

    public static native long callPPPPP(long var0, long var2, long var4, int var6, long var7, long var9);

    public static native long callPPPPP(long var0, long var2, long var4, long var6, int var8, long var9);

    public static native long callPJPPP(long var0, long var2, int var4, int var5, long var6, long var8, long var10);

    public static native long callPJPPPP(long var0, long var2, long var4, long var6, long var8, long var10);

    public static native long callPPPPPP(long var0, int var2, long var3, long var5, long var7, long var9, long var11);

    public static native long callPJPPPPP(long var0, long var2, long var4, long var6, long var8, long var10, long var12);

    public static native long callPPPJPPP(long var0, long var2, long var4, long var6, long var8, long var10, long var12);

    public static native long callPPPPPPP(long var0, int var2, long var3, long var5, long var7, long var9, long var11, long var13);

    public static native long callPPPPPPP(long var0, int var2, long var3, long var5, int var7, long var8, long var10, long var12, long var14);

    public static native long callPJPPPPPPP(long var0, long var2, long var4, long var6, long var8, long var10, long var12, long var14, long var16);

    public static native long callPPJPPPPPP(long var0, long var2, int var4, long var5, long var7, long var9, int var11, long var12, long var14, long var16, long var18);

    public static native long callPJPPPPPPPPP(long var0, long var2, long var4, long var6, long var8, long var10, long var12, long var14, long var16, long var18, long var20);

    public static native long callPPJPPPPPPPP(long var0, long var2, int var4, long var5, long var7, long var9, long var11, long var13, int var15, long var16, long var18, long var20, long var22);

    public static native short callS(int var0, long var1);

    public static native short callPS(long var0, long var2);

    public static native short callPS(long var0, short var2, long var3);

    public static native short callPS(short var0, long var1, long var3);

    public static native short callPS(short var0, long var1, short var3, long var4);

    public static native short callPPS(long var0, long var2, long var4);

    public static native short callPPS(long var0, long var2, int var4, long var5);

    public static native short callPPS(long var0, long var2, short var4, long var5);

    public static native short callPPS(long var0, short var2, long var3, long var5);

    public static native short callPPS(short var0, long var1, long var3, long var5);

    public static native short callPJS(long var0, long var2, short var4, short var5, long var6);

    public static native short callPPS(long var0, int var2, long var3, int var5, long var6);

    public static native short callPPS(long var0, short var2, short var3, long var4, int var6, long var7);

    public static native short callPPPS(long var0, long var2, long var4, long var6);

    public static native short callPPPS(long var0, long var2, short var4, long var5, long var7);

    public static native short callPPPS(long var0, int var2, long var3, int var5, long var6, long var8);

    public static native short callPPPS(long var0, short var2, long var3, short var5, long var6, long var8);

    public static native short callPPPS(long var0, short var2, short var3, long var4, int var6, long var7, long var9);

    public static native short callPPPS(short var0, long var1, short var3, short var4, long var5, short var7, long var8, long var10);

    public static native short callPPPPS(long var0, short var2, long var3, long var5, long var7, long var9);

    public static native short callPPPPS(long var0, long var2, int var4, long var5, int var7, long var8, long var10);

    public static native short callPPPPS(long var0, long var2, short var4, long var5, short var7, long var8, long var10);

    public static native short callPPPPS(long var0, short var2, short var3, long var4, long var6, long var8, long var10);

    public static native short callPPPPS(long var0, long var2, short var4, long var5, short var7, long var8, short var10, long var11);

    public static native short callPPPPS(long var0, short var2, short var3, long var4, short var6, long var7, long var9, long var11);

    public static native short callPPPPS(long var0, long var2, short var4, long var5, short var7, long var8, short var10, short var11, short var12, long var13);

    public static native short callPPPPS(long var0, short var2, long var3, short var5, long var6, short var8, long var9, short var11, short var12, short var13, long var14);

    public static native short callPPPPPS(long var0, short var2, long var3, long var5, long var7, long var9, long var11);

    public static native short callPPPPPS(long var0, long var2, long var4, short var6, long var7, short var9, long var10, short var12, long var13);

    public static native short callPPPPPS(long var0, short var2, long var3, short var5, long var6, long var8, short var10, long var11, long var13);

    public static native short callPPPPPS(short var0, long var1, short var3, long var4, long var6, long var8, short var10, long var11, long var13);

    public static native short callPPPPPS(long var0, long var2, short var4, long var5, short var7, long var8, short var10, long var11, short var13, long var14);

    public static native short callPPPPPS(long var0, short var2, short var3, short var4, long var5, short var7, short var8, long var9, long var11, long var13, long var15);

    public static native short callPPPPPS(long var0, short var2, short var3, short var4, short var5, long var6, short var8, long var9, long var11, long var13, long var15);

    public static native short callPPPPPPPS(long var0, short var2, long var3, short var5, long var6, long var8, long var10, long var12, long var14, long var16);

    public static native short callPPPPPPPS(long var0, long var2, short var4, long var5, short var7, long var8, short var10, long var11, short var13, long var14, short var16, long var17, short var19, long var20);

    public static native short callPPPPPPPPPS(long var0, short var2, long var3, short var5, long var6, long var8, long var10, long var12, long var14, long var16, long var18, long var20);

    public static native void callV(long var0);

    public static native void callV(byte var0, long var1);

    public static native void callV(double var0, long var2);

    public static native void callV(float var0, long var1);

    public static native void callV(int var0, long var1);

    public static native void callV(short var0, long var1);

    public static native void callV(boolean var0, long var1);

    public static native void callV(double var0, double var2, long var4);

    public static native void callV(float var0, float var1, long var2);

    public static native void callV(float var0, boolean var1, long var2);

    public static native void callV(int var0, double var1, long var3);

    public static native void callV(int var0, float var1, long var2);

    public static native void callV(int var0, int var1, long var2);

    public static native void callV(int var0, short var1, long var2);

    public static native void callV(int var0, boolean var1, long var2);

    public static native void callV(short var0, short var1, long var2);

    public static native void callV(byte var0, byte var1, byte var2, long var3);

    public static native void callV(double var0, double var2, double var4, long var6);

    public static native void callV(float var0, float var1, float var2, long var3);

    public static native void callV(int var0, double var1, double var3, long var5);

    public static native void callV(int var0, float var1, float var2, long var3);

    public static native void callV(int var0, int var1, double var2, long var4);

    public static native void callV(int var0, int var1, float var2, long var3);

    public static native void callV(int var0, int var1, int var2, long var3);

    public static native void callV(int var0, int var1, short var2, long var3);

    public static native void callV(int var0, int var1, boolean var2, long var3);

    public static native void callV(int var0, short var1, short var2, long var3);

    public static native void callV(short var0, short var1, short var2, long var3);

    public static native void callV(byte var0, byte var1, byte var2, byte var3, long var4);

    public static native void callV(double var0, double var2, double var4, double var6, long var8);

    public static native void callV(float var0, float var1, float var2, float var3, long var4);

    public static native void callV(int var0, double var1, double var3, double var5, long var7);

    public static native void callV(int var0, float var1, float var2, float var3, long var4);

    public static native void callV(int var0, int var1, double var2, double var4, long var6);

    public static native void callV(int var0, int var1, float var2, float var3, long var4);

    public static native void callV(int var0, int var1, float var2, int var3, long var4);

    public static native void callV(int var0, int var1, int var2, double var3, long var5);

    public static native void callV(int var0, int var1, int var2, float var3, long var4);

    public static native void callV(int var0, int var1, int var2, int var3, long var4);

    public static native void callV(int var0, int var1, int var2, boolean var3, long var4);

    public static native void callV(int var0, int var1, boolean var2, int var3, long var4);

    public static native void callV(int var0, short var1, short var2, short var3, long var4);

    public static native void callV(short var0, short var1, short var2, short var3, long var4);

    public static native void callV(boolean var0, boolean var1, boolean var2, boolean var3, long var4);

    public static native void callV(int var0, byte var1, byte var2, byte var3, byte var4, long var5);

    public static native void callV(int var0, double var1, double var3, double var5, double var7, long var9);

    public static native void callV(int var0, float var1, float var2, float var3, float var4, long var5);

    public static native void callV(int var0, int var1, double var2, double var4, double var6, long var8);

    public static native void callV(int var0, int var1, float var2, float var3, float var4, long var5);

    public static native void callV(int var0, int var1, int var2, float var3, int var4, long var5);

    public static native void callV(int var0, int var1, int var2, int var3, int var4, long var5);

    public static native void callV(int var0, int var1, int var2, boolean var3, int var4, long var5);

    public static native void callV(int var0, short var1, short var2, short var3, short var4, long var5);

    public static native void callV(int var0, boolean var1, boolean var2, boolean var3, boolean var4, long var5);

    public static native void callV(double var0, double var2, double var4, double var6, double var8, double var10, long var12);

    public static native void callV(float var0, float var1, float var2, float var3, float var4, boolean var5, long var6);

    public static native void callV(int var0, double var1, double var3, int var5, double var6, double var8, long var10);

    public static native void callV(int var0, float var1, float var2, int var3, float var4, float var5, long var6);

    public static native void callV(int var0, int var1, double var2, double var4, double var6, double var8, long var10);

    public static native void callV(int var0, int var1, float var2, float var3, float var4, float var5, long var6);

    public static native void callV(int var0, int var1, int var2, int var3, int var4, int var5, long var6);

    public static native void callV(int var0, int var1, int var2, int var3, int var4, boolean var5, long var6);

    public static native void callV(int var0, int var1, int var2, int var3, boolean var4, int var5, long var6);

    public static native void callV(int var0, double var1, double var3, double var5, double var7, double var9, double var11, long var13);

    public static native void callV(int var0, int var1, int var2, double var3, double var5, double var7, double var9, long var11);

    public static native void callV(int var0, int var1, int var2, float var3, float var4, float var5, float var6, long var7);

    public static native void callV(int var0, int var1, int var2, int var3, int var4, int var5, int var6, long var7);

    public static native void callV(int var0, int var1, int var2, int var3, int var4, int var5, boolean var6, long var7);

    public static native void callV(int var0, int var1, int var2, boolean var3, int var4, int var5, int var6, long var7);

    public static native void callV(float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, long var8);

    public static native void callV(int var0, int var1, int var2, float var3, float var4, float var5, float var6, float var7, long var8);

    public static native void callV(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, long var8);

    public static native void callV(int var0, int var1, int var2, int var3, int var4, int var5, int var6, boolean var7, long var8);

    public static native void callV(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, long var9);

    public static native void callV(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8, long var9);

    public static native void callV(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, long var10);

    public static native void callV(int var0, int var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, long var11);

    public static native void callV(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, long var12);

    public static native void callV(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, int var14, long var15);

    public static native void callV(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, int var14, int var15, int var16, long var17);

    public static native void callJV(long var0, long var2);

    public static native void callPV(long var0, long var2);

    public static native void callJV(int var0, long var1, long var3);

    public static native void callJV(long var0, int var2, long var3);

    public static native void callPV(int var0, long var1, long var3);

    public static native void callPV(long var0, float var2, long var3);

    public static native void callPV(long var0, int var2, long var3);

    public static native void callJV(int var0, int var1, long var2, long var4);

    public static native void callPV(int var0, int var1, long var2, long var4);

    public static native void callPV(int var0, long var1, int var3, long var4);

    public static native void callPV(long var0, float var2, float var3, long var4);

    public static native void callPV(long var0, int var2, int var3, long var4);

    public static native void callJV(int var0, long var1, int var3, int var4, long var5);

    public static native void callPV(int var0, float var1, long var2, int var4, long var5);

    public static native void callPV(int var0, int var1, int var2, long var3, long var5);

    public static native void callPV(int var0, int var1, long var2, int var4, long var5);

    public static native void callPV(int var0, int var1, boolean var2, long var3, long var5);

    public static native void callPV(int var0, long var1, int var3, int var4, long var5);

    public static native void callPV(long var0, float var2, float var3, float var4, long var5);

    public static native void callPV(long var0, int var2, int var3, int var4, long var5);

    public static native void callPV(int var0, int var1, int var2, int var3, long var4, long var6);

    public static native void callPV(int var0, int var1, int var2, long var3, int var5, long var6);

    public static native void callPV(int var0, int var1, int var2, long var3, boolean var5, long var6);

    public static native void callPV(int var0, int var1, int var2, boolean var3, long var4, long var6);

    public static native void callPV(int var0, int var1, long var2, int var4, int var5, long var6);

    public static native void callPV(int var0, long var1, int var3, int var4, int var5, long var6);

    public static native void callPV(int var0, boolean var1, int var2, int var3, long var4, long var6);

    public static native void callPV(long var0, int var2, int var3, int var4, int var5, long var6);

    public static native void callJV(int var0, int var1, int var2, int var3, int var4, long var5, long var7);

    public static native void callPV(int var0, double var1, double var3, int var5, int var6, long var7, long var9);

    public static native void callPV(int var0, float var1, float var2, int var3, int var4, long var5, long var7);

    public static native void callPV(int var0, int var1, int var2, int var3, int var4, long var5, long var7);

    public static native void callPV(int var0, int var1, int var2, int var3, long var4, boolean var6, long var7);

    public static native void callPV(int var0, int var1, int var2, long var3, int var5, int var6, long var7);

    public static native void callPV(int var0, int var1, int var2, boolean var3, int var4, long var5, long var7);

    public static native void callPV(int var0, int var1, long var2, int var4, int var5, int var6, long var7);

    public static native void callPV(int var0, boolean var1, int var2, int var3, int var4, long var5, long var7);

    public static native void callPV(long var0, int var2, int var3, int var4, int var5, int var6, long var7);

    public static native void callJV(int var0, int var1, int var2, int var3, int var4, int var5, long var6, long var8);

    public static native void callPV(int var0, int var1, float var2, float var3, float var4, float var5, long var6, long var8);

    public static native void callPV(int var0, int var1, int var2, int var3, int var4, int var5, long var6, long var8);

    public static native void callPV(int var0, int var1, int var2, int var3, int var4, long var5, int var7, long var8);

    public static native void callPV(int var0, int var1, int var2, long var3, int var5, int var6, int var7, long var8);

    public static native void callPV(int var0, int var1, long var2, int var4, int var5, int var6, int var7, long var8);

    public static native void callPV(long var0, int var2, int var3, int var4, int var5, int var6, int var7, long var8);

    public static native void callJV(int var0, int var1, int var2, int var3, int var4, int var5, int var6, long var7, long var9);

    public static native void callJV(int var0, int var1, int var2, int var3, int var4, boolean var5, int var6, long var7, long var9);

    public static native void callPV(int var0, int var1, int var2, int var3, int var4, int var5, int var6, long var7, long var9);

    public static native void callPV(int var0, int var1, int var2, int var3, int var4, boolean var5, int var6, long var7, long var9);

    public static native void callJV(int var0, int var1, int var2, int var3, int var4, int var5, boolean var6, int var7, long var8, long var10);

    public static native void callPV(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, long var8, long var10);

    public static native void callPV(int var0, int var1, long var2, int var4, int var5, int var6, int var7, int var8, float var9, long var10);

    public static native void callPV(int var0, double var1, double var3, int var5, int var6, double var7, double var9, int var11, int var12, long var13, long var15);

    public static native void callPV(int var0, float var1, float var2, int var3, int var4, float var5, float var6, int var7, int var8, long var9, long var11);

    public static native void callPV(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, long var9, long var11);

    public static native void callJV(long var0, int var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, float var11, long var12);

    public static native void callPV(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, long var10, long var12);

    public static native void callPV(long var0, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, long var12);

    public static native void callPV(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, long var11, long var13);

    public static native void callJPV(long var0, long var2, long var4);

    public static native void callPJV(long var0, long var2, long var4);

    public static native void callPPV(long var0, long var2, long var4);

    public static native void callJJV(int var0, long var1, long var3, long var5);

    public static native void callPJV(long var0, int var2, long var3, long var5);

    public static native void callPJV(long var0, long var2, int var4, long var5);

    public static native void callPPV(int var0, long var1, long var3, long var5);

    public static native void callPPV(long var0, int var2, long var3, long var5);

    public static native void callPPV(long var0, long var2, int var4, long var5);

    public static native void callJJV(int var0, int var1, long var2, long var4, long var6);

    public static native void callJPV(int var0, int var1, long var2, long var4, long var6);

    public static native void callJPV(int var0, long var1, int var3, long var4, long var6);

    public static native void callPJV(int var0, long var1, int var3, long var4, long var6);

    public static native void callPJV(long var0, int var2, long var3, int var5, long var6);

    public static native void callPJV(long var0, long var2, int var4, int var5, long var6);

    public static native void callPPV(int var0, int var1, long var2, long var4, long var6);

    public static native void callPPV(int var0, long var1, int var3, long var4, long var6);

    public static native void callPPV(int var0, long var1, long var3, int var5, long var6);

    public static native void callPPV(int var0, long var1, long var3, boolean var5, long var6);

    public static native void callPPV(long var0, int var2, float var3, long var4, long var6);

    public static native void callPPV(long var0, int var2, int var3, long var4, long var6);

    public static native void callPPV(long var0, long var2, int var4, int var5, long var6);

    public static native void callPJV(long var0, long var2, int var4, int var5, int var6, long var7);

    public static native void callPPV(int var0, int var1, int var2, long var3, long var5, long var7);

    public static native void callPPV(int var0, int var1, long var2, int var4, long var5, long var7);

    public static native void callPPV(int var0, int var1, long var2, long var4, int var6, long var7);

    public static native void callPPV(int var0, long var1, int var3, long var4, int var6, long var7);

    public static native void callPPV(int var0, long var1, long var3, int var5, int var6, long var7);

    public static native void callPPV(long var0, int var2, int var3, int var4, long var5, long var7);

    public static native void callPPV(int var0, int var1, int var2, int var3, long var4, long var6, long var8);

    public static native void callPPV(int var0, int var1, long var2, int var4, int var5, long var6, long var8);

    public static native void callPPV(int var0, int var1, long var2, long var4, int var6, int var7, long var8);

    public static native void callPPV(int var0, long var1, long var3, int var5, int var6, int var7, long var8);

    public static native void callPPV(int var0, int var1, int var2, int var3, int var4, long var5, long var7, long var9);

    public static native void callPPV(int var0, int var1, int var2, long var3, int var5, int var6, long var7, long var9);

    public static native void callPPV(int var0, int var1, long var2, int var4, int var5, int var6, long var7, long var9);

    public static native void callPPV(int var0, int var1, long var2, long var4, int var6, int var7, int var8, long var9);

    public static native void callPPV(int var0, int var1, int var2, int var3, int var4, int var5, long var6, long var8, long var10);

    public static native void callPPV(int var0, int var1, int var2, int var3, long var4, int var6, int var7, long var8, long var10);

    public static native void callPPV(int var0, int var1, long var2, int var4, int var5, int var6, int var7, long var8, long var10);

    public static native void callPPV(int var0, int var1, int var2, long var3, int var5, float var6, float var7, int var8, long var9, long var11);

    public static native void callPPV(int var0, int var1, long var2, int var4, int var5, int var6, int var7, int var8, long var9, long var11);

    public static native void callPPV(int var0, int var1, long var2, int var4, int var5, int var6, long var7, int var9, int var10, float var11, long var12);

    public static native void callPJJV(long var0, long var2, long var4, long var6);

    public static native void callPJPV(long var0, long var2, long var4, long var6);

    public static native void callPPPV(long var0, long var2, long var4, long var6);

    public static native void callJJJV(int var0, long var1, long var3, long var5, long var7);

    public static native void callPJJV(long var0, long var2, long var4, int var6, long var7);

    public static native void callPJPV(long var0, long var2, int var4, long var5, long var7);

    public static native void callPPPV(int var0, long var1, long var3, long var5, long var7);

    public static native void callPPPV(long var0, int var2, long var3, long var5, long var7);

    public static native void callPPPV(long var0, long var2, float var4, long var5, long var7);

    public static native void callPPPV(long var0, long var2, int var4, long var5, long var7);

    public static native void callPPPV(long var0, long var2, long var4, int var6, long var7);

    public static native void callPPPV(long var0, long var2, boolean var4, long var5, long var7);

    public static native void callJJJV(int var0, int var1, long var2, long var4, long var6, long var8);

    public static native void callPJJV(long var0, int var2, long var3, long var5, int var7, long var8);

    public static native void callPJJV(long var0, long var2, long var4, int var6, int var7, long var8);

    public static native void callPPPV(int var0, int var1, long var2, long var4, long var6, long var8);

    public static native void callPPPV(int var0, long var1, int var3, long var4, long var6, long var8);

    public static native void callPPPV(int var0, long var1, long var3, int var5, long var6, long var8);

    public static native void callPPPV(int var0, long var1, long var3, long var5, int var7, long var8);

    public static native void callPPPV(long var0, int var2, int var3, long var4, long var6, long var8);

    public static native void callPPPV(long var0, int var2, long var3, int var5, long var6, long var8);

    public static native void callPJPV(long var0, int var2, long var3, int var5, int var6, long var7, long var9);

    public static native void callPJPV(long var0, long var2, int var4, int var5, int var6, long var7, long var9);

    public static native void callPPJV(long var0, int var2, long var3, int var5, long var6, int var8, long var9);

    public static native void callPPPV(int var0, int var1, int var2, long var3, long var5, long var7, long var9);

    public static native void callPPPV(int var0, int var1, long var2, int var4, long var5, long var7, long var9);

    public static native void callPPPV(int var0, long var1, int var3, long var4, int var6, long var7, long var9);

    public static native void callPJJV(long var0, int var2, int var3, long var4, long var6, int var8, int var9, long var10);

    public static native void callPPPV(int var0, int var1, int var2, int var3, long var4, long var6, long var8, long var10);

    public static native void callPPPV(int var0, int var1, long var2, long var4, int var6, int var7, long var8, long var10);

    public static native void callPPPV(int var0, int var1, int var2, int var3, long var4, int var6, long var7, long var9, long var11);

    public static native void callPPPV(long var0, int var2, int var3, int var4, int var5, int var6, long var7, long var9, long var11);

    public static native void callPPPV(long var0, long var2, int var4, int var5, int var6, int var7, int var8, int var9, long var10, int var12, int var13, int var14, int var15, int var16, int var17, int var18, int var19, int var20, long var21);

    public static native void callPJJPV(long var0, long var2, long var4, long var6, long var8);

    public static native void callPJPPV(long var0, long var2, long var4, long var6, long var8);

    public static native void callPPPPV(long var0, long var2, long var4, long var6, long var8);

    public static native void callJJJJV(int var0, long var1, long var3, long var5, long var7, long var9);

    public static native void callPJJJV(long var0, long var2, long var4, long var6, int var8, long var9);

    public static native void callPJJPV(long var0, long var2, long var4, int var6, long var7, long var9);

    public static native void callPPPPV(int var0, long var1, long var3, long var5, long var7, long var9);

    public static native void callPPPPV(long var0, long var2, long var4, int var6, long var7, long var9);

    public static native void callPPPPV(long var0, long var2, long var4, long var6, int var8, long var9);

    public static native void callJJJJV(int var0, int var1, long var2, long var4, long var6, long var8, long var10);

    public static native void callPJJPV(long var0, long var2, int var4, long var5, int var7, long var8, long var10);

    public static native void callPJJPV(long var0, long var2, long var4, int var6, int var7, long var8, long var10);

    public static native void callPJPPV(long var0, long var2, int var4, long var5, int var7, long var8, long var10);

    public static native void callPPPPV(int var0, long var1, int var3, long var4, long var6, long var8, long var10);

    public static native void callPPPPV(int var0, long var1, long var3, long var5, long var7, int var9, long var10);

    public static native void callPPPPV(long var0, int var2, int var3, long var4, long var6, long var8, long var10);

    public static native void callPJJPV(long var0, long var2, int var4, long var5, int var7, int var8, long var9, long var11);

    public static native void callPPPPV(int var0, int var1, int var2, long var3, long var5, long var7, long var9, long var11);

    public static native void callPPPPV(int var0, int var1, long var2, long var4, long var6, long var8, int var10, long var11);

    public static native void callPJJPV(long var0, long var2, int var4, long var5, int var7, int var8, long var9, int var11, long var12);

    public static native void callPJPPV(long var0, int var2, long var3, int var5, int var6, long var7, int var9, long var10, long var12);

    public static native void callPPPPV(long var0, int var2, int var3, int var4, int var5, long var6, int var8, long var9, int var11, long var12, long var14);

    public static native void callPJJJPV(long var0, long var2, long var4, long var6, long var8, long var10);

    public static native void callPJJJJV(long var0, long var2, long var4, long var6, long var8, int var10, int var11, long var12);

    public static native void callPPPPPV(int var0, int var1, long var2, long var4, long var6, long var8, long var10, long var12);

    public static native void callPJJJJV(long var0, long var2, int var4, int var5, long var6, long var8, long var10, int var12, long var13);

    public static native void callPJPPPV(long var0, int var2, int var3, long var4, long var6, int var8, long var9, long var11, long var13);

    public static native void callPPPPPV(long var0, int var2, long var3, int var5, int var6, int var7, long var8, int var10, long var11, int var13, long var14, long var16);

    public static native void callPPPPPPV(long var0, long var2, long var4, int var6, int var7, long var8, long var10, long var12, long var14);

    public static native void callPPPPPPPV(int var0, int var1, int var2, long var3, int var5, long var6, long var8, long var10, long var12, long var14, long var16, long var18);

    public static native void callPPJJJJJJV(long var0, long var2, long var4, long var6, int var8, long var9, long var11, long var13, long var15, long var17);

    public static native void callPJJJJJJJJJJJV(long var0, long var2, long var4, long var6, long var8, long var10, long var12, long var14, long var16, long var18, long var20, long var22, int var24, int var25, int var26, long var27);

    public static native boolean callZ(long var0);

    public static native boolean callZ(int var0, long var1);

    public static native boolean callZ(boolean var0, long var1);

    public static native boolean callZ(int var0, int var1, long var2);

    public static native boolean callZ(int var0, float var1, float var2, long var3);

    public static native boolean callZ(int var0, int var1, float var2, float var3, long var4);

    public static native boolean callJZ(long var0, long var2);

    public static native boolean callPZ(long var0, long var2);

    public static native boolean callJZ(int var0, long var1, long var3);

    public static native boolean callPZ(int var0, long var1, long var3);

    public static native boolean callPZ(long var0, int var2, long var3);

    public static native boolean callPZ(boolean var0, long var1, long var3);

    public static native boolean callJZ(int var0, long var1, int var3, long var4);

    public static native boolean callPZ(int var0, int var1, long var2, long var4);

    public static native boolean callPZ(int var0, long var1, int var3, long var4);

    public static native boolean callPZ(int var0, float var1, float var2, long var3, long var5);

    public static native boolean callPPZ(long var0, long var2, long var4);

    public static native boolean callJPZ(long var0, long var2, int var4, long var5);

    public static native boolean callPPZ(int var0, long var1, long var3, long var5);

    public static native boolean callPPZ(long var0, int var2, long var3, long var5);

    public static native boolean callPPZ(long var0, long var2, int var4, long var5);

    public static native boolean callPPZ(int var0, long var1, int var3, long var4, long var6);

    public static native boolean callPPZ(int var0, int var1, long var2, int var4, long var5, long var7);

    public static native boolean callJPPZ(long var0, long var2, long var4, long var6);

    public static native boolean callPPPZ(long var0, long var2, long var4, long var6);

    public static native boolean callPPPPZ(int var0, int var1, int var2, float var3, long var4, long var6, long var8, long var10, long var12);

    public static native boolean callPPJPPZ(long var0, long var2, long var4, long var6, long var8, long var10);

    public static native boolean callPPPPPZ(long var0, long var2, long var4, long var6, long var8, long var10);

    public static native byte invokePB(byte var0, @Nullable int[] var1, long var2);

    public static native int invokePI(short var0, @Nullable int[] var1, long var2);

    public static native int invokePI(@Nullable float[] var0, short var1, long var2);

    public static native int invokePI(short var0, @Nullable float[] var1, byte var2, long var3);

    public static native int invokePI(short var0, @Nullable int[] var1, byte var2, long var3);

    public static native int invokePI(short var0, @Nullable short[] var1, byte var2, long var3);

    public static native int invokePPI(long var0, @Nullable float[] var2, short var3, long var4);

    public static native int invokePPI(int var0, @Nullable int[] var1, int var2, @Nullable int[] var3, int var4, boolean var5, long var6);

    public static native int invokePPI(int var0, @Nullable short[] var1, int var2, @Nullable short[] var3, int var4, boolean var5, long var6);

    public static native int invokePPPPI(long var0, long var2, long var4, @Nullable long[] var6, long var7);

    public static native int invokePPPPI(long var0, long var2, int var4, int var5, @Nullable float[] var6, @Nullable int[] var7, long var8);

    public static native int invokePPPPI(long var0, long var2, int var4, int var5, @Nullable int[] var6, @Nullable int[] var7, long var8);

    public static native int invokePPPPPPPPI(long var0, int var2, int var3, long var4, @Nullable int[] var6, @Nullable int[] var7, @Nullable float[] var8, @Nullable int[] var9, @Nullable int[] var10, @Nullable int[] var11, long var12);

    public static native long invokePP(@Nullable double[] var0, int var1, long var2);

    public static native long invokePP(@Nullable float[] var0, int var1, long var2);

    public static native long invokePP(@Nullable int[] var0, int var1, long var2);

    public static native long invokePP(@Nullable long[] var0, int var1, long var2);

    public static native long invokePP(@Nullable short[] var0, int var1, long var2);

    public static native long invokePPP(long var0, @Nullable int[] var2, long var3);

    public static native short invokePS(byte var0, @Nullable short[] var1, boolean var2, long var3);

    public static native short invokePS(short var0, @Nullable short[] var1, short var2, long var3);

    public static native short invokePPPS(@Nullable short[] var0, long var1, long var3, short var5, float var6, long var7);

    public static native void invokePV(byte var0, @Nullable float[] var1, long var2);

    public static native void invokePV(int var0, @Nullable double[] var1, long var2);

    public static native void invokePV(int var0, @Nullable float[] var1, long var2);

    public static native void invokePV(int var0, @Nullable int[] var1, long var2);

    public static native void invokePV(int var0, int var1, @Nullable double[] var2, long var3);

    public static native void invokePV(int var0, int var1, @Nullable float[] var2, long var3);

    public static native void invokePV(int var0, int var1, @Nullable int[] var2, long var3);

    public static native void invokePV(int var0, int var1, @Nullable long[] var2, long var3);

    public static native void invokePV(short var0, short var1, @Nullable short[] var2, long var3);

    public static native void invokePV(short var0, @Nullable double[] var1, short var2, long var3);

    public static native void invokePV(short var0, @Nullable float[] var1, short var2, long var3);

    public static native void invokePV(short var0, @Nullable int[] var1, short var2, long var3);

    public static native void invokePV(short var0, @Nullable long[] var1, short var2, long var3);

    public static native void invokePV(short var0, @Nullable short[] var1, short var2, long var3);

    public static native void invokePV(int var0, int var1, @Nullable float[] var2, int var3, int var4, long var5);

    public static native void invokePV(int var0, int var1, @Nullable int[] var2, int var3, int var4, long var5);

    public static native void invokePV(int var0, int var1, @Nullable short[] var2, int var3, int var4, long var5);

    public static native void invokePPV(long var0, @Nullable float[] var2, int var3, long var4);

    public static native void invokePPV(long var0, @Nullable int[] var2, int var3, long var4);

    public static native void invokePPV(long var0, @Nullable short[] var2, int var3, long var4);

    public static native void invokePPV(short var0, @Nullable float[] var1, @Nullable float[] var2, long var3);

    public static native void invokePPV(long var0, int var2, int var3, @Nullable int[] var4, long var5);

    public static native void invokePPV(long var0, int var2, int var3, @Nullable long[] var4, long var5);

    public static native void invokePPV(long var0, short var2, @Nullable double[] var3, short var4, long var5);

    public static native void invokePPV(long var0, short var2, @Nullable float[] var3, short var4, long var5);

    public static native void invokePPV(long var0, short var2, @Nullable int[] var3, short var4, long var5);

    public static native void invokePPV(long var0, short var2, @Nullable long[] var3, short var4, long var5);

    public static native void invokePPV(long var0, short var2, @Nullable short[] var3, short var4, long var5);

    public static native void invokePPPV(long var0, @Nullable double[] var2, @Nullable double[] var3, long var4);

    public static native void invokePPPV(long var0, @Nullable float[] var2, @Nullable float[] var3, long var4);

    public static native void invokePPPV(long var0, @Nullable int[] var2, @Nullable int[] var3, long var4);

    public static native void invokePPPV(@Nullable int[] var0, @Nullable int[] var1, @Nullable int[] var2, long var3);

    public static native void invokePPPV(int var0, @Nullable float[] var1, @Nullable float[] var2, @Nullable float[] var3, long var4);

    public static native void invokePPPV(int var0, int var1, @Nullable double[] var2, @Nullable double[] var3, @Nullable double[] var4, long var5);

    public static native void invokePPPV(int var0, int var1, @Nullable float[] var2, @Nullable float[] var3, @Nullable float[] var4, long var5);

    public static native void invokePPPV(int var0, int var1, @Nullable long[] var2, @Nullable long[] var3, @Nullable long[] var4, long var5);

    public static native void invokePPPV(@Nullable float[] var0, int var1, long var2, long var4, int var6, long var7);

    public static native void invokePPPV(@Nullable float[] var0, boolean var1, int var2, long var3, long var5, int var7, long var8);

    public static native void invokePPPPPV(long var0, @Nullable int[] var2, @Nullable int[] var3, @Nullable int[] var4, @Nullable int[] var5, long var6);

    public static native void invokePPPPPV(long var0, int var2, long var3, @Nullable int[] var5, long var6, long var8, long var10);

    public static native void invokePPPPPV(int var0, long var1, int var3, @Nullable float[] var4, @Nullable float[] var5, long var6, int var8, long var9, int var11, boolean var12, long var13);

    public static native void invokePPPPPV(int var0, @Nullable int[] var1, int var2, @Nullable float[] var3, @Nullable float[] var4, long var5, int var7, @Nullable int[] var8, int var9, boolean var10, long var11);

    public static native void invokePPPPPV(int var0, @Nullable short[] var1, int var2, @Nullable float[] var3, @Nullable float[] var4, long var5, int var7, @Nullable short[] var8, int var9, boolean var10, long var11);

    public static native boolean invokePPZ(long var0, @Nullable int[] var2, long var3);

    public static native int callPI(@Nullable int[] var0, long var1);

    public static native int callPI(int var0, @Nullable int[] var1, long var2);

    public static native int callPI(@Nullable int[] var0, int var1, long var2);

    public static native int callPI(int var0, int var1, @Nullable int[] var2, long var3);

    public static native int callPI(int var0, @Nullable int[] var1, int var2, long var3);

    public static native int callPI(int var0, int var1, int var2, @Nullable int[] var3, long var4);

    public static native int callPI(int var0, int var1, int var2, int var3, @Nullable float[] var4, long var5);

    public static native int callPI(int var0, int var1, int var2, int var3, @Nullable int[] var4, long var5);

    public static native int callPPI(long var0, @Nullable int[] var2, long var3);

    public static native int callPPI(long var0, @Nullable long[] var2, long var3);

    public static native int callPPI(@Nullable int[] var0, long var1, long var3);

    public static native int callPPI(int var0, long var1, @Nullable int[] var3, long var4);

    public static native int callPPI(long var0, int var2, @Nullable int[] var3, long var4);

    public static native int callPPI(long var0, int var2, @Nullable long[] var3, long var4);

    public static native int callPPI(long var0, int var2, int var3, @Nullable int[] var4, long var5);

    public static native int callPJPI(long var0, long var2, @Nullable int[] var4, long var5);

    public static native int callPPPI(long var0, long var2, @Nullable int[] var4, long var5);

    public static native int callPPPI(long var0, long var2, @Nullable long[] var4, long var5);

    public static native int callPPPI(long var0, @Nullable int[] var2, long var3, long var5);

    public static native int callPPPI(long var0, @Nullable int[] var2, @Nullable int[] var3, long var4);

    public static native int callPPPI(long var0, @Nullable long[] var2, @Nullable long[] var3, long var4);

    public static native int callPPPI(@Nullable int[] var0, long var1, @Nullable int[] var3, long var4);

    public static native int callPJPI(long var0, int var2, long var3, @Nullable int[] var5, long var6);

    public static native int callPJPI(long var0, long var2, int var4, @Nullable long[] var5, long var6);

    public static native int callPPPI(long var0, int var2, long var3, @Nullable double[] var5, long var6);

    public static native int callPPPI(long var0, int var2, long var3, @Nullable float[] var5, long var6);

    public static native int callPPPI(long var0, int var2, long var3, @Nullable int[] var5, long var6);

    public static native int callPPPI(long var0, int var2, long var3, @Nullable long[] var5, long var6);

    public static native int callPPPI(long var0, int var2, long var3, @Nullable short[] var5, long var6);

    public static native int callPPPI(long var0, int var2, @Nullable int[] var3, @Nullable int[] var4, long var5);

    public static native int callPPPI(long var0, int var2, @Nullable int[] var3, @Nullable long[] var4, long var5);

    public static native int callPPPI(long var0, long var2, int var4, @Nullable int[] var5, long var6);

    public static native int callPPPI(long var0, long var2, int var4, @Nullable long[] var5, long var6);

    public static native int callPPPI(long var0, long var2, @Nullable int[] var4, int var5, long var6);

    public static native int callPPJI(long var0, int var2, @Nullable long[] var3, int var4, long var5, long var7);

    public static native int callPPPI(long var0, int var2, int var3, int var4, @Nullable int[] var5, @Nullable float[] var6, long var7);

    public static native int callPPPI(long var0, int var2, int var3, int var4, @Nullable int[] var5, @Nullable int[] var6, long var7);

    public static native int callPJPPI(long var0, long var2, @Nullable int[] var4, long var5, long var7);

    public static native int callPJPPI(long var0, long var2, @Nullable int[] var4, @Nullable int[] var5, long var6);

    public static native int callPJPPI(long var0, long var2, @Nullable int[] var4, @Nullable long[] var5, long var6);

    public static native int callPPPPI(long var0, long var2, long var4, @Nullable long[] var6, long var7);

    public static native int callPPPPI(long var0, long var2, @Nullable int[] var4, long var5, long var7);

    public static native int callPPPPI(long var0, long var2, @Nullable int[] var4, @Nullable int[] var5, long var6);

    public static native int callPJPPI(long var0, long var2, int var4, long var5, @Nullable int[] var7, long var8);

    public static native int callPJPPI(long var0, long var2, int var4, @Nullable int[] var5, @Nullable int[] var6, long var7);

    public static native int callPPPPI(long var0, int var2, long var3, long var5, @Nullable long[] var7, long var8);

    public static native int callPPPPI(long var0, int var2, long var3, @Nullable int[] var5, long var6, long var8);

    public static native int callPPPPI(long var0, int var2, long var3, @Nullable long[] var5, long var6, long var8);

    public static native int callPPPPI(long var0, int var2, long var3, @Nullable long[] var5, @Nullable long[] var6, long var7);

    public static native int callPPPPI(long var0, long var2, int var4, long var5, @Nullable int[] var7, long var8);

    public static native int callPPPPI(long var0, long var2, long var4, int var6, @Nullable int[] var7, long var8);

    public static native int callPPPPI(long var0, @Nullable int[] var2, long var3, int var5, @Nullable int[] var6, long var7);

    public static native int callPPPPI(long var0, @Nullable long[] var2, int var3, long var4, @Nullable int[] var6, long var7);

    public static native int callPJPPI(long var0, long var2, int var4, int var5, long var6, @Nullable int[] var8, long var9);

    public static native int callPPPPI(long var0, int var2, int var3, long var4, @Nullable int[] var6, long var7, long var9);

    public static native int callPPPPI(long var0, int var2, int var3, long var4, @Nullable long[] var6, long var7, long var9);

    public static native int callPPPPI(long var0, int var2, int var3, @Nullable long[] var4, @Nullable int[] var5, @Nullable int[] var6, long var7);

    public static native int callPPPPI(long var0, int var2, long var3, int var5, int var6, long var7, @Nullable int[] var9, long var10);

    public static native int callPJPPPI(long var0, long var2, long var4, long var6, @Nullable long[] var8, long var9);

    public static native int callPPPPPI(long var0, long var2, @Nullable int[] var4, @Nullable int[] var5, @Nullable int[] var6, long var7);

    public static native int callPPPPPI(long var0, long var2, @Nullable int[] var4, @Nullable int[] var5, @Nullable long[] var6, long var7);

    public static native int callPPPPPI(long var0, @Nullable int[] var2, @Nullable int[] var3, @Nullable int[] var4, long var5, long var7);

    public static native int callPJPPPI(long var0, long var2, int var4, long var5, long var7, @Nullable long[] var9, long var10);

    public static native int callPPPPPI(long var0, long var2, int var4, long var5, @Nullable int[] var7, long var8, long var10);

    public static native int callPPPPPI(long var0, long var2, int var4, long var5, @Nullable long[] var7, long var8, long var10);

    public static native int callPPPPPI(long var0, @Nullable int[] var2, @Nullable float[] var3, int var4, @Nullable int[] var5, @Nullable int[] var6, long var7);

    public static native int callPPPPPI(int var0, int var1, @Nullable int[] var2, @Nullable int[] var3, @Nullable int[] var4, @Nullable int[] var5, long var6, long var8);

    public static native int callPJPPJI(long var0, long var2, int var4, int var5, long var6, @Nullable int[] var8, long var9, int var11, long var12);

    public static native int callPJPPJI(long var0, long var2, int var4, int var5, long var6, @Nullable long[] var8, long var9, int var11, long var12);

    public static native int callPJJJJPI(long var0, long var2, long var4, long var6, long var8, @Nullable int[] var10, long var11);

    public static native int callPPPPPPI(long var0, @Nullable int[] var2, @Nullable int[] var3, @Nullable int[] var4, @Nullable int[] var5, @Nullable int[] var6, long var7);

    public static native int callPPPPPPI(int var0, int var1, @Nullable int[] var2, @Nullable int[] var3, @Nullable int[] var4, @Nullable int[] var5, @Nullable int[] var6, long var7, long var9);

    public static native int callPPPPPPPI(long var0, long var2, long var4, long var6, int var8, long var9, @Nullable int[] var11, long var12, long var14);

    public static native int callPPPPPPPI(long var0, long var2, @Nullable float[] var4, long var5, long var7, int var9, long var10, long var12, long var14);

    public static native int callPPPPPPPI(long var0, long var2, @Nullable int[] var4, long var5, long var7, int var9, long var10, long var12, long var14);

    public static native int callPPPPPPPI(long var0, long var2, int var4, long var5, long var7, @Nullable double[] var9, int var10, long var11, long var13, long var15);

    public static native int callPPPPPPPI(long var0, long var2, int var4, long var5, long var7, @Nullable float[] var9, int var10, long var11, long var13, long var15);

    public static native int callPPPPPPPI(long var0, long var2, int var4, long var5, long var7, @Nullable int[] var9, int var10, long var11, long var13, long var15);

    public static native int callPPPPPPPI(long var0, long var2, int var4, long var5, long var7, @Nullable short[] var9, int var10, long var11, long var13, long var15);

    public static native int callPPPPPPPPPI(long var0, long var2, int var4, long var5, long var7, long var9, long var11, @Nullable double[] var13, int var14, long var15, long var17, long var19);

    public static native int callPPPPPPPPPI(long var0, long var2, int var4, long var5, long var7, long var9, long var11, @Nullable float[] var13, int var14, long var15, long var17, long var19);

    public static native int callPPPPPPPPPI(long var0, long var2, int var4, long var5, long var7, long var9, long var11, @Nullable int[] var13, int var14, long var15, long var17, long var19);

    public static native int callPPPPPPPPPI(long var0, long var2, int var4, long var5, long var7, long var9, long var11, @Nullable short[] var13, int var14, long var15, long var17, long var19);

    public static native int callPPPPPPPPPPPPI(long var0, long var2, int var4, long var5, long var7, long var9, long var11, long var13, long var15, long var17, @Nullable double[] var19, int var20, long var21, long var23, long var25);

    public static native int callPPPPPPPPPPPPI(long var0, long var2, int var4, long var5, long var7, long var9, long var11, long var13, long var15, long var17, @Nullable float[] var19, int var20, long var21, long var23, long var25);

    public static native int callPPPPPPPPPPPPI(long var0, long var2, int var4, long var5, long var7, long var9, long var11, long var13, long var15, long var17, @Nullable int[] var19, int var20, long var21, long var23, long var25);

    public static native int callPPPPPPPPPPPPI(long var0, long var2, int var4, long var5, long var7, long var9, long var11, long var13, long var15, long var17, @Nullable short[] var19, int var20, long var21, long var23, long var25);

    public static native long callPP(@Nullable int[] var0, long var1);

    public static native long callPPP(long var0, @Nullable int[] var2, long var3);

    public static native long callPPP(int var0, long var1, @Nullable int[] var3, long var4);

    public static native long callPPP(long var0, int var2, @Nullable int[] var3, long var4);

    public static native long callPPP(int var0, int var1, int var2, @Nullable int[] var3, @Nullable int[] var4, long var5);

    public static native long callPPP(long var0, int var2, int var3, int var4, @Nullable int[] var5, long var6);

    public static native long callPPPP(long var0, long var2, @Nullable int[] var4, long var5);

    public static native long callPPPP(long var0, @Nullable int[] var2, @Nullable int[] var3, long var4);

    public static native long callPJPP(long var0, long var2, int var4, @Nullable int[] var5, long var6);

    public static native long callPPPP(long var0, int var2, @Nullable int[] var3, long var4, long var6);

    public static native long callPPPP(long var0, long var2, int var4, @Nullable int[] var5, long var6);

    public static native long callPPPP(long var0, long var2, int var4, int var5, @Nullable int[] var6, long var7);

    public static native long callPJPP(long var0, long var2, int var4, int var5, int var6, @Nullable int[] var7, long var8);

    public static native long callPPJPP(long var0, long var2, long var4, @Nullable int[] var6, long var7);

    public static native long callPPPPP(long var0, long var2, long var4, @Nullable int[] var6, long var7);

    public static native long callPPPPP(long var0, long var2, @Nullable long[] var4, @Nullable int[] var5, long var6);

    public static native long callPJPPP(long var0, long var2, int var4, long var5, @Nullable int[] var7, long var8);

    public static native long callPJPPP(long var0, long var2, @Nullable int[] var4, int var5, @Nullable int[] var6, long var7);

    public static native long callPPPPP(long var0, int var2, long var3, long var5, @Nullable int[] var7, long var8);

    public static native long callPPPPP(long var0, long var2, int var4, long var5, @Nullable int[] var7, long var8);

    public static native long callPPPPP(long var0, long var2, long var4, int var6, @Nullable int[] var7, long var8);

    public static native long callPJPPP(long var0, long var2, int var4, int var5, @Nullable int[] var6, @Nullable int[] var7, long var8);

    public static native long callPJPPPP(long var0, long var2, long var4, long var6, @Nullable int[] var8, long var9);

    public static native long callPJPPPP(long var0, long var2, long var4, @Nullable double[] var6, @Nullable int[] var7, long var8);

    public static native long callPJPPPP(long var0, long var2, long var4, @Nullable float[] var6, @Nullable int[] var7, long var8);

    public static native long callPJPPPP(long var0, long var2, long var4, @Nullable int[] var6, @Nullable int[] var7, long var8);

    public static native long callPJPPPP(long var0, long var2, long var4, @Nullable short[] var6, @Nullable int[] var7, long var8);

    public static native long callPPPPPP(long var0, int var2, long var3, long var5, long var7, @Nullable int[] var9, long var10);

    public static native long callPJPPPPP(long var0, long var2, long var4, long var6, long var8, @Nullable int[] var10, long var11);

    public static native long callPJPPPPP(long var0, long var2, long var4, long var6, @Nullable float[] var8, @Nullable int[] var9, long var10);

    public static native long callPJPPPPP(long var0, long var2, long var4, long var6, @Nullable int[] var8, @Nullable int[] var9, long var10);

    public static native long callPJPPPPP(long var0, long var2, long var4, long var6, @Nullable short[] var8, @Nullable int[] var9, long var10);

    public static native long callPPPJPPP(long var0, long var2, long var4, long var6, long var8, @Nullable int[] var10, long var11);

    public static native long callPPPPPPP(long var0, int var2, long var3, long var5, long var7, @Nullable int[] var9, @Nullable int[] var10, long var11);

    public static native long callPJPPPPPPP(long var0, long var2, long var4, long var6, long var8, long var10, long var12, @Nullable int[] var14, long var15);

    public static native long callPJPPPPPPP(long var0, long var2, long var4, long var6, long var8, long var10, @Nullable float[] var12, @Nullable int[] var13, long var14);

    public static native long callPJPPPPPPP(long var0, long var2, long var4, long var6, long var8, long var10, @Nullable int[] var12, @Nullable int[] var13, long var14);

    public static native long callPJPPPPPPP(long var0, long var2, long var4, long var6, long var8, long var10, @Nullable short[] var12, @Nullable int[] var13, long var14);

    public static native long callPPJPPPPPP(long var0, long var2, int var4, long var5, long var7, long var9, int var11, long var12, long var14, @Nullable int[] var16, long var17);

    public static native long callPJPPPPPPPPP(long var0, long var2, long var4, long var6, long var8, long var10, long var12, long var14, long var16, @Nullable int[] var18, long var19);

    public static native long callPJPPPPPPPPP(long var0, long var2, long var4, long var6, long var8, long var10, long var12, long var14, @Nullable float[] var16, @Nullable int[] var17, long var18);

    public static native long callPJPPPPPPPPP(long var0, long var2, long var4, long var6, long var8, long var10, long var12, long var14, @Nullable int[] var16, @Nullable int[] var17, long var18);

    public static native long callPJPPPPPPPPP(long var0, long var2, long var4, long var6, long var8, long var10, long var12, long var14, @Nullable short[] var16, @Nullable int[] var17, long var18);

    public static native long callPPJPPPPPPPP(long var0, long var2, int var4, long var5, long var7, long var9, long var11, long var13, int var15, long var16, long var18, @Nullable int[] var20, long var21);

    public static native void callPV(@Nullable double[] var0, long var1);

    public static native void callPV(@Nullable float[] var0, long var1);

    public static native void callPV(@Nullable int[] var0, long var1);

    public static native void callPV(@Nullable short[] var0, long var1);

    public static native void callPV(int var0, @Nullable double[] var1, long var2);

    public static native void callPV(int var0, @Nullable float[] var1, long var2);

    public static native void callPV(int var0, @Nullable int[] var1, long var2);

    public static native void callPV(int var0, @Nullable long[] var1, long var2);

    public static native void callPV(int var0, @Nullable short[] var1, long var2);

    public static native void callPV(int var0, int var1, @Nullable double[] var2, long var3);

    public static native void callPV(int var0, int var1, @Nullable float[] var2, long var3);

    public static native void callPV(int var0, int var1, @Nullable int[] var2, long var3);

    public static native void callPV(int var0, int var1, @Nullable long[] var2, long var3);

    public static native void callPV(int var0, int var1, @Nullable short[] var2, long var3);

    public static native void callPV(int var0, @Nullable int[] var1, int var2, long var3);

    public static native void callPV(int var0, int var1, int var2, @Nullable double[] var3, long var4);

    public static native void callPV(int var0, int var1, int var2, @Nullable float[] var3, long var4);

    public static native void callPV(int var0, int var1, int var2, @Nullable int[] var3, long var4);

    public static native void callPV(int var0, int var1, int var2, @Nullable long[] var3, long var4);

    public static native void callPV(int var0, int var1, int var2, @Nullable short[] var3, long var4);

    public static native void callPV(int var0, int var1, boolean var2, @Nullable double[] var3, long var4);

    public static native void callPV(int var0, int var1, boolean var2, @Nullable float[] var3, long var4);

    public static native void callPV(int var0, int var1, boolean var2, @Nullable int[] var3, long var4);

    public static native void callPV(int var0, int var1, @Nullable int[] var2, int var3, long var4);

    public static native void callPV(int var0, @Nullable int[] var1, int var2, int var3, long var4);

    public static native void callPV(int var0, int var1, int var2, int var3, @Nullable double[] var4, long var5);

    public static native void callPV(int var0, int var1, int var2, int var3, @Nullable float[] var4, long var5);

    public static native void callPV(int var0, int var1, int var2, int var3, @Nullable int[] var4, long var5);

    public static native void callPV(int var0, int var1, int var2, int var3, @Nullable long[] var4, long var5);

    public static native void callPV(int var0, int var1, int var2, int var3, @Nullable short[] var4, long var5);

    public static native void callPV(int var0, int var1, int var2, boolean var3, @Nullable double[] var4, long var5);

    public static native void callPV(int var0, int var1, int var2, boolean var3, @Nullable float[] var4, long var5);

    public static native void callPV(int var0, int var1, int var2, @Nullable int[] var3, boolean var4, long var5);

    public static native void callPV(int var0, int var1, @Nullable int[] var2, int var3, int var4, long var5);

    public static native void callPV(int var0, boolean var1, int var2, int var3, @Nullable int[] var4, long var5);

    public static native void callPV(int var0, double var1, double var3, int var5, int var6, @Nullable double[] var7, long var8);

    public static native void callPV(int var0, float var1, float var2, int var3, int var4, @Nullable float[] var5, long var6);

    public static native void callPV(int var0, int var1, int var2, int var3, int var4, @Nullable double[] var5, long var6);

    public static native void callPV(int var0, int var1, int var2, int var3, int var4, @Nullable float[] var5, long var6);

    public static native void callPV(int var0, int var1, int var2, int var3, int var4, @Nullable int[] var5, long var6);

    public static native void callPV(int var0, int var1, int var2, int var3, int var4, @Nullable short[] var5, long var6);

    public static native void callPV(int var0, int var1, int var2, int var3, @Nullable int[] var4, boolean var5, long var6);

    public static native void callPV(int var0, int var1, int var2, boolean var3, int var4, @Nullable float[] var5, long var6);

    public static native void callPV(int var0, int var1, int var2, boolean var3, int var4, @Nullable int[] var5, long var6);

    public static native void callPV(int var0, int var1, int var2, boolean var3, int var4, @Nullable short[] var5, long var6);

    public static native void callPV(int var0, int var1, int var2, int var3, int var4, int var5, @Nullable double[] var6, long var7);

    public static native void callPV(int var0, int var1, int var2, int var3, int var4, int var5, @Nullable float[] var6, long var7);

    public static native void callPV(int var0, int var1, int var2, int var3, int var4, int var5, @Nullable int[] var6, long var7);

    public static native void callPV(int var0, int var1, int var2, int var3, int var4, int var5, @Nullable short[] var6, long var7);

    public static native void callPV(int var0, int var1, @Nullable int[] var2, int var3, int var4, int var5, int var6, long var7);

    public static native void callPV(int var0, int var1, int var2, int var3, int var4, int var5, int var6, @Nullable double[] var7, long var8);

    public static native void callPV(int var0, int var1, int var2, int var3, int var4, int var5, int var6, @Nullable float[] var7, long var8);

    public static native void callPV(int var0, int var1, int var2, int var3, int var4, int var5, int var6, @Nullable int[] var7, long var8);

    public static native void callPV(int var0, int var1, int var2, int var3, int var4, int var5, int var6, @Nullable short[] var7, long var8);

    public static native void callPV(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, @Nullable double[] var8, long var9);

    public static native void callPV(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, @Nullable float[] var8, long var9);

    public static native void callPV(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, @Nullable int[] var8, long var9);

    public static native void callPV(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, @Nullable short[] var8, long var9);

    public static native void callPV(int var0, double var1, double var3, int var5, int var6, double var7, double var9, int var11, int var12, @Nullable double[] var13, long var14);

    public static native void callPV(int var0, float var1, float var2, int var3, int var4, float var5, float var6, int var7, int var8, @Nullable float[] var9, long var10);

    public static native void callPV(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, @Nullable double[] var9, long var10);

    public static native void callPV(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, @Nullable float[] var9, long var10);

    public static native void callPV(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, @Nullable int[] var9, long var10);

    public static native void callPV(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, @Nullable short[] var9, long var10);

    public static native void callPV(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, @Nullable double[] var10, long var11);

    public static native void callPV(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, @Nullable float[] var10, long var11);

    public static native void callPV(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, @Nullable int[] var10, long var11);

    public static native void callPV(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, @Nullable short[] var10, long var11);

    public static native void callPV(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, @Nullable double[] var11, long var12);

    public static native void callPV(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, @Nullable float[] var11, long var12);

    public static native void callPV(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, @Nullable int[] var11, long var12);

    public static native void callPV(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, @Nullable short[] var11, long var12);

    public static native void callPPV(long var0, @Nullable float[] var2, long var3);

    public static native void callPPV(long var0, @Nullable int[] var2, long var3);

    public static native void callPPV(@Nullable double[] var0, @Nullable double[] var1, long var2);

    public static native void callPPV(@Nullable float[] var0, @Nullable float[] var1, long var2);

    public static native void callPPV(@Nullable int[] var0, @Nullable int[] var1, long var2);

    public static native void callPPV(@Nullable short[] var0, @Nullable short[] var1, long var2);

    public static native void callPPV(int var0, long var1, @Nullable int[] var3, long var4);

    public static native void callPPV(int var0, @Nullable int[] var1, @Nullable float[] var2, long var3);

    public static native void callPPV(int var0, @Nullable int[] var1, @Nullable int[] var2, long var3);

    public static native void callPPV(@Nullable int[] var0, int var1, @Nullable int[] var2, long var3);

    public static native void callPPV(int var0, int var1, long var2, @Nullable int[] var4, long var5);

    public static native void callPPV(int var0, int var1, @Nullable int[] var2, long var3, long var5);

    public static native void callPPV(int var0, int var1, @Nullable int[] var2, @Nullable float[] var3, long var4);

    public static native void callPPV(int var0, int var1, @Nullable int[] var2, @Nullable int[] var3, long var4);

    public static native void callPPV(int var0, long var1, int var3, @Nullable int[] var4, long var5);

    public static native void callPPV(int var0, long var1, @Nullable double[] var3, int var4, long var5);

    public static native void callPPV(int var0, long var1, @Nullable float[] var3, int var4, long var5);

    public static native void callPPV(int var0, long var1, @Nullable int[] var3, int var4, long var5);

    public static native void callPPV(int var0, long var1, @Nullable long[] var3, int var4, long var5);

    public static native void callPPV(int var0, long var1, @Nullable short[] var3, int var4, long var5);

    public static native void callPPV(int var0, @Nullable int[] var1, @Nullable int[] var2, int var3, long var4);

    public static native void callPPV(int var0, @Nullable long[] var1, @Nullable int[] var2, int var3, long var4);

    public static native void callPPV(int var0, int var1, int var2, long var3, @Nullable int[] var5, long var6);

    public static native void callPPV(int var0, int var1, int var2, @Nullable int[] var3, long var4, long var6);

    public static native void callPPV(int var0, int var1, int var2, @Nullable int[] var3, @Nullable int[] var4, long var5);

    public static native void callPPV(int var0, int var1, long var2, @Nullable int[] var4, int var5, long var6);

    public static native void callPPV(int var0, int var1, @Nullable int[] var2, int var3, @Nullable int[] var4, long var5);

    public static native void callPPV(int var0, @Nullable int[] var1, int var2, long var3, int var5, long var6);

    public static native void callPPV(int var0, @Nullable int[] var1, int var2, @Nullable int[] var3, int var4, long var5);

    public static native void callPPV(int var0, @Nullable int[] var1, long var2, int var4, int var5, long var6);

    public static native void callPPV(long var0, int var2, int var3, int var4, @Nullable int[] var5, long var6);

    public static native void callPPV(int var0, int var1, int var2, int var3, @Nullable int[] var4, long var5, long var7);

    public static native void callPPV(int var0, int var1, long var2, int var4, int var5, @Nullable float[] var6, long var7);

    public static native void callPPV(int var0, int var1, long var2, int var4, int var5, @Nullable short[] var6, long var7);

    public static native void callPPV(int var0, int var1, @Nullable int[] var2, long var3, int var5, int var6, long var7);

    public static native void callPPV(int var0, int var1, int var2, long var3, int var5, int var6, @Nullable float[] var7, long var8);

    public static native void callPPV(int var0, int var1, long var2, int var4, int var5, int var6, @Nullable float[] var7, long var8);

    public static native void callPPV(int var0, int var1, int var2, int var3, long var4, int var6, int var7, @Nullable float[] var8, long var9);

    public static native void callPPV(int var0, int var1, int var2, int var3, long var4, int var6, int var7, @Nullable short[] var8, long var9);

    public static native void callPPV(int var0, int var1, long var2, int var4, int var5, int var6, int var7, @Nullable float[] var8, long var9);

    public static native void callPPV(int var0, int var1, int var2, long var3, int var5, float var6, float var7, int var8, @Nullable float[] var9, long var10);

    public static native void callPPV(int var0, int var1, long var2, int var4, int var5, int var6, int var7, int var8, @Nullable float[] var9, long var10);

    public static native void callPJPV(long var0, long var2, @Nullable long[] var4, long var5);

    public static native void callPPPV(long var0, @Nullable int[] var2, long var3, long var5);

    public static native void callPPPV(int var0, long var1, long var3, @Nullable double[] var5, long var6);

    public static native void callPPPV(int var0, long var1, long var3, @Nullable float[] var5, long var6);

    public static native void callPPPV(int var0, long var1, long var3, @Nullable int[] var5, long var6);

    public static native void callPPPV(int var0, long var1, long var3, @Nullable long[] var5, long var6);

    public static native void callPPPV(int var0, long var1, long var3, @Nullable short[] var5, long var6);

    public static native void callPPPV(long var0, int var2, @Nullable int[] var3, long var4, long var6);

    public static native void callPPPV(long var0, int var2, @Nullable long[] var3, long var4, long var6);

    public static native void callPPPV(long var0, long var2, int var4, @Nullable int[] var5, long var6);

    public static native void callPPPV(int var0, int var1, long var2, long var4, @Nullable double[] var6, long var7);

    public static native void callPPPV(int var0, int var1, long var2, long var4, @Nullable float[] var6, long var7);

    public static native void callPPPV(int var0, int var1, long var2, long var4, @Nullable int[] var6, long var7);

    public static native void callPPPV(int var0, int var1, long var2, long var4, @Nullable short[] var6, long var7);

    public static native void callPPPV(int var0, int var1, @Nullable int[] var2, long var3, @Nullable int[] var5, long var6);

    public static native void callPPPV(int var0, int var1, @Nullable int[] var2, @Nullable int[] var3, long var4, long var6);

    public static native void callPPPV(int var0, long var1, int var3, @Nullable int[] var4, long var5, long var7);

    public static native void callPPPV(int var0, long var1, int var3, @Nullable int[] var4, @Nullable int[] var5, long var6);

    public static native void callPPPV(int var0, @Nullable int[] var1, @Nullable int[] var2, int var3, @Nullable int[] var4, long var5);

    public static native void callPPPV(long var0, int var2, int var3, @Nullable int[] var4, @Nullable int[] var5, long var6);

    public static native void callPPPV(long var0, int var2, int var3, @Nullable long[] var4, @Nullable long[] var5, long var6);

    public static native void callPJPV(long var0, long var2, int var4, int var5, int var6, @Nullable double[] var7, long var8);

    public static native void callPJPV(long var0, long var2, int var4, int var5, int var6, @Nullable float[] var7, long var8);

    public static native void callPJPV(long var0, long var2, int var4, int var5, int var6, @Nullable int[] var7, long var8);

    public static native void callPJPV(long var0, long var2, int var4, int var5, int var6, @Nullable long[] var7, long var8);

    public static native void callPJPV(long var0, long var2, int var4, int var5, int var6, @Nullable short[] var7, long var8);

    public static native void callPPJV(long var0, int var2, @Nullable long[] var3, int var4, long var5, int var7, long var8);

    public static native void callPPPV(int var0, int var1, int var2, @Nullable int[] var3, long var4, long var6, long var8);

    public static native void callPPPV(int var0, int var1, int var2, @Nullable int[] var3, long var4, @Nullable int[] var6, long var7);

    public static native void callPPPV(int var0, int var1, @Nullable int[] var2, int var3, @Nullable int[] var4, @Nullable int[] var5, long var6);

    public static native void callPPPV(int var0, @Nullable int[] var1, int var2, long var3, int var5, @Nullable int[] var6, long var7);

    public static native void callPPPV(int var0, int var1, long var2, long var4, int var6, int var7, @Nullable float[] var8, long var9);

    public static native void callPPPV(int var0, int var1, long var2, long var4, int var6, int var7, @Nullable int[] var8, long var9);

    public static native void callPPPV(int var0, int var1, long var2, long var4, int var6, int var7, @Nullable short[] var8, long var9);

    public static native void callPPPV(int var0, int var1, int var2, int var3, @Nullable int[] var4, int var5, @Nullable int[] var6, @Nullable float[] var7, long var8);

    public static native void callPPPV(int var0, int var1, int var2, int var3, @Nullable int[] var4, int var5, @Nullable int[] var6, @Nullable int[] var7, long var8);

    public static native void callPPPV(long var0, int var2, int var3, int var4, int var5, int var6, @Nullable int[] var7, long var8, long var10);

    public static native void callPJPPV(long var0, long var2, @Nullable int[] var4, long var5, long var7);

    public static native void callPPPPV(long var0, long var2, @Nullable int[] var4, long var5, long var7);

    public static native void callPPPPV(@Nullable long[] var0, @Nullable int[] var1, @Nullable int[] var2, @Nullable int[] var3, int var4, long var5);

    public static native void callPPPPV(int var0, long var1, @Nullable int[] var3, @Nullable int[] var4, @Nullable int[] var5, int var6, long var7);

    public static native void callPPPPV(long var0, int var2, int var3, @Nullable long[] var4, @Nullable long[] var5, @Nullable long[] var6, long var7);

    public static native void callPPPPV(int var0, int var1, int var2, @Nullable int[] var3, @Nullable int[] var4, @Nullable int[] var5, long var6, long var8);

    public static native void callPPPPV(int var0, int var1, long var2, long var4, @Nullable int[] var6, @Nullable int[] var7, int var8, long var9);

    public static native void callPJPPV(long var0, int var2, long var3, int var5, int var6, @Nullable long[] var7, int var8, @Nullable int[] var9, long var10);

    public static native void callPJJJPV(long var0, long var2, long var4, long var6, @Nullable double[] var8, long var9);

    public static native void callPJJJPV(long var0, long var2, long var4, long var6, @Nullable float[] var8, long var9);

    public static native void callPJJJPV(long var0, long var2, long var4, long var6, @Nullable int[] var8, long var9);

    public static native void callPJJJPV(long var0, long var2, long var4, long var6, @Nullable long[] var8, long var9);

    public static native void callPJJJPV(long var0, long var2, long var4, long var6, @Nullable short[] var8, long var9);

    public static native void callPPPPPV(int var0, int var1, long var2, @Nullable int[] var4, @Nullable int[] var5, @Nullable int[] var6, @Nullable int[] var7, long var8);

    public static native void callPPPPPV(long var0, int var2, @Nullable long[] var3, int var4, int var5, int var6, long var7, int var9, long var10, int var12, long var13, long var15);

    public static native void callPPPPPPPV(int var0, int var1, int var2, long var3, int var5, long var6, @Nullable int[] var8, @Nullable int[] var9, @Nullable int[] var10, @Nullable int[] var11, @Nullable long[] var12, long var13);

    public static native boolean callPPZ(int var0, @Nullable int[] var1, long var2, long var4);

    public static native boolean callPPPPZ(int var0, int var1, int var2, float var3, @Nullable float[] var4, @Nullable float[] var5, @Nullable float[] var6, @Nullable float[] var7, long var8);

    static {
        Library.initialize();
    }
}

