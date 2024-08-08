package lol.point.returnclient.ui.utils;


import org.apache.commons.codec.digest.DigestUtils;

import java.net.URI;

public class SystemUtils {
    public static void openWebLink(final URI url) {
        try {
            final Class<?> desktop = Class.forName("java.awt.Desktop");
            final Object object = desktop.getMethod("getDesktop", new Class[0]).invoke(null);
            desktop.getMethod("browse", new Class[]{URI.class}).invoke(object, url);
        } catch (Throwable throwable) {
            System.err.println(throwable.getCause().getMessage());
        }
    }

    // Source: https://github.com/zzurio/HWID-Authentication-System
    public static String getSystemInfo() {
        return DigestUtils.sha256Hex(DigestUtils.sha256Hex(
                String.format(
                        "%s-%s-%s-%s-%s-%s-%s-%s-%s-%s-%s",
                        System.getProperty("user.name"),
                        System.getProperty("os.name"),
                        System.getProperty("os.arch"),
                        System.getenv("os"),
                        System.getenv("SystemRoot"),
                        System.getenv("HOMEDRIVE"),
                        System.getenv("PROCESSOR_LEVEL"),
                        System.getenv("PROCESSOR_REVISION"),
                        System.getenv("PROCESSOR_IDENTIFIER"),
                        System.getenv("PROCESSOR_ARCHITECTURE"),
                        System.getenv("NUMBER_OF_PROCESSORS")
                )
        ));
    }
}
