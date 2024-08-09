package im.expensive.utils.components;

import com.mojang.blaze3d.matrix.MatrixStack;
import im.expensive.utils.math.MathUtil;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.DisplayUtils;
import im.expensive.utils.render.font.Fonts;
import lombok.Getter;
import lombok.Setter;

public class    ButtonComponent {

    @Setter
    @Getter
    float x, y, width, height;
    public String name;
    Runnable action;
    public ButtonComponent(float x, float y, float width, float height, String name, Runnable action) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.name = name;
        this.action = action;
    }

    public void draw(MatrixStack stack, float mouseX, float mouseY) {
        DisplayUtils.drawRoundedRect(x,y,width,height, 3, ColorUtils.rgba(27, 27, 27, 255));
        Fonts.montserrat.drawCenteredText(stack,name, x + width / 2f, y + height / 2f - 6 / 2f, -1, 6);
    }

    public void click(int mouseX, int mouseY) {
        if (MathUtil.isHovered(mouseX,mouseY,x,y,width,height)) {
            action.run();
        }
    }

}
