package wtf.resolute.moduled.impl.render.HUD.HudElement.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import wtf.resolute.ResoluteInfo;
import wtf.resolute.evented.EventDisplay;
import wtf.resolute.evented.EventUpdate;
import wtf.resolute.moduled.Categories;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.impl.render.HUD.HUD;
import wtf.resolute.moduled.impl.render.HUD.HudElement.ElementRenderer;
import wtf.resolute.moduled.impl.render.HUD.HudElement.ElementUpdater;
import wtf.resolute.utiled.font.Fonted;
import wtf.resolute.utiled.math.StopWatch;
import wtf.resolute.utiled.render.ColorUtils;
import wtf.resolute.utiled.render.DisplayUtils;
import wtf.resolute.utiled.render.font.Fonts;
import ru.hogoshi.Animation;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

import static wtf.resolute.moduled.impl.render.HUD.HUD.arraymodel;

public class ArrayListRenderer implements ElementRenderer, ElementUpdater {
    //бб лни осоя яняхре акъдх еасвхе
    private int lastIndex;
    List<Module> list;
    StopWatch stopWatch = new StopWatch();

    public ArrayListRenderer() {
    }

    public void update(EventUpdate e) {
        if (this.stopWatch.isReached(1000L)) {
            this.list = ResoluteInfo.getInstance().getFunctionRegistry().getSorted(Fonts.sfMedium, 7.5F).stream().filter((m) -> {
                return m.getCategory() != Categories.Render;
            }).filter((m) -> {
                return m.getCategory() != Categories.Misc;
            }).toList();
            this.stopWatch.reset();
        }

    }

    public void render(EventDisplay eventDisplay) {
        MatrixStack ms = eventDisplay.getMatrixStack();
        float rounding = 6.0F;
        float padding = 3.5F;
        float posX = 4.0F;
        float posY = 44.0F;
        int index = 0;
        if (this.list != null) {
            Iterator var8 = this.list.iterator();

            Module f;
            float fontSize;
            Animation anim;
            float value;
            String text;
            float textWidth;
            while(var8.hasNext()) {
                f = (Module)var8.next();
                fontSize = 6.5F;
                anim = f.getAnimation();
                value = (float)anim.getValue();
                text = f.getName();
                textWidth = Fonts.sfMedium.getWidth(text, fontSize);
                if (value != 0.0F) {
                    float var10000 = fontSize * value;
                    var10000 = textWidth * value;
                    posY += (fontSize + padding * 2.0F) * value;
                    ++index;
                }
            }

            index = 0;
            posY = 34.0F;
            var8 = this.list.iterator();

            while(true) {
                do {
                    if (!var8.hasNext()) {
                        this.lastIndex = index - 1;
                        return;
                    }

                    f = (Module)var8.next();
                    fontSize = 6.5F;
                    anim = f.getAnimation();
                    anim.update();
                    value = (float)anim.getValue();
                    text = f.getName();
                    textWidth = Fonts.sfMedium.getWidth(text, fontSize);
                } while(value == 0.0F);

                float localFontSize = fontSize * value;
                float localTextWidth = textWidth * value;
                boolean isFirst = index == 0;
                boolean isLast = index == this.lastIndex;
                Iterator var20 = this.list.subList(this.list.indexOf(f) + 1, this.list.size()).iterator();

                while(var20.hasNext()) {
                    Module f2 = (Module)var20.next();
                    if (f2.getAnimation().getValue() != 0.0) {
                        if (!isLast) {
                            Math.min(textWidth - Fonts.sfui.getWidth(f2.getName(), fontSize), rounding);
                        }
                        break;
                    }
                }

                int firstColor = ColorUtils.getColorStyle(0.0F);
                int secondColor = ColorUtils.getColorStyle(100.0F);
                DisplayUtils.drawShadow(posX, posY, localTextWidth + padding * 2.0F, localFontSize + padding * 2.0F, 8, firstColor, secondColor);
                DisplayUtils.drawRoundedRect(posX, posY, localTextWidth + padding * 2.0F, localFontSize + padding * 2.0F, 3.0F, DisplayUtils.reAlphaInt((new Color(33, 32, 34)).getRGB(), 210));
                Fonts.sfMedium.drawText(ms, f.getName(), posX + padding, posY + padding, ColorUtils.setAlpha(HUD.getColor(index), (int)(255.0F * value)), localFontSize);
                posY += (fontSize + padding * 2.0F) * value;
                ++index;
            }
        }
    }
}
