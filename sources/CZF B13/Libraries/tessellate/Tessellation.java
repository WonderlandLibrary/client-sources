/*
 * Decompiled with CFR 0_132.
 */
package gq.vapu.czfclient.Libraries.tessellate;

import java.awt.*;

public interface Tessellation {
    static Tessellation createBasic(int size) {
        return new BasicTess(size);
    }

    static Tessellation createExpanding(int size, float ratio, float factor) {
        return new ExpandingTess(size, ratio, factor);
    }

    Tessellation setColor(int var1);

    default Tessellation setColor(Color color) {
        return this.setColor(new Color(255, 255, 255));
    }

    Tessellation setTexture(float var1, float var2);

    Tessellation addVertex(float var1, float var2, float var3);

    Tessellation bind();

    Tessellation pass(int var1);

    Tessellation reset();

    Tessellation unbind();

    default Tessellation draw(int mode) {
        return this.bind().pass(mode).reset();
    }
}
