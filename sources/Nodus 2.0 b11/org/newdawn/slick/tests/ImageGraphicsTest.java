/*   1:    */ package org.newdawn.slick.tests;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import org.newdawn.slick.AngelCodeFont;
/*   5:    */ import org.newdawn.slick.AppGameContainer;
/*   6:    */ import org.newdawn.slick.BasicGame;
/*   7:    */ import org.newdawn.slick.Color;
/*   8:    */ import org.newdawn.slick.Font;
/*   9:    */ import org.newdawn.slick.GameContainer;
/*  10:    */ import org.newdawn.slick.Graphics;
/*  11:    */ import org.newdawn.slick.Image;
/*  12:    */ import org.newdawn.slick.SlickException;
/*  13:    */ import org.newdawn.slick.opengl.pbuffer.GraphicsFactory;
/*  14:    */ 
/*  15:    */ public class ImageGraphicsTest
/*  16:    */   extends BasicGame
/*  17:    */ {
/*  18:    */   private Image preloaded;
/*  19:    */   private Image target;
/*  20:    */   private Image cut;
/*  21:    */   private Graphics gTarget;
/*  22:    */   private Graphics offscreenPreload;
/*  23:    */   private Image testImage;
/*  24:    */   private Font testFont;
/*  25:    */   private float ang;
/*  26: 37 */   private String using = "none";
/*  27:    */   
/*  28:    */   public ImageGraphicsTest()
/*  29:    */   {
/*  30: 43 */     super("Image Graphics Test");
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void init(GameContainer container)
/*  34:    */     throws SlickException
/*  35:    */   {
/*  36: 50 */     this.testImage = new Image("testdata/logo.png");
/*  37: 51 */     this.preloaded = new Image("testdata/logo.png");
/*  38: 52 */     this.testFont = new AngelCodeFont("testdata/hiero.fnt", "testdata/hiero.png");
/*  39: 53 */     this.target = new Image(400, 300);
/*  40: 54 */     this.cut = new Image(100, 100);
/*  41: 55 */     this.gTarget = this.target.getGraphics();
/*  42: 56 */     this.offscreenPreload = this.preloaded.getGraphics();
/*  43:    */     
/*  44: 58 */     this.offscreenPreload.drawString("Drawing over a loaded image", 5.0F, 15.0F);
/*  45: 59 */     this.offscreenPreload.setLineWidth(5.0F);
/*  46: 60 */     this.offscreenPreload.setAntiAlias(true);
/*  47: 61 */     this.offscreenPreload.setColor(Color.blue.brighter());
/*  48: 62 */     this.offscreenPreload.drawOval(200.0F, 30.0F, 50.0F, 50.0F);
/*  49: 63 */     this.offscreenPreload.setColor(Color.white);
/*  50: 64 */     this.offscreenPreload.drawRect(190.0F, 20.0F, 70.0F, 70.0F);
/*  51: 65 */     this.offscreenPreload.flush();
/*  52: 67 */     if (GraphicsFactory.usingFBO()) {
/*  53: 68 */       this.using = "FBO (Frame Buffer Objects)";
/*  54: 69 */     } else if (GraphicsFactory.usingPBuffer()) {
/*  55: 70 */       this.using = "Pbuffer (Pixel Buffers)";
/*  56:    */     }
/*  57: 73 */     System.out.println(this.preloaded.getColor(50, 50));
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void render(GameContainer container, Graphics g)
/*  61:    */     throws SlickException
/*  62:    */   {
/*  63: 83 */     this.gTarget.setBackground(new Color(0, 0, 0, 0));
/*  64: 84 */     this.gTarget.clear();
/*  65: 85 */     this.gTarget.rotate(200.0F, 160.0F, this.ang);
/*  66: 86 */     this.gTarget.setFont(this.testFont);
/*  67: 87 */     this.gTarget.fillRect(10.0F, 10.0F, 50.0F, 50.0F);
/*  68: 88 */     this.gTarget.drawString("HELLO WORLD", 10.0F, 10.0F);
/*  69:    */     
/*  70: 90 */     this.gTarget.drawImage(this.testImage, 100.0F, 150.0F);
/*  71: 91 */     this.gTarget.drawImage(this.testImage, 100.0F, 50.0F);
/*  72: 92 */     this.gTarget.drawImage(this.testImage, 50.0F, 75.0F);
/*  73:    */     
/*  74:    */ 
/*  75:    */ 
/*  76: 96 */     this.gTarget.flush();
/*  77:    */     
/*  78: 98 */     g.setColor(Color.red);
/*  79: 99 */     g.fillRect(250.0F, 50.0F, 200.0F, 200.0F);
/*  80:    */     
/*  81:    */ 
/*  82:102 */     this.target.draw(300.0F, 100.0F);
/*  83:103 */     this.target.draw(300.0F, 410.0F, 200.0F, 150.0F);
/*  84:104 */     this.target.draw(505.0F, 410.0F, 100.0F, 75.0F);
/*  85:    */     
/*  86:    */ 
/*  87:    */ 
/*  88:108 */     g.setColor(Color.white);
/*  89:109 */     g.drawString("Testing On Offscreen Buffer", 300.0F, 80.0F);
/*  90:110 */     g.setColor(Color.green);
/*  91:111 */     g.drawRect(300.0F, 100.0F, this.target.getWidth(), this.target.getHeight());
/*  92:112 */     g.drawRect(300.0F, 410.0F, this.target.getWidth() / 2, this.target.getHeight() / 2);
/*  93:113 */     g.drawRect(505.0F, 410.0F, this.target.getWidth() / 4, this.target.getHeight() / 4);
/*  94:    */     
/*  95:    */ 
/*  96:    */ 
/*  97:    */ 
/*  98:118 */     g.setColor(Color.white);
/*  99:119 */     g.drawString("Testing Font On Back Buffer", 10.0F, 100.0F);
/* 100:120 */     g.drawString("Using: " + this.using, 10.0F, 580.0F);
/* 101:121 */     g.setColor(Color.red);
/* 102:122 */     g.fillRect(10.0F, 120.0F, 200.0F, 5.0F);
/* 103:    */     
/* 104:    */ 
/* 105:125 */     int xp = (int)(60.0D + Math.sin(this.ang / 60.0F) * 50.0D);
/* 106:126 */     g.copyArea(this.cut, xp, 50);
/* 107:    */     
/* 108:    */ 
/* 109:    */ 
/* 110:130 */     this.cut.draw(30.0F, 250.0F);
/* 111:131 */     g.setColor(Color.white);
/* 112:132 */     g.drawRect(30.0F, 250.0F, this.cut.getWidth(), this.cut.getHeight());
/* 113:133 */     g.setColor(Color.gray);
/* 114:134 */     g.drawRect(xp, 50.0F, this.cut.getWidth(), this.cut.getHeight());
/* 115:    */     
/* 116:    */ 
/* 117:    */ 
/* 118:    */ 
/* 119:139 */     this.preloaded.draw(2.0F, 400.0F);
/* 120:140 */     g.setColor(Color.blue);
/* 121:141 */     g.drawRect(2.0F, 400.0F, this.preloaded.getWidth(), this.preloaded.getHeight());
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void update(GameContainer container, int delta)
/* 125:    */   {
/* 126:148 */     this.ang += delta * 0.1F;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public static void main(String[] argv)
/* 130:    */   {
/* 131:    */     try
/* 132:    */     {
/* 133:158 */       GraphicsFactory.setUseFBO(false);
/* 134:    */       
/* 135:160 */       AppGameContainer container = new AppGameContainer(new ImageGraphicsTest());
/* 136:161 */       container.setDisplayMode(800, 600, false);
/* 137:162 */       container.start();
/* 138:    */     }
/* 139:    */     catch (SlickException e)
/* 140:    */     {
/* 141:164 */       e.printStackTrace();
/* 142:    */     }
/* 143:    */   }
/* 144:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.ImageGraphicsTest
 * JD-Core Version:    0.7.0.1
 */