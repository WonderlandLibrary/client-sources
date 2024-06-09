/*     */ package io.netty.channel.epoll;
/*     */ 
/*     */ import io.netty.channel.EventLoopGroup;
/*     */ import io.netty.channel.SingleThreadEventLoop;
/*     */ import io.netty.util.collection.IntObjectHashMap;
/*     */ import io.netty.util.collection.IntObjectMap;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
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
/*     */ final class EpollEventLoop
/*     */   extends SingleThreadEventLoop
/*     */ {
/*  39 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(EpollEventLoop.class);
/*     */   private static final AtomicIntegerFieldUpdater<EpollEventLoop> WAKEN_UP_UPDATER;
/*     */   
/*     */   static {
/*  43 */     AtomicIntegerFieldUpdater<EpollEventLoop> updater = PlatformDependent.newAtomicIntegerFieldUpdater(EpollEventLoop.class, "wakenUp");
/*     */     
/*  45 */     if (updater == null) {
/*  46 */       updater = AtomicIntegerFieldUpdater.newUpdater(EpollEventLoop.class, "wakenUp");
/*     */     }
/*  48 */     WAKEN_UP_UPDATER = updater;
/*     */   }
/*     */   
/*     */   private final int epollFd;
/*     */   private final int eventFd;
/*  53 */   private final IntObjectMap<AbstractEpollChannel> ids = (IntObjectMap<AbstractEpollChannel>)new IntObjectHashMap();
/*     */   
/*     */   private final long[] events;
/*     */   
/*     */   private int id;
/*     */   
/*     */   private boolean overflown;
/*     */   private volatile int wakenUp;
/*  61 */   private volatile int ioRatio = 50;
/*     */   
/*     */   EpollEventLoop(EventLoopGroup parent, ThreadFactory threadFactory, int maxEvents) {
/*  64 */     super(parent, threadFactory, false);
/*  65 */     this.events = new long[maxEvents];
/*  66 */     boolean success = false;
/*  67 */     int epollFd = -1;
/*  68 */     int eventFd = -1;
/*     */     try {
/*  70 */       this.epollFd = epollFd = Native.epollCreate();
/*  71 */       this.eventFd = eventFd = Native.eventFd();
/*  72 */       Native.epollCtlAdd(epollFd, eventFd, 1, 0);
/*  73 */       success = true;
/*     */     } finally {
/*  75 */       if (!success) {
/*  76 */         if (epollFd != -1) {
/*     */           try {
/*  78 */             Native.close(epollFd);
/*  79 */           } catch (Exception e) {}
/*     */         }
/*     */ 
/*     */         
/*  83 */         if (eventFd != -1) {
/*     */           try {
/*  85 */             Native.close(eventFd);
/*  86 */           } catch (Exception e) {}
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int nextId() {
/*  95 */     int id = this.id;
/*  96 */     if (id == Integer.MAX_VALUE) {
/*  97 */       this.overflown = true;
/*  98 */       id = 0;
/*     */     } 
/* 100 */     if (this.overflown) {
/*     */       
/*     */       do {
/*     */       
/* 104 */       } while (this.ids.containsKey(++id));
/* 105 */       this.id = id;
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 110 */       this.id = ++id;
/*     */     } 
/* 112 */     return id;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void wakeup(boolean inEventLoop) {
/* 117 */     if (!inEventLoop && WAKEN_UP_UPDATER.compareAndSet(this, 0, 1))
/*     */     {
/* 119 */       Native.eventFdWrite(this.eventFd, 1L);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void add(AbstractEpollChannel ch) {
/* 127 */     assert inEventLoop();
/* 128 */     int id = nextId();
/* 129 */     Native.epollCtlAdd(this.epollFd, ch.fd, ch.flags, id);
/* 130 */     ch.id = id;
/* 131 */     this.ids.put(id, ch);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void modify(AbstractEpollChannel ch) {
/* 138 */     assert inEventLoop();
/* 139 */     Native.epollCtlMod(this.epollFd, ch.fd, ch.flags, ch.id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void remove(AbstractEpollChannel ch) {
/* 146 */     assert inEventLoop();
/* 147 */     if (this.ids.remove(ch.id) != null && ch.isOpen())
/*     */     {
/*     */       
/* 150 */       Native.epollCtlDel(this.epollFd, ch.fd);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Queue<Runnable> newTaskQueue() {
/* 157 */     return PlatformDependent.newMpscQueue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIoRatio() {
/* 164 */     return this.ioRatio;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIoRatio(int ioRatio) {
/* 172 */     if (ioRatio <= 0 || ioRatio > 100) {
/* 173 */       throw new IllegalArgumentException("ioRatio: " + ioRatio + " (expected: 0 < ioRatio <= 100)");
/*     */     }
/* 175 */     this.ioRatio = ioRatio;
/*     */   }
/*     */   
/*     */   private int epollWait(boolean oldWakenUp) {
/* 179 */     int selectCnt = 0;
/* 180 */     long currentTimeNanos = System.nanoTime();
/* 181 */     long selectDeadLineNanos = currentTimeNanos + delayNanos(currentTimeNanos);
/*     */     while (true) {
/* 183 */       long timeoutMillis = (selectDeadLineNanos - currentTimeNanos + 500000L) / 1000000L;
/* 184 */       if (timeoutMillis <= 0L) {
/* 185 */         if (selectCnt == 0) {
/* 186 */           int ready = Native.epollWait(this.epollFd, this.events, 0);
/* 187 */           if (ready > 0) {
/* 188 */             return ready;
/*     */           }
/*     */         } 
/*     */         
/*     */         break;
/*     */       } 
/* 194 */       int selectedKeys = Native.epollWait(this.epollFd, this.events, (int)timeoutMillis);
/* 195 */       selectCnt++;
/*     */       
/* 197 */       if (selectedKeys != 0 || oldWakenUp || this.wakenUp == 1 || hasTasks() || hasScheduledTasks())
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 202 */         return selectedKeys;
/*     */       }
/* 204 */       currentTimeNanos = System.nanoTime();
/*     */     } 
/* 206 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void run() {
/*     */     while (true) {
/* 212 */       boolean oldWakenUp = (WAKEN_UP_UPDATER.getAndSet(this, 0) == 1);
/*     */       try {
/*     */         int ready;
/* 215 */         if (hasTasks()) {
/*     */           
/* 217 */           ready = Native.epollWait(this.epollFd, this.events, 0);
/*     */         } else {
/* 219 */           ready = epollWait(oldWakenUp);
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
/*     */ 
/*     */ 
/*     */           
/* 249 */           if (this.wakenUp == 1) {
/* 250 */             Native.eventFdWrite(this.eventFd, 1L);
/*     */           }
/*     */         } 
/*     */         
/* 254 */         int ioRatio = this.ioRatio;
/* 255 */         if (ioRatio == 100) {
/* 256 */           if (ready > 0) {
/* 257 */             processReady(this.events, ready);
/*     */           }
/* 259 */           runAllTasks();
/*     */         } else {
/* 261 */           long ioStartTime = System.nanoTime();
/*     */           
/* 263 */           if (ready > 0) {
/* 264 */             processReady(this.events, ready);
/*     */           }
/*     */           
/* 267 */           long ioTime = System.nanoTime() - ioStartTime;
/* 268 */           runAllTasks(ioTime * (100 - ioRatio) / ioRatio);
/*     */         } 
/*     */         
/* 271 */         if (isShuttingDown()) {
/* 272 */           closeAll();
/* 273 */           if (confirmShutdown()) {
/*     */             break;
/*     */           }
/*     */         } 
/* 277 */       } catch (Throwable t) {
/* 278 */         logger.warn("Unexpected exception in the selector loop.", t);
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 283 */           Thread.sleep(1000L);
/* 284 */         } catch (InterruptedException e) {}
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void closeAll() {
/* 292 */     Native.epollWait(this.epollFd, this.events, 0);
/* 293 */     Collection<AbstractEpollChannel> channels = new ArrayList<AbstractEpollChannel>(this.ids.size());
/*     */     
/* 295 */     for (IntObjectMap.Entry<AbstractEpollChannel> entry : (Iterable<IntObjectMap.Entry<AbstractEpollChannel>>)this.ids.entries()) {
/* 296 */       channels.add(entry.value());
/*     */     }
/*     */     
/* 299 */     for (AbstractEpollChannel ch : channels) {
/* 300 */       ch.unsafe().close(ch.unsafe().voidPromise());
/*     */     }
/*     */   }
/*     */   
/*     */   private void processReady(long[] events, int ready) {
/* 305 */     for (int i = 0; i < ready; i++) {
/* 306 */       long ev = events[i];
/*     */       
/* 308 */       int id = (int)(ev >> 32L);
/* 309 */       if (id == 0) {
/*     */         
/* 311 */         Native.eventFdRead(this.eventFd);
/*     */       } else {
/* 313 */         boolean read = ((ev & 0x1L) != 0L);
/* 314 */         boolean write = ((ev & 0x2L) != 0L);
/* 315 */         boolean close = ((ev & 0x8L) != 0L);
/*     */         
/* 317 */         AbstractEpollChannel ch = (AbstractEpollChannel)this.ids.get(id);
/* 318 */         if (ch != null) {
/* 319 */           AbstractEpollChannel.AbstractEpollUnsafe unsafe = (AbstractEpollChannel.AbstractEpollUnsafe)ch.unsafe();
/* 320 */           if (write && ch.isOpen())
/*     */           {
/* 322 */             unsafe.epollOutReady();
/*     */           }
/* 324 */           if (read && ch.isOpen())
/*     */           {
/* 326 */             unsafe.epollInReady();
/*     */           }
/* 328 */           if (close && ch.isOpen()) {
/* 329 */             unsafe.epollRdHupReady();
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void cleanup() {
/*     */     try {
/* 339 */       Native.close(this.epollFd);
/* 340 */     } catch (IOException e) {
/* 341 */       logger.warn("Failed to close the epoll fd.", e);
/*     */     } 
/*     */     try {
/* 344 */       Native.close(this.eventFd);
/* 345 */     } catch (IOException e) {
/* 346 */       logger.warn("Failed to close the event fd.", e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\epoll\EpollEventLoop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */