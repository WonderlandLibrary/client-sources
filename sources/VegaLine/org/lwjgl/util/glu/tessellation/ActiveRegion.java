/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.util.glu.tessellation;

import org.lwjgl.util.glu.tessellation.DictNode;
import org.lwjgl.util.glu.tessellation.GLUhalfEdge;

class ActiveRegion {
    GLUhalfEdge eUp;
    DictNode nodeUp;
    int windingNumber;
    boolean inside;
    boolean sentinel;
    boolean dirty;
    boolean fixUpperEdge;

    ActiveRegion() {
    }
}

