/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.texture;

import net.optifine.texture.BlenderLinear;
import net.optifine.texture.BlenderSplit;
import net.optifine.texture.ColorBlenderSeparate;

public class ColorBlenderLabPbrSpecular
extends ColorBlenderSeparate {
    public ColorBlenderLabPbrSpecular() {
        super(new BlenderLinear(), new BlenderSplit(230, true), new BlenderSplit(65, false), new BlenderSplit(255, true));
    }
}

