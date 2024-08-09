/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item.crafting;

import java.util.Iterator;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.util.math.MathHelper;

public interface IRecipePlacer<T> {
    default public void placeRecipe(int n, int n2, int n3, IRecipe<?> iRecipe, Iterator<T> iterator2, int n4) {
        int n5 = n;
        int n6 = n2;
        if (iRecipe instanceof ShapedRecipe) {
            ShapedRecipe shapedRecipe = (ShapedRecipe)iRecipe;
            n5 = shapedRecipe.getWidth();
            n6 = shapedRecipe.getHeight();
        }
        int n7 = 0;
        block0: for (int i = 0; i < n2; ++i) {
            if (n7 == n3) {
                ++n7;
            }
            boolean bl = (float)n6 < (float)n2 / 2.0f;
            int n8 = MathHelper.floor((float)n2 / 2.0f - (float)n6 / 2.0f);
            if (bl && n8 > i) {
                n7 += n;
                ++i;
            }
            for (int j = 0; j < n; ++j) {
                boolean bl2;
                if (!iterator2.hasNext()) {
                    return;
                }
                bl = (float)n5 < (float)n / 2.0f;
                n8 = MathHelper.floor((float)n / 2.0f - (float)n5 / 2.0f);
                int n9 = n5;
                boolean bl3 = bl2 = j < n5;
                if (bl) {
                    n9 = n8 + n5;
                    boolean bl4 = bl2 = n8 <= j && j < n8 + n5;
                }
                if (bl2) {
                    this.setSlotContents(iterator2, n7, n4, i, j);
                } else if (n9 == j) {
                    n7 += n - j;
                    continue block0;
                }
                ++n7;
            }
        }
    }

    public void setSlotContents(Iterator<T> var1, int var2, int var3, int var4, int var5);
}

