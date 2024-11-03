package net.augustus.utils.interfaces;

import net.augustus.Augustus;
import net.augustus.settings.SettingsManager;

public interface SM {
   SettingsManager sm = Augustus.getInstance().getSettingsManager();
}
