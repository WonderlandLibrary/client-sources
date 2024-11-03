package net.silentclient.client.mods.hud;

import net.silentclient.client.Client;
import net.silentclient.client.mods.HudMod;
import net.silentclient.client.mods.ModCategory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ClockMod extends HudMod {
	public ClockMod() {
		super("Clock", ModCategory.MODS, "silentclient/icons/mods/clock.png", false);
	}
	
	@Override
	public void setup() {
		super.setupWithAfterValue();
		this.addBooleanSetting("24 Hour Format", this, false);
	}
	
	@Override
	public String getText() {
		boolean format = Client.getInstance().getSettingsManager().getSettingByName(this, "24 Hour Format").getValBoolean();
		return "00:00" + (format ? "" : " PM");
	}
	
	@Override
	public String getTextForRender() {
		boolean format = Client.getInstance().getSettingsManager().getSettingByName(this, "24 Hour Format").getValBoolean();
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat((format ? "HH" : "hh")+ ":mm" + (format ? "" : " a"));
        String time = df.format(c.getTime());
        
        return time;
	}
}
