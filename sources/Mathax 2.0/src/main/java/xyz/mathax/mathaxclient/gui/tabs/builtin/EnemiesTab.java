package xyz.mathax.mathaxclient.gui.tabs.builtin;

import xyz.mathax.mathaxclient.systems.enemies.Enemies;
import xyz.mathax.mathaxclient.systems.enemies.Enemy;
import xyz.mathax.mathaxclient.systems.themes.Theme;
import xyz.mathax.mathaxclient.gui.tabs.Tab;
import xyz.mathax.mathaxclient.gui.tabs.TabScreen;
import xyz.mathax.mathaxclient.gui.tabs.WindowTabScreen;
import xyz.mathax.mathaxclient.gui.widgets.containers.WHorizontalList;
import xyz.mathax.mathaxclient.gui.widgets.containers.WTable;
import xyz.mathax.mathaxclient.gui.widgets.input.WTextBox;
import xyz.mathax.mathaxclient.gui.widgets.pressable.WMinus;
import xyz.mathax.mathaxclient.gui.widgets.pressable.WPlus;
import xyz.mathax.mathaxclient.utils.network.Executor;
import net.minecraft.client.gui.screen.Screen;

public class EnemiesTab extends Tab {
    public EnemiesTab() {
        super("Enemies");
    }

    @Override
    public TabScreen createScreen(Theme theme) {
        return new EnemiesScreen(theme, this);
    }

    @Override
    public boolean isScreen(Screen screen) {
        return screen instanceof EnemiesScreen;
    }

    private static class EnemiesScreen extends WindowTabScreen {
        public EnemiesScreen(Theme theme, Tab tab) {
            super(theme, tab);
        }

        @Override
        public void initWidgets() {
            WTable table = add(theme.table()).expandX().minWidth(400).widget();
            initTable(table);

            add(theme.horizontalSeparator()).expandX();

            // New
            WHorizontalList list = add(theme.horizontalList()).expandX().widget();

            WTextBox nameW = list.add(theme.textBox("", (text, c) -> c != ' ')).expandX().widget();
            nameW.setFocused(true);

            WPlus add = list.add(theme.plus()).widget();
            add.action = () -> {
                String name = nameW.get().trim();
                Enemy enemy = new Enemy(name);
                if (Enemies.get().add(enemy)) {
                    nameW.set("");
                    reload();

                    Executor.execute(() -> {
                        enemy.updateInfo();
                        reload();
                    });
                }
            };

            enterAction = add.action;
        }

        private void initTable(WTable table) {
            table.clear();

            if (Enemies.get().isEmpty()) {
                return;
            }

            for (Enemy enemy : Enemies.get()) {
                table.add(theme.texture(32, 32, enemy.getHead().needsRotate() ? 90 : 0, enemy.getHead()));
                table.add(theme.label(enemy.getName()));

                WMinus remove = table.add(theme.minus()).expandCellX().right().widget();
                remove.action = () -> {
                    Enemies.get().remove(enemy);
                    reload();
                };

                table.row();
            }
        }
    }
}