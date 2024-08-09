package xyz.mathax.mathaxclient.gui.screens.settings;

import xyz.mathax.mathaxclient.systems.themes.Theme;
import xyz.mathax.mathaxclient.gui.WindowScreen;
import xyz.mathax.mathaxclient.gui.widgets.WItemWithLabel;
import xyz.mathax.mathaxclient.gui.widgets.containers.WTable;
import xyz.mathax.mathaxclient.gui.widgets.input.WTextBox;
import xyz.mathax.mathaxclient.gui.widgets.pressable.WButton;
import xyz.mathax.mathaxclient.settings.ItemSetting;
import xyz.mathax.mathaxclient.utils.misc.Names;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import org.apache.commons.lang3.StringUtils;

public class ItemSettingScreen extends WindowScreen {
    private final ItemSetting setting;

    private WTable table;

    private WTextBox filter;
    private String filterText = "";

    public ItemSettingScreen(Theme theme, ItemSetting setting) {
        super(theme, "Select item");

        this.setting = setting;
    }

    @Override
    public void initWidgets() {
        filter = add(theme.textBox("")).minWidth(400).expandX().widget();
        filter.setFocused(true);
        filter.action = () -> {
            filterText = filter.get().trim();

            table.clear();
            initTable();
        };

        table = add(theme.table()).expandX().widget();
        initTable();
    }

    public void initTable() {
        for (Item item : Registries.ITEM) {
            if (setting.filter != null && !setting.filter.test(item)) {
                continue;
            }

            if (item == Items.AIR) {
                continue;
            }

            WItemWithLabel itemLabel = theme.itemWithLabel(item.getDefaultStack(), Names.get(item));
            if (!filterText.isEmpty() && !StringUtils.containsIgnoreCase(itemLabel.getLabelText(), filterText)) {
                continue;
            }

            table.add(itemLabel);

            WButton select = table.add(theme.button("Select")).expandCellX().right().widget();
            select.action = () -> {
                setting.set(item);
                close();
            };

            table.row();
        }
    }
}
