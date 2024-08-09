package xyz.mathax.mathaxclient.gui.widgets;

import xyz.mathax.mathaxclient.gui.widgets.containers.WHorizontalList;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;

import java.util.List;

public class WItemWithLabel extends WHorizontalList {
    private ItemStack itemStack;
    private String name;

    private WItem item;
    private WLabel label;

    public WItemWithLabel(ItemStack itemStack, String name) {
        this.itemStack = itemStack;
        this.name = name;
    }

    @Override
    public void init() {
        item = add(theme.item(itemStack)).widget();
        label = add(theme.label(name + getStringToAppend())).widget();
    }

    private String getStringToAppend() {
        String string = "";
        if (itemStack.getItem() == Items.POTION) {
            List<StatusEffectInstance> effects = PotionUtil.getPotion(itemStack).getEffects();
            if (effects.size() > 0) {
                string += " ";
                StatusEffectInstance effect = effects.get(0);
                if (effect.getAmplifier() > 0) {
                    string += effect.getAmplifier() + 1 + " ";
                }

                string += "(" + StatusEffectUtil.durationToString(effect, 1) + ")";
            }
        }

        return string;
    }

    public void set(ItemStack itemStack) {
        this.itemStack = itemStack;
        item.itemStack = itemStack;

        name = itemStack.getName().getString();
        label.set(name + getStringToAppend());
    }

    public String getLabelText() {
        return label == null ? name : label.get();
    }
}
