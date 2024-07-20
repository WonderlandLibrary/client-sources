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

        void setupModule(Module.SetupContext context, boolean includeStacktrace) {
            context.setMixInAnnotations(StackTraceElement.class, StackTraceElementMixIn.class);
            context.setMixInAnnotations(Marker.class, MarkerMixIn.class);
            context.setMixInAnnotations(Level.class, LevelMixIn.class);
            context.setMixInAnnotations(LogEvent.class, LogEventJsonMixIn.class);
            context.setMixInAnnotations(ExtendedStackTraceElement.class, ExtendedStackTraceElementMixIn.class);
            context.setMixInAnnotations(ThrowableProxy.class, includeStacktrace ? ThrowableProxyMixIn.class : ThrowableProxyWithoutStacktraceMixIn.class);
        }
    }

    static class SetupContextInitializer {
        SetupContextInitializer() {
        }

        void setupModule(Module.SetupContext context, boolean includeStacktrace) {
            context.setMixInAnnotations(StackTraceElement.class, StackTraceElementMixIn.class);
            context.setMixInAnnotations(Marker.class, MarkerMixIn.class);
            context.setMixInAnnotations(Level.class, LevelMixIn.class);
            context.setMixInAnnotations(LogEvent.class, LogEventWithContextListMixIn.class);
            context.setMixInAnnotations(ExtendedStackTraceElement.class, ExtendedStackTraceElementMixIn.class);
            context.setMixInAnnotations(ThrowableProxy.class, includeStacktrace ? ThrowableProxyMixIn.class : ThrowableProxyWithoutStacktraceMixIn.class);
        }
    }
}

