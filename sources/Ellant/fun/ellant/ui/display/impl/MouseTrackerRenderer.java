package fun.ellant.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import fun.ellant.Ellant;
import fun.ellant.events.EventDisplay;
import fun.ellant.ui.display.ElementRenderer;
import fun.ellant.ui.styles.Style;
import fun.ellant.utils.drag.Dragging;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.Scissor;
import fun.ellant.utils.player.MouseUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.concurrent.TimeUnit;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MouseTrackerRenderer implements ElementRenderer {

    final Dragging mousedrag;
    float width;
    float height;
    float lastPosX;
    float lastPosY;
    long lastTime;
    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack ms = eventDisplay.getMatrixStack();

        float posX = mousedrag.getX();
        float posY = mousedrag.getY();
        float size = 40.0f; // Увеличенный размер квадрата
        float padding = 5.0f;

        Style style = Ellant.getInstance().getStyleManager().getCurrentStyle();

        DisplayUtils.drawShadow(posX, posY, size, size, 10, style.getFirstColor().getRGB(), style.getSecondColor().getRGB());
        drawStyledRect(posX, posY, size, size, 4);
        Scissor.push();
        Scissor.setFromComponentCoordinates(posX, posY, size, size);

        // Draw the circle in the center
        float circleSize = 12.0f; // Размер круга
        float circleX = posX + size / 2; // Центр квадрата по оси X
        float circleY = posY + size / 2; // Центр квадрата по оси Y

        // Определяем изменение позиции мыши
        float deltaX = posX - lastPosX;
        float deltaY = posY - lastPosY;
        long currentTime = System.nanoTime();
        long deltaTime = TimeUnit.NANOSECONDS.toSeconds(currentTime - lastTime); // Используйте TimeUnit для получения разницы в секундах
        lastTime = currentTime;
        lastPosX = posX;
        lastPosY = posY;

        // Определение границы правой части квадрата
        float rightBoundary = posX + size * 0.5f;

        // Проверка, находится ли мышь в правой части квадрата
        boolean isMouseInRightArea = MouseUtil.getMouseX() > rightBoundary;

        // Если мышь в левой части квадрата и происходит движение влево, смещаем круг влево
        if (!isMouseInRightArea && deltaX < 0) {
            // Определяем, на сколько можно сместить круг влево, чтобы он оставался в пределах квадрата
            float maxLeftShift = circleX - posX - circleSize / 2;
            // Смещаем круг влево, но не более чем до границы квадрата
            circleX -= Math.min(Math.abs(deltaX), maxLeftShift);
        }

        // Если прошло более 0.2 секунды с момента последнего движения, смещаем круг в центр
        if (deltaTime > 0.2) {
            circleX = posX + size / 2;
            circleY = posY + size / 2;
        }

        // Отрисовываем круг
        DisplayUtils.drawCircle(circleX, circleY, circleSize / 2, ColorUtils.getColor(255));

        Scissor.unset();
        Scissor.pop();
        mousedrag.setWidth(size);
        mousedrag.setHeight(size);
    }

    private void drawStyledRect(float x,
                                float y,
                                float width,
                                float height,
                                float radius) {
        DisplayUtils.drawRoundedRect(x - 0.5f, y - 0.5f, width + 1, height + 1, radius + 0.5f, ColorUtils.getColor(0)); // Outline
        DisplayUtils.drawRoundedRect(x, y, width, height, radius, ColorUtils.rgba(21, 21, 21, 255)); // Background
    }

    public boolean isEmpty() {
        return true; // Since it's just a visual element, it doesn't contain any specific content to check
    }
}
