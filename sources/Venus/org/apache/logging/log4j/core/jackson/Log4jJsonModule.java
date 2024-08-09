/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.logging.log4j.core.jackson.Initializers;

class Log4jJsonModule
extends SimpleModule {
    private static final long serialVersionUID = 1L;
    private final boolean encodeThreadContextAsList;
    private final boolean includeStacktrace;

    Log4jJsonModule(boolean bl, boolean bl2) {
        super(Log4jJsonModule.class.getName(), new Version(2, 0, 0, null, null, null));
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

