package cc.slack.utils.player;
import cc.slack.utils.client.IMinecraft;

public class ItemSpoofUtil implements IMinecraft {


    public static boolean isEnabled = false;
    public static int renderSlot = 0;
    public static int realSlot = 0;

    public static void startSpoofing(int slot) {
        if (isEnabled) {
            realSlot = slot;
            mc.thePlayer.inventory.currentItem = realSlot;
            return;
        }
        renderSlot = mc.thePlayer.inventory.currentItem;
        realSlot = slot;
        mc.thePlayer.inventory.currentItem = realSlot;
        isEnabled = true;
    }

    public static void stopSpoofing() {
        realSlot = renderSlot;
        isEnabled = false;
        mc.thePlayer.inventory.currentItem = renderSlot;
    }
}
