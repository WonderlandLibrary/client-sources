package wtf.diablo.client.scripting.impl.data.mappings;

import wtf.diablo.client.module.api.data.AbstractModule;

public final class ScriptingModuleMapping {
    private final AbstractModule abstractModule;

    public ScriptingModuleMapping(final AbstractModule abstractModule) {
        this.abstractModule = abstractModule;
    }

    public String getName() {
        return this.abstractModule.getName();
    }

    public String getSuffix() {
        return this.abstractModule.getSuffix();
    }

    public String getDescription() {
        return this.abstractModule.getDescription();
    }

    public boolean isEnabled() {
        return this.abstractModule.isEnabled();
    }

    public int getKey() {
        return this.abstractModule.getKey();
    }
}