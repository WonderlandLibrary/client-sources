/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import net.minecraft.client.renderer.texture.Stitcher;

public class StitcherException
extends RuntimeException {
    private final Stitcher.Holder holder;

    public StitcherException(Stitcher.Holder holderIn, String message) {
        super(message);
        this.holder = holderIn;
    }
}

