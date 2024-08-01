package wtf.diablo.client.scripting.impl.data.mappings;

import wtf.diablo.client.scripting.api.Script;

public final class ScriptingMapping {

    private final ScriptingModuleMapping scriptingModuleMapping;

    public ScriptingMapping(final Script script) {
        this.scriptingModuleMapping = new ScriptingModuleMapping(script.getAbstractModule());
    }
}
