/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

import java.util.Iterator;
import net.minecraft.client.renderer.ViewFrustum;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.util.math.BlockPos;
import net.optifine.BlockPosM;
import net.optifine.shaders.Iterator3d;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class IteratorRenderChunks
implements Iterator<ChunkRenderDispatcher.ChunkRender> {
    private ViewFrustum viewFrustum;
    private Iterator3d Iterator3d;
    private BlockPosM posBlock = new BlockPosM(0, 0, 0);

    public IteratorRenderChunks(ViewFrustum viewFrustum, BlockPos blockPos, BlockPos blockPos2, int n, int n2) {
        this.viewFrustum = viewFrustum;
        this.Iterator3d = new Iterator3d(blockPos, blockPos2, n, n2);
    }

    @Override
    public boolean hasNext() {
        return this.Iterator3d.hasNext();
    }

    @Override
    public ChunkRenderDispatcher.ChunkRender next() {
        BlockPos blockPos = this.Iterator3d.next();
        this.posBlock.setXyz(blockPos.getX() << 4, blockPos.getY() << 4, blockPos.getZ() << 4);
        return this.viewFrustum.getRenderChunk(this.posBlock);
    }

    @Override
    public void remove() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Object next() {
        return this.next();
    }
}

