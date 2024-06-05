package win.sightclient.cmd.commands;

import java.util.concurrent.TimeUnit;

import win.sightclient.Sight;
import win.sightclient.cmd.Command;
import win.sightclient.utils.minecraft.ChatUtils;

public class PlayTimeCommand extends Command {

	public PlayTimeCommand() {
		super(new String[] {"playtime", "timeplayed", "timeplay"});
	}

	@Override
	public void onCommand(String message) {
		if (mc.thePlayer == null) {
			return;
		}
		ChatUtils.sendMessage(this.getTotalString());
	}
	
	private String getTotalString() {
		long currentsession = System.currentTimeMillis() - (Sight.instance.getStartTime() - TimeUnit.SECONDS.toMillis((long) ((Sight.instance.hoursPlayed * 60) * 60)));
 		long second = TimeUnit.MILLISECONDS.toSeconds(currentsession) % 60;
		long minute = TimeUnit.MILLISECONDS.toMinutes(currentsession) % 60;
		long hour = TimeUnit.MILLISECONDS.toHours(currentsession) % 24;
		long day = TimeUnit.MILLISECONDS.toDays(currentsession);

		StringBuilder sb = new StringBuilder("Time Played: ");
		sb.append((day > 0 ? day + " day" + (day > 1 ? "s, " : ", ") : ""));
		sb.append((hour > 0 ? hour + " hour" + (hour > 1 ? "s, " : ", ") : ""));
		sb.append((minute > 0 ? minute + " minute" + (minute > 1 ? "s, " : ", ") : ""));
		sb.append((second > 0 ? second + " second" + (second > 1 ? "s" : "") : ""));
		return sb.toString();
	}
}
