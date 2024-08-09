/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.util;

import com.google.common.collect.Lists;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.Bidi;
import com.ibm.icu.text.BidiRun;
import java.util.ArrayList;
import net.minecraft.client.util.BidiReorder;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextProperties;

public class BidiReorderer {
    public static IReorderingProcessor func_243508_a(ITextProperties iTextProperties, boolean bl) {
        BidiReorder bidiReorder = BidiReorder.func_244290_a(iTextProperties, UCharacter::getMirror, BidiReorderer::func_243507_a);
        Bidi bidi = new Bidi(bidiReorder.func_244286_a(), bl ? 127 : 126);
        bidi.setReorderingMode(0);
        ArrayList<IReorderingProcessor> arrayList = Lists.newArrayList();
        int n = bidi.countRuns();
        for (int i = 0; i < n; ++i) {
            BidiRun bidiRun = bidi.getVisualRun(i);
            arrayList.addAll(bidiReorder.func_244287_a(bidiRun.getStart(), bidiRun.getLength(), bidiRun.isOddRun()));
        }
        return IReorderingProcessor.func_242241_a(arrayList);
    }

    private static String func_243507_a(String string) {
        try {
            return new ArabicShaping(8).shape(string);
        } catch (Exception exception) {
            return string;
        }
    }
}

