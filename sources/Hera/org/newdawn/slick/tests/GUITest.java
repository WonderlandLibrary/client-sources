/*     */ package org.newdawn.slick.tests;
/*     */ 
/*     */ import org.newdawn.slick.AngelCodeFont;
/*     */ import org.newdawn.slick.AppGameContainer;
/*     */ import org.newdawn.slick.BasicGame;
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.Font;
/*     */ import org.newdawn.slick.Game;
/*     */ import org.newdawn.slick.GameContainer;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.Image;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.gui.AbstractComponent;
/*     */ import org.newdawn.slick.gui.ComponentListener;
/*     */ import org.newdawn.slick.gui.GUIContext;
/*     */ import org.newdawn.slick.gui.MouseOverArea;
/*     */ import org.newdawn.slick.gui.TextField;
/*     */ import org.newdawn.slick.util.Log;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GUITest
/*     */   extends BasicGame
/*     */   implements ComponentListener
/*     */ {
/*     */   private Image image;
/*  28 */   private MouseOverArea[] areas = new MouseOverArea[4];
/*     */   
/*     */   private GameContainer container;
/*     */   
/*  32 */   private String message = "Demo Menu System with stock images";
/*     */ 
/*     */   
/*     */   private TextField field;
/*     */ 
/*     */   
/*     */   private TextField field2;
/*     */   
/*     */   private Image background;
/*     */   
/*     */   private Font font;
/*     */   
/*     */   private AppGameContainer app;
/*     */ 
/*     */   
/*     */   public GUITest() {
/*  48 */     super("GUI Test");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameContainer container) throws SlickException {
/*  55 */     if (container instanceof AppGameContainer) {
/*  56 */       this.app = (AppGameContainer)container;
/*  57 */       this.app.setIcon("testdata/icon.tga");
/*     */     } 
/*     */     
/*  60 */     this.font = (Font)new AngelCodeFont("testdata/demo2.fnt", "testdata/demo2_00.tga");
/*  61 */     this.field = new TextField((GUIContext)container, this.font, 150, 20, 500, 35, new ComponentListener() {
/*     */           public void componentActivated(AbstractComponent source) {
/*  63 */             GUITest.this.message = "Entered1: " + GUITest.this.field.getText();
/*  64 */             GUITest.this.field2.setFocus(true);
/*     */           }
/*     */         });
/*  67 */     this.field2 = new TextField((GUIContext)container, this.font, 150, 70, 500, 35, new ComponentListener() {
/*     */           public void componentActivated(AbstractComponent source) {
/*  69 */             GUITest.this.message = "Entered2: " + GUITest.this.field2.getText();
/*  70 */             GUITest.this.field.setFocus(true);
/*     */           }
/*     */         });
/*  73 */     this.field2.setBorderColor(Color.red);
/*     */     
/*  75 */     this.container = container;
/*     */     
/*  77 */     this.image = new Image("testdata/logo.tga");
/*  78 */     this.background = new Image("testdata/dungeontiles.gif");
/*  79 */     container.setMouseCursor("testdata/cursor.tga", 0, 0);
/*     */     
/*  81 */     for (int i = 0; i < 4; i++) {
/*  82 */       this.areas[i] = new MouseOverArea((GUIContext)container, this.image, 300, 100 + i * 100, 200, 90, this);
/*  83 */       this.areas[i].setNormalColor(new Color(1.0F, 1.0F, 1.0F, 0.8F));
/*  84 */       this.areas[i].setMouseOverColor(new Color(1.0F, 1.0F, 1.0F, 0.9F));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GameContainer container, Graphics g) {
/*  92 */     this.background.draw(0.0F, 0.0F, 800.0F, 500.0F);
/*     */     
/*  94 */     for (int i = 0; i < 4; i++) {
/*  95 */       this.areas[i].render((GUIContext)container, g);
/*     */     }
/*  97 */     this.field.render((GUIContext)container, g);
/*  98 */     this.field2.render((GUIContext)container, g);
/*     */     
/* 100 */     g.setFont(this.font);
/* 101 */     g.drawString(this.message, 200.0F, 550.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(GameContainer container, int delta) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyPressed(int key, char c) {
/* 114 */     if (key == 1) {
/* 115 */       System.exit(0);
/*     */     }
/* 117 */     if (key == 60) {
/* 118 */       this.app.setDefaultMouseCursor();
/*     */     }
/* 120 */     if (key == 59 && 
/* 121 */       this.app != null) {
/*     */       try {
/* 123 */         this.app.setDisplayMode(640, 480, false);
/* 124 */       } catch (SlickException e) {
/* 125 */         Log.error((Throwable)e);
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
/*     */   public static void main(String[] argv) {
/*     */     try {
/* 138 */       AppGameContainer container = new AppGameContainer((Game)new GUITest());
/* 139 */       container.setDisplayMode(800, 600, false);
/* 140 */       container.start();
/* 141 */     } catch (SlickException e) {
/* 142 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void componentActivated(AbstractComponent source) {
/* 150 */     System.out.println("ACTIVL : " + source);
/* 151 */     for (int i = 0; i < 4; i++) {
/* 152 */       if (source == this.areas[i]) {
/* 153 */         this.message = "Option " + (i + 1) + " pressed!";
/*     */       }
/*     */     } 
/* 156 */     if (source == this.field2);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\GUITest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */