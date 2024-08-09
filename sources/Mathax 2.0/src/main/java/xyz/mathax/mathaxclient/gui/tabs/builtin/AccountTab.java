package xyz.mathax.mathaxclient.gui.tabs.builtin;

import net.minecraft.client.gui.screen.Screen;
import xyz.mathax.mathaxclient.gui.tabs.Tab;
import xyz.mathax.mathaxclient.gui.tabs.TabScreen;
import xyz.mathax.mathaxclient.gui.tabs.Tabs;
import xyz.mathax.mathaxclient.gui.tabs.WindowTabScreen;
import xyz.mathax.mathaxclient.gui.widgets.input.WTextBox;
import xyz.mathax.mathaxclient.gui.widgets.pressable.WButton;
import xyz.mathax.mathaxclient.systems.themes.Theme;
import xyz.mathax.mathaxclient.utils.network.api.Api;

public class AccountTab extends Tab {
    public AccountTab() {
        super("Account");
    }

    @Override
    public TabScreen createScreen(Theme theme) {
        return Api.token.isBlank() ? new LoginScreen(theme, this) : new AccountScreen(theme, this);
    }

    @Override
    public boolean isScreen(Screen screen) {
        return screen instanceof AccountScreen;
    }

    private static class AccountScreen extends WindowTabScreen {
        public AccountScreen(Theme theme, Tab tab) {
            super(theme, tab);
        }

        @Override
        public void initWidgets() {
            WButton logout = add(theme.button("Log Out")).expandX().widget();
            logout.action = () -> {
                Api.token = "";
                Tabs.get(AccountTab.class).openScreen(theme);
            };
        }
    }

    private static class LoginScreen extends WindowTabScreen {
        public LoginScreen(Theme theme, Tab tab) {
            super(theme, tab);
        }

        @Override
        public void initWidgets() {
            add(theme.label("Username or E-Mail")).expandX();
            WTextBox usernameOrEmail = add(theme.textBox("")).minWidth(400).expandX().widget();

            add(theme.label("Password")).expandX();
            WTextBox password = add(theme.textBox("")).minWidth(400).expandX().widget();

            add(theme.horizontalSeparator()).expandX();

            WButton login = add(theme.button("Log In")).expandX().widget();
            login.action = () -> {
                Api.login(usernameOrEmail.get(), password.get());
                reload();
            };
        }
    }
}
