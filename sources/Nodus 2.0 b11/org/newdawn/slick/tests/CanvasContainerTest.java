/*   1:    */ package org.newdawn.slick.tests;
/*   2:    */ 
/*   3:    */ import java.awt.Frame;
/*   4:    */ import java.awt.GridLayout;
/*   5:    */ import java.awt.event.WindowAdapter;
/*   6:    */ import java.awt.event.WindowEvent;
/*   7:    */ import org.newdawn.slick.BasicGame;
/*   8:    */ import org.newdawn.slick.CanvasGameContainer;
/*   9:    */ import org.newdawn.slick.GameContainer;
/*  10:    */ import org.newdawn.slick.Graphics;
/*  11:    */ import org.newdawn.slick.Image;
/*  12:    */ import org.newdawn.slick.SlickException;
/*  13:    */ 
/*  14:    */ public class CanvasContainerTest
/*  15:    */   extends BasicGame
/*  16:    */ {
/*  17:    */   private Image tga;
/*  18:    */   private Image scaleMe;
/*  19:    */   private Image scaled;
/*  20:    */   private Image gif;
/*  21:    */   private Image image;
/*  22:    */   private Image subImage;
/*  23:    */   private float rot;
/*  24:    */   
/*  25:    */   public CanvasContainerTest()
/*  26:    */   {
/*  27: 41 */     super("Canvas Container Test");
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void init(GameContainer container)
/*  31:    */     throws SlickException
/*  32:    */   {
/*  33: 48 */     this.image = (this.tga = new Image("testdata/logo.tga"));
/*  34: 49 */     this.scaleMe = new Image("testdata/logo.tga", true, 2);
/*  35: 50 */     this.gif = new Image("testdata/logo.gif");
/*  36: 51 */     this.scaled = this.gif.getScaledCopy(120, 120);
/*  37: 52 */     this.subImage = this.image.getSubImage(200, 0, 70, 260);
/*  38: 53 */     this.rot = 0.0F;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void render(GameContainer container, Graphics g)
/*  42:    */   {
/*  43: 60 */     this.image.draw(0.0F, 0.0F);
/*  44: 61 */     this.image.draw(500.0F, 0.0F, 200.0F, 100.0F);
/*  45: 62 */     this.scaleMe.draw(500.0F, 100.0F, 200.0F, 100.0F);
/*  46: 63 */     this.scaled.draw(400.0F, 500.0F);
/*  47: 64 */     Image flipped = this.scaled.getFlippedCopy(true, false);
/*  48: 65 */     flipped.draw(520.0F, 500.0F);
/*  49: 66 */     Image flipped2 = flipped.getFlippedCopy(false, true);
/*  50: 67 */     flipped2.draw(520.0F, 380.0F);
/*  51: 68 */     Image flipped3 = flipped2.getFlippedCopy(true, false);
/*  52: 69 */     flipped3.draw(400.0F, 380.0F);
/*  53: 71 */     for (int i = 0; i < 3; i++) {
/*  54: 72 */       this.subImage.draw(200 + i * 30, 300.0F);
/*  55:    */     }
/*  56: 75 */     g.translate(500.0F, 200.0F);
/*  57: 76 */     g.rotate(50.0F, 50.0F, this.rot);
/*  58: 77 */     g.scale(0.3F, 0.3F);
/*  59: 78 */     this.image.draw();
/*  60: 79 */     g.resetTransform();
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void update(GameContainer container, int delta)
/*  64:    */   {
/*  65: 86 */     this.rot += delta * 0.1F;
/*  66: 87 */     if (this.rot > 360.0F) {
/*  67: 88 */       this.rot -= 360.0F;
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   public static void main(String[] argv)
/*  72:    */   {
/*  73:    */     try
/*  74:    */     {
/*  75: 99 */       CanvasGameContainer container = new CanvasGameContainer(new CanvasContainerTest());
/*  76:    */       
/*  77:101 */       Frame frame = new Frame("Test");
/*  78:102 */       frame.setLayout(new GridLayout(1, 2));
/*  79:103 */       frame.setSize(500, 500);
/*  80:104 */       frame.add(container);
/*  81:    */       
/*  82:106 */       frame.addWindowListener(new WindowAdapter()
/*  83:    */       {
/*  84:    */         public void windowClosing(WindowEvent e)
/*  85:    */         {
/*  86:108 */           System.exit(0);
/*  87:    */         }
/*  88:110 */       });
/*  89:111 */       frame.setVisible(true);
/*  90:112 */       container.start();
/*  91:    */     }
/*  92:    */     catch (Exception e)
/*  93:    */     {
/*  94:114 */       e.printStackTrace();
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void keyPressed(int key, char c)
/*  99:    */   {
/* 100:122 */     if (key == 57) {
/* 101:123 */       if (this.image == this.gif) {
/* 102:124 */         this.image = this.tga;
/* 103:    */       } else {
/* 104:126 */         this.image = this.gif;
/* 105:    */       }
/* 106:    */     }
/* 107:    */   }
/* 108:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.CanvasContainerTest
 * JD-Core Version:    0.7.0.1
 */