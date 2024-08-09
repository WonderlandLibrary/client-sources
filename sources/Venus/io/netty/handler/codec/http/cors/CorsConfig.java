/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.cors;

import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.EmptyHttpHeaders;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.cors.CorsConfigBuilder;
import io.netty.util.internal.StringUtil;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

public final class CorsConfig {
    private final Set<String> origins;
    private final boolean anyOrigin;
    private final boolean enabled;
    private final Set<String> exposeHeaders;
    private final boolean allowCredentials;
    private final long maxAge;
    private final Set<HttpMethod> allowedRequestMethods;
    private final Set<String> allowedRequestHeaders;
    private final boolean allowNullOrigin;
    private final Map<CharSequence, Callable<?>> preflightHeaders;
    private final boolean shortCircuit;

    CorsConfig(CorsConfigBuilder corsConfigBuilder) {
        this.origins = new LinkedHashSet<String>(corsConfigBuilder.origins);
        this.anyOrigin = corsConfigBuilder.anyOrigin;
        this.enabled = corsConfigBuilder.enabled;
        this.exposeHeaders = corsConfigBuilder.exposeHeaders;
        this.allowCredentials = corsConfigBuilder.allowCredentials;
        this.maxAge = corsConfigBuilder.maxAge;
        this.allowedRequestMethods = corsConfigBuilder.requestMethods;
        this.allowedRequestHeaders = corsConfigBuilder.requestHeaders;
        this.allowNullOrigin = corsConfigBuilder.allowNullOrigin;
        this.preflightHeaders = corsConfigBuilder.preflightHeaders;
        this.shortCircuit = corsConfigBuilder.shortCircuit;
    }

    public boolean isCorsSupportEnabled() {
        return this.enabled;
    }

    public boolean isAnyOriginSupported() {
        return this.anyOrigin;
    }

    public String origin() {
        return this.origins.isEmpty() ? "*" : this.origins.iterator().next();
    }

    public Set<String> origins() {
        return this.origins;
    }

    public boolean isNullOriginAllowed() {
        return this.allowNullOrigin;
    }

    public Set<String> exposedHeaders() {
        return Collections.unmodifiableSet(this.exposeHeaders);
    }

    public boolean isCredentialsAllowed() {
        return this.allowCredentials;
    }

    public long maxAge() {
        return this.maxAge;
    }

    public Set<HttpMethod> allowedRequestMethods() {
        return Collections.unmodifiableSet(this.allowedRequestMethods);
    }

    public Set<String> allowedRequestHeaders() {
        return Collections.unmodifiableSet(this.allowedRequestHeaders);
    }

    public HttpHeaders preflightResponseHeaders() {
        if (this.preflightHeaders.isEmpty()) {
            return EmptyHttpHeaders.INSTANCE;
        }
        DefaultHttpHeaders defaultHttpHeaders = new DefaultHttpHeaders();
        for (Map.Entry<CharSequence, Callable<?>> entry : this.preflightHeaders.entrySet()) {
            Object obj = CorsConfig.getValue(entry.getValue());
            if (obj instanceof Iterable) {
                ((HttpHeaders)defaultHttpHeaders).add(entry.getKey(), (Iterable)obj);
                continue;
            }
            ((HttpHeaders)defaultHttpHeaders).add(entry.getKey(), obj);
        }
        return defaultHttpHeaders;
    }

    public boolean isShortCircuit() {
        return this.shortCircuit;
    }

    @Deprecated
    public boolean isShortCurcuit() {
        return this.isShortCircuit();
    }

    private static <T> T getValue(Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception exception) {
            throw new IllegalStateException("Could not generate value for callable [" + callable + ']', exception);
        }
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + "[enabled=" + this.enabled + ", origins=" + this.origins + ", anyOrigin=" + this.anyOrigin + ", exposedHeaders=" + this.exposeHeaders + ", isCredentialsAllowed=" + this.allowCredentials + ", maxAge=" + this.maxAge + ", allowedRequestMethods=" + this.allowedRequestMethods + ", allowedRequestHeaders=" + this.allowedRequestHeaders + ", preflightHeaders=" + this.preflightHeaders + ']';
    }

    @Deprecated
    public static Builder withAnyOrigin() {
        return new Builder();
    }

    @Deprecated
    public static Builder withOrigin(String string) {
        if ("*".equals(string)) {
            return new Builder();
        }
        return new Builder(string);
    }

    @Deprecated
    public static Builder withOrigins(String ... stringArray) {
        return new Builder(stringArray);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    @Deprecated
    public static final class DateValueGenerator
    implements Callable<Date> {
        @Override
        public Date call() throws Exception {
            return new Date();
        }

        @Override
        public Object call() throws Exception {
            return this.call();
        }
    }

    @Deprecated
    public static class Builder {
        private final CorsConfigBuilder builder;

        @Deprecated
        public Builder(String ... stringArray) {
            this.builder = new CorsConfigBuilder(stringArray);
        }

        @Deprecated
        public Builder() {
            this.builder = new CorsConfigBuilder();
        }

        @Deprecated
        public Builder allowNullOrigin() {
            this.builder.allowNullOrigin();
            return this;
        }

        @Deprecated
        public Builder disable() {
            this.builder.disable();
            return this;
        }

        @Deprecated
        public Builder exposeHeaders(String ... stringArray) {
            this.builder.exposeHeaders(stringArray);
            return this;
        }

        @Deprecated
        public Builder allowCredentials() {
            this.builder.allowCredentials();
            return this;
        }

        @Deprecated
        public Builder maxAge(long l) {
            this.builder.maxAge(l);
            return this;
        }

        @Deprecated
        public Builder allowedRequestMethods(HttpMethod ... httpMethodArray) {
            this.builder.allowedRequestMethods(httpMethodArray);
            return this;
        }

        @Deprecated
        public Builder allowedRequestHeaders(String ... stringArray) {
            this.builder.allowedRequestHeaders(stringArray);
            return this;
        }

        @Deprecated
        public Builder preflightResponseHeader(CharSequence charSequence, Object ... objectArray) {
            this.builder.preflightResponseHeader(charSequence, objectArray);
            return this;
        }

        @Deprecated
        public <T> Builder preflightResponseHeader(CharSequence charSequence, Iterable<T> iterable) {
            this.builder.preflightResponseHeader(charSequence, iterable);
            return this;
        }

        @Deprecated
        public <T> Builder preflightResponseHeader(String string, Callable<T> callable) {
            this.builder.preflightResponseHeader((CharSequence)string, callable);
            return this;
        }

        @Deprecated
        public Builder noPreflightResponseHeaders() {
            this.builder.noPreflightResponseHeaders();
            return this;
        }

        @Deprecated
        public CorsConfig build() {
            return this.builder.build();
        }

        @Deprecated
        public Builder shortCurcuit() {
            this.builder.shortCircuit();
            return this;
        }
    }
}

