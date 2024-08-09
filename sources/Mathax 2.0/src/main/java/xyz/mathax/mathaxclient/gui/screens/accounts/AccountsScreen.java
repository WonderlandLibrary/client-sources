package xyz.mathax.mathaxclient.gui.screens.accounts;

import xyz.mathax.mathaxclient.systems.themes.Theme;
import xyz.mathax.mathaxclient.gui.WindowScreen;
import xyz.mathax.mathaxclient.gui.widgets.WAccount;
import xyz.mathax.mathaxclient.gui.widgets.containers.WContainer;
import xyz.mathax.mathaxclient.gui.widgets.containers.WHorizontalList;
import xyz.mathax.mathaxclient.gui.widgets.pressable.WButton;
import xyz.mathax.mathaxclient.systems.accounts.Account;
import xyz.mathax.mathaxclient.systems.accounts.Accounts;
import xyz.mathax.mathaxclient.systems.accounts.MicrosoftLogin;
import xyz.mathax.mathaxclient.systems.accounts.types.MicrosoftAccount;
import xyz.mathax.mathaxclient.utils.network.Executor;
import org.jetbrains.annotations.Nullable;
import xyz.mathax.mathaxclient.MatHax;

public class AccountsScreen extends WindowScreen {
    public AccountsScreen(Theme theme) {
        super(theme, "Accounts");
    }

    @Override
    public void initWidgets() {
        // Accounts
        for (Account<?> account : Accounts.get()) {
            WAccount wAccount = add(theme.account(this, account)).expandX().widget();
            wAccount.refreshScreenAction = this::reload;
        }

        // Add account
        WHorizontalList l = add(theme.horizontalList()).expandX().widget();

        addButton(l, "Cracked", () -> MatHax.mc.setScreen(new AddCrackedAccountScreen(theme, this)));
        addButton(l, "Altening", () -> MatHax.mc.setScreen(new AddAlteningAccountScreen(theme, this)));
        addButton(l, "Microsoft", () -> {
            locked = true;

            MicrosoftLogin.getRefreshToken(refreshToken -> {
                locked = false;

                if (refreshToken != null) {
                    MicrosoftAccount account = new MicrosoftAccount(refreshToken);
                    addAccount(null, this, account);
                }
            });
        });
    }

    private void addButton(WContainer c, String text, Runnable action) {
        WButton button = c.add(theme.button(text)).expandX().widget();
        button.action = action;
    }

    public static void addAccount(@Nullable AddAccountScreen screen, AccountsScreen parent, Account<?> account) {
        if (screen != null) {
            screen.locked = true;
        }

        Executor.execute(() -> {
            if (account.fetchInfo()) {
                account.getCache().loadHead();

                Accounts.get().add(account);
                if (account.login()) Accounts.get().save();

                if (screen != null) {
                    screen.locked = false;
                    screen.close();
                }

                parent.reload();

                return;
            }

            if (screen != null) {
                screen.locked = false;
            }
        });
    }
}
