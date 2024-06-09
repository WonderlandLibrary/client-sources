/*     */ package org.newdawn.slick.openal;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.ShortBuffer;
/*     */ import javax.sound.sampled.AudioFormat;
/*     */ import javax.sound.sampled.AudioInputStream;
/*     */ import javax.sound.sampled.AudioSystem;
/*     */ import org.lwjgl.LWJGLUtil;
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
/*     */ public class WaveData
/*     */ {
/*     */   public final ByteBuffer data;
/*     */   public final int format;
/*     */   public final int samplerate;
/*     */   
/*     */   private WaveData(ByteBuffer data, int format, int samplerate) {
/*  75 */     this.data = data;
/*  76 */     this.format = format;
/*  77 */     this.samplerate = samplerate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose() {
/*  84 */     this.data.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WaveData create(URL path) {
/*     */     try {
/*  95 */       return create(AudioSystem.getAudioInputStream(new BufferedInputStream(path.openStream())));
/*     */     
/*     */     }
/*  98 */     catch (Exception e) {
/*  99 */       LWJGLUtil.log("Unable to create from: " + path);
/* 100 */       e.printStackTrace();
/* 101 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WaveData create(String path) {
/* 112 */     return create(WaveData.class.getClassLoader().getResource(path));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WaveData create(InputStream is) {
/*     */     try {
/* 123 */       return create(AudioSystem.getAudioInputStream(is));
/*     */     }
/* 125 */     catch (Exception e) {
/* 126 */       LWJGLUtil.log("Unable to create from inputstream");
/* 127 */       e.printStackTrace();
/* 128 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WaveData create(byte[] buffer) {
/*     */     try {
/* 140 */       return create(AudioSystem.getAudioInputStream(new BufferedInputStream(new ByteArrayInputStream(buffer))));
/*     */     
/*     */     }
/* 143 */     catch (Exception e) {
/* 144 */       e.printStackTrace();
/* 145 */       return null;
/*     */     } 
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
/*     */   public static WaveData create(ByteBuffer buffer) {
/*     */     try {
/* 159 */       byte[] bytes = null;
/*     */       
/* 161 */       if (buffer.hasArray()) {
/* 162 */         bytes = buffer.array();
/*     */       } else {
/* 164 */         bytes = new byte[buffer.capacity()];
/* 165 */         buffer.get(bytes);
/*     */       } 
/* 167 */       return create(bytes);
/* 168 */     } catch (Exception e) {
/* 169 */       e.printStackTrace();
/* 170 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WaveData create(AudioInputStream ais) {
/* 182 */     AudioFormat audioformat = ais.getFormat();
/*     */ 
/*     */     
/* 185 */     int channels = 0;
/* 186 */     if (audioformat.getChannels() == 1) {
/* 187 */       if (audioformat.getSampleSizeInBits() == 8) {
/* 188 */         channels = 4352;
/* 189 */       } else if (audioformat.getSampleSizeInBits() == 16) {
/* 190 */         channels = 4353;
/*     */       } else {
/* 192 */         throw new RuntimeException("Illegal sample size");
/*     */       } 
/* 194 */     } else if (audioformat.getChannels() == 2) {
/* 195 */       if (audioformat.getSampleSizeInBits() == 8) {
/* 196 */         channels = 4354;
/* 197 */       } else if (audioformat.getSampleSizeInBits() == 16) {
/* 198 */         channels = 4355;
/*     */       } else {
/* 200 */         throw new RuntimeException("Illegal sample size");
/*     */       } 
/*     */     } else {
/* 203 */       throw new RuntimeException("Only mono or stereo is supported");
/*     */     } 
/*     */ 
/*     */     
/* 207 */     byte[] buf = new byte[audioformat.getChannels() * (int)ais.getFrameLength() * audioformat.getSampleSizeInBits() / 8];
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 212 */     int read = 0, total = 0;
/*     */     
/*     */     try {
/* 215 */       while ((read = ais.read(buf, total, buf.length - total)) != -1 && total < buf.length) {
/* 216 */         total += read;
/*     */       }
/* 218 */     } catch (IOException ioe) {
/* 219 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 223 */     ByteBuffer buffer = convertAudioBytes(buf, (audioformat.getSampleSizeInBits() == 16));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 229 */     WaveData wavedata = new WaveData(buffer, channels, (int)audioformat.getSampleRate());
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 234 */       ais.close();
/* 235 */     } catch (IOException ioe) {}
/*     */ 
/*     */     
/* 238 */     return wavedata;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ByteBuffer convertAudioBytes(byte[] audio_bytes, boolean two_bytes_data) {
/* 249 */     ByteBuffer dest = ByteBuffer.allocateDirect(audio_bytes.length);
/* 250 */     dest.order(ByteOrder.nativeOrder());
/* 251 */     ByteBuffer src = ByteBuffer.wrap(audio_bytes);
/* 252 */     src.order(ByteOrder.LITTLE_ENDIAN);
/* 253 */     if (two_bytes_data) {
/* 254 */       ShortBuffer dest_short = dest.asShortBuffer();
/* 255 */       ShortBuffer src_short = src.asShortBuffer();
/* 256 */       while (src_short.hasRemaining())
/* 257 */         dest_short.put(src_short.get()); 
/*     */     } else {
/* 259 */       while (src.hasRemaining())
/* 260 */         dest.put(src.get()); 
/*     */     } 
/* 262 */     dest.rewind();
/* 263 */     return dest;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\openal\WaveData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */