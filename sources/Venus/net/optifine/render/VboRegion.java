/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.render;

import com.mojang.blaze3d.platform.GlStateManager;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.optifine.Config;
import net.optifine.render.VboRange;
import net.optifine.shaders.ShadersRender;
import net.optifine.util.LinkedList;

public class VboRegion {
    private RenderType layer = null;
    private int glBufferId = GlStateManager.genBuffers();
    private int capacity = 4096;
    private int positionTop = 0;
    private int sizeUsed;
    private LinkedList<VboRange> rangeList = new LinkedList();
    private VboRange compactRangeLast = null;
    private IntBuffer bufferIndexVertex = Config.createDirectIntBuffer(this.capacity);
    private IntBuffer bufferCountVertex = Config.createDirectIntBuffer(this.capacity);
    private final int vertexBytes = DefaultVertexFormats.BLOCK.getSize();
    private int drawMode = 7;
    private boolean isShaders = Config.isShaders();

    public VboRegion(RenderType renderType) {
        this.layer = renderType;
        this.bindBuffer();
        long l = this.toBytes(this.capacity);
        GlStateManager.bufferData(GlStateManager.GL_ARRAY_BUFFER, l, GlStateManager.GL_STATIC_DRAW);
        this.unbindBuffer();
    }

    public void bufferData(ByteBuffer byteBuffer, VboRange vboRange) {
        if (this.glBufferId >= 0) {
            int n = vboRange.getPosition();
            int n2 = vboRange.getSize();
            int n3 = this.toVertex(byteBuffer.limit());
            if (n3 <= 0) {
                if (n >= 0) {
                    vboRange.setPosition(-1);
                    vboRange.setSize(0);
                    this.rangeList.remove(vboRange.getNode());
                    this.sizeUsed -= n2;
                }
            } else {
                if (n3 > n2) {
                    vboRange.setPosition(this.positionTop);
                    vboRange.setSize(n3);
                    this.positionTop += n3;
                    if (n >= 0) {
                        this.rangeList.remove(vboRange.getNode());
                    }
                    this.rangeList.addLast(vboRange.getNode());
                }
                vboRange.setSize(n3);
                this.sizeUsed += n3 - n2;
                this.checkVboSize(vboRange.getPositionNext());
                long l = this.toBytes(vboRange.getPosition());
                this.bindBuffer();
                GlStateManager.bufferSubData(GlStateManager.GL_ARRAY_BUFFER, l, byteBuffer);
                this.unbindBuffer();
                if (this.positionTop > this.sizeUsed * 11 / 10) {
                    this.compactRanges(1);
                }
            }
        }
    }

    private void compactRanges(int n) {
        if (!this.rangeList.isEmpty()) {
            VboRange vboRange = this.compactRangeLast;
            if (vboRange == null || !this.rangeList.contains(vboRange.getNode())) {
                vboRange = this.rangeList.getFirst().getItem();
            }
            int n2 = vboRange.getPosition();
            VboRange vboRange2 = vboRange.getPrev();
            n2 = vboRange2 == null ? 0 : vboRange2.getPositionNext();
            for (int i = 0; vboRange != null && i < n; ++i) {
                if (vboRange.getPosition() == n2) {
                    n2 += vboRange.getSize();
                    vboRange = vboRange.getNext();
                    continue;
                }
                int n3 = vboRange.getPosition() - n2;
                if (vboRange.getSize() <= n3) {
                    this.copyVboData(vboRange.getPosition(), n2, vboRange.getSize());
                    vboRange.setPosition(n2);
                    n2 += vboRange.getSize();
                    vboRange = vboRange.getNext();
                    continue;
                }
                this.checkVboSize(this.positionTop + vboRange.getSize());
                this.copyVboData(vboRange.getPosition(), this.positionTop, vboRange.getSize());
                vboRange.setPosition(this.positionTop);
                this.positionTop += vboRange.getSize();
                VboRange vboRange3 = vboRange.getNext();
                this.rangeList.remove(vboRange.getNode());
                this.rangeList.addLast(vboRange.getNode());
                vboRange = vboRange3;
            }
            if (vboRange == null) {
                this.positionTop = this.rangeList.getLast().getItem().getPositionNext();
            }
            this.compactRangeLast = vboRange;
        }
    }

    private void checkRanges() {
        int n = 0;
        int n2 = 0;
        for (VboRange vboRange = this.rangeList.getFirst().getItem(); vboRange != null; vboRange = vboRange.getNext()) {
            ++n;
            n2 += vboRange.getSize();
            if (vboRange.getPosition() < 0 || vboRange.getSize() <= 0 || vboRange.getPositionNext() > this.positionTop) {
                throw new RuntimeException("Invalid range: " + vboRange);
            }
            VboRange vboRange2 = vboRange.getPrev();
            if (vboRange2 != null && vboRange.getPosition() < vboRange2.getPositionNext()) {
                throw new RuntimeException("Invalid range: " + vboRange);
            }
            VboRange vboRange3 = vboRange.getNext();
            if (vboRange3 == null || vboRange.getPositionNext() <= vboRange3.getPosition()) continue;
            throw new RuntimeException("Invalid range: " + vboRange);
        }
        if (n != this.rangeList.getSize()) {
            throw new RuntimeException("Invalid count: " + n + " <> " + this.rangeList.getSize());
        }
        if (n2 != this.sizeUsed) {
            throw new RuntimeException("Invalid size: " + n2 + " <> " + this.sizeUsed);
        }
    }

    private void checkVboSize(int n) {
        if (this.capacity < n) {
            this.expandVbo(n);
        }
    }

    private void copyVboData(int n, int n2, int n3) {
        long l = this.toBytes(n);
        long l2 = this.toBytes(n2);
        long l3 = this.toBytes(n3);
        GlStateManager.bindBuffer(GlStateManager.GL_COPY_READ_BUFFER, this.glBufferId);
        GlStateManager.bindBuffer(GlStateManager.GL_COPY_WRITE_BUFFER, this.glBufferId);
        GlStateManager.copyBufferSubData(GlStateManager.GL_COPY_READ_BUFFER, GlStateManager.GL_COPY_WRITE_BUFFER, l, l2, l3);
        Config.checkGlError("Copy VBO range");
        GlStateManager.bindBuffer(GlStateManager.GL_COPY_READ_BUFFER, 0);
        GlStateManager.bindBuffer(GlStateManager.GL_COPY_WRITE_BUFFER, 0);
    }

    private void expandVbo(int n) {
        int n2 = this.capacity * 6 / 4;
        while (n2 < n) {
            n2 = n2 * 6 / 4;
        }
        long l = this.toBytes(this.capacity);
        long l2 = this.toBytes(n2);
        int n3 = GlStateManager.genBuffers();
        GlStateManager.bindBuffer(GlStateManager.GL_ARRAY_BUFFER, n3);
        GlStateManager.bufferData(GlStateManager.GL_ARRAY_BUFFER, l2, GlStateManager.GL_STATIC_DRAW);
        Config.checkGlError("Expand VBO");
        GlStateManager.bindBuffer(GlStateManager.GL_ARRAY_BUFFER, 0);
        GlStateManager.bindBuffer(GlStateManager.GL_COPY_READ_BUFFER, this.glBufferId);
        GlStateManager.bindBuffer(GlStateManager.GL_COPY_WRITE_BUFFER, n3);
        GlStateManager.copyBufferSubData(GlStateManager.GL_COPY_READ_BUFFER, GlStateManager.GL_COPY_WRITE_BUFFER, 0L, 0L, l);
        Config.checkGlError("Copy VBO: " + l2);
        GlStateManager.bindBuffer(GlStateManager.GL_COPY_READ_BUFFER, 0);
        GlStateManager.bindBuffer(GlStateManager.GL_COPY_WRITE_BUFFER, 0);
        GlStateManager.deleteBuffers(this.glBufferId);
        this.bufferIndexVertex = Config.createDirectIntBuffer(n2);
        this.bufferCountVertex = Config.createDirectIntBuffer(n2);
        this.glBufferId = n3;
        this.capacity = n2;
    }

    public void bindBuffer() {
        GlStateManager.bindBuffer(GlStateManager.GL_ARRAY_BUFFER, this.glBufferId);
    }

    public void drawArrays(int n, VboRange vboRange) {
        if (this.drawMode != n) {
            if (this.bufferIndexVertex.position() > 0) {
                throw new IllegalArgumentException("Mixed region draw modes: " + this.drawMode + " != " + n);
            }
            this.drawMode = n;
        }
        this.bufferIndexVertex.put(vboRange.getPosition());
        this.bufferCountVertex.put(vboRange.getSize());
    }

    public void finishDraw() {
        this.bindBuffer();
        DefaultVertexFormats.BLOCK.setupBufferState(0L);
        if (this.isShaders) {
            ShadersRender.setupArrayPointersVbo();
        }
        this.bufferIndexVertex.flip();
        this.bufferCountVertex.flip();
        GlStateManager.glMultiDrawArrays(this.drawMode, this.bufferIndexVertex, this.bufferCountVertex);
        this.bufferIndexVertex.limit(this.bufferIndexVertex.capacity());
        this.bufferCountVertex.limit(this.bufferCountVertex.capacity());
        if (this.positionTop > this.sizeUsed * 11 / 10) {
            this.compactRanges(1);
        }
    }

    public void unbindBuffer() {
        GlStateManager.bindBuffer(GlStateManager.GL_ARRAY_BUFFER, 0);
    }

    public void deleteGlBuffers() {
        if (this.glBufferId >= 0) {
            GlStateManager.deleteBuffers(this.glBufferId);
            this.glBufferId = -1;
        }
    }

    private long toBytes(int n) {
        return (long)n * (long)this.vertexBytes;
    }

    private int toVertex(long l) {
        return (int)(l / (long)this.vertexBytes);
    }

    public int getPositionTop() {
        return this.positionTop;
    }
}

