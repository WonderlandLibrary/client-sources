package xyz.mathax.mathaxclient.systems.modules.render;

import xyz.mathax.mathaxclient.settings.ColorSetting;
import xyz.mathax.mathaxclient.settings.ItemListSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemHighlight extends Module {
    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    private final Setting<List<Item>> itemsSetting = generalSettings.add(new ItemListSetting.Builder()
            .name("Items")
            .description("Items to highlight.")
            .build()
    );

    private final Setting<SettingColor> colorSetting = generalSettings.add(new ColorSetting.Builder()
            .name("Color")
            .description("The color to highlight the items with.")
            .defaultValue(new SettingColor(225, 25, 255, 75))
            .build()
    );

    public ItemHighlight(Category category) {
        super(category, "Item Highlight", "Highlights selected items when in guis");
    }

    public int getColor(ItemStack stack) {
        if (isEnabled() && itemsSetting.get().contains(stack.getItem())) {
            return colorSetting.get().getPacked();
        }

        return -1;
    }
}