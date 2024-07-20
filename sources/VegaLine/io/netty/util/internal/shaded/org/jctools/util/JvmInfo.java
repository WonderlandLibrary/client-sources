/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.util;

import io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;

public interface JvmInfo {
    public static final int CACHE_LINE_SIZE = Integer.getInteger("jctools.cacheLineSize", 64);
    public static final int PAGE_SIZE = UnsafeAccess.UNSAFE.pageSize();
    public static final int CPUs = Runtime.getRuntime().availableProcessors();
}

