/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class EndianUtils {
    public static short swapShort(short s) {
        return (short)(((s >> 0 & 0xFF) << 8) + ((s >> 8 & 0xFF) << 0));
    }

    public static int swapInteger(int n) {
        return ((n >> 0 & 0xFF) << 24) + ((n >> 8 & 0xFF) << 16) + ((n >> 16 & 0xFF) << 8) + ((n >> 24 & 0xFF) << 0);
    }

    public static long swapLong(long l) {
        return ((l >> 0 & 0xFFL) << 56) + ((l >> 8 & 0xFFL) << 48) + ((l >> 16 & 0xFFL) << 40) + ((l >> 24 & 0xFFL) << 32) + ((l >> 32 & 0xFFL) << 24) + ((l >> 40 & 0xFFL) << 16) + ((l >> 48 & 0xFFL) << 8) + ((l >> 56 & 0xFFL) << 0);
    }

    public static float swapFloat(float f) {
        return Float.intBitsToFloat(EndianUtils.swapInteger(Float.floatToIntBits(f)));
    }

    public static double swapDouble(double d) {
        return Double.longBitsToDouble(EndianUtils.swapLong(Double.doubleToLongBits(d)));
    }

    public static void writeSwappedShort(byte[] byArray, int n, short s) {
        byArray[n + 0] = (byte)(s >> 0 & 0xFF);
        byArray[n + 1] = (byte)(s >> 8 & 0xFF);
    }

    public static short readSwappedShort(byte[] byArray, int n) {
        return (short)(((byArray[n + 0] & 0xFF) << 0) + ((byArray[n + 1] & 0xFF) << 8));
    }

    public static int readSwappedUnsignedShort(byte[] byArray, int n) {
        return ((byArray[n + 0] & 0xFF) << 0) + ((byArray[n + 1] & 0xFF) << 8);
    }

    public static void writeSwappedInteger(byte[] byArray, int n, int n2) {
        byArray[n + 0] = (byte)(n2 >> 0 & 0xFF);
        byArray[n + 1] = (byte)(n2 >> 8 & 0xFF);
        byArray[n + 2] = (byte)(n2 >> 16 & 0xFF);
        byArray[n + 3] = (byte)(n2 >> 24 & 0xFF);
    }

    public static int readSwappedInteger(byte[] byArray, int n) {
        return ((byArray[n + 0] & 0xFF) << 0) + ((byArray[n + 1] & 0xFF) << 8) + ((byArray[n + 2] & 0xFF) << 16) + ((byArray[n + 3] & 0xFF) << 24);
    }

    public static long readSwappedUnsignedInteger(byte[] byArray, int n) {
        long l = ((byArray[n + 0] & 0xFF) << 0) + ((byArray[n + 1] & 0xFF) << 8) + ((byArray[n + 2] & 0xFF) << 16);
        long l2 = byArray[n + 3] & 0xFF;
        return (l2 << 24) + (0xFFFFFFFFL & l);
    }

    public static void writeSwappedLong(byte[] byArray, int n, long l) {
        byArray[n + 0] = (byte)(l >> 0 & 0xFFL);
        byArray[n + 1] = (byte)(l >> 8 & 0xFFL);
        byArray[n + 2] = (byte)(l >> 16 & 0xFFL);
        byArray[n + 3] = (byte)(l >> 24 & 0xFFL);
        byArray[n + 4] = (byte)(l >> 32 & 0xFFL);
        byArray[n + 5] = (byte)(l >> 40 & 0xFFL);
        byArray[n + 6] = (byte)(l >> 48 & 0xFFL);
        byArray[n + 7] = (byte)(l >> 56 & 0xFFL);
    }

    public static long readSwappedLong(byte[] byArray, int n) {
        long l = EndianUtils.readSwappedInteger(byArray, n);
        long l2 = EndianUtils.readSwappedInteger(byArray, n + 4);
        return (l2 << 32) + (0xFFFFFFFFL & l);
    }

    public static void writeSwappedFloat(byte[] byArray, int n, float f) {
        EndianUtils.writeSwappedInteger(byArray, n, Float.floatToIntBits(f));
    }

    public static float readSwappedFloat(byte[] byArray, int n) {
        return Float.intBitsToFloat(EndianUtils.readSwappedInteger(byArray, n));
    }

    public static void writeSwappedDouble(byte[] byArray, int n, double d) {
        EndianUtils.writeSwappedLong(byArray, n, Double.doubleToLongBits(d));
    }

    public static double readSwappedDouble(byte[] byArray, int n) {
        return Double.longBitsToDouble(EndianUtils.readSwappedLong(byArray, n));
    }

    public static void writeSwappedShort(OutputStream outputStream, short s) throws IOException {
        outputStream.write((byte)(s >> 0 & 0xFF));
        outputStream.write((byte)(s >> 8 & 0xFF));
    }

    public static short readSwappedShort(InputStream inputStream) throws IOException {
        return (short)(((EndianUtils.read(inputStream) & 0xFF) << 0) + ((EndianUtils.read(inputStream) & 0xFF) << 8));
    }

    public static int readSwappedUnsignedShort(InputStream inputStream) throws IOException {
        int n = EndianUtils.read(inputStream);
        int n2 = EndianUtils.read(inputStream);
        return ((n & 0xFF) << 0) + ((n2 & 0xFF) << 8);
    }

    public static void writeSwappedInteger(OutputStream outputStream, int n) throws IOException {
        outputStream.write((byte)(n >> 0 & 0xFF));
        outputStream.write((byte)(n >> 8 & 0xFF));
        outputStream.write((byte)(n >> 16 & 0xFF));
        outputStream.write((byte)(n >> 24 & 0xFF));
    }

    public static int readSwappedInteger(InputStream inputStream) throws IOException {
        int n = EndianUtils.read(inputStream);
        int n2 = EndianUtils.read(inputStream);
        int n3 = EndianUtils.read(inputStream);
        int n4 = EndianUtils.read(inputStream);
        return ((n & 0xFF) << 0) + ((n2 & 0xFF) << 8) + ((n3 & 0xFF) << 16) + ((n4 & 0xFF) << 24);
    }

    public static long readSwappedUnsignedInteger(InputStream inputStream) throws IOException {
        int n = EndianUtils.read(inputStream);
        int n2 = EndianUtils.read(inputStream);
        int n3 = EndianUtils.read(inputStream);
        int n4 = EndianUtils.read(inputStream);
        long l = ((n & 0xFF) << 0) + ((n2 & 0xFF) << 8) + ((n3 & 0xFF) << 16);
        long l2 = n4 & 0xFF;
        return (l2 << 24) + (0xFFFFFFFFL & l);
    }

    public static void writeSwappedLong(OutputStream outputStream, long l) throws IOException {
        outputStream.write((byte)(l >> 0 & 0xFFL));
        outputStream.write((byte)(l >> 8 & 0xFFL));
        outputStream.write((byte)(l >> 16 & 0xFFL));
        outputStream.write((byte)(l >> 24 & 0xFFL));
        outputStream.write((byte)(l >> 32 & 0xFFL));
        outputStream.write((byte)(l >> 40 & 0xFFL));
        outputStream.write((byte)(l >> 48 & 0xFFL));
        outputStream.write((byte)(l >> 56 & 0xFFL));
    }

    public static long readSwappedLong(InputStream inputStream) throws IOException {
        byte[] byArray = new byte[8];
        for (int i = 0; i < 8; ++i) {
            byArray[i] = (byte)EndianUtils.read(inputStream);
        }
        return EndianUtils.readSwappedLong(byArray, 0);
    }

    public static void writeSwappedFloat(OutputStream outputStream, float f) throws IOException {
        EndianUtils.writeSwappedInteger(outputStream, Float.floatToIntBits(f));
    }

    public static float readSwappedFloat(InputStream inputStream) throws IOException {
        return Float.intBitsToFloat(EndianUtils.readSwappedInteger(inputStream));
    }

    public static void writeSwappedDouble(OutputStream outputStream, double d) throws IOException {
        EndianUtils.writeSwappedLong(outputStream, Double.doubleToLongBits(d));
    }

    public static double readSwappedDouble(InputStream inputStream) throws IOException {
        return Double.longBitsToDouble(EndianUtils.readSwappedLong(inputStream));
    }

    private static int read(InputStream inputStream) throws IOException {
        int n = inputStream.read();
        if (-1 == n) {
            throw new EOFException("Unexpected EOF reached");
        }
        return n;
    }
}

