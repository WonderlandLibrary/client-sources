package src.Wiksi.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import src.Wiksi.Wiksi;
import src.Wiksi.events.EventDisplay;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.impl.render.HUD;
import src.Wiksi.ui.display.ElementRenderer;
import src.Wiksi.ui.display.ElementUpdater;
import src.Wiksi.utils.math.StopWatch;
import src.Wiksi.utils.render.ColorUtils;
import src.Wiksi.utils.render.DisplayUtils;
import src.Wiksi.utils.render.font.Fonts;
import net.minecraft.util.math.vector.Vector4f;
import ru.hogoshi.Animation;

import java.util.List;

public class ArrayList2Renderer implements ElementRenderer, ElementUpdater {

    private int lastIndex;

    List<Function> list;


    StopWatch stopWatch = new StopWatch();

    @Override
    public void update(EventUpdate e) {
        if (stopWatch.isReached(1000)) {
            list = Wiksi.getInstance().getFunctionRegistry().getSorted(Fonts.sfui, 9 - 1.5f)
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
        float rounding = 4;
        float padding = 1.5f;
        float posX = 4;
        float posY = 4 + 2;
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

                //DisplayUtils.drawShadow(posX, posY, localTextWidth + padding * 2, localFontSize + padding + 3, 20, ColorUtils.setAlpha(HUD.getColor(index), (int) (255 * value)));
                posY += (fontSize + padding * 3) * value;
                index++;
            }
        }
        index = 0;
        posY = 4 + 2;
        for (Function f : list) {
            float fontSize = 6.5f;
            Animation anim = f.getAnimation();
            anim.update();

            float value = (float) anim.getValue();

            String text = f.getName();
            float textWidth = Fonts.sfui.getWidth(text, fontSize);

            if (value != 0) {
                float localFontSize = fontSize * value;
                float localTextWidth = textWidth + value;

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

                //DisplayUtils.drawShadow(posX, posY, localTextWidth + padding * 2, localFontSize + padding + 3, 6, ColorUtils.getColor(90) );
                //DisplayUtils.drawRoundedRect(posX, posY, localTextWidth + padding * 3, localFontSize + padding + 6,  4, ColorUtils.getColor(0)); // outline
                DisplayUtils.drawRoundedRect(posX, posY + 20, localTextWidth + padding * 3, localFontSize + padding + 6, 4, ColorUtils.rgba(21, 21, 21, 235));

                Fonts.sfui.drawText(ms, f.getName(), posX + padding + 0.3f, posY + padding + 22, ColorUtils.setAlpha(HUD.getColor(index), (int) (255 * value)), localFontSize);
                //ColorUtils.setAlpha(HUD.getColor(index), (int) (255 * value))

                posY += (fontSize + padding + 8) * value;
                index++;
            }
        }

        lastIndex = index - 1;
    }
}