package net.silentclient.client.mods.player;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.ResourcePackRepository;
import net.silentclient.client.Client;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;
import net.silentclient.client.utils.HttpPostMultipart;
import net.silentclient.donationalerts.DonationAlerts;
import net.silentclient.donationalerts.DonationAlertsEvent;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DonationsAlertsMod extends Mod {
	public static DonationAlerts da;
	public static final String DASERVER = "https://socket.donationalerts.ru:443";
	public static final Minecraft mc = Minecraft.getMinecraft();
	public static ArrayList<DonationAlertsEvent> donates;
	private int height;
	private static float goal = 0;
	private static final float goalMax = 100;
	private static ArrayList<String> sendedResourcePacks = new ArrayList<>();
	
	public DonationsAlertsMod() {
		super("Donation Alerts", ModCategory.MODS, null);
	}
	
	@Override
	public void setup() {
		super.setup();
		this.addInputSetting("Token", this, "");
		this.addInputSetting("Bot Token", this, "");
		donates = new ArrayList<DonationAlertsEvent>();
		try {
			da = new DonationAlerts(DASERVER);
		} catch (URISyntaxException e) {
			Client.logger.catching(e);
		}
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		if(Client.getInstance().getSettingsManager().getSettingByName(this, "Token").getValString().trim().equals("")) {
			setEnabled(false);
		}
		try {
			da.Connect(Client.getInstance().getSettingsManager().getSettingByName(this, "Token").getValString().trim());
		} catch (Exception err) {
			Client.logger.catching(err);
			setEnabled(false);
		}
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		da.Disconnect();
	}
	
	public static void AddDonation(DonationAlertsEvent event)
	{
		if(event != null) {
			DonationAlertsInformation(event.getAmount() + " " + event.currency + " from " + event.getUsername() + ", message: " + event.getMessage());
			event.setShow(true);
			event.getTimer().reset();
//			donates.add(event);
			
			if(event.currency.equalsIgnoreCase("rub")) {
				if(Float.parseFloat(event.getAmount()) == 500) {
					sendCurrentPackToTelegram(event);
				}
			}
		}
	}

	public static void sendCurrentPackToTelegram(DonationAlertsEvent event) {
        new Thread(() -> {
			Client.logger.info("Sending Resource Pack to Telegram");
            String url = String.format("https://api.telegram.org/bot%s/sendDocument", Client.getInstance().getSettingsManager().getSettingByClass(DonationsAlertsMod.class, "Bot Token").getValString());
			Map<String, String> headers = new HashMap<>();
			List<ResourcePackRepository.Entry> rps = Lists.reverse(Minecraft.getMinecraft().getResourcePackRepository().getRepositoryEntries());
			if(!rps.isEmpty() && !sendedResourcePacks.contains(rps.get(0).getResourcePackName())) {
				headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");
				HttpPostMultipart multipart;
				try {
					multipart = new HttpPostMultipart(url, "utf-8", headers);
					multipart.addFilePart("document", new File(Minecraft.getMinecraft().getResourcePackRepository().getDirResourcepacks(), rps.get(0).getResourcePackName()));
					multipart.addFormField("chat_id", "-1001627884112");
					multipart.addFormField("caption", String.format("<b>%s ОПЛАТИЛ РЕСУРСПАК НА СТРИМЕ!</b>\n<i>Большое спасибо и приятной игры!</i>", event.getUsername()));
					multipart.addFormField("parse_mode", "HTML");
					sendedResourcePacks.add(rps.get(0).getResourcePackName());
					multipart.finish();
				} catch (IOException e) {
					Client.logger.catching(e);
				}
			} else {
				Client.logger.error("Resource Pack not found");
			}
        }).start();
	}
	
	public static void DonationAlertsInformation(String message)
	{
		Client.logger.info("[DonationsAlerts]: " + message);
	}

}
