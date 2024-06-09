/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
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
/*     */ public class DefaultHttpHeaders
/*     */   extends HttpHeaders
/*     */ {
/*     */   private static final int BUCKET_SIZE = 17;
/*     */   
/*     */   private static int index(int hash) {
/*  37 */     return hash % 17;
/*     */   }
/*     */   
/*  40 */   private final HeaderEntry[] entries = new HeaderEntry[17];
/*  41 */   private final HeaderEntry head = new HeaderEntry();
/*     */   protected final boolean validate;
/*     */   
/*     */   public DefaultHttpHeaders() {
/*  45 */     this(true);
/*     */   }
/*     */   
/*     */   public DefaultHttpHeaders(boolean validate) {
/*  49 */     this.validate = validate;
/*  50 */     this.head.before = this.head.after = this.head;
/*     */   }
/*     */   
/*     */   void validateHeaderName0(CharSequence headerName) {
/*  54 */     validateHeaderName(headerName);
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders add(HttpHeaders headers) {
/*  59 */     if (headers instanceof DefaultHttpHeaders) {
/*  60 */       DefaultHttpHeaders defaultHttpHeaders = (DefaultHttpHeaders)headers;
/*  61 */       HeaderEntry e = defaultHttpHeaders.head.after;
/*  62 */       while (e != defaultHttpHeaders.head) {
/*  63 */         add(e.key, e.value);
/*  64 */         e = e.after;
/*     */       } 
/*  66 */       return this;
/*     */     } 
/*  68 */     return super.add(headers);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpHeaders set(HttpHeaders headers) {
/*  74 */     if (headers instanceof DefaultHttpHeaders) {
/*  75 */       clear();
/*  76 */       DefaultHttpHeaders defaultHttpHeaders = (DefaultHttpHeaders)headers;
/*  77 */       HeaderEntry e = defaultHttpHeaders.head.after;
/*  78 */       while (e != defaultHttpHeaders.head) {
/*  79 */         add(e.key, e.value);
/*  80 */         e = e.after;
/*     */       } 
/*  82 */       return this;
/*     */     } 
/*  84 */     return super.set(headers);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpHeaders add(String name, Object value) {
/*  90 */     return add(name, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders add(CharSequence name, Object value) {
/*     */     CharSequence strVal;
/*  96 */     if (this.validate) {
/*  97 */       validateHeaderName0(name);
/*  98 */       strVal = toCharSequence(value);
/*  99 */       validateHeaderValue(strVal);
/*     */     } else {
/* 101 */       strVal = toCharSequence(value);
/*     */     } 
/* 103 */     int h = hash(name);
/* 104 */     int i = index(h);
/* 105 */     add0(h, i, name, strVal);
/* 106 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders add(String name, Iterable<?> values) {
/* 111 */     return add(name, values);
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders add(CharSequence name, Iterable<?> values) {
/* 116 */     if (this.validate) {
/* 117 */       validateHeaderName0(name);
/*     */     }
/* 119 */     int h = hash(name);
/* 120 */     int i = index(h);
/* 121 */     for (Object v : values) {
/* 122 */       CharSequence vstr = toCharSequence(v);
/* 123 */       if (this.validate) {
/* 124 */         validateHeaderValue(vstr);
/*     */       }
/* 126 */       add0(h, i, name, vstr);
/*     */     } 
/* 128 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   private void add0(int h, int i, CharSequence name, CharSequence value) {
/* 133 */     HeaderEntry e = this.entries[i];
/*     */     
/* 135 */     HeaderEntry newEntry = new HeaderEntry(h, name, value);
/* 136 */     newEntry.next = e;
/*     */ 
/*     */     
/* 139 */     newEntry.addBefore(this.head);
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders remove(String name) {
/* 144 */     return remove(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders remove(CharSequence name) {
/* 149 */     if (name == null) {
/* 150 */       throw new NullPointerException("name");
/*     */     }
/* 152 */     int h = hash(name);
/* 153 */     int i = index(h);
/* 154 */     remove0(h, i, name);
/* 155 */     return this;
/*     */   }
/*     */   
/*     */   private void remove0(int h, int i, CharSequence name) {
/* 159 */     HeaderEntry e = this.entries[i];
/* 160 */     if (e == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 165 */     while (e.hash == h && equalsIgnoreCase(name, e.key)) {
/* 166 */       e.remove();
/* 167 */       HeaderEntry next = e.next;
/* 168 */       if (next != null) {
/* 169 */         this.entries[i] = next;
/* 170 */         e = next; continue;
/*     */       } 
/* 172 */       this.entries[i] = null;
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/*     */     while (true) {
/* 181 */       HeaderEntry next = e.next;
/* 182 */       if (next == null) {
/*     */         break;
/*     */       }
/* 185 */       if (next.hash == h && equalsIgnoreCase(name, next.key)) {
/* 186 */         e.next = next.next;
/* 187 */         next.remove(); continue;
/*     */       } 
/* 189 */       e = next;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpHeaders set(String name, Object value) {
/* 196 */     return set(name, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders set(CharSequence name, Object value) {
/*     */     CharSequence strVal;
/* 202 */     if (this.validate) {
/* 203 */       validateHeaderName0(name);
/* 204 */       strVal = toCharSequence(value);
/* 205 */       validateHeaderValue(strVal);
/*     */     } else {
/* 207 */       strVal = toCharSequence(value);
/*     */     } 
/* 209 */     int h = hash(name);
/* 210 */     int i = index(h);
/* 211 */     remove0(h, i, name);
/* 212 */     add0(h, i, name, strVal);
/* 213 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders set(String name, Iterable<?> values) {
/* 218 */     return set(name, values);
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders set(CharSequence name, Iterable<?> values) {
/* 223 */     if (values == null) {
/* 224 */       throw new NullPointerException("values");
/*     */     }
/* 226 */     if (this.validate) {
/* 227 */       validateHeaderName0(name);
/*     */     }
/*     */     
/* 230 */     int h = hash(name);
/* 231 */     int i = index(h);
/*     */     
/* 233 */     remove0(h, i, name);
/* 234 */     for (Object v : values) {
/* 235 */       if (v == null) {
/*     */         break;
/*     */       }
/* 238 */       CharSequence strVal = toCharSequence(v);
/* 239 */       if (this.validate) {
/* 240 */         validateHeaderValue(strVal);
/*     */       }
/* 242 */       add0(h, i, name, strVal);
/*     */     } 
/*     */     
/* 245 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders clear() {
/* 250 */     Arrays.fill((Object[])this.entries, (Object)null);
/* 251 */     this.head.before = this.head.after = this.head;
/* 252 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String get(String name) {
/* 257 */     return get(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public String get(CharSequence name) {
/* 262 */     if (name == null) {
/* 263 */       throw new NullPointerException("name");
/*     */     }
/*     */     
/* 266 */     int h = hash(name);
/* 267 */     int i = index(h);
/* 268 */     HeaderEntry e = this.entries[i];
/* 269 */     CharSequence value = null;
/*     */     
/* 271 */     while (e != null) {
/* 272 */       if (e.hash == h && equalsIgnoreCase(name, e.key)) {
/* 273 */         value = e.value;
/*     */       }
/*     */       
/* 276 */       e = e.next;
/*     */     } 
/* 278 */     if (value == null) {
/* 279 */       return null;
/*     */     }
/* 281 */     return value.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getAll(String name) {
/* 286 */     return getAll(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getAll(CharSequence name) {
/* 291 */     if (name == null) {
/* 292 */       throw new NullPointerException("name");
/*     */     }
/*     */     
/* 295 */     LinkedList<String> values = new LinkedList<String>();
/*     */     
/* 297 */     int h = hash(name);
/* 298 */     int i = index(h);
/* 299 */     HeaderEntry e = this.entries[i];
/* 300 */     while (e != null) {
/* 301 */       if (e.hash == h && equalsIgnoreCase(name, e.key)) {
/* 302 */         values.addFirst(e.getValue());
/*     */       }
/* 304 */       e = e.next;
/*     */     } 
/* 306 */     return values;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Map.Entry<String, String>> entries() {
/* 311 */     List<Map.Entry<String, String>> all = new LinkedList<Map.Entry<String, String>>();
/*     */ 
/*     */     
/* 314 */     HeaderEntry e = this.head.after;
/* 315 */     while (e != this.head) {
/* 316 */       all.add(e);
/* 317 */       e = e.after;
/*     */     } 
/* 319 */     return all;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<Map.Entry<String, String>> iterator() {
/* 324 */     return new HeaderIterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(String name) {
/* 329 */     return (get(name) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(CharSequence name) {
/* 334 */     return (get(name) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 339 */     return (this.head == this.head.after);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(String name, String value, boolean ignoreCaseValue) {
/* 344 */     return contains(name, value, ignoreCaseValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(CharSequence name, CharSequence value, boolean ignoreCaseValue) {
/* 349 */     if (name == null) {
/* 350 */       throw new NullPointerException("name");
/*     */     }
/*     */     
/* 353 */     int h = hash(name);
/* 354 */     int i = index(h);
/* 355 */     HeaderEntry e = this.entries[i];
/* 356 */     while (e != null) {
/* 357 */       if (e.hash == h && equalsIgnoreCase(name, e.key)) {
/* 358 */         if (ignoreCaseValue) {
/* 359 */           if (equalsIgnoreCase(e.value, value)) {
/* 360 */             return true;
/*     */           }
/*     */         }
/* 363 */         else if (e.value.equals(value)) {
/* 364 */           return true;
/*     */         } 
/*     */       }
/*     */       
/* 368 */       e = e.next;
/*     */     } 
/* 370 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> names() {
/* 375 */     Set<String> names = new LinkedHashSet<String>();
/* 376 */     HeaderEntry e = this.head.after;
/* 377 */     while (e != this.head) {
/* 378 */       names.add(e.getKey());
/* 379 */       e = e.after;
/*     */     } 
/* 381 */     return names;
/*     */   }
/*     */   
/*     */   private static CharSequence toCharSequence(Object value) {
/* 385 */     if (value == null) {
/* 386 */       return null;
/*     */     }
/* 388 */     if (value instanceof CharSequence) {
/* 389 */       return (CharSequence)value;
/*     */     }
/* 391 */     if (value instanceof Number) {
/* 392 */       return value.toString();
/*     */     }
/* 394 */     if (value instanceof Date) {
/* 395 */       return HttpHeaderDateFormat.get().format((Date)value);
/*     */     }
/* 397 */     if (value instanceof Calendar) {
/* 398 */       return HttpHeaderDateFormat.get().format(((Calendar)value).getTime());
/*     */     }
/* 400 */     return value.toString();
/*     */   }
/*     */   
/*     */   void encode(ByteBuf buf) {
/* 404 */     HeaderEntry e = this.head.after;
/* 405 */     while (e != this.head) {
/* 406 */       e.encode(buf);
/* 407 */       e = e.after;
/*     */     } 
/*     */   }
/*     */   
/*     */   private final class HeaderIterator
/*     */     implements Iterator<Map.Entry<String, String>> {
/* 413 */     private DefaultHttpHeaders.HeaderEntry current = DefaultHttpHeaders.this.head;
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 417 */       return (this.current.after != DefaultHttpHeaders.this.head);
/*     */     }
/*     */ 
/*     */     
/*     */     public Map.Entry<String, String> next() {
/* 422 */       this.current = this.current.after;
/*     */       
/* 424 */       if (this.current == DefaultHttpHeaders.this.head) {
/* 425 */         throw new NoSuchElementException();
/*     */       }
/*     */       
/* 428 */       return this.current;
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 433 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     private HeaderIterator() {}
/*     */   }
/*     */   
/*     */   private final class HeaderEntry implements Map.Entry<String, String> {
/*     */     final int hash;
/*     */     final CharSequence key;
/*     */     CharSequence value;
/*     */     
/*     */     HeaderEntry(int hash, CharSequence key, CharSequence value) {
/* 445 */       this.hash = hash;
/* 446 */       this.key = key;
/* 447 */       this.value = value;
/*     */     }
/*     */     HeaderEntry next; HeaderEntry before; HeaderEntry after;
/*     */     HeaderEntry() {
/* 451 */       this.hash = -1;
/* 452 */       this.key = null;
/* 453 */       this.value = null;
/*     */     }
/*     */     
/*     */     void remove() {
/* 457 */       this.before.after = this.after;
/* 458 */       this.after.before = this.before;
/*     */     }
/*     */     
/*     */     void addBefore(HeaderEntry e) {
/* 462 */       this.after = e;
/* 463 */       this.before = e.before;
/* 464 */       this.before.after = this;
/* 465 */       this.after.before = this;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getKey() {
/* 470 */       return this.key.toString();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getValue() {
/* 475 */       return this.value.toString();
/*     */     }
/*     */ 
/*     */     
/*     */     public String setValue(String value) {
/* 480 */       if (value == null) {
/* 481 */         throw new NullPointerException("value");
/*     */       }
/* 483 */       HttpHeaders.validateHeaderValue(value);
/* 484 */       CharSequence oldValue = this.value;
/* 485 */       this.value = value;
/* 486 */       return oldValue.toString();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 491 */       return this.key.toString() + '=' + this.value.toString();
/*     */     }
/*     */     
/*     */     void encode(ByteBuf buf) {
/* 495 */       HttpHeaders.encode(this.key, this.value, buf);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\DefaultHttpHeaders.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */