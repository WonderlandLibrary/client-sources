/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.optifine.Config;

public class GlErrors {
    private static boolean frameStarted = false;
    private static long timeCheckStartMs = -1L;
    private static Int2ObjectOpenHashMap<GlError> glErrors = new Int2ObjectOpenHashMap();
    private static final long CHECK_INTERVAL_MS = 3000L;
    private static final int CHECK_ERROR_MAX = 10;

    public static void frameStart() {
        frameStarted = true;
        if (!glErrors.isEmpty()) {
            if (timeCheckStartMs < 0L) {
                timeCheckStartMs = System.currentTimeMillis();
            }
            if (System.currentTimeMillis() > timeCheckStartMs + 3000L) {
                for (GlError glError : glErrors.values()) {
                    glError.onFrameStart();
                }
                timeCheckStartMs = System.currentTimeMillis();
            }
        }
    }

    public static boolean isEnabled(int n) {
        if (!frameStarted) {
            return false;
        }
        GlError glError = GlErrors.getGlError(n);
        return glError.isEnabled();
    }

    private static GlError getGlError(int n) {
        GlError glError = glErrors.get(n);
        if (glError == null) {
            glError = new GlError(n);
            glErrors.put(n, glError);
        }
        return glError;
    }

    public static class GlError {
        private int id;
        private int countErrors = 0;
        private int countErrorsSuppressed = 0;
        private boolean suppressed = false;
        private boolean oneErrorEnabled = false;

        public GlError(int n) {
            this.id = n;
        }

        public void onFrameStart() {
            if (this.countErrorsSuppressed > 0) {
                Config.error("Suppressed " + this.countErrors + " OpenGL errors: " + this.id);
            }
            this.suppressed = this.countErrors > 10;
            this.countErrors = 0;
            this.countErrorsSuppressed = 0;
            this.oneErrorEnabled = true;
        }

        public boolean isEnabled() {
            ++this.countErrors;
            if (this.oneErrorEnabled) {
                this.oneErrorEnabled = false;
                return false;
            }
            if (this.suppressed) {
                ++this.countErrorsSuppressed;
            }
            return !this.suppressed;
        }
    }
}

