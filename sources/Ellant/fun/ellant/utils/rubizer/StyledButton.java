package fun.ellant.utils.rubizer;

import com.mojang.blaze3d.matrix.MatrixStack;
import fun.ellant.functions.impl.hud.HUD;
import fun.ellant.utils.math.MathUtil;
import fun.ellant.utils.math.Vector4i;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.font.Fonts;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraft.util.text.ITextComponent;

public class StyledButton extends Button {
    public StyledButton(int x, int y, int width, int height, ITextComponent text, IPressable onPress) {
        super(x, y, width, height, text, onPress);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        float x = this.x;
        float y = this.y;
        float width = this.width;
        float height = this.height;
        float radius = 5; // Радиус скругления углов

        // Отрисовываем стилизованную кнопку
        drawStyledRect(matrixStack, x, y, width, height + 3, radius + 1);
        // Отрисовываем тень
        int shadowColor = ColorUtils.rgba(0, 0, 0, 150);
        DisplayUtils.drawShadow(x + 2, y + 2, width, height, 10, shadowColor, shadowColor);

        // Отрисовываем текст на кнопке
        int color = ColorUtils.rgb(161, 164, 177);
        if (MathUtil.isHovered(mouseX, mouseY, x, y, width, height)) {
            color = ColorUtils.rgb(255, 255, 255);
        }
        Fonts.montserrat.drawCenteredText(matrixStack, this.getMessage().getString(), x + width / 2f, y + height / 2f - 5.5f + 2, color, 10f);
    }

    private void drawStyledRect(MatrixStack matrixStack, float x, float y, float width, float height, float radius) {
        Vector4i colors = new Vector4i(HUD.getColor(0, 1), HUD.getColor(90, 1), HUD.getColor(180, 1), HUD.getColor(270, 1));
        DisplayUtils.drawRoundedRect(x - 1.5f, y - 1.5f, width + 3f, height + 3f, new Vector4f(7,7,7,7),colors);
        DisplayUtils.drawRoundedRect(x, y, width, height, radius - 0.5f, ColorUtils.rgba(21, 21, 21, 255));
    }
}
