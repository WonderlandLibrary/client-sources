package net.augustus.modules.misc;

import java.awt.Color;

import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.notify.GeneralNotifyManager;
import net.augustus.notify.NotificationType;
import net.augustus.settings.BooleanValue;

public class IRC extends Module{
	
   public BooleanValue showUsername;
   public BooleanValue showCoords;
   public BooleanValue showHealth;
	
	public IRC() {
		super("IRC", Color.gray, Categorys.MISC);
		showUsername = new BooleanValue(294230, "ShowUsername", this, true);
		showCoords = new BooleanValue(5435431, "ShowCoords", this, true);
		showHealth = new BooleanValue(534745, "ShowHealth", this, true);
	}

	public void onEnable() {
        GeneralNotifyManager.addNotification("The IRC feature is not enabled at the moment.", NotificationType.Error);
		this.toggle();
	}
	
}
