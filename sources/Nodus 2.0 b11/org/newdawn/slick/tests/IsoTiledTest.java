/*  1:   */ package org.newdawn.slick.tests;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.BasicGame;
/*  4:   */ import org.newdawn.slick.GameContainer;
/*  5:   */ import org.newdawn.slick.Graphics;
/*  6:   */ import org.newdawn.slick.SlickException;
/*  7:   */ import org.newdawn.slick.tiled.TiledMap;
/*  8:   */ import org.newdawn.slick.util.Bootstrap;
/*  9:   */ 
/* 10:   */ public class IsoTiledTest
/* 11:   */   extends BasicGame
/* 12:   */ {
/* 13:   */   private TiledMap tilemap;
/* 14:   */   
/* 15:   */   public IsoTiledTest()
/* 16:   */   {
/* 17:23 */     super("Isometric Tiled Map Test");
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void init(GameContainer container)
/* 21:   */     throws SlickException
/* 22:   */   {
/* 23:31 */     this.tilemap = new TiledMap("testdata/isoexample.tmx", "testdata/");
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void update(GameContainer container, int delta)
/* 27:   */     throws SlickException
/* 28:   */   {}
/* 29:   */   
/* 30:   */   public void render(GameContainer container, Graphics g)
/* 31:   */     throws SlickException
/* 32:   */   {
/* 33:48 */     this.tilemap.render(350, 150);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public static void main(String[] argv)
/* 37:   */   {
/* 38:57 */     Bootstrap.runAsApplication(new IsoTiledTest(), 800, 600, false);
/* 39:   */   }
/* 40:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.IsoTiledTest
 * JD-Core Version:    0.7.0.1
 */