package club.bluezenith.module.modules.render.targethuds.components;

import club.bluezenith.events.impl.Render2DEvent;
import club.bluezenith.module.modules.render.TargetHUD;
import club.bluezenith.module.modules.render.targethuds.components.provider.ColorProvider;
import club.bluezenith.util.render.RenderUtil;

import java.awt.*;

import static club.bluezenith.util.render.RenderUtil.darken;
import static java.lang.Math.abs;

public class Bar extends Component {

    float currentWidth = -1F;
    float prevWidth = -1F;
    boolean drawEasing;
    int easingDarkener = 80;
    boolean drawBackground;
    ColorProvider backgroundColorProvider;

    @Override
    public void render(Render2DEvent event, TargetHUD targetHUD) {
        if(currentWidth == -1F)
            currentWidth = width;
        if(prevWidth == -1F)
            prevWidth = currentWidth;

        final float targetWidth = width * progressProvider.getProgress(target);
        currentWidth = widthProvider.getWidth(targetWidth, currentWidth, target);

        if(drawEasing && abs(currentWidth - targetWidth) < 0.08) {
            prevWidth = widthProvider.getWidth(currentWidth, prevWidth, target);
        }

        final int color = colorProvider.getColorFromTarget(target);
        if(drawBackground) {
            RenderUtil.rect(posX, posY, posX + width, posY + height, backgroundColorProvider.getColorFromTarget(target));
        }
        if(drawEasing) {
            RenderUtil.rect(posX, posY, posX + prevWidth, posY + height, darken(new Color(color), easingDarkener));
        }
        RenderUtil.rect(posX, posY, posX + currentWidth, posY + height, color);
    }

    public Bar drawEasing(boolean drawEasing) {
        this.drawEasing = drawEasing;
        return this;
    }

    public Bar easingDarkener(int easingDarkener) {
        this.easingDarkener = easingDarkener;
        return this;
    }

    public Bar backgroundColorProvider(ColorProvider backgroundColorProvider) {
        this.backgroundColorProvider = backgroundColorProvider;
        return this;
    }

    public Bar drawBackground(boolean drawBackground) {
        this.drawBackground = drawBackground;
        return this;
    }


}
