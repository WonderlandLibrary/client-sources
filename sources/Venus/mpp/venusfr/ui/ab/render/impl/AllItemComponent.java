/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.ab.render.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import mpp.venusfr.ui.ab.donate.DonateItems;
import mpp.venusfr.ui.ab.render.Window;
import mpp.venusfr.ui.ab.render.impl.AddedItemComponent;
import mpp.venusfr.ui.ab.render.impl.Component;
import mpp.venusfr.ui.ab.render.impl.item.EditComponent;
import mpp.venusfr.utils.math.MathUtil;
import mpp.venusfr.utils.render.Scissor;
import net.minecraft.client.Minecraft;
import net.minecraft.item.AirItem;
import net.minecraft.item.Item;
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

    public AllItemComponent(AddedItemComponent addedItemComponent) {
        this.addedItemComponents = addedItemComponent;
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2) {
        this.scroll = MathHelper.clamp(this.scroll, (float)(-(Registry.ITEM.stream().count() + (long)DonateItems.donitem.size())) + this.height - 75.0f, 0.0f);
        float f = 0.0f;
        float f2 = this.scroll;
        Scissor.push();
        Scissor.setFromComponentCoordinates(this.x, this.y, this.width - 10.0f, this.height - 75.0f, (float)Window.openAnimation.getOutput());
        for (Item object : Registry.ITEM) {
            if (object instanceof AirItem) continue;
            if (MathUtil.isHovered(this.x + 2.0f + f, this.y + f2, this.x, this.y - 20.0f, this.width, this.height + 20.0f)) {
                Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(object.getDefaultInstance(), (int)(this.x + 2.0f + f), (int)(this.y + f2));
            }
            if (!(this.x + (f += 18.0f) >= this.x + this.width - 25.0f)) continue;
            f = 0.0f;
            f2 += 20.0f;
        }
        for (ItemStack itemStack : DonateItems.donitem) {
            if (MathUtil.isHovered(this.x + 2.0f + f, this.y + f2, this.x, this.y - 20.0f, this.width, this.height + 20.0f)) {
                Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(itemStack, (int)(this.x + 2.0f + f), (int)(this.y + f2));
            }
            if (!(this.x + (f += 18.0f) >= this.x + this.width - 25.0f)) continue;
            f = 0.0f;
            f2 += 20.0f;
        }
        Scissor.unset();
        Scissor.pop();
    }

    @Override
    public void mouseScrolled(double d, double d2, double d3) {
        if (MathUtil.isHovered((float)d, (float)d2, this.x, this.y - 20.0f, this.width, this.height + 20.0f)) {
            this.scroll = (float)((double)this.scroll + d3 * 10.0);
        }
        if (this.component != null) {
            this.component.mouseScrolled(d, d2, d3);
        }
    }

    @Override
    public void mouseClicked(double d, double d2, int n) {
        float f = 0.0f;
        float f2 = this.scroll;
        if (this.component != null) {
            this.component.mouseClicked(d, d2, n);
        }
        for (Item object : Registry.ITEM) {
            if (object instanceof AirItem) continue;
            if (MathUtil.isHovered(this.x + 2.0f + f, this.y + f2, this.x, this.y - 20.0f, this.width, this.height + 20.0f) && MathUtil.isHovered((float)d, (float)d2, (int)(this.x + 2.0f + f), (int)(this.y + f2), 16.0f, 16.0f)) {
                this.component = new EditComponent(object.getDefaultInstance(), this.addedItemComponents, this);
            }
            if (!(this.x + (f += 18.0f) >= this.x + this.width - 25.0f)) continue;
            f = 0.0f;
            f2 += 20.0f;
        }
        for (ItemStack itemStack : DonateItems.donitem) {
            if (MathUtil.isHovered(this.x + 2.0f + f, this.y + f2, this.x, this.y - 20.0f, this.width, this.height + 20.0f) && MathUtil.isHovered((float)d, (float)d2, (int)(this.x + 2.0f + f), (int)(this.y + f2), 16.0f, 16.0f)) {
                this.component = new EditComponent(itemStack, this.addedItemComponents, this);
            }
            if (!(this.x + (f += 18.0f) >= this.x + this.width - 25.0f)) continue;
            f = 0.0f;
            f2 += 20.0f;
        }
    }

    @Override
    public void mouseReleased(double d, double d2, int n) {
        if (this.component != null) {
            this.component.mouseReleased(d, d2, n);
        }
    }

    @Override
    public void keyTyped(int n, int n2, int n3) {
        if (this.component != null) {
            this.component.keyTyped(n, n2, n3);
        }
    }

    @Override
    public void charTyped(char c, int n) {
        if (this.component != null) {
            this.component.charTyped(c, n);
        }
    }

    public void setX(float f) {
        this.x = f;
    }

    public void setY(float f) {
        this.y = f;
    }

    public void setWidth(float f) {
        this.width = f;
    }

    public void setHeight(float f) {
        this.height = f;
    }

    public void setScroll(float f) {
        this.scroll = f;
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

