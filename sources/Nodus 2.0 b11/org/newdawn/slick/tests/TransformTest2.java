/*   1:    */ package org.newdawn.slick.tests;
/*   2:    */ 
/*   3:    */ import org.newdawn.slick.AppGameContainer;
/*   4:    */ import org.newdawn.slick.BasicGame;
/*   5:    */ import org.newdawn.slick.Color;
/*   6:    */ import org.newdawn.slick.GameContainer;
/*   7:    */ import org.newdawn.slick.Graphics;
/*   8:    */ import org.newdawn.slick.SlickException;
/*   9:    */ 
/*  10:    */ public class TransformTest2
/*  11:    */   extends BasicGame
/*  12:    */ {
/*  13: 18 */   private float scale = 1.0F;
/*  14:    */   private boolean scaleUp;
/*  15:    */   private boolean scaleDown;
/*  16: 25 */   private float camX = 320.0F;
/*  17: 27 */   private float camY = 240.0F;
/*  18:    */   private boolean moveLeft;
/*  19:    */   private boolean moveUp;
/*  20:    */   private boolean moveRight;
/*  21:    */   private boolean moveDown;
/*  22:    */   
/*  23:    */   public TransformTest2()
/*  24:    */   {
/*  25: 42 */     super("Transform Test");
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void init(GameContainer container)
/*  29:    */     throws SlickException
/*  30:    */   {
/*  31: 49 */     container.setTargetFrameRate(100);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void render(GameContainer contiainer, Graphics g)
/*  35:    */   {
/*  36: 56 */     g.translate(320.0F, 240.0F);
/*  37:    */     
/*  38: 58 */     g.translate(-this.camX * this.scale, -this.camY * this.scale);
/*  39:    */     
/*  40:    */ 
/*  41: 61 */     g.scale(this.scale, this.scale);
/*  42:    */     
/*  43: 63 */     g.setColor(Color.red);
/*  44: 64 */     for (int x = 0; x < 10; x++) {
/*  45: 65 */       for (int y = 0; y < 10; y++) {
/*  46: 66 */         g.fillRect(-500 + x * 100, -500 + y * 100, 80.0F, 80.0F);
/*  47:    */       }
/*  48:    */     }
/*  49: 70 */     g.setColor(new Color(1.0F, 1.0F, 1.0F, 0.5F));
/*  50: 71 */     g.fillRect(-320.0F, -240.0F, 640.0F, 480.0F);
/*  51: 72 */     g.setColor(Color.white);
/*  52: 73 */     g.drawRect(-320.0F, -240.0F, 640.0F, 480.0F);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void update(GameContainer container, int delta)
/*  56:    */   {
/*  57: 80 */     if (this.scaleUp) {
/*  58: 81 */       this.scale += delta * 0.001F;
/*  59:    */     }
/*  60: 83 */     if (this.scaleDown) {
/*  61: 84 */       this.scale -= delta * 0.001F;
/*  62:    */     }
/*  63: 87 */     float moveSpeed = delta * 0.4F * (1.0F / this.scale);
/*  64: 89 */     if (this.moveLeft) {
/*  65: 90 */       this.camX -= moveSpeed;
/*  66:    */     }
/*  67: 92 */     if (this.moveUp) {
/*  68: 93 */       this.camY -= moveSpeed;
/*  69:    */     }
/*  70: 95 */     if (this.moveRight) {
/*  71: 96 */       this.camX += moveSpeed;
/*  72:    */     }
/*  73: 98 */     if (this.moveDown) {
/*  74: 99 */       this.camY += moveSpeed;
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void keyPressed(int key, char c)
/*  79:    */   {
/*  80:107 */     if (key == 1) {
/*  81:108 */       System.exit(0);
/*  82:    */     }
/*  83:110 */     if (key == 16) {
/*  84:111 */       this.scaleUp = true;
/*  85:    */     }
/*  86:113 */     if (key == 30) {
/*  87:114 */       this.scaleDown = true;
/*  88:    */     }
/*  89:117 */     if (key == 203) {
/*  90:118 */       this.moveLeft = true;
/*  91:    */     }
/*  92:120 */     if (key == 200) {
/*  93:121 */       this.moveUp = true;
/*  94:    */     }
/*  95:123 */     if (key == 205) {
/*  96:124 */       this.moveRight = true;
/*  97:    */     }
/*  98:126 */     if (key == 208) {
/*  99:127 */       this.moveDown = true;
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void keyReleased(int key, char c)
/* 104:    */   {
/* 105:135 */     if (key == 16) {
/* 106:136 */       this.scaleUp = false;
/* 107:    */     }
/* 108:138 */     if (key == 30) {
/* 109:139 */       this.scaleDown = false;
/* 110:    */     }
/* 111:142 */     if (key == 203) {
/* 112:143 */       this.moveLeft = false;
/* 113:    */     }
/* 114:145 */     if (key == 200) {
/* 115:146 */       this.moveUp = false;
/* 116:    */     }
/* 117:148 */     if (key == 205) {
/* 118:149 */       this.moveRight = false;
/* 119:    */     }
/* 120:151 */     if (key == 208) {
/* 121:152 */       this.moveDown = false;
/* 122:    */     }
/* 123:    */   }
/* 124:    */   
/* 125:    */   public static void main(String[] argv)
/* 126:    */   {
/* 127:    */     try
/* 128:    */     {
/* 129:163 */       AppGameContainer container = new AppGameContainer(new TransformTest2());
/* 130:164 */       container.setDisplayMode(640, 480, false);
/* 131:165 */       container.start();
/* 132:    */     }
/* 133:    */     catch (SlickException e)
/* 134:    */     {
/* 135:167 */       e.printStackTrace();
/* 136:    */     }
/* 137:    */   }
/* 138:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.TransformTest2
 * JD-Core Version:    0.7.0.1
 */