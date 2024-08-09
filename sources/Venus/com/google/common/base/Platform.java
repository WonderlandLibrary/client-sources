/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.CharMatcher;
import com.google.common.base.CommonPattern;
import com.google.common.base.Enums;
import com.google.common.base.JdkPattern;
import com.google.common.base.Optional;
import com.google.common.base.PatternCompiler;
import com.google.common.base.Preconditions;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Locale;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

@GwtCompatible(emulated=true)
final class Platform {
    private static final Logger logger = Logger.getLogger(Platform.class.getName());
    private static final PatternCompiler patternCompiler = Platform.loadPatternCompiler();

    private Platform() {
    }

    static long systemNanoTime() {
        return System.nanoTime();
    }

    static CharMatcher precomputeCharMatcher(CharMatcher charMatcher) {
        return charMatcher.precomputedInternal();
    }

    static <T extends Enum<T>> Optional<T> getEnumIfPresent(Class<T> clazz, String string) {
        WeakReference<Enum<?>> weakReference = Enums.getEnumConstants(clazz).get(string);
        return weakReference == null ? Optional.absent() : Optional.of(clazz.cast(weakReference.get()));
    }

    static String formatCompact4Digits(double d) {
        return String.format(Locale.ROOT, "%.4g", d);
    }

    static boolean stringIsNullOrEmpty(@Nullable String string) {
        return string == null || string.isEmpty();
    }

    static CommonPattern compilePattern(String string) {
        Preconditions.checkNotNull(string);
        return patternCompiler.compile(string);
    }

    static boolean usingJdkPatternCompiler() {
        return patternCompiler instanceof JdkPatternCompiler;
    }

    private static PatternCompiler loadPatternCompiler() {
        ServiceLoader<PatternCompiler> serviceLoader = ServiceLoader.load(PatternCompiler.class);
        try {
            Iterator<PatternCompiler> iterator2 = serviceLoader.iterator();
            while (iterator2.hasNext()) {
                try {
                    return iterator2.next();
                } catch (ServiceConfigurationError serviceConfigurationError) {
                    Platform.logPatternCompilerError(serviceConfigurationError);
                }
            }
        } catch (ServiceConfigurationError serviceConfigurationError) {
            Platform.logPatternCompilerError(serviceConfigurationError);
        }
        return new JdkPatternCompiler(null);
    }

    private static void logPatternCompilerError(ServiceConfigurationError serviceConfigurationError) {
        logger.log(Level.WARNING, "Error loading regex compiler, falling back to next option", serviceConfigurationError);
    }

    private static final class JdkPatternCompiler
    implements PatternCompiler {
        private JdkPatternCompiler() {
        }

        @Override
        public CommonPattern compile(String string) {
            return new JdkPattern(Pattern.compile(string));
        }

        JdkPatternCompiler(1 var1_1) {
            this();
        }
    }
}

