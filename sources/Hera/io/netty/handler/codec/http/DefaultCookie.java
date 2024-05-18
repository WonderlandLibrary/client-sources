/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
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
/*     */ 
/*     */ 
/*     */ public class DefaultCookie
/*     */   implements Cookie
/*     */ {
/*     */   private final String name;
/*     */   private String value;
/*     */   private String domain;
/*     */   private String path;
/*     */   private String comment;
/*     */   private String commentUrl;
/*     */   private boolean discard;
/*  36 */   private Set<Integer> ports = Collections.emptySet();
/*  37 */   private Set<Integer> unmodifiablePorts = this.ports;
/*  38 */   private long maxAge = Long.MIN_VALUE;
/*     */   
/*     */   private int version;
/*     */   
/*     */   private boolean secure;
/*     */   
/*     */   private boolean httpOnly;
/*     */   
/*     */   public DefaultCookie(String name, String value) {
/*  47 */     if (name == null) {
/*  48 */       throw new NullPointerException("name");
/*     */     }
/*  50 */     name = name.trim();
/*  51 */     if (name.isEmpty()) {
/*  52 */       throw new IllegalArgumentException("empty name");
/*     */     }
/*     */     
/*  55 */     for (int i = 0; i < name.length(); i++) {
/*  56 */       char c = name.charAt(i);
/*  57 */       if (c > '') {
/*  58 */         throw new IllegalArgumentException("name contains non-ascii character: " + name);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*  63 */       switch (c) { case '\t': case '\n': case '\013': case '\f': case '\r': case ' ': case ',':
/*     */         case ';':
/*     */         case '=':
/*  66 */           throw new IllegalArgumentException("name contains one of the following prohibited characters: =,; \\t\\r\\n\\v\\f: " + name); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/*  72 */     if (name.charAt(0) == '$') {
/*  73 */       throw new IllegalArgumentException("name starting with '$' not allowed: " + name);
/*     */     }
/*     */     
/*  76 */     this.name = name;
/*  77 */     setValue(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  82 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValue() {
/*  87 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValue(String value) {
/*  92 */     if (value == null) {
/*  93 */       throw new NullPointerException("value");
/*     */     }
/*  95 */     this.value = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDomain() {
/* 100 */     return this.domain;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDomain(String domain) {
/* 105 */     this.domain = validateValue("domain", domain);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPath() {
/* 110 */     return this.path;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPath(String path) {
/* 115 */     this.path = validateValue("path", path);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getComment() {
/* 120 */     return this.comment;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setComment(String comment) {
/* 125 */     this.comment = validateValue("comment", comment);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getCommentUrl() {
/* 130 */     return this.commentUrl;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCommentUrl(String commentUrl) {
/* 135 */     this.commentUrl = validateValue("commentUrl", commentUrl);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDiscard() {
/* 140 */     return this.discard;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDiscard(boolean discard) {
/* 145 */     this.discard = discard;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<Integer> getPorts() {
/* 150 */     if (this.unmodifiablePorts == null) {
/* 151 */       this.unmodifiablePorts = Collections.unmodifiableSet(this.ports);
/*     */     }
/* 153 */     return this.unmodifiablePorts;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPorts(int... ports) {
/* 158 */     if (ports == null) {
/* 159 */       throw new NullPointerException("ports");
/*     */     }
/*     */     
/* 162 */     int[] portsCopy = (int[])ports.clone();
/* 163 */     if (portsCopy.length == 0) {
/* 164 */       this.unmodifiablePorts = this.ports = Collections.emptySet();
/*     */     } else {
/* 166 */       Set<Integer> newPorts = new TreeSet<Integer>();
/* 167 */       for (int p : portsCopy) {
/* 168 */         if (p <= 0 || p > 65535) {
/* 169 */           throw new IllegalArgumentException("port out of range: " + p);
/*     */         }
/* 171 */         newPorts.add(Integer.valueOf(p));
/*     */       } 
/* 173 */       this.ports = newPorts;
/* 174 */       this.unmodifiablePorts = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPorts(Iterable<Integer> ports) {
/* 180 */     Set<Integer> newPorts = new TreeSet<Integer>();
/* 181 */     for (Iterator<Integer> i$ = ports.iterator(); i$.hasNext(); ) { int p = ((Integer)i$.next()).intValue();
/* 182 */       if (p <= 0 || p > 65535) {
/* 183 */         throw new IllegalArgumentException("port out of range: " + p);
/*     */       }
/* 185 */       newPorts.add(Integer.valueOf(p)); }
/*     */     
/* 187 */     if (newPorts.isEmpty()) {
/* 188 */       this.unmodifiablePorts = this.ports = Collections.emptySet();
/*     */     } else {
/* 190 */       this.ports = newPorts;
/* 191 */       this.unmodifiablePorts = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public long getMaxAge() {
/* 197 */     return this.maxAge;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMaxAge(long maxAge) {
/* 202 */     this.maxAge = maxAge;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getVersion() {
/* 207 */     return this.version;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVersion(int version) {
/* 212 */     this.version = version;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSecure() {
/* 217 */     return this.secure;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSecure(boolean secure) {
/* 222 */     this.secure = secure;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHttpOnly() {
/* 227 */     return this.httpOnly;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHttpOnly(boolean httpOnly) {
/* 232 */     this.httpOnly = httpOnly;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 237 */     return getName().hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 242 */     if (!(o instanceof Cookie)) {
/* 243 */       return false;
/*     */     }
/*     */     
/* 246 */     Cookie that = (Cookie)o;
/* 247 */     if (!getName().equalsIgnoreCase(that.getName())) {
/* 248 */       return false;
/*     */     }
/*     */     
/* 251 */     if (getPath() == null) {
/* 252 */       if (that.getPath() != null)
/* 253 */         return false; 
/*     */     } else {
/* 255 */       if (that.getPath() == null)
/* 256 */         return false; 
/* 257 */       if (!getPath().equals(that.getPath())) {
/* 258 */         return false;
/*     */       }
/*     */     } 
/* 261 */     if (getDomain() == null) {
/* 262 */       if (that.getDomain() != null)
/* 263 */         return false; 
/*     */     } else {
/* 265 */       if (that.getDomain() == null) {
/* 266 */         return false;
/*     */       }
/* 268 */       return getDomain().equalsIgnoreCase(that.getDomain());
/*     */     } 
/*     */     
/* 271 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(Cookie c) {
/* 277 */     int v = getName().compareToIgnoreCase(c.getName());
/* 278 */     if (v != 0) {
/* 279 */       return v;
/*     */     }
/*     */     
/* 282 */     if (getPath() == null) {
/* 283 */       if (c.getPath() != null)
/* 284 */         return -1; 
/*     */     } else {
/* 286 */       if (c.getPath() == null) {
/* 287 */         return 1;
/*     */       }
/* 289 */       v = getPath().compareTo(c.getPath());
/* 290 */       if (v != 0) {
/* 291 */         return v;
/*     */       }
/*     */     } 
/*     */     
/* 295 */     if (getDomain() == null) {
/* 296 */       if (c.getDomain() != null)
/* 297 */         return -1; 
/*     */     } else {
/* 299 */       if (c.getDomain() == null) {
/* 300 */         return 1;
/*     */       }
/* 302 */       v = getDomain().compareToIgnoreCase(c.getDomain());
/* 303 */       return v;
/*     */     } 
/*     */     
/* 306 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 311 */     StringBuilder buf = new StringBuilder();
/* 312 */     buf.append(getName());
/* 313 */     buf.append('=');
/* 314 */     buf.append(getValue());
/* 315 */     if (getDomain() != null) {
/* 316 */       buf.append(", domain=");
/* 317 */       buf.append(getDomain());
/*     */     } 
/* 319 */     if (getPath() != null) {
/* 320 */       buf.append(", path=");
/* 321 */       buf.append(getPath());
/*     */     } 
/* 323 */     if (getComment() != null) {
/* 324 */       buf.append(", comment=");
/* 325 */       buf.append(getComment());
/*     */     } 
/* 327 */     if (getMaxAge() >= 0L) {
/* 328 */       buf.append(", maxAge=");
/* 329 */       buf.append(getMaxAge());
/* 330 */       buf.append('s');
/*     */     } 
/* 332 */     if (isSecure()) {
/* 333 */       buf.append(", secure");
/*     */     }
/* 335 */     if (isHttpOnly()) {
/* 336 */       buf.append(", HTTPOnly");
/*     */     }
/* 338 */     return buf.toString();
/*     */   }
/*     */   
/*     */   private static String validateValue(String name, String value) {
/* 342 */     if (value == null) {
/* 343 */       return null;
/*     */     }
/* 345 */     value = value.trim();
/* 346 */     if (value.isEmpty()) {
/* 347 */       return null;
/*     */     }
/* 349 */     for (int i = 0; i < value.length(); i++) {
/* 350 */       char c = value.charAt(i);
/* 351 */       switch (c) { case '\n': case '\013': case '\f': case '\r':
/*     */         case ';':
/* 353 */           throw new IllegalArgumentException(name + " contains one of the following prohibited characters: " + ";\\r\\n\\f\\v (" + value + ')'); }
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 358 */     return value;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\DefaultCookie.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */