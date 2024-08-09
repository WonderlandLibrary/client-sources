/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.render;

import java.util.Arrays;
import java.util.List;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;

public class RenderStateManager {
    private static boolean cacheEnabled;
    private static final RenderState[] PENDING_CLEAR_STATES;

    public static void setupRenderStates(List<RenderState> list) {
        if (cacheEnabled) {
            RenderStateManager.setupCached(list);
        } else {
            for (int i = 0; i < list.size(); ++i) {
                RenderState renderState = list.get(i);
                renderState.setupRenderState();
            }
        }
    }

    public static void clearRenderStates(List<RenderState> list) {
        if (cacheEnabled) {
            RenderStateManager.clearCached(list);
        } else {
            for (int i = 0; i < list.size(); ++i) {
                RenderState renderState = list.get(i);
                renderState.clearRenderState();
            }
        }
    }

    private static void setupCached(List<RenderState> list) {
        for (int i = 0; i < list.size(); ++i) {
            RenderState renderState = list.get(i);
            RenderStateManager.setupCached(renderState, i);
        }
    }

    private static void clearCached(List<RenderState> list) {
        for (int i = 0; i < list.size(); ++i) {
            RenderState renderState = list.get(i);
            RenderStateManager.clearCached(renderState, i);
        }
    }

    private static void setupCached(RenderState renderState, int n) {
        RenderState renderState2 = PENDING_CLEAR_STATES[n];
        if (renderState2 != null) {
            if (renderState == renderState2) {
                RenderStateManager.PENDING_CLEAR_STATES[n] = null;
                return;
            }
            renderState2.clearRenderState();
            RenderStateManager.PENDING_CLEAR_STATES[n] = null;
        }
        renderState.setupRenderState();
    }

    private static void clearCached(RenderState renderState, int n) {
        RenderState renderState2 = PENDING_CLEAR_STATES[n];
        if (renderState2 != null) {
            renderState2.clearRenderState();
        }
        RenderStateManager.PENDING_CLEAR_STATES[n] = renderState;
    }

    public static void enableCache() {
        if (!cacheEnabled) {
            cacheEnabled = true;
            Arrays.fill(PENDING_CLEAR_STATES, null);
        }
    }

    public static void disableCache() {
        if (cacheEnabled) {
            cacheEnabled = false;
            for (int i = 0; i < PENDING_CLEAR_STATES.length; ++i) {
                RenderState renderState = PENDING_CLEAR_STATES[i];
                if (renderState == null) continue;
                renderState.clearRenderState();
            }
            Arrays.fill(PENDING_CLEAR_STATES, null);
        }
    }

    static {
        PENDING_CLEAR_STATES = new RenderState[RenderType.getCountRenderStates()];
    }
}

