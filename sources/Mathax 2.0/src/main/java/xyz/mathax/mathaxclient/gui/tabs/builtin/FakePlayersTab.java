package xyz.mathax.mathaxclient.gui.tabs.builtin;

    /*    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    public final Setting<String> nameSetting = generalSettings.add(new StringSetting.Builder()
            .name("Name")
            .description("The name of the fake player.")
            .defaultValue("Matejko06")
            .build()
    );

    public final Setting<Boolean> copyInventorySetting = generalSettings.add(new BoolSetting.Builder()
            .name("Copy inventory")
            .description("Copy your inventory to the fake player.")
            .defaultValue(true)
            .build()
    );

    public final Setting<Integer> healthSetting = generalSettings.add(new IntSetting.Builder()
            .name("Health")
            .description("The fake player's default health.")
            .defaultValue(20)
            .min(1)
            .sliderRange(1, 36)
            .build()
    );*/


    /*    @Override
    public WWidget getWidget(Theme theme) {
        WHorizontalList horizontalList = theme.horizontalList();

        WButton spawn = horizontalList.add(theme.button("Spawn")).widget();
        spawn.action = () -> FakePlayerManager.add(nameSetting.get(), healthSetting.get(), copyInventorySetting.get());

        WButton clear = horizontalList.add(theme.button("Clear")).widget();
        clear.action = FakePlayerManager::clear;

        return horizontalList;
    }*/


import net.minecraft.client.gui.screen.Screen;
import xyz.mathax.mathaxclient.gui.tabs.Tab;
import xyz.mathax.mathaxclient.gui.tabs.TabScreen;
import xyz.mathax.mathaxclient.gui.tabs.WindowTabScreen;
import xyz.mathax.mathaxclient.gui.widgets.containers.WHorizontalList;
import xyz.mathax.mathaxclient.gui.widgets.containers.WTable;
import xyz.mathax.mathaxclient.gui.widgets.pressable.WButton;
import xyz.mathax.mathaxclient.gui.widgets.pressable.WMinus;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.themes.Theme;
import xyz.mathax.mathaxclient.utils.entity.fakeplayer.FakePlayerManager;
import xyz.mathax.mathaxclient.utils.entity.fakeplayer.FakePlayerSettings;

public class FakePlayersTab extends Tab {
    public FakePlayersTab() {
        super("Fake Players");
    }

    @Override
    public TabScreen createScreen(Theme theme) {
        return new FakePlayersScreen(theme, this);
    }

    @Override
    public boolean isScreen(Screen screen) {
        return screen instanceof FakePlayersScreen;
    }

    private static class FakePlayersScreen extends WindowTabScreen {
        private final Settings settings;

        public FakePlayersScreen(Theme theme, Tab tab) {
            super(theme, tab);

            settings = FakePlayerSettings.settings;
            settings.onEnabled();
        }

        @Override
        public void initWidgets() {
            add(theme.settings(settings)).expandX();

            WHorizontalList horizontalList = theme.horizontalList();

            WButton spawn = horizontalList.add(theme.button("Spawn")).widget();
            spawn.action = () -> {
                FakePlayerManager.add(FakePlayerSettings.nameSetting.get(), FakePlayerSettings.healthSetting.get(), FakePlayerSettings.copyInventorySetting.get());
                reload();
            };

            WButton clear = horizontalList.add(theme.button("Clear")).widget();
            clear.action = () -> {
                FakePlayerManager.clear();
                reload();
            };

            add(horizontalList);

            WTable table = add(theme.table()).expandX().minWidth(400).widget();
            initTable(table);
        }

        private void initTable(WTable table) {
            table.clear();

            if (FakePlayerManager.empty()) {
                return;
            }

            FakePlayerManager.forEach(fakePlayer -> {
                table.add(theme.texture(32, 32, fakePlayer.getHead().needsRotate() ? 90 : 0, fakePlayer.getHead()));
                table.add(theme.label(fakePlayer.getEntityName()));

                WMinus remove = table.add(theme.minus()).expandCellX().right().widget();
                remove.action = () -> {
                    FakePlayerManager.remove(fakePlayer);
                    reload();
                };

                table.row();
            });
        }

        @Override
        public void tick() {
            super.tick();

            settings.tick(window, theme);
        }
    }
}
