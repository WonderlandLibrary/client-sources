/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.List;
import mpp.venusfr.events.EventDisplay;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.ui.display.ElementRenderer;
import mpp.venusfr.ui.display.ElementUpdater;
import mpp.venusfr.ui.styles.Style;
import mpp.venusfr.utils.math.StopWatch;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.utils.render.font.Fonts;
import mpp.venusfr.venusfr;
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
            this.list = venusfr.getInstance().getFunctionRegistry().getSorted(Fonts.gilroybold, 7.5f).stream().filter(ArrayListRenderer::lambda$update$0).filter(ArrayListRenderer::lambda$update$1).toList();
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
            f3 = Fonts.gilroybold.getWidth(string, f5);
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
            f4 = (float)animation.getValue();
            string = function.getName();
            f3 = Fonts.gilroybold.getWidth(string, f5);
            if (f4 == 0.0f) continue;
            f2 = f5 * f4;
            f = f3 * f4;
            boolean bl = n == 0;
            boolean bl2 = n == this.lastIndex;
            float f12 = f6;
            for (Function function2 : this.list.subList(this.list.indexOf(function) + 1, this.list.size())) {
                if (function2.getAnimation().getValue() == 0.0) continue;
                f12 = bl2 ? f6 : Math.min(f3 - Fonts.gilroybold.getWidth(function2.getName(), f5), f6);
                break;
            }
            Vector4f vector4f = new Vector4f(bl ? f6 : 0.0f, bl2 ? f6 : 0.0f, bl ? f6 : 0.0f, bl2 ? f6 : f12);
            float f13 = f11;
            DisplayUtils.drawRoundedRect(f10 - 0.5f, f13 - 0.5f, f + f7 * 2.0f + 1.0f, f2 + f7 * 2.0f, 1.5f, ColorUtils.rgba(25, 26, 40, 165));
            DisplayUtils.drawRoundedRect(f10, f13, f + f7 * 2.0f + 1.0f, f2 + f7 * 2.0f, 1.0f, ColorUtils.rgba(25, 26, 40, 165));
            DisplayUtils.drawShadow(f10, f11, f + f7 * 2.0f, f2 + f7 * 2.0f, 15, ColorUtils.rgba(25, 26, 40, 165));
            Style style = venusfr.getInstance().getStyleManager().getCurrentStyle();
            DisplayUtils.drawRectHorizontalW(f10 + 0.5f, f11 + 0.6f, 1.5, f2 + f7 * 1.3f, ColorUtils.getColor(1), ColorUtils.getColor(1));
            DisplayUtils.drawShadow(f10 + 0.5f, f11 + 0.6f, 1.5f, f2 + f7 * 1.3f, 4, ColorUtils.getColor(1), ColorUtils.getColor(1));
            DisplayUtils.drawShadow(f10 + f7 - 1.0f, f11 + f7 - 1.0f, Fonts.gilroybold.getWidth(function.getName(), f2) + 2.0f, Fonts.gilroybold.getHeight(f2) + 2.0f, 4, ColorUtils.getColor(1), 50);
            Fonts.gilroybold.drawText(matrixStack, function.getName(), f10 + f7, f11 + f7, ColorUtils.getColor(1), f2);
            f11 += (f5 + f7 * 2.0f) * f4;
            ++n;
        }
        this.lastIndex = n - 1;
    }

    private static boolean lambda$update$1(Function function) {
        return function.getCategory() != Category.Misc;
    }

    private static boolean lambda$update$0(Function function) {
        return function.getCategory() != Category.Visual;
    }
}

