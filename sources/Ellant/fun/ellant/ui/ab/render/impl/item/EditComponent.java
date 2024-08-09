//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package fun.ellant.ui.ab.render.impl.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import fun.ellant.Ellant;
import fun.ellant.ui.ab.donate.DonatItems;
import fun.ellant.ui.ab.render.impl.AddedItemComponent;
import fun.ellant.ui.ab.render.impl.AllItemComponent;
import fun.ellant.ui.ab.render.impl.Component;
import fun.ellant.utils.client.IMinecraft;
import fun.ellant.utils.components.ButtonComponent;
import fun.ellant.utils.components.FieldComponent;
import fun.ellant.utils.components.SliderComponent;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.Scissor;
import fun.ellant.utils.render.font.Fonts;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public class EditComponent extends Component implements IMinecraft {
    float x;
    float y;
    private ItemStack stack;
    private DonatItems.CustomItem customItem;
    private EnchantmentWidget enchantmentWidget;
    private AddedItemComponent addedItemComponents;
    private AllItemComponent allItemComponent;
    private AllItemComponent parent;
    private final FieldComponent price = new FieldComponent(0.0F, 0.0F, 0.0F, 0.0F, "Цена");
    private final SliderComponent damage = new SliderComponent(0.0F, 0.0F, 0.0F, 0.0F, 1, 100, "Прочность");
    private final SliderComponent count = new SliderComponent(0.0F, 0.0F, 0.0F, 0.0F, 2, 64, "Количество");
    private final ButtonComponent add = new ButtonComponent(0.0F, 0.0F, 0.0F, 0.0F, "Добавить", () -> {
        if (!this.price.get().isEmpty()) {
            Ellant.getInstance().getItemStorage().addItem(this.stack.getItem(), Integer.parseInt(this.price.get()), Integer.parseInt(this.count.fieldComponent.get()), Integer.parseInt(this.damage.fieldComponent.get()), this.enchantmentWidget.get());
            this.allItemComponent.component = null;
        }

    });

    public EditComponent(ItemStack stack, AddedItemComponent addedItemComponents, AllItemComponent allItemComponent) {
        this.stack = stack;
        this.allItemComponent = allItemComponent;
        this.addedItemComponents = addedItemComponents;
        this.enchantmentWidget = new EnchantmentWidget(stack);
        this.customItem = this.customItem;
        this.addedItemComponents = addedItemComponents;
        this.parent = this.parent;
    }

    public void render(MatrixStack stack, int mouseX, int mouseY) {
        Scissor.push();
        Scissor.setFromComponentCoordinates((double)this.x, (double)this.y, 100.0, 100.0);
        DisplayUtils.drawRoundedRect(this.x, this.y, 100.0F, 100.0F, 4.0F, ColorUtils.rgba(17, 17, 17, 255));
        float width = 100.0F;
        Fonts.montserrat.drawText(stack, TextFormatting.getTextWithoutFormattingCodes(this.stack.getDisplayName().getString()), this.x + 24.0F, this.y + 9.0F, -1, 6.0F, 0.05F);
        if (Fonts.montserrat.getWidth(TextFormatting.getTextWithoutFormattingCodes(this.stack.getDisplayName().getString()), 6.0F, 0.05F) + 24.0F > width) {
            DisplayUtils.drawRectVerticalW((double)(this.x + width - 10.0F), (double)(this.y + 9.0F), 10.0, 6.0, ColorUtils.rgba(17, 17, 17, 255), ColorUtils.rgba(17, 17, 17, 0));
        }

        mc.getItemRenderer().renderItemAndEffectIntoGUI(this.stack, (int)this.x + 5, (int)this.y + 5);
        this.price.setX(this.x + 5.0F);
        this.price.setY(this.y + 25.0F);
        this.price.setWidth(50.0F);
        this.price.setHeight(17.0F);
        this.price.draw(stack, (float)mouseX, (float)mouseY);
        if (this.stack.getItem().isDamageable()) {
            this.damage.setX(this.x + 5.0F);
            this.damage.setY(this.y + 25.0F + 20.0F);
            this.damage.setWidth(50.0F);
            this.damage.setHeight(10.0F);
            this.damage.max = 100;
            this.damage.draw(stack, (float)mouseX, (float)mouseY);
        }

        if (this.stack.getItem().getMaxStackSize() > 1) {
            this.count.setX(this.x + 5.0F);
            this.count.setY(this.y + 25.0F + (float)(this.stack.getItem().isDamageable() ? 40 : 20));
            this.count.setWidth(50.0F);
            this.count.setHeight(10.0F);
            this.count.max = this.stack.getMaxStackSize();
            this.count.draw(stack, (float)mouseX, (float)mouseY);
        }

        this.add.setX(this.x + 5.0F);
        this.add.setY(this.y + 100.0F - 22.0F);
        this.add.setWidth(width - 10.0F);
        this.add.setHeight(17.0F);
        this.add.draw(stack, (float)mouseX, (float)mouseY);
        Scissor.unset();
        Scissor.pop();
        if (this.stack.isEnchantable()) {
            this.enchantmentWidget.setX(this.x);
            this.enchantmentWidget.setY(this.y + 125.0F);
            this.enchantmentWidget.render(stack, mouseX, mouseY);
        }

    }

    public void mouseScrolled(double mouseX, double mouseY, double delta) {
        this.enchantmentWidget.mouseScrolled(mouseX, mouseY, delta);
    }

    public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
        this.enchantmentWidget.mouseClicked(mouseX, mouseY, mouseButton);
        this.price.click((int)mouseX, (int)mouseY);
        this.add.click((int)mouseX, (int)mouseY);
        this.damage.click((int)mouseX, (int)mouseY);
        this.count.click((int)mouseX, (int)mouseY);
    }

    public void mouseReleased(double mouseX, double mouseY, int mouseButton) {
        this.enchantmentWidget.mouseReleased(mouseX, mouseY, mouseButton);
        this.damage.unpress();
        this.count.unpress();
    }

    public void keyTyped(int keyCode, int scanCode, int modifiers) {
        this.price.key(keyCode);
        this.damage.key(keyCode);
        this.count.key(keyCode);
        this.enchantmentWidget.keyTyped(keyCode, scanCode, modifiers);
    }

    public void charTyped(char codePoint, int modifiers) {
        if (Character.isDigit(codePoint)) {
            this.price.charTyped(codePoint);
            this.damage.charTyped(codePoint);
            this.count.charTyped(codePoint);
        }

        this.enchantmentWidget.charTyped(codePoint, modifiers);
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }
}
