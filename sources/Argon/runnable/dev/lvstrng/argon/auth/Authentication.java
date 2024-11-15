package dev.lvstrng.argon.auth;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;

public final class Authentication {
    public static Color method331(final int alpha, final int increment) {
        return getRainbow(increment, alpha);
    }

    public static Color getRainbow(int incr, int alpha) {
        Color color = Color.getHSBColor(((System.currentTimeMillis() + incr * 200) % (360 * 20)) / (360f * 20), 0.5f, 1f);
        return new Color(color.getRed(), color.getBlue(), color.getGreen(), alpha);
    }

    public static File getArgonJar() throws URISyntaxException {
        return new File(Authentication.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
    }

    public static void downloadTo(final String downloadURL, final File savePath) throws IOException {
        final HttpURLConnection connection = (HttpURLConnection) new URL(downloadURL).openConnection();
        connection.setRequestMethod("GET");

        try (final InputStream is = connection.getInputStream(); FileOutputStream os = new FileOutputStream(savePath)) {
            byte[] buffer = new byte[4096];
            int read;
            while ((read = is.read(buffer)) != -1) os.write(buffer, 0, read);
        } finally {
            connection.disconnect();
        }
    }
}
