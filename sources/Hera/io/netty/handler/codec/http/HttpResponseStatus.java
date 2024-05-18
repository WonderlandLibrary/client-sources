/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.util.CharsetUtil;
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
/*     */ public class HttpResponseStatus
/*     */   implements Comparable<HttpResponseStatus>
/*     */ {
/*  33 */   public static final HttpResponseStatus CONTINUE = new HttpResponseStatus(100, "Continue", true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  38 */   public static final HttpResponseStatus SWITCHING_PROTOCOLS = new HttpResponseStatus(101, "Switching Protocols", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  44 */   public static final HttpResponseStatus PROCESSING = new HttpResponseStatus(102, "Processing", true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  49 */   public static final HttpResponseStatus OK = new HttpResponseStatus(200, "OK", true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   public static final HttpResponseStatus CREATED = new HttpResponseStatus(201, "Created", true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   public static final HttpResponseStatus ACCEPTED = new HttpResponseStatus(202, "Accepted", true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   public static final HttpResponseStatus NON_AUTHORITATIVE_INFORMATION = new HttpResponseStatus(203, "Non-Authoritative Information", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   public static final HttpResponseStatus NO_CONTENT = new HttpResponseStatus(204, "No Content", true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   public static final HttpResponseStatus RESET_CONTENT = new HttpResponseStatus(205, "Reset Content", true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   public static final HttpResponseStatus PARTIAL_CONTENT = new HttpResponseStatus(206, "Partial Content", true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   public static final HttpResponseStatus MULTI_STATUS = new HttpResponseStatus(207, "Multi-Status", true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   public static final HttpResponseStatus MULTIPLE_CHOICES = new HttpResponseStatus(300, "Multiple Choices", true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   public static final HttpResponseStatus MOVED_PERMANENTLY = new HttpResponseStatus(301, "Moved Permanently", true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   public static final HttpResponseStatus FOUND = new HttpResponseStatus(302, "Found", true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 105 */   public static final HttpResponseStatus SEE_OTHER = new HttpResponseStatus(303, "See Other", true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 110 */   public static final HttpResponseStatus NOT_MODIFIED = new HttpResponseStatus(304, "Not Modified", true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   public static final HttpResponseStatus USE_PROXY = new HttpResponseStatus(305, "Use Proxy", true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 120 */   public static final HttpResponseStatus TEMPORARY_REDIRECT = new HttpResponseStatus(307, "Temporary Redirect", true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 125 */   public static final HttpResponseStatus BAD_REQUEST = new HttpResponseStatus(400, "Bad Request", true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 130 */   public static final HttpResponseStatus UNAUTHORIZED = new HttpResponseStatus(401, "Unauthorized", true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 135 */   public static final HttpResponseStatus PAYMENT_REQUIRED = new HttpResponseStatus(402, "Payment Required", true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 140 */   public static final HttpResponseStatus FORBIDDEN = new HttpResponseStatus(403, "Forbidden", true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 145 */   public static final HttpResponseStatus NOT_FOUND = new HttpResponseStatus(404, "Not Found", true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 150 */   public static final HttpResponseStatus METHOD_NOT_ALLOWED = new HttpResponseStatus(405, "Method Not Allowed", true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 155 */   public static final HttpResponseStatus NOT_ACCEPTABLE = new HttpResponseStatus(406, "Not Acceptable", true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 160 */   public static final HttpResponseStatus PROXY_AUTHENTICATION_REQUIRED = new HttpResponseStatus(407, "Proxy Authentication Required", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 166 */   public static final HttpResponseStatus REQUEST_TIMEOUT = new HttpResponseStatus(408, "Request Timeout", true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 171 */   public static final HttpResponseStatus CONFLICT = new HttpResponseStatus(409, "Conflict", true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 176 */   public static final HttpResponseStatus GONE = new HttpResponseStatus(410, "Gone", true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 181 */   public static final HttpResponseStatus LENGTH_REQUIRED = new HttpResponseStatus(411, "Length Required", true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 186 */   public static final HttpResponseStatus PRECONDITION_FAILED = new HttpResponseStatus(412, "Precondition Failed", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 192 */   public static final HttpResponseStatus REQUEST_ENTITY_TOO_LARGE = new HttpResponseStatus(413, "Request Entity Too Large", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 198 */   public static final HttpResponseStatus REQUEST_URI_TOO_LONG = new HttpResponseStatus(414, "Request-URI Too Long", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 204 */   public static final HttpResponseStatus UNSUPPORTED_MEDIA_TYPE = new HttpResponseStatus(415, "Unsupported Media Type", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 210 */   public static final HttpResponseStatus REQUESTED_RANGE_NOT_SATISFIABLE = new HttpResponseStatus(416, "Requested Range Not Satisfiable", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 216 */   public static final HttpResponseStatus EXPECTATION_FAILED = new HttpResponseStatus(417, "Expectation Failed", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 222 */   public static final HttpResponseStatus UNPROCESSABLE_ENTITY = new HttpResponseStatus(422, "Unprocessable Entity", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 228 */   public static final HttpResponseStatus LOCKED = new HttpResponseStatus(423, "Locked", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 234 */   public static final HttpResponseStatus FAILED_DEPENDENCY = new HttpResponseStatus(424, "Failed Dependency", true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 239 */   public static final HttpResponseStatus UNORDERED_COLLECTION = new HttpResponseStatus(425, "Unordered Collection", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 245 */   public static final HttpResponseStatus UPGRADE_REQUIRED = new HttpResponseStatus(426, "Upgrade Required", true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 250 */   public static final HttpResponseStatus PRECONDITION_REQUIRED = new HttpResponseStatus(428, "Precondition Required", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 256 */   public static final HttpResponseStatus TOO_MANY_REQUESTS = new HttpResponseStatus(429, "Too Many Requests", true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 261 */   public static final HttpResponseStatus REQUEST_HEADER_FIELDS_TOO_LARGE = new HttpResponseStatus(431, "Request Header Fields Too Large", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 267 */   public static final HttpResponseStatus INTERNAL_SERVER_ERROR = new HttpResponseStatus(500, "Internal Server Error", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 273 */   public static final HttpResponseStatus NOT_IMPLEMENTED = new HttpResponseStatus(501, "Not Implemented", true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 278 */   public static final HttpResponseStatus BAD_GATEWAY = new HttpResponseStatus(502, "Bad Gateway", true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 283 */   public static final HttpResponseStatus SERVICE_UNAVAILABLE = new HttpResponseStatus(503, "Service Unavailable", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 289 */   public static final HttpResponseStatus GATEWAY_TIMEOUT = new HttpResponseStatus(504, "Gateway Timeout", true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 294 */   public static final HttpResponseStatus HTTP_VERSION_NOT_SUPPORTED = new HttpResponseStatus(505, "HTTP Version Not Supported", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 300 */   public static final HttpResponseStatus VARIANT_ALSO_NEGOTIATES = new HttpResponseStatus(506, "Variant Also Negotiates", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 306 */   public static final HttpResponseStatus INSUFFICIENT_STORAGE = new HttpResponseStatus(507, "Insufficient Storage", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 312 */   public static final HttpResponseStatus NOT_EXTENDED = new HttpResponseStatus(510, "Not Extended", true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 317 */   public static final HttpResponseStatus NETWORK_AUTHENTICATION_REQUIRED = new HttpResponseStatus(511, "Network Authentication Required", true);
/*     */   
/*     */   private final int code;
/*     */   
/*     */   private final String reasonPhrase;
/*     */   private final byte[] bytes;
/*     */   
/*     */   public static HttpResponseStatus valueOf(int code) {
/*     */     String reasonPhrase;
/* 326 */     switch (code) {
/*     */       case 100:
/* 328 */         return CONTINUE;
/*     */       case 101:
/* 330 */         return SWITCHING_PROTOCOLS;
/*     */       case 102:
/* 332 */         return PROCESSING;
/*     */       case 200:
/* 334 */         return OK;
/*     */       case 201:
/* 336 */         return CREATED;
/*     */       case 202:
/* 338 */         return ACCEPTED;
/*     */       case 203:
/* 340 */         return NON_AUTHORITATIVE_INFORMATION;
/*     */       case 204:
/* 342 */         return NO_CONTENT;
/*     */       case 205:
/* 344 */         return RESET_CONTENT;
/*     */       case 206:
/* 346 */         return PARTIAL_CONTENT;
/*     */       case 207:
/* 348 */         return MULTI_STATUS;
/*     */       case 300:
/* 350 */         return MULTIPLE_CHOICES;
/*     */       case 301:
/* 352 */         return MOVED_PERMANENTLY;
/*     */       case 302:
/* 354 */         return FOUND;
/*     */       case 303:
/* 356 */         return SEE_OTHER;
/*     */       case 304:
/* 358 */         return NOT_MODIFIED;
/*     */       case 305:
/* 360 */         return USE_PROXY;
/*     */       case 307:
/* 362 */         return TEMPORARY_REDIRECT;
/*     */       case 400:
/* 364 */         return BAD_REQUEST;
/*     */       case 401:
/* 366 */         return UNAUTHORIZED;
/*     */       case 402:
/* 368 */         return PAYMENT_REQUIRED;
/*     */       case 403:
/* 370 */         return FORBIDDEN;
/*     */       case 404:
/* 372 */         return NOT_FOUND;
/*     */       case 405:
/* 374 */         return METHOD_NOT_ALLOWED;
/*     */       case 406:
/* 376 */         return NOT_ACCEPTABLE;
/*     */       case 407:
/* 378 */         return PROXY_AUTHENTICATION_REQUIRED;
/*     */       case 408:
/* 380 */         return REQUEST_TIMEOUT;
/*     */       case 409:
/* 382 */         return CONFLICT;
/*     */       case 410:
/* 384 */         return GONE;
/*     */       case 411:
/* 386 */         return LENGTH_REQUIRED;
/*     */       case 412:
/* 388 */         return PRECONDITION_FAILED;
/*     */       case 413:
/* 390 */         return REQUEST_ENTITY_TOO_LARGE;
/*     */       case 414:
/* 392 */         return REQUEST_URI_TOO_LONG;
/*     */       case 415:
/* 394 */         return UNSUPPORTED_MEDIA_TYPE;
/*     */       case 416:
/* 396 */         return REQUESTED_RANGE_NOT_SATISFIABLE;
/*     */       case 417:
/* 398 */         return EXPECTATION_FAILED;
/*     */       case 422:
/* 400 */         return UNPROCESSABLE_ENTITY;
/*     */       case 423:
/* 402 */         return LOCKED;
/*     */       case 424:
/* 404 */         return FAILED_DEPENDENCY;
/*     */       case 425:
/* 406 */         return UNORDERED_COLLECTION;
/*     */       case 426:
/* 408 */         return UPGRADE_REQUIRED;
/*     */       case 428:
/* 410 */         return PRECONDITION_REQUIRED;
/*     */       case 429:
/* 412 */         return TOO_MANY_REQUESTS;
/*     */       case 431:
/* 414 */         return REQUEST_HEADER_FIELDS_TOO_LARGE;
/*     */       case 500:
/* 416 */         return INTERNAL_SERVER_ERROR;
/*     */       case 501:
/* 418 */         return NOT_IMPLEMENTED;
/*     */       case 502:
/* 420 */         return BAD_GATEWAY;
/*     */       case 503:
/* 422 */         return SERVICE_UNAVAILABLE;
/*     */       case 504:
/* 424 */         return GATEWAY_TIMEOUT;
/*     */       case 505:
/* 426 */         return HTTP_VERSION_NOT_SUPPORTED;
/*     */       case 506:
/* 428 */         return VARIANT_ALSO_NEGOTIATES;
/*     */       case 507:
/* 430 */         return INSUFFICIENT_STORAGE;
/*     */       case 510:
/* 432 */         return NOT_EXTENDED;
/*     */       case 511:
/* 434 */         return NETWORK_AUTHENTICATION_REQUIRED;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 439 */     if (code < 100) {
/* 440 */       reasonPhrase = "Unknown Status";
/* 441 */     } else if (code < 200) {
/* 442 */       reasonPhrase = "Informational";
/* 443 */     } else if (code < 300) {
/* 444 */       reasonPhrase = "Successful";
/* 445 */     } else if (code < 400) {
/* 446 */       reasonPhrase = "Redirection";
/* 447 */     } else if (code < 500) {
/* 448 */       reasonPhrase = "Client Error";
/* 449 */     } else if (code < 600) {
/* 450 */       reasonPhrase = "Server Error";
/*     */     } else {
/* 452 */       reasonPhrase = "Unknown Status";
/*     */     } 
/*     */     
/* 455 */     return new HttpResponseStatus(code, reasonPhrase + " (" + code + ')');
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
/*     */   public HttpResponseStatus(int code, String reasonPhrase) {
/* 468 */     this(code, reasonPhrase, false);
/*     */   }
/*     */   
/*     */   private HttpResponseStatus(int code, String reasonPhrase, boolean bytes) {
/* 472 */     if (code < 0) {
/* 473 */       throw new IllegalArgumentException("code: " + code + " (expected: 0+)");
/*     */     }
/*     */ 
/*     */     
/* 477 */     if (reasonPhrase == null) {
/* 478 */       throw new NullPointerException("reasonPhrase");
/*     */     }
/*     */     
/* 481 */     for (int i = 0; i < reasonPhrase.length(); i++) {
/* 482 */       char c = reasonPhrase.charAt(i);
/*     */       
/* 484 */       switch (c) { case '\n':
/*     */         case '\r':
/* 486 */           throw new IllegalArgumentException("reasonPhrase contains one of the following prohibited characters: \\r\\n: " + reasonPhrase); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 492 */     this.code = code;
/* 493 */     this.reasonPhrase = reasonPhrase;
/* 494 */     if (bytes) {
/* 495 */       this.bytes = (code + " " + reasonPhrase).getBytes(CharsetUtil.US_ASCII);
/*     */     } else {
/* 497 */       this.bytes = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int code() {
/* 505 */     return this.code;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String reasonPhrase() {
/* 512 */     return this.reasonPhrase;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 517 */     return code();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 526 */     if (!(o instanceof HttpResponseStatus)) {
/* 527 */       return false;
/*     */     }
/*     */     
/* 530 */     return (code() == ((HttpResponseStatus)o).code());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(HttpResponseStatus o) {
/* 539 */     return code() - o.code();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 544 */     StringBuilder buf = new StringBuilder(this.reasonPhrase.length() + 5);
/* 545 */     buf.append(this.code);
/* 546 */     buf.append(' ');
/* 547 */     buf.append(this.reasonPhrase);
/* 548 */     return buf.toString();
/*     */   }
/*     */   
/*     */   void encode(ByteBuf buf) {
/* 552 */     if (this.bytes == null) {
/* 553 */       HttpHeaders.encodeAscii0(String.valueOf(code()), buf);
/* 554 */       buf.writeByte(32);
/* 555 */       HttpHeaders.encodeAscii0(String.valueOf(reasonPhrase()), buf);
/*     */     } else {
/* 557 */       buf.writeBytes(this.bytes);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\HttpResponseStatus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */