/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufProcessor;
/*     */ import io.netty.buffer.ByteBufUtil;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.DecoderResult;
/*     */ import io.netty.handler.codec.ReplayingDecoder;
/*     */ import io.netty.handler.codec.TooLongFrameException;
/*     */ import io.netty.util.internal.AppendableCharSequence;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class HttpObjectDecoder
/*     */   extends ReplayingDecoder<HttpObjectDecoder.State>
/*     */ {
/*     */   private final int maxInitialLineLength;
/*     */   private final int maxHeaderSize;
/*     */   private final int maxChunkSize;
/*     */   private final boolean chunkedSupported;
/*     */   protected final boolean validateHeaders;
/* 111 */   private final AppendableCharSequence seq = new AppendableCharSequence(128);
/* 112 */   private final HeaderParser headerParser = new HeaderParser(this.seq);
/* 113 */   private final LineParser lineParser = new LineParser(this.seq);
/*     */   
/*     */   private HttpMessage message;
/*     */   private long chunkSize;
/*     */   private int headerSize;
/* 118 */   private long contentLength = Long.MIN_VALUE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   enum State
/*     */   {
/* 125 */     SKIP_CONTROL_CHARS,
/* 126 */     READ_INITIAL,
/* 127 */     READ_HEADER,
/* 128 */     READ_VARIABLE_LENGTH_CONTENT,
/* 129 */     READ_FIXED_LENGTH_CONTENT,
/* 130 */     READ_CHUNK_SIZE,
/* 131 */     READ_CHUNKED_CONTENT,
/* 132 */     READ_CHUNK_DELIMITER,
/* 133 */     READ_CHUNK_FOOTER,
/* 134 */     BAD_MESSAGE,
/* 135 */     UPGRADED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected HttpObjectDecoder() {
/* 144 */     this(4096, 8192, 8192, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected HttpObjectDecoder(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean chunkedSupported) {
/* 152 */     this(maxInitialLineLength, maxHeaderSize, maxChunkSize, chunkedSupported, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected HttpObjectDecoder(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean chunkedSupported, boolean validateHeaders) {
/* 162 */     super(State.SKIP_CONTROL_CHARS);
/*     */     
/* 164 */     if (maxInitialLineLength <= 0) {
/* 165 */       throw new IllegalArgumentException("maxInitialLineLength must be a positive integer: " + maxInitialLineLength);
/*     */     }
/*     */ 
/*     */     
/* 169 */     if (maxHeaderSize <= 0) {
/* 170 */       throw new IllegalArgumentException("maxHeaderSize must be a positive integer: " + maxHeaderSize);
/*     */     }
/*     */ 
/*     */     
/* 174 */     if (maxChunkSize <= 0) {
/* 175 */       throw new IllegalArgumentException("maxChunkSize must be a positive integer: " + maxChunkSize);
/*     */     }
/*     */ 
/*     */     
/* 179 */     this.maxInitialLineLength = maxInitialLineLength;
/* 180 */     this.maxHeaderSize = maxHeaderSize;
/* 181 */     this.maxChunkSize = maxChunkSize;
/* 182 */     this.chunkedSupported = chunkedSupported;
/* 183 */     this.validateHeaders = validateHeaders; } protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception { int i; int readLimit; int toRead;
/*     */     int readableBytes;
/*     */     int j;
/*     */     HttpContent chunk;
/*     */     ByteBuf content;
/* 188 */     switch ((State)state()) {
/*     */       case SKIP_CONTROL_CHARS:
/*     */         try {
/* 191 */           skipControlCharacters(buffer);
/* 192 */           checkpoint(State.READ_INITIAL);
/*     */         } finally {
/* 194 */           checkpoint();
/*     */         } 
/*     */       case READ_INITIAL:
/*     */         try {
/* 198 */           String[] initialLine = splitInitialLine(this.lineParser.parse(buffer));
/* 199 */           if (initialLine.length < 3) {
/*     */             
/* 201 */             checkpoint(State.SKIP_CONTROL_CHARS);
/*     */             
/*     */             return;
/*     */           } 
/* 205 */           this.message = createMessage(initialLine);
/* 206 */           checkpoint(State.READ_HEADER);
/*     */         }
/* 208 */         catch (Exception e) {
/* 209 */           out.add(invalidMessage(e)); return;
/*     */         } 
/*     */       case READ_HEADER:
/*     */         try {
/* 213 */           State nextState = readHeaders(buffer);
/* 214 */           checkpoint(nextState);
/* 215 */           if (nextState == State.READ_CHUNK_SIZE) {
/* 216 */             if (!this.chunkedSupported) {
/* 217 */               throw new IllegalArgumentException("Chunked messages not supported");
/*     */             }
/*     */             
/* 220 */             out.add(this.message);
/*     */             return;
/*     */           } 
/* 223 */           if (nextState == State.SKIP_CONTROL_CHARS) {
/*     */             
/* 225 */             out.add(this.message);
/* 226 */             out.add(LastHttpContent.EMPTY_LAST_CONTENT);
/* 227 */             reset();
/*     */             return;
/*     */           } 
/* 230 */           long contentLength = contentLength();
/* 231 */           if (contentLength == 0L || (contentLength == -1L && isDecodingRequest())) {
/* 232 */             out.add(this.message);
/* 233 */             out.add(LastHttpContent.EMPTY_LAST_CONTENT);
/* 234 */             reset();
/*     */             
/*     */             return;
/*     */           } 
/* 238 */           assert nextState == State.READ_FIXED_LENGTH_CONTENT || nextState == State.READ_VARIABLE_LENGTH_CONTENT;
/*     */           
/* 240 */           out.add(this.message);
/*     */           
/* 242 */           if (nextState == State.READ_FIXED_LENGTH_CONTENT)
/*     */           {
/* 244 */             this.chunkSize = contentLength;
/*     */           }
/*     */ 
/*     */           
/*     */           return;
/* 249 */         } catch (Exception e) {
/* 250 */           out.add(invalidMessage(e));
/*     */           return;
/*     */         } 
/*     */       
/*     */       case READ_VARIABLE_LENGTH_CONTENT:
/* 255 */         i = Math.min(actualReadableBytes(), this.maxChunkSize);
/* 256 */         if (i > 0) {
/* 257 */           ByteBuf byteBuf = ByteBufUtil.readBytes(ctx.alloc(), buffer, i);
/* 258 */           if (buffer.isReadable()) {
/* 259 */             out.add(new DefaultHttpContent(byteBuf));
/*     */           } else {
/*     */             
/* 262 */             out.add(new DefaultLastHttpContent(byteBuf, this.validateHeaders));
/* 263 */             reset();
/*     */           } 
/* 265 */         } else if (!buffer.isReadable()) {
/*     */           
/* 267 */           out.add(LastHttpContent.EMPTY_LAST_CONTENT);
/* 268 */           reset();
/*     */         } 
/*     */         return;
/*     */       
/*     */       case READ_FIXED_LENGTH_CONTENT:
/* 273 */         readLimit = actualReadableBytes();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 281 */         if (readLimit == 0) {
/*     */           return;
/*     */         }
/*     */         
/* 285 */         j = Math.min(readLimit, this.maxChunkSize);
/* 286 */         if (j > this.chunkSize) {
/* 287 */           j = (int)this.chunkSize;
/*     */         }
/* 289 */         content = ByteBufUtil.readBytes(ctx.alloc(), buffer, j);
/* 290 */         this.chunkSize -= j;
/*     */         
/* 292 */         if (this.chunkSize == 0L) {
/*     */           
/* 294 */           out.add(new DefaultLastHttpContent(content, this.validateHeaders));
/* 295 */           reset();
/*     */         } else {
/* 297 */           out.add(new DefaultHttpContent(content));
/*     */         } 
/*     */         return;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case READ_CHUNK_SIZE:
/*     */         try {
/* 306 */           AppendableCharSequence line = this.lineParser.parse(buffer);
/* 307 */           int chunkSize = getChunkSize(line.toString());
/* 308 */           this.chunkSize = chunkSize;
/* 309 */           if (chunkSize == 0) {
/* 310 */             checkpoint(State.READ_CHUNK_FOOTER);
/*     */             return;
/*     */           } 
/* 313 */           checkpoint(State.READ_CHUNKED_CONTENT);
/*     */         }
/* 315 */         catch (Exception e) {
/* 316 */           out.add(invalidChunk(e));
/*     */           return;
/*     */         } 
/*     */       case READ_CHUNKED_CONTENT:
/* 320 */         assert this.chunkSize <= 2147483647L;
/* 321 */         toRead = Math.min((int)this.chunkSize, this.maxChunkSize);
/*     */         
/* 323 */         chunk = new DefaultHttpContent(ByteBufUtil.readBytes(ctx.alloc(), buffer, toRead));
/* 324 */         this.chunkSize -= toRead;
/*     */         
/* 326 */         out.add(chunk);
/*     */         
/* 328 */         if (this.chunkSize == 0L) {
/*     */           
/* 330 */           checkpoint(State.READ_CHUNK_DELIMITER);
/*     */         } else {
/*     */           return;
/*     */         } 
/*     */       
/*     */       case READ_CHUNK_DELIMITER:
/*     */         while (true) {
/* 337 */           byte next = buffer.readByte();
/* 338 */           if (next == 13) {
/* 339 */             if (buffer.readByte() == 10) {
/* 340 */               checkpoint(State.READ_CHUNK_SIZE); return;
/*     */             }  continue;
/*     */           } 
/* 343 */           if (next == 10) {
/* 344 */             checkpoint(State.READ_CHUNK_SIZE);
/*     */             return;
/*     */           } 
/* 347 */           checkpoint();
/*     */         } 
/*     */       
/*     */       case READ_CHUNK_FOOTER:
/*     */         try {
/* 352 */           LastHttpContent trailer = readTrailingHeaders(buffer);
/* 353 */           out.add(trailer);
/* 354 */           reset();
/*     */           return;
/* 356 */         } catch (Exception e) {
/* 357 */           out.add(invalidChunk(e));
/*     */           return;
/*     */         } 
/*     */       
/*     */       case BAD_MESSAGE:
/* 362 */         buffer.skipBytes(actualReadableBytes());
/*     */         break;
/*     */       
/*     */       case UPGRADED:
/* 366 */         readableBytes = actualReadableBytes();
/* 367 */         if (readableBytes > 0)
/*     */         {
/*     */ 
/*     */ 
/*     */           
/* 372 */           out.add(buffer.readBytes(actualReadableBytes()));
/*     */         }
/*     */         break;
/*     */     }  }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decodeLast(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/* 381 */     decode(ctx, in, out);
/*     */ 
/*     */     
/* 384 */     if (this.message != null) {
/*     */       boolean prematureClosure;
/*     */ 
/*     */       
/* 388 */       if (isDecodingRequest()) {
/*     */         
/* 390 */         prematureClosure = true;
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 395 */         prematureClosure = (contentLength() > 0L);
/*     */       } 
/* 397 */       reset();
/*     */       
/* 399 */       if (!prematureClosure) {
/* 400 */         out.add(LastHttpContent.EMPTY_LAST_CONTENT);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean isContentAlwaysEmpty(HttpMessage msg) {
/* 406 */     if (msg instanceof HttpResponse) {
/* 407 */       HttpResponse res = (HttpResponse)msg;
/* 408 */       int code = res.getStatus().code();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 415 */       if (code >= 100 && code < 200)
/*     */       {
/* 417 */         return (code != 101 || res.headers().contains("Sec-WebSocket-Accept"));
/*     */       }
/*     */       
/* 420 */       switch (code) { case 204: case 205:
/*     */         case 304:
/* 422 */           return true; }
/*     */     
/*     */     } 
/* 425 */     return false;
/*     */   }
/*     */   
/*     */   private void reset() {
/* 429 */     HttpMessage message = this.message;
/* 430 */     this.message = null;
/* 431 */     this.contentLength = Long.MIN_VALUE;
/* 432 */     if (!isDecodingRequest()) {
/* 433 */       HttpResponse res = (HttpResponse)message;
/* 434 */       if (res != null && res.getStatus().code() == 101) {
/* 435 */         checkpoint(State.UPGRADED);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 440 */     checkpoint(State.SKIP_CONTROL_CHARS);
/*     */   }
/*     */   
/*     */   private HttpMessage invalidMessage(Exception cause) {
/* 444 */     checkpoint(State.BAD_MESSAGE);
/* 445 */     if (this.message != null) {
/* 446 */       this.message.setDecoderResult(DecoderResult.failure(cause));
/*     */     } else {
/* 448 */       this.message = createInvalidMessage();
/* 449 */       this.message.setDecoderResult(DecoderResult.failure(cause));
/*     */     } 
/*     */     
/* 452 */     HttpMessage ret = this.message;
/* 453 */     this.message = null;
/* 454 */     return ret;
/*     */   }
/*     */   
/*     */   private HttpContent invalidChunk(Exception cause) {
/* 458 */     checkpoint(State.BAD_MESSAGE);
/* 459 */     HttpContent chunk = new DefaultLastHttpContent(Unpooled.EMPTY_BUFFER);
/* 460 */     chunk.setDecoderResult(DecoderResult.failure(cause));
/* 461 */     this.message = null;
/* 462 */     return chunk;
/*     */   }
/*     */   private static void skipControlCharacters(ByteBuf buffer) {
/*     */     char c;
/*     */     do {
/* 467 */       c = (char)buffer.readUnsignedByte();
/* 468 */     } while (Character.isISOControl(c) || Character.isWhitespace(c));
/*     */     
/* 470 */     buffer.readerIndex(buffer.readerIndex() - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private State readHeaders(ByteBuf buffer) {
/*     */     State nextState;
/* 477 */     this.headerSize = 0;
/* 478 */     HttpMessage message = this.message;
/* 479 */     HttpHeaders headers = message.headers();
/*     */     
/* 481 */     AppendableCharSequence line = this.headerParser.parse(buffer);
/* 482 */     String name = null;
/* 483 */     String value = null;
/* 484 */     if (line.length() > 0) {
/* 485 */       headers.clear();
/*     */       do {
/* 487 */         char firstChar = line.charAt(0);
/* 488 */         if (name != null && (firstChar == ' ' || firstChar == '\t')) {
/* 489 */           value = value + ' ' + line.toString().trim();
/*     */         } else {
/* 491 */           if (name != null) {
/* 492 */             headers.add(name, value);
/*     */           }
/* 494 */           String[] header = splitHeader(line);
/* 495 */           name = header[0];
/* 496 */           value = header[1];
/*     */         } 
/*     */         
/* 499 */         line = this.headerParser.parse(buffer);
/* 500 */       } while (line.length() > 0);
/*     */ 
/*     */       
/* 503 */       if (name != null) {
/* 504 */         headers.add(name, value);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 510 */     if (isContentAlwaysEmpty(message)) {
/* 511 */       HttpHeaders.removeTransferEncodingChunked(message);
/* 512 */       nextState = State.SKIP_CONTROL_CHARS;
/* 513 */     } else if (HttpHeaders.isTransferEncodingChunked(message)) {
/* 514 */       nextState = State.READ_CHUNK_SIZE;
/* 515 */     } else if (contentLength() >= 0L) {
/* 516 */       nextState = State.READ_FIXED_LENGTH_CONTENT;
/*     */     } else {
/* 518 */       nextState = State.READ_VARIABLE_LENGTH_CONTENT;
/*     */     } 
/* 520 */     return nextState;
/*     */   }
/*     */   
/*     */   private long contentLength() {
/* 524 */     if (this.contentLength == Long.MIN_VALUE) {
/* 525 */       this.contentLength = HttpHeaders.getContentLength(this.message, -1L);
/*     */     }
/* 527 */     return this.contentLength;
/*     */   }
/*     */   
/*     */   private LastHttpContent readTrailingHeaders(ByteBuf buffer) {
/* 531 */     this.headerSize = 0;
/* 532 */     AppendableCharSequence line = this.headerParser.parse(buffer);
/* 533 */     String lastHeader = null;
/* 534 */     if (line.length() > 0) {
/* 535 */       LastHttpContent trailer = new DefaultLastHttpContent(Unpooled.EMPTY_BUFFER, this.validateHeaders);
/*     */       do {
/* 537 */         char firstChar = line.charAt(0);
/* 538 */         if (lastHeader != null && (firstChar == ' ' || firstChar == '\t')) {
/* 539 */           List<String> current = trailer.trailingHeaders().getAll(lastHeader);
/* 540 */           if (!current.isEmpty()) {
/* 541 */             int lastPos = current.size() - 1;
/* 542 */             String newString = (String)current.get(lastPos) + line.toString().trim();
/* 543 */             current.set(lastPos, newString);
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 548 */           String[] header = splitHeader(line);
/* 549 */           String name = header[0];
/* 550 */           if (!HttpHeaders.equalsIgnoreCase(name, "Content-Length") && !HttpHeaders.equalsIgnoreCase(name, "Transfer-Encoding") && !HttpHeaders.equalsIgnoreCase(name, "Trailer"))
/*     */           {
/*     */             
/* 553 */             trailer.trailingHeaders().add(name, header[1]);
/*     */           }
/* 555 */           lastHeader = name;
/*     */         } 
/*     */         
/* 558 */         line = this.headerParser.parse(buffer);
/* 559 */       } while (line.length() > 0);
/*     */       
/* 561 */       return trailer;
/*     */     } 
/*     */     
/* 564 */     return LastHttpContent.EMPTY_LAST_CONTENT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getChunkSize(String hex) {
/* 572 */     hex = hex.trim();
/* 573 */     for (int i = 0; i < hex.length(); i++) {
/* 574 */       char c = hex.charAt(i);
/* 575 */       if (c == ';' || Character.isWhitespace(c) || Character.isISOControl(c)) {
/* 576 */         hex = hex.substring(0, i);
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 581 */     return Integer.parseInt(hex, 16);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] splitInitialLine(AppendableCharSequence sb) {
/* 592 */     int aStart = findNonWhitespace((CharSequence)sb, 0);
/* 593 */     int aEnd = findWhitespace((CharSequence)sb, aStart);
/*     */     
/* 595 */     int bStart = findNonWhitespace((CharSequence)sb, aEnd);
/* 596 */     int bEnd = findWhitespace((CharSequence)sb, bStart);
/*     */     
/* 598 */     int cStart = findNonWhitespace((CharSequence)sb, bEnd);
/* 599 */     int cEnd = findEndOfString((CharSequence)sb);
/*     */     
/* 601 */     return new String[] { sb.substring(aStart, aEnd), sb.substring(bStart, bEnd), (cStart < cEnd) ? sb.substring(cStart, cEnd) : "" };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] splitHeader(AppendableCharSequence sb) {
/* 608 */     int length = sb.length();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 615 */     int nameStart = findNonWhitespace((CharSequence)sb, 0); int nameEnd;
/* 616 */     for (nameEnd = nameStart; nameEnd < length; nameEnd++) {
/* 617 */       char ch = sb.charAt(nameEnd);
/* 618 */       if (ch == ':' || Character.isWhitespace(ch)) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     int colonEnd;
/* 623 */     for (colonEnd = nameEnd; colonEnd < length; colonEnd++) {
/* 624 */       if (sb.charAt(colonEnd) == ':') {
/* 625 */         colonEnd++;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 630 */     int valueStart = findNonWhitespace((CharSequence)sb, colonEnd);
/* 631 */     if (valueStart == length) {
/* 632 */       return new String[] { sb.substring(nameStart, nameEnd), "" };
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 638 */     int valueEnd = findEndOfString((CharSequence)sb);
/* 639 */     return new String[] { sb.substring(nameStart, nameEnd), sb.substring(valueStart, valueEnd) };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int findNonWhitespace(CharSequence sb, int offset) {
/*     */     int result;
/* 647 */     for (result = offset; result < sb.length() && 
/* 648 */       Character.isWhitespace(sb.charAt(result)); result++);
/*     */ 
/*     */ 
/*     */     
/* 652 */     return result;
/*     */   }
/*     */   
/*     */   private static int findWhitespace(CharSequence sb, int offset) {
/*     */     int result;
/* 657 */     for (result = offset; result < sb.length() && 
/* 658 */       !Character.isWhitespace(sb.charAt(result)); result++);
/*     */ 
/*     */ 
/*     */     
/* 662 */     return result;
/*     */   }
/*     */   
/*     */   private static int findEndOfString(CharSequence sb) {
/*     */     int result;
/* 667 */     for (result = sb.length(); result > 0 && 
/* 668 */       Character.isWhitespace(sb.charAt(result - 1)); result--);
/*     */ 
/*     */ 
/*     */     
/* 672 */     return result;
/*     */   }
/*     */   protected abstract boolean isDecodingRequest();
/*     */   protected abstract HttpMessage createMessage(String[] paramArrayOfString) throws Exception;
/*     */   protected abstract HttpMessage createInvalidMessage();
/*     */   private final class HeaderParser implements ByteBufProcessor { private final AppendableCharSequence seq;
/*     */     HeaderParser(AppendableCharSequence seq) {
/* 679 */       this.seq = seq;
/*     */     }
/*     */     
/*     */     public AppendableCharSequence parse(ByteBuf buffer) {
/* 683 */       this.seq.reset();
/* 684 */       HttpObjectDecoder.this.headerSize = 0;
/* 685 */       int i = buffer.forEachByte(this);
/* 686 */       buffer.readerIndex(i + 1);
/* 687 */       return this.seq;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean process(byte value) throws Exception {
/* 692 */       char nextByte = (char)value;
/* 693 */       HttpObjectDecoder.this.headerSize++;
/* 694 */       if (nextByte == '\r') {
/* 695 */         return true;
/*     */       }
/* 697 */       if (nextByte == '\n') {
/* 698 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 702 */       if (HttpObjectDecoder.this.headerSize >= HttpObjectDecoder.this.maxHeaderSize)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 707 */         throw new TooLongFrameException("HTTP header is larger than " + HttpObjectDecoder.this.maxHeaderSize + " bytes.");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 712 */       this.seq.append(nextByte);
/* 713 */       return true;
/*     */     } }
/*     */ 
/*     */   
/*     */   private final class LineParser implements ByteBufProcessor {
/*     */     private final AppendableCharSequence seq;
/*     */     private int size;
/*     */     
/*     */     LineParser(AppendableCharSequence seq) {
/* 722 */       this.seq = seq;
/*     */     }
/*     */     
/*     */     public AppendableCharSequence parse(ByteBuf buffer) {
/* 726 */       this.seq.reset();
/* 727 */       this.size = 0;
/* 728 */       int i = buffer.forEachByte(this);
/* 729 */       buffer.readerIndex(i + 1);
/* 730 */       return this.seq;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean process(byte value) throws Exception {
/* 735 */       char nextByte = (char)value;
/* 736 */       if (nextByte == '\r')
/* 737 */         return true; 
/* 738 */       if (nextByte == '\n') {
/* 739 */         return false;
/*     */       }
/* 741 */       if (this.size >= HttpObjectDecoder.this.maxInitialLineLength)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 746 */         throw new TooLongFrameException("An HTTP line is larger than " + HttpObjectDecoder.this.maxInitialLineLength + " bytes.");
/*     */       }
/*     */ 
/*     */       
/* 750 */       this.size++;
/* 751 */       this.seq.append(nextByte);
/* 752 */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\HttpObjectDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */