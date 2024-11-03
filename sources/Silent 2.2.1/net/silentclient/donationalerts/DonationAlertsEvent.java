package net.silentclient.donationalerts;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.silentclient.client.Client;
import net.silentclient.client.utils.TimerUtils;

public class DonationAlertsEvent {
	public String amount;
	public String currency;
	public String username;
	public String message;
	public TimerUtils timer = new TimerUtils();
	public boolean show;
	
	public String getAmount() {
		return amount;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getUsername() {
		return username;
	}
	
	public static DonationAlertsEvent getDonationAlertsEvent(String json) {
		try {
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			
			return gson.fromJson(json.toString(), DonationAlertsEvent.class);
		} catch (Exception err) {
			Client.logger.catching(err);
			return null;
		}
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public TimerUtils getTimer() {
		return timer;
	}
	
	public boolean isShow() {
		return show;
	}
	
	public void setShow(boolean show) {
		this.show = show;
	}
}
