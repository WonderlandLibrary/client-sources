package im.expensive.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import im.expensive.Furious;
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
            list = Furious.getInstance().getFunctionRegistry().getSorted(Fonts.sfui, 9 - 1.5f)
                    .stream()
                    .filter(m -> m.getCategory() != Category.Render)
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
        float posY = 4 + 18;
        int index = 0;

        if (list == null) return;

        for (Function f : list) {
            float fontSize = 6.5f;
            Animation anim = f.getAnimation();
            float value = (float) anim.getValue();
            String text = f.getName();
            float textWidth = Fonts.sfui.getWidth(text, fontSize);

            if (value != 0) {
                float localFontSize = fontSize * value;
                float localTextWidth = textWidth * value;

                DisplayUtils.drawShadow(posX, posY, localTextWidth + padding * 2, localFontSize + padding * 2, 14, ColorUtils.setAlpha(HUD.getColor(index), (int) (255 * value)));
                posY += (fontSize + padding * 1.85f) * value;
                index++;
            }
        }
        index = 0;
        posY = 4 + 18;
        for (Function f : list) {
            float fontSize = 6.5f;
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

                DisplayUtils.drawRoundedRect(posX, posY, localTextWidth + padding * 2, localFontSize + padding * 2, 1, ColorUtils.rgba(21, 21, 21, (int) (130 * value)));

                Fonts.sfui.drawText(ms, f.getName(), posX + padding, posY + padding, ColorUtils.setAlpha(HUD.getColor(index), (int) (255)), localFontSize);

                DisplayUtils.drawRectVerticalW(posX,posY,2.2f,localFontSize + padding * 2,HUD.getColor(0,1), HUD.getColor(1,0));
                posY += (fontSize + padding * 1.85f) * value;
                index++;
            }
        }

        lastIndex = index - 1;
    }
}
