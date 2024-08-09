package im.expensive.ui.ab.render.impl.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import im.expensive.ui.ab.render.impl.Component;
import im.expensive.utils.components.SliderComponent;
import im.expensive.utils.math.MathUtil;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.DisplayUtils;
import im.expensive.utils.render.Scissor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.optifine.util.EnchantmentUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnchantmentWidget extends Component {

    @Setter
    @Getter
    float x, y;

    private final ItemStack stack;

    private final List<SliderComponent> sliderComponents = new ArrayList<>();

    public EnchantmentWidget(ItemStack stack) {
        this.stack = stack;
        List<Enchantment> enchantmentsMap = Registry.ENCHANTMENT.stream().filter(m -> m.canApply(this.stack)).toList();
        for (Enchantment enchantment : enchantmentsMap) {
            sliderComponents.add(new SliderComponent(0,0,0,0, enchantment.getMinLevel(), enchantment.getMaxLevel(), enchantment, I18n.format(enchantment.getName())));
        }
    }


    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY) {
        Scissor.push();
        Scissor.setFromComponentCoordinates(x, y, 100, 100);
        DisplayUtils.drawRoundedRect(x, y, 100, 100, 4, ColorUtils.rgba(17, 17, 17, 255));
        if (sliderComponents.size() * 13 > 100) {
            scroll = MathHelper.clamp(scroll, -(sliderComponents.size() * 13) + 90, 0);
        } else {
            scroll = 0;
        }
        int i = 0;
        for (SliderComponent s : sliderComponents) {
            s.setX(x + 5);
            s.setY(y + 5 + (i * (s.getHeight() + 3)) + scroll);
            s.setWidth(100 - 10);
            s.setHeight(10);

            s.draw(stack,mouseX,mouseY);
            i++;
        }
        Scissor.unset();
        Scissor.pop();

    }

    public Map<Enchantment, Integer> get() {
        Map<Enchantment, Integer> enchantments = new HashMap<>();
        for (SliderComponent s : sliderComponents) {
            if (Integer.valueOf(s.fieldComponent.get()) > 0) {
                enchantments.put(s.enchantment, Integer.valueOf(s.fieldComponent.get()));
            }
        }
        return enchantments;
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
        for (SliderComponent s : sliderComponents) {
            s.click((int) mouseX, (int) mouseY);
        }
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int mouseButton) {
        for (SliderComponent s : sliderComponents) {
            s.unpress();
        }
    }

    int scroll = 0;

    @Override
    public void mouseScrolled(double mouseX, double mouseY, double delta) {
        if (MathUtil.isHovered((float) mouseX, (float) mouseY,x,y,100,100)) {
            scroll += delta * 10;
        }
    }

    @Override
    public void keyTyped(int keyCode, int scanCode, int modifiers) {
        for (SliderComponent s : sliderComponents) {
            s.key(keyCode);
        }
    }

    @Override
    public void charTyped(char codePoint, int modifiers) {
        for (SliderComponent s : sliderComponents) {
            s.charTyped(codePoint);
        }
    }
}
