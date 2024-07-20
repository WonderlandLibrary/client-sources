/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils;

import java.util.ArrayList;
import java.util.List;
import ru.govno.client.utils.Render.GaussianBlur;

public interface Utility {
    public static final List<Runnable> NORMAL_BLUR_RUNNABLES = new ArrayList<Runnable>();
    public static final List<Runnable> NORMAL_SHADOW_BLACK = new ArrayList<Runnable>();

    public static void render2DRunnables() {
        if (!NORMAL_BLUR_RUNNABLES.isEmpty()) {
            GaussianBlur.renderBlur(15.0f, NORMAL_BLUR_RUNNABLES);
        }
        if (!NORMAL_BLUR_RUNNABLES.isEmpty() || !NORMAL_SHADOW_BLACK.isEmpty()) {
            Utility.clearRunnables();
        }
    }

    public static void clearRunnables() {
        NORMAL_BLUR_RUNNABLES.clear();
        NORMAL_SHADOW_BLACK.clear();
    }
}

