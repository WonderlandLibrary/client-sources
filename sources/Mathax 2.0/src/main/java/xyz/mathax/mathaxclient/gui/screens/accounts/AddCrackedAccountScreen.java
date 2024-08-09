package xyz.mathax.mathaxclient.gui.screens.accounts;

import xyz.mathax.mathaxclient.systems.themes.Theme;
import xyz.mathax.mathaxclient.gui.widgets.containers.WTable;
import xyz.mathax.mathaxclient.gui.widgets.input.WTextBox;
import xyz.mathax.mathaxclient.systems.accounts.Accounts;
import xyz.mathax.mathaxclient.systems.accounts.types.CrackedAccount;

public class AddCrackedAccountScreen extends AddAccountScreen {
    public AddCrackedAccountScreen(Theme theme, AccountsScreen parent) {
        super(theme, "Add Cracked Account", parent);
    }

    @Override
    public void initWidgets() {
        WTable table = add(theme.table()).widget();

        // Name
        table.add(theme.label("Name: "));
        WTextBox name = table.add(theme.textBox("", (text, c) -> c != ' ')).minWidth(400).expandX().widget();
        name.setFocused(true);
        table.row();

        // Add
        add = table.add(theme.button("Add")).expandX().widget();
        add.action = () -> {
            if (!name.get().isEmpty() && (name.get().length() < 17) && name.get().matches("^[a-zA-Z0-9_]+$")) {
                CrackedAccount account = new CrackedAccount(name.get());
                if (!(Accounts.get().exists(account))) {
                    AccountsScreen.addAccount(this, parent, account);
                }
            }
        };

        enterAction = add.action;
    }
}
