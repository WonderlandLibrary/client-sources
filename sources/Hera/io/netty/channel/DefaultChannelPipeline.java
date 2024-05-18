/*      */ package io.netty.channel;
/*      */ 
/*      */ import io.netty.util.ReferenceCountUtil;
/*      */ import io.netty.util.concurrent.EventExecutor;
/*      */ import io.netty.util.concurrent.EventExecutorGroup;
/*      */ import io.netty.util.concurrent.Future;
/*      */ import io.netty.util.internal.PlatformDependent;
/*      */ import io.netty.util.internal.StringUtil;
/*      */ import io.netty.util.internal.logging.InternalLogger;
/*      */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*      */ import java.net.SocketAddress;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.IdentityHashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.WeakHashMap;
/*      */ import java.util.concurrent.ExecutionException;
/*      */ import java.util.concurrent.Future;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ final class DefaultChannelPipeline
/*      */   implements ChannelPipeline
/*      */ {
/*   46 */   static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultChannelPipeline.class);
/*      */ 
/*      */   
/*   49 */   private static final WeakHashMap<Class<?>, String>[] nameCaches = (WeakHashMap<Class<?>, String>[])new WeakHashMap[Runtime.getRuntime().availableProcessors()];
/*      */   final AbstractChannel channel;
/*      */   
/*      */   static {
/*   53 */     for (int i = 0; i < nameCaches.length; i++) {
/*   54 */       nameCaches[i] = new WeakHashMap<Class<?>, String>();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   final AbstractChannelHandlerContext head;
/*      */   
/*      */   final AbstractChannelHandlerContext tail;
/*      */   
/*   63 */   private final Map<String, AbstractChannelHandlerContext> name2ctx = new HashMap<String, AbstractChannelHandlerContext>(4);
/*      */ 
/*      */   
/*   66 */   final Map<EventExecutorGroup, EventExecutor> childExecutors = new IdentityHashMap<EventExecutorGroup, EventExecutor>();
/*      */ 
/*      */   
/*      */   public DefaultChannelPipeline(AbstractChannel channel) {
/*   70 */     if (channel == null) {
/*   71 */       throw new NullPointerException("channel");
/*      */     }
/*   73 */     this.channel = channel;
/*      */     
/*   75 */     this.tail = new TailContext(this);
/*   76 */     this.head = new HeadContext(this);
/*      */     
/*   78 */     this.head.next = this.tail;
/*   79 */     this.tail.prev = this.head;
/*      */   }
/*      */ 
/*      */   
/*      */   public Channel channel() {
/*   84 */     return this.channel;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline addFirst(String name, ChannelHandler handler) {
/*   89 */     return addFirst(null, name, handler);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline addFirst(EventExecutorGroup group, String name, ChannelHandler handler) {
/*   94 */     synchronized (this) {
/*   95 */       checkDuplicateName(name);
/*   96 */       AbstractChannelHandlerContext newCtx = new DefaultChannelHandlerContext(this, group, name, handler);
/*   97 */       addFirst0(name, newCtx);
/*      */     } 
/*      */     
/*  100 */     return this;
/*      */   }
/*      */   
/*      */   private void addFirst0(String name, AbstractChannelHandlerContext newCtx) {
/*  104 */     checkMultiplicity(newCtx);
/*      */     
/*  106 */     AbstractChannelHandlerContext nextCtx = this.head.next;
/*  107 */     newCtx.prev = this.head;
/*  108 */     newCtx.next = nextCtx;
/*  109 */     this.head.next = newCtx;
/*  110 */     nextCtx.prev = newCtx;
/*      */     
/*  112 */     this.name2ctx.put(name, newCtx);
/*      */     
/*  114 */     callHandlerAdded(newCtx);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline addLast(String name, ChannelHandler handler) {
/*  119 */     return addLast(null, name, handler);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline addLast(EventExecutorGroup group, String name, ChannelHandler handler) {
/*  124 */     synchronized (this) {
/*  125 */       checkDuplicateName(name);
/*      */       
/*  127 */       AbstractChannelHandlerContext newCtx = new DefaultChannelHandlerContext(this, group, name, handler);
/*  128 */       addLast0(name, newCtx);
/*      */     } 
/*      */     
/*  131 */     return this;
/*      */   }
/*      */   
/*      */   private void addLast0(String name, AbstractChannelHandlerContext newCtx) {
/*  135 */     checkMultiplicity(newCtx);
/*      */     
/*  137 */     AbstractChannelHandlerContext prev = this.tail.prev;
/*  138 */     newCtx.prev = prev;
/*  139 */     newCtx.next = this.tail;
/*  140 */     prev.next = newCtx;
/*  141 */     this.tail.prev = newCtx;
/*      */     
/*  143 */     this.name2ctx.put(name, newCtx);
/*      */     
/*  145 */     callHandlerAdded(newCtx);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline addBefore(String baseName, String name, ChannelHandler handler) {
/*  150 */     return addBefore(null, baseName, name, handler);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ChannelPipeline addBefore(EventExecutorGroup group, String baseName, String name, ChannelHandler handler) {
/*  156 */     synchronized (this) {
/*  157 */       AbstractChannelHandlerContext ctx = getContextOrDie(baseName);
/*  158 */       checkDuplicateName(name);
/*  159 */       AbstractChannelHandlerContext newCtx = new DefaultChannelHandlerContext(this, group, name, handler);
/*  160 */       addBefore0(name, ctx, newCtx);
/*      */     } 
/*  162 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   private void addBefore0(String name, AbstractChannelHandlerContext ctx, AbstractChannelHandlerContext newCtx) {
/*  167 */     checkMultiplicity(newCtx);
/*      */     
/*  169 */     newCtx.prev = ctx.prev;
/*  170 */     newCtx.next = ctx;
/*  171 */     ctx.prev.next = newCtx;
/*  172 */     ctx.prev = newCtx;
/*      */     
/*  174 */     this.name2ctx.put(name, newCtx);
/*      */     
/*  176 */     callHandlerAdded(newCtx);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline addAfter(String baseName, String name, ChannelHandler handler) {
/*  181 */     return addAfter(null, baseName, name, handler);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ChannelPipeline addAfter(EventExecutorGroup group, String baseName, String name, ChannelHandler handler) {
/*  187 */     synchronized (this) {
/*  188 */       AbstractChannelHandlerContext ctx = getContextOrDie(baseName);
/*  189 */       checkDuplicateName(name);
/*  190 */       AbstractChannelHandlerContext newCtx = new DefaultChannelHandlerContext(this, group, name, handler);
/*      */       
/*  192 */       addAfter0(name, ctx, newCtx);
/*      */     } 
/*      */     
/*  195 */     return this;
/*      */   }
/*      */   
/*      */   private void addAfter0(String name, AbstractChannelHandlerContext ctx, AbstractChannelHandlerContext newCtx) {
/*  199 */     checkDuplicateName(name);
/*  200 */     checkMultiplicity(newCtx);
/*      */     
/*  202 */     newCtx.prev = ctx;
/*  203 */     newCtx.next = ctx.next;
/*  204 */     ctx.next.prev = newCtx;
/*  205 */     ctx.next = newCtx;
/*      */     
/*  207 */     this.name2ctx.put(name, newCtx);
/*      */     
/*  209 */     callHandlerAdded(newCtx);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline addFirst(ChannelHandler... handlers) {
/*  214 */     return addFirst((EventExecutorGroup)null, handlers);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline addFirst(EventExecutorGroup executor, ChannelHandler... handlers) {
/*  219 */     if (handlers == null) {
/*  220 */       throw new NullPointerException("handlers");
/*      */     }
/*  222 */     if (handlers.length == 0 || handlers[0] == null) {
/*  223 */       return this;
/*      */     }
/*      */     
/*      */     int size;
/*  227 */     for (size = 1; size < handlers.length && 
/*  228 */       handlers[size] != null; size++);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  233 */     for (int i = size - 1; i >= 0; i--) {
/*  234 */       ChannelHandler h = handlers[i];
/*  235 */       addFirst(executor, generateName(h), h);
/*      */     } 
/*      */     
/*  238 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline addLast(ChannelHandler... handlers) {
/*  243 */     return addLast((EventExecutorGroup)null, handlers);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline addLast(EventExecutorGroup executor, ChannelHandler... handlers) {
/*  248 */     if (handlers == null) {
/*  249 */       throw new NullPointerException("handlers");
/*      */     }
/*      */     
/*  252 */     for (ChannelHandler h : handlers) {
/*  253 */       if (h == null) {
/*      */         break;
/*      */       }
/*  256 */       addLast(executor, generateName(h), h);
/*      */     } 
/*      */     
/*  259 */     return this;
/*      */   }
/*      */   private String generateName(ChannelHandler handler) {
/*      */     String name;
/*  263 */     WeakHashMap<Class<?>, String> cache = nameCaches[(int)(Thread.currentThread().getId() % nameCaches.length)];
/*  264 */     Class<?> handlerType = handler.getClass();
/*      */     
/*  266 */     synchronized (cache) {
/*  267 */       name = cache.get(handlerType);
/*  268 */       if (name == null) {
/*  269 */         name = generateName0(handlerType);
/*  270 */         cache.put(handlerType, name);
/*      */       } 
/*      */     } 
/*      */     
/*  274 */     synchronized (this) {
/*      */ 
/*      */       
/*  277 */       if (this.name2ctx.containsKey(name)) {
/*  278 */         String baseName = name.substring(0, name.length() - 1);
/*  279 */         for (int i = 1;; i++) {
/*  280 */           String newName = baseName + i;
/*  281 */           if (!this.name2ctx.containsKey(newName)) {
/*  282 */             name = newName;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  289 */     return name;
/*      */   }
/*      */   
/*      */   private static String generateName0(Class<?> handlerType) {
/*  293 */     return StringUtil.simpleClassName(handlerType) + "#0";
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline remove(ChannelHandler handler) {
/*  298 */     remove(getContextOrDie(handler));
/*  299 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelHandler remove(String name) {
/*  304 */     return remove(getContextOrDie(name)).handler();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <T extends ChannelHandler> T remove(Class<T> handlerType) {
/*  310 */     return (T)remove(getContextOrDie(handlerType)).handler();
/*      */   } private AbstractChannelHandlerContext remove(final AbstractChannelHandlerContext ctx) {
/*      */     AbstractChannelHandlerContext context;
/*      */     Future future;
/*  314 */     assert ctx != this.head && ctx != this.tail;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  319 */     synchronized (this) {
/*  320 */       if (!ctx.channel().isRegistered() || ctx.executor().inEventLoop()) {
/*  321 */         remove0(ctx);
/*  322 */         return ctx;
/*      */       } 
/*  324 */       future = ctx.executor().submit(new Runnable()
/*      */           {
/*      */             public void run() {
/*  327 */               synchronized (DefaultChannelPipeline.this) {
/*  328 */                 DefaultChannelPipeline.this.remove0(ctx);
/*      */               } 
/*      */             }
/*      */           });
/*  332 */       context = ctx;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  339 */     waitForFuture((Future<?>)future);
/*      */     
/*  341 */     return context;
/*      */   }
/*      */   
/*      */   void remove0(AbstractChannelHandlerContext ctx) {
/*  345 */     AbstractChannelHandlerContext prev = ctx.prev;
/*  346 */     AbstractChannelHandlerContext next = ctx.next;
/*  347 */     prev.next = next;
/*  348 */     next.prev = prev;
/*  349 */     this.name2ctx.remove(ctx.name());
/*  350 */     callHandlerRemoved(ctx);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelHandler removeFirst() {
/*  355 */     if (this.head.next == this.tail) {
/*  356 */       throw new NoSuchElementException();
/*      */     }
/*  358 */     return remove(this.head.next).handler();
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelHandler removeLast() {
/*  363 */     if (this.head.next == this.tail) {
/*  364 */       throw new NoSuchElementException();
/*      */     }
/*  366 */     return remove(this.tail.prev).handler();
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline replace(ChannelHandler oldHandler, String newName, ChannelHandler newHandler) {
/*  371 */     replace(getContextOrDie(oldHandler), newName, newHandler);
/*  372 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelHandler replace(String oldName, String newName, ChannelHandler newHandler) {
/*  377 */     return replace(getContextOrDie(oldName), newName, newHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T extends ChannelHandler> T replace(Class<T> oldHandlerType, String newName, ChannelHandler newHandler) {
/*  384 */     return (T)replace(getContextOrDie(oldHandlerType), newName, newHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private ChannelHandler replace(final AbstractChannelHandlerContext ctx, final String newName, ChannelHandler newHandler) {
/*      */     Future future;
/*  391 */     assert ctx != this.head && ctx != this.tail;
/*      */ 
/*      */     
/*  394 */     synchronized (this) {
/*  395 */       boolean sameName = ctx.name().equals(newName);
/*  396 */       if (!sameName) {
/*  397 */         checkDuplicateName(newName);
/*      */       }
/*      */       
/*  400 */       final AbstractChannelHandlerContext newCtx = new DefaultChannelHandlerContext(this, (EventExecutorGroup)ctx.executor, newName, newHandler);
/*      */ 
/*      */       
/*  403 */       if (!newCtx.channel().isRegistered() || newCtx.executor().inEventLoop()) {
/*  404 */         replace0(ctx, newName, newCtx);
/*  405 */         return ctx.handler();
/*      */       } 
/*  407 */       future = newCtx.executor().submit(new Runnable()
/*      */           {
/*      */             public void run() {
/*  410 */               synchronized (DefaultChannelPipeline.this) {
/*  411 */                 DefaultChannelPipeline.this.replace0(ctx, newName, newCtx);
/*      */               } 
/*      */             }
/*      */           });
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  421 */     waitForFuture((Future<?>)future);
/*      */     
/*  423 */     return ctx.handler();
/*      */   }
/*      */ 
/*      */   
/*      */   private void replace0(AbstractChannelHandlerContext oldCtx, String newName, AbstractChannelHandlerContext newCtx) {
/*  428 */     checkMultiplicity(newCtx);
/*      */     
/*  430 */     AbstractChannelHandlerContext prev = oldCtx.prev;
/*  431 */     AbstractChannelHandlerContext next = oldCtx.next;
/*  432 */     newCtx.prev = prev;
/*  433 */     newCtx.next = next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  439 */     prev.next = newCtx;
/*  440 */     next.prev = newCtx;
/*      */     
/*  442 */     if (!oldCtx.name().equals(newName)) {
/*  443 */       this.name2ctx.remove(oldCtx.name());
/*      */     }
/*  445 */     this.name2ctx.put(newName, newCtx);
/*      */ 
/*      */     
/*  448 */     oldCtx.prev = newCtx;
/*  449 */     oldCtx.next = newCtx;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  454 */     callHandlerAdded(newCtx);
/*  455 */     callHandlerRemoved(oldCtx);
/*      */   }
/*      */   
/*      */   private static void checkMultiplicity(ChannelHandlerContext ctx) {
/*  459 */     ChannelHandler handler = ctx.handler();
/*  460 */     if (handler instanceof ChannelHandlerAdapter) {
/*  461 */       ChannelHandlerAdapter h = (ChannelHandlerAdapter)handler;
/*  462 */       if (!h.isSharable() && h.added) {
/*  463 */         throw new ChannelPipelineException(h.getClass().getName() + " is not a @Sharable handler, so can't be added or removed multiple times.");
/*      */       }
/*      */ 
/*      */       
/*  467 */       h.added = true;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void callHandlerAdded(final ChannelHandlerContext ctx) {
/*  472 */     if (ctx.channel().isRegistered() && !ctx.executor().inEventLoop()) {
/*  473 */       ctx.executor().execute(new Runnable()
/*      */           {
/*      */             public void run() {
/*  476 */               DefaultChannelPipeline.this.callHandlerAdded0(ctx);
/*      */             }
/*      */           });
/*      */       return;
/*      */     } 
/*  481 */     callHandlerAdded0(ctx);
/*      */   }
/*      */   
/*      */   private void callHandlerAdded0(ChannelHandlerContext ctx) {
/*      */     try {
/*  486 */       ctx.handler().handlerAdded(ctx);
/*  487 */     } catch (Throwable t) {
/*  488 */       boolean removed = false;
/*      */       try {
/*  490 */         remove((AbstractChannelHandlerContext)ctx);
/*  491 */         removed = true;
/*  492 */       } catch (Throwable t2) {
/*  493 */         if (logger.isWarnEnabled()) {
/*  494 */           logger.warn("Failed to remove a handler: " + ctx.name(), t2);
/*      */         }
/*      */       } 
/*      */       
/*  498 */       if (removed) {
/*  499 */         fireExceptionCaught(new ChannelPipelineException(ctx.handler().getClass().getName() + ".handlerAdded() has thrown an exception; removed.", t));
/*      */       }
/*      */       else {
/*      */         
/*  503 */         fireExceptionCaught(new ChannelPipelineException(ctx.handler().getClass().getName() + ".handlerAdded() has thrown an exception; also failed to remove.", t));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void callHandlerRemoved(final AbstractChannelHandlerContext ctx) {
/*  511 */     if (ctx.channel().isRegistered() && !ctx.executor().inEventLoop()) {
/*  512 */       ctx.executor().execute(new Runnable()
/*      */           {
/*      */             public void run() {
/*  515 */               DefaultChannelPipeline.this.callHandlerRemoved0(ctx);
/*      */             }
/*      */           });
/*      */       return;
/*      */     } 
/*  520 */     callHandlerRemoved0(ctx);
/*      */   }
/*      */ 
/*      */   
/*      */   private void callHandlerRemoved0(AbstractChannelHandlerContext ctx) {
/*      */     try {
/*  526 */       ctx.handler().handlerRemoved(ctx);
/*  527 */       ctx.setRemoved();
/*  528 */     } catch (Throwable t) {
/*  529 */       fireExceptionCaught(new ChannelPipelineException(ctx.handler().getClass().getName() + ".handlerRemoved() has thrown an exception.", t));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void waitForFuture(Future<?> future) {
/*      */     try {
/*  551 */       future.get();
/*  552 */     } catch (ExecutionException ex) {
/*      */       
/*  554 */       PlatformDependent.throwException(ex.getCause());
/*  555 */     } catch (InterruptedException ex) {
/*      */       
/*  557 */       Thread.currentThread().interrupt();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelHandler first() {
/*  563 */     ChannelHandlerContext first = firstContext();
/*  564 */     if (first == null) {
/*  565 */       return null;
/*      */     }
/*  567 */     return first.handler();
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelHandlerContext firstContext() {
/*  572 */     AbstractChannelHandlerContext first = this.head.next;
/*  573 */     if (first == this.tail) {
/*  574 */       return null;
/*      */     }
/*  576 */     return this.head.next;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelHandler last() {
/*  581 */     AbstractChannelHandlerContext last = this.tail.prev;
/*  582 */     if (last == this.head) {
/*  583 */       return null;
/*      */     }
/*  585 */     return last.handler();
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelHandlerContext lastContext() {
/*  590 */     AbstractChannelHandlerContext last = this.tail.prev;
/*  591 */     if (last == this.head) {
/*  592 */       return null;
/*      */     }
/*  594 */     return last;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelHandler get(String name) {
/*  599 */     ChannelHandlerContext ctx = context(name);
/*  600 */     if (ctx == null) {
/*  601 */       return null;
/*      */     }
/*  603 */     return ctx.handler();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T extends ChannelHandler> T get(Class<T> handlerType) {
/*  610 */     ChannelHandlerContext ctx = context(handlerType);
/*  611 */     if (ctx == null) {
/*  612 */       return null;
/*      */     }
/*  614 */     return (T)ctx.handler();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ChannelHandlerContext context(String name) {
/*  620 */     if (name == null) {
/*  621 */       throw new NullPointerException("name");
/*      */     }
/*      */     
/*  624 */     synchronized (this) {
/*  625 */       return this.name2ctx.get(name);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelHandlerContext context(ChannelHandler handler) {
/*  631 */     if (handler == null) {
/*  632 */       throw new NullPointerException("handler");
/*      */     }
/*      */     
/*  635 */     AbstractChannelHandlerContext ctx = this.head.next;
/*      */     
/*      */     while (true) {
/*  638 */       if (ctx == null) {
/*  639 */         return null;
/*      */       }
/*      */       
/*  642 */       if (ctx.handler() == handler) {
/*  643 */         return ctx;
/*      */       }
/*      */       
/*  646 */       ctx = ctx.next;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelHandlerContext context(Class<? extends ChannelHandler> handlerType) {
/*  652 */     if (handlerType == null) {
/*  653 */       throw new NullPointerException("handlerType");
/*      */     }
/*      */     
/*  656 */     AbstractChannelHandlerContext ctx = this.head.next;
/*      */     while (true) {
/*  658 */       if (ctx == null) {
/*  659 */         return null;
/*      */       }
/*  661 */       if (handlerType.isAssignableFrom(ctx.handler().getClass())) {
/*  662 */         return ctx;
/*      */       }
/*  664 */       ctx = ctx.next;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public List<String> names() {
/*  670 */     List<String> list = new ArrayList<String>();
/*  671 */     AbstractChannelHandlerContext ctx = this.head.next;
/*      */     while (true) {
/*  673 */       if (ctx == null) {
/*  674 */         return list;
/*      */       }
/*  676 */       list.add(ctx.name());
/*  677 */       ctx = ctx.next;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Map<String, ChannelHandler> toMap() {
/*  683 */     Map<String, ChannelHandler> map = new LinkedHashMap<String, ChannelHandler>();
/*  684 */     AbstractChannelHandlerContext ctx = this.head.next;
/*      */     while (true) {
/*  686 */       if (ctx == this.tail) {
/*  687 */         return map;
/*      */       }
/*  689 */       map.put(ctx.name(), ctx.handler());
/*  690 */       ctx = ctx.next;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Iterator<Map.Entry<String, ChannelHandler>> iterator() {
/*  696 */     return toMap().entrySet().iterator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  704 */     StringBuilder buf = new StringBuilder();
/*  705 */     buf.append(StringUtil.simpleClassName(this));
/*  706 */     buf.append('{');
/*  707 */     AbstractChannelHandlerContext ctx = this.head.next;
/*      */     
/*  709 */     while (ctx != this.tail) {
/*      */ 
/*      */ 
/*      */       
/*  713 */       buf.append('(');
/*  714 */       buf.append(ctx.name());
/*  715 */       buf.append(" = ");
/*  716 */       buf.append(ctx.handler().getClass().getName());
/*  717 */       buf.append(')');
/*      */       
/*  719 */       ctx = ctx.next;
/*  720 */       if (ctx == this.tail) {
/*      */         break;
/*      */       }
/*      */       
/*  724 */       buf.append(", ");
/*      */     } 
/*  726 */     buf.append('}');
/*  727 */     return buf.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline fireChannelRegistered() {
/*  732 */     this.head.fireChannelRegistered();
/*  733 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline fireChannelUnregistered() {
/*  738 */     this.head.fireChannelUnregistered();
/*      */ 
/*      */     
/*  741 */     if (!this.channel.isOpen()) {
/*  742 */       teardownAll();
/*      */     }
/*  744 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void teardownAll() {
/*  753 */     this.tail.prev.teardown();
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline fireChannelActive() {
/*  758 */     this.head.fireChannelActive();
/*      */     
/*  760 */     if (this.channel.config().isAutoRead()) {
/*  761 */       this.channel.read();
/*      */     }
/*      */     
/*  764 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline fireChannelInactive() {
/*  769 */     this.head.fireChannelInactive();
/*  770 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline fireExceptionCaught(Throwable cause) {
/*  775 */     this.head.fireExceptionCaught(cause);
/*  776 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline fireUserEventTriggered(Object event) {
/*  781 */     this.head.fireUserEventTriggered(event);
/*  782 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline fireChannelRead(Object msg) {
/*  787 */     this.head.fireChannelRead(msg);
/*  788 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline fireChannelReadComplete() {
/*  793 */     this.head.fireChannelReadComplete();
/*  794 */     if (this.channel.config().isAutoRead()) {
/*  795 */       read();
/*      */     }
/*  797 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline fireChannelWritabilityChanged() {
/*  802 */     this.head.fireChannelWritabilityChanged();
/*  803 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture bind(SocketAddress localAddress) {
/*  808 */     return this.tail.bind(localAddress);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture connect(SocketAddress remoteAddress) {
/*  813 */     return this.tail.connect(remoteAddress);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
/*  818 */     return this.tail.connect(remoteAddress, localAddress);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture disconnect() {
/*  823 */     return this.tail.disconnect();
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture close() {
/*  828 */     return this.tail.close();
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture deregister() {
/*  833 */     return this.tail.deregister();
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline flush() {
/*  838 */     this.tail.flush();
/*  839 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture bind(SocketAddress localAddress, ChannelPromise promise) {
/*  844 */     return this.tail.bind(localAddress, promise);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture connect(SocketAddress remoteAddress, ChannelPromise promise) {
/*  849 */     return this.tail.connect(remoteAddress, promise);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
/*  854 */     return this.tail.connect(remoteAddress, localAddress, promise);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture disconnect(ChannelPromise promise) {
/*  859 */     return this.tail.disconnect(promise);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture close(ChannelPromise promise) {
/*  864 */     return this.tail.close(promise);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture deregister(ChannelPromise promise) {
/*  869 */     return this.tail.deregister(promise);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline read() {
/*  874 */     this.tail.read();
/*  875 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture write(Object msg) {
/*  880 */     return this.tail.write(msg);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture write(Object msg, ChannelPromise promise) {
/*  885 */     return this.tail.write(msg, promise);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture writeAndFlush(Object msg, ChannelPromise promise) {
/*  890 */     return this.tail.writeAndFlush(msg, promise);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture writeAndFlush(Object msg) {
/*  895 */     return this.tail.writeAndFlush(msg);
/*      */   }
/*      */   
/*      */   private void checkDuplicateName(String name) {
/*  899 */     if (this.name2ctx.containsKey(name)) {
/*  900 */       throw new IllegalArgumentException("Duplicate handler name: " + name);
/*      */     }
/*      */   }
/*      */   
/*      */   private AbstractChannelHandlerContext getContextOrDie(String name) {
/*  905 */     AbstractChannelHandlerContext ctx = (AbstractChannelHandlerContext)context(name);
/*  906 */     if (ctx == null) {
/*  907 */       throw new NoSuchElementException(name);
/*      */     }
/*  909 */     return ctx;
/*      */   }
/*      */ 
/*      */   
/*      */   private AbstractChannelHandlerContext getContextOrDie(ChannelHandler handler) {
/*  914 */     AbstractChannelHandlerContext ctx = (AbstractChannelHandlerContext)context(handler);
/*  915 */     if (ctx == null) {
/*  916 */       throw new NoSuchElementException(handler.getClass().getName());
/*      */     }
/*  918 */     return ctx;
/*      */   }
/*      */ 
/*      */   
/*      */   private AbstractChannelHandlerContext getContextOrDie(Class<? extends ChannelHandler> handlerType) {
/*  923 */     AbstractChannelHandlerContext ctx = (AbstractChannelHandlerContext)context(handlerType);
/*  924 */     if (ctx == null) {
/*  925 */       throw new NoSuchElementException(handlerType.getName());
/*      */     }
/*  927 */     return ctx;
/*      */   }
/*      */   
/*      */   static final class TailContext
/*      */     extends AbstractChannelHandlerContext
/*      */     implements ChannelInboundHandler
/*      */   {
/*  934 */     private static final String TAIL_NAME = DefaultChannelPipeline.generateName0(TailContext.class);
/*      */     
/*      */     TailContext(DefaultChannelPipeline pipeline) {
/*  937 */       super(pipeline, (EventExecutorGroup)null, TAIL_NAME, true, false);
/*      */     }
/*      */ 
/*      */     
/*      */     public ChannelHandler handler() {
/*  942 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public void channelRegistered(ChannelHandlerContext ctx) throws Exception {}
/*      */ 
/*      */     
/*      */     public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {}
/*      */ 
/*      */     
/*      */     public void channelActive(ChannelHandlerContext ctx) throws Exception {}
/*      */ 
/*      */     
/*      */     public void channelInactive(ChannelHandlerContext ctx) throws Exception {}
/*      */ 
/*      */     
/*      */     public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {}
/*      */ 
/*      */     
/*      */     public void handlerAdded(ChannelHandlerContext ctx) throws Exception {}
/*      */ 
/*      */     
/*      */     public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {}
/*      */ 
/*      */     
/*      */     public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {}
/*      */ 
/*      */     
/*      */     public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/*  971 */       DefaultChannelPipeline.logger.warn("An exceptionCaught() event was fired, and it reached at the tail of the pipeline. It usually means the last handler in the pipeline did not handle the exception.", cause);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/*      */       try {
/*  979 */         DefaultChannelPipeline.logger.debug("Discarded inbound message {} that reached at the tail of the pipeline. Please check your pipeline configuration.", msg);
/*      */       }
/*      */       finally {
/*      */         
/*  983 */         ReferenceCountUtil.release(msg);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {}
/*      */   }
/*      */   
/*      */   static final class HeadContext
/*      */     extends AbstractChannelHandlerContext
/*      */     implements ChannelOutboundHandler {
/*  993 */     private static final String HEAD_NAME = DefaultChannelPipeline.generateName0(HeadContext.class);
/*      */     
/*      */     protected final Channel.Unsafe unsafe;
/*      */     
/*      */     HeadContext(DefaultChannelPipeline pipeline) {
/*  998 */       super(pipeline, (EventExecutorGroup)null, HEAD_NAME, false, true);
/*  999 */       this.unsafe = pipeline.channel().unsafe();
/*      */     }
/*      */ 
/*      */     
/*      */     public ChannelHandler handler() {
/* 1004 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void handlerAdded(ChannelHandlerContext ctx) throws Exception {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
/* 1021 */       this.unsafe.bind(localAddress, promise);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
/* 1029 */       this.unsafe.connect(remoteAddress, localAddress, promise);
/*      */     }
/*      */ 
/*      */     
/*      */     public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 1034 */       this.unsafe.disconnect(promise);
/*      */     }
/*      */ 
/*      */     
/*      */     public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 1039 */       this.unsafe.close(promise);
/*      */     }
/*      */ 
/*      */     
/*      */     public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 1044 */       this.unsafe.deregister(promise);
/*      */     }
/*      */ 
/*      */     
/*      */     public void read(ChannelHandlerContext ctx) {
/* 1049 */       this.unsafe.beginRead();
/*      */     }
/*      */ 
/*      */     
/*      */     public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 1054 */       this.unsafe.write(msg, promise);
/*      */     }
/*      */ 
/*      */     
/*      */     public void flush(ChannelHandlerContext ctx) throws Exception {
/* 1059 */       this.unsafe.flush();
/*      */     }
/*      */ 
/*      */     
/*      */     public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 1064 */       ctx.fireExceptionCaught(cause);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\DefaultChannelPipeline.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */