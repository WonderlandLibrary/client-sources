/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ReadOnlyBufferException;
/*     */ import java.security.Principal;
/*     */ import java.security.cert.Certificate;
/*     */ import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
/*     */ import javax.net.ssl.SSLEngine;
/*     */ import javax.net.ssl.SSLEngineResult;
/*     */ import javax.net.ssl.SSLException;
/*     */ import javax.net.ssl.SSLSession;
/*     */ import javax.net.ssl.SSLSessionContext;
/*     */ import javax.security.cert.X509Certificate;
/*     */ import org.apache.tomcat.jni.Buffer;
/*     */ import org.apache.tomcat.jni.SSL;
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
/*     */ public final class OpenSslEngine
/*     */   extends SSLEngine
/*     */ {
/*  47 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(OpenSslEngine.class);
/*     */   
/*  49 */   private static final Certificate[] EMPTY_CERTIFICATES = new Certificate[0];
/*  50 */   private static final X509Certificate[] EMPTY_X509_CERTIFICATES = new X509Certificate[0];
/*     */   
/*  52 */   private static final SSLException ENGINE_CLOSED = new SSLException("engine closed");
/*  53 */   private static final SSLException RENEGOTIATION_UNSUPPORTED = new SSLException("renegotiation unsupported");
/*  54 */   private static final SSLException ENCRYPTED_PACKET_OVERSIZED = new SSLException("encrypted packet oversized");
/*     */   
/*     */   static {
/*  57 */     ENGINE_CLOSED.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
/*  58 */     RENEGOTIATION_UNSUPPORTED.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
/*  59 */     ENCRYPTED_PACKET_OVERSIZED.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
/*     */   }
/*     */ 
/*     */   
/*     */   private static final int MAX_PLAINTEXT_LENGTH = 16384;
/*     */   
/*     */   private static final int MAX_COMPRESSED_LENGTH = 17408;
/*     */   
/*     */   private static final int MAX_CIPHERTEXT_LENGTH = 18432;
/*     */   
/*     */   static final int MAX_ENCRYPTED_PACKET_LENGTH = 18713;
/*     */   static final int MAX_ENCRYPTION_OVERHEAD_LENGTH = 2329;
/*  71 */   private static final AtomicIntegerFieldUpdater<OpenSslEngine> DESTROYED_UPDATER = AtomicIntegerFieldUpdater.newUpdater(OpenSslEngine.class, "destroyed");
/*     */ 
/*     */   
/*     */   private long ssl;
/*     */ 
/*     */   
/*     */   private long networkBIO;
/*     */ 
/*     */   
/*     */   private int accepted;
/*     */   
/*     */   private boolean handshakeFinished;
/*     */   
/*     */   private boolean receivedShutdown;
/*     */   
/*     */   private volatile int destroyed;
/*     */   
/*     */   private String cipher;
/*     */   
/*     */   private volatile String applicationProtocol;
/*     */   
/*     */   private boolean isInboundDone;
/*     */   
/*     */   private boolean isOutboundDone;
/*     */   
/*     */   private boolean engineClosed;
/*     */   
/*     */   private int lastPrimingReadResult;
/*     */   
/*     */   private final ByteBufAllocator alloc;
/*     */   
/*     */   private final String fallbackApplicationProtocol;
/*     */   
/*     */   private SSLSession session;
/*     */ 
/*     */   
/*     */   public OpenSslEngine(long sslCtx, ByteBufAllocator alloc, String fallbackApplicationProtocol) {
/* 108 */     OpenSsl.ensureAvailability();
/* 109 */     if (sslCtx == 0L) {
/* 110 */       throw new NullPointerException("sslContext");
/*     */     }
/* 112 */     if (alloc == null) {
/* 113 */       throw new NullPointerException("alloc");
/*     */     }
/*     */     
/* 116 */     this.alloc = alloc;
/* 117 */     this.ssl = SSL.newSSL(sslCtx, true);
/* 118 */     this.networkBIO = SSL.makeNetworkBIO(this.ssl);
/* 119 */     this.fallbackApplicationProtocol = fallbackApplicationProtocol;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void shutdown() {
/* 126 */     if (DESTROYED_UPDATER.compareAndSet(this, 0, 1)) {
/* 127 */       SSL.freeSSL(this.ssl);
/* 128 */       SSL.freeBIO(this.networkBIO);
/* 129 */       this.ssl = this.networkBIO = 0L;
/*     */ 
/*     */       
/* 132 */       this.isInboundDone = this.isOutboundDone = this.engineClosed = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int writePlaintextData(ByteBuffer src) {
/* 142 */     int sslWrote, pos = src.position();
/* 143 */     int limit = src.limit();
/* 144 */     int len = Math.min(limit - pos, 16384);
/*     */ 
/*     */     
/* 147 */     if (src.isDirect()) {
/* 148 */       long addr = Buffer.address(src) + pos;
/* 149 */       sslWrote = SSL.writeToSSL(this.ssl, addr, len);
/* 150 */       if (sslWrote > 0) {
/* 151 */         src.position(pos + sslWrote);
/* 152 */         return sslWrote;
/*     */       } 
/*     */     } else {
/* 155 */       ByteBuf buf = this.alloc.directBuffer(len);
/*     */       try {
/*     */         long addr;
/* 158 */         if (buf.hasMemoryAddress()) {
/* 159 */           addr = buf.memoryAddress();
/*     */         } else {
/* 161 */           addr = Buffer.address(buf.nioBuffer());
/*     */         } 
/*     */         
/* 164 */         src.limit(pos + len);
/*     */         
/* 166 */         buf.setBytes(0, src);
/* 167 */         src.limit(limit);
/*     */         
/* 169 */         sslWrote = SSL.writeToSSL(this.ssl, addr, len);
/* 170 */         if (sslWrote > 0) {
/* 171 */           src.position(pos + sslWrote);
/* 172 */           return sslWrote;
/*     */         } 
/* 174 */         src.position(pos);
/*     */       } finally {
/*     */         
/* 177 */         buf.release();
/*     */       } 
/*     */     } 
/*     */     
/* 181 */     throw new IllegalStateException("SSL.writeToSSL() returned a non-positive value: " + sslWrote);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int writeEncryptedData(ByteBuffer src) {
/* 188 */     int pos = src.position();
/* 189 */     int len = src.remaining();
/* 190 */     if (src.isDirect()) {
/* 191 */       long addr = Buffer.address(src) + pos;
/* 192 */       int netWrote = SSL.writeToBIO(this.networkBIO, addr, len);
/* 193 */       if (netWrote >= 0) {
/* 194 */         src.position(pos + netWrote);
/* 195 */         this.lastPrimingReadResult = SSL.readFromSSL(this.ssl, addr, 0);
/* 196 */         return netWrote;
/*     */       } 
/*     */     } else {
/* 199 */       ByteBuf buf = this.alloc.directBuffer(len);
/*     */       try {
/*     */         long addr;
/* 202 */         if (buf.hasMemoryAddress()) {
/* 203 */           addr = buf.memoryAddress();
/*     */         } else {
/* 205 */           addr = Buffer.address(buf.nioBuffer());
/*     */         } 
/*     */         
/* 208 */         buf.setBytes(0, src);
/*     */         
/* 210 */         int netWrote = SSL.writeToBIO(this.networkBIO, addr, len);
/* 211 */         if (netWrote >= 0) {
/* 212 */           src.position(pos + netWrote);
/* 213 */           this.lastPrimingReadResult = SSL.readFromSSL(this.ssl, addr, 0);
/* 214 */           return netWrote;
/*     */         } 
/* 216 */         src.position(pos);
/*     */       } finally {
/*     */         
/* 219 */         buf.release();
/*     */       } 
/*     */     } 
/*     */     
/* 223 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int readPlaintextData(ByteBuffer dst) {
/* 230 */     if (dst.isDirect()) {
/* 231 */       int pos = dst.position();
/* 232 */       long addr = Buffer.address(dst) + pos;
/* 233 */       int len = dst.limit() - pos;
/* 234 */       int sslRead = SSL.readFromSSL(this.ssl, addr, len);
/* 235 */       if (sslRead > 0) {
/* 236 */         dst.position(pos + sslRead);
/* 237 */         return sslRead;
/*     */       } 
/*     */     } else {
/* 240 */       int pos = dst.position();
/* 241 */       int limit = dst.limit();
/* 242 */       int len = Math.min(18713, limit - pos);
/* 243 */       ByteBuf buf = this.alloc.directBuffer(len);
/*     */       try {
/*     */         long addr;
/* 246 */         if (buf.hasMemoryAddress()) {
/* 247 */           addr = buf.memoryAddress();
/*     */         } else {
/* 249 */           addr = Buffer.address(buf.nioBuffer());
/*     */         } 
/*     */         
/* 252 */         int sslRead = SSL.readFromSSL(this.ssl, addr, len);
/* 253 */         if (sslRead > 0) {
/* 254 */           dst.limit(pos + sslRead);
/* 255 */           buf.getBytes(0, dst);
/* 256 */           dst.limit(limit);
/* 257 */           return sslRead;
/*     */         } 
/*     */       } finally {
/* 260 */         buf.release();
/*     */       } 
/*     */     } 
/*     */     
/* 264 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int readEncryptedData(ByteBuffer dst, int pending) {
/* 271 */     if (dst.isDirect() && dst.remaining() >= pending) {
/* 272 */       int pos = dst.position();
/* 273 */       long addr = Buffer.address(dst) + pos;
/* 274 */       int bioRead = SSL.readFromBIO(this.networkBIO, addr, pending);
/* 275 */       if (bioRead > 0) {
/* 276 */         dst.position(pos + bioRead);
/* 277 */         return bioRead;
/*     */       } 
/*     */     } else {
/* 280 */       ByteBuf buf = this.alloc.directBuffer(pending);
/*     */       try {
/*     */         long addr;
/* 283 */         if (buf.hasMemoryAddress()) {
/* 284 */           addr = buf.memoryAddress();
/*     */         } else {
/* 286 */           addr = Buffer.address(buf.nioBuffer());
/*     */         } 
/*     */         
/* 289 */         int bioRead = SSL.readFromBIO(this.networkBIO, addr, pending);
/* 290 */         if (bioRead > 0) {
/* 291 */           int oldLimit = dst.limit();
/* 292 */           dst.limit(dst.position() + bioRead);
/* 293 */           buf.getBytes(0, dst);
/* 294 */           dst.limit(oldLimit);
/* 295 */           return bioRead;
/*     */         } 
/*     */       } finally {
/* 298 */         buf.release();
/*     */       } 
/*     */     } 
/*     */     
/* 302 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized SSLEngineResult wrap(ByteBuffer[] srcs, int offset, int length, ByteBuffer dst) throws SSLException {
/* 310 */     if (this.destroyed != 0) {
/* 311 */       return new SSLEngineResult(SSLEngineResult.Status.CLOSED, SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING, 0, 0);
/*     */     }
/*     */ 
/*     */     
/* 315 */     if (srcs == null) {
/* 316 */       throw new NullPointerException("srcs");
/*     */     }
/* 318 */     if (dst == null) {
/* 319 */       throw new NullPointerException("dst");
/*     */     }
/*     */     
/* 322 */     if (offset >= srcs.length || offset + length > srcs.length) {
/* 323 */       throw new IndexOutOfBoundsException("offset: " + offset + ", length: " + length + " (expected: offset <= offset + length <= srcs.length (" + srcs.length + "))");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 328 */     if (dst.isReadOnly()) {
/* 329 */       throw new ReadOnlyBufferException();
/*     */     }
/*     */ 
/*     */     
/* 333 */     if (this.accepted == 0) {
/* 334 */       beginHandshakeImplicitly();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 339 */     SSLEngineResult.HandshakeStatus handshakeStatus = getHandshakeStatus();
/* 340 */     if ((!this.handshakeFinished || this.engineClosed) && handshakeStatus == SSLEngineResult.HandshakeStatus.NEED_UNWRAP) {
/* 341 */       return new SSLEngineResult(getEngineStatus(), SSLEngineResult.HandshakeStatus.NEED_UNWRAP, 0, 0);
/*     */     }
/*     */     
/* 344 */     int bytesProduced = 0;
/*     */ 
/*     */ 
/*     */     
/* 348 */     int pendingNet = SSL.pendingWrittenBytesInBIO(this.networkBIO);
/* 349 */     if (pendingNet > 0) {
/*     */       
/* 351 */       int capacity = dst.remaining();
/* 352 */       if (capacity < pendingNet) {
/* 353 */         return new SSLEngineResult(SSLEngineResult.Status.BUFFER_OVERFLOW, handshakeStatus, 0, bytesProduced);
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/* 358 */         bytesProduced += readEncryptedData(dst, pendingNet);
/* 359 */       } catch (Exception e) {
/* 360 */         throw new SSLException(e);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 366 */       if (this.isOutboundDone) {
/* 367 */         shutdown();
/*     */       }
/*     */       
/* 370 */       return new SSLEngineResult(getEngineStatus(), getHandshakeStatus(), 0, bytesProduced);
/*     */     } 
/*     */ 
/*     */     
/* 374 */     int bytesConsumed = 0;
/* 375 */     for (int i = offset; i < length; i++) {
/* 376 */       ByteBuffer src = srcs[i];
/* 377 */       while (src.hasRemaining()) {
/*     */ 
/*     */         
/*     */         try {
/* 381 */           bytesConsumed += writePlaintextData(src);
/* 382 */         } catch (Exception e) {
/* 383 */           throw new SSLException(e);
/*     */         } 
/*     */ 
/*     */         
/* 387 */         pendingNet = SSL.pendingWrittenBytesInBIO(this.networkBIO);
/* 388 */         if (pendingNet > 0) {
/*     */           
/* 390 */           int capacity = dst.remaining();
/* 391 */           if (capacity < pendingNet) {
/* 392 */             return new SSLEngineResult(SSLEngineResult.Status.BUFFER_OVERFLOW, getHandshakeStatus(), bytesConsumed, bytesProduced);
/*     */           }
/*     */ 
/*     */           
/*     */           try {
/* 397 */             bytesProduced += readEncryptedData(dst, pendingNet);
/* 398 */           } catch (Exception e) {
/* 399 */             throw new SSLException(e);
/*     */           } 
/*     */           
/* 402 */           return new SSLEngineResult(getEngineStatus(), getHandshakeStatus(), bytesConsumed, bytesProduced);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 407 */     return new SSLEngineResult(getEngineStatus(), getHandshakeStatus(), bytesConsumed, bytesProduced);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized SSLEngineResult unwrap(ByteBuffer src, ByteBuffer[] dsts, int offset, int length) throws SSLException {
/* 415 */     if (this.destroyed != 0) {
/* 416 */       return new SSLEngineResult(SSLEngineResult.Status.CLOSED, SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING, 0, 0);
/*     */     }
/*     */ 
/*     */     
/* 420 */     if (src == null) {
/* 421 */       throw new NullPointerException("src");
/*     */     }
/* 423 */     if (dsts == null) {
/* 424 */       throw new NullPointerException("dsts");
/*     */     }
/* 426 */     if (offset >= dsts.length || offset + length > dsts.length) {
/* 427 */       throw new IndexOutOfBoundsException("offset: " + offset + ", length: " + length + " (expected: offset <= offset + length <= dsts.length (" + dsts.length + "))");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 432 */     int capacity = 0;
/* 433 */     int endOffset = offset + length;
/* 434 */     for (int i = offset; i < endOffset; i++) {
/* 435 */       ByteBuffer dst = dsts[i];
/* 436 */       if (dst == null) {
/* 437 */         throw new IllegalArgumentException();
/*     */       }
/* 439 */       if (dst.isReadOnly()) {
/* 440 */         throw new ReadOnlyBufferException();
/*     */       }
/* 442 */       capacity += dst.remaining();
/*     */     } 
/*     */ 
/*     */     
/* 446 */     if (this.accepted == 0) {
/* 447 */       beginHandshakeImplicitly();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 452 */     SSLEngineResult.HandshakeStatus handshakeStatus = getHandshakeStatus();
/* 453 */     if ((!this.handshakeFinished || this.engineClosed) && handshakeStatus == SSLEngineResult.HandshakeStatus.NEED_WRAP) {
/* 454 */       return new SSLEngineResult(getEngineStatus(), SSLEngineResult.HandshakeStatus.NEED_WRAP, 0, 0);
/*     */     }
/*     */ 
/*     */     
/* 458 */     if (src.remaining() > 18713) {
/* 459 */       this.isInboundDone = true;
/* 460 */       this.isOutboundDone = true;
/* 461 */       this.engineClosed = true;
/* 462 */       shutdown();
/* 463 */       throw ENCRYPTED_PACKET_OVERSIZED;
/*     */     } 
/*     */ 
/*     */     
/* 467 */     int bytesConsumed = 0;
/* 468 */     this.lastPrimingReadResult = 0;
/*     */     try {
/* 470 */       bytesConsumed += writeEncryptedData(src);
/* 471 */     } catch (Exception e) {
/* 472 */       throw new SSLException(e);
/*     */     } 
/*     */ 
/*     */     
/* 476 */     String error = SSL.getLastError();
/* 477 */     if (error != null && !error.startsWith("error:00000000:")) {
/* 478 */       if (logger.isInfoEnabled()) {
/* 479 */         logger.info("SSL_read failed: primingReadResult: " + this.lastPrimingReadResult + "; OpenSSL error: '" + error + '\'');
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 485 */       shutdown();
/* 486 */       throw new SSLException(error);
/*     */     } 
/*     */ 
/*     */     
/* 490 */     int pendingApp = (SSL.isInInit(this.ssl) == 0) ? SSL.pendingReadableBytesInSSL(this.ssl) : 0;
/*     */ 
/*     */     
/* 493 */     if (capacity < pendingApp) {
/* 494 */       return new SSLEngineResult(SSLEngineResult.Status.BUFFER_OVERFLOW, getHandshakeStatus(), bytesConsumed, 0);
/*     */     }
/*     */ 
/*     */     
/* 498 */     int bytesProduced = 0;
/* 499 */     int idx = offset;
/* 500 */     while (idx < endOffset) {
/* 501 */       int bytesRead; ByteBuffer dst = dsts[idx];
/* 502 */       if (!dst.hasRemaining()) {
/* 503 */         idx++;
/*     */         
/*     */         continue;
/*     */       } 
/* 507 */       if (pendingApp <= 0) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/* 513 */         bytesRead = readPlaintextData(dst);
/* 514 */       } catch (Exception e) {
/* 515 */         throw new SSLException(e);
/*     */       } 
/*     */       
/* 518 */       if (bytesRead == 0) {
/*     */         break;
/*     */       }
/*     */       
/* 522 */       bytesProduced += bytesRead;
/* 523 */       pendingApp -= bytesRead;
/*     */       
/* 525 */       if (!dst.hasRemaining()) {
/* 526 */         idx++;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 531 */     if (!this.receivedShutdown && (SSL.getShutdown(this.ssl) & 0x2) == 2) {
/* 532 */       this.receivedShutdown = true;
/* 533 */       closeOutbound();
/* 534 */       closeInbound();
/*     */     } 
/*     */     
/* 537 */     return new SSLEngineResult(getEngineStatus(), getHandshakeStatus(), bytesConsumed, bytesProduced);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Runnable getDelegatedTask() {
/* 545 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void closeInbound() throws SSLException {
/* 550 */     if (this.isInboundDone) {
/*     */       return;
/*     */     }
/*     */     
/* 554 */     this.isInboundDone = true;
/* 555 */     this.engineClosed = true;
/*     */     
/* 557 */     if (this.accepted != 0) {
/* 558 */       if (!this.receivedShutdown) {
/* 559 */         shutdown();
/* 560 */         throw new SSLException("Inbound closed before receiving peer's close_notify: possible truncation attack?");
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 565 */       shutdown();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized boolean isInboundDone() {
/* 571 */     return (this.isInboundDone || this.engineClosed);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void closeOutbound() {
/* 576 */     if (this.isOutboundDone) {
/*     */       return;
/*     */     }
/*     */     
/* 580 */     this.isOutboundDone = true;
/* 581 */     this.engineClosed = true;
/*     */     
/* 583 */     if (this.accepted != 0 && this.destroyed == 0) {
/* 584 */       int mode = SSL.getShutdown(this.ssl);
/* 585 */       if ((mode & 0x1) != 1) {
/* 586 */         SSL.shutdownSSL(this.ssl);
/*     */       }
/*     */     } else {
/*     */       
/* 590 */       shutdown();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized boolean isOutboundDone() {
/* 596 */     return this.isOutboundDone;
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getSupportedCipherSuites() {
/* 601 */     return EmptyArrays.EMPTY_STRINGS;
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getEnabledCipherSuites() {
/* 606 */     return EmptyArrays.EMPTY_STRINGS;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnabledCipherSuites(String[] strings) {
/* 611 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getSupportedProtocols() {
/* 616 */     return EmptyArrays.EMPTY_STRINGS;
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getEnabledProtocols() {
/* 621 */     return EmptyArrays.EMPTY_STRINGS;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnabledProtocols(String[] strings) {
/* 626 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public SSLSession getSession() {
/* 631 */     SSLSession session = this.session;
/* 632 */     if (session == null) {
/* 633 */       this.session = session = new SSLSession()
/*     */         {
/*     */           public byte[] getId() {
/* 636 */             return String.valueOf(OpenSslEngine.this.ssl).getBytes();
/*     */           }
/*     */ 
/*     */           
/*     */           public SSLSessionContext getSessionContext() {
/* 641 */             return null;
/*     */           }
/*     */ 
/*     */           
/*     */           public long getCreationTime() {
/* 646 */             return 0L;
/*     */           }
/*     */ 
/*     */           
/*     */           public long getLastAccessedTime() {
/* 651 */             return 0L;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void invalidate() {}
/*     */ 
/*     */           
/*     */           public boolean isValid() {
/* 660 */             return false;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void putValue(String s, Object o) {}
/*     */ 
/*     */           
/*     */           public Object getValue(String s) {
/* 669 */             return null;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void removeValue(String s) {}
/*     */ 
/*     */           
/*     */           public String[] getValueNames() {
/* 678 */             return EmptyArrays.EMPTY_STRINGS;
/*     */           }
/*     */ 
/*     */           
/*     */           public Certificate[] getPeerCertificates() {
/* 683 */             return OpenSslEngine.EMPTY_CERTIFICATES;
/*     */           }
/*     */ 
/*     */           
/*     */           public Certificate[] getLocalCertificates() {
/* 688 */             return OpenSslEngine.EMPTY_CERTIFICATES;
/*     */           }
/*     */ 
/*     */           
/*     */           public X509Certificate[] getPeerCertificateChain() {
/* 693 */             return OpenSslEngine.EMPTY_X509_CERTIFICATES;
/*     */           }
/*     */ 
/*     */           
/*     */           public Principal getPeerPrincipal() {
/* 698 */             return null;
/*     */           }
/*     */ 
/*     */           
/*     */           public Principal getLocalPrincipal() {
/* 703 */             return null;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCipherSuite() {
/* 708 */             return OpenSslEngine.this.cipher;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public String getProtocol() {
/* 714 */             String applicationProtocol = OpenSslEngine.this.applicationProtocol;
/* 715 */             if (applicationProtocol == null) {
/* 716 */               return "unknown";
/*     */             }
/* 718 */             return "unknown:" + applicationProtocol;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public String getPeerHost() {
/* 724 */             return null;
/*     */           }
/*     */ 
/*     */           
/*     */           public int getPeerPort() {
/* 729 */             return 0;
/*     */           }
/*     */ 
/*     */           
/*     */           public int getPacketBufferSize() {
/* 734 */             return 18713;
/*     */           }
/*     */ 
/*     */           
/*     */           public int getApplicationBufferSize() {
/* 739 */             return 16384;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/* 744 */     return session;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void beginHandshake() throws SSLException {
/* 749 */     if (this.engineClosed) {
/* 750 */       throw ENGINE_CLOSED;
/*     */     }
/*     */     
/* 753 */     switch (this.accepted) {
/*     */       case 0:
/* 755 */         SSL.doHandshake(this.ssl);
/* 756 */         this.accepted = 2;
/*     */         return;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 765 */         this.accepted = 2;
/*     */         return;
/*     */       case 2:
/* 768 */         throw RENEGOTIATION_UNSUPPORTED;
/*     */     } 
/* 770 */     throw new Error();
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized void beginHandshakeImplicitly() throws SSLException {
/* 775 */     if (this.engineClosed) {
/* 776 */       throw ENGINE_CLOSED;
/*     */     }
/*     */     
/* 779 */     if (this.accepted == 0) {
/* 780 */       SSL.doHandshake(this.ssl);
/* 781 */       this.accepted = 1;
/*     */     } 
/*     */   }
/*     */   
/*     */   private SSLEngineResult.Status getEngineStatus() {
/* 786 */     return this.engineClosed ? SSLEngineResult.Status.CLOSED : SSLEngineResult.Status.OK;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized SSLEngineResult.HandshakeStatus getHandshakeStatus() {
/* 791 */     if (this.accepted == 0 || this.destroyed != 0) {
/* 792 */       return SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
/*     */     }
/*     */ 
/*     */     
/* 796 */     if (!this.handshakeFinished) {
/*     */       
/* 798 */       if (SSL.pendingWrittenBytesInBIO(this.networkBIO) != 0) {
/* 799 */         return SSLEngineResult.HandshakeStatus.NEED_WRAP;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 804 */       if (SSL.isInInit(this.ssl) == 0) {
/* 805 */         this.handshakeFinished = true;
/* 806 */         this.cipher = SSL.getCipherForSSL(this.ssl);
/* 807 */         String applicationProtocol = SSL.getNextProtoNegotiated(this.ssl);
/* 808 */         if (applicationProtocol == null) {
/* 809 */           applicationProtocol = this.fallbackApplicationProtocol;
/*     */         }
/* 811 */         if (applicationProtocol != null) {
/* 812 */           this.applicationProtocol = applicationProtocol.replace(':', '_');
/*     */         } else {
/* 814 */           this.applicationProtocol = null;
/*     */         } 
/* 816 */         return SSLEngineResult.HandshakeStatus.FINISHED;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 821 */       return SSLEngineResult.HandshakeStatus.NEED_UNWRAP;
/*     */     } 
/*     */ 
/*     */     
/* 825 */     if (this.engineClosed) {
/*     */       
/* 827 */       if (SSL.pendingWrittenBytesInBIO(this.networkBIO) != 0) {
/* 828 */         return SSLEngineResult.HandshakeStatus.NEED_WRAP;
/*     */       }
/*     */ 
/*     */       
/* 832 */       return SSLEngineResult.HandshakeStatus.NEED_UNWRAP;
/*     */     } 
/*     */     
/* 835 */     return SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUseClientMode(boolean clientMode) {
/* 840 */     if (clientMode) {
/* 841 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getUseClientMode() {
/* 847 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNeedClientAuth(boolean b) {
/* 852 */     if (b) {
/* 853 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getNeedClientAuth() {
/* 859 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWantClientAuth(boolean b) {
/* 864 */     if (b) {
/* 865 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getWantClientAuth() {
/* 871 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnableSessionCreation(boolean b) {
/* 876 */     if (b) {
/* 877 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getEnableSessionCreation() {
/* 883 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\ssl\OpenSslEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */