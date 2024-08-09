package mathax.client.gui.screens.accounts;

import mathax.client.gui.GuiTheme;
import mathax.client.gui.widgets.containers.WTable;
import mathax.client.gui.widgets.input.WTextBox;
import mathax.client.systems.accounts.types.EasyMCAccount;

public class AddEasyMCAccountScreen extends AddAccountScreen {
    public AddEasyMCAccountScreen(GuiTheme theme, AccountsScreen parent) {
        super(theme, "Add an EasyMC Account", parent);
    }

    @Override
    public void initWidgets() {
        WTable t = add(theme.table()).widget();

        // Token
        t.add(theme.label("Token: "));
        WTextBox token = t.add(theme.textBox("")).minWidth(400).expandX().widget();
        token.setFocused(true);
        t.row();

        // Add
        add = t.add(theme.button("Add")).expandX().widget();
        add.action = () -> {
            if (!token.get().isEmpty()) {
                AccountsScreen.addAccount(this, parent, new EasyMCAccount(token.get()));
            }
        };

        enterAction = add.action;
    }
}
