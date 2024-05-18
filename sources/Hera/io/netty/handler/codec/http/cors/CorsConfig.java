/*     */ package io.netty.handler.codec.http.cors;
/*     */ 
/*     */ import io.netty.handler.codec.http.DefaultHttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpMethod;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Callable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CorsConfig
/*     */ {
/*     */   private final Set<String> origins;
/*     */   private final boolean anyOrigin;
/*     */   private final boolean enabled;
/*     */   private final Set<String> exposeHeaders;
/*     */   private final boolean allowCredentials;
/*     */   private final long maxAge;
/*     */   private final Set<HttpMethod> allowedRequestMethods;
/*     */   private final Set<String> allowedRequestHeaders;
/*     */   private final boolean allowNullOrigin;
/*     */   private final Map<CharSequence, Callable<?>> preflightHeaders;
/*     */   private final boolean shortCurcuit;
/*     */   
/*     */   private CorsConfig(Builder builder) {
/*  53 */     this.origins = new LinkedHashSet<String>(builder.origins);
/*  54 */     this.anyOrigin = builder.anyOrigin;
/*  55 */     this.enabled = builder.enabled;
/*  56 */     this.exposeHeaders = builder.exposeHeaders;
/*  57 */     this.allowCredentials = builder.allowCredentials;
/*  58 */     this.maxAge = builder.maxAge;
/*  59 */     this.allowedRequestMethods = builder.requestMethods;
/*  60 */     this.allowedRequestHeaders = builder.requestHeaders;
/*  61 */     this.allowNullOrigin = builder.allowNullOrigin;
/*  62 */     this.preflightHeaders = builder.preflightHeaders;
/*  63 */     this.shortCurcuit = builder.shortCurcuit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCorsSupportEnabled() {
/*  72 */     return this.enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAnyOriginSupported() {
/*  81 */     return this.anyOrigin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String origin() {
/*  90 */     return this.origins.isEmpty() ? "*" : this.origins.iterator().next();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> origins() {
/*  99 */     return this.origins;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNullOriginAllowed() {
/* 112 */     return this.allowNullOrigin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> exposedHeaders() {
/* 138 */     return Collections.unmodifiableSet(this.exposeHeaders);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCredentialsAllowed() {
/* 159 */     return this.allowCredentials;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long maxAge() {
/* 173 */     return this.maxAge;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<HttpMethod> allowedRequestMethods() {
/* 183 */     return Collections.unmodifiableSet(this.allowedRequestMethods);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> allowedRequestHeaders() {
/* 195 */     return Collections.unmodifiableSet(this.allowedRequestHeaders);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpHeaders preflightResponseHeaders() {
/* 204 */     if (this.preflightHeaders.isEmpty()) {
/* 205 */       return HttpHeaders.EMPTY_HEADERS;
/*     */     }
/* 207 */     DefaultHttpHeaders defaultHttpHeaders = new DefaultHttpHeaders();
/* 208 */     for (Map.Entry<CharSequence, Callable<?>> entry : this.preflightHeaders.entrySet()) {
/* 209 */       Object value = getValue(entry.getValue());
/* 210 */       if (value instanceof Iterable) {
/* 211 */         defaultHttpHeaders.add(entry.getKey(), (Iterable)value); continue;
/*     */       } 
/* 213 */       defaultHttpHeaders.add(entry.getKey(), value);
/*     */     } 
/*     */     
/* 216 */     return (HttpHeaders)defaultHttpHeaders;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isShortCurcuit() {
/* 230 */     return this.shortCurcuit;
/*     */   }
/*     */   
/*     */   private static <T> T getValue(Callable<T> callable) {
/*     */     try {
/* 235 */       return callable.call();
/* 236 */     } catch (Exception e) {
/* 237 */       throw new IllegalStateException("Could not generate value for callable [" + callable + ']', e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 243 */     return StringUtil.simpleClassName(this) + "[enabled=" + this.enabled + ", origins=" + this.origins + ", anyOrigin=" + this.anyOrigin + ", exposedHeaders=" + this.exposeHeaders + ", isCredentialsAllowed=" + this.allowCredentials + ", maxAge=" + this.maxAge + ", allowedRequestMethods=" + this.allowedRequestMethods + ", allowedRequestHeaders=" + this.allowedRequestHeaders + ", preflightHeaders=" + this.preflightHeaders + ']';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Builder withAnyOrigin() {
/* 260 */     return new Builder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Builder withOrigin(String origin) {
/* 269 */     if ("*".equals(origin)) {
/* 270 */       return new Builder();
/*     */     }
/* 272 */     return new Builder(new String[] { origin });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Builder withOrigins(String... origins) {
/* 281 */     return new Builder(origins);
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Builder
/*     */   {
/*     */     private final Set<String> origins;
/*     */     
/*     */     private final boolean anyOrigin;
/*     */     
/*     */     private boolean allowNullOrigin;
/*     */     private boolean enabled = true;
/*     */     private boolean allowCredentials;
/* 294 */     private final Set<String> exposeHeaders = new HashSet<String>();
/*     */     private long maxAge;
/* 296 */     private final Set<HttpMethod> requestMethods = new HashSet<HttpMethod>();
/* 297 */     private final Set<String> requestHeaders = new HashSet<String>();
/* 298 */     private final Map<CharSequence, Callable<?>> preflightHeaders = new HashMap<CharSequence, Callable<?>>();
/*     */ 
/*     */     
/*     */     private boolean noPreflightHeaders;
/*     */ 
/*     */     
/*     */     private boolean shortCurcuit;
/*     */ 
/*     */     
/*     */     public Builder(String... origins) {
/* 308 */       this.origins = new LinkedHashSet<String>(Arrays.asList(origins));
/* 309 */       this.anyOrigin = false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder() {
/* 318 */       this.anyOrigin = true;
/* 319 */       this.origins = Collections.emptySet();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder allowNullOrigin() {
/* 330 */       this.allowNullOrigin = true;
/* 331 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder disable() {
/* 340 */       this.enabled = false;
/* 341 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder exposeHeaders(String... headers) {
/* 370 */       this.exposeHeaders.addAll(Arrays.asList(headers));
/* 371 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder allowCredentials() {
/* 390 */       this.allowCredentials = true;
/* 391 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder maxAge(long max) {
/* 404 */       this.maxAge = max;
/* 405 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder allowedRequestMethods(HttpMethod... methods) {
/* 416 */       this.requestMethods.addAll(Arrays.asList(methods));
/* 417 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder allowedRequestHeaders(String... headers) {
/* 437 */       this.requestHeaders.addAll(Arrays.asList(headers));
/* 438 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder preflightResponseHeader(CharSequence name, Object... values) {
/* 452 */       if (values.length == 1) {
/* 453 */         this.preflightHeaders.put(name, new CorsConfig.ConstantValueGenerator(values[0]));
/*     */       } else {
/* 455 */         preflightResponseHeader(name, Arrays.asList(values));
/*     */       } 
/* 457 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <T> Builder preflightResponseHeader(CharSequence name, Iterable<T> value) {
/* 472 */       this.preflightHeaders.put(name, new CorsConfig.ConstantValueGenerator(value));
/* 473 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <T> Builder preflightResponseHeader(String name, Callable<T> valueGenerator) {
/* 492 */       this.preflightHeaders.put(name, valueGenerator);
/* 493 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder noPreflightResponseHeaders() {
/* 502 */       this.noPreflightHeaders = true;
/* 503 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public CorsConfig build() {
/* 512 */       if (this.preflightHeaders.isEmpty() && !this.noPreflightHeaders) {
/* 513 */         this.preflightHeaders.put("Date", new CorsConfig.DateValueGenerator());
/* 514 */         this.preflightHeaders.put("Content-Length", new CorsConfig.ConstantValueGenerator("0"));
/*     */       } 
/* 516 */       return new CorsConfig(this);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder shortCurcuit() {
/* 530 */       this.shortCurcuit = true;
/* 531 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class ConstantValueGenerator
/*     */     implements Callable<Object>
/*     */   {
/*     */     private final Object value;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private ConstantValueGenerator(Object value) {
/* 550 */       if (value == null) {
/* 551 */         throw new IllegalArgumentException("value must not be null");
/*     */       }
/* 553 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object call() {
/* 558 */       return this.value;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class DateValueGenerator
/*     */     implements Callable<Date>
/*     */   {
/*     */     public Date call() throws Exception {
/* 571 */       return new Date();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\cors\CorsConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */