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
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.font.Fonts;
import net.minecraft.util.math.vector.Vector4f;
import ru.hogoshi.Animation;

import java.util.List;

public class ArrayListRenderer implements ElementRenderer, ElementUpdater {

    private int lastIndex;
    private List<Function> list;
    private StopWatch stopWatch = new StopWatch();
    private float spacing = 2.5f; // Расстояние между закругленным


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
        float posY = 4 + 34;
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

                // Рисуем кружок вокруг текста
                DisplayUtils.drawRoundedRect(posX, posY, localTextWidth + padding * 2, localFontSize + padding * 2, rounding, ColorUtils.rgba(21, 21, 21, (int) (205 * value)));

                // Рисуем текст в кружке
                Fonts.sfui.drawText(ms, f.getName(), posX + padding, posY + padding, ColorUtils.setAlpha(HUD.getColor(index), (int) (255 * value)), localFontSize);

                posY += (fontSize + padding * 2) * value + spacing; // Добавляем расстояние между кружками
                index++;
            }
        }

        lastIndex = index - 1;
    }
}