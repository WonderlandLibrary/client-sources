/*   1:    */ package org.newdawn.slick.tests;
/*   2:    */ 
/*   3:    */ import org.newdawn.slick.AppGameContainer;
/*   4:    */ import org.newdawn.slick.BasicGame;
/*   5:    */ import org.newdawn.slick.Color;
/*   6:    */ import org.newdawn.slick.GameContainer;
/*   7:    */ import org.newdawn.slick.Graphics;
/*   8:    */ import org.newdawn.slick.Input;
/*   9:    */ import org.newdawn.slick.SlickException;
/*  10:    */ import org.newdawn.slick.opengl.renderer.Renderer;
/*  11:    */ import org.newdawn.slick.svg.InkscapeLoader;
/*  12:    */ import org.newdawn.slick.svg.SimpleDiagramRenderer;
/*  13:    */ 
/*  14:    */ public class InkscapeTest
/*  15:    */   extends BasicGame
/*  16:    */ {
/*  17: 21 */   private SimpleDiagramRenderer[] renderer = new SimpleDiagramRenderer[5];
/*  18: 23 */   private float zoom = 1.0F;
/*  19:    */   private float x;
/*  20:    */   private float y;
/*  21:    */   
/*  22:    */   public InkscapeTest()
/*  23:    */   {
/*  24: 33 */     super("Inkscape Test");
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void init(GameContainer container)
/*  28:    */     throws SlickException
/*  29:    */   {
/*  30: 40 */     container.getGraphics().setBackground(Color.white);
/*  31:    */     
/*  32: 42 */     InkscapeLoader.RADIAL_TRIANGULATION_LEVEL = 2;
/*  33:    */     
/*  34:    */ 
/*  35:    */ 
/*  36:    */ 
/*  37: 47 */     this.renderer[3] = new SimpleDiagramRenderer(InkscapeLoader.load("testdata/svg/clonetest.svg"));
/*  38:    */     
/*  39:    */ 
/*  40: 50 */     container.getGraphics().setBackground(new Color(0.5F, 0.7F, 1.0F));
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void update(GameContainer container, int delta)
/*  44:    */     throws SlickException
/*  45:    */   {
/*  46: 57 */     if (container.getInput().isKeyDown(16))
/*  47:    */     {
/*  48: 58 */       this.zoom += delta * 0.01F;
/*  49: 59 */       if (this.zoom > 10.0F) {
/*  50: 60 */         this.zoom = 10.0F;
/*  51:    */       }
/*  52:    */     }
/*  53: 63 */     if (container.getInput().isKeyDown(30))
/*  54:    */     {
/*  55: 64 */       this.zoom -= delta * 0.01F;
/*  56: 65 */       if (this.zoom < 0.1F) {
/*  57: 66 */         this.zoom = 0.1F;
/*  58:    */       }
/*  59:    */     }
/*  60: 69 */     if (container.getInput().isKeyDown(205)) {
/*  61: 70 */       this.x += delta * 0.1F;
/*  62:    */     }
/*  63: 72 */     if (container.getInput().isKeyDown(203)) {
/*  64: 73 */       this.x -= delta * 0.1F;
/*  65:    */     }
/*  66: 75 */     if (container.getInput().isKeyDown(208)) {
/*  67: 76 */       this.y += delta * 0.1F;
/*  68:    */     }
/*  69: 78 */     if (container.getInput().isKeyDown(200)) {
/*  70: 79 */       this.y -= delta * 0.1F;
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void render(GameContainer container, Graphics g)
/*  75:    */     throws SlickException
/*  76:    */   {
/*  77: 87 */     g.scale(this.zoom, this.zoom);
/*  78: 88 */     g.translate(this.x, this.y);
/*  79: 89 */     g.scale(0.3F, 0.3F);
/*  80:    */     
/*  81: 91 */     g.scale(3.333333F, 3.333333F);
/*  82: 92 */     g.translate(400.0F, 0.0F);
/*  83:    */     
/*  84: 94 */     g.translate(100.0F, 300.0F);
/*  85: 95 */     g.scale(0.7F, 0.7F);
/*  86:    */     
/*  87: 97 */     g.scale(1.428572F, 1.428572F);
/*  88:    */     
/*  89: 99 */     g.scale(0.5F, 0.5F);
/*  90:100 */     g.translate(-1100.0F, -380.0F);
/*  91:101 */     this.renderer[3].render(g);
/*  92:102 */     g.scale(2.0F, 2.0F);
/*  93:    */     
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:    */ 
/*  98:108 */     g.resetTransform();
/*  99:    */   }
/* 100:    */   
/* 101:    */   public static void main(String[] argv)
/* 102:    */   {
/* 103:    */     try
/* 104:    */     {
/* 105:118 */       Renderer.setRenderer(2);
/* 106:119 */       Renderer.setLineStripRenderer(4);
/* 107:    */       
/* 108:121 */       AppGameContainer container = new AppGameContainer(new InkscapeTest());
/* 109:122 */       container.setDisplayMode(800, 600, false);
/* 110:123 */       container.start();
/* 111:    */     }
/* 112:    */     catch (SlickException e)
/* 113:    */     {
/* 114:125 */       e.printStackTrace();
/* 115:    */     }
/* 116:    */   }
/* 117:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.InkscapeTest
 * JD-Core Version:    0.7.0.1
 */