/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.config;

public class GlVersion {
    private int major;
    private int minor;
    private int release;
    private String suffix;

    public GlVersion(int n, int n2) {
        this(n, n2, 0);
    }

    public GlVersion(int n, int n2, int n3) {
        this(n, n2, n3, null);
    }

    public GlVersion(int n, int n2, int n3, String string) {
        this.major = n;
        this.minor = n2;
        this.release = n3;
        this.suffix = string;
    }

    public int getMajor() {
        return this.major;
    }

    public int getMinor() {
        return this.minor;
    }

    public int getRelease() {
        return this.release;
    }

    public int toInt() {
        if (this.minor > 9) {
            return this.major * 100 + this.minor;
        }
        return this.release > 9 ? this.major * 100 + this.minor * 10 + 9 : this.major * 100 + this.minor * 10 + this.release;
    }

    public String toString() {
        return this.suffix == null ? this.major + "." + this.minor + "." + this.release : this.major + "." + this.minor + "." + this.release + this.suffix;
    }
}

