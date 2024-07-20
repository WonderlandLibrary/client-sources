/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import ru.govno.client.event.events.EventRenderChunkContainer;

public abstract class ChunkRenderContainer {
    private double viewEntityX;
    private double viewEntityY;
    private double viewEntityZ;
    protected List<RenderChunk> renderChunks = Lists.newArrayListWithCapacity(17424);
    protected boolean initialized;

    public void initialize(double viewEntityXIn, double viewEntityYIn, double viewEntityZIn) {
        this.initialized = true;
        this.renderChunks.clear();
        this.viewEntityX = viewEntityXIn;
        this.viewEntityY = viewEntityYIn;
        this.viewEntityZ = viewEntityZIn;
    }

    public void preRenderChunk(RenderChunk renderChunkIn) {
        BlockPos blockpos = renderChunkIn.getPosition();
        GlStateManager.translate((float)((double)blockpos.getX() - this.viewEntityX), (float)((double)blockpos.getY() - this.viewEntityY), (float)((double)blockpos.getZ() - this.viewEntityZ));
        EventRenderChunkContainer zalupizda = new EventRenderChunkContainer(renderChunkIn);
        zalupizda.call();
    }

    public void addRenderChunk(RenderChunk renderChunkIn, BlockRenderLayer layer) {
        this.renderChunks.add(renderChunkIn);
    }

    public abstract void renderChunkLayer(BlockRenderLayer var1);
}

