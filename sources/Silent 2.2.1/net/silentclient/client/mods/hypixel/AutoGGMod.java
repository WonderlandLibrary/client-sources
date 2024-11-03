package net.silentclient.client.mods.hypixel;

import net.minecraft.network.play.server.S02PacketChat;
import net.silentclient.client.Client;
import net.silentclient.client.event.EventTarget;
import net.silentclient.client.event.impl.EventReceivePacket;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;
import net.silentclient.client.mods.util.Server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class AutoGGMod extends Mod {
	
	private static boolean isHypixel;
	private static List<Object> triggers;
	private static boolean isRunning;
	public static final ExecutorService THREAD_POOL = Executors.newCachedThreadPool((ThreadFactory)new AutoGGThreadFactory());
	
	public AutoGGMod() {
		super("Auto GG", ModCategory.MODS, "silentclient/icons/mods/autogg.png");
		getTriggers();
	}
	
	@Override
	public void setup() {
		this.addInputSetting("Phrase", this, "gg");
	}
	
	@EventTarget
	public void onNewMessageInChat(EventReceivePacket event) {
		if(!(event.getPacket() instanceof S02PacketChat)) {
			return;
		}
		S02PacketChat e = (S02PacketChat) event.getPacket();
		if(isRunning || mc.getCurrentServerData() == null) {
			return;
		}

		try {
			AutoGGMod.isHypixel = Server.isHypixel();
		} catch (Exception err) {
			err.printStackTrace();
			AutoGGMod.isHypixel = false;
		}

		if(Server.isRuHypixel()) {
			String unformattedMessage = e.getChatComponent().getUnformattedText();
			if((unformattedMessage.contains("Победитель:") || unformattedMessage.contains("Winner:")) && unformattedMessage.startsWith(" ")) {
				AutoGGMod.isRunning = true;
				AutoGGMod.THREAD_POOL.submit(new sendGG());
			}
			return;
		}
				
		if(AutoGGMod.isHypixel) {
			try {
				String unformattedMessage = e.getChatComponent().getUnformattedText();
				if (AutoGGMod.triggers.stream().anyMatch(trigger -> unformattedMessage.contains(trigger.toString())) && unformattedMessage.startsWith(" ")) {
					AutoGGMod.isRunning = true;
					AutoGGMod.THREAD_POOL.submit(new sendGG());
				} 
			} catch (Exception err) {
				err.printStackTrace();
			}
		}
	}
	
	private void getTriggers() {
		try {
			String rawTriggers = "1st Killer - \n"
					+ "1st Place - \n"
					+ "Winner: \n"
					+ " - Damage Dealt - \n"
					+ "Winning Team -\n"
					+ "1st - \n"
					+ "Winners: \n"
					+ "Winner: \n"
					+ "Winning Team: \n"
					+ " won the game!\n"
					+ "Top Seeker: \n"
					+ "1st Place: \n"
					+ "Last team standing!\n"
					+ "Winner #1 (\n"
					+ "Top Survivors\n"
					+ "Winners - \n"
					+ "Sumo Duel - " + "1-й убийца -\n"
					+ "1-е место - \n"
					+ "Победитель: \n"
					+ " - Нанесенный урон - \n"
					+ "Команда-победитель -\n"
					+ "1-й - \n"
					+ "Победители: \n"
					+ "Победитель: \n"
					+ "Команда-победитель:\n"
					+ "выиграл игру!\n"
					+ "Лучший искатель:\n"
					+ "1-е место:\n"
					+ "Последняя оставшаяся команда!\n"
					+ "Победитель №1 (\n"
					+ "Лучшие выжившие\n"
					+ "Победители - \n"
					+ "Дуэль Сумо -";
			AutoGGMod.triggers = new ArrayList<Object>(Arrays.asList((Object[])rawTriggers.split("\n")));
		} catch (Exception e) {
			 e.printStackTrace();
		} 
	}
	
	private class sendGG implements Runnable {
		public void run() {
			try {
				mc.thePlayer.sendChatMessage(Client.getInstance().getSettingsManager().getSettingByClass(AutoGGMod.class, "Phrase").getValString());
				Thread.sleep(2000L);
				AutoGGMod.isRunning = false;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static class AutoGGThreadFactory implements ThreadFactory {
		  private final AtomicInteger threadNumber = new AtomicInteger(1);
		  
		  public Thread newThread(Runnable r) {
		    return new Thread(r, "AutoGG" + this.threadNumber.getAndIncrement());
		  }
	}

}