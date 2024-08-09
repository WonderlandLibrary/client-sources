/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import java.util.Comparator;
import net.minecraft.client.renderer.VideoMode;

public class DisplayModeComparator
implements Comparator {
    public int compare(Object object, Object object2) {
        int n;
        VideoMode videoMode = (VideoMode)object;
        VideoMode videoMode2 = (VideoMode)object2;
        if (videoMode.getWidth() != videoMode2.getWidth()) {
            return videoMode.getWidth() - videoMode2.getWidth();
        }
        if (videoMode.getHeight() != videoMode2.getHeight()) {
            return videoMode.getHeight() - videoMode2.getHeight();
        }
        int n2 = videoMode.getRedBits() + videoMode.getGreenBits() + videoMode.getBlueBits();
        if (n2 != (n = videoMode2.getRedBits() + videoMode2.getGreenBits() + videoMode2.getBlueBits())) {
            return n2 - n;
        }
        return videoMode.getRefreshRate() != videoMode2.getRefreshRate() ? videoMode.getRefreshRate() - videoMode2.getRefreshRate() : 0;
    }
}

