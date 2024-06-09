/*     */ package org.newdawn.slick.openal;
/*     */ 
/*     */ import com.jcraft.jogg.Packet;
/*     */ import com.jcraft.jogg.Page;
/*     */ import com.jcraft.jogg.StreamState;
/*     */ import com.jcraft.jogg.SyncState;
/*     */ import com.jcraft.jorbis.Block;
/*     */ import com.jcraft.jorbis.Comment;
/*     */ import com.jcraft.jorbis.DspState;
/*     */ import com.jcraft.jorbis.Info;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.newdawn.slick.util.Log;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OggInputStream
/*     */   extends InputStream
/*     */   implements AudioInputStream
/*     */ {
/*  29 */   private int convsize = 16384;
/*     */   
/*  31 */   private byte[] convbuffer = new byte[this.convsize];
/*     */   
/*     */   private InputStream input;
/*     */   
/*  35 */   private Info oggInfo = new Info();
/*     */ 
/*     */   
/*     */   private boolean endOfStream;
/*     */   
/*  40 */   private SyncState syncState = new SyncState();
/*     */   
/*  42 */   private StreamState streamState = new StreamState();
/*     */   
/*  44 */   private Page page = new Page();
/*     */   
/*  46 */   private Packet packet = new Packet();
/*     */ 
/*     */   
/*  49 */   private Comment comment = new Comment();
/*     */   
/*  51 */   private DspState dspState = new DspState();
/*     */   
/*  53 */   private Block vorbisBlock = new Block(this.dspState);
/*     */ 
/*     */   
/*     */   byte[] buffer;
/*     */   
/*  58 */   int bytes = 0;
/*     */   
/*  60 */   boolean bigEndian = ByteOrder.nativeOrder().equals(ByteOrder.BIG_ENDIAN);
/*     */ 
/*     */   
/*     */   boolean endOfBitStream = true;
/*     */   
/*     */   boolean inited = false;
/*     */   
/*     */   private int readIndex;
/*     */   
/*  69 */   private ByteBuffer pcmBuffer = BufferUtils.createByteBuffer(2048000);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int total;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OggInputStream(InputStream input) throws IOException {
/*  80 */     this.input = input;
/*  81 */     this.total = input.available();
/*     */     
/*  83 */     init();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLength() {
/*  92 */     return this.total;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getChannels() {
/*  99 */     return this.oggInfo.channels;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRate() {
/* 106 */     return this.oggInfo.rate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void init() throws IOException {
/* 115 */     initVorbis();
/* 116 */     readPCM();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int available() {
/* 123 */     return this.endOfStream ? 0 : 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initVorbis() {
/* 130 */     this.syncState.init();
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
/*     */ 
/*     */   
/*     */   private boolean getPageAndPacket() {
/* 145 */     int index = this.syncState.buffer(4096);
/*     */     
/* 147 */     this.buffer = this.syncState.data;
/* 148 */     if (this.buffer == null) {
/* 149 */       this.endOfStream = true;
/* 150 */       return false;
/*     */     } 
/*     */     
/*     */     try {
/* 154 */       this.bytes = this.input.read(this.buffer, index, 4096);
/* 155 */     } catch (Exception e) {
/* 156 */       Log.error("Failure reading in vorbis");
/* 157 */       Log.error(e);
/* 158 */       this.endOfStream = true;
/* 159 */       return false;
/*     */     } 
/* 161 */     this.syncState.wrote(this.bytes);
/*     */ 
/*     */     
/* 164 */     if (this.syncState.pageout(this.page) != 1) {
/*     */       
/* 166 */       if (this.bytes < 4096) {
/* 167 */         return false;
/*     */       }
/*     */       
/* 170 */       Log.error("Input does not appear to be an Ogg bitstream.");
/* 171 */       this.endOfStream = true;
/* 172 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 177 */     this.streamState.init(this.page.serialno());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 187 */     this.oggInfo.init();
/* 188 */     this.comment.init();
/* 189 */     if (this.streamState.pagein(this.page) < 0) {
/*     */       
/* 191 */       Log.error("Error reading first page of Ogg bitstream data.");
/* 192 */       this.endOfStream = true;
/* 193 */       return false;
/*     */     } 
/*     */     
/* 196 */     if (this.streamState.packetout(this.packet) != 1) {
/*     */       
/* 198 */       Log.error("Error reading initial header packet.");
/* 199 */       this.endOfStream = true;
/* 200 */       return false;
/*     */     } 
/*     */     
/* 203 */     if (this.oggInfo.synthesis_headerin(this.comment, this.packet) < 0) {
/*     */       
/* 205 */       Log.error("This Ogg bitstream does not contain Vorbis audio data.");
/* 206 */       this.endOfStream = true;
/* 207 */       return false;
/*     */     } 
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
/* 220 */     int i = 0;
/* 221 */     while (i < 2) {
/* 222 */       while (i < 2) {
/*     */         
/* 224 */         int result = this.syncState.pageout(this.page);
/* 225 */         if (result == 0) {
/*     */           break;
/*     */         }
/*     */ 
/*     */         
/* 230 */         if (result == 1) {
/* 231 */           this.streamState.pagein(this.page);
/*     */ 
/*     */           
/* 234 */           while (i < 2) {
/* 235 */             result = this.streamState.packetout(this.packet);
/* 236 */             if (result == 0)
/*     */               break; 
/* 238 */             if (result == -1) {
/*     */ 
/*     */               
/* 241 */               Log.error("Corrupt secondary header.  Exiting.");
/* 242 */               this.endOfStream = true;
/* 243 */               return false;
/*     */             } 
/*     */             
/* 246 */             this.oggInfo.synthesis_headerin(this.comment, this.packet);
/* 247 */             i++;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 252 */       index = this.syncState.buffer(4096);
/* 253 */       this.buffer = this.syncState.data;
/*     */       try {
/* 255 */         this.bytes = this.input.read(this.buffer, index, 4096);
/* 256 */       } catch (Exception e) {
/* 257 */         Log.error("Failed to read Vorbis: ");
/* 258 */         Log.error(e);
/* 259 */         this.endOfStream = true;
/* 260 */         return false;
/*     */       } 
/* 262 */       if (this.bytes == 0 && i < 2) {
/* 263 */         Log.error("End of file before finding all Vorbis headers!");
/* 264 */         this.endOfStream = true;
/* 265 */         return false;
/*     */       } 
/* 267 */       this.syncState.wrote(this.bytes);
/*     */     } 
/*     */     
/* 270 */     this.convsize = 4096 / this.oggInfo.channels;
/*     */ 
/*     */ 
/*     */     
/* 274 */     this.dspState.synthesis_init(this.oggInfo);
/* 275 */     this.vorbisBlock.init(this.dspState);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 281 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readPCM() throws IOException {
/* 290 */     boolean wrote = false;
/*     */     
/*     */     while (true) {
/* 293 */       if (this.endOfBitStream) {
/* 294 */         if (!getPageAndPacket()) {
/*     */           break;
/*     */         }
/* 297 */         this.endOfBitStream = false;
/*     */       } 
/*     */       
/* 300 */       if (!this.inited) {
/* 301 */         this.inited = true;
/*     */         
/*     */         return;
/*     */       } 
/* 305 */       float[][][] _pcm = new float[1][][];
/* 306 */       int[] _index = new int[this.oggInfo.channels];
/*     */       
/* 308 */       while (!this.endOfBitStream) {
/* 309 */         while (!this.endOfBitStream) {
/* 310 */           int result = this.syncState.pageout(this.page);
/*     */           
/* 312 */           if (result == 0) {
/*     */             break;
/*     */           }
/*     */           
/* 316 */           if (result == -1) {
/* 317 */             Log.error("Corrupt or missing data in bitstream; continuing..."); continue;
/*     */           } 
/* 319 */           this.streamState.pagein(this.page);
/*     */           
/*     */           while (true) {
/* 322 */             result = this.streamState.packetout(this.packet);
/*     */             
/* 324 */             if (result == 0)
/*     */               break; 
/* 326 */             if (result == -1) {
/*     */               continue;
/*     */             }
/*     */ 
/*     */             
/* 331 */             if (this.vorbisBlock.synthesis(this.packet) == 0) {
/* 332 */               this.dspState.synthesis_blockin(this.vorbisBlock);
/*     */             }
/*     */ 
/*     */ 
/*     */             
/*     */             int samples;
/*     */ 
/*     */ 
/*     */             
/* 341 */             while ((samples = this.dspState.synthesis_pcmout(_pcm, _index)) > 0) {
/* 342 */               float[][] pcm = _pcm[0];
/*     */               
/* 344 */               int bout = (samples < this.convsize) ? samples : this.convsize;
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 349 */               for (int i = 0; i < this.oggInfo.channels; i++) {
/* 350 */                 int ptr = i * 2;
/*     */                 
/* 352 */                 int mono = _index[i];
/* 353 */                 for (int j = 0; j < bout; j++) {
/* 354 */                   int val = (int)(pcm[i][mono + j] * 32767.0D);
/*     */                   
/* 356 */                   if (val > 32767) {
/* 357 */                     val = 32767;
/*     */                   }
/* 359 */                   if (val < -32768) {
/* 360 */                     val = -32768;
/*     */                   }
/* 362 */                   if (val < 0) {
/* 363 */                     val |= 0x8000;
/*     */                   }
/* 365 */                   if (this.bigEndian) {
/* 366 */                     this.convbuffer[ptr] = (byte)(val >>> 8);
/* 367 */                     this.convbuffer[ptr + 1] = (byte)val;
/*     */                   } else {
/* 369 */                     this.convbuffer[ptr] = (byte)val;
/* 370 */                     this.convbuffer[ptr + 1] = (byte)(val >>> 8);
/*     */                   } 
/* 372 */                   ptr += 2 * this.oggInfo.channels;
/*     */                 } 
/*     */               } 
/*     */               
/* 376 */               int bytesToWrite = 2 * this.oggInfo.channels * bout;
/* 377 */               if (bytesToWrite >= this.pcmBuffer.remaining()) {
/* 378 */                 Log.warn("Read block from OGG that was too big to be buffered: " + bytesToWrite);
/*     */               } else {
/* 380 */                 this.pcmBuffer.put(this.convbuffer, 0, bytesToWrite);
/*     */               } 
/*     */               
/* 383 */               wrote = true;
/* 384 */               this.dspState.synthesis_read(bout);
/*     */             } 
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 390 */           if (this.page.eos() != 0) {
/* 391 */             this.endOfBitStream = true;
/*     */           }
/*     */           
/* 394 */           if (!this.endOfBitStream && wrote) {
/*     */             return;
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 400 */         if (!this.endOfBitStream) {
/* 401 */           this.bytes = 0;
/* 402 */           int index = this.syncState.buffer(4096);
/* 403 */           if (index >= 0) {
/* 404 */             this.buffer = this.syncState.data;
/*     */             try {
/* 406 */               this.bytes = this.input.read(this.buffer, index, 4096);
/* 407 */             } catch (Exception e) {
/* 408 */               Log.error("Failure during vorbis decoding");
/* 409 */               Log.error(e);
/* 410 */               this.endOfStream = true;
/*     */               return;
/*     */             } 
/*     */           } else {
/* 414 */             this.bytes = 0;
/*     */           } 
/* 416 */           this.syncState.wrote(this.bytes);
/* 417 */           if (this.bytes == 0) {
/* 418 */             this.endOfBitStream = true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 425 */       this.streamState.clear();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 430 */       this.vorbisBlock.clear();
/* 431 */       this.dspState.clear();
/* 432 */       this.oggInfo.clear();
/*     */     } 
/*     */ 
/*     */     
/* 436 */     this.syncState.clear();
/* 437 */     this.endOfStream = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int read() throws IOException {
/* 444 */     if (this.readIndex >= this.pcmBuffer.position()) {
/* 445 */       this.pcmBuffer.clear();
/* 446 */       readPCM();
/* 447 */       this.readIndex = 0;
/*     */     } 
/* 449 */     if (this.readIndex >= this.pcmBuffer.position()) {
/* 450 */       return -1;
/*     */     }
/*     */     
/* 453 */     int value = this.pcmBuffer.get(this.readIndex);
/* 454 */     if (value < 0) {
/* 455 */       value = 256 + value;
/*     */     }
/* 457 */     this.readIndex++;
/*     */     
/* 459 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean atEnd() {
/* 466 */     return (this.endOfStream && this.readIndex >= this.pcmBuffer.position());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int read(byte[] b, int off, int len) throws IOException {
/* 473 */     for (int i = 0; i < len; i++) {
/*     */       try {
/* 475 */         int value = read();
/* 476 */         if (value >= 0) {
/* 477 */           b[i] = (byte)value;
/*     */         } else {
/* 479 */           if (i == 0) {
/* 480 */             return -1;
/*     */           }
/* 482 */           return i;
/*     */         }
/*     */       
/* 485 */       } catch (IOException e) {
/* 486 */         Log.error(e);
/* 487 */         return i;
/*     */       } 
/*     */     } 
/*     */     
/* 491 */     return len;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int read(byte[] b) throws IOException {
/* 498 */     return read(b, 0, b.length);
/*     */   }
/*     */   
/*     */   public void close() throws IOException {}
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\openal\OggInputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */