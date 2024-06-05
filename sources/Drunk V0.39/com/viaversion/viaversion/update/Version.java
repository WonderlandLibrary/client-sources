/*
 * Decompiled with CFR 0.152.
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

    public Version(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Version can not be null");
        }
        Matcher matcher = semVer.matcher(value);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid version format");
        }
        this.parts[0] = Integer.parseInt(matcher.group("a"));
        this.parts[1] = Integer.parseInt(matcher.group("b"));
        this.parts[2] = matcher.group("c") == null ? 0 : Integer.parseInt(matcher.group("c"));
        this.tag = matcher.group("tag") == null ? "" : matcher.group("tag");
    }

    public static int compare(Version verA, Version verB) {
        if (verA == verB) {
            return 0;
        }
        if (verA == null) {
            return -1;
        }
        if (verB == null) {
            return 1;
        }
        int max = Math.max(verA.parts.length, verB.parts.length);
        for (int i = 0; i < max; ++i) {
            int partB;
            int partA = i < verA.parts.length ? verA.parts[i] : 0;
            int n = partB = i < verB.parts.length ? verB.parts[i] : 0;
            if (partA < partB) {
                return -1;
            }
            if (partA <= partB) continue;
            return 1;
        }
        if (verA.tag.isEmpty() && !verB.tag.isEmpty()) {
            return 1;
        }
        if (verA.tag.isEmpty()) return 0;
        if (!verB.tag.isEmpty()) return 0;
        return -1;
    }

    public static boolean equals(Version verA, Version verB) {
        if (verA == verB) return true;
        if (verA == null) return false;
        if (verB == null) return false;
        if (Version.compare(verA, verB) != 0) return false;
        return true;
    }

    public String toString() {
        String string;
        Object[] split = new String[this.parts.length];
        for (int i = 0; i < this.parts.length; ++i) {
            split[i] = String.valueOf(this.parts[i]);
        }
        StringBuilder stringBuilder = new StringBuilder().append(Joiner.on(".").join(split));
        if (!this.tag.isEmpty()) {
            string = "-" + this.tag;
            return stringBuilder.append(string).toString();
        }
        string = "";
        return stringBuilder.append(string).toString();
    }

    @Override
    public int compareTo(Version that) {
        return Version.compare(this, that);
    }

    public boolean equals(Object that) {
        if (!(that instanceof Version)) return false;
        if (!Version.equals(this, (Version)that)) return false;
        return true;
    }

    public int hashCode() {
        int result = Objects.hash(this.tag);
        return 31 * result + Arrays.hashCode(this.parts);
    }

    public String getTag() {
        return this.tag;
    }
}

