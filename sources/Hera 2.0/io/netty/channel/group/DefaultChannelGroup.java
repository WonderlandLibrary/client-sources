/*     */ package io.netty.channel.group;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.internal.ConcurrentSet;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
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
/*     */ public class DefaultChannelGroup
/*     */   extends AbstractSet<Channel>
/*     */   implements ChannelGroup
/*     */ {
/*  42 */   private static final AtomicInteger nextId = new AtomicInteger();
/*     */   private final String name;
/*     */   private final EventExecutor executor;
/*  45 */   private final ConcurrentSet<Channel> serverChannels = new ConcurrentSet();
/*  46 */   private final ConcurrentSet<Channel> nonServerChannels = new ConcurrentSet();
/*  47 */   private final ChannelFutureListener remover = new ChannelFutureListener()
/*     */     {
/*     */       public void operationComplete(ChannelFuture future) throws Exception {
/*  50 */         DefaultChannelGroup.this.remove(future.channel());
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultChannelGroup(EventExecutor executor) {
/*  59 */     this("group-0x" + Integer.toHexString(nextId.incrementAndGet()), executor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultChannelGroup(String name, EventExecutor executor) {
/*  68 */     if (name == null) {
/*  69 */       throw new NullPointerException("name");
/*     */     }
/*  71 */     this.name = name;
/*  72 */     this.executor = executor;
/*     */   }
/*     */ 
/*     */   
/*     */   public String name() {
/*  77 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  82 */     return (this.nonServerChannels.isEmpty() && this.serverChannels.isEmpty());
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  87 */     return this.nonServerChannels.size() + this.serverChannels.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(Object o) {
/*  92 */     if (o instanceof Channel) {
/*  93 */       Channel c = (Channel)o;
/*  94 */       if (o instanceof io.netty.channel.ServerChannel) {
/*  95 */         return this.serverChannels.contains(c);
/*     */       }
/*  97 */       return this.nonServerChannels.contains(c);
/*     */     } 
/*     */     
/* 100 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(Channel channel) {
/* 106 */     ConcurrentSet<Channel> set = (channel instanceof io.netty.channel.ServerChannel) ? this.serverChannels : this.nonServerChannels;
/*     */ 
/*     */     
/* 109 */     boolean added = set.add(channel);
/* 110 */     if (added) {
/* 111 */       channel.closeFuture().addListener((GenericFutureListener)this.remover);
/*     */     }
/* 113 */     return added;
/*     */   }
/*     */   
/*     */   public boolean remove(Object o) {
/*     */     boolean removed;
/* 118 */     if (!(o instanceof Channel)) {
/* 119 */       return false;
/*     */     }
/*     */     
/* 122 */     Channel c = (Channel)o;
/* 123 */     if (c instanceof io.netty.channel.ServerChannel) {
/* 124 */       removed = this.serverChannels.remove(c);
/*     */     } else {
/* 126 */       removed = this.nonServerChannels.remove(c);
/*     */     } 
/* 128 */     if (!removed) {
/* 129 */       return false;
/*     */     }
/*     */     
/* 132 */     c.closeFuture().removeListener((GenericFutureListener)this.remover);
/* 133 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 138 */     this.nonServerChannels.clear();
/* 139 */     this.serverChannels.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<Channel> iterator() {
/* 144 */     return new CombinedIterator<Channel>(this.serverChannels.iterator(), this.nonServerChannels.iterator());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] toArray() {
/* 151 */     Collection<Channel> channels = new ArrayList<Channel>(size());
/* 152 */     channels.addAll((Collection<? extends Channel>)this.serverChannels);
/* 153 */     channels.addAll((Collection<? extends Channel>)this.nonServerChannels);
/* 154 */     return channels.toArray();
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T[] toArray(T[] a) {
/* 159 */     Collection<Channel> channels = new ArrayList<Channel>(size());
/* 160 */     channels.addAll((Collection<? extends Channel>)this.serverChannels);
/* 161 */     channels.addAll((Collection<? extends Channel>)this.nonServerChannels);
/* 162 */     return channels.toArray(a);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelGroupFuture close() {
/* 167 */     return close(ChannelMatchers.all());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelGroupFuture disconnect() {
/* 172 */     return disconnect(ChannelMatchers.all());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelGroupFuture deregister() {
/* 177 */     return deregister(ChannelMatchers.all());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelGroupFuture write(Object message) {
/* 182 */     return write(message, ChannelMatchers.all());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object safeDuplicate(Object message) {
/* 188 */     if (message instanceof ByteBuf)
/* 189 */       return ((ByteBuf)message).duplicate().retain(); 
/* 190 */     if (message instanceof ByteBufHolder) {
/* 191 */       return ((ByteBufHolder)message).duplicate().retain();
/*     */     }
/* 193 */     return ReferenceCountUtil.retain(message);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelGroupFuture write(Object message, ChannelMatcher matcher) {
/* 199 */     if (message == null) {
/* 200 */       throw new NullPointerException("message");
/*     */     }
/* 202 */     if (matcher == null) {
/* 203 */       throw new NullPointerException("matcher");
/*     */     }
/*     */     
/* 206 */     Map<Channel, ChannelFuture> futures = new LinkedHashMap<Channel, ChannelFuture>(size());
/* 207 */     for (Channel c : this.nonServerChannels) {
/* 208 */       if (matcher.matches(c)) {
/* 209 */         futures.put(c, c.write(safeDuplicate(message)));
/*     */       }
/*     */     } 
/*     */     
/* 213 */     ReferenceCountUtil.release(message);
/* 214 */     return new DefaultChannelGroupFuture(this, futures, this.executor);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelGroup flush() {
/* 219 */     return flush(ChannelMatchers.all());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelGroupFuture flushAndWrite(Object message) {
/* 224 */     return writeAndFlush(message);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelGroupFuture writeAndFlush(Object message) {
/* 229 */     return writeAndFlush(message, ChannelMatchers.all());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelGroupFuture disconnect(ChannelMatcher matcher) {
/* 234 */     if (matcher == null) {
/* 235 */       throw new NullPointerException("matcher");
/*     */     }
/*     */     
/* 238 */     Map<Channel, ChannelFuture> futures = new LinkedHashMap<Channel, ChannelFuture>(size());
/*     */ 
/*     */     
/* 241 */     for (Channel c : this.serverChannels) {
/* 242 */       if (matcher.matches(c)) {
/* 243 */         futures.put(c, c.disconnect());
/*     */       }
/*     */     } 
/* 246 */     for (Channel c : this.nonServerChannels) {
/* 247 */       if (matcher.matches(c)) {
/* 248 */         futures.put(c, c.disconnect());
/*     */       }
/*     */     } 
/*     */     
/* 252 */     return new DefaultChannelGroupFuture(this, futures, this.executor);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelGroupFuture close(ChannelMatcher matcher) {
/* 257 */     if (matcher == null) {
/* 258 */       throw new NullPointerException("matcher");
/*     */     }
/*     */     
/* 261 */     Map<Channel, ChannelFuture> futures = new LinkedHashMap<Channel, ChannelFuture>(size());
/*     */ 
/*     */     
/* 264 */     for (Channel c : this.serverChannels) {
/* 265 */       if (matcher.matches(c)) {
/* 266 */         futures.put(c, c.close());
/*     */       }
/*     */     } 
/* 269 */     for (Channel c : this.nonServerChannels) {
/* 270 */       if (matcher.matches(c)) {
/* 271 */         futures.put(c, c.close());
/*     */       }
/*     */     } 
/*     */     
/* 275 */     return new DefaultChannelGroupFuture(this, futures, this.executor);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelGroupFuture deregister(ChannelMatcher matcher) {
/* 280 */     if (matcher == null) {
/* 281 */       throw new NullPointerException("matcher");
/*     */     }
/*     */     
/* 284 */     Map<Channel, ChannelFuture> futures = new LinkedHashMap<Channel, ChannelFuture>(size());
/*     */ 
/*     */     
/* 287 */     for (Channel c : this.serverChannels) {
/* 288 */       if (matcher.matches(c)) {
/* 289 */         futures.put(c, c.deregister());
/*     */       }
/*     */     } 
/* 292 */     for (Channel c : this.nonServerChannels) {
/* 293 */       if (matcher.matches(c)) {
/* 294 */         futures.put(c, c.deregister());
/*     */       }
/*     */     } 
/*     */     
/* 298 */     return new DefaultChannelGroupFuture(this, futures, this.executor);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelGroup flush(ChannelMatcher matcher) {
/* 303 */     for (Channel c : this.nonServerChannels) {
/* 304 */       if (matcher.matches(c)) {
/* 305 */         c.flush();
/*     */       }
/*     */     } 
/* 308 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelGroupFuture flushAndWrite(Object message, ChannelMatcher matcher) {
/* 313 */     return writeAndFlush(message, matcher);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelGroupFuture writeAndFlush(Object message, ChannelMatcher matcher) {
/* 318 */     if (message == null) {
/* 319 */       throw new NullPointerException("message");
/*     */     }
/*     */     
/* 322 */     Map<Channel, ChannelFuture> futures = new LinkedHashMap<Channel, ChannelFuture>(size());
/*     */     
/* 324 */     for (Channel c : this.nonServerChannels) {
/* 325 */       if (matcher.matches(c)) {
/* 326 */         futures.put(c, c.writeAndFlush(safeDuplicate(message)));
/*     */       }
/*     */     } 
/*     */     
/* 330 */     ReferenceCountUtil.release(message);
/*     */     
/* 332 */     return new DefaultChannelGroupFuture(this, futures, this.executor);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 337 */     return System.identityHashCode(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 342 */     return (this == o);
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(ChannelGroup o) {
/* 347 */     int v = name().compareTo(o.name());
/* 348 */     if (v != 0) {
/* 349 */       return v;
/*     */     }
/*     */     
/* 352 */     return System.identityHashCode(this) - System.identityHashCode(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 357 */     return StringUtil.simpleClassName(this) + "(name: " + name() + ", size: " + size() + ')';
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\group\DefaultChannelGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */