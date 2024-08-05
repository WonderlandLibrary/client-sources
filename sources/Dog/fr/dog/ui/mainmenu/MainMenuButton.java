package fr.dog.ui.mainmenu;

import fr.dog.util.render.RenderUtil;
import fr.dog.util.render.animation.Animation;
import fr.dog.util.render.animation.Easing;
import fr.dog.util.render.font.Fonts;
import fr.dog.util.render.font.TTFFontRenderer;
import lombok.RequiredArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

@Getter
@Setter
@RequiredArgsConstructor
public class MainMenuButton {
    private final ButtonType buttonType;
    private final Runnable action;
    private float x,y;

    private final Animation hoverAnimation = new Animation(Easing.EASE_OUT_QUAD, 500);
    private final float size = 50;

    public void render(final float x, final float y, final int mouseX, final int mouseY) {
        this.x = x;
        this.y = y;

        RenderUtil.drawCircle(x,y,size,new Color(0,0,0,(int) (50 + 100*hoverAnimation.getValue())));

        TTFFontRenderer font = Fonts.getOpenSansRegular(17);
        font.drawCenteredString(buttonType.getName(),x + size / 2,y + size + 5, -1);

        buttonType.getImage().drawImg(x + size / 4f,y + size / 4f,size / 2f,size / 2f);

        hoverAnimation.run(isHovered(mouseX, mouseY) ? 1.0F : 0.0F);
    }

    public void renderBlur(final int mouseX, final int mouseY) {
        RenderUtil.drawRoundedRectGl(x, y, size, size, size/2, new Color(0,0,0));
    }

    public void click(final int mouseX, final int mouseY) {
        if (isHovered(mouseX, mouseY))
            action.run();
    }

    public boolean isHovered(final int mouseX, final int mouseY) {
        return mouseX > x && mouseX < x + size && mouseY > y && mouseY < y + size + 1;
    }
}