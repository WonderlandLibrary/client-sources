/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package src.Wiksi.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.List;
import src.Wiksi.events.EventDisplay;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.ui.display.ElementRenderer;
import src.Wiksi.ui.display.ElementUpdater;
import src.Wiksi.ui.styles.Style;
import src.Wiksi.utils.math.StopWatch;
import src.Wiksi.utils.render.ColorUtils;
import src.Wiksi.utils.render.DisplayUtils;
import src.Wiksi.utils.render.font.Fonts;
import src.Wiksi.Wiksi;
import net.minecraft.util.math.vector.Vector4f;
import ru.hogoshi.Animation;

public class ArrayListRenderer
implements ElementRenderer,
ElementUpdater {
    private int lastIndex;
    List<Function> list;
    StopWatch stopWatch = new StopWatch();

    @Override
    public void update(EventUpdate eventUpdate) {
        if (this.stopWatch.isReached(1000L)) {
            this.list = Wiksi.getInstance().getFunctionRegistry().getSorted(Fonts.sfbold, 7.5f).stream().filter(function -> function.getCategory() != Category.Render).filter(function -> function.getCategory() != Category.Misc).toList();
            this.stopWatch.reset();
        }
    }

    @Override
    public void render(EventDisplay eventDisplay) {
        float f;
        float f2;
        float f3;
        String string;
        float f4;
        Animation animation;
        float f5;
        MatrixStack matrixStack = eventDisplay.getMatrixStack();
        float f6 = 6.0f;
        float f7 = 3.5f;
        float f8 = 5.0f;
        float f9 = 10.0f;
        float f10 = 4.0f;
        float f11 = 4.0f + f9 + f8 * 2.0f + f8 + f9 + f8 * 2.0f + f8;
        int n = 0;
        if (this.list == null) {
            return;
        }
        for (Function function : this.list) {
            f5 = 6.5f;
            animation = function.getAnimation();
            f4 = (float)animation.getValue();
            string = function.getName();
            f3 = Fonts.sfMedium.getWidth(string, f5);
            if (f4 == 0.0f) continue;
            f2 = f5 * f4;
            f = f3 * f4;
            f11 += (f5 + f7 * 2.0f) * f4;
            ++n;
        }
        n = 0;
        f11 = 4.0f + f9 + f8 * 2.0f + f8 + f9 + f8 * 2.0f + f8;
        for (Function function : this.list) {
            f5 = 6.5f;
            animation = function.getAnimation();
            animation.update();
            int n2 = "\u5cc0\u677f\u5897\u6438".length();
            int n3 = "\u50ef\u6eb4".length();
            f4 = (float)animation.getValue();
            string = function.getName();
            f3 = Fonts.sfMedium.getWidth(string, f5);
            if (f4 == 0.0f) continue;
            f2 = f5 * f4;
            f = f3 * f4;
            boolean bl = n == 0;
            boolean bl2 = n == this.lastIndex;
            float f12 = f6;
            for (Function function2 : this.list.subList(this.list.indexOf(function) + 1, this.list.size())) {
                float f13;
                if (function2.getAnimation().getValue() == 0.0) continue;
                if (bl2) {
                    f13 = f6;
                } else {
                    int n4 = "\u5f65\u5d97\u6b56\u6a9c\u5fc6".length();
                    int n5 = "\u4f9a\u62f7".length();
                    f13 = Math.min(f3 - Fonts.sfMedium.getWidth(function2.getName(), f5), f6);
                }
                f12 = f13;
                break;
            }
            int n6 = "\u62e9\u61a8\u5db8\u5102".length();
            int n7 = "\u5b4e\u65a5\u69e0\u53f9\u5857".length();
            int n8 = "\u6756\u5b87\u68f5".length();
            Vector4f vector4f = new Vector4f(bl ? f6 : 0.0f, bl2 ? f6 : 0.0f, bl ? f6 : 0.0f, bl2 ? f6 : f12);
            float f14 = f11;
            int n9 = "\u64ad\u4eff\u63ec\u5811\u6e11".length();
            int n10 = "\u53aa".length();
            int n11 = "\u5e1f\u5440".length();
            int n12 = "\u52df\u688a\u5265\u70b9".length();
            int n13 = "\u6b32\u5291\u67e5\u6852".length();
            DisplayUtils.drawRoundedRect(f10 - 0.5f, f14 - 0.5f, f + f7 * 2.0f + 1.0f, f2 + f7 * 2.0f, 1.5f, ColorUtils.rgba(25, 26, 40, 165));
            DisplayUtils.drawRoundedRect(f10, f14, f + f7 * 2.0f + 1.0f, f2 + f7 * 2.0f, 1.0f, ColorUtils.rgba(25, 26, 40, 165));
            DisplayUtils.drawShadow(f10, f11, f + f7 * 2.0f, f2 + f7 * 2.0f, 15, ColorUtils.rgba(25, 26, 40, 165));
            Style style = Wiksi.getInstance().getStyleManager().getCurrentStyle();
            DisplayUtils.drawRectHorizontalW(f10 + 0.5f, f11 + 0.6f, 1.5, f2 + f7 * 1.3f, ColorUtils.getColor(1), ColorUtils.getColor(1));
            DisplayUtils.drawShadow(f10 + 0.5f, f11 + 0.6f, 1.5f, f2 + f7 * 1.3f, 4, ColorUtils.getColor(1), ColorUtils.getColor(1));
            int n14 = "\u66a6".length();
            int n15 = "\u57c4\u6687\u6bc4\u64a4".length();
            int n16 = "\u6402\u621c\u58e0\u6cee\u7078".length();
            int n17 = "\u7077".length();
            int n18 = "\u69fb\u6bd0\u600b\u56fb\u5952".length();
            int n19 = "\u6a23\u6839\u5041\u6488".length();
            DisplayUtils.drawShadow(f10 + f7 - 1.0f, f11 + f7 - 1.0f, Fonts.sfMedium.getWidth(function.getName(), f2) + 2.0f, Fonts.sfMedium.getHeight(f2) + 2.0f, 4, ColorUtils.getColor(1), 50);
            Fonts.sfMedium.drawText(matrixStack, function.getName(), f10 + f7, f11 + f7, ColorUtils.getColor(1), f2);
            f11 += (f5 + f7 * 2.0f) * f4;
            ++n;
        }
        int n20 = "\u50c6\u64ca\u4ee1\u640b\u63c3".length();
        this.lastIndex = n - 1;
    }
}

