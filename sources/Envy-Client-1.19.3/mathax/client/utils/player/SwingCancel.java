package mathax.client.utils.player;

import static mathax.client.MatHax.mc;

public class SwingCancel {
    public static void noSwing() {
        if (mc.player.handSwingProgress >= 0) {
            mc.player.handSwinging = false;
        }
    }
}
