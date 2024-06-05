package net.minecraft.client.renderer;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import com.google.common.collect.Lists;

import baritone.Baritone;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;

public abstract class ChunkRenderContainer
{
    private double viewEntityX;
    private double viewEntityY;
    private double viewEntityZ;
    protected List<RenderChunk> renderChunks = Lists.<RenderChunk>newArrayListWithCapacity(17424);
    protected boolean initialized;

    public void initialize(double viewEntityXIn, double viewEntityYIn, double viewEntityZIn)
    {
        this.initialized = true;
        this.renderChunks.clear();
        this.viewEntityX = viewEntityXIn;
        this.viewEntityY = viewEntityYIn;
        this.viewEntityZ = viewEntityZIn;
    }

    public void preRenderChunk(RenderChunk renderChunkIn)
    {
        BlockPos blockpos = renderChunkIn.getPosition();
        GlStateManager.translate((float)((double)blockpos.getX() - this.viewEntityX), (float)((double)blockpos.getY() - this.viewEntityY), (float)((double)blockpos.getZ() - this.viewEntityZ));
    }
    
    private BlockPos getPosition(RenderChunk renderChunkIn) {
        if (Baritone.settings().renderCachedChunks.value && !Minecraft.getMinecraft().isSingleplayer() && Minecraft.getMinecraft().theWorld.getChunkFromBlockCoords(renderChunkIn.getPosition()).isEmpty()) {
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GL14.glBlendColor(0, 0, 0, Baritone.settings().cachedChunksOpacity.value);
            GlStateManager.tryBlendFuncSeparate(GL11.GL_CONSTANT_ALPHA, GL11.GL_ONE_MINUS_CONSTANT_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        }
        return renderChunkIn.getPosition();
    }

    public void addRenderChunk(RenderChunk renderChunkIn, EnumWorldBlockLayer layer)
    {
        this.renderChunks.add(renderChunkIn);
    }

    public abstract void renderChunkLayer(EnumWorldBlockLayer layer);
}
