/*   1:    */ package org.newdawn.slick.tests;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.newdawn.slick.AngelCodeFont;
/*   5:    */ import org.newdawn.slick.AppGameContainer;
/*   6:    */ import org.newdawn.slick.BasicGame;
/*   7:    */ import org.newdawn.slick.Font;
/*   8:    */ import org.newdawn.slick.GameContainer;
/*   9:    */ import org.newdawn.slick.Graphics;
/*  10:    */ import org.newdawn.slick.Image;
/*  11:    */ import org.newdawn.slick.Music;
/*  12:    */ import org.newdawn.slick.SlickException;
/*  13:    */ import org.newdawn.slick.Sound;
/*  14:    */ import org.newdawn.slick.loading.DeferredResource;
/*  15:    */ import org.newdawn.slick.loading.LoadingList;
/*  16:    */ 
/*  17:    */ public class DeferredLoadingTest
/*  18:    */   extends BasicGame
/*  19:    */ {
/*  20:    */   private Music music;
/*  21:    */   private Sound sound;
/*  22:    */   private Image image;
/*  23:    */   private Font font;
/*  24:    */   private DeferredResource nextResource;
/*  25:    */   private boolean started;
/*  26:    */   
/*  27:    */   public DeferredLoadingTest()
/*  28:    */   {
/*  29: 43 */     super("Deferred Loading Test");
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void init(GameContainer container)
/*  33:    */     throws SlickException
/*  34:    */   {
/*  35: 50 */     LoadingList.setDeferredLoading(true);
/*  36:    */     
/*  37: 52 */     new Sound("testdata/cbrown01.wav");
/*  38: 53 */     new Sound("testdata/engine.wav");
/*  39: 54 */     this.sound = new Sound("testdata/restart.ogg");
/*  40: 55 */     new Music("testdata/testloop.ogg");
/*  41: 56 */     this.music = new Music("testdata/SMB-X.XM");
/*  42:    */     
/*  43: 58 */     new Image("testdata/cursor.png");
/*  44: 59 */     new Image("testdata/cursor.tga");
/*  45: 60 */     new Image("testdata/cursor.png");
/*  46: 61 */     new Image("testdata/cursor.png");
/*  47: 62 */     new Image("testdata/dungeontiles.gif");
/*  48: 63 */     new Image("testdata/logo.gif");
/*  49: 64 */     this.image = new Image("testdata/logo.tga");
/*  50: 65 */     new Image("testdata/logo.png");
/*  51: 66 */     new Image("testdata/rocket.png");
/*  52: 67 */     new Image("testdata/testpack.png");
/*  53:    */     
/*  54: 69 */     this.font = new AngelCodeFont("testdata/demo.fnt", "testdata/demo_00.tga");
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void render(GameContainer container, Graphics g)
/*  58:    */   {
/*  59: 76 */     if (this.nextResource != null) {
/*  60: 77 */       g.drawString("Loading: " + this.nextResource.getDescription(), 100.0F, 100.0F);
/*  61:    */     }
/*  62: 80 */     int total = LoadingList.get().getTotalResources();
/*  63: 81 */     int loaded = LoadingList.get().getTotalResources() - LoadingList.get().getRemainingResources();
/*  64:    */     
/*  65: 83 */     float bar = loaded / total;
/*  66: 84 */     g.fillRect(100.0F, 150.0F, loaded * 40, 20.0F);
/*  67: 85 */     g.drawRect(100.0F, 150.0F, total * 40, 20.0F);
/*  68: 87 */     if (this.started)
/*  69:    */     {
/*  70: 88 */       this.image.draw(100.0F, 200.0F);
/*  71: 89 */       this.font.drawString(100.0F, 500.0F, "LOADING COMPLETE");
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void update(GameContainer container, int delta)
/*  76:    */     throws SlickException
/*  77:    */   {
/*  78: 97 */     if (this.nextResource != null) {
/*  79:    */       try
/*  80:    */       {
/*  81: 99 */         this.nextResource.load();
/*  82:    */         try
/*  83:    */         {
/*  84:101 */           Thread.sleep(50L);
/*  85:    */         }
/*  86:    */         catch (Exception localException) {}
/*  87:106 */         this.nextResource = null;
/*  88:    */       }
/*  89:    */       catch (IOException e)
/*  90:    */       {
/*  91:103 */         throw new SlickException("Failed to load: " + this.nextResource.getDescription(), e);
/*  92:    */       }
/*  93:    */     }
/*  94:109 */     if (LoadingList.get().getRemainingResources() > 0)
/*  95:    */     {
/*  96:110 */       this.nextResource = LoadingList.get().getNext();
/*  97:    */     }
/*  98:112 */     else if (!this.started)
/*  99:    */     {
/* 100:113 */       this.started = true;
/* 101:114 */       this.music.loop();
/* 102:115 */       this.sound.play();
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   public static void main(String[] argv)
/* 107:    */   {
/* 108:    */     try
/* 109:    */     {
/* 110:127 */       AppGameContainer container = new AppGameContainer(new DeferredLoadingTest());
/* 111:128 */       container.setDisplayMode(800, 600, false);
/* 112:129 */       container.start();
/* 113:    */     }
/* 114:    */     catch (SlickException e)
/* 115:    */     {
/* 116:131 */       e.printStackTrace();
/* 117:    */     }
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void keyPressed(int key, char c) {}
/* 121:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.DeferredLoadingTest
 * JD-Core Version:    0.7.0.1
 */