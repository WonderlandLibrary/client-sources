/*     */ package org.newdawn.slick.openal;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.HashMap;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.Sys;
/*     */ import org.lwjgl.openal.AL;
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
/*     */ public class SoundStore
/*     */ {
/*  30 */   private static SoundStore store = new SoundStore();
/*     */ 
/*     */   
/*     */   private boolean sounds;
/*     */   
/*     */   private boolean music;
/*     */   
/*     */   private boolean soundWorks;
/*     */   
/*     */   private int sourceCount;
/*     */   
/*  41 */   private HashMap loaded = new HashMap<Object, Object>();
/*     */   
/*  43 */   private int currentMusic = -1;
/*     */ 
/*     */   
/*     */   private IntBuffer sources;
/*     */   
/*     */   private int nextSource;
/*     */   
/*     */   private boolean inited = false;
/*     */   
/*     */   private MODSound mod;
/*     */   
/*     */   private OpenALStreamPlayer stream;
/*     */   
/*  56 */   private float musicVolume = 1.0F;
/*     */   
/*  58 */   private float soundVolume = 1.0F;
/*     */   
/*  60 */   private float lastCurrentMusicVolume = 1.0F;
/*     */ 
/*     */   
/*     */   private boolean paused;
/*     */ 
/*     */   
/*     */   private boolean deferred;
/*     */   
/*  68 */   private FloatBuffer sourceVel = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0F, 0.0F, 0.0F });
/*     */   
/*  70 */   private FloatBuffer sourcePos = BufferUtils.createFloatBuffer(3);
/*     */ 
/*     */   
/*  73 */   private int maxSources = 64;
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
/*     */   public void clear() {
/*  85 */     store = new SoundStore();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disable() {
/*  92 */     this.inited = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDeferredLoading(boolean deferred) {
/* 102 */     this.deferred = deferred;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDeferredLoading() {
/* 111 */     return this.deferred;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMusicOn(boolean music) {
/* 120 */     if (this.soundWorks) {
/* 121 */       this.music = music;
/* 122 */       if (music) {
/* 123 */         restartLoop();
/* 124 */         setMusicVolume(this.musicVolume);
/*     */       } else {
/* 126 */         pauseLoop();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMusicOn() {
/* 137 */     return this.music;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMusicVolume(float volume) {
/* 146 */     if (volume < 0.0F) {
/* 147 */       volume = 0.0F;
/*     */     }
/* 149 */     if (volume > 1.0F) {
/* 150 */       volume = 1.0F;
/*     */     }
/*     */     
/* 153 */     this.musicVolume = volume;
/* 154 */     if (this.soundWorks) {
/* 155 */       AL10.alSourcef(this.sources.get(0), 4106, this.lastCurrentMusicVolume * this.musicVolume);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getCurrentMusicVolume() {
/* 165 */     return this.lastCurrentMusicVolume;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCurrentMusicVolume(float volume) {
/* 174 */     if (volume < 0.0F) {
/* 175 */       volume = 0.0F;
/*     */     }
/* 177 */     if (volume > 1.0F) {
/* 178 */       volume = 1.0F;
/*     */     }
/*     */     
/* 181 */     if (this.soundWorks) {
/* 182 */       this.lastCurrentMusicVolume = volume;
/* 183 */       AL10.alSourcef(this.sources.get(0), 4106, this.lastCurrentMusicVolume * this.musicVolume);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSoundVolume(float volume) {
/* 193 */     if (volume < 0.0F) {
/* 194 */       volume = 0.0F;
/*     */     }
/* 196 */     this.soundVolume = volume;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean soundWorks() {
/* 205 */     return this.soundWorks;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean musicOn() {
/* 214 */     return this.music;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getSoundVolume() {
/* 223 */     return this.soundVolume;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMusicVolume() {
/* 232 */     return this.musicVolume;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSource(int index) {
/* 242 */     if (!this.soundWorks) {
/* 243 */       return -1;
/*     */     }
/* 245 */     if (index < 0) {
/* 246 */       return -1;
/*     */     }
/* 248 */     return this.sources.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSoundsOn(boolean sounds) {
/* 257 */     if (this.soundWorks) {
/* 258 */       this.sounds = sounds;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean soundsOn() {
/* 268 */     return this.sounds;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxSources(int max) {
/* 278 */     this.maxSources = max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/* 286 */     if (this.inited) {
/*     */       return;
/*     */     }
/* 289 */     Log.info("Initialising sounds..");
/* 290 */     this.inited = true;
/*     */     
/* 292 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */           public Object run() {
/*     */             try {
/* 295 */               AL.create();
/* 296 */               SoundStore.this.soundWorks = true;
/* 297 */               SoundStore.this.sounds = true;
/* 298 */               SoundStore.this.music = true;
/* 299 */               Log.info("- Sound works");
/* 300 */             } catch (Exception e) {
/* 301 */               Log.error("Sound initialisation failure.");
/* 302 */               Log.error(e);
/* 303 */               SoundStore.this.soundWorks = false;
/* 304 */               SoundStore.this.sounds = false;
/* 305 */               SoundStore.this.music = false;
/*     */             } 
/*     */             
/* 308 */             return null;
/*     */           }
/*     */         });
/* 311 */     if (this.soundWorks) {
/* 312 */       this.sourceCount = 0;
/* 313 */       this.sources = BufferUtils.createIntBuffer(this.maxSources);
/* 314 */       while (AL10.alGetError() == 0) {
/* 315 */         IntBuffer temp = BufferUtils.createIntBuffer(1);
/*     */         
/*     */         try {
/* 318 */           AL10.alGenSources(temp);
/*     */           
/* 320 */           if (AL10.alGetError() == 0) {
/* 321 */             this.sourceCount++;
/* 322 */             this.sources.put(temp.get(0));
/* 323 */             if (this.sourceCount > this.maxSources - 1) {
/*     */               break;
/*     */             }
/*     */           } 
/* 327 */         } catch (OpenALException e) {
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/* 332 */       Log.info("- " + this.sourceCount + " OpenAL source available");
/*     */       
/* 334 */       if (AL10.alGetError() != 0) {
/* 335 */         this.sounds = false;
/* 336 */         this.music = false;
/* 337 */         this.soundWorks = false;
/* 338 */         Log.error("- AL init failed");
/*     */       } else {
/* 340 */         FloatBuffer listenerOri = BufferUtils.createFloatBuffer(6).put(new float[] { 0.0F, 0.0F, -1.0F, 0.0F, 1.0F, 0.0F });
/*     */         
/* 342 */         FloatBuffer listenerVel = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0F, 0.0F, 0.0F });
/*     */         
/* 344 */         FloatBuffer listenerPos = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0F, 0.0F, 0.0F });
/*     */         
/* 346 */         listenerPos.flip();
/* 347 */         listenerVel.flip();
/* 348 */         listenerOri.flip();
/* 349 */         AL10.alListener(4100, listenerPos);
/* 350 */         AL10.alListener(4102, listenerVel);
/* 351 */         AL10.alListener(4111, listenerOri);
/*     */         
/* 353 */         Log.info("- Sounds source generated");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void stopSource(int index) {
/* 364 */     AL10.alSourceStop(this.sources.get(index));
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
/*     */   int playAsSound(int buffer, float pitch, float gain, boolean loop) {
/* 378 */     return playAsSoundAt(buffer, pitch, gain, loop, 0.0F, 0.0F, 0.0F);
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
/*     */ 
/*     */   
/*     */   int playAsSoundAt(int buffer, float pitch, float gain, boolean loop, float x, float y, float z) {
/* 395 */     gain *= this.soundVolume;
/* 396 */     if (gain == 0.0F) {
/* 397 */       gain = 0.001F;
/*     */     }
/* 399 */     if (this.soundWorks && 
/* 400 */       this.sounds) {
/* 401 */       int nextSource = findFreeSource();
/* 402 */       if (nextSource == -1) {
/* 403 */         return -1;
/*     */       }
/*     */       
/* 406 */       AL10.alSourceStop(this.sources.get(nextSource));
/*     */       
/* 408 */       AL10.alSourcei(this.sources.get(nextSource), 4105, buffer);
/* 409 */       AL10.alSourcef(this.sources.get(nextSource), 4099, pitch);
/* 410 */       AL10.alSourcef(this.sources.get(nextSource), 4106, gain);
/* 411 */       AL10.alSourcei(this.sources.get(nextSource), 4103, loop ? 1 : 0);
/*     */       
/* 413 */       this.sourcePos.clear();
/* 414 */       this.sourceVel.clear();
/* 415 */       this.sourceVel.put(new float[] { 0.0F, 0.0F, 0.0F });
/* 416 */       this.sourcePos.put(new float[] { x, y, z });
/* 417 */       this.sourcePos.flip();
/* 418 */       this.sourceVel.flip();
/* 419 */       AL10.alSource(this.sources.get(nextSource), 4100, this.sourcePos);
/* 420 */       AL10.alSource(this.sources.get(nextSource), 4102, this.sourceVel);
/*     */       
/* 422 */       AL10.alSourcePlay(this.sources.get(nextSource));
/*     */       
/* 424 */       return nextSource;
/*     */     } 
/*     */ 
/*     */     
/* 428 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isPlaying(int index) {
/* 437 */     int state = AL10.alGetSourcei(this.sources.get(index), 4112);
/*     */     
/* 439 */     return (state == 4114);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int findFreeSource() {
/* 448 */     for (int i = 1; i < this.sourceCount - 1; i++) {
/* 449 */       int state = AL10.alGetSourcei(this.sources.get(i), 4112);
/*     */       
/* 451 */       if (state != 4114 && state != 4115) {
/* 452 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 456 */     return -1;
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
/*     */   void playAsMusic(int buffer, float pitch, float gain, boolean loop) {
/* 468 */     this.paused = false;
/*     */     
/* 470 */     setMOD(null);
/*     */     
/* 472 */     if (this.soundWorks) {
/* 473 */       if (this.currentMusic != -1) {
/* 474 */         AL10.alSourceStop(this.sources.get(0));
/*     */       }
/*     */       
/* 477 */       getMusicSource();
/*     */       
/* 479 */       AL10.alSourcei(this.sources.get(0), 4105, buffer);
/* 480 */       AL10.alSourcef(this.sources.get(0), 4099, pitch);
/* 481 */       AL10.alSourcei(this.sources.get(0), 4103, loop ? 1 : 0);
/*     */       
/* 483 */       this.currentMusic = this.sources.get(0);
/*     */       
/* 485 */       if (!this.music) {
/* 486 */         pauseLoop();
/*     */       } else {
/* 488 */         AL10.alSourcePlay(this.sources.get(0));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getMusicSource() {
/* 499 */     return this.sources.get(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMusicPitch(float pitch) {
/* 508 */     if (this.soundWorks) {
/* 509 */       AL10.alSourcef(this.sources.get(0), 4099, pitch);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pauseLoop() {
/* 517 */     if (this.soundWorks && this.currentMusic != -1) {
/* 518 */       this.paused = true;
/* 519 */       AL10.alSourcePause(this.currentMusic);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void restartLoop() {
/* 527 */     if (this.music && this.soundWorks && this.currentMusic != -1) {
/* 528 */       this.paused = false;
/* 529 */       AL10.alSourcePlay(this.currentMusic);
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
/*     */   boolean isPlaying(OpenALStreamPlayer player) {
/* 541 */     return (this.stream == player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Audio getMOD(String ref) throws IOException {
/* 552 */     return getMOD(ref, ResourceLoader.getResourceAsStream(ref));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Audio getMOD(InputStream in) throws IOException {
/* 563 */     return getMOD(in.toString(), in);
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
/*     */   public Audio getMOD(String ref, InputStream in) throws IOException {
/* 575 */     if (!this.soundWorks) {
/* 576 */       return new NullAudio();
/*     */     }
/* 578 */     if (!this.inited) {
/* 579 */       throw new RuntimeException("Can't load sounds until SoundStore is init(). Use the container init() method.");
/*     */     }
/* 581 */     if (this.deferred) {
/* 582 */       return new DeferredSound(ref, in, 3);
/*     */     }
/*     */     
/* 585 */     return new MODSound(this, in);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Audio getAIF(String ref) throws IOException {
/* 596 */     return getAIF(ref, ResourceLoader.getResourceAsStream(ref));
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
/*     */   public Audio getAIF(InputStream in) throws IOException {
/* 608 */     return getAIF(in.toString(), in);
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
/*     */   public Audio getAIF(String ref, InputStream in) throws IOException {
/* 620 */     in = new BufferedInputStream(in);
/*     */     
/* 622 */     if (!this.soundWorks) {
/* 623 */       return new NullAudio();
/*     */     }
/* 625 */     if (!this.inited) {
/* 626 */       throw new RuntimeException("Can't load sounds until SoundStore is init(). Use the container init() method.");
/*     */     }
/* 628 */     if (this.deferred) {
/* 629 */       return new DeferredSound(ref, in, 4);
/*     */     }
/*     */     
/* 632 */     int buffer = -1;
/*     */     
/* 634 */     if (this.loaded.get(ref) != null) {
/* 635 */       buffer = ((Integer)this.loaded.get(ref)).intValue();
/*     */     } else {
/*     */       try {
/* 638 */         IntBuffer buf = BufferUtils.createIntBuffer(1);
/*     */         
/* 640 */         AiffData data = AiffData.create(in);
/* 641 */         AL10.alGenBuffers(buf);
/* 642 */         AL10.alBufferData(buf.get(0), data.format, data.data, data.samplerate);
/*     */         
/* 644 */         this.loaded.put(ref, new Integer(buf.get(0)));
/* 645 */         buffer = buf.get(0);
/* 646 */       } catch (Exception e) {
/* 647 */         Log.error(e);
/* 648 */         IOException x = new IOException("Failed to load: " + ref);
/* 649 */         x.initCause(e);
/*     */         
/* 651 */         throw x;
/*     */       } 
/*     */     } 
/*     */     
/* 655 */     if (buffer == -1) {
/* 656 */       throw new IOException("Unable to load: " + ref);
/*     */     }
/*     */     
/* 659 */     return new AudioImpl(this, buffer);
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
/*     */   public Audio getWAV(String ref) throws IOException {
/* 672 */     return getWAV(ref, ResourceLoader.getResourceAsStream(ref));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Audio getWAV(InputStream in) throws IOException {
/* 683 */     return getWAV(in.toString(), in);
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
/*     */   public Audio getWAV(String ref, InputStream in) throws IOException {
/* 695 */     if (!this.soundWorks) {
/* 696 */       return new NullAudio();
/*     */     }
/* 698 */     if (!this.inited) {
/* 699 */       throw new RuntimeException("Can't load sounds until SoundStore is init(). Use the container init() method.");
/*     */     }
/* 701 */     if (this.deferred) {
/* 702 */       return new DeferredSound(ref, in, 2);
/*     */     }
/*     */     
/* 705 */     int buffer = -1;
/*     */     
/* 707 */     if (this.loaded.get(ref) != null) {
/* 708 */       buffer = ((Integer)this.loaded.get(ref)).intValue();
/*     */     } else {
/*     */       try {
/* 711 */         IntBuffer buf = BufferUtils.createIntBuffer(1);
/*     */         
/* 713 */         WaveData data = WaveData.create(in);
/* 714 */         AL10.alGenBuffers(buf);
/* 715 */         AL10.alBufferData(buf.get(0), data.format, data.data, data.samplerate);
/*     */         
/* 717 */         this.loaded.put(ref, new Integer(buf.get(0)));
/* 718 */         buffer = buf.get(0);
/* 719 */       } catch (Exception e) {
/* 720 */         Log.error(e);
/* 721 */         IOException x = new IOException("Failed to load: " + ref);
/* 722 */         x.initCause(e);
/*     */         
/* 724 */         throw x;
/*     */       } 
/*     */     } 
/*     */     
/* 728 */     if (buffer == -1) {
/* 729 */       throw new IOException("Unable to load: " + ref);
/*     */     }
/*     */     
/* 732 */     return new AudioImpl(this, buffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Audio getOggStream(String ref) throws IOException {
/* 743 */     if (!this.soundWorks) {
/* 744 */       return new NullAudio();
/*     */     }
/*     */     
/* 747 */     setMOD(null);
/* 748 */     setStream(null);
/*     */     
/* 750 */     if (this.currentMusic != -1) {
/* 751 */       AL10.alSourceStop(this.sources.get(0));
/*     */     }
/*     */     
/* 754 */     getMusicSource();
/* 755 */     this.currentMusic = this.sources.get(0);
/*     */     
/* 757 */     return new StreamSound(new OpenALStreamPlayer(this.currentMusic, ref));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Audio getOggStream(URL ref) throws IOException {
/* 768 */     if (!this.soundWorks) {
/* 769 */       return new NullAudio();
/*     */     }
/*     */     
/* 772 */     setMOD(null);
/* 773 */     setStream(null);
/*     */     
/* 775 */     if (this.currentMusic != -1) {
/* 776 */       AL10.alSourceStop(this.sources.get(0));
/*     */     }
/*     */     
/* 779 */     getMusicSource();
/* 780 */     this.currentMusic = this.sources.get(0);
/*     */     
/* 782 */     return new StreamSound(new OpenALStreamPlayer(this.currentMusic, ref));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Audio getOgg(String ref) throws IOException {
/* 793 */     return getOgg(ref, ResourceLoader.getResourceAsStream(ref));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Audio getOgg(InputStream in) throws IOException {
/* 804 */     return getOgg(in.toString(), in);
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
/*     */   public Audio getOgg(String ref, InputStream in) throws IOException {
/* 816 */     if (!this.soundWorks) {
/* 817 */       return new NullAudio();
/*     */     }
/* 819 */     if (!this.inited) {
/* 820 */       throw new RuntimeException("Can't load sounds until SoundStore is init(). Use the container init() method.");
/*     */     }
/* 822 */     if (this.deferred) {
/* 823 */       return new DeferredSound(ref, in, 1);
/*     */     }
/*     */     
/* 826 */     int buffer = -1;
/*     */     
/* 828 */     if (this.loaded.get(ref) != null) {
/* 829 */       buffer = ((Integer)this.loaded.get(ref)).intValue();
/*     */     } else {
/*     */       try {
/* 832 */         IntBuffer buf = BufferUtils.createIntBuffer(1);
/*     */         
/* 834 */         OggDecoder decoder = new OggDecoder();
/* 835 */         OggData ogg = decoder.getData(in);
/*     */         
/* 837 */         AL10.alGenBuffers(buf);
/* 838 */         AL10.alBufferData(buf.get(0), (ogg.channels > 1) ? 4355 : 4353, ogg.data, ogg.rate);
/*     */         
/* 840 */         this.loaded.put(ref, new Integer(buf.get(0)));
/*     */         
/* 842 */         buffer = buf.get(0);
/* 843 */       } catch (Exception e) {
/* 844 */         Log.error(e);
/* 845 */         Sys.alert("Error", "Failed to load: " + ref + " - " + e.getMessage());
/* 846 */         throw new IOException("Unable to load: " + ref);
/*     */       } 
/*     */     } 
/*     */     
/* 850 */     if (buffer == -1) {
/* 851 */       throw new IOException("Unable to load: " + ref);
/*     */     }
/*     */     
/* 854 */     return new AudioImpl(this, buffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setMOD(MODSound sound) {
/* 863 */     if (!this.soundWorks) {
/*     */       return;
/*     */     }
/*     */     
/* 867 */     this.currentMusic = this.sources.get(0);
/* 868 */     stopSource(0);
/*     */     
/* 870 */     this.mod = sound;
/* 871 */     if (sound != null) {
/* 872 */       this.stream = null;
/*     */     }
/* 874 */     this.paused = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setStream(OpenALStreamPlayer stream) {
/* 883 */     if (!this.soundWorks) {
/*     */       return;
/*     */     }
/*     */     
/* 887 */     this.currentMusic = this.sources.get(0);
/* 888 */     this.stream = stream;
/* 889 */     if (stream != null) {
/* 890 */       this.mod = null;
/*     */     }
/* 892 */     this.paused = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void poll(int delta) {
/* 901 */     if (!this.soundWorks) {
/*     */       return;
/*     */     }
/* 904 */     if (this.paused) {
/*     */       return;
/*     */     }
/*     */     
/* 908 */     if (this.music) {
/* 909 */       if (this.mod != null) {
/*     */         try {
/* 911 */           this.mod.poll();
/* 912 */         } catch (OpenALException e) {
/* 913 */           Log.error("Error with OpenGL MOD Player on this this platform");
/* 914 */           Log.error((Throwable)e);
/* 915 */           this.mod = null;
/*     */         } 
/*     */       }
/* 918 */       if (this.stream != null) {
/*     */         try {
/* 920 */           this.stream.update();
/* 921 */         } catch (OpenALException e) {
/* 922 */           Log.error("Error with OpenGL Streaming Player on this this platform");
/* 923 */           Log.error((Throwable)e);
/* 924 */           this.mod = null;
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMusicPlaying() {
/* 937 */     if (!this.soundWorks) {
/* 938 */       return false;
/*     */     }
/*     */     
/* 941 */     int state = AL10.alGetSourcei(this.sources.get(0), 4112);
/* 942 */     return (state == 4114 || state == 4115);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SoundStore get() {
/* 951 */     return store;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stopSoundEffect(int id) {
/* 962 */     AL10.alSourceStop(id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSourceCount() {
/* 972 */     return this.sourceCount;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\openal\SoundStore.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */