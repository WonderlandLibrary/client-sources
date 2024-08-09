package fun.ellant.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import fun.ellant.Ellant;
import fun.ellant.events.EventDisplay;
import fun.ellant.functions.impl.hud.HUD;
import fun.ellant.ui.display.ElementRenderer;
import fun.ellant.ui.styles.Style;
import fun.ellant.utils.drag.Dragging;
import fun.ellant.utils.math.Vector4i;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.Scissor;
import fun.ellant.utils.render.font.Fonts;
import fun.ellant.utils.text.GradientUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class SeeInventoryRenderer implements ElementRenderer {
    private final int INVENTORY_ROWS = 3;
    private final int INVENTORY_COLUMNS = 9;
    private final int SLOT_SIZE = 18;
    private final int SLOT_PADDING = 3;
    final Dragging dragging;
    float width;
    float height;
    private final Minecraft mc = Minecraft.getInstance();
    final ResourceLocation imagePath = new ResourceLocation("expensive/images/hud/backpack.png");
    float padding = 5;

    final float iconSize = 10;
    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack ms = eventDisplay.getMatrixStack();

        float posX = dragging.getX();
        float posY = dragging.getY();
        float fontSize = 6.5f;
        float padding = 5;

        ITextComponent name = GradientUtil.gradient("Инвентарь", ColorUtils.rgba(255, 0, 0, 160), ColorUtils.rgba(0, 255, 0, 255));

        Style style = Ellant.getInstance().getStyleManager().getCurrentStyle();

        DisplayUtils.drawShadow(posX, posY, width, height, 10, ColorUtils.rgb(30, 30, 30));
        drawStyledRect(posX, posY, width, height, 4);

        NonNullList<ItemStack> inventorySlots = mc.player.inventory.mainInventory;

// Рендеринг предметов в слотах инвентаря с сдвигом и уменьшением размера
        for (int row = 0; row < INVENTORY_ROWS; row++) {
            for (int col = 0; col < INVENTORY_COLUMNS; col++) {
                float slotX = posX + 3.5f + col * (SLOT_SIZE + SLOT_PADDING); // Начальная позиция слота по X
                float slotY = posY + height - 62 + row * (SLOT_SIZE + SLOT_PADDING); // Начальная позиция слота по Y

                // Получение индекса слота в инвентаре
                int slotIndex = col + (row + 1) * INVENTORY_COLUMNS; // Начинаем с индекса 9, чтобы пропустить хотбар

                // Получение предмета в текущем слоте инвентаря
                ItemStack itemStack = inventorySlots.get(slotIndex);

                // Сдвиг отображения предмета на 2 пикселя вправо и на 1 пиксель вниз
                float itemRenderX = slotX + 2.7f; // Сдвиг на 2 пикселя вправо
                float itemRenderY = slotY + 2f; // Сдвиг на 1 пиксель вниз

                // Размеры предмета уменьшены на 20%
                float scaleFactor = 0.8f;
                int scaledWidth = (int) (16 * scaleFactor);
                int scaledHeight = (int) (16 * scaleFactor);

                // Рендеринг предмета в слоте
                if (!itemStack.isEmpty()) {
                    GlStateManager.pushMatrix();
                    GlStateManager.scalef(scaleFactor, scaleFactor, scaleFactor); // Уменьшаем размеры предмета
                    mc.getItemRenderer().renderItemIntoGUI(itemStack, (int) (itemRenderX / scaleFactor), (int) (itemRenderY / scaleFactor));
                    GlStateManager.popMatrix();

// Отображение количества предметов, если их больше одного
                    int count = itemStack.getCount();
                    if (count > 1) {
                        String countString = String.valueOf(count);
                        float countX = itemRenderX + (count > 9 ? 8.5f : 11f); // Смещаем влево на 2.5 пикселя или 6.5 пикселя в зависимости от размера текста
                        float countY = itemRenderY + (count > 9 ? 10f : 10f); // Смещаем вниз на 1.5 пикселя

                        // Уменьшаем размер шрифта, если количество больше 9
                        float countScale = count > 9 ? 0.6f : 0.65f;

                        // Сохраняем текущую матрицу и масштабируем её
                        RenderSystem.pushMatrix();
                        RenderSystem.scalef(countScale, countScale, countScale);

                        // Рисуем текст с тенью с новым размером шрифта
                        mc.fontRenderer.drawStringWithShadow(eventDisplay.getMatrixStack(), countString, countX / countScale, countY / countScale, 0xFFFFFF);

                        // Восстанавливаем исходную матрицу
                        RenderSystem.popMatrix();
                    }



// Рендеринг предмета в слоте
                    if (!itemStack.isEmpty()) {
                        GlStateManager.pushMatrix();
                        GlStateManager.scalef(scaleFactor, scaleFactor, scaleFactor); // Уменьшаем размеры предмета
                        mc.getItemRenderer().renderItemIntoGUI(itemStack, (int) (itemRenderX / scaleFactor), (int) (itemRenderY / scaleFactor));
                        GlStateManager.popMatrix();
                    }

                }

                // Рендеринг квадрата слота
                DisplayUtils.drawRoundedRect(slotX, slotY, SLOT_SIZE, SLOT_SIZE, 4, 0x55CCCCCC);
            }
        }


        Vector4i colors = new Vector4i(HUD.getColor(0, 1), HUD.getColor(90, 1), HUD.getColor(180, 1), HUD.getColor(270, 1));
        DisplayUtils.drawImage(imagePath, posX + padding, posY + padding, iconSize, iconSize, colors);
        Scissor.push();
        Scissor.setFromComponentCoordinates(posX, posY, width, height);
        Fonts.sfui.drawCenteredText(ms, name, posX + width / 2, posY + padding + 0.5f, fontSize);

        posY += fontSize + padding * 2;

        float maxWidth = Fonts.sfMedium.getWidth(name, fontSize) + padding * 2;
        float localHeight = fontSize + padding * 2;


        DisplayUtils.drawRectHorizontalW(posX + 0.5f, posY, width - 1, 2.5f, 3, ColorUtils.rgba(0, 0, 0, (int) (255 * 0.25f)));
        Scissor.unset();
        Scissor.pop();
        width = Math.max(maxWidth, 194);
        height = localHeight + 65.0f;
        dragging.setWidth(width);
        dragging.setHeight(height);
    }


    private void drawStyledRect(float x,
                                float y,
                                float width,
                                float height,
                                float radius) {

        DisplayUtils.drawRoundedRect(x, y, width, height, radius, ColorUtils.rgba(21, 21, 21, 160));
    }

}
