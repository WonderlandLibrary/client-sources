package io.github.nevalackin.radium.module.impl.render;

import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import io.github.nevalackin.radium.module.ModuleManager;
import io.github.nevalackin.radium.property.Property;
import io.github.nevalackin.radium.utils.render.Colors;

@ModuleInfo(label = "Enchant Glint", category = ModuleCategory.RENDER)
public final class EnchantGlint extends Module {

    private final Property<Integer> itemColorProperty = new Property<>("Item Glint Color", Colors.RED);
    private final Property<Integer> armorModelColorProperty = new Property<>("Armor Glint Color", Colors.RED);

    private static EnchantGlint getInstance() {
        return ModuleManager.getInstance(EnchantGlint.class);
    }

    public static boolean isGlintEnabled() {
        return getInstance().isEnabled();
    }

    public static int getItemColor() {
        return getInstance().itemColorProperty.getValue();
    }

    public static int getArmorModelColor() {
        return getInstance().armorModelColorProperty.getValue();
    }
}
