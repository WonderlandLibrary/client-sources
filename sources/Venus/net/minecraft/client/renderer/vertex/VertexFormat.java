/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.vertex;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.stream.Collectors;
import net.minecraft.client.renderer.vertex.VertexFormatElement;

public class VertexFormat {
    private ImmutableList<VertexFormatElement> elements;
    private IntList offsets = new IntArrayList();
    private int vertexSize;
    private String name;
    private int positionElementOffset = -1;
    private int normalElementOffset = -1;
    private int colorElementOffset = -1;
    private Int2IntMap uvOffsetsById = new Int2IntArrayMap();

    public VertexFormat(ImmutableList<VertexFormatElement> immutableList) {
        this.elements = immutableList;
        int n = 0;
        for (VertexFormatElement vertexFormatElement : immutableList) {
            this.offsets.add(n);
            VertexFormatElement.Usage usage = vertexFormatElement.getUsage();
            if (usage == VertexFormatElement.Usage.POSITION) {
                this.positionElementOffset = n;
            } else if (usage == VertexFormatElement.Usage.NORMAL) {
                this.normalElementOffset = n;
            } else if (usage == VertexFormatElement.Usage.COLOR) {
                this.colorElementOffset = n;
            } else if (usage == VertexFormatElement.Usage.UV) {
                this.uvOffsetsById.put(vertexFormatElement.getIndex(), n);
            }
            n += vertexFormatElement.getSize();
        }
        this.vertexSize = n;
    }

    public String toString() {
        return "format: " + this.name + " " + this.elements.size() + " elements: " + this.elements.stream().map(Object::toString).collect(Collectors.joining(" "));
    }

    public int getIntegerSize() {
        return this.getSize() / 4;
    }

    public int getSize() {
        return this.vertexSize;
    }

    public ImmutableList<VertexFormatElement> getElements() {
        return this.elements;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object != null && this.getClass() == object.getClass()) {
            VertexFormat vertexFormat = (VertexFormat)object;
            return this.vertexSize != vertexFormat.vertexSize ? false : this.elements.equals(vertexFormat.elements);
        }
        return true;
    }

    public int hashCode() {
        return this.elements.hashCode();
    }

    public void setupBufferState(long l) {
        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(() -> this.lambda$setupBufferState$0(l));
        } else {
            int n = this.getSize();
            ImmutableList<VertexFormatElement> immutableList = this.getElements();
            for (int i = 0; i < immutableList.size(); ++i) {
                ((VertexFormatElement)immutableList.get(i)).setupBufferState(l + (long)this.offsets.getInt(i), n);
            }
        }
    }

    public void clearBufferState() {
        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(this::clearBufferState);
        } else {
            for (VertexFormatElement vertexFormatElement : this.getElements()) {
                vertexFormatElement.clearBufferState();
            }
        }
    }

    public int getOffset(int n) {
        return this.offsets.getInt(n);
    }

    public boolean hasPosition() {
        return this.positionElementOffset >= 0;
    }

    public int getPositionOffset() {
        return this.positionElementOffset;
    }

    public boolean hasNormal() {
        return this.normalElementOffset >= 0;
    }

    public int getNormalOffset() {
        return this.normalElementOffset;
    }

    public boolean hasColor() {
        return this.colorElementOffset >= 0;
    }

    public int getColorOffset() {
        return this.colorElementOffset;
    }

    public boolean hasUV(int n) {
        return this.uvOffsetsById.containsKey(n);
    }

    public int getUvOffsetById(int n) {
        return this.uvOffsetsById.get(n);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String string) {
        this.name = string;
    }

    public void copyFrom(VertexFormat vertexFormat) {
        this.elements = vertexFormat.elements;
        this.offsets = vertexFormat.offsets;
        this.vertexSize = vertexFormat.vertexSize;
        this.name = vertexFormat.name;
        this.positionElementOffset = vertexFormat.positionElementOffset;
        this.normalElementOffset = vertexFormat.normalElementOffset;
        this.colorElementOffset = vertexFormat.colorElementOffset;
        this.uvOffsetsById = vertexFormat.uvOffsetsById;
    }

    public VertexFormat duplicate() {
        VertexFormat vertexFormat = new VertexFormat(ImmutableList.of());
        vertexFormat.copyFrom(this);
        return vertexFormat;
    }

    private void lambda$setupBufferState$0(long l) {
        this.setupBufferState(l);
    }
}

