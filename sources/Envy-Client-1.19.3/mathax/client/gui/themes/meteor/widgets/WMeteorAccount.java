package mathax.client.gui.themes.meteor.widgets;

import mathax.client.gui.WidgetScreen;
import mathax.client.gui.themes.meteor.MeteorWidget;
import mathax.client.gui.widgets.WAccount;
import mathax.client.systems.accounts.Account;
import mathax.client.utils.render.color.Color;

public class WMeteorAccount extends WAccount implements MeteorWidget {
    public WMeteorAccount(WidgetScreen screen, Account<?> account) {
        super(screen, account);
    }

    @Override
    protected Color loggedInColor() {
        return theme().loggedInColor.get();
    }

    @Override
    protected Color accountTypeColor() {
        return theme().textSecondaryColor.get();
    }
}
