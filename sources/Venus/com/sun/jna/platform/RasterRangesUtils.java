/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.platform;

import java.awt.Rectangle;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.MultiPixelPackedSampleModel;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.SinglePixelPackedSampleModel;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class RasterRangesUtils {
    private static final int[] subColMasks = new int[]{128, 64, 32, 16, 8, 4, 2, 1};
    private static final Comparator<Object> COMPARATOR = new Comparator<Object>(){

        @Override
        public int compare(Object object, Object object2) {
            return ((Rectangle)object).x - ((Rectangle)object2).x;
        }
    };

    public static boolean outputOccupiedRanges(Raster raster, RangesOutput rangesOutput) {
        Object object;
        boolean bl;
        Rectangle rectangle = raster.getBounds();
        SampleModel sampleModel = raster.getSampleModel();
        boolean bl2 = bl = sampleModel.getNumBands() == 4;
        if (raster.getParent() == null && rectangle.x == 0 && rectangle.y == 0 && ((DataBuffer)(object = raster.getDataBuffer())).getNumBanks() == 1) {
            if (sampleModel instanceof MultiPixelPackedSampleModel) {
                MultiPixelPackedSampleModel multiPixelPackedSampleModel = (MultiPixelPackedSampleModel)sampleModel;
                if (multiPixelPackedSampleModel.getPixelBitStride() == 1) {
                    return RasterRangesUtils.outputOccupiedRangesOfBinaryPixels(((DataBufferByte)object).getData(), rectangle.width, rectangle.height, rangesOutput);
                }
            } else if (sampleModel instanceof SinglePixelPackedSampleModel && sampleModel.getDataType() == 3) {
                return RasterRangesUtils.outputOccupiedRanges(((DataBufferInt)object).getData(), rectangle.width, rectangle.height, bl ? -16777216 : 0xFFFFFF, rangesOutput);
            }
        }
        object = raster.getPixels(0, 0, rectangle.width, rectangle.height, (int[])null);
        return RasterRangesUtils.outputOccupiedRanges((int[])object, rectangle.width, rectangle.height, bl ? -16777216 : 0xFFFFFF, rangesOutput);
    }

    public static boolean outputOccupiedRangesOfBinaryPixels(byte[] byArray, int n, int n2, RangesOutput rangesOutput) {
        HashSet<Object> hashSet = new HashSet<Object>();
        TreeSet<Object> cloneable = Collections.EMPTY_SET;
        int n3 = byArray.length / n2;
        for (int i = 0; i < n2; ++i) {
            TreeSet<Object> cloneable2 = new TreeSet<Object>(COMPARATOR);
            int n4 = i * n3;
            int n5 = -1;
            for (int j = 0; j < n3; ++j) {
                int n6 = j << 3;
                byte by = byArray[n4 + j];
                if (by == 0) {
                    if (n5 < 0) continue;
                    cloneable2.add(new Rectangle(n5, i, n6 - n5, 1));
                    n5 = -1;
                    continue;
                }
                if (by == 255) {
                    if (n5 >= 0) continue;
                    n5 = n6;
                    continue;
                }
                for (int k = 0; k < 8; ++k) {
                    int n7 = n6 | k;
                    if ((by & subColMasks[k]) != 0) {
                        if (n5 >= 0) continue;
                        n5 = n7;
                        continue;
                    }
                    if (n5 < 0) continue;
                    cloneable2.add(new Rectangle(n5, i, n7 - n5, 1));
                    n5 = -1;
                }
            }
            if (n5 >= 0) {
                cloneable2.add(new Rectangle(n5, i, n - n5, 1));
            }
            Set<Rectangle> set = RasterRangesUtils.mergeRects((Set<Rectangle>)cloneable, cloneable2);
            hashSet.addAll(set);
            cloneable = cloneable2;
        }
        hashSet.addAll(cloneable);
        for (Rectangle rectangle : hashSet) {
            if (rangesOutput.outputRange(rectangle.x, rectangle.y, rectangle.width, rectangle.height)) continue;
            return true;
        }
        return false;
    }

    public static boolean outputOccupiedRanges(int[] nArray, int n, int n2, int n3, RangesOutput rangesOutput) {
        HashSet<Object> hashSet = new HashSet<Object>();
        TreeSet<Object> cloneable = Collections.EMPTY_SET;
        for (int i = 0; i < n2; ++i) {
            TreeSet<Object> cloneable2 = new TreeSet<Object>(COMPARATOR);
            int n4 = i * n;
            int n5 = -1;
            for (int j = 0; j < n; ++j) {
                if ((nArray[n4 + j] & n3) != 0) {
                    if (n5 >= 0) continue;
                    n5 = j;
                    continue;
                }
                if (n5 < 0) continue;
                cloneable2.add(new Rectangle(n5, i, j - n5, 1));
                n5 = -1;
            }
            if (n5 >= 0) {
                cloneable2.add(new Rectangle(n5, i, n - n5, 1));
            }
            Set<Rectangle> set = RasterRangesUtils.mergeRects((Set<Rectangle>)cloneable, cloneable2);
            hashSet.addAll(set);
            cloneable = cloneable2;
        }
        hashSet.addAll(cloneable);
        for (Rectangle rectangle : hashSet) {
            if (rangesOutput.outputRange(rectangle.x, rectangle.y, rectangle.width, rectangle.height)) continue;
            return true;
        }
        return false;
    }

    private static Set<Rectangle> mergeRects(Set<Rectangle> set, Set<Rectangle> set2) {
        HashSet<Rectangle> hashSet = new HashSet<Rectangle>(set);
        if (!set.isEmpty() && !set2.isEmpty()) {
            Rectangle[] rectangleArray = set.toArray(new Rectangle[set.size()]);
            Rectangle[] rectangleArray2 = set2.toArray(new Rectangle[set2.size()]);
            int n = 0;
            int n2 = 0;
            while (n < rectangleArray.length && n2 < rectangleArray2.length) {
                while (rectangleArray2[n2].x < rectangleArray[n].x) {
                    if (++n2 != rectangleArray2.length) continue;
                    return hashSet;
                }
                if (rectangleArray2[n2].x == rectangleArray[n].x && rectangleArray2[n2].width == rectangleArray[n].width) {
                    hashSet.remove(rectangleArray[n]);
                    rectangleArray2[n2].y = rectangleArray[n].y;
                    rectangleArray2[n2].height = rectangleArray[n].height + 1;
                    ++n2;
                    continue;
                }
                ++n;
            }
        }
        return hashSet;
    }

    public static interface RangesOutput {
        public boolean outputRange(int var1, int var2, int var3, int var4);
    }
}

