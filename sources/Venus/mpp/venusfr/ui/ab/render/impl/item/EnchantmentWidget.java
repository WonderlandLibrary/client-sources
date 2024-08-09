/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.ab.render.impl.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mpp.venusfr.ui.ab.render.impl.Component;
import mpp.venusfr.utils.components.SliderComponent;
import mpp.venusfr.utils.math.MathUtil;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.utils.render.Scissor;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

public class EnchantmentWidget
extends Component {
    float x;
    float y;
    private final ItemStack stack;
    private final List<SliderComponent> sliderComponents = new ArrayList<SliderComponent>();
    int scroll = 0;

    public EnchantmentWidget(ItemStack itemStack) {
        this.stack = itemStack;
        List list = Registry.ENCHANTMENT.stream().filter(this::lambda$new$0).toList();
        for (Enchantment enchantment : list) {
            this.sliderComponents.add(new SliderComponent(0.0f, 0.0f, 0.0f, 0.0f, enchantment.getMinLevel(), enchantment.getMaxLevel(), enchantment, I18n.format(enchantment.getName(), new Object[0])));
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2) {
        Scissor.push();
        Scissor.setFromComponentCoordinates(this.x, this.y, 100.0, 100.0);
        DisplayUtils.drawRoundedRect(this.x, this.y, 100.0f, 100.0f, 4.0f, ColorUtils.rgba(17, 17, 17, 255));
        this.scroll = this.sliderComponents.size() * 13 > 100 ? MathHelper.clamp(this.scroll, -(this.sliderComponents.size() * 13) + 90, 0) : 0;
        int n3 = 0;
        for (SliderComponent sliderComponent : this.sliderComponents) {
            sliderComponent.setX(this.x + 5.0f);
            sliderComponent.setY(this.y + 5.0f + (float)n3 * (sliderComponent.getHeight() + 3.0f) + (float)this.scroll);
            sliderComponent.setWidth(90.0f);
            sliderComponent.setHeight(10.0f);
            sliderComponent.draw(matrixStack, n, n2);
            ++n3;
        }
        Scissor.unset();
        Scissor.pop();
    }

    public Map<Enchantment, Integer> get() {
        HashMap<Enchantment, Integer> hashMap = new HashMap<Enchantment, Integer>();
        for (SliderComponent sliderComponent : this.sliderComponents) {
            if (Integer.valueOf(sliderComponent.fieldComponent.get()) <= 0) continue;
            hashMap.put(sliderComponent.enchantment, Integer.valueOf(sliderComponent.fieldComponent.get()));
        }
        return hashMap;
    }

    @Override
    public void mouseClicked(double d, double d2, int n) {
        for (SliderComponent sliderComponent : this.sliderComponents) {
            sliderComponent.click((int)d, (int)d2);
        }
    }

    @Override
    public void mouseReleased(double d, double d2, int n) {
        for (SliderComponent sliderComponent : this.sliderComponents) {
            sliderComponent.unpress();
        }
    }

    @Override
    public void mouseScrolled(double d, double d2, double d3) {
        if (MathUtil.isHovered((float)d, (float)d2, this.x, this.y, 100.0f, 100.0f)) {
            this.scroll = (int)((double)this.scroll + d3 * 10.0);
        }
    }

    @Override
    public void keyTyped(int n, int n2, int n3) {
        for (SliderComponent sliderComponent : this.sliderComponents) {
            sliderComponent.key(n);
        }
    }

    @Override
    public void charTyped(char c, int n) {
        for (SliderComponent sliderComponent : this.sliderComponents) {
            sliderComponent.charTyped(c);
        }
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

    private boolean lambda$new$0(Enchantment enchantment) {
        return enchantment.canApply(this.stack);
    }
}

