package src.Wiksi.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import src.Wiksi.Wiksi;
import src.Wiksi.events.EventDisplay;
import src.Wiksi.functions.impl.render.HUD;
import src.Wiksi.ui.display.ElementRenderer;
import src.Wiksi.ui.styles.Style;
import src.Wiksi.utils.drag.Dragging;
import src.Wiksi.utils.math.Vector4i;
import src.Wiksi.utils.render.ColorUtils;
import src.Wiksi.utils.render.DisplayUtils;
import src.Wiksi.utils.render.KawaseBlur;
import src.Wiksi.utils.render.Scissor;
import src.Wiksi.utils.render.font.Fonts;
import src.Wiksi.utils.text.GradientUtil;
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
public class SeeInventory2Renderer implements ElementRenderer {
    private final int INVENTORY_ROWS = 3;
    private final int INVENTORY_COLUMNS = 9;
    private final int SLOT_SIZE = 18;
    private final int SLOT_PADDING = 2;
    final Dragging dragging;
    float width;
    float height;
    private final Minecraft mc = Minecraft.getInstance();
    float padding = 5;

    final ResourceLocation bag = new ResourceLocation("Wiksi/images/hud/bag1.png");
    final float iconSize = 10;
    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack ms = eventDisplay.getMatrixStack();

        float posX = dragging.getX();
        float posY = dragging.getY();
        float fontSize = 6.5f;
        float padding = 5;

        ITextComponent name = GradientUtil.gradient("Inventory");

        Style style = Wiksi.getInstance().getStyleManager().getCurrentStyle();

        //DisplayUtils.drawShadow(posX, posY, width, height, 10, style.getFirstColor().getRGB(), style.getSecondColor().getRGB());
        drawStyledRect(posX, posY, width, height, 4);

        NonNullList<ItemStack> inventorySlots = mc.player.inventory.mainInventory;

        for (int row = 0; row < INVENTORY_ROWS; row++) {
            for (int col = 0; col < INVENTORY_COLUMNS; col++) {
                float slotX = posX + 3.5f + col * (SLOT_SIZE + SLOT_PADDING); // Начальная позиция слота по X
                float slotY = posY + height - 62 + row * (SLOT_SIZE + SLOT_PADDING); // Начальная позиция слота по Y

                // Получение индекса слота в инвентаре
                int slotIndex = col + (row + 1) * INVENTORY_COLUMNS;

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

                    int count = itemStack.getCount();
                    if (count > 1) {
                        String countString = String.valueOf(count);
                        float countX = itemRenderX + (count > 9 ? 8.5f : 11f);
                        float countY = itemRenderY + (count > 9 ? 10f : 10f);

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
        DisplayUtils.drawImage(bag, posX + padding, posY + padding, iconSize, iconSize, ColorUtils.rgb(255,255,255));
        Scissor.push();
        Scissor.setFromComponentCoordinates(posX, posY, width, height);
        Fonts.sfui.drawText(ms, "Inventory", posX + 20, posY + padding + 1.5f,ColorUtils.rgb(255,255,255), 6.5f);
        DisplayUtils.drawRectVerticalW(posX + 18.0f, posY + 3.0f, 1, 14.0f, 3, ColorUtils.rgba(255, 255, 255, (int) (255 * 0.75f)));

        posY += fontSize + padding * 2;

        float maxWidth = Fonts.sfMedium.getWidth(name, fontSize) + padding * 2;
        float localHeight = fontSize + padding * 2;


        //DisplayUtils.drawRectHorizontalW(posX + 0.5f, posY, width - 1, 2.5f, 3, ColorUtils.rgba(0, 0, 0, (int) (255 * 0.25f)));
        Scissor.unset();
        Scissor.pop();
        width = Math.max(maxWidth, 185);
        height = localHeight + 65.0f;
        dragging.setWidth(width);
        dragging.setHeight(height);
    }


    private void drawStyledRect(float x,
                                float y,
                                float width,
                                float height,
                                float radius) {

        //DisplayUtils.drawRoundedRect(x - 0.5f, y - 0.5f, width + 1, height + 1, radius + 0.5f, ColorUtils.getColor(0)); // outline
        DisplayUtils.drawRoundedRect(x, y, width, height, radius, ColorUtils.rgba(21, 21, 21, 215));
    }
}