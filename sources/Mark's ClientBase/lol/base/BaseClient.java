package lol.base;

import lol.base.managers.ClientManager;
import lol.base.managers.EventManager;
import lol.base.managers.ModuleManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

public class BaseClient {

    public ClientManager clientManager = new ClientManager();

    public final Logger logger = LogManager.getLogger(clientManager.name);

    public EventManager eventManager;
    public ModuleManager moduleManager;

    public void preLaunch() {
        eventManager = new EventManager();
    }

    public void postLaunch() {
        Display.setTitle(String.format("%s - %s - %s", clientManager.name, clientManager.version, clientManager.author));

        moduleManager = new ModuleManager();

        moduleManager.init();

        logger.info("Client started!");
    }

    public void reload() {
        eventManager = new EventManager();
        moduleManager = new ModuleManager();
    }

    public void shutdown() {
        // Perform shutdown stuff
    }

    private static volatile BaseClient singleton;

    public static synchronized BaseClient get() {
        return singleton == null ? singleton = new BaseClient() : singleton;
    }

}
