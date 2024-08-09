/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.render;

import net.optifine.render.VertexPosition;
import net.optifine.util.IntExpiringCache;
import net.optifine.util.RandomUtils;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class BoxVertexPositions
extends IntExpiringCache<VertexPosition[][]> {
    public BoxVertexPositions() {
        super(60000 + RandomUtils.getRandomInt(10000));
    }

    @Override
    protected VertexPosition[][] make() {
        VertexPosition[][] vertexPositionArray = new VertexPosition[6][4];
        for (int i = 0; i < vertexPositionArray.length; ++i) {
            VertexPosition[] vertexPositionArray2 = vertexPositionArray[i];
            for (int j = 0; j < vertexPositionArray2.length; ++j) {
                vertexPositionArray2[j] = new VertexPosition();
            }
        }
        return vertexPositionArray;
    }

    @Override
    protected Object make() {
        return this.make();
    }
}

