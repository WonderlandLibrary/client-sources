/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.List;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.realms.RealmsObjectSelectionList;

public abstract class ListButton {
    public final int field_225125_a;
    public final int field_225126_b;
    public final int field_225127_c;
    public final int field_225128_d;

    public ListButton(int n, int n2, int n3, int n4) {
        this.field_225125_a = n;
        this.field_225126_b = n2;
        this.field_225127_c = n3;
        this.field_225128_d = n4;
    }

    public void func_237726_a_(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
        int n5 = n + this.field_225127_c;
        int n6 = n2 + this.field_225128_d;
        boolean bl = false;
        if (n3 >= n5 && n3 <= n5 + this.field_225125_a && n4 >= n6 && n4 <= n6 + this.field_225126_b) {
            bl = true;
        }
        this.func_230435_a_(matrixStack, n5, n6, bl);
    }

    protected abstract void func_230435_a_(MatrixStack var1, int var2, int var3, boolean var4);

    public int func_225122_a() {
        return this.field_225127_c + this.field_225125_a;
    }

    public int func_225123_b() {
        return this.field_225128_d + this.field_225126_b;
    }

    public abstract void func_225121_a(int var1);

    public static void func_237727_a_(MatrixStack matrixStack, List<ListButton> list, RealmsObjectSelectionList<?> realmsObjectSelectionList, int n, int n2, int n3, int n4) {
        for (ListButton listButton : list) {
            if (realmsObjectSelectionList.getRowWidth() <= listButton.func_225122_a()) continue;
            listButton.func_237726_a_(matrixStack, n, n2, n3, n4);
        }
    }

    public static void func_237728_a_(RealmsObjectSelectionList<?> realmsObjectSelectionList, ExtendedList.AbstractListEntry<?> abstractListEntry, List<ListButton> list, int n, double d, double d2) {
        int n2;
        if (n == 0 && (n2 = realmsObjectSelectionList.getEventListeners().indexOf(abstractListEntry)) > -1) {
            realmsObjectSelectionList.func_231400_a_(n2);
            int n3 = realmsObjectSelectionList.getRowLeft();
            int n4 = realmsObjectSelectionList.getRowTop(n2);
            int n5 = (int)(d - (double)n3);
            int n6 = (int)(d2 - (double)n4);
            for (ListButton listButton : list) {
                if (n5 < listButton.field_225127_c || n5 > listButton.func_225122_a() || n6 < listButton.field_225128_d || n6 > listButton.func_225123_b()) continue;
                listButton.func_225121_a(n2);
            }
        }
    }
}

