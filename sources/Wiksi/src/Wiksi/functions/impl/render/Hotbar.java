package src.Wiksi.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventDisplay;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.impl.ModeSetting;
import src.Wiksi.utils.math.Vector4i;
import src.Wiksi.utils.render.ColorUtils;
import src.Wiksi.utils.render.DisplayUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.vector.Vector4f;

@FunctionRegister(name = "Hotbar", type = Category.Render)
public class Hotbar extends Function {
    public static Hotbar hotbar;
    public boolean state;
    static final ModeSetting a = new ModeSetting("Мод", "Мод1", "Мод1", "Мод2");
    private final FontRenderer fontRenderer = mc.fontRenderer;
    public Hotbar() {
        addSettings(a);
    }

    @Subscribe
    public void onRender(EventDisplay var1) {
        if (a.is("Мод1")) {
        }
    }

    @Subscribe
    public void onDisplay(EventDisplay e) {
        if (a.is("Мод2")) {
            if (e.getType() != EventDisplay.Type.POST) return;

            int hotbarX = (mc.getMainWindow().getScaledWidth() - 182) / 2;
            int hotbarY = mc.getMainWindow().getScaledHeight() - 22;

            drawStyledRect(hotbarX - 1, hotbarY - 1, 182, 22, 3.5f, 1, 0x7F000000); // Прозрачность установлена на 50% (0x7F)

            DisplayUtils.drawShadow(hotbarX - 1, hotbarY - 1, 182, 22, 25, ColorUtils.rgba(0, 0, 0, 255), ColorUtils.rgba(0, 0, 0, 0));

            NonNullList<ItemStack> hotbarItems = mc.player.inventory.mainInventory;
            for (int i = 0; i < 9; i++) {
                int slotX = hotbarX + i * 20;
                int slotY = hotbarY;

                int slotColor = 0x55000000; // Прозрачность установлена на 33% (0x55)
                if (i == mc.player.inventory.currentItem) {
                    slotColor = 0x55CCCCCC;
                }

                DisplayUtils.drawRoundedRect(slotX, slotY, 20, 20, 3, slotColor);

                ItemStack itemStack = hotbarItems.get(i);
                if (!itemStack.isEmpty()) {
                    mc.getItemRenderer().renderItemAndEffectIntoGUI(itemStack, slotX + 2, slotY + 2);
                    mc.getItemRenderer().renderItemOverlayIntoGUI(fontRenderer, itemStack, slotX + 2, slotY + 2, null);
                }
            }

            // Отрисовка хотбара для левой руки
            NonNullList<ItemStack> offhandItems = mc.player.inventory.offHandInventory;
            ItemStack offhandItem = offhandItems.get(0); // Получаем предмет в левом слоте
            if (!offhandItem.isEmpty()) { // Проверяем, не пустой ли слот
                for (int i = 0; i < 1; i++) { // Отрисовываем только если слот не пустой
                    int slotX = hotbarX - 28 - i * 20; // Смещаем слоты влево от основного хотбара
                    int slotY = hotbarY;

                    int slotColor = 0x55000000; // Прозрачность установлена на 33% (0x55)

                    DisplayUtils.drawRoundedRect(slotX, slotY, 20, 20, 3, slotColor);
                    drawStyledRect(slotX, slotY, 20, 20, 3, 1, 0x7F000000); // Прозрачность установлена на 50% (0x7F)

                    ItemStack itemStack = offhandItems.get(i);
                    if (!itemStack.isEmpty()) {
                        mc.getItemRenderer().renderItemAndEffectIntoGUI(itemStack, slotX + 2, slotY + 2);
                        mc.getItemRenderer().renderItemOverlayIntoGUI(fontRenderer, itemStack, slotX + 2, slotY + 2, null);
                    }
                }
            }
        }
    }

    private void drawStyledRect(float x, float y, float width, float height, float radius, float borderWidth, int color) {
        Vector4i colors = new Vector4i(HUD.getColor(0, 1.0F), HUD.getColor(90, 1.0F), HUD.getColor(180, 1.0F), HUD.getColor(270, 1.0F));
        DisplayUtils.drawRoundedRect(x - borderWidth, y - borderWidth, width + borderWidth * 2, height + borderWidth * 2, new Vector4f(7, 7, 7, 7), colors);
        DisplayUtils.drawRoundedRect(x, y, width, height, radius, color); // Установите цвет прямоугольника, используя параметр color
    }
}