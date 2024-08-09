/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.logging.log4j.core.jackson.Initializers;

final class Log4jYamlModule
extends SimpleModule {
    private static final long serialVersionUID = 1L;
    private final boolean encodeThreadContextAsList;
    private final boolean includeStacktrace;

    Log4jYamlModule(boolean bl, boolean bl2) {
        super(Log4jYamlModule.class.getName(), new Version(2, 0, 0, null, null, null));
        this.encodeThreadContextAsList = bl;
        this.includeStacktrace = bl2;
        new Initializers.SimpleModuleInitializer().initialize(this);
    }

    @Override
    public void setupModule(Module.SetupContext setupContext) {
        super.setupModule(setupContext);
        if (this.encodeThreadContextAsList) {
            new Initializers.SetupContextInitializer().setupModule(setupContext, this.includeStacktrace);
        } else {
            new Initializers.SetupContextJsonInitializer().setupModule(setupContext, this.includeStacktrace);
        }
    }
}

