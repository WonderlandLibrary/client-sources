/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.resources.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.client.resources.data.AnimationFrame;
import net.minecraft.client.resources.data.AnimationMetadataSectionSerializer;

public class AnimationMetadataSection {
    public static final AnimationMetadataSectionSerializer SERIALIZER = new AnimationMetadataSectionSerializer();
    public static final AnimationMetadataSection EMPTY = new AnimationMetadataSection((List)Lists.newArrayList(), -1, -1, 1, false){

        @Override
        public Pair<Integer, Integer> getSpriteSize(int n, int n2) {
            return Pair.of(n, n2);
        }
    };
    private final List<AnimationFrame> animationFrames;
    private final int frameWidth;
    private final int frameHeight;
    private final int frameTime;
    private final boolean interpolate;

    public AnimationMetadataSection(List<AnimationFrame> list, int n, int n2, int n3, boolean bl) {
        this.animationFrames = list;
        this.frameWidth = n;
        this.frameHeight = n2;
        this.frameTime = n3;
        this.interpolate = bl;
    }

    private static boolean isMultipleOf(int n, int n2) {
        return n / n2 * n2 == n;
    }

    public Pair<Integer, Integer> getSpriteSize(int n, int n2) {
        Pair<Integer, Integer> pair = this.getFrameSize(n, n2);
        int n3 = pair.getFirst();
        int n4 = pair.getSecond();
        if (AnimationMetadataSection.isMultipleOf(n, n3) && AnimationMetadataSection.isMultipleOf(n2, n4)) {
            return pair;
        }
        throw new IllegalArgumentException(String.format("Image size %s,%s is not multiply of frame size %s,%s", n, n2, n3, n4));
    }

    private Pair<Integer, Integer> getFrameSize(int n, int n2) {
        if (this.frameWidth != -1) {
            return this.frameHeight != -1 ? Pair.of(this.frameWidth, this.frameHeight) : Pair.of(this.frameWidth, n2);
        }
        if (this.frameHeight != -1) {
            return Pair.of(n, this.frameHeight);
        }
        int n3 = Math.min(n, n2);
        return Pair.of(n3, n3);
    }

    public int getFrameHeight(int n) {
        return this.frameHeight == -1 ? n : this.frameHeight;
    }

    public int getFrameWidth(int n) {
        return this.frameWidth == -1 ? n : this.frameWidth;
    }

    public int getFrameCount() {
        return this.animationFrames.size();
    }

    public int getFrameTime() {
        return this.frameTime;
    }

    public boolean isInterpolate() {
        return this.interpolate;
    }

    private AnimationFrame getAnimationFrame(int n) {
        return this.animationFrames.get(n);
    }

    public int getFrameTimeSingle(int n) {
        AnimationFrame animationFrame = this.getAnimationFrame(n);
        return animationFrame.hasNoTime() ? this.frameTime : animationFrame.getFrameTime();
    }

    public int getFrameIndex(int n) {
        return this.animationFrames.get(n).getFrameIndex();
    }

    public Set<Integer> getFrameIndexSet() {
        HashSet<Integer> hashSet = Sets.newHashSet();
        for (AnimationFrame animationFrame : this.animationFrames) {
            hashSet.add(animationFrame.getFrameIndex());
        }
        return hashSet;
    }
}

