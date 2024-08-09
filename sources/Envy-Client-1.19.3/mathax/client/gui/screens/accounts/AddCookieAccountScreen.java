package mathax.client.gui.screens.accounts;

import mathax.client.MatHax;
import mathax.client.gui.GuiTheme;
import mathax.client.gui.widgets.containers.WTable;
import mathax.client.gui.widgets.input.WTextBox;
import mathax.client.gui.widgets.pressable.WButton;
import mathax.client.systems.accounts.MicrosoftCookieLogin;
import mathax.client.systems.accounts.types.MicrosoftAccount;
import mathax.client.utils.network.MatHaxExecutor;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

import java.io.File;

public class AddCookieAccountScreen extends AddAccountScreen{
    Exception exception = null;
    protected AddCookieAccountScreen(GuiTheme theme, AccountsScreen parent) {
        super(theme, "Import Cookie alt", parent);
    }

    public void initWidgets() {
        WTable t = add(theme.table()).widget();

        // last error
        if (exception != null) {
            t.add(theme.label("ERROR: " + exception.getLocalizedMessage()));
            t.row();
        }

        // Token
        t.add(theme.label("Path: "));
        WTextBox path = t.add(theme.textBox("")).minWidth(400).expandX().widget();
        path.setFocused(true);
        WButton browse = t.add(theme.button("Browse")).expandX().widget();
        browse.action = () -> MatHaxExecutor.EXECUTOR.execute(
            () -> {
                String temp = TinyFileDialogs.tinyfd_openFileDialog("Select Cookie alt", path.get(), null, null, false);
                if (temp != null) {
                    path.set(temp);
                }
            });
        t.row();

        // Add
        add = t.add(theme.button("Add")).expandX().widget();
        add.action = () -> MatHaxExecutor.EXECUTOR.execute(
            () -> {
                if (!path.get().isEmpty()) {
                    try {
                        locked = true;
                        File file = new File(path.get());
                        String refreshToken = MicrosoftCookieLogin.getRefreshTokenFromCookie(file);
                        locked = false;

                        if (refreshToken != null) {
                            MicrosoftAccount account = new MicrosoftAccount(refreshToken);
                            AccountsScreen.addAccount(this, parent, account);
                        }
                    } catch (Exception e) {
                        exception = e;
                        locked = false;
                    }
                }
            }
        );

        enterAction = add.action;
    }
}
