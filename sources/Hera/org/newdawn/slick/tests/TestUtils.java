/*     */ package org.newdawn.slick.tests;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import org.lwjgl.LWJGLException;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.opengl.Display;
/*     */ import org.lwjgl.opengl.DisplayMode;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.Font;
/*     */ import org.newdawn.slick.TrueTypeFont;
/*     */ import org.newdawn.slick.openal.Audio;
/*     */ import org.newdawn.slick.openal.AudioLoader;
/*     */ import org.newdawn.slick.openal.SoundStore;
/*     */ import org.newdawn.slick.opengl.Texture;
/*     */ import org.newdawn.slick.opengl.TextureLoader;
/*     */ import org.newdawn.slick.util.Log;
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
/*     */ public class TestUtils
/*     */ {
/*     */   private Texture texture;
/*     */   private Audio oggEffect;
/*     */   private Audio wavEffect;
/*     */   private Audio aifEffect;
/*     */   private Audio oggStream;
/*     */   private Audio modStream;
/*     */   private Font font;
/*     */   
/*     */   public void start() {
/*  48 */     initGL(800, 600);
/*  49 */     init();
/*     */     
/*     */     while (true) {
/*  52 */       update();
/*  53 */       GL11.glClear(16384);
/*  54 */       render();
/*     */       
/*  56 */       Display.update();
/*  57 */       Display.sync(100);
/*     */       
/*  59 */       if (Display.isCloseRequested()) {
/*  60 */         System.exit(0);
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
/*     */   private void initGL(int width, int height) {
/*     */     try {
/*  73 */       Display.setDisplayMode(new DisplayMode(width, height));
/*  74 */       Display.create();
/*  75 */       Display.setVSyncEnabled(true);
/*  76 */     } catch (LWJGLException e) {
/*  77 */       e.printStackTrace();
/*  78 */       System.exit(0);
/*     */     } 
/*     */     
/*  81 */     GL11.glEnable(3553);
/*  82 */     GL11.glShadeModel(7425);
/*  83 */     GL11.glDisable(2929);
/*  84 */     GL11.glDisable(2896);
/*     */     
/*  86 */     GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
/*  87 */     GL11.glClearDepth(1.0D);
/*     */     
/*  89 */     GL11.glEnable(3042);
/*  90 */     GL11.glBlendFunc(770, 771);
/*     */     
/*  92 */     GL11.glViewport(0, 0, width, height);
/*  93 */     GL11.glMatrixMode(5888);
/*     */     
/*  95 */     GL11.glMatrixMode(5889);
/*  96 */     GL11.glLoadIdentity();
/*  97 */     GL11.glOrtho(0.0D, width, height, 0.0D, 1.0D, -1.0D);
/*  98 */     GL11.glMatrixMode(5888);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/* 106 */     Log.setVerbose(false);
/*     */     
/* 108 */     Font awtFont = new Font("Times New Roman", 1, 16);
/* 109 */     this.font = (Font)new TrueTypeFont(awtFont, false);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 115 */       this.texture = TextureLoader.getTexture("PNG", new FileInputStream("testdata/rocks.png"));
/*     */       
/* 117 */       System.out.println("Texture loaded: " + this.texture);
/* 118 */       System.out.println(">> Image width: " + this.texture.getImageWidth());
/* 119 */       System.out.println(">> Image height: " + this.texture.getImageWidth());
/* 120 */       System.out.println(">> Texture width: " + this.texture.getTextureWidth());
/* 121 */       System.out.println(">> Texture height: " + this.texture.getTextureHeight());
/* 122 */       System.out.println(">> Texture ID: " + this.texture.getTextureID());
/* 123 */     } catch (IOException e) {
/* 124 */       e.printStackTrace();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 130 */       this.oggEffect = AudioLoader.getAudio("OGG", new FileInputStream("testdata/restart.ogg"));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 135 */       this.oggStream = AudioLoader.getStreamingAudio("OGG", (new File("testdata/bongos.ogg")).toURL());
/*     */ 
/*     */ 
/*     */       
/* 139 */       this.modStream = AudioLoader.getStreamingAudio("MOD", (new File("testdata/SMB-X.XM")).toURL());
/*     */ 
/*     */ 
/*     */       
/* 143 */       this.modStream.playAsMusic(1.0F, 1.0F, true);
/*     */ 
/*     */ 
/*     */       
/* 147 */       this.aifEffect = AudioLoader.getAudio("AIF", new FileInputStream("testdata/burp.aif"));
/*     */ 
/*     */ 
/*     */       
/* 151 */       this.wavEffect = AudioLoader.getAudio("WAV", new FileInputStream("testdata/cbrown01.wav"));
/* 152 */     } catch (IOException e) {
/* 153 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/* 161 */     while (Keyboard.next()) {
/* 162 */       if (Keyboard.getEventKeyState()) {
/* 163 */         if (Keyboard.getEventKey() == 16)
/*     */         {
/* 165 */           this.oggEffect.playAsSoundEffect(1.0F, 1.0F, false);
/*     */         }
/* 167 */         if (Keyboard.getEventKey() == 17)
/*     */         {
/*     */           
/* 170 */           this.oggStream.playAsMusic(1.0F, 1.0F, true);
/*     */         }
/* 172 */         if (Keyboard.getEventKey() == 18)
/*     */         {
/*     */           
/* 175 */           this.modStream.playAsMusic(1.0F, 1.0F, true);
/*     */         }
/* 177 */         if (Keyboard.getEventKey() == 19)
/*     */         {
/* 179 */           this.aifEffect.playAsSoundEffect(1.0F, 1.0F, false);
/*     */         }
/* 181 */         if (Keyboard.getEventKey() == 20)
/*     */         {
/* 183 */           this.wavEffect.playAsSoundEffect(1.0F, 1.0F, false);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 190 */     SoundStore.get().poll(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render() {
/* 197 */     Color.white.bind();
/* 198 */     this.texture.bind();
/*     */     
/* 200 */     GL11.glBegin(7);
/* 201 */     GL11.glTexCoord2f(0.0F, 0.0F);
/* 202 */     GL11.glVertex2f(100.0F, 100.0F);
/* 203 */     GL11.glTexCoord2f(1.0F, 0.0F);
/* 204 */     GL11.glVertex2f((100 + this.texture.getTextureWidth()), 100.0F);
/* 205 */     GL11.glTexCoord2f(1.0F, 1.0F);
/* 206 */     GL11.glVertex2f((100 + this.texture.getTextureWidth()), (100 + this.texture.getTextureHeight()));
/* 207 */     GL11.glTexCoord2f(0.0F, 1.0F);
/* 208 */     GL11.glVertex2f(100.0F, (100 + this.texture.getTextureHeight()));
/* 209 */     GL11.glEnd();
/*     */     
/* 211 */     this.font.drawString(150.0F, 300.0F, "HELLO LWJGL WORLD", Color.yellow);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] argv) {
/* 220 */     TestUtils utils = new TestUtils();
/* 221 */     utils.start();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\TestUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */