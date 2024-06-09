package com.client.glowclient.sponge.mixinutils.renderglobal;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.chunk.*;
import net.minecraft.util.math.*;
import javax.annotation.*;

@SideOnly(Side.CLIENT)
public class ViewFrustum
{
    public RenderChunk[] renderChunks;
    public final World world;
    public int countChunksX;
    public final RenderGlobal renderGlobal;
    public int countChunksY;
    public int countChunksZ;
    
    public void markBlocksForUpdate(int intFloorDiv, int intFloorDiv2, int intFloorDiv3, int intFloorDiv4, int intFloorDiv5, int intFloorDiv6, final boolean needsUpdate) {
        intFloorDiv = MathHelper.intFloorDiv(intFloorDiv, 16);
        intFloorDiv2 = MathHelper.intFloorDiv(intFloorDiv2, 16);
        intFloorDiv3 = MathHelper.intFloorDiv(intFloorDiv3, 16);
        intFloorDiv4 = MathHelper.intFloorDiv(intFloorDiv4, 16);
        intFloorDiv5 = MathHelper.intFloorDiv(intFloorDiv5, 16);
        intFloorDiv6 = MathHelper.intFloorDiv(intFloorDiv6, 16);
        int i = intFloorDiv = intFloorDiv;
        while (i <= intFloorDiv4) {
            int n;
            if ((n = intFloorDiv % this.countChunksX) < 0) {
                n += this.countChunksX;
            }
            int n2;
            int j = n2 = intFloorDiv2;
            while (j <= intFloorDiv5) {
                int n3;
                if ((n3 = n2 % this.countChunksY) < 0) {
                    n3 += this.countChunksY;
                }
                int n4;
                int k = n4 = intFloorDiv3;
                while (k <= intFloorDiv6) {
                    int n5;
                    if ((n5 = n4 % this.countChunksZ) < 0) {
                        n5 += this.countChunksZ;
                    }
                    final int n6 = (n5 * this.countChunksY + n3) * this.countChunksX + n;
                    final RenderChunk[] renderChunks = this.renderChunks;
                    final int n7 = n6;
                    ++n4;
                    renderChunks[n7].setNeedsUpdate(needsUpdate);
                    k = n4;
                }
                j = ++n2;
            }
            i = ++intFloorDiv;
        }
    }
    
    public void deleteGlResources() {
        final RenderChunk[] renderChunks;
        final int length = (renderChunks = this.renderChunks).length;
        int n;
        int i = n = 0;
        while (i < length) {
            renderChunks[n++].deleteGlResources();
            i = n;
        }
    }
    
    public void createRenderChunks(final IRenderChunkFactory renderChunkFactory) {
        this.renderChunks = new RenderChunk[this.countChunksX * this.countChunksY * this.countChunksZ];
        int n = 0;
        int n2;
        int i = n2 = 0;
        while (i < this.countChunksX) {
            int n3;
            int j = n3 = 0;
            while (j < this.countChunksY) {
                int n4;
                int k = n4 = 0;
                while (k < this.countChunksZ) {
                    final int n5 = (n4 * this.countChunksY + n3) * this.countChunksX + n2;
                    final RenderChunk[] renderChunks = this.renderChunks;
                    final int n6 = n5;
                    final RenderChunk create = renderChunkFactory.create(this.world, this.renderGlobal, n);
                    ++n;
                    renderChunks[n6] = create;
                    this.renderChunks[n5].setPosition(n2 * 16, n3 * 16, n4++ * 16);
                    k = n4;
                }
                j = ++n3;
            }
            i = ++n2;
        }
    }
    
    public ViewFrustum(final World world, final int countChunksXYZ, final RenderGlobal renderGlobal, final IRenderChunkFactory renderChunkFactory) {
        super();
        this.renderGlobal = renderGlobal;
        this.world = world;
        this.setCountChunksXYZ(countChunksXYZ);
        this.createRenderChunks(renderChunkFactory);
    }
    
    public int getBaseCoordinate(int n, final int n2, int n3) {
        if ((n = (n3 *= 16) - n + n2 / 2) < 0) {
            n -= n2 - 1;
        }
        return n3 - n / n2 * n2;
    }
    
    @Nullable
    public RenderChunk getRenderChunk(final BlockPos blockPos) {
        final int intFloorDiv = MathHelper.intFloorDiv(blockPos.getX(), 16);
        final int intFloorDiv2 = MathHelper.intFloorDiv(blockPos.getY(), 16);
        final int intFloorDiv3 = MathHelper.intFloorDiv(blockPos.getZ(), 16);
        if (intFloorDiv2 >= 0 && intFloorDiv2 < this.countChunksY) {
            int n;
            if ((n = intFloorDiv % this.countChunksX) < 0) {
                n += this.countChunksX;
            }
            int n2;
            if ((n2 = intFloorDiv3 % this.countChunksZ) < 0) {
                n2 += this.countChunksZ;
            }
            return this.renderChunks[(n2 * this.countChunksY + intFloorDiv2) * this.countChunksX + n];
        }
        return null;
    }
    
    public void setCountChunksXYZ(int countChunksX) {
        final int countChunksZ = countChunksX = countChunksX * 2 + 1;
        final int countChunksY = 16;
        this.countChunksX = countChunksX;
        this.countChunksY = countChunksY;
        this.countChunksZ = countChunksZ;
    }
    
    public void updateChunkPositions(final double n, final double n2) {
        final int n3 = MathHelper.floor(n) - 8;
        final int n4 = MathHelper.floor(n2) - 8;
        final int n5 = this.countChunksX * 16;
        int n6;
        int i = n6 = 0;
        while (i < this.countChunksX) {
            final int baseCoordinate = this.getBaseCoordinate(n3, n5, n6);
            int n7;
            int j = n7 = 0;
            while (j < this.countChunksZ) {
                final int baseCoordinate2 = this.getBaseCoordinate(n4, n5, n7);
                int n8;
                int k = n8 = 0;
                while (k < this.countChunksY) {
                    final int n9 = n8 * 16;
                    final RenderChunk renderChunk = this.renderChunks[(n7 * this.countChunksY + n8) * this.countChunksX + n6];
                    final int n10 = baseCoordinate;
                    ++n8;
                    renderChunk.setPosition(n10, n9, baseCoordinate2);
                    k = n8;
                }
                j = ++n7;
            }
            i = ++n6;
        }
    }
}
