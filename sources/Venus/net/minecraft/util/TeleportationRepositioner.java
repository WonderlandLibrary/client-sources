/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import java.util.function.Predicate;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public class TeleportationRepositioner {
    public static Result findLargestRectangle(BlockPos blockPos, Direction.Axis axis, int n, Direction.Axis axis2, int n2, Predicate<BlockPos> predicate) {
        IntBounds intBounds;
        int n3;
        BlockPos.Mutable mutable = blockPos.toMutable();
        Direction direction = Direction.getFacingFromAxis(Direction.AxisDirection.NEGATIVE, axis);
        Direction direction2 = direction.getOpposite();
        Direction direction3 = Direction.getFacingFromAxis(Direction.AxisDirection.NEGATIVE, axis2);
        Direction direction4 = direction3.getOpposite();
        int n4 = TeleportationRepositioner.distanceInDirection(predicate, mutable.setPos(blockPos), direction, n);
        int n5 = TeleportationRepositioner.distanceInDirection(predicate, mutable.setPos(blockPos), direction2, n);
        int n6 = n4;
        IntBounds[] intBoundsArray = new IntBounds[n4 + 1 + n5];
        intBoundsArray[n4] = new IntBounds(TeleportationRepositioner.distanceInDirection(predicate, mutable.setPos(blockPos), direction3, n2), TeleportationRepositioner.distanceInDirection(predicate, mutable.setPos(blockPos), direction4, n2));
        int n7 = intBoundsArray[n4].min;
        for (n3 = 1; n3 <= n4; ++n3) {
            intBounds = intBoundsArray[n6 - (n3 - 1)];
            intBoundsArray[n6 - n3] = new IntBounds(TeleportationRepositioner.distanceInDirection(predicate, mutable.setPos(blockPos).move(direction, n3), direction3, intBounds.min), TeleportationRepositioner.distanceInDirection(predicate, mutable.setPos(blockPos).move(direction, n3), direction4, intBounds.max));
        }
        for (n3 = 1; n3 <= n5; ++n3) {
            intBounds = intBoundsArray[n6 + n3 - 1];
            intBoundsArray[n6 + n3] = new IntBounds(TeleportationRepositioner.distanceInDirection(predicate, mutable.setPos(blockPos).move(direction2, n3), direction3, intBounds.min), TeleportationRepositioner.distanceInDirection(predicate, mutable.setPos(blockPos).move(direction2, n3), direction4, intBounds.max));
        }
        n3 = 0;
        int n8 = 0;
        int n9 = 0;
        int n10 = 0;
        int[] nArray = new int[intBoundsArray.length];
        for (int i = n7; i >= 0; --i) {
            int n11;
            int n12;
            IntBounds intBounds2;
            for (int j = 0; j < intBoundsArray.length; ++j) {
                intBounds2 = intBoundsArray[j];
                n12 = n7 - intBounds2.min;
                n11 = n7 + intBounds2.max;
                nArray[j] = i >= n12 && i <= n11 ? n11 + 1 - i : 0;
            }
            Pair<IntBounds, Integer> pair = TeleportationRepositioner.largestRectInHeights(nArray);
            intBounds2 = pair.getFirst();
            n12 = 1 + intBounds2.max - intBounds2.min;
            n11 = pair.getSecond();
            if (n12 * n11 <= n9 * n10) continue;
            n3 = intBounds2.min;
            n8 = i;
            n9 = n12;
            n10 = n11;
        }
        return new Result(blockPos.func_241872_a(axis, n3 - n6).func_241872_a(axis2, n8 - n7), n9, n10);
    }

    private static int distanceInDirection(Predicate<BlockPos> predicate, BlockPos.Mutable mutable, Direction direction, int n) {
        int n2;
        for (n2 = 0; n2 < n && predicate.test(mutable.move(direction)); ++n2) {
        }
        return n2;
    }

    @VisibleForTesting
    static Pair<IntBounds, Integer> largestRectInHeights(int[] nArray) {
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        IntArrayList intArrayList = new IntArrayList();
        intArrayList.push(0);
        for (int i = 1; i <= nArray.length; ++i) {
            int n4;
            int n5 = n4 = i == nArray.length ? 0 : nArray[i];
            while (!intArrayList.isEmpty()) {
                int n6 = nArray[intArrayList.topInt()];
                if (n4 >= n6) {
                    intArrayList.push(i);
                    break;
                }
                intArrayList.popInt();
                int n7 = intArrayList.isEmpty() ? 0 : intArrayList.topInt() + 1;
                if (n6 * (i - n7) <= n3 * (n2 - n)) continue;
                n2 = i;
                n = n7;
                n3 = n6;
            }
            if (!intArrayList.isEmpty()) continue;
            intArrayList.push(i);
        }
        return new Pair<IntBounds, Integer>(new IntBounds(n, n2 - 1), n3);
    }

    public static class IntBounds {
        public final int min;
        public final int max;

        public IntBounds(int n, int n2) {
            this.min = n;
            this.max = n2;
        }

        public String toString() {
            return "IntBounds{min=" + this.min + ", max=" + this.max + "}";
        }
    }

    public static class Result {
        public final BlockPos startPos;
        public final int width;
        public final int height;

        public Result(BlockPos blockPos, int n, int n2) {
            this.startPos = blockPos;
            this.width = n;
            this.height = n2;
        }
    }
}

