package fun.ellant.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import fun.ellant.Ellant;
import fun.ellant.events.EventDisplay;
import fun.ellant.events.EventUpdate;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.impl.hud.HUD;
import fun.ellant.ui.display.ElementRenderer;
import fun.ellant.ui.display.ElementUpdater;
import fun.ellant.utils.math.StopWatch;
import fun.ellant.utils.math.Vector4i;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.font.Fonts;
import net.minecraft.util.math.vector.Vector4f;
import ru.hogoshi.Animation;

import java.util.List;

public class AncientArrayList implements ElementRenderer, ElementUpdater {

    private int lastIndex;

    List<Function> list;


    StopWatch stopWatch = new StopWatch();

    @Override
    public void update(EventUpdate e) {
        if (stopWatch.isReached(1000)) {
            list = Ellant.getInstance().getFunctionRegistry().getSorted(Fonts.sfui, 9 - 1.5f)
                    .stream()
                    .filter(m -> m.getCategory() != Category.RENDER)
                    .filter(m -> m.getCategory() != Category.PLAYER)
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
            float fontSize = 6.5f;
            Animation anim = f.getAnimation();
            float value = (float) anim.getValue();
            String text = f.getName();
            float textWidth = Fonts.sfui.getWidth(text, fontSize);

            if (value != 0) {
                float localFontSize = fontSize * value;
                float localTextWidth = textWidth * value;

                DisplayUtils.drawShadow(posX + 1, posY + 4f, localTextWidth + padding * 2 - 7, localFontSize + padding * 2 - 7, 14, ColorUtils.setAlpha(HUD.getColor(index), (int) (255 * value)));
                posY += (fontSize + padding * 2) * value;
                index++;
            }
        }
        index = 0;
        posY = 4 + 28;
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
                Vector4f rectVect = new Vector4f(HUD.getColor(0, 1.0F), HUD.getColor(0, 1.0F), HUD.getColor(90, 1.0F), HUD.getColor(90, 1.0F));

                DisplayUtils.drawRoundedRect(posX + 6, posY, localTextWidth + padding * 2, localFontSize + padding * 2, rectVect, ColorUtils.rgba(0, 0, 0/*21, 21, 21*/, (int) (255 * value)));
                DisplayUtils.drawRoundedRect(posX + 4, posY, 2f, 14f, new Vector4f(0.0F, 0.0F, 0.0F, 0.0F), new Vector4i(HUD.getColor(0, 1.0F), HUD.getColor(0, 1.0F), HUD.getColor(90, 1.0F), HUD.getColor(90, 1.0F)));
                Fonts.sfui.drawText(ms, f.getName(), posX + padding + 5, posY + padding, ColorUtils.setAlpha(HUD.getColor(index), (int) (255 * value)), localFontSize);

                posY += (fontSize + padding * 2) * value;
                index++;
            }
        }

        lastIndex = index - 1;
    }
}
