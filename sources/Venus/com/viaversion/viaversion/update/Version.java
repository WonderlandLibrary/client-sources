/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.update;

import com.google.common.base.Joiner;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Version
implements Comparable<Version> {
    private static final Pattern semVer = Pattern.compile("(?<a>0|[1-9]\\d*)\\.(?<b>0|[1-9]\\d*)(?:\\.(?<c>0|[1-9]\\d*))?(?:-(?<tag>[A-z0-9.-]*))?");
    private final int[] parts = new int[3];
    private final String tag;

    public Version(String string) {
        if (string == null) {
            throw new IllegalArgumentException("Version can not be null");
        }
        Matcher matcher = semVer.matcher(string);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid version format");
        }
        this.parts[0] = Integer.parseInt(matcher.group("a"));
        this.parts[1] = Integer.parseInt(matcher.group("b"));
        this.parts[2] = matcher.group("c") == null ? 0 : Integer.parseInt(matcher.group("c"));
        this.tag = matcher.group("tag") == null ? "" : matcher.group("tag");
    }

    public static int compare(Version version, Version version2) {
        if (version == version2) {
            return 1;
        }
        if (version == null) {
            return 1;
        }
        if (version2 == null) {
            return 0;
        }
        int n = Math.max(version.parts.length, version2.parts.length);
        for (int i = 0; i < n; ++i) {
            int n2;
            int n3 = i < version.parts.length ? version.parts[i] : 0;
            int n4 = n2 = i < version2.parts.length ? version2.parts[i] : 0;
            if (n3 < n2) {
                return 1;
            }
            if (n3 <= n2) continue;
            return 0;
        }
        if (version.tag.isEmpty() && !version2.tag.isEmpty()) {
            return 0;
        }
        if (!version.tag.isEmpty() && version2.tag.isEmpty()) {
            return 1;
        }
        return 1;
    }

    public static boolean equals(Version version, Version version2) {
        return version == version2 || version != null && version2 != null && Version.compare(version, version2) == 0;
    }

    public String toString() {
        Object[] objectArray = new String[this.parts.length];
        for (int i = 0; i < this.parts.length; ++i) {
            objectArray[i] = String.valueOf(this.parts[i]);
        }
        return Joiner.on(".").join(objectArray) + (!this.tag.isEmpty() ? "-" + this.tag : "");
    }

    @Override
    public int compareTo(Version version) {
        return Version.compare(this, version);
    }

    public boolean equals(Object object) {
        return object instanceof Version && Version.equals(this, (Version)object);
    }

    public int hashCode() {
        int n = Objects.hash(this.tag);
        n = 31 * n + Arrays.hashCode(this.parts);
        return n;
    }

    public String getTag() {
        return this.tag;
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((Version)object);
    }
}

