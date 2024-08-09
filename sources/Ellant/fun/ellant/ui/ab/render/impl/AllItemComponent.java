/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package fun.ellant.ui.ab.render.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import fun.ellant.ui.ab.donate.DonateItems;
import fun.ellant.ui.ab.render.Window;
import fun.ellant.ui.ab.render.impl.AddedItemComponent;
import fun.ellant.ui.ab.render.impl.Component;
import fun.ellant.ui.ab.render.impl.item.EditComponent;
import fun.ellant.utils.math.MathUtil;
import fun.ellant.utils.render.Scissor;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

public class AllItemComponent
extends Component {
    float x;
    float y;
    float width;
    float height;
    float scroll;
    public EditComponent component;
    AddedItemComponent addedItemComponents;

    public AllItemComponent(AddedItemComponent addedItemComponents) {
        this.addedItemComponents = addedItemComponents;
    }

    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY) {
        this.scroll = MathHelper.clamp(this.scroll, (float)(-(Registry.ITEM.stream().count() + (long)DonateItems.donitem.size())) + this.height - 75.0f, 0.0f);
        float currentX = 0.0f;
        float currentY = this.scroll;
        Scissor.push();
        Scissor.setFromComponentCoordinates(this.x, this.y, this.width - 10.0f, this.height - 75.0f, (float)Window.openAnimation.getOutput());
        for (ItemStack item : DonateItems.donitem) {
            if (MathUtil.isHovered(this.x + 2.0f + currentX, this.y + currentY, this.x, this.y - 20.0f, this.width, this.height + 20.0f)) {
                Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(item, (int)(this.x + 2.0f + currentX), (int)(this.y + currentY));
            }
            if (!(this.x + (currentX += 18.0f) >= this.x + this.width - 25.0f)) continue;
            currentX = 0.0f;
            currentY += 20.0f;
        }
        Scissor.unset();
        Scissor.pop();
    }

    @Override
    public void mouseScrolled(double mouseX, double mouseY, double delta) {
        if (MathUtil.isHovered((float)mouseX, (float)mouseY, this.x, this.y - 20.0f, this.width, this.height + 20.0f)) {
            this.scroll = (float)((double)this.scroll + delta * 10.0);
        }
        if (this.component != null) {
            this.component.mouseScrolled(mouseX, mouseY, delta);
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
        float currentX = 0.0f;
        float currentY = this.scroll;
        if (this.component != null) {
            this.component.mouseClicked(mouseX, mouseY, mouseButton);
            currentX += 18.0f;
            if (this.x + currentX >= this.x + this.width - 25.0f) {
                currentX = 0.0f;
                currentY += 20.0f;
            }
        }
        for (ItemStack item : DonateItems.donitem) {
            if (MathUtil.isHovered(this.x + 2.0f + currentX, this.y + currentY, this.x, this.y - 20.0f, this.width, this.height + 20.0f) && MathUtil.isHovered((float)mouseX, (float)mouseY, (int)(this.x + 2.0f + currentX), (int)(this.y + currentY), 16.0f, 16.0f)) {
                this.component = new EditComponent(item, this.addedItemComponents, this);
            }
            if (!(this.x + (currentX += 18.0f) >= this.x + this.width - 25.0f)) continue;
            currentX = 0.0f;
            currentY += 20.0f;
        }
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int mouseButton) {
        if (this.component != null) {
            this.component.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void keyTyped(int keyCode, int scanCode, int modifiers) {
        if (this.component != null) {
            this.component.keyTyped(keyCode, scanCode, modifiers);
        }
    }

    @Override
    public void charTyped(char codePoint, int modifiers) {
        if (this.component != null) {
            this.component.charTyped(codePoint, modifiers);
        }
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setScroll(float scroll) {
        this.scroll = scroll;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public float getScroll() {
        return this.scroll;
    }
}

