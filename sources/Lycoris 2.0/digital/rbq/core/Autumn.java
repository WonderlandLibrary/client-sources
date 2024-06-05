/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.Display
 */
package digital.rbq.core;

import org.lwjgl.opengl.Display;
import digital.rbq.core.registry.impl.EventBusRegistry;
import digital.rbq.core.registry.impl.ManagerRegistry;

public final class Autumn {
    public static final Autumn INSTANCE = Autumn.builder().name("Autumn").version("1.0.0").authors("Zane", "Imminent", "NullEX").build();
    public static final EventBusRegistry EVENT_BUS_REGISTRY = new EventBusRegistry();
    public static final ManagerRegistry MANAGER_REGISTRY = new ManagerRegistry();
    private final String name;
    private final String version;
    private final String[] authors;

    private Autumn(String name, String version, String[] authors) {
        this.name = name;
        this.version = version;
        this.authors = authors;
    }

    private static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return this.name;
    }

    public String getVersion() {
        return this.version;
    }

    public String[] getAuthors() {
        return this.authors;
    }

    public void start() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::stop));
        Display.setTitle((String)(this.name + " " + this.version));
    }

    public void stop() {
        Autumn.MANAGER_REGISTRY.moduleManager.saveData();
    }

    public static class Builder {
        private String name;
        private String version;
        private String[] authors;

        protected Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder version(String version) {
            this.version = version;
            return this;
        }

        public Builder authors(String ... authors) {
            this.authors = authors;
            return this;
        }

        public final Autumn build() {
            return new Autumn(this.name, this.version, this.authors);
        }
    }
}

