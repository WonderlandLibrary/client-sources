/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.ab.render.impl.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import mpp.venusfr.ui.ab.render.impl.AddedItemComponent;
import mpp.venusfr.ui.ab.render.impl.AllItemComponent;
import mpp.venusfr.ui.ab.render.impl.Component;
import mpp.venusfr.ui.ab.render.impl.item.EnchantmentWidget;
import mpp.venusfr.utils.client.IMinecraft;
import mpp.venusfr.utils.components.ButtonComponent;
import mpp.venusfr.utils.components.FieldComponent;
import mpp.venusfr.utils.components.SliderComponent;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.utils.render.Scissor;
import mpp.venusfr.utils.render.font.Fonts;
import mpp.venusfr.venusfr;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public class EditComponent
extends Component
implements IMinecraft {
    float x;
    float y;
    private ItemStack stack;
    private EnchantmentWidget enchantmentWidget;
    private AddedItemComponent addedItemComponents;
    private AllItemComponent allItemComponent;
    private final FieldComponent price = new FieldComponent(0.0f, 0.0f, 0.0f, 0.0f, "\u0426\u0435\u043d\u0430");
    private final SliderComponent damage = new SliderComponent(0.0f, 0.0f, 0.0f, 0.0f, 1, 100, "\u041f\u0440\u043e\u0447\u043d\u043e\u0441\u0442\u044c");
    private final SliderComponent count = new SliderComponent(0.0f, 0.0f, 0.0f, 0.0f, 2, 64, "\u041a\u043e\u043b\u0438\u0447\u0435\u0441\u0442\u0432\u043e");
    private final ButtonComponent add = new ButtonComponent(0.0f, 0.0f, 0.0f, 0.0f, "\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c", this::lambda$new$0);

    public EditComponent(ItemStack itemStack, AddedItemComponent addedItemComponent, AllItemComponent allItemComponent) {
        this.stack = itemStack;
        this.allItemComponent = allItemComponent;
        this.addedItemComponents = addedItemComponent;
        this.enchantmentWidget = new EnchantmentWidget(itemStack);
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2) {
        Scissor.push();
        Scissor.setFromComponentCoordinates(this.x, this.y, 100.0, 100.0);
        DisplayUtils.drawRoundedRect(this.x, this.y, 100.0f, 100.0f, 4.0f, ColorUtils.rgba(17, 17, 17, 255));
        float f = 100.0f;
        Fonts.montserrat.drawText(matrixStack, TextFormatting.getTextWithoutFormattingCodes(this.stack.getDisplayName().getString()), this.x + 24.0f, this.y + 9.0f, -1, 6.0f, 0.05f);
        if (Fonts.montserrat.getWidth(TextFormatting.getTextWithoutFormattingCodes(this.stack.getDisplayName().getString()), 6.0f, 0.05f) + 24.0f > f) {
            DisplayUtils.drawRectVerticalW(this.x + f - 10.0f, this.y + 9.0f, 10.0, 6.0, ColorUtils.rgba(17, 17, 17, 255), ColorUtils.rgba(17, 17, 17, 0));
        }
        mc.getItemRenderer().renderItemAndEffectIntoGUI(this.stack, (int)this.x + 5, (int)this.y + 5);
        this.price.setX(this.x + 5.0f);
        this.price.setY(this.y + 25.0f);
        this.price.setWidth(50.0f);
        this.price.setHeight(17.0f);
        this.price.draw(matrixStack, n, n2);
        if (this.stack.getItem().isDamageable()) {
            this.damage.setX(this.x + 5.0f);
            this.damage.setY(this.y + 25.0f + 20.0f);
            this.damage.setWidth(50.0f);
            this.damage.setHeight(10.0f);
            this.damage.max = 100;
            this.damage.draw(matrixStack, n, n2);
        }
        if (this.stack.getItem().getMaxStackSize() > 1) {
            this.count.setX(this.x + 5.0f);
            this.count.setY(this.y + 25.0f + (float)(this.stack.getItem().isDamageable() ? 40 : 20));
            this.count.setWidth(50.0f);
            this.count.setHeight(10.0f);
            this.count.max = this.stack.getMaxStackSize();
            this.count.draw(matrixStack, n, n2);
        }
        this.add.setX(this.x + 5.0f);
        this.add.setY(this.y + 100.0f - 22.0f);
        this.add.setWidth(f - 10.0f);
        this.add.setHeight(17.0f);
        this.add.draw(matrixStack, n, n2);
        Scissor.unset();
        Scissor.pop();
        if (this.stack.isEnchantable()) {
            this.enchantmentWidget.setX(this.x);
            this.enchantmentWidget.setY(this.y + 125.0f);
            this.enchantmentWidget.render(matrixStack, n, n2);
        }
    }

    @Override
    public void mouseScrolled(double d, double d2, double d3) {
        this.enchantmentWidget.mouseScrolled(d, d2, d3);
    }

    @Override
    public void mouseClicked(double d, double d2, int n) {
        this.enchantmentWidget.mouseClicked(d, d2, n);
        this.price.click((int)d, (int)d2);
        this.add.click((int)d, (int)d2);
        this.damage.click((int)d, (int)d2);
        this.count.click((int)d, (int)d2);
    }

    @Override
    public void mouseReleased(double d, double d2, int n) {
        this.enchantmentWidget.mouseReleased(d, d2, n);
        this.damage.unpress();
        this.count.unpress();
    }

    @Override
    public void keyTyped(int n, int n2, int n3) {
        this.price.key(n);
        this.damage.key(n);
        this.count.key(n);
        this.enchantmentWidget.keyTyped(n, n2, n3);
    }

    @Override
    public void charTyped(char c, int n) {
        if (Character.isDigit(c)) {
            this.price.charTyped(c);
            this.damage.charTyped(c);
            this.count.charTyped(c);
        }
        this.enchantmentWidget.charTyped(c, n);
    }

    public void setX(float f) {
        this.x = f;
    }

    public void setY(float f) {
        this.y = f;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    private void lambda$new$0() {
        if (!this.price.get().isEmpty()) {
            venusfr.getInstance().getItemStorage().addItem(this.stack.getItem(), Integer.parseInt(this.price.get()), Integer.parseInt(this.count.fieldComponent.get()), Integer.parseInt(this.damage.fieldComponent.get()), this.enchantmentWidget.get());
            this.allItemComponent.component = null;
        }
    }
}

