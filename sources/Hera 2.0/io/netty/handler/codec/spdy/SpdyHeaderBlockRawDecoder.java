/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
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
/*     */ public class SpdyHeaderBlockRawDecoder
/*     */   extends SpdyHeaderBlockDecoder
/*     */ {
/*     */   private static final int LENGTH_FIELD_SIZE = 4;
/*     */   private final int maxHeaderSize;
/*     */   private State state;
/*     */   private ByteBuf cumulation;
/*     */   private int headerSize;
/*     */   private int numHeaders;
/*     */   private int length;
/*     */   private String name;
/*     */   
/*     */   private enum State
/*     */   {
/*  38 */     READ_NUM_HEADERS,
/*  39 */     READ_NAME_LENGTH,
/*  40 */     READ_NAME,
/*  41 */     SKIP_NAME,
/*  42 */     READ_VALUE_LENGTH,
/*  43 */     READ_VALUE,
/*  44 */     SKIP_VALUE,
/*  45 */     END_HEADER_BLOCK,
/*  46 */     ERROR;
/*     */   }
/*     */   
/*     */   public SpdyHeaderBlockRawDecoder(SpdyVersion spdyVersion, int maxHeaderSize) {
/*  50 */     if (spdyVersion == null) {
/*  51 */       throw new NullPointerException("spdyVersion");
/*     */     }
/*  53 */     this.maxHeaderSize = maxHeaderSize;
/*  54 */     this.state = State.READ_NUM_HEADERS;
/*     */   }
/*     */   
/*     */   private static int readLengthField(ByteBuf buffer) {
/*  58 */     int length = SpdyCodecUtil.getSignedInt(buffer, buffer.readerIndex());
/*  59 */     buffer.skipBytes(4);
/*  60 */     return length;
/*     */   }
/*     */ 
/*     */   
/*     */   void decode(ByteBuf headerBlock, SpdyHeadersFrame frame) throws Exception {
/*  65 */     if (headerBlock == null) {
/*  66 */       throw new NullPointerException("headerBlock");
/*     */     }
/*  68 */     if (frame == null) {
/*  69 */       throw new NullPointerException("frame");
/*     */     }
/*     */     
/*  72 */     if (this.cumulation == null) {
/*  73 */       decodeHeaderBlock(headerBlock, frame);
/*  74 */       if (headerBlock.isReadable()) {
/*  75 */         this.cumulation = headerBlock.alloc().buffer(headerBlock.readableBytes());
/*  76 */         this.cumulation.writeBytes(headerBlock);
/*     */       } 
/*     */     } else {
/*  79 */       this.cumulation.writeBytes(headerBlock);
/*  80 */       decodeHeaderBlock(this.cumulation, frame);
/*  81 */       if (this.cumulation.isReadable()) {
/*  82 */         this.cumulation.discardReadBytes();
/*     */       } else {
/*  84 */         releaseBuffer();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decodeHeaderBlock(ByteBuf headerBlock, SpdyHeadersFrame frame) throws Exception {
/*  91 */     while (headerBlock.isReadable()) {
/*  92 */       int skipLength; byte[] nameBytes; byte[] valueBytes; int index; int offset; switch (this.state) {
/*     */         case READ_NUM_HEADERS:
/*  94 */           if (headerBlock.readableBytes() < 4) {
/*     */             return;
/*     */           }
/*     */           
/*  98 */           this.numHeaders = readLengthField(headerBlock);
/*     */           
/* 100 */           if (this.numHeaders < 0) {
/* 101 */             this.state = State.ERROR;
/* 102 */             frame.setInvalid(); continue;
/* 103 */           }  if (this.numHeaders == 0) {
/* 104 */             this.state = State.END_HEADER_BLOCK; continue;
/*     */           } 
/* 106 */           this.state = State.READ_NAME_LENGTH;
/*     */           continue;
/*     */ 
/*     */         
/*     */         case READ_NAME_LENGTH:
/* 111 */           if (headerBlock.readableBytes() < 4) {
/*     */             return;
/*     */           }
/*     */           
/* 115 */           this.length = readLengthField(headerBlock);
/*     */ 
/*     */           
/* 118 */           if (this.length <= 0) {
/* 119 */             this.state = State.ERROR;
/* 120 */             frame.setInvalid(); continue;
/* 121 */           }  if (this.length > this.maxHeaderSize || this.headerSize > this.maxHeaderSize - this.length) {
/* 122 */             this.headerSize = this.maxHeaderSize + 1;
/* 123 */             this.state = State.SKIP_NAME;
/* 124 */             frame.setTruncated(); continue;
/*     */           } 
/* 126 */           this.headerSize += this.length;
/* 127 */           this.state = State.READ_NAME;
/*     */           continue;
/*     */ 
/*     */         
/*     */         case READ_NAME:
/* 132 */           if (headerBlock.readableBytes() < this.length) {
/*     */             return;
/*     */           }
/*     */           
/* 136 */           nameBytes = new byte[this.length];
/* 137 */           headerBlock.readBytes(nameBytes);
/* 138 */           this.name = new String(nameBytes, "UTF-8");
/*     */ 
/*     */           
/* 141 */           if (frame.headers().contains(this.name)) {
/* 142 */             this.state = State.ERROR;
/* 143 */             frame.setInvalid(); continue;
/*     */           } 
/* 145 */           this.state = State.READ_VALUE_LENGTH;
/*     */           continue;
/*     */ 
/*     */         
/*     */         case SKIP_NAME:
/* 150 */           skipLength = Math.min(headerBlock.readableBytes(), this.length);
/* 151 */           headerBlock.skipBytes(skipLength);
/* 152 */           this.length -= skipLength;
/*     */           
/* 154 */           if (this.length == 0) {
/* 155 */             this.state = State.READ_VALUE_LENGTH;
/*     */           }
/*     */           continue;
/*     */         
/*     */         case READ_VALUE_LENGTH:
/* 160 */           if (headerBlock.readableBytes() < 4) {
/*     */             return;
/*     */           }
/*     */           
/* 164 */           this.length = readLengthField(headerBlock);
/*     */ 
/*     */           
/* 167 */           if (this.length < 0) {
/* 168 */             this.state = State.ERROR;
/* 169 */             frame.setInvalid(); continue;
/* 170 */           }  if (this.length == 0) {
/* 171 */             if (!frame.isTruncated())
/*     */             {
/* 173 */               frame.headers().add(this.name, "");
/*     */             }
/*     */             
/* 176 */             this.name = null;
/* 177 */             if (--this.numHeaders == 0) {
/* 178 */               this.state = State.END_HEADER_BLOCK; continue;
/*     */             } 
/* 180 */             this.state = State.READ_NAME_LENGTH;
/*     */             continue;
/*     */           } 
/* 183 */           if (this.length > this.maxHeaderSize || this.headerSize > this.maxHeaderSize - this.length) {
/* 184 */             this.headerSize = this.maxHeaderSize + 1;
/* 185 */             this.name = null;
/* 186 */             this.state = State.SKIP_VALUE;
/* 187 */             frame.setTruncated(); continue;
/*     */           } 
/* 189 */           this.headerSize += this.length;
/* 190 */           this.state = State.READ_VALUE;
/*     */           continue;
/*     */ 
/*     */         
/*     */         case READ_VALUE:
/* 195 */           if (headerBlock.readableBytes() < this.length) {
/*     */             return;
/*     */           }
/*     */           
/* 199 */           valueBytes = new byte[this.length];
/* 200 */           headerBlock.readBytes(valueBytes);
/*     */ 
/*     */           
/* 203 */           index = 0;
/* 204 */           offset = 0;
/*     */ 
/*     */           
/* 207 */           if (valueBytes[0] == 0) {
/* 208 */             this.state = State.ERROR;
/* 209 */             frame.setInvalid();
/*     */             
/*     */             continue;
/*     */           } 
/* 213 */           while (index < this.length) {
/* 214 */             while (index < valueBytes.length && valueBytes[index] != 0) {
/* 215 */               index++;
/*     */             }
/* 217 */             if (index < valueBytes.length)
/*     */             {
/* 219 */               if (index + 1 == valueBytes.length || valueBytes[index + 1] == 0) {
/*     */ 
/*     */ 
/*     */                 
/* 223 */                 this.state = State.ERROR;
/* 224 */                 frame.setInvalid();
/*     */                 break;
/*     */               } 
/*     */             }
/* 228 */             String value = new String(valueBytes, offset, index - offset, "UTF-8");
/*     */             
/*     */             try {
/* 231 */               frame.headers().add(this.name, value);
/* 232 */             } catch (IllegalArgumentException e) {
/*     */               
/* 234 */               this.state = State.ERROR;
/* 235 */               frame.setInvalid();
/*     */               
/*     */               break;
/*     */             } 
/* 239 */             offset = ++index;
/*     */           } 
/*     */           
/* 242 */           this.name = null;
/*     */ 
/*     */           
/* 245 */           if (this.state == State.ERROR) {
/*     */             continue;
/*     */           }
/*     */           
/* 249 */           if (--this.numHeaders == 0) {
/* 250 */             this.state = State.END_HEADER_BLOCK; continue;
/*     */           } 
/* 252 */           this.state = State.READ_NAME_LENGTH;
/*     */           continue;
/*     */ 
/*     */         
/*     */         case SKIP_VALUE:
/* 257 */           skipLength = Math.min(headerBlock.readableBytes(), this.length);
/* 258 */           headerBlock.skipBytes(skipLength);
/* 259 */           this.length -= skipLength;
/*     */           
/* 261 */           if (this.length == 0) {
/* 262 */             if (--this.numHeaders == 0) {
/* 263 */               this.state = State.END_HEADER_BLOCK; continue;
/*     */             } 
/* 265 */             this.state = State.READ_NAME_LENGTH;
/*     */           } 
/*     */           continue;
/*     */ 
/*     */         
/*     */         case END_HEADER_BLOCK:
/* 271 */           this.state = State.ERROR;
/* 272 */           frame.setInvalid();
/*     */           continue;
/*     */         
/*     */         case ERROR:
/* 276 */           headerBlock.skipBytes(headerBlock.readableBytes());
/*     */           return;
/*     */       } 
/*     */       
/* 280 */       throw new Error("Shouldn't reach here.");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void endHeaderBlock(SpdyHeadersFrame frame) throws Exception {
/* 287 */     if (this.state != State.END_HEADER_BLOCK) {
/* 288 */       frame.setInvalid();
/*     */     }
/*     */     
/* 291 */     releaseBuffer();
/*     */ 
/*     */     
/* 294 */     this.headerSize = 0;
/* 295 */     this.name = null;
/* 296 */     this.state = State.READ_NUM_HEADERS;
/*     */   }
/*     */ 
/*     */   
/*     */   void end() {
/* 301 */     releaseBuffer();
/*     */   }
/*     */   
/*     */   private void releaseBuffer() {
/* 305 */     if (this.cumulation != null) {
/* 306 */       this.cumulation.release();
/* 307 */       this.cumulation = null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\spdy\SpdyHeaderBlockRawDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */