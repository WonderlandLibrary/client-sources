/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
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
/*     */ public class DefaultSpdyHeaders
/*     */   extends SpdyHeaders
/*     */ {
/*     */   private static final int BUCKET_SIZE = 17;
/*     */   
/*     */   private static int hash(String name) {
/*  33 */     int h = 0;
/*  34 */     for (int i = name.length() - 1; i >= 0; i--) {
/*  35 */       char c = name.charAt(i);
/*  36 */       if (c >= 'A' && c <= 'Z') {
/*  37 */         c = (char)(c + 32);
/*     */       }
/*  39 */       h = 31 * h + c;
/*     */     } 
/*     */     
/*  42 */     if (h > 0)
/*  43 */       return h; 
/*  44 */     if (h == Integer.MIN_VALUE) {
/*  45 */       return Integer.MAX_VALUE;
/*     */     }
/*  47 */     return -h;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean eq(String name1, String name2) {
/*  52 */     int nameLen = name1.length();
/*  53 */     if (nameLen != name2.length()) {
/*  54 */       return false;
/*     */     }
/*     */     
/*  57 */     for (int i = nameLen - 1; i >= 0; i--) {
/*  58 */       char c1 = name1.charAt(i);
/*  59 */       char c2 = name2.charAt(i);
/*  60 */       if (c1 != c2) {
/*  61 */         if (c1 >= 'A' && c1 <= 'Z') {
/*  62 */           c1 = (char)(c1 + 32);
/*     */         }
/*  64 */         if (c2 >= 'A' && c2 <= 'Z') {
/*  65 */           c2 = (char)(c2 + 32);
/*     */         }
/*  67 */         if (c1 != c2) {
/*  68 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*  72 */     return true;
/*     */   }
/*     */   
/*     */   private static int index(int hash) {
/*  76 */     return hash % 17;
/*     */   }
/*     */   
/*  79 */   private final HeaderEntry[] entries = new HeaderEntry[17];
/*  80 */   private final HeaderEntry head = new HeaderEntry(-1, null, null);
/*     */   
/*     */   DefaultSpdyHeaders() {
/*  83 */     this.head.before = this.head.after = this.head;
/*     */   }
/*     */ 
/*     */   
/*     */   public SpdyHeaders add(String name, Object value) {
/*  88 */     String lowerCaseName = name.toLowerCase();
/*  89 */     SpdyCodecUtil.validateHeaderName(lowerCaseName);
/*  90 */     String strVal = toString(value);
/*  91 */     SpdyCodecUtil.validateHeaderValue(strVal);
/*  92 */     int h = hash(lowerCaseName);
/*  93 */     int i = index(h);
/*  94 */     add0(h, i, lowerCaseName, strVal);
/*  95 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   private void add0(int h, int i, String name, String value) {
/* 100 */     HeaderEntry e = this.entries[i];
/*     */     
/* 102 */     HeaderEntry newEntry = new HeaderEntry(h, name, value);
/* 103 */     newEntry.next = e;
/*     */ 
/*     */     
/* 106 */     newEntry.addBefore(this.head);
/*     */   }
/*     */ 
/*     */   
/*     */   public SpdyHeaders remove(String name) {
/* 111 */     if (name == null) {
/* 112 */       throw new NullPointerException("name");
/*     */     }
/* 114 */     String lowerCaseName = name.toLowerCase();
/* 115 */     int h = hash(lowerCaseName);
/* 116 */     int i = index(h);
/* 117 */     remove0(h, i, lowerCaseName);
/* 118 */     return this;
/*     */   }
/*     */   
/*     */   private void remove0(int h, int i, String name) {
/* 122 */     HeaderEntry e = this.entries[i];
/* 123 */     if (e == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 128 */     while (e.hash == h && eq(name, e.key)) {
/* 129 */       e.remove();
/* 130 */       HeaderEntry next = e.next;
/* 131 */       if (next != null) {
/* 132 */         this.entries[i] = next;
/* 133 */         e = next; continue;
/*     */       } 
/* 135 */       this.entries[i] = null;
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/*     */     while (true) {
/* 144 */       HeaderEntry next = e.next;
/* 145 */       if (next == null) {
/*     */         break;
/*     */       }
/* 148 */       if (next.hash == h && eq(name, next.key)) {
/* 149 */         e.next = next.next;
/* 150 */         next.remove(); continue;
/*     */       } 
/* 152 */       e = next;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SpdyHeaders set(String name, Object value) {
/* 159 */     String lowerCaseName = name.toLowerCase();
/* 160 */     SpdyCodecUtil.validateHeaderName(lowerCaseName);
/* 161 */     String strVal = toString(value);
/* 162 */     SpdyCodecUtil.validateHeaderValue(strVal);
/* 163 */     int h = hash(lowerCaseName);
/* 164 */     int i = index(h);
/* 165 */     remove0(h, i, lowerCaseName);
/* 166 */     add0(h, i, lowerCaseName, strVal);
/* 167 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SpdyHeaders set(String name, Iterable<?> values) {
/* 172 */     if (values == null) {
/* 173 */       throw new NullPointerException("values");
/*     */     }
/*     */     
/* 176 */     String lowerCaseName = name.toLowerCase();
/* 177 */     SpdyCodecUtil.validateHeaderName(lowerCaseName);
/*     */     
/* 179 */     int h = hash(lowerCaseName);
/* 180 */     int i = index(h);
/*     */     
/* 182 */     remove0(h, i, lowerCaseName);
/* 183 */     for (Object v : values) {
/* 184 */       if (v == null) {
/*     */         break;
/*     */       }
/* 187 */       String strVal = toString(v);
/* 188 */       SpdyCodecUtil.validateHeaderValue(strVal);
/* 189 */       add0(h, i, lowerCaseName, strVal);
/*     */     } 
/* 191 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SpdyHeaders clear() {
/* 196 */     for (int i = 0; i < this.entries.length; i++) {
/* 197 */       this.entries[i] = null;
/*     */     }
/* 199 */     this.head.before = this.head.after = this.head;
/* 200 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String get(String name) {
/* 205 */     if (name == null) {
/* 206 */       throw new NullPointerException("name");
/*     */     }
/*     */     
/* 209 */     int h = hash(name);
/* 210 */     int i = index(h);
/* 211 */     HeaderEntry e = this.entries[i];
/* 212 */     while (e != null) {
/* 213 */       if (e.hash == h && eq(name, e.key)) {
/* 214 */         return e.value;
/*     */       }
/*     */       
/* 217 */       e = e.next;
/*     */     } 
/* 219 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getAll(String name) {
/* 224 */     if (name == null) {
/* 225 */       throw new NullPointerException("name");
/*     */     }
/*     */     
/* 228 */     LinkedList<String> values = new LinkedList<String>();
/*     */     
/* 230 */     int h = hash(name);
/* 231 */     int i = index(h);
/* 232 */     HeaderEntry e = this.entries[i];
/* 233 */     while (e != null) {
/* 234 */       if (e.hash == h && eq(name, e.key)) {
/* 235 */         values.addFirst(e.value);
/*     */       }
/* 237 */       e = e.next;
/*     */     } 
/* 239 */     return values;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Map.Entry<String, String>> entries() {
/* 244 */     List<Map.Entry<String, String>> all = new LinkedList<Map.Entry<String, String>>();
/*     */ 
/*     */     
/* 247 */     HeaderEntry e = this.head.after;
/* 248 */     while (e != this.head) {
/* 249 */       all.add(e);
/* 250 */       e = e.after;
/*     */     } 
/* 252 */     return all;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<Map.Entry<String, String>> iterator() {
/* 257 */     return new HeaderIterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(String name) {
/* 262 */     return (get(name) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> names() {
/* 267 */     Set<String> names = new TreeSet<String>();
/*     */     
/* 269 */     HeaderEntry e = this.head.after;
/* 270 */     while (e != this.head) {
/* 271 */       names.add(e.key);
/* 272 */       e = e.after;
/*     */     } 
/* 274 */     return names;
/*     */   }
/*     */ 
/*     */   
/*     */   public SpdyHeaders add(String name, Iterable<?> values) {
/* 279 */     SpdyCodecUtil.validateHeaderValue(name);
/* 280 */     int h = hash(name);
/* 281 */     int i = index(h);
/* 282 */     for (Object v : values) {
/* 283 */       String vstr = toString(v);
/* 284 */       SpdyCodecUtil.validateHeaderValue(vstr);
/* 285 */       add0(h, i, name, vstr);
/*     */     } 
/* 287 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 292 */     return (this.head == this.head.after);
/*     */   }
/*     */   
/*     */   private static String toString(Object value) {
/* 296 */     if (value == null) {
/* 297 */       return null;
/*     */     }
/* 299 */     return value.toString();
/*     */   }
/*     */   
/*     */   private final class HeaderIterator
/*     */     implements Iterator<Map.Entry<String, String>> {
/* 304 */     private DefaultSpdyHeaders.HeaderEntry current = DefaultSpdyHeaders.this.head;
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 308 */       return (this.current.after != DefaultSpdyHeaders.this.head);
/*     */     }
/*     */ 
/*     */     
/*     */     public Map.Entry<String, String> next() {
/* 313 */       this.current = this.current.after;
/*     */       
/* 315 */       if (this.current == DefaultSpdyHeaders.this.head) {
/* 316 */         throw new NoSuchElementException();
/*     */       }
/*     */       
/* 319 */       return this.current;
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 324 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     private HeaderIterator() {}
/*     */   }
/*     */   
/*     */   private static final class HeaderEntry implements Map.Entry<String, String> {
/*     */     final int hash;
/*     */     final String key;
/*     */     String value;
/*     */     
/*     */     HeaderEntry(int hash, String key, String value) {
/* 336 */       this.hash = hash;
/* 337 */       this.key = key;
/* 338 */       this.value = value;
/*     */     }
/*     */     HeaderEntry next; HeaderEntry before; HeaderEntry after;
/*     */     void remove() {
/* 342 */       this.before.after = this.after;
/* 343 */       this.after.before = this.before;
/*     */     }
/*     */     
/*     */     void addBefore(HeaderEntry e) {
/* 347 */       this.after = e;
/* 348 */       this.before = e.before;
/* 349 */       this.before.after = this;
/* 350 */       this.after.before = this;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getKey() {
/* 355 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getValue() {
/* 360 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public String setValue(String value) {
/* 365 */       if (value == null) {
/* 366 */         throw new NullPointerException("value");
/*     */       }
/* 368 */       SpdyCodecUtil.validateHeaderValue(value);
/* 369 */       String oldValue = this.value;
/* 370 */       this.value = value;
/* 371 */       return oldValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 376 */       return this.key + '=' + this.value;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\spdy\DefaultSpdyHeaders.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */