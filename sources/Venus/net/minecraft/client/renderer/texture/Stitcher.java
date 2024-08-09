/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.texture;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.client.renderer.StitcherException;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.optifine.util.MathUtils;

public class Stitcher {
    private static final Comparator<Holder> COMPARATOR_HOLDER = Comparator.comparing(Stitcher::lambda$static$0).thenComparing(Stitcher::lambda$static$1).thenComparing(Stitcher::lambda$static$2);
    private final int mipmapLevelStitcher;
    private final Set<Holder> setStitchHolders = Sets.newHashSetWithExpectedSize(256);
    private final List<Slot> stitchSlots = Lists.newArrayListWithCapacity(256);
    private int currentWidth;
    private int currentHeight;
    private final int maxWidth;
    private final int maxHeight;

    public Stitcher(int n, int n2, int n3) {
        this.mipmapLevelStitcher = n3;
        this.maxWidth = n;
        this.maxHeight = n2;
    }

    public int getCurrentWidth() {
        return this.currentWidth;
    }

    public int getCurrentHeight() {
        return this.currentHeight;
    }

    public void addSprite(TextureAtlasSprite.Info info) {
        Holder holder = new Holder(info, this.mipmapLevelStitcher);
        this.setStitchHolders.add(holder);
    }

    public void doStitch() {
        ArrayList<Holder> arrayList = Lists.newArrayList(this.setStitchHolders);
        arrayList.sort(COMPARATOR_HOLDER);
        for (Holder holder : arrayList) {
            if (this.allocateSlot(holder)) continue;
            throw new StitcherException(holder.spriteInfo, arrayList.stream().map(Stitcher::lambda$doStitch$3).collect(ImmutableList.toImmutableList()), this.currentWidth, this.currentHeight, this.maxWidth, this.maxHeight);
        }
        this.currentWidth = MathHelper.smallestEncompassingPowerOfTwo(this.currentWidth);
        this.currentHeight = MathHelper.smallestEncompassingPowerOfTwo(this.currentHeight);
    }

    public void getStitchSlots(ISpriteLoader iSpriteLoader) {
        for (Slot slot : this.stitchSlots) {
            slot.getAllStitchSlots(arg_0 -> this.lambda$getStitchSlots$4(iSpriteLoader, arg_0));
        }
    }

    private static int getMipmapDimension(int n, int n2) {
        return (n >> n2) + ((n & (1 << n2) - 1) == 0 ? 0 : 1) << n2;
    }

    private boolean allocateSlot(Holder holder) {
        for (Slot slot : this.stitchSlots) {
            if (!slot.addSlot(holder)) continue;
            return false;
        }
        return this.expandAndAllocateSlot(holder);
    }

    private boolean expandAndAllocateSlot(Holder holder) {
        Slot slot;
        boolean bl;
        boolean bl2;
        int n = MathHelper.smallestEncompassingPowerOfTwo(this.currentWidth);
        int n2 = MathHelper.smallestEncompassingPowerOfTwo(this.currentHeight);
        int n3 = MathHelper.smallestEncompassingPowerOfTwo(this.currentWidth + holder.width);
        int n4 = MathHelper.smallestEncompassingPowerOfTwo(this.currentHeight + holder.height);
        boolean bl3 = n3 <= this.maxWidth;
        boolean bl4 = bl2 = n4 <= this.maxHeight;
        if (!bl3 && !bl2) {
            return true;
        }
        int n5 = MathUtils.roundDownToPowerOfTwo(this.currentHeight);
        boolean bl5 = bl = bl3 && n3 <= 2 * n5;
        if (this.currentWidth == 0 && this.currentHeight == 0) {
            bl = true;
        }
        if (bl) {
            if (this.currentHeight == 0) {
                this.currentHeight = holder.height;
            }
            slot = new Slot(this.currentWidth, 0, holder.width, this.currentHeight);
            this.currentWidth += holder.width;
        } else {
            slot = new Slot(0, this.currentHeight, this.currentWidth, holder.height);
            this.currentHeight += holder.height;
        }
        slot.addSlot(holder);
        this.stitchSlots.add(slot);
        return false;
    }

    private void lambda$getStitchSlots$4(ISpriteLoader iSpriteLoader, Slot slot) {
        Holder holder = slot.getStitchHolder();
        TextureAtlasSprite.Info info = holder.spriteInfo;
        iSpriteLoader.load(info, this.currentWidth, this.currentHeight, slot.getOriginX(), slot.getOriginY());
    }

    private static TextureAtlasSprite.Info lambda$doStitch$3(Holder holder) {
        return holder.spriteInfo;
    }

    private static ResourceLocation lambda$static$2(Holder holder) {
        return holder.spriteInfo.getSpriteLocation();
    }

    private static Integer lambda$static$1(Holder holder) {
        return -holder.width;
    }

    private static Integer lambda$static$0(Holder holder) {
        return -holder.height;
    }

    static class Holder {
        public final TextureAtlasSprite.Info spriteInfo;
        public final int width;
        public final int height;

        public Holder(TextureAtlasSprite.Info info, int n) {
            this.spriteInfo = info;
            this.width = Stitcher.getMipmapDimension(info.getSpriteWidth(), n);
            this.height = Stitcher.getMipmapDimension(info.getSpriteHeight(), n);
        }

        public String toString() {
            return "Holder{width=" + this.width + ", height=" + this.height + ", name=" + this.spriteInfo.getSpriteLocation() + "}";
        }
    }

    public static class Slot {
        private final int originX;
        private final int originY;
        private final int width;
        private final int height;
        private List<Slot> subSlots;
        private Holder holder;

        public Slot(int n, int n2, int n3, int n4) {
            this.originX = n;
            this.originY = n2;
            this.width = n3;
            this.height = n4;
        }

        public Holder getStitchHolder() {
            return this.holder;
        }

        public int getOriginX() {
            return this.originX;
        }

        public int getOriginY() {
            return this.originY;
        }

        public boolean addSlot(Holder holder) {
            if (this.holder != null) {
                return true;
            }
            int n = holder.width;
            int n2 = holder.height;
            if (n <= this.width && n2 <= this.height) {
                if (n == this.width && n2 == this.height) {
                    this.holder = holder;
                    return false;
                }
                if (this.subSlots == null) {
                    this.subSlots = Lists.newArrayListWithCapacity(1);
                    this.subSlots.add(new Slot(this.originX, this.originY, n, n2));
                    int n3 = this.width - n;
                    int n4 = this.height - n2;
                    if (n4 > 0 && n3 > 0) {
                        int n5;
                        int n6 = Math.max(this.height, n3);
                        if (n6 >= (n5 = Math.max(this.width, n4))) {
                            this.subSlots.add(new Slot(this.originX, this.originY + n2, n, n4));
                            this.subSlots.add(new Slot(this.originX + n, this.originY, n3, this.height));
                        } else {
                            this.subSlots.add(new Slot(this.originX + n, this.originY, n3, n2));
                            this.subSlots.add(new Slot(this.originX, this.originY + n2, this.width, n4));
                        }
                    } else if (n3 == 0) {
                        this.subSlots.add(new Slot(this.originX, this.originY + n2, n, n4));
                    } else if (n4 == 0) {
                        this.subSlots.add(new Slot(this.originX + n, this.originY, n3, n2));
                    }
                }
                for (Slot slot : this.subSlots) {
                    if (!slot.addSlot(holder)) continue;
                    return false;
                }
                return true;
            }
            return true;
        }

        public void getAllStitchSlots(Consumer<Slot> consumer) {
            if (this.holder != null) {
                consumer.accept(this);
            } else if (this.subSlots != null) {
                for (Slot slot : this.subSlots) {
                    slot.getAllStitchSlots(consumer);
                }
            }
        }

        public String toString() {
            return "Slot{originX=" + this.originX + ", originY=" + this.originY + ", width=" + this.width + ", height=" + this.height + ", texture=" + this.holder + ", subSlots=" + this.subSlots + "}";
        }
    }

    public static interface ISpriteLoader {
        public void load(TextureAtlasSprite.Info var1, int var2, int var3, int var4, int var5);
    }
}

