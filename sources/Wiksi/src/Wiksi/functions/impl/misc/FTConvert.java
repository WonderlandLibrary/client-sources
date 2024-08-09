package src.Wiksi.functions.impl.misc;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventPacket;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import java.util.Locale;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SChatPacket;
import net.minecraft.util.text.TextFormatting;

@FunctionRegister(name = "FT Convert", type = Category.Misc)
public class FTConvert extends Function {
    public FTConvert() {
    }

    @Subscribe
    private void onPacket(EventPacket packetEvent) {
        IPacket event = packetEvent.getPacket();
        if (event instanceof SChatPacket chatPacket) {
            String chatMessage = chatPacket.getChatComponent().getString().toLowerCase(Locale.ROOT);
            if (chatMessage.contains("до следующего ивента:")) {
                int startIndex = chatMessage.indexOf(":") + 2;
                int endIndex = chatMessage.indexOf(" сек", startIndex);
                if (endIndex == -1) {
                    return;
                }

                String secondsString = chatMessage.substring(startIndex, endIndex);
                int seconds = Integer.parseInt(secondsString.trim());
                String convertedTime = this.convertTime(seconds);
                this.print(TextFormatting.GREEN + "До следующего ивента: " + convertedTime);
            }
        }

    }

    private String convertTime(int seconds) {
        int hours = seconds / 3600;
        int minutes = seconds % 3600 / 60;
        int remainingSeconds = seconds % 60;
        String timeString = "";
        if (hours > 0) {
            timeString = timeString + hours + " часов ";
        }

        if (minutes > 0) {
            timeString = timeString + minutes + " минут ";
        }

        if (remainingSeconds > 0 || timeString.isEmpty()) {
            timeString = timeString + remainingSeconds + " секунд";
        }

        return timeString.trim();
    }
}
