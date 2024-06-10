/*   1:    */ package org.newdawn.slick.tests;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import org.newdawn.slick.AppGameContainer;
/*   5:    */ import org.newdawn.slick.BasicGame;
/*   6:    */ import org.newdawn.slick.GameContainer;
/*   7:    */ import org.newdawn.slick.Graphics;
/*   8:    */ import org.newdawn.slick.Image;
/*   9:    */ import org.newdawn.slick.SlickException;
/*  10:    */ 
/*  11:    */ public class ImageTest
/*  12:    */   extends BasicGame
/*  13:    */ {
/*  14:    */   private Image tga;
/*  15:    */   private Image scaleMe;
/*  16:    */   private Image scaled;
/*  17:    */   private Image gif;
/*  18:    */   private Image image;
/*  19:    */   private Image subImage;
/*  20:    */   private Image rotImage;
/*  21:    */   private float rot;
/*  22: 34 */   public static boolean exitMe = true;
/*  23:    */   
/*  24:    */   public ImageTest()
/*  25:    */   {
/*  26: 40 */     super("Image Test");
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void init(GameContainer container)
/*  30:    */     throws SlickException
/*  31:    */   {
/*  32: 47 */     this.image = (this.tga = new Image("testdata/logo.png"));
/*  33: 48 */     this.rotImage = new Image("testdata/logo.png");
/*  34: 49 */     this.rotImage = this.rotImage.getScaledCopy(this.rotImage.getWidth() / 2, this.rotImage.getHeight() / 2);
/*  35:    */     
/*  36:    */ 
/*  37: 52 */     this.scaleMe = new Image("testdata/logo.tga", true, 2);
/*  38: 53 */     this.gif = new Image("testdata/logo.gif");
/*  39: 54 */     this.gif.destroy();
/*  40: 55 */     this.gif = new Image("testdata/logo.gif");
/*  41: 56 */     this.scaled = this.gif.getScaledCopy(120, 120);
/*  42: 57 */     this.subImage = this.image.getSubImage(200, 0, 70, 260);
/*  43: 58 */     this.rot = 0.0F;
/*  44: 60 */     if (exitMe) {
/*  45: 61 */       container.exit();
/*  46:    */     }
/*  47: 64 */     Image test = this.tga.getSubImage(50, 50, 50, 50);
/*  48: 65 */     System.out.println(test.getColor(50, 50));
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void render(GameContainer container, Graphics g)
/*  52:    */   {
/*  53: 72 */     g.drawRect(0.0F, 0.0F, this.image.getWidth(), this.image.getHeight());
/*  54: 73 */     this.image.draw(0.0F, 0.0F);
/*  55: 74 */     this.image.draw(500.0F, 0.0F, 200.0F, 100.0F);
/*  56: 75 */     this.scaleMe.draw(500.0F, 100.0F, 200.0F, 100.0F);
/*  57: 76 */     this.scaled.draw(400.0F, 500.0F);
/*  58: 77 */     Image flipped = this.scaled.getFlippedCopy(true, false);
/*  59: 78 */     flipped.draw(520.0F, 500.0F);
/*  60: 79 */     Image flipped2 = flipped.getFlippedCopy(false, true);
/*  61: 80 */     flipped2.draw(520.0F, 380.0F);
/*  62: 81 */     Image flipped3 = flipped2.getFlippedCopy(true, false);
/*  63: 82 */     flipped3.draw(400.0F, 380.0F);
/*  64: 84 */     for (int i = 0; i < 3; i++) {
/*  65: 85 */       this.subImage.draw(200 + i * 30, 300.0F);
/*  66:    */     }
/*  67: 88 */     g.translate(500.0F, 200.0F);
/*  68: 89 */     g.rotate(50.0F, 50.0F, this.rot);
/*  69: 90 */     g.scale(0.3F, 0.3F);
/*  70: 91 */     this.image.draw();
/*  71: 92 */     g.resetTransform();
/*  72:    */     
/*  73: 94 */     this.rotImage.setRotation(this.rot);
/*  74: 95 */     this.rotImage.draw(100.0F, 200.0F);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void update(GameContainer container, int delta)
/*  78:    */   {
/*  79:102 */     this.rot += delta * 0.1F;
/*  80:103 */     if (this.rot > 360.0F) {
/*  81:104 */       this.rot -= 360.0F;
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   public static void main(String[] argv)
/*  86:    */   {
/*  87:114 */     boolean sharedContextTest = false;
/*  88:    */     try
/*  89:    */     {
/*  90:117 */       exitMe = false;
/*  91:118 */       if (sharedContextTest)
/*  92:    */       {
/*  93:119 */         GameContainer.enableSharedContext();
/*  94:120 */         exitMe = true;
/*  95:    */       }
/*  96:123 */       AppGameContainer container = new AppGameContainer(new ImageTest());
/*  97:124 */       container.setForceExit(!sharedContextTest);
/*  98:125 */       container.setDisplayMode(800, 600, false);
/*  99:126 */       container.start();
/* 100:128 */       if (sharedContextTest)
/* 101:    */       {
/* 102:129 */         System.out.println("Exit first instance");
/* 103:130 */         exitMe = false;
/* 104:131 */         container = new AppGameContainer(new ImageTest());
/* 105:132 */         container.setDisplayMode(800, 600, false);
/* 106:133 */         container.start();
/* 107:    */       }
/* 108:    */     }
/* 109:    */     catch (SlickException e)
/* 110:    */     {
/* 111:136 */       e.printStackTrace();
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void keyPressed(int key, char c)
/* 116:    */   {
/* 117:145 */     if (key == 57) {
/* 118:146 */       if (this.image == this.gif) {
/* 119:147 */         this.image = this.tga;
/* 120:    */       } else {
/* 121:149 */         this.image = this.gif;
/* 122:    */       }
/* 123:    */     }
/* 124:    */   }
/* 125:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.ImageTest
 * JD-Core Version:    0.7.0.1
 */