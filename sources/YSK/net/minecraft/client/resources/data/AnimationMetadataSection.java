package net.minecraft.client.resources.data;

import com.google.common.collect.*;
import java.util.*;

public class AnimationMetadataSection implements IMetadataSection
{
    private final int frameWidth;
    private final int frameTime;
    private final List<AnimationFrame> animationFrames;
    private final boolean interpolate;
    private final int frameHeight;
    
    private AnimationFrame getAnimationFrame(final int n) {
        return this.animationFrames.get(n);
    }
    
    public int getFrameHeight() {
        return this.frameHeight;
    }
    
    public int getFrameWidth() {
        return this.frameWidth;
    }
    
    public int getFrameTimeSingle(final int n) {
        final AnimationFrame animationFrame = this.getAnimationFrame(n);
        int n2;
        if (animationFrame.hasNoTime()) {
            n2 = this.frameTime;
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else {
            n2 = animationFrame.getFrameTime();
        }
        return n2;
    }
    
    public boolean frameHasTime(final int n) {
        int n2;
        if (this.animationFrames.get(n).hasNoTime()) {
            n2 = "".length();
            "".length();
            if (2 < 0) {
                throw null;
            }
        }
        else {
            n2 = " ".length();
        }
        return n2 != 0;
    }
    
    public int getFrameCount() {
        return this.animationFrames.size();
    }
    
    public Set<Integer> getFrameIndexSet() {
        final HashSet hashSet = Sets.newHashSet();
        final Iterator<AnimationFrame> iterator = this.animationFrames.iterator();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            hashSet.add(iterator.next().getFrameIndex());
        }
        return (Set<Integer>)hashSet;
    }
    
    public AnimationMetadataSection(final List<AnimationFrame> animationFrames, final int frameWidth, final int frameHeight, final int frameTime, final boolean interpolate) {
        this.animationFrames = animationFrames;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.frameTime = frameTime;
        this.interpolate = interpolate;
    }
    
    public boolean isInterpolate() {
        return this.interpolate;
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
            if (3 < 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public int getFrameIndex(final int n) {
        return this.animationFrames.get(n).getFrameIndex();
    }
    
    public int getFrameTime() {
        return this.frameTime;
    }
}
