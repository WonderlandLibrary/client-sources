package xyz.mathax.mathaxclient.systems.themes.widgets;

import xyz.mathax.mathaxclient.gui.WidgetScreen;
import xyz.mathax.mathaxclient.gui.widgets.WAccount;
import xyz.mathax.mathaxclient.systems.accounts.Account;
import xyz.mathax.mathaxclient.utils.render.color.Color;

public class WAccountTheme extends WAccount implements WidgetTheme {
    public WAccountTheme(WidgetScreen screen, Account<?> account) {
        super(screen, account);
    }

    @Override
    protected Color loggedInColor() {
        return theme().loggedInColorSetting.get();
    }

    @Override
    protected Color accountTypeColor() {
        return theme().textSecondaryColorSetting.get();
    }
}
