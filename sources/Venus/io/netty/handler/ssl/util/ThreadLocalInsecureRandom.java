/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl.util;

import io.netty.util.internal.PlatformDependent;
import java.security.SecureRandom;
import java.util.Random;

final class ThreadLocalInsecureRandom
extends SecureRandom {
    private static final long serialVersionUID = -8209473337192526191L;
    private static final SecureRandom INSTANCE = new ThreadLocalInsecureRandom();

    static SecureRandom current() {
        return INSTANCE;
    }

    private ThreadLocalInsecureRandom() {
    }

    @Override
    public String getAlgorithm() {
        return "insecure";
    }

    @Override
    public void setSeed(byte[] byArray) {
    }

    @Override
    public void setSeed(long l) {
    }

    @Override
    public void nextBytes(byte[] byArray) {
        ThreadLocalInsecureRandom.random().nextBytes(byArray);
    }

    @Override
    public byte[] generateSeed(int n) {
        byte[] byArray = new byte[n];
        ThreadLocalInsecureRandom.random().nextBytes(byArray);
        return byArray;
    }

    @Override
    public int nextInt() {
        return ThreadLocalInsecureRandom.random().nextInt();
    }

    @Override
    public int nextInt(int n) {
        return ThreadLocalInsecureRandom.random().nextInt(n);
    }

    @Override
    public boolean nextBoolean() {
        return ThreadLocalInsecureRandom.random().nextBoolean();
    }

    @Override
    public long nextLong() {
        return ThreadLocalInsecureRandom.random().nextLong();
    }

    @Override
    public float nextFloat() {
        return ThreadLocalInsecureRandom.random().nextFloat();
    }

    @Override
    public double nextDouble() {
        return ThreadLocalInsecureRandom.random().nextDouble();
    }

    @Override
    public double nextGaussian() {
        return ThreadLocalInsecureRandom.random().nextGaussian();
    }

    private static Random random() {
        return PlatformDependent.threadLocalRandom();
    }
}

