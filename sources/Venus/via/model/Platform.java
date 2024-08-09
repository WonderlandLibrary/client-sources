/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package via.model;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class Platform {
    public static int COUNT = 0;
    public static final List<ProtocolVersion> TEMP_INPUT_PROTOCOLS = new ArrayList<ProtocolVersion>();
    private final String name;
    private final BooleanSupplier load;
    private final Runnable executor;
    private final Consumer<List<ProtocolVersion>> versionCallback;

    public Platform(String string, BooleanSupplier booleanSupplier, Runnable runnable) {
        this(string, booleanSupplier, runnable, null);
    }

    public Platform(String string, BooleanSupplier booleanSupplier, Runnable runnable, Consumer<List<ProtocolVersion>> consumer) {
        this.name = string;
        this.load = booleanSupplier;
        this.executor = runnable;
        this.versionCallback = consumer;
    }

    public String getName() {
        return this.name;
    }

    public void createProtocolPath() {
        if (this.versionCallback != null) {
            this.versionCallback.accept(TEMP_INPUT_PROTOCOLS);
        }
    }

    public void build(Logger logger) {
        if (this.load.getAsBoolean()) {
            try {
                this.executor.run();
                logger.info("Loaded Platform " + this.name);
                ++COUNT;
            } catch (Throwable throwable) {
                logger.severe("An error occurred while loading Platform " + this.name + ":");
                throwable.printStackTrace();
            }
            return;
        }
        logger.severe("Platform " + this.name + " is not present");
    }
}

