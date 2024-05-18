/*     */ package io.netty.channel.nio;
/*     */ 
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.EventLoopException;
/*     */ import io.netty.channel.EventLoopGroup;
/*     */ import io.netty.channel.SingleThreadEventLoop;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Field;
/*     */ import java.nio.channels.CancelledKeyException;
/*     */ import java.nio.channels.SelectableChannel;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.nio.channels.Selector;
/*     */ import java.nio.channels.spi.SelectorProvider;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.ConcurrentModificationException;
/*     */ import java.util.Iterator;
/*     */ import java.util.Queue;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
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
/*     */ public final class NioEventLoop
/*     */   extends SingleThreadEventLoop
/*     */ {
/*  53 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioEventLoop.class);
/*     */   
/*     */   private static final int CLEANUP_INTERVAL = 256;
/*     */   
/*  57 */   private static final boolean DISABLE_KEYSET_OPTIMIZATION = SystemPropertyUtil.getBoolean("io.netty.noKeySetOptimization", false);
/*     */   
/*     */   private static final int MIN_PREMATURE_SELECTOR_RETURNS = 3;
/*     */   
/*     */   private static final int SELECTOR_AUTO_REBUILD_THRESHOLD;
/*     */   
/*     */   Selector selector;
/*     */   
/*     */   private SelectedSelectionKeySet selectedKeys;
/*     */   private final SelectorProvider provider;
/*     */   
/*     */   static {
/*  69 */     String key = "sun.nio.ch.bugLevel";
/*     */     try {
/*  71 */       String buglevel = SystemPropertyUtil.get(key);
/*  72 */       if (buglevel == null) {
/*  73 */         System.setProperty(key, "");
/*     */       }
/*  75 */     } catch (SecurityException e) {
/*  76 */       if (logger.isDebugEnabled()) {
/*  77 */         logger.debug("Unable to get/set System Property: {}", key, e);
/*     */       }
/*     */     } 
/*     */     
/*  81 */     int selectorAutoRebuildThreshold = SystemPropertyUtil.getInt("io.netty.selectorAutoRebuildThreshold", 512);
/*  82 */     if (selectorAutoRebuildThreshold < 3) {
/*  83 */       selectorAutoRebuildThreshold = 0;
/*     */     }
/*     */     
/*  86 */     SELECTOR_AUTO_REBUILD_THRESHOLD = selectorAutoRebuildThreshold;
/*     */     
/*  88 */     if (logger.isDebugEnabled()) {
/*  89 */       logger.debug("-Dio.netty.noKeySetOptimization: {}", Boolean.valueOf(DISABLE_KEYSET_OPTIMIZATION));
/*  90 */       logger.debug("-Dio.netty.selectorAutoRebuildThreshold: {}", Integer.valueOf(SELECTOR_AUTO_REBUILD_THRESHOLD));
/*     */     } 
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
/* 108 */   private final AtomicBoolean wakenUp = new AtomicBoolean();
/*     */   
/* 110 */   private volatile int ioRatio = 50;
/*     */   private int cancelledKeys;
/*     */   private boolean needsToSelectAgain;
/*     */   
/*     */   NioEventLoop(NioEventLoopGroup parent, ThreadFactory threadFactory, SelectorProvider selectorProvider) {
/* 115 */     super((EventLoopGroup)parent, threadFactory, false);
/* 116 */     if (selectorProvider == null) {
/* 117 */       throw new NullPointerException("selectorProvider");
/*     */     }
/* 119 */     this.provider = selectorProvider;
/* 120 */     this.selector = openSelector();
/*     */   }
/*     */   
/*     */   private Selector openSelector() {
/*     */     Selector selector;
/*     */     try {
/* 126 */       selector = this.provider.openSelector();
/* 127 */     } catch (IOException e) {
/* 128 */       throw new ChannelException("failed to open a new selector", e);
/*     */     } 
/*     */     
/* 131 */     if (DISABLE_KEYSET_OPTIMIZATION) {
/* 132 */       return selector;
/*     */     }
/*     */     
/*     */     try {
/* 136 */       SelectedSelectionKeySet selectedKeySet = new SelectedSelectionKeySet();
/*     */       
/* 138 */       Class<?> selectorImplClass = Class.forName("sun.nio.ch.SelectorImpl", false, PlatformDependent.getSystemClassLoader());
/*     */ 
/*     */ 
/*     */       
/* 142 */       if (!selectorImplClass.isAssignableFrom(selector.getClass())) {
/* 143 */         return selector;
/*     */       }
/*     */       
/* 146 */       Field selectedKeysField = selectorImplClass.getDeclaredField("selectedKeys");
/* 147 */       Field publicSelectedKeysField = selectorImplClass.getDeclaredField("publicSelectedKeys");
/*     */       
/* 149 */       selectedKeysField.setAccessible(true);
/* 150 */       publicSelectedKeysField.setAccessible(true);
/*     */       
/* 152 */       selectedKeysField.set(selector, selectedKeySet);
/* 153 */       publicSelectedKeysField.set(selector, selectedKeySet);
/*     */       
/* 155 */       this.selectedKeys = selectedKeySet;
/* 156 */       logger.trace("Instrumented an optimized java.util.Set into: {}", selector);
/* 157 */     } catch (Throwable t) {
/* 158 */       this.selectedKeys = null;
/* 159 */       logger.trace("Failed to instrument an optimized java.util.Set into: {}", selector, t);
/*     */     } 
/*     */     
/* 162 */     return selector;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Queue<Runnable> newTaskQueue() {
/* 168 */     return PlatformDependent.newMpscQueue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void register(SelectableChannel ch, int interestOps, NioTask<?> task) {
/* 177 */     if (ch == null) {
/* 178 */       throw new NullPointerException("ch");
/*     */     }
/* 180 */     if (interestOps == 0) {
/* 181 */       throw new IllegalArgumentException("interestOps must be non-zero.");
/*     */     }
/* 183 */     if ((interestOps & (ch.validOps() ^ 0xFFFFFFFF)) != 0) {
/* 184 */       throw new IllegalArgumentException("invalid interestOps: " + interestOps + "(validOps: " + ch.validOps() + ')');
/*     */     }
/*     */     
/* 187 */     if (task == null) {
/* 188 */       throw new NullPointerException("task");
/*     */     }
/*     */     
/* 191 */     if (isShutdown()) {
/* 192 */       throw new IllegalStateException("event loop shut down");
/*     */     }
/*     */     
/*     */     try {
/* 196 */       ch.register(this.selector, interestOps, task);
/* 197 */     } catch (Exception e) {
/* 198 */       throw new EventLoopException("failed to register a channel", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIoRatio() {
/* 206 */     return this.ioRatio;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIoRatio(int ioRatio) {
/* 214 */     if (ioRatio <= 0 || ioRatio > 100) {
/* 215 */       throw new IllegalArgumentException("ioRatio: " + ioRatio + " (expected: 0 < ioRatio <= 100)");
/*     */     }
/* 217 */     this.ioRatio = ioRatio;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rebuildSelector() {
/*     */     Selector newSelector;
/* 225 */     if (!inEventLoop()) {
/* 226 */       execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 229 */               NioEventLoop.this.rebuildSelector();
/*     */             }
/*     */           });
/*     */       
/*     */       return;
/*     */     } 
/* 235 */     Selector oldSelector = this.selector;
/*     */ 
/*     */     
/* 238 */     if (oldSelector == null) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 243 */       newSelector = openSelector();
/* 244 */     } catch (Exception e) {
/* 245 */       logger.warn("Failed to create a new Selector.", e);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 250 */     int nChannels = 0;
/*     */     while (true) {
/*     */       try {
/* 253 */         for (SelectionKey key : oldSelector.keys()) {
/* 254 */           Object a = key.attachment();
/*     */           try {
/* 256 */             if (!key.isValid() || key.channel().keyFor(newSelector) != null) {
/*     */               continue;
/*     */             }
/*     */             
/* 260 */             int interestOps = key.interestOps();
/* 261 */             key.cancel();
/* 262 */             SelectionKey newKey = key.channel().register(newSelector, interestOps, a);
/* 263 */             if (a instanceof AbstractNioChannel)
/*     */             {
/* 265 */               ((AbstractNioChannel)a).selectionKey = newKey;
/*     */             }
/* 267 */             nChannels++;
/* 268 */           } catch (Exception e) {
/* 269 */             logger.warn("Failed to re-register a Channel to the new Selector.", e);
/* 270 */             if (a instanceof AbstractNioChannel) {
/* 271 */               AbstractNioChannel ch = (AbstractNioChannel)a;
/* 272 */               ch.unsafe().close(ch.unsafe().voidPromise());
/*     */               continue;
/*     */             } 
/* 275 */             NioTask<SelectableChannel> task = (NioTask<SelectableChannel>)a;
/* 276 */             invokeChannelUnregistered(task, key, e);
/*     */           } 
/*     */         } 
/*     */         break;
/* 280 */       } catch (ConcurrentModificationException e) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 288 */     this.selector = newSelector;
/*     */ 
/*     */     
/*     */     try {
/* 292 */       oldSelector.close();
/* 293 */     } catch (Throwable t) {
/* 294 */       if (logger.isWarnEnabled()) {
/* 295 */         logger.warn("Failed to close the old Selector.", t);
/*     */       }
/*     */     } 
/*     */     
/* 299 */     logger.info("Migrated " + nChannels + " channel(s) to the new Selector.");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void run() {
/*     */     while (true) {
/* 305 */       boolean oldWakenUp = this.wakenUp.getAndSet(false);
/*     */       try {
/* 307 */         if (hasTasks()) {
/* 308 */           selectNow();
/*     */         } else {
/* 310 */           select(oldWakenUp);
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
/* 340 */           if (this.wakenUp.get()) {
/* 341 */             this.selector.wakeup();
/*     */           }
/*     */         } 
/*     */         
/* 345 */         this.cancelledKeys = 0;
/* 346 */         this.needsToSelectAgain = false;
/* 347 */         int ioRatio = this.ioRatio;
/* 348 */         if (ioRatio == 100) {
/* 349 */           processSelectedKeys();
/* 350 */           runAllTasks();
/*     */         } else {
/* 352 */           long ioStartTime = System.nanoTime();
/*     */           
/* 354 */           processSelectedKeys();
/*     */           
/* 356 */           long ioTime = System.nanoTime() - ioStartTime;
/* 357 */           runAllTasks(ioTime * (100 - ioRatio) / ioRatio);
/*     */         } 
/*     */         
/* 360 */         if (isShuttingDown()) {
/* 361 */           closeAll();
/* 362 */           if (confirmShutdown()) {
/*     */             break;
/*     */           }
/*     */         } 
/* 366 */       } catch (Throwable t) {
/* 367 */         logger.warn("Unexpected exception in the selector loop.", t);
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 372 */           Thread.sleep(1000L);
/* 373 */         } catch (InterruptedException e) {}
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void processSelectedKeys() {
/* 381 */     if (this.selectedKeys != null) {
/* 382 */       processSelectedKeysOptimized(this.selectedKeys.flip());
/*     */     } else {
/* 384 */       processSelectedKeysPlain(this.selector.selectedKeys());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void cleanup() {
/*     */     try {
/* 391 */       this.selector.close();
/* 392 */     } catch (IOException e) {
/* 393 */       logger.warn("Failed to close a selector.", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   void cancel(SelectionKey key) {
/* 398 */     key.cancel();
/* 399 */     this.cancelledKeys++;
/* 400 */     if (this.cancelledKeys >= 256) {
/* 401 */       this.cancelledKeys = 0;
/* 402 */       this.needsToSelectAgain = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Runnable pollTask() {
/* 408 */     Runnable task = super.pollTask();
/* 409 */     if (this.needsToSelectAgain) {
/* 410 */       selectAgain();
/*     */     }
/* 412 */     return task;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processSelectedKeysPlain(Set<SelectionKey> selectedKeys) {
/* 419 */     if (selectedKeys.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 423 */     Iterator<SelectionKey> i = selectedKeys.iterator();
/*     */     while (true) {
/* 425 */       SelectionKey k = i.next();
/* 426 */       Object a = k.attachment();
/* 427 */       i.remove();
/*     */       
/* 429 */       if (a instanceof AbstractNioChannel) {
/* 430 */         processSelectedKey(k, (AbstractNioChannel)a);
/*     */       } else {
/*     */         
/* 433 */         NioTask<SelectableChannel> task = (NioTask<SelectableChannel>)a;
/* 434 */         processSelectedKey(k, task);
/*     */       } 
/*     */       
/* 437 */       if (!i.hasNext()) {
/*     */         break;
/*     */       }
/*     */       
/* 441 */       if (this.needsToSelectAgain) {
/* 442 */         selectAgain();
/* 443 */         selectedKeys = this.selector.selectedKeys();
/*     */ 
/*     */         
/* 446 */         if (selectedKeys.isEmpty()) {
/*     */           break;
/*     */         }
/* 449 */         i = selectedKeys.iterator();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void processSelectedKeysOptimized(SelectionKey[] selectedKeys) {
/* 456 */     for (int i = 0;; i++) {
/* 457 */       SelectionKey k = selectedKeys[i];
/* 458 */       if (k == null) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/* 463 */       selectedKeys[i] = null;
/*     */       
/* 465 */       Object a = k.attachment();
/*     */       
/* 467 */       if (a instanceof AbstractNioChannel) {
/* 468 */         processSelectedKey(k, (AbstractNioChannel)a);
/*     */       } else {
/*     */         
/* 471 */         NioTask<SelectableChannel> task = (NioTask<SelectableChannel>)a;
/* 472 */         processSelectedKey(k, task);
/*     */       } 
/*     */       
/* 475 */       if (this.needsToSelectAgain) {
/*     */ 
/*     */ 
/*     */         
/* 479 */         while (selectedKeys[i] != null) {
/*     */ 
/*     */           
/* 482 */           selectedKeys[i] = null;
/* 483 */           i++;
/*     */         } 
/*     */         
/* 486 */         selectAgain();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 492 */         selectedKeys = this.selectedKeys.flip();
/* 493 */         i = -1;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void processSelectedKey(SelectionKey k, AbstractNioChannel ch) {
/* 499 */     AbstractNioChannel.NioUnsafe unsafe = ch.unsafe();
/* 500 */     if (!k.isValid()) {
/*     */       
/* 502 */       unsafe.close(unsafe.voidPromise());
/*     */       
/*     */       return;
/*     */     } 
/*     */     try {
/* 507 */       int readyOps = k.readyOps();
/*     */ 
/*     */       
/* 510 */       if ((readyOps & 0x11) != 0 || readyOps == 0) {
/* 511 */         unsafe.read();
/* 512 */         if (!ch.isOpen()) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */       
/* 517 */       if ((readyOps & 0x4) != 0)
/*     */       {
/* 519 */         ch.unsafe().forceFlush();
/*     */       }
/* 521 */       if ((readyOps & 0x8) != 0) {
/*     */ 
/*     */         
/* 524 */         int ops = k.interestOps();
/* 525 */         ops &= 0xFFFFFFF7;
/* 526 */         k.interestOps(ops);
/*     */         
/* 528 */         unsafe.finishConnect();
/*     */       } 
/* 530 */     } catch (CancelledKeyException ignored) {
/* 531 */       unsafe.close(unsafe.voidPromise());
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void processSelectedKey(SelectionKey k, NioTask<SelectableChannel> task) {
/* 536 */     int state = 0;
/*     */     try {
/* 538 */       task.channelReady(k.channel(), k);
/* 539 */       state = 1;
/* 540 */     } catch (Exception e) {
/* 541 */       k.cancel();
/* 542 */       invokeChannelUnregistered(task, k, e);
/* 543 */       state = 2;
/*     */     } finally {
/* 545 */       switch (state) {
/*     */         case 0:
/* 547 */           k.cancel();
/* 548 */           invokeChannelUnregistered(task, k, (Throwable)null);
/*     */           break;
/*     */         case 1:
/* 551 */           if (!k.isValid()) {
/* 552 */             invokeChannelUnregistered(task, k, (Throwable)null);
/*     */           }
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void closeAll() {
/* 560 */     selectAgain();
/* 561 */     Set<SelectionKey> keys = this.selector.keys();
/* 562 */     Collection<AbstractNioChannel> channels = new ArrayList<AbstractNioChannel>(keys.size());
/* 563 */     for (SelectionKey k : keys) {
/* 564 */       Object a = k.attachment();
/* 565 */       if (a instanceof AbstractNioChannel) {
/* 566 */         channels.add((AbstractNioChannel)a); continue;
/*     */       } 
/* 568 */       k.cancel();
/*     */       
/* 570 */       NioTask<SelectableChannel> task = (NioTask<SelectableChannel>)a;
/* 571 */       invokeChannelUnregistered(task, k, (Throwable)null);
/*     */     } 
/*     */ 
/*     */     
/* 575 */     for (AbstractNioChannel ch : channels) {
/* 576 */       ch.unsafe().close(ch.unsafe().voidPromise());
/*     */     }
/*     */   }
/*     */   
/*     */   private static void invokeChannelUnregistered(NioTask<SelectableChannel> task, SelectionKey k, Throwable cause) {
/*     */     try {
/* 582 */       task.channelUnregistered(k.channel(), cause);
/* 583 */     } catch (Exception e) {
/* 584 */       logger.warn("Unexpected exception while running NioTask.channelUnregistered()", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void wakeup(boolean inEventLoop) {
/* 590 */     if (!inEventLoop && this.wakenUp.compareAndSet(false, true)) {
/* 591 */       this.selector.wakeup();
/*     */     }
/*     */   }
/*     */   
/*     */   void selectNow() throws IOException {
/*     */     try {
/* 597 */       this.selector.selectNow();
/*     */     } finally {
/*     */       
/* 600 */       if (this.wakenUp.get()) {
/* 601 */         this.selector.wakeup();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void select(boolean oldWakenUp) throws IOException {
/* 607 */     Selector selector = this.selector;
/*     */     try {
/* 609 */       int selectCnt = 0;
/* 610 */       long currentTimeNanos = System.nanoTime();
/* 611 */       long selectDeadLineNanos = currentTimeNanos + delayNanos(currentTimeNanos);
/*     */       while (true) {
/* 613 */         long timeoutMillis = (selectDeadLineNanos - currentTimeNanos + 500000L) / 1000000L;
/* 614 */         if (timeoutMillis <= 0L) {
/* 615 */           if (selectCnt == 0) {
/* 616 */             selector.selectNow();
/* 617 */             selectCnt = 1;
/*     */           } 
/*     */           
/*     */           break;
/*     */         } 
/* 622 */         int selectedKeys = selector.select(timeoutMillis);
/* 623 */         selectCnt++;
/*     */         
/* 625 */         if (selectedKeys != 0 || oldWakenUp || this.wakenUp.get() || hasTasks() || hasScheduledTasks()) {
/*     */           break;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 632 */         if (Thread.interrupted()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 638 */           if (logger.isDebugEnabled()) {
/* 639 */             logger.debug("Selector.select() returned prematurely because Thread.currentThread().interrupt() was called. Use NioEventLoop.shutdownGracefully() to shutdown the NioEventLoop.");
/*     */           }
/*     */ 
/*     */           
/* 643 */           selectCnt = 1;
/*     */           
/*     */           break;
/*     */         } 
/* 647 */         long time = System.nanoTime();
/* 648 */         if (time - TimeUnit.MILLISECONDS.toNanos(timeoutMillis) >= currentTimeNanos) {
/*     */           
/* 650 */           selectCnt = 1;
/* 651 */         } else if (SELECTOR_AUTO_REBUILD_THRESHOLD > 0 && selectCnt >= SELECTOR_AUTO_REBUILD_THRESHOLD) {
/*     */ 
/*     */ 
/*     */           
/* 655 */           logger.warn("Selector.select() returned prematurely {} times in a row; rebuilding selector.", Integer.valueOf(selectCnt));
/*     */ 
/*     */ 
/*     */           
/* 659 */           rebuildSelector();
/* 660 */           selector = this.selector;
/*     */ 
/*     */           
/* 663 */           selector.selectNow();
/* 664 */           selectCnt = 1;
/*     */           
/*     */           break;
/*     */         } 
/* 668 */         currentTimeNanos = time;
/*     */       } 
/*     */       
/* 671 */       if (selectCnt > 3 && 
/* 672 */         logger.isDebugEnabled()) {
/* 673 */         logger.debug("Selector.select() returned prematurely {} times in a row.", Integer.valueOf(selectCnt - 1));
/*     */       }
/*     */     }
/* 676 */     catch (CancelledKeyException e) {
/* 677 */       if (logger.isDebugEnabled()) {
/* 678 */         logger.debug(CancelledKeyException.class.getSimpleName() + " raised by a Selector - JDK bug?", e);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void selectAgain() {
/* 685 */     this.needsToSelectAgain = false;
/*     */     try {
/* 687 */       this.selector.selectNow();
/* 688 */     } catch (Throwable t) {
/* 689 */       logger.warn("Failed to update SelectionKeys.", t);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\nio\NioEventLoop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */