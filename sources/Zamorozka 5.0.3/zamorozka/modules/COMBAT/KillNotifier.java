package zamorozka.modules.COMBAT;

import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.text.TextFormatting;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPacketReceive;
import zamorozka.event.events.EventRender2D;
import zamorozka.event.events.EventSendPacket;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.font.CFontRenderer;
import zamorozka.ui.font.Fonts;

public class KillNotifier extends Module {

	private static int count = 0;
	
	public KillNotifier() {
		super("KillNotifier", 0, Category.COMBAT);
	}
	
	@EventTarget
    public void onRender2D(EventRender2D event) {
        CFontRenderer font = Fonts.elliot17;
        int y = font.getHeight() + 11;
        y += 70;
        font.drawStringWithShadow("KillCount: " + count, 4, y, -1);
    }
    @EventTarget
    public void onSned(EventSendPacket event) {
        if (event.getPacket() instanceof SPacketChat) {
            SPacketChat packet = (SPacketChat) event.getPacket();
            String message = TextFormatting.getTextWithoutFormattingCodes(packet.getChatComponent().getUnformattedText());
            if (message != null && message.length() > 0) {
                String[] text = message.split(" ");
                if ((text[1].equalsIgnoreCase("killed") || text[1].equalsIgnoreCase("shot")) && text[0].startsWith(mc.player.getName() + "(")) {
                    count++;
                }
            }
        }
    }

    public static void setCount(int negro) {
        count = negro;
    }
}