package dev.luvbeeq.via.model;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class Platform {
    public static int COUNT = 0;
    public final static List<ProtocolVersion> TEMP_INPUT_PROTOCOLS = new ArrayList<>(); // List of all Platform's protocol versions

    private final String name;
    private final BooleanSupplier load;
    private final Runnable executor;
    private final Consumer<List<ProtocolVersion>> versionCallback;

    public Platform(String name, BooleanSupplier load, Runnable executor) {
        this(name, load, executor, null);
    }

    public Platform(String name, BooleanSupplier load, Runnable executor, Consumer<List<ProtocolVersion>> versionCallback) {
        this.name = name;
        this.load = load;
        this.executor = executor;
        this.versionCallback = versionCallback;
    }

    public String getName() {
        return name;
    }

    public void createProtocolPath() {
        if (this.versionCallback != null) {
            this.versionCallback.accept(TEMP_INPUT_PROTOCOLS);
        }
    }

    public void build(final Logger logger) {
        if (this.load.getAsBoolean()) {
            try {
                this.executor.run();
                logger.info("Loaded Platform " + this.name);
                COUNT++;
            } catch(Throwable t) {
                logger.severe("An error occurred while loading Platform " + this.name + ":");
                t.printStackTrace();
            }
            return;
        }
        logger.severe("Platform " + this.name + " is not present");
    }
}
