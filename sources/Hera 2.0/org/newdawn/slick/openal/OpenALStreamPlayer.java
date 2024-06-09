/*     */ package org.newdawn.slick.openal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.openal.AL10;
/*     */ import org.lwjgl.openal.OpenALException;
/*     */ import org.newdawn.slick.util.Log;
/*     */ import org.newdawn.slick.util.ResourceLoader;
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
/*     */ public class OpenALStreamPlayer
/*     */ {
/*     */   public static final int BUFFER_COUNT = 3;
/*     */   private static final int sectionSize = 81920;
/*  30 */   private byte[] buffer = new byte[81920];
/*     */   
/*     */   private IntBuffer bufferNames;
/*     */   
/*  34 */   private ByteBuffer bufferData = BufferUtils.createByteBuffer(81920);
/*     */   
/*  36 */   private IntBuffer unqueued = BufferUtils.createIntBuffer(1);
/*     */ 
/*     */   
/*     */   private int source;
/*     */ 
/*     */   
/*     */   private int remainingBufferCount;
/*     */ 
/*     */   
/*     */   private boolean loop;
/*     */ 
/*     */   
/*     */   private boolean done = true;
/*     */ 
/*     */   
/*     */   private AudioInputStream audio;
/*     */   
/*     */   private String ref;
/*     */   
/*     */   private URL url;
/*     */   
/*     */   private float pitch;
/*     */   
/*     */   private float positionOffset;
/*     */ 
/*     */   
/*     */   public OpenALStreamPlayer(int source, String ref) {
/*  63 */     this.source = source;
/*  64 */     this.ref = ref;
/*     */     
/*  66 */     this.bufferNames = BufferUtils.createIntBuffer(3);
/*  67 */     AL10.alGenBuffers(this.bufferNames);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenALStreamPlayer(int source, URL url) {
/*  77 */     this.source = source;
/*  78 */     this.url = url;
/*     */     
/*  80 */     this.bufferNames = BufferUtils.createIntBuffer(3);
/*  81 */     AL10.alGenBuffers(this.bufferNames);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initStreams() throws IOException {
/*     */     OggInputStream audio;
/*  90 */     if (this.audio != null) {
/*  91 */       this.audio.close();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  96 */     if (this.url != null) {
/*  97 */       audio = new OggInputStream(this.url.openStream());
/*     */     } else {
/*  99 */       audio = new OggInputStream(ResourceLoader.getResourceAsStream(this.ref));
/*     */     } 
/*     */     
/* 102 */     this.audio = audio;
/* 103 */     this.positionOffset = 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSource() {
/* 112 */     return (this.url == null) ? this.ref : this.url.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void removeBuffers() {
/* 119 */     IntBuffer buffer = BufferUtils.createIntBuffer(1);
/* 120 */     int queued = AL10.alGetSourcei(this.source, 4117);
/*     */     
/* 122 */     while (queued > 0) {
/*     */       
/* 124 */       AL10.alSourceUnqueueBuffers(this.source, buffer);
/* 125 */       queued--;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void play(boolean loop) throws IOException {
/* 136 */     this.loop = loop;
/* 137 */     initStreams();
/*     */     
/* 139 */     this.done = false;
/*     */     
/* 141 */     AL10.alSourceStop(this.source);
/* 142 */     removeBuffers();
/*     */     
/* 144 */     startPlayback();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setup(float pitch) {
/* 153 */     this.pitch = pitch;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean done() {
/* 163 */     return this.done;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/*     */     float sampleSize;
/* 173 */     if (this.done) {
/*     */       return;
/*     */     }
/*     */     
/* 177 */     float sampleRate = this.audio.getRate();
/*     */     
/* 179 */     if (this.audio.getChannels() > 1) {
/* 180 */       sampleSize = 4.0F;
/*     */     } else {
/* 182 */       sampleSize = 2.0F;
/*     */     } 
/*     */     
/* 185 */     int processed = AL10.alGetSourcei(this.source, 4118);
/* 186 */     while (processed > 0) {
/* 187 */       this.unqueued.clear();
/* 188 */       AL10.alSourceUnqueueBuffers(this.source, this.unqueued);
/*     */       
/* 190 */       int bufferIndex = this.unqueued.get(0);
/*     */       
/* 192 */       float bufferLength = AL10.alGetBufferi(bufferIndex, 8196) / sampleSize / sampleRate;
/* 193 */       this.positionOffset += bufferLength;
/*     */       
/* 195 */       if (stream(bufferIndex)) {
/* 196 */         AL10.alSourceQueueBuffers(this.source, this.unqueued);
/*     */       } else {
/* 198 */         this.remainingBufferCount--;
/* 199 */         if (this.remainingBufferCount == 0) {
/* 200 */           this.done = true;
/*     */         }
/*     */       } 
/* 203 */       processed--;
/*     */     } 
/*     */     
/* 206 */     int state = AL10.alGetSourcei(this.source, 4112);
/*     */     
/* 208 */     if (state != 4114) {
/* 209 */       AL10.alSourcePlay(this.source);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean stream(int bufferId) {
/*     */     try {
/* 221 */       int count = this.audio.read(this.buffer);
/*     */       
/* 223 */       if (count != -1) {
/* 224 */         this.bufferData.clear();
/* 225 */         this.bufferData.put(this.buffer, 0, count);
/* 226 */         this.bufferData.flip();
/*     */         
/* 228 */         int format = (this.audio.getChannels() > 1) ? 4355 : 4353;
/*     */         try {
/* 230 */           AL10.alBufferData(bufferId, format, this.bufferData, this.audio.getRate());
/* 231 */         } catch (OpenALException e) {
/* 232 */           Log.error("Failed to loop buffer: " + bufferId + " " + format + " " + count + " " + this.audio.getRate(), (Throwable)e);
/* 233 */           return false;
/*     */         }
/*     */       
/* 236 */       } else if (this.loop) {
/* 237 */         initStreams();
/* 238 */         stream(bufferId);
/*     */       } else {
/* 240 */         this.done = true;
/* 241 */         return false;
/*     */       } 
/*     */ 
/*     */       
/* 245 */       return true;
/* 246 */     } catch (IOException e) {
/* 247 */       Log.error(e);
/* 248 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setPosition(float position) {
/*     */     try {
/*     */       float sampleSize;
/* 260 */       if (getPosition() > position) {
/* 261 */         initStreams();
/*     */       }
/*     */       
/* 264 */       float sampleRate = this.audio.getRate();
/*     */       
/* 266 */       if (this.audio.getChannels() > 1) {
/* 267 */         sampleSize = 4.0F;
/*     */       } else {
/* 269 */         sampleSize = 2.0F;
/*     */       } 
/*     */       
/* 272 */       while (this.positionOffset < position) {
/* 273 */         int count = this.audio.read(this.buffer);
/* 274 */         if (count != -1) {
/* 275 */           float bufferLength = count / sampleSize / sampleRate;
/* 276 */           this.positionOffset += bufferLength; continue;
/*     */         } 
/* 278 */         if (this.loop) {
/* 279 */           initStreams();
/*     */         } else {
/* 281 */           this.done = true;
/*     */         } 
/* 283 */         return false;
/*     */       } 
/*     */ 
/*     */       
/* 287 */       startPlayback();
/*     */       
/* 289 */       return true;
/* 290 */     } catch (IOException e) {
/* 291 */       Log.error(e);
/* 292 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void startPlayback() {
/* 300 */     AL10.alSourcei(this.source, 4103, 0);
/* 301 */     AL10.alSourcef(this.source, 4099, this.pitch);
/*     */     
/* 303 */     this.remainingBufferCount = 3;
/*     */     
/* 305 */     for (int i = 0; i < 3; i++) {
/* 306 */       stream(this.bufferNames.get(i));
/*     */     }
/*     */     
/* 309 */     AL10.alSourceQueueBuffers(this.source, this.bufferNames);
/* 310 */     AL10.alSourcePlay(this.source);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPosition() {
/* 319 */     return this.positionOffset + AL10.alGetSourcef(this.source, 4132);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\openal\OpenALStreamPlayer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */