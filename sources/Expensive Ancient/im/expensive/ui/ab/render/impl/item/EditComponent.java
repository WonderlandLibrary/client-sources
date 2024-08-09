package im.expensive.ui.ab.render.impl.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import im.expensive.Expensive;
import im.expensive.ui.ab.model.IItem;
import im.expensive.ui.ab.model.ItemImpl;
import im.expensive.ui.ab.render.impl.AddedItemComponent;
import im.expensive.ui.ab.render.impl.AllItemComponent;
import im.expensive.ui.ab.render.impl.Component;
import im.expensive.utils.client.IMinecraft;
import im.expensive.utils.components.ButtonComponent;
import im.expensive.utils.components.FieldComponent;
import im.expensive.utils.components.SliderComponent;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.DisplayUtils;
import im.expensive.utils.render.Scissor;
import im.expensive.utils.render.font.Fonts;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class EditComponent extends Component implements IMinecraft {

    @Setter
    @Getter
    float x, y;

    private ItemStack stack;
    private EnchantmentWidget enchantmentWidget;
    private AddedItemComponent addedItemComponents;
    private AllItemComponent allItemComponent;

    public EditComponent(ItemStack stack, AddedItemComponent addedItemComponents, AllItemComponent allItemComponent) {
        this.stack = stack;
        this.allItemComponent = allItemComponent;
        this.addedItemComponents = addedItemComponents;
        enchantmentWidget = new EnchantmentWidget(stack);
    }

    private final FieldComponent price = new FieldComponent(0, 0, 0, 0, "Цена");
    private final SliderComponent damage = new SliderComponent(0, 0, 0, 0, 1, 100, "Прочность");
    private final SliderComponent count = new SliderComponent(0, 0, 0, 0, 2, 64, "Количество");
    private final ButtonComponent add = new ButtonComponent(0, 0, 0, 0, "Добавить", () -> {
        if (!price.get().isEmpty()) {

            Expensive.getInstance().getItemStorage().addItem(stack.getItem(), Integer.parseInt(price.get()), Integer.parseInt(count.fieldComponent.get()), Integer.parseInt(damage.fieldComponent.get()), enchantmentWidget.get());
            allItemComponent.component = null;
        }
    });

    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY) {
        Scissor.push();
        Scissor.setFromComponentCoordinates(x, y, 100, 100);

        DisplayUtils.drawRoundedRect(x, y, 100, 100, 4, ColorUtils.rgba(17, 17, 17, 255));
        float width = 100;
        Fonts.montserrat.drawText(stack, TextFormatting.getTextWithoutFormattingCodes(this.stack.getDisplayName().getString()), x + 24, y + 9f, -1, 6, 0.05f);
        if (Fonts.montserrat.getWidth(TextFormatting.getTextWithoutFormattingCodes(this.stack.getDisplayName().getString()), 6, 0.05f) + 24 > width) {
            DisplayUtils.drawRectVerticalW(x + width - 10f, y + 9f, 10, 6, ColorUtils.rgba(17, 17, 17, 255), ColorUtils.rgba(17, 17, 17, 0));
        }

        mc.getItemRenderer().renderItemAndEffectIntoGUI(this.stack, (int) x + 5, (int) y + 5);

        price.setX(x + 5);
        price.setY(y + 25);
        price.setWidth(50);
        price.setHeight(17);

        price.draw(stack, mouseX, mouseY);
        if (this.stack.getItem().isDamageable()) {
            damage.setX(x + 5);
            damage.setY(y + 25 + 20);
            damage.setWidth(50);
            damage.setHeight(10);
            damage.max = 100;

            // дед проверка на max damage ес чо (max damage) * (damage.current / 100)

            damage.draw(stack, mouseX, mouseY);
        }

        if (this.stack.getItem().getMaxStackSize() > 1) {
            count.setX(x + 5);
            count.setY(y + 25 + (this.stack.getItem().isDamageable() ? 40 : 20));
            count.setWidth(50);
            count.setHeight(10);
            count.max = this.stack.getMaxStackSize();

            count.draw(stack, mouseX, mouseY);
        }

        add.setX(x + 5);
        add.setY(y + 100 - 22);
        add.setWidth(width - 10);
        add.setHeight(17);
        add.draw(stack, mouseX, mouseY);

        Scissor.unset();
        Scissor.pop();

        if (this.stack.isEnchantable()) {
            enchantmentWidget.setX(x);
            enchantmentWidget.setY(y + 125);
            enchantmentWidget.render(stack, mouseX, mouseY);
        }
    }

    @Override
    public void mouseScrolled(double mouseX, double mouseY, double delta) {
        enchantmentWidget.mouseScrolled(mouseX, mouseY, delta);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
        enchantmentWidget.mouseClicked(mouseX, mouseY, mouseButton);
        price.click((int) mouseX, (int) mouseY);
        add.click((int) mouseX, (int) mouseY);
        damage.click((int) mouseX, (int) mouseY);
        count.click((int) mouseX, (int) mouseY);
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int mouseButton) {
        enchantmentWidget.mouseReleased(mouseX, mouseY, mouseButton);
        damage.unpress();
        count.unpress();
    }

    @Override
    public void keyTyped(int keyCode, int scanCode, int modifiers) {
        price.key(keyCode);
        damage.key(keyCode);
        count.key(keyCode);
        enchantmentWidget.keyTyped(keyCode, scanCode, modifiers);
    }

    @Override
    public void charTyped(char codePoint, int modifiers) {
        if (Character.isDigit(codePoint)) {
            price.charTyped(codePoint);
            damage.charTyped(codePoint);
            count.charTyped(codePoint);
        }
        enchantmentWidget.charTyped(codePoint, modifiers);
    }

}
