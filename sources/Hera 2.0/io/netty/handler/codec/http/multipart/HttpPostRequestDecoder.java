/*      */ package io.netty.handler.codec.http.multipart;
/*      */ 
/*      */ import io.netty.buffer.ByteBuf;
/*      */ import io.netty.buffer.Unpooled;
/*      */ import io.netty.handler.codec.DecoderException;
/*      */ import io.netty.handler.codec.http.HttpConstants;
/*      */ import io.netty.handler.codec.http.HttpContent;
/*      */ import io.netty.handler.codec.http.HttpMethod;
/*      */ import io.netty.handler.codec.http.HttpRequest;
/*      */ import io.netty.handler.codec.http.QueryStringDecoder;
/*      */ import io.netty.util.internal.StringUtil;
/*      */ import java.io.IOException;
/*      */ import java.nio.charset.Charset;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.TreeMap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class HttpPostRequestDecoder
/*      */ {
/*      */   private static final int DEFAULT_DISCARD_THRESHOLD = 10485760;
/*      */   private final HttpDataFactory factory;
/*      */   private final HttpRequest request;
/*      */   private final Charset charset;
/*      */   private boolean bodyToDecode;
/*      */   private boolean isLastChunk;
/*   78 */   private final List<InterfaceHttpData> bodyListHttpData = new ArrayList<InterfaceHttpData>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   83 */   private final Map<String, List<InterfaceHttpData>> bodyMapHttpData = new TreeMap<String, List<InterfaceHttpData>>(CaseIgnoringComparator.INSTANCE);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ByteBuf undecodedChunk;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isMultipart;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int bodyListHttpDataRank;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String multipartDataBoundary;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String multipartMixedBoundary;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  115 */   private MultiPartStatus currentStatus = MultiPartStatus.NOTSTARTED;
/*      */ 
/*      */ 
/*      */   
/*      */   private Map<String, Attribute> currentFieldAttributes;
/*      */ 
/*      */ 
/*      */   
/*      */   private FileUpload currentFileUpload;
/*      */ 
/*      */ 
/*      */   
/*      */   private Attribute currentAttribute;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean destroyed;
/*      */ 
/*      */   
/*  134 */   private int discardThreshold = 10485760;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpPostRequestDecoder(HttpRequest request) throws ErrorDataDecoderException, IncompatibleDataDecoderException {
/*  150 */     this(new DefaultHttpDataFactory(16384L), request, HttpConstants.DEFAULT_CHARSET);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpPostRequestDecoder(HttpDataFactory factory, HttpRequest request) throws ErrorDataDecoderException, IncompatibleDataDecoderException {
/*  169 */     this(factory, request, HttpConstants.DEFAULT_CHARSET);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpPostRequestDecoder(HttpDataFactory factory, HttpRequest request, Charset charset) throws ErrorDataDecoderException, IncompatibleDataDecoderException {
/*  190 */     if (factory == null) {
/*  191 */       throw new NullPointerException("factory");
/*      */     }
/*  193 */     if (request == null) {
/*  194 */       throw new NullPointerException("request");
/*      */     }
/*  196 */     if (charset == null) {
/*  197 */       throw new NullPointerException("charset");
/*      */     }
/*  199 */     this.request = request;
/*  200 */     HttpMethod method = request.getMethod();
/*  201 */     if (method.equals(HttpMethod.POST) || method.equals(HttpMethod.PUT) || method.equals(HttpMethod.PATCH)) {
/*  202 */       this.bodyToDecode = true;
/*      */     }
/*  204 */     this.charset = charset;
/*  205 */     this.factory = factory;
/*      */ 
/*      */     
/*  208 */     String contentType = this.request.headers().get("Content-Type");
/*  209 */     if (contentType != null) {
/*  210 */       checkMultipart(contentType);
/*      */     } else {
/*  212 */       this.isMultipart = false;
/*      */     } 
/*  214 */     if (!this.bodyToDecode) {
/*  215 */       throw new IncompatibleDataDecoderException("No Body to decode");
/*      */     }
/*  217 */     if (request instanceof HttpContent) {
/*      */ 
/*      */       
/*  220 */       offer((HttpContent)request);
/*      */     } else {
/*  222 */       this.undecodedChunk = Unpooled.buffer();
/*  223 */       parseBody();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private enum MultiPartStatus
/*      */   {
/*  258 */     NOTSTARTED, PREAMBLE, HEADERDELIMITER, DISPOSITION, FIELD, FILEUPLOAD, MIXEDPREAMBLE, MIXEDDELIMITER,
/*  259 */     MIXEDDISPOSITION, MIXEDFILEUPLOAD, MIXEDCLOSEDELIMITER, CLOSEDELIMITER, PREEPILOGUE, EPILOGUE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkMultipart(String contentType) throws ErrorDataDecoderException {
/*  268 */     String[] headerContentType = splitHeaderContentType(contentType);
/*  269 */     if (headerContentType[0].toLowerCase().startsWith("multipart/form-data") && headerContentType[1].toLowerCase().startsWith("boundary")) {
/*      */       
/*  271 */       String[] boundary = StringUtil.split(headerContentType[1], '=');
/*  272 */       if (boundary.length != 2) {
/*  273 */         throw new ErrorDataDecoderException("Needs a boundary value");
/*      */       }
/*  275 */       if (boundary[1].charAt(0) == '"') {
/*  276 */         String bound = boundary[1].trim();
/*  277 */         int index = bound.length() - 1;
/*  278 */         if (bound.charAt(index) == '"') {
/*  279 */           boundary[1] = bound.substring(1, index);
/*      */         }
/*      */       } 
/*  282 */       this.multipartDataBoundary = "--" + boundary[1];
/*  283 */       this.isMultipart = true;
/*  284 */       this.currentStatus = MultiPartStatus.HEADERDELIMITER;
/*      */     } else {
/*  286 */       this.isMultipart = false;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkDestroyed() {
/*  291 */     if (this.destroyed) {
/*  292 */       throw new IllegalStateException(HttpPostRequestDecoder.class.getSimpleName() + " was destroyed already");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isMultipart() {
/*  302 */     checkDestroyed();
/*  303 */     return this.isMultipart;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDiscardThreshold(int discardThreshold) {
/*  312 */     if (discardThreshold < 0) {
/*  313 */       throw new IllegalArgumentException("discardThreshold must be >= 0");
/*      */     }
/*  315 */     this.discardThreshold = discardThreshold;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDiscardThreshold() {
/*  322 */     return this.discardThreshold;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<InterfaceHttpData> getBodyHttpDatas() throws NotEnoughDataDecoderException {
/*  336 */     checkDestroyed();
/*      */     
/*  338 */     if (!this.isLastChunk) {
/*  339 */       throw new NotEnoughDataDecoderException();
/*      */     }
/*  341 */     return this.bodyListHttpData;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<InterfaceHttpData> getBodyHttpDatas(String name) throws NotEnoughDataDecoderException {
/*  356 */     checkDestroyed();
/*      */     
/*  358 */     if (!this.isLastChunk) {
/*  359 */       throw new NotEnoughDataDecoderException();
/*      */     }
/*  361 */     return this.bodyMapHttpData.get(name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InterfaceHttpData getBodyHttpData(String name) throws NotEnoughDataDecoderException {
/*  377 */     checkDestroyed();
/*      */     
/*  379 */     if (!this.isLastChunk) {
/*  380 */       throw new NotEnoughDataDecoderException();
/*      */     }
/*  382 */     List<InterfaceHttpData> list = this.bodyMapHttpData.get(name);
/*  383 */     if (list != null) {
/*  384 */       return list.get(0);
/*      */     }
/*  386 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpPostRequestDecoder offer(HttpContent content) throws ErrorDataDecoderException {
/*  399 */     checkDestroyed();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  404 */     ByteBuf buf = content.content();
/*  405 */     if (this.undecodedChunk == null) {
/*  406 */       this.undecodedChunk = buf.copy();
/*      */     } else {
/*  408 */       this.undecodedChunk.writeBytes(buf);
/*      */     } 
/*  410 */     if (content instanceof io.netty.handler.codec.http.LastHttpContent) {
/*  411 */       this.isLastChunk = true;
/*      */     }
/*  413 */     parseBody();
/*  414 */     if (this.undecodedChunk != null && this.undecodedChunk.writerIndex() > this.discardThreshold) {
/*  415 */       this.undecodedChunk.discardReadBytes();
/*      */     }
/*  417 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasNext() throws EndOfDataDecoderException {
/*  431 */     checkDestroyed();
/*      */     
/*  433 */     if (this.currentStatus == MultiPartStatus.EPILOGUE)
/*      */     {
/*  435 */       if (this.bodyListHttpDataRank >= this.bodyListHttpData.size()) {
/*  436 */         throw new EndOfDataDecoderException();
/*      */       }
/*      */     }
/*  439 */     return (!this.bodyListHttpData.isEmpty() && this.bodyListHttpDataRank < this.bodyListHttpData.size());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InterfaceHttpData next() throws EndOfDataDecoderException {
/*  455 */     checkDestroyed();
/*      */     
/*  457 */     if (hasNext()) {
/*  458 */       return this.bodyListHttpData.get(this.bodyListHttpDataRank++);
/*      */     }
/*  460 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void parseBody() throws ErrorDataDecoderException {
/*  471 */     if (this.currentStatus == MultiPartStatus.PREEPILOGUE || this.currentStatus == MultiPartStatus.EPILOGUE) {
/*  472 */       if (this.isLastChunk) {
/*  473 */         this.currentStatus = MultiPartStatus.EPILOGUE;
/*      */       }
/*      */       return;
/*      */     } 
/*  477 */     if (this.isMultipart) {
/*  478 */       parseBodyMultipart();
/*      */     } else {
/*  480 */       parseBodyAttributes();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addHttpData(InterfaceHttpData data) {
/*  488 */     if (data == null) {
/*      */       return;
/*      */     }
/*  491 */     List<InterfaceHttpData> datas = this.bodyMapHttpData.get(data.getName());
/*  492 */     if (datas == null) {
/*  493 */       datas = new ArrayList<InterfaceHttpData>(1);
/*  494 */       this.bodyMapHttpData.put(data.getName(), datas);
/*      */     } 
/*  496 */     datas.add(data);
/*  497 */     this.bodyListHttpData.add(data);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void parseBodyAttributesStandard() throws ErrorDataDecoderException {
/*  509 */     int firstpos = this.undecodedChunk.readerIndex();
/*  510 */     int currentpos = firstpos;
/*      */ 
/*      */     
/*  513 */     if (this.currentStatus == MultiPartStatus.NOTSTARTED) {
/*  514 */       this.currentStatus = MultiPartStatus.DISPOSITION;
/*      */     }
/*  516 */     boolean contRead = true;
/*      */     try {
/*  518 */       while (this.undecodedChunk.isReadable() && contRead) {
/*  519 */         char read = (char)this.undecodedChunk.readUnsignedByte();
/*  520 */         currentpos++;
/*  521 */         switch (this.currentStatus) {
/*      */           case DISPOSITION:
/*  523 */             if (read == '=') {
/*  524 */               this.currentStatus = MultiPartStatus.FIELD;
/*  525 */               int equalpos = currentpos - 1;
/*  526 */               String key = decodeAttribute(this.undecodedChunk.toString(firstpos, equalpos - firstpos, this.charset), this.charset);
/*      */               
/*  528 */               this.currentAttribute = this.factory.createAttribute(this.request, key);
/*  529 */               firstpos = currentpos; continue;
/*  530 */             }  if (read == '&') {
/*  531 */               this.currentStatus = MultiPartStatus.DISPOSITION;
/*  532 */               int ampersandpos = currentpos - 1;
/*  533 */               String key = decodeAttribute(this.undecodedChunk.toString(firstpos, ampersandpos - firstpos, this.charset), this.charset);
/*      */               
/*  535 */               this.currentAttribute = this.factory.createAttribute(this.request, key);
/*  536 */               this.currentAttribute.setValue("");
/*  537 */               addHttpData(this.currentAttribute);
/*  538 */               this.currentAttribute = null;
/*  539 */               firstpos = currentpos;
/*  540 */               contRead = true;
/*      */             } 
/*      */             continue;
/*      */           case FIELD:
/*  544 */             if (read == '&') {
/*  545 */               this.currentStatus = MultiPartStatus.DISPOSITION;
/*  546 */               int i = currentpos - 1;
/*  547 */               setFinalBuffer(this.undecodedChunk.copy(firstpos, i - firstpos));
/*  548 */               firstpos = currentpos;
/*  549 */               contRead = true; continue;
/*  550 */             }  if (read == '\r') {
/*  551 */               if (this.undecodedChunk.isReadable()) {
/*  552 */                 read = (char)this.undecodedChunk.readUnsignedByte();
/*  553 */                 currentpos++;
/*  554 */                 if (read == '\n') {
/*  555 */                   this.currentStatus = MultiPartStatus.PREEPILOGUE;
/*  556 */                   int ampersandpos = currentpos - 2;
/*  557 */                   setFinalBuffer(this.undecodedChunk.copy(firstpos, ampersandpos - firstpos));
/*  558 */                   firstpos = currentpos;
/*  559 */                   contRead = false;
/*      */                   continue;
/*      */                 } 
/*  562 */                 throw new ErrorDataDecoderException("Bad end of line");
/*      */               } 
/*      */               
/*  565 */               currentpos--; continue;
/*      */             } 
/*  567 */             if (read == '\n') {
/*  568 */               this.currentStatus = MultiPartStatus.PREEPILOGUE;
/*  569 */               int ampersandpos = currentpos - 1;
/*  570 */               setFinalBuffer(this.undecodedChunk.copy(firstpos, ampersandpos - firstpos));
/*  571 */               firstpos = currentpos;
/*  572 */               contRead = false;
/*      */             } 
/*      */             continue;
/*      */         } 
/*      */         
/*  577 */         contRead = false;
/*      */       } 
/*      */       
/*  580 */       if (this.isLastChunk && this.currentAttribute != null) {
/*      */         
/*  582 */         int i = currentpos;
/*  583 */         if (i > firstpos) {
/*  584 */           setFinalBuffer(this.undecodedChunk.copy(firstpos, i - firstpos));
/*  585 */         } else if (!this.currentAttribute.isCompleted()) {
/*  586 */           setFinalBuffer(Unpooled.EMPTY_BUFFER);
/*      */         } 
/*  588 */         firstpos = currentpos;
/*  589 */         this.currentStatus = MultiPartStatus.EPILOGUE;
/*  590 */         this.undecodedChunk.readerIndex(firstpos);
/*      */         return;
/*      */       } 
/*  593 */       if (contRead && this.currentAttribute != null) {
/*      */         
/*  595 */         if (this.currentStatus == MultiPartStatus.FIELD) {
/*  596 */           this.currentAttribute.addContent(this.undecodedChunk.copy(firstpos, currentpos - firstpos), false);
/*      */           
/*  598 */           firstpos = currentpos;
/*      */         } 
/*  600 */         this.undecodedChunk.readerIndex(firstpos);
/*      */       } else {
/*      */         
/*  603 */         this.undecodedChunk.readerIndex(firstpos);
/*      */       } 
/*  605 */     } catch (ErrorDataDecoderException e) {
/*      */       
/*  607 */       this.undecodedChunk.readerIndex(firstpos);
/*  608 */       throw e;
/*  609 */     } catch (IOException e) {
/*      */       
/*  611 */       this.undecodedChunk.readerIndex(firstpos);
/*  612 */       throw new ErrorDataDecoderException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void parseBodyAttributes() throws ErrorDataDecoderException {
/*      */     // Byte code:
/*      */     //   0: new io/netty/handler/codec/http/multipart/HttpPostBodyUtil$SeekAheadOptimize
/*      */     //   3: dup
/*      */     //   4: aload_0
/*      */     //   5: getfield undecodedChunk : Lio/netty/buffer/ByteBuf;
/*      */     //   8: invokespecial <init> : (Lio/netty/buffer/ByteBuf;)V
/*      */     //   11: astore_1
/*      */     //   12: goto -> 21
/*      */     //   15: astore_2
/*      */     //   16: aload_0
/*      */     //   17: invokespecial parseBodyAttributesStandard : ()V
/*      */     //   20: return
/*      */     //   21: aload_0
/*      */     //   22: getfield undecodedChunk : Lio/netty/buffer/ByteBuf;
/*      */     //   25: invokevirtual readerIndex : ()I
/*      */     //   28: istore_2
/*      */     //   29: iload_2
/*      */     //   30: istore_3
/*      */     //   31: aload_0
/*      */     //   32: getfield currentStatus : Lio/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus;
/*      */     //   35: getstatic io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus.NOTSTARTED : Lio/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus;
/*      */     //   38: if_acmpne -> 48
/*      */     //   41: aload_0
/*      */     //   42: getstatic io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus.DISPOSITION : Lio/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus;
/*      */     //   45: putfield currentStatus : Lio/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus;
/*      */     //   48: iconst_1
/*      */     //   49: istore #6
/*      */     //   51: aload_1
/*      */     //   52: getfield pos : I
/*      */     //   55: aload_1
/*      */     //   56: getfield limit : I
/*      */     //   59: if_icmpge -> 512
/*      */     //   62: aload_1
/*      */     //   63: getfield bytes : [B
/*      */     //   66: aload_1
/*      */     //   67: dup
/*      */     //   68: getfield pos : I
/*      */     //   71: dup_x1
/*      */     //   72: iconst_1
/*      */     //   73: iadd
/*      */     //   74: putfield pos : I
/*      */     //   77: baload
/*      */     //   78: sipush #255
/*      */     //   81: iand
/*      */     //   82: i2c
/*      */     //   83: istore #7
/*      */     //   85: iinc #3, 1
/*      */     //   88: getstatic io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$1.$SwitchMap$io$netty$handler$codec$http$multipart$HttpPostRequestDecoder$MultiPartStatus : [I
/*      */     //   91: aload_0
/*      */     //   92: getfield currentStatus : Lio/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus;
/*      */     //   95: invokevirtual ordinal : ()I
/*      */     //   98: iaload
/*      */     //   99: lookupswitch default -> 498, 1 -> 124, 2 -> 287
/*      */     //   124: iload #7
/*      */     //   126: bipush #61
/*      */     //   128: if_icmpne -> 192
/*      */     //   131: aload_0
/*      */     //   132: getstatic io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus.FIELD : Lio/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus;
/*      */     //   135: putfield currentStatus : Lio/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus;
/*      */     //   138: iload_3
/*      */     //   139: iconst_1
/*      */     //   140: isub
/*      */     //   141: istore #4
/*      */     //   143: aload_0
/*      */     //   144: getfield undecodedChunk : Lio/netty/buffer/ByteBuf;
/*      */     //   147: iload_2
/*      */     //   148: iload #4
/*      */     //   150: iload_2
/*      */     //   151: isub
/*      */     //   152: aload_0
/*      */     //   153: getfield charset : Ljava/nio/charset/Charset;
/*      */     //   156: invokevirtual toString : (IILjava/nio/charset/Charset;)Ljava/lang/String;
/*      */     //   159: aload_0
/*      */     //   160: getfield charset : Ljava/nio/charset/Charset;
/*      */     //   163: invokestatic decodeAttribute : (Ljava/lang/String;Ljava/nio/charset/Charset;)Ljava/lang/String;
/*      */     //   166: astore #8
/*      */     //   168: aload_0
/*      */     //   169: aload_0
/*      */     //   170: getfield factory : Lio/netty/handler/codec/http/multipart/HttpDataFactory;
/*      */     //   173: aload_0
/*      */     //   174: getfield request : Lio/netty/handler/codec/http/HttpRequest;
/*      */     //   177: aload #8
/*      */     //   179: invokeinterface createAttribute : (Lio/netty/handler/codec/http/HttpRequest;Ljava/lang/String;)Lio/netty/handler/codec/http/multipart/Attribute;
/*      */     //   184: putfield currentAttribute : Lio/netty/handler/codec/http/multipart/Attribute;
/*      */     //   187: iload_3
/*      */     //   188: istore_2
/*      */     //   189: goto -> 509
/*      */     //   192: iload #7
/*      */     //   194: bipush #38
/*      */     //   196: if_icmpne -> 509
/*      */     //   199: aload_0
/*      */     //   200: getstatic io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus.DISPOSITION : Lio/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus;
/*      */     //   203: putfield currentStatus : Lio/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus;
/*      */     //   206: iload_3
/*      */     //   207: iconst_1
/*      */     //   208: isub
/*      */     //   209: istore #5
/*      */     //   211: aload_0
/*      */     //   212: getfield undecodedChunk : Lio/netty/buffer/ByteBuf;
/*      */     //   215: iload_2
/*      */     //   216: iload #5
/*      */     //   218: iload_2
/*      */     //   219: isub
/*      */     //   220: aload_0
/*      */     //   221: getfield charset : Ljava/nio/charset/Charset;
/*      */     //   224: invokevirtual toString : (IILjava/nio/charset/Charset;)Ljava/lang/String;
/*      */     //   227: aload_0
/*      */     //   228: getfield charset : Ljava/nio/charset/Charset;
/*      */     //   231: invokestatic decodeAttribute : (Ljava/lang/String;Ljava/nio/charset/Charset;)Ljava/lang/String;
/*      */     //   234: astore #8
/*      */     //   236: aload_0
/*      */     //   237: aload_0
/*      */     //   238: getfield factory : Lio/netty/handler/codec/http/multipart/HttpDataFactory;
/*      */     //   241: aload_0
/*      */     //   242: getfield request : Lio/netty/handler/codec/http/HttpRequest;
/*      */     //   245: aload #8
/*      */     //   247: invokeinterface createAttribute : (Lio/netty/handler/codec/http/HttpRequest;Ljava/lang/String;)Lio/netty/handler/codec/http/multipart/Attribute;
/*      */     //   252: putfield currentAttribute : Lio/netty/handler/codec/http/multipart/Attribute;
/*      */     //   255: aload_0
/*      */     //   256: getfield currentAttribute : Lio/netty/handler/codec/http/multipart/Attribute;
/*      */     //   259: ldc ''
/*      */     //   261: invokeinterface setValue : (Ljava/lang/String;)V
/*      */     //   266: aload_0
/*      */     //   267: aload_0
/*      */     //   268: getfield currentAttribute : Lio/netty/handler/codec/http/multipart/Attribute;
/*      */     //   271: invokevirtual addHttpData : (Lio/netty/handler/codec/http/multipart/InterfaceHttpData;)V
/*      */     //   274: aload_0
/*      */     //   275: aconst_null
/*      */     //   276: putfield currentAttribute : Lio/netty/handler/codec/http/multipart/Attribute;
/*      */     //   279: iload_3
/*      */     //   280: istore_2
/*      */     //   281: iconst_1
/*      */     //   282: istore #6
/*      */     //   284: goto -> 509
/*      */     //   287: iload #7
/*      */     //   289: bipush #38
/*      */     //   291: if_icmpne -> 330
/*      */     //   294: aload_0
/*      */     //   295: getstatic io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus.DISPOSITION : Lio/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus;
/*      */     //   298: putfield currentStatus : Lio/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus;
/*      */     //   301: iload_3
/*      */     //   302: iconst_1
/*      */     //   303: isub
/*      */     //   304: istore #5
/*      */     //   306: aload_0
/*      */     //   307: aload_0
/*      */     //   308: getfield undecodedChunk : Lio/netty/buffer/ByteBuf;
/*      */     //   311: iload_2
/*      */     //   312: iload #5
/*      */     //   314: iload_2
/*      */     //   315: isub
/*      */     //   316: invokevirtual copy : (II)Lio/netty/buffer/ByteBuf;
/*      */     //   319: invokespecial setFinalBuffer : (Lio/netty/buffer/ByteBuf;)V
/*      */     //   322: iload_3
/*      */     //   323: istore_2
/*      */     //   324: iconst_1
/*      */     //   325: istore #6
/*      */     //   327: goto -> 509
/*      */     //   330: iload #7
/*      */     //   332: bipush #13
/*      */     //   334: if_icmpne -> 450
/*      */     //   337: aload_1
/*      */     //   338: getfield pos : I
/*      */     //   341: aload_1
/*      */     //   342: getfield limit : I
/*      */     //   345: if_icmpge -> 437
/*      */     //   348: aload_1
/*      */     //   349: getfield bytes : [B
/*      */     //   352: aload_1
/*      */     //   353: dup
/*      */     //   354: getfield pos : I
/*      */     //   357: dup_x1
/*      */     //   358: iconst_1
/*      */     //   359: iadd
/*      */     //   360: putfield pos : I
/*      */     //   363: baload
/*      */     //   364: sipush #255
/*      */     //   367: iand
/*      */     //   368: i2c
/*      */     //   369: istore #7
/*      */     //   371: iinc #3, 1
/*      */     //   374: iload #7
/*      */     //   376: bipush #10
/*      */     //   378: if_icmpne -> 422
/*      */     //   381: aload_0
/*      */     //   382: getstatic io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus.PREEPILOGUE : Lio/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus;
/*      */     //   385: putfield currentStatus : Lio/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus;
/*      */     //   388: iload_3
/*      */     //   389: iconst_2
/*      */     //   390: isub
/*      */     //   391: istore #5
/*      */     //   393: aload_1
/*      */     //   394: iconst_0
/*      */     //   395: invokevirtual setReadPosition : (I)V
/*      */     //   398: aload_0
/*      */     //   399: aload_0
/*      */     //   400: getfield undecodedChunk : Lio/netty/buffer/ByteBuf;
/*      */     //   403: iload_2
/*      */     //   404: iload #5
/*      */     //   406: iload_2
/*      */     //   407: isub
/*      */     //   408: invokevirtual copy : (II)Lio/netty/buffer/ByteBuf;
/*      */     //   411: invokespecial setFinalBuffer : (Lio/netty/buffer/ByteBuf;)V
/*      */     //   414: iload_3
/*      */     //   415: istore_2
/*      */     //   416: iconst_0
/*      */     //   417: istore #6
/*      */     //   419: goto -> 512
/*      */     //   422: aload_1
/*      */     //   423: iconst_0
/*      */     //   424: invokevirtual setReadPosition : (I)V
/*      */     //   427: new io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$ErrorDataDecoderException
/*      */     //   430: dup
/*      */     //   431: ldc 'Bad end of line'
/*      */     //   433: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   436: athrow
/*      */     //   437: aload_1
/*      */     //   438: getfield limit : I
/*      */     //   441: ifle -> 509
/*      */     //   444: iinc #3, -1
/*      */     //   447: goto -> 509
/*      */     //   450: iload #7
/*      */     //   452: bipush #10
/*      */     //   454: if_icmpne -> 509
/*      */     //   457: aload_0
/*      */     //   458: getstatic io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus.PREEPILOGUE : Lio/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus;
/*      */     //   461: putfield currentStatus : Lio/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus;
/*      */     //   464: iload_3
/*      */     //   465: iconst_1
/*      */     //   466: isub
/*      */     //   467: istore #5
/*      */     //   469: aload_1
/*      */     //   470: iconst_0
/*      */     //   471: invokevirtual setReadPosition : (I)V
/*      */     //   474: aload_0
/*      */     //   475: aload_0
/*      */     //   476: getfield undecodedChunk : Lio/netty/buffer/ByteBuf;
/*      */     //   479: iload_2
/*      */     //   480: iload #5
/*      */     //   482: iload_2
/*      */     //   483: isub
/*      */     //   484: invokevirtual copy : (II)Lio/netty/buffer/ByteBuf;
/*      */     //   487: invokespecial setFinalBuffer : (Lio/netty/buffer/ByteBuf;)V
/*      */     //   490: iload_3
/*      */     //   491: istore_2
/*      */     //   492: iconst_0
/*      */     //   493: istore #6
/*      */     //   495: goto -> 512
/*      */     //   498: aload_1
/*      */     //   499: iconst_0
/*      */     //   500: invokevirtual setReadPosition : (I)V
/*      */     //   503: iconst_0
/*      */     //   504: istore #6
/*      */     //   506: goto -> 512
/*      */     //   509: goto -> 51
/*      */     //   512: aload_0
/*      */     //   513: getfield isLastChunk : Z
/*      */     //   516: ifeq -> 592
/*      */     //   519: aload_0
/*      */     //   520: getfield currentAttribute : Lio/netty/handler/codec/http/multipart/Attribute;
/*      */     //   523: ifnull -> 592
/*      */     //   526: iload_3
/*      */     //   527: istore #5
/*      */     //   529: iload #5
/*      */     //   531: iload_2
/*      */     //   532: if_icmple -> 554
/*      */     //   535: aload_0
/*      */     //   536: aload_0
/*      */     //   537: getfield undecodedChunk : Lio/netty/buffer/ByteBuf;
/*      */     //   540: iload_2
/*      */     //   541: iload #5
/*      */     //   543: iload_2
/*      */     //   544: isub
/*      */     //   545: invokevirtual copy : (II)Lio/netty/buffer/ByteBuf;
/*      */     //   548: invokespecial setFinalBuffer : (Lio/netty/buffer/ByteBuf;)V
/*      */     //   551: goto -> 573
/*      */     //   554: aload_0
/*      */     //   555: getfield currentAttribute : Lio/netty/handler/codec/http/multipart/Attribute;
/*      */     //   558: invokeinterface isCompleted : ()Z
/*      */     //   563: ifne -> 573
/*      */     //   566: aload_0
/*      */     //   567: getstatic io/netty/buffer/Unpooled.EMPTY_BUFFER : Lio/netty/buffer/ByteBuf;
/*      */     //   570: invokespecial setFinalBuffer : (Lio/netty/buffer/ByteBuf;)V
/*      */     //   573: iload_3
/*      */     //   574: istore_2
/*      */     //   575: aload_0
/*      */     //   576: getstatic io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus.EPILOGUE : Lio/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus;
/*      */     //   579: putfield currentStatus : Lio/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus;
/*      */     //   582: aload_0
/*      */     //   583: getfield undecodedChunk : Lio/netty/buffer/ByteBuf;
/*      */     //   586: iload_2
/*      */     //   587: invokevirtual readerIndex : (I)Lio/netty/buffer/ByteBuf;
/*      */     //   590: pop
/*      */     //   591: return
/*      */     //   592: iload #6
/*      */     //   594: ifeq -> 649
/*      */     //   597: aload_0
/*      */     //   598: getfield currentAttribute : Lio/netty/handler/codec/http/multipart/Attribute;
/*      */     //   601: ifnull -> 649
/*      */     //   604: aload_0
/*      */     //   605: getfield currentStatus : Lio/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus;
/*      */     //   608: getstatic io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus.FIELD : Lio/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus;
/*      */     //   611: if_acmpne -> 637
/*      */     //   614: aload_0
/*      */     //   615: getfield currentAttribute : Lio/netty/handler/codec/http/multipart/Attribute;
/*      */     //   618: aload_0
/*      */     //   619: getfield undecodedChunk : Lio/netty/buffer/ByteBuf;
/*      */     //   622: iload_2
/*      */     //   623: iload_3
/*      */     //   624: iload_2
/*      */     //   625: isub
/*      */     //   626: invokevirtual copy : (II)Lio/netty/buffer/ByteBuf;
/*      */     //   629: iconst_0
/*      */     //   630: invokeinterface addContent : (Lio/netty/buffer/ByteBuf;Z)V
/*      */     //   635: iload_3
/*      */     //   636: istore_2
/*      */     //   637: aload_0
/*      */     //   638: getfield undecodedChunk : Lio/netty/buffer/ByteBuf;
/*      */     //   641: iload_2
/*      */     //   642: invokevirtual readerIndex : (I)Lio/netty/buffer/ByteBuf;
/*      */     //   645: pop
/*      */     //   646: goto -> 658
/*      */     //   649: aload_0
/*      */     //   650: getfield undecodedChunk : Lio/netty/buffer/ByteBuf;
/*      */     //   653: iload_2
/*      */     //   654: invokevirtual readerIndex : (I)Lio/netty/buffer/ByteBuf;
/*      */     //   657: pop
/*      */     //   658: goto -> 696
/*      */     //   661: astore #7
/*      */     //   663: aload_0
/*      */     //   664: getfield undecodedChunk : Lio/netty/buffer/ByteBuf;
/*      */     //   667: iload_2
/*      */     //   668: invokevirtual readerIndex : (I)Lio/netty/buffer/ByteBuf;
/*      */     //   671: pop
/*      */     //   672: aload #7
/*      */     //   674: athrow
/*      */     //   675: astore #7
/*      */     //   677: aload_0
/*      */     //   678: getfield undecodedChunk : Lio/netty/buffer/ByteBuf;
/*      */     //   681: iload_2
/*      */     //   682: invokevirtual readerIndex : (I)Lio/netty/buffer/ByteBuf;
/*      */     //   685: pop
/*      */     //   686: new io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$ErrorDataDecoderException
/*      */     //   689: dup
/*      */     //   690: aload #7
/*      */     //   692: invokespecial <init> : (Ljava/lang/Throwable;)V
/*      */     //   695: athrow
/*      */     //   696: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #627	-> 0
/*      */     //   #631	-> 12
/*      */     //   #628	-> 15
/*      */     //   #629	-> 16
/*      */     //   #630	-> 20
/*      */     //   #632	-> 21
/*      */     //   #633	-> 29
/*      */     //   #636	-> 31
/*      */     //   #637	-> 41
/*      */     //   #639	-> 48
/*      */     //   #641	-> 51
/*      */     //   #642	-> 62
/*      */     //   #643	-> 85
/*      */     //   #644	-> 88
/*      */     //   #646	-> 124
/*      */     //   #647	-> 131
/*      */     //   #648	-> 138
/*      */     //   #649	-> 143
/*      */     //   #651	-> 168
/*      */     //   #652	-> 187
/*      */     //   #653	-> 189
/*      */     //   #654	-> 199
/*      */     //   #655	-> 206
/*      */     //   #656	-> 211
/*      */     //   #658	-> 236
/*      */     //   #659	-> 255
/*      */     //   #660	-> 266
/*      */     //   #661	-> 274
/*      */     //   #662	-> 279
/*      */     //   #663	-> 281
/*      */     //   #664	-> 284
/*      */     //   #667	-> 287
/*      */     //   #668	-> 294
/*      */     //   #669	-> 301
/*      */     //   #670	-> 306
/*      */     //   #671	-> 322
/*      */     //   #672	-> 324
/*      */     //   #673	-> 330
/*      */     //   #674	-> 337
/*      */     //   #675	-> 348
/*      */     //   #676	-> 371
/*      */     //   #677	-> 374
/*      */     //   #678	-> 381
/*      */     //   #679	-> 388
/*      */     //   #680	-> 393
/*      */     //   #681	-> 398
/*      */     //   #682	-> 414
/*      */     //   #683	-> 416
/*      */     //   #684	-> 419
/*      */     //   #687	-> 422
/*      */     //   #688	-> 427
/*      */     //   #691	-> 437
/*      */     //   #692	-> 444
/*      */     //   #695	-> 450
/*      */     //   #696	-> 457
/*      */     //   #697	-> 464
/*      */     //   #698	-> 469
/*      */     //   #699	-> 474
/*      */     //   #700	-> 490
/*      */     //   #701	-> 492
/*      */     //   #702	-> 495
/*      */     //   #707	-> 498
/*      */     //   #708	-> 503
/*      */     //   #709	-> 506
/*      */     //   #711	-> 509
/*      */     //   #712	-> 512
/*      */     //   #714	-> 526
/*      */     //   #715	-> 529
/*      */     //   #716	-> 535
/*      */     //   #717	-> 554
/*      */     //   #718	-> 566
/*      */     //   #720	-> 573
/*      */     //   #721	-> 575
/*      */     //   #722	-> 582
/*      */     //   #723	-> 591
/*      */     //   #725	-> 592
/*      */     //   #727	-> 604
/*      */     //   #728	-> 614
/*      */     //   #730	-> 635
/*      */     //   #732	-> 637
/*      */     //   #735	-> 649
/*      */     //   #745	-> 658
/*      */     //   #737	-> 661
/*      */     //   #739	-> 663
/*      */     //   #740	-> 672
/*      */     //   #741	-> 675
/*      */     //   #743	-> 677
/*      */     //   #744	-> 686
/*      */     //   #746	-> 696
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   16	5	2	e1	Lio/netty/handler/codec/http/multipart/HttpPostBodyUtil$SeekAheadNoBackArrayException;
/*      */     //   168	21	8	key	Ljava/lang/String;
/*      */     //   143	49	4	equalpos	I
/*      */     //   236	48	8	key	Ljava/lang/String;
/*      */     //   211	119	5	ampersandpos	I
/*      */     //   393	29	5	ampersandpos	I
/*      */     //   85	424	7	read	C
/*      */     //   469	123	5	ampersandpos	I
/*      */     //   663	12	7	e	Lio/netty/handler/codec/http/multipart/HttpPostRequestDecoder$ErrorDataDecoderException;
/*      */     //   677	19	7	e	Ljava/io/IOException;
/*      */     //   0	697	0	this	Lio/netty/handler/codec/http/multipart/HttpPostRequestDecoder;
/*      */     //   12	685	1	sao	Lio/netty/handler/codec/http/multipart/HttpPostBodyUtil$SeekAheadOptimize;
/*      */     //   29	668	2	firstpos	I
/*      */     //   31	666	3	currentpos	I
/*      */     //   51	646	6	contRead	Z
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   0	12	15	io/netty/handler/codec/http/multipart/HttpPostBodyUtil$SeekAheadNoBackArrayException
/*      */     //   51	591	661	io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$ErrorDataDecoderException
/*      */     //   51	591	675	java/io/IOException
/*      */     //   592	658	661	io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$ErrorDataDecoderException
/*      */     //   592	658	675	java/io/IOException
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setFinalBuffer(ByteBuf buffer) throws ErrorDataDecoderException, IOException {
/*  749 */     this.currentAttribute.addContent(buffer, true);
/*  750 */     String value = decodeAttribute(this.currentAttribute.getByteBuf().toString(this.charset), this.charset);
/*  751 */     this.currentAttribute.setValue(value);
/*  752 */     addHttpData(this.currentAttribute);
/*  753 */     this.currentAttribute = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String decodeAttribute(String s, Charset charset) throws ErrorDataDecoderException {
/*      */     try {
/*  763 */       return QueryStringDecoder.decodeComponent(s, charset);
/*  764 */     } catch (IllegalArgumentException e) {
/*  765 */       throw new ErrorDataDecoderException("Bad string: '" + s + '\'', e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void parseBodyMultipart() throws ErrorDataDecoderException {
/*  777 */     if (this.undecodedChunk == null || this.undecodedChunk.readableBytes() == 0) {
/*      */       return;
/*      */     }
/*      */     
/*  781 */     InterfaceHttpData data = decodeMultipart(this.currentStatus);
/*  782 */     while (data != null) {
/*  783 */       addHttpData(data);
/*  784 */       if (this.currentStatus == MultiPartStatus.PREEPILOGUE || this.currentStatus == MultiPartStatus.EPILOGUE) {
/*      */         break;
/*      */       }
/*  787 */       data = decodeMultipart(this.currentStatus);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private InterfaceHttpData decodeMultipart(MultiPartStatus state) throws ErrorDataDecoderException {
/*      */     Charset localCharset;
/*      */     Attribute charsetAttribute;
/*      */     Attribute nameAttribute;
/*      */     Attribute finalAttribute;
/*  808 */     switch (state) {
/*      */       case NOTSTARTED:
/*  810 */         throw new ErrorDataDecoderException("Should not be called with the current getStatus");
/*      */       
/*      */       case PREAMBLE:
/*  813 */         throw new ErrorDataDecoderException("Should not be called with the current getStatus");
/*      */       
/*      */       case HEADERDELIMITER:
/*  816 */         return findMultipartDelimiter(this.multipartDataBoundary, MultiPartStatus.DISPOSITION, MultiPartStatus.PREEPILOGUE);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case DISPOSITION:
/*  829 */         return findMultipartDisposition();
/*      */ 
/*      */       
/*      */       case FIELD:
/*  833 */         localCharset = null;
/*  834 */         charsetAttribute = this.currentFieldAttributes.get("charset");
/*  835 */         if (charsetAttribute != null) {
/*      */           try {
/*  837 */             localCharset = Charset.forName(charsetAttribute.getValue());
/*  838 */           } catch (IOException e) {
/*  839 */             throw new ErrorDataDecoderException(e);
/*      */           } 
/*      */         }
/*  842 */         nameAttribute = this.currentFieldAttributes.get("name");
/*  843 */         if (this.currentAttribute == null) {
/*      */           try {
/*  845 */             this.currentAttribute = this.factory.createAttribute(this.request, cleanString(nameAttribute.getValue()));
/*      */           }
/*  847 */           catch (NullPointerException e) {
/*  848 */             throw new ErrorDataDecoderException(e);
/*  849 */           } catch (IllegalArgumentException e) {
/*  850 */             throw new ErrorDataDecoderException(e);
/*  851 */           } catch (IOException e) {
/*  852 */             throw new ErrorDataDecoderException(e);
/*      */           } 
/*  854 */           if (localCharset != null) {
/*  855 */             this.currentAttribute.setCharset(localCharset);
/*      */           }
/*      */         } 
/*      */         
/*      */         try {
/*  860 */           loadFieldMultipart(this.multipartDataBoundary);
/*  861 */         } catch (NotEnoughDataDecoderException e) {
/*  862 */           return null;
/*      */         } 
/*  864 */         finalAttribute = this.currentAttribute;
/*  865 */         this.currentAttribute = null;
/*  866 */         this.currentFieldAttributes = null;
/*      */         
/*  868 */         this.currentStatus = MultiPartStatus.HEADERDELIMITER;
/*  869 */         return finalAttribute;
/*      */ 
/*      */       
/*      */       case FILEUPLOAD:
/*  873 */         return getFileUpload(this.multipartDataBoundary);
/*      */ 
/*      */ 
/*      */       
/*      */       case MIXEDDELIMITER:
/*  878 */         return findMultipartDelimiter(this.multipartMixedBoundary, MultiPartStatus.MIXEDDISPOSITION, MultiPartStatus.HEADERDELIMITER);
/*      */ 
/*      */       
/*      */       case MIXEDDISPOSITION:
/*  882 */         return findMultipartDisposition();
/*      */ 
/*      */       
/*      */       case MIXEDFILEUPLOAD:
/*  886 */         return getFileUpload(this.multipartMixedBoundary);
/*      */       
/*      */       case PREEPILOGUE:
/*  889 */         return null;
/*      */       case EPILOGUE:
/*  891 */         return null;
/*      */     } 
/*  893 */     throw new ErrorDataDecoderException("Shouldn't reach here.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void skipControlCharacters() throws NotEnoughDataDecoderException {
/*      */     HttpPostBodyUtil.SeekAheadOptimize sao;
/*      */     try {
/*  905 */       sao = new HttpPostBodyUtil.SeekAheadOptimize(this.undecodedChunk);
/*  906 */     } catch (SeekAheadNoBackArrayException e) {
/*      */       try {
/*  908 */         skipControlCharactersStandard();
/*  909 */       } catch (IndexOutOfBoundsException e1) {
/*  910 */         throw new NotEnoughDataDecoderException(e1);
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/*  915 */     while (sao.pos < sao.limit) {
/*  916 */       char c = (char)(sao.bytes[sao.pos++] & 0xFF);
/*  917 */       if (!Character.isISOControl(c) && !Character.isWhitespace(c)) {
/*  918 */         sao.setReadPosition(1);
/*      */         return;
/*      */       } 
/*      */     } 
/*  922 */     throw new NotEnoughDataDecoderException("Access out of bounds");
/*      */   }
/*      */   
/*      */   void skipControlCharactersStandard() {
/*      */     while (true) {
/*  927 */       char c = (char)this.undecodedChunk.readUnsignedByte();
/*  928 */       if (!Character.isISOControl(c) && !Character.isWhitespace(c)) {
/*  929 */         this.undecodedChunk.readerIndex(this.undecodedChunk.readerIndex() - 1);
/*      */         return;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private InterfaceHttpData findMultipartDelimiter(String delimiter, MultiPartStatus dispositionStatus, MultiPartStatus closeDelimiterStatus) throws ErrorDataDecoderException {
/*      */     String newline;
/*  950 */     int readerIndex = this.undecodedChunk.readerIndex();
/*      */     try {
/*  952 */       skipControlCharacters();
/*  953 */     } catch (NotEnoughDataDecoderException e1) {
/*  954 */       this.undecodedChunk.readerIndex(readerIndex);
/*  955 */       return null;
/*      */     } 
/*  957 */     skipOneLine();
/*      */     
/*      */     try {
/*  960 */       newline = readDelimiter(delimiter);
/*  961 */     } catch (NotEnoughDataDecoderException e) {
/*  962 */       this.undecodedChunk.readerIndex(readerIndex);
/*  963 */       return null;
/*      */     } 
/*  965 */     if (newline.equals(delimiter)) {
/*  966 */       this.currentStatus = dispositionStatus;
/*  967 */       return decodeMultipart(dispositionStatus);
/*      */     } 
/*  969 */     if (newline.equals(delimiter + "--")) {
/*      */       
/*  971 */       this.currentStatus = closeDelimiterStatus;
/*  972 */       if (this.currentStatus == MultiPartStatus.HEADERDELIMITER) {
/*      */ 
/*      */         
/*  975 */         this.currentFieldAttributes = null;
/*  976 */         return decodeMultipart(MultiPartStatus.HEADERDELIMITER);
/*      */       } 
/*  978 */       return null;
/*      */     } 
/*  980 */     this.undecodedChunk.readerIndex(readerIndex);
/*  981 */     throw new ErrorDataDecoderException("No Multipart delimiter found");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private InterfaceHttpData findMultipartDisposition() throws ErrorDataDecoderException {
/*  991 */     int readerIndex = this.undecodedChunk.readerIndex();
/*  992 */     if (this.currentStatus == MultiPartStatus.DISPOSITION) {
/*  993 */       this.currentFieldAttributes = new TreeMap<String, Attribute>(CaseIgnoringComparator.INSTANCE);
/*      */     }
/*      */     
/*  996 */     while (!skipOneLine()) {
/*      */       String newline;
/*      */       try {
/*  999 */         skipControlCharacters();
/* 1000 */         newline = readLine();
/* 1001 */       } catch (NotEnoughDataDecoderException e) {
/* 1002 */         this.undecodedChunk.readerIndex(readerIndex);
/* 1003 */         return null;
/*      */       } 
/* 1005 */       String[] contents = splitMultipartHeader(newline);
/* 1006 */       if (contents[0].equalsIgnoreCase("Content-Disposition")) {
/*      */         boolean checkSecondArg;
/* 1008 */         if (this.currentStatus == MultiPartStatus.DISPOSITION) {
/* 1009 */           checkSecondArg = contents[1].equalsIgnoreCase("form-data");
/*      */         } else {
/* 1011 */           checkSecondArg = (contents[1].equalsIgnoreCase("attachment") || contents[1].equalsIgnoreCase("file"));
/*      */         } 
/*      */         
/* 1014 */         if (checkSecondArg)
/*      */         {
/* 1016 */           for (int i = 2; i < contents.length; i++) {
/* 1017 */             Attribute attribute; String[] values = StringUtil.split(contents[i], '=');
/*      */             
/*      */             try {
/* 1020 */               String name = cleanString(values[0]);
/* 1021 */               String value = values[1];
/*      */ 
/*      */               
/* 1024 */               if ("filename".equals(name)) {
/*      */                 
/* 1026 */                 value = value.substring(1, value.length() - 1);
/*      */               } else {
/*      */                 
/* 1029 */                 value = cleanString(value);
/*      */               } 
/* 1031 */               attribute = this.factory.createAttribute(this.request, name, value);
/* 1032 */             } catch (NullPointerException e) {
/* 1033 */               throw new ErrorDataDecoderException(e);
/* 1034 */             } catch (IllegalArgumentException e) {
/* 1035 */               throw new ErrorDataDecoderException(e);
/*      */             } 
/* 1037 */             this.currentFieldAttributes.put(attribute.getName(), attribute);
/*      */           }  }  continue;
/*      */       } 
/* 1040 */       if (contents[0].equalsIgnoreCase("Content-Transfer-Encoding")) {
/*      */         Attribute attribute;
/*      */         try {
/* 1043 */           attribute = this.factory.createAttribute(this.request, "Content-Transfer-Encoding", cleanString(contents[1]));
/*      */         }
/* 1045 */         catch (NullPointerException e) {
/* 1046 */           throw new ErrorDataDecoderException(e);
/* 1047 */         } catch (IllegalArgumentException e) {
/* 1048 */           throw new ErrorDataDecoderException(e);
/*      */         } 
/* 1050 */         this.currentFieldAttributes.put("Content-Transfer-Encoding", attribute); continue;
/* 1051 */       }  if (contents[0].equalsIgnoreCase("Content-Length")) {
/*      */         Attribute attribute;
/*      */         try {
/* 1054 */           attribute = this.factory.createAttribute(this.request, "Content-Length", cleanString(contents[1]));
/*      */         }
/* 1056 */         catch (NullPointerException e) {
/* 1057 */           throw new ErrorDataDecoderException(e);
/* 1058 */         } catch (IllegalArgumentException e) {
/* 1059 */           throw new ErrorDataDecoderException(e);
/*      */         } 
/* 1061 */         this.currentFieldAttributes.put("Content-Length", attribute); continue;
/* 1062 */       }  if (contents[0].equalsIgnoreCase("Content-Type")) {
/*      */         
/* 1064 */         if (contents[1].equalsIgnoreCase("multipart/mixed")) {
/* 1065 */           if (this.currentStatus == MultiPartStatus.DISPOSITION) {
/* 1066 */             String[] values = StringUtil.split(contents[2], '=');
/* 1067 */             this.multipartMixedBoundary = "--" + values[1];
/* 1068 */             this.currentStatus = MultiPartStatus.MIXEDDELIMITER;
/* 1069 */             return decodeMultipart(MultiPartStatus.MIXEDDELIMITER);
/*      */           } 
/* 1071 */           throw new ErrorDataDecoderException("Mixed Multipart found in a previous Mixed Multipart");
/*      */         } 
/*      */         
/* 1074 */         for (int i = 1; i < contents.length; i++) {
/* 1075 */           if (contents[i].toLowerCase().startsWith("charset")) {
/* 1076 */             Attribute attribute; String[] values = StringUtil.split(contents[i], '=');
/*      */             
/*      */             try {
/* 1079 */               attribute = this.factory.createAttribute(this.request, "charset", cleanString(values[1]));
/*      */             }
/* 1081 */             catch (NullPointerException e) {
/* 1082 */               throw new ErrorDataDecoderException(e);
/* 1083 */             } catch (IllegalArgumentException e) {
/* 1084 */               throw new ErrorDataDecoderException(e);
/*      */             } 
/* 1086 */             this.currentFieldAttributes.put("charset", attribute);
/*      */           } else {
/*      */             Attribute attribute;
/*      */             try {
/* 1090 */               attribute = this.factory.createAttribute(this.request, cleanString(contents[0]), contents[i]);
/*      */             }
/* 1092 */             catch (NullPointerException e) {
/* 1093 */               throw new ErrorDataDecoderException(e);
/* 1094 */             } catch (IllegalArgumentException e) {
/* 1095 */               throw new ErrorDataDecoderException(e);
/*      */             } 
/* 1097 */             this.currentFieldAttributes.put(attribute.getName(), attribute);
/*      */           } 
/*      */         } 
/*      */         continue;
/*      */       } 
/* 1102 */       throw new ErrorDataDecoderException("Unknown Params: " + newline);
/*      */     } 
/*      */ 
/*      */     
/* 1106 */     Attribute filenameAttribute = this.currentFieldAttributes.get("filename");
/* 1107 */     if (this.currentStatus == MultiPartStatus.DISPOSITION) {
/* 1108 */       if (filenameAttribute != null) {
/*      */         
/* 1110 */         this.currentStatus = MultiPartStatus.FILEUPLOAD;
/*      */         
/* 1112 */         return decodeMultipart(MultiPartStatus.FILEUPLOAD);
/*      */       } 
/*      */       
/* 1115 */       this.currentStatus = MultiPartStatus.FIELD;
/*      */       
/* 1117 */       return decodeMultipart(MultiPartStatus.FIELD);
/*      */     } 
/*      */     
/* 1120 */     if (filenameAttribute != null) {
/*      */       
/* 1122 */       this.currentStatus = MultiPartStatus.MIXEDFILEUPLOAD;
/*      */       
/* 1124 */       return decodeMultipart(MultiPartStatus.MIXEDFILEUPLOAD);
/*      */     } 
/*      */     
/* 1127 */     throw new ErrorDataDecoderException("Filename not found");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected InterfaceHttpData getFileUpload(String delimiter) throws ErrorDataDecoderException {
/* 1143 */     Attribute encoding = this.currentFieldAttributes.get("Content-Transfer-Encoding");
/* 1144 */     Charset localCharset = this.charset;
/*      */     
/* 1146 */     HttpPostBodyUtil.TransferEncodingMechanism mechanism = HttpPostBodyUtil.TransferEncodingMechanism.BIT7;
/* 1147 */     if (encoding != null) {
/*      */       String code;
/*      */       try {
/* 1150 */         code = encoding.getValue().toLowerCase();
/* 1151 */       } catch (IOException e) {
/* 1152 */         throw new ErrorDataDecoderException(e);
/*      */       } 
/* 1154 */       if (code.equals(HttpPostBodyUtil.TransferEncodingMechanism.BIT7.value())) {
/* 1155 */         localCharset = HttpPostBodyUtil.US_ASCII;
/* 1156 */       } else if (code.equals(HttpPostBodyUtil.TransferEncodingMechanism.BIT8.value())) {
/* 1157 */         localCharset = HttpPostBodyUtil.ISO_8859_1;
/* 1158 */         mechanism = HttpPostBodyUtil.TransferEncodingMechanism.BIT8;
/* 1159 */       } else if (code.equals(HttpPostBodyUtil.TransferEncodingMechanism.BINARY.value())) {
/*      */         
/* 1161 */         mechanism = HttpPostBodyUtil.TransferEncodingMechanism.BINARY;
/*      */       } else {
/* 1163 */         throw new ErrorDataDecoderException("TransferEncoding Unknown: " + code);
/*      */       } 
/*      */     } 
/* 1166 */     Attribute charsetAttribute = this.currentFieldAttributes.get("charset");
/* 1167 */     if (charsetAttribute != null) {
/*      */       try {
/* 1169 */         localCharset = Charset.forName(charsetAttribute.getValue());
/* 1170 */       } catch (IOException e) {
/* 1171 */         throw new ErrorDataDecoderException(e);
/*      */       } 
/*      */     }
/* 1174 */     if (this.currentFileUpload == null) {
/* 1175 */       long l; Attribute filenameAttribute = this.currentFieldAttributes.get("filename");
/* 1176 */       Attribute nameAttribute = this.currentFieldAttributes.get("name");
/* 1177 */       Attribute contentTypeAttribute = this.currentFieldAttributes.get("Content-Type");
/* 1178 */       if (contentTypeAttribute == null) {
/* 1179 */         throw new ErrorDataDecoderException("Content-Type is absent but required");
/*      */       }
/* 1181 */       Attribute lengthAttribute = this.currentFieldAttributes.get("Content-Length");
/*      */       
/*      */       try {
/* 1184 */         l = (lengthAttribute != null) ? Long.parseLong(lengthAttribute.getValue()) : 0L;
/* 1185 */       } catch (IOException e) {
/* 1186 */         throw new ErrorDataDecoderException(e);
/* 1187 */       } catch (NumberFormatException e) {
/* 1188 */         l = 0L;
/*      */       } 
/*      */       try {
/* 1191 */         this.currentFileUpload = this.factory.createFileUpload(this.request, cleanString(nameAttribute.getValue()), cleanString(filenameAttribute.getValue()), contentTypeAttribute.getValue(), mechanism.value(), localCharset, l);
/*      */ 
/*      */       
/*      */       }
/* 1195 */       catch (NullPointerException e) {
/* 1196 */         throw new ErrorDataDecoderException(e);
/* 1197 */       } catch (IllegalArgumentException e) {
/* 1198 */         throw new ErrorDataDecoderException(e);
/* 1199 */       } catch (IOException e) {
/* 1200 */         throw new ErrorDataDecoderException(e);
/*      */       } 
/*      */     } 
/*      */     
/*      */     try {
/* 1205 */       readFileUploadByteMultipart(delimiter);
/* 1206 */     } catch (NotEnoughDataDecoderException e) {
/*      */ 
/*      */ 
/*      */       
/* 1210 */       return null;
/*      */     } 
/* 1212 */     if (this.currentFileUpload.isCompleted()) {
/*      */       
/* 1214 */       if (this.currentStatus == MultiPartStatus.FILEUPLOAD) {
/* 1215 */         this.currentStatus = MultiPartStatus.HEADERDELIMITER;
/* 1216 */         this.currentFieldAttributes = null;
/*      */       } else {
/* 1218 */         this.currentStatus = MultiPartStatus.MIXEDDELIMITER;
/* 1219 */         cleanMixedAttributes();
/*      */       } 
/* 1221 */       FileUpload fileUpload = this.currentFileUpload;
/* 1222 */       this.currentFileUpload = null;
/* 1223 */       return fileUpload;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1228 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void destroy() {
/* 1236 */     checkDestroyed();
/* 1237 */     cleanFiles();
/* 1238 */     this.destroyed = true;
/*      */     
/* 1240 */     if (this.undecodedChunk != null && this.undecodedChunk.refCnt() > 0) {
/* 1241 */       this.undecodedChunk.release();
/* 1242 */       this.undecodedChunk = null;
/*      */     } 
/*      */ 
/*      */     
/* 1246 */     for (int i = this.bodyListHttpDataRank; i < this.bodyListHttpData.size(); i++) {
/* 1247 */       ((InterfaceHttpData)this.bodyListHttpData.get(i)).release();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void cleanFiles() {
/* 1255 */     checkDestroyed();
/*      */     
/* 1257 */     this.factory.cleanRequestHttpDatas(this.request);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeHttpDataFromClean(InterfaceHttpData data) {
/* 1264 */     checkDestroyed();
/*      */     
/* 1266 */     this.factory.removeHttpDataFromClean(this.request, data);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void cleanMixedAttributes() {
/* 1274 */     this.currentFieldAttributes.remove("charset");
/* 1275 */     this.currentFieldAttributes.remove("Content-Length");
/* 1276 */     this.currentFieldAttributes.remove("Content-Transfer-Encoding");
/* 1277 */     this.currentFieldAttributes.remove("Content-Type");
/* 1278 */     this.currentFieldAttributes.remove("filename");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String readLineStandard() throws NotEnoughDataDecoderException {
/* 1290 */     int readerIndex = this.undecodedChunk.readerIndex();
/*      */     try {
/* 1292 */       ByteBuf line = Unpooled.buffer(64);
/*      */       
/* 1294 */       while (this.undecodedChunk.isReadable()) {
/* 1295 */         byte nextByte = this.undecodedChunk.readByte();
/* 1296 */         if (nextByte == 13) {
/*      */           
/* 1298 */           nextByte = this.undecodedChunk.getByte(this.undecodedChunk.readerIndex());
/* 1299 */           if (nextByte == 10) {
/*      */             
/* 1301 */             this.undecodedChunk.skipBytes(1);
/* 1302 */             return line.toString(this.charset);
/*      */           } 
/*      */           
/* 1305 */           line.writeByte(13); continue;
/*      */         } 
/* 1307 */         if (nextByte == 10) {
/* 1308 */           return line.toString(this.charset);
/*      */         }
/* 1310 */         line.writeByte(nextByte);
/*      */       }
/*      */     
/* 1313 */     } catch (IndexOutOfBoundsException e) {
/* 1314 */       this.undecodedChunk.readerIndex(readerIndex);
/* 1315 */       throw new NotEnoughDataDecoderException(e);
/*      */     } 
/* 1317 */     this.undecodedChunk.readerIndex(readerIndex);
/* 1318 */     throw new NotEnoughDataDecoderException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String readLine() throws NotEnoughDataDecoderException {
/*      */     HttpPostBodyUtil.SeekAheadOptimize sao;
/*      */     try {
/* 1332 */       sao = new HttpPostBodyUtil.SeekAheadOptimize(this.undecodedChunk);
/* 1333 */     } catch (SeekAheadNoBackArrayException e1) {
/* 1334 */       return readLineStandard();
/*      */     } 
/* 1336 */     int readerIndex = this.undecodedChunk.readerIndex();
/*      */     try {
/* 1338 */       ByteBuf line = Unpooled.buffer(64);
/*      */       
/* 1340 */       while (sao.pos < sao.limit) {
/* 1341 */         byte nextByte = sao.bytes[sao.pos++];
/* 1342 */         if (nextByte == 13) {
/* 1343 */           if (sao.pos < sao.limit) {
/* 1344 */             nextByte = sao.bytes[sao.pos++];
/* 1345 */             if (nextByte == 10) {
/* 1346 */               sao.setReadPosition(0);
/* 1347 */               return line.toString(this.charset);
/*      */             } 
/*      */             
/* 1350 */             sao.pos--;
/* 1351 */             line.writeByte(13);
/*      */             continue;
/*      */           } 
/* 1354 */           line.writeByte(nextByte); continue;
/*      */         } 
/* 1356 */         if (nextByte == 10) {
/* 1357 */           sao.setReadPosition(0);
/* 1358 */           return line.toString(this.charset);
/*      */         } 
/* 1360 */         line.writeByte(nextByte);
/*      */       }
/*      */     
/* 1363 */     } catch (IndexOutOfBoundsException e) {
/* 1364 */       this.undecodedChunk.readerIndex(readerIndex);
/* 1365 */       throw new NotEnoughDataDecoderException(e);
/*      */     } 
/* 1367 */     this.undecodedChunk.readerIndex(readerIndex);
/* 1368 */     throw new NotEnoughDataDecoderException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String readDelimiterStandard(String delimiter) throws NotEnoughDataDecoderException {
/* 1387 */     int readerIndex = this.undecodedChunk.readerIndex();
/*      */     try {
/* 1389 */       StringBuilder sb = new StringBuilder(64);
/* 1390 */       int delimiterPos = 0;
/* 1391 */       int len = delimiter.length();
/* 1392 */       while (this.undecodedChunk.isReadable() && delimiterPos < len) {
/* 1393 */         byte nextByte = this.undecodedChunk.readByte();
/* 1394 */         if (nextByte == delimiter.charAt(delimiterPos)) {
/* 1395 */           delimiterPos++;
/* 1396 */           sb.append((char)nextByte);
/*      */           continue;
/*      */         } 
/* 1399 */         this.undecodedChunk.readerIndex(readerIndex);
/* 1400 */         throw new NotEnoughDataDecoderException();
/*      */       } 
/*      */ 
/*      */       
/* 1404 */       if (this.undecodedChunk.isReadable()) {
/* 1405 */         byte nextByte = this.undecodedChunk.readByte();
/*      */         
/* 1407 */         if (nextByte == 13) {
/* 1408 */           nextByte = this.undecodedChunk.readByte();
/* 1409 */           if (nextByte == 10) {
/* 1410 */             return sb.toString();
/*      */           }
/*      */ 
/*      */           
/* 1414 */           this.undecodedChunk.readerIndex(readerIndex);
/* 1415 */           throw new NotEnoughDataDecoderException();
/*      */         } 
/* 1417 */         if (nextByte == 10)
/* 1418 */           return sb.toString(); 
/* 1419 */         if (nextByte == 45) {
/* 1420 */           sb.append('-');
/*      */           
/* 1422 */           nextByte = this.undecodedChunk.readByte();
/* 1423 */           if (nextByte == 45) {
/* 1424 */             sb.append('-');
/*      */             
/* 1426 */             if (this.undecodedChunk.isReadable()) {
/* 1427 */               nextByte = this.undecodedChunk.readByte();
/* 1428 */               if (nextByte == 13) {
/* 1429 */                 nextByte = this.undecodedChunk.readByte();
/* 1430 */                 if (nextByte == 10) {
/* 1431 */                   return sb.toString();
/*      */                 }
/*      */ 
/*      */                 
/* 1435 */                 this.undecodedChunk.readerIndex(readerIndex);
/* 1436 */                 throw new NotEnoughDataDecoderException();
/*      */               } 
/* 1438 */               if (nextByte == 10) {
/* 1439 */                 return sb.toString();
/*      */               }
/*      */ 
/*      */ 
/*      */               
/* 1444 */               this.undecodedChunk.readerIndex(this.undecodedChunk.readerIndex() - 1);
/* 1445 */               return sb.toString();
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1452 */             return sb.toString();
/*      */           }
/*      */         
/*      */         }
/*      */       
/*      */       } 
/* 1458 */     } catch (IndexOutOfBoundsException e) {
/* 1459 */       this.undecodedChunk.readerIndex(readerIndex);
/* 1460 */       throw new NotEnoughDataDecoderException(e);
/*      */     } 
/* 1462 */     this.undecodedChunk.readerIndex(readerIndex);
/* 1463 */     throw new NotEnoughDataDecoderException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String readDelimiter(String delimiter) throws NotEnoughDataDecoderException {
/*      */     HttpPostBodyUtil.SeekAheadOptimize sao;
/*      */     try {
/* 1483 */       sao = new HttpPostBodyUtil.SeekAheadOptimize(this.undecodedChunk);
/* 1484 */     } catch (SeekAheadNoBackArrayException e1) {
/* 1485 */       return readDelimiterStandard(delimiter);
/*      */     } 
/* 1487 */     int readerIndex = this.undecodedChunk.readerIndex();
/* 1488 */     int delimiterPos = 0;
/* 1489 */     int len = delimiter.length();
/*      */     try {
/* 1491 */       StringBuilder sb = new StringBuilder(64);
/*      */       
/* 1493 */       while (sao.pos < sao.limit && delimiterPos < len) {
/* 1494 */         byte nextByte = sao.bytes[sao.pos++];
/* 1495 */         if (nextByte == delimiter.charAt(delimiterPos)) {
/* 1496 */           delimiterPos++;
/* 1497 */           sb.append((char)nextByte);
/*      */           continue;
/*      */         } 
/* 1500 */         this.undecodedChunk.readerIndex(readerIndex);
/* 1501 */         throw new NotEnoughDataDecoderException();
/*      */       } 
/*      */ 
/*      */       
/* 1505 */       if (sao.pos < sao.limit) {
/* 1506 */         byte nextByte = sao.bytes[sao.pos++];
/* 1507 */         if (nextByte == 13) {
/*      */           
/* 1509 */           if (sao.pos < sao.limit) {
/* 1510 */             nextByte = sao.bytes[sao.pos++];
/* 1511 */             if (nextByte == 10) {
/* 1512 */               sao.setReadPosition(0);
/* 1513 */               return sb.toString();
/*      */             } 
/*      */ 
/*      */             
/* 1517 */             this.undecodedChunk.readerIndex(readerIndex);
/* 1518 */             throw new NotEnoughDataDecoderException();
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 1523 */           this.undecodedChunk.readerIndex(readerIndex);
/* 1524 */           throw new NotEnoughDataDecoderException();
/*      */         } 
/* 1526 */         if (nextByte == 10) {
/*      */ 
/*      */           
/* 1529 */           sao.setReadPosition(0);
/* 1530 */           return sb.toString();
/* 1531 */         }  if (nextByte == 45) {
/* 1532 */           sb.append('-');
/*      */           
/* 1534 */           if (sao.pos < sao.limit) {
/* 1535 */             nextByte = sao.bytes[sao.pos++];
/* 1536 */             if (nextByte == 45) {
/* 1537 */               sb.append('-');
/*      */               
/* 1539 */               if (sao.pos < sao.limit) {
/* 1540 */                 nextByte = sao.bytes[sao.pos++];
/* 1541 */                 if (nextByte == 13) {
/* 1542 */                   if (sao.pos < sao.limit) {
/* 1543 */                     nextByte = sao.bytes[sao.pos++];
/* 1544 */                     if (nextByte == 10) {
/* 1545 */                       sao.setReadPosition(0);
/* 1546 */                       return sb.toString();
/*      */                     } 
/*      */ 
/*      */                     
/* 1550 */                     this.undecodedChunk.readerIndex(readerIndex);
/* 1551 */                     throw new NotEnoughDataDecoderException();
/*      */                   } 
/*      */ 
/*      */ 
/*      */                   
/* 1556 */                   this.undecodedChunk.readerIndex(readerIndex);
/* 1557 */                   throw new NotEnoughDataDecoderException();
/*      */                 } 
/* 1559 */                 if (nextByte == 10) {
/* 1560 */                   sao.setReadPosition(0);
/* 1561 */                   return sb.toString();
/*      */                 } 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1567 */                 sao.setReadPosition(1);
/* 1568 */                 return sb.toString();
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1575 */               sao.setReadPosition(0);
/* 1576 */               return sb.toString();
/*      */             }
/*      */           
/*      */           }
/*      */         
/*      */         }
/*      */       
/*      */       } 
/* 1584 */     } catch (IndexOutOfBoundsException e) {
/* 1585 */       this.undecodedChunk.readerIndex(readerIndex);
/* 1586 */       throw new NotEnoughDataDecoderException(e);
/*      */     } 
/* 1588 */     this.undecodedChunk.readerIndex(readerIndex);
/* 1589 */     throw new NotEnoughDataDecoderException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readFileUploadByteMultipartStandard(String delimiter) throws NotEnoughDataDecoderException, ErrorDataDecoderException {
/* 1604 */     int readerIndex = this.undecodedChunk.readerIndex();
/*      */     
/* 1606 */     boolean newLine = true;
/* 1607 */     int index = 0;
/* 1608 */     int lastPosition = this.undecodedChunk.readerIndex();
/* 1609 */     boolean found = false;
/* 1610 */     while (this.undecodedChunk.isReadable()) {
/* 1611 */       byte nextByte = this.undecodedChunk.readByte();
/* 1612 */       if (newLine) {
/*      */         
/* 1614 */         if (nextByte == delimiter.codePointAt(index)) {
/* 1615 */           index++;
/* 1616 */           if (delimiter.length() == index) {
/* 1617 */             found = true;
/*      */             break;
/*      */           } 
/*      */           continue;
/*      */         } 
/* 1622 */         newLine = false;
/* 1623 */         index = 0;
/*      */         
/* 1625 */         if (nextByte == 13) {
/* 1626 */           if (this.undecodedChunk.isReadable()) {
/* 1627 */             nextByte = this.undecodedChunk.readByte();
/* 1628 */             if (nextByte == 10) {
/* 1629 */               newLine = true;
/* 1630 */               index = 0;
/* 1631 */               lastPosition = this.undecodedChunk.readerIndex() - 2;
/*      */               continue;
/*      */             } 
/* 1634 */             lastPosition = this.undecodedChunk.readerIndex() - 1;
/*      */ 
/*      */             
/* 1637 */             this.undecodedChunk.readerIndex(lastPosition);
/*      */           }  continue;
/*      */         } 
/* 1640 */         if (nextByte == 10) {
/* 1641 */           newLine = true;
/* 1642 */           index = 0;
/* 1643 */           lastPosition = this.undecodedChunk.readerIndex() - 1;
/*      */           continue;
/*      */         } 
/* 1646 */         lastPosition = this.undecodedChunk.readerIndex();
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/* 1651 */       if (nextByte == 13) {
/* 1652 */         if (this.undecodedChunk.isReadable()) {
/* 1653 */           nextByte = this.undecodedChunk.readByte();
/* 1654 */           if (nextByte == 10) {
/* 1655 */             newLine = true;
/* 1656 */             index = 0;
/* 1657 */             lastPosition = this.undecodedChunk.readerIndex() - 2;
/*      */             continue;
/*      */           } 
/* 1660 */           lastPosition = this.undecodedChunk.readerIndex() - 1;
/*      */ 
/*      */           
/* 1663 */           this.undecodedChunk.readerIndex(lastPosition);
/*      */         }  continue;
/*      */       } 
/* 1666 */       if (nextByte == 10) {
/* 1667 */         newLine = true;
/* 1668 */         index = 0;
/* 1669 */         lastPosition = this.undecodedChunk.readerIndex() - 1;
/*      */         continue;
/*      */       } 
/* 1672 */       lastPosition = this.undecodedChunk.readerIndex();
/*      */     } 
/*      */ 
/*      */     
/* 1676 */     ByteBuf buffer = this.undecodedChunk.copy(readerIndex, lastPosition - readerIndex);
/* 1677 */     if (found) {
/*      */       
/*      */       try {
/* 1680 */         this.currentFileUpload.addContent(buffer, true);
/*      */         
/* 1682 */         this.undecodedChunk.readerIndex(lastPosition);
/* 1683 */       } catch (IOException e) {
/* 1684 */         throw new ErrorDataDecoderException(e);
/*      */       } 
/*      */     } else {
/*      */ 
/*      */       
/*      */       try {
/* 1690 */         this.currentFileUpload.addContent(buffer, false);
/*      */         
/* 1692 */         this.undecodedChunk.readerIndex(lastPosition);
/* 1693 */         throw new NotEnoughDataDecoderException();
/* 1694 */       } catch (IOException e) {
/* 1695 */         throw new ErrorDataDecoderException(e);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readFileUploadByteMultipart(String delimiter) throws NotEnoughDataDecoderException, ErrorDataDecoderException {
/*      */     HttpPostBodyUtil.SeekAheadOptimize sao;
/*      */     try {
/* 1714 */       sao = new HttpPostBodyUtil.SeekAheadOptimize(this.undecodedChunk);
/* 1715 */     } catch (SeekAheadNoBackArrayException e1) {
/* 1716 */       readFileUploadByteMultipartStandard(delimiter);
/*      */       return;
/*      */     } 
/* 1719 */     int readerIndex = this.undecodedChunk.readerIndex();
/*      */     
/* 1721 */     boolean newLine = true;
/* 1722 */     int index = 0;
/* 1723 */     int lastrealpos = sao.pos;
/*      */     
/* 1725 */     boolean found = false;
/*      */     
/* 1727 */     while (sao.pos < sao.limit) {
/* 1728 */       byte nextByte = sao.bytes[sao.pos++];
/* 1729 */       if (newLine) {
/*      */         
/* 1731 */         if (nextByte == delimiter.codePointAt(index)) {
/* 1732 */           index++;
/* 1733 */           if (delimiter.length() == index) {
/* 1734 */             found = true;
/*      */             break;
/*      */           } 
/*      */           continue;
/*      */         } 
/* 1739 */         newLine = false;
/* 1740 */         index = 0;
/*      */         
/* 1742 */         if (nextByte == 13) {
/* 1743 */           if (sao.pos < sao.limit) {
/* 1744 */             nextByte = sao.bytes[sao.pos++];
/* 1745 */             if (nextByte == 10) {
/* 1746 */               newLine = true;
/* 1747 */               index = 0;
/* 1748 */               lastrealpos = sao.pos - 2;
/*      */ 
/*      */               
/*      */               continue;
/*      */             } 
/*      */             
/* 1754 */             lastrealpos = --sao.pos;
/*      */           }  continue;
/*      */         } 
/* 1757 */         if (nextByte == 10) {
/* 1758 */           newLine = true;
/* 1759 */           index = 0;
/* 1760 */           lastrealpos = sao.pos - 1;
/*      */           continue;
/*      */         } 
/* 1763 */         lastrealpos = sao.pos;
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/* 1768 */       if (nextByte == 13) {
/* 1769 */         if (sao.pos < sao.limit) {
/* 1770 */           nextByte = sao.bytes[sao.pos++];
/* 1771 */           if (nextByte == 10) {
/* 1772 */             newLine = true;
/* 1773 */             index = 0;
/* 1774 */             lastrealpos = sao.pos - 2;
/*      */ 
/*      */             
/*      */             continue;
/*      */           } 
/*      */           
/* 1780 */           lastrealpos = --sao.pos;
/*      */         }  continue;
/*      */       } 
/* 1783 */       if (nextByte == 10) {
/* 1784 */         newLine = true;
/* 1785 */         index = 0;
/* 1786 */         lastrealpos = sao.pos - 1;
/*      */         continue;
/*      */       } 
/* 1789 */       lastrealpos = sao.pos;
/*      */     } 
/*      */ 
/*      */     
/* 1793 */     int lastPosition = sao.getReadPosition(lastrealpos);
/* 1794 */     ByteBuf buffer = this.undecodedChunk.copy(readerIndex, lastPosition - readerIndex);
/* 1795 */     if (found) {
/*      */       
/*      */       try {
/* 1798 */         this.currentFileUpload.addContent(buffer, true);
/*      */         
/* 1800 */         this.undecodedChunk.readerIndex(lastPosition);
/* 1801 */       } catch (IOException e) {
/* 1802 */         throw new ErrorDataDecoderException(e);
/*      */       } 
/*      */     } else {
/*      */ 
/*      */       
/*      */       try {
/* 1808 */         this.currentFileUpload.addContent(buffer, false);
/*      */         
/* 1810 */         this.undecodedChunk.readerIndex(lastPosition);
/* 1811 */         throw new NotEnoughDataDecoderException();
/* 1812 */       } catch (IOException e) {
/* 1813 */         throw new ErrorDataDecoderException(e);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void loadFieldMultipartStandard(String delimiter) throws NotEnoughDataDecoderException, ErrorDataDecoderException {
/* 1827 */     int readerIndex = this.undecodedChunk.readerIndex();
/*      */     
/*      */     try {
/* 1830 */       boolean newLine = true;
/* 1831 */       int index = 0;
/* 1832 */       int lastPosition = this.undecodedChunk.readerIndex();
/* 1833 */       boolean found = false;
/* 1834 */       while (this.undecodedChunk.isReadable()) {
/* 1835 */         byte nextByte = this.undecodedChunk.readByte();
/* 1836 */         if (newLine) {
/*      */           
/* 1838 */           if (nextByte == delimiter.codePointAt(index)) {
/* 1839 */             index++;
/* 1840 */             if (delimiter.length() == index) {
/* 1841 */               found = true;
/*      */               break;
/*      */             } 
/*      */             continue;
/*      */           } 
/* 1846 */           newLine = false;
/* 1847 */           index = 0;
/*      */           
/* 1849 */           if (nextByte == 13) {
/* 1850 */             if (this.undecodedChunk.isReadable()) {
/* 1851 */               nextByte = this.undecodedChunk.readByte();
/* 1852 */               if (nextByte == 10) {
/* 1853 */                 newLine = true;
/* 1854 */                 index = 0;
/* 1855 */                 lastPosition = this.undecodedChunk.readerIndex() - 2;
/*      */                 continue;
/*      */               } 
/* 1858 */               lastPosition = this.undecodedChunk.readerIndex() - 1;
/* 1859 */               this.undecodedChunk.readerIndex(lastPosition);
/*      */               continue;
/*      */             } 
/* 1862 */             lastPosition = this.undecodedChunk.readerIndex() - 1; continue;
/*      */           } 
/* 1864 */           if (nextByte == 10) {
/* 1865 */             newLine = true;
/* 1866 */             index = 0;
/* 1867 */             lastPosition = this.undecodedChunk.readerIndex() - 1; continue;
/*      */           } 
/* 1869 */           lastPosition = this.undecodedChunk.readerIndex();
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/* 1874 */         if (nextByte == 13) {
/* 1875 */           if (this.undecodedChunk.isReadable()) {
/* 1876 */             nextByte = this.undecodedChunk.readByte();
/* 1877 */             if (nextByte == 10) {
/* 1878 */               newLine = true;
/* 1879 */               index = 0;
/* 1880 */               lastPosition = this.undecodedChunk.readerIndex() - 2;
/*      */               continue;
/*      */             } 
/* 1883 */             lastPosition = this.undecodedChunk.readerIndex() - 1;
/* 1884 */             this.undecodedChunk.readerIndex(lastPosition);
/*      */             continue;
/*      */           } 
/* 1887 */           lastPosition = this.undecodedChunk.readerIndex() - 1; continue;
/*      */         } 
/* 1889 */         if (nextByte == 10) {
/* 1890 */           newLine = true;
/* 1891 */           index = 0;
/* 1892 */           lastPosition = this.undecodedChunk.readerIndex() - 1; continue;
/*      */         } 
/* 1894 */         lastPosition = this.undecodedChunk.readerIndex();
/*      */       } 
/*      */ 
/*      */       
/* 1898 */       if (found) {
/*      */ 
/*      */         
/*      */         try {
/*      */ 
/*      */           
/* 1904 */           this.currentAttribute.addContent(this.undecodedChunk.copy(readerIndex, lastPosition - readerIndex), true);
/*      */         }
/* 1906 */         catch (IOException e) {
/* 1907 */           throw new ErrorDataDecoderException(e);
/*      */         } 
/* 1909 */         this.undecodedChunk.readerIndex(lastPosition);
/*      */       } else {
/*      */         try {
/* 1912 */           this.currentAttribute.addContent(this.undecodedChunk.copy(readerIndex, lastPosition - readerIndex), false);
/*      */         }
/* 1914 */         catch (IOException e) {
/* 1915 */           throw new ErrorDataDecoderException(e);
/*      */         } 
/* 1917 */         this.undecodedChunk.readerIndex(lastPosition);
/* 1918 */         throw new NotEnoughDataDecoderException();
/*      */       } 
/* 1920 */     } catch (IndexOutOfBoundsException e) {
/* 1921 */       this.undecodedChunk.readerIndex(readerIndex);
/* 1922 */       throw new NotEnoughDataDecoderException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void loadFieldMultipart(String delimiter) throws NotEnoughDataDecoderException, ErrorDataDecoderException {
/*      */     HttpPostBodyUtil.SeekAheadOptimize sao;
/*      */     try {
/* 1936 */       sao = new HttpPostBodyUtil.SeekAheadOptimize(this.undecodedChunk);
/* 1937 */     } catch (SeekAheadNoBackArrayException e1) {
/* 1938 */       loadFieldMultipartStandard(delimiter);
/*      */       return;
/*      */     } 
/* 1941 */     int readerIndex = this.undecodedChunk.readerIndex();
/*      */     
/*      */     try {
/* 1944 */       boolean newLine = true;
/* 1945 */       int index = 0;
/*      */       
/* 1947 */       int lastrealpos = sao.pos;
/* 1948 */       boolean found = false;
/*      */       
/* 1950 */       while (sao.pos < sao.limit) {
/* 1951 */         byte nextByte = sao.bytes[sao.pos++];
/* 1952 */         if (newLine) {
/*      */           
/* 1954 */           if (nextByte == delimiter.codePointAt(index)) {
/* 1955 */             index++;
/* 1956 */             if (delimiter.length() == index) {
/* 1957 */               found = true;
/*      */               break;
/*      */             } 
/*      */             continue;
/*      */           } 
/* 1962 */           newLine = false;
/* 1963 */           index = 0;
/*      */           
/* 1965 */           if (nextByte == 13) {
/* 1966 */             if (sao.pos < sao.limit) {
/* 1967 */               nextByte = sao.bytes[sao.pos++];
/* 1968 */               if (nextByte == 10) {
/* 1969 */                 newLine = true;
/* 1970 */                 index = 0;
/* 1971 */                 lastrealpos = sao.pos - 2;
/*      */                 
/*      */                 continue;
/*      */               } 
/* 1975 */               lastrealpos = --sao.pos;
/*      */             }  continue;
/*      */           } 
/* 1978 */           if (nextByte == 10) {
/* 1979 */             newLine = true;
/* 1980 */             index = 0;
/* 1981 */             lastrealpos = sao.pos - 1; continue;
/*      */           } 
/* 1983 */           lastrealpos = sao.pos;
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/* 1988 */         if (nextByte == 13) {
/* 1989 */           if (sao.pos < sao.limit) {
/* 1990 */             nextByte = sao.bytes[sao.pos++];
/* 1991 */             if (nextByte == 10) {
/* 1992 */               newLine = true;
/* 1993 */               index = 0;
/* 1994 */               lastrealpos = sao.pos - 2;
/*      */               
/*      */               continue;
/*      */             } 
/* 1998 */             lastrealpos = --sao.pos;
/*      */           }  continue;
/*      */         } 
/* 2001 */         if (nextByte == 10) {
/* 2002 */           newLine = true;
/* 2003 */           index = 0;
/* 2004 */           lastrealpos = sao.pos - 1; continue;
/*      */         } 
/* 2006 */         lastrealpos = sao.pos;
/*      */       } 
/*      */ 
/*      */       
/* 2010 */       int lastPosition = sao.getReadPosition(lastrealpos);
/* 2011 */       if (found) {
/*      */ 
/*      */         
/*      */         try {
/*      */ 
/*      */           
/* 2017 */           this.currentAttribute.addContent(this.undecodedChunk.copy(readerIndex, lastPosition - readerIndex), true);
/*      */         }
/* 2019 */         catch (IOException e) {
/* 2020 */           throw new ErrorDataDecoderException(e);
/*      */         } 
/* 2022 */         this.undecodedChunk.readerIndex(lastPosition);
/*      */       } else {
/*      */         try {
/* 2025 */           this.currentAttribute.addContent(this.undecodedChunk.copy(readerIndex, lastPosition - readerIndex), false);
/*      */         }
/* 2027 */         catch (IOException e) {
/* 2028 */           throw new ErrorDataDecoderException(e);
/*      */         } 
/* 2030 */         this.undecodedChunk.readerIndex(lastPosition);
/* 2031 */         throw new NotEnoughDataDecoderException();
/*      */       } 
/* 2033 */     } catch (IndexOutOfBoundsException e) {
/* 2034 */       this.undecodedChunk.readerIndex(readerIndex);
/* 2035 */       throw new NotEnoughDataDecoderException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String cleanString(String field) {
/* 2045 */     StringBuilder sb = new StringBuilder(field.length());
/* 2046 */     for (int i = 0; i < field.length(); i++) {
/* 2047 */       char nextChar = field.charAt(i);
/* 2048 */       if (nextChar == ':') {
/* 2049 */         sb.append(32);
/* 2050 */       } else if (nextChar == ',') {
/* 2051 */         sb.append(32);
/* 2052 */       } else if (nextChar == '=') {
/* 2053 */         sb.append(32);
/* 2054 */       } else if (nextChar == ';') {
/* 2055 */         sb.append(32);
/* 2056 */       } else if (nextChar == '\t') {
/* 2057 */         sb.append(32);
/* 2058 */       } else if (nextChar != '"') {
/*      */ 
/*      */         
/* 2061 */         sb.append(nextChar);
/*      */       } 
/*      */     } 
/* 2064 */     return sb.toString().trim();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean skipOneLine() {
/* 2073 */     if (!this.undecodedChunk.isReadable()) {
/* 2074 */       return false;
/*      */     }
/* 2076 */     byte nextByte = this.undecodedChunk.readByte();
/* 2077 */     if (nextByte == 13) {
/* 2078 */       if (!this.undecodedChunk.isReadable()) {
/* 2079 */         this.undecodedChunk.readerIndex(this.undecodedChunk.readerIndex() - 1);
/* 2080 */         return false;
/*      */       } 
/* 2082 */       nextByte = this.undecodedChunk.readByte();
/* 2083 */       if (nextByte == 10) {
/* 2084 */         return true;
/*      */       }
/* 2086 */       this.undecodedChunk.readerIndex(this.undecodedChunk.readerIndex() - 2);
/* 2087 */       return false;
/*      */     } 
/* 2089 */     if (nextByte == 10) {
/* 2090 */       return true;
/*      */     }
/* 2092 */     this.undecodedChunk.readerIndex(this.undecodedChunk.readerIndex() - 1);
/* 2093 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String[] splitHeaderContentType(String sb) {
/* 2106 */     int aStart = HttpPostBodyUtil.findNonWhitespace(sb, 0);
/* 2107 */     int aEnd = sb.indexOf(';');
/* 2108 */     if (aEnd == -1) {
/* 2109 */       return new String[] { sb, "" };
/*      */     }
/* 2111 */     if (sb.charAt(aEnd - 1) == ' ') {
/* 2112 */       aEnd--;
/*      */     }
/* 2114 */     int bStart = HttpPostBodyUtil.findNonWhitespace(sb, aEnd + 1);
/* 2115 */     int bEnd = HttpPostBodyUtil.findEndOfString(sb);
/* 2116 */     return new String[] { sb.substring(aStart, aEnd), sb.substring(bStart, bEnd) };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String[] splitMultipartHeader(String sb) {
/*      */     String[] values;
/* 2126 */     ArrayList<String> headers = new ArrayList<String>(1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2132 */     int nameStart = HttpPostBodyUtil.findNonWhitespace(sb, 0); int nameEnd;
/* 2133 */     for (nameEnd = nameStart; nameEnd < sb.length(); nameEnd++) {
/* 2134 */       char ch = sb.charAt(nameEnd);
/* 2135 */       if (ch == ':' || Character.isWhitespace(ch))
/*      */         break; 
/*      */     } 
/*      */     int colonEnd;
/* 2139 */     for (colonEnd = nameEnd; colonEnd < sb.length(); colonEnd++) {
/* 2140 */       if (sb.charAt(colonEnd) == ':') {
/* 2141 */         colonEnd++;
/*      */         break;
/*      */       } 
/*      */     } 
/* 2145 */     int valueStart = HttpPostBodyUtil.findNonWhitespace(sb, colonEnd);
/* 2146 */     int valueEnd = HttpPostBodyUtil.findEndOfString(sb);
/* 2147 */     headers.add(sb.substring(nameStart, nameEnd));
/* 2148 */     String svalue = sb.substring(valueStart, valueEnd);
/*      */     
/* 2150 */     if (svalue.indexOf(';') >= 0) {
/* 2151 */       values = StringUtil.split(svalue, ';');
/*      */     } else {
/* 2153 */       values = StringUtil.split(svalue, ',');
/*      */     } 
/* 2155 */     for (String value : values) {
/* 2156 */       headers.add(value.trim());
/*      */     }
/* 2158 */     String[] array = new String[headers.size()];
/* 2159 */     for (int i = 0; i < headers.size(); i++) {
/* 2160 */       array[i] = headers.get(i);
/*      */     }
/* 2162 */     return array;
/*      */   }
/*      */ 
/*      */   
/*      */   public static class NotEnoughDataDecoderException
/*      */     extends DecoderException
/*      */   {
/*      */     private static final long serialVersionUID = -7846841864603865638L;
/*      */ 
/*      */     
/*      */     public NotEnoughDataDecoderException() {}
/*      */ 
/*      */     
/*      */     public NotEnoughDataDecoderException(String msg) {
/* 2176 */       super(msg);
/*      */     }
/*      */     
/*      */     public NotEnoughDataDecoderException(Throwable cause) {
/* 2180 */       super(cause);
/*      */     }
/*      */     
/*      */     public NotEnoughDataDecoderException(String msg, Throwable cause) {
/* 2184 */       super(msg, cause);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class EndOfDataDecoderException
/*      */     extends DecoderException
/*      */   {
/*      */     private static final long serialVersionUID = 1336267941020800769L;
/*      */   }
/*      */ 
/*      */   
/*      */   public static class ErrorDataDecoderException
/*      */     extends DecoderException
/*      */   {
/*      */     private static final long serialVersionUID = 5020247425493164465L;
/*      */ 
/*      */     
/*      */     public ErrorDataDecoderException() {}
/*      */     
/*      */     public ErrorDataDecoderException(String msg) {
/* 2205 */       super(msg);
/*      */     }
/*      */     
/*      */     public ErrorDataDecoderException(Throwable cause) {
/* 2209 */       super(cause);
/*      */     }
/*      */     
/*      */     public ErrorDataDecoderException(String msg, Throwable cause) {
/* 2213 */       super(msg, cause);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class IncompatibleDataDecoderException
/*      */     extends DecoderException
/*      */   {
/*      */     private static final long serialVersionUID = -953268047926250267L;
/*      */ 
/*      */     
/*      */     public IncompatibleDataDecoderException() {}
/*      */     
/*      */     public IncompatibleDataDecoderException(String msg) {
/* 2227 */       super(msg);
/*      */     }
/*      */     
/*      */     public IncompatibleDataDecoderException(Throwable cause) {
/* 2231 */       super(cause);
/*      */     }
/*      */     
/*      */     public IncompatibleDataDecoderException(String msg, Throwable cause) {
/* 2235 */       super(msg, cause);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\multipart\HttpPostRequestDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */