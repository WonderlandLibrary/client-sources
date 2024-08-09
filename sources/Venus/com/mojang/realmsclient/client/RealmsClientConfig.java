/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.client;

import java.net.Proxy;

public class RealmsClientConfig {
    private static Proxy field_224897_a;

    public static Proxy func_224895_a() {
        return field_224897_a;
    }

    public static void func_224896_a(Proxy proxy) {
        if (field_224897_a == null) {
            field_224897_a = proxy;
        }
    }
}

