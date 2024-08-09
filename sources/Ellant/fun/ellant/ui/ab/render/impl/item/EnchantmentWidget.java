//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package fun.ellant.ui.ab.render.impl.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import fun.ellant.ui.ab.render.impl.Component;
import fun.ellant.utils.components.SliderComponent;
import fun.ellant.utils.math.MathUtil;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.Scissor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

public class EnchantmentWidget extends Component {
    float x;
    float y;
    private final ItemStack stack;
    private final List<SliderComponent> sliderComponents = new ArrayList();
    int scroll = 0;

    public EnchantmentWidget(ItemStack stack) {
        this.stack = stack;
        List<Enchantment> enchantmentsMap = Registry.ENCHANTMENT.stream().filter((m) -> {
            return m.canApply(this.stack);
        }).toList();
        Iterator var3 = enchantmentsMap.iterator();

        while(var3.hasNext()) {
            Enchantment enchantment = (Enchantment)var3.next();
            this.sliderComponents.add(new SliderComponent(0.0F, 0.0F, 0.0F, 0.0F, enchantment.getMinLevel(), enchantment.getMaxLevel(), enchantment, I18n.format(enchantment.getName(), new Object[0])));
        }

    }

    public void render(MatrixStack stack, int mouseX, int mouseY) {
        Scissor.push();
        Scissor.setFromComponentCoordinates((double)this.x, (double)this.y, 100.0, 100.0);
        DisplayUtils.drawRoundedRect(this.x, this.y, 100.0F, 100.0F, 4.0F, ColorUtils.rgba(17, 17, 17, 255));
        if (this.sliderComponents.size() * 13 > 100) {
            this.scroll = MathHelper.clamp(this.scroll, -(this.sliderComponents.size() * 13) + 90, 0);
        } else {
            this.scroll = 0;
        }

        int i = 0;

        for(Iterator var5 = this.sliderComponents.iterator(); var5.hasNext(); ++i) {
            SliderComponent s = (SliderComponent)var5.next();
            s.setX(this.x + 5.0F);
            s.setY(this.y + 5.0F + (float)i * (s.getHeight() + 3.0F) + (float)this.scroll);
            s.setWidth(90.0F);
            s.setHeight(10.0F);
            s.draw(stack, (float)mouseX, (float)mouseY);
        }

        Scissor.unset();
        Scissor.pop();
    }

    public Map<Enchantment, Integer> get() {
        Map<Enchantment, Integer> enchantments = new HashMap();
        Iterator var2 = this.sliderComponents.iterator();

        while(var2.hasNext()) {
            SliderComponent s = (SliderComponent)var2.next();
            if (Integer.valueOf(s.fieldComponent.get()) > 0) {
                enchantments.put(s.enchantment, Integer.valueOf(s.fieldComponent.get()));
            }
        }

        return enchantments;
    }

    public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
        Iterator var6 = this.sliderComponents.iterator();

        while(var6.hasNext()) {
            SliderComponent s = (SliderComponent)var6.next();
            s.click((int)mouseX, (int)mouseY);
        }

    }

    public void mouseReleased(double mouseX, double mouseY, int mouseButton) {
        Iterator var6 = this.sliderComponents.iterator();

        while(var6.hasNext()) {
            SliderComponent s = (SliderComponent)var6.next();
            s.unpress();
        }

    }

    public void mouseScrolled(double mouseX, double mouseY, double delta) {
        if (MathUtil.isHovered((float)mouseX, (float)mouseY, this.x, this.y, 100.0F, 100.0F)) {
            this.scroll = (int)((double)this.scroll + delta * 10.0);
        }

    }

    public void keyTyped(int keyCode, int scanCode, int modifiers) {
        Iterator var4 = this.sliderComponents.iterator();

        while(var4.hasNext()) {
            SliderComponent s = (SliderComponent)var4.next();
            s.key(keyCode);
        }

    }

    public void charTyped(char codePoint, int modifiers) {
        Iterator var3 = this.sliderComponents.iterator();

        while(var3.hasNext()) {
            SliderComponent s = (SliderComponent)var3.next();
            s.charTyped(codePoint);
        }

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
