/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.client;

import org.apache.http.config.Lookup;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.impl.cookie.DefaultCookieSpecProvider;
import org.apache.http.impl.cookie.IgnoreSpecProvider;
import org.apache.http.impl.cookie.NetscapeDraftSpecProvider;
import org.apache.http.impl.cookie.RFC6265CookieSpecProvider;

public final class CookieSpecRegistries {
    public static RegistryBuilder<CookieSpecProvider> createDefaultBuilder(PublicSuffixMatcher publicSuffixMatcher) {
        DefaultCookieSpecProvider defaultCookieSpecProvider = new DefaultCookieSpecProvider(publicSuffixMatcher);
        RFC6265CookieSpecProvider rFC6265CookieSpecProvider = new RFC6265CookieSpecProvider(RFC6265CookieSpecProvider.CompatibilityLevel.RELAXED, publicSuffixMatcher);
        RFC6265CookieSpecProvider rFC6265CookieSpecProvider2 = new RFC6265CookieSpecProvider(RFC6265CookieSpecProvider.CompatibilityLevel.STRICT, publicSuffixMatcher);
        return RegistryBuilder.create().register("default", defaultCookieSpecProvider).register("best-match", defaultCookieSpecProvider).register("compatibility", defaultCookieSpecProvider).register("standard", (DefaultCookieSpecProvider)((Object)rFC6265CookieSpecProvider)).register("standard-strict", (DefaultCookieSpecProvider)((Object)rFC6265CookieSpecProvider2)).register("netscape", (DefaultCookieSpecProvider)((Object)new NetscapeDraftSpecProvider())).register("ignoreCookies", (DefaultCookieSpecProvider)((Object)new IgnoreSpecProvider()));
    }

    public static RegistryBuilder<CookieSpecProvider> createDefaultBuilder() {
        return CookieSpecRegistries.createDefaultBuilder(PublicSuffixMatcherLoader.getDefault());
    }

    public static Lookup<CookieSpecProvider> createDefault() {
        return CookieSpecRegistries.createDefault(PublicSuffixMatcherLoader.getDefault());
    }

    public static Lookup<CookieSpecProvider> createDefault(PublicSuffixMatcher publicSuffixMatcher) {
        return CookieSpecRegistries.createDefaultBuilder(publicSuffixMatcher).build();
    }

    private CookieSpecRegistries() {
    }
}

