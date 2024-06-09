package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.mixin.extensibility.*;
import org.spongepowered.asm.mixin.*;

public class Config
{
    private final String name;
    private final MixinConfig config;
    
    public Config(final MixinConfig config) {
        super();
        this.name = config.getName();
        this.config = config;
    }
    
    public String getName() {
        return this.name;
    }
    
    MixinConfig get() {
        return this.config;
    }
    
    public boolean isVisited() {
        return this.config.isVisited();
    }
    
    public IMixinConfig getConfig() {
        return this.config;
    }
    
    public MixinEnvironment getEnvironment() {
        return this.config.getEnvironment();
    }
    
    @Override
    public String toString() {
        return this.config.toString();
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof Config && this.name.equals(((Config)o).name);
    }
    
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
    
    @Deprecated
    public static Config create(final String s, final MixinEnvironment mixinEnvironment) {
        return MixinConfig.create(s, mixinEnvironment);
    }
    
    public static Config create(final String s) {
        return MixinConfig.create(s, MixinEnvironment.getDefaultEnvironment());
    }
}
