/*     */ package io.netty.bootstrap;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.DefaultChannelPromise;
/*     */ import io.netty.channel.EventLoopGroup;
/*     */ import io.netty.util.AttributeKey;
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.concurrent.GlobalEventExecutor;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
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
/*     */ public abstract class AbstractBootstrap<B extends AbstractBootstrap<B, C>, C extends Channel>
/*     */   implements Cloneable
/*     */ {
/*     */   private volatile EventLoopGroup group;
/*     */   private volatile ChannelFactory<? extends C> channelFactory;
/*     */   private volatile SocketAddress localAddress;
/*  52 */   private final Map<ChannelOption<?>, Object> options = new LinkedHashMap<ChannelOption<?>, Object>();
/*  53 */   private final Map<AttributeKey<?>, Object> attrs = new LinkedHashMap<AttributeKey<?>, Object>();
/*     */ 
/*     */   
/*     */   private volatile ChannelHandler handler;
/*     */ 
/*     */ 
/*     */   
/*     */   AbstractBootstrap(AbstractBootstrap<B, C> bootstrap) {
/*  61 */     this.group = bootstrap.group;
/*  62 */     this.channelFactory = bootstrap.channelFactory;
/*  63 */     this.handler = bootstrap.handler;
/*  64 */     this.localAddress = bootstrap.localAddress;
/*  65 */     synchronized (bootstrap.options) {
/*  66 */       this.options.putAll(bootstrap.options);
/*     */     } 
/*  68 */     synchronized (bootstrap.attrs) {
/*  69 */       this.attrs.putAll(bootstrap.attrs);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public B group(EventLoopGroup group) {
/*  79 */     if (group == null) {
/*  80 */       throw new NullPointerException("group");
/*     */     }
/*  82 */     if (this.group != null) {
/*  83 */       throw new IllegalStateException("group set already");
/*     */     }
/*  85 */     this.group = group;
/*  86 */     return (B)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public B channel(Class<? extends C> channelClass) {
/*  95 */     if (channelClass == null) {
/*  96 */       throw new NullPointerException("channelClass");
/*     */     }
/*  98 */     return channelFactory(new BootstrapChannelFactory<C>(channelClass));
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
/*     */   public B channelFactory(ChannelFactory<? extends C> channelFactory) {
/* 110 */     if (channelFactory == null) {
/* 111 */       throw new NullPointerException("channelFactory");
/*     */     }
/* 113 */     if (this.channelFactory != null) {
/* 114 */       throw new IllegalStateException("channelFactory set already");
/*     */     }
/*     */     
/* 117 */     this.channelFactory = channelFactory;
/* 118 */     return (B)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public B localAddress(SocketAddress localAddress) {
/* 127 */     this.localAddress = localAddress;
/* 128 */     return (B)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public B localAddress(int inetPort) {
/* 135 */     return localAddress(new InetSocketAddress(inetPort));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public B localAddress(String inetHost, int inetPort) {
/* 142 */     return localAddress(new InetSocketAddress(inetHost, inetPort));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public B localAddress(InetAddress inetHost, int inetPort) {
/* 149 */     return localAddress(new InetSocketAddress(inetHost, inetPort));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> B option(ChannelOption<T> option, T value) {
/* 158 */     if (option == null) {
/* 159 */       throw new NullPointerException("option");
/*     */     }
/* 161 */     if (value == null) {
/* 162 */       synchronized (this.options) {
/* 163 */         this.options.remove(option);
/*     */       } 
/*     */     } else {
/* 166 */       synchronized (this.options) {
/* 167 */         this.options.put(option, value);
/*     */       } 
/*     */     } 
/* 170 */     return (B)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> B attr(AttributeKey<T> key, T value) {
/* 178 */     if (key == null) {
/* 179 */       throw new NullPointerException("key");
/*     */     }
/* 181 */     if (value == null) {
/* 182 */       synchronized (this.attrs) {
/* 183 */         this.attrs.remove(key);
/*     */       } 
/*     */     } else {
/* 186 */       synchronized (this.attrs) {
/* 187 */         this.attrs.put(key, value);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 192 */     return (B)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public B validate() {
/* 202 */     if (this.group == null) {
/* 203 */       throw new IllegalStateException("group not set");
/*     */     }
/* 205 */     if (this.channelFactory == null) {
/* 206 */       throw new IllegalStateException("channel or channelFactory not set");
/*     */     }
/* 208 */     return (B)this;
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
/*     */   public ChannelFuture register() {
/* 224 */     validate();
/* 225 */     return initAndRegister();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture bind() {
/* 232 */     validate();
/* 233 */     SocketAddress localAddress = this.localAddress;
/* 234 */     if (localAddress == null) {
/* 235 */       throw new IllegalStateException("localAddress not set");
/*     */     }
/* 237 */     return doBind(localAddress);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture bind(int inetPort) {
/* 244 */     return bind(new InetSocketAddress(inetPort));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture bind(String inetHost, int inetPort) {
/* 251 */     return bind(new InetSocketAddress(inetHost, inetPort));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture bind(InetAddress inetHost, int inetPort) {
/* 258 */     return bind(new InetSocketAddress(inetHost, inetPort));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture bind(SocketAddress localAddress) {
/* 265 */     validate();
/* 266 */     if (localAddress == null) {
/* 267 */       throw new NullPointerException("localAddress");
/*     */     }
/* 269 */     return doBind(localAddress);
/*     */   }
/*     */   private ChannelFuture doBind(final SocketAddress localAddress) {
/*     */     final PendingRegistrationPromise promise;
/* 273 */     final ChannelFuture regFuture = initAndRegister();
/* 274 */     final Channel channel = regFuture.channel();
/* 275 */     if (regFuture.cause() != null) {
/* 276 */       return regFuture;
/*     */     }
/*     */ 
/*     */     
/* 280 */     if (regFuture.isDone()) {
/* 281 */       ChannelPromise promise = channel.newPromise();
/* 282 */       doBind0(regFuture, channel, localAddress, promise);
/*     */     } else {
/*     */       
/* 285 */       pendingRegistrationPromise = new PendingRegistrationPromise(channel);
/* 286 */       regFuture.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */           {
/*     */             public void operationComplete(ChannelFuture future) throws Exception {
/* 289 */               AbstractBootstrap.doBind0(regFuture, channel, localAddress, promise);
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 294 */     return (ChannelFuture)pendingRegistrationPromise;
/*     */   }
/*     */   
/*     */   final ChannelFuture initAndRegister() {
/* 298 */     Channel channel = (Channel)channelFactory().newChannel();
/*     */     try {
/* 300 */       init(channel);
/* 301 */     } catch (Throwable t) {
/* 302 */       channel.unsafe().closeForcibly();
/*     */       
/* 304 */       return (ChannelFuture)(new DefaultChannelPromise(channel, (EventExecutor)GlobalEventExecutor.INSTANCE)).setFailure(t);
/*     */     } 
/*     */     
/* 307 */     ChannelFuture regFuture = group().register(channel);
/* 308 */     if (regFuture.cause() != null) {
/* 309 */       if (channel.isRegistered()) {
/* 310 */         channel.close();
/*     */       } else {
/* 312 */         channel.unsafe().closeForcibly();
/*     */       } 
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
/* 325 */     return regFuture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void doBind0(final ChannelFuture regFuture, final Channel channel, final SocketAddress localAddress, final ChannelPromise promise) {
/* 336 */     channel.eventLoop().execute(new Runnable()
/*     */         {
/*     */           public void run() {
/* 339 */             if (regFuture.isSuccess()) {
/* 340 */               channel.bind(localAddress, promise).addListener((GenericFutureListener)ChannelFutureListener.CLOSE_ON_FAILURE);
/*     */             } else {
/* 342 */               promise.setFailure(regFuture.cause());
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public B handler(ChannelHandler handler) {
/* 353 */     if (handler == null) {
/* 354 */       throw new NullPointerException("handler");
/*     */     }
/* 356 */     this.handler = handler;
/* 357 */     return (B)this;
/*     */   }
/*     */   
/*     */   final SocketAddress localAddress() {
/* 361 */     return this.localAddress;
/*     */   }
/*     */   
/*     */   final ChannelFactory<? extends C> channelFactory() {
/* 365 */     return this.channelFactory;
/*     */   }
/*     */   
/*     */   final ChannelHandler handler() {
/* 369 */     return this.handler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final EventLoopGroup group() {
/* 376 */     return this.group;
/*     */   }
/*     */   
/*     */   final Map<ChannelOption<?>, Object> options() {
/* 380 */     return this.options;
/*     */   }
/*     */   
/*     */   final Map<AttributeKey<?>, Object> attrs() {
/* 384 */     return this.attrs;
/*     */   }
/*     */   AbstractBootstrap() {}
/*     */   
/*     */   public String toString() {
/* 389 */     StringBuilder buf = new StringBuilder();
/* 390 */     buf.append(StringUtil.simpleClassName(this));
/* 391 */     buf.append('(');
/* 392 */     if (this.group != null) {
/* 393 */       buf.append("group: ");
/* 394 */       buf.append(StringUtil.simpleClassName(this.group));
/* 395 */       buf.append(", ");
/*     */     } 
/* 397 */     if (this.channelFactory != null) {
/* 398 */       buf.append("channelFactory: ");
/* 399 */       buf.append(this.channelFactory);
/* 400 */       buf.append(", ");
/*     */     } 
/* 402 */     if (this.localAddress != null) {
/* 403 */       buf.append("localAddress: ");
/* 404 */       buf.append(this.localAddress);
/* 405 */       buf.append(", ");
/*     */     } 
/* 407 */     synchronized (this.options) {
/* 408 */       if (!this.options.isEmpty()) {
/* 409 */         buf.append("options: ");
/* 410 */         buf.append(this.options);
/* 411 */         buf.append(", ");
/*     */       } 
/*     */     } 
/* 414 */     synchronized (this.attrs) {
/* 415 */       if (!this.attrs.isEmpty()) {
/* 416 */         buf.append("attrs: ");
/* 417 */         buf.append(this.attrs);
/* 418 */         buf.append(", ");
/*     */       } 
/*     */     } 
/* 421 */     if (this.handler != null) {
/* 422 */       buf.append("handler: ");
/* 423 */       buf.append(this.handler);
/* 424 */       buf.append(", ");
/*     */     } 
/* 426 */     if (buf.charAt(buf.length() - 1) == '(') {
/* 427 */       buf.append(')');
/*     */     } else {
/* 429 */       buf.setCharAt(buf.length() - 2, ')');
/* 430 */       buf.setLength(buf.length() - 1);
/*     */     } 
/* 432 */     return buf.toString();
/*     */   }
/*     */   public abstract B clone();
/*     */   
/*     */   abstract void init(Channel paramChannel) throws Exception;
/*     */   
/*     */   private static final class BootstrapChannelFactory<T extends Channel> implements ChannelFactory<T> { BootstrapChannelFactory(Class<? extends T> clazz) {
/* 439 */       this.clazz = clazz;
/*     */     }
/*     */     private final Class<? extends T> clazz;
/*     */     
/*     */     public T newChannel() {
/*     */       try {
/* 445 */         return this.clazz.newInstance();
/* 446 */       } catch (Throwable t) {
/* 447 */         throw new ChannelException("Unable to create Channel from class " + this.clazz, t);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 453 */       return StringUtil.simpleClassName(this.clazz) + ".class";
/*     */     } }
/*     */ 
/*     */   
/*     */   private static final class PendingRegistrationPromise extends DefaultChannelPromise {
/*     */     private PendingRegistrationPromise(Channel channel) {
/* 459 */       super(channel);
/*     */     }
/*     */ 
/*     */     
/*     */     protected EventExecutor executor() {
/* 464 */       if (channel().isRegistered())
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 469 */         return super.executor();
/*     */       }
/*     */       
/* 472 */       return (EventExecutor)GlobalEventExecutor.INSTANCE;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\bootstrap\AbstractBootstrap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */