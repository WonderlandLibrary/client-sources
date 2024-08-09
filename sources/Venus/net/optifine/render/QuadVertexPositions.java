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
public class QuadVertexPositions
extends IntExpiringCache<VertexPosition[]> {
    public QuadVertexPositions() {
        super(60000 + RandomUtils.getRandomInt(10000));
    }

    @Override
    protected VertexPosition[] make() {
        return new VertexPosition[]{new VertexPosition(), new VertexPosition(), new VertexPosition(), new VertexPosition()};
    }

    @Override
    protected Object make() {
        return this.make();
    }
}

