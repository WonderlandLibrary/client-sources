/*   1:    */ package org.newdawn.slick.tests;
/*   2:    */ 
/*   3:    */ import org.newdawn.slick.AppGameContainer;
/*   4:    */ import org.newdawn.slick.BasicGame;
/*   5:    */ import org.newdawn.slick.BigImage;
/*   6:    */ import org.newdawn.slick.Color;
/*   7:    */ import org.newdawn.slick.GameContainer;
/*   8:    */ import org.newdawn.slick.Graphics;
/*   9:    */ import org.newdawn.slick.Image;
/*  10:    */ import org.newdawn.slick.Input;
/*  11:    */ import org.newdawn.slick.SlickException;
/*  12:    */ import org.newdawn.slick.SpriteSheet;
/*  13:    */ 
/*  14:    */ public class BigImageTest
/*  15:    */   extends BasicGame
/*  16:    */ {
/*  17:    */   private Image original;
/*  18:    */   private Image image;
/*  19:    */   private Image imageX;
/*  20:    */   private Image imageY;
/*  21:    */   private Image sub;
/*  22:    */   private Image scaledSub;
/*  23:    */   private float x;
/*  24:    */   private float y;
/*  25: 37 */   private float ang = 30.0F;
/*  26:    */   private SpriteSheet bigSheet;
/*  27:    */   
/*  28:    */   public BigImageTest()
/*  29:    */   {
/*  30: 45 */     super("Big Image Test");
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void init(GameContainer container)
/*  34:    */     throws SlickException
/*  35:    */   {
/*  36: 53 */     this.original = (this.image = new BigImage("testdata/bigimage.tga", 2, 512));
/*  37: 54 */     this.sub = this.image.getSubImage(210, 210, 200, 130);
/*  38: 55 */     this.scaledSub = this.sub.getScaledCopy(2.0F);
/*  39: 56 */     this.image = this.image.getScaledCopy(0.3F);
/*  40: 57 */     this.imageX = this.image.getFlippedCopy(true, false);
/*  41: 58 */     this.imageY = this.imageX.getFlippedCopy(true, true);
/*  42:    */     
/*  43: 60 */     this.bigSheet = new SpriteSheet(this.original, 16, 16);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void render(GameContainer container, Graphics g)
/*  47:    */   {
/*  48: 67 */     this.original.draw(0.0F, 0.0F, new Color(1.0F, 1.0F, 1.0F, 0.4F));
/*  49:    */     
/*  50: 69 */     this.image.draw(this.x, this.y);
/*  51: 70 */     this.imageX.draw(this.x + 400.0F, this.y);
/*  52: 71 */     this.imageY.draw(this.x, this.y + 300.0F);
/*  53: 72 */     this.scaledSub.draw(this.x + 300.0F, this.y + 300.0F);
/*  54:    */     
/*  55: 74 */     this.bigSheet.getSprite(7, 5).draw(50.0F, 10.0F);
/*  56: 75 */     g.setColor(Color.white);
/*  57: 76 */     g.drawRect(50.0F, 10.0F, 64.0F, 64.0F);
/*  58: 77 */     g.rotate(this.x + 400.0F, this.y + 165.0F, this.ang);
/*  59: 78 */     g.drawImage(this.sub, this.x + 300.0F, this.y + 100.0F);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static void main(String[] argv)
/*  63:    */   {
/*  64:    */     try
/*  65:    */     {
/*  66: 88 */       AppGameContainer container = new AppGameContainer(new BigImageTest());
/*  67: 89 */       container.setDisplayMode(800, 600, false);
/*  68: 90 */       container.start();
/*  69:    */     }
/*  70:    */     catch (SlickException e)
/*  71:    */     {
/*  72: 92 */       e.printStackTrace();
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void update(GameContainer container, int delta)
/*  77:    */     throws SlickException
/*  78:    */   {
/*  79:100 */     this.ang += delta * 0.1F;
/*  80:102 */     if (container.getInput().isKeyDown(203)) {
/*  81:103 */       this.x -= delta * 0.1F;
/*  82:    */     }
/*  83:105 */     if (container.getInput().isKeyDown(205)) {
/*  84:106 */       this.x += delta * 0.1F;
/*  85:    */     }
/*  86:108 */     if (container.getInput().isKeyDown(200)) {
/*  87:109 */       this.y -= delta * 0.1F;
/*  88:    */     }
/*  89:111 */     if (container.getInput().isKeyDown(208)) {
/*  90:112 */       this.y += delta * 0.1F;
/*  91:    */     }
/*  92:    */   }
/*  93:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.BigImageTest
 * JD-Core Version:    0.7.0.1
 */