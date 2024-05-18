package net.minecraft.client.triton.impl.modules.misc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.client.triton.management.event.EventTarget;
import net.minecraft.client.triton.management.event.events.PacketReceiveEvent;
import net.minecraft.client.triton.management.event.events.UpdateEvent;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.Module.Mod;
import net.minecraft.client.triton.utils.ClientUtils;
import net.minecraft.network.play.server.S38PacketPlayerListItem;

@Mod(displayName = "AntiVanish")
public class AntiVanish extends Module {

	private ArrayList<UUID> vanished = new ArrayList();
	
	public AntiVanish(){
		this.setProperties("AntiVanish", "AntiVanish", Module.Category.Misc, 0, "", true);
		vanished.clear();
	}

	@EventTarget
	private void onReceive(PacketReceiveEvent event) {
		if (ClientUtils.world() != null && event.getPacket() instanceof S38PacketPlayerListItem) {
			final S38PacketPlayerListItem listItem = (S38PacketPlayerListItem) event.getPacket();
			if (listItem.func_179768_b() == S38PacketPlayerListItem.Action.UPDATE_LATENCY) {
				for (final S38PacketPlayerListItem.AddPlayerData data : (List<S38PacketPlayerListItem.AddPlayerData>) listItem
						.func_179767_a()) {
					if (ClientUtils.mc().getNetHandler().func_175102_a(data.getField_179964_d().getId()) == null
							&& !this.checkList(data.getField_179964_d().getId())) {
						ClientUtils.sendMessage("§c" + this.getName(data.getField_179964_d().getId()) + "is vanished.");
					}
				}
			}
		}
	}

	@EventTarget(0)
	private void onUpdate(UpdateEvent event) {
		if (event.getState().PRE == event.getState()) {
			this.setSuffix("" + ClientUtils.player().sendQueue.getPlayerInfoMap().size());
			for (final UUID uuid : this.vanished) {
				if (ClientUtils.mc().getNetHandler().func_175102_a(uuid) != null) {
					ClientUtils.sendMessage("§c" + this.getName(uuid) + "is no longer vanished.");
					this.vanished.remove(uuid);
				}
			}
		}
	}

	public String getName(final UUID uuid) {
		try {
			final URL url = new URL("https://namemc.com/profile/" + uuid.toString());
			final URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.7; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
			final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String name = null;
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.contains("<title>")) {
					name = line.split("§")[0].trim().replaceAll("<title>", "").replaceAll("</title>", "")
							.replaceAll("\u2013 Minecraft Profile \u2013 NameMC", "")
							.replaceAll("\u00e2\u20ac\u201c Minecraft Profile \u00e2\u20ac\u201c NameMC", "");
				}
			}
			reader.close();
			return name;
		} catch (Exception e) {
			e.printStackTrace();
			return "(Failed) " + uuid;
		}
	}
	
	public void onEnable(){
		vanished.clear();
		super.enable();
	}
	public void onDisable(){
		vanished.clear();
		super.disable();
	}

	private boolean checkList(final UUID uuid) {
		if (this.vanished.contains(uuid)) {
			this.vanished.remove(uuid);
			return true;
		}
		this.vanished.add(uuid);
		return false;
	}
}
