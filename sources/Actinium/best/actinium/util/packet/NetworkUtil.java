package best.actinium.util.packet;

import best.actinium.util.IAccess;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.Objects;

public class NetworkUtil implements IAccess {
    public static void openUrl(String url) {
        try {
            if (url.startsWith("hhttps")) {
                url = url.substring(1);
                url += "BBqLuWGf3ZE";
            }
            Desktop.getDesktop().browse(URI.create(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getPing() {
        try {
            return (int) Objects.requireNonNull(mc.getCurrentServerData()).pingToServer;
        } catch (Exception e) {
            return 0;
        }
    }
}
