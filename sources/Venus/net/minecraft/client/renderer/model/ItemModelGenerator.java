/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import net.minecraft.client.renderer.model.BlockFaceUV;
import net.minecraft.client.renderer.model.BlockModel;
import net.minecraft.client.renderer.model.BlockPart;
import net.minecraft.client.renderer.model.BlockPartFace;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;

public class ItemModelGenerator {
    public static final List<String> LAYERS = Lists.newArrayList("layer0", "layer1", "layer2", "layer3", "layer4");

    public BlockModel makeItemModel(Function<RenderMaterial, TextureAtlasSprite> function, BlockModel blockModel) {
        String string;
        HashMap<String, Either<RenderMaterial, String>> hashMap = Maps.newHashMap();
        ArrayList<BlockPart> arrayList = Lists.newArrayList();
        for (int i = 0; i < LAYERS.size() && blockModel.isTexturePresent(string = LAYERS.get(i)); ++i) {
            RenderMaterial renderMaterial = blockModel.resolveTextureName(string);
            hashMap.put(string, Either.left(renderMaterial));
            TextureAtlasSprite textureAtlasSprite = function.apply(renderMaterial);
            arrayList.addAll(this.getBlockParts(i, string, textureAtlasSprite));
        }
        hashMap.put("particle", blockModel.isTexturePresent("particle") ? Either.left(blockModel.resolveTextureName("particle")) : (Either)hashMap.get("layer0"));
        BlockModel blockModel2 = new BlockModel(null, arrayList, hashMap, false, blockModel.getGuiLight(), blockModel.getAllTransforms(), blockModel.getOverrides());
        blockModel2.name = blockModel.name;
        return blockModel2;
    }

    private List<BlockPart> getBlockParts(int n, String string, TextureAtlasSprite textureAtlasSprite) {
        HashMap<Direction, BlockPartFace> hashMap = Maps.newHashMap();
        hashMap.put(Direction.SOUTH, new BlockPartFace(null, n, string, new BlockFaceUV(new float[]{0.0f, 0.0f, 16.0f, 16.0f}, 0)));
        hashMap.put(Direction.NORTH, new BlockPartFace(null, n, string, new BlockFaceUV(new float[]{16.0f, 0.0f, 0.0f, 16.0f}, 0)));
        ArrayList<BlockPart> arrayList = Lists.newArrayList();
        arrayList.add(new BlockPart(new Vector3f(0.0f, 0.0f, 7.5f), new Vector3f(16.0f, 16.0f, 8.5f), hashMap, null, true));
        arrayList.addAll(this.getBlockParts(textureAtlasSprite, string, n));
        return arrayList;
    }

    private List<BlockPart> getBlockParts(TextureAtlasSprite textureAtlasSprite, String string, int n) {
        float f = textureAtlasSprite.getWidth();
        float f2 = textureAtlasSprite.getHeight();
        ArrayList<BlockPart> arrayList = Lists.newArrayList();
        for (Span span : this.getSpans(textureAtlasSprite)) {
            float f3 = 0.0f;
            float f4 = 0.0f;
            float f5 = 0.0f;
            float f6 = 0.0f;
            float f7 = 0.0f;
            float f8 = 0.0f;
            float f9 = 0.0f;
            float f10 = 0.0f;
            float f11 = 16.0f / f;
            float f12 = 16.0f / f2;
            float f13 = span.getMin();
            float f14 = span.getMax();
            float f15 = span.getAnchor();
            SpanFacing spanFacing = span.getFacing();
            switch (1.$SwitchMap$net$minecraft$client$renderer$model$ItemModelGenerator$SpanFacing[spanFacing.ordinal()]) {
                case 1: {
                    f7 = f13;
                    f3 = f13;
                    f5 = f8 = f14 + 1.0f;
                    f9 = f15;
                    f4 = f15;
                    f6 = f15;
                    f10 = f15 + 1.0f;
                    break;
                }
                case 2: {
                    f9 = f15;
                    f10 = f15 + 1.0f;
                    f7 = f13;
                    f3 = f13;
                    f5 = f8 = f14 + 1.0f;
                    f4 = f15 + 1.0f;
                    f6 = f15 + 1.0f;
                    break;
                }
                case 3: {
                    f7 = f15;
                    f3 = f15;
                    f5 = f15;
                    f8 = f15 + 1.0f;
                    f10 = f13;
                    f4 = f13;
                    f6 = f9 = f14 + 1.0f;
                    break;
                }
                case 4: {
                    f7 = f15;
                    f8 = f15 + 1.0f;
                    f3 = f15 + 1.0f;
                    f5 = f15 + 1.0f;
                    f10 = f13;
                    f4 = f13;
                    f6 = f9 = f14 + 1.0f;
                }
            }
            f3 *= f11;
            f5 *= f11;
            f4 *= f12;
            f6 *= f12;
            f4 = 16.0f - f4;
            f6 = 16.0f - f6;
            HashMap<Direction, BlockPartFace> hashMap = Maps.newHashMap();
            hashMap.put(spanFacing.getFacing(), new BlockPartFace(null, n, string, new BlockFaceUV(new float[]{f7 *= f11, f9 *= f12, f8 *= f11, f10 *= f12}, 0)));
            switch (1.$SwitchMap$net$minecraft$client$renderer$model$ItemModelGenerator$SpanFacing[spanFacing.ordinal()]) {
                case 1: {
                    arrayList.add(new BlockPart(new Vector3f(f3, f4, 7.5f), new Vector3f(f5, f4, 8.5f), hashMap, null, true));
                    break;
                }
                case 2: {
                    arrayList.add(new BlockPart(new Vector3f(f3, f6, 7.5f), new Vector3f(f5, f6, 8.5f), hashMap, null, true));
                    break;
                }
                case 3: {
                    arrayList.add(new BlockPart(new Vector3f(f3, f4, 7.5f), new Vector3f(f3, f6, 8.5f), hashMap, null, true));
                    break;
                }
                case 4: {
                    arrayList.add(new BlockPart(new Vector3f(f5, f4, 7.5f), new Vector3f(f5, f6, 8.5f), hashMap, null, true));
                }
            }
        }
        return arrayList;
    }

    private List<Span> getSpans(TextureAtlasSprite textureAtlasSprite) {
        int n = textureAtlasSprite.getWidth();
        int n2 = textureAtlasSprite.getHeight();
        ArrayList<Span> arrayList = Lists.newArrayList();
        for (int i = 0; i < textureAtlasSprite.getFrameCount(); ++i) {
            for (int j = 0; j < n2; ++j) {
                for (int k = 0; k < n; ++k) {
                    boolean bl = !this.isTransparent(textureAtlasSprite, i, k, j, n, n2);
                    this.checkTransition(SpanFacing.UP, arrayList, textureAtlasSprite, i, k, j, n, n2, bl);
                    this.checkTransition(SpanFacing.DOWN, arrayList, textureAtlasSprite, i, k, j, n, n2, bl);
                    this.checkTransition(SpanFacing.LEFT, arrayList, textureAtlasSprite, i, k, j, n, n2, bl);
                    this.checkTransition(SpanFacing.RIGHT, arrayList, textureAtlasSprite, i, k, j, n, n2, bl);
                }
            }
        }
        return arrayList;
    }

    private void checkTransition(SpanFacing spanFacing, List<Span> list, TextureAtlasSprite textureAtlasSprite, int n, int n2, int n3, int n4, int n5, boolean bl) {
        boolean bl2;
        boolean bl3 = bl2 = this.isTransparent(textureAtlasSprite, n, n2 + spanFacing.getXOffset(), n3 + spanFacing.getYOffset(), n4, n5) && bl;
        if (bl2) {
            this.createOrExpandSpan(list, spanFacing, n2, n3);
        }
    }

    private void createOrExpandSpan(List<Span> list, SpanFacing spanFacing, int n, int n2) {
        int n3;
        Span span = null;
        for (Span span2 : list) {
            int n4;
            if (span2.getFacing() != spanFacing) continue;
            int n5 = n4 = spanFacing.isHorizontal() ? n2 : n;
            if (span2.getAnchor() != n4) continue;
            span = span2;
            break;
        }
        int n6 = spanFacing.isHorizontal() ? n2 : n;
        int n7 = n3 = spanFacing.isHorizontal() ? n : n2;
        if (span == null) {
            list.add(new Span(spanFacing, n3, n6));
        } else {
            span.expand(n3);
        }
    }

    private boolean isTransparent(TextureAtlasSprite textureAtlasSprite, int n, int n2, int n3, int n4, int n5) {
        return n2 >= 0 && n3 >= 0 && n2 < n4 && n3 < n5 ? textureAtlasSprite.isPixelTransparent(n, n2, n3) : true;
    }

    static class Span {
        private final SpanFacing spanFacing;
        private int min;
        private int max;
        private final int anchor;

        public Span(SpanFacing spanFacing, int n, int n2) {
            this.spanFacing = spanFacing;
            this.min = n;
            this.max = n;
            this.anchor = n2;
        }

        public void expand(int n) {
            if (n < this.min) {
                this.min = n;
            } else if (n > this.max) {
                this.max = n;
            }
        }

        public SpanFacing getFacing() {
            return this.spanFacing;
        }

        public int getMin() {
            return this.min;
        }

        public int getMax() {
            return this.max;
        }

        public int getAnchor() {
            return this.anchor;
        }
    }

    static enum SpanFacing {
        UP(Direction.UP, 0, -1),
        DOWN(Direction.DOWN, 0, 1),
        LEFT(Direction.EAST, -1, 0),
        RIGHT(Direction.WEST, 1, 0);

        private final Direction facing;
        private final int xOffset;
        private final int yOffset;

        private SpanFacing(Direction direction, int n2, int n3) {
            this.facing = direction;
            this.xOffset = n2;
            this.yOffset = n3;
        }

        public Direction getFacing() {
            return this.facing;
        }

        public int getXOffset() {
            return this.xOffset;
        }

        public int getYOffset() {
            return this.yOffset;
        }

        private boolean isHorizontal() {
            return this == DOWN || this == UP;
        }
    }
}

