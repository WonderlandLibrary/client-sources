package im.expensive.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import im.expensive.Expensive;
import im.expensive.events.EventDisplay;
import im.expensive.events.EventUpdate;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.impl.render.HUD;
import im.expensive.ui.display.ElementRenderer;
import im.expensive.ui.display.ElementUpdater;
import im.expensive.utils.math.StopWatch;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.DisplayUtils;
import im.expensive.utils.render.font.Fonts;
import net.minecraft.util.math.vector.Vector4f;
import ru.hogoshi.Animation;

import java.util.List;

public class ArrayListRenderer implements ElementRenderer, ElementUpdater {

    private int lastIndex;

    List<Function> list;


    StopWatch stopWatch = new StopWatch();

    @Override
    public void update(EventUpdate e) {
        if (stopWatch.isReached(1000)) {
            list = Expensive.getInstance().getFunctionRegistry().getSorted(Fonts.sfui, 9 - 1.5f)
                    .stream()
                    .filter(m -> m.getCategory() != Category.Render)
                    .filter(m -> m.getCategory() != Category.Misc)
                    .toList();
            stopWatch.reset();
        }
    }

    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack ms = eventDisplay.getMatrixStack();
        float rounding = 6;
        float padding = 3.5f;
        float posX = 4;
        float posY = 4 + 28;
        int index = 0;

        if (list == null) return;

        for (Function f : list) {
            float fontSize = 8;
            Animation anim = f.getAnimation();
            float value = (float) anim.getValue();
            String text = f.getName();
            float textWidth = Fonts.sfui.getWidth(text, fontSize);

            if (value != 0) {
                float localFontSize = fontSize * value;
                float localTextWidth = textWidth * value;

                DisplayUtils.drawShadow(posX, posY, localTextWidth + padding * 2, localFontSize + padding * 2, 14, ColorUtils.setAlpha(HUD.getColor(index), (int) (255 * value)));
                posY += (fontSize + padding * 2) * value;
                index++;
            }
        }
        index = 0;
        posY = 4 + 28;
        for (Function f : list) {
            float fontSize = 8;
            Animation anim = f.getAnimation();
            anim.update();

            float value = (float) anim.getValue();

            String text = f.getName();
            float textWidth = Fonts.sfui.getWidth(text, fontSize);

            if (value != 0) {
                float localFontSize = fontSize * value;
                float localTextWidth = textWidth * value;

                boolean isFirst = index == 0;
                boolean isLast = index == lastIndex;

                float localRounding = rounding;

                for (Function f2 : list.subList(list.indexOf(f) + 1, list.size())) { // predict next active module
                    if (f2.getAnimation().getValue() != 0) {
                        localRounding = isLast ? rounding : Math.min(textWidth - Fonts.sfui.getWidth(f2.getName(), fontSize), rounding);
                        break;
                    }
                }
                
                Vector4f rectVec = new Vector4f(isFirst ? rounding : 0, isLast ? rounding : 0, isFirst ? rounding : 0, isLast ? rounding : localRounding);

                DisplayUtils.drawRoundedRect(posX, posY, localTextWidth + padding * 2, localFontSize + padding * 2, rectVec, ColorUtils.rgba(21, 21, 21, (int) (255 * value)));

                Fonts.sfui.drawText(ms, f.getName(), posX + padding, posY + padding, ColorUtils.setAlpha(HUD.getColor(index), (int) (255 * value)), localFontSize);

                posY += (fontSize + padding * 2) * value;
                index++;
            }
        }

        lastIndex = index - 1;
    }
}
