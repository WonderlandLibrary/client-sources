/*   1:    */ package org.newdawn.slick.tests;
/*   2:    */ 
/*   3:    */ import org.newdawn.slick.AppGameContainer;
/*   4:    */ import org.newdawn.slick.BasicGame;
/*   5:    */ import org.newdawn.slick.Color;
/*   6:    */ import org.newdawn.slick.GameContainer;
/*   7:    */ import org.newdawn.slick.Graphics;
/*   8:    */ import org.newdawn.slick.Image;
/*   9:    */ import org.newdawn.slick.SlickException;
/*  10:    */ import org.newdawn.slick.geom.Polygon;
/*  11:    */ import org.newdawn.slick.util.FastTrig;
/*  12:    */ 
/*  13:    */ public class GraphicsTest
/*  14:    */   extends BasicGame
/*  15:    */ {
/*  16:    */   private boolean clip;
/*  17:    */   private float ang;
/*  18:    */   private Image image;
/*  19:    */   private Polygon poly;
/*  20:    */   private GameContainer container;
/*  21:    */   
/*  22:    */   public GraphicsTest()
/*  23:    */   {
/*  24: 35 */     super("Graphics Test");
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void init(GameContainer container)
/*  28:    */     throws SlickException
/*  29:    */   {
/*  30: 42 */     this.container = container;
/*  31:    */     
/*  32: 44 */     this.image = new Image("testdata/logo.tga", true);
/*  33:    */     
/*  34: 46 */     Image temp = new Image("testdata/palette_tool.png");
/*  35: 47 */     container.setMouseCursor(temp, 0, 0);
/*  36:    */     
/*  37: 49 */     container.setIcons(new String[] { "testdata/icon.tga" });
/*  38: 50 */     container.setTargetFrameRate(100);
/*  39:    */     
/*  40: 52 */     this.poly = new Polygon();
/*  41: 53 */     float len = 100.0F;
/*  42: 55 */     for (int x = 0; x < 360; x += 30)
/*  43:    */     {
/*  44: 56 */       if (len == 100.0F) {
/*  45: 57 */         len = 50.0F;
/*  46:    */       } else {
/*  47: 59 */         len = 100.0F;
/*  48:    */       }
/*  49: 61 */       this.poly.addPoint((float)FastTrig.cos(Math.toRadians(x)) * len, 
/*  50: 62 */         (float)FastTrig.sin(Math.toRadians(x)) * len);
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void render(GameContainer container, Graphics g)
/*  55:    */     throws SlickException
/*  56:    */   {
/*  57: 70 */     g.setColor(Color.white);
/*  58:    */     
/*  59: 72 */     g.setAntiAlias(true);
/*  60: 73 */     for (int x = 0; x < 360; x += 10) {
/*  61: 74 */       g.drawLine(700.0F, 100.0F, (int)(700.0D + Math.cos(Math.toRadians(x)) * 100.0D), 
/*  62: 75 */         (int)(100.0D + Math.sin(Math.toRadians(x)) * 100.0D));
/*  63:    */     }
/*  64: 77 */     g.setAntiAlias(false);
/*  65:    */     
/*  66: 79 */     g.setColor(Color.yellow);
/*  67: 80 */     g.drawString("The Graphics Test!", 300.0F, 50.0F);
/*  68: 81 */     g.setColor(Color.white);
/*  69: 82 */     g.drawString("Space - Toggles clipping", 400.0F, 80.0F);
/*  70: 83 */     g.drawString("Frame rate capped to 100", 400.0F, 120.0F);
/*  71: 85 */     if (this.clip)
/*  72:    */     {
/*  73: 86 */       g.setColor(Color.gray);
/*  74: 87 */       g.drawRect(100.0F, 260.0F, 400.0F, 100.0F);
/*  75: 88 */       g.setClip(100, 260, 400, 100);
/*  76:    */     }
/*  77: 91 */     g.setColor(Color.yellow);
/*  78: 92 */     g.translate(100.0F, 120.0F);
/*  79: 93 */     g.fill(this.poly);
/*  80: 94 */     g.setColor(Color.blue);
/*  81: 95 */     g.setLineWidth(3.0F);
/*  82: 96 */     g.draw(this.poly);
/*  83: 97 */     g.setLineWidth(1.0F);
/*  84: 98 */     g.translate(0.0F, 230.0F);
/*  85: 99 */     g.draw(this.poly);
/*  86:100 */     g.resetTransform();
/*  87:    */     
/*  88:102 */     g.setColor(Color.magenta);
/*  89:103 */     g.drawRoundRect(10.0F, 10.0F, 100.0F, 100.0F, 10);
/*  90:104 */     g.fillRoundRect(10.0F, 210.0F, 100.0F, 100.0F, 10);
/*  91:    */     
/*  92:106 */     g.rotate(400.0F, 300.0F, this.ang);
/*  93:107 */     g.setColor(Color.green);
/*  94:108 */     g.drawRect(200.0F, 200.0F, 200.0F, 200.0F);
/*  95:109 */     g.setColor(Color.blue);
/*  96:110 */     g.fillRect(250.0F, 250.0F, 100.0F, 100.0F);
/*  97:    */     
/*  98:112 */     g.drawImage(this.image, 300.0F, 270.0F);
/*  99:    */     
/* 100:114 */     g.setColor(Color.red);
/* 101:115 */     g.drawOval(100.0F, 100.0F, 200.0F, 200.0F);
/* 102:116 */     g.setColor(Color.red.darker());
/* 103:117 */     g.fillOval(300.0F, 300.0F, 150.0F, 100.0F);
/* 104:118 */     g.setAntiAlias(true);
/* 105:119 */     g.setColor(Color.white);
/* 106:120 */     g.setLineWidth(5.0F);
/* 107:121 */     g.drawOval(300.0F, 300.0F, 150.0F, 100.0F);
/* 108:122 */     g.setAntiAlias(true);
/* 109:123 */     g.resetTransform();
/* 110:125 */     if (this.clip) {
/* 111:126 */       g.clearClip();
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void update(GameContainer container, int delta)
/* 116:    */   {
/* 117:134 */     this.ang += delta * 0.1F;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void keyPressed(int key, char c)
/* 121:    */   {
/* 122:141 */     if (key == 1) {
/* 123:142 */       System.exit(0);
/* 124:    */     }
/* 125:144 */     if (key == 57) {
/* 126:145 */       this.clip = (!this.clip);
/* 127:    */     }
/* 128:    */   }
/* 129:    */   
/* 130:    */   public static void main(String[] argv)
/* 131:    */   {
/* 132:    */     try
/* 133:    */     {
/* 134:156 */       AppGameContainer container = new AppGameContainer(new GraphicsTest());
/* 135:157 */       container.setDisplayMode(800, 600, false);
/* 136:158 */       container.start();
/* 137:    */     }
/* 138:    */     catch (SlickException e)
/* 139:    */     {
/* 140:160 */       e.printStackTrace();
/* 141:    */     }
/* 142:    */   }
/* 143:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.GraphicsTest
 * JD-Core Version:    0.7.0.1
 */