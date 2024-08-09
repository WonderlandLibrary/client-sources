package fun.ellant.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import ru.hogoshi.Animation;
import fun.ellant.Ellant;
import fun.ellant.events.EventDisplay;
import fun.ellant.events.EventUpdate;
import fun.ellant.functions.api.Function;
import fun.ellant.ui.display.ElementRenderer;
import fun.ellant.ui.display.ElementUpdater;
import fun.ellant.utils.math.StopWatch;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.font.Fonts;

public class ArrayListRenderer2 implements ElementRenderer, ElementUpdater {
    private int lastIndex;
    List<Function> list;
    StopWatch stopWatch = new StopWatch();

    public void update(EventUpdate e) {
        if (this.stopWatch.isReached(1000L)) {
            this.list = Ellant.getInstance().getFunctionRegistry().getSorted(Fonts.sfui, 7.5F).stream().toList();
            this.stopWatch.reset();
        }

    }

    public void render(EventDisplay eventDisplay) {
        MatrixStack ms = eventDisplay.getMatrixStack();
        float rounding = 6.0F;
        float padding = 3.5F;
        Minecraft mc = Minecraft.getInstance();
        int screenWidth = mc.getMainWindow().getScaledWidth();
        float posY = 0.0F;
        int index = 0;
        if (this.list != null) {
            Iterator var9 = this.list.iterator();

            Function f;
            float fontSize;
            Animation anim;
            float value;
            String text;
            while(var9.hasNext()) {
                f = (Function)var9.next();
                fontSize = 6.5F;
                anim = f.getAnimation();
                value = (float)anim.getValue();
                text = f.getName();
                Fonts.sfui.getWidth(text, fontSize);
                if (value != 0.0F) {
                    posY += (fontSize + padding * 2.0F) * value;
                    ++index;
                }
            }

            index = 0;
            posY = 5.0F;
            var9 = this.list.iterator();

            while(var9.hasNext()) {
                f = (Function)var9.next();
                fontSize = 6.5F;
                anim = f.getAnimation();
                anim.update();
                value = (float)anim.getValue();
                text = f.getName();
                float textWidth = Fonts.sfui.getWidth(text, fontSize);
                if (value != 0.0F) {
                    float localFontSize = fontSize * value;
                    float localTextWidth = textWidth * value;
                    float posX = (float)screenWidth - localTextWidth - padding * 2.0F - 4.0F;
                    boolean isFirst = index == 0;
                    boolean isLast = index == this.lastIndex;
                    float topLeftRadius = isFirst ? rounding : 0.0F;
                    float bottomLeftRadius = isLast ? rounding : 0.0F;
                    DisplayUtils.drawRoundedRect(posX, posY, localTextWidth + padding * 2.0F, localFontSize + padding * 2.0F, value, ColorUtils.rgba(25, 25, 25, 250));
                    DisplayUtils.drawShadow(posX, posY, localTextWidth + padding * 2.0F + 3.0F, localFontSize + padding * 2.0F + 3.0F, 6, ColorUtils.rgba(25, 25, 25, 250));
                    Fonts.sfui.drawText(ms, f.getName(), posX + padding, posY + padding, ColorUtils.rgb(255, 255, 255), localFontSize);
                    posY += (fontSize + padding * 2.0F - 1.0F) * value;
                    ++index;
                }
            }

            this.lastIndex = index - 1;
        }
    }
}