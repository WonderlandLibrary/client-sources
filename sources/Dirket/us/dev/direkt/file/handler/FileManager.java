package us.dev.direkt.file.handler;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ImmutableClassToInstanceMap;
import us.dev.direkt.Direkt;
import us.dev.direkt.file.ClientFile;
import us.dev.direkt.file.internal.files.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Foundry
 */
public final class FileManager {
    private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    private final ClassToInstanceMap<ClientFile> fileRegistry = new ImmutableClassToInstanceMap.Builder<ClientFile>()
            .put(AccountsFile.class, new AccountsFile())
            .put(FontsFile.class, new FontsFile())
            .put(FriendsFile.class, new FriendsFile())
            .put(ModulesFile.class, new ModulesFile())
            .put(WaypointsFile.class, new WaypointsFile())
            .build();

    public void setup() {
        if ((!Direkt.getInstance().getClientDirectory().exists())
                && Direkt.getInstance().getClientDirectory().mkdirs()) {
            logger.log(Level.FINE, "Successfully created " + Direkt.getInstance().getClientName() + " directory at \"" + Direkt.getInstance().getClientDirectory().getAbsolutePath() + "\".");
        }
        else if (!Direkt.getInstance().getClientDirectory().exists()) {
            logger.log(Level.SEVERE, "Failed to create " + Direkt.getInstance().getClientName() + " directory.");
        }
    }

    public void loadAllFiles() {
        for (ClientFile file : fileRegistry.values()) {
            try {
                file.load();
            } catch (IOException e) {
                logger.log(Level.SEVERE, String.format("File Manager: ERROR: File \"%s.%s\" could not save, a stack trace has been printed.", file.getName(), file.getExtension()));
                e.printStackTrace();
            }
        }
    }

    public void saveAllFiles() {
        for (ClientFile file : fileRegistry.values()) {
            try {
                file.save();
            } catch (IOException e) {
                logger.log(Level.SEVERE, String.format("File Manager: ERROR: File \"%s.%s\" could not save, a stack trace has been printed.", file.getName(), file.getExtension()));
                e.printStackTrace();
            }
        }
    }

    public <T extends ClientFile> T getFile(Class<T> clazz) {
        return fileRegistry.getInstance(clazz);
    }
}
