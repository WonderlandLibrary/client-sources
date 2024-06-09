/*     */ package io.netty.util;
/*     */ 
/*     */ import io.netty.util.concurrent.FastThreadLocal;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.Arrays;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
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
/*     */ public abstract class Recycler<T>
/*     */ {
/*  37 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(Recycler.class);
/*     */   
/*  39 */   private static final AtomicInteger ID_GENERATOR = new AtomicInteger(-2147483648);
/*  40 */   private static final int OWN_THREAD_ID = ID_GENERATOR.getAndIncrement();
/*     */ 
/*     */   
/*     */   private static final int DEFAULT_MAX_CAPACITY;
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  48 */     int maxCapacity = SystemPropertyUtil.getInt("io.netty.recycler.maxCapacity.default", 0);
/*  49 */     if (maxCapacity <= 0)
/*     */     {
/*  51 */       maxCapacity = 262144;
/*     */     }
/*     */     
/*  54 */     DEFAULT_MAX_CAPACITY = maxCapacity;
/*  55 */     if (logger.isDebugEnabled())
/*  56 */       logger.debug("-Dio.netty.recycler.maxCapacity.default: {}", Integer.valueOf(DEFAULT_MAX_CAPACITY)); 
/*     */   }
/*     */   
/*  59 */   private static final int INITIAL_CAPACITY = Math.min(DEFAULT_MAX_CAPACITY, 256);
/*     */   
/*     */   private final int maxCapacity;
/*     */   
/*  63 */   private final FastThreadLocal<Stack<T>> threadLocal = new FastThreadLocal<Stack<T>>()
/*     */     {
/*     */       protected Recycler.Stack<T> initialValue() {
/*  66 */         return new Recycler.Stack<T>(Recycler.this, Thread.currentThread(), Recycler.this.maxCapacity);
/*     */       }
/*     */     };
/*     */   
/*     */   protected Recycler() {
/*  71 */     this(DEFAULT_MAX_CAPACITY);
/*     */   }
/*     */   
/*     */   protected Recycler(int maxCapacity) {
/*  75 */     this.maxCapacity = Math.max(0, maxCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public final T get() {
/*  80 */     Stack<T> stack = (Stack<T>)this.threadLocal.get();
/*  81 */     DefaultHandle handle = stack.pop();
/*  82 */     if (handle == null) {
/*  83 */       handle = stack.newHandle();
/*  84 */       handle.value = newObject(handle);
/*     */     } 
/*  86 */     return (T)handle.value;
/*     */   }
/*     */   
/*     */   public final boolean recycle(T o, Handle handle) {
/*  90 */     DefaultHandle h = (DefaultHandle)handle;
/*  91 */     if (h.stack.parent != this) {
/*  92 */       return false;
/*     */     }
/*  94 */     if (o != h.value) {
/*  95 */       throw new IllegalArgumentException("o does not belong to handle");
/*     */     }
/*  97 */     h.recycle();
/*  98 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   static final class DefaultHandle
/*     */     implements Handle
/*     */   {
/*     */     private int lastRecycledId;
/*     */     
/*     */     private int recycleId;
/*     */     
/*     */     private Recycler.Stack<?> stack;
/*     */     private Object value;
/*     */     
/*     */     DefaultHandle(Recycler.Stack<?> stack) {
/* 113 */       this.stack = stack;
/*     */     }
/*     */     
/*     */     public void recycle() {
/* 117 */       Thread thread = Thread.currentThread();
/* 118 */       if (thread == this.stack.thread) {
/* 119 */         this.stack.push(this);
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 125 */       Map<Recycler.Stack<?>, Recycler.WeakOrderQueue> delayedRecycled = (Map<Recycler.Stack<?>, Recycler.WeakOrderQueue>)Recycler.DELAYED_RECYCLED.get();
/* 126 */       Recycler.WeakOrderQueue queue = delayedRecycled.get(this.stack);
/* 127 */       if (queue == null) {
/* 128 */         delayedRecycled.put(this.stack, queue = new Recycler.WeakOrderQueue(this.stack, thread));
/*     */       }
/* 130 */       queue.add(this);
/*     */     }
/*     */   }
/*     */   
/* 134 */   private static final FastThreadLocal<Map<Stack<?>, WeakOrderQueue>> DELAYED_RECYCLED = new FastThreadLocal<Map<Stack<?>, WeakOrderQueue>>()
/*     */     {
/*     */       protected Map<Recycler.Stack<?>, Recycler.WeakOrderQueue> initialValue()
/*     */       {
/* 138 */         return new WeakHashMap<Recycler.Stack<?>, Recycler.WeakOrderQueue>();
/*     */       }
/*     */     };
/*     */   
/*     */   protected abstract T newObject(Handle paramHandle);
/*     */   
/*     */   private static final class WeakOrderQueue { private static final int LINK_CAPACITY = 16;
/*     */     private Link head;
/*     */     private Link tail;
/*     */     private WeakOrderQueue next;
/*     */     private final WeakReference<Thread> owner;
/*     */     
/* 150 */     private static final class Link extends AtomicInteger { private final Recycler.DefaultHandle[] elements = new Recycler.DefaultHandle[16];
/*     */ 
/*     */       
/*     */       private Link() {}
/*     */ 
/*     */       
/*     */       private int readIndex;
/*     */       
/*     */       private Link next; }
/*     */ 
/*     */     
/* 161 */     private final int id = Recycler.ID_GENERATOR.getAndIncrement();
/*     */     
/*     */     WeakOrderQueue(Recycler.Stack<?> stack, Thread thread) {
/* 164 */       this.head = this.tail = new Link();
/* 165 */       this.owner = new WeakReference<Thread>(thread);
/* 166 */       synchronized (stack) {
/* 167 */         this.next = stack.head;
/* 168 */         stack.head = this;
/*     */       } 
/*     */     }
/*     */     
/*     */     void add(Recycler.DefaultHandle handle) {
/* 173 */       handle.lastRecycledId = this.id;
/*     */       
/* 175 */       Link tail = this.tail;
/*     */       int writeIndex;
/* 177 */       if ((writeIndex = tail.get()) == 16) {
/* 178 */         this.tail = tail = tail.next = new Link();
/* 179 */         writeIndex = tail.get();
/*     */       } 
/* 181 */       tail.elements[writeIndex] = handle;
/* 182 */       handle.stack = null;
/*     */ 
/*     */       
/* 185 */       tail.lazySet(writeIndex + 1);
/*     */     }
/*     */     
/*     */     boolean hasFinalData() {
/* 189 */       return (this.tail.readIndex != this.tail.get());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean transfer(Recycler.Stack<?> to) {
/* 196 */       Link head = this.head;
/* 197 */       if (head == null) {
/* 198 */         return false;
/*     */       }
/*     */       
/* 201 */       if (head.readIndex == 16) {
/* 202 */         if (head.next == null) {
/* 203 */           return false;
/*     */         }
/* 205 */         this.head = head = head.next;
/*     */       } 
/*     */       
/* 208 */       int start = head.readIndex;
/* 209 */       int end = head.get();
/* 210 */       if (start == end) {
/* 211 */         return false;
/*     */       }
/*     */       
/* 214 */       int count = end - start;
/* 215 */       if (to.size + count > to.elements.length) {
/* 216 */         to.elements = Arrays.<Recycler.DefaultHandle>copyOf(to.elements, (to.size + count) * 2);
/*     */       }
/*     */       
/* 219 */       Recycler.DefaultHandle[] src = head.elements;
/* 220 */       Recycler.DefaultHandle[] trg = to.elements;
/* 221 */       int size = to.size;
/* 222 */       while (start < end) {
/* 223 */         Recycler.DefaultHandle element = src[start];
/* 224 */         if (element.recycleId == 0) {
/* 225 */           element.recycleId = element.lastRecycledId;
/* 226 */         } else if (element.recycleId != element.lastRecycledId) {
/* 227 */           throw new IllegalStateException("recycled already");
/*     */         } 
/* 229 */         element.stack = to;
/* 230 */         trg[size++] = element;
/* 231 */         src[start++] = null;
/*     */       } 
/* 233 */       to.size = size;
/*     */       
/* 235 */       if (end == 16 && head.next != null) {
/* 236 */         this.head = head.next;
/*     */       }
/*     */       
/* 239 */       head.readIndex = end;
/* 240 */       return true;
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   static final class Stack<T>
/*     */   {
/*     */     final Recycler<T> parent;
/*     */     
/*     */     final Thread thread;
/*     */     
/*     */     private Recycler.DefaultHandle[] elements;
/*     */     
/*     */     private final int maxCapacity;
/*     */     private int size;
/*     */     private volatile Recycler.WeakOrderQueue head;
/*     */     private Recycler.WeakOrderQueue cursor;
/*     */     private Recycler.WeakOrderQueue prev;
/*     */     
/*     */     Stack(Recycler<T> parent, Thread thread, int maxCapacity) {
/* 260 */       this.parent = parent;
/* 261 */       this.thread = thread;
/* 262 */       this.maxCapacity = maxCapacity;
/* 263 */       this.elements = new Recycler.DefaultHandle[Recycler.INITIAL_CAPACITY];
/*     */     }
/*     */     
/*     */     Recycler.DefaultHandle pop() {
/* 267 */       int size = this.size;
/* 268 */       if (size == 0) {
/* 269 */         if (!scavenge()) {
/* 270 */           return null;
/*     */         }
/* 272 */         size = this.size;
/*     */       } 
/* 274 */       size--;
/* 275 */       Recycler.DefaultHandle ret = this.elements[size];
/* 276 */       if (ret.lastRecycledId != ret.recycleId) {
/* 277 */         throw new IllegalStateException("recycled multiple times");
/*     */       }
/* 279 */       ret.recycleId = 0;
/* 280 */       ret.lastRecycledId = 0;
/* 281 */       this.size = size;
/* 282 */       return ret;
/*     */     }
/*     */ 
/*     */     
/*     */     boolean scavenge() {
/* 287 */       if (scavengeSome()) {
/* 288 */         return true;
/*     */       }
/*     */ 
/*     */       
/* 292 */       this.prev = null;
/* 293 */       this.cursor = this.head;
/* 294 */       return false;
/*     */     }
/*     */     
/*     */     boolean scavengeSome() {
/* 298 */       boolean success = false;
/* 299 */       Recycler.WeakOrderQueue cursor = this.cursor, prev = this.prev;
/* 300 */       while (cursor != null) {
/* 301 */         if (cursor.transfer(this)) {
/* 302 */           success = true;
/*     */           break;
/*     */         } 
/* 305 */         Recycler.WeakOrderQueue next = cursor.next;
/* 306 */         if (cursor.owner.get() == null) {
/*     */ 
/*     */ 
/*     */           
/* 310 */           if (cursor.hasFinalData()) {
/*     */             do {  }
/* 312 */             while (cursor.transfer(this));
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 317 */           if (prev != null) {
/* 318 */             prev.next = next;
/*     */           }
/*     */         } else {
/* 321 */           prev = cursor;
/*     */         } 
/* 323 */         cursor = next;
/*     */       } 
/* 325 */       this.prev = prev;
/* 326 */       this.cursor = cursor;
/* 327 */       return success;
/*     */     }
/*     */     
/*     */     void push(Recycler.DefaultHandle item) {
/* 331 */       if ((item.recycleId | item.lastRecycledId) != 0) {
/* 332 */         throw new IllegalStateException("recycled already");
/*     */       }
/* 334 */       item.recycleId = item.lastRecycledId = Recycler.OWN_THREAD_ID;
/*     */       
/* 336 */       int size = this.size;
/* 337 */       if (size == this.elements.length) {
/* 338 */         if (size == this.maxCapacity) {
/*     */           return;
/*     */         }
/*     */         
/* 342 */         this.elements = Arrays.<Recycler.DefaultHandle>copyOf(this.elements, size << 1);
/*     */       } 
/*     */       
/* 345 */       this.elements[size] = item;
/* 346 */       this.size = size + 1;
/*     */     }
/*     */     
/*     */     Recycler.DefaultHandle newHandle() {
/* 350 */       return new Recycler.DefaultHandle(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface Handle {}
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\nett\\util\Recycler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */