/*   1:    */ package org.newdawn.slick.tests;
/*   2:    */ 
/*   3:    */ import org.newdawn.slick.Animation;
/*   4:    */ import org.newdawn.slick.AppGameContainer;
/*   5:    */ import org.newdawn.slick.BasicGame;
/*   6:    */ import org.newdawn.slick.GameContainer;
/*   7:    */ import org.newdawn.slick.Graphics;
/*   8:    */ import org.newdawn.slick.Image;
/*   9:    */ import org.newdawn.slick.PackedSpriteSheet;
/*  10:    */ import org.newdawn.slick.SlickException;
/*  11:    */ import org.newdawn.slick.SpriteSheet;
/*  12:    */ 
/*  13:    */ public class PackedSheetTest
/*  14:    */   extends BasicGame
/*  15:    */ {
/*  16:    */   private PackedSpriteSheet sheet;
/*  17:    */   private GameContainer container;
/*  18: 25 */   private float r = -500.0F;
/*  19:    */   private Image rocket;
/*  20:    */   private Animation runner;
/*  21:    */   private float ang;
/*  22:    */   
/*  23:    */   public PackedSheetTest()
/*  24:    */   {
/*  25: 37 */     super("Packed Sprite Sheet Test");
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void init(GameContainer container)
/*  29:    */     throws SlickException
/*  30:    */   {
/*  31: 44 */     this.container = container;
/*  32:    */     
/*  33: 46 */     this.sheet = new PackedSpriteSheet("testdata/testpack.def", 2);
/*  34: 47 */     this.rocket = this.sheet.getSprite("rocket");
/*  35:    */     
/*  36: 49 */     SpriteSheet anim = this.sheet.getSpriteSheet("runner");
/*  37: 50 */     this.runner = new Animation();
/*  38: 52 */     for (int y = 0; y < 2; y++) {
/*  39: 53 */       for (int x = 0; x < 6; x++) {
/*  40: 54 */         this.runner.addFrame(anim.getSprite(x, y), 50);
/*  41:    */       }
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void render(GameContainer container, Graphics g)
/*  46:    */   {
/*  47: 63 */     this.rocket.draw((int)this.r, 100.0F);
/*  48: 64 */     this.runner.draw(250.0F, 250.0F);
/*  49: 65 */     g.scale(1.2F, 1.2F);
/*  50: 66 */     this.runner.draw(250.0F, 250.0F);
/*  51: 67 */     g.scale(1.2F, 1.2F);
/*  52: 68 */     this.runner.draw(250.0F, 250.0F);
/*  53: 69 */     g.resetTransform();
/*  54:    */     
/*  55: 71 */     g.rotate(670.0F, 470.0F, this.ang);
/*  56: 72 */     this.sheet.getSprite("floppy").draw(600.0F, 400.0F);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void update(GameContainer container, int delta)
/*  60:    */   {
/*  61: 79 */     this.r += delta * 0.4F;
/*  62: 80 */     if (this.r > 900.0F) {
/*  63: 81 */       this.r = -500.0F;
/*  64:    */     }
/*  65: 84 */     this.ang += delta * 0.1F;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static void main(String[] argv)
/*  69:    */   {
/*  70:    */     try
/*  71:    */     {
/*  72: 94 */       AppGameContainer container = new AppGameContainer(new PackedSheetTest());
/*  73: 95 */       container.setDisplayMode(800, 600, false);
/*  74: 96 */       container.start();
/*  75:    */     }
/*  76:    */     catch (SlickException e)
/*  77:    */     {
/*  78: 98 */       e.printStackTrace();
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void keyPressed(int key, char c)
/*  83:    */   {
/*  84:106 */     if (key == 1) {
/*  85:107 */       this.container.exit();
/*  86:    */     }
/*  87:    */   }
/*  88:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.PackedSheetTest
 * JD-Core Version:    0.7.0.1
 */