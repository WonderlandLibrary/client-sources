/*   1:    */ package org.newdawn.slick.tests;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.newdawn.slick.AppGameContainer;
/*   5:    */ import org.newdawn.slick.BasicGame;
/*   6:    */ import org.newdawn.slick.GameContainer;
/*   7:    */ import org.newdawn.slick.Graphics;
/*   8:    */ import org.newdawn.slick.Image;
/*   9:    */ import org.newdawn.slick.Input;
/*  10:    */ import org.newdawn.slick.SlickException;
/*  11:    */ import org.newdawn.slick.imageout.ImageOut;
/*  12:    */ import org.newdawn.slick.particles.ParticleIO;
/*  13:    */ import org.newdawn.slick.particles.ParticleSystem;
/*  14:    */ 
/*  15:    */ public class ImageOutTest
/*  16:    */   extends BasicGame
/*  17:    */ {
/*  18:    */   private GameContainer container;
/*  19:    */   private ParticleSystem fire;
/*  20:    */   private Graphics g;
/*  21:    */   private Image copy;
/*  22:    */   private String message;
/*  23:    */   
/*  24:    */   public ImageOutTest()
/*  25:    */   {
/*  26: 37 */     super("Image Out Test");
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void init(GameContainer container)
/*  30:    */     throws SlickException
/*  31:    */   {
/*  32: 44 */     this.container = container;
/*  33:    */     try
/*  34:    */     {
/*  35: 47 */       this.fire = ParticleIO.loadConfiguredSystem("testdata/system.xml");
/*  36:    */     }
/*  37:    */     catch (IOException e)
/*  38:    */     {
/*  39: 49 */       throw new SlickException("Failed to load particle systems", e);
/*  40:    */     }
/*  41: 52 */     this.copy = new Image(400, 300);
/*  42: 53 */     String[] formats = ImageOut.getSupportedFormats();
/*  43: 54 */     this.message = "Formats supported: ";
/*  44: 55 */     for (int i = 0; i < formats.length; i++)
/*  45:    */     {
/*  46: 56 */       this.message += formats[i];
/*  47: 57 */       if (i < formats.length - 1) {
/*  48: 58 */         this.message += ",";
/*  49:    */       }
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void render(GameContainer container, Graphics g)
/*  54:    */   {
/*  55: 67 */     g.drawString("T - TGA Snapshot", 10.0F, 50.0F);
/*  56: 68 */     g.drawString("J - JPG Snapshot", 10.0F, 70.0F);
/*  57: 69 */     g.drawString("P - PNG Snapshot", 10.0F, 90.0F);
/*  58:    */     
/*  59: 71 */     g.setDrawMode(Graphics.MODE_ADD);
/*  60: 72 */     g.drawImage(this.copy, 200.0F, 300.0F);
/*  61: 73 */     g.setDrawMode(Graphics.MODE_NORMAL);
/*  62:    */     
/*  63: 75 */     g.drawString(this.message, 10.0F, 400.0F);
/*  64: 76 */     g.drawRect(200.0F, 0.0F, 400.0F, 300.0F);
/*  65: 77 */     g.translate(400.0F, 250.0F);
/*  66: 78 */     this.fire.render();
/*  67: 79 */     this.g = g;
/*  68:    */   }
/*  69:    */   
/*  70:    */   private void writeTo(String fname)
/*  71:    */     throws SlickException
/*  72:    */   {
/*  73: 89 */     this.g.copyArea(this.copy, 200, 0);
/*  74: 90 */     ImageOut.write(this.copy, fname);
/*  75: 91 */     this.message = ("Written " + fname);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void update(GameContainer container, int delta)
/*  79:    */     throws SlickException
/*  80:    */   {
/*  81: 98 */     this.fire.update(delta);
/*  82:100 */     if (container.getInput().isKeyPressed(25)) {
/*  83:101 */       writeTo("ImageOutTest.png");
/*  84:    */     }
/*  85:103 */     if (container.getInput().isKeyPressed(36)) {
/*  86:104 */       writeTo("ImageOutTest.jpg");
/*  87:    */     }
/*  88:106 */     if (container.getInput().isKeyPressed(20)) {
/*  89:107 */       writeTo("ImageOutTest.tga");
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   public static void main(String[] argv)
/*  94:    */   {
/*  95:    */     try
/*  96:    */     {
/*  97:118 */       AppGameContainer container = new AppGameContainer(new ImageOutTest());
/*  98:119 */       container.setDisplayMode(800, 600, false);
/*  99:120 */       container.start();
/* 100:    */     }
/* 101:    */     catch (SlickException e)
/* 102:    */     {
/* 103:122 */       e.printStackTrace();
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void keyPressed(int key, char c)
/* 108:    */   {
/* 109:130 */     if (key == 1) {
/* 110:131 */       this.container.exit();
/* 111:    */     }
/* 112:    */   }
/* 113:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.ImageOutTest
 * JD-Core Version:    0.7.0.1
 */