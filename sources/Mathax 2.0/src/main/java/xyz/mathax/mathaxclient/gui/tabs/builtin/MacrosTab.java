package xyz.mathax.mathaxclient.gui.tabs.builtin;

import xyz.mathax.mathaxclient.settings.Settings;
import xyz.mathax.mathaxclient.systems.screens.EditSystemScreen;
import xyz.mathax.mathaxclient.systems.themes.Theme;
import xyz.mathax.mathaxclient.gui.renderer.GuiRenderer;
import xyz.mathax.mathaxclient.gui.tabs.Tab;
import xyz.mathax.mathaxclient.gui.tabs.TabScreen;
import xyz.mathax.mathaxclient.gui.tabs.WindowTabScreen;
import xyz.mathax.mathaxclient.gui.widgets.containers.WTable;
import xyz.mathax.mathaxclient.gui.widgets.pressable.WButton;
import xyz.mathax.mathaxclient.gui.widgets.pressable.WMinus;
import xyz.mathax.mathaxclient.systems.macros.Macro;
import xyz.mathax.mathaxclient.systems.macros.Macros;
import net.minecraft.client.gui.screen.Screen;

import static xyz.mathax.mathaxclient.MatHax.mc;

public class MacrosTab extends Tab {
    public MacrosTab() {
        super("Macros");
    }

    @Override
    public TabScreen createScreen(Theme theme) {
        return new MacrosScreen(theme, this);
    }

    @Override
    public boolean isScreen(Screen screen) {
        return screen instanceof MacrosScreen;
    }

    private static class MacrosScreen extends WindowTabScreen {
        public MacrosScreen(Theme theme, Tab tab) {
            super(theme, tab);
        }

        @Override
        public void initWidgets() {
            WTable table = add(theme.table()).expandX().minWidth(400).widget();
            initTable(table);

            add(theme.horizontalSeparator()).expandX();

            WButton create = add(theme.button("Create")).expandX().widget();
            create.action = () -> mc.setScreen(new EditMacroScreen(theme, null, this::reload));
        }

        private void initTable(WTable table) {
            table.clear();

            if (Macros.get().isEmpty()) {
                return;
            }

            for (Macro macro : Macros.get()) {
                table.add(theme.label(macro.nameSetting.get() + " (" + macro.keybindSetting.get() + ")"));

                WButton edit = table.add(theme.button(GuiRenderer.EDIT)).expandCellX().right().widget();
                edit.action = () -> mc.setScreen(new EditMacroScreen(theme, macro, this::reload));

                WMinus remove = table.add(theme.minus()).widget();
                remove.action = () -> {
                    Macros.get().remove(macro);
                    reload();
                };

                table.row();
            }
        }
    }

    private static class EditMacroScreen extends EditSystemScreen<Macro> {
        public EditMacroScreen(Theme theme, Macro value, Runnable reload) {
            super(theme, value, reload);
        }

        @Override
        public Macro create() {
            return new Macro();
        }

        @Override
        public boolean save() {
            if (value.nameSetting.get().isBlank() || value.messagesSetting.get().isEmpty() || !value.keybindSetting.get().isSet()) {
                return false;
            }

            if (isNew) {
                for (Macro macro : Macros.get()) {
                    if (value.equals(macro)) {
                        return false;
                    }
                }
            }

            if (isNew) {
                Macros.get().add(value);
            } else {
                Macros.get().save();
            }

            return true;
        }

        @Override
        public Settings getSettings() {
            return value.settings;
        }
    }
}