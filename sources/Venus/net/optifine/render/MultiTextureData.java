/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.render;

import net.optifine.render.SpriteRenderData;
import net.optifine.util.ArrayUtils;

public class MultiTextureData {
    private SpriteRenderData[] spriteRenderDatas;

    public MultiTextureData(SpriteRenderData[] spriteRenderDataArray) {
        this.spriteRenderDatas = spriteRenderDataArray;
    }

    public SpriteRenderData[] getSpriteRenderDatas() {
        return this.spriteRenderDatas;
    }

    public String toString() {
        return ArrayUtils.arrayToString(this.spriteRenderDatas);
    }
}

