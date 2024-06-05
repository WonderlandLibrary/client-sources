package io.github.nevalackin.radium.module.impl.render;

import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import io.github.nevalackin.radium.module.ModuleManager;
import io.github.nevalackin.radium.property.Property;
import io.github.nevalackin.radium.utils.render.Colors;

@ModuleInfo(label = "Block Outline", category = ModuleCategory.RENDER)
public final class BlockOutline extends Module {

    private final Property<Integer> blockOutlineColorProperty = new Property<>("Outline Color", Colors.PURPLE);

    // simpa.svensson@hotmail.com:simsve03

    public static float getOutlineAlpha() {
        return (ModuleManager.getInstance(BlockOutline.class).blockOutlineColorProperty.getValue() >> 25 & 0xFF) / 255.0F;
    }

    public static int getOutlineColor() {
        return ModuleManager.getInstance(BlockOutline.class).blockOutlineColorProperty.getValue();
    }

    public static boolean isOutlineActive() {
        return ModuleManager.getInstance(BlockOutline.class).isEnabled();
    }

}
