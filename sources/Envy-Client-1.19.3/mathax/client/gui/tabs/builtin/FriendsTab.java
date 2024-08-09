package mathax.client.gui.tabs.builtin;

import mathax.client.gui.widgets.containers.WHorizontalList;
import mathax.client.gui.widgets.containers.WSection;
import mathax.client.gui.widgets.containers.WTable;
import mathax.client.gui.widgets.input.WTextBox;
import mathax.client.gui.widgets.pressable.WMinus;
import mathax.client.gui.widgets.pressable.WPlus;
import mathax.client.utils.render.color.SettingColor;
import mathax.client.gui.GuiTheme;
import mathax.client.gui.tabs.Tab;
import mathax.client.gui.tabs.TabScreen;
import mathax.client.gui.tabs.WindowTabScreen;
import mathax.client.settings.BoolSetting;
import mathax.client.settings.ColorSetting;
import mathax.client.settings.SettingGroup;
import mathax.client.settings.Settings;
import mathax.client.systems.friends.Friend;
import mathax.client.systems.friends.Friends;
import mathax.client.utils.misc.NbtUtils;
import net.minecraft.client.gui.screen.Screen;

public class FriendsTab extends Tab {

    public FriendsTab() {
        super("Friends");
    }

    @Override
    public TabScreen createScreen(GuiTheme theme) {
        return new FriendsScreen(theme, this);
    }

    @Override
    public boolean isScreen(Screen screen) {
        return screen instanceof FriendsScreen;
    }

    public static class FriendsScreen extends WindowTabScreen {
        private final Settings settings = new Settings();

        public FriendsScreen(GuiTheme theme, Tab tab) {
            super(theme, tab);

            SettingGroup sgGeneral = settings.getDefaultGroup();

            sgGeneral.add(new ColorSetting.Builder()
                .name("color")
                .description("The color used to show friends.")
                .defaultValue(new SettingColor(0, 255, 180))
                .onChanged(Friends.get().color::set)
                .onModuleActivated(colorSetting -> colorSetting.set(Friends.get().color))
                .build()
            );

            sgGeneral.add(new BoolSetting.Builder()
                .name("attack")
                .description("Whether to attack friends.")
                .defaultValue(false)
                .onChanged(aBoolean -> Friends.get().attack = aBoolean)
                .onModuleActivated(booleanSetting -> booleanSetting.set(Friends.get().attack))
                .build()
            );

            settings.onActivated();
        }

        @Override
        public void initWidgets() {
            // Settings
            add(theme.settings(settings)).expandX();

            // Friends
            WSection friends = add(theme.section("Friends")).expandX().widget();
            WTable table = friends.add(theme.table()).expandX().widget();

            initTable(table);

            // New
            WHorizontalList list = friends.add(theme.horizontalList()).expandX().widget();

            WTextBox nameW = list.add(theme.textBox("")).minWidth(400).expandX().widget();
            nameW.setFocused(true);

            WPlus add = list.add(theme.plus()).widget();
            add.action = () -> {
                String name = nameW.get().trim();

                if (Friends.get().add(new Friend(name))) {
                    nameW.set("");

                    table.clear();
                    initTable(table);
                }
            };

            enterAction = add.action;
        }

        private void initTable(WTable table) {
            for (Friend friend : Friends.get()) {
                table.add(theme.label(friend.name));

                WMinus remove = table.add(theme.minus()).expandCellX().right().widget();
                remove.action = () -> {
                    Friends.get().remove(friend);

                    table.clear();
                    initTable(table);
                };

                table.row();
            }
        }

        @Override
        public boolean toClipboard() {
            return NbtUtils.toClipboard(Friends.get());
        }

        @Override
        public boolean fromClipboard() {
            return NbtUtils.fromClipboard(Friends.get());
        }
    }
}
