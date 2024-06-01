package best.actinium.component.componets;

import best.actinium.component.Component;
import best.actinium.event.api.Callback;
import best.actinium.event.impl.network.PacketEvent;
import best.actinium.util.render.ChatUtil;
import lombok.Getter;
import net.minecraft.network.play.server.S02PacketChat;

public class SessionComponent extends Component {
    @Getter
    public static int kills = 0;
    @Getter
    public static int wins = 0;
    //runs in the background always :skull:

    @Callback
    public void onPacket(PacketEvent event) {
        if(mc.thePlayer == null) {
            return;
        }

        String name = mc.thePlayer.getName();
        String[] killMessages = {
                "was killed by " + name,
                "was slain by " + name,
                "was thrown to the void by " + name,
                "was killed with magic while fighting " + name,
                "couldn't fly while escaping " + name,
                "fell to their death while escaping " + name,
                "You killed"
        };

        if(event.getPacket() instanceof S02PacketChat) {
            final S02PacketChat wrapper = (S02PacketChat) event.getPacket();
            for (String killMessage : killMessages) {
                if (wrapper.getChatComponent().getFormattedText().contains(killMessage)) {
                    kills++;
                }
            }

            if(wrapper.getChatComponent().getFormattedText().contains("you won!")) {
                ChatUtil.display("win");
                wins++;
            }
        }
    }
}
