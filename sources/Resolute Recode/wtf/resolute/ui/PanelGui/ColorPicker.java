package wtf.resolute.ui.PanelGui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.AbstractGui;
import wtf.resolute.utiled.render.ColorUtils;

import java.awt.*;

public class ColorPicker extends AbstractGui {
    private int x, y, width, height;
    private Color selectedColor;

    public ColorPicker(int x, int y, int width, int height, Color initialColor) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.selectedColor = initialColor;
    }

    public void render(MatrixStack stack, float mouseX, float mouseY) {
        // Отобразить палитру цветов
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color color = new Color(Color.HSBtoRGB((float) i / width, (float) j / height, 1.0f));
                fill(stack, x + i, y + j, x + i + 1, y + j + 1, color.getRGB());
            }
        }

        // Отобразить выбранный цвет
        fill(stack, x + width + 10, y, x + width + 20, y + 10, selectedColor.getRGB());

        // Обработать выбор цвета
        if (isHovered(mouseX, mouseY) && net.minecraft.client.Minecraft.getInstance().mouseHelper.isLeftDown()) {
            int colorX = (int) (mouseX - x);
            int colorY = (int) (mouseY - y);
            selectedColor = new Color(Color.HSBtoRGB((float) colorX / width, (float) colorY / height, 1.0f));
        }
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    private boolean isHovered(float mouseX, float mouseY) {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }
}
