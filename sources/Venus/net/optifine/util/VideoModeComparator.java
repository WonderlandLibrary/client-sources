/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import java.util.Comparator;
import net.minecraft.client.renderer.VideoMode;

public class VideoModeComparator
implements Comparator<VideoMode> {
    @Override
    public int compare(VideoMode videoMode, VideoMode videoMode2) {
        int n;
        if (videoMode.getWidth() != videoMode2.getWidth()) {
            return videoMode.getWidth() - videoMode2.getWidth();
        }
        if (videoMode.getHeight() != videoMode2.getHeight()) {
            return videoMode.getHeight() - videoMode2.getHeight();
        }
        if (videoMode.getRefreshRate() != videoMode2.getRefreshRate()) {
            return videoMode.getRefreshRate() - videoMode2.getRefreshRate();
        }
        int n2 = videoMode.getRedBits() + videoMode.getGreenBits() + videoMode.getBlueBits();
        return n2 != (n = videoMode2.getRedBits() + videoMode2.getGreenBits() + videoMode2.getBlueBits()) ? n2 - n : 0;
    }

    @Override
    public int compare(Object object, Object object2) {
        return this.compare((VideoMode)object, (VideoMode)object2);
    }
}

