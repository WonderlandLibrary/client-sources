package net.minecraft.client.renderer;

import net.minecraft.world.*;
import net.minecraft.client.renderer.chunk.*;
import net.minecraft.util.*;

public class ViewFrustum
{
    public RenderChunk[] renderChunks;
    protected int countChunksX;
    protected int countChunksY;
    protected final World world;
    protected final RenderGlobal renderGlobal;
    protected int countChunksZ;
    
    public void markBlocksForUpdate(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final int bucketInt = MathHelper.bucketInt(n, 0x25 ^ 0x35);
        final int bucketInt2 = MathHelper.bucketInt(n2, 0x6C ^ 0x7C);
        final int bucketInt3 = MathHelper.bucketInt(n3, 0x17 ^ 0x7);
        final int bucketInt4 = MathHelper.bucketInt(n4, 0x65 ^ 0x75);
        final int bucketInt5 = MathHelper.bucketInt(n5, 0x37 ^ 0x27);
        final int bucketInt6 = MathHelper.bucketInt(n6, 0xBC ^ 0xAC);
        int i = bucketInt;
        "".length();
        if (false) {
            throw null;
        }
        while (i <= bucketInt4) {
            int n7 = i % this.countChunksX;
            if (n7 < 0) {
                n7 += this.countChunksX;
            }
            int j = bucketInt2;
            "".length();
            if (-1 >= 0) {
                throw null;
            }
            while (j <= bucketInt5) {
                int n8 = j % this.countChunksY;
                if (n8 < 0) {
                    n8 += this.countChunksY;
                }
                int k = bucketInt3;
                "".length();
                if (4 <= 1) {
                    throw null;
                }
                while (k <= bucketInt6) {
                    int n9 = k % this.countChunksZ;
                    if (n9 < 0) {
                        n9 += this.countChunksZ;
                    }
                    this.renderChunks[(n9 * this.countChunksY + n8) * this.countChunksX + n7].setNeedsUpdate(" ".length() != 0);
                    ++k;
                }
                ++j;
            }
            ++i;
        }
    }
    
    public void deleteGlResources() {
        final RenderChunk[] renderChunks;
        final int length = (renderChunks = this.renderChunks).length;
        int i = "".length();
        "".length();
        if (4 <= 3) {
            throw null;
        }
        while (i < length) {
            renderChunks[i].deleteGlResources();
            ++i;
        }
    }
    
    protected void setCountChunksXYZ(final int n) {
        final int n2 = n * "  ".length() + " ".length();
        this.countChunksX = n2;
        this.countChunksY = (0x60 ^ 0x70);
        this.countChunksZ = n2;
    }
    
    public ViewFrustum(final World world, final int countChunksXYZ, final RenderGlobal renderGlobal, final IRenderChunkFactory renderChunkFactory) {
        this.renderGlobal = renderGlobal;
        this.world = world;
        this.setCountChunksXYZ(countChunksXYZ);
        this.createRenderChunks(renderChunkFactory);
    }
    
    public void updateChunkPositions(final double n, final double n2) {
        final int n3 = MathHelper.floor_double(n) - (0x99 ^ 0x91);
        final int n4 = MathHelper.floor_double(n2) - (0x53 ^ 0x5B);
        final int n5 = this.countChunksX * (0x2C ^ 0x3C);
        int i = "".length();
        "".length();
        if (-1 >= 0) {
            throw null;
        }
        while (i < this.countChunksX) {
            final int func_178157_a = this.func_178157_a(n3, n5, i);
            int j = "".length();
            "".length();
            if (3 < 1) {
                throw null;
            }
            while (j < this.countChunksZ) {
                final int func_178157_a2 = this.func_178157_a(n4, n5, j);
                int k = "".length();
                "".length();
                if (4 <= 2) {
                    throw null;
                }
                while (k < this.countChunksY) {
                    final int n6 = k * (0x86 ^ 0x96);
                    final RenderChunk renderChunk = this.renderChunks[(j * this.countChunksY + k) * this.countChunksX + i];
                    final BlockPos position = new BlockPos(func_178157_a, n6, func_178157_a2);
                    if (!position.equals(renderChunk.getPosition())) {
                        renderChunk.setPosition(position);
                    }
                    ++k;
                }
                ++j;
            }
            ++i;
        }
    }
    
    private int func_178157_a(final int n, final int n2, final int n3) {
        final int n4 = n3 * (0x3D ^ 0x2D);
        int n5 = n4 - n + n2 / "  ".length();
        if (n5 < 0) {
            n5 -= n2 - " ".length();
        }
        return n4 - n5 / n2 * n2;
    }
    
    protected RenderChunk getRenderChunk(final BlockPos blockPos) {
        final int bucketInt = MathHelper.bucketInt(blockPos.getX(), 0x98 ^ 0x88);
        final int bucketInt2 = MathHelper.bucketInt(blockPos.getY(), 0x9C ^ 0x8C);
        final int bucketInt3 = MathHelper.bucketInt(blockPos.getZ(), 0x43 ^ 0x53);
        if (bucketInt2 >= 0 && bucketInt2 < this.countChunksY) {
            int n = bucketInt % this.countChunksX;
            if (n < 0) {
                n += this.countChunksX;
            }
            int n2 = bucketInt3 % this.countChunksZ;
            if (n2 < 0) {
                n2 += this.countChunksZ;
            }
            return this.renderChunks[(n2 * this.countChunksY + bucketInt2) * this.countChunksX + n];
        }
        return null;
    }
    
    protected void createRenderChunks(final IRenderChunkFactory renderChunkFactory) {
        this.renderChunks = new RenderChunk[this.countChunksX * this.countChunksY * this.countChunksZ];
        int length = "".length();
        int i = "".length();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (i < this.countChunksX) {
            int j = "".length();
            "".length();
            if (1 == 3) {
                throw null;
            }
            while (j < this.countChunksY) {
                int k = "".length();
                "".length();
                if (3 != 3) {
                    throw null;
                }
                while (k < this.countChunksZ) {
                    this.renderChunks[(k * this.countChunksY + j) * this.countChunksX + i] = renderChunkFactory.makeRenderChunk(this.world, this.renderGlobal, new BlockPos(i * (0x76 ^ 0x66), j * (0xD6 ^ 0xC6), k * (0xBD ^ 0xAD)), length++);
                    ++k;
                }
                ++j;
            }
            ++i;
        }
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
