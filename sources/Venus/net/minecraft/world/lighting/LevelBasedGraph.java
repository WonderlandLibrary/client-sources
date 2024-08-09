/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.lighting;

import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongList;
import java.util.function.LongPredicate;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.lighting.BlockLightEngine;
import net.minecraft.world.lighting.BlockLightStorage;
import net.minecraft.world.lighting.SkyLightEngine;
import net.minecraft.world.lighting.SkyLightStorage;

public abstract class LevelBasedGraph {
    private final int levelCount;
    private final LongLinkedOpenHashSet[] updatesByLevel;
    private final Long2ByteMap propagationLevels;
    private int minLevelToUpdate;
    private volatile boolean needsUpdate;

    protected LevelBasedGraph(int n, int n2, int n3) {
        if (n >= 254) {
            throw new IllegalArgumentException("Level count must be < 254.");
        }
        this.levelCount = n;
        this.updatesByLevel = new LongLinkedOpenHashSet[n];
        int n4 = n2;
        int n5 = n3;
        if (this.getClass() != BlockLightEngine.class && this.getClass() != SkyLightEngine.class) {
            if (this.getClass() == BlockLightStorage.class || this.getClass() == SkyLightStorage.class) {
                n4 = Math.max(n2, 2048);
                n5 = Math.max(n3, 2048);
            }
        } else {
            n4 = Math.max(n2, 8192);
            n5 = Math.max(n3, 8192);
        }
        for (int i = 0; i < n; ++i) {
            this.updatesByLevel[i] = new LongLinkedOpenHashSet(this, n4, 0.5f, n2){
                final int val$p_i51298_2_;
                final LevelBasedGraph this$0;
                {
                    this.this$0 = levelBasedGraph;
                    this.val$p_i51298_2_ = n2;
                    super(n, f);
                }

                @Override
                protected void rehash(int n) {
                    if (n > this.val$p_i51298_2_) {
                        super.rehash(n);
                    }
                }
            };
        }
        this.propagationLevels = new Long2ByteOpenHashMap(this, n5, 0.5f, n3){
            final int val$p_i51298_3_;
            final LevelBasedGraph this$0;
            {
                this.this$0 = levelBasedGraph;
                this.val$p_i51298_3_ = n2;
                super(n, f);
            }

            @Override
            protected void rehash(int n) {
                if (n > this.val$p_i51298_3_) {
                    super.rehash(n);
                }
            }
        };
        this.propagationLevels.defaultReturnValue((byte)-1);
        this.minLevelToUpdate = n;
    }

    private int minLevel(int n, int n2) {
        int n3 = n;
        if (n > n2) {
            n3 = n2;
        }
        if (n3 > this.levelCount - 1) {
            n3 = this.levelCount - 1;
        }
        return n3;
    }

    private void updateMinLevel(int n) {
        int n2 = this.minLevelToUpdate;
        this.minLevelToUpdate = n;
        for (int i = n2 + 1; i < n; ++i) {
            if (this.updatesByLevel[i].isEmpty()) continue;
            this.minLevelToUpdate = i;
            break;
        }
    }

    protected void cancelUpdate(long l) {
        int n = this.propagationLevels.get(l) & 0xFF;
        if (n != 255) {
            int n2 = this.getLevel(l);
            int n3 = this.minLevel(n2, n);
            this.removeToUpdate(l, n3, this.levelCount, false);
            this.needsUpdate = this.minLevelToUpdate < this.levelCount;
        }
    }

    public void func_227465_a_(LongPredicate longPredicate) {
        LongArrayList longArrayList = new LongArrayList();
        this.propagationLevels.keySet().forEach(arg_0 -> LevelBasedGraph.lambda$func_227465_a_$0(longPredicate, longArrayList, arg_0));
        longArrayList.forEach(this::cancelUpdate);
    }

    private void removeToUpdate(long l, int n, int n2, boolean bl) {
        if (bl) {
            this.propagationLevels.remove(l);
        }
        this.updatesByLevel[n].remove(l);
        if (this.updatesByLevel[n].isEmpty() && this.minLevelToUpdate == n) {
            this.updateMinLevel(n2);
        }
    }

    private void addToUpdate(long l, int n, int n2) {
        this.propagationLevels.put(l, (byte)n);
        this.updatesByLevel[n2].add(l);
        if (this.minLevelToUpdate > n2) {
            this.minLevelToUpdate = n2;
        }
    }

    protected void scheduleUpdate(long l) {
        this.scheduleUpdate(l, l, this.levelCount - 1, true);
    }

    protected void scheduleUpdate(long l, long l2, int n, boolean bl) {
        this.propagateLevel(l, l2, n, this.getLevel(l2), this.propagationLevels.get(l2) & 0xFF, bl);
        this.needsUpdate = this.minLevelToUpdate < this.levelCount;
    }

    private void propagateLevel(long l, long l2, int n, int n2, int n3, boolean bl) {
        if (!this.isRoot(l2)) {
            boolean bl2;
            n = MathHelper.clamp(n, 0, this.levelCount - 1);
            n2 = MathHelper.clamp(n2, 0, this.levelCount - 1);
            if (n3 == 255) {
                bl2 = true;
                n3 = n2;
            } else {
                bl2 = false;
            }
            int n4 = bl ? Math.min(n3, n) : MathHelper.clamp(this.computeLevel(l2, l, n), 0, this.levelCount - 1);
            int n5 = this.minLevel(n2, n3);
            if (n2 != n4) {
                int n6 = this.minLevel(n2, n4);
                if (n5 != n6 && !bl2) {
                    this.removeToUpdate(l2, n5, n6, true);
                }
                this.addToUpdate(l2, n4, n6);
            } else if (!bl2) {
                this.removeToUpdate(l2, n5, this.levelCount, false);
            }
        }
    }

    protected final void propagateLevel(long l, long l2, int n, boolean bl) {
        int n2 = this.propagationLevels.get(l2) & 0xFF;
        int n3 = MathHelper.clamp(this.getEdgeLevel(l, l2, n), 0, this.levelCount - 1);
        if (bl) {
            this.propagateLevel(l, l2, n3, this.getLevel(l2), n2, false);
        } else {
            int n4;
            boolean bl2;
            if (n2 == 255) {
                bl2 = true;
                n4 = MathHelper.clamp(this.getLevel(l2), 0, this.levelCount - 1);
            } else {
                n4 = n2;
                bl2 = false;
            }
            if (n3 == n4) {
                this.propagateLevel(l, l2, this.levelCount - 1, bl2 ? n4 : this.getLevel(l2), n2, true);
            }
        }
    }

    protected final boolean needsUpdate() {
        return this.needsUpdate;
    }

    protected final int processUpdates(int n) {
        if (this.minLevelToUpdate >= this.levelCount) {
            return n;
        }
        while (this.minLevelToUpdate < this.levelCount && n > 0) {
            int n2;
            --n;
            LongLinkedOpenHashSet longLinkedOpenHashSet = this.updatesByLevel[this.minLevelToUpdate];
            long l = longLinkedOpenHashSet.removeFirstLong();
            int n3 = MathHelper.clamp(this.getLevel(l), 0, this.levelCount - 1);
            if (longLinkedOpenHashSet.isEmpty()) {
                this.updateMinLevel(this.levelCount);
            }
            if ((n2 = this.propagationLevels.remove(l) & 0xFF) < n3) {
                this.setLevel(l, n2);
                this.notifyNeighbors(l, n2, false);
                continue;
            }
            if (n2 <= n3) continue;
            this.addToUpdate(l, n2, this.minLevel(this.levelCount - 1, n2));
            this.setLevel(l, this.levelCount - 1);
            this.notifyNeighbors(l, n3, true);
        }
        this.needsUpdate = this.minLevelToUpdate < this.levelCount;
        return n;
    }

    public int func_227467_c_() {
        return this.propagationLevels.size();
    }

    protected abstract boolean isRoot(long var1);

    protected abstract int computeLevel(long var1, long var3, int var5);

    protected abstract void notifyNeighbors(long var1, int var3, boolean var4);

    protected abstract int getLevel(long var1);

    protected abstract void setLevel(long var1, int var3);

    protected abstract int getEdgeLevel(long var1, long var3, int var5);

    protected int queuedUpdateSize() {
        return this.propagationLevels.size();
    }

    private static void lambda$func_227465_a_$0(LongPredicate longPredicate, LongList longList, Long l) {
        if (longPredicate.test(l)) {
            longList.add(l);
        }
    }
}

