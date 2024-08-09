/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.cors;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.cors.CorsConfig;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

public final class CorsConfigBuilder {
    final Set<String> origins;
    final boolean anyOrigin;
    boolean allowNullOrigin;
    boolean enabled = true;
    boolean allowCredentials;
    final Set<String> exposeHeaders = new HashSet<String>();
    long maxAge;
    final Set<HttpMethod> requestMethods = new HashSet<HttpMethod>();
    final Set<String> requestHeaders = new HashSet<String>();
    final Map<CharSequence, Callable<?>> preflightHeaders = new HashMap();
    private boolean noPreflightHeaders;
    boolean shortCircuit;

    public static CorsConfigBuilder forAnyOrigin() {
        return new CorsConfigBuilder();
    }

    public static CorsConfigBuilder forOrigin(String string) {
        if ("*".equals(string)) {
            return new CorsConfigBuilder();
        }
        return new CorsConfigBuilder(string);
    }

    public static CorsConfigBuilder forOrigins(String ... stringArray) {
        return new CorsConfigBuilder(stringArray);
    }

    CorsConfigBuilder(String ... stringArray) {
        this.origins = new LinkedHashSet<String>(Arrays.asList(stringArray));
        this.anyOrigin = false;
    }

    CorsConfigBuilder() {
        this.anyOrigin = true;
        this.origins = Collections.emptySet();
    }

    public CorsConfigBuilder allowNullOrigin() {
        this.allowNullOrigin = true;
        return this;
    }

    public CorsConfigBuilder disable() {
        this.enabled = false;
        return this;
    }

    public CorsConfigBuilder exposeHeaders(String ... stringArray) {
        this.exposeHeaders.addAll(Arrays.asList(stringArray));
        return this;
    }

    public CorsConfigBuilder exposeHeaders(CharSequence ... charSequenceArray) {
        for (CharSequence charSequence : charSequenceArray) {
            this.exposeHeaders.add(charSequence.toString());
        }
        return this;
    }

    public CorsConfigBuilder allowCredentials() {
        this.allowCredentials = true;
        return this;
    }

    public CorsConfigBuilder maxAge(long l) {
        this.maxAge = l;
        return this;
    }

    public CorsConfigBuilder allowedRequestMethods(HttpMethod ... httpMethodArray) {
        this.requestMethods.addAll(Arrays.asList(httpMethodArray));
        return this;
    }

    public CorsConfigBuilder allowedRequestHeaders(String ... stringArray) {
        this.requestHeaders.addAll(Arrays.asList(stringArray));
        return this;
    }

    public CorsConfigBuilder allowedRequestHeaders(CharSequence ... charSequenceArray) {
        for (CharSequence charSequence : charSequenceArray) {
            this.requestHeaders.add(charSequence.toString());
        }
        return this;
    }

    public CorsConfigBuilder preflightResponseHeader(CharSequence charSequence, Object ... objectArray) {
        if (objectArray.length == 1) {
            this.preflightHeaders.put(charSequence, new ConstantValueGenerator(objectArray[0], null));
        } else {
            this.preflightResponseHeader(charSequence, Arrays.asList(objectArray));
        }
        return this;
    }

    public <T> CorsConfigBuilder preflightResponseHeader(CharSequence charSequence, Iterable<T> iterable) {
        this.preflightHeaders.put(charSequence, new ConstantValueGenerator(iterable, null));
        return this;
    }

    public <T> CorsConfigBuilder preflightResponseHeader(CharSequence charSequence, Callable<T> callable) {
        this.preflightHeaders.put(charSequence, callable);
        return this;
    }

    public CorsConfigBuilder noPreflightResponseHeaders() {
        this.noPreflightHeaders = true;
        return this;
    }

    public CorsConfigBuilder shortCircuit() {
        this.shortCircuit = true;
        return this;
    }

    public CorsConfig build() {
        if (this.preflightHeaders.isEmpty() && !this.noPreflightHeaders) {
            this.preflightHeaders.put(HttpHeaderNames.DATE, DateValueGenerator.INSTANCE);
            this.preflightHeaders.put(HttpHeaderNames.CONTENT_LENGTH, new ConstantValueGenerator("0", null));
        }
        return new CorsConfig(this);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static final class DateValueGenerator
    implements Callable<Date> {
        static final DateValueGenerator INSTANCE = new DateValueGenerator();

        private DateValueGenerator() {
        }

        @Override
        public Date call() throws Exception {
            return new Date();
        }

        @Override
        public Object call() throws Exception {
            return this.call();
        }
    }

    private static final class ConstantValueGenerator
    implements Callable<Object> {
        private final Object value;

        private ConstantValueGenerator(Object object) {
            if (object == null) {
                throw new IllegalArgumentException("value must not be null");
            }
            this.value = object;
        }

        @Override
        public Object call() {
            return this.value;
        }

        ConstantValueGenerator(Object object, 1 var2_2) {
            this(object);
        }
    }
}

