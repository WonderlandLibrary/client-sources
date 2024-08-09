/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.server;

import java.nio.file.Path;
import java.util.function.UnaryOperator;
import net.minecraft.server.dedicated.ServerProperties;
import net.minecraft.util.registry.DynamicRegistries;

public class ServerPropertiesProvider {
    private final Path propertiesPath;
    private ServerProperties properties;

    public ServerPropertiesProvider(DynamicRegistries dynamicRegistries, Path path) {
        this.propertiesPath = path;
        this.properties = ServerProperties.func_244380_a(dynamicRegistries, path);
    }

    public ServerProperties getProperties() {
        return this.properties;
    }

    public void save() {
        this.properties.save(this.propertiesPath);
    }

    public ServerPropertiesProvider func_219033_a(UnaryOperator<ServerProperties> unaryOperator) {
        this.properties = (ServerProperties)unaryOperator.apply(this.properties);
        this.properties.save(this.propertiesPath);
        return this;
    }
}

