/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
 */
package org.apache.logging.log4j.core.jackson;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import org.apache.logging.log4j.core.jackson.Initializers;

final class Log4jXmlModule
extends JacksonXmlModule {
    private static final long serialVersionUID = 1L;
    private final boolean includeStacktrace;

    Log4jXmlModule(boolean includeStacktrace) {
        this.includeStacktrace = includeStacktrace;
        new Initializers.SimpleModuleInitializer().initialize((SimpleModule)((Object)this));
    }

    public void setupModule(Module.SetupContext context) {
        super.setupModule(context);
        new Initializers.SetupContextInitializer().setupModule(context, this.includeStacktrace);
    }
}

