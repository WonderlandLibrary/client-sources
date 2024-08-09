/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Locale;

public class JSONBlendingMode {
    private static JSONBlendingMode lastApplied;
    private final int srcColorFactor;
    private final int srcAlphaFactor;
    private final int destColorFactor;
    private final int destAlphaFactor;
    private final int blendFunction;
    private final boolean separateBlend;
    private final boolean opaque;

    private JSONBlendingMode(boolean bl, boolean bl2, int n, int n2, int n3, int n4, int n5) {
        this.separateBlend = bl;
        this.srcColorFactor = n;
        this.destColorFactor = n2;
        this.srcAlphaFactor = n3;
        this.destAlphaFactor = n4;
        this.opaque = bl2;
        this.blendFunction = n5;
    }

    public JSONBlendingMode() {
        this(false, true, 1, 0, 1, 0, 32774);
    }

    public JSONBlendingMode(int n, int n2, int n3) {
        this(false, false, n, n2, n, n2, n3);
    }

    public JSONBlendingMode(int n, int n2, int n3, int n4, int n5) {
        this(true, false, n, n2, n3, n4, n5);
    }

    public void apply() {
        if (!this.equals(lastApplied)) {
            if (lastApplied == null || this.opaque != lastApplied.isOpaque()) {
                lastApplied = this;
                if (this.opaque) {
                    RenderSystem.disableBlend();
                    return;
                }
                RenderSystem.enableBlend();
            }
            RenderSystem.blendEquation(this.blendFunction);
            if (this.separateBlend) {
                RenderSystem.blendFuncSeparate(this.srcColorFactor, this.destColorFactor, this.srcAlphaFactor, this.destAlphaFactor);
            } else {
                RenderSystem.blendFunc(this.srcColorFactor, this.destColorFactor);
            }
        }
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof JSONBlendingMode)) {
            return true;
        }
        JSONBlendingMode jSONBlendingMode = (JSONBlendingMode)object;
        if (this.blendFunction != jSONBlendingMode.blendFunction) {
            return true;
        }
        if (this.destAlphaFactor != jSONBlendingMode.destAlphaFactor) {
            return true;
        }
        if (this.destColorFactor != jSONBlendingMode.destColorFactor) {
            return true;
        }
        if (this.opaque != jSONBlendingMode.opaque) {
            return true;
        }
        if (this.separateBlend != jSONBlendingMode.separateBlend) {
            return true;
        }
        if (this.srcAlphaFactor != jSONBlendingMode.srcAlphaFactor) {
            return true;
        }
        return this.srcColorFactor == jSONBlendingMode.srcColorFactor;
    }

    public int hashCode() {
        int n = this.srcColorFactor;
        n = 31 * n + this.srcAlphaFactor;
        n = 31 * n + this.destColorFactor;
        n = 31 * n + this.destAlphaFactor;
        n = 31 * n + this.blendFunction;
        n = 31 * n + (this.separateBlend ? 1 : 0);
        return 31 * n + (this.opaque ? 1 : 0);
    }

    public boolean isOpaque() {
        return this.opaque;
    }

    public static int stringToBlendFunction(String string) {
        String string2 = string.trim().toLowerCase(Locale.ROOT);
        if ("add".equals(string2)) {
            return 1;
        }
        if ("subtract".equals(string2)) {
            return 1;
        }
        if ("reversesubtract".equals(string2)) {
            return 0;
        }
        if ("reverse_subtract".equals(string2)) {
            return 0;
        }
        if ("min".equals(string2)) {
            return 0;
        }
        return "max".equals(string2) ? 32776 : 32774;
    }

    public static int stringToBlendFactor(String string) {
        String string2 = string.trim().toLowerCase(Locale.ROOT);
        string2 = string2.replaceAll("_", "");
        string2 = string2.replaceAll("one", "1");
        string2 = string2.replaceAll("zero", "0");
        if ("0".equals(string2 = string2.replaceAll("minus", "-"))) {
            return 1;
        }
        if ("1".equals(string2)) {
            return 0;
        }
        if ("srccolor".equals(string2)) {
            return 1;
        }
        if ("1-srccolor".equals(string2)) {
            return 0;
        }
        if ("dstcolor".equals(string2)) {
            return 1;
        }
        if ("1-dstcolor".equals(string2)) {
            return 0;
        }
        if ("srcalpha".equals(string2)) {
            return 1;
        }
        if ("1-srcalpha".equals(string2)) {
            return 0;
        }
        if ("dstalpha".equals(string2)) {
            return 1;
        }
        return "1-dstalpha".equals(string2) ? 773 : -1;
    }
}

