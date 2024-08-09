/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.lookup;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.AbstractConfigurationAwareLookup;
import org.apache.logging.log4j.status.StatusLogger;

@Plugin(name="log4j", category="Lookup")
public class Log4jLookup
extends AbstractConfigurationAwareLookup {
    public static final String KEY_CONFIG_LOCATION = "configLocation";
    public static final String KEY_CONFIG_PARENT_LOCATION = "configParentLocation";
    private static final Logger LOGGER = StatusLogger.getLogger();

    private static String asPath(URI uRI) {
        if (uRI.getScheme() == null || uRI.getScheme().equals("file")) {
            return uRI.getPath();
        }
        return uRI.toString();
    }

    private static URI getParent(URI uRI) throws URISyntaxException {
        String string = uRI.toString();
        int n = string.lastIndexOf(47);
        if (n > -1) {
            return new URI(string.substring(0, n));
        }
        return new URI("../");
    }

    @Override
    public String lookup(LogEvent logEvent, String string) {
        if (this.configuration != null) {
            ConfigurationSource configurationSource = this.configuration.getConfigurationSource();
            File file = configurationSource.getFile();
            if (file != null) {
                switch (string) {
                    case "configLocation": {
                        return file.getAbsolutePath();
                    }
                    case "configParentLocation": {
                        return file.getParentFile().getAbsolutePath();
                    }
                }
                return null;
            }
            URL uRL = configurationSource.getURL();
            if (uRL != null) {
                try {
                    switch (string) {
                        case "configLocation": {
                            return Log4jLookup.asPath(uRL.toURI());
                        }
                        case "configParentLocation": {
                            return Log4jLookup.asPath(Log4jLookup.getParent(uRL.toURI()));
                        }
                    }
                    return null;
                } catch (URISyntaxException uRISyntaxException) {
                    LOGGER.error(uRISyntaxException);
                    return null;
                }
            }
        }
        return null;
    }
}

