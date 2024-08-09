/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Properties;
import net.minecraft.util.SharedConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerEula {
    private static final Logger LOG = LogManager.getLogger();
    private final Path eulaFile;
    private final boolean acceptedEULA;

    public ServerEula(Path path) {
        this.eulaFile = path;
        this.acceptedEULA = SharedConstants.developmentMode || this.loadEulaStatus();
    }

    private boolean loadEulaStatus() {
        boolean bl;
        block8: {
            InputStream inputStream = Files.newInputStream(this.eulaFile, new OpenOption[0]);
            try {
                Properties properties = new Properties();
                properties.load(inputStream);
                bl = Boolean.parseBoolean(properties.getProperty("eula", "false"));
                if (inputStream == null) break block8;
            } catch (Throwable throwable) {
                try {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                } catch (Exception exception) {
                    LOG.warn("Failed to load {}", (Object)this.eulaFile);
                    this.createEULAFile();
                    return true;
                }
            }
            inputStream.close();
        }
        return bl;
    }

    public boolean hasAcceptedEULA() {
        return this.acceptedEULA;
    }

    private void createEULAFile() {
        if (!SharedConstants.developmentMode) {
            try (OutputStream outputStream = Files.newOutputStream(this.eulaFile, new OpenOption[0]);){
                Properties properties = new Properties();
                properties.setProperty("eula", "false");
                properties.store(outputStream, "By changing the setting below to TRUE you are indicating your agreement to our EULA (https://account.mojang.com/documents/minecraft_eula).");
            } catch (Exception exception) {
                LOG.warn("Failed to save {}", (Object)this.eulaFile, (Object)exception);
            }
        }
    }
}

