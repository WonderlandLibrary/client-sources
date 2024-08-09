/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.jackson;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.impl.ExtendedStackTraceElement;
import org.apache.logging.log4j.core.impl.ThrowableProxy;
import org.apache.logging.log4j.core.jackson.ExtendedStackTraceElementMixIn;
import org.apache.logging.log4j.core.jackson.LevelMixIn;
import org.apache.logging.log4j.core.jackson.Log4jStackTraceElementDeserializer;
import org.apache.logging.log4j.core.jackson.LogEventJsonMixIn;
import org.apache.logging.log4j.core.jackson.LogEventWithContextListMixIn;
import org.apache.logging.log4j.core.jackson.MarkerMixIn;
import org.apache.logging.log4j.core.jackson.MutableThreadContextStackDeserializer;
import org.apache.logging.log4j.core.jackson.StackTraceElementMixIn;
import org.apache.logging.log4j.core.jackson.ThrowableProxyMixIn;
import org.apache.logging.log4j.core.jackson.ThrowableProxyWithoutStacktraceMixIn;

class Initializers {
    Initializers() {
    }

    static class SimpleModuleInitializer {
        SimpleModuleInitializer() {
        }

        void initialize(SimpleModule simpleModule) {
            simpleModule.addDeserializer(StackTraceElement.class, new Log4jStackTraceElementDeserializer());
            simpleModule.addDeserializer(ThreadContext.ContextStack.class, new MutableThreadContextStackDeserializer());
        }
    }

    static class SetupContextJsonInitializer {
        SetupContextJsonInitializer() {
        }

        void setupModule(Module.SetupContext setupContext, boolean bl) {
            setupContext.setMixInAnnotations(StackTraceElement.class, StackTraceElementMixIn.class);
            setupContext.setMixInAnnotations(Marker.class, MarkerMixIn.class);
            setupContext.setMixInAnnotations(Level.class, LevelMixIn.class);
            setupContext.setMixInAnnotations(LogEvent.class, LogEventJsonMixIn.class);
            setupContext.setMixInAnnotations(ExtendedStackTraceElement.class, ExtendedStackTraceElementMixIn.class);
            setupContext.setMixInAnnotations(ThrowableProxy.class, bl ? ThrowableProxyMixIn.class : ThrowableProxyWithoutStacktraceMixIn.class);
        }
    }

    static class SetupContextInitializer {
        SetupContextInitializer() {
        }

        void setupModule(Module.SetupContext setupContext, boolean bl) {
            setupContext.setMixInAnnotations(StackTraceElement.class, StackTraceElementMixIn.class);
            setupContext.setMixInAnnotations(Marker.class, MarkerMixIn.class);
            setupContext.setMixInAnnotations(Level.class, LevelMixIn.class);
            setupContext.setMixInAnnotations(LogEvent.class, LogEventWithContextListMixIn.class);
            setupContext.setMixInAnnotations(ExtendedStackTraceElement.class, ExtendedStackTraceElementMixIn.class);
            setupContext.setMixInAnnotations(ThrowableProxy.class, bl ? ThrowableProxyMixIn.class : ThrowableProxyWithoutStacktraceMixIn.class);
        }
    }
}

