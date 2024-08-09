/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.blaze3d.platform;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;

public class PlatformDescriptors {
    public static String getGlVendor() {
        return GlStateManager.getString(7936);
    }

    public static String getCpuInfo() {
        return GLX._getCpuInfo();
    }

    public static String getGlRenderer() {
        return GlStateManager.getString(7937);
    }

    public static String getGlVersion() {
        return GlStateManager.getString(7938);
    }
}

