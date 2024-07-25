package club.bluezenith.util.client;

import club.bluezenith.module.modules.misc.Debug;
import club.bluezenith.util.MinecraftInstance;
import com.google.gson.JsonObject;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.FileChooser;
import net.minecraft.util.IChatComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

public class ClientUtils extends MinecraftInstance {
    private static final Logger logger = LogManager.getLogger("BlueZenith");

    public static Logger getLogger() {
        return logger;
    }

    public static void displayChatMessage(final String message) {
        if (mc.thePlayer == null) {
            getLogger().info("(MCChat)" + message.replaceAll("§", ""));
            return;
        }

        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("text", message);

        mc.thePlayer.addChatMessage(IChatComponent.Serializer.jsonToComponent(jsonObject.toString()));
    }
    
    public static void fancyMessage(String f) {
        Chat.bz(f);//displayChatMessage("§r§bBlue Zenith §r§7\u00bb " + f);
    } //§3§l[§r§bBlue Zenith§3§l] §r§9"+f

    public static void fancyMessage(Object f){
        fancyMessage(String.valueOf(f));
    }

    public static void debug(String f) {
      /*  if(BlueZenith.getBlueZenith().getModuleManager().getModule(Debug.class).getState())
        displayChatMessage("§r§bBlue Zenith §r§7\u00bb " + f);*/
        Debug.debug(f);
    }

    public static void debug(String caller, String text) {
        debug("[" + caller + "] " + text);
    }
    public static void debug(Object f) {
        debug(String.valueOf(f));
    }

    public static void debugf(String f, Object... objects) {
        debug(String.format(f, objects));
    }

    public static void debugf(String caller, String f, Object... objects) {
        debug(caller, String.format(f, objects));
    }

    public static File openFileChooser(File initFile, FileChooser.ExtensionFilter...ex){
        AtomicReference<File> o = new AtomicReference<>();
        o.set(null);

        runAndWait(() -> {
            FileChooser d = new FileChooser();
            if (initFile != null) {
                if(initFile.getParentFile().isDirectory()){
                    d.setInitialDirectory(initFile.getParentFile());
                }
                d.setInitialFileName(initFile.getName());
            }
            d.getExtensionFilters().addAll(ex);
            o.set(d.showOpenDialog(null));
        });

        return o.get();
    }

    public static File openFileSaver(File initFile, FileChooser.ExtensionFilter...ex){
        AtomicReference<File> o = new AtomicReference<>();
        o.set(null);

        runAndWait(() -> {
            FileChooser d = new FileChooser();
            if (initFile != null) {
                if(initFile.getParentFile().isDirectory()){
                    d.setInitialDirectory(initFile.getParentFile());
                }

                d.setInitialFileName(initFile.getName());
            }
            d.getExtensionFilters().addAll(ex);
            o.set(d.showSaveDialog(null));
        });

        return o.get();
    }
    /**
     * THIS WASN'T MADE BY ME! https://news.kynosarges.org/2014/05/01/simulating-platform-runandwait/ WHY THIS TOOK SO LONG OMG
     */
    public static void runAndWait(Runnable action) {
        if (action == null)
            throw new NullPointerException("action");

        if (Platform.isFxApplicationThread()) {
            action.run();
            return;
        }

        final CountDownLatch doneLatch = new CountDownLatch(1);
        new JFXPanel();
        Platform.runLater(() -> {
            try {
                action.run();
            } finally {
                doneLatch.countDown();
            }
        });

        try {
            doneLatch.await();
        } catch (InterruptedException ignored) {}
    }

    public static <Z> Z runCatching(UnsafeSupplier<Z> func) {
        try {
            return func.get();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public interface UnsafeRunnable {
        void run() throws Throwable;
    }

    public static void runCatching(UnsafeRunnable func) {
        try {
            func.run();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public static final Pattern emailPattern =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", CASE_INSENSITIVE);


}
